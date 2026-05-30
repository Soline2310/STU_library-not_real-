package UI;

import entity.PhieuNhap;
import entity.Sach;
import service.PhieuNhapService;
import service.SachService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PhieuNhapPanel extends JPanel {

    private final PhieuNhapService service     = new PhieuNhapService();
    private final SachService      sachService = new SachService();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtMaPhieuNhap, txtMaSach, txtSoLuong, txtNhaCungCap, txtNgayNhap;
    private JComboBox<String> cbTinhTrang;
    private DefaultTableModel tableModel;

    public PhieuNhapPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaPhieuNhap = UIHelper.addField(pForm, "Mã Phiếu Nhập:", new JTextField(), 0, 0, gbc);
        txtMaSach      = UIHelper.addField(pForm, "Mã Sách:",        new JTextField(), 0, 1, gbc);
        txtSoLuong     = UIHelper.addField(pForm, "Số Lượng:",       new JTextField(), 0, 2, gbc);
        txtNhaCungCap  = UIHelper.addField(pForm, "Nhà Cung Cấp:",   new JTextField(), 2, 0, gbc);
        txtNgayNhap    = UIHelper.addField(pForm, "Ngày Nhập:",      new JTextField(), 2, 1, gbc);

        // ComboBox Tình Trạng — visual unchanged
        cbTinhTrang = new JComboBox<>(new String[]{"Đã nhận", "Đang giao", "Hoàn trả"});
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        pForm.add(new JLabel("Tình Trạng:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        pForm.add(cbTinhTrang, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Phiếu", "Mã Sách", "Số Lượng", "Nhà Cung Cấp", "Ngày Nhập", "Tình Trạng"}
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
                txtMaPhieuNhap.setText(val(row, 0));
                txtMaSach     .setText(val(row, 1));
                txtSoLuong    .setText(val(row, 2));
                txtNhaCungCap .setText(val(row, 3));
                txtNgayNhap   .setText(val(row, 4));
                cbTinhTrang.setSelectedItem(val(row, 5));
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
        String ma    = txtMaPhieuNhap.getText().trim();
        String maSach = txtMaSach.getText().trim();
        String slStr  = txtSoLuong.getText().trim();

        if (ma.isEmpty() || maSach.isEmpty() || slStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Phiếu, Mã Sách và Số Lượng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sach sach = sachService.getAll().stream()
                .filter(s -> s.getMaSach().equalsIgnoreCase(maSach)).findFirst().orElse(null);
        if (sach == null) {
            JOptionPane.showMessageDialog(this, "Mã Sách \"" + maSach + "\" không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sl = parseSoLuong(slStr);
        if (sl < 0) return;

        LocalDate ngayNhap = txtNgayNhap.getText().trim().isEmpty()
                ? LocalDate.now() : parseNgay(txtNgayNhap.getText().trim());
        if (ngayNhap == null) return;

        try {
            service.add(new PhieuNhap(ma, sach, sl, txtNhaCungCap.getText().trim(),
                    ngayNhap, (String) cbTinhTrang.getSelectedItem()));
            JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaPhieuNhap.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PhieuNhap existing = service.findById(ma);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu \"" + ma + "\"!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sl = parseSoLuong(txtSoLuong.getText().trim());
        if (sl < 0) return;

        LocalDate ngayNhap = parseNgay(txtNgayNhap.getText().trim());
        if (ngayNhap == null && !txtNgayNhap.getText().trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật phiếu nhập \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            existing.setSoLuong(sl);
            existing.setNhaCungCap(txtNhaCungCap.getText().trim());
            if (ngayNhap != null) existing.setNgayNhap(ngayNhap);
            existing.setTinhTrang((String) cbTinhTrang.getSelectedItem());
            service.update(existing);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaPhieuNhap.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa phiếu nhập \"" + ma + "\"? Hành động này không thể hoàn tác.",
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
        txtMaPhieuNhap.setText(""); txtMaSach.setText(""); txtSoLuong.setText("");
        txtNhaCungCap.setText(""); txtNgayNhap.setText("");
        cbTinhTrang.setSelectedIndex(0);
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<PhieuNhap> list) {
        tableModel.setRowCount(0);
        for (PhieuNhap p : list)
            tableModel.addRow(new Object[]{
                p.getMaPhieuNhap(), p.getMaSach(), p.getSoLuong(),
                p.getNhaCungCap(),
                p.getNgayNhap() != null ? p.getNgayNhap().format(FMT) : "",
                p.getTinhTrang()
            });
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }

    private int parseSoLuong(String s) {
        try { return s.isEmpty() ? 0 : Integer.parseInt(s); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private LocalDate parseNgay(String s) {
        if (s.isEmpty()) return null;
        try { return LocalDate.parse(s, FMT); }
        catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Định dạng: dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
