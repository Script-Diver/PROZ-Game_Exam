package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    Segment segments[];
    public final static double INFINITY = 10000000;
    public final static double SEGMENTW = 600;
    public final static double SEGMENTH = 600;
    public static double gravity = 0.5;


    public Map() {
       // segments = new ArrayList<Segment>();
        segments = new Segment[10];

        for(int i = 0 ; i < segments.length ; i++) {
            segments[i] = new Segment(i);
        }
    }

    public void addSegmentsToPane(Pane pane) {
        for(int i = 0 ; i < segments.length ; i++) {
            segments[i].addBoxesToPane(pane);
        }
    }

    // TODO //
    public void init(Pane pane, double maxW) {
        loadSegmentsFromFile("Platforms.txt", maxW);
        addSegmentsToPane(pane);
    }

    private void loadSegmentsFromFile(String fileName, double maxW){ // maksymalna szerokosc postaci w grze
        String s;
        BufferedReader reader;
        Color c = Color.BLACK;

        try {
            reader = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException exception) {
            // TODO - alert box //
            System.out.println("File not found");  // to ma sie wyswietlac w okienku
            return;
        }

        try {
            while ((s = reader.readLine()) != null) {
                String data[] = s.split(" ");
                if (data[0].isEmpty()) {
                    System.out.println("Invalid line");
                    break;
                }

                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                double w = Double.parseDouble(data[2]);
                double h = Double.parseDouble(data[3]);
                Box b = new Box(x, y, w, h, c);

                int segNum = (int)(x / SEGMENTW);
                while (true) {
                    segments[segNum].addBox(b);
                    b.increaseNumberOfSegments();
                    if (x + w < (segNum + 1) * SEGMENTW)  // TODO czy po lewej stronie nierownosci wystarczy, ze dpdamy szerokosc najdluzszej postaci?
                        break;
                    segNum++;
                }

                /*

                for (int i = 4; i < data.length; i++) {         // dodawanie danego bloku do okreslonych segmentow
                    int numSeg = Integer.parseInt(data[i]);
                    segments[numSeg].addBox(b);
                    System.out.println("DODANO");
                }

                */
            }
        }
        catch(IOException exception) {
            System.out.println("File corupted");  // to ma sie wyswietlac w okienku
        }

        try {
            reader.close();
        }
        catch(IOException exception) {
            System.out.println("Closing file failed");
        }
    }

    // TODO - jesli sie da, to zrobic prywatne
    public Segment getSegment(int idx) {
        if (idx >= 0 && idx < segments.length)
            return segments[idx];
        return null;
    }

    /*
    //zmieniamy rzeczywiste polozenie blokow w sasiednich segmentach oraz w segmencie obecnym
    public void pushEverything(double translation, int currSegment) {
        for (Segment segment : segments) {
            segment.changeTranslation(translation);
        }

        if(getSegment(currSegment) != null) {
            getSegment(currSegment).updatePosition();
        }
        if(getSegment(currSegment - 1) != null) {
            getSegment(currSegment - 1).updatePosition();
        }
        if(getSegment(currSegment + 1) != null) {
            getSegment(currSegment + 1).updatePosition();
        }
    }
    */

    public void pushEverything(double translation) {
        for (Segment segment : segments) {
            segment.setTranslateX(segment.getTranslateX() + translation);
            segment.updateBlocksPosition(translation);
        }
    }

    public int whichSegment(Character x) {
        if(!x.getBoundsInParent().intersects(segments[x.getCurrSegment()].getBoundsInParent())) {
            if(x.getCurrSegment() != segments.length && x.getBoundsInParent().intersects(segments[x.getCurrSegment() + 1].getBoundsInParent()))
                x.setCurrSegment(x.getCurrSegment() + 1);
            else if(x.getCurrSegment() != 0 && x.getBoundsInParent().intersects(segments[x.getCurrSegment() - 1].getBoundsInParent()))
                x.setCurrSegment(x.getCurrSegment() - 1);
            else
                System.out.println("AAAAAAAAAAAAAAA GDZIE JESTEM?????!!!!");
        }
        return x.getCurrSegment();
    }
}
