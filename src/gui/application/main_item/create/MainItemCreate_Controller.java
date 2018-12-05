package gui.application.main_item.create;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataModifier;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resource.utility.UtilityProvider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainItemCreate_Controller implements Initializable {

    private static Stage window;

    public static void showCreator() {
        window = new Stage();
        window.setTitle("New Item");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setScene(getCreateScene());
        window.showAndWait();
    }

    public static Scene getCreateScene() {
        Scene createScene = null;
        try {
            Parent root = FXMLLoader.load(MainItemCreate_Controller.class.getResource("MainItemCreate_GUI.fxml"));
            createScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("Main Item Create GUI could not be loaded!");
        }
        return createScene;
    }

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField priceField;
    @FXML public JFXTextField discountField;
    @FXML public JFXTextField typeField;
    @FXML public JFXTextField availabilityField;
    @FXML public JFXTextField inventoryField;
    @FXML public JFXTextField stockField;

    // area
    @FXML public JFXTextArea descriptionField;

    // photo
    @FXML public ImageView photo;

    public void cancelBtnOnAction(ActionEvent event) {
        window.close();
    }

    public void createBtnOnAction(ActionEvent event) {
        DB_DataModifier.insertMainItem(
                nameField.getText(), UtilityProvider.getFloat("Price Field", priceField.getText()),
                descriptionField.getText(), UtilityProvider.getInt("Type ID Field", typeField.getText()),
                UtilityProvider.getFloat("Discount Field", discountField.getText()),
                availabilityField.getText(), UtilityProvider.getFloat("Cost Field", inventoryField.getText()),
                UtilityProvider.getInt("Stock Field", stockField.getText()), path
        );
        window.close();
    }

    String path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/Food/default.png";
    public void uploadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Taqui\\IdeaProjects\\POS\\src\\resource"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            path = selectedFile.toURI().toString();
            photo.setImage(new Image(path));
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You haven't selected anything!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idField.setEditable(false);
    }

}
