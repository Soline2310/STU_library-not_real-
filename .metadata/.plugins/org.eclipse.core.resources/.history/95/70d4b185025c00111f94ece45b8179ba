package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {
    public ThongKePanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Bộ lọc mốc thời gian báo cáo
        UIHelper.addField(pForm, "Từ Ngày:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Đến Ngày:", new JTextField(), 2, 0, gbc);
        
        // Nút xuất dữ liệu báo cáo
        GridBagConstraints gbcBtn = (GridBagConstraints) gbc.clone();
        gbcBtn.gridx = 0; gbcBtn.gridy = 1; gbcBtn.gridwidth = 4; gbcBtn.insets = new Insets(10, 5, 5, 5);
        pForm.add(new JButton("Lập Báo Cáo Thống Kê Tổng Quan"), gbcBtn);
        
        // Bảng kết quả tổng hợp báo cáo
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Loại Báo Cáo", "Tổng Số Lượng", "Ghi Chú"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}