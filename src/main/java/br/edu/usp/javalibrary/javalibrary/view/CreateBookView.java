package br.edu.usp.javalibrary.javalibrary.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateBookView {
    public CreateBookView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/usp/javalibrary/javalibrary/book-create.fxml"));
        Scene scene = new Scene(loader.load());
        final Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Creating Book");
        stage.showAndWait();
    }
}
