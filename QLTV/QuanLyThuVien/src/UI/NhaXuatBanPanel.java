package UI;

import entity.NhaXuatBan;
import service.NhaXuatBanService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class NhaXuatBanPanel extends JPanel {

    private final NhaXuatBanService service = new NhaXuatBanService();

    private JTextField txtMaNXB, txtTenNXB, txtDiaChi, txtSDT;
    private DefaultTableModel tableModel;

    public NhaXuatBanPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaNXB  = UIHelper.addField(pForm, "Mã NXB:",  new JTextField(), 0, 0, gbc);
        txtTenNXB = UIHelper.addField(pForm, "Tên NXB:", new JTextField(), 0, 1, gbc);
        txtDiaChi = UIHelper.addField(pForm, "Địa Chỉ:", new JTextField(), 2, 0, gbc);
        txtSDT    = UIHelper.addField(pForm, "SĐT:",     new JTextField(), 2, 1, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NXB", "Tên NXB", "Địa Chỉ", "SĐT"}
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
                txtMaNXB .setText(val(row, 0));
                txtTenNXB.setText(val(row, 1));
                txtDiaChi.setText(val(row, 2));
                txtSDT   .setText(val(row, 3));
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
        String ma  = txtMaNXB.getText().trim();
        String ten = txtTenNXB.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên NXB!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            service.add(new NhaXuatBan(ma, ten, txtDiaChi.getText().trim(), txtSDT.getText().trim()));
            JOptionPane.showMessageDialog(this, "Thêm nhà xuất bản thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaNXB.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một NXB từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật NXB \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            service.update(new NhaXuatBan(ma, txtTenNXB.getText().trim(), txtDiaChi.getText().trim(), txtSDT.getText().trim()));
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaNXB.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một NXB từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.coSachLienKet(ma)) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa! NXB \"" + ma + "\" đang có sách trong hệ thống.",
                "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa NXB \"" + ma + "\"? Hành động này không thể hoàn tác.",
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
        txtMaNXB.setText(""); txtTenNXB.setText("");
        txtDiaChi.setText(""); txtSDT.setText("");
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<NhaXuatBan> list) {
        tableModel.setRowCount(0);
        for (NhaXuatBan n : list)
            tableModel.addRow(new Object[]{n.getMaNXB(), n.getTenNXB(), n.getDiaChi(), n.getSDT()});
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }
}
