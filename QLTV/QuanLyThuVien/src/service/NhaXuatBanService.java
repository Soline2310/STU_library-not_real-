package service;

import java.util.List;

import javax.persistence.EntityManager;

import dao.JPAUtil;
import entity.NhaXuatBan;

public class NhaXuatBanService {

    public List<NhaXuatBan> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT x FROM NhaXuatBan x", NhaXuatBan.class).getResultList();
        } finally { em.close(); }
    }

    public NhaXuatBan findById(String id) {
        EntityManager em = JPAUtil.getEM();
        try { return em.find(NhaXuatBan.class, id); }
        finally { em.close(); }
    }

    public void add(NhaXuatBan obj) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }

    public void update(NhaXuatBan obj) {
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
            NhaXuatBan obj = em.find(NhaXuatBan.class, id);
            if (obj != null) em.remove(obj);
            em.getTransaction().commit();
        } catch (Exception e) { em.getTransaction().rollback(); throw e; }
        finally { em.close(); }
    }
}
