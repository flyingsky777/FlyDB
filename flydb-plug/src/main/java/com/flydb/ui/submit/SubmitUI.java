package com.flydb.ui.submit;

import cn.hutool.core.util.StrUtil;
import com.flydb.components.myTree.CheckBoxTreeNode;
import com.flydb.data.entity.HistoryInfo;
import com.flydb.data.service.HistoryService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SubmitUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    //    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private SqlTreeUI sqlTreeUI;
    private SubmitMsgUI submitMsgUI;
    private DataSubmit data;

    private String content;
    private int selectedIndex;
    private List<HistoryInfo> selectedList;

    public SubmitUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        JBSplitter splitter = new JBSplitter(true);

        data = new DataSubmit(project.getBasePath());
        sqlTreeUI = new SqlTreeUI(data);
        submitMsgUI = new SubmitMsgUI(data);

        splitter.setFirstComponent(sqlTreeUI);
        splitter.setSecondComponent(submitMsgUI);
        splitter.setProportion(0.8f);
        splitter.setDividerWidth(1);
        setContent(splitter);

        // 更新下拉框
        data.updateComboBox(sqlTreeUI.getHost(), sqlTreeUI.getDatabases(), submitMsgUI.getFlyDb());

        // 手动更新下拉框
        submitMsgUI.getUpdateConfig().addActionListener(e -> {
            data.updateComboBox(sqlTreeUI.getHost(), sqlTreeUI.getDatabases(), submitMsgUI.getFlyDb());
            submitMsgUI.getError().setVisible(false);
        });

        // 手动刷新数据库
        sqlTreeUI.getRefresh().addActionListener(e -> {
            Object sHost = sqlTreeUI.getHost().getSelectedItem();
            Object sDb = sqlTreeUI.getDatabases().getSelectedItem();

            submitMsgUI.getError().setVisible(false);
            data.updateTree(sHost, sDb, sqlTreeUI.getTree());
        });

        // todo 自动更新需要解决、保留更新前的状态
//        // 延迟1秒后执行任务，之后每3秒执行一次
//        executorService.scheduleAtFixedRate(() -> {
//            data.updateTree(sqlTreeUI.getTree());
//        }, 3, 5, TimeUnit.SECONDS);

        // 提交
        submitMsgUI.getSubmit().addActionListener(e -> {
            boolean check = check();
            if (!check) {
                return;
            }

            String confirmMsg = "请检查是否存在未选择的记录、一旦提交则自动放弃未提交记录、且不可撤回！！！";
            int option = JOptionPane.showConfirmDialog(null, confirmMsg, "请认真查看提交说明", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                submit();
            }
        });

    }

    public void submit() {
        try {
            String path = (String) data.getFlyDBConfig().getOriginal()[selectedIndex];
            String name = data.getFlyDBConfig().getName();
            HistoryService historyService = new HistoryService(path);
            historyService.addHistory(content, StrUtil.isNotBlank(name) ? name : "flydb", selectedList);

            // 提交成功后、更新flydb_last、再更新一个列表
            Object sHost = sqlTreeUI.getHost().getSelectedItem();
            Object sDb = sqlTreeUI.getDatabases().getSelectedItem();
            data.submitDb(sHost, sDb);
            data.updateTree(sHost, sDb, sqlTreeUI.getTree());
            submitMsgUI.getArea().setText(null);
            submitMsgUI.getError().setVisible(false);
        } catch (Exception err) {
            submitMsgUI.getError().setText("提交失败！" + err.getMessage());
            submitMsgUI.getError().setVisible(true);
        }
    }

    public boolean check() {
        content = submitMsgUI.getArea().getText();
        // 内容是空
        if (content.equals("")) {
            submitMsgUI.getError().setText("请输入提交内容！");
            submitMsgUI.getError().setVisible(true);
            return false;
        }
        if (content.length() > 1000) {
            submitMsgUI.getError().setText("提交内容太长！");
            submitMsgUI.getError().setVisible(true);
            return false;
        }

        // fly.db未选择
        selectedIndex = submitMsgUI.getFlyDb().getSelectedIndex();
        if (selectedIndex < 0) {
            submitMsgUI.getError().setText("请在resource下新建fly.db文件、然后点击刷新配置！");
            submitMsgUI.getError().setVisible(true);
            return false;
        }

        selectedList = new ArrayList<>();
        Object root = sqlTreeUI.getTree().getModel().getRoot();
        if (root instanceof CheckBoxTreeNode) {
            traversalCheckTree(selectedList, root);
        }
        // 过滤 ddl/dml/其他
        selectedList = selectedList.stream().filter(item -> StrUtil.isNotBlank(item.getSql())).toList();

        // 未选择sql
        if (selectedList.isEmpty()) {
            submitMsgUI.getError().setText("请选择提交的sql！");
            submitMsgUI.getError().setVisible(true);
            return false;
        }
        return true;
    }

    public void traversalCheckTree(List<HistoryInfo> list, Object node) {
        CheckBoxTreeNode checkBoxTreeNode = (CheckBoxTreeNode) node;
        HistoryInfo info = (HistoryInfo) checkBoxTreeNode.getUserObject();
        if (checkBoxTreeNode.isSelected()) {
            list.add(info);
        }
        if (checkBoxTreeNode.getChildCount() > 0) {
            for (int i = 0; i < checkBoxTreeNode.getChildCount(); i++) {
                Object child = checkBoxTreeNode.getChildAt(i);
                traversalCheckTree(list, child);
            }
        }
    }


}
