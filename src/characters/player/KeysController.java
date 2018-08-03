package characters.player;
import javafx.scene.Scene;

public class KeysController {
    public static void keysHandler(Scene scene, PlayerController player) {
        scene.setOnKeyPressed((event) -> {
            switch(event.getCode()){
                case W:
                    player.startJumping();
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
                case W:
                    player.stopJumping();
                    break;
                case A:
                    player.stopGoingLeft();
                    break;
                case D:
                    player.stopGoingRight();
                    break;
                default:
                    break;
            }
        });
    }
}