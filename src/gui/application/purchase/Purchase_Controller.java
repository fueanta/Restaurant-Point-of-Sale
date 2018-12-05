package gui.application.purchase;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.purchase.Purchase;
import gui.application.dashboard.DashBoad_Controller;
import gui.application.purchase.purchase_item.PurchaseItem_Controller;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import resource.utility.UtilityProvider;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Purchase_Controller implements Initializable {


    // method that creates purchase gui
    public static Scene getPurchaseScene() {
        Scene purchaseScene = null;
        try {
            Parent root = FXMLLoader.load(Purchase_Controller.class.getResource("Purchase_GUI.fxml"));
            purchaseScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Purchase GUI could not be loaded!");
        }
        return purchaseScene;
    }

    // buttons
    @FXML public JFXButton insert;
    @FXML public JFXButton update;
    @FXML public JFXButton delete;
    @FXML public JFXButton inventory;
    @FXML public JFXButton printBtn;
    @FXML public JFXButton backBtn;
    @FXML public JFXButton refreshBtn;

    // listener for buttons
    public void insertOnAction(ActionEvent event) {
        if (insert.getText().equals("Insert")) {
            setInsertModeOn();
        }
        else if (insert.getText().equals("Save")) {
            DB_DataModifier.insertPurchase(
                    descriptionField.getText(),
                    (dateField.getEditor().getText() + " " + hh.getText() + ":" + mi.getText() + ":" + ss.getText()),
                    Main.logged_user,
                    UtilityProvider.getInt("Supplier Field", supplierField.getText()),
                    UtilityProvider.getFloat("Cost Field", costField.getText()),
                    UtilityProvider.getFloat("Discount Field", discountField.getText())
            );
            setInsertModeOff();
        }
    }

    private void insertClear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        supplierField.clear();
        discountField.clear();
        descriptionField.clear();
        costField.clear();
        userField.clear();
        dateField.getEditor().clear();
        hh.clear();
        mi.clear();
        ss.clear();
    }

    // update button tweaks
    public void setInsertModeOn() {
        insert.setText("Save");
        insert.setDefaultButton(true);
        update.setDisable(true);
        delete.setDisable(true);
        inventory.setDisable(true);
        table.setDisable(true);
        insertClear();

        Date date = new Date();
        dateField.setValue(UtilityProvider.getLocalDate(date));

        String wholeDate = UtilityProvider.getStringDate(date);
        String hour = wholeDate.substring(11,13),
                min = wholeDate.substring(14,16), sec = wholeDate.substring(17);

        hh.setText(hour); mi.setText(min); ss.setText(sec);

        enable();
    }

    public void setInsertModeOff() {
        insert.setText("Insert");
        insert.setDefaultButton(false);
        update.setDisable(false);
        delete.setDisable(false);
        inventory.setDisable(false);
        table.setDisable(false);

        refresh();
    }

    // trace changes
    Purchase copyPurchase = null, actualPurchase = null;

    public void updateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (update.getText().equals("Update")) {
                actualPurchase = table.getSelectionModel().getSelectedItem();
                copyPurchase = Purchase.copyObject(actualPurchase);

                setUpdateModeOn();
            }
            else if (update.getText().equals("Save")) {
                String updateQuery = Purchase.update_Changes(copyPurchase, getUpdatedObject());
                if (updateQuery.equals("")) AlertBox_Controller.showAlert("No Changes!", "No changes could be found!");
                else {
                    //System.out.println(updateQuery);
                    DB_DataModifier.updateAnyTable(updateQuery);
                }
                copyPurchase = null; actualPurchase = null;
                setUpdateModeOff();
            }
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select a purchase before updating!");
        }
    }

    // get Updated Object
    public Purchase getUpdatedObject() {
        return new Purchase(
                UtilityProvider.getInt("ID Field", idField.getText()),
                descriptionField.getText(),
                (dateField.getEditor().getText() + " " + hh.getText() + ":" + mi.getText() + ":" + ss.getText()),
                Main.logged_user,
                UtilityProvider.getInt("Supplier Field", supplierField.getText()),
                UtilityProvider.getFloat("Cost Field", costField.getText()),
                UtilityProvider.getFloat("Discount Field", discountField.getText())
        );
    }

    // update button tweaks
    public void setUpdateModeOn() {
        update.setText("Save");
        update.setDefaultButton(true);
        insert.setDisable(true);
        delete.setDisable(true);
        table.setDisable(true);
        inventory.setDisable(false);

        enable();
    }
    public void setUpdateModeOff() {
        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        table.setDisable(false);
        inventory.setDisable(true);

        refresh();
    }

    public void deleteOnAction(ActionEvent event) {
        Purchase purchase = table.getSelectionModel().getSelectedItem();
        if (purchase != null) {
            DB_DataModifier.deletePurchase(purchase.getId());
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select a purchase first to delete!");
        }
    }

    public void printOnAction(ActionEvent event) {

    }

    public void backBtnOnAction(ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField supplierField;
    @FXML public JFXTextField discountField;
    @FXML public JFXTextField costField;
    @FXML public JFXTextField userField;

    @FXML public JFXTextField hh, mi, ss;

    // area
    @FXML public JFXTextArea descriptionField;

    // datepicker
    @FXML public JFXDatePicker dateField;

    // table
    @FXML public TableView<Purchase> table;

    // table columns
    @FXML public TableColumn<Purchase, Integer> id;
    @FXML public TableColumn<Purchase, String> description;
    @FXML public TableColumn<Purchase, String> time;
    @FXML public TableColumn<Purchase, String> user;
    @FXML public TableColumn<Purchase, Integer> supplier;
    @FXML public TableColumn<Purchase, Float> cost;
    @FXML public TableColumn<Purchase, Float> discount;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>
                ("id"));
        description.setCellValueFactory(new PropertyValueFactory<Purchase, String>
                ("description"));
        time.setCellValueFactory(new PropertyValueFactory<Purchase, String>
                ("time"));
        user.setCellValueFactory(new PropertyValueFactory<Purchase, String>
                ("logged_user"));
        supplier.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>
                ("supp_id"));
        cost.setCellValueFactory(new PropertyValueFactory<Purchase, Float>
                ("total_cost"));
        discount.setCellValueFactory(new PropertyValueFactory<Purchase, Float>
                ("discount_rate"));
    }

    private void tableSelectionListenner() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Purchase purchase = newValue;
                idField.setText(purchase.getId() + "");
                supplierField.setText(purchase.getSupp_id() + "");
                discountField.setText(purchase.getDiscount_rate() + "");
                costField.setText(purchase.getTotal_cost() + "");
                userField.setText(purchase.getLogged_user());
                descriptionField.setText(purchase.getDescription());

                String wholeDate = purchase.getTime();
                String date = wholeDate.substring(0,2) + "/" +
                        wholeDate.substring(3,5) + "/" +
                        wholeDate.substring(6,10),
                hour = wholeDate.substring(11,13),
                min = wholeDate.substring(14,16), sec = wholeDate.substring(17);

                dateField.getEditor().setText(date);
                hh.setText(hour); mi.setText(min); ss.setText(sec);
            }
        });
    }

    public float generateSum() {
        float sum = 0;
        for (Purchase purchase : table.getItems()) {
            sum += purchase.getTotal_cost();
        }
        return sum;
    }
    @FXML public Label totalLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactories();
        tableSelectionListenner();
        refresh();

        totalLabel.setText(generateSum() + "");
    }

    private void refresh() {
        idField.setDisable(true);
        clear();
        table.setItems(DB_DataFetcher.getAllPurchases());
        table.refresh();
        totalLabel.setText(generateSum() + "");
    }

    public void clear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        supplierField.clear();
        discountField.clear();
        descriptionField.clear();
        costField.clear();
        userField.clear();
        dateField.getEditor().clear();
        hh.clear();
        mi.clear();
        ss.clear();

        update.setText("Update");
        update.setDefaultButton(false);
        insert.setDisable(false);
        delete.setDisable(false);
        inventory.setDisable(true);
        table.setDisable(false);

        disable();
    }

    private void disable() {
        //idField.setEditable(false);
        supplierField.setEditable(false);
        discountField.setEditable(false);
        descriptionField.setEditable(false);
        costField.setEditable(false);
        userField.setEditable(false);
        dateField.getEditor().setEditable(false);
        hh.setEditable(false);
        mi.setEditable(false);
        ss.setEditable(false);
    }

    private void enable() {
        //idField.setEditable(false);
        supplierField.setEditable(true);
        discountField.setEditable(true);
        descriptionField.setEditable(true);
        costField.setEditable(true);
        userField.setEditable(true);
        dateField.getEditor().setEditable(true);
        hh.setEditable(true);
        mi.setEditable(true);
        ss.setEditable(true);
    }

    public void unselectOnAction(ActionEvent event) {
        setInsertModeOff();
        setUpdateModeOff();
    }

    public void refreshOnAction(ActionEvent event) {
        refresh();
    }

    public void inventoryOnAction(ActionEvent event) {
        Purchase purchase = table.getSelectionModel().getSelectedItem();
        if (purchase != null) {
            PurchaseItem_Controller.loadInventory(UtilityProvider.getInt("Id Field", idField.getText()));
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select a purchase first to get the inventory list!");
        }
    }

}
