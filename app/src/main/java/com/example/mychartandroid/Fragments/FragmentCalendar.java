package com.example.mychartandroid.Fragments;


import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.R;
import com.google.android.material.button.MaterialButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@EFragment(R.layout.fragment_calendar_pick)
public class FragmentCalendar extends Fragment {

    @ViewById(R.id.buttonSelectDate)
    Button datePickerButton;

    @AfterViews
    void initViews() {

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();

        minDate.set(2020, Calendar.FEBRUARY, 10);  // Data inicial mínima
        maxDate.set(2023, Calendar.MARCH, 20);    // Data máxima
        currentDate.set(2021, Calendar.JUNE, 15); // Data atual selecionada

        DatePickerDialogFragment.newInstance(minDate, maxDate, currentDate, this::onDateSelected)
                .show(getChildFragmentManager(), "datePicker");
    }

    private void onDateSelected(Calendar date) {
        // Atualizar a interface do usuário com a data selecionada
        datePickerButton.setText("Data Selecionada: " + android.text.format.DateFormat.format("dd MMMM yyyy", date));
    }
}