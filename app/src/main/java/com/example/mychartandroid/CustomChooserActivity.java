package com.example.mychartandroid;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.activity_custom_chooser)
public class CustomChooserActivity extends AppCompatActivity {

    @Extra("csvFilePath")
    protected String csvFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // O Android Annotations cuida da injeção de extras, então não é necessário recuperar manualmente o csvFilePath.
    }

    @Click(R.id.share_button)
    void shareCsvFile() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/csv");
        // Configurar o Uri do arquivo aqui, não esqueça de usar FileProvider se necessário
        startActivity(Intent.createChooser(sendIntent, "Compartilhar CSV"));
    }

    @Click(R.id.view_button)
    void viewCsvFile() {
        // Certifique-se de que CsvViewerActivity_ é gerado pelo Android Annotations
        CsvViewerActivity_.intent(this).csvFilePath(csvFilePath).start();
        finish();
    }
}