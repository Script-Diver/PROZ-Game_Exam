package environment.map;

import useful.AlertBox;
import characters.character.CharacterController;
import characters.student.StudentController;
import environment.elements.fallingPlatform.FallingPlatformController;
import environment.elements.fallingRubble.FallingRubbleController;
import environment.elements.platform.PlatformController;
import environment.elements.teleport.Teleport;
import events.HitEvent;
import useful.Functions;
import javafx.event.Event;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public final static double INFINITY = 10000000;
    public final static double SEGMENTW = 1000;
    public final static double SEGMENTH = 600;
    public static double gravity = 0.5;

    private Segment segments[];
    private ArrayList<StudentController> students;

    public Map() {
        segments = new Segment[30];
        students = new ArrayList<StudentController>();

        for(int i = 0 ; i < segments.length ; i++) {
            segments[i] = new Segment(i);
        }
    }
    private void addStudent(StudentController st) {
        students.add(st);
    }

    private void addPlatformToSegments(PlatformController p, double maxW, Color c) {
        int segNum = (int)((p.getBonds().getX() + p.getBonds().getTranslateX() - 2 * maxW) / SEGMENTW);
        p.setColor(c);
        p.setStroke(Color.BLACK, 4);
        while (true) {
            if( segNum >= 0 && segNum < segments.length) {
                segments[segNum].addPlatform(p);
                p.increaseNumberOfSegments();
                if (p.getBonds().getTranslateX() + p.getBonds().getX() + p.getBonds().getWidth() + 2 * maxW < (segNum + 1) * SEGMENTW)
                    break;
                segNum++;
            }
            else break;
        }
    }

    private void addFallingRubbleToTheSegments(FallingRubbleController fr, double maxW) {
        int segNum = (int)((fr.getBonds().getX() + fr.getBonds().getTranslateX() - maxW) / SEGMENTW);

        while (true) {
            if( segNum >= 0 && segNum < segments.length) {
                segments[segNum].addFallingRubble(fr);
                fr.increaseNumberOfSegments();
                if (fr.getBonds().getTranslateX() + fr.getBonds().getX() + fr.getBonds().getWidth() + maxW < (segNum + 1) * SEGMENTW)
                    break;
                segNum++;
            }
            else
                break;
        }
    }

    private void addPhantomBlockToSegments(PlatformController p, double maxW, Color c) {
        int segNum = (int)((p.getBonds().getX() + p.getBonds().getTranslateX() - maxW) / SEGMENTW);
        p.setColor(c);
        while (true) {
            if( segNum >= 0 && segNum < segments.length) {
                segments[segNum].addPhantomBlock(p);
                p.increaseNumberOfSegments();
                if (p.getBonds().getTranslateX() + p.getBonds().getX() + p.getBonds().getWidth() + maxW < (segNum + 1) * SEGMENTW)
                    break;
                segNum++;
            }
            else
                break;
        }
    }

    private void addFallingPlatformToTheSegments(FallingPlatformController fp, double maxW) {
        int segNum = (int)((fp.getBonds().getX() + fp.getBonds().getTranslateX() - 2 * maxW) / SEGMENTW);
        fp.setStroke(Color.BLACK, 4);

        while (true) {
            if ( segNum >= 0 && segNum < segments.length) {
                segments[segNum].addFallingPlatform(fp);
                fp.increaseNumberOfSegments();
                if (fp.getBonds().getTranslateX() + fp.getBonds().getX() + fp.getBonds().getWidth() + 2 * maxW < (segNum + 1) * SEGMENTW)
                    break;
                segNum++;
            }
            else
                break;
        }
    }

    private void addTeleportToTheSegment(double x, double y, double w, double h, int c1) {
        int segNum = (int)(x / SEGMENTW);
        boolean end = false;
        if( segNum >= 0 && segNum < segments.length) {
            if (c1 == 0)
                end = true;
            Teleport t = new Teleport(x, y, 100, 100, w, h, end);
            t.increaseNumberOfSegments();
            segments[segNum].addTeleport(t);
        }
    }

    private void addStudentsToPane(Pane pane) {
        for (StudentController st : students)
            st.addToPane(pane);
    }

    private void addSegmentsToPane(Pane pane) {
        for(int i = 0 ; i < segments.length ; i++) {
           // segments[i].addAllToPane(pane);
            segments[i].addAllToPane(pane);
        }
    }

    public Segment getSegment(int idx) {
        if (idx >= 0 && idx < segments.length)
            return segments[idx];
        return null;
    }

    public void init(Pane pane, double maxW) {
        loadMapFromFile("Platforms.txt", maxW);
        addSegmentsToPane(pane);
        addStudentsToPane(pane);
    }

    //maxW jest to maksymalna szerokosc poruszajacej sie po mapie postaci
    private void loadMapFromFile(String fileName, double maxW){ // maksymalna szerokosc postaci w grze
        String s;
        BufferedReader reader;

        try { reader = new BufferedReader(new FileReader(fileName)); }
        catch (FileNotFoundException exception) {
            AlertBox.display("Error001 : Invalid map", "File not found");
            System.exit(1);
            return;
        }

        try {
            while ((s = reader.readLine()) != null) {
                String data[] = s.split(" ");
                if (data[0].isEmpty() || !(data.length == 8)) {
                    AlertBox.display("Error002 : Invalid map", "Invalid line");
                    System.exit(2);
                    break;
                }
                int n = Integer.parseInt(data[0]);
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                double w = Double.parseDouble(data[3]);
                double h = Double.parseDouble(data[4]);
                int c1 = Integer.parseInt(data[5]);
                int c2 = Integer.parseInt(data[6]);
                int c3 = Integer.parseInt(data[7]);

                if (n == 0)
                    addPlatformToSegments(new PlatformController(x, y, w, h), maxW, Color.rgb(c1,c2, c3, 1));
                else if (n == 1) addFallingRubbleToTheSegments(new FallingRubbleController(x, y, (int)w, h), maxW);
                else if (n == 2) addFallingPlatformToTheSegments(new FallingPlatformController(x, y, w, h), maxW);
                else if (n == 3) addStudent(new StudentController(x, y, w, h, StudentController.StMaxVX, StudentController.StMaxVY));
                else if (n == 4) addPhantomBlockToSegments(new PlatformController(x, y, w, h), maxW, Color.rgb(c1,c2, c3, 1));
                else if (n == 5) addTeleportToTheSegment(x, y, w, h, c1);
            }
        }
        catch(IOException exception) { AlertBox.display("Error003 : Invalid map", "File corupted"); System.exit(3);}

        try { reader.close(); }
        catch(IOException exception) { AlertBox.display("Error004 : Invalid map", "Closing file failed"); }
    }

    public void pushEverything(double translation) {
        for (Segment segment : segments) {
            segment.setTranslateX(segment.getTranslateX() + translation);
            segment.updateComponentsPosition(translation);
        }
        for (StudentController st : students) {
            st.correctMove(-translation);
        }
    }

    private void updateStudents(CharacterController target) {
        for (StudentController st : students)
            st.chase(getSegment(whichSegment(st)), target);
    }

    public boolean updateAllSegments(CharacterController target) {
        boolean result = false;
        for (Segment seg : segments) {
            if (seg.update(target, this))
                result = true;
        }
        return result;
    }

    public boolean update(CharacterController target) {
        boolean result = updateAllSegments(target);
        updateStudents(target);
        return result;
    }

    public int whichSegment(CharacterController x) {
        if(x.getBonds().getY() + x.getBonds().getTranslateY() > SEGMENTH) {
            HitEvent event = new HitEvent(x, x, x.getHp() + 1);
            Event.fireEvent(x, event);
        }

        else if(!Functions.isDetectorColliding(x.getBonds(), segments[x.getCurrSegment()])) {
            if(x.getCurrSegment() != segments.length && Functions.isDetectorColliding(x.getBonds(), segments[x.getCurrSegment()+1]))
                x.setCurrSegment(x.getCurrSegment() + 1);
            else if(x.getCurrSegment() != 0 && Functions.isDetectorColliding(x.getBonds(), segments[x.getCurrSegment()-1]))
                x.setCurrSegment(x.getCurrSegment() - 1);
            else {
                int i;
                for (i = 0 ; i < segments.length ; i++) {
                    if (Functions.isDetectorColliding(x.getBonds(), segments[i])) {
                        x.setCurrSegment(i);
                        break;
                    }
                }
                if (i == segments.length) {
                    HitEvent event = new HitEvent(x, x, x.getHp() + 1);
                    Event.fireEvent(x, event);
                }
            }
        }

        return x.getCurrSegment();
    }

    public void reset() {
        for (Segment s : segments)
            s.reset();
        for (StudentController st : students)
            st.reset();
    }
}
