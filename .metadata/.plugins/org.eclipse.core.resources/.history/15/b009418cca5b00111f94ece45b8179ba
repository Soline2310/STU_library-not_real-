package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TacGiaPanel extends JPanel {
    public TacGiaPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Các ô nhập liệu thông tin tác giả
        UIHelper.addField(pForm, "Mã Tác Giả:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Tên Tác Giả:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Năm Sinh:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "Quốc Tịch:", new JTextField(), 2, 1, gbc);
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        // Bảng danh sách tác giả
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã Tác Giả", "Tên Tác Giả", "Năm Sinh", "Quốc Tịch"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}