package com.awin.recruitment.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RawTransaction {

    private final int id;
    private final Date date;
    private final List<Product> listOfProducts;

    public RawTransaction(int id, Date date, List<Product> list) {
        this.id = id;
        this.date = date;
        this.listOfProducts = list;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }


}
