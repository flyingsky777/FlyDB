package com.flydb.ui.base;

import javax.swing.*;
import java.awt.*;

public class SwingComponents {


    /**
     * 占位
     * @param str
     * @return
     */
    public static JPanel placeholderJpanel(String str) {
        JPanel placeholder = new JPanel();
        placeholder.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // 设置水平和垂直居中
        gbc.weightx = 1.0; // 水平方向上的伸缩权重
        gbc.weighty = 1.0; // 垂直方向上的伸缩权重
        placeholder.add(new JLabel(str), gbc);
        return placeholder;
    }

}
