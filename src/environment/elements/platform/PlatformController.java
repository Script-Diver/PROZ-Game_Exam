package environment.elements.platform;

import interfaces.EnvironmentElement;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlatformController implements EnvironmentElement{
    private PlatformView view;
    private PlatformModel model;
    private int numberOfSegmentsX;

    public PlatformController(double x, double y, double w, double h) {
        view = new PlatformView(x, y, w, h);
        model = new PlatformModel();
    }

    public void addToPane(Pane pane) {
        view.addToPane(pane);
    }

    public void increaseNumberOfSegments() {
        numberOfSegmentsX++;
    }

    public Rectangle getBonds() {
        return view.getBonds();
    }

    public double getObjectTranslateY() {
        return view.getObjectTranslateY();
    }

    public int getNumberOfSegments() {
        return numberOfSegmentsX;
    }

    @Override
    public void pushByX(double x) {
        if(numberOfSegmentsX != 0)
            view.pushByX(x/numberOfSegmentsX);
    }

    @Override
    public void pushByY(double y) {
        if(numberOfSegmentsX != 0) // tu dodano
            view.pushByY(y);
    }

    public void reset() {
        view.reset();
        model.reset();
    }

    public void setColor(Color color) {
        view.setColor(color);
    }

    public void setStroke(Color color, int w) { view.setStroke(color, w); }
}