package com.example.mychartandroid.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mychartandroid.Component.CustomDatePicker;
import com.example.mychartandroid.Component.CustomDatePicker_;
import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

@EFragment
public class FragmentDatePicker extends Fragment {

    @ViewById(R.id.fragmentContainer)
    LinearLayout datePickerContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @AfterViews
    void initView() {

        // Cria o CustomDatePicker programaticamente
        CustomDatePicker customDatePicker = CustomDatePicker_.build(getContext(),null);
        // Configura as datas mínima e máxima
        Calendar minDate = Calendar.getInstance();
        minDate.set(2020, Calendar.JANUARY, 1);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2024, Calendar.DECEMBER, 31);

        customDatePicker.setMinDate(minDate);
        customDatePicker.setMaxDate(maxDate);

        // Adiciona o CustomDatePicker ao container
        datePickerContainer.addView(customDatePicker);
    }
}