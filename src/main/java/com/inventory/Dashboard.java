package com.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Dashboard {

    // 1. Create an instance of our backend tool
    private final InventoryDAO dao = new CSVInventoryDAO();

    public void show(Stage stage) {
        stage.setTitle("Clothing Inventory - Dashboard");

        // 2. Create the Table
        TableView<ClothingItem> table = new TableView<>();

        // 3. Define the Columns (Must match your ClothingItem getters!)
        TableColumn<ClothingItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ClothingItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ClothingItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ClothingItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<ClothingItem, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<ClothingItem, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        // Add columns to the table
        table.getColumns().addAll(idCol, nameCol, priceCol, qtyCol, sizeCol, colorCol);

        // 4. Load Data from CSV
        refreshTable(table);

        // 5. Create Buttons (Logout, etc.)
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            // Go back to the Login Screen
            new App().start(stage);
        });

        // 6. Layout
        HBox topMenu = new HBox(10); // Horizontal box for buttons
        topMenu.setPadding(new Insets(10));
        topMenu.getChildren().add(logoutBtn);

        BorderPane root = new BorderPane();
        root.setTop(topMenu);
        root.setCenter(table);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to get data from DAO and put it in the table
    private void refreshTable(TableView<ClothingItem> table) {
        ObservableList<ClothingItem> data = FXCollections.observableArrayList(dao.getAllProducts());
        table.setItems(data);
    }
}