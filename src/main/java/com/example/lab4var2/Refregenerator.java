package com.example.lab4var2;

public class Refregenerator extends Appliances{
    private double extra;
    private boolean isDouble;
    public Refregenerator(String name, String company, double price, int capacity, boolean isOn, double extra, String applianceType) {
        super(name, company, price, capacity, applianceType);
        this.extra = extra;
        this.isDouble = isOn;
    }
    public double getExtra() {
        return extra;
    }
    public void setExtra(double extra) {
        this.extra = extra;
    }
    public boolean getIsDouble() {
        return isDouble;
    }
    public void setIsDouble(boolean isDouble) {
        this.isDouble = isDouble;
    }
    @Override
    public String toString() {
        return super.toString() + ", наценка: " + extra + ", включен: " + isDouble;
    }
}
