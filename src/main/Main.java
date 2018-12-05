package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static boolean modifiable = true;

    public static String logged_user = "taqui", user_type = "ADMIN";

    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.window = primaryStage;
        window.getIcons().add(new Image("/resource/image/spoons.png"));
        window.setTitle("Login Panel");
        window.setScene(gui.application.login.Login_Controller.getLoginScene());
        window.show();
    }

}
