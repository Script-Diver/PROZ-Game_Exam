package tests;

import characters.player.PlayerController;
import environment.elements.fallingPlatform.FallingPlatformController;
import environment.elements.fallingRubble.FallingRubbleController;
import environment.elements.fallingRubble.FallingRubbleModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FallingPlatformControllerTest {
    @Test
    public void testWantToFall() {
        FallingPlatformController fp = new FallingPlatformController(100, 100, 100, 20);
        PlayerController pl = new PlayerController(300, 50, 10, 10, 1, 100, 0);
        fp.increaseNumberOfSegments();
        fp.update(pl);
        assertEquals(fp.isWantToFall(), true, "Error : FallingPlatformController : WantToFall");
    }

    @Test
    public void testFall() {
        FallingPlatformController fp = new FallingPlatformController(100, 100, 100, 20);
        PlayerController pl = new PlayerController(300, 50, 10, 10, 1, 100, 0);
        fp.increaseNumberOfSegments();

        double y = fp.getBonds().getTranslateY();

        for(int i = 0; i < 2 * fp.getTimeToFall(); i++)
            fp.update(pl);
        assertEquals(fp.isFalling(), true, "Error : FallingPlatformController : Fall");
        assertEquals(fp.getBonds().getTranslateY() > y, true, "Error : FallingPlatformController : Fall");
    }

    @Test
    public void testReset() {
        FallingPlatformController fp = new FallingPlatformController(100, 100, 100, 20);
        PlayerController pl = new PlayerController(100, 20, 10, 10, 1, 100, 0);
        for (int i = 0 ; i < 1000 ; i++)
            fp.update(pl);
        fp.reset();
        assertEquals(fp.isWantToFall(), false, "Error : FallingPlatformController : Update");
        assertEquals(fp.getBonds().getX(), fp.getRestartPoint().getX(), "Error : FallingPlatformController : Reset 1");
        assertEquals(fp.getBonds().getY(), fp.getRestartPoint().getY(), "Error : FallingPlatformController : Reset 2");
    }

    @Test
    void testIncreaseNumberOfSegments() {
        FallingPlatformController fp = new FallingPlatformController(100, 100, 100, 20);
        fp.increaseNumberOfSegments();
        assertEquals(fp.getNumberOfSegmentsX(), 1, "Error : FallingPlatformController : IncreaseNumberOfSegments");
    }
}
