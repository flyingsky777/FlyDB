package com.flydb.ui.history;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

public class HistoryUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    public HistoryUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {

    }
}
