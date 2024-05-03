module org.example.gestorempleadosioritzperugorria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.gestorempleadosioritzperugorria to javafx.fxml;
    exports org.example.gestorempleadosioritzperugorria;
}