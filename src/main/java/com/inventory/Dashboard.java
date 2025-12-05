package com.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dashboard {

    private final InventoryDAO dao = new CSVInventoryDAO();
    private TableView<ClothingItem> table;

    public void show(Stage stage, User currentUser) {
        stage.setTitle("Clothing Inventory - Dashboard (" + currentUser.getUsername() + ")");

        table = new TableView<>();
        setupTableColumns();
        refreshTable();

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new App().start(stage));

        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> showItemDialog(stage, null));

        Button editBtn = new Button("Edit");
        editBtn.setOnAction(e -> {
            ClothingItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Select an item to edit.");
            } else {
                showItemDialog(stage, selected);
            }
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("button-red"); 
        deleteBtn.setOnAction(e -> deleteSelectedItem());

        if (currentUser.getRole() != Role.ADMIN) {
            addBtn.setDisable(true);
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }

        HBox topMenu = new HBox(10);
        topMenu.setPadding(new Insets(10));
        topMenu.getChildren().addAll(logoutBtn, addBtn, editBtn, deleteBtn);

        BorderPane root = new BorderPane();
        root.setTop(topMenu);
        root.setCenter(table);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }       
    
    private void deleteSelectedItem() {
        ClothingItem selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an item to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getName() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            dao.deleteProduct(selected.getID());
            refreshTable();
        }
    }

    private void showItemDialog(Stage parentStage, ClothingItem itemToEdit) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentStage);
        boolean isEditing = (itemToEdit != null);
        dialog.setTitle(isEditing ? "Edit Item" : "Add New Item");

        TextField nameField = new TextField(isEditing ? itemToEdit.getName() : "");
        TextField priceField = new TextField(isEditing ? String.valueOf(itemToEdit.getPrice()) : "");
        TextField qtyField = new TextField(isEditing ? String.valueOf(itemToEdit.getQuantity()) : "");
        TextField sizeField = new TextField(isEditing ? itemToEdit.getSize() : "");
        TextField colorField = new TextField(isEditing ? itemToEdit.getColor() : "");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Price:"), priceField);
        grid.addRow(2, new Label("Quantity:"), qtyField);
        grid.addRow(3, new Label("Size:"), sizeField);
        grid.addRow(4, new Label("Color:"), colorField);

        Button saveBtn = new Button(isEditing ? "Update" : "Save");
        saveBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                String size = sizeField.getText();
                String color = colorField.getText();

                if (isEditing) {
                    ClothingItem updated = new ClothingItem(itemToEdit.getID(), name, price, qty, size, color);
                    dao.updateProduct(updated);
                } else {
                    int newId = dao.getAllProducts().stream().mapToInt(ClothingItem::getID).max().orElse(0) + 1;
                    ClothingItem newItem = new ClothingItem(newId, name, price, qty, size, color);
                    dao.addProduct(newItem);
                }
                refreshTable();
                dialog.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid numbers for Price or Quantity.");
            }
        });

        VBox layout = new VBox(10, grid, saveBtn);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(layout, 300, 300);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }

    private void setupTableColumns() {
        TableColumn<ClothingItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
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
        table.getColumns().addAll(idCol, nameCol, priceCol, qtyCol, sizeCol, colorCol);
    }

    private void refreshTable() {
        ObservableList<ClothingItem> data = FXCollections.observableArrayList(dao.getAllProducts());
        table.setItems(data);
    }
}