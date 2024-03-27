package com.example.mychartandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mychartandroid.Fragments.FragmentMPChart;
import com.example.mychartandroid.Fragments.FragmentMPChart_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @AfterViews
    void afterViews() {
        // Adiciona o fragmento ao container assim que as views forem criadas
        FragmentMPChart fragmento = new FragmentMPChart_();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmento)
                .commit();
    }
}
