package com.expensesplitter.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class IconFactory {

    public enum IconType { SAVE, LOAD, EXIT }

    public static Icon createIcon(IconType type, int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.translate(x, y);
                g2d.setColor(UITheme.TEXT_COLOR);
                g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                switch (type) {
                    case SAVE:
                        // Floppy disk icon
                        g2d.draw(new RoundRectangle2D.Float(3, 3, width - 6, height - 6, 5, 5));
                        g2d.fillRect(7, 3, width - 14, 8);
                        g2d.drawLine(6, height - 8, width - 6, height - 8);
                        break;
                    case LOAD:
                        // Folder icon
                        g2d.draw(new RoundRectangle2D.Float(3, 6, width - 6, height - 9, 5, 5));
                        g2d.draw(new RoundRectangle2D.Float(3, 4, 10, 5, 5, 5));
                        break;
                    case EXIT:
                        // Power off icon
                        g2d.drawOval(5, 5, width - 10, height - 10);
                        g2d.drawLine(width / 2, 4, width / 2, height / 2);
                        break;
                }
                g2d.dispose();
            }
            @Override public int getIconWidth() { return width; }
            @Override public int getIconHeight() { return height; }
        };
    }
}

