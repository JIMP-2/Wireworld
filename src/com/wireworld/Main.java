package com.wireworld;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage)  {
        WelcomeView welcomeView = new WelcomeView(stage);
        welcomeView.draw();
        Scene scene = new Scene(welcomeView);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
