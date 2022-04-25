package fr.legrain.lib.data;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.hibernate.proxy.HibernateProxy;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public abstract class AbstractLgrDAO<T> extends JPABdLgr {
	
	//private static final Log logger = LogFactory.getLog(AbstractLgrDAO.class);
	static Logger logger = Logger.getLogger(AbstractLgrDAO.class.getName());
	
	/** Utilisation du versionning de JPA => OptimisticLock.<br>Tout est gere par JPA, il faut juste traite les exceptions*/
	public static final int LOCK_TYPE_JPA = 2;
	/** Utilisation d'une table speciale ou sont inscrite les lignes en cours de modification.<br>Il faut appele les methodes de verrouillage et de deverouillage */
	public static final int LOCK_TYPE_LGR = 1;
	
	private static final int TYPE_ENREGISTREMENT_MERGE = 1;
	private static final int TYPE_ENREGISTREMENT_PERSIST = 2;
	
	protected static GestionModif gestionModif = new GestionModif();
	/** nom de la table dans la bdd correspondant a l'entite geree par ce DAO*/
	protected String nomTable;
	protected String nomTableJPA;
	protected String nomTableMere;
	/** veleur de l'ID de l'entite geree par ce DAO*/
	protected Integer valeurIdTable;
	protected String champIdTable;
	protected String champIdEntite;
	protected String champGenere;
	protected String valeurGenere;
	
	private int lockType = LOCK_TYPE_LGR;
//	private int lockType = LOCK_TYPE_JPA;
	
	/** Contient le mode dans lequel se trouve l'objet */
	private ModeObjet modeObjet = new ModeObjet();
	static private Properties listeChampMaj=new Properties(); 
	protected String JPQLQuery = null;
	protected String JPQLQueryInitial = null;
	
	//Paramètre pour la construction de la clause "where" de requete simple
	//clé = nom du champs, valeur = [type de comparaison (=,>,<,like)][valeur du champs]
	protected Map<String,String[]> paramWhereSQL = null;
	
	protected HashMap listeChampsCalcules = null;
	
	public AbstractLgrDAO() {
		super();
	}
	
	protected abstract void persist(T transientInstance);
	
	protected abstract T merge(T detachedInstance);
	
	protected abstract void remove(T persistentInstance);
	protected abstract T refresh(T persistentInstance);
	
	/**
	 * Persist
	 * @param transientInstance
	 * @throws Exception
	 */
	public void enregistrerPersist(T transientInstance) throws Exception{
		enregistrer(transientInstance,TYPE_ENREGISTREMENT_PERSIST);
	}
	
	/**
	 * Merge
	 * @param transientInstance
	 * @throws Exception
	 */
	public T enregistrerMerge(T persistentInstance) throws Exception{
		return enregistrer(persistentInstance,TYPE_ENREGISTREMENT_MERGE);
	}
	
	public void consultation() {
		getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
	}
	
	/**
	 * 
	 * @param instance
	 * @param typeEnregistrement - MERGE ou PERSIST, cf constantes TYPE_ENREGISTREMENT_
	 * @throws Exception
	 */
	private T enregistrer(T instance,int typeEnregistrement) throws Exception{
		T retour = null;
		init(instance);
		
		
		if(lockType==LOCK_TYPE_LGR) {
			if(typeEnregistrement==TYPE_ENREGISTREMENT_MERGE)
				retour = merge(instance);
			else if(typeEnregistrement==TYPE_ENREGISTREMENT_PERSIST)
				persist(instance);
			
			annuleModification(false);
		} else {
			if(typeEnregistrement==TYPE_ENREGISTREMENT_MERGE)
				retour = merge(instance);
			else if(typeEnregistrement==TYPE_ENREGISTREMENT_PERSIST)
				persist(instance);
		}

//		if (champGenere!=null && valeurGenere!=null)
//			annulerCodeGenere(valeurGenere,champGenere);
//		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		
		return retour;
	}
	
	public void afterEnregistrer() throws Exception{
		if (champGenere!=null && valeurGenere!=null)
			annulerCodeGenere(valeurGenere,champGenere);
		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		
	}

	public Boolean autoriseModification(T persistentInstance){
		Boolean retour=true;
		try{
			if(lockType==LOCK_TYPE_LGR) {
				if(persistentInstance!=null){
					init(persistentInstance);
					retour=autoriseModification();
				}
			}
		}
		catch (Exception e) {
			retour = false;
		}
		return retour;
	}
	public void messageNonAutoriseModification() throws Exception{
		if(lockType==LOCK_TYPE_LGR) {
			if(!dataSetEnModif()) {
					String message="Cet enregistrement a été modifié par un autre utilisateur !!!" +
					"\r\nIl doit être rechargé pour y " +
					"apporter de nouvelles modifications.";
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),"Attention",
							message);
					throw new Exception();
			}
		}
	}	
	public void verifAutoriseModification(T persistentInstance) throws Exception{
		if(lockType==LOCK_TYPE_LGR) {
			init(persistentInstance);
			if(!dataSetEnModif()) {
				if (autoriseModification()){						
				}else{
					String message="Cet enregistrement est déjà en cours de modification !!!" +
					"\r\nVous devez attendre qu'il soit libéré pour y " +
					"apporter de nouvelles modifications.";
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),"Attention",
							message);
					throw new Exception();
				}
			}
		}
	}
	
	public void modifier(T persistentInstance) throws Exception{
		if(lockType==LOCK_TYPE_LGR) {
			init(persistentInstance);
			if (autoriseModification()){					
				rentreEnModification();	
			}else{
				String message="Cet enregistrement est déjà en cours de modification !!!" +
				"\r\nVous devez attendre qu'il soit libéré pour y " +
				"apporter de nouvelles modifications.";
//				logger.info(persistentInstance.toString()+" est deja en cours de modification");
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),"Attention",
						message);
				throw new Exception();				
			}
		}
		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
	}
	
	public void inserer(T transientInstance) throws Exception{
		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
	}

	public void annuler(T persistentInstance) throws Exception{
		if(lockType==LOCK_TYPE_LGR) {
			annuleModification(true);
		}
		
		if (champGenere!=null && valeurGenere!=null)
			annulerCodeGenere(valeurGenere,champGenere);
		if(modeObjet.getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION)==0)
			persistentInstance=refresh(persistentInstance);
		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
	}
	public T annulerT(T persistentInstance) throws Exception{
		if(lockType==LOCK_TYPE_LGR) {
			annuleModification(true);
		}
		
		if (champGenere!=null && valeurGenere!=null)
			annulerCodeGenere(valeurGenere,champGenere);
		if(modeObjet.getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION)==0)
			persistentInstance=refresh(persistentInstance);
		modeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		return persistentInstance;
	}
	
	public void supprimer(T persistentInstance) throws Exception{
		remove(persistentInstance);
	}
	
	public void commit(EntityTransaction transaction) throws Exception {
		if(transaction.isActive()){
			getEntityManager().flush();
			transaction.commit();
			}
		if((modeObjet.getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (modeObjet.getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			afterEnregistrer();		
		}
	}
	public void begin(EntityTransaction transaction) throws Exception {
		if(!transaction.isActive()){
			transaction.begin();
		}
	}

	public abstract T findById(int id);

	public abstract List<T> selectAll();
//	public abstract int selectCount();
	
	public abstract void initConnexion();
	
	public boolean dataSetEnModif() {
		if(modeObjet.getMode()==EnumModeObjet.C_MO_EDITION
				|| modeObjet.getMode()==EnumModeObjet.C_MO_IMPORTATION
				|| modeObjet.getMode()==EnumModeObjet.C_MO_INSERTION)
			return true;
		else
			return false;
	}

	public ModeObjet getModeObjet() {
		return modeObjet;
	}

	public void setModeObjet(ModeObjet modeObjet) {
		this.modeObjet = modeObjet;
	}
	
	public void addChangeModeListener(ChangeModeListener l) {
		modeObjet.addChangeModeListener(l);
	}
	
	public void removeChangeModeListener(ChangeModeListener l) {
		modeObjet.removeChangeModeListener(l);
	}

	public String getChampIdTable() {
		return champIdTable;
	}
	
//	public abstract void ctrlSaisieSpecifique( ValeurChamps valeurChamps) throws ExceptLgr;
	
	public Map<String,String[]> getParamWhereSQL() {
		return paramWhereSQL;
	}
	
	public void setParamWhereSQL(Map<String,String[]> paramWhereSQL) {
		if(paramWhereSQL!=null){
		this.paramWhereSQL = paramWhereSQL;
		changeCloseWhere();
		}
	}
	
	private void initJPQLQueryInitial() {
		if(JPQLQueryInitial==null) {
			JPQLQueryInitial=getJPQLQuery();
		}
	}
	
	protected void changeCloseWhere() {
		changeCloseWhere(true);
	}
	
	/**
	 * Creation de la nouvelle requete à partir de <code>paramWhereSQL</code> et ajout
	 * d'une close "where" à la fin de celle-ci. 
	 * La nouvelle requete remplace la précédente.
	 * @param init boolean - si vrai, travaille a partir de la requete initiale,
	 *  sinon ajoute les clauses a la fin de la requete actuelle.
	 */
	protected void changeCloseWhere(boolean init) {
		String nouvelleRequete = "";
		initJPQLQueryInitial();
		if(init) {
			setJPQLQuery(JPQLQueryInitial);
		}
		
		nouvelleRequete = getJPQLQuery();
		boolean debutRqt = false;
		if(!paramWhereSQL.isEmpty()) {
			if (!nouvelleRequete.toUpperCase().contains("WHERE")){
				nouvelleRequete += " where ";					
				debutRqt = true;
			}
			for(String champs : paramWhereSQL.keySet()) {
				if(!debutRqt)
					nouvelleRequete+=" and ";
				else
					debutRqt = false;
				nouvelleRequete += champs + " " + paramWhereSQL.get(champs)[0] 
				                                                            +" "+ paramWhereSQL.get(champs)[1];
			}
		}
		setJPQLQuery(nouvelleRequete);
	}



	public String getJPQLQuery() {
		return JPQLQuery;
	}



	public void setJPQLQuery(String query) {
		JPQLQuery = query;
	}
	
	/*
	 * =========================================================================================================================
	 * Gestion du verrouillage et des codes generes ===> IBQuLgr
	 * =========================================================================================================================
	 */
	
	/**
	 * Si l'objet passé en parametre est un proxy Hibernate/CGLIB, récupère la vrai classe. Sinon retourne null.<br>
	 * Ex : TaArticle$$EnhencedByCGLIB ==> TaArticle<br>
	 * <p>Cette methode est spécifique à Hibernate et ne peu pas être utilisé avec d'autres implémentations de JPA.</p>
	 * @param entity
	 * @return
	 * @see AbstractLgrDAO#deproxyEntity(Object)
	 */
	private Class deproxyClass(Object entity) {
		Class clazz = null;
		if (entity instanceof HibernateProxy) {
			clazz = ((HibernateProxy)entity).getHibernateLazyInitializer().getPersistentClass();
		}
		return clazz;
	}
	
	/**
	 * Si l'objet passé en parametre est un proxy Hibernate/CGLIB, retourne l'entité gérée par ce proxy. Sinon retourne null<br>
	 * <p>Cette methode est spécifique à Hibernate et ne peu pas être utilisé avec d'autres implémentations de JPA.</p>
	 * @param entity
	 * @return
	 * @see AbstractLgrDAO#deproxyClass(Object)
	 */
	private T deproxyEntity(Object entity) {
		T res = null;
		if (entity instanceof HibernateProxy) { 
			res = (T) ((HibernateProxy)entity).getHibernateLazyInitializer().getImplementation();
		}
		return res;
	}
	
	/**
	 * initialisation de <code>nomTable</code>, <code>champIdTable</code> et <code>valeurIdTable</code> a partir des annotations JPA
	 * @param entity
	 */
	protected void init(T entity){
		initNomTable(entity);
		initValeurIdTable(entity);
		gestionModif.setCnx(cnx);
	}
	
	/**
	 * initialisation de <code>nomTable</code> a partir des annotations JPA
	 * @param entity
	 */
	protected void initNomTable(T entity) {
		//nomTable
		Table ta = null;
		SecondaryTable taSecond=null;
		//if(nomTable==null) {
		Class c = deproxyClass(entity);
		if(c!=null) {
			ta = (Table) c.getAnnotation(Table.class);
		} else {
			ta = entity.getClass().getAnnotation(Table.class);
			taSecond=entity.getClass().getAnnotation(SecondaryTable.class);
		}
		if(ta==null){
			nomTable=taSecond.name();
		}
		else
		nomTable=ta.name();
		nomTableJPA=entity.getClass().getSimpleName();
		
		//}
	}
	
	protected void initNomTableMere(Object entity) {
		Table ta = null;
		SecondaryTable taSecond=null;
		Class c = deproxyClass(entity);
		if(c!=null) {
			ta = (Table) c.getAnnotation(Table.class);
		} else {
			ta = entity.getClass().getAnnotation(Table.class);
		}
		nomTableMere=ta.name();		
	}
	/**
	 * initialisation de <code>champIdTable</code> et <code>valeurIdTable</code> a partir des annotations JPA
	 * @param entity
	 * @return l'Id de l'entité passé en paramètre ou 0 si on ne peut pas lire l'Id
	 */
	//protected int initValeurIdTable(T entity) {
	public int initValeurIdTable(T entity) {
		//valeurIdTable		
		Class clazz = deproxyClass(entity);
		PropertyDescriptor[] p;
		if(clazz!=null) {
			p = PropertyUtils.getPropertyDescriptors(clazz);
		} else {
			p = PropertyUtils.getPropertyDescriptors(entity);
		}
		
		boolean idTrouve = false;
		int i = 0;
		while(!idTrouve && i<p.length) {
			if(p[i].getReadMethod().getAnnotation(Id.class)!=null) {
				idTrouve = true;
			}
			if(!idTrouve) {
				i++;
			}
		}
		if(idTrouve) {
			try {
				Column c = p[i].getReadMethod().getAnnotation(Column.class);
				champIdTable = c.name();
				champIdEntite = p[i].getName();
				valeurIdTable = (Integer)p[i].getReadMethod().invoke(entity, new Object[]{});
				return valeurIdTable;
			} catch (IllegalArgumentException e) {
				logger.error("",e);
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			}
			return 0;
		} else {
			return 0;
		}
	}
	
	
	public static boolean autoriseUtilisationCodeGenere(String code,String nomTable) throws Exception{
		return autoriseUtilisationCodeGenere(code,nomTable,true);
	}	

	public boolean autoriseUtilisationCodeGenere(String code) throws Exception{
		return autoriseUtilisationCodeGenere(code,nomTable,true);
	}

	public static boolean autoriseUtilisationCodeGenere(String code,String nomTable,Boolean verif_Connection) throws Exception{
		boolean res = true;
		String champVerif=gestionModif.recupChampGenere(nomTable);
		if (champVerif!=null)
			res= gestionModif.autoriseModifCodeGenere(nomTable,champVerif,code,verif_Connection);
		return res;
	}	

	public boolean rentreCodeGenere(String code,String ChampCourant) throws Exception{
		try{
			boolean res = false;
			String champVerif=gestionModif.recupChampGenere(this.nomTable);
			if (champVerif.equals(ChampCourant)|| ChampCourant==null)
				if (champVerif!=null && code !=null && !LibChaine.empty(code))
				{
					gestionModif.rentreEnModif(nomTable,champVerif,code);
					champGenere=champVerif;
					valeurGenere=code;
					res=true;
				}
			return res;
		}catch(Exception e){
			logger.error("",e);
			return false;
		}
	}


	public void annulerCodeGenere(String code,String ChampCourant) throws Exception{
		String champVerif=gestionModif.recupChampGenere(this.nomTable);
		if (champVerif.equals(ChampCourant)|| ChampCourant==null)
			if (champVerif!=null && code !=null && !LibChaine.empty(code))
			{
				gestionModif.annuleModif(nomTable,champVerif,code,false);
			}
	}

	public boolean recordModifiable(String nomTable,Integer valeurChamp) {
		try{
			CallableStatement cs;
			boolean res=true;
			if (valeurChamp==null )throw new Exception("Valeur id_table non renseignée");
			if (!LibChaine.emptyNumeric(valeurChamp)){
				cs = cnx.prepareCall("{? = call " + "RECORD_MODIFIABLE" +"(?,?)}");

				cs.registerOutParameter(1,Types.INTEGER);
				cs.setString(2,nomTable);
				cs.setInt(3,valeurChamp);
				cs.execute();
				if (cs.getInt(1)>0)
					res=false;
			}
			return res;
		}catch(Exception e){
			logger.error("",e);
			return false;
		}
	}
//	public boolean recordModifiable(String nomTable,String valeurChamp) {
//		try{
//			CallableStatement cs;
//			boolean res=true;
//			if (valeurChamp==null )throw new Exception("Valeur id_table non renseignée");
//			if (!LibChaine.empty(valeurChamp)){
//				cs = cnx.prepareCall("{? = call " + "RECORD_MODIFIABLE" +"(?,?)}");
//
//				cs.registerOutParameter(1,Types.INTEGER);
//				cs.setString(2,nomTable);
//				cs.setInt(3,LibConversion.stringToInteger(valeurChamp));
//				cs.execute();
//				if (cs.getInt(1)>0)
//					res=false;
//			}
//			return res;
//		}catch(Exception e){
//			logger.error("",e);
//			return false;
//		}
//	}

	public boolean autoriseModification() throws Exception{
		boolean res = true;
		String champVerif=gestionModif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			res= gestionModif.autoriseModif(nomTable,champVerif,valeurIdTable.toString());
		return res;
	}

	public void rentreEnModification() throws Exception{
		String champVerif=gestionModif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			gestionModif.rentreEnModif(nomTable,champVerif,valeurIdTable);
	}

	public void annuleModification(boolean commited) throws Exception{
		String champVerif=gestionModif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			gestionModif.annuleModif(nomTable,champVerif,
					valeurIdTable,commited);
	}
	

	public void setListeChampMaj(String fileName){
		try {
			if (!new File(fileName).exists()) {
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
						"Le fichier .properties "+ fileName + " est inexistant");
			} else {
				FileInputStream file = new FileInputStream(fileName);
				listeChampMaj.load(file);
				file.close();
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setListeChampMaj", e);
		}
	}
	
	public static Properties getListeChampMaj() {
		return listeChampMaj;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public Integer getValeurIdTable() {
		return valeurIdTable;
	}

	public String getChampIdEntite() {
		return champIdEntite;
	}
	
	public HashMap getListeChampsCalcules() {
		return listeChampsCalcules;
	}
	
	public void setListeChampsCalcules(HashMap listeChampsCalcules) {
		this.listeChampsCalcules = listeChampsCalcules;
	}


	public int selectCount() {
		// TODO Auto-generated method stub
		return 1;
//		return selectAll().size();
	}

	public String getChampGenere() {
		return champGenere;
	}

	public void setChampGenere(String champGenere) {
		this.champGenere = champGenere;
	}

	public String getJPQLQueryInitial() {
		return JPQLQueryInitial;
	}

	public void setJPQLQueryInitial(String jPQLQueryInitial) {
		JPQLQueryInitial = jPQLQueryInitial;
	}
	protected String initChampId(T entity) {	
			Class clazz = deproxyClass(entity);
			PropertyDescriptor[] p;
			if(clazz!=null) {
				p = PropertyUtils.getPropertyDescriptors(clazz);
			} else {
				p = PropertyUtils.getPropertyDescriptors(entity);
			}
			
			boolean idTrouve = false;
			int i = 0;
			while(!idTrouve && i<p.length) {
				if(p[i].getReadMethod().getAnnotation(Id.class)!=null) {
					idTrouve = true;
				}
				if(!idTrouve) {
					i++;
				}
			}
			if(idTrouve) {
				try {
					Column c = p[i].getReadMethod().getAnnotation(Column.class);
					this.champIdTable =c.name();
					return this.champIdTable;
				} catch (IllegalArgumentException e) {
					logger.error("",e);
				} 
				return null;
			} else {
				return null;
			}
		}	
	
	protected String initChampId(Class entityClass) {	
		//Class clazz = deproxyClass(entity);
		PropertyDescriptor[] p=null;
		boolean idTrouve = false;
		int i = 0;
		if(entityClass!=null) {
			p = PropertyUtils.getPropertyDescriptors(entityClass);
			
			while(!idTrouve && i<p.length) {
				if(p[i].getReadMethod().getAnnotation(Id.class)!=null) {
					idTrouve = true;
				}
				if(!idTrouve) {
					i++;
				}
			}
		} 	
		if(idTrouve) {
			try {
				Id c = p[i].getReadMethod().getAnnotation(Id.class);
				this.champIdTable =p[i].getName();
				return this.champIdTable;
			} catch (IllegalArgumentException e) {
				logger.error("",e);
			} 
			return null;
		} else {
			return null;
		}
	}
//		
//		PropertyDescriptor[] properties= PropertyUtils.getPropertyDescriptors(entity);
//				javax.persistence.Id id=null;
//		for (int i = 0; i < properties.length; i++) {
//			if(PropertyUtils.getReadMethod(properties[i]).getAnnotation(javax.persistence.Id.class)!=null){
//				champIdTable=properties[i].getName();
//			}
//		}
//	}

}