package com.flydb.ui.test;

import com.flydb.components.myJBTable.ColumnOption;
import com.flydb.components.myJBTable.MyJBTable;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.util.Vector;


public class TestJBTable extends JSplitPane {

    public TestJBTable() {


        Vector<ColumnOption> options = new Vector<>();
        options.add(new ColumnOption("标题", "title", null, "left"));
        options.add(new ColumnOption("提交人", "name", 80, "center"));
        options.add(new ColumnOption("提交时间", "time", 120, "center"));

        JBTable table = new MyJBTable<>().create(null, options);

        JPanel tablePanel = createTablePanel(table);
//        jPanel.add(tablePanel, BorderLayout.CENTER);


//        add(jPanel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 3, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
    }

    private static JPanel createTablePanel(final JBTable table) {
        return ToolbarDecorator.createDecorator(table)
                .disableUpAction()
                .disableDownAction()
                .setAddAction(anActionButton -> {
                    System.out.println("add");
                })
                .addExtraAction(new ExtraButtonAction())

                .createPanel();
    }
}
