package com.flydb.ui.submit;

import cn.hutool.core.thread.ThreadUtil;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class SubmitMsgUI extends JPanel {

    public SubmitMsgUI(DataSubmit data) {
        setLayout(new BorderLayout());

        JBTextArea text = new JBTextArea();
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        text.setAlignmentX(JTextField.LEFT);
        text.setAlignmentY(JTextField.TOP);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBorder(JBUI.Borders.empty(5));
        text.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        text.getEmptyText().setText("请输入提交内容");

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(text);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // 提交按钮
        JButton submit = new JButton("提交");
        submit.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        submit.setPreferredSize(new Dimension(80, 32));

        // flydb选择框
        ComboBox<Object> box = new ComboBox<>(data.getFlyDBs());
        box.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        box.setPreferredSize(new Dimension(200, 32));

        // 错误提示
        JLabel error = new JLabel("错误消息");
        error.setForeground(Color.RED);
        error.setVisible(false);

        // 顶部按钮条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(submit);
        btnP.add(box);
        btnP.add(error);

        add(jbScrollPane, BorderLayout.CENTER);
        add(btnP, BorderLayout.SOUTH);


        // 业务逻辑
        submit.addActionListener(e -> {
            String content = text.getText();
            if (content.equals("")) {
                error.setText("请输入提交内容！");
                error.setVisible(true);
            }
        });
    }


}
