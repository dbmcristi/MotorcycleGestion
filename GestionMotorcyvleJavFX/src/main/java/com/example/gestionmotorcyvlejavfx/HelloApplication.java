package com.example.gestionmotorcyvlejavfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    public HelloApplication() throws IOException {
    }

    @Override
    public void start(Stage stage) throws IOException {

      //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginForm.fxml"));

        URL fxmlLocation = getClass().getResource("D://FACULTATE_23-24//mpp23-24//gitClones//mpp-proiect-java-dbmcristi//GestionMotorcyvleJavFX//src//main//resources//com//example//gestionmotorcyvlejavfx//login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}