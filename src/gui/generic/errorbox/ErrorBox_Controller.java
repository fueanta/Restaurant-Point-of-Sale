package gui.generic.errorbox;

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
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorBox_Controller implements Initializable {

    private static Stage window;
    private static String message;

    public static void showError(String title, String message) {
        Main.modifiable = false;
        ErrorBox_Controller.message = message;
        window = new Stage();
        window.setTitle(title);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/error2.png"));
        window.setScene(getErrorScene());
        window.showAndWait();
    }

    public static Scene getErrorScene() {
        Scene errorScene = null;
        try {
            Parent root = FXMLLoader.load(ErrorBox_Controller.class.getResource("ErrorBox_GUI.fxml"));
            errorScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("ErrorBox GUI could not be loaded!");
        }
        return errorScene;
    }

    @FXML private Label messageBox;

    public void okBtnAction(ActionEvent event) {
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageBox.setText(message);
    }

}
