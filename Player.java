package sample;

import javafx.scene.paint.Color;

public class Player extends Character{
    public Player(double h, double w, double vY, double vX, int health, Color color, double x, double y){
        super(h, w, vY, vX, health, color, x, y);
    }

    @Override
    // funkcja majaca na celu uplynnienie sterowania postacia przez gracza
    public void goLeft() {
        isGoingLeft = true;
        currVX = -maxVX;
    }

    @Override
    // funkcja majaca na celu uplynnienie sterowania postacia przez gracza
    public void goRight() {
        isGoingRight = true;
        currVX = maxVX;
    }

    // funkcja majaca na celu uplynnienie sterowania postacia przez gracza
    public void stopGoingLeft() {
        isGoingLeft = false;
        if(isGoingRight)
            currVX = maxVX;
        if(currVX < 0)
            currVX = 0;
    }

    // funkcja majaca na celu uplynnienie sterowania postacia przez gracza
    public void stopGoingRight() {
        isGoingRight = false;
        if(isGoingLeft)
            currVX = -maxVX;
        if(currVX > 0)
            currVX = 0;
    }
}
