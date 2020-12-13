package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label label;
    @FXML
    private TextField inputField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void buttonAction(ActionEvent actionEvent) {
        try {
            String str = inputField.getText();
            label.setText("hi " + str);
        } catch (NullPointerException e ) {
            System.out.println("Не работает");
        }
    }

    @FXML
    private void joinGameAction(ActionEvent actionEvent) {
        try {
            if (this.inputField.getText().equals("")) {
                noneName();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resource/joinGame.fxml"));
                Parent root = loader.load();
                JoinGameController joinGameController = loader.getController();
                Stage primaryStage = new Stage();
                primaryStage.setScene(new Scene(root, 585, 380));
                primaryStage.show();

            }

        } catch (NullPointerException | IOException e) {


        }

    }

    @FXML
    private void createGameAction(ActionEvent actionEvent) throws IOException {
        try {
            String text = this.inputField.getText();
            if(text.equals("")) {
                noneName();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resource/createGame.fxml"));
                inputField.getScene().getWindow().hide();
                Parent newView = loader.load();
                CreateGameController createGameController = loader.getController();
                System.out.println(text);
                createGameController.setNameField(text);
                Stage primaryStage = new Stage();
                primaryStage.setScene(new Scene(newView, 585, 380));
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void noneName() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Empty name");
        alert.setContentText("Enter your name");
        alert.showAndWait();
    }
}
