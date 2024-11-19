package com.flydb.ui;

import com.flydb.ui.base.MyButton;
import com.flydb.util.ButtonUtils;
import com.flydb.util.ColorUtils;
import com.flydb.util.JPanelUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class TestSubmit extends JFrame {

    /**
     * 刷新 | 添加 | 展开 | 关闭 | DDL | DML               数据库host 下拉 | 库 下拉  (请选择数据库 下拉)
     * DDL ..数量
     *  [表图标]更新的表名  ..数量
     *   【CREATE】        SQL
     *   【ALTER】         字段名称(多个)
     *   【ALTER-ADD】     字段名称
     *   【ALTER-MODIFY】  字段名称
     *   【ALTER-DROP】    字段名称
     *   【DROP】          SQL
     *
     * DML
     *  [表图标]更新的表名  ..数量
     *   【INSERT】   SQL
     *   【UPDATE】   SQL
     *   【DELETE】   SQL
     *
     * [提交文本框]
     * [提交按钮]    FlyDB  下拉
     *              FlyDB[classpath:/fly.db]
     *
     */

    public static void main(String[] args) {
        new TestSubmit();
    }

    private static final int RESIZE_AREA_HEIGHT = 5;
    private boolean resizing;
    private int lastMouseY;

    public TestSubmit() {
        JPanel topP = new JPanel();
        topP.setPreferredSize(new Dimension(0, 32));
        topP.setBorder(new EmptyBorder(6, 0, 1, 0)); // 上下左右间距
        topP.setBorder(BorderFactory.createCompoundBorder(topP.getBorder(),
                BorderFactory.createMatteBorder(0, 0, 1, 0, ColorUtils.hexTocolor("#1E1F22"))));
        topP.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));


        ArrayList<MyButton> btnList = new ArrayList<>();
        btnList.add(new MyButton("刷新", "/imgs/refresh_dark.svg"));
        btnList.add(new MyButton("添加", "/imgs/refresh.svg"));
        ButtonUtils.addBtnList(topP, btnList);


        JPanel contentP = new JPanel();
        JPanelUtils.setBottomBorder(contentP);


        JPanel bottomP = new JPanel();
        bottomP.setPreferredSize(new Dimension(0, 150));


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(topP, BorderLayout.NORTH);
        panel.add(contentP, BorderLayout.CENTER);
        panel.add(bottomP, BorderLayout.SOUTH);


        getContentPane().add(panel);
        pack();
        setBounds(0, 0, 400, 800);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
