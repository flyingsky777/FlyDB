package com.flydb.ui.submit;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class SubmitMsgUI extends JPanel {
    private  JButton updateConfig;
    private  JLabel error;
    private ComboBox<Object> flyDb;
    private DataSubmit data;

    public SubmitMsgUI(DataSubmit data) {
        this.data = data;
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

        // 提交按钮
        updateConfig = new JButton("刷新配置");
        updateConfig.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        updateConfig.setPreferredSize(new Dimension(80, 32));

        // flydb选择框
        flyDb = new ComboBox<>();
        flyDb.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        flyDb.setPreferredSize(new Dimension(200, 32));

        // 错误提示
        error = new JLabel("错误消息");
        error.setForeground(Color.RED);
        error.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        error.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        error.setPreferredSize(new Dimension(0, 20));
        error.setIcon(AllIcons.General.Error);
        error.setVisible(false);

        // 顶部按钮条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(submit);
        btnP.add(flyDb);
        btnP.add(updateConfig);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(error, BorderLayout.NORTH);
        bottom.add(btnP, BorderLayout.CENTER);

        add(jbScrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);


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
