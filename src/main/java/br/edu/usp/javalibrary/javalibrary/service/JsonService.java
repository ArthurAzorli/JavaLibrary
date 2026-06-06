package br.edu.usp.javalibrary.javalibrary.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class JsonService {
    private final Gson converter = new Gson();

    private static JsonService instance;
    public static JsonService getInstance(){
        if (instance == null) instance = new  JsonService();
        return instance;
    }

    private JsonService(){}

    public <T> T loadJson(String filePath) throws IOException {
        final Path path = Paths.get(filePath);
        final String json = Files.readString(path, StandardCharsets.UTF_8);
        final Type type = new TypeToken<T>() {
        }.getType();
        return converter.fromJson(json, type);
    }

    public <T> void saveJson(String filePath, T data) throws IOException {
        final Path path = Paths.get(filePath);
        final String json = converter.toJson(data);
        Files.writeString(path, json, StandardCharsets.UTF_8);
    }

}
