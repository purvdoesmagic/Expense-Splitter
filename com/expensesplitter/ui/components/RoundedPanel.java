package com.expensesplitter.ui.components;

import com.expensesplitter.ui.UITheme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 20;

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        graphics.setColor(UITheme.PANEL_BACKGROUND);
        graphics.fill(new RoundRectangle2D.Float(0, 0, width-1, height-1, arcs.width, arcs.height));
        graphics.setColor(UITheme.BORDER_COLOR);
        graphics.draw(new RoundRectangle2D.Float(0, 0, width-1, height-1, arcs.width, arcs.height));
    }
}
