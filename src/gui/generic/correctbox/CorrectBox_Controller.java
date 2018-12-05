package gui.generic.correctbox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CorrectBox_Controller implements Initializable {

    private static Stage window;
    private static String message;

    public static void showCorrect(String title, String message) {
        CorrectBox_Controller.message = message;
        window = new Stage();
        window.setTitle(title);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/correct2.png"));
        window.setScene(getCorrectScene());
        window.showAndWait();
    }

    public static Scene getCorrectScene() {
        Scene errorScene = null;
        try {
            Parent root = FXMLLoader.load(CorrectBox_Controller.class.getResource("CorrectBox_GUI.fxml"));
            errorScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("CorrectBox GUI could not be loaded!");
        }
        return errorScene;
    }

    @FXML
    private Label messageBox;

    public void okBtnAction(ActionEvent event) {
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageBox.setText(message);
    }
}
