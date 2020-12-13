package Game.code;

import javafx.scene.image.Image;

public class Player {
    private int Hp;
    private Image img;
    private int posX, posY;
    boolean destroyed;

    public Player(int hp, Image img, int posX, int posY, boolean destroyed) {
        Hp = hp;
        this.img = img;
        this.posX = posX;
        this.posY = posY;
        this.destroyed = destroyed;
    }

}
