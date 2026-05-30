package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dao.JPAUtil;
import entity.Sach;

public class SachService {

    /** Get all books */
    public List<Sach> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT s FROM Sach s", Sach.class).getResultList();
        } finally { em.close(); }
    }

    /** Find by primary key */
    public Sach findById(String maSach) {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.find(Sach.class, maSach);
        } finally { em.close(); }
    }

    /** Search by name (case-insensitive, partial match) */
    public List<Sach> search(String keyword) {
        EntityManager em = JPAUtil.getEM();
        try {
            TypedQuery<Sach> q = em.createQuery(
                "SELECT s FROM Sach s WHERE LOWER(s.tenSach) LIKE :kw", Sach.class);
            q.setParameter("kw", "%" + keyword.toLowerCase() + "%");
            return q.getResultList();
        } finally { em.close(); }
    }

    /** Add new book */
    public void add(Sach sach) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(sach);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    /** Update existing book */
    public void update(Sach sach) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(sach);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    /** Delete by primary key */
    public void delete(String maSach) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            Sach sach = em.find(Sach.class, maSach);
            if (sach != null) em.remove(sach);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}
