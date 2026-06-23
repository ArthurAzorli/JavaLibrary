package br.edu.usp.javalibrary.javalibrary.view.book;

import br.edu.usp.javalibrary.javalibrary.service.domains.Book;
import br.edu.usp.javalibrary.javalibrary.service.repository.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class BookController {

    final private BookRepository bookRepository = BookRepository.getInstance();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private TextField search;

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
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> category;

    @FXML
    private TableColumn<Book, Integer> copies;

    @FXML
    public void initialize() {

        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        copies.setCellValueFactory(new PropertyValueFactory<>("copiesCount"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        updateList();
        booksTable.setItems(bookList);
    }

    private void updateList() {
        bookList.clear();
        bookList.addAll(bookRepository.getBooks());
    }


    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    @FXML
    private void handleButtonAddBook(ActionEvent event) {
        try {
            new CreateBookView();
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Não foi possível carregar a tela");
        }
    }

    @FXML
    private void handleButtonUpdateBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum livro selecionado", null, "Por favor, selecione um livro na tabela para remover.");
            return;
        }

        try {
            new CreateBookView(selectedBook);
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Não foi possível carregar a tela");
        }
    }

    @FXML
    private void handleButtonRemoveBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum livro selecionado", null, "Por favor, selecione um livro na tabela para remover.");
            return;
        }

        Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmar Exclusão", "Você está prestes a remover um livro.", "Tem certeza que deseja remover o livro: \"" + selectedBook.getTitle() + "\" - " + selectedBook.getIsbn() + " ?");
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        if (bookRepository.removeBook(selectedBook.getIsbn())) {
            bookList.remove(selectedBook);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", null, "Livro removido com sucesso!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Houve um erro ao remover o livro!");
        }

    }

    @FXML
    private void handleButtonSearch(ActionEvent event) {
        String query = search.getText();

        if (query == null || query.isBlank()) {
            updateList();
            return;
        }

        String lowerCaseFilter = query.toLowerCase().trim();

        List<Book> filteredBooks = bookRepository.getBooks()
                .stream()
                .filter(book -> {
                    boolean matchesIsbn = book.getIsbn() != null && book.getIsbn().toLowerCase().contains(lowerCaseFilter);
                    boolean matchesTitle = book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerCaseFilter);
                    boolean matchesCategory = book.getCategory() != null && book.getCategory().toLowerCase().contains(lowerCaseFilter);
                    boolean matchesAuthor = book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerCaseFilter);
                    return matchesIsbn || matchesTitle || matchesCategory || matchesAuthor;
                })
                .toList();

        bookList.clear();
        bookList.addAll(filteredBooks);
    }

    @FXML
    private void handleButtonClear(ActionEvent event) {
        search.clear();
        updateList();
    }


}