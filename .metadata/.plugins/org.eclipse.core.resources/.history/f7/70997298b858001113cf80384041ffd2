package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SachPanel extends JPanel {
    public SachPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Các ô nhập liệu thông tin sách
        UIHelper.addField(pForm, "Mã Sách:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Tên Sách:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Tác Giả:", new JTextField(), 0, 2, gbc);
        UIHelper.addField(pForm, "Thể Loại:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "Nhà XB:", new JTextField(), 2, 1, gbc);
        UIHelper.addField(pForm, "Số Lượng:", new JTextField(), 2, 2, gbc);
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        // Bảng dữ liệu hiển thị danh sách sách
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "NXB", "Số Lượng"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}