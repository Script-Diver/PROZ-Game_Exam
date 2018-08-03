package characters.character;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CharacterView {
    Rectangle body;

    private final double height;
    private int heightDifferenceWhenGoing = 0;
    private final int maxHeightDifferenceWhenGoing = 7;

    private boolean isRising;
    protected boolean isTurnedRight;

    public CharacterView(double x, double y, double w, double h, Color color) {
        body = new Rectangle(w, h);
        body.setX(x);
        body.setY(y);
        body.setFill(color);

        height = h;
        isRising = false;
        isTurnedRight = false;      // TODO - dopracowac obracanie gracza
    }

    public Rectangle getBonds() {
        Rectangle bonds = new Rectangle(body.getX(), body.getY(), body.getWidth(), body.getHeight());
        bonds.setTranslateX(body.getTranslateX());
        bonds.setTranslateY(body.getTranslateY());
        bonds.setFill(body.getFill());
        return bonds;
    }

    public void setX(double x) { body.setX(x); }

    public void setY(double y) { body.setY(y); }

    public void makeStrangeMovesWhenMovingX(boolean isFalling, boolean isJumping, boolean isGoingLeft, boolean isGoingRight) {
        if(!isFalling && !isJumping && (isGoingLeft || isGoingRight)) {
            if(isRising) {
                if (heightDifferenceWhenGoing > 0)
                    isRising = false;
                heightDifferenceWhenGoing++;
                body.setTranslateY(body.getTranslateY() - 1);
            }
            else {
                if (heightDifferenceWhenGoing < -maxHeightDifferenceWhenGoing)
                    isRising = true;
                heightDifferenceWhenGoing--;
                body.setTranslateY(body.getTranslateY() + 1);
            }
            body.setHeight(height + heightDifferenceWhenGoing);
        }
        else {
            body.setTranslateY(body.getTranslateY() + heightDifferenceWhenGoing);
            body.setHeight(height);
            heightDifferenceWhenGoing = 0;
            isRising = false;
        }
    }

    public void pushByX(double x) {
        body.setTranslateX(body.getTranslateX() + x);
    }

    public void pushByY(double y) {
        body.setTranslateY(body.getTranslateY() + y);
    }

    public void setColor(Color color) {
        body.setFill(color);
    }

    public void reset(double x, double y) {
        body.setHeight(height);
        body.setX(x);
        body.setY(y);
        body.setTranslateX(0);
        body.setTranslateY(0);
        isRising = false;
        isTurnedRight = false;
    }
}
