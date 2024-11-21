package com.flydb.ui.submit;

import com.flydb.util.ColorUtils;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SubmitMsgUI extends JPanel {

    public SubmitMsgUI() {
//        setBackground(JBColor.RED);
        setLayout(new BorderLayout());
        setBackground(ColorUtils.DARK());

        JTextArea text = new JTextArea();
//        text.setPlaceholder("提交消息");
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
        JButton submit = new JButton();
        submit.setText("提 交");
        submit.setBackground(ColorUtils.hexTocolor("#292B2E"));
        submit.setPreferredSize(new Dimension(100, 34));

        // 顶部按钮条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setBackground(ColorUtils.DARK());
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(submit);

        add(jbScrollPane, BorderLayout.CENTER);
        add(btnP, BorderLayout.SOUTH);
    }
}
