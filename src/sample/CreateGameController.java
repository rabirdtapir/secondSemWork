package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameController implements Initializable {
    @FXML
    private Text nameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showInformation(String name) {
        nameField.setText(name);
    }


}
