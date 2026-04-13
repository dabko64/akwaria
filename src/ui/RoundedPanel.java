package ui;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color backgroundColor;
    private final Color shadowColor;
    private final int shadowSize;

    public RoundedPanel(int cornerRadius, Color backgroundColor, Color shadowColor, int shadowSize) {
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        this.shadowColor = shadowColor;
        this.shadowSize = shadowSize;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(shadowColor);
        g2.fillRoundRect(
                shadowSize,
                shadowSize,
                getWidth() - shadowSize,
                getHeight() - shadowSize,
                cornerRadius,
                cornerRadius
        );

        g2.setColor(backgroundColor);
        g2.fillRoundRect(
                0,
                0,
                getWidth() - shadowSize,
                getHeight() - shadowSize,
                cornerRadius,
                cornerRadius
        );

        g2.dispose();
        super.paintComponent(g);
    }
}