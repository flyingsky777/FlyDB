package com.flydb.ui.submit;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

public class SubmitUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    public SubmitUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        JBSplitter splitter = new JBSplitter(true);
        DataSubmit data = new DataSubmit(project.getBasePath());
        splitter.setFirstComponent(new SqlTreeUI(data));
        splitter.setSecondComponent(new SubmitMsgUI(data));
        splitter.setProportion(0.8f);
        splitter.setDividerWidth(1);
        setContent(splitter);
    }


}
