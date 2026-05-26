package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThuThuPanel extends JPanel {
    public ThuThuPanel() {
        setLayout(new BorderLayout(0, 15)); 
        setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); 
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(5, 5, 5, 15); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        UIHelper.addField(pForm, "Mã Thủ Thư:", new JTextField(), 0, 0, gbc);
        UIHelper.addField(pForm, "Tên Thủ Thư:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Ca Trực:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "SĐT:", new JTextField(), 2, 1, gbc);
        UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());
        
        if (!Auth.isAdmin()) {
            for (Component c : pForm.getComponents()) {
                if (c instanceof JButton) {
                    JButton btn = (JButton) c;
                    String btnText = btn.getText();
                    if (btnText.contains("Thêm") || btnText.contains("Cập Nhật") || btnText.contains("Xóa")) {
                        btn.setEnabled(false); 
                    }
                }
            }
        }

        JTable tbl = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Mã TT", "Tên Thủ Thư", "Ca Trực", "SĐT"}));
        tbl.setRowHeight(25); 
        add(new JScrollPane(tbl), BorderLayout.CENTER);
    }
}