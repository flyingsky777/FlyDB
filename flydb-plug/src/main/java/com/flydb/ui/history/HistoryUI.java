package com.flydb.ui.history;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

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
        JBSplitter splitter = new JBSplitter(false);

        DataHistory data = null;

        splitter.setFirstComponent(new LeftHistoryUI(data));
        splitter.setSecondComponent(new RightInfoUI(data));
        splitter.setProportion(0.5f);
        splitter.setDividerWidth(1);
        setContent(splitter);
    }
}
