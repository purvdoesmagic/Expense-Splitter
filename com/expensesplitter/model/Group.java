package com.expensesplitter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<Person> members;
    private final List<Expense> expenses;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Person> getMembers() {
        return members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public String getName() {
        return name;
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
}