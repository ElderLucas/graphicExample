package com.example.mychartandroid.Fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.mychartandroid.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

@EFragment(R.layout.dialog_date_picker)
public class DatePickerDialogFragment extends DialogFragment {

    @ViewById
    protected NumberPicker dayPicker, monthPicker, yearPicker;

    @ViewById
    protected TextView dateInfoTextView;

    @ViewById
    protected Button confirmButton;

    private Calendar minDate, maxDate, currentDate;

    public interface DatePickerCallback {
        void onDateSelected(Calendar selectedDate);
    }

    private DatePickerCallback callback;

    public static DatePickerDialogFragment newInstance(Calendar minDate, Calendar maxDate, Calendar currentDate, DatePickerCallback callback) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment_();
        fragment.minDate = minDate;
        fragment.maxDate = maxDate;
        fragment.currentDate = currentDate;
        fragment.callback = callback;
        return fragment;
    }


    @AfterViews
    protected void setupDialog() {
        initializePickerValues();
        setupListeners();
    }

    private void initializePickerValues() {
        String[] months = new DateFormatSymbols().getMonths();
        monthPicker.setDisplayedValues(months);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(currentDate.get(Calendar.MONTH) + 1);

        yearPicker.setMinValue(minDate.get(Calendar.YEAR));
        yearPicker.setMaxValue(maxDate.get(Calendar.YEAR));
        yearPicker.setValue(currentDate.get(Calendar.YEAR));

        updateDays();
    }

    private void updateDays() {
        int month = monthPicker.getValue();
        int year = yearPicker.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(maxDayOfMonth);
        dayPicker.setValue(Math.min(Math.max(currentDate.get(Calendar.DAY_OF_MONTH), 1), maxDayOfMonth));
    }

    private void setupListeners() {
        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDays());
        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDays());

        confirmButton.setOnClickListener(v -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, yearPicker.getValue());
            selectedDate.set(Calendar.MONTH, monthPicker.getValue() - 1);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayPicker.getValue());

            if (selectedDate.before(minDate) || selectedDate.after(maxDate)) {
                dateInfoTextView.setText("Selected date is out of range. Please select another date.");
            } else {
                if (callback != null) {
                    callback.onDateSelected(selectedDate);
                }
                dismiss();
            }
        });
    }
}
