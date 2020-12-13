package Game.code;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Sprites extends Rectangle {
    boolean dead = false;
    final String type;

    public Sprites(double x, double y, double width, double height, String type, Color color) {
        super(width, height, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public Sprites(double x, double y, double width, double height, String type) {

        super(width, height);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }



    void moveUp() {
        setTranslateY(getTranslateY() - 7);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 7);
    }

    void moveRight() {
        setTranslateX(getTranslateX() + 7);
    }

    void moveLeft() {
        setTranslateX(getTranslateX() - 7);
    }
}
