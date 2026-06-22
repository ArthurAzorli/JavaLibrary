package br.edu.usp.javalibrary.javalibrary.service.repository;

import br.edu.usp.javalibrary.javalibrary.service.JsonService;
import br.edu.usp.javalibrary.javalibrary.service.domains.Category;
// import br.edu.usp.javalibrary.javalibrary.service.domains.User;

// import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class CategoryRepository {
    static final String categoryFilePath = "categories.json";

    private ArrayList<Category> categories;

    private static CategoryRepository instance;
    public static CategoryRepository getInstance(){
        if (instance == null) instance = new CategoryRepository();
        return instance;
    }

    private CategoryRepository(){}

    private void loadCategoriesFile() {
        try {
            Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
            categories = JsonService.getInstance().loadJson(categoryFilePath, listType);
        } catch (Exception e){
            categories = new ArrayList<>();
        }
    }

    private boolean saveCategoriesFile(){
        try{
            JsonService.getInstance().saveJson(categoryFilePath, categories);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Category> getCategories() {
        if (categories == null) loadCategoriesFile();
        return categories;
    }

    public ArrayList<Category> getCategories(ArrayList<UUID> categoriesID) {
        return getCategories().stream()
                .filter(category -> categoriesID.contains(category.id()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean addCategory(Category category) {
        final ArrayList<Category> categories = getCategories();
        categories.add(category);
        return saveCategoriesFile();
    }

    public boolean removeCategory(Category category) {
        final ArrayList<Category> categories = getCategories();
        categories.remove(category);
        return saveCategoriesFile();
    }


}
