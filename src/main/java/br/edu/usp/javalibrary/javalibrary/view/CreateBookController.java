package br.edu.usp.javalibrary.javalibrary.view;

import br.edu.usp.javalibrary.javalibrary.service.domains.Book;
import br.edu.usp.javalibrary.javalibrary.service.repository.BookRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

public class CreateBookController {

    BookRepository repository = BookRepository.getInstance();

    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtPublisher;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCopies;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() {
        // Filtro regex para permitir APENAS dígitos numéricos nos campos específicos
        UnaryOperator<TextFormatter.Change> filterNumbersOnly = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null; // Ignora a alteração se não for número
        };

        // Aplica as restrições numéricas aos campos solicitados
        txtIsbn.setTextFormatter(new TextFormatter<>(filterNumbersOnly));
        txtCopies.setTextFormatter(new TextFormatter<>(filterNumbersOnly));
    }

    @FXML
    private void handleSave() {
        if (isInputInvalid()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha todos os campos.");
            return;
        }

        final String isbn = txtIsbn.getText();
        final String title = txtTitle.getText();
        final String description = txtDescription.getText();
        final String publisher = txtPublisher.getText();
        final String author = txtAuthor.getText();
        final String category = txtCategory.getText();
        int copiesCount = txtCopies.getText().isEmpty() ? 0 : Integer.parseInt(txtCopies.getText());

        Book newBook = new Book(isbn, title, description, publisher, author, category, copiesCount);
        if (repository.saveBook(newBook)) {
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Livro cadastrado com sucesso!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Houve um erro ao salvar.");
        }

    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private boolean isInputInvalid() {
        return txtIsbn.getText().isBlank() ||
                txtTitle.getText().isBlank() ||
                txtDescription.getText().isBlank() ||
                txtPublisher.getText().isBlank() ||
                txtAuthor.getText().isBlank() ||
                txtCategory.getText().isBlank() ||
                txtCopies.getText().isBlank();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
