package environment.elements.fallingPlatform;

import characters.character.CharacterController;
import environment.map.Map;
import interfaces.EnvironmentElement;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import useful.Functions;

public class FallingPlatformController implements EnvironmentElement {
    public static double FallingPlatformMaxVy = 10;

    FallingPlatformModel model;
    FallingPlatformView view;

    private double currVY;
    private boolean isFalling;
    private boolean wantToFall;
    private int timeToFall;
    private int counter;
    private Point2D restartPoint;
    private int numberOfSegmentsX;

    public FallingPlatformController(double x, double y, double w, double h) {
        model = new FallingPlatformModel(FallingPlatformMaxVy);
        view = new FallingPlatformView(x, y, w, h);

        restartPoint = new javafx.geometry.Point2D(x, y);

        currVY = 0;
        isFalling = false;
        wantToFall = false;
        timeToFall = 70;
        counter = 0;
    }

    public void addToPane(Pane pane) {
        view.addToPane(pane);
    }

    private void count() {
        if (wantToFall && counter++ >= numberOfSegmentsX * timeToFall) {
            isFalling = true;
            currVY = model.getMaxVY();
        }
    }

    private Rectangle createPrayDetector() {
        Rectangle preyDetector = new Rectangle(view.getObjectWidth(), view.getObjectHeight());
        preyDetector.setX(view.getBonds().getX() + view.getBonds().getTranslateX());
        preyDetector.setY(view.getBonds().getY() + view.getBonds().getTranslateY() - view.getObjectHeight());
        return preyDetector;
    }

    private void fall() {
        if (isFalling) {
            view.pushByY(currVY / numberOfSegmentsX);
            if (view.getObjectTranslateY() > Map.SEGMENTH + 100) {
                isFalling = false;
                currVY = 0;
            }
        }
    }

    public Point2D getRestartPoint() {
        return restartPoint;
    }

    public boolean isWantToFall() {
        return wantToFall;
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

    public int getNumberOfSegmentsX() {
        return numberOfSegmentsX;
    }

    @Override

    public void pushByX(double x) {
        if (numberOfSegmentsX != 0)
            view.pushByX(x / numberOfSegmentsX);
    }

    @Override
    public void pushByY(double y) {
        if (numberOfSegmentsX != 0)
            view.pushByY(y);
    }

    private void waitForPrey(CharacterController target) {
        if (!isFalling && Functions.isDetectorColliding(createPrayDetector(), target.getBonds())) {
            wantToFall = true;
            currVY = model.getMaxVY();
        }
    }

    public void update(CharacterController target) {
        if (view.getObjectTranslateY() < Map.SEGMENTH) {
            waitForPrey(target);
            count();
            fall();
        }
    }

    public boolean isFalling() {
        return isFalling;
    }

    public int getTimeToFall() {
        return timeToFall;
    }

    public void reset() {
        model.reset();
        view.reset(restartPoint.getX(), restartPoint.getY());
        currVY = 0;
        isFalling = false;
        wantToFall = false;
        counter = 0;
    }

    public void setStroke(Color color, int w) {
        view.setStroke(color, w);
    }
}
