package UI;

import service.ThongKeService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {

    private final ThongKeService service = new ThongKeService();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtTuNgay, txtDenNgay;
    private DefaultTableModel tableModel;

    public ThongKePanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pForm = new JPanel(new GridBagLayout());
        add(pForm, BorderLayout.NORTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTuNgay  = UIHelper.addField(pForm, "Từ Ngày:", new JTextField(), 0, 0, gbc);
        txtDenNgay = UIHelper.addField(pForm, "Đến Ngày:", new JTextField(), 2, 0, gbc);

        // Lập Báo Cáo button
        GridBagConstraints gbcBtn = (GridBagConstraints) gbc.clone();
        gbcBtn.gridx = 0; gbcBtn.gridy = 1; gbcBtn.gridwidth = 4;
        gbcBtn.insets = new Insets(10, 5, 5, 5);
        JButton btnBaoCao = new JButton("Lập Báo Cáo Thống Kê Tổng Quan");
        pForm.add(btnBaoCao, gbcBtn);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Loại Báo Cáo", "Tổng Số Lượng", "Ghi Chú"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(tableModel);
        tbl.setRowHeight(25);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        btnBaoCao.addActionListener(e -> onLapBaoCao());
    }

    // ── Report generation ────────────────────────────────────

    private void onLapBaoCao() {
        String tuStr  = txtTuNgay.getText().trim();
        String denStr = txtDenNgay.getText().trim();

        // Default to current month if empty
        LocalDate tuNgay  = tuStr.isEmpty()  ? LocalDate.now().withDayOfMonth(1) : parseNgay(tuStr);
        LocalDate denNgay = denStr.isEmpty() ? LocalDate.now()                   : parseNgay(denStr);

        if (tuNgay == null || denNgay == null) return;

        if (tuNgay.isAfter(denNgay)) {
            JOptionPane.showMessageDialog(this, "Từ Ngày không được sau Đến Ngày!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);

        // ── Section 1: Sách được mượn nhiều nhất ─────────────
        tableModel.addRow(new Object[]{"─── THỐNG KÊ SÁCH MƯỢN ───", "", ""});
        List<Object[]> sachRows = service.thongKeSachMuon(tuNgay, denNgay);
        if (sachRows.isEmpty()) {
            tableModel.addRow(new Object[]{"Sách", "0", "Không có lượt mượn trong kỳ"});
        } else {
            for (Object[] row : sachRows)
                tableModel.addRow(new Object[]{
                    row[1],          // Tên Sách
                    row[2],          // Tổng lượt mượn
                    "Mã: " + row[0]  // Mã Sách as note
                });
        }

        // ── Section 2: Độc giả mượn nhiều nhất ───────────────
        tableModel.addRow(new Object[]{"", "", ""});
        tableModel.addRow(new Object[]{"─── THỐNG KÊ ĐỌC GIẢ ───", "", ""});
        List<Object[]> dgRows = service.thongKeDocGia(tuNgay, denNgay);
        if (dgRows.isEmpty()) {
            tableModel.addRow(new Object[]{"Độc giả", "0", "Không có lượt mượn trong kỳ"});
        } else {
            for (Object[] row : dgRows)
                tableModel.addRow(new Object[]{
                    row[1],          // Tên Độc Giả
                    row[2],          // Tổng lượt mượn
                    "Mã: " + row[0]  // Mã ĐG as note
                });
        }

        JOptionPane.showMessageDialog(this,
            "Báo cáo từ " + tuNgay.format(FMT) + " đến " + denNgay.format(FMT) + " đã được lập.",
            "Hoàn thành", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Helpers ──────────────────────────────────────────────

    private LocalDate parseNgay(String s) {
        if (s.isEmpty()) return null;
        try { return LocalDate.parse(s, FMT); }
        catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Định dạng: dd/MM/yyyy", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
