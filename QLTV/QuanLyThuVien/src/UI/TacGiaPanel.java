package UI;

import entity.TacGia;
import service.TacGiaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TacGiaPanel extends JPanel {

    private final TacGiaService service = new TacGiaService();

    private JTextField txtMaTacGia, txtTenTacGia, txtNamSinh, txtQuocTich;
    private DefaultTableModel tableModel;

    public TacGiaPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaTacGia  = UIHelper.addField(pForm, "Mã Tác Giả:",  new JTextField(), 0, 0, gbc);
        txtTenTacGia = UIHelper.addField(pForm, "Tên Tác Giả:", new JTextField(), 0, 1, gbc);
        txtNamSinh   = UIHelper.addField(pForm, "Năm Sinh:",    new JTextField(), 2, 0, gbc);
        txtQuocTich  = UIHelper.addField(pForm, "Quốc Tịch:",   new JTextField(), 2, 1, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Tác Giả", "Tên Tác Giả", "Năm Sinh", "Quốc Tịch"}
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
                txtMaTacGia .setText(val(row, 0));
                txtTenTacGia.setText(val(row, 1));
                txtNamSinh  .setText(val(row, 2));
                txtQuocTich .setText(val(row, 3));
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
        String ma  = txtMaTacGia.getText().trim();
        String ten = txtTenTacGia.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên Tác Giả!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer namSinh = parseNamSinh(txtNamSinh.getText().trim());
        if (namSinh == null && !txtNamSinh.getText().trim().isEmpty()) return;

        try {
            service.add(new TacGia(ma, ten, namSinh, txtQuocTich.getText().trim()));
            JOptionPane.showMessageDialog(this, "Thêm tác giả thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaTacGia.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer namSinh = parseNamSinh(txtNamSinh.getText().trim());
        if (namSinh == null && !txtNamSinh.getText().trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật tác giả \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            service.update(new TacGia(ma, txtTenTacGia.getText().trim(), namSinh, txtQuocTich.getText().trim()));
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaTacGia.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.coSachLienKet(ma)) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa! Tác giả \"" + ma + "\" đang có sách trong hệ thống.",
                "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa tác giả \"" + ma + "\"? Hành động này không thể hoàn tác.",
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
        txtMaTacGia.setText(""); txtTenTacGia.setText("");
        txtNamSinh.setText(""); txtQuocTich.setText("");
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<TacGia> list) {
        tableModel.setRowCount(0);
        for (TacGia t : list)
            tableModel.addRow(new Object[]{
                t.getMaTacGia(), t.getTenTacGia(), t.getNamSinh(), t.getQuocTich()
            });
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }

    private Integer parseNamSinh(String s) {
        if (s.isEmpty()) return null;
        try { return Integer.parseInt(s); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
