package com.example.cashprovider;

import java.io.Serializable;

/**
 * Outcome
 * Represents an outcome transaction
 * Last-Change: 02.01.2022
 *
 * @author Antonius Metry Saad
 */
public class Outcome extends MoneyOperation implements Serializable {
    public Outcome(String date, String title, double amount) {
        super(date, title, amount*-1);
    }

    @Override
    public void setAmount(double amount){
        this.amount = amount * -1;
    }
}
