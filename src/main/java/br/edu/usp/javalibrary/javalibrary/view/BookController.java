package br.edu.usp.javalibrary.javalibrary.view;

import br.edu.usp.javalibrary.javalibrary.service.SessionService;
import br.edu.usp.javalibrary.javalibrary.service.domains.Book;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BookController {
    
    SessionService session = SessionService.getInstance();

    @FXML
    private Label username;

    @FXML
    private Button loan;
    
    @FXML
    private Button addBook;
    
    @FXML
    private Button logout;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> isbn;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> description;

    @FXML
    private TableColumn<Book, String> publisher;

    @FXML
    private TableColumn<Book, String> authors;

    @FXML
    private TableColumn<Book, String> categories;

    @FXML
    private TableColumn<Book, Integer> copies;

    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        Platform.runLater(() -> {
            if (session.isLogged()) {
                username.setText(session.getUsername());
            } else {
                redirectToLogin();
            }
        });

        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        copies.setCellValueFactory(new PropertyValueFactory<>("copiesCount"));

        authors.setCellValueFactory(cellData -> {
            ArrayList<String> authors = cellData.getValue().getAuthors();
            String authorsText = (authors != null && !authors.isEmpty()) ? String.join(", ", authors) : "No authors";
            return new SimpleStringProperty(authorsText);
        });

        categories.setCellValueFactory(cellData -> {
            ArrayList<UUID> categoryList = cellData.getValue().getCategories();
            if (categoryList == null || categoryList.isEmpty()) {
                return new SimpleStringProperty("No categories");
            }
            
            String categoriesText = categoryList.stream().map(UUID::toString).collect(java.util.stream.Collectors.joining(", "));
                                                
            return new SimpleStringProperty(categoriesText);
        });

        booksTable.setItems(bookList);
        for (int i = 0; i < 20; i++) loadSampleData();
    }

    private void loadSampleData() {
        ArrayList<String> authors1 = new ArrayList<>();
        authors1.add("Deitel");
        authors1.add("Harvey");
        authors1.add("Harvey");
        authors1.add("Harvey");
        authors1.add("Harvey");
        authors1.add("Harvey");

        ArrayList<UUID> categories1 = new ArrayList<>();
        categories1.add(UUID.randomUUID());
        categories1.add(UUID.randomUUID());
        categories1.add(UUID.randomUUID());

        Book book1 = new Book("978-85", "Java: How to Program", "Comprehensive Java guide", "Pearson", authors1, categories1, 12);
        
        bookList.addAll(book1);
    }

    private void redirectToLogin() {
        try {
            final Stage stage = (Stage) username.getScene().getWindow();
            new LoginView(stage);
        } catch (IOException ignored) {}
    }

    @FXML
    private void handleButtonLoan(ActionEvent event) {
        // System.out.println("Loan\n");
    }

    @FXML
    private void handleButtonAddBook(ActionEvent event) {
        // System.out.println("AddBook\n");
    }

    @FXML
    private void handleButtonLogout(ActionEvent event) {

        session.logout();
        redirectToLogin();
    }

}