package com.inventory;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clothing Inventory - Login");

        // 1. Create Controls (The "Widgets")
        Label titleLabel = new Label("Welcome Back!");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField userField = new TextField();
        userField.setPromptText("Username"); // Ghost text
        userField.setMaxWidth(200);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        Label statusLabel = new Label(""); // To show "Wrong password" errors
        statusLabel.setStyle("-fx-text-fill: red;");

        // 2. Define Layout (Vertical Box - stacks items top to bottom)
        VBox layout = new VBox(15); // 15 pixels of space between items
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, userField, passField, loginButton, statusLabel);

        // 3. Add Button Action (The Logic)
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            if (authenticate(username, password)) {
                new Dashboard().show(primaryStage);
                // Later, we will switch scenes here
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("Invalid Credentials");
            }
        });

        // 4. Set Scene
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // A simple hardcoded check for now
    private boolean authenticate(String user, String pass) {
        // In the real version, we will check the CSV file!
        return user.equals("admin") && pass.equals("1234");
    }

    public static void main(String[] args) {
        launch();
    }
}