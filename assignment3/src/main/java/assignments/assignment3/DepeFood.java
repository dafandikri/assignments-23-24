package assignments.assignment3;

import java.util.ArrayList;
import java.util.List;

import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment2.MainMenu;
import assignments.assignment3.LoginManager;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.UserSystemCLI;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class DepeFood extends MainMenu{
    public static ArrayList<Restaurant> restoList;
    public static List<User> userList = new ArrayList<>();

    public static void initUser() {
        // Initialize your users here
        userList = new ArrayList<User>();
        
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }

    public static String getValidRestaurantName(String name) {
        for (Restaurant currentResto : restoList) {
            if (currentResto.getNama().equals(name)) {
                return name;
            }
        }
        throw new IllegalArgumentException("Restaurant name does not exist");
    }
}