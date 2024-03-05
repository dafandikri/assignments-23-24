/*
    Nama        : Erdafa Andikri
    NPM         : 2306244993
    Kode Asdos  : RAC

    Program ini dibuat untuk memenuhi tugas TP 1 mata kuliah DDP 2.

    Program ini merupakan sebuah aplikasi sederhana untuk menghasilkan Order ID dan Bill dari sebuah restoran.
    Program ini memiliki 3 menu:
        1. Generate Order ID
        2. Generate Bill
        3. Keluar
    
    Menu 1 digunakan untuk menghasilkan Order ID dari nama restoran, tanggal pemesanan, dan nomor telepon.
    Menu 2 digunakan untuk menghasilkan Bill dari Order ID yang valid dan lokasi pengiriman.
    Menu 3 digunakan untuk keluar dari program.

    Program ini menggunakan metode checksum untuk memastikan validitas Order ID.
    Program juga mempunyai error handling untuk memastikan input yang diberikan valid.
 */

package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    // Method to show the menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    // Method to generate order ID from restaurant name, order date, and phone number
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

    // Method to generate bill from order ID and location
    public static String generateBill(String OrderID, String lokasi){
        lokasi = lokasi.toUpperCase();

        // Slice the order ID to get the restaurant's name, order date, and phone number
        String namaRestoran = OrderID.substring(0, 4);
        String tanggalOrder = OrderID.substring(4, 12);
        String noTelepon = OrderID.substring(12, 14);

        // Using if-else statements to determine the delivery cost based on the location
        String biayaKirim = "";
        if (lokasi.equals("P")) {
            biayaKirim = "Rp 10.000";
        } else if (lokasi.equals("U")) {
            biayaKirim = "Rp 20.000";
        } else if (lokasi.equals("T")) {
            biayaKirim = "Rp 35.000";
        } else if (lokasi.equals("S")) {
            biayaKirim = "Rp 40.000";
        } else if (lokasi.equals("B")) {
            biayaKirim = "Rp 60.000";
        }

        // Format the order date to DD/MM/YYYY
        String hari = tanggalOrder.substring(0, 2);
        String bulan = tanggalOrder.substring(2, 4);
        String tahun = tanggalOrder.substring(4, 8);
        tanggalOrder = hari + "/" + bulan + "/" + tahun;
        
        // Return the bill
        return "Bill:\nOrder ID: " + OrderID + "\nTanggal Pemesanan: " + tanggalOrder + "\nLokasi Pengiriman: " + lokasi + "\nBiaya Ongkos Kirim: " + biayaKirim + "\n";
    }

    // Main method
    public static void main(String[] args) {
        while (true) {
            // Show the menu
            showMenu();
            System.out.println("--------------------------------------------");

            // Input menu choice
            System.out.print("Pilihan menu: ");
            int pilihan = input.nextInt();
            input.nextLine();

            // Generate order ID
            if (pilihan == 1) {
                while (true) {  
                    // Input restaurant's name
                    System.out.print("Nama restoran: ");
                    String namaRestoran = input.nextLine();

                    // Validate the restaurant's name
                    if (namaRestoran.length() < 4) {
                        System.out.println("Nama restoran harus memiliki minimal 4 karakter!");
                        continue;
                    }
                    
                    // Input order date
                    System.out.print("Tanggal pemesanan: ");
                    String tanggalOrder = input.nextLine();

                    // Validate the order date usin
                    if (tanggalOrder.length() != 10 || !tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                        continue;
                    }
                    
                    // Input phone number
                    System.out.print("No. telepon: ");
                    String noTelepon = input.nextLine();

                    // Validate the phone number using regex if it is a positive integer
                    if (!noTelepon.matches("\\d+")) {
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                        continue;
                    }
                    
                    // Generate the order ID and print it
                    String orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                    System.out.println(orderID);
                    break;
                }
            
            // Generate bill
            } else if (pilihan == 2) {
                while (true) {
                    // Input order ID
                    System.out.print("Order ID: ");
                    String orderID = input.nextLine();
                    
                    // Validate the order ID
                    if (orderID.length() != 16) {
                        System.out.println("Order ID harus memiliki 16 karakter!");
                        continue;
                    }

                    // Slice the order ID to get the restaurant's name, order date, and phone number
                    String namaRestoran = orderID.substring(0, 4);
                    String tanggalOrder = orderID.substring(4, 12);
                    String noTelepon = orderID.substring(12, 14);

                    // Validate the order ID using the checksum
                    if (!checksum(namaRestoran, tanggalOrder, noTelepon).equals(orderID)) {
                        System.out.println("Order ID tidak valid!");
                        continue;
                    }

                    // Define the delivery location
                    String validLocations = "PUTSB";

                    // Input the delivery location and uppercase it
                    System.out.print("Lokasi pengiriman: ");
                    String lokasi = input.nextLine();
                    lokasi = lokasi.toUpperCase();

                    // Validate the delivery location
                    if (validLocations.indexOf(lokasi) == -1) {
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                        continue;
                    }

                    // Generate the bill and print it
                    String bill = generateBill(orderID, lokasi);
                    System.out.println(bill);
                    break;
                }

            // Exit the program
            } else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;

            // Invalid choice
            } else {
                System.out.println("Pilihan tidak valid!");
                continue;
            }
        }
    }
}

//     It's The End of The Program
//           Thank You RAC!
//               ,-._
//             _.-'  '--.
//           .'      _  -`\_
//          / .----.`_.'----'
//          ;/     `
//         /_;
//     ._      ._      ._      ._
// _.-._)`\_.-._)`\_.-._)`\_.-._)`\_.-._
//          Here's a Dolphin
// .-.                                        _                  .-.               
// : :                                       :_;                 : :               
// : `-.  .--.  .-..-. .--.    .--.    ,-.,-..-. .--.  .--.    .-' : .--.  .-..-.  
// : .. :' .; ; : `; :' '_.'  ' .; ;   : ,. :: :'  ..'' '_.'  ' .; :' .; ; : :; :  
// :_;:_;`.__,_;`.__.'`.__.'  `.__,_;  :_;:_;:_;`.__.'`.__.'  `.__.'`.__,_;`._. ;  
//                                                                          .-. :  
//                                                                          `._.'  