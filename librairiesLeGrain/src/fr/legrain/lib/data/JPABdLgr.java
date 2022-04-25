package fr.legrain.lib.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.lib.gui.LgrMessageBox;

public class JPABdLgr {
	
	/*
	 * 1 seul EntityManager :
	 * ENTITY_MANAGER_UNIQUE = true;
	 * ThreadLocal
	 * 1ere partie de la classe active (avant les ///////////////////////)
	 * 
	 * Plusieurs EntityManager
	 * ENTITY_MANAGER_UNIQUE = false;
	 * private EntityManager em = null;
	 * 2ème partie de la classe active
	 */

	//private static final Log logger = LogFactory.getLog(JPABdLgr.class);
	static Logger logger = Logger.getLogger(JPABdLgr.class);
	
	private static boolean ENTITY_MANAGER_UNIQUE = false;
	//private static boolean ENTITY_MANAGER_UNIQUE = true;
	
	protected static EntityManagerFactory emf;
	
	@PersistenceContext
	private EntityManager em = null;//getEntityManager();
	//private static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
	

	static protected Connection cnx;

	static protected ResourceBundle fListeTitre;

	static protected String fichierListeTitre;
	
	public static boolean isENTITY_MANAGER_UNIQUE() {
		return ENTITY_MANAGER_UNIQUE;
	}

	public static void setENTITY_MANAGER_UNIQUE(boolean eNTITYMANAGERUNIQUE) {
		ENTITY_MANAGER_UNIQUE = eNTITYMANAGERUNIQUE;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 1 seul EntityManager
	
//	/**
//     * Corresponds to a Hibernate Session.
//     * @return
//     */
//    public static EntityManager getEntityManager() {
//        EntityManager em = entitymanager.get();
//
//        // Create a new EntityManager
//        if (em == null) {
//            em = emf.createEntityManager();
//            //em.setFlushMode(FlushModeType.COMMIT);
//            entitymanager.set(em);
//        }
//		//logger.error("hhhh-------------------------------------------");
//        return em;
//    }
//
//    /**
//     * Close our "session".
//     */
//    public static void closeEntityManager() {
//    	EntityManager em = entitymanager.get();
//    	if (em != null) em.close();
//    	entitymanager.set(null);
//    }
//
//	public DatabaseMetaData getDbMetaData() {
//		try {
//			return cnx.getMetaData();
//		} catch (SQLException e) {
//			getLogger().error("",e);
//		}
//		return null;
//	}
//
//	public void setCnx(Connection cnx) {
//		this.cnx = cnx;
//	}
//
//	public static void fermetureConnection(){
//		closeEntityManager();
//		ConnectionJDBC.closeConnection();
//		if (emf != null) {
//			emf.close();
//			emf=null;
//		}
//	}
//	
//    
//	/**
//	 * Retourne le titre d'un champs tel que celui-ci doit être affiché dans le programme
//	 * @param value String
//	 * @return String
//	 */
//	static public String getTitreChamp(String value){
//		String result = value;
//		if(fListeTitre==null) {
//			if (new File(getFichierListeTitre()).exists()) {
//				try {
//					FileInputStream file = new FileInputStream(getFichierListeTitre());
//					fListeTitre = new PropertyResourceBundle(file);
//					file.close();
//				} catch (FileNotFoundException e) {
//					getLogger().error("", e);
//				} catch (IOException e) {
//					getLogger().error("", e);
//				}
//			}
//			else {
//				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","le fichier "+value+" n'existe pas");
//			}
//		}
//		if(fListeTitre!=null) {
//			try {
//				result = fListeTitre.getString(value);
//			} catch (Exception e) {
//				logger.error("",e);
//			}
//		}
//		return result;
//	}
//
//	static public String getFichierListeTitre() {
//		return fichierListeTitre;
//	}
//	static public void setFichierListeTitre(String fichierListeTitre) {
//		//BdLgr.fichierListeTitre = fichierListeTitre;
//		JPABdLgr.fichierListeTitre = fichierListeTitre;
//	}
//
//	public static Logger getLogger() {
//		return logger;
//	}
//
//	public void setEm(EntityManager em) {
//		//this.em = em;
//	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Plusieur EntityManager

	/**
     * Corresponds to a Hibernate Session.
     * @return
     */
    public /*static*/ EntityManager getEntityManager() {
       // EntityManager em = entitymanager.get();

        // Create a new EntityManager
        if (em == null) {
            em = emf.createEntityManager();
            //em.setFlushMode(FlushModeType.COMMIT);
           // entitymanager.set(em);
        }
		//logger.error("hhhh-------------------------------------------");
        return em;
    }

    /**
     * Close our "session".
     */
    public /*static*/ void closeEntityManager() {
    	//EntityManager em = entitymanager.get();
    	if (em != null) em.close();
    	//entitymanager.set(null);
    }

	public DatabaseMetaData getDbMetaData() {
		try {
			return cnx.getMetaData();
		} catch (SQLException e) {
			getLogger().error("",e);
		}
		return null;
	}

	public void setCnx(Connection cnx) {
		this.cnx = cnx;
	}

	public /*static*/ void fermetureConnection(){
		closeEntityManager();
		ConnectionJDBC.closeConnection();
		GestionModif.deConnection();
		if (emf != null) {
			emf.close();
			emf=null;
		}
	}
	
    
	/**
	 * Retourne le titre d'un champs tel que celui-ci doit être affiché dans le programme
	 * @param value String
	 * @return String
	 */
	static public String getTitreChamp(String value){
		String result = value;
		if(fListeTitre==null) {
			if (new File(getFichierListeTitre()).exists()) {
				try {
					FileInputStream file = new FileInputStream(getFichierListeTitre());
					fListeTitre = new PropertyResourceBundle(file);
					file.close();
				} catch (FileNotFoundException e) {
					getLogger().error("", e);
				} catch (IOException e) {
					getLogger().error("", e);
				}
			}
			else {
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","le fichier "+value+" n'existe pas");
			}
		}
		if(fListeTitre!=null) {
			try {
				result = fListeTitre.getString(value);
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return result;
	}

	static public String getFichierListeTitre() {
		return fichierListeTitre;
	}
	static public void setFichierListeTitre(String fichierListeTitre) {
		//BdLgr.fichierListeTitre = fichierListeTitre;
		JPABdLgr.fichierListeTitre = fichierListeTitre;
	}

	public static Logger getLogger() {
		return logger;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public static Connection getCnx() {
		return cnx;
	}

	


}
