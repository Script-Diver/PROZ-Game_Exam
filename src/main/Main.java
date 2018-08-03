package main;


import javafx.application.Application;
import javafx.stage.Stage;
import scenes.Scenes;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        Scenes.initScenes(primaryStage);
        primaryStage.setTitle("Exam");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

