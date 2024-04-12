package com.example.mychartandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.mychartandroid.Fragments.FragmentCSVGen;
import com.example.mychartandroid.Fragments.FragmentCSVGen_;
import com.example.mychartandroid.Fragments.FragmentCalendar;
import com.example.mychartandroid.Fragments.FragmentCalendar_;
import com.example.mychartandroid.Fragments.FragmentDatePicker;

import com.example.mychartandroid.Fragments.FragmentDatePicker_;
import com.example.mychartandroid.Fragments.FragmentEditTextSlider;

import com.example.mychartandroid.Fragments.FragmentEditTextSlider_;
import com.example.mychartandroid.Fragments.FragmentInputDataJSON;

import com.example.mychartandroid.Fragments.FragmentLongText;

import com.example.mychartandroid.Fragments.FragmentLongText_;
import com.example.mychartandroid.Fragments.FragmentMPChart;

import com.example.mychartandroid.Fragments.FragmentMPChart_;
import com.example.mychartandroid.Fragments.PieChartFragment;
import com.example.mychartandroid.Fragments.PieChartFragment_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.json.JSONObject;

import java.util.Calendar;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    void afterViews() {
        // Adiciona o fragmento ao container assim que as views forem criadas
        showEditTextSlider();

        //showDateFragmetn();

        //showPieGraphic();

        //showPieGraphic();

        //ShowLongText();

        //showCalendarFragmetn();

        //showFragmentCSVGen();


    }

    public void showEditTextSlider(){

        FragmentEditTextSlider fragmento = new FragmentEditTextSlider_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmento)
                .commit();
    }

    public void showFrag0(){

        FragmentMPChart fragmento = new FragmentMPChart_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmento)
                .commit();
    }

    public void showDateFragmetn(){
        FragmentDatePicker fragmentDatePicker = new FragmentDatePicker_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentDatePicker)
                .commit();
    }

    public void showPieGraphic(){
        PieChartFragment fragmentPieChart = new PieChartFragment_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentPieChart)
                .commit();
    }


    public void ShowLongText(){


        FragmentLongText fragmentLongText = new FragmentLongText_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentLongText)
                .commit();

    }


    public void showCalendarFragmetn(){
        FragmentCalendar fragmentCalendarPicker = new FragmentCalendar_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentCalendarPicker)
                .commit();
    }

    public void showFragmentCSVGen(){
        FragmentCSVGen fragment = new FragmentCSVGen_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }




}
