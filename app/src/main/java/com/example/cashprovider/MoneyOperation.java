package com.example.cashprovider;

import java.io.Serializable;

/**
 * MoneyOperation
 * Represents a transaction with money
 * Last-Change: 02.01.2022
 *
 * @author Antonius Metry Saad
 */
public class MoneyOperation  implements Serializable {
    protected String date;
    protected String title;
    protected double amount;

    public MoneyOperation(String date, String title, double amount) {
        this.date = date;
        this.title = title;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
