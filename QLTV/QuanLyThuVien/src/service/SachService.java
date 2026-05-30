package service;

import entity.Sach;
import javax.persistence.*;
import java.util.List;

public class SachService {

    public List<Sach> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT s FROM Sach s LEFT JOIN FETCH s.tacGia LEFT JOIN FETCH s.nhaXuatBan ORDER BY s.maSach",
                    Sach.class).getResultList();
        } finally { em.close(); }
    }

    public void add(Sach s) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(Sach s) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(s);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(String maSach) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            Sach s = em.find(Sach.class, maSach);
            if (s != null) em.remove(s);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public List<Sach> search(String keyword) {
        EntityManager em = JPAUtil.getEM();
        try {
            String kw = "%" + keyword.trim() + "%";
            return em.createQuery(
                    "SELECT s FROM Sach s LEFT JOIN FETCH s.tacGia LEFT JOIN FETCH s.nhaXuatBan " +
                    "WHERE s.tenSach LIKE :kw OR s.theLoai LIKE :kw " +
                    "OR s.tacGia.tenTacGia LIKE :kw OR s.nhaXuatBan.tenNXB LIKE :kw",
                    Sach.class).setParameter("kw", kw).getResultList();
        } finally { em.close(); }
    }

    /** Returns true if the book has any borrow slip with status Đang mượn or Quá hạn */
    public boolean coPhieuMuonDangMuon(String maSach) {
        EntityManager em = JPAUtil.getEM();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(p) FROM PhieuMuon p WHERE p.sach.maSach = :ma " +
                    "AND p.trangThai IN ('Đang mượn', 'Quá hạn')", Long.class)
                    .setParameter("ma", maSach)
                    .getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }
}
