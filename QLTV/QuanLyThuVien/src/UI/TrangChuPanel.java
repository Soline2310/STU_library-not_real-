package UI;

import service.ThongKeService;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class TrangChuPanel extends JPanel {

    public TrangChuPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblWelcome = new JLabel("Chào mừng trở lại, Thủ thư hệ thống!", SwingConstants.LEFT);
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 28));
        add(lblWelcome, BorderLayout.NORTH);

        // Load counts from DB
        ThongKeService service = new ThongKeService();
        String tongSach  = String.valueOf(service.tongSoSach());
        String tongDocGia = String.valueOf(service.tongDocGia());
        String dangMuon  = String.valueOf(service.dangMuon());
        String treHan    = String.valueOf(service.treHan());

        JPanel pCards = new JPanel(new GridLayout(2, 2, 40, 40));
        pCards.add(createCard("TỔNG SÁCH",   tongSach,  new Color(41, 128, 185)));
        pCards.add(createCard("ĐỘC GIẢ",     tongDocGia, new Color(39, 174, 96)));
        pCards.add(createCard("ĐANG MƯỢN",   dangMuon,  new Color(243, 156, 18)));
        pCards.add(createCard("TRỄ HẠN",     treHan,    new Color(192, 57, 43)));
        add(pCards, BorderLayout.CENTER);

        JLabel lblFooter = new JLabel("Phần mềm phát triển bởi Nhóm 5 thành viên", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblFooter.setForeground(Color.GRAY);
        add(lblFooter, BorderLayout.SOUTH);
    }

    private JPanel createCard(String title, String val, Color c) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(c);
        JLabel v = new JLabel(val, SwingConstants.CENTER);
        v.setFont(new Font("Tahoma", Font.BOLD, 60));
        v.setForeground(Color.WHITE);
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Tahoma", Font.BOLD, 18));
        t.setForeground(Color.WHITE);
        p.add(v);
        p.add(t);
        return p;
    }
}
