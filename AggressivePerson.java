package sample;

import javafx.scene.shape.Rectangle;

public interface AggressivePerson {
    void attack(Character character);
    boolean isAttackPrepared();
    void chase(Segment segment, Rectangle target);
}
