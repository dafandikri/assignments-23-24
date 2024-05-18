package assignments.assignment4.components.form;

import java.beans.EventHandler;
import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput; // TextField for name input
    private TextField phoneInput; // TextField for phone input
    private Label errorLabel; // Label for error message

    public LoginForm(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.stage.centerOnScreen();
    }

    private Scene createLoginForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Load the image
        Image iconImage = new Image(getClass().getResourceAsStream("/icon.png"));
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(35);
        iconImageView.setPreserveRatio(true);
        grid.add(iconImageView, 0, 0);
        
        Button invisibleButton = new Button();
        invisibleButton.setVisible(false);
        grid.add(invisibleButton, 0, 0);

        Label signInLabel = new Label("Sign in to DepeFood");
        signInLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Avenir';");
        grid.add(signInLabel, 0, 1, 2, 1);

        Label accountLabel = new Label("Use your Name and Phone Number to sign in");
        grid.add(accountLabel, 0, 2, 2, 1);

        nameInput = new TextField();
        nameInput.setPromptText("Name");
        grid.add(nameInput, 0, 3);

        phoneInput = new TextField();
        phoneInput.setPromptText("Phone Number");
        grid.add(phoneInput, 0, 4);

        // Create error label and add it to the grid
        errorLabel = new Label();
        errorLabel.setTextFill(Color.DARKRED); // Set text color to dark red
        errorLabel.setText("Can't find your Name or Number");
        errorLabel.setVisible(false);
        grid.add(errorLabel, 0, 5);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007DFF; -fx-text-fill: white;");
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #007DFF; -fx-text-fill: white;"));
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #71B1FF; -fx-text-fill: white;"));
        loginButton.setCursor(Cursor.HAND);
        grid.add(loginButton, 1, 7);
        GridPane.setHalignment(loginButton, HPos.RIGHT);

        loginButton.setOnAction(e -> handleLogin()); // Set action for button

        grid.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER ) {
                handleLogin();
            }
        });

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

    private void handleLogin() {
        // Implementation for login validation
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        User user = DepeFood.handleLogin(name, phone);
        if (name.isEmpty() || phone.isEmpty() || user == null) {
            // Show error message in GUI
            errorLabel.setVisible(true);
        } else {
            // Process login
            errorLabel.setVisible(false);
            if (user.getRole().equals("Admin")) {
                AdminMenu adminMenu = new AdminMenu(stage, mainApp, user);
                stage.setScene(adminMenu.getScene());
                stage.centerOnScreen();
            } 
            else {
                CustomerMenu customerMenu = new CustomerMenu(stage, mainApp, user);
                stage.setScene(customerMenu.getScene());
                stage.centerOnScreen();
            }
        }
    }

    public Scene getScene(){
        return this.createLoginForm();
    }
}