package fr.legrain.gestCom.Appli;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ConnectionJDBC;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.GenCode;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.QueDesChiffres;
import fr.legrain.lib.data.QueDesChiffresPositif;

public abstract class AbstractApplicationDAO<T> extends AbstractLgrDAO<T> {

	//private static final Log logger = LogFactory.getLog(AbstractApplicationDAO.class);
	static Logger logger = Logger.getLogger(AbstractApplicationDAO.class);
	
	private static final String PERSISTENCE_UNIT_NAME = "sample";
	
//	@PersistenceContext
//	//private EntityManager entityManager = getEntityManager();
//	private /*static final*/ ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
	
	protected JPACtrl_Application ctrlGeneraux = null;

	public AbstractApplicationDAO() {
		super();
		initConnexion();
		ctrlGeneraux = new JPACtrl_Application();

	}


	
	@Override
	public void initConnexion() {	
		Connection cnx = ConnectionJDBC.getConnection(Const.C_URL_BDD+Const.C_FICHIER_BDD, Const.C_USER, Const.C_PASS, Const.C_DRIVER_JDBC);
		setCnx(cnx);
//		if (gestionModif.getListeGestionModif()==null)
			gestionModif.setListeGestionModif(Const.C_FICHIER_MODIF);
		gestionModif.setCnx(cnx);
		emf = getEntityManagerFactory();
		//EntityManagerUtil.getEntityManager();
	}

	public static void deconnectionGestionModif(){
		gestionModif.deConnection();
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		try {
			
		if (emf == null) {
			logger.info("Creation de EntityManagerFactory ...");
			// Create the EntityManagerFactory
			Map<String,String> configOverrides = new HashMap<String,String>();
			configOverrides.put("hibernate.connection.driver_class", Const.C_DRIVER_JDBC);
			configOverrides.put("hibernate.connection.password", Const.C_PASS);
			configOverrides.put("hibernate.connection.url", Const.C_URL_BDD+Const.C_FICHIER_BDD);
			configOverrides.put("hibernate.connection.username", Const.C_USER);
			// Fait dans persistence.xml
			//configOverrides.put("hibernate.dialect", "fr.legrain.gestCom.global.LgrHibernateFirebirdDialect");
			// pourquoi ?
			configOverrides.put("hibernate.validator.autoregister_listeners", "false");


			/* Enable Hibernate's automatic session context management */
//			configOverrides.put("current_session_context_class", "thread");
//			configOverrides.put("hibernate.archive.autodetection", "class, hbm");
			
			// Fait dans persistence.xml
			//configOverrides.put("hibernate.cache.use_second_level_cache", "true");
			//configOverrides.put("hibernate.cache.provider_class","org.hibernate.cache.EhCacheProvider");
			
			//configOverrides.put("hibernate.cache.use_query_cache", "true");

			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,configOverrides);
			logger.info("EntityManagerFactory créé.");
		}
		} catch (Exception e) {
			logger.error("EntityManagerFactory pas générée correctement",e);
			return null;
		}
		return emf;
	}
	
	public static void CloseEntityManagerFactory() {
			ConnectionJDBC.closeConnection();
			deconnectionGestionModif();
			if (emf != null) {
				emf.close();
				emf=null;
			}
	}

//	/**
//     * Corresponds to a Hibernate Session.
//     * @return
//     */
//    public /*static*/ EntityManager getEntityManager() {
//        EntityManager em = entitymanager.get();
//
//        // Create a new EntityManager
//        if (em == null) {
//            em = emf.createEntityManager();
//            entitymanager.set(em);
//        }
//        return em;
//    }
//
//    /**
//     * Close our "session".
//     */
//    public /*static*/ void closeEntityManager() {
//        EntityManager em = entitymanager.get();
//        entitymanager.set(null);
//        if (em != null) em.close();
//    }
	
	public IStatus validate(T entity, String field, String validationContext){
		return validate(entity, field, validationContext, false);
	}
	
	/**
	 * 
	 * @param entity - Entite contenant la valeur
	 * @param field - Nom du champ de l'entité à valider
	 * @param validationContext - contexte de validation
	 * @param verrouilleCode - Pour les champs/codes dont on veut empecher la modif s'ils sont deja en cours de modif 
	 * ou pour empecher de générer 2 fois un même code
	 * @return
	 */
	public IStatus validate(T entity, String field, String validationContext, boolean verrouilleCode){
		//public IStatus validate(Object entity, String field, String contexte){
			IStatus s = null;
			try {
//				ClassValidator<TaUnite> uniteValidator = new ClassValidator<TaUnite>( TaUnite.class );
//				InvalidValue[] iv = uniteValidator.getInvalidValues(entity,field);
				
				System.out.println("xxxDAO.validate() : "+field);

				MessCtrlLgr mess = new MessCtrlLgr();
				LgrHibernateValidated a = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(entity, field)).getAnnotation(LgrHibernateValidated.class);
				mess.setContexte(validationContext);
				if(a!=null){//isa le 19-03-2009 car existant dans taPrixDao par exemple
					mess.setNomChampDB(a.champ());
					mess.setNomChamp(field);
					mess.setNomTable(entity.getClass().getAnnotation(Table.class).name());
					mess.setEntityClass(a.clazz());
					mess.setModeObjet(getModeObjet());
					if(PropertyUtils.getProperty(entity, field)!=null) {
						if(PropertyUtils.getProperty(entity, field).getClass() == java.util.Date.class)
							mess.setValeur(LibDate.dateToString((java.util.Date)PropertyUtils.getProperty(entity, field)));
						else
							mess.setValeur(PropertyUtils.getProperty(entity, field).toString());

						//annulerCodeGenere(mess.getValeur(),mess.getNomChampDB());
						if(verrouilleCode) {
							rentreCodeGenere(mess.getValeur(),mess.getNomChampDB());
						}
					} else {
						mess.setValeur(null);
					}
					mess.setNomChampId(initChampId(entity));
					initValeurIdTable(entity);
					mess.setID_Valeur(valeurIdTable.toString());


					ctrlGeneraux.ctrlSaisie(mess);
				} else {
					logger.error("Annotation de controle (LgrHibernateValidated) non presente.");
				}
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				if(s.getSeverity()!=IStatus.ERROR ){
					ctrlSaisieSpecifique(entity, field);
				}
				return s;
			} catch (ExceptLgr e) {
				logger.error("",e);
				s = new Status(Status.ERROR,gestComBdPlugin.PLUGIN_ID,e.getMessage(),e);
				return s;
				//valid = false;
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			} catch (Exception e) {
				logger.error("",e);
			}
			return null;

		}
	
	/**
	 * Verification entre les champs, permet par exemple d'initialiser ou de modifier une valeurs dès qu'une autre valeur est saisie
	 * @param entity - l'objet modifier
	 * @param field - le champ modifier dans cet objet
	 * @throws ExceptLgr
	 */
	public void ctrlSaisieSpecifique(T entity,String field) throws ExceptLgr {}
    
	public static String genereCode(String nomTable,Boolean verif_Connection) throws Exception{
		try{
			boolean nonAutorise = false;
			String res=null;
			int compteur=-1;
			GenCode code = new GenCode();
			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			while (!nonAutorise) {
				compteur ++;
				res=code.GenereCode(nomTable,compteur,taInfoEntreprise.getCodexoInfoEntreprise());
				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable,verif_Connection);	
			};
			code.reinitialiseListeGestCode();
			return res;
		}catch(Exception e){
			logger.error("",e);
			return "";
		}
	}

	public static String genereCode(String nomTable) throws Exception{
		try{
			boolean nonAutorise = false;
			String res=null;
			int compteur=-1;
			GenCode code = new GenCode();
			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			while (!nonAutorise) {
				compteur ++;
				res=code.GenereCode(nomTable,compteur,taInfoEntreprise.getCodexoInfoEntreprise());
				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable);	
			};
			code.reinitialiseListeGestCode();
			return res;
		}catch(Exception e){
			logger.error("",e);
			return "";
		}
	}
	
	public static GenCode recupCodeDocument(String nomTable) throws Exception{
		try{
			boolean nonAutorise = false;
			String res=null;
			int compteur=-1;
			GenCode code = new GenCode();
			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			while (!nonAutorise) {
				compteur ++;
				res=code.GenereCode(nomTable,compteur,taInfoEntreprise.getCodexoInfoEntreprise());
				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable);	
			};
			code.reinitialiseListeGestCode();
			return code;
		}catch(Exception e){
			return null;
		}
	}	
	
	public String genereCode() throws Exception{
		return genereCode(new LinkedList<String>());		
	}
	
	public String genereCode(List<String> listeCodes) throws Exception{
		try{
			boolean nonAutorise = false;
			String res=null;
			int compteur=-1;
			GenCode code = new GenCode();
			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			while (!nonAutorise) {
				compteur ++;
				res=code.GenereCode(nomTable,compteur,taInfoEntreprise.getCodexoInfoEntreprise());
				nonAutorise=autoriseUtilisationCodeGenere(res);
				if(listeCodes.size()>0)
					nonAutorise=!listeCodes.contains(res);
			};
			code.reinitialiseListeGestCode();
			return res;
		}catch(Exception e){
			logger.error("",e);
			return "";
		}
	}

	public String genereCodeJPA() throws Exception{
		return genereCodeJPA(new LinkedList<String>());		
	}
	
	public String genereCodeJPA(List<String> listeCodes) throws Exception{
		try{
			boolean nonAutorise = false;
			String res=null;
			int compteur=-1;
			GenCode code = new GenCode();
			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			while (!nonAutorise) {
				compteur ++;
				res=code.GenereCodeJPA(nomTableJPA,compteur,taInfoEntreprise.getCodexoInfoEntreprise());
				nonAutorise=autoriseUtilisationCodeGenere(res,nomTableMere,true);
				if(listeCodes.size()>0)
					nonAutorise=!listeCodes.contains(res);
			};
			code.reinitialiseListeGestCode();
			return res;
		}catch(Exception e){
			logger.error("",e);
			return "";
		}
	}
	
	public Boolean retourneEtatLegrain(Map<IDocumentTiers, Boolean> listeEtat,IDocumentTiers doc){
		for (IDocumentTiers docLoc : listeEtat.keySet()) {
			if(doc.getCodeDocument().equals(docLoc.getCodeDocument()))
				return docLoc.isLegrain();
		}
		return false;
	}
//	public VerifyListener initialiseVerifyListener(T entity, String field, int[] listeControle){
//		LgrHibernateValidated a;
//		try {
//			a = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(entity, field)).getAnnotation(LgrHibernateValidated.class);
//			if(a!=null){
//				for (int i = 0; i < listeControle.length; i++) {
//					if(listeControle[i]==InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS) {
//						return new QueDesChiffresPositif(a.champ(),a.table(), getDbMetaData());
//					} else if(listeControle[i]==InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES) {
//						return new QueDesChiffres(a.champ(),a.table(), getDbMetaData());
//					} else if(listeControle[i]==InfosVerifSaisie.CTRL_LONGUEUR_TEXTE) {
//						//return new QueDesChiffres(a.champ(),a.table(), getDbMetaData());
//					}
//				}
//			}
//		} catch (IllegalAccessException e) {
//			logger.error("",e);
//			return null;
//		} catch (InvocationTargetException e) {
//			logger.error("",e);
//			return null;
//		} catch (NoSuchMethodException e) {
//			logger.error("",e);
//			return null;
//		}
//		return null;
//	}
//	
//	public QueDesChiffres initialiseQueDesChiffres(T entity, String field ){
//		LgrHibernateValidated a;
//		try {
//			a = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(entity, field)).getAnnotation(LgrHibernateValidated.class);
//			if(a!=null){
//				return new QueDesChiffres(a.champ(),a.table(), getDbMetaData());
//			}
//		} catch (IllegalAccessException e) {
//			logger.error("",e);
//			return null;
//		} catch (InvocationTargetException e) {
//			logger.error("",e);
//			return null;
//		} catch (NoSuchMethodException e) {
//			logger.error("",e);
//			return null;
//		}
//		return null;
//	}
	
//	public String genereCodeForInsert() throws Exception{
//		String code = genereCode();
//		rentreCodeGenere(code, ChampCourant);
//		return code;
//	}

}