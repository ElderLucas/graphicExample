package com.example.mychartandroid.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mychartandroid.Component.DatePickerViewGroup;
import com.example.mychartandroid.Component.DatePickerViewGroup_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

@EFragment
public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerViewGroup datePickerViewGroup;
    private Calendar minDate, maxDate, initialDate;

    public interface DatePickerDialogListener {
        void onDateSelected(Calendar date);
    }

    private DatePickerDialogListener listener;

    // Método para definir o listener
    public void setDatePickerDialogListener(DatePickerDialogListener listener) {
        this.listener = listener;
    }


    // Utilize este método estático para criar novas instâncias do fragmento com argumentos de data
    public static DatePickerDialogFragment newInstance(Calendar minDate, Calendar maxDate, Calendar initialDate) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment_();
        Bundle args = new Bundle();
        args.putSerializable("minDate", minDate);
        args.putSerializable("maxDate", maxDate);
        args.putSerializable("initialDate", initialDate);
        fragment.setArguments(args);
        return fragment;
    }

    @AfterInject
    void afterInject() {
        // Extrai as datas do Bundle de argumentos e as armazena nas variáveis locais
        if (getArguments() != null) {
            minDate = (Calendar) getArguments().getSerializable("minDate");
            maxDate = (Calendar) getArguments().getSerializable("maxDate");
            initialDate = (Calendar) getArguments().getSerializable("initialDate");


        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        // Infla o DatePickerViewGroup e configura as datas
        datePickerViewGroup = DatePickerViewGroup_.build(getContext(),null);

        if (minDate != null) datePickerViewGroup.setMinimumDate(minDate);
        if (maxDate != null) datePickerViewGroup.setMaximumDate(maxDate);
        if (initialDate != null) datePickerViewGroup.setInitialDate(initialDate);

        dialog.setContentView(datePickerViewGroup);
        dialog.setTitle("Selecione uma Data");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Definindo o listener para quando o botão for clicado e a data for selecionada
        datePickerViewGroup.setOnDateSelectedListener(new DatePickerViewGroup.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar date) {
                if (listener != null) {
                    listener.onDateSelected(date);
                }
                // Fechando o diálogo
                dialog.dismiss();
            }
        });

        return dialog;
    }
}