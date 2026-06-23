package br.edu.usp.javalibrary.javalibrary.view.loan;

import br.edu.usp.javalibrary.javalibrary.service.domains.Loan;
import br.edu.usp.javalibrary.javalibrary.service.domains.User;
import br.edu.usp.javalibrary.javalibrary.service.repository.BookRepository;
import br.edu.usp.javalibrary.javalibrary.service.repository.LoanRepository;
import br.edu.usp.javalibrary.javalibrary.service.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class CreateLoanController {

    private final LoanRepository loanRepository = LoanRepository.getInstance();
    private final BookRepository bookRepository = BookRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    private Loan loan;
    private boolean isEditMode = false;

    @FXML private ComboBox<String> cbBook;
    @FXML private ComboBox<String> cbUser;
    @FXML private DatePicker dpEndPrevision;
    @FXML private Button btnReturnBook;
    @FXML private Button btnCancel;

    public void setLoan(Loan loan) {
        this.loan = loan;
        this.isEditMode = true;

        final Optional<User> user = userRepository.getUser(loan.getId());

        cbBook.setValue(loan.getBookISBN());
        user.ifPresent(value -> cbUser.setValue(value.getEmailAddress()));
        dpEndPrevision.setValue(loan.getEndPrevision().toLocalDate());

        cbBook.setDisable(true);
        cbUser.setDisable(true);


        if (!loan.isFinished()) {
            btnReturnBook.setVisible(true);
        }
    }

    @FXML
    public void initialize() {
        bookRepository.getBooks().forEach(book -> cbBook.getItems().add(book.getIsbn()));
        userRepository.getUsers().forEach(user -> cbUser.getItems().add(user.getEmailAddress()));

        if (loan == null) {
            this.loan = new Loan(UUID.randomUUID(), null, null, LocalDateTime.now(), null);
        }
    }

    @FXML
    private void handleSave() {
        if (cbBook.getValue() == null || cbUser.getValue() == null || dpEndPrevision.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Preencha todos os campos obrigatórios.");
            return;
        }
        final Optional<User> user = userRepository.getUser(cbUser.getValue());
        if (user.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Não foi possivel encontrar o usuário");
            return;
        }

        loan.setBookISBN(cbBook.getValue());
        loan.setUserID(user.get().getId());
        loan.setEndPrevision(dpEndPrevision.getValue().atStartOfDay());

        if (loanRepository.saveLoan(loan)) {
            bookRepository.loanBook(loan.getBookISBN());
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Empréstimo salvo com sucesso!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível salvar o empréstimo.");
        }
    }

    @FXML
    private void handleMarkAsReturned() {
        loan.setEnd(LocalDateTime.now());
        if (loanRepository.saveLoan(loan)) {
            bookRepository.returnBook(loan.getBookISBN());
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Livro marcado como Entregue!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível registrar a entrega.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
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