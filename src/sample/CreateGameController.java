package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameController implements Initializable {
    @FXML
    private Label nameField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.setText("yolo");
    }

    public void setNameField(String name) {
        nameField.setText(name);
    }


}
