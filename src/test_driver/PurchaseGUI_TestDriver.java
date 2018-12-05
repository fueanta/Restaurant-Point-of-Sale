package test_driver;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PurchaseGUI_TestDriver extends Application {
    private static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setTitle("Purchase Panel");
        window.setScene(gui.application.purchase.Purchase_Controller.getPurchaseScene());
        window.show();
    }
}