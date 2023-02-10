package com.example.cashprovider;

import java.io.Serializable;

/**
 * Income
 * Represents an income transaction
 * Last-Change: 02.01.2022
 *
 * @author Antonius Metry Saad
 */
public class Income extends MoneyOperation implements Serializable {
    public Income(String date, String title, double amount) {
        super(date, title, amount);
    }
}
