package assignments.assignment3.systemCLI;

import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import java.util.List;
import java.util.ArrayList;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

import java.util.Scanner;

import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;

public abstract class UserSystemCLI {
    protected static List<Order> orderList;
    protected static ArrayList<Menu> menuList;
    protected Scanner input;
    protected static ArrayList<Restaurant> restoList;
    protected static ArrayList<User> userList;
    protected User userLoggedIn;

    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    abstract void displayMenu();

    abstract boolean handleMenu(int command);

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // Remove restaurant's name's spaces and make it uppercase
        namaRestoran = namaRestoran.replace(" ", "");
        namaRestoran = namaRestoran.toUpperCase();
        String namaRestoranID = namaRestoran.substring(0, 4);

        // Remove order date's slashes
        String tanggalOrderID = tanggalOrder.replace("/", "");
        
        // Convert the phone number to an array of integers
        int[] noTeleponArray = new int[noTelepon.length()];
        for (int i = 0; i < noTelepon.length(); i++) {
            noTeleponArray[i] = Character.getNumericValue(noTelepon.charAt(i));
        }

        // Sum the phone number's digits
        int total = 0;
        for (int number : noTeleponArray) {
            total += number;
        }

        // Modulo the total by 100 and format it to 2 digits
        String noTeleponID = String.format("%02d", total % 100);

        // Generate the order ID and return it
        String orderID = checksum(namaRestoranID, tanggalOrderID, noTeleponID);
        return orderID;
    }

    // Method to generate checksum
    public static String checksum(String namaRestoranID, String tanggalOrderID, String noTeleponID) {
        // Add 3 parts of the order ID
        String orderID = namaRestoranID + tanggalOrderID + noTeleponID;

        // Define the character set
        String CHARACTERSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Calculate the sum of the odd and even characters
        int sumOdd = 0;
        int sumEven = 0;
        for (int i = 0; i < orderID.length(); i++) {
            if (i % 2 == 0) {
                // Get the index of the character and add it to the sum of the even characters
                sumEven += CHARACTERSET.indexOf(orderID.charAt(i));
            } else {
                // Get the index of the character and add it to the sum of the odd characters
                sumOdd += CHARACTERSET.indexOf(orderID.charAt(i));
            }
        }
        
        // Get the checksum characters by modulo the sums by 36 and decode it back
        char checksumOdd = CHARACTERSET.charAt(sumOdd % 36);
        char checksumEven = CHARACTERSET.charAt(sumEven % 36);
        
        // Return the order ID with the checksum characters
        orderID = orderID + checksumEven + checksumOdd;
        return orderID;
    }

    public UserSystemCLI() {
        this.input = new Scanner(System.in);
    }
}
