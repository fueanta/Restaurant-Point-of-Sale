package gui.application.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.order.Order;
import gui.application.dashboard.DashBoad_Controller;
import gui.application.order.order_item.OrderItem_Controller;
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

public class Order_Controller implements Initializable {


    //private static Scene orderScene = null;

    public static Scene getOrderScene() {
        Scene orderScene = null;
        try {
            Parent root = FXMLLoader.load(Order_Controller.class.getResource("Order_GUI.fxml"));
            orderScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Order GUI could not be loaded!");
        }
        return orderScene;
    }

    // table
    @FXML
    public TableView<Order> table;

    @FXML public Label totalLabel;

    // fields
    @FXML public JFXTextField idField;
    @FXML public JFXTextField userField;
    @FXML public JFXTextField discountField;
    @FXML public JFXTextField costField;
    @FXML public JFXTextField cardField;
    @FXML public JFXTextField hh;
    @FXML public JFXTextField mi;
    @FXML public JFXTextField ss;

    @FXML public JFXTextArea descriptionField;

    @FXML public JFXDatePicker dateField;

    // buttons

    @FXML public JFXButton insert;
    @FXML public JFXButton update;
    @FXML public JFXButton delete;
    @FXML public JFXButton inventory;
    @FXML public JFXButton printBtn;
    @FXML public JFXButton refresh;
    @FXML public JFXButton unselect;
    @FXML public JFXButton back;

    // columns
    @FXML
    public TableColumn<Order, Integer> id;
    @FXML
    public TableColumn<Order, String> description;
    @FXML
    public TableColumn<Order, String> time;
    @FXML
    public TableColumn<Order, String> user;
    @FXML
    public TableColumn<Order, Float> price;
    @FXML
    public TableColumn<Order, Float> discount;
    @FXML
    public TableColumn<Order, String> card;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        id.setCellValueFactory(new PropertyValueFactory<Order, Integer>
                ("id"));
        description.setCellValueFactory(new PropertyValueFactory<Order, String>
                ("description"));
        time.setCellValueFactory(new PropertyValueFactory<Order, String>
                ("place_time"));
        user.setCellValueFactory(new PropertyValueFactory<Order, String>
                ("logged_user"));
        price.setCellValueFactory(new PropertyValueFactory<Order, Float>
                ("total_cost"));
        discount.setCellValueFactory(new PropertyValueFactory<Order, Float>
                ("discount"));
        card.setCellValueFactory(new PropertyValueFactory<Order, String>
                ("card_number"));
    }

    private void tableSelectionListenner() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Order order = newValue;
                idField.setText(order.getId() + "");
                cardField.setText(order.getCard_number() + "");
                discountField.setText(order.getDiscount() + "");
                costField.setText(order.getTotal_cost() + "");
                userField.setText(order.getLogged_user());
                descriptionField.setText(order.getDescription());

                String wholeDate = order.getPlace_time();
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
        for (Order order : table.getItems()) {
            sum += order.getTotal_cost();
        }
        return sum;
    }

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
        table.setItems(DB_DataFetcher.getAllOrders());
        table.refresh();
        totalLabel.setText(generateSum() + "");
    }

    public void clear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        discountField.clear();
        descriptionField.clear();
        costField.clear();
        userField.clear();
        dateField.getEditor().clear();
        cardField.clear();
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
        cardField.setEditable(false);
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
        cardField.setEditable(true);
        discountField.setEditable(true);
        descriptionField.setEditable(true);
        costField.setEditable(true);
        userField.setEditable(true);
        dateField.getEditor().setEditable(true);
        hh.setEditable(true);
        mi.setEditable(true);
        ss.setEditable(true);
    }


    // listener for buttons
    public void insertOnAction(ActionEvent event) {
        if (insert.getText().equals("Insert")) {
            setInsertModeOn();
        }
        else if (insert.getText().equals("Save")) {
            DB_DataModifier.insertOrder(
                    descriptionField.getText(),
                    (dateField.getEditor().getText() + " " + hh.getText() + ":" + mi.getText() + ":" + ss.getText()),
                    Main.logged_user,
                    UtilityProvider.getFloat("Cost Field", costField.getText()),
                    UtilityProvider.getFloat("Discount Field", discountField.getText()),
                    cardField.getText()
                    );
            setInsertModeOff();
        }
    }

    private void insertClear() {
        table.getSelectionModel().clearSelection();
        idField.clear();
        cardField.clear();
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
    Order copyOrder = null, actualOrder = null;

    public void updateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            if (update.getText().equals("Update")) {
                actualOrder = table.getSelectionModel().getSelectedItem();
                copyOrder = Order.copyObject(actualOrder);

                setUpdateModeOn();
            }
            else if (update.getText().equals("Save")) {
                String updateQuery = Order.update_Changes(copyOrder, getUpdatedObject());
                if (updateQuery.equals("")) AlertBox_Controller.showAlert("No Changes!", "No changes could be found!");
                else {
                    //System.out.println(updateQuery);
                    DB_DataModifier.updateAnyTable(updateQuery);
                }
                copyOrder = null; actualOrder = null;
                setUpdateModeOff();
            }
        }
        else {
            AlertBox_Controller.showAlert("No selection!", "You must select an order before updating!");
        }
    }

    // get Updated Object
    public Order getUpdatedObject() {
        return new Order(
                UtilityProvider.getInt("ID Field", idField.getText()),
                descriptionField.getText(),
                (dateField.getEditor().getText() + " " + hh.getText() + ":" + mi.getText() + ":" + ss.getText()),
                Main.logged_user,
                UtilityProvider.getFloat("Cost Field", costField.getText()),
                UtilityProvider.getFloat("Discount Field", discountField.getText()),
                cardField.getText()
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
        Order order = table.getSelectionModel().getSelectedItem();
        if (order != null) {
            DB_DataModifier.deleteOrder(order.getId());
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select an order first to delete!");
        }
    }

    public void backBtnOnAction(ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    public void printOnAction(ActionEvent event) {

    }

    public void inventoryOnAction(ActionEvent event) {
        Order order = table.getSelectionModel().getSelectedItem();
        if (order != null) {
            OrderItem_Controller.loadInventory(UtilityProvider.getInt("Id Field", idField.getText()));
            refresh();
        }
        else {
            AlertBox_Controller.showAlert("No selection", "You must select an order first to get the inventory list!");
        }
    }

    public void unselectOnAction(ActionEvent event) {
        clear();
        disable();
    }

    public void refreshOnAction(ActionEvent event) {
        refresh();
    }
}
