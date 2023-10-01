package com.example.store.Backend.expenses;

import jakarta.persistence.Id;

public class Expense {
    @Id
    private int id;
    private String name;
    private double price;
    private String date;
    
}
