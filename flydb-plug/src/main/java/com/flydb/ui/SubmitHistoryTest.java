package com.flydb.ui;

import cn.hutool.core.util.RandomUtil;
import com.flydb.data.entity.SubmitHistory;
import com.flydb.data.entity.SubmitHistoryInfo;
import com.flydb.ui.dto.BeanTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubmitHistoryTest extends JFrame {

    public SubmitHistoryTest() {
        initUI();
    }

    public static void main(String[] args) {
        new SubmitHistoryTest();
    }

    public List<SubmitHistory> getHistoryList() {
        List<SubmitHistory> historyList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(100); i++) {
            historyList.add(new SubmitHistory(RandomUtil.randomString(50)));
        }
        return historyList;
    }

    public List<SubmitHistoryInfo> getHistoryInfoList(String historyId, String operate) {
        List<SubmitHistoryInfo> historyList = new ArrayList<>();
        for (int i = 0; i < RandomUtil.randomInt(100); i++) {
            historyList.add(new SubmitHistoryInfo(operate));
        }
        return historyList;
    }


    private JTable initLeftHistoryTable() {
        BeanTableModel<SubmitHistory> historyBeanList = new BeanTableModel<>(getHistoryList(), 3,
                (invoke, rowIndex, columnIndex) -> switch (columnIndex) {
                    case 0 -> invoke.getTitle();
                    case 1 -> invoke.getName();
                    case 2 -> invoke.getTime();
                    default -> "";
                });

        JTable historyTable = new JTable(historyBeanList);
        // 隐藏表头
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setPreferredSize(new Dimension(0, 0));
        historyTable.getTableHeader().setVisible(false);
        historyTable.getTableHeader().setDefaultRenderer(renderer);

        // 去掉网格线
        historyTable.setShowGrid(false);
        historyTable.setShowHorizontalLines(false);
        historyTable.setShowVerticalLines(false);

        // 提交人 单元格居中、字体加粗(无效)
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(SwingConstants.CENTER);
//        cr.setFont(new Font("SansSerif", Font.ITALIC, 20));

        // 提交人 宽度
        TableColumn column1 = historyTable.getColumnModel().getColumn(1);
        column1.setPreferredWidth(65);
        column1.setMaxWidth(80);
        column1.setMinWidth(50);

        // 时间 宽度
        TableColumn column2 = historyTable.getColumnModel().getColumn(2);
        column2.setPreferredWidth(120);
        column2.setMaxWidth(120);
        column2.setMinWidth(120);
        column2.setCellRenderer(cr);

        return historyTable;
    }

    private JTable initRightInfoTable(String operate) {
        List<SubmitHistoryInfo> historyInfoList = getHistoryInfoList("", operate);
        BeanTableModel<SubmitHistoryInfo> historyBeanList = new BeanTableModel<>(historyInfoList, 4,
                (invoke, rowIndex, columnIndex) -> switch (columnIndex) {
                    case 0 -> invoke.getType();
                    case 1 -> invoke.getTableName();
                    case 2 -> invoke.getFieldName();
                    case 3 -> invoke.getSql();
                    default -> "";
                });

        JTable infoTable = new JTable(historyBeanList);
        // 隐藏表头
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setPreferredSize(new Dimension(0, 0));
        infoTable.getTableHeader().setVisible(false);
        infoTable.getTableHeader().setDefaultRenderer(renderer);

        // 去掉网格线
        infoTable.setShowGrid(false);
        infoTable.setShowHorizontalLines(false);
        infoTable.setShowVerticalLines(false);

        //  单元格居中、字体加粗(无效)
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(SwingConstants.CENTER);
//        cr.setFont(new Font("SansSerif", Font.ITALIC, 20));


        // 操作类型
        TableColumn column0 = infoTable.getColumnModel().getColumn(0);
        column0.setPreferredWidth(40);
        column0.setMaxWidth(40);
        column0.setMinWidth(40);
        column0.setCellRenderer(cr);

        // 表名
        TableColumn column1 = infoTable.getColumnModel().getColumn(1);
        column1.setPreferredWidth(60);
        column1.setMaxWidth(60);
        column1.setMinWidth(60);
        column1.setCellRenderer(cr);

        // 字段名
        TableColumn column2 = infoTable.getColumnModel().getColumn(2);
        column2.setPreferredWidth(60);
        column2.setMaxWidth(60);
        column2.setMinWidth(60);
        column2.setCellRenderer(cr);

        return infoTable;
    }

    private void initUI() {

        // 搜索框
        JTextField searchText = new JTextField();
        searchText.setPreferredSize(new Dimension(150, 20));

        // 功能按钮
        JPanel leftTopRightBtnPanel = new JPanel();
        JButton searchButton = new JButton("刷新");
        JButton configButton = new JButton("配置");
        leftTopRightBtnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        leftTopRightBtnPanel.add(searchButton);
        leftTopRightBtnPanel.add(configButton);

        // 左侧顶部搜索功能栏
        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new BorderLayout());
        leftTopPanel.add(searchText, BorderLayout.WEST);
        leftTopPanel.add(leftTopRightBtnPanel, BorderLayout.EAST);

        // 左侧区域
        JTable historyTable = initLeftHistoryTable();
        JScrollPane leftTablePanel = new JScrollPane(historyTable);
        leftTablePanel.setBorder(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftPanel.add(leftTablePanel, BorderLayout.CENTER);


        // 右侧
        JTabbedPane rightPanel = new JTabbedPane();
        rightPanel.setBorder(null);

        JTable ddlTable = initRightInfoTable("ddl");
        JScrollPane ddlPanel = new JScrollPane(ddlTable);

        JTable dmlTable = initRightInfoTable("dml");
        JScrollPane dmlPanel = new JScrollPane(dmlTable);

        rightPanel.add("DDL  2个提交", ddlPanel);
        rightPanel.add("DML  4个提交", dmlPanel);

        // 主要
        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(leftPanel);
        panel.setRightComponent(rightPanel);
        panel.setResizeWeight(0.3); // 有内容后无效
        panel.setDividerSize(1);

        getContentPane().add(panel);
        pack();
        setBounds(0, 0, 1800, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}


