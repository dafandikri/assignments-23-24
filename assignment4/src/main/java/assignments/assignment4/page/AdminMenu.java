package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ContentDisplay;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.beans.EventHandler;
import java.time.LocalTime;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();
    private Label errorLabelAddMenu;
    private Label errorLabelAddResto;

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();

        this.stage.centerOnScreen();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Scene createBaseMenu() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Get the current hour
        int hour = LocalTime.now().getHour();

        // Determine the greeting based on the current hour
        String greeting;
        if (hour < 12) {
            greeting = "Good Morning, ";
        } else if (hour < 17) {
            greeting = "Good Afternoon, ";
        } else {
            greeting = "Good Evening, ";
        }

        Label title = new Label(greeting + this.user.getNama());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Avenir';");
        grid.add(title, 0, 1, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER);

        Button logoutButton = new Button();
        Image image = new Image(getClass().getResourceAsStream("/logout_icon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(15);
        imageView.setPreserveRatio(true);
        logoutButton.setGraphic(imageView);
        logoutButton.setOnAction(e -> {
            mainApp.logout();
        });
        logoutButton.setStyle("-fx-background-color: #F28280;");
        logoutButton.setOnMouseExited(enterEvent -> logoutButton.setStyle("-fx-background-color: #F28280;"));
        logoutButton.setOnMouseEntered(exitEvent -> logoutButton.setStyle("-fx-background-color: #FA9593"));
        logoutButton.setCursor(Cursor.HAND);

        // Create a new HBox, add the logout button to it, and align it to the right
        HBox hboxLogout = new HBox(logoutButton);
        hboxLogout.setAlignment(Pos.TOP_RIGHT);

        grid.add(hboxLogout, 0, 0, 2, 1);
        GridPane.setHalignment(hboxLogout, HPos.RIGHT);

        Image imageViewMenu = new Image(getClass().getResourceAsStream("/viewmenu.png"));
        ImageView imageViewViewMenu = new ImageView(imageViewMenu);
        imageViewViewMenu.setFitWidth(15);
        imageViewViewMenu.setPreserveRatio(true);

        Button seeResto = new Button("View Menu");
        seeResto.setGraphic(imageViewViewMenu);
        seeResto.setPrefSize(210, 40);
        seeResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        seeResto.setOnMouseExited(e -> seeResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        seeResto.setOnMouseEntered(e -> seeResto.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        seeResto.setCursor(Cursor.HAND);
        seeResto.setOnAction(e -> {
            stage.setScene(createViewRestaurantsForm());
        });
        seeResto.setAlignment(Pos.CENTER);
        grid.add(seeResto, 0, 2, 2, 1);

        HBox hboxButton = new HBox(10);
        GridPane.setHalignment(seeResto, HPos.CENTER);
        
        // Create the image view
        Image imageAddResto = new Image(getClass().getResourceAsStream("/addresto.png"));
        ImageView imageViewAddResto = new ImageView(imageAddResto);
        imageViewAddResto.setFitWidth(30);
        imageViewAddResto.setPreserveRatio(true);

        Label labelResto = new Label("Add Restaurant");
        labelResto.setWrapText(true); // This will enable text wrapping
        labelResto.setMaxWidth(100); // Set the max width to the width of your button
        labelResto.setGraphic(imageViewAddResto);
        labelResto.setContentDisplay(ContentDisplay.TOP);
        labelResto.setTextAlignment(TextAlignment.CENTER);
        labelResto.setGraphicTextGap(10); // Adjust this value as needed
        labelResto.setAlignment(Pos.CENTER);

        VBox vboxResto = new VBox(imageViewAddResto, labelResto);
        vboxResto.setAlignment(Pos.CENTER); // This will center the items in the VBox

        Button addResto = new Button();
        addResto.setGraphic(vboxResto); // Set the VBox as the graphic
        addResto.setPrefSize(100, 100);
        addResto.setContentDisplay(ContentDisplay.CENTER);
        addResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        addResto.setTextAlignment(TextAlignment.CENTER);
        addResto.setOnMouseExited(e -> addResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addResto.setOnMouseEntered(e -> addResto.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addResto.setCursor(Cursor.HAND);
        addResto.setOnAction(e -> {
            stage.setScene(createAddRestaurantForm());
        });
        addResto.setAlignment(Pos.CENTER);

        // Create the image view
        Image imageAddMenu = new Image(getClass().getResourceAsStream("/addmenu.png"));
        ImageView imageViewAddMenu = new ImageView(imageAddMenu);
        imageViewAddMenu.setFitWidth(30);
        imageViewAddMenu.setPreserveRatio(true);

        // Create the text
        Label labelMenu = new Label("Add Menu");
        labelMenu.setWrapText(true); // This will enable text wrapping
        labelMenu.setMaxWidth(100); // Set the max width to the width of your button
        labelMenu.setGraphic(imageViewAddMenu);
        labelMenu.setContentDisplay(ContentDisplay.TOP);
        labelMenu.setTextAlignment(TextAlignment.CENTER);
        labelMenu.setGraphicTextGap(10); // Adjust this value as needed
        labelMenu.setAlignment(Pos.CENTER); // Center the label

        VBox vboxMenu = new VBox(imageViewAddMenu, labelMenu);
        vboxMenu.setAlignment(Pos.CENTER); // This will center the items in the VBox

        Button addMenu = new Button();
        addMenu.setGraphic(vboxMenu); // Set the VBox as the graphic
        addMenu.setPrefSize(100, 100);
        addMenu.setContentDisplay(ContentDisplay.CENTER);
        addMenu.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        addMenu.setTextAlignment(TextAlignment.CENTER);
        addMenu.setOnMouseExited(e -> addMenu.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addMenu.setOnMouseEntered(e -> addMenu.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addMenu.setCursor(Cursor.HAND);
        addMenu.setOnAction(e -> {
            stage.setScene(createAddMenuForm());
        });
        addMenu.setAlignment(Pos.CENTER);
        
        hboxButton.getChildren().addAll(addResto, addMenu);
        hboxButton.setAlignment(Pos.CENTER);
        grid.add(hboxButton, 0, 3, 2, 1);

        // Create a Rectangle with a DropShadow effect
        Rectangle rectangle = new Rectangle(300, 300); // Adjust size as needed
        rectangle.setFill(Color.WHITE);
        rectangle.setArcWidth(30.0); // Set corner radius
        rectangle.setArcHeight(30.0); // Set corner radius
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5)); // Set shadow color
        rectangle.setEffect(dropShadow);

        // Create a StackPane to layer the Rectangle and GridPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, grid); // Add both rectangle and grid
        stackPane.setStyle("-fx-background-color: #F0F8FF;"); // Set background color to very light blue

        return new Scene(stackPane, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    
        Label label = new Label("Add Restaurant");
        label.setFont(Font.font("Avenir", FontWeight.NORMAL, 20));
        TextField nameField = new TextField();
        nameField.setPromptText("Restaurant Name");
        nameField.setPrefWidth(200);
        
        Button addButton = new Button("Add Restaurant");
        addButton.setOnAction(e -> handleTambahRestoran(nameField.getText()));
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(getScene()));

        // Create an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10); // Set the spacing between the buttons
        buttonBox.getChildren().addAll(backButton, addButton); // Add the buttons to the HBox
        HBox.setHgrow(addButton, Priority.ALWAYS); // Make the addButton grow to fill the available space

        errorLabelAddResto = new Label();
        errorLabelAddResto.setTextFill(Color.RED); // Set the text color to red
        errorLabelAddResto.setVisible(false); // Initially set it to invisible

        grid.add(errorLabelAddResto, 0, 3); // Add the errorLabel to the grid
        grid.add(label, 0, 0);
        grid.add(nameField, 0, 1);
        grid.add(buttonBox, 0, 2); // Add the HBox to the grid

        // Create a Rectangle with a DropShadow effect
        Rectangle rectangle = new Rectangle(300, 300); // Adjust size as needed
        rectangle.setFill(Color.WHITE);
        rectangle.setArcWidth(30.0); // Set corner radius
        rectangle.setArcHeight(30.0); // Set corner radius
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5)); // Set shadow color
        rectangle.setEffect(dropShadow);

        // Create a StackPane to layer the Rectangle and GridPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, grid); // Add both rectangle and grid
        stackPane.setStyle("-fx-background-color: #F0F8FF;"); // Set background color to very light blue

        return new Scene(stackPane, 400, 600);
    }

    private Scene createAddMenuForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label label = new Label("Add Menu");
        label.setFont(Font.font("Avenir", FontWeight.NORMAL, 20));
        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Restaurant Name");
        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button addButton = new Button("Add Menu");
        addButton.setOnAction(e -> {
            Restaurant selectedRestaurant = DepeFood.findRestaurant(restaurantNameField.getText());
            handleTambahMenuRestoran(selectedRestaurant, itemNameField.getText(), priceField.getText());
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(getScene()));

        // Create an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10); // Set the spacing between the buttons
        buttonBox.getChildren().addAll(backButton, addButton); // Add the buttons to the HBox
        HBox.setHgrow(addButton, Priority.ALWAYS); // Make the addButton grow to fill the available space

        errorLabelAddMenu = new Label();
        errorLabelAddMenu.setTextFill(Color.RED); // Set the text color to red
        errorLabelAddMenu.setVisible(false); // Initially set it to invisible

        grid.add(errorLabelAddMenu, 0, 5); // Add the errorLabelAddMenu to the grid
        grid.add(label, 0, 0);
        grid.add(restaurantNameField, 0, 1);
        grid.add(itemNameField, 0, 2);
        grid.add(priceField, 0, 3);
        grid.add(buttonBox, 0, 4); // Add the HBox to the grid

        Rectangle rectangle = new Rectangle(300, 300); // Adjust size as needed
        rectangle.setFill(Color.WHITE);
        rectangle.setArcWidth(30.0); // Set corner radius
        rectangle.setArcHeight(30.0); // Set corner radius
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5)); // Set shadow color
        rectangle.setEffect(dropShadow);

        // Create a StackPane to layer the Rectangle and GridPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, grid); // Add both rectangle and grid
        stackPane.setStyle("-fx-background-color: #F0F8FF;"); // Set background color to very light blue

        return new Scene(stackPane, 400, 600);
    }
    
    private Scene createViewRestaurantsForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label label = new Label("View Menu");
        label.setFont(Font.font("Avenir", FontWeight.NORMAL, 20));
        restaurantComboBox = new ComboBox<>();
        restaurantComboBox.setPrefWidth(200);
        restaurantComboBox.getItems().addAll(DepeFood.getRestoList().stream().map(Restaurant::getNama).collect(Collectors.toList()));
        restaurantComboBox.setPromptText("Select Restaurant");

        ListView<String> menuItemsListView = new ListView<>();

        restaurantComboBox.setOnAction(e -> {
            Restaurant selectedRestaurant = DepeFood.findRestaurant(restaurantComboBox.getValue());
            if (selectedRestaurant != null) {
                menuItemsListView.setItems(FXCollections.observableArrayList(selectedRestaurant.getMenu().stream().map(menu -> menu.getNama() + " - Rp " + menu.getHarga()).collect(Collectors.toList())));
            }
        });

        Button backButton =  new Button("Back");
        backButton.setOnAction(e -> stage.setScene(getScene()));

        // Create an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10); // Set the spacing between the buttons
        buttonBox.getChildren().addAll(backButton); // Add the backButton to the HBox
        HBox.setHgrow(backButton, Priority.ALWAYS); // Make the backButton grow to fill the available space

        grid.add(label, 0, 0);
        grid.add(restaurantComboBox, 0, 1);
        grid.add(menuItemsListView, 0, 2);
        grid.add(buttonBox, 0, 3); // Add the HBox to the grid

        // Create a Rectangle with a DropShadow effect
        Rectangle rectangle = new Rectangle(300, 550); // Adjust size as needed
        rectangle.setFill(Color.WHITE);
        rectangle.setArcWidth(30.0); // Set corner radius
        rectangle.setArcHeight(30.0); // Set corner radius
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5)); // Set shadow color
        rectangle.setEffect(dropShadow);

        // Create a StackPane to layer the Rectangle and GridPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, grid); // Add both rectangle and grid
        stackPane.setStyle("-fx-background-color: #F0F8FF;"); // Set background color to very light blue

        return new Scene(stackPane, 400, 600);
    }
    
    private void handleTambahRestoran(String nama) {
        //TODO: Implementasi validasi isian nama Restoran
        String validName = DepeFood.getValidRestaurantName(nama);
        if (validName.equals(nama)) {
            DepeFood.handleTambahRestoran(nama);
            errorLabelAddResto.setText(nama + " has been added successfully");
            errorLabelAddResto.setTextFill(Color.GREEN); // Set the text color to green
            errorLabelAddResto.setVisible(true);
        } else if (validName.startsWith("Restoran dengan nama")) {
            errorLabelAddResto.setTextFill(Color.RED);
            errorLabelAddResto.setText(nama + " has been taken");
            errorLabelAddResto.setVisible(true);
        } else {
            errorLabelAddResto.setTextFill(Color.RED);
            errorLabelAddResto.setText("Require minimum of 4 characters");
            errorLabelAddResto.setVisible(true);
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, String price) {
        errorLabelAddMenu.setTextFill(Color.RED);
        try {
            // Check if restaurant is null or not in the list
            if (restaurant == null || DepeFood.findRestaurant(restaurant.getNama()) == null) {
                throw new IllegalArgumentException("Resto is null or not in the list");
            }

            // Check if itemName is null or empty
            if (itemName == null || itemName.trim().isEmpty()) {
                throw new IllegalArgumentException("Item name can't be empty");
            }

            // Check if itemName already exists in the restaurant's menu
            if (restaurant.getMenu().stream().anyMatch(item -> item.getNama().equals(itemName))) {
                throw new IllegalArgumentException("Item name already exists");
            }

            // Check if price is less than or equal to zero
            Double priceDouble = Double.parseDouble(price);
            if (Double.parseDouble(price) <= 0 || price.isEmpty()) {
                throw new IllegalArgumentException("Price must be greater than zero");
            }
            
            // If all checks pass, add the menu item
            DepeFood.handleTambahMenuRestoran(restaurant, itemName, priceDouble);
            errorLabelAddMenu.setText(itemName + " has been added");
            errorLabelAddMenu.setTextFill(Color.GREEN); // Set the text color to green
            errorLabelAddMenu.setVisible(true);
        } catch (IllegalArgumentException e) {
            errorLabelAddMenu.setText(e.getMessage());
            errorLabelAddMenu.setVisible(true);
        } catch (Exception e) {
            errorLabelAddMenu.setText("An unexpected error occurred: " + e.getMessage());
            errorLabelAddMenu.setVisible(true);
        }
    }
}