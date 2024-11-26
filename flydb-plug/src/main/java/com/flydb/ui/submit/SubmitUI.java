package com.flydb.ui.submit;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SubmitUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public SubmitUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        JBSplitter splitter = new JBSplitter(true);

        DataSubmit data = new DataSubmit(project.getBasePath());
        SqlTreeUI sqlTreeUI = new SqlTreeUI(data);
        SubmitMsgUI submitMsgUI = new SubmitMsgUI(data);

        splitter.setFirstComponent(sqlTreeUI);
        splitter.setSecondComponent(submitMsgUI);
        splitter.setProportion(0.8f);
        splitter.setDividerWidth(1);
        setContent(splitter);

        // 更新下拉框
        data.updateComboBox(sqlTreeUI.getHost(), sqlTreeUI.getDatabases(), submitMsgUI.getFlyDb());

        // 手动刷新数据库
        sqlTreeUI.getRefresh().addActionListener(e -> {
            data.updateTree(sqlTreeUI.getTree());
        });

        // 手动更新下拉框
        submitMsgUI.getUpdateConfig().addActionListener(e -> {
            data.updateComboBox(sqlTreeUI.getHost(), sqlTreeUI.getDatabases(), submitMsgUI.getFlyDb());
            submitMsgUI.getError().setVisible(false);
        });


        // todo 自动更新需要解决、保留更新前的状态
//        // 延迟1秒后执行任务，之后每3秒执行一次
//        executorService.scheduleAtFixedRate(() -> {
//            data.updateTree(sqlTreeUI.getTree());
//        }, 3, 5, TimeUnit.SECONDS);
    }


}
