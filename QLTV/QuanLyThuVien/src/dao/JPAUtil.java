package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton helper — call JPAUtil.getEM() anywhere to get an EntityManager.
 * Call JPAUtil.close() when the app shuts down.
 */
public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("QLTVSTU");

    private JPAUtil() {}

    public static EntityManager getEM() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
