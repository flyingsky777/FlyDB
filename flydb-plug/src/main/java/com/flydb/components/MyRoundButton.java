package com.flydb.components;

import javax.swing.*;
import java.awt.*;

public class MyRoundButton extends JButton {
    private static final int CORNER_RADIUS = 5; // 设置圆角的半径

    public MyRoundButton(String text) {
        super(text);
        setFont(new Font("微软雅黑", Font.PLAIN, 13));
        setOpaque(true);  // 透明
        setFocusPainted(false); // 移除焦点
        setBorderPainted(false); // 移除边框
        setContentAreaFilled(false); // 不填充按钮内容区域的背景
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 绘制圆角矩形边框
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        g2d.setColor(getBackground()); // 设置边框颜色为背景颜色
        g2d.setStroke(new BasicStroke(2.0f)); // 设置边框粗细
        g2d.drawRoundRect(1, 1, width - 3, height - 3, CORNER_RADIUS, CORNER_RADIUS);
        g2d.dispose();

        // 绘制按钮内容
        super.paintComponent(g);
    }
}
