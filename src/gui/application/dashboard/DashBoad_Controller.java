package gui.application.dashboard;

import com.jfoenix.controls.JFXButton;
import database.DB_DataModifier;
import gui.application.admin_view.Admin_Controller;
import gui.application.login.Login_Controller;
import gui.application.main_item.MainItem_Controller;
import gui.application.order.Order_Controller;
import gui.application.purchase.Purchase_Controller;
import gui.application.sub_item.SubItem_Controller;
import gui.application.supplier.Supplier_Controller;
import gui.application.user.User_Controller;
import gui.generic.errorbox.ErrorBox_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoad_Controller implements Initializable{

    //private static Scene dashScene = null;
    private static String userType = "";

    public static Scene getDashScene(String type) {
        Scene dashScene = null;
        DashBoad_Controller.userType = type;
        try {
            Parent root = FXMLLoader.load(DashBoad_Controller.class.getResource("DashBoard_GUI.fxml"));
            dashScene = new Scene(root);
        } catch (IOException e) {
            ErrorBox_Controller.showError("Load ERROR!", "Dashboard couldn't be loaded!");
        }
        return dashScene;
    }

    public void supplierOnAction(ActionEvent event) {
        Main.window.setTitle("Suppliers");
        Main.window.hide();
        Main.window.setScene(Supplier_Controller.getSupplierScene());
        Main.window.show();
    }

    public void userOnAction(ActionEvent event) {
        Main.window.setTitle("Users");
        Main.window.hide();
        Main.window.setScene(User_Controller.getUserScene());
        Main.window.show();
    }

    public void orderOnAction(ActionEvent event) {
        Main.window.setTitle("Orders");
        Main.window.hide();
        Main.window.setScene(Order_Controller.getOrderScene());
        Main.window.show();
    }

    public void purchaseOnAction(ActionEvent event) {
        Main.window.setTitle("Purchases");
        Main.window.hide();
        Main.window.setScene(Purchase_Controller.getPurchaseScene());
        Main.window.show();
    }

    public void foodOnAction(ActionEvent event) {
        Main.window.setTitle("Food / Main Items");
        Main.window.hide();
        Main.window.setScene(MainItem_Controller.getItemScene());
        Main.window.show();
    }

    public void inventoryOnAction(ActionEvent event) {
        Main.window.setTitle("Inventory / Sub Items");
        Main.window.hide();
        Main.window.setScene(SubItem_Controller.getSubItemScene());
        Main.window.show();
    }

    public void logOutOnAction(ActionEvent event) {
        userLabel.setText("");
        DB_DataModifier.updateLogout(Main.logged_user);
        Main.logged_user = "";
        Main.user_type = "";
        Main.window.setTitle("Login");
        Main.window.hide();
        Main.window.setScene(Login_Controller.getLoginScene());
        Main.window.show();
    }

    @FXML
    public Label userLabel;
    @FXML
    public JFXButton usr, admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(userType);
        if (Main.user_type != "ADMIN") {usr.setDisable(true); admin.setDisable(true);}
    }

    public void adminOnaction(ActionEvent event) {
        Main.window.setTitle("Information");
        Main.window.hide();
        Main.window.setScene(Admin_Controller.getSubItemScene());
        Main.window.show();
    }
}
