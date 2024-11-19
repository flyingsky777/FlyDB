package com.flydb.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JPanelUtils {
    // 边框颜色
    private static final String BORDER_COLOR = "#1E1F22";
    private static final Integer BORDER_SIZE = 1;

    /**
     * 设置底部边框颜色
     * @param panel
     */
    public static void setBottomBorder(JPanel panel) {
        panel.setBorder(new EmptyBorder(0, 0, 1, 0)); // 上下左右间距
        panel.setBorder(BorderFactory.createCompoundBorder(panel.getBorder(),
                BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, 0,
                        ColorUtils.hexTocolor(BORDER_COLOR))));
    }
}
