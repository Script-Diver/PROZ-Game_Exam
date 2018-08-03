package environment.elements.fallingPlatform;

public class FallingPlatformModel {
    private double maxVY;

    FallingPlatformModel(double maxVy){
        maxVY = maxVy;
    }

    public double getMaxVY() {
        return maxVY;
    }

    public void reset() {}
}