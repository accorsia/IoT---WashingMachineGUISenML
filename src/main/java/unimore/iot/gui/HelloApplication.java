package unimore.iot.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).openStream());
        stage.getIcons().add(icon);

        stage.setTitle("Interact with your Washing Machine");
        stage.setScene(scene);

        //  what to do when red X is pressed
        stage.setOnCloseRequest(event -> {
            System.out.println("Chiusura della finestra");
            System.exit(0);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}