package com.flydb.util;

import java.awt.*;

public class ColorUtils {

    private static final String DARK_COLOR = "#1E1F22";

    public static Color DARK() {
        return hexTocolor(DARK_COLOR);
    }

    public static Color hexTocolor(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Color(r, g, b);
    }
}