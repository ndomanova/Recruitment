package com.awin.recruitment.entities;

public class Product {

    private String name;
    private double price;


    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "NAME: "+name+" PRICE: "+price+"\r\n";
    }
}
