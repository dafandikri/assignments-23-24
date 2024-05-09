/*
    Nama        : Erdafa Andikri
    NPM         : 2306244993
    Kode Asdos  : RAC

    Program ini dibuat untuk memenuhi tugas TP 2 mata kuliah DDP 2.

    Program ini adalah program untuk memesan makanan dari restoran-restoran yang terdaftar.
    Program ini memiliki 2 role, yaitu Customer dan Admin.
        - Customer : Memesan makanan, mencetak bill, melihat menu, dan mengupdate status pesanan.
        - Admin    : menambah restoran dan menghapus restoran.

    Program ini memiliki 4 class, yaitu:
        - MainMenu   : Class utama yang berisi method main dan method-method untuk menghandle input dari user.
        - User       : Class yang berisi data-data user, seperti nama, nomor telepon, email, lokasi, dan role.
        - Restaurant : Class yang berisi data-data restoran, seperti nama dan menu.
        - Menu       : Class yang berisi data-data menu, seperti nama makanan dan harga.

    Program ini mempunyai error handling untuk input yang tidak valid, seperti tanggal yang tidak valid, harga yang tidak valid, dan lain-lain.
 */

package assignments.assignment2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    // Scanner for input
    private static final Scanner input = new Scanner(System.in);

    // List of restaurants, users, orders, and menus
    private static ArrayList<Restaurant> restoList = new ArrayList<>();
    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Order> orderList = new ArrayList<>();
    private static ArrayList<Menu> menuList = new ArrayList<>();

    // User that is currently logged in
    private static User userLoggedIn;

    // Main method
    public static void main(String[] args) {
        restoList = new ArrayList<>();
        initUser();
        boolean programRunning = true;
        initUser();
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            // Handle user input
            // 1. Login
            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                userLoggedIn = getUser(nama, noTelp);
                
                // If user is not found
                if(userLoggedIn == null){
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;

                // If user is found
                } else {
                    System.out.print("Selamat datang, " + userLoggedIn.getNama() + "!");
                }
                boolean isLoggedIn = true;

                // If user is a customer
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
                
                // If user is an admin
                }else{
                    System.out.println("Selamat Datang "+userLoggedIn.getNama()+"!");
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
            
            // 2. Exit
            }else if(command == 2){
                programRunning = false;

            // Invalid command
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

    // Handle buat pesanan
    public static void handleBuatPesanan(){
        System.out.println("--------------Buat Pesanan----------------");
        Menu[] items;
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList) {
                // If the restaurant is found
                if(resto.getNama().equalsIgnoreCase(nama)){
                    currentResto = resto;
                    break;
                }
            }

            // If the restaurant is not found
            if (currentResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            // Input order date and validate it
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggal = input.nextLine();

            // Validate the order date using regex
            if (tanggal.length() != 10 || !tanggal.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            // Input the number of items and validate it
            System.out.print("Jumlah Pesanan: ");
            String strJumlahPesanan = input.nextLine();
            int jumlahPesanan;

            // Validate the number of items
            try {
                jumlahPesanan = Integer.parseInt(strJumlahPesanan);
                if (jumlahPesanan < 1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Jumlah pesanan tidak valid!\n");
                continue;
            }
            boolean found = false;

            // Input the items and validate them
            items = new Menu[jumlahPesanan];
            System.out.println("Order:");
            for (int i = 0; i < jumlahPesanan; i++) {
                String makanan = input.nextLine();
                for (Menu menu : currentResto.getMenu()) {
                    // If the item is found
                    if(menu.getNamaMakanan().equals(makanan)){
                        items[i] = menu;
                        found = true;
                    }
                }
            }

            // If the item is not found
            if (!found) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }

            // Generate the order ID and add the order to the order list
            String orderID = generateOrderID(nama, tanggal, userLoggedIn.getNomorTelepon());

            // Calculate the delivery fee based on the user's location
            int ongkir = 0;
            if (userLoggedIn.getLokasi().equals("P")) {
                ongkir = 10000;
            } else if (userLoggedIn.getLokasi().equals("U")) {
                ongkir = 20000;
            } else if (userLoggedIn.getLokasi().equals("T")) {
                ongkir = 35000;
            } else if (userLoggedIn.getLokasi().equals("S")) {
                ongkir = 40000;
            } else if (userLoggedIn.getLokasi().equals("B")) {
                ongkir = 60000;
            }

            // Create the order and add it to the order list
            Order newOrder = new Order(orderID, tanggal, ongkir, currentResto, items);
            orderList.add(newOrder);
            System.out.print("Pesanan dengan ID " + orderID + " diterima!");
            return;
        }
    }

    // Handle cetak bill
    public static void handleCetakBill(){
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();
            System.out.println("\nBill:");
            for (Order currentOrder : orderList) {
                // If the order is found
                if(currentOrder.getOrderId().equals(orderID)){
                    // Print the order details
                    System.out.println("Order ID: " + currentOrder.getOrderId());
                    System.out.println("Tanggal Pemesanan: " + currentOrder.getTanggal());
                    System.out.println("Restaurant: " + currentOrder.getResto().getNama());
                    System.out.println("Lokasi Pengiriman: " + userLoggedIn.getLokasi());
                    if (currentOrder.getOrderFinished() == false) {
                        System.out.println("Status Pengiriman: Not Finished");
                    } else {
                        System.out.println("Status Pengiriman: Finished");
                    }
                    System.out.println("Pesanan:");
                    double totalHarga = 0;
                    for (Menu menu : currentOrder.getItems()) {
                        System.out.println("- " + menu.getNamaMakanan() + " " + (int) menu.getHarga());
                        totalHarga += menu.getHarga();
                    }
                    System.out.println("Biaya Ongkos Kirim: Rp " + currentOrder.getOngkir());
                    System.out.print("Total Biaya: Rp " + (int)(currentOrder.getOngkir() + totalHarga));
                    return;
                }
            }

            // If the order is not found
            System.out.println("Order ID tidak dapat ditemukan.\n");
            continue;
        }
    }

    // Handle lihat menu
    public static void handleLihatMenu(){
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList) {
                // If the restaurant is found
                if(resto.getNama().equals(nama)){
                    currentResto = resto;
                    break;
                }
            }

            // If the restaurant is not found
            if (currentResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Menu:");

            // Sort the menu based on price and name
            List<Menu> sortedMenu = new ArrayList<>(currentResto.getMenu());
            for (int i = 0; i < sortedMenu.size() - 1; i++) {
                for (int j = 0; j < sortedMenu.size() - i - 1; j++) {
                    // Compare the elements
                    Menu menu1 = sortedMenu.get(j);
                    Menu menu2 = sortedMenu.get(j + 1);
                    if (menu1.getHarga() > menu2.getHarga()) {
                        // Swap the elements based on price
                        sortedMenu.set(j, menu2);
                        sortedMenu.set(j + 1, menu1);
                    } else if (menu1.getHarga() == menu2.getHarga()) {
                        if (menu1.getNamaMakanan().compareTo(menu2.getNamaMakanan()) > 0) {
                            // Swap the elements based on name
                            sortedMenu.set(j, menu2);
                            sortedMenu.set(j + 1, menu1);
                        }
                    }
                }
            }
            for (int i = 0; i < sortedMenu.size(); i++) {
                System.out.print("\n" + (i + 1) + ". " + sortedMenu.get(i).getNamaMakanan() + " " + (int) sortedMenu.get(i).getHarga());
            }
            return;
        }
    }

    // Handle update status pesanan
    public static void handleUpdateStatusPesanan(){
        System.out.println("--------------Update Status Pesanan----------------");
        while (true) {
            boolean valid = false;
            boolean orderIDValid = false;
            System.out.print("Order ID: ");
            String orderID = input.nextLine();
            for (Order currentOrder : orderList) {
                // If the order is found
                if(currentOrder.getOrderId().equals(orderID)){
                    orderIDValid = true;
                    System.out.print("Status: ");
                    String status = input.nextLine();

                    // If the status is valid
                    if (status.equals("Selesai")) {
                        if (currentOrder.getOrderFinished() == false) {
                            valid = true;
                            currentOrder.setOrderFinished(true);
                        } else {
                            break;
                        }

                    // If the status is not valid
                    } else {
                        break;
                    }
                }
            }

            // If the orderID is not found
            if (orderIDValid == false) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            // Print the status of the order if the status is valid
            if (valid == true) {
                System.out.print("Status pesanan dengan ID " + orderID + " berhasil diupdate!");
                return;

            // If the status is not valid
            } else {
                System.out.print("Status pesanan dengan ID" + orderID + " tidak berhasil diupdate!");
                return;
            }
        }
    }

    // Handle adding new restaurant
    public static void handleTambahRestoran(){
        System.out.println("--------------Tambah Restoran----------------");
        while (true) {
            menuList = new ArrayList<>();
            boolean validFormat = true;
            boolean validInt = true;
            System.out.print("Nama: ");
            String nama = input.nextLine();

            // If length of nama less than 4
            if (nama.length() < 4) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            for (Restaurant currentResto : restoList) {
                // If the resto has the same name as one in list
                if(currentResto.getNama().equals(nama)){
                    System.out.println("Restoran dengan nama " + nama + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n");
                    validFormat = false;
                    break;
                }
            }
            if (validFormat == false) {
                continue;
            }
            
            // Input the number of makanan
            System.out.print("Jumlah Makanan: ");
            int jumlahMakanan = input.nextInt();
            input.nextLine();
            for (int i = 0; i < jumlahMakanan; i++) {
                String makananAndHarga = input.nextLine();
                ArrayList<String> makananAndHargaList = new ArrayList<String>(Arrays.asList(makananAndHarga.split(" ")));

                // If the length of arraylist is less than 2
                if (makananAndHargaList.size() < 2) {
                    validFormat = false;
                    break;
                }

                // Take the last member of arraylist
                String strHarga = makananAndHargaList.get(makananAndHargaList.size() - 1);
                int harga;

                // Change to Int from String
                try {
                    harga = Integer.parseInt(strHarga);
                } catch (NumberFormatException e) {
                    validInt = false;
                    continue;
                }

                // Remove the last member of arraylist
                makananAndHargaList.remove(makananAndHargaList.size() - 1);

                // Combine all the member array and add to menuList
                String makanan = String.join(" ", makananAndHargaList);
                Menu newMenu = new Menu(makanan, harga);
                menuList.add(newMenu);
            }

            // If not Integer
            if (validInt == false) {
                System.out.println("Harga menu harus bilangan bulat!\n");
                menuList.clear();
                continue;
            }

            // If format is wrong
            if (validFormat == false) {
                System.out.println("Format makanan dan harga tidak valid!\n");
                menuList.clear();
                continue;
            }

            // Add newResto to restoList
            Restaurant newResto = new Restaurant(nama);
            newResto.setMenu(menuList);
            restoList.add(newResto);
            System.out.print("Restaurant " + nama + " berhasil terdaftar.");
            return;
        }
    }

    // Handle removing restaurant
    public static void handleHapusRestoran(){
        System.out.println("--------------Hapus Restoran----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            for (Restaurant currentResto : restoList) {
                // If the resto has the same name as one in list, remove it
                if(currentResto.getNama().equals(nama)){
                    restoList.remove(currentResto);
                    System.out.print("Restoran berhasil dihapus.");
                    return;
                }
            }
        
        // If resto not in restoList
        System.out.println("Restoran tidak terdaftar pada sistem.\n");
        continue;
        }
    }

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

//     It's The End of The Program
//           Thank You RAC!
//          _      _      _
//        >(.)__ <(.)__ =(.)__
//         (___/  (___/  (___/
//           Here are ducks
//    __                               _              __         
//   / /  ___ __  _____   ___ _  ___  (_)______   ___/ /__ ___ __
//  / _ \/ _ `/ |/ / -_) / _ `/ / _ \/ / __/ -_) / _  / _ `/ // /
// /_//_/\_,_/|___/\__/  \_,_/ /_//_/_/\__/\__/  \_,_/\_,_/\_, / 
//                                                        /___/  