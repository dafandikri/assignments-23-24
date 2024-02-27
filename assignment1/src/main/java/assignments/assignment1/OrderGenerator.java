package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String CHARACTERSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        namaRestoran = namaRestoran.replace(" ", "");
        String namaRestoranID = namaRestoran.substring(0, 3);

        String tanggalOrderID = tanggalOrder.replace("/", "");

        int total = 0;
        for (char c : noTelepon.toCharArray()) {
            total += Character.getNumericValue(c);
        }
        String noTeleponID = String.format("%02d", total % 100);

        String orderID = namaRestoranID + tanggalOrderID + noTeleponID;

        int sumOdd = 0;
        int sumEven = 0;
        for (int i = 0; i < orderID.length(); i++) {
            if (i % 2 == 0) {
                sumEven += CHARACTERSET.indexOf(orderID.charAt(i));
            } else {
                sumOdd += CHARACTERSET.indexOf(orderID.charAt(i));
            }
        }
    
        char checksumOdd = CHARACTERSET.charAt(sumOdd % 36);
        char checksumEven = CHARACTERSET.charAt(sumEven % 36);
    
        orderID = orderID + checksumOdd + checksumEven;
        return orderID;
    }


    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        return "Bill";
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();
            System.out.println("--------------------------------------------");

            // Input
            System.out.print("Pilihan menu: ");
            int pilihan = input.nextInt();
            input.nextLine();

            if (pilihan == 1) {
                System.out.println("Nama restoran: ");
                String namaRestoran = input.nextLine();

                if (namaRestoran.length() < 4) {
                    System.out.println("Nama restoran harus memiliki minimal 4 karakter!");
                    return;
                }
        
                System.out.println("Tanggal pemesanan: ");
                String tanggalOrder = input.nextLine();
        
                System.out.println("No. telepon: ");
                String noTelepon = input.nextLine();
        
                String orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                System.out.println(orderID);
            } else if (pilihan == 2) {
                System.out.println("Test");
            } else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;
            } else {
                System.out.println("Pilihan tidak valid!");
            }
        }
    }

    
}
