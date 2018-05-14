package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Character{
    private int counter;            // licznik wykorzystywany do odmierzania dystansu przy patrolowaniu
    private boolean targetSpotted;
    private boolean targetReached;

    public Enemy(double h, double w, double maxvY, double maxvX, int health, Color color, double x, double y) {
        super(h, w, maxvY, maxvX, health, color, x, y);
        counter = 0;
        targetSpotted = false;
        targetReached = false;
    }
    // obrat postaci (wraz z polem jej widzenia)
    private void changeDirection() {
        if(isTurnedRight) {
            sight.setTranslateX(this.getTranslateX() - sight.getWidth());
            isTurnedRight = false;
        }
        else {
            sight.setTranslateX(this.getTranslateX() + getWidth());
            isTurnedRight = true;
        }
    }
    public boolean isTargetSpotted() {
        return targetSpotted;
    }
    public boolean isTargetReached() {
        return targetReached;
    }
    // Jesli zobaczy cel zaczyna do niego podazac. Zwraca true jesli go dorwie
    public void search(Rectangle target, double distance) {
        double targetPoint, enemyPoint;
        targetSpotted = true;
        targetReached = false;

        // cel wszedl na wroga - proba utrzymania dystansu
        if(isDetectorColliding(this, target)) {
            targetReached = true;
            if(isTurnedRight)
                goLeft();
            else
                goRight();
        }

        // cel jest widziany przez wroga - poscig i utrzymanie dystansu
        else if(isDetectorColliding(sight, target)) {

            // ustalanie punktow na ktorych podstawie oceniamy jak okreslic odleglosc pomiedzy celem a wrogiem
            if (target.getTranslateX() < this.getTranslateX()) {
                targetPoint = target.getTranslateX() + target.getWidth();
                enemyPoint = this.getTranslateX();
            }
            else {
                targetPoint = target.getTranslateX();
                enemyPoint = sight.getTranslateX();
            }

            // poscig za celem
            if (Math.abs(targetPoint - enemyPoint) > distance) {
                if (isTurnedRight)
                    goRight();
                else
                    goLeft();
            }

            // cel zostal "dorwany" - poczatek utrzymywania dystansu
            else {
                stop();
                targetReached = true;
            }
        }

        // cel nie jest widziany przez wroga
        else
            targetSpotted = false;
    }
    // postac patroluje teren zmieniajac swoj kierunek co czas timeToChangeDirection
    public void patrol(int timeToChangeDirection, boolean withWalking) {
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
}
