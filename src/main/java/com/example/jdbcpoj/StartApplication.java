package com.example.jdbcpoj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 250);
        stage.setTitle("Туристический комплекс");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}// TODO: 11.11.2023 tt