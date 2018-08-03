package tests;

import characters.character.CharacterController;
import environment.elements.fallingRubble.FallingRubbleController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FallingRubbleControllerTest {
    @Test
    public void testAttack() {
        CharacterController ch = new CharacterController(10, 10, 10, 0, 100, 0, 50);
        FallingRubbleController fr = new FallingRubbleController(0, 0, 1, 60);
        fr.increaseNumberOfSegments();
        fr.attack(ch);
        assertEquals(ch.getHp() < 100, true, "Error : FallingPlatformController : Update");
    }

    @Test
    public void testFall() {
        CharacterController ch = new CharacterController(50, 50, 10, 0, 100, 0, 100);
        FallingRubbleController fr = new FallingRubbleController(0, 0, 1, 120);
        fr.increaseNumberOfSegments();
        fr.update(ch);
        assertEquals(fr.isFalling(), true, "Error : FallingPlatformController : Fall");
    }

    @Test
    public void testUpdate() {
        CharacterController ch = new CharacterController(50, 50, 10, 0, 100, 0, 100);
        FallingRubbleController fr = new FallingRubbleController(0, 0, 1, 120);
        fr.increaseNumberOfSegments();
        for (int i = 0 ; i < 10 ; i++)
        fr.update(ch);
        assertEquals(ch.getHp() < 100, true, "Error : FallingPlatformController : Update");
    }

    @Test
    public void testIncreaseNumberOfSegments() {
        FallingRubbleController fp = new FallingRubbleController(100, 100, 100, 20);
        fp.increaseNumberOfSegments();
        assertEquals(fp.getNumberOfSegmentsX(), 1, "Error : FallingRubbleController : IncreaseNumberOfSegments");
    }
}
