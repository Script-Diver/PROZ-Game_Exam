package tests;

import characters.character.CharacterController;
import environment.elements.platform.PlatformController;
import environment.map.Segment;
import events.HitEvent;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterControllerTest {

    @Test
    public void TestAddToPane() {
        Pane pane = new Pane();
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        c.addToPane(pane);
        assertEquals(pane.getChildren() != null, true, "Invalid addToPane()");
    }

    @Test
    public void testGoingLeftWithoutDisturber() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 80, 100, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 10, 0);
        c.goLeft();
        c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX(), 10 + c.getCurrVX(), "Error while going left without disturber");
    }

    @Test
    public void testGoingRightWithoutDisturber() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(50, 80, 100, 20));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        c.goRight();
        c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX(), c.getCurrVX(), "Error while going right with disturber");
    }

    @Test
    public void testGoingLeftWithDisturber() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 80, 100, 10));
        segment.addPlatform(new PlatformController(0, 0, 1, 80));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 10, 0);
        c.goLeft();
        for (int i = 0 ; i < 5 ; i++)
            c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX(), 2, "Error while going left with disturber");
    }

    @Test
    public void testGoingRightWithDisturber() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 80, 100, 10));
        segment.addPlatform(new PlatformController(55, 0, 1, 80));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        c.goRight();
        for (int i = 0 ; i < 5 ; i++)
            c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX(), 9, "Error while going right with disturber");
    }

    @Test
    public void testJumping() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 200, 100, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 120);
        c.startJumping();
        c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX() < 120, true , "Error while jumping");
    }

    @Test
    public void testFalling() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 400, 100, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        for (int i = 0 ; i < 5 ; i++)
            c.move(segment);
        assertEquals(c.getBonds().getY() + c.getBonds().getTranslateY() > 0, true , "Error while falling");
    }

    @Test
    public void testFallingWithDisturber() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 200, 100, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        for (int i = 0 ; i < 25 ; i++)
            c.move(segment);
        assertEquals(c.getBonds().getY() + c.getBonds().getTranslateY(), 120 , "Error while falling with disturber");
    }

    @Test
    public void stop() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 80, 100, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        c.goRight();
        c.stop();
        c.move(segment);
        assertEquals(c.getBonds().getX()+c.getBonds().getTranslateX(), 0);
    }

    @Test
    public void takeHit() {
        double maxHp;
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        maxHp = c.getHp();
        HitEvent hitevent = new HitEvent(c, c, 10);
        c.fireEvent(hitevent);
        assertEquals(c.getHp(), maxHp - 10, "Error while takeing hit");
    }

    @Test
    public void testReset() {
        Segment segment = new Segment(0);
        segment.addPlatform(new PlatformController(0, 80, 10, 10));
        CharacterController c = new CharacterController(80, 45, 10, 5, 100, 0, 0);
        c.goRight();
        for(int i = 0 ; i < 5 ; i++)
            c.move(segment);
        c.stop();
        c.reset();
        assertEquals(c.getBonds().getX(), 0);
        assertEquals(c.getBonds().getY(), 0);
        assertEquals(c.getBonds().getTranslateX(), 0);
        assertEquals(c.getBonds().getTranslateY(), 0);
    }
}