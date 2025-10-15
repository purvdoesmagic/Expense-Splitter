package com.expensesplitter.model;

import java.io.Serializable;
import java.util.List;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String description;
    private final double amount;
    private final Person paidBy;
    private final List<Person> splitWith;

    public Expense(String description, double amount, Person paidBy, List<Person> splitWith) {
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitWith = splitWith;
    }

    public double getAmount() {
        return amount;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    public List<Person> getSplitWith() {
        return splitWith;
    }

    public double getSharePerPerson() {
        if (splitWith == null || splitWith.isEmpty()) {
            return 0;
        }
        return amount / splitWith.size();
    }
}