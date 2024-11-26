package com.flydb.ui.history;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.awt.*;

public class LeftHistoryUI extends JPanel {

    public LeftHistoryUI(DataHistory data) {
        setLayout(new BorderLayout());


        // 数据库选择
        ComboBox<Object> flydb = new ComboBox<>();
        flydb.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        flydb.setPreferredSize(new Dimension(150, 32));

        // 搜索
        JBTextField field = new JBTextField();
        field.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 32));
        field.getEmptyText().setText("输入关键字搜索");

        // 刷新
        JButton refresh = new JButton("刷新");
        refresh.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        refresh.setPreferredSize(new Dimension(50, 32));

        // 搜索条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(flydb);
        btnP.add(field);
        btnP.add(refresh);

        // 表格
        JBTable table = new JBTable();
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.getEmptyText().setText("数据为空、请点击刷新更新数据！");

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(table);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(btnP, BorderLayout.NORTH);
        add(jbScrollPane, BorderLayout.CENTER);
    }
}
