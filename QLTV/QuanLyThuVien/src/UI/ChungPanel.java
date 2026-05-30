package UI;

import entity.CaiDatChung;
import service.CaiDatChungService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ChungPanel extends JPanel {

    private final CaiDatChungService service = new CaiDatChungService();

    private JTextField txtTenThuVien, txtDiaChi, txtNoiQuy, txtMaThue;

    public ChungPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTenThuVien = UIHelper.addField(pForm, "Tên Thư Viện:", new JTextField("Thư Viện STU"), 0, 0, gbc);
        txtDiaChi     = UIHelper.addField(pForm, "Địa Chỉ:",      new JTextField(), 0, 1, gbc);
        txtNoiQuy     = UIHelper.addField(pForm, "Nội Quy:",      new JTextField(), 2, 0, gbc);
        txtMaThue     = UIHelper.addField(pForm, "Mã Thuế:",      new JTextField(), 2, 1, gbc);

        // Lưu button
        GridBagConstraints gbcBtn = (GridBagConstraints) gbc.clone();
        gbcBtn.gridx = 0; gbcBtn.gridy = 3; gbcBtn.gridwidth = 4;
        gbcBtn.insets = new Insets(15, 5, 5, 5);
        JButton btnLuu = new JButton("Lưu Cài Đặt Hệ Thống");
        pForm.add(btnLuu, gbcBtn);

        // Load existing settings from DB
        loadSettings();

        btnLuu.addActionListener(e -> onLuu());
    }

    // ── Handlers ─────────────────────────────────────────────

    private void loadSettings() {
        try {
            CaiDatChung c = service.load();
            txtTenThuVien.setText(c.getTenThuVien() != null ? c.getTenThuVien() : "");
            txtDiaChi    .setText(c.getDiaChi()     != null ? c.getDiaChi()     : "");
            txtNoiQuy    .setText(c.getNoiQuy()     != null ? c.getNoiQuy()     : "");
            txtMaThue    .setText(c.getMaThue()     != null ? c.getMaThue()     : "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể tải cài đặt: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLuu() {
        String ten = txtTenThuVien.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên Thư Viện không được để trống!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            service.save(new CaiDatChung(1, ten,
                txtDiaChi.getText().trim(),
                txtNoiQuy.getText().trim(),
                txtMaThue.getText().trim()));
            JOptionPane.showMessageDialog(this, "Lưu cài đặt thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
