package br.edu.usp.javalibrary.javalibrary.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeController {

    @FXML
    private BorderPane homePanel;

    private void changeCenterContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            homePanel.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela: " + fxmlPath);
        }
    }

    @FXML
    private void handleMenuBooks(Event event) {
        changeCenterContent("/br/edu/usp/javalibrary/javalibrary/book.fxml");
    }

    @FXML
    private void handleMenuUsers(Event event) {
        changeCenterContent("/br/edu/usp/javalibrary/javalibrary/user.fxml");
    }

    @FXML
    private void handleMenuLoans(Event event) {
        changeCenterContent("/br/edu/usp/javalibrary/javalibrary/loan.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}