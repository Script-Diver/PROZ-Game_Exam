package characters.player;

import characters.character.CharacterController;
import environment.map.Segment;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PlayerController extends CharacterController {
    private boolean wantToJump;
    private boolean wantToGoLeft;
    private boolean wantToGoRight;
    private int counter;
    private final int timeToTakeHit;

    public PlayerController(int h, double w, double vY, double vX, int health, double x, double y){
        super(h, w, vY, vX, health, x, y);
        view.setColor(new Color(0, 0.6, 0 , 1));
        wantToJump = false;
        wantToGoLeft = false;
        wantToGoRight = false;
        counter = 0;
        timeToTakeHit = 80;
    }

    @Override
    public void goLeft() {
        wantToGoLeft = true;
        wantToGoRight = false;
        isGoingLeft = true;
        currVX = -model.getMaxVX();
    }

    @Override
    public void goRight() {
        wantToGoLeft = false;
        wantToGoRight = true;
        isGoingRight = true;
        currVX = model.getMaxVX();
    }

    public void stopGoingLeft() {
        isGoingLeft = false;
        wantToGoLeft = false;
        if(isGoingRight) {
            currVX = model.getMaxVX();
            wantToGoRight = true;
        }
        if(currVX < 0) {
            currVX = 0;
            wantToGoRight = false;
        }
    }

    public void stopGoingRight() {
        isGoingRight = false;
        wantToGoRight = false;
        if(isGoingLeft) {
            currVX = -model.getMaxVX();
            wantToGoLeft = true;
        }
        if(currVX > 0) {
            currVX = 0;
            wantToGoLeft = false;
        }
    }

    @Override
    public void startJumping() {
        wantToJump = true;
    }

    public void stopJumping() {
        wantToJump = false;
    }

    @Override
    protected void moveY(Segment segment) {
        if (wantToJump)
            super.startJumping();
        super.moveY(segment);
    }

    @Override
    protected void moveX(Segment segment) {
        if (wantToGoRight)
            goRight();
        else if (wantToGoLeft)
            goLeft();
        super.moveX(segment);
    }

    @Override
    public void move(Segment segment) {
        moveX(segment);
        moveY(segment);
    }

    public void update(Segment segment) {
        if(++counter == timeToTakeHit){
            counter = 0;
            takeHit(1);
        }
        move(segment);
    }

    @Override
    public void reset() {
        super.reset();
        wantToJump = false;
        wantToGoLeft = false;
        wantToGoRight = false;
        counter = 0;
    }
}
