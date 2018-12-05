package gui.application.main_item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.main_item.MainItem;
import gui.application.dashboard.DashBoad_Controller;
import gui.application.main_item.create.MainItemCreate_Controller;
import gui.application.main_item.main_sub.MainSub_Controller;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.ResourceBundle;

public class MainItem_Controller implements Initializable {

    // default pic path
    String path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/Food/default.png";

    // scene for MainItem gui
    //private static Scene itemScene = null;

    // method that creates MainItem gui
    public static Scene getItemScene() {
        Scene itemScene = null;
        try {
            Parent root = FXMLLoader.load(MainItem_Controller.class.getResource("MainItem_GUI.fxml"));
            itemScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Main Item GUI could not be loaded!");
        }
        return itemScene;
    }

    // text fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField priceField;
    @FXML public JFXTextField discountField;
    @FXML public JFXTextField typeField;
    @FXML public JFXTextField availabilityField;
    @FXML public JFXTextField inventoryField;
    @FXML public JFXTextField stockField;

    // text area
    @FXML public JFXTextArea descriptionField;

    // image view
    @FXML public ImageView photo;

    // buttons
    @FXML public JFXButton insert;
    @FXML public JFXButton update;
    @FXML public JFXButton delete;
    @FXML public JFXButton inventory;
    @FXML public JFXButton upload;
    @FXML public JFXButton back;
    @FXML public JFXButton print;
    @FXML public JFXButton refresh;

    // buuton listeners
    public void insertOnAction(ActionEvent event) {
        MainItemCreate_Controller.showCreator();
        refresh();
    }

    // trace changes
    MainItem copyItem = null, actualItem = null;

    public void updateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (update.getText().equals("Update")) {
                actualItem = table.getSelectionModel().getSelectedItem();
                copyItem = MainItem.copyObject(actualItem);
                String myString = actualItem.getPicture();
                if (myString != null && !myString.equals("")) {
                    path = actualItem.getPicture();
                }
                else {
                    path = "file:/C:/Users/Taqui/IdeaProjects/POS/src/resource/Food/default.png";
                    copyItem.setPicture(path);
                }
                setUpdateModeOn();
            }
            else if (update.getText().equals("Save")) {
                String updateQuery = MainItem.update_Changes(copyItem, getUpdatedObject());
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
            AlertBox_Controller.showAlert("No selection!", "You must select an item before updating!");
        }
    }

    // get Updated Object
    public MainItem getUpdatedObject() {
        return new MainItem(
                UtilityProvider.getInt("ID Field", idField.getText()),
                nameField.getText(),
                UtilityProvider.getFloat("Price Field", priceField.getText()),
                descriptionField.getText(),
                UtilityProvider.getInt("Type Field", typeField.getText()),
                UtilityProvider.getFloat("Discount Field", discountField.getText()),
                availabilityField.getText(),
                UtilityProvider.getFloat("Inventory Field", inventoryField.getText()),
                UtilityProvider.getInt("Stock Field", stockField.getText()),
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
        inventory.setDisable(true);

        enable();
    }
    public void setUpdateModeOff() {
        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        upload.setDisable(true);
        inventory.setDisable(false);

        refresh();
    }

    public void deleteOnAction(ActionEvent event) {
        MainItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            DB_DataModifier.deleteMainItem(item.getId());
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select an Item first to delete!");
        }
    }

    public void inventoryOnAction(ActionEvent event) {
        MainItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            MainSub_Controller.loadInventory(UtilityProvider.getInt("Id Field", idField.getText()));
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select an Item first to get the inventory list!");
        }
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
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You haven't selected anything!");
        }
    }

    public void backOnAction(ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    public void printOnAction(ActionEvent event) {
        AlertBox_Controller.showAlert("Not Supported!", "Driver couldn't be found!");
    }

    public void refreshOnAction(ActionEvent event) {
        refresh();
    }

    public void clearOnAction(ActionEvent event) {
        clear();
    }

    // table
    @FXML public TableView<MainItem> table;

    // columns
    // table columns
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Integer>
            id;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, String>
            name;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Float>
            price;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, String>
            description;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Integer>
            type_id;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Float>
            discount_rate;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, String>
            availability;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Float>
            inventory_cost;
    @FXML public javafx.scene.control.TableColumn<entity.main_item.MainItem, Integer>
            stock_count;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<MainItem, Integer>
                ("id"));
        name.setCellValueFactory(new PropertyValueFactory<MainItem, String>
                ("name"));
        price.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("price"));
        description.setCellValueFactory(new PropertyValueFactory<MainItem, String>
                ("description"));
        type_id.setCellValueFactory(new PropertyValueFactory<MainItem, Integer>
                ("type_id"));
        discount_rate.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("discount_rate"));
        availability.setCellValueFactory(new PropertyValueFactory<MainItem, String>
                ("availability"));
        inventory_cost.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("inventory_cost"));
        stock_count.setCellValueFactory(new PropertyValueFactory<MainItem, Integer>
                ("stock_count"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactories();
        tableSelectionListenner();
        refresh();
    }

    private void tableSelectionListenner() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                MainItem item = newValue;
                idField.setText(String.valueOf(item.getId()));
                nameField.setText(item.getName());
                priceField.setText(String.valueOf(item.getPrice()));
                descriptionField.setText(item.getDescription());
                typeField.setText(String.valueOf(item.getType_id()));
                discountField.setText(String.valueOf(item.getDiscount_rate()));
                availabilityField.setText(item.getAvailability());
                inventoryField.setText(String.valueOf(item.getInventory_cost()));
                stockField.setText(String.valueOf(item.getStock_count()));

                String myString = item.getPicture();
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

    // enable, disable, clear, refresh
    private void enable() {
        //idField.setEditable(true);
        nameField.setEditable(true);
        priceField.setEditable(true);
        discountField.setEditable(true);
        descriptionField.setEditable(true);
        typeField.setEditable(true);
        availabilityField.setEditable(true);
        inventoryField.setEditable(true);
        stockField.setEditable(true);
    }

    private void disable() {
        //idField.setEditable(false);
        nameField.setEditable(false);
        priceField.setEditable(false);
        discountField.setEditable(false);
        descriptionField.setEditable(false);
        typeField.setEditable(false);
        availabilityField.setEditable(false);
        inventoryField.setEditable(false);
        stockField.setEditable(false);
    }

    public void clear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        nameField.clear();
        priceField.clear();
        discountField.clear();
        descriptionField.clear();
        typeField.clear();
        availabilityField.clear();
        inventoryField.clear();
        stockField.clear();

        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        upload.setDisable(true);
        inventory.setDisable(false);

        disable();
    }

    private void refresh() {
        idField.setDisable(true);
        clear();
        table.setItems(DB_DataFetcher.getAllMainItems());
        table.refresh();
    }

}
