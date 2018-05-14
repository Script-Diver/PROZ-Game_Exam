package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Student extends Enemy implements AggressivePerson {
    int charge; // czas potrzebny na przygotowanie ataku
    int timeToNextAttack;
    double strength;

    public Student(double h, double w, double maxvY, double maxvX, int health, Color color, double x, double y) {
        super(h, w, maxvY, maxvX, health, color, x, y);
        charge = 100;
        timeToNextAttack = 0;
    }

    @Override
    public void attack(Character character) {
        if(isAttackPrepared()) {
            character.takeHit(strength);
            System.out.println("AUC!");     // TODO - otrzymywanie
        }
    }

    @Override
    public boolean isAttackPrepared() {
        if(timeToNextAttack-- <= 0){
            timeToNextAttack = charge;
            return true;
        }
        return false;
    }

    @Override
    public void chase(Segment segment, Rectangle target) {
        search(target, 10);
        if (!isTargetSpotted()) {
            patrol(50, true);
            timeToNextAttack = 0;
        }
        else if (isTargetReached() && target instanceof Character)
            attack((Character) target);

        super.move(segment);
    }
}
