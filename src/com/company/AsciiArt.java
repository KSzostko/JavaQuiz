package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AsciiArt {
    public static String drawWord(String word) {
        int width = 35;
        int height = 15;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 12));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(word, 5, 10);

        StringBuilder result = new StringBuilder();
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? " " : "#");
            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            result.append(sb);
            result.append("\n");
        }

        return result.toString();
    }
}
