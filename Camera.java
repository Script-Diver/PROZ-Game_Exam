package sample;

public class Camera {
    public static void record(Player player, Map map, Enemy enemy) {
        /*
        if (player.getTranslateX() > map.SEGMENTW * 0.3 && player.isGoingRight()) {
            map.pushEverything(-player.getCurrVX());
            player.correctMove(player.getCurrVX());
            enemy.correctMove(player.getCurrVX());
        }
        else if (player.getTranslateX() < map.SEGMENTW * 0.7 && player.isGoingLeft()) {
        */
            map.pushEverything(-player.getCurrVX());
            player.correctMove(player.getCurrVX());
            enemy.correctMove(player.getCurrVX());

    }
}
