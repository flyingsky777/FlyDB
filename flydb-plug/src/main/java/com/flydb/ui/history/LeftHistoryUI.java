package com.flydb.ui.history;

import com.flydb.components.myJBTable.ColumnOption;
import com.flydb.components.myJBTable.MyJBTable;
import com.flydb.data.entity.History;
import com.flydb.data.entity.HistoryInfo;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

@Getter
public class LeftHistoryUI extends JPanel {

    private final ComboBox<Object> flydb;
    private JBTextField field;
    private JButton refresh;
    private MyJBTable<History> table;
    private ComboBox<Object> host;
    private ComboBox<Object> databases;

    public LeftHistoryUI(DataHistory data) {
        setLayout(new BorderLayout());

        // 数据库选择
        flydb = new ComboBox<>();
        flydb.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        flydb.setPreferredSize(new Dimension(120, 32));

        // 搜索
        field = new JBTextField();
        field.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(150, 32));
        field.getEmptyText().setText("输入关键字搜索");

        // 数据库连接
        host = new ComboBox<>();
        host.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        host.setPreferredSize(new Dimension(120, 32));

        // 数据库选择
        databases = new ComboBox<>();
        databases.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        databases.setPreferredSize(new Dimension(120, 32));

        // 刷新
        refresh = new JButton("刷新");
        refresh.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        refresh.setPreferredSize(new Dimension(50, 32));

        // 搜索条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(flydb);
        btnP.add(host);
        btnP.add(databases);
        btnP.add(field);
        btnP.add(refresh);

        // 表格
        Vector<ColumnOption> options = new Vector<>();
        options.add(new ColumnOption("ID", "id", 0, "left"));
        options.add(new ColumnOption("标题", "title", null, "left"));
        options.add(new ColumnOption("提交人", "name", 80, "center"));
        options.add(new ColumnOption("提交时间", "time", 120, "center"));
        table = new MyJBTable<>(options);
        table.setText("数据为空、请点击刷新更新数据！");

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(table);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(btnP, BorderLayout.NORTH);
        add(jbScrollPane, BorderLayout.CENTER);
    }
}
