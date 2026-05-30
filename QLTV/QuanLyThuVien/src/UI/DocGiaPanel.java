package UI;

import entity.DocGia;
import service.DocGiaService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DocGiaPanel extends JPanel {

    private final DocGiaService service = new DocGiaService();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtMaDocGia, txtTenDocGia, txtNgaySinh, txtSDT, txtEmail, txtDiaChi;
    private DefaultTableModel tableModel;

    public DocGiaPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaDocGia  = UIHelper.addField(pForm, "Mã Đọc Giả:",  new JTextField(), 0, 0, gbc);
        txtTenDocGia = UIHelper.addField(pForm, "Tên Đọc Giả:", new JTextField(), 0, 1, gbc);
        txtNgaySinh  = UIHelper.addField(pForm, "Ngày Sinh:",   new JTextField(), 0, 2, gbc);
        txtSDT       = UIHelper.addField(pForm, "SĐT:",         new JTextField(), 2, 0, gbc);
        txtEmail     = UIHelper.addField(pForm, "Email:",       new JTextField(), 2, 1, gbc);
        txtDiaChi    = UIHelper.addField(pForm, "Địa Chỉ:",     new JTextField(), 2, 2, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã ĐG", "Tên", "Ngày Sinh", "SĐT", "Email", "Địa Chỉ"}
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
                txtMaDocGia .setText(val(row, 0));
                txtTenDocGia.setText(val(row, 1));
                txtNgaySinh .setText(val(row, 2));
                txtSDT      .setText(val(row, 3));
                txtEmail    .setText(val(row, 4));
                txtDiaChi   .setText(val(row, 5));
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
        String ma  = txtMaDocGia.getText().trim();
        String ten = txtTenDocGia.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên Đọc Giả!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate ngaySinh = parseNgay(txtNgaySinh.getText().trim());
        if (ngaySinh == null && !txtNgaySinh.getText().trim().isEmpty()) return;

        try {
            service.add(new DocGia(ma, ten, ngaySinh,
                    txtSDT.getText().trim(), txtEmail.getText().trim(), txtDiaChi.getText().trim()));
            JOptionPane.showMessageDialog(this, "Thêm đọc giả thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaDocGia.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đọc giả từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate ngaySinh = parseNgay(txtNgaySinh.getText().trim());
        if (ngaySinh == null && !txtNgaySinh.getText().trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật đọc giả \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            service.update(new DocGia(ma, txtTenDocGia.getText().trim(), ngaySinh,
                    txtSDT.getText().trim(), txtEmail.getText().trim(), txtDiaChi.getText().trim()));
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaDocGia.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đọc giả từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.coPhieuMuonDangMuon(ma)) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa! Đọc giả \"" + ma + "\" đang có sách chưa trả.",
                "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa đọc giả \"" + ma + "\"? Hành động này không thể hoàn tác.",
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
        txtMaDocGia.setText(""); txtTenDocGia.setText(""); txtNgaySinh.setText("");
        txtSDT.setText(""); txtEmail.setText(""); txtDiaChi.setText("");
        loadTable(service.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<DocGia> list) {
        tableModel.setRowCount(0);
        for (DocGia d : list)
            tableModel.addRow(new Object[]{
                d.getMaDocGia(), d.getTenDocGia(),
                d.getNgaySinh() != null ? d.getNgaySinh().format(FMT) : "",
                d.getSDT(), d.getEmail(), d.getDiaChi()
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
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ! Định dạng: dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
