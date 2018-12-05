package gui.application.sub_item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.sub_item.SubItem;
import gui.application.dashboard.DashBoad_Controller;
import gui.application.sub_item.create.SubItemCreate_Controller;
import gui.generic.alertbox.AlertBox_Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubItem_Controller implements Initializable {

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField nameField;
    @FXML public JFXTextField costField;
    @FXML public JFXTextArea descriptionField;
    // spinner
    @FXML public Spinner<Integer> countSpinner;

    public void selectSpinner(MouseEvent event) {
        SpinnerValueFactory factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000,
                table.getSelectionModel().getSelectedItem().getCount());
        countSpinner.setValueFactory(factory);
    }

    // buttons
    @FXML public JFXButton insertBtn;
    @FXML public JFXButton updateBtn;
    @FXML public JFXButton deleteBtn;
    @FXML public JFXButton backBtn;

    // listeners for buttons
    public void insertBtnOnAction(ActionEvent event) {
        clear();

        int tableSize = table.getItems().size(), newId = 1001;
        if (tableSize != 0) newId = table.getItems().get(tableSize - 1).getId() + 1;

        SubItemCreate_Controller.showCreator(table, newId);
    }

    SubItem actualItem = null, copyItem = null;
    public void updateBtnOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {

            if (updateBtn.getText().equals("Update")) {
                actualItem = table.getSelectionModel().getSelectedItem();
                copyItem = SubItem.copyObject(actualItem);
                setUpdateModeOn();
            }
            else if (updateBtn.getText().equals("Save")) {
                String updateQuery = SubItem.update_Changes(copyItem, actualItem);
                if (updateQuery.equals("")) AlertBox_Controller.showAlert("No Changes!", "No changes have been made!");
                else {
                    //System.out.println(updateQuery);
                    DB_DataModifier.updateAnyTable(updateQuery);
                }
                copyItem = null; actualItem = null;
                setUpdateModeOff();
            }
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select an Item before updating!");
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
        SubItem item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            table.getItems().remove(item);
            DB_DataModifier.deleteSubItem(item.getId());
            clear();
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select an Item before deleting!");
        }
    }

    public void backBtnOnAction(ActionEvent event) {
        if (updateBtn.getText().equals("Save")) setUpdateModeOff();
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    // tableView
    @FXML public TableView<SubItem> table;

    // table columns
    @FXML public TableColumn<SubItem, Integer> id;
    @FXML public TableColumn<SubItem, String> name;
    @FXML public TableColumn<SubItem, String> description;
    @FXML public TableColumn<SubItem, Float> unit_cost;
    @FXML public TableColumn<SubItem, Integer> count;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<SubItem, Integer>
                ("id"));
        name.setCellValueFactory(new PropertyValueFactory<SubItem, String>
                ("name"));
        description.setCellValueFactory(new PropertyValueFactory<SubItem, String>
                ("description"));
        unit_cost.setCellValueFactory(new PropertyValueFactory<SubItem, Float>
                ("unit_cost"));
        count.setCellValueFactory(new PropertyValueFactory<SubItem, Integer>
                ("count"));
    }

    // listener for the table
    private void bindTableWithFields() {
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            // unbinding
            if (oldValue != null) {
                idField.textProperty().unbindBidirectional(oldValue.idProperty());
                nameField.textProperty().unbindBidirectional(oldValue.nameProperty());
                descriptionField.textProperty().unbindBidirectional(oldValue.descriptionProperty());
                costField.textProperty().unbindBidirectional(oldValue.unit_costProperty());

                countSpinner.getEditor().textProperty().unbindBidirectional(oldValue.countProperty());
            }

            //binding
            if (newValue != null) {
                idField.textProperty().bindBidirectional(newValue.idProperty(), new NumberStringConverter());
                nameField.textProperty().bindBidirectional(newValue.nameProperty());
                descriptionField.textProperty().bindBidirectional(newValue.descriptionProperty());
                costField.textProperty().bindBidirectional(newValue.unit_costProperty(), new NumberStringConverter());

                countSpinner.getEditor().textProperty().bindBidirectional(newValue.countProperty(),
                        new NumberStringConverter());
            }

        });
    }

    // observable list for dataset
    public ObservableList<SubItem> list;

    // Method that generates data
    private void putDataOnList() {
        list = DB_DataFetcher.getAllSubitems();
    }

    // Method that passes the scene
    public static Scene getSubItemScene() {
        Scene subItemScene = null;
        try {
            Parent root = FXMLLoader.load(SubItem_Controller.class.getResource("SubItem_GUI.fxml"));
            subItemScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sub Item GUI could not be loaded!");
        }
        return subItemScene;
    }

    // enable, disable, clear
    public void enable() {
        idField.setEditable(true);
        nameField.setEditable(true);
        costField.setEditable(true);
        descriptionField.setEditable(true);
        countSpinner.setDisable(false);
    }

    public void disable() {
        idField.setEditable(false);
        nameField.setEditable(false);
        costField.setEditable(false);
        descriptionField.setEditable(false);
        countSpinner.setDisable(true);
    }

    public void clear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        nameField.clear();
        costField.clear();
        descriptionField.clear();
        countSpinner.getEditor().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disable();
        putDataOnList();
        setCellValueFactories();
        table.setItems(list);
        bindTableWithFields();
    }

    public void refreshOnAction(ActionEvent event) {
        table.setItems(DB_DataFetcher.getAllSubitems());
        table.refresh();
    }

    public void unselectOnAction(ActionEvent event) {
        clear();
    }

}
