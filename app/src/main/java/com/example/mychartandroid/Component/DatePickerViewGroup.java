package com.example.mychartandroid.Component;

import com.example.mychartandroid.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

@EViewGroup(R.layout.dialog_date_picker)
public class DatePickerViewGroup extends LinearLayout {

    @ViewById
    protected NumberPicker dayPicker, monthPicker, yearPicker;

    @ViewById
    protected Button confirmButton;

    @ViewById
    protected TextView dateInfoTextView; // Correctly injected with Android Annotations

    private Calendar minCalendar = Calendar.getInstance();
    private Calendar maxCalendar = Calendar.getInstance();
    private Calendar currentCalendar = Calendar.getInstance();

    public interface DatePickerDialogListener {
        void onDateSelected(Calendar date);
    }

    private DatePickerDialogListener listener;

    public DatePickerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDatePickerDialogListener(DatePickerDialogListener listener) {
        this.listener = listener;
    }

    @AfterViews
    void init() {
        setupPickers();
        confirmButton.setOnClickListener(v -> {
            if (listener != null && dateIsWithinBounds(currentCalendar, minCalendar, maxCalendar)) {
                listener.onDateSelected(currentCalendar);
                confirmButton.setText(formatDate(currentCalendar)); // Optional: display date on button
            }
        });
        updateDateInfo();
    }

    private void setupPickers() {
        setupMonthPicker();
        setupDayAndYearPickers();
    }

    private void setupMonthPicker() {
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        months = Arrays.stream(months).filter(month -> !month.isEmpty()).toArray(String[]::new);

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length - 1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(currentCalendar.get(Calendar.MONTH));

        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            currentCalendar.set(Calendar.MONTH, newVal);
            updateDaysOfMonth();
        });
    }

    private void setupDayAndYearPickers() {
        yearPicker.setMinValue(minCalendar.get(Calendar.YEAR));
        yearPicker.setMaxValue(maxCalendar.get(Calendar.YEAR));
        yearPicker.setValue(currentCalendar.get(Calendar.YEAR));

        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            currentCalendar.set(Calendar.YEAR, newVal);
            updateMonthsOfYear();
            updateDaysOfMonth();
        });

        updateDaysOfMonth();
    }

    private void updateMonthsOfYear() {
        int year = yearPicker.getValue();
        int minMonth = (year == minCalendar.get(Calendar.YEAR)) ? minCalendar.get(Calendar.MONTH) : 0;
        int maxMonth = (year == maxCalendar.get(Calendar.YEAR)) ? maxCalendar.get(Calendar.MONTH) : 11;
        monthPicker.setMinValue(minMonth);
        monthPicker.setMaxValue(maxMonth);
        monthPicker.setValue(currentCalendar.get(Calendar.MONTH));
    }

    private void updateDaysOfMonth() {
        int maxDayOfMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(maxDayOfMonth);
        dayPicker.setValue(currentCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean dateIsWithinBounds(Calendar date, Calendar min, Calendar max) {
        return !date.before(min) && !date.after(max);
    }

    private String formatDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return dateFormat.format(date.getTime());
    }

    private void updateDateInfo() {
        dateInfoTextView.setText("Data Mínima: " + formatDate(minCalendar) +
                "\nData Máxima: " + formatDate(maxCalendar));
    }

    public void setMinimumDate(Calendar minDate) {
        minCalendar = minDate;
        yearPicker.setMinValue(minDate.get(Calendar.YEAR));
        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    public void setMaximumDate(Calendar maxDate) {
        maxCalendar = maxDate;
        yearPicker.setMaxValue(maxDate.get(Calendar.YEAR));
        updateMonthsOfYear();
        updateDaysOfMonth();
    }

    public void setInitialDate(Calendar initialDate) {
        currentCalendar = initialDate;
        yearPicker.setValue(initialDate.get(Calendar.YEAR));
        monthPicker.setValue(initialDate.get(Calendar.MONTH));
        updateMonthsOfYear();
        updateDaysOfMonth();
        dayPicker.setValue(initialDate.get(Calendar.DAY_OF_MONTH));
    }
}
