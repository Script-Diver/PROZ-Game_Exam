package characters.enemy;

import characters.character.CharacterController;
import useful.Functions;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class EnemyController extends CharacterController{
    public static double SIGHT_W = 150;
    private int counter;            // licznik wykorzystywany do odmierzania dystansu przy patrolowaniu
    private boolean targetSpotted;
    private boolean targetReached;
    private boolean isTurnedRight;

    public EnemyController(double h, double w, double maxVy, double maxVx, int health, double x, double y) {
        super(h, w, maxVy, maxVx, health, x, y);
        counter = 0;
        targetSpotted = false;
        targetReached = false;
        isTurnedRight = false;
    }

    @Override
    public void addToPane(Pane pane) {
        super.addToPane(pane);
    }

    private void changeDirection() {
        if(isTurnedRight) {
            isTurnedRight = false;
        }
        else {
            isTurnedRight = true;
        }
    }

    private void coursing(double targetPoint, double enemyPoint, double distance) {
        if (Math.abs(targetPoint - enemyPoint) > distance) {
            if (isTurnedRight)  goRight();
            else    goLeft();
        } else {
            stop();
            targetReached = true;
        }
    }

    private Rectangle createSight() {
        Rectangle sight = new Rectangle(SIGHT_W, view.getBonds().getHeight() - 1);
        sight.setY(view.getBonds().getY() + view.getBonds().getTranslateY());
        if(isTurnedRight) {
            sight.setX(view.getBonds().getX() + view.getBonds().getTranslateX() + view.getBonds().getWidth());
        }
        else {
            sight.setX(view.getBonds().getX() + view.getBonds().getTranslateX() - SIGHT_W);
        }
        return sight;
    }

    public boolean isTargetSpotted() {
        return targetSpotted;
    }

    public boolean isTargetReached() {
        return targetReached;
    }

    private void keepDistance(CharacterController target) {
        targetReached = true;
        if(isTurnedRight) goLeft();
        else goRight();
    }

    public void search(CharacterController target, double distance) {
        double targetPoint, enemyPoint;
        targetSpotted = true;
        targetReached = false;

        if(Functions.isDetectorColliding(view.getBonds(), target.getBonds())) {
            keepDistance(target);
            targetReached = true;
        }

        else if(Functions.isDetectorColliding(createSight(), target.getBonds())) {
            if (target.getBonds().getTranslateX() + target.getBonds().getX() < view.getBonds().getTranslateX() + view.getBonds().getX()) {
                targetPoint = target.getBonds().getTranslateX() + target.getBonds().getX() + target.getBonds().getWidth();
                enemyPoint = view.getBonds().getTranslateX() + view.getBonds().getX();
            } else {
                targetPoint = target.getBonds().getTranslateX() + target.getBonds().getX();
                enemyPoint = createSight().getTranslateX() + createSight().getX();
            }
            coursing(targetPoint, enemyPoint, distance);
        }

        else{
            targetSpotted = false;
        }
    }

    protected void patrol(int timeToChangeDirection, boolean withWalking) {
        if(counter++ >= timeToChangeDirection) {
            counter = 0;
            changeDirection();
        }

        if(withWalking) {
            if (isTurnedRight)
                goRight();
            else
                goLeft();
        }
    }

    @Override
    public void pushByY(double y) {
        super.pushByY(y);
    }

    @Override
    public void pushByX(double x) {
        super.pushByX(x);
    }

    @Override
    public void reset() {
        super.reset();
        counter = 0;
        targetSpotted = false;
        targetReached = false;
        isTurnedRight = false;
    }
}
