package com.expensesplitter.gui;

import com.expensesplitter.model.Expense;
import com.expensesplitter.model.Person;
import com.expensesplitter.ui.UITheme;
import com.expensesplitter.ui.components.RoundedPanel;
import com.expensesplitter.ui.components.RoundedTextField;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExpensePanel extends RoundedPanel {
    private final MainFrame mainFrame;
    private final JTextField descriptionField, amountField;
    private final JComboBox<Person> paidByComboBox;
    private final JPanel splitWithPanel;

    public ExpensePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 15));
        setBorder(UITheme.PADDING);

        // --- Centered title and separator ---
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Add New Expense", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_BOLD);
        titleLabel.setForeground(UITheme.TEXT_COLOR);

        JSeparator separator = new JSeparator();
        separator.setForeground(UITheme.BORDER_COLOR);
        separator.setBackground(UITheme.PANEL_BACKGROUND);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(separator, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        
        descriptionField = new RoundedTextField(15);
        amountField = new RoundedTextField(15);
        paidByComboBox = new JComboBox<>();
        splitWithPanel = new JPanel();

        paidByComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Person) {
                    setText(((Person) value).getName());
                } else {
                    setText("--- Select Payer ---");
                }
                setBackground(isSelected ? UITheme.ACCENT_COLOR : UITheme.PANEL_BACKGROUND);
                setForeground(isSelected ? Color.BLACK : UITheme.TEXT_COLOR);
                return this;
            }
        });

        setupFormRow(formPanel, 0, "Description:", descriptionField);
        setupFormRow(formPanel, 1, "Amount:", amountField);
        setupFormRow(formPanel, 2, "Paid By:", paidByComboBox);
        
        setupSplitWithPanel(formPanel, 3);
        add(formPanel, BorderLayout.CENTER);

        // Add Button
        JButton addExpenseButton = new JButton("Add Expense");
        UITheme.styleButton(addExpenseButton);
        addExpenseButton.addActionListener(e -> addExpense());
        amountField.addActionListener(e -> addExpense());
        add(addExpenseButton, BorderLayout.SOUTH);
    }
    
    private void setupFormRow(JPanel p, int y, String labelText, JComponent c) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel label = new JLabel(labelText);
        UITheme.styleLabel(label);
        gbc.gridx = 0; gbc.gridy = y;
        p.add(label, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        if (c instanceof JComboBox) {
            c.setFont(UITheme.FONT_REGULAR);
            c.setBackground(UITheme.PANEL_BACKGROUND);
        }
        p.add(c, gbc);
    }
    
    private void setupSplitWithPanel(JPanel parent, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel splitWithLabel = new JLabel("Split With:");
        UITheme.styleLabel(splitWithLabel);
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.NORTHWEST;
        parent.add(splitWithLabel, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        splitWithPanel.setLayout(new BoxLayout(splitWithPanel, BoxLayout.Y_AXIS));
        splitWithPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(splitWithPanel);
        UITheme.styleScrollPane(scrollPane);
        scrollPane.setPreferredSize(new Dimension(150, 120));
        parent.add(scrollPane, gbc);
    }

    private void addExpense() {
        try {
            String desc = descriptionField.getText().trim();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double amount = Double.parseDouble(amountField.getText().trim());
            Person paidBy = (Person) paidByComboBox.getSelectedItem();

            if (paidBy == null) {
                JOptionPane.showMessageDialog(this, "Please select who paid the expense.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Person> splitWith = new ArrayList<>();
            for (Component comp : splitWithPanel.getComponents()) {
                if (comp instanceof JCheckBox && ((JCheckBox) comp).isSelected()) {
                    splitWith.add((Person) ((JCheckBox) comp).getClientProperty("person"));
                }
            }

            if (splitWith.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You must split the expense with at least one person.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (amount <= 0) {
                 JOptionPane.showMessageDialog(this, "Amount must be greater than zero.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            mainFrame.getGroup().addExpense(new Expense(desc, amount, paidBy, splitWith));
            mainFrame.refreshAllPanels();
            descriptionField.setText("");
            amountField.setText("");
            descriptionField.requestFocusInWindow();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updatePayerAndSplitMembers() {
        Vector<Person> members = new Vector<>();
        members.add(null); 
        members.addAll(mainFrame.getGroup().getMembers());

        paidByComboBox.setModel(new DefaultComboBoxModel<>(members));
        
        splitWithPanel.removeAll();
        for (Person p : mainFrame.getGroup().getMembers()) {
            JCheckBox cb = new JCheckBox(p.getName());
            cb.setFont(UITheme.FONT_REGULAR);
            cb.setOpaque(false);
            cb.setForeground(UITheme.TEXT_COLOR);
            cb.setSelected(true);
            cb.putClientProperty("person", p);
            splitWithPanel.add(cb);
        }
        splitWithPanel.revalidate();
        splitWithPanel.repaint();
    }
}

