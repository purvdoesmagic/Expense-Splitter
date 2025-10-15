package com.expensesplitter.main;

import com.expensesplitter.gui.MainFrame;
import javax.swing.SwingUtilities;

public class ExpenseSplitter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

