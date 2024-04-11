package com.example.mychartandroid.Fragments;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.R;
import com.google.android.material.button.MaterialButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@EFragment(R.layout.fragment_calendar_pick)
public class FragmentCalendar extends Fragment {


    @ViewById
    MaterialButton buttonSelectDate;

    @AfterViews
    void setup() {

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                // Criando a data mínima (10/03/2020)
                Calendar calendarMin = Calendar.getInstance();
                calendarMin.clear(); // Limpa qualquer configuração de tempo/hora presente
                calendarMin.set(2020, Calendar.MARCH, 10); // Note: Os meses começam do 0, então Março é 2
                Date minDate = calendarMin.getTime();

                // Criando a data máxima (25/04/2024)
                Calendar calendarMax = Calendar.getInstance();
                calendarMax.clear(); // Limpa qualquer configuração de tempo/hora presente
                calendarMax.set(2024, Calendar.APRIL, 25); // Note: Os meses começam do 0, então Abril é 3
                Date maxDate = calendarMax.getTime();

                Date initialDate = calendar.getTime(); // Data inicial é 6 meses antes da data máxima

                Calendar minCalendar = Calendar.getInstance();
                minCalendar.setTime(minDate);

                Calendar maxCalendar = Calendar.getInstance();
                maxCalendar.setTime(maxDate);

                Calendar initialCalendar = Calendar.getInstance();
                initialCalendar.setTime(initialDate);

                // Agora você pode passar objetos Calendar para newInstance
                DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance(minCalendar, maxCalendar, initialCalendar);

                dialogFragment.setDatePickerDialogListener(new DatePickerDialogFragment.DatePickerDialogListener() {
                    @Override
                    public void onDateSelected(Calendar date) {

                        // Formata a data como desejar
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String dateString = dateFormat.format(date.getTime());

                        // Define a data formatada como texto do botão
                        buttonSelectDate.setText("Data Selecionada:" + dateString);

                    }
                });

                dialogFragment.show(getFragmentManager(), "datePicker");

            }
        });

    }


}