package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PhieuMuonPanel extends JPanel {
    public PhieuMuonPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Các ô thông tin giao dịch mượn sách
        UIHelper.addField(pForm, "Mã Phiếu:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Mã Đọc Giả:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Mã Sách:", new JTextField(), 0, 2, gbc);
        UIHelper.addField(pForm, "Ngày Mượn:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "Hạn Trả:", new JTextField(), 2, 1, gbc);
        
        // Combobox lựa chọn trạng thái phiếu
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; pForm.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pForm.add(new JComboBox<>(new String[]{"Đang mượn", "Đã trả", "Quá hạn"}), gbc);
        
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        // Bảng giám sát phiếu mượn
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã Phiếu", "Mã ĐG", "Mã Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}