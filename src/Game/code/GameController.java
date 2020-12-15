package Game.code;

import client.GameClient;
import client.GameServer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GameController extends Application {

    Sprites player = new Sprites(300, 750, 40, 40, "player");
    private Pane root = new Pane();
    private final int width = 600;
    private int height = 800;
    private double t = 0;
    private int timer = 0;
    private boolean isEnd;
    URL player1 = getClass().getResource("firstPlayer.png");
    URL enemy = getClass().getResource("enemy.png");
//    String enemy = getClass().getResource("Game/lib/enemy.png").toExternalForm();

    private Socket socket;
    private ServerSocket serverSocket;

    private DataOutputStream dos;
    private DataInputStream dis;
    private Thread thread;

    public void run() {

    }

    public static void main(String[] args) {
        launch(args);
    }


    private Parent createContent() {
        root.setPrefSize(width, height);
        player.setFill(new ImagePattern(new Image(String.valueOf(player1))));

        root.getChildren().add(player);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        nextLvl();
        return root;
    }

    private void nextLvl() {
        for (int i = 0; i < width / 100; i++) {
            Sprites sprite = new Sprites(i * 100 + 20, 150, 60, 60, "enemy");
            sprite.setFill(new ImagePattern(new Image(String.valueOf(enemy))));
            root.getChildren().add(sprite);
        }
    }

    private List<Sprites> listSprites() {
       return root.getChildren().stream().map(n -> (Sprites)n).collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;
        timer++;
        listSprites().forEach(s -> {
            switch (s.type) {
                case "enemyshoot":
                    s.moveDown();
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;

                case "playershoot":
                    s.moveUp();
                    listSprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });

                    break;
                case "enemy":
                    if (timer == 30) {
                        s.moveDown();
                        timer = 0;
                        break;
                    }

                    if(t > 2) {
                        if(Math.random() > 0.9)
                        shoot(s);
                    }

                    if(t > 2.2) {
                        t = 0;
                    }
                    break;

            }
        });
        root.getChildren().removeIf(n -> {
            Sprites s = (Sprites) n;
            return s.dead;
        });

        if (player.dead) {
            isEnd = true;
        }
    }

    private void shoot(Sprites player) {
        Sprites sprites = new Sprites(player.getTranslateX() + 20, player.getTranslateY(), 5, 20, player.type + "shoot", Color.BLACK);

        root.getChildren().add(sprites);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case W:
                    player.moveUp();
                    break;
                case S:
                    player.moveDown();
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });
        primaryStage.show();
    }


}
