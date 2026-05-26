package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PhieuNhapPanel extends JPanel {
    public PhieuNhapPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form ghi nhận thông tin lô sách nhập kho
        UIHelper.addField(pForm, "Mã Phiếu Nhập:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Mã Sách:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Số Lượng:", new JTextField(), 0, 2, gbc);
        UIHelper.addField(pForm, "Nhà Cung Cấp:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "Ngày Nhập:", new JTextField(), 2, 1, gbc);
        
        // Combobox tình trạng giao nhận hàng
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; pForm.add(new JLabel("Tình Trạng:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pForm.add(new JComboBox<>(new String[]{"Đã nhận", "Đang giao", "Hoàn trả"}), gbc);
        
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        // Bảng danh sách phiếu nhập kho
        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã Phiếu", "Mã Sách", "Số Lượng", "Nhà Cung Cấp", "Ngày Nhập", "Tình Trạng"}));
        tbl.setRowHeight(25); add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}