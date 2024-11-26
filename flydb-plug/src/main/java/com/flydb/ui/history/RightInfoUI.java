package com.flydb.ui.history;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.awt.*;

public class RightInfoUI extends JPanel {

    public RightInfoUI(DataHistory data) {
        setLayout(new BorderLayout());

        // 表格
        JBTable table = new JBTable();
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.getEmptyText().setText("请选择左侧记录查看更改详情！");

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(table);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(jbScrollPane, BorderLayout.CENTER);
    }
}
