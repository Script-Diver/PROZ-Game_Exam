package environment.elements.fallingPlatform;

import environment.elements.platform.PlatformView;
import javafx.scene.paint.Color;

public class FallingPlatformView extends PlatformView{
    FallingPlatformView(double x, double y, double w, double h){
        super(x, y, w, h);
        super.setColor(Color.FIREBRICK);
    }

    public void reset(double x, double y) {
        reset();
        setObjectX(x);
        setObjectY(y);
    }
}