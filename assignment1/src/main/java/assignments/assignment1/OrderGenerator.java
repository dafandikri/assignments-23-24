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
        namaRestoran = namaRestoran.replace(" ", "");
        namaRestoran = namaRestoran.toUpperCase();
        String namaRestoranID = namaRestoran.substring(0, 4);

        String tanggalOrderID = tanggalOrder.replace("/", "");

        int total = 0;
        for (char c : noTelepon.toCharArray()) {
            total += Character.getNumericValue(c);
        }
        String noTeleponID = String.format("%02d", total % 100);
        String orderID = checksum(namaRestoranID, tanggalOrderID, noTeleponID);
        return orderID;
    }

    public static String checksum(String namaRestoranID, String tanggalOrderID, String noTeleponID) {
        String orderID = namaRestoranID + tanggalOrderID + noTeleponID;
        String CHARACTERSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
    
        orderID = orderID + checksumEven + checksumOdd;
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
        lokasi = lokasi.toUpperCase();

        String namaRestoran = OrderID.substring(0, 4);
        String tanggalOrder = OrderID.substring(4, 12);
        String noTelepon = OrderID.substring(12, 14);

        int biayaKirim = 0;
        if (lokasi.equals("P")) {
            biayaKirim = 10000;
        } else if (lokasi.equals("U")) {
            biayaKirim = 20000;
        } else if (lokasi.equals("T")) {
            biayaKirim = 35000;
        } else if (lokasi.equals("S")) {
            biayaKirim = 40000;
        } else if (lokasi.equals("B")) {
            biayaKirim = 60000;
        }

        tanggalOrder = tanggalOrder.substring(0, 2) + "/" + tanggalOrder.substring(2, 4) + "/" + tanggalOrder.substring(4, 8);

        String biayaKirimFormatted = String.format("Rp %,d", biayaKirim).replace(',', '.');
        
        return "Bill:\nOrder ID: " + OrderID + "\nTanggal Pemesanan: " + tanggalOrder + "\nLokasi Pengiriman: " + lokasi + "\nBiaya Ongkos Kirim: " + biayaKirimFormatted;
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
                System.out.print("Nama restoran: ");
                String namaRestoran = input.nextLine();

                if (namaRestoran.length() < 4) {
                    System.out.println("Nama restoran harus memiliki minimal 4 karakter!");
                    continue;
                }
        
                System.out.print("Tanggal pemesanan: ");
                String tanggalOrder = input.nextLine();

                if (tanggalOrder.length() != 10) {
                    System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                    continue;
                }
        
                System.out.print("No. telepon: ");
                String noTelepon = input.nextLine();

                if (!noTelepon.matches("\\d+")) {
                    System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                    continue;
                }
        
                String orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                System.out.println(orderID);

            } else if (pilihan == 2) {
                System.out.print("Order ID: ");
                String orderID = input.nextLine();
                
                if (orderID.length() != 16) {
                    System.out.println("Order ID harus memiliki 16 karakter!");
                    continue;
                }

                String namaRestoran = orderID.substring(0, 4);
                String tanggalOrder = orderID.substring(4, 12);
                String noTelepon = orderID.substring(12, 14);

                if (!checksum(namaRestoran, tanggalOrder, noTelepon).equals(orderID)) {
                    System.out.println("Order ID tidak valid!");
                    continue;
                }

                String validLocations = "PUTSB";

                System.out.print("Lokasi pengiriman: ");
                String lokasi = input.nextLine();
                lokasi = lokasi.toUpperCase();

                if (validLocations.indexOf(lokasi) == -1) {
                    System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                    continue;
                }

                String bill = generateBill(orderID, lokasi);
                System.out.println(bill);
                
            } else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;

            } else {
                System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
