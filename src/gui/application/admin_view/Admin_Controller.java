package gui.application.admin_view;

import database.DB_DataFetcher;
import entity.main_item.MainItem;
import gui.application.dashboard.DashBoad_Controller;
import javafx.collections.ObservableList;
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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Admin_Controller implements Initializable {
    @FXML public TableView<MainItem> table;
    @FXML public TableColumn<MainItem, String> name;
    @FXML public TableColumn<MainItem, Float> cost;

    @FXML public TableView<MainItem> table2;
    @FXML public TableColumn<MainItem, String> item2;
    @FXML public TableColumn<MainItem, Float> cost2;
    @FXML public TableColumn<MainItem, Float> sale2;
    @FXML public TableColumn<MainItem, Float> profit2;

    private void setCellValueFactories1() {
        name.setCellValueFactory(new PropertyValueFactory<MainItem, String>
                ("name"));
        cost.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("price"));
    }

    private void setCellValueFactories2() {
        item2.setCellValueFactory(new PropertyValueFactory<MainItem, String>
                ("name"));
        cost2.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("price"));
        sale2.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("discount_rate"));
        profit2.setCellValueFactory(new PropertyValueFactory<MainItem, Float>
                ("inventory_cost"));
    }

    // observable list for dataset
    public ObservableList<MainItem> list;
    public ObservableList<MainItem> list2;

    // Method that generates data
    private void putDataOnList1() {
        list = DB_DataFetcher.getTopSuppliers();
        list2 = DB_DataFetcher.getDailySale();
    }

    // Method that passes the scene
    public static Scene getSubItemScene() {
        Scene adminScene = null;
        try {
            Parent root = FXMLLoader.load(Admin_Controller.class.getResource("Admin_GUI.fxml"));
            adminScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adminScene;
    }

    public float generateSum() {
        float sum = 0;
        for (MainItem item : table2.getItems()) {
            sum += item.getInventory_cost();
        }
        return sum;
    }
    public void backOnAction(ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }

    @FXML public Label total;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactories1();
        setCellValueFactories2();
        putDataOnList1();
        table.setItems(list);
        table2.setItems(list2);

        total.setText("" + generateSum());
    }

    public void backOnAction(javafx.event.ActionEvent event) {
        Main.window.setTitle("Dashboard");
        Main.window.hide();
        Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
        Main.window.show();
    }
}
