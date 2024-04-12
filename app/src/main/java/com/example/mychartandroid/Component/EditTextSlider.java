package com.example.mychartandroid.Component;
import com.example.mychartandroid.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.TextChange;

@EViewGroup(R.layout.edit_text_slider)
public class EditTextSlider extends LinearLayout {

    @ViewById
    protected EditText editTextValue;
    @ViewById
    protected SeekBar slider;
    @ViewById
    protected TextView textViewValue;

    private String prefix = "$";
    private String suffix = " USD";
    private int decimalPlaces = 2;
    private double minValue = 20.0;
    private double maxValue = 50.0;
    private double interval = 0.5;

    public EditTextSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    protected void init() {
        setupEditText();
        updateDisplay(minValue, true);
        slider.setMax((int) ((maxValue - minValue) / interval));
    }

    private void setupEditText() {
        editTextValue.addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ignoreChange && !s.toString().isEmpty()) {
                    ignoreChange = true;
                    String strippedInput = s.toString().replace(prefix, "").replace(suffix, "");
                    if (strippedInput.matches("[\\d]*\\.?[\\d]*")) {
                        try {
                            double enteredValue = Double.parseDouble(strippedInput);
                            if (strippedInput.contains(".")) {
                                int dotIndex = strippedInput.indexOf('.');
                                int digitsAfterDecimal = strippedInput.length() - dotIndex - 1;
                                if (digitsAfterDecimal > decimalPlaces) {
                                    strippedInput = strippedInput.substring(0, dotIndex + decimalPlaces + 1);
                                    enteredValue = Double.parseDouble(strippedInput);
                                }

                                enteredValue = adjustValueWithinRange(enteredValue);
                                updateDisplay(enteredValue, false); // Não ajustar ao intervalo enquanto digita após o ponto
                            } else {
                                enteredValue = adjustValueWithinRange(enteredValue);
                                updateDisplay(enteredValue, true); // Ajustar ao intervalo para valores completos
                            }
                        } catch (NumberFormatException e) {
                            // Ignora entradas não numéricas parcialmente completadas
                        }
                    }
                    ignoreChange = false;
                }
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

    private void updateDisplay(double value, boolean adjustToInterval) {
        if (adjustToInterval) {
            value = adjustValueWithinRange(value);
        }
        String formattedValue = String.format("%." + decimalPlaces + "f", value);
        editTextValue.setText(prefix + formattedValue + suffix);
        editTextValue.setSelection(prefix.length() + formattedValue.length());
        slider.setProgress((int) ((value - minValue) / interval));
        textViewValue.setText(formattedValue);
    }

    private double adjustValueWithinRange(double value) {
        value = Math.max(minValue, Math.min(value, maxValue));  // Garante que o valor esteja dentro dos limites
        double remainder = (value - minValue) % interval;
        return remainder < interval / 2 ? value - remainder : value + interval - remainder;  // Ajusta ao intervalo mais próximo
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        updateDisplay(minValue, true);
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        updateDisplay(minValue, true);
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        updateDisplay(minValue, true);
    }

    public void setRange(double minValue, double maxValue, double interval) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.interval = interval;
        slider.setMax((int) ((maxValue - minValue) / interval));
        updateDisplay(minValue, true);
    }
}
