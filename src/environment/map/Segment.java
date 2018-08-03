package environment.map;

import characters.character.CharacterController;
import environment.elements.fallingPlatform.FallingPlatformController;
import environment.elements.fallingRubble.FallingRubbleController;
import environment.elements.platform.PlatformController;

import java.util.ArrayList;

import environment.elements.teleport.Teleport;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Segment extends Rectangle{
    private ArrayList<PlatformController> platforms;
    private ArrayList<FallingPlatformController> fallingPlatforms;
    private ArrayList<FallingRubbleController> fallingRubbles;
    private ArrayList<PlatformController> phantomBlocks;
    private ArrayList<Teleport> teleports;
    private int index;

    public Segment(int idx) {
        super(Map.SEGMENTW, Map.SEGMENTH);
        setFill(Color.TRANSPARENT);
        setTranslateX(Map.SEGMENTW*idx);
        setTranslateY(0);

        platforms = new ArrayList<PlatformController>();
        fallingPlatforms = new ArrayList<FallingPlatformController>();
        fallingRubbles = new ArrayList<FallingRubbleController>();
        phantomBlocks = new ArrayList<PlatformController>();
        teleports = new ArrayList<Teleport>();
        index = idx;
    }

    public void addPlatform(PlatformController platform) {
        platform.setStroke(Color.BLACK, 4);
        platforms.add(platform);
    }

    public void addFallingRubble(FallingRubbleController rubble) {
        rubble.setStroke(Color.BLACK, 4);
        fallingRubbles.add(rubble);
    }

    public void addFallingPlatform(FallingPlatformController fp) {
        fp.setStroke(Color.BLACK, 4);
        fallingPlatforms.add(fp);
    }

    public void addPhantomBlock(PlatformController b) { phantomBlocks.add(b); }

    public void addTeleport(Teleport t) {
        t.setStroke(Color.TRANSPARENT, 1);
        teleports.add(t);
    }

    private void addBoxesToPane(Pane pane) {
        for(PlatformController platform : platforms) {
            if(!pane.getChildren().contains(platform))
                platform.addToPane(pane);
        }
    }

    private void addFallingRubblesToPane(Pane pane) {
        for(FallingRubbleController fr : fallingRubbles) {
            if(!pane.getChildren().contains(fr)) {
                fr.addToPane(pane);
            }
        }
    }

    private void addFallingPlatformsToPane(Pane pane) {
        for(FallingPlatformController fp : fallingPlatforms) {
            if(!pane.getChildren().contains(fp))
                fp.addToPane(pane);
        }
    }

    private void addPhantomBlocksToPane(Pane pane) {
        for(PlatformController platform : phantomBlocks) {
            if(!pane.getChildren().contains(platform))
                platform.addToPane(pane);
        }
    }

    private void addTeleportsToPane(Pane pane) {
        for(Teleport t : teleports) {
            if(!pane.getChildren().contains(t))
                t.addToPane(pane);
        }
    }

    public void addAllToPane(Pane pane) {
        addPhantomBlocksToPane(pane);
        addBoxesToPane(pane);
        addFallingRubblesToPane(pane);
        addFallingPlatformsToPane(pane);
        addTeleportsToPane(pane);
        pane.getChildren().add(this);
    }

    protected boolean isDetectorColliding(Rectangle detector, Rectangle box) {
        return detector.getBoundsInParent().intersects(box.getBoundsInParent());
    }

    public double measureDistanceToTheGround(Rectangle landingDetector) {
        double min = Map.INFINITY;
        double currVal;
        double dUpperEdge = landingDetector.getY();

        for (PlatformController platform : platforms) {
            if (isDetectorColliding(landingDetector, platform.getBonds())) {
                double pUpperEdge = platform.getBonds().getY() + platform.getObjectTranslateY();
                if(( currVal = Math.abs(pUpperEdge - dUpperEdge)) < min) {

                    min = currVal;
                }
            }
        }

        for (FallingPlatformController fp : fallingPlatforms) {
            if (isDetectorColliding(landingDetector, fp.getBonds())) {
                double fpUpperEdge = fp.getBonds().getY() + fp.getObjectTranslateY();
                if(( currVal = Math.abs(fpUpperEdge - dUpperEdge)) < min)
                    min = currVal;
            }
        }
        return min;
    }

    public double measureDistanceToTheLeftWall(Rectangle leftDetector) {
        double min = Map.INFINITY;
        double currVal;
        double dRightEdge = leftDetector.getX() + leftDetector.getWidth();

        for (PlatformController p : platforms) {
            if (isDetectorColliding(leftDetector, p.getBonds())) {
                double pRightEdge = p.getBonds().getX() + p.getBonds().getWidth() + p.getBonds().getTranslateX();
                if(( currVal = Math.abs(pRightEdge - dRightEdge)) < min) {
                    min = currVal;
                }
            }
        }
        for (FallingPlatformController fp : fallingPlatforms) {
            if (isDetectorColliding(leftDetector, fp.getBonds())) {
                double fpRightEdge = fp.getBonds().getX() + fp.getBonds().getWidth() + fp.getBonds().getTranslateX();
                if(( currVal = Math.abs(fpRightEdge - dRightEdge)) < min)
                    min = currVal;
            }
        }
        return min;
    }

    public double measureDistanceToTheRightWall(Rectangle rightDetector) {
        double min = Map.INFINITY;
        double currVal;
        double dLeftEdge = rightDetector.getX();
        for (PlatformController p : platforms) {
            if (isDetectorColliding(rightDetector, p.getBonds())) {
                double pLeftEdge = p.getBonds().getX() + p.getBonds().getTranslateX();
                if(( currVal = Math.abs(pLeftEdge - dLeftEdge)) < min)
                    min = currVal;
            }
        }
        for (FallingPlatformController fp : fallingPlatforms) {
            if (isDetectorColliding(rightDetector, fp.getBonds())) {
                double fpLeftEdge = fp.getBonds().getX() + fp.getBonds().getTranslateX();
                if(( currVal = Math.abs(fpLeftEdge - dLeftEdge)) < min)
                    min = currVal;
            }
        }
        return min;
    }

    public double measureDistanceToTheRoof(Rectangle roofDetector) {
        double min = Map.INFINITY;
        double currVal;
        double dBottomEdge = roofDetector.getY() + roofDetector.getHeight();

        for (PlatformController p : platforms) {
            if (isDetectorColliding(roofDetector, p.getBonds())) {
                double pBottomEdge = p.getBonds().getY() + p.getBonds().getTranslateY() + p.getBonds().getHeight();
                if ((currVal = Math.abs(pBottomEdge - dBottomEdge)) < min) {
                    min = currVal;
                }
            }
        }
        for (FallingPlatformController fp : fallingPlatforms) {
            if (isDetectorColliding(roofDetector, fp.getBonds())) {
                double fpBottomEdge = fp.getBonds().getY() + fp.getBonds().getTranslateY() + fp.getBonds().getHeight();
                if ((currVal = Math.abs(fpBottomEdge - dBottomEdge)) < min) {
                    min = currVal;
                }
            }
        }
        return min;
    }

    public boolean update(CharacterController target, Map map) {
        return updateComponentsMove(target, map);
    }

    private boolean updateComponentsMove(CharacterController target, Map map) {
        updateFallingPlatforms(target);
        updateFallingRubbles(target);
        return updateTeleports(target, map);
    }

    public void updateComponentsPosition(double x) {
        for (PlatformController platform : platforms) {
            platform.pushByX(x);
        }
        for (FallingRubbleController rubble : fallingRubbles) {
            rubble.pushByX(x);
        }
        for (FallingPlatformController fp : fallingPlatforms) {
            fp.pushByX(x);
        }
        for (PlatformController b : phantomBlocks) {
            b.pushByX(x);
        }
        for (Teleport t : teleports){
            t.pushByX(x);
        }
    } // dla kamery

    private void updateFallingPlatforms(CharacterController target) {
        for(FallingPlatformController fp : fallingPlatforms) {
            fp.update(target);
        }
    }

    private void updateFallingRubbles(CharacterController target) {
        for(FallingRubbleController r : fallingRubbles) {
            r.update(target);
        }
    }

    private boolean updateTeleports(CharacterController target, Map map) {
        boolean result = false;
        for(Teleport t : teleports) {
            if(t.work(target, map))
                result = true;
        }
        return result;
    }

    public void reset() {
        setTranslateY(0);
        setTranslateX(Map.SEGMENTW * index);
        for (PlatformController p : platforms)
            p.reset();
        for (FallingPlatformController fp : fallingPlatforms)
            fp.reset();
        for (FallingRubbleController fr : fallingRubbles)
            fr.reset();
        for (PlatformController p : phantomBlocks)
            p.reset();
        for (Teleport t : teleports)
            t.reset();
    }
}
