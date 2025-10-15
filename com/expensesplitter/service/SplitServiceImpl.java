package com.expensesplitter.service;

import com.expensesplitter.model.Expense;
import com.expensesplitter.model.Group;
import com.expensesplitter.model.Person;

import java.util.*;
import java.util.stream.Collectors;

public class SplitServiceImpl implements SplitService {

    @Override
    public Map<Person, Double> calculateBalances(Group group) {
        Map<Person, Double> balances = new HashMap<>();
        for (Person member : group.getMembers()) {
            balances.put(member, 0.0);
        }

        for (Expense expense : group.getExpenses()) {
            Person paidBy = expense.getPaidBy();
            double amount = expense.getAmount();
            List<Person> splitWith = expense.getSplitWith();
            double share = expense.getSharePerPerson();

            balances.put(paidBy, balances.get(paidBy) + amount);

            for (Person participant : splitWith) {
                balances.put(participant, balances.get(participant) - share);
            }
        }
        return balances;
    }

    /**
     * This is the core logic. It calculates the simplest way to settle all debts.
     * It continues running until all creditors have been paid.
     */
    @Override
    public List<String> settleDebts(Map<Person, Double> balances) {
        // 1. Separate people who are owed money (creditors) from people who owe money (debtors)
        Map<Person, Double> creditors = balances.entrySet().stream()
                .filter(entry -> entry.getValue() > 0.01) // Filter for positive balances
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Person, Double> debtors = balances.entrySet().stream()
                .filter(entry -> entry.getValue() < -0.01) // Filter for negative balances
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        List<String> transactions = new ArrayList<>();

        Iterator<Map.Entry<Person, Double>> creditorIter = creditors.entrySet().iterator();
        Iterator<Map.Entry<Person, Double>> debtorIter = debtors.entrySet().iterator();

        Map.Entry<Person, Double> creditorEntry = creditorIter.hasNext() ? creditorIter.next() : null;
        Map.Entry<Person, Double> debtorEntry = debtorIter.hasNext() ? debtorIter.next() : null;

        // 2. THIS IS THE KEY LOOP: It continues as long as there is someone who needs to be paid
        //    AND someone who needs to pay. It does NOT stop after one transaction.
        while (creditorEntry != null && debtorEntry != null) {
            Person creditor = creditorEntry.getKey();
            double credit = creditorEntry.getValue();
            Person debtor = debtorEntry.getKey();
            double debt = -debtorEntry.getValue();

            double payment = Math.min(credit, debt);

            // 3. A new transaction is added to the list for every payment.
            transactions.add(String.format("%s pays %s: %.2f", debtor.getName(), creditor.getName(), payment));

            creditorEntry.setValue(credit - payment);
            debtorEntry.setValue(-(debt - payment));

            // Move to the next debtor if the current one has paid off their debt
            if (Math.abs(debtorEntry.getValue()) < 0.01) {
                debtorEntry = debtorIter.hasNext() ? debtorIter.next() : null;
            }

            // Move to the next creditor if the current one has been fully paid back
            if (Math.abs(creditorEntry.getValue()) < 0.01) {
                creditorEntry = creditorIter.hasNext() ? creditorIter.next() : null;
            }
        }
        // 4. The COMPLETE list of all necessary transactions is returned.
        return transactions;
    }
}

