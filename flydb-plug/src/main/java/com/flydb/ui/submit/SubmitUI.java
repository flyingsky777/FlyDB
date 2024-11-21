package com.flydb.ui.submit;

import com.flydb.util.ColorUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import javax.swing.border.Border;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
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
     * [表图标]更新的表名  ..数量
     * 【CREATE】        SQL
     * 【ALTER】         字段名称(多个)
     * 【ALTER-ADD】     字段名称
     * 【ALTER-MODIFY】  字段名称
     * 【ALTER-DROP】    字段名称
     * 【DROP】          SQL
     * <p>
     * DML
     * [表图标]更新的表名  ..数量
     * 【INSERT】   SQL
     * 【UPDATE】   SQL
     * 【DELETE】   SQL
     * <p>
     * [提交文本框]
     * [提交按钮]    FlyDB  下拉
     * FlyDB[classpath:/fly.db]
     */
    private void initUI() {
//        String url = "jdbc:mysql://127.0.0.1:3306/flydb";
//        String username = "root";
//        String password = "sw@3100@admin";
//        String driver = "com.mysql.cj.jdbc.Driver";
//        DBConfig dbConfig = new DBConfig();
//        dbConfig.setUrl(url);
//        dbConfig.setName(username);
//        dbConfig.setPass(password);
//        dbConfig.setDriver(driver);
//        MySqlService service = new MySqlService(dbConfig);
//        List<HistoryInfo> list = service.getList();
//        List<HistoryInfo> flydb = service.getTree(list, "flydb");
//
//        AllIcons.ToolbarDecorator allIcons = new AllIcons.ToolbarDecorator();
//
//        TreeTableModel treeTableModel = new SubmitTreeTableModel(flydb);
//        JBTreeTable jbTreeTable = new JBTreeTable(treeTableModel);

        JBSplitter splitter = new JBSplitter(true);
        splitter.setFirstComponent(new SqlTreeUI());
        splitter.setSecondComponent(new SubmitMsgUI());
        splitter.setProportion(0.8f);
        // 分割线宽度和颜色
        splitter.setDividerWidth(1);
//        splitter.getDivider().setBackground(ColorUtils.BORDER());

        setContent(splitter);
    }

}
