package com.flydb.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

import java.util.List;

public class SubmitHistoryUI extends SimpleToolWindowPanel {

    private final Project project;
    private final ToolWindow toolWindow;
    // flydb 列表
    private List<String> flyDBPaths;


    public SubmitHistoryUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;

        loadData(null);
        initUI();
    }

    public void loadData(String key) {

    }


    private void initUI() {


//        setContent();
    }

}
