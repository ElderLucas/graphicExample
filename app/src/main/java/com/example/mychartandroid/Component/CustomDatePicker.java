package com.example.mychartandroid.Component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

@EViewGroup(R.layout.dialog_date_picker)
public class CustomDatePicker extends LinearLayout {

    @ViewById(R.id.picker_day)
    NumberPicker picker_day;

    @ViewById(R.id.picker_month)
    NumberPicker picker_month;

    @ViewById(R.id.picker_year)
    NumberPicker picker_year;

    private Calendar minDate = Calendar.getInstance();
    private Calendar maxDate = Calendar.getInstance();
    private Calendar selectedDate = Calendar.getInstance();

    String[] meses = getContext().getResources().getStringArray(R.array.meses);


    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void initView() {
        setupPickers();
    }

    public void setMinDate(Calendar minDate) {
        this.minDate = minDate;
        setupPickers();
    }

    public void setMaxDate(Calendar maxDate) {
        this.maxDate = maxDate;
        setupPickers();
    }

    private void setupPickers() {

        // Ajuste para garantir que os limites de data sejam respeitados
        picker_year.setMinValue(minDate.get(Calendar.YEAR));
        picker_year.setMaxValue(maxDate.get(Calendar.YEAR));

        picker_year.setOnValueChangedListener((picker, oldVal, newVal) -> adjustPickers());

        picker_month.setMinValue(0);
        picker_month.setMaxValue(meses.length - 1);
        picker_month.setDisplayedValues(meses);


        picker_month.setOnValueChangedListener((picker, oldVal, newVal) -> adjustPickers());

        adjustPickers(); // Ajusta inicialmente os pickers
    }

    private void adjustPickers() {
        adjustMonthPicker();
        adjustDayPicker();
    }

    private void adjustMonthPicker() {
        int year = picker_year.getValue();
        picker_month.setMinValue(year == minDate.get(Calendar.YEAR) ? minDate.get(Calendar.MONTH) + 1 : 1);
        picker_month.setMaxValue(year == maxDate.get(Calendar.YEAR) ? maxDate.get(Calendar.MONTH) + 1 : 12);
    }

    private void adjustDayPicker() {
        int year = picker_year.getValue();
        int month = picker_month.getValue();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);

        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        picker_day.setMinValue((year == minDate.get(Calendar.YEAR) && month == minDate.get(Calendar.MONTH) + 1) ? minDate.get(Calendar.DAY_OF_MONTH) : 1);
        picker_day.setMaxValue((year == maxDate.get(Calendar.YEAR) && month == maxDate.get(Calendar.MONTH) + 1) ? maxDate.get(Calendar.DAY_OF_MONTH) : maxDayOfMonth);
    }


    public Calendar getSelectedDate() {
        int year = picker_year.getValue();
        int month = picker_month.getValue(); // Lembre-se: Janeiro = 0, Fevereiro = 1, etc.
        int day = picker_day.getValue();

        selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);

        return selectedDate;
    }
}