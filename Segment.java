package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Segment extends Rectangle{

    double translation;                             // translacja - zeby nie przesuwac za kazdym razem wszystkich blokow, tylko zmieniac liczbe
    ArrayList<Box> boxes;
    int index;

    double realPosition;

    public Segment(int idx) {
        super(Map.SEGMENTW, Map.SEGMENTH);
        setFill(Color.TRANSPARENT);
        setTranslateX(Map.SEGMENTW*idx);
        setTranslateY(0);

        boxes = new ArrayList<Box>();
        translation = 0;                            // tu dodano translacje
        index = idx;
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void addBoxesToPane(Pane pane){
        for(Box b : boxes) {
            if(!pane.getChildren().contains(b))
                pane.getChildren().add(b);
        }
    }

    // TODO czy da sie usunac?
    public ArrayList<Box> getPlatforms() {
        return boxes;
    }
/*
    public void changeTranslation(double x) {
        translation = translation + x;
    }

    public void updatePosition() {
        if (translation != 0) {
            for (Box box : boxes) {
                box.setTranslateX(box.getTranslateX() + translation);
            }
            translation = 0;
        }
    }
*/
    public int getIndex() {
        return index;
    }

    public void updateBlocksPosition(double x) {
        for (Box box : boxes) {
            box.pushByX(x);
        }
    }
}
