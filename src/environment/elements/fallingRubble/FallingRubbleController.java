package environment.elements.fallingRubble;

import characters.character.CharacterController;
import javafx.scene.paint.Color;
import useful.Functions;
import interfaces.EnvironmentElement;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import events.HitEvent;


public class FallingRubbleController implements interfaces.Aggressive, EnvironmentElement {
    public static int FallingRubbleDmg = 10;
    public static double FallingRubbleMaxVy = 10;
    private Point2D restartPoint;
    private double diffPreyDetectorY;


    FallingRubbleModel model;
    FallingRubbleView view;

    private boolean isFalling;
    private boolean isDestroyed;

    private double currVY;
    private int numberOfSegmentsX;

    public FallingRubbleController(double x, double y, int reload, double preyDetectory) {
        model = new FallingRubbleModel(reload, FallingRubbleDmg, FallingRubbleMaxVy);
        view = new FallingRubbleView(x, y);

        restartPoint = new Point2D(x, y);
        diffPreyDetectorY = preyDetectory - restartPoint.getY();

        isDestroyed = false;
        isFalling = false;

        currVY = 0;
    }

    public void addToPane(Pane pane) {
        view.addToPane(pane);
    }

    public void attack(CharacterController target) {
        Event.fireEvent(target, new HitEvent(this, target, model.getDamage() / numberOfSegmentsX));
    }

    private Rectangle createPreyDetector() {
        Rectangle preyDetector = new Rectangle(view.getObjectWidth(), FallingRubbleView.fallingRubbleHeight);
        preyDetector.setTranslateY( restartPoint.getY() + diffPreyDetectorY);
        preyDetector.setTranslateX(view.getBonds().getTranslateX() + view.getBonds().getX());
        return preyDetector;
    }

    public int getReloadTime() {
        return model.getReloadTime();
    }

    public int getNumberOfSegmentsX() {
        return numberOfSegmentsX;
    }

    private void fall(CharacterController target) {
        if (isFalling) {
            double corr;
            pushByY(currVY);
            if (!isDestroyed && Functions.isDetectorColliding(view.getBonds(), target.getBonds())) {
                attack(target);
                isDestroyed = true;
            }
            if((corr = createPreyDetector().getTranslateY() + createPreyDetector().getHeight() - view.getObjectTranslateY() - view.getObjectHeight()) < currVY) {
                pushByY(-corr);
                isFalling = false;
                isDestroyed = true;
            }
        }
    }

    public void increaseNumberOfSegments() {
        numberOfSegmentsX++;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public Rectangle getBonds() {
        return view.getBonds();
    }

    @Override
    public void pushByX(double x) {
        if(numberOfSegmentsX != 0) {
            view.pushByX(x / numberOfSegmentsX);
        }
    }

    @Override
    public void pushByY(double y) {
        view.pushByY(y / numberOfSegmentsX);
    }

    private void reload() {
        if(isDestroyed) {
            if(model.decrementCharge() < 0) {
                isFalling = false;
                isDestroyed = false;
                view.setObjectTranslateY(0);
                model.restartCharge();
            }
        }
    }

    public void update(CharacterController target) {
        waitForPrey(target);
        fall(target);
        reload();
    }

    private void waitForPrey(CharacterController target) {
        if (!isDestroyed && !isFalling && Functions.isDetectorColliding(createPreyDetector(), target.getBonds())) {
            isFalling = true;
            currVY = model.getMaxVY();
        }
    }

    public void setStroke(Color color, int w) { view.setStroke(color, w);}

    public void reset() {
        view.reset(restartPoint.getY());
        model.reset();
        isFalling = false;
        isDestroyed = false;
        currVY = 0;
    }
}
