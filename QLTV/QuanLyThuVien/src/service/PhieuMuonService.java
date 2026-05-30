package service;

import entity.PhieuMuon;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class PhieuMuonService {

    public List<PhieuMuon> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery(
                    "SELECT p FROM PhieuMuon p LEFT JOIN FETCH p.docGia LEFT JOIN FETCH p.sach ORDER BY p.maPhieu",
                    PhieuMuon.class).getResultList();
        } finally { em.close(); }
    }

    public PhieuMuon findById(String maPhieu) {
        EntityManager em = JPAUtil.getEM();
        try { return em.find(PhieuMuon.class, maPhieu); }
        finally { em.close(); }
    }

    /**
     * Add a new borrow slip.
     * Business rule: reader cannot hold >= 3 active books.
     */
    public void add(PhieuMuon p) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            Long active = em.createQuery(
                    "SELECT COUNT(pm) FROM PhieuMuon pm WHERE pm.docGia.maDocGia = :id " +
                    "AND pm.trangThai IN ('Đang mượn', 'Quá hạn')", Long.class)
                    .setParameter("id", p.getDocGia().getMaDocGia())
                    .getSingleResult();
            if (active >= 3) {
                em.getTransaction().rollback();
                throw new IllegalStateException("Từ chối: Độc giả đang giữ 3 cuốn chưa trả!");
            }
            em.persist(p);
            em.getTransaction().commit();
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            em.getTransaction().rollback(); throw e;
        } finally { em.close(); }
    }

    public void update(PhieuMuon p) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(String maPhieu) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            PhieuMuon p = em.find(PhieuMuon.class, maPhieu);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public List<PhieuMuon> search(String keyword) {
        EntityManager em = JPAUtil.getEM();
        try {
            String kw = "%" + keyword.trim() + "%";
            return em.createQuery(
                    "SELECT p FROM PhieuMuon p LEFT JOIN FETCH p.docGia LEFT JOIN FETCH p.sach " +
                    "WHERE p.maPhieu LIKE :kw OR p.docGia.maDocGia LIKE :kw " +
                    "OR p.sach.maSach LIKE :kw OR p.trangThai LIKE :kw",
                    PhieuMuon.class).setParameter("kw", kw).getResultList();
        } finally { em.close(); }
    }

    /** Auto-mark overdue slips */
    public void capNhatQuaHan() {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.createQuery(
                    "UPDATE PhieuMuon p SET p.trangThai = 'Quá hạn' " +
                    "WHERE p.trangThai = 'Đang mượn' AND p.hanTra < :today")
                    .setParameter("today", LocalDate.now())
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
