package org.example.gestorempleadosioritzperugorria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class  Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("pantallaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);

        stage.setTitle("Gestor Empleados");

        stage.setMinWidth(500);
        stage.setMinHeight(500);
        Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/icon.png")));
        stage.getIcons().add(imagen);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}