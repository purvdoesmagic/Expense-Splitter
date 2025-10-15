package com.expensesplitter.ui.components;

import com.expensesplitter.ui.UITheme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private Shape shape;
    public RoundedTextField(int size) {
        super(size);
        setOpaque(false);
        setBackground(UITheme.PANEL_BACKGROUND);
        setForeground(UITheme.TEXT_COLOR);
        setFont(UITheme.FONT_REGULAR);
        setCaretColor(UITheme.ACCENT_COLOR);
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         Graphics2D g2 = (Graphics2D) g;
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.setColor(UITheme.BORDER_COLOR);
         g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}
