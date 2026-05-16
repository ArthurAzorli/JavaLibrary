module br.edu.usp.javalibrary.javalibrary {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.edu.usp.javalibrary.javalibrary to javafx.fxml;
    exports br.edu.usp.javalibrary.javalibrary;
}