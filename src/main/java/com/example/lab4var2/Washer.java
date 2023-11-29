package com.example.lab4var2;

public class Washer extends Appliances {
    private double discount;
    public Washer(String name, String company, double price, int capacity, double discount, String applianceType) {
        super(name, company, price, capacity, applianceType);
        this.discount = discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getDiscount() {
        return discount;
    }
    public String toString() {
        return super.toString() + ", скидка: " + discount;
    }
}
