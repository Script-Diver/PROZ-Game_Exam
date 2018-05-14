package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static double screenW = 1300;
    public static double screenH = 1300;

    Timeline time;
    Map map;
    Player player;
    Student enemy;  // TODO

    @Override
    public void start(Stage primaryStage) throws Exception{
        map = new Map();
        player = new Player(80, 45, 10, 5, 50, Color.BLUE, 200, 0);
        enemy = new Student(80, 45, 10, 5, 50, Color.RED, 520, 0); // TODO

        Pane layout = new Pane();
        map.init(layout, 45);
        layout.getChildren().addAll(player, enemy, enemy.sight, map.segments[0]);

        Scene scene = new Scene(layout, 600, 600);
        keysHandler(scene);

        KeyFrame frame = new KeyFrame(Duration.millis(16), event ->{
            player.move(map.getSegment(map.whichSegment(player)));
            enemy.chase(map.getSegment(map.whichSegment(enemy)), player);
            Camera.record(player, map, enemy);
        });

        time = new Timeline();
        time.getKeyFrames(). add(frame);
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

        primaryStage.setTitle("Exam");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void keysHandler(Scene scene) {
        scene.setOnKeyPressed((event) -> {
            switch(event.getCode()){
                case W:
                    player.startJumping();
                    break;
                case S:
                    break;
                case A:
                    player.goLeft();
                    break;
                case D:
                    player.goRight();
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()){
                case A:
                    player.stopGoingLeft();
                    break;
                case D:
                    player.stopGoingRight();
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
