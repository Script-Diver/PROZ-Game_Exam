package tests;

import environment.elements.platform.PlatformController;
import javafx.scene.layout.Pane;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlatformControllerTest {
    @Test
    public void testDefaultNumberOfSegments() {
        PlatformController p = new PlatformController(0, 0, 100, 20);
        assertEquals(p.getNumberOfSegments(), 0);
    }
    @Test
    public void testIncreaseNumberOfSegments() {
        PlatformController p = new PlatformController(0, 0, 100, 20);
        p.increaseNumberOfSegments();
        assertEquals(p.getNumberOfSegments(), 1);
    }
    @Test
    public void testPushByXWhileNumberOfSegmentsEqualsZero () {
        PlatformController p = new PlatformController(0, 0, 100, 20);
        p.pushByX(20);
        assertEquals(p.getBonds().getX() + p.getBonds().getTranslateX(), 0.0);
    }
    @Test
    public void testPushByXWhileNumberOfSegmentsNotEqualsZero () {
        PlatformController p = new PlatformController(0, 0, 100, 20);
        p.increaseNumberOfSegments();
        p.pushByX(20);
        assertEquals((p.getBonds().getX() + p.getBonds().getTranslateX()), 20.0);
    }
    @Test
    public void testReset() {
        PlatformController p =  new PlatformController(0, 0, 100, 20);
        p.increaseNumberOfSegments();
        p.pushByX(20);
        p.reset();
        assertEquals(p.getBonds().getX() + p.getBonds().getTranslateX(), 0);
    }
    @Test
    public void testAddToPane() {
        Pane pane = new Pane();
        PlatformController p =  new PlatformController(0, 0, 100, 20);
        p.addToPane(pane);
        assertEquals (pane.getChildren() != null, true);
    }
}