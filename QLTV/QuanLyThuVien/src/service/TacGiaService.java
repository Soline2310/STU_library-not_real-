package service;

import entity.TacGia;
import javax.persistence.*;
import java.util.List;

public class TacGiaService {

    public List<TacGia> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT t FROM TacGia t ORDER BY t.maTacGia", TacGia.class)
                     .getResultList();
        } finally { em.close(); }
    }

    public void add(TacGia t) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(TacGia t) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(String maTacGia) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            TacGia t = em.find(TacGia.class, maTacGia);
            if (t != null) em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public List<TacGia> search(String keyword) {
        EntityManager em = JPAUtil.getEM();
        try {
            String kw = "%" + keyword.trim() + "%";
            return em.createQuery(
                    "SELECT t FROM TacGia t WHERE t.tenTacGia LIKE :kw OR t.quocTich LIKE :kw",
                    TacGia.class).setParameter("kw", kw).getResultList();
        } finally { em.close(); }
    }

    /** Returns true if this author has any books linked in the system */
    public boolean coSachLienKet(String maTacGia) {
        EntityManager em = JPAUtil.getEM();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(s) FROM Sach s WHERE s.tacGia.maTacGia = :ma", Long.class)
                    .setParameter("ma", maTacGia)
                    .getSingleResult();
            return count > 0;
        } finally { em.close(); }
    }
}
