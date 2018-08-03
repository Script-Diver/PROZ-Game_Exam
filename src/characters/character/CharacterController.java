package characters.character;

import environment.map.Map;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import events.HitEvent;
import environment.map.Segment;

public class CharacterController extends Rectangle{
    protected CharacterView view;
    protected CharacterModel model;

    private int currSegment;
    protected double currVY;
    protected double currVX;

    protected Point2D restartPoint;

    public boolean isGoingLeft;
    protected boolean isGoingRight;
    protected boolean isJumping;
    protected boolean isFalling;

    public CharacterController(double h, double w, double maxVy, double maxVx, int health, double x, double y) {
        super();
        if (health > 10000)
            health = 10000;
        view = new CharacterView(x, y, w, h, Color.BLUEVIOLET);
        model = new CharacterModel(maxVy, maxVx, health);

        currVY = 0;
        currVX = 0;
        isFalling = false;
        isGoingLeft = false;
        isGoingRight = false;
        isJumping = false;
        currSegment = (int)(x/Map.SEGMENTW);

        restartPoint = new Point2D(x, y);

        EventHandler<? super HitEvent> hitHandler = new EventHandler<HitEvent>() {
            @Override
            public void handle(HitEvent event) {
                takeHit(event.getDamage());
                event.consume();
            }
        };

        this.addEventHandler(
                HitEvent.HIT,
                hitHandler
        );
    }

    public void addToPane(Pane pane) {
        pane.getChildren().add(view.body);
    }

    public void correctMove(double x) {
        pushByX(-x);
    }

    public void correctMoveY(double y) {
        pushByY(y);
    }

    private Rectangle createLandingDetector() {
        Rectangle landingDetector = new Rectangle(view.getBonds().getWidth(), 2*model.getMaxVY());
        landingDetector.setX(view.getBonds().getX() + view.getBonds().getTranslateX());
        landingDetector.setY(view.getBonds().getY() + view.getBonds().getTranslateY() + view.getBonds().getHeight());
        return landingDetector;
    }

    private Rectangle createLeftWallDetector() {
        Rectangle leftDetector = new Rectangle(model.getMaxVX(), view.getBonds().getHeight());
        leftDetector.setX(view.getBonds().getTranslateX() + view.getBonds().getX() - model.getMaxVX());
        leftDetector.setY(view.getBonds().getTranslateY() + view.getBonds().getY());
        return leftDetector;
    }

    private Rectangle createRightWallDetector() {
        Rectangle rightDetector = new Rectangle(model.getMaxVX(), view.getBonds().getHeight()-1);
        rightDetector.setX(view.getBonds().getTranslateX() + view.getBonds().getX() + view.getBonds().getWidth());
        rightDetector.setY(view.getBonds().getTranslateY() + view.getBonds().getY());
        return rightDetector;
    }

    private Rectangle createRoofDetector() {
        Rectangle roofDetector = new Rectangle(view.getBonds().getWidth(), model.getMaxVY());
        roofDetector.setX(view.getBonds().getTranslateX() + view.getBonds().getX());
        roofDetector.setY(view.getBonds().getTranslateY() + view.getBonds().getY() - model.getMaxVY());
        return roofDetector;
    }

    private void fall(Segment segment) {
        if (!isJumping) {
            double y;
            if (( y = segment.measureDistanceToTheGround(createLandingDetector())) <= Math.abs(currVY)) {
                pushByY(y);
                isFalling = false;
                currVY = 0;
            } else {
                isFalling = true;
                if (currVY > -model.getMaxVY()) {
                    currVY -= Map.gravity;
                } else {
                    currVY = -model.getMaxVX();
                }
            }
        }
    }

    public Rectangle getBonds() {
        return view.getBonds();
    }

    public int getCurrSegment() {
        return currSegment;
    }

    public double getCurrVX() {
        return currVX;
    }

    public int getHp() {
        return model.getHp();
    }

    public void goLeft() {
        isGoingRight = false;
        isGoingLeft = true;
        currVX = -model.getMaxVX();
    }

    public void goRight() {
        isGoingRight = true;
        isGoingLeft = false;
        currVX = model.getMaxVX();
    }

    private void jump(Segment segment) {
        if (isJumping && !isFalling) {
            Rectangle roofDetector = createRoofDetector();
            double y;
            if((y = segment.measureDistanceToTheRoof(roofDetector)) < Math.abs(currVY)){
                pushByY(-y);
                isJumping = false;
                isFalling = true;
                currVY = 0;
            } else {
                if (currVY > 0) {
                    currVY -= Map.gravity;
                } else {
                    currVY = 0;
                    isJumping = false;
                    isFalling = true;
                }
            }
        }
    }

    public void move(Segment segment) {
        moveX(segment);
        moveY(segment);
    }

    protected void moveX(Segment segment) {
        double x;
        if(isGoingLeft && (x = segment.measureDistanceToTheLeftWall(createLeftWallDetector())) < Math.abs(currVX)) {
            pushByX(-x + 1);
            isGoingLeft = false;
            if(currVX < 0 ) currVX = 0;
        }
        else if(isGoingRight && (x = segment.measureDistanceToTheRightWall(createRightWallDetector())) < Math.abs(currVX)) {
            pushByX(x - 1);
            isGoingRight = false;
            if(currVX > 0 ) currVX = 0;
        }

        view.makeStrangeMovesWhenMovingX(isFalling, isJumping, isGoingLeft, isGoingRight);
        if(currVX != 0)
            pushByX(currVX);
    }

    protected void moveY(Segment segment) {
        jump(segment);
        fall(segment);
        pushByY(-currVY);
    }

    public void setCurrSegment(int currSegment) {
        this.currSegment = currSegment;
    }

    public void startJumping() {
        if(!isFalling && !isJumping) {
            isJumping = true;
            currVY = model.getMaxVY();
        }
    }

    public void stop() {
        isGoingRight = false;
        isGoingLeft = false;
        currVX = 0;
    }

    protected void pushByY(double y) {
        view.pushByY(y);
    }

    protected void pushByX(double x) {
        view.pushByX(x);
    }

    protected void takeHit(int damage) {
        takeDamage(damage);
    }

    private void takeDamage(int damage) {
        model.setHp(model.getHp() - damage);
    }

    public void reset() {
        model.reset();
        view.reset(restartPoint.getX(), restartPoint.getY());
        currSegment =(int) (restartPoint.getX() / Map.SEGMENTW);
        currVY = 0;
        currVX = 0;
        isFalling = false;
        isGoingLeft = false;
        isGoingRight = false;
        isJumping = false;
    }

    public void resetView() {
        view.reset(restartPoint.getX(), restartPoint.getY());
        currSegment =(int) (restartPoint.getX() / Map.SEGMENTW);
        isJumping = false;
        isFalling = false;
    }

    public Point2D getRestartPoint() {
        return restartPoint;
    }
}
