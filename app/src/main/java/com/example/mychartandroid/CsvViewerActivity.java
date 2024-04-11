package com.example.mychartandroid;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_csv_viewer)
public class CsvViewerActivity extends AppCompatActivity {

    @ViewById
    TextView csvContentTextView;

    @Extra
    String csvFilePath;

    @AfterViews
    protected void init() {
        // Aqui, leia o arquivo CSV do caminho fornecido e exiba seu conteúdo
        // Isso é apenas um esboço, ajuste conforme necessário para sua implementação específica
        String csvContent = readCsvFile(csvFilePath);
        csvContentTextView.setText(csvContent);
    }

    private String readCsvFile(String path) {
        // Implemente a lógica para ler o arquivo CSV do armazenamento e retornar seu conteúdo como String.
        // Isso é um esboço, por favor, implemente de acordo com a lógica de leitura do arquivo do seu app.
        return "Conteúdo do CSV aqui...";
    }
}