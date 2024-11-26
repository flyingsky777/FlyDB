package com.flydb.ui.submit;

import com.flydb.components.myTree.CheckBoxTreeCellRenderer;
import com.flydb.components.myTree.CheckBoxTreeNodeSelectionListener;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class SqlTreeUI extends JPanel {
    private  JButton refresh;
    private Tree tree;
    private ComboBox<Object> host;
    private ComboBox<Object> databases;
    private DataSubmit data;

    public SqlTreeUI(DataSubmit data) {
        this.data = data;
        setLayout(new BorderLayout());

        // 数据库连接
        host = new ComboBox<>();
        host.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        host.setPreferredSize(new Dimension(150, 32));

        // 数据库选择
        databases = new ComboBox<>();
        databases.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        databases.setPreferredSize(new Dimension(120, 32));

//        // 配置
//        JButton config = new JButton("配置");
//        config.setFont(new Font("微软雅黑", Font.PLAIN, 13));
//        config.setPreferredSize(new Dimension(50, 32));

        // 刷新
        refresh = new JButton("刷新");
        refresh.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        refresh.setPreferredSize(new Dimension(50, 32));

        // 顶部按钮条
        JPanel btnP = new JPanel();
        btnP.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnP.setPreferredSize(new Dimension(0, 40));
        btnP.add(host);
        btnP.add(databases);
//        btnP.add(config);
        btnP.add(refresh);
        btnP.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));

        // 树
        tree = new Tree();
        tree.setRootVisible(false);
        tree.setCellRenderer(new CheckBoxTreeCellRenderer());
        tree.addMouseListener(new CheckBoxTreeNodeSelectionListener());

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(tree);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(btnP, BorderLayout.NORTH);
        add(jbScrollPane, BorderLayout.CENTER);

    }


}

