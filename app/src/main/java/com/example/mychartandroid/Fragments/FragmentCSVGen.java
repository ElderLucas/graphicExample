package com.example.mychartandroid.Fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;

import com.example.mychartandroid.CsvViewerActivity_;
import com.example.mychartandroid.CustomChooserActivity;
import com.example.mychartandroid.CustomChooserActivity_;
import com.example.mychartandroid.R;
import com.example.mychartandroid.Utils.CsvDownloader;
import com.example.mychartandroid.Utils.JsonToCsvConverter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@EFragment(R.layout.fragment_json2csv)
public class FragmentCSVGen extends Fragment {

    //@ViewById
    //Button download_csv;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_json2csv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        view.findViewById(R.id.download_csv).setOnClickListener(v -> checkAndRequestPermissions());
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        } else {

            saveCsvAndShare(genFakeData());
            //saveCsvAndShare();
            Toast.makeText(getContext(), "Salvar o Arquivo e Compartilhar", Toast.LENGTH_SHORT).show();
            //saveCsv(); // Diretamente salva o CSV se a permissão já está concedida ou não é necessária
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                saveCsvAndShare(genFakeData());
                //saveCsvAndShare();
                //saveCsv(); // Salva o CSV uma vez que a permissão foi concedida
            } else {
                Toast.makeText(getContext(), "Permissão necessária para salvar o arquivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveCsv() {
        String csvContent = "Nome,Idade,Email\nJoão,28,joao@example.com\nMaria,32,maria@example.com";
        String fileName = "usuarios.csv";

        CsvDownloader.saveCsvToDownloads(getContext(), csvContent, fileName);

        // Notificar o usuário que o arquivo foi salvo
        Toast.makeText(getContext(), "Arquivo CSV salvo na pasta Downloads", Toast.LENGTH_LONG).show();
    }


    public void saveCsvAndShare() {
        String csvContent = "Nome,Idade,Email\nJoão,28,joao@example.com\nMaria,32,maria@example.com";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String fileName = "usuarios_" + currentDateandTime + ".csv";

        // Chamada para salvar o arquivo CSV na pasta de Downloads
        CsvDownloader.saveCsvToDownloads(getContext(), csvContent, fileName, new CsvDownloader.Callback() {
            @Override
            public void onSuccess(String filePath) {
                // Após salvar o arquivo com sucesso, compartilhe-o
                shareOrOpenCsvFile(filePath);
                //showCsvFileOptions(filePath);
            }

            @Override
            public void onFailure(IOException e) {
                Toast.makeText(getContext(), "Erro ao salvar o arquivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveCsvAndShare(JSONObject jsonResult) {
        StringBuilder csvBuilder = new StringBuilder();
        try {
            // Extrair o objeto "result"
            JSONObject result = jsonResult.getJSONObject("result");

            // Processando as colunas
            JSONArray colunas = result.getJSONArray("colunas");
            for (int i = 0; i < colunas.length(); i++) {
                JSONObject coluna = colunas.getJSONObject(i);
                csvBuilder.append(coluna.getString("nome")).append(",");
            }
            // Removendo a última vírgula e adicionando quebra de linha
            csvBuilder.deleteCharAt(csvBuilder.length() - 1).append("\n");

            // Processando os itens
            JSONArray itens = result.getJSONArray("itens");
            for (int i = 0; i < itens.length(); i++) {
                // Cada item é agora um JSONArray
                JSONArray item = itens.getJSONArray(i);
                for (int j = 0; j < item.length(); j++) {
                    Object valor = item.get(j);
                    csvBuilder.append(valor.toString()).append(",");
                }
                // Removendo a última vírgula e adicionando quebra de linha ao final de cada item (linha)
                csvBuilder.deleteCharAt(csvBuilder.length() - 1).append("\n");
            }


            // Conteúdo CSV está pronto para ser salvo
            String csvContent = csvBuilder.toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());
            String fileName = "resultado_" + currentDateAndTime + ".csv";


            // Chamada para salvar o arquivo CSV na pasta de Downloads
            CsvDownloader.saveCsvToDownloads(getContext(), csvContent, fileName, new CsvDownloader.Callback() {
                @Override
                public void onSuccess(String filePath) {
                    // Após salvar o arquivo com sucesso, compartilhe-o
                    shareOrOpenCsvFile(filePath);
                    // Chama uma Activity para exibir um Arquivo
                    //showCsvFileOptions(filePath);
                }

                @Override
                public void onFailure(IOException e) {
                    Toast.makeText(getContext(), "Erro ao salvar o arquivo: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            // Implemente a lógica para salvar o arquivo CSV e compartilhá-lo aqui
            // Por exemplo, você pode chamar saveCsvToDownloadsAndShare(csvContent, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        }
    }

    public void shareOrOpenCsvFile(String filePath) {
        File file = new File(filePath);
        Uri uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);
        Intent sendIntent = new Intent();
        sendIntent.setType("text/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent shareIntent = Intent.createChooser(sendIntent, "O que fazer com o arquivo?");
        startActivity(shareIntent);

    }

    public void shareOrViewCsvFile(String filePath) {

        CsvViewerActivity_.intent(getContext()).csvFilePath(filePath).start();

    }

    public void showCsvFileOptions(String csvFilePath) {
        CustomChooserActivity_.intent(this).csvFilePath(csvFilePath).start();
    }

    public void openCustomChooser(String csvFilePath) {

        CsvViewerActivity_.intent(getContext()).csvFilePath("caminho_para_seu_arquivo_csv").start();

    }



    public JSONObject genFakeData(){

        JSONObject json = new JSONObject();

        try {
            json = new JSONObject();

            JSONObject result = new JSONObject();
            json.put("result", result);

            JSONArray colunas = new JSONArray();
            result.put("colunas", colunas);

            colunas.put(new JSONObject().put("nome", "Coluna 1"));
            colunas.put(new JSONObject().put("nome", "Coluna 2"));
            colunas.put(new JSONObject().put("nome", "Coluna 3"));
            colunas.put(new JSONObject().put("nome", "Coluna 3"));

            JSONArray itens = new JSONArray();
            result.put("itens", itens);

            JSONArray item1 = new JSONArray();
            item1.put(100);
            item1.put("tipo");
            item1.put(12.5);
            item1.put("casa");

            JSONArray item2 = new JSONArray();
            item2.put(101);
            item2.put("tipo");
            item2.put(12.5);
            item2.put("casa");

            JSONArray item3 = new JSONArray();
            item3.put(102);
            item3.put("tipo");
            item3.put(12.5);
            item3.put("casa");

            itens.put(item1);
            itens.put(item2);
            itens.put(item3);

            // Use o json em sua chamada de método
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }






}