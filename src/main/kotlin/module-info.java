module com.example.task_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires opencv;
    requires com.google.gson;

    opens com.example.task_2 to javafx.fxml, com.google.gson;
    exports com.example.task_2;
}