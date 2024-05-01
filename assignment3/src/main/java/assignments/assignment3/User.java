package assignments.assignment3;

import java.util.ArrayList;
import java.util.Arrays;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

public class User {
    // Atributes
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    public String role;
    private ArrayList<Order> orderHistory;
    private DepeFoodPaymentSystem payment;
    private long saldo;

    // Constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = payment;
        this.saldo = saldo;
        this.orderHistory = new ArrayList<Order>();
    }

    // Setters and Getters
    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return this.nama;
    }

    public void setNomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }

    public String getNomorTelepon(){
        return this.nomorTelepon;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getLokasi(){
        return this.lokasi;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

    public void setOrderHistory(ArrayList<Order> orderHistory){
        this.orderHistory = orderHistory;
    }

    public ArrayList<Order> getOrderHistory(){
        return this.orderHistory;
    }

    public void setSaldo(long saldo){
        this.saldo = saldo;
    }

    public long getSaldo(){
        return this.saldo;
    }
}
