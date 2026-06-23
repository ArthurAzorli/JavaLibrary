package br.edu.usp.javalibrary.javalibrary.view.loan;

import br.edu.usp.javalibrary.javalibrary.service.domains.Loan;
import br.edu.usp.javalibrary.javalibrary.service.repository.LoanRepository; // Supondo a existência dele
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LoanController {

    private final LoanRepository loanRepository = LoanRepository.getInstance();
    private final ObservableList<Loan> loanList = FXCollections.observableArrayList();

    @FXML private TextField search;
    @FXML private TableView<Loan> loansTable;
    @FXML private TableColumn<Loan, String> colId;
    @FXML private TableColumn<Loan, String> colIsbn;
    @FXML private TableColumn<Loan, String> colUserId;
    @FXML private TableColumn<Loan, LocalDateTime> colStart;
    @FXML private TableColumn<Loan, LocalDateTime> colEndPrevision;
    @FXML private TableColumn<Loan, LocalDateTime> colEnd;
    @FXML private TableColumn<Loan, String> colStatus;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEndPrevision.setCellValueFactory(new PropertyValueFactory<>("endPrevision"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        updateList();
        loansTable.setItems(loanList);
    }

    private void updateList() {
        loanList.clear();
        loanList.addAll(loanRepository.getLoans());
    }

    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    @FXML
    private void handleButtonAddLoan(ActionEvent event) {
        try {
            new CreateLoanView();
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela.");
        }
    }

    @FXML
    private void handleButtonUpdateLoan(ActionEvent event) {
        Loan selectedLoan = loansTable.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum empréstimo selecionado", "Selecione um registro para editar/devolver.");
            return;
        }

        try {
            new CreateLoanView(selectedLoan);
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela.");
        }
    }

    @FXML
    private void handleButtonSearch(ActionEvent event) {
        String query = search.getText();
        if (query == null || query.isBlank()) {
            updateList();
            return;
        }

        String filter = query.toLowerCase().trim();
        List<Loan> filtered = loanRepository.getLoans().stream()
                .filter(l -> l.getBookISBN().toLowerCase().contains(filter) ||
                        l.getUserID().toString().toLowerCase().contains(filter))
                .toList();

        loanList.clear();
        loanList.addAll(filtered);
    }

    @FXML
    private void handleButtonClear(ActionEvent event) {
        search.clear();
        updateList();
    }
}