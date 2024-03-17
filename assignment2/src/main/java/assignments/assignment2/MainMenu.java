package assignments.assignment2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
// import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        boolean programRunning = true;
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // TODO: Validasi input login
                User userLoggedIn = getUser(nama, noTelp);
                if(userLoggedIn == null){
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                } else {
                    System.out.println("Selamat datang, " + userLoggedIn.getNama() + "!");
                }
                boolean isLoggedIn = true;

                if(userLoggedIn.role == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan();
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
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

    public static void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
    }

    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    // Add new restaurant method
    public static void handleTambahRestoran(){
        ArrayList<Menu> menuList = new ArrayList<Menu>();
        while (true) {
            System.out.println("--------------Tambah Restoran----------------");
            System.out.print("Nama: ");
            String nama = input.nextLine();
            if (length(nama) < 3) {
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }
            for (Restaurant currentResto : restoList) {
                if(currentResto.getNama().equals(nama)){
                    System.out.println("Restoran dengan nama " + nama + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!");
                    continue;
                }
            }
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine();
            for (int i = 0; i < jumlahMakanan; i++) {
                String makanan = input.next();
                String strHarga = input.next();
                input.nextLine();
                if (!strHarga.matches("\\d+") || strHarga.startsWith("-")) {
                    System.out.println("Harga menu harus bilangan bulat positif!");
                    menuList.clear();
                    continue;
                }
                double harga = Double.parseDouble(strHarga);
                Menu newMenu = new Menu(makanan, harga);
                menuList.add(newMenu);
            }
            Restaurant newResto = new Restaurant(nama, menuList);
            restoList.add(newResto);
            System.out.println("Restaurant " + nama + " berhasil terdaftar.");
        }
    }

    // Remove restaurant method
    public static void handleHapusRestoran(){
        System.out.println("--------------Hapus Restoran----------------");
        System.out.print("Nama Restoran: ");
        String nama = input.nextLine();
        for (Restaurant currentResto : restoList) {
            if(currentResto.getNama().equals(nama)){
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }
            restoList.remove(currentResto);
            System.out.println("Restoran berhasil dihapus.");
        }
    }

    public static void initUser(){
        userList = new ArrayList<User>();
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
