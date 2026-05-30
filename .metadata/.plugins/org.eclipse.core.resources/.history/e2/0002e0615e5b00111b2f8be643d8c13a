package service;

import java.util.List;

import javax.persistence.EntityManager;

import dao.JPAUtil;
import entity.TacGia;

public class TacGiaService {

    public List<TacGia> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT x FROM TacGia x", TacGia.class).getResultList();
        } finally { em.close(); }
    }

    public TacGia findById(String id) {
        EntityManager em = JPAUtil.getEM();
        try { return em.find(TacGia.class, id); }
        finally { em.close(); }
    }

    public void add(TacGia obj) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(TacGia obj) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void delete(String id) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            TacGia obj = em.find(TacGia.class, id);
            if (obj != null) em.remove(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
