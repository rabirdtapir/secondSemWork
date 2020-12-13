package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class JoinGameController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField hostName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void JoinGameAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
    }

    public String getPort() {
        return hostName.getText();
    }
}
