package com.flydb.ui.submit;

import com.flydb.components.MyRoundButton;
import com.flydb.util.ColorUtils;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class SubmitMsgUI extends JPanel {

    public SubmitMsgUI() {
        setLayout(new BorderLayout());
        setBackground(ColorUtils.DARK());


        JBTextArea text = new JBTextArea();
        text.setBackground(ColorUtils.DARK());
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        text.setAlignmentX(JTextField.LEFT);
        text.setAlignmentY(JTextField.TOP);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBorder(JBUI.Borders.empty(5));
        text.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));


        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(text);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // 提交按钮
        MyRoundButton submit = new MyRoundButton("提交");
        submit.setPreferredSize(new Dimension(80, 32));
        submit.setForeground(Color.WHITE); // 白色字体
//        submit.setBackground(Color.darkGray.brighter());

        // flydb选择框
        ComboBox<Object> box = new ComboBox<>();
        box.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        box.setPreferredSize(new Dimension(150, 32));

        // 错误提示
        JLabel error = new JLabel("错误消息");
        error.setForeground(Color.RED);

        // 顶部按钮条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setBackground(ColorUtils.DARK());
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(submit);
        btnP.add(box);
        btnP.add(error);

        add(jbScrollPane, BorderLayout.CENTER);
        add(btnP, BorderLayout.SOUTH);
    }


}
