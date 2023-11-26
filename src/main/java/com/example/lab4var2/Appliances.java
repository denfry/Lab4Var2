package com.example.lab4var2;

public class Appliances {
    private String name;
    private String company;
    private double price;
    private int capacity;
    private boolean isOn;
    public Appliances(String name, String company, double price, int capacity, boolean isOn) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.capacity = capacity;
        this.isOn = isOn;
    }
    public String getName() {
        return name;
    }
    public String getCompany() {
        return company;
    }
    public double getPrice() {
        return price;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean getIsOn() {
        return isOn;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }
    public String toString() {
        return "Название: " + getName() + ", " + "Производитель: " + getCompany() + ", " +  "Цена: " + getPrice() + ", \n"
                + "" + "Вместимость: " + getCapacity() + ", " +  "Включен: " + getIsOn();
    }
}
