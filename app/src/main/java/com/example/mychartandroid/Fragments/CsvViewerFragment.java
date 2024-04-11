package com.example.mychartandroid.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mychartandroid.R;

public class CsvViewerFragment extends Fragment {

    private static final String ARG_CSV_PATH = "csvFilePath";

    public static CsvViewerFragment newInstance(String csvFilePath) {
        CsvViewerFragment fragment = new CsvViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CSV_PATH, csvFilePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csv_viewer, container, false);
        TextView csvContentTextView = view.findViewById(R.id.csvContentTextView);
        if (getArguments() != null && getArguments().containsKey(ARG_CSV_PATH)) {
            String csvFilePath = getArguments().getString(ARG_CSV_PATH);
            // Aqui, leia o arquivo CSV do caminho fornecido e exiba o seu conteúdo
            String csvContent = ""; // Substitua isso pela lógica de leitura do arquivo CSV
            csvContentTextView.setText(csvContent);
        }
        return view;
    }
}
