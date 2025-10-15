package com.expensesplitter.ui;

import com.expensesplitter.ui.components.CustomScrollBarUI;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UITheme {

    public static final Color APP_BACKGROUND = new Color(0x1A1A1A);
    public static final Color PANEL_BACKGROUND = new Color(0x252525);
    public static final Color TEXT_COLOR = new Color(0xE0E0E0);
    public static final Color ACCENT_COLOR = new Color(0x32D74B); // Vibrant Green
    public static final Color BORDER_COLOR = new Color(0x3A3A3A);
    public static final Font FONT_REGULAR = new Font("Inter", Font.PLAIN, 15);
    public static final Font FONT_BOLD = new Font("Inter", Font.BOLD, 18);
    public static final Font FONT_MONO = new Font("JetBrains Mono", Font.PLAIN, 14);
    public static final Border PADDING = BorderFactory.createEmptyBorder(15, 15, 15, 15);

    public static void styleButton(JButton button) {
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.BLACK);
        button.setFont(FONT_REGULAR.deriveFont(Font.BOLD));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color originalColor = ACCENT_COLOR;
        Color hoverColor = originalColor.brighter();
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    // This is the missing method from your file.
    public static void styleLabel(JLabel label) {
        label.setForeground(TEXT_COLOR);
        label.setFont(FONT_REGULAR);
    }

    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(UITheme.PANEL_BACKGROUND);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());
        verticalScrollBar.setPreferredSize(new Dimension(8, 0));
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new CustomScrollBarUI());
        horizontalScrollBar.setPreferredSize(new Dimension(0, 8));
    }
    
    public static void styleOptionPane() {
        UIManager.put("Panel.background", PANEL_BACKGROUND);
        UIManager.put("OptionPane.background", PANEL_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", TEXT_COLOR);
        UIManager.put("OptionPane.messageFont", FONT_REGULAR);
        UIManager.put("Button.background", ACCENT_COLOR);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.font", FONT_REGULAR.deriveFont(Font.BOLD));
        UIManager.put("TextField.background", APP_BACKGROUND);
        UIManager.put("TextField.foreground", TEXT_COLOR);
        UIManager.put("TextField.caretForeground", ACCENT_COLOR);
        UIManager.put("TextField.font", FONT_REGULAR);
    }
}

