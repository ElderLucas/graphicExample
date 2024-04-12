package com.example.mychartandroid;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@EActivity(R.layout.activity_csv_viewer)
public class CsvViewerActivity extends AppCompatActivity {

    @ViewById
    TableLayout tableLayout;

    @Extra
    String csvFilePath;

    @AfterViews
    void init() {
        loadCsvIntoTable(csvFilePath);
    }

    private void loadCsvIntoTable(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(","); // Assumindo que o separador é uma vírgula
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                for (String cell : row) {
                    TextView textView = new TextView(this);
                    textView.setText(cell);
                    textView.setPadding(5, 10, 5, 10);
                    tableRow.addView(textView);
                }

                tableLayout.addView(tableRow);
            }

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
