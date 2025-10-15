package com.expensesplitter.gui;

import com.expensesplitter.model.Person;
import com.expensesplitter.ui.UITheme;
import com.expensesplitter.ui.components.RoundedPanel;
import com.expensesplitter.ui.components.RoundedTextField;
import javax.swing.*;
import java.awt.*;

public class MemberPanel extends RoundedPanel {
    private final MainFrame mainFrame;
    private final DefaultListModel<String> memberListModel;
    private final JTextField memberNameField;

    public MemberPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 15));
        setBorder(UITheme.PADDING);

        // --- Centered title and separator ---
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Group Members", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_BOLD);
        titleLabel.setForeground(UITheme.TEXT_COLOR);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(UITheme.BORDER_COLOR);
        separator.setBackground(UITheme.PANEL_BACKGROUND);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(separator, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        add(titlePanel, BorderLayout.NORTH);

        // Member List with custom scrollbar
        memberListModel = new DefaultListModel<>();
        JList<String> memberList = new JList<>(memberListModel);
        memberList.setBackground(UITheme.PANEL_BACKGROUND);
        memberList.setForeground(UITheme.TEXT_COLOR);
        memberList.setFont(UITheme.FONT_REGULAR);
        JScrollPane scrollPane = new JScrollPane(memberList);
        UITheme.styleScrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Input Panel with rounded text field
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setOpaque(false);
        
        memberNameField = new RoundedTextField(10);
        memberNameField.addActionListener(e -> addMember());
        
        JButton addMemberButton = new JButton("Add");
        UITheme.styleButton(addMemberButton);
        addMemberButton.addActionListener(e -> addMember());

        inputPanel.add(memberNameField, BorderLayout.CENTER);
        inputPanel.add(addMemberButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addMember() {
        String name = memberNameField.getText().trim();
        if (!name.isEmpty() && mainFrame.getGroup().getMembers().stream().noneMatch(p -> p.getName().equalsIgnoreCase(name))) {
            mainFrame.getGroup().addMember(new Person(name));
            mainFrame.refreshAllPanels();
            memberNameField.setText("");
        }
        memberNameField.requestFocusInWindow();
    }

    public void updateMemberList() {
        memberListModel.clear();
        mainFrame.getGroup().getMembers().forEach(p -> memberListModel.addElement(p.getName()));
    }
}

