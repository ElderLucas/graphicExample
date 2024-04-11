package com.example.mychartandroid.Component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

@EViewGroup(R.layout.dialog_date_picker)
public class DatePickerViewGroup extends LinearLayout {

    @ViewById
    protected NumberPicker dayPicker, monthPicker, yearPicker;

    @ViewById
    protected Button confirmButton;

    private Calendar minCalendar = Calendar.getInstance();
    private Calendar maxCalendar = Calendar.getInstance();
    private Calendar currentCalendar = Calendar.getInstance();

    private OnDateSelectedListener dateSelectedListener;

    public DatePickerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar date);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.dateSelectedListener = listener;
    }

    @AfterViews
    void init() {
        setupPickers();
        confirmButton.setOnClickListener(v -> {
            if (dateSelectedListener != null) {
                dateSelectedListener.onDateSelected(currentCalendar);
            }
        });
    }

    private void setupPickers() {
        setupMonthPicker();

        int currentYear = currentCalendar.get(Calendar.YEAR);
        yearPicker.setMinValue(currentYear - 100);
        yearPicker.setMaxValue(currentYear + 100);
        yearPicker.setValue(currentYear);

        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            currentCalendar.set(Calendar.MONTH, newVal);
            updateDaysOfMonth();
        });

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            currentCalendar.set(Calendar.YEAR, newVal);
            updateMonthsOfYear();
            updateDaysOfMonth();
        });

        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    private void setupMonthPicker() {
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        months = Arrays.stream(months).filter(month -> !month.isEmpty()).toArray(String[]::new);

        monthPicker.setDisplayedValues(null); // Limpa valores exibidos antes de definir o intervalo
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length - 1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(currentCalendar.get(Calendar.MONTH));
    }

    public void setMinimumDate(Calendar minDate) {
        this.minCalendar = minDate;
        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    public void setMaximumDate(Calendar maxDate) {
        this.maxCalendar = maxDate;
        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    public void setInitialDate(Calendar initialDate) {
        if (initialDate != null) {
            currentCalendar = initialDate;
            yearPicker.setValue(currentCalendar.get(Calendar.YEAR));
            monthPicker.setValue(currentCalendar.get(Calendar.MONTH));
            dayPicker.setValue(currentCalendar.get(Calendar.DAY_OF_MONTH));
        }
        adjustPickerRanges();
    }

    private void adjustPickerRanges() {
        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    private void updateMonthsOfYear() {
        int minMonth = (yearPicker.getValue() == minCalendar.get(Calendar.YEAR)) ? minCalendar.get(Calendar.MONTH) : 0;
        int maxMonth = (yearPicker.getValue() == maxCalendar.get(Calendar.YEAR)) ? maxCalendar.get(Calendar.MONTH) : 11;
        monthPicker.setDisplayedValues(null); // Necessário para resetar os valores
        monthPicker.setMinValue(minMonth);
        monthPicker.setMaxValue(maxMonth);
        // Ajusta o valor do mês para garantir que está dentro do novo intervalo
        monthPicker.setValue(Math.max(minMonth, Math.min(monthPicker.getValue(), maxMonth)));
        // Reaplicando os nomes dos meses para refletir a mudança de intervalo
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        months = Arrays.stream(months).filter(month -> !month.isEmpty()).toArray(String[]::new);
        monthPicker.setDisplayedValues(months);
    }

    private void updateDaysOfMonth() {
        int maxDayOfMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(maxDayOfMonth);
        dayPicker.setValue(currentCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
