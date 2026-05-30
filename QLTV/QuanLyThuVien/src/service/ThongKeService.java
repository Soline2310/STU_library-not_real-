package service;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class ThongKeService {

    /**
     * Returns rows: [MaSach, TenSach, TongLuotMuon]
     * Mirrors sp_ThongKeSachMuon
     */
    public List<Object[]> thongKeSachMuon(LocalDate tuNgay, LocalDate denNgay) {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT s.maSach, s.tenSach, COUNT(pm.maPhieu) " +
                    "FROM PhieuMuon pm JOIN pm.sach s " +
                    "WHERE pm.ngayMuon >= :tu AND pm.ngayMuon <= :den " +
                    "GROUP BY s.maSach, s.tenSach ORDER BY COUNT(pm.maPhieu) DESC",
                    Object[].class)
                    .setParameter("tu", tuNgay)
                    .setParameter("den", denNgay)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Returns rows: [MaDocGia, TenDocGia, SoSachDaMuon]
     * Mirrors sp_ThongKeDocGia
     */
    public List<Object[]> thongKeDocGia(LocalDate tuNgay, LocalDate denNgay) {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT dg.maDocGia, dg.tenDocGia, COUNT(pm.maPhieu) " +
                    "FROM PhieuMuon pm JOIN pm.docGia dg " +
                    "WHERE pm.ngayMuon >= :tu AND pm.ngayMuon <= :den " +
                    "GROUP BY dg.maDocGia, dg.tenDocGia ORDER BY COUNT(pm.maPhieu) DESC",
                    Object[].class)
                    .setParameter("tu", tuNgay)
                    .setParameter("den", denNgay)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /** Quick totals for dashboard cards in TrangChuPanel */
    public long tongSoSach() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Sach s", Long.class).getSingleResult();
        } finally { em.close(); }
    }

    public long tongDocGia() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT COUNT(d) FROM DocGia d", Long.class).getSingleResult();
        } finally { em.close(); }
    }

    public long dangMuon() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT COUNT(p) FROM PhieuMuon p WHERE p.trangThai = 'Đang mượn'", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long treHan() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT COUNT(p) FROM PhieuMuon p WHERE p.trangThai = 'Quá hạn'", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }
}
