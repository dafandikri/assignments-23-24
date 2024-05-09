/*
    Nama        : Erdafa Andikri
    NPM         : 2306244993
    Kode Asdos  : RAC

    Program ini dibuat untuk memenuhi tugas TP 3 mata kuliah DDP 2.

    Program ini adalah program untuk memesan makanan dari restoran-restoran yang terdaftar.
    Program ini memiliki 2 role, yaitu Customer dan Admin.
        - Customer : Memesan makanan, mencetak bill, melihat menu, dan mengupdate status pesanan.
        - Admin    : menambah restoran dan menghapus restoran.

    Program ini memiliki 2 jenis pembayaran, yaitu DebitPayment dan CreditCardPayment.
    - DebitPayment      : Pembayaran menggunakan debit.
    - CreditCardPayment : Pembayaran menggunakan kartu kredit.
    

    Program ini mempunyai error handling untuk input yang tidak valid, seperti tanggal yang tidak valid, harga yang tidak valid, dan lain-lain.
 */


package assignments.assignment3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.LoginManager;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.UserSystemCLI;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class MainMenu {
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<User> userList;
    private static ArrayList<Restaurant> restoList;

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new Scanner(System.in), new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        initUser();
        mainMenu.run();
    }

    public void run() {
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }

    private void login() {
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        User userLoggedIn = getUser(nama, noTelp);

        // If user is not found
        if(userLoggedIn == null){
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
            return;
        // If user is found
        } else {
            System.out.print("Selamat datang, " + userLoggedIn.getNama() + "!");
        }
        boolean isLoggedIn = true;

        UserSystemCLI system = loginManager.getSystem(userLoggedIn.role);
        if (system instanceof CustomerSystemCLI) {
            ((CustomerSystemCLI) system).setUserLoggedIn(userLoggedIn);
        }
        system.run();
    }

    // Get user by nama and nomorTelepon from userList
    public static User getUser(String nama, String nomorTelepon){
        for (User currentUser : userList) {
            if(currentUser.getNama().equals(nama) && currentUser.getNomorTelepon().equals(nomorTelepon)){
                return currentUser;
            }
        }
        return null; // return null if no matching user is found
    }

    private static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu(){
        System.out.println("\nSelamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void initUser(){
        userList = new ArrayList<User>();
        
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(
                new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }
}