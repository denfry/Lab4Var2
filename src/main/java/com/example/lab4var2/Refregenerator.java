package com.example.lab4var2;

public class Refregenerator extends Appliances{
    private double extra;
    public Refregenerator(String name, String company, double price, int capacity, boolean isOn, double extra) {
        super(name, company, price, capacity, isOn);
        this.extra = extra;
    }
    public double getExtra() {
        return extra;
    }
    public void setExtra(double extra) {
        this.extra = extra;
    }
    @Override
    public String toString() {
        return super.toString() + ", наценка: " + extra;
    }
}
