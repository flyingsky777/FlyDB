package com.flydb.util;

import com.flydb.ui.TestSubmit;
import com.flydb.ui.base.MyButton;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ButtonUtils {
    public static void addBtnList(JPanel jPanel, List<MyButton> list) {
        for (MyButton btn : list) {

            URL url = TestSubmit.class.getResource(btn.getIcon());
            ImageIcon icon = new ImageIcon(url); // 替换为你的图片路径

            JButton button = new JButton();
            button.setToolTipText(btn.getName());
            button.setBorder(null);
            button.setBackground(null);
            button.setPreferredSize(new Dimension(20, 20));
//            button.setBorderPainted(false); //是否画边框，如果用自定义图片做按钮背景可以设为 false
//            button.setFocusPainted(false); // 是否绘制焦点(例如浅色虚线框或者加粗的边框表明按钮当前有焦点)。
//            button.setContentAreaFilled(false); //是否填充，如果你的自定义图片不是矩形或存在空白边距，可以设为 false 使按钮看起来透明。
//            button.setMargin(new Insets(0, 0, 0, 0)); //改变边距，如果 borderPainted 和 contentAreaFilled 都设成了 false，建议把边距都调为 0:new Insets(0, 0, 0, 0)
            button.setIcon(icon);
//            button.setPressedIcon(icon); //按下时的图标。
//            button.setRolloverIcon(icon); //鼠标经过时的图标。
//            button.setRolloverSelectedIcon(icon);//鼠标经过时且被选中状态的图标。


            jPanel.add(button);
        }
    }
}
