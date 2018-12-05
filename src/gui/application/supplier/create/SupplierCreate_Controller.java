package gui.application.supplier.create;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataModifier;
import entity.supplier.Supplier;
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
import java.util.Date;
import java.util.ResourceBundle;

public class SupplierCreate_Controller implements Initializable {

    @FXML public JFXButton cancelBtn;

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField addressField;
    @FXML public JFXTextField contactField;
    @FXML public JFXTextField emailField;
    @FXML public JFXTextField detailsField;
    @FXML public JFXDatePicker dateField;

    private static Stage window;
    private static TableView<Supplier> tableView;
    private static int id = 1001;

    public static void showCreator(TableView<Supplier> table, int id) {
        SupplierCreate_Controller.id = id;
        tableView = table;
        window = new Stage();
        window.setTitle("New Supplier");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setScene(getCreateScene());
        window.showAndWait();
    }

    public static Scene getCreateScene() {
        Scene createScene = null;
        try {
            Parent root = FXMLLoader.load(SupplierCreate_Controller.class.getResource("SupplierCreate_GUI.fxml"));
            createScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("Supplier Create GUI could not be loaded!");
        }
        return createScene;
    }

    public void createBtnOnAction(ActionEvent event) {
        Supplier supplier = new Supplier(UtilityProvider.getInt("Supplier ID", idField.getText()),
                nameField.getText(), addressField.getText(),
                contactField.getText(), emailField.getText(), detailsField.getText(),
                UtilityProvider.getStringDate(dateField.getValue()));
        tableView.getItems().add(supplier);

        DB_DataModifier.insertSupplier(nameField.getText(), addressField.getText(),
                contactField.getText(), emailField.getText(), detailsField.getText(),
                UtilityProvider.getStringDate(dateField.getValue()));
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
        dateField.setEditable(false);
        dateField.setValue(UtilityProvider.getLocalDate(new Date()));
    }
}
