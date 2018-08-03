package camera;

import characters.character.CharacterController;
import characters.student.StudentController;
import environment.map.Map;

public class Camera {
    public static void record(CharacterController player, Map map) {
            map.pushEverything(-player.getCurrVX());
            player.correctMove(player.getCurrVX());
    }
}