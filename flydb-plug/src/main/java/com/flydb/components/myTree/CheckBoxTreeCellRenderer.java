package com.flydb.components.myTree;

import cn.hutool.core.util.StrUtil;
import com.flydb.components.MyIcons;
import com.flydb.data.entity.HistoryInfo;
import com.intellij.icons.AllIcons;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class CheckBoxTreeCellRenderer extends JPanel implements TreeCellRenderer {
    protected JCheckBox check;
    protected JLabel label;

    public CheckBoxTreeCellRenderer() {
        setLayout(null);
        add(check = new JCheckBox());
        add(label = new JLabel());
        check.setOpaque(false);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        CheckBoxTreeNode node = (CheckBoxTreeNode) value;
        HistoryInfo info = (HistoryInfo) node.getUserObject();

        setEnabled(tree.isEnabled());
        check.setSelected(((CheckBoxTreeNode) value).isSelected());
        label.setFont(tree.getFont());
        label.setVerticalAlignment(SwingConstants.CENTER);

        String title = info.getTitle();
        if (StrUtil.isNotBlank(title)) {
            if (title.contains(",")) {
                String field = title.split(",")[1].replace("`", "").replace("`", "");
                label.setText(field + "    " + info.getSql());
                String type = title.split(",")[0];
                switch (type.toLowerCase()) {
                    case "insert":
                        label.setIcon(MyIcons.insert);
                        break;
                    case "update":
                        label.setIcon(MyIcons.update);
                        break;
                    case "delete":
                        label.setIcon(MyIcons.delete);
                        break;
                    case "create":
                        label.setIcon(MyIcons.create);
                        break;
                    case "alter":
                        label.setIcon(MyIcons.alter);
                        break;
                    case "drop":
                        label.setIcon(MyIcons.drop);
                        break;
                }
            } else {
                if (title.equals("DML") | title.equals("DDL")) {
                    label.setIcon(AllIcons.Modules.EditFolder);
                    label.setText(title + "    " + node.getChildCount() + " 个表修改");
                } else {
                    label.setIcon(AllIcons.Nodes.DataTables);
                    label.setText(title + "    " + node.getChildCount() + " 个修改");
                }
            }
        }

        label.setOpaque(false);
        this.setOpaque(false);
        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dCheck = check.getPreferredSize();
        Dimension dLabel = label.getPreferredSize();
        return new Dimension(dCheck.width + dLabel.width, Math.max(dCheck.height, dLabel.height));
    }

    @Override
    public void doLayout() {
        Dimension dCheck = check.getPreferredSize();
        Dimension dLabel = label.getPreferredSize();
        int yCheck = 0;
        int yLabel = 0;
        if (dCheck.height < dLabel.height)
            yCheck = (dLabel.height - dCheck.height) / 2;
        else
            yLabel = (dCheck.height - dLabel.height) / 2;
        check.setLocation(0, yCheck);
        check.setBounds(0, yCheck, dCheck.width, dCheck.height);
        label.setLocation(dCheck.width + 6, yLabel);
        label.setBounds(dCheck.width + 6, yLabel, dLabel.width, dLabel.height);
    }

    @Override
    public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }
}
