package service;

import javax.persistence.*;

/**
 * Singleton helper that creates and shares the EntityManagerFactory.
 * Call JPAUtil.getEM() to get a fresh EntityManager for each operation.
 */
public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("QLTVSTU");

    public static EntityManager getEM() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
