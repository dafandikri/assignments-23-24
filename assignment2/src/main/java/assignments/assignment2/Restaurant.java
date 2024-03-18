package assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;

public class Restaurant {
    // Atributes
    private String nama;
    private ArrayList<Menu> menu;

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

    // Methods
}
