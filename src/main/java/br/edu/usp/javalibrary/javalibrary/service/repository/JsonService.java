package br.edu.usp.javalibrary.javalibrary.service.repository;

import br.edu.usp.javalibrary.javalibrary.exceptions.FileLoadException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jakarta.inject.Singleton;

@Singleton
public class JsonService {

    private final Gson converter = new Gson();

    public <T> T loadJson(String filePath) {
        try {
            final Path path = Paths.get(filePath);
            final String json = Files.readString(path, StandardCharsets.UTF_8);
            final Type type = new TypeToken<T>() {}.getType();
            return converter.fromJson(json, type);
        } catch (Exception e) {
            throw new FileLoadException(filePath);
        }
    }

    public <T> void saveJson(String filePath, T data){
        try {
            final Path path = Paths.get(filePath);
            final String json = converter.toJson(data);
            Files.writeString(path, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
