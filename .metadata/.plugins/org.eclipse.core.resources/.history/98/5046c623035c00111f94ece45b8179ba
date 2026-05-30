package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ChungPanel extends JPanel {
    public ChungPanel() {
        setLayout(new BorderLayout(0, 15)); setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel pForm = new JPanel(new GridBagLayout()); add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(5, 5, 5, 15); gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form tinh chỉnh thông tin hệ thống cơ sở
        UIHelper.addField(pForm, "Tên Thư Viện:", new JTextField("Thư Viện STU"), 0, 0, gbc);
        UIHelper.addField(pForm, "Địa Chỉ:", new JTextField(), 0, 1, gbc);
        UIHelper.addField(pForm, "Nội Quy:", new JTextField(), 2, 0, gbc);
        UIHelper.addField(pForm, "Mã Thuế:", new JTextField(), 2, 1, gbc);
        
        // Nút lưu dữ liệu thiết lập
        GridBagConstraints gbcBtn = (GridBagConstraints) gbc.clone();
        gbcBtn.gridx = 0; gbcBtn.gridy = 3; gbcBtn.gridwidth = 4; gbcBtn.insets = new Insets(15, 5, 5, 5);
        pForm.add(new JButton("Lưu Cài Đặt Hệ Thống"), gbcBtn);
    }
}