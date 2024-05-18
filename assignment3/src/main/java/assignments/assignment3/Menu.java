package assignments.assignment3;

public class Menu {
    // Atributes
    private String namaMakanan;
    private double harga;

    // Constructor
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    // Setters and Getters
    public void setNamaMakanan(String namaMakanan){
        this.namaMakanan = namaMakanan;
    }

    public String getNamaMakanan(){
        return this.namaMakanan;
    }

    public void setHarga(double harga){
        this.harga = harga;
    }

    public double getHarga(){
        return this.harga;
    }

    public String getNama() {
        return namaMakanan;
    }
}
