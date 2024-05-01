package assignments.assignment3.systemCLI;

import java.util.Scanner;

import assignments.assignment3.User;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.Order;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: Extends Abstract yang diberikan
public class AdminSystemCLI extends UserSystemCLI{

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
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
                if(currentResto.getNama().toLowerCase().equals(nama.toLowerCase())){
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

    protected void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        System.out.println("--------------Hapus Restoran----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String nama = input.nextLine();
            for (Restaurant currentResto : restoList) {
                // If the resto has the same name as one in list, remove it
                if(currentResto.getNama().toLowerCase().equals(nama.toLowerCase())){
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

    public AdminSystemCLI() {
        super();
        this.restoList = new ArrayList<>();
        this.orderList = new ArrayList<>();
    }
}
