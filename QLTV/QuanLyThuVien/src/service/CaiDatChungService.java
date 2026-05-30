package service;

import entity.CaiDatChung;
import javax.persistence.*;

public class CaiDatChungService {

    private static final int SETTINGS_ID = 1;

    /** Load the single settings row, or return a default if not yet saved */
    public CaiDatChung load() {
        EntityManager em = JPAUtil.getEM();
        try {
            CaiDatChung c = em.find(CaiDatChung.class, SETTINGS_ID);
            return c != null ? c : new CaiDatChung(SETTINGS_ID, "Thư Viện STU", "", "", "");
        } finally { em.close(); }
    }

    /** Insert or update the single settings row */
    public void save(CaiDatChung c) {
        EntityManager em = JPAUtil.getEM();
        try {
            em.getTransaction().begin();
            c.setId(SETTINGS_ID);
            em.merge(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}
