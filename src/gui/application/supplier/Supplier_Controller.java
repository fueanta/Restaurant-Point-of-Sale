package gui.application.supplier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.supplier.Supplier;
import gui.application.dashboard.DashBoad_Controller;
import gui.application.supplier.create.SupplierCreate_Controller;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Supplier_Controller implements Initializable {

    // table itself
    @FXML public javafx.scene.control.TableView<Supplier>
            table;

    // table columns
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, Integer>
            id;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            name;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            address;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            contact_number;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            email;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            details;
    @FXML public javafx.scene.control.TableColumn<entity.supplier.Supplier, String>
            date_added;

    // fields of the page
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField addressField;
    @FXML public JFXTextField contactField;
    @FXML public JFXTextField emailField;
    @FXML public JFXTextField detailsField;
    @FXML public JFXDatePicker dateField;

    // buttons on the page
    @FXML public JFXButton insertBtn;
    @FXML public JFXButton updateBtn;
    @FXML public JFXButton deleteBtn;
    @FXML public JFXButton backBtn;


    // binding table and textfield
    private void bindTableWithFields() throws Exception {
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            // unbinding
            if (oldValue != null) {
                idField.textProperty().unbindBidirectional(oldValue.idProperty());
                nameField.textProperty().unbindBidirectional(oldValue.nameProperty());
                addressField.textProperty().unbindBidirectional(oldValue.addressProperty());
                contactField.textProperty().unbindBidirectional(oldValue.contact_numberProperty());
                emailField.textProperty().unbindBidirectional(oldValue.emailProperty());
                detailsField.textProperty().unbindBidirectional(oldValue.detailsProperty());
                dateField.getEditor().textProperty().unbindBidirectional(oldValue.date_addedProperty());
            }

            //binding
            if (newValue != null) {
                //idField.textProperty().bindBidirectional(newValue.idProperty(), new NumberStringConverter());
                Bindings.bindBidirectional(idField.textProperty(), newValue.idProperty(), new NumberStringConverter());
                nameField.textProperty().bindBidirectional(newValue.nameProperty());
                addressField.textProperty().bindBidirectional(newValue.addressProperty());
                contactField.textProperty().bindBidirectional(newValue.contact_numberProperty());
                emailField.textProperty().bindBidirectional(newValue.emailProperty());
                detailsField.textProperty().bindBidirectional(newValue.detailsProperty());
                dateField.getEditor().textProperty().bindBidirectional(newValue.date_addedProperty());
            }

        });
    }

    // observable list for dataset
    public javafx.collections.ObservableList<Supplier> list;

    // Method that generates data
    private void putDataOnList() {
        list = DB_DataFetcher.getAllSuppliers();
    }

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>
                ("id"));
        name.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("name"));
        address.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("address"));
        contact_number.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("contact_number"));
        email.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("email"));
        details.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("details"));
        date_added.setCellValueFactory(new PropertyValueFactory<Supplier, String>
                ("date_added"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        putDataOnList();
        setCellValueFactories();
        table.setItems(list);

        try {
            bindTableWithFields();
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }

        disable();
    }

    // enable, disable, clear
    public void enable() {
        idField.setEditable(true);
        nameField.setEditable(true);
        addressField.setEditable(true);
        contactField.setEditable(true);
        emailField.setEditable(true);
        detailsField.setEditable(true);
        dateField.setDisable(false);
    }

    public void disable() {
        idField.setEditable(false);
        nameField.setEditable(false);
        addressField.setEditable(false);
        contactField.setEditable(false);
        emailField.setEditable(false);
        detailsField.setEditable(false);
        dateField.setDisable(true);
    }

    public void clear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        nameField.clear();
        addressField.clear();
        contactField.clear();
        emailField.clear();
        detailsField.clear();
        dateField.getEditor().clear();
    }

    // method that creates supplier gui
    public static Scene getSupplierScene() {
        Scene supplierScene = null;
        try {
            Parent root = FXMLLoader.load(Supplier_Controller.class.getResource("Supplier_GUI.fxml"));
            supplierScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return supplierScene;
    }

    // listener for buttons

    public void backBtnOnAction(ActionEvent event) {
        if (updateBtn.getText().equals("Save")) setUpdateModeOff();
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    public void insertBtnOnAction(ActionEvent event) {
        clear();

        int tableSize = table.getItems().size(), newId = 1001;
        if (tableSize != 0) newId = table.getItems().get(tableSize - 1).getId() + 1;

        SupplierCreate_Controller.showCreator(table, newId);
    }

    Supplier actualItem = null, copyItem = null;
    public void updateBtnOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {

            if (updateBtn.getText().equals("Update")) {
                actualItem = table.getSelectionModel().getSelectedItem();
                copyItem = Supplier.copyObject(actualItem);
                setUpdateModeOn();
            }
            else if (updateBtn.getText().equals("Save")) {
                String updateQuery = Supplier.update_Changes(copyItem, actualItem);
                if (updateQuery.equals("")) AlertBox_Controller.showAlert("No Changes!", "No changes have been made!");
                else {
                    System.out.println(updateQuery);
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

    // update button tweaks
    public void setUpdateModeOn() {
        updateBtn.setText("Save");
        updateBtn.setDefaultButton(true);
        insertBtn.setDisable(true);
        deleteBtn.setDisable(true);

        enable();
    }
    public void setUpdateModeOff() {
        updateBtn.setText("Update");
        updateBtn.setDefaultButton(false);
        insertBtn.setDisable(false);
        deleteBtn.setDisable(false);

        disable();
        clear();
    }

    public void deleteBtnOnAction(ActionEvent event) {
        Supplier supplier = table.getSelectionModel().getSelectedItem();
        if (supplier != null) {
            table.getItems().remove(supplier);
            DB_DataModifier.deleteSupplier(supplier.getId());
            clear();
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select a supplier before deleting!");
        }
    }

    public void unselectOnAction(ActionEvent event) {
        clear();
        disable();
    }

    public void refreshOnAction(ActionEvent event) {
        refresh();
    }

    public void refresh() {
        idField.setDisable(true);
        clear();
        table.setItems(DB_DataFetcher.getAllSuppliers());
        table.refresh();
    }

}
