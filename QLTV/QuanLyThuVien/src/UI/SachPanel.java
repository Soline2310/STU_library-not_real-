package UI;

import entity.Sach;
import entity.TacGia;
import entity.NhaXuatBan;
import service.SachService;
import service.TacGiaService;
import service.NhaXuatBanService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SachPanel extends JPanel {

    private final SachService       sachService = new SachService();
    private final TacGiaService     tgService   = new TacGiaService();
    private final NhaXuatBanService nxbService  = new NhaXuatBanService();

    private JTextField txtMaSach, txtTenSach, txtTacGia, txtTheLoai, txtNhaXB, txtSoLuong;
    private DefaultTableModel tableModel;

    public SachPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSach  = UIHelper.addField(pForm, "Mã Sách:",  new JTextField(), 0, 0, gbc);
        txtTenSach = UIHelper.addField(pForm, "Tên Sách:", new JTextField(), 0, 1, gbc);
        txtTacGia  = UIHelper.addField(pForm, "Tác Giả:",  new JTextField(), 0, 2, gbc);
        txtTheLoai = UIHelper.addField(pForm, "Thể Loại:", new JTextField(), 2, 0, gbc);
        txtNhaXB   = UIHelper.addField(pForm, "Nhà XB:",   new JTextField(), 2, 1, gbc);
        txtSoLuong = UIHelper.addField(pForm, "Số Lượng:", new JTextField(), 2, 2, gbc);

        UIHelper.ButtonBar bar = UIHelper.addButtonsAndSearch(pForm, gbc, new JTextField());

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "NXB", "Số Lượng"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(tableModel);
        tbl.setRowHeight(25);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        loadTable(sachService.getAll());

        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() >= 0) {
                int row = tbl.getSelectedRow();
                txtMaSach .setText(val(row, 0));
                txtTenSach.setText(val(row, 1));
                txtTacGia .setText(val(row, 2));
                txtTheLoai.setText(val(row, 3));
                txtNhaXB  .setText(val(row, 4));
                txtSoLuong.setText(val(row, 5));
            }
        });

        bar.btnTim.addActionListener(e -> {
            String kw = bar.txtTim.getText().trim();
            loadTable(kw.isEmpty() ? sachService.getAll() : sachService.search(kw));
        });
        bar.txtTim.addActionListener(e -> bar.btnTim.doClick());

        bar.btnThem   .addActionListener(e -> onThem());
        bar.btnCapNhat.addActionListener(e -> onCapNhat());
        bar.btnXoa    .addActionListener(e -> onXoa());
        bar.btnLamMoi .addActionListener(e -> onLamMoi());
    }

    private void onThem() {
        String ma      = txtMaSach.getText().trim();
        String ten     = txtTenSach.getText().trim();
        String maTG    = txtTacGia.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        String maNXB   = txtNhaXB.getText().trim();
        String slStr   = txtSoLuong.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã Sách và Tên Sách!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TacGia tg = resolveTA(maTG); if (tg == null && !maTG.isEmpty()) return;
        NhaXuatBan nxb = resolveNXB(maNXB); if (nxb == null && !maNXB.isEmpty()) return;

        int sl = parseSoLuong(slStr); if (sl < 0) return;

        try {
            sachService.add(new Sach(ma, ten, tg, theLoai, nxb, sl));
            JOptionPane.showMessageDialog(this, "Thêm sách thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCapNhat() {
        String ma = txtMaSach.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maTG  = txtTacGia.getText().trim();
        String maNXB = txtNhaXB.getText().trim();

        TacGia tg = resolveTA(maTG); if (tg == null && !maTG.isEmpty()) return;
        NhaXuatBan nxb = resolveNXB(maNXB); if (nxb == null && !maNXB.isEmpty()) return;
        int sl = parseSoLuong(txtSoLuong.getText().trim()); if (sl < 0) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Cập nhật sách \"" + ma + "\"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            sachService.update(new Sach(ma, txtTenSach.getText().trim(), tg, txtTheLoai.getText().trim(), nxb, sl));
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onXoa() {
        String ma = txtMaSach.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách từ bảng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (sachService.coPhieuMuonDangMuon(ma)) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa! Sách \"" + ma + "\" đang có phiếu mượn chưa trả.",
                "Không thể xóa", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa sách \"" + ma + "\"? Hành động này không thể hoàn tác.",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            sachService.delete(ma);
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            onLamMoi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLamMoi() {
        txtMaSach.setText(""); txtTenSach.setText(""); txtTacGia.setText("");
        txtTheLoai.setText(""); txtNhaXB.setText(""); txtSoLuong.setText("");
        loadTable(sachService.getAll());
    }

    // ── Helpers ──────────────────────────────────────────────

    private void loadTable(List<Sach> list) {
        tableModel.setRowCount(0);
        for (Sach s : list)
            tableModel.addRow(new Object[]{
                s.getMaSach(), s.getTenSach(), s.getTenTacGia(),
                s.getTheLoai(), s.getTenNXB(), s.getSoLuong()
            });
    }

    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v != null ? v.toString() : "";
    }

    private TacGia resolveTA(String maTG) {
        if (maTG.isEmpty()) return null;
        TacGia tg = tgService.getAll().stream()
                .filter(t -> t.getMaTacGia().equalsIgnoreCase(maTG)).findFirst().orElse(null);
        if (tg == null)
            JOptionPane.showMessageDialog(this, "Mã Tác Giả \"" + maTG + "\" không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return tg;
    }

    private NhaXuatBan resolveNXB(String maNXB) {
        if (maNXB.isEmpty()) return null;
        NhaXuatBan nxb = nxbService.getAll().stream()
                .filter(n -> n.getMaNXB().equalsIgnoreCase(maNXB)).findFirst().orElse(null);
        if (nxb == null)
            JOptionPane.showMessageDialog(this, "Mã NXB \"" + maNXB + "\" không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return nxb;
    }

    private int parseSoLuong(String s) {
        try { return s.isEmpty() ? 0 : Integer.parseInt(s); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}
