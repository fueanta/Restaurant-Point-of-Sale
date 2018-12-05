package test_driver;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminTestDriver extends Application {
    private static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setTitle("Login Panel");
        window.setScene(gui.application.admin_view.Admin_Controller.getSubItemScene());
        window.show();
    }
}
