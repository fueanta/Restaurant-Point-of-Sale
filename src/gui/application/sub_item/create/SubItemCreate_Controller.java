package gui.application.sub_item.create;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataModifier;
import entity.sub_item.SubItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resource.utility.UtilityProvider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubItemCreate_Controller implements Initializable {

    @FXML
    public JFXButton cancelBtn;

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField costField;
    @FXML public JFXTextField countField;
    @FXML public JFXTextArea descriptionField;

    private static Stage window;
    private static TableView<SubItem> tableView;
    private static int id = 0;

    public static void showCreator(TableView<SubItem> table, int id) {
        SubItemCreate_Controller.id = id;
        tableView = table;
        window = new Stage();
        window.setTitle("New Inventory Item");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setScene(getCreateScene());
        window.showAndWait();
    }

    public static Scene getCreateScene() {
        Scene createScene = null;
        try {
            Parent root = FXMLLoader.load(SubItemCreate_Controller.class.getResource("SubItemCreate_GUI.fxml"));
            createScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("SubItem Create GUI could not be loaded!");
        }
        return createScene;
    }

    public void createBtnOnAction(ActionEvent event) {
        SubItem subItem = new SubItem(UtilityProvider.getInt("Item ID", idField.getText()), nameField.getText(),
                descriptionField.getText(), UtilityProvider.getFloat("Unit Cost", costField.getText()),
                UtilityProvider.getInt("Inventory Count", countField.getText()));
        tableView.getItems().add(subItem);
        DB_DataModifier.insertSubItem(nameField.getText(),
                descriptionField.getText(), UtilityProvider.getFloat("Unit Cost", costField.getText()),
                UtilityProvider.getInt("Inventory Count", countField.getText()));
        /*
        CorrectBox_Controller.showCorrect("Supplier Inserted!", "A new supplier ID: " + supplier.getId() +
                " has been added.");
                */
        window.close();
    }

    public void cancelBtnOnAction(ActionEvent event) {
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idField.setText(String.valueOf(id));
        idField.setEditable(false);
    }
}