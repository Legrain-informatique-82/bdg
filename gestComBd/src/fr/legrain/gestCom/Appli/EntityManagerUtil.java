package fr.legrain.gestCom.Appli;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * HibernateUtil but for
 */
public class EntityManagerUtil
{
    private static EntityManagerFactory emf;
    public static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
    private static final String PERSISTENCE_UNIT_NAME = "sample";

    public static EntityManagerFactory getEntityManagerFactory()
    {
        if (emf == null)
        {
            // Create the EntityManagerFactory
        	Map<String,String> configOverrides = new HashMap<String,String>();
        	configOverrides.put("hibernate.connection.driver_class", Const.C_DRIVER_JDBC);
        	configOverrides.put("hibernate.connection.password", Const.C_PASS);
        	configOverrides.put("hibernate.connection.url", Const.C_URL_BDD+Const.C_FICHIER_BDD);
        	configOverrides.put("hibernate.connection.username", Const.C_USER);
        	configOverrides.put("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
        	/* Enable Hibernate's automatic session context management */
//        	configOverrides.put("current_session_context_class", "thread");
//        	configOverrides.put("hibernate.archive.autodetection", "class, hbm");
        	configOverrides.put("hibernate.validator.autoregister_listeners", "false");
        	
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,configOverrides);
        }

        return emf;
    }


    /**
     * Corresponds to a Hibernate Session.
     *
     * @return
     */
    public static EntityManager getEntityManager()
    {
        EntityManager em = entitymanager.get();

        // Create a new EntityManager
        if (em == null)
        {
            em = emf.createEntityManager();
            entitymanager.set(em);
        }
        return em;
    }


    /**
     * Close our "session".
     */
    public static void closeEntityManager()
    {
        EntityManager em = entitymanager.get();
        entitymanager.set(null);
        if (em != null) em.close();
    }

}