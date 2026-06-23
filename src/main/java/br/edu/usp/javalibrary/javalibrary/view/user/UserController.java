package br.edu.usp.javalibrary.javalibrary.view.user;


import br.edu.usp.javalibrary.javalibrary.service.domains.User;
import br.edu.usp.javalibrary.javalibrary.service.repository.UserRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserController {

    private final UserRepository userRepository = UserRepository.getInstance();
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private TextField search;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, UUID> id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> address;



    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        updateList();
        usersTable.setItems(userList);
    }

    private void updateList() {
        userList.clear();
        userList.addAll(userRepository.getUsers());
    }

    @FXML
    private void handleButtonAddUser(ActionEvent event) {
        try {
            new CreateUserView();
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Não foi possível carregar a tela");
        }
    }

    @FXML
    private void handleButtonUpdateUser(ActionEvent event) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum usuário selecionado", null, "Por favor, selecione um usuário na tabela para editar.");
            return;
        }
        try {
            new CreateUserView(selectedUser);
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Não foi possível carregar a tela");
        }
    }

    @FXML
    private void handleButtonRemoveUser(ActionEvent event) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum usuário selecionado", null, "Por favor, selecione um usuário na tabela para remover.");
            return;
        }

        Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmar Exclusão", "Você está prestes a remover um usuário.", "Tem certeza que deseja remover: \"" + selectedUser.getName() + "\"?");
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        if (userRepository.removeUser(selectedUser.getId())) {
            userList.remove(selectedUser);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", null, "Usuário removido com sucesso!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", null, "Houve um erro ao remover o usuário!");
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
        List<User> filteredUsers = userRepository.getUsers().stream()
                .filter(user -> {
                    boolean matchesName = user.getName() != null && user.getName().toLowerCase().contains(lowerCaseFilter);
                    boolean matchesEmail = user.getEmailAddress() != null && user.getEmailAddress().toLowerCase().contains(lowerCaseFilter);
                    return matchesName || matchesEmail;
                }).toList();

        userList.clear();
        userList.addAll(filteredUsers);
    }

    @FXML
    private void handleButtonClear(ActionEvent event) {
        search.clear();
        updateList();
    }

    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}