package br.edu.usp.javalibrary.javalibrary.view.user;

import br.edu.usp.javalibrary.javalibrary.service.domains.User;
import br.edu.usp.javalibrary.javalibrary.service.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class CreateUserController {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final UserRepository repository = UserRepository.getInstance();
    private User user;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txtAddress;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    public void setUser(User user) {
        this.user = user;
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmailAddress());
        txtAddress.setText(user.getAddress());
    }

    @FXML
    public void initialize() {
        if (user == null) user = new User();
    }

    @FXML
    private void handleSave() {
        if (isInputInvalid()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha todos os campos.");
            return;
        }

        if (!isEmailValid()){
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, insira um e-mail válido");
            return;
        }

        user.setName(txtName.getText().trim());
        user.setEmailAddress(txtEmail.getText().trim());
        user.setAddress(txtAddress.getText().trim());

        if (repository.saveUser(user)) {
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário salvo com sucesso!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Houve um erro ao salvar os dados.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private boolean isInputInvalid() {
        return txtName.getText().isBlank() || txtEmail.getText().isBlank() || txtAddress.getText().isBlank();
    }

    private boolean isEmailValid() {
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(txtEmail.getText()).matches();
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