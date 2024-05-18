package assignments.assignment3;

import java.util.ArrayList;
import java.util.Arrays;

public class Restaurant {
    // Atributes
    private String nama;
    private ArrayList<Menu> menu;
    private long saldo;

    // Constructor
    public Restaurant(String nama){
        this.nama = nama;
        this.menu = new ArrayList<Menu>();
    }
    
    // Setters and Getters
    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return this.nama;
    }

    public void setMenu(ArrayList<Menu> menu){
        this.menu = menu;
    }

    public ArrayList<Menu> getMenu(){
        return this.menu;
    }

    public void setSaldo(long saldo){
        this.saldo = saldo;
    }

    public long getSaldo(){
        return this.saldo;
    }

    public void addMenu(Menu menu){
        this.menu.add(menu);
    }
}
