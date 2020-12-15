package Game.code;

import javafx.scene.image.Image;

import java.net.InetAddress;

public class PlayerMP extends Sprites {

    public InetAddress ipAddress;
    public int port;


        public PlayerMP(double x, double y, double width, double height, String type, InetAddress ip, int port) {
            super(x, y, width, height, type);
            this.ipAddress = ip;
            this.port = port;

        }

}
