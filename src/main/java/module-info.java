module br.edu.usp.javalibrary.javalibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jakarta.inject;


    opens br.edu.usp.javalibrary.javalibrary to javafx.fxml;
    exports br.edu.usp.javalibrary.javalibrary;
}