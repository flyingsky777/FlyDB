package com.flydb.components;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface MyIcons {

    Icon create = IconLoader.getIcon("/icons/create.svg", MyIcons.class);
    Icon alter = IconLoader.getIcon("/icons/alter.svg", MyIcons.class);
    Icon drop = IconLoader.getIcon("/icons/drop.svg", MyIcons.class);

    Icon insert = IconLoader.getIcon("/icons/insert.svg", MyIcons.class);
    Icon update = IconLoader.getIcon("/icons/update.svg", MyIcons.class);
    Icon delete = IconLoader.getIcon("/icons/delete.svg", MyIcons.class);
}
