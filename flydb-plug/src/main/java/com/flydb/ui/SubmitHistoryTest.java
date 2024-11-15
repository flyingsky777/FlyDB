package com.flydb.ui;

import com.flydb.data.entity.History;
import com.flydb.data.service.HistoryService;
import com.flydb.ui.base.ColumnOption;
import com.flydb.ui.base.ConfigUtil;
import com.flydb.ui.base.MyJTable;
import com.flydb.ui.base.SwingComponents;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class SubmitHistoryTest extends JFrame {
    // flydb 列表
    private List<String> flyDBPaths;
    // 当前显示的提交记录
    private List<History> historyList;

    private JProgressBar progressBar;

    private JPanel dynamicPanel;
    private JPanel errorPanel;
    private JScrollPane tableSPanel;
    private JTable historyTable;
    private MyJTable<History> myJTable;

    public SubmitHistoryTest() {
        initUI();
        firstData();
    }

    public static void main(String[] args) {
        new SubmitHistoryTest();
    }

    public void firstData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {

                flyDBPaths = ConfigUtil.getFlyDBPath("E:\\zhl\\FlyDB\\flydb-plug");
                HistoryService historyService = new HistoryService(flyDBPaths.get(0));
                historyList = historyService.getHistoryList(null);

                return null;
            }

            @Override
            protected void done() {
                try {
                    ((CardLayout) dynamicPanel.getLayout()).show(dynamicPanel, "table");

                    myJTable.reloadData(historyList);
                } catch (Exception e) {
                    e.printStackTrace();
//                    ((CardLayout) dynamicPanel.getLayout()).show(errorPanel, "error");
                }
                progressBar.setValue(100); // 请求完成，进度条设置为完成状态
            }
        };

        worker.execute();
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


        // loading
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // 设置进度条为不确定模式

        // 占位
        JPanel place1derPanel = SwingComponents.placeholderJpanel("加载中...");
        errorPanel = SwingComponents.placeholderJpanel("数据加载失败！");

        // 表格
        Vector<ColumnOption> options = new Vector<>();
        options.add(new ColumnOption("标题", "title", null, "left"));
        options.add(new ColumnOption("提交人", "name", 80, "center"));
        options.add(new ColumnOption("提交时间", "time", 120, "center"));

        myJTable = new MyJTable<>();
        historyTable = myJTable.create(historyList, options);


        tableSPanel = new JScrollPane(historyTable);

        // 动态切换提示的面板
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new CardLayout());
        dynamicPanel.add(place1derPanel);
        dynamicPanel.add(tableSPanel, "table");
        dynamicPanel.add(errorPanel, "error");

        // loading + 动态切换面板
        JPanel leftTablePanel = new JPanel(new BorderLayout());
        leftTablePanel.add(progressBar, BorderLayout.NORTH);
        leftTablePanel.add(dynamicPanel, BorderLayout.CENTER);

        // 左侧 顶部搜索 + 列表的
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftPanel.add(leftTablePanel, BorderLayout.CENTER);

        // 右侧
//        ArrayList<ColumnOption> infoOptions = new ArrayList<>();
//        infoOptions.add(new ColumnOption("类型", "type", 80, "center"));
//        infoOptions.add(new ColumnOption("表", "tableName", 80, "center"));
//        infoOptions.add(new ColumnOption("字段", "fieldName", 80, "center"));
//        infoOptions.add(new ColumnOption("sql", "sql", null, "left"));
//
//        JTabbedPane rightPanel = new JTabbedPane();
//        rightPanel.setBorder(null);
//
//        JTable ddlTable = new MyJTable<SubmitHistoryInfo>().create(historyList, options);
//        JScrollPane ddlPanel = new JScrollPane(ddlTable);
//
//        JTable dmlTable = new MyJTable<SubmitHistoryInfo>().create(historyList, options);
//        JScrollPane dmlPanel = new JScrollPane(dmlTable);
//
//        rightPanel.add("DDL  2个提交", ddlPanel);
//        rightPanel.add("DML  4个提交", dmlPanel);


        // 主要
        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(leftPanel);
        panel.setRightComponent(SwingComponents.placeholderJpanel("选择要查看的记录"));
        panel.setResizeWeight(0.3); // 有内容后无效
        panel.setDividerSize(1);

        getContentPane().add(panel);
        pack();
        setBounds(0, 0, 1600, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}


