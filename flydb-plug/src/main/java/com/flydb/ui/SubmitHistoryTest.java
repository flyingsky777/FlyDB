package com.flydb.ui;

import com.flydb.data.entity.SubmitHistory;
import com.flydb.ui.dto.BeanTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SubmitHistoryTest extends JFrame {

    static List<SubmitHistory> historyList;

    public SubmitHistoryTest() {
        initUI();
    }

    public static void main(String[] args) {
        historyList = new ArrayList<>();
        historyList.add(new SubmitHistory("init"));
        historyList.add(new SubmitHistory("first"));
        historyList.add(new SubmitHistory("send"));

        new SubmitHistoryTest();
    }

    private void initUI() {
//        JList<SubmitHistory> list = new JList<>(historyArray);
//        list.setCellRenderer(new HistoryCellRenderer());
//        list.addListSelectionListener(new HistoryCellListener());

        BeanTableModel<SubmitHistory> historyBeanList = new BeanTableModel<>(historyList, 3, (invoke, rowIndex, columnIndex) -> switch (columnIndex) {
            case 0 -> invoke.getTitle();
            case 1 -> invoke.getName();
            case 2 -> invoke.getTime();
            default -> "";
        });

        JTable table = new JTable(historyBeanList);


        JScrollPane leftPanel = new JScrollPane(table);
        JScrollPane rightPanel = new JScrollPane();


        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(leftPanel);
        panel.setRightComponent(rightPanel);
        panel.setResizeWeight(0.5);
        panel.setDividerSize(1);

        getContentPane().add(panel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}


//class HistoryCellListener implements ListSelectionListener {
//
//    @Override
//    public void valueChanged(ListSelectionEvent e) {
//        if (!e.getValueIsAdjusting()) {
//            JList list = (JList) e.getSource();
//            SubmitHistory entry = (SubmitHistory) list.getSelectedValue();
//            System.out.println(entry.getTitle());
//        }
//    }
//}
//
//class HistoryCellRenderer extends JLabel implements ListCellRenderer<SubmitHistory> {
//
//    @Override
//    public Component getListCellRendererComponent(JList<? extends SubmitHistory> list, SubmitHistory value, int index, boolean isSelected, boolean cellHasFocus) {
//
//        JPanel panel = new JPanel();
//        GridBagLayout gridBagLayout = new GridBagLayout();
//        gridBagLayout.columnWidths = new int[]{0, 0, 0};  //设置了总共有3列
//        gridBagLayout.rowHeights = new int[]{0};  //设置了总共有1行
//        gridBagLayout.columnWeights = new double[]{0.5, 0.2, 0.3};  //设置了列的宽度为容器宽度
//        gridBagLayout.rowWeights = new double[]{1.0};  //第一行的高度占了容器的2份，第二行的高度占了容器的8份
//        panel.setLayout(gridBagLayout);
//
//        JLabel jLabel1 = new JLabel(value.getTitle());
//        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
//        JLabel jLabel2 = new JLabel(value.getName());
//        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
//        JLabel jLabel3 = new JLabel(value.getTime());
//        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
//
//        panel.add(jLabel1);
//        panel.add(jLabel2);
//        panel.add(jLabel3);
//
//        if (isSelected) {
//            setBackground(Color.getHSBColor(46, 67, 110));
//            setForeground(Color.getHSBColor(46, 67, 110));
//        } else {
//            setBackground(Color.white);
//            setForeground(Color.black);
//        }
//        return panel;
//    }
//}

