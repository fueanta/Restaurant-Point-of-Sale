package gui.application.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import gui.application.dashboard.DashBoad_Controller;
import gui.generic.correctbox.CorrectBox_Controller;
import gui.generic.errorbox.ErrorBox_Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {

    //private static Scene loginScene = null;

    @FXML public BorderPane root;
    @FXML public Label connLabel;
    @FXML public JFXTextField usrField;
    @FXML public JFXPasswordField passField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadSpashSceen();
        testConn();
    }

    public static Scene getLoginScene() {
        Scene loginScene = null;
        try {
            Parent root = FXMLLoader.load(Login_Controller.class.getResource("Login_GUI.fxml"));
            loginScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("Login GUI could not be loaded!");
        }
        return loginScene;
    }

    private void testConn() {
        try {
            if (database.DB_ConnectionProvider.getConnection() != null ) {
                if (!database.DB_ConnectionProvider.getConnection().isClosed()) {
                    connLabel.getStyleClass().add("connected");
                    connLabel.setText("Connection with the database has been established.");
                }
                else {
                    connLabel.getStyleClass().add("not-connected");
                    connLabel.setText("Connection with the database has NOT been established!");
                }
            }
            else {
                connLabel.getStyleClass().add("not-connected");
                connLabel.setText("Connection with the database has NOT been established!");
            }
        } catch (SQLException e) {
            System.out.println("Connection Error!");
        }
    }

    public void logInAction(javafx.event.ActionEvent event) {
        String access = DB_DataFetcher.getAccess(usrField.getText(), passField.getText());
        //System.out.println(access);
        if (access.equals("X")) {
            ErrorBox_Controller.showError("Invalid Login","There is a mismatch in the username or password or in both! Please, try again!");
        }
        else if (access.equals("N")) {
            CorrectBox_Controller.showCorrect("Login Succeeded", "Successful login as a user!");
            Main.logged_user = usrField.getText();
            DB_DataModifier.updateLogin(Main.logged_user);
            Main.user_type = "USER";
            Main.window.setTitle("Dashboard");
            Main.window.hide();
            Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
            Main.window.show();
        }
        else {
            CorrectBox_Controller.showCorrect("Login Succeeded", "Successful login as an Admin!");
            Main.logged_user = usrField.getText();
            DB_DataModifier.updateLogin(Main.logged_user);
            Main.user_type = "ADMIN";
            Main.window.setTitle("Dashboard");
            Main.window.hide();
            Main.window.setScene(DashBoad_Controller.getDashScene(Main.user_type));
            Main.window.show();
        }
    }

    public void refreshAction(javafx.event.ActionEvent event) {
        testConn();
    }
}
