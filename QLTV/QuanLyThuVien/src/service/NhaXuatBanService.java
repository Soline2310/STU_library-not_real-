package service;

import entity.NhaXuatBan;
import javax.persistence.*;
import java.util.List;

public class NhaXuatBanService {

    public List<NhaXuatBan> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT n FROM NhaXuatBan n ORDER BY n.maNXB", NhaXuatBan.class)
                     .getResultList();
        } finally { em.close(); }
    }

    public void add(NhaXuatBan n) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(n);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(NhaXuatBan n) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(n);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(String maNXB) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            NhaXuatBan n = em.find(NhaXuatBan.class, maNXB);
            if (n != null) em.remove(n);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public List<NhaXuatBan> search(String keyword) {
        EntityManager em = JPAUtil.getEM();
        try {
            String kw = "%" + keyword.trim() + "%";
            return em.createQuery(
                    "SELECT n FROM NhaXuatBan n WHERE n.tenNXB LIKE :kw OR n.diaChi LIKE :kw OR n.sDT LIKE :kw",
                    NhaXuatBan.class).setParameter("kw", kw).getResultList();
        } finally { em.close(); }
    }

    /** Returns true if this publisher has any books linked in the system */
    public boolean coSachLienKet(String maNXB) {
        EntityManager em = JPAUtil.getEM();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(s) FROM Sach s WHERE s.nhaXuatBan.maNXB = :ma", Long.class)
                    .setParameter("ma", maNXB)
                    .getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }
}
