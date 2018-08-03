package environment.elements.platform;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlatformView {
    protected Rectangle rec;

    public PlatformView(double x, double y, double w, double h){
        rec = new Rectangle(x, y, w, h);
        rec.setFill(Color.BLACK);
        rec.setStroke(Color.BLACK);
    }

    public void addToPane(Pane pane) {
        if(!pane.getChildren().contains(rec))
            pane.getChildren().add(rec);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PlatformView
                && ((PlatformView) o).getObjectHeight() == rec.getHeight()
                && ((PlatformView) o).getObjectWidth() == rec.getWidth()
                && ((PlatformView) o).getObjectTranslateX() == rec.getTranslateX()
                && ((PlatformView) o).getObjectTranslateY() == rec.getTranslateY();
    }

    public double getObjectHeight() {
        return rec.getHeight();
    }

    public double getObjectWidth() {
        return rec.getWidth();
    }

    public double getObjectTranslateX() {
        return rec.getTranslateX();
    }

    public double getObjectTranslateY() {
        return rec.getTranslateY();
    }

    public Rectangle getBonds() {
        Rectangle bonds = new Rectangle(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
        bonds.setTranslateX(rec.getTranslateX());
        bonds.setTranslateY(rec.getTranslateY());
        return bonds;
    }

    public void pushByX(double x) {
            rec.setTranslateX(rec.getTranslateX() + x);
    }

    public void pushByY(double y) {
        rec.setTranslateY(rec.getTranslateY() + y);
    }

    protected void setObjectTranslateY(double y) {
        rec.setTranslateY(y);
    }

    protected void setObjectX(double x) {rec.setX(x);}

    protected void setObjectY(double y) {rec.setY(y);}

    public void setColor(Color color) {
        rec.setFill(color);
    }

    public void setStroke(Color color, int w) { rec.setStroke(color); rec.setStrokeWidth(w); }

    public void reset() {
        rec.setTranslateX(0);
        rec.setTranslateY(0);
    }
}
