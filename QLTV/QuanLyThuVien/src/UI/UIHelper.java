package UI;

import javax.swing.*;
import java.awt.*;

public class UIHelper {

    /** Result holder returned by addButtonsAndSearch() */
    public static class ButtonBar {
        public final JButton btnThem;
        public final JButton btnCapNhat;
        public final JButton btnXoa;
        public final JButton btnLamMoi;
        public final JButton btnTim;
        public final JTextField txtTim;

        public ButtonBar(JButton them, JButton capNhat, JButton xoa,
                         JButton lamMoi, JButton tim, JTextField txtTim) {
            this.btnThem    = them;
            this.btnCapNhat = capNhat;
            this.btnXoa     = xoa;
            this.btnLamMoi  = lamMoi;
            this.btnTim     = tim;
            this.txtTim     = txtTim;
        }
    }

    /** Result holder returned by addField() */
    public static class FieldResult {
        public final JTextField tf;
        public FieldResult(JTextField tf) { this.tf = tf; }
    }

    // Hàm tạo ô nhập liệu nhanh — now returns the JTextField for wiring
    public static JTextField addField(JPanel p, String lbl, JTextField tf,
                                      int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;
        p.add(new JLabel(lbl), gbc);
        gbc.gridx = x + 1;
        gbc.weightx = 1.0;
        p.add(tf, gbc);
        return tf;
    }

    // Hàm tạo thanh nút bấm chức năng (Thêm, Sửa, Xóa...) và ô Tìm kiếm nhanh
    // Visual is IDENTICAL to before — now returns ButtonBar for wiring
    public static ButtonBar addButtonsAndSearch(JPanel p, GridBagConstraints gbc,
                                                JTextField txtTim) {
        JButton btnThem     = new JButton("Thêm");
        JButton btnCapNhat  = new JButton("Cập Nhật");
        JButton btnXoa      = new JButton("Xóa");
        JButton btnLamMoi   = new JButton("Làm Mới");
        JButton btnTim      = new JButton("Tìm");

        GridBagConstraints gbcBtn = (GridBagConstraints) gbc.clone();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 5;
        gbcBtn.gridwidth = 4;
        gbcBtn.insets = new Insets(15, 5, 5, 5);
        JPanel pBtn = new JPanel(new GridLayout(1, 4, 15, 0));
        pBtn.add(btnThem);
        pBtn.add(btnCapNhat);
        pBtn.add(btnXoa);
        pBtn.add(btnLamMoi);
        p.add(pBtn, gbcBtn);

        GridBagConstraints gbcSearch = (GridBagConstraints) gbc.clone();
        gbcSearch.gridx = 0;
        gbcSearch.gridy = 6;
        gbcSearch.gridwidth = 1;
        gbcSearch.weightx = 0;
        p.add(new JLabel("Tìm kiếm:"), gbcSearch);
        gbcSearch.gridx = 1;
        gbcSearch.gridwidth = 3;
        gbcSearch.weightx = 1.0;
        JPanel pSearch = new JPanel(new BorderLayout(5, 0));
        pSearch.add(txtTim, BorderLayout.CENTER);
        pSearch.add(btnTim, BorderLayout.EAST);
        p.add(pSearch, gbcSearch);

        return new ButtonBar(btnThem, btnCapNhat, btnXoa, btnLamMoi, btnTim, txtTim);
    }
}
