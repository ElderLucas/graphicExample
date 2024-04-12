package com.example.mychartandroid.Component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mychartandroid.R;
import com.google.android.material.slider.Slider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.component_edittext_slider)
public class EditTextSliderComponent extends LinearLayout {

    @ViewById
    protected EditText editText;

    @ViewById
    protected Slider slider;

    private String prefix = "R$";
    private String suffix = " BRL";
    private int decimalPlaces = 0;
    private float minValue = 0;
    private float maxValue = 500;
    private boolean isProgrammaticChange = false;
    private String lastValidValue = "";

    public EditTextSliderComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    protected void initView() {

        // Configuração inicial do Slider
        configureSlider(minValue, maxValue, 1);

        // Sua configuração inicial aqui, incluindo a definição de um valor inicial para lastValidValue
        lastValidValue = prefix + "0" + suffix; // Ajuste conforme necessário
        updateEditTextWithSliderValue(slider.getValue());

        // Listeners do Slider
        slider.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                isProgrammaticChange = true;
                updateEditTextWithSliderValue(value);
                isProgrammaticChange = false;
            }
        });

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int position = editText.getOffsetForPosition(event.getX(), event.getY());
                position = Math.max(position, prefix.length());
                position = Math.min(position, editText.length() - suffix.length());
                editText.setSelection(position);
                return true;
            }
            return false;
        }); 

        slider.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                isProgrammaticChange = true;
                updateEditTextWithSliderValue(value);
                isProgrammaticChange = false;
            }
        });



        // Listener para mudanças no texto do EditText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isProgrammaticChange) {
                    updateSliderWithEditText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isProgrammaticChange) return; // Evitar loop infinito

                String text = s.toString();
                int suffixLength = suffix.isEmpty() ? 0 : suffix.length();
                // Verificar se o texto atual termina com o sufixo e ajustar a verificação de casas decimais
                boolean endsWithSuffix = text.endsWith(suffix) && suffixLength > 0;
                String textWithoutSuffix = endsWithSuffix ? text.substring(0, text.length() - suffixLength) : text;

                if (textWithoutSuffix.contains(".")) {
                    int decimalIndex = textWithoutSuffix.indexOf(".");
                    int numDecimalPlaces = textWithoutSuffix.length() - decimalIndex - 1;
                    if (numDecimalPlaces > decimalPlaces) {
                        isProgrammaticChange = true;
                        String correctedText = textWithoutSuffix.substring(0, decimalIndex + 1 + decimalPlaces);
                        s.replace(0, s.length(), correctedText + (endsWithSuffix ? suffix : ""));
                        isProgrammaticChange = false;
                    }
                }
            }

        });
    }


    private void configureSlider(float minValue, float maxValue, float stepSize) {
        slider.setValueFrom(minValue);
        slider.setValueTo(maxValue);
        slider.setStepSize(stepSize);
    }

    private void updateEditTextWithSliderValue(float value) {
        // Assegura a aplicação do prefixo, valor formatado e sufixo
        String formattedValue = String.format("%s%." + decimalPlaces + "f%s", prefix, value, suffix);
        // Utiliza isProgrammaticChange para evitar chamadas recursivas
        isProgrammaticChange = true;
        editText.setText(formattedValue);
        isProgrammaticChange = false;
    }

    private void updateSliderWithEditText(String input) {
        if (isProgrammaticChange) {
            // Se a mudança foi programática, não processar para evitar loop
            return;
        }

        try {
            String text = input.replace(prefix, "").replace(suffix, "");
            float value = Float.parseFloat(text);

            if (value >= minValue && value <= maxValue) {
                // Se o valor está dentro da faixa permitida
                slider.setValue(adjustValueToStepSize(value, slider.getStepSize()));
                //slider.setValue(value);
                lastValidValue = input; // Atualizar o último valor válido
            } else {
                // Se o valor está fora da faixa, restaurar o último valor válido
                showToastMessage("Valor fora da faixa permitida.");
                restoreLastValidValue();
            }
        } catch (NumberFormatException e) {
            // Se a entrada é inválida, restaurar o último valor válido
            showToastMessage("Entrada inválida. Por favor, insira um valor numérico.");
            restoreLastValidValue();
        }
    }

    private void restoreLastValidValue() {
        isProgrammaticChange = true; // Impedir loop ao restaurar o valor
        editText.setText(lastValidValue);
        isProgrammaticChange = false;
    }

    private float adjustValueToStepSize(float value, float stepSize) {
        // Arredonda o valor para o múltiplo mais próximo de stepSize
        long multiple = Math.round(value / stepSize);
        return multiple * stepSize;
    }


    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Métodos setters e getters para prefix, suffix, decimalPlaces, minValue, maxValue
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = Math.max(0, Math.min(decimalPlaces, 4));
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
        configureSlider(minValue, maxValue, 1);
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        configureSlider(minValue, maxValue, 1);
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void updateView() {
        updateEditTextWithSliderValue(slider.getValue());
    }

}