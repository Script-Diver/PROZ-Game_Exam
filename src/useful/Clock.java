package useful;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class Clock {
    Rectangle rec;
    Text text;

    Integer time;

    public Clock(int t, int height) {
        time = t;

        rec = new Rectangle(0, 0, 2*height, height);
        rec.setFill(Color.WHITE);
        rec.setStroke(Color.GREEN);

        text = new Text();
        text.setFont(new Font(height/2));
        text.setFill(Color.RED);
        text.setText(time.toString());
        text.setTranslateY(height*2/3);
        text.setTranslateX(height/4);
    }

    public void addToPane(Pane pane) {
        pane.getChildren().addAll(rec, text);
    }

    public void setTime(int t) {
        time = t;
        text.setText(time.toString());
    }
}
