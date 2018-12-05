package gui.application.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.user.User;
import gui.application.dashboard.DashBoad_Controller;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.Main;
import resource.utility.UtilityProvider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class User_Controller implements Initializable {

    // default
    private static String path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/User/usr.png";
    private static String default_path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/User/usr.png";

    // method that creates user gui
    public static Scene getUserScene() {
        Scene userScene = null;
        try {
            Parent root = FXMLLoader.load(User_Controller.class.getResource("User_GUI.fxml"));
            userScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("User GUI could not be loaded!");
        }
        return userScene;
    }

    // buttons
    @FXML public JFXButton insert;
    @FXML public JFXButton update;
    @FXML public JFXButton delete;
    @FXML public JFXButton upload;

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField usrField;
    @FXML public JFXTextField passField;
    @FXML public JFXTextField accessField;
    @FXML public JFXTextField salaryField;
    @FXML public JFXTextField commField;

    // date picker
    @FXML public JFXDatePicker dateField;

    // area
    @FXML public JFXTextArea descriptionField;

    // Image View
    @FXML public ImageView photo;

    // table
    @FXML public TableView<User> table;

    // table columns
    @FXML public TableColumn<User, Integer> id;
    @FXML public TableColumn<User, String> username;
    @FXML public TableColumn<User, String> password;
    @FXML public TableColumn<User, String> admin_access;
    @FXML public TableColumn<User, String> description;
    @FXML public TableColumn<User, Float> salary;
    @FXML public TableColumn<User, Float> commission;
    @FXML public TableColumn<User, String> hiredate;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<User, Integer>
                ("id"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>
                ("username"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>
                ("password"));
        admin_access.setCellValueFactory(new PropertyValueFactory<User, String>
                ("admin_access"));
        description.setCellValueFactory(new PropertyValueFactory<User, String>
                ("description"));
        salary.setCellValueFactory(new PropertyValueFactory<User, Float>
                ("salary"));
        commission.setCellValueFactory(new PropertyValueFactory<User, Float>
                ("commission"));
        hiredate.setCellValueFactory(new PropertyValueFactory<User, String>
                ("hire_date"));
    }

    private void tableSelectionListenner() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                User user = table.getSelectionModel().getSelectedItem();
                idField.setText(user.getId() + "");
                usrField.setText(user.getUsername());
                passField.setText(user.getPassword());
                accessField.setText(user.getAdmin_access());
                salaryField.setText(user.getSalary() + "");
                commField.setText(user.getCommission() + "");
                descriptionField.setText(user.getDescription());
                dateField.getEditor().setText(user.getHire_date());

                String myString = user.getPicture();
                if (myString != null && !myString.equals("")) {
                    Image picture = new Image(myString);
                    photo.setImage(picture);
                }
                else {
                    Image picture = new Image(path);
                    photo.setImage(picture);
                }

            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactories();
        tableSelectionListenner();
        refresh();
    }

    private void refresh() {
        idField.setDisable(true);
        clear();
        table.setItems(DB_DataFetcher.getAllUsers());
        table.refresh();
    }

    public void clear() {

        table.getSelectionModel().clearSelection();
        idField.clear();
        usrField.clear();
        passField.clear();
        accessField.clear();
        descriptionField.clear();
        salaryField.clear();
        commField.clear();
        dateField.getEditor().clear();
        photo.setImage(new Image(default_path));

        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        upload.setDisable(true);

        disable();
    }

    private void enable() {
        //idField.setEditable(true);
        usrField.setEditable(true);
        passField.setEditable(true);
        accessField.setEditable(true);
        descriptionField.setEditable(true);
        salaryField.setEditable(true);
        commField.setEditable(true);
        dateField.setDisable(false);
    }

    private void disable() {
        //idField.setEditable(true);
        usrField.setEditable(false);
        passField.setEditable(false);
        accessField.setEditable(false);
        descriptionField.setEditable(false);
        salaryField.setEditable(false);
        commField.setEditable(false);
        dateField.setDisable(true);
    }

    public void backOnAction(ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    public void refreshOnAction(ActionEvent event) {
        refresh();
    }

    public void unselectOnAction(ActionEvent event) {
        clear();
        disable();
    }

    public void printOnAction(ActionEvent event) {

    }

    public void uploadOnAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\Taqui\\IdeaProjects\\POS\\src\\resource"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
        );
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            path = selectedFile.toURI().toString();
            photo.setImage(new Image(path));
            //path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/User/usr.png";
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You haven't selected anything!");
        }
    }

    public void insertOnAction(ActionEvent event) {
        if (insert.getText().equals("Insert")) {
            setInsertModeOn();
        }
        else if (insert.getText().equals("Save")) {
            DB_DataModifier.insertUser(
                    usrField.getText(),
                    passField.getText(),
                    accessField.getText(),
                    descriptionField.getText(),
                    UtilityProvider.getFloat("Salary Field", salaryField.getText()),
                    UtilityProvider.getFloat("Commission Field", commField.getText()),
                    dateField.getEditor().getText(),
                    path
            );
            setInsertModeOff();
        }
    }

    private void insertClear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        usrField.clear();
        passField.clear();
        accessField.clear();
        descriptionField.clear();
        salaryField.clear();
        commField.clear();
        dateField.getEditor().clear();
        photo.setImage(new Image(default_path));
    }

    // update button tweaks
    public void setInsertModeOn() {
        insert.setText("Save");
        insert.setDefaultButton(true);
        update.setDisable(true);
        delete.setDisable(true);
        table.setDisable(true);
        upload.setDisable(false);
        insertClear();
        dateField.setValue(UtilityProvider.getLocalDate(new Date()));
        enable();
    }
    public void setInsertModeOff() {
        insert.setText("Insert");
        insert.setDefaultButton(false);
        update.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        upload.setDisable(true);

        refresh();
    }

    // trace changes
    User copyItem = null, actualItem = null;

    public void updateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (update.getText().equals("Update")) {
                actualItem = table.getSelectionModel().getSelectedItem();
                copyItem = User.copyObject(actualItem);
                String myString = actualItem.getPicture();
                if (myString != null && !myString.equals("")) {
                    path = actualItem.getPicture();
                }
                else {
                    //path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/Food/default.png";
                    copyItem.setPicture(path);
                }
                setUpdateModeOn();
            }
            else if (update.getText().equals("Save")) {
                String updateQuery = User.update_Changes(copyItem, getUpdatedObject());
                if (updateQuery.equals("")) AlertBox_Controller.showAlert("No Changes!", "No changes could be found!");
                else {
                    //System.out.println(updateQuery);
                    DB_DataModifier.updateAnyTable(updateQuery);
                }
                copyItem = null; actualItem = null;
                setUpdateModeOff();
            }
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select a supplier before updating!");
        }
    }

    // get Updated Object
    public User getUpdatedObject() {
        return new User(
                UtilityProvider.getInt("ID Field", idField.getText()),
                usrField.getText(),
                passField.getText(),
                accessField.getText(),
                descriptionField.getText(),
                UtilityProvider.getFloat("Salary Field", salaryField.getText()),
                UtilityProvider.getFloat("Commission Field", commField.getText()),
                dateField.getEditor().getText(),
                path
        );
    }

    // update button tweaks
    public void setUpdateModeOn() {
        update.setText("Save");
        update.setDefaultButton(true);
        insert.setDisable(true);
        delete.setDisable(true);
        table.setDisable(true);
        upload.setDisable(false);

        enable();
    }
    public void setUpdateModeOff() {
        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        upload.setDisable(true);

        refresh();
    }

    public void deleteOnAction(ActionEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            DB_DataModifier.deleteUser(user.getId());
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select a user first to delete!");
        }
    }

    //
}
