package com.example.mychartandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mychartandroid.Fragments.FragmentDatePicker;
import com.example.mychartandroid.Fragments.FragmentDatePicker_;
import com.example.mychartandroid.Fragments.FragmentEditTextSlider;
import com.example.mychartandroid.Fragments.FragmentEditTextSlider_;
import com.example.mychartandroid.Fragments.FragmentMPChart;
import com.example.mychartandroid.Fragments.FragmentMPChart_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Calendar;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    void afterViews() {
        // Adiciona o fragmento ao container assim que as views forem criadas
        //showFrag1();

        showDateFragmetn();


    }

    public void showFrag1(){

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



}
