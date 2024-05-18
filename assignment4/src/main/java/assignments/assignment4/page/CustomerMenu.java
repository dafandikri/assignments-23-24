package assignments.assignment4.page;

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
import javafx.stage.Stage;
import java.beans.EventHandler;
import java.time.LocalTime;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;


    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
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

        Button seeResto = new Button("Make Order");
        seeResto.setGraphic(imageViewViewMenu);
        seeResto.setPrefSize(250, 80);
        seeResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        seeResto.setOnMouseExited(e -> seeResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        seeResto.setOnMouseEntered(e -> seeResto.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        seeResto.setCursor(Cursor.HAND);
        seeResto.setOnAction(e -> {
            stage.setScene(createTambahPesananForm());
        });
        seeResto.setAlignment(Pos.CENTER);
        grid.add(seeResto, 0, 2, 2, 1);

        HBox hboxButton = new HBox(10);
        GridPane.setHalignment(seeResto, HPos.CENTER);
        
        // Create the image view
        Image imageAddResto = new Image(getClass().getResourceAsStream("/addresto.png"));
        ImageView imageViewAddResto = new ImageView(imageAddResto);
        imageViewAddResto.setFitWidth(15);
        imageViewAddResto.setPreserveRatio(true);

        Label labelResto = new Label("Print Bill");
        labelResto.setWrapText(true); // This will enable text wrapping
        labelResto.setGraphic(imageViewAddResto);
        labelResto.setContentDisplay(ContentDisplay.TOP);
        labelResto.setTextAlignment(TextAlignment.CENTER);
        labelResto.setGraphicTextGap(10); // Adjust this value as needed
        labelResto.setAlignment(Pos.CENTER);

        VBox vboxResto = new VBox(imageViewAddResto, labelResto);
        vboxResto.setAlignment(Pos.CENTER); // This will center the items in the VBox

        Button addResto = new Button();
        addResto.setGraphic(vboxResto); // Set the VBox as the graphic
        addResto.setPrefSize(120, 30);
        addResto.setContentDisplay(ContentDisplay.CENTER);
        addResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        addResto.setTextAlignment(TextAlignment.CENTER);
        addResto.setOnMouseExited(e -> addResto.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addResto.setOnMouseEntered(e -> addResto.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addResto.setCursor(Cursor.HAND);
        addResto.setOnAction(e -> {
            stage.setScene(createBillPrinter());
        });
        addResto.setAlignment(Pos.CENTER);

        // Create the image view
        Image imageAddMenu = new Image(getClass().getResourceAsStream("/addmenu.png"));
        ImageView imageViewAddMenu = new ImageView(imageAddMenu);
        imageViewAddMenu.setFitWidth(15);
        imageViewAddMenu.setPreserveRatio(true);

        // Create the text
        Label labelMenu = new Label("Check Balance");
        labelMenu.setWrapText(true); // This will enable text wrapping
        labelMenu.setGraphic(imageViewAddMenu);
        labelMenu.setContentDisplay(ContentDisplay.TOP);
        labelMenu.setTextAlignment(TextAlignment.CENTER);
        labelMenu.setGraphicTextGap(10); // Adjust this value as needed
        labelMenu.setAlignment(Pos.CENTER); // Center the label

        VBox vboxMenu = new VBox(imageViewAddMenu, labelMenu);
        vboxMenu.setAlignment(Pos.CENTER); // This will center the items in the VBox

        Button addMenu = new Button();
        addMenu.setGraphic(vboxMenu); // Set the VBox as the graphic
        addMenu.setPrefSize(120,30);
        addMenu.setContentDisplay(ContentDisplay.CENTER);
        addMenu.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;");
        addMenu.setTextAlignment(TextAlignment.CENTER);
        addMenu.setOnMouseExited(e -> addMenu.setStyle("-fx-background-color: #D7ECFF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addMenu.setOnMouseEntered(e -> addMenu.setStyle("-fx-background-color: #BDE0FF; -fx-background-radius: 8; -fx-border-width: 3px; -fx-border-color: #BDE0FF; -fx-border-radius: 8; -fx-font-weight: bold;"));
        addMenu.setCursor(Cursor.HAND);
        addMenu.setOnAction(e -> {
            stage.setScene(createCekSaldoScene());
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

    private Scene createTambahPesananForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah pesanan
        VBox menuLayout = new VBox(10);
    
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBillPrinter(){
        // TODO: Implementasikan method ini untuk menampilkan page cetak bill

        return null;
    }

    private Scene createBayarBillForm() {
        // TODO: Implementasikan method ini untuk menampilkan page bayar bill
        VBox menuLayout = new VBox(10);

        return new Scene(menuLayout, 400,600);
    }


    private Scene createCekSaldoScene() {
        // TODO: Implementasikan method ini untuk menampilkan page cetak saldo
        VBox menuLayout = new VBox(10);

        return new Scene(menuLayout, 400,600);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        //TODO: Implementasi validasi isian pesanan
        try {

        } catch (Exception e) {

        }
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        //TODO: Implementasi validasi pembayaran
        try {

        } catch (Exception e) {

        }
    }
}