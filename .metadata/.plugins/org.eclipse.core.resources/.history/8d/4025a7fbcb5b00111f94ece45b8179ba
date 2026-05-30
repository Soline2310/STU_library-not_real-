package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class NhaXuatBanPanel extends JPanel {
    public NhaXuatBanPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Các ô nhập liệu đối tác NXB
        UIHelper.addField(pForm, "Mã NXB:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Tên NXB:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Địa Chỉ:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "SĐT:", new JTextField(), 2, 1, gbc);
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        // Bảng danh sách nhà xuất bản
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã NXB", "Tên NXB", "Địa Chỉ", "SĐT"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}