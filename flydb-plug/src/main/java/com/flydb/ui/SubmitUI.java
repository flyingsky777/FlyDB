package com.flydb.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import javax.swing.*;
import java.awt.*;

public class SubmitUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    public SubmitUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    /**
     * 刷新 | 添加 | 展开 | 关闭 | DDL | DML               数据库host 下拉 | 库 下拉  (请选择数据库 下拉)
     * DDL ..数量
     *  [表图标]更新的表名  ..数量
     *   【CREATE】        SQL
     *   【ALTER】         字段名称(多个)
     *   【ALTER-ADD】     字段名称
     *   【ALTER-MODIFY】  字段名称
     *   【ALTER-DROP】    字段名称
     *   【DROP】          SQL
     *
     * DML
     *  [表图标]更新的表名  ..数量
     *   【INSERT】   SQL
     *   【UPDATE】   SQL
     *   【DELETE】   SQL
     *
     * [提交文本框]
     * [提交按钮]    FlyDB  下拉
     *              FlyDB[classpath:/fly.db]
     *
     */
    private void initUI() {
        // 创建一个垂直分割面板
        JBSplitter panel = new JBSplitter(JSplitPane.VERTICAL_SPLIT);

        JPanel sqlPanel = new JPanel();
        sqlPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        sqlPanel.setBackground(Color.red);

        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        msgPanel.setBackground(Color.white);

        panel.setFirstComponent(sqlPanel);
        panel.setSecondComponent(msgPanel);
        panel.setProportion(0.9f);


        setContent(panel);
    }

}
