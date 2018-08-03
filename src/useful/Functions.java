package useful;

import javafx.scene.shape.Rectangle;

public class Functions {
    public static boolean isDetectorColliding(Rectangle detector, Rectangle box) {
        return detector.getBoundsInParent().intersects(box.getBoundsInParent());
    }
}