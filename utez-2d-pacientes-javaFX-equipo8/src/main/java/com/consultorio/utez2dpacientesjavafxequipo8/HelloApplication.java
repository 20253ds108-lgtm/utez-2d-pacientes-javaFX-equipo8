package com.consultorio.utez2dpacientesjavafxequipo8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/consultorio/utez2dpacientesjavafxequipo8/views/pantalla.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Consultorio Utez");
        stage.setScene(scene);
        stage.show();
    }
}
