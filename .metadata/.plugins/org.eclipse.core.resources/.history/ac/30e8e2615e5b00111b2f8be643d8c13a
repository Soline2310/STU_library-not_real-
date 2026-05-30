package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import dao.JPAUtil;
import entity.ThuThu;

public class ThuThuService {

    /**
     * Login check — returns the ThuThu if credentials match, null otherwise.
     * Use this in DangNhap.kiemTraDangNhap() to populate Auth static fields.
     */
    public ThuThu dangNhap(String taiKhoan, String matKhau) {
        EntityManager em = JPAUtil.getEM();
        try {
            TypedQuery<ThuThu> q = em.createQuery(
                "SELECT t FROM ThuThu t WHERE t.taiKhoan = :tk AND t.matKhau = :mk",
                ThuThu.class);
            q.setParameter("tk", taiKhoan);
            q.setParameter("mk", matKhau);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;   // wrong credentials
        } finally { em.close(); }
    }

    public List<ThuThu> getAll() {
        EntityManager em = JPAUtil.getEM();
        try {
            return em.createQuery("SELECT t FROM ThuThu t", ThuThu.class).getResultList();
        } finally { em.close(); }
    }

    public void add(ThuThu t) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public void update(ThuThu t) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public void delete(String maThuThu) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            ThuThu t = em.find(ThuThu.class, maThuThu);
            if (t != null) em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}
