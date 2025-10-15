package com.expensesplitter.service;

import com.expensesplitter.model.Group;
import com.expensesplitter.model.Person;
import java.util.List;
import java.util.Map;

public interface SplitService {

    Map<Person, Double> calculateBalances(Group group);
    
    List<String> settleDebts(Map<Person, Double> balances);

}