package scenes;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import useful.Clock;
import camera.Camera;
import characters.player.KeysController;
import characters.player.PlayerController;
import environment.map.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Scenes {
    public static double SCREEN_H = 600;
    public static double SCREEN_W = 1000;

    public static Scene startScene;
    public static Scene gameScene;
    public static Scene endScene;
    public static Scene winScene;

    static Map map;
    static PlayerController player;
    static Clock counter;

    public static void initScenes(Stage primaryStage) {
        initStartScene(primaryStage);
        initGameScene(primaryStage);
        initEndScene(primaryStage);
        initWinScene(primaryStage);
        primaryStage.setScene(startScene);
    }
    private static void initGameScene(Stage primaryStage) {
        Pane layout = new Pane();

        map = new Map();
        player = new PlayerController(80, 45, 12, 5, 300, SCREEN_W/2 - 40, 360);
        counter = new Clock(player.getHp(), 40);

        map.init(layout, 45);
        player.addToPane(layout);
        counter.addToPane(layout);

        gameScene = new Scene(layout, SCREEN_W, SCREEN_H);
        KeysController.keysHandler(gameScene, player);

        Timeline timeline = new Timeline (
                new KeyFrame(Duration.millis(16), event -> {
                    if (primaryStage.getScene() == gameScene) {
                        boolean result = map.update(player);
                        counter.setTime(player.getHp());
                        player.update(map.getSegment(map.whichSegment(player)));
                        Camera.record(player, map);
                        if(player.getHp() <= 0) {
                            primaryStage.setScene(endScene);
                        }
                        if(result) {
                            System.out.println(result);
                            primaryStage.setScene(winScene);    //TODO - ekran zwyciestwa
                        }
                    }
                })
        );
        if(primaryStage.getScene() != gameScene)
            timeline.stop();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private static void initStartScene(Stage primaryStage) {
        StackPane layout = new StackPane();

        Button startButton = new Button("Start");
        startButton.setMinSize(200, 100);
        startButton.setMaxSize(200, 100);
        startButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                event.consume();
                primaryStage.setScene(gameScene);
            }
        });

        Label label = new Label("Naciśnij przycisk aby zacząć grę!");
        label.setFont(Font.font("font1", FontWeight.BOLD, 60));
        label.setTextFill(Color.RED);
        label.setTranslateY(100);

        layout.getChildren().addAll(startButton, label);
        layout.setAlignment(label, Pos.TOP_CENTER);
        layout.setAlignment(startButton, Pos.CENTER);

        startScene = new Scene(layout, SCREEN_W, SCREEN_H);
    }
    private static void initEndScene(Stage primaryStage) {
        StackPane layout = new StackPane();
        Button startButton = new Button("Retry");
        startButton.setMinSize(200, 100);
        startButton.setMaxSize(200, 100);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();
                resetGame();
                primaryStage.setScene(gameScene);
            }
        });

        Label label = new Label("Uwaliłeś!");
        label.setFont(Font.font("font1", FontWeight.BOLD, 100));
        label.setTextFill(Color.RED);
        label.setTranslateY(100);

        layout.getChildren().addAll(startButton, label);
        layout.setAlignment(label, Pos.TOP_CENTER);
        layout.setAlignment(startButton, Pos.CENTER);

        endScene = new Scene(layout, SCREEN_W, SCREEN_H);
    }
    private static void initWinScene(Stage primaryStage) {
        StackPane layout = new StackPane();
        Button startButton = new Button("Play again");
        startButton.setMinSize(200, 100);
        startButton.setMaxSize(200, 100);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();
                resetGame();
                primaryStage.setScene(gameScene);
            }
        });

        Label label = new Label("Gratulacje! Ale i tak uwaliłeś...");
        label.setFont(new Font("font1", 60));
        label.setTextFill(Color.RED);
        label.setTranslateY(100);
        layout.getChildren().addAll(label, startButton);
        layout.setAlignment(label, Pos.TOP_CENTER);
        layout.setAlignment(startButton, Pos.CENTER);

        winScene = new Scene(layout, SCREEN_W, SCREEN_H);
    }
    static private void resetGame() {
        map.reset();
        player.reset();
    }
}

