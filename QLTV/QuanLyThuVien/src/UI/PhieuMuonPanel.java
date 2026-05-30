package UI;

import entity.PhieuMuon;
import entity.DocGia;
import entity.Sach;
import service.PhieuMuonService;
import service.DocGiaService;
import service.SachService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PhieuMuonPanel extends JPanel {

    private final PhieuMuonService service    = new PhieuMuonService();
    private final DocGiaService    dgService  = new DocGiaService();
    private final SachService      sachService = new SachService();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtMaPhieu, txtMaDocGia, txtMaSach, txtNgayMuon, txtHanTra;
    private JComboBox<String> cbTrangThai;
    private DefaultTableModel tableModel;

    public PhieuMuonPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaPhieu  = UIHelper.addField(pForm, "Mã Phiếu:",   new JTextField(), 0, 0, gbc);
        txtMaDocGia = UIHelper.addField(pForm, "Mã Đọc Giả:", new JTextField(), 0, 1, gbc);
        txtMaSach   = UIHelper.addField(pForm, "Mã Sách:",     new JTextField(), 0, 2, gbc);
        txtNgayMuon = UIHelper.addField(pForm, "Ngày Mượn:",   new JTextField(), 2, 0, gbc);
        txtHanTra   = UIHelper.addField(pForm, "Hạn Trả:",     new JTextField(), 2, 1, gbc);

        // ComboBox Trạng Thái — visual unchanged
        cbTrangThai = new JComboBox<>(new String[]{"Đang mượn", "Đã trả", "Quá hạn"});
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        pForm.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        pForm.add(cbTrangThai, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Phiếu", "Mã ĐG", "Mã Sách", "Ngày Mượn", "Hạn Trả", "Trạng Thái"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(tableModel);
        tbl.setRowHeight(25);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Auto-mark overdue then load
        service.capNhatQuaHan();
        loadTable(service.getAll());

        // Row click → fill form
        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() >= 0) {
                int row = tbl.getSelectedRow();
                txtMaPhieu .setText(val(row, 0));
                txtMaDocGia.setText(val(row, 1));
                txtMaSach  .setText(val(row, 2));
                txtNgayMuon.setText(val(row, 3));
                txtHanTra  .setText(val(row, 4));
                cbTrangThai.setSelectedItem(val(row, 5));
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
        String ma     = txtMaPhieu.getText().trim();
        String maDG   = txtMaDocGia.getText().trim();
        String maSach = txtMaSach.getText().trim();
        String ngayMuonStr = txtNgayMuon.getText().trim();
        String hanTraStr   = txtHanTra.getText().trim();

        if (ma.isEmpty() || maDG.isEmpty() || maSach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Phiếu, Mã Đọc Giả và Mã Sách!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DocGia dg = dgService.getAll().stream()
                .filter(d -> d.getMaDocGia().equalsIgnoreCase(maDG)).findFirst().orElse(null);
        if (dg == null) {
            JOptionPane.showMessageDialog(this, "Mã Đọc Giả \"" + maDG + "\" không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sach sach = sachService.getAll().stream()
                .filter(s -> s.getMaSach().equalsIgnoreCase(maSach)).findFirst().orElse(null);
        if (sach == null) {
            JOptionPane.showMessageDialog(this, "Mã Sách \"" + maSach + "\" không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sach.getSoLuong() <= 0) {
            JOptionPane.showMessageDialog(this, "Sách \"" + maSach + "\" đã hết trong kho!", "Không thể mượn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate ngayMuon = ngayMuonStr.isEmpty() ? LocalDate.now() : parseNgay(ngayMuonStr);
        if (ngayMuon == null) return;

        LocalDate hanTra = hanTraStr.isEmpty() ? ngayMuon.plusDays(14) : parseNgay(hanTraStr);
        if (hanTra == null) return;

        try {
            service.add(new PhieuMuon(ma, dg, sach, ngayMuon, hanTra, (String) cbTrangThai.getSelectedItem()));
            JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công!");
            onLamMoi();
        } catch (IllegalStateException ex) {
            // Business rule violation from service (max 3 books)
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Không thể mượn", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaPhieu.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PhieuMuon existing = service.findById(ma);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu \"" + ma + "\"!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate ngayMuon = parseNgay(txtNgayMuon.getText().trim());
        if (ngayMuon == null && !txtNgayMuon.getText().trim().isEmpty()) return;
        LocalDate hanTra = parseNgay(txtHanTra.getText().trim());
        if (hanTra == null && !txtHanTra.getText().trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật phiếu \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            // Only update dates and status — keep DocGia and Sach unchanged
            if (ngayMuon != null) existing.setNgayMuon(ngayMuon);
            if (hanTra   != null) existing.setHanTra(hanTra);
            existing.setTrangThai((String) cbTrangThai.getSelectedItem());
            service.update(existing);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaPhieu.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa phiếu mượn \"" + ma + "\"? Hành động này không thể hoàn tác.",
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
        txtMaPhieu.setText(""); txtMaDocGia.setText(""); txtMaSach.setText("");
        txtNgayMuon.setText(""); txtHanTra.setText("");
        cbTrangThai.setSelectedIndex(0);
        service.capNhatQuaHan();
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<PhieuMuon> list) {
        tableModel.setRowCount(0);
        for (PhieuMuon p : list)
            tableModel.addRow(new Object[]{
                p.getMaPhieu(), p.getMaDocGia(), p.getMaSach(),
                p.getNgayMuon() != null ? p.getNgayMuon().format(FMT) : "",
                p.getHanTra()   != null ? p.getHanTra().format(FMT)   : "",
                p.getTrangThai()
            });
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
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
