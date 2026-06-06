package br.edu.usp.javalibrary.javalibrary.service.domains;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Book {
    String isbn;
    String title;
    String description;
    String publisher;
    ArrayList<String> authors;
    ArrayList<UUID> categories;
    int copiesCount;

    public Book(String isbn, String title, String description, String publisher, ArrayList<String> authors, ArrayList<UUID> categories, int copiesCount) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.authors = authors;
        this.categories = categories;
        this.copiesCount = copiesCount;
    }

    public Book(String isbn, String title, String description, String publisher, int copiesCount) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.copiesCount = copiesCount;
    }

    public Book() {
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<UUID> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<UUID> categories) {
        this.categories = categories;
    }

    public boolean addCategory(Category category) {
        if (categories.contains(category.id())) return false;
        return categories.add(category.id());
    }

    public int getCopiesCount() {
        return copiesCount;
    }

    public void setCopiesCount(int copiesCount) {
        this.copiesCount = copiesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Book other = (Book) obj;

        return this.isbn.trim().equalsIgnoreCase(other.isbn.trim()) &&
                this.title.trim().equalsIgnoreCase(other.title.trim()) &&
                this.description.trim().equalsIgnoreCase(other.description.trim()) &&
                this.publisher.trim().equalsIgnoreCase(other.publisher.trim()) &&
                this.copiesCount == other.copiesCount &&
                this.categories.equals(other.categories) &&
                this.authors.equals(other.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

}
