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

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField userField = new TextField();
        userField.setPromptText("Username");
        userField.setMaxWidth(200);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: red;");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, userField, passField, loginButton, statusLabel);

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            User validUser = authenticate(username, password);

            if (validUser != null) {
                new Dashboard().show(primaryStage, validUser);
            } else {
                statusLabel.setText("Invalid Credentials");
            }
        });

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.show();
    }

    private User authenticate(String user, String pass) {
        if (user.equals("admin") && pass.equals("admin123")) {
            return new User("admin", "admin123", Role.ADMIN);
        } 
        else if (user.equals("employee") && pass.equals("1234")) {
            return new User("employee", "1234", Role.USER);
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}