package service;

import java.util.List;

import javax.persistence.EntityManager;

import dao.JPAUtil;
import entity.DocGia;

public class DocGiaService {

    public List<DocGia> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT x FROM DocGia x", DocGia.class).getResultList();
        } finally { em.close(); }
    }

    public DocGia findById(String id) {
        EntityManager em = JPAUtil.getEM();
        try { return em.find(DocGia.class, id); }
        finally { em.close(); }
    }

    public void add(DocGia obj) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(DocGia obj) {
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
            DocGia obj = em.find(DocGia.class, id);
            if (obj != null) em.remove(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
