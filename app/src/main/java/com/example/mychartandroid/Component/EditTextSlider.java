package com.example.mychartandroid.Component;

import com.example.mychartandroid.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.edit_text_slider)
public class EditTextSlider extends LinearLayout {

    @ViewById
    protected EditText editTextValue;
    @ViewById
    protected SeekBar slider;
    @ViewById
    protected TextView minValueView, maxValueView;

    private String prefix = "$";
    private String suffix = " USD";
    private int decimalPlaces = 2;
    private double minValue = 20.0;
    private double maxValue = 50.0;
    private double initValue = 22.5;
    private double interval = 0.1;

    public EditTextSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    protected void init() {
        setupEditText();
        updateDisplay(initValue, true);
        slider.setMax((int) ((maxValue - minValue) / interval));

    }

    private void setupEditText() {
        editTextValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Verifica se é uma ação de "conclusão" ou um evento de tecla "Enter"
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    validateAndCorrectInput();
                    // Fechar o teclado virtual
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus(); // Opcional: remover o foco do EditText
                    return true; // Consumir o evento
                }
                return false; // Não consumir o evento
            }
        });


        editTextValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateDisplay(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @SeekBarProgressChange(R.id.slider)
    void onSliderProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            double value = minValue + progress * interval;
            updateDisplay(value, true);
        }
    }

    private void validateDisplay(String input) {
        String strippedInput = input.replace(prefix, "").replace(suffix, "");
        if (strippedInput.matches("[\\d]*\\.?[\\d]{0," + decimalPlaces + "}")) {
            try {
                double enteredValue = Double.parseDouble(strippedInput);
                if (!isValueWithinRange(enteredValue)) {
                    editTextValue.setError("Valor fora da Faixa Permitida");
                } else {
                    editTextValue.setError(null);
                }
            } catch (NumberFormatException e) {
                editTextValue.setError("Número inválido");
            }
        } else {
            editTextValue.setError("Formato Inválido, digite até " + decimalPlaces + " " + "casas" );
        }
    }

    private void validateAndCorrectInput() {
        String input = editTextValue.getText().toString().replace(prefix, "").replace(suffix, "");
        if (input.matches("[\\d]*\\.?[\\d]{0," + decimalPlaces + "}")) {
            try {
                double enteredValue = Double.parseDouble(input);
                enteredValue = adjustValueWithinRange(enteredValue);
                updateDisplay(enteredValue, true);
            } catch (NumberFormatException e) {
                updateDisplay(minValue, true); // Reset to the minimum value if input is invalid
            }
        } else {

            double enteredValue = Double.parseDouble(input);
            if (!isValueWithinRange(enteredValue)) {
                enteredValue = adjustValueWithinRange(enteredValue);
                updateDisplay(enteredValue, true);
            } else {
                editTextValue.setError(null);
                updateDisplay(enteredValue, true);
            }
        }
    }

    private boolean isValueWithinRange(double value) {
        return value >= minValue && value <= maxValue;
    }

    private void updateDisplay(double value, boolean adjustToInterval) {
        if (adjustToInterval) {
            value = adjustValueWithinRange(value);
        }
        String formattedValue = String.format("%." + decimalPlaces + "f", value);
        editTextValue.setText(prefix + formattedValue + suffix);
        editTextValue.setSelection(prefix.length()); // Move cursor to the beginning
        slider.setProgress((int) ((value - minValue) / interval));
    }

    private double adjustValueWithinRange(double value) {
        // Primeiro, limitar o valor aos limites máximos e mínimos
        double limitedValue = Math.max(minValue, Math.min(value, maxValue));

        // Calcular a diferença do valor limitado para o valor mínimo
        double difference = limitedValue - minValue;

        // Calcular o número de intervalos desde o valor mínimo
        double intervalCount = Math.round(difference / interval);

        // Calcular o valor ajustado baseado no número de intervalos
        double adjustedValue = minValue + intervalCount * interval;

        // Garantir que o valor ajustado ainda esteja dentro dos limites
        return Math.max(minValue, Math.min(adjustedValue, maxValue));
    }


    private void updateLimitViews() {
        minValueView.setText(String.format("%s%.2f%s", prefix, minValue, suffix));
        maxValueView.setText(String.format("%s%.2f%s", prefix, maxValue, suffix));
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        updateDisplay(minValue, true);
        updateLimitViews();

        editTextValue.setSelection(prefix.length());
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        updateDisplay(minValue, true);
        updateLimitViews();
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        updateDisplay(minValue, true);
        updateLimitViews();
    }

    public void setRange(double minValue, double maxValue, double interval) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.interval = interval;
        slider.setMax((int) ((maxValue - minValue) / interval));
        updateDisplay(minValue, true);
        updateLimitViews();
    }
}
