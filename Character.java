package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Character extends Rectangle {
    private final double height;

    // zmienne pomocnicze do "ruchu postaci w oosi OY" podczas chodzenia
    private int heightDifferenceWhenGoing = 0;
    private int maxHeightDifferenceWhenGoing = 7;
    private boolean isRising;

    // informacja o aktualnym segmencie (zeby przy przechodzeniu do innych segmentow nie sprawdzac wszystkich)
    private int currSegment;

    // predkosci i zycie
    protected final double maxVY;
    protected final double maxVX;
    protected double currVY;
    protected double currVX;
    private int hp;

    // detektory ruchu
    private Rectangle landingDetector;
    private Rectangle roofDetector;
    private Rectangle leftDetector;
    private Rectangle rightDetector;
    public Rectangle sight;                 // TODO - nowy detektor

    // zmienne pomocnicze
    protected boolean isGoingLeft;
    protected boolean isGoingRight;
    protected boolean isJumping;
    protected boolean isFalling;
    protected boolean isTurnedRight;

    public Character(double h, double w, double maxvY, double maxvX, int health, Color color, double x, double y) {
        super(w, h);
        height = this.getHeight();
        setFill(color);

        setTranslateX(x);
        setTranslateY(y);

        currSegment = (int)(x/Map.SEGMENTW);

        landingDetector = new Rectangle(w, maxvY); // 50
        landingDetector.setTranslateX(this.getTranslateX());
        landingDetector.setTranslateY(this.getTranslateY() + height);
        landingDetector.setFill(Color.TRANSPARENT);

        roofDetector = new Rectangle(w, maxvY); // 50
        roofDetector.setTranslateX(getTranslateX());
        roofDetector.setTranslateY(getTranslateY()-roofDetector.getHeight());
        roofDetector.setFill(Color.TRANSPARENT);

        leftDetector = new Rectangle(maxvX, h-1);                              // TODO : poeksperymentowac z wielkoscia detektorow //
        leftDetector.setTranslateX(this.getTranslateX() - leftDetector.getWidth());
        leftDetector.setTranslateY(this.getTranslateY());
        leftDetector.setFill(Color.TRANSPARENT);

        rightDetector = new Rectangle(maxvX, h-1);
        rightDetector.setTranslateX(this.getTranslateX() + getWidth());
        rightDetector.setTranslateY(this.getTranslateY());
        rightDetector.setFill(Color.TRANSPARENT);

        sight = new Rectangle(100, h-11);
        sight.setTranslateX(this.getTranslateX() - sight.getWidth());
        sight.setTranslateY(this.getTranslateY() + 10);
        sight.setFill(Color.TRANSPARENT);
        sight.setStroke(Color.ORANGE);

        maxVY = maxvY;
        maxVX = maxvX;
        isRising = false;

        currVY = 0;
        currVX = 0;
        hp = health;
        isFalling = false;
        isGoingLeft = false;
        isGoingRight = false;
        isJumping = false;
        isTurnedRight = false;      // TODO - dopracowac obracanie gracza
    }
    // poprawka wprowadzana przy uzyciu kamery (postac ma stac na srodku ekranu)
    public void correctMove(double x) {
        pushByX(-x);
    }
    private void fall(Segment segment) {
        if (!isJumping) {
            double y;
            if (( y = measureDistanceToTheGround(segment)) <= Math.abs(currVY)) {
                pushByY(y);
                isFalling = false;
                currVY = 0;
            } else {
                isFalling = true;
                if (currVY > -maxVY) {
                    currVY -= Map.gravity;
                } else {
                    currVY = -maxVY;
                }
            }
        }
    }
    public int getCurrSegment() {
        return currSegment;
    }
    public double getCurrVX() {
        return currVX;
    }
    public double getCurrVY() {
        return currVY;
    }
    public void goLeft() {
        isGoingRight = false;
        isGoingLeft = true;
        currVX = -maxVX;
    }
    public void goRight() {
        isGoingRight = true;
        isGoingLeft = false;
        currVX = maxVX;
    }
    protected boolean isDetectorColliding(Rectangle detector, Rectangle box) {
        return detector.getBoundsInParent().intersects(box.getBoundsInParent());
    }
    public boolean isGoingLeft() {
        return isGoingLeft;
    }
    public boolean isGoingRight() {
        return isGoingRight;
    }
    public boolean isJumping() {
        return isJumping;
    }
    public boolean isFalling() {
        return isFalling;
    }
    private void jump(Segment segment) {
        if (isJumping && !isFalling) {
            double y;
            if((y = measureDistanceToTheRoof(segment)) < Math.abs(currVY)){
                pushByY(-y + 1);
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
    private void makeStrangeMovesWhenMovingX() {
        if(!isFalling && !isJumping && (isGoingLeft || isGoingRight)) {
            if(isRising) {
                if (heightDifferenceWhenGoing > 0)
                    isRising = false;
                heightDifferenceWhenGoing++;
                setTranslateY(getTranslateY() - 1);
            }
            else {
                if (heightDifferenceWhenGoing < -maxHeightDifferenceWhenGoing)
                    isRising = true;
                heightDifferenceWhenGoing--;
                setTranslateY(getTranslateY() + 1);
            }
            setHeight(height + heightDifferenceWhenGoing);
        }
        else {
            setTranslateY(getTranslateY() + heightDifferenceWhenGoing);
            setHeight(height);
            heightDifferenceWhenGoing = 0;
            isRising = false;
        }
    }
    private double measureDistanceToTheGround(Segment segment) {
        double min = Map.INFINITY;
        double currVal;

        for (Box b : segment.getPlatforms()) {
            if (isDetectorColliding(landingDetector, b)) {
                if(( currVal = Math.abs(b.getTranslateY() - landingDetector.getTranslateY())) < min)
                    min = currVal;
            }
        }

        return min;
    }
    private double measureDistanceToTheLeftWall(Segment segment) {
        double min = Map.INFINITY;
        double currVal;

        for (Box b : segment.getPlatforms()) {
            if (isDetectorColliding(leftDetector, b)) {
                if(( currVal = Math.abs(b.getTranslateX() + b.getWidth() - getTranslateX())) < min)
                    min = currVal;
            }
        }

        return min;
    }
    private double measureDistanceToTheRightWall(Segment segment) {
        double min = Map.INFINITY;
        double currVal;

        for (Box b : segment.getPlatforms()) {
            if (isDetectorColliding(rightDetector, b)) {
                if(( currVal = Math.abs(b.getTranslateX() - rightDetector.getTranslateX())) < min)
                    min = currVal;
            }
        }

        return min;
    }
    private double measureDistanceToTheRoof(Segment segment) {
        double min = Map.INFINITY;
        double currVal;

        for (Box b : segment.getPlatforms()) {
            if (isDetectorColliding(roofDetector, b)) {
                if ((currVal = Math.abs(b.getTranslateY() + b.getHeight() - getTranslateY())) < min) {
                    min = currVal;
                }
            }
        }

        return min;
    }
    private void moveX(Segment segment) {
        double x;
        if(isGoingLeft && (x = measureDistanceToTheLeftWall(segment)) < Math.abs(currVX)) {
            pushByX(-x + 1);
            isGoingLeft = false;
            if(currVX < 0 ) currVX = 0;
        }
        else if(isGoingRight && (x = measureDistanceToTheRightWall(segment)) < Math.abs(currVX)) {
            pushByX(x - 1);
            isGoingRight = false;
            if(currVX > 0 ) currVX = 0;
        }

        makeStrangeMovesWhenMovingX();
        if(isGoingLeft || isGoingRight) {
            pushByX(currVX);
        }
    }
    private void moveY(Segment segment) {
        jump(segment);
        fall(segment);
        pushByY(-currVY);
    }
    protected void pushByY(double y) {
        setTranslateY(this.getTranslateY() + y);
        leftDetector.setTranslateY(leftDetector.getTranslateY() + y);
        rightDetector.setTranslateY(rightDetector.getTranslateY() + y);
        landingDetector.setTranslateY(landingDetector.getTranslateY() + y);
        roofDetector.setTranslateY(roofDetector.getTranslateY() + y);
        sight.setTranslateY(sight.getTranslateY() + y);
    }
    protected void pushByX(double x) {
        setTranslateX(this.getTranslateX() + x);
        leftDetector.setTranslateX(leftDetector.getTranslateX() + x);
        rightDetector.setTranslateX(rightDetector.getTranslateX() + x);
        landingDetector.setTranslateX(landingDetector.getTranslateX() + x);
        roofDetector.setTranslateX(roofDetector.getTranslateX() + x);
        sight.setTranslateX(sight.getTranslateX() + x);
    }
    public void setCurrSegment(int currSegment) {
        this.currSegment = currSegment;
    }
    public void startJumping() {
        if(!isFalling && !isJumping) {
            isJumping = true;
            currVY = maxVY;
        }
    }
    public void stop() {
        isGoingRight = false;
        isGoingLeft = false;
        currVX = 0;
    }
    public void takeHit(double damage) {
        takeDamage(damage);
        if(hp <= 0) {
            System.out.println("UMARLEM!!!");       // TODO - obsluga smierci
        }
    }
    private void takeDamage(double damage) {        // TODO - moze jakas animacja?
        hp -= damage;
    }
}
