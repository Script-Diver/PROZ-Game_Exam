package environment.elements.fallingRubble;


public class FallingRubbleModel {
    private int charge;
    private int reloadTime;

    private int damage;
    private double maxVY;

    public FallingRubbleModel(int reload, int dmg, double maxVy) {
        charge = reloadTime = reload;
        damage = dmg;
        maxVY = maxVy;
    }

    public int decrementCharge() {
        return --charge;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public double getMaxVY() {
        return maxVY;
    }

    public int getDamage() {
        return damage;
    }

    public void restartCharge() {
        charge = reloadTime;
    }

    public void reset() {
        restartCharge();
    }
}
