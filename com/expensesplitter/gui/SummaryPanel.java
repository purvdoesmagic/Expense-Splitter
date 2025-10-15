package com.expensesplitter.gui;

import com.expensesplitter.model.Group;
import com.expensesplitter.model.Person;
import com.expensesplitter.service.SplitService;
import com.expensesplitter.ui.UITheme;
import com.expensesplitter.ui.components.RoundedPanel;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class SummaryPanel extends RoundedPanel {
    private final JTextArea balancesTextArea;
    private final JTextArea transactionsTextArea;
    private final JLabel groupInfoLabel;

    public SummaryPanel() {
        setLayout(new BorderLayout(10, 15));
        setBorder(UITheme.PADDING);

        // --- Title and Group Info ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Summary", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_BOLD);
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        
        groupInfoLabel = new JLabel("Group: ... | Members: 0 | Total Spent: 0.00", SwingConstants.CENTER);
        groupInfoLabel.setFont(UITheme.FONT_REGULAR);
        groupInfoLabel.setForeground(UITheme.TEXT_COLOR);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(UITheme.BORDER_COLOR);
        separator.setBackground(UITheme.PANEL_BACKGROUND);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(groupInfoLabel, BorderLayout.CENTER);
        topPanel.add(separator, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        add(topPanel, BorderLayout.NORTH);
        
        // Content with custom scrollpanes
        balancesTextArea = createReadOnlyTextArea();
        transactionsTextArea = createReadOnlyTextArea();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                createTitledScrollPane("Final Balances", balancesTextArea),
                createTitledScrollPane("Settlement Plan", transactionsTextArea));
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);
        splitPane.setDividerSize(10);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JScrollPane createTitledScrollPane(String title, JComponent content) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_REGULAR.deriveFont(Font.BOLD));
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        UITheme.styleScrollPane(scrollPane);
        return scrollPane;
    }

    private JTextArea createReadOnlyTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(UITheme.FONT_MONO);
        textArea.setOpaque(false);
        textArea.setForeground(UITheme.TEXT_COLOR);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        return textArea;
    }

    public void updateSummary(Group group, SplitService splitService) {
        groupInfoLabel.setText(String.format("Group: %s   |   Members: %d   |   Total Spent: %.2f",
                group.getName(), group.getMembers().size(), group.getTotalExpenses()));
        
        Map<Person, Double> balances = splitService.calculateBalances(group);
        StringBuilder balanceText = new StringBuilder();
        balances.forEach((person, balance) -> {
            String status = Math.abs(balance) < 0.01 ? "is settled" : (balance > 0 ? "gets back" : "owes");
            balanceText.append(String.format("%-20s %-10s %10.2f\n", person.getName(), status, Math.abs(balance)));
        });
        balancesTextArea.setText(balanceText.toString());

        List<String> transactions = splitService.settleDebts(balances);
        transactionsTextArea.setText(transactions.isEmpty() ? "All debts are settled!" : String.join("\n", transactions));
    }
}

