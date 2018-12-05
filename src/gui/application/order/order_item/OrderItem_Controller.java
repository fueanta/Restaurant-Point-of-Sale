package gui.application.order.order_item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.DB_DataFetcher;
import database.DB_DataModifier;
import entity.main_sub.ModifiedMainSub;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderItem_Controller implements Initializable {

    public static int id;

    private static Stage window;

    public static void loadInventory(int id) {
        OrderItem_Controller.id = id;
        window = new Stage();
        window.setTitle("Order Items");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setScene(getOrderItemScene());
        window.showAndWait();
    }

    public static Scene getOrderItemScene() {
        Scene orderScene = null;
        try {
            Parent root = FXMLLoader.load(OrderItem_Controller.class.getResource("OrderItem_GUI.fxml"));
            orderScene = new Scene(root);
        } catch (IOException e) {
            System.out.println("Order Item GUI could not be loaded!");
        }
        return orderScene;
    }

    // label
    @FXML public Label idLabel;
    @FXML public Label nameLabel;
    @FXML public Label total;

    //spinner
    //@FXML public Spinner<Integer> spinner;

    // combo
    @FXML public JFXComboBox<Integer> combo;


    // buttons
    @FXML public JFXButton add;
    @FXML public JFXButton remove;

    // table
    @FXML public TableView<ModifiedMainSub> table1, table2;

    // table1 columns
    @FXML public TableColumn<ModifiedMainSub, String> name1;
    @FXML public TableColumn<ModifiedMainSub, Float> cost1;

    // table2 columns
    @FXML public TableColumn<ModifiedMainSub, String> name2;
    @FXML public TableColumn<ModifiedMainSub, Float> cost21;
    @FXML public TableColumn<ModifiedMainSub, Integer> count2;
    @FXML public TableColumn<ModifiedMainSub, Float> cost2;

    // observable list for datasets
    public ObservableList<ModifiedMainSub> list1, list2;

    // Method that generates data matching the column names
    private void setCellValueFactories() {
        name1.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, String>
                ("name"));
        cost1.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, Float>
                ("unit_cost"));

        name2.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, String>
                ("name"));
        cost21.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, Float>
                ("unit_cost"));
        count2.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, Integer>
                ("count"));
        cost2.setCellValueFactory(new PropertyValueFactory<ModifiedMainSub, Float>
                ("total_cost"));
    }

    ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);

    public void tableListeners() {
        table1.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                table1Selection();
            }
        });
        table2.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                table2Selection();
            }
        });
    }

    public float generateSum() {
        float sum = 0;
        for (ModifiedMainSub item : table2.getItems()) {
            sum += item.getTotal_cost();
        }
        return sum;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        putDataOnList();
        setCellValueFactories();
        table1.setItems(list1);
        table2.setItems(list2);
        tableListeners();

        combo.setItems(list);
        combo.setDisable(true);

        idLabel.setText(String.valueOf(id));
        total.setText(String.valueOf(generateSum()));
        add.setDisable(true);
        remove.setDisable(true);

    }

    // Method that generates data
    private void putDataOnList() {
        list1 = DB_DataFetcher.getOtherOrderItems(id);
        list2 = DB_DataFetcher.getOrderItems(id);
    }

    public void updateBtnOnAction(ActionEvent event) {
        ObservableList<ModifiedMainSub> list = table2.getItems();
        DB_DataModifier.update_order_item(list, id);
        window.close();
    }

    public void cancelBtnOnAction(ActionEvent event) {
        window.close();
    }

    public void removeOnAction(ActionEvent event) {
        ModifiedMainSub item = table2.getSelectionModel().getSelectedItem();
        table2.getItems().remove(item);
        table2.getSelectionModel().clearSelection();

        item.setCount(0);
        item.setTotal_cost(item.getUnit_cost()*item.getCount());
        table1.getItems().add(item);
        total.setText(String.valueOf(generateSum()));

        nameLabel.setText("Item Name");
        remove.setDisable(true);
    }

    public void addOnAction(ActionEvent event) {
        ModifiedMainSub item = table1.getSelectionModel().getSelectedItem();
        table1.getItems().remove(item);
        table1.getSelectionModel().clearSelection();
        int count = combo.getValue();
        item.setCount(count);
        item.setTotal_cost(item.getUnit_cost()*item.getCount());
        table2.getItems().add(item);
        total.setText(String.valueOf(generateSum()));

        add.setDisable(true);
        combo.getSelectionModel().clearSelection();
        combo.setDisable(true);
        nameLabel.setText("Item Name");
    }

    public void table1Selection() {
        table2.getSelectionModel().clearSelection();
        nameLabel.setText(table1.getSelectionModel().getSelectedItem().getName());
        combo.setDisable(false);
        add.setDisable(false);
        remove.setDisable(true);
    }

    public void table2Selection() {
        table1.getSelectionModel().clearSelection();
        nameLabel.setText(table2.getSelectionModel().getSelectedItem().getName());
        combo.setDisable(true);
        add.setDisable(true);
        remove.setDisable(false);
    }

}
