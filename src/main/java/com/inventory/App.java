package com.inventory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Create a simple label
        Label label = new Label("Hello, Inventory Manager!");

        // 2. Add it to a layout
        StackPane root = new StackPane();
        root.getChildren().add(label);

        // 3. Create the Scene
        Scene scene = new Scene(root, 640, 480);

        // 4. Set up the Stage (Window)
        stage.setTitle("Clothing Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}