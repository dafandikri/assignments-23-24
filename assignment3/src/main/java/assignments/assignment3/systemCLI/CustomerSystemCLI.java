package assignments.assignment3.systemCLI;

import java.util.Scanner;

import assignments.assignment3.User;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.Order;
import java.util.List;
import java.util.ArrayList;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI{

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("--------------Buat Pesanan----------------");
        Menu[] items;
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList) {
                // If the restaurant is found
                if(resto.getNama().toLowerCase().equals(nama.toLowerCase())){
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

    void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
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

    void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList) {
                // If the restaurant is found
                if(resto.getNama().toLowerCase().equals(nama.toLowerCase())){
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

    void handleBayarBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
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

            // If the orderIF is not found
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

    void handleCekSaldo(){
        // TODO: Implementasi method untuk handle ketika customer ingin mengecek saldo yang dimiliki
    }
    
    public CustomerSystemCLI() {
        super();
        this.restoList = new ArrayList<>();
        this.orderList = new ArrayList<>();
    }

    public void setUserLoggedIn(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }
}
