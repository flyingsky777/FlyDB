package com.flydb.ui.history;

import com.flydb.components.myTree.CheckBoxTreeCellRenderer;
import com.flydb.components.myTree.CheckBoxTreeNodeSelectionListener;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.JBUI;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class RightInfoUI extends JPanel {
    private Tree tree;
    private JBTextArea area;

    public RightInfoUI(DataHistory data) {
        setLayout(new BorderLayout());

        // 树
        tree = new Tree();
        tree.setRootVisible(false);
        tree.setCellRenderer(new CheckBoxTreeCellRenderer());
        tree.addMouseListener(new CheckBoxTreeNodeSelectionListener());
        tree.getEmptyText().setText("请点击刷新按钮、获取数据库信息！");

        // 滚动条
        JBScrollPane jbScrollPane = new JBScrollPane(tree);
        jbScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jbScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        area = new JBTextArea();
        area.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        area.setAlignmentX(JTextField.LEFT);
        area.setAlignmentY(JTextField.TOP);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(JBUI.Borders.empty(5));
        area.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
        area.setEditable(false);

        JBSplitter splitter = new JBSplitter(true);
        splitter.setFirstComponent(jbScrollPane);
        splitter.setSecondComponent(area);
        splitter.setProportion(0.6f);
        splitter.setDividerWidth(2);

        add(splitter, BorderLayout.CENTER);
    }
}
