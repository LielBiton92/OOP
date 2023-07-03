package com.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtilities {

    private static final Type REVIEW_TYPE = new TypeToken<List<JsonModel>>() {
    }.getType();

    public static JsonModel getJsonModelFromFile(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        JsonModel data = gson.fromJson(reader, JsonModel.class);
        return data;
    }


}
