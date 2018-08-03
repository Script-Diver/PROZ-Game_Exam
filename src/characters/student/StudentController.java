package characters.student;

import characters.character.CharacterController;
import characters.enemy.EnemyController;
import environment.map.Segment;
import events.HitEvent;
import interfaces.Aggressive;
import javafx.event.Event;
import javafx.scene.paint.Color;

import java.util.Random;

public class StudentController extends EnemyController implements Aggressive {
    public static int StudentMaxHp = 1000;
    public static Color StudentColor = Color.NAVY;
    public static double StMaxVX = 5;
    public static double StMaxVY = 10;
    private int timeToChangeDirection;

    int charge; // czas potrzebny na przygotowanie ataku
    int reloadTime;
    int damage;

    public StudentController(double x, double y, double w, double h, double maxvX, double maxvY) {
        super(h, w, maxvY, maxvX, StudentMaxHp, x, y);
        view.setColor(StudentColor);
        charge = 100;
        reloadTime = 0;
        damage = 10;
        Random generator = new Random();
        timeToChangeDirection = generator.nextInt() % 50 + 150;
    }

    // realizuje strategie dzialania studenta
    public void chase(Segment segment, CharacterController target) {
        search(target, 5 );
        reload();
        if (!isTargetSpotted())
            patrol(timeToChangeDirection, true);
        else if (isTargetReached())
            attack(target);

        move(segment);
    }

    private void reload() {
        if (reloadTime > 0) {
            if (isTargetSpotted()) reloadTime--;
            else {
                reloadTime -= 2;
                if(reloadTime < 0) reloadTime = 0;
            }
        }
    }

    @Override
    public void attack(CharacterController character) {
        if(reloadTime == 0) {
            HitEvent event = new HitEvent(this, character, damage);
            Event.fireEvent(character, event);
            reloadTime = charge;
        }
    }

    @Override
    public void reset() {
        super.reset();
        reloadTime = 0;
    }
}
