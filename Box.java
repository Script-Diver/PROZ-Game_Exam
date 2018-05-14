package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Box extends Rectangle {
    int numberOfSegments;

    public Box(double x, double y, double w, double h, Color color){
        super(w, h);
        setTranslateY(y);
        setTranslateX(x);
        setFill(color);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Box
                && ((Box) o).getHeight() == getHeight()
                && ((Box) o).getWidth() == getWidth()
                && ((Box) o).getTranslateX() == getTranslateX()
                && ((Box) o).getTranslateY() == getTranslateY();
    }

    public void pushByX(double x) {
        if(numberOfSegments != 0)
            setTranslateX(getTranslateX() + x/numberOfSegments);
    }

    public void increaseNumberOfSegments() {
        numberOfSegments++;
    }

    public int getNumberOfSegments() {
        return numberOfSegments;
    }
}
