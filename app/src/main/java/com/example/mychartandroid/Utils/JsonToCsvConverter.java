package com.example.mychartandroid.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

public class JsonToCsvConverter {

    public static void convertJsonToCsv(String json, String csvFilePath) throws IOException {
        JsonElement jsonElement = JsonParser.parseString(json);
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                // Correção aplicada aqui para verificar se o array JSON está vazio
                if (jsonArray.size()>0) { // ou jsonArray.size() > 0
                    // Extrair cabeçalhos do primeiro objeto
                    JsonObject firstObj = jsonArray.get(0).getAsJsonObject();
                    Set<Entry<String, JsonElement>> entries = firstObj.entrySet();
                    boolean isFirstHeader = true;
                    for (Entry<String, JsonElement> entry : entries) {
                        if (!isFirstHeader) {
                            writer.append(',');
                        }
                        writer.append(entry.getKey());
                        isFirstHeader = false;
                    }
                    writer.append('\n');

                    // Escrever os dados
                    for (JsonElement elem : jsonArray) {
                        JsonObject obj = elem.getAsJsonObject();
                        boolean isFirstColumn = true;
                        for (Entry<String, JsonElement> entry : entries) {
                            if (!isFirstColumn) {
                                writer.append(',');
                            }
                            writer.append(obj.get(entry.getKey()).getAsString());
                            isFirstColumn = false;
                        }
                        writer.append('\n');
                    }
                }
            }
        }
    }
}

