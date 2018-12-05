package test_driver;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OrderGUI_TestDriver extends Application {
    private static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setTitle("Order Panel");
        window.setScene(gui.application.order.Order_Controller.getOrderScene());
        window.show();
    }
}