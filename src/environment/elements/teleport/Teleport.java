package environment.elements.teleport;

import characters.character.CharacterController;
import environment.elements.platform.PlatformController;
import environment.map.Map;
import interfaces.EnvironmentElement;
import useful.Functions;
import javafx.scene.paint.Color;

public class Teleport extends PlatformController implements EnvironmentElement{
    private double tX;
    private double tY;
    private boolean end;        // jesli true to znaczy, ze "teleport" do zwyciestwa
    public Teleport(double x, double y, double w, double h, double tx, double ty, boolean e) {
        super(x, y, w, h);
        tX = tx;
        tY = ty;
        setColor(Color.TRANSPARENT);
        end = e;
    }

    public boolean transport(CharacterController target, Map map) {
        if (!end) {
            target.resetView();
            target.correctMoveY(-target.getBonds().getTranslateY() - target.getBonds().getY() + tY);
            map.pushEverything(-map.getSegment(0).getTranslateX() + target.getRestartPoint().getX() );
            map.pushEverything(-tX);
            return false;
        }
        return true;
    }

    // zwroci prawde, jesli koniec gry
    public boolean work(CharacterController target, Map map) {
        if(Functions.isDetectorColliding(getBonds(), target.getBonds())) {
            return transport(target, map);
        }
        return false;
    }

    public void reset() {
        super.reset();
    }
}
