package UI;

import entity.ThuThu;
import service.ThuThuService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThuThuPanel extends JPanel {

    private final ThuThuService service = new ThuThuService();

    private JTextField txtMaThuThu, txtTenThuThu, txtCaTruc, txtSDT;
    private DefaultTableModel tableModel;

    public ThuThuPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaThuThu  = UIHelper.addField(pForm, "Mã Thủ Thư:",  new JTextField(), 0, 0, gbc);
        txtTenThuThu = UIHelper.addField(pForm, "Tên Thủ Thư:", new JTextField(), 0, 1, gbc);
        txtCaTruc    = UIHelper.addField(pForm, "Ca Trực:",      new JTextField(), 2, 0, gbc);
        txtSDT       = UIHelper.addField(pForm, "SĐT:",          new JTextField(), 2, 1, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        // Non-admin cannot add/edit/delete
        if (!Auth.isAdmin()) {
            bar.btnThem.setEnabled(false);
            bar.btnCapNhat.setEnabled(false);
            bar.btnXoa.setEnabled(false);
        }

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã TT", "Tên Thủ Thư", "Ca Trực", "SĐT"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(tableModel);
        tbl.setRowHeight(25);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        loadTable(service.getAll());

        // Row click → fill form
        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() >= 0) {
                int row = tbl.getSelectedRow();
                txtMaThuThu .setText(val(row, 0));
                txtTenThuThu.setText(val(row, 1));
                txtCaTruc   .setText(val(row, 2));
                txtSDT      .setText(val(row, 3));
            }
        });

        // Search
        bar.btnTim.addActionListener(e -> {
            String kw = bar.txtTim.getText().trim();
            loadTable(kw.isEmpty() ? service.getAll() : service.search(kw));
        });
        bar.txtTim.addActionListener(e -> bar.btnTim.doClick());

        // Buttons
        bar.btnThem   .addActionListener(e -> onThem());
        bar.btnCapNhat.addActionListener(e -> onCapNhat());
        bar.btnXoa    .addActionListener(e -> onXoa());
        bar.btnLamMoi .addActionListener(e -> onLamMoi());
    }

    // ── CRUD ─────────────────────────────────────────────────

    private void onThem() {
        String ma  = txtMaThuThu.getText().trim();
        String ten = txtTenThuThu.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên Thủ Thư!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Prompt for account credentials on add
        JTextField txtTK = new JTextField();
        JPasswordField txtMK = new JPasswordField();
        JTextField txtVaiTro = new JTextField("Thu thu");
        Object[] fields = {"Tài Khoản:", txtTK, "Mật Khẩu:", txtMK, "Vai Trò (Admin / Thu thu):", txtVaiTro};
        int opt = JOptionPane.showConfirmDialog(this, fields, "Thông tin đăng nhập", JOptionPane.OK_CANCEL_OPTION);
        if (opt != JOptionPane.OK_OPTION) return;

        String tk = txtTK.getText().trim();
        String mk = new String(txtMK.getPassword()).trim();
        String vt = txtVaiTro.getText().trim();

        if (tk.isEmpty() || mk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tài khoản và mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            service.add(new ThuThu(ma, ten, txtCaTruc.getText().trim(), txtSDT.getText().trim(), tk, mk, vt));
            JOptionPane.showMessageDialog(this, "Thêm thủ thư thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaThuThu.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thủ thư từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Fetch existing record to keep credentials unchanged unless user edits
        ThuThu existing = service.findById(ma);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thủ thư \"" + ma + "\"!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật thủ thư \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            // Keep existing taiKhoan, matKhau, vaiTro — only update display fields
            existing.setTenThuThu(txtTenThuThu.getText().trim());
            existing.setCaTruc(txtCaTruc.getText().trim());
            existing.setSDT(txtSDT.getText().trim());
            service.update(existing);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaThuThu.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thủ thư từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Prevent deleting yourself
        if (ma.equals(Auth.maThuThu)) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản đang đăng nhập!", "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa thủ thư \"" + ma + "\"? Hành động này không thể hoàn tác.",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            service.delete(ma);
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLamMoi() {
        txtMaThuThu.setText(""); txtTenThuThu.setText("");
        txtCaTruc.setText(""); txtSDT.setText("");
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<ThuThu> list) {
        tableModel.setRowCount(0);
        for (ThuThu t : list)
            tableModel.addRow(new Object[]{t.getMaThuThu(), t.getTenThuThu(), t.getCaTruc(), t.getSDT()});
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }
}
