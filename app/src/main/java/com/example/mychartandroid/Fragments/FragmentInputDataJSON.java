package com.example.mychartandroid.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.json.JSONException;
import org.json.JSONObject;

@EFragment (R.layout.fragment_mpchart)
public class FragmentInputDataJSON extends Fragment {

    // Inicialização de variáveis para armazenar os argumentos
    private String jsonString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jsonString = getArguments().getString("ARG_JSON", "");
            // Aqui você pode usar o jsonString como necessário
        }
    }

    // Método estático para adicionar argumentos à fragment
    public static FragmentInputDataJSON newInstance(String json) {
        FragmentInputDataJSON fragment = new FragmentInputDataJSON_();
        Bundle args = new Bundle();
        args.putString("ARG_JSON", json);
        fragment.setArguments(args);
        return fragment;
    }

}
