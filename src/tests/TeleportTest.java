package tests;

import characters.character.CharacterController;
import environment.elements.teleport.Teleport;
import environment.map.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeleportTest {
    @Test
    public void testTeleportationInsideSegment() {
        Map m = new Map();
        Teleport t = new Teleport(0, 0, 100, 100, 200, 200, false);
        CharacterController c = new CharacterController(20, 20, 0, 0, 100, 0, 0);
        t.work(c, m);

        assertEquals(m.getSegment(0).getTranslateX(), -200, "Teleport : testTeleportationInsideSegment : 1");
        assertEquals(c.getBonds().getTranslateY() + c.getBonds().getY(), 200, "Teleport : testTeleportationInsideSegment : 2");
    }

    @Test
    public void testTeleportationToAnotherSegment() {
        Map m = new Map();
        Teleport t = new Teleport(0, 0, 100, 100, 20000, 200, false);
        CharacterController c = new CharacterController(20, 20, 0, 0, 100, 0, 0);
        t.work(c, m);

        assertEquals(m.getSegment(0).getTranslateX(), -20000, "Teleport : testTeleportationToAnotherSegment : 1");
        assertEquals(c.getBonds().getTranslateY() + c.getBonds().getY(), 200, "Teleport : testTeleportationToAnotherSegment : 2");
    }
}
