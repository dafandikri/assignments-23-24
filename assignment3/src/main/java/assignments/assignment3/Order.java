package assignments.assignment3;

import java.util.ArrayList;
import java.util.Arrays;

public class Order {
    // Atributes
    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant resto;
    private ArrayList<Menu> items;
    private boolean orderFinished;


    // Constructor
    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.resto = resto;
        this.items = new ArrayList<Menu>(Arrays.asList(items));
        this.orderFinished = false;
    }
    
    // Setters and Getters
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return this.orderId;
    }

    public void setTanggal(String tanggal){
        this.tanggal = tanggal;
    }

    public String getTanggal(){
        return this.tanggal;
    }

    public void setOngkir(int ongkir){
        this.ongkir = ongkir;
    }

    public int getOngkir(){
        return this.ongkir;
    }

    public void setResto(Restaurant resto){
        this.resto = resto;
    }

    public Restaurant getResto(){
        return this.resto;
    }

    public void setItems(Menu[] items){
        this.items = new ArrayList<Menu>(Arrays.asList(items));
    }

    public ArrayList<Menu> getItems(){
        return this.items;
    }

    public void setOrderFinished(boolean orderFinished){
        this.orderFinished = orderFinished;
    }

    public boolean getOrderFinished(){
        return this.orderFinished;
    }
}
