package com.flydb;

import com.flydb.ui.SubmitUI;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class FlyDBSubmit implements ToolWindowFactory {

    private static final String DISPLAY_NAME = "";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //toolwindow 里面的内容，创建我们自己定义的面板
        SubmitUI httpRunnerToolWindow = new SubmitUI(project, toolWindow);

        //获取ContentFactory实例
        //这种方式是为了兼容老版本的IDEA，但是SERVICE是已经被标记为了@Deprecated
        //ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        //这种方式只能在 build number 222.3345.118 之后调用，也就是如果我们使用了这种方式，那么在plugin.xml中的since-version必须要配置为：<idea-version since-build="222.3345.118"/>
        ContentFactory contentFactory = ContentFactory.getInstance();

        //创建一个Content，也就是toolwindow里面的一个tab页
        Content content = contentFactory.createContent(httpRunnerToolWindow, DISPLAY_NAME, false);
        //将Content加入到toolwindow中
        toolWindow.getContentManager().addContent(content);
    }


}
