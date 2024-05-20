package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
    
        Label heading = new Label("Print Bill");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    
        // Text field for entering the order ID
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID");
    
        // Define billArea before using it
        TextArea billArea = new TextArea();
        billArea.setEditable(false);
        billArea.setPrefHeight(200);

        // Define printButton after billArea
        Button printButton = new Button("Print Bill");

        // Update the printButton action to show bill details
        printButton.setOnAction(e -> {
            String orderId = orderIdField.getText();
            if (!orderId.isEmpty()) {
                printBill(orderId, billArea);
            } else {
                billArea.setText("Please enter a valid order ID.");
            }
        });
            
    
        layout.getChildren().addAll(heading, orderIdField, printButton, billArea);
        return new Scene(layout, 400, 400);
    }

    public void printBill(String orderId, TextArea billArea) {
        // Simulating fetching an order from a database
        Order order = fetchOrderById(orderId);  // You'll need to implement this method
    
        if (order != null) {
            StringBuilder billContent = new StringBuilder();
            billContent.append("Bill Details:\n");
            billContent.append("Order ID: ").append(order.getOrderId()).append("\n");
            billContent.append("Tanggal Pemesanan: ").append(order.getTanggal()).append("\n");
            billContent.append("Restaurant: ").append(order.getResto().getNama()).append("\n");
            billContent.append("Lokasi Pengiriman: ").append(user.getLokasi()).append("\n");
            if (order.getOrderFinished()) {
                billContent.append("Status Pengiriman: Finished\n");
            } else {
                billContent.append("Status Pengiriman: Not Finished\n");
            }

            billContent.append("Pesanan:\n");
            billContent.append("Biaya Pengiriman: Rp. ").append(order.getOngkir()).append("\n");
            double total = 0;
            for (Menu item : order.getItems()) {
                billContent.append(item.getNama()).append(" - Rp. ").append(item.getHarga()).append("\n");
                total += item.getHarga();
            }
            total += order.getOngkir();
            billContent.append("Total Biaya: Rp. ").append(total).append("\n");

            billArea.setText(billContent.toString());
        } else {
            billArea.setText("No order found with ID: " + orderId);
        }
    }
    
    // Example helper method to simulate fetching an order
    private Order fetchOrderById(String orderId) {
        // This should interact with your data management system to retrieve an Order
        // Here, returning null simulates not finding the order
        return DepeFood.getOrderOrNull(orderId);
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
