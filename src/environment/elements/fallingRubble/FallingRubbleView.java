package environment.elements.fallingRubble;

import environment.elements.platform.PlatformView;
import javafx.scene.paint.Color;

public class FallingRubbleView extends PlatformView{
    public static double fallingRubbleWidth = 40;
    public static double fallingRubbleHeight = 20;
    public static Color fallingRubbleColor = Color.GRAY;

    FallingRubbleView(double x, double y) {
        super(x, y, fallingRubbleWidth, fallingRubbleHeight);
        setColor(fallingRubbleColor);
    }

    @Override
    public void setObjectTranslateY(double y) {
        super.setObjectTranslateY(y);
    }

    public void reset(double previousY) {
        super.reset();
        setObjectTranslateY(-previousY);
    }
}
