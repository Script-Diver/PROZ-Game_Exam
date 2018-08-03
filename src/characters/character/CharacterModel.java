package characters.character;


public class CharacterModel {

    // predkosci i zycie
    protected final double maxVY;
    protected final double maxVX;
    private final int maxHp;
    private int hp;


    public CharacterModel(double maxvY, double maxvX, int health) {
        maxVY = maxvY;
        maxVX = maxvX;

        maxHp = health;
        hp = health;
    }

    public int getHp() {
        return hp;
    }

    public double getMaxVY() {
        return maxVY;
    }

    public double getMaxVX() {
        return maxVX;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void reset() {
        hp = maxHp;
    }
}
