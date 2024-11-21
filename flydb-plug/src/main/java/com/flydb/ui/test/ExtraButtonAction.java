package com.flydb.ui.test;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import org.jetbrains.annotations.NotNull;

class ExtraButtonAction extends AnActionButton {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("button" + e);
    }

}
