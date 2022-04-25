package fr.legrain.lib.data;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.lib.gui.LgrLocale;

/**
 * <p>Title: </p>
 * <p>Description: Classe de contrôle de saisie générale.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public abstract class JPACtrlChamp extends JPABdLgr {

	private static final Log logger = LogFactory.getLog(JPACtrlChamp.class);

//	/*
	private HashMap valeurRetour; 
	/*static*/ private HashMap listeTableBD=new HashMap(); //Liste des tables et des vues présentes dans la base
	/*static*/ private Properties listeIDBD=new Properties();
	public MessUtilisateur messUtilisateur;
//	private QueryDataSet dataSetRech = new QueryDataSet();

//	private FEventCtrlLgr : TEventCtrlLgr;
//	private FExecuteControle:TExecuteControle;
//	private FIBQuery: TIBQuery;
	private boolean afficheMessage;
	private String fichierIniID;

	/**
	 * Construit la liste des table et vues d'une base DB
	 * Attention, ne pas appeler cette procédure lors de la construction
	 * d'une requete portant sur FIBQuery
	 * @param liste Vector
	 */
	protected void getAllTable_View(Vector liste){ //TStrings
		try {
			ResultSet res = getDbMetaData().getTables(null, null, "%",new String[] {"TABLE", "VIEW"});
			while (res.next()){
				listeTableBD.put(res.getString("TABLE_NAME"),"");
			}
		}
		catch (SQLException ex) {
			logger.error("Erreur : getAllTable_View", ex);
		}

//		SELECT rdb$relation_name FROM RDB$relations
//		where rdb$relation_name not like('%$%')
//		FIBQuery.close();
		//FIBQuery.SQL.Clear;
//		FIBQuery.setQuery(new QueryDescriptor(this.getFIBBase(),
//		"SELECT rdb$relation_name FROM RDB$relations where rdb$relation_name not like(''%$%'')",
//		true));
//		FIBQuery.open();
//		do { //FListeTableBD=ChampTableVersListe("rdb$relation_name",FIBQuery);
		/** @todo Mettre dans clé , dans valeur ou bien utiliser un autre container */
//		FListeTableBD.put(FIBQuery.getString("rdb$relation_name"),"");
//		} while(FIBQuery.next());

		//for (int i=0; i<FListeTableBD.size(); i++) {   //i:=0 to FListeTableBD.Count -1 {
		//  FListeTableBD.getStrings[i] = str_supprespaces(FListeTableBD.Strings[i]);
		//}

		//liste=ChampTableVersListe("rdb$relation_name",FIBQuery);
//		for(Iterator ite = FListeTableBD.keySet().iterator(); ite.hasNext(); ){
//		liste.add(FListeTableBD.get((String)ite.next()));
//		}
		//for (int j =0; j<liste.length; j++) { //j:=0 to Liste.Count -1 {
		//  liste.Strings[j] = str_supprespaces(liste.Strings[j]);
		//}
	}

	/**
	 * @param value String - Chemin du fichier contenant les identifiants primaire de chaque table
	 */
	public void setFichierIni_ID(String value){
		try {
			if (!new File(value).exists()) {
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
						"Le fichier .ini "+ value + " est inexistant");
			} else {
				fichierIniID = value;
				FileInputStream file= new FileInputStream(fichierIniID);
				listeIDBD.load(file);
				file.close();
				getAllTable_View(null);
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setFichierIni_ID", e);
			fichierIniID = "";
		}
	}

	/**
	 * cle primaire de chaque table
	 * @return String - Chemin du fichier contenant les identifiants primaire de chaque table
	 */
	public String getFichierIni_ID(){
		return fichierIniID;
	}

	/**
	 * ctrlSaisie
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public abstract void ctrlSaisie(MessCtrlLgr message) throws ExceptLgr;


	/**
	 * @param nomTable String - Nom de la table dont on cherche l'identifiant
	 * @return String - Identifiant principal de la table
	 */
	/*static*/ public String getID_TABLE(Class nomTable) {
//		String result = LgrConstantes.C_STR_VIDE;
//		if (listeIDBD.size() == 0) {
//			MessageDialog.openWarning(PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getShell(), "ATTENTION",
//			"Attention,la liste des identifiants de table est vide.");
//		}
////#JPA
////		if (listeTableBD.containsKey(nomTable)) {
//			if (listeIDBD.containsKey(nomTable)) { 
//				result = (String)listeIDBD.get(nomTable);
//			} else {
//				MessageDialog.openWarning(PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
//						"Attention, la table ["+nomTable+"] est introuvable dans la liste FListeIDBD.");
//			}
////		} else {
////			MessageDialog.openWarning(PlatformUI.getWorkbench()
////					.getActiveWorkbenchWindow().getShell(), "ATTENTION",
////					"Attention, la table ["+nomTable+"] est introuvable dans la liste FListeTableBD.");
////		}
//		return result;
	return null;
	}

	public JPACtrlChamp() {
		super();
		//	fIBQuery = new QueryDataSet();
		if(listeTableBD==null)
			listeTableBD = new HashMap();
		if (listeIDBD==null)
			listeIDBD = new Properties();
		afficheMessage = true;
	}

	/**
	 * ctrl_ValeurQueDesChiffres N°109
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public boolean ctrl_ValeurQueDesChiffres(MessCtrlLgr message,boolean formate) throws ExceptLgr {
		DecimalFormatSymbols decimalSeparateur = new DecimalFormatSymbols();
		String V= "";
		try {/*message.getNomTable()+"."+*/
			if(! LibChaine.emptyNumeric(message.getValeur())){
				message.setMessageAffiche("La valeur du champ : "+JPABdLgr.getTitreChamp(message.getNomChamp())+" n'est pas correcte.");//
				if (message.getValeur()!=null){
					for (int i = 0; i < message.getValeur().length(); i++) {
						V =  message.getValeur().substring(i,i+1);
						try {
							if (formate && !LibChaine.formatChiffre(V))							
								LibConversion.stringToInteger(V);
							else if (!formate)
								LibConversion.stringToInteger(V);
						} catch (Exception e) {
							throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			return true;
		} catch(Exception e) {
			logger.error("Erreur : ctrl_ValeurQueDesChiffres", e);
			return false;
		}
	}

	/**
	 * ctrl_ValeurNumerique
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public void ctrl_ValeurNumerique(MessCtrlLgr message) throws ExceptLgr {
		try {
			if(! LibChaine.emptyNumeric(message.getValeur())){
				if (ctrl_ValeurQueDesChiffres(message,true)){
					message.setMessageAffiche("La valeur : "+message.getValeur()+" n'est pas une valeur numérique correcte");
					//LibConversion.stringToFloat(message.getValeur());
					//System.out.println("message.getValeur() : "+message.getValeur());
					NumberFormat nf = NumberFormat.getInstance(LgrLocale.decimal);

					Number myNumber = nf.parse(message.getValeur());
					//System.out.println("Number : "+myNumber);
					myNumber = myNumber.floatValue();
					//System.out.println("floatValue : "+myNumber.floatValue());
					//Number.parseFloat(message.getValeur());
				}else{ 
					throw new ExceptLgr();
				}
			}
		} catch(Exception e) {
			logger.error("Erreur : ctrl_ValeurNumerique", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}		
	}

	/**
	 * ctrl_MontantPositif N°106
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public void ctrl_MontantPositif(MessCtrlLgr message) throws ExceptLgr {
		try {
			if(! LibChaine.emptyNumeric(message.getValeur())){
				if (ctrl_ValeurQueDesChiffres(message,true)){
					ctrl_ValeurNumerique(message);			
					message.setMessageAffiche("Le champ "+JPABdLgr.getTitreChamp(/*message.getNomTable()+"."+*/message.getNomChamp())+" est négatif.");//+C_STR_SAUT_DEUX+C_CHAMP_NON_VIDE;
					if ((message.getValeur()==null)||(java.lang.Float.valueOf(message.getValeur()).floatValue()<0)){
						throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
					}
				}else {
					throw new ExceptLgr();
				}
			}
		} catch(Exception e) {
			logger.error("Erreur : ctrl_MontantPositif", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * ctrl_Boolean N°108
	 * @param message
	 * @throws ExceptLgr
	 */
	public void ctrl_Boolean(MessCtrlLgr message) throws ExceptLgr {
		try {
			message.setMessageAffiche("La valeur du champ : "+message.getNomChamp()+" n'est pas correcte.");//
			if (message.getValeur()!=null){
				if(((message.getValeur() == null) || !(message.getValeur().equalsIgnoreCase("true")||
						message.getValeur().equalsIgnoreCase("false")||
						message.getValeur().equalsIgnoreCase("1")||
						message.getValeur().equalsIgnoreCase("0"))))
					throw new ExceptLgr();
			}
		} catch(Exception e) {
			logger.error("Erreur : ctrl_Boolean", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * ctrl_ValeursKeyAutorisees N°110
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public boolean ctrl_ValeursKeyAutorisees(MessCtrlLgr message) throws ExceptLgr {
		String V= "";
		try {			
//			message.setMessageAffiche("La valeur du champ : "+BdLgr.getTitreChamp(message.getNomChamp())+" n'est pas correcte.");//
			message.setMessageAffiche("La valeur du champ : "+message.getNomChamp()+" n'est pas correcte.");//
			if (message.getValeur()!=null){
				for (int i = 0; i < message.getValeur().length(); i++) {
					V =  message.getValeur().substring(i,i+1);
					try {
						if (!LibChaine.keyAutorise(V))	throw new ExceptLgr();		
					} catch (Exception e) {
						throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			return true;
		} catch(Exception e) {
			logger.error("Erreur : ctrl_ValeursKeyAutorisees", e);
			return false;
		}
	}

	/**
	 * ctrl_Date N°105
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public void ctrl_Date(MessCtrlLgr message) throws ExceptLgr {
		java.util.Date dateTmp = null;
		try {
			message.setMessageAffiche("La date "+ message.getValeur() +" n''est pas une date correcte");
			dateTmp=LibDate.stringToDate(message.getValeur());
			if(LibDate.dateToString(dateTmp).equals(""))
				throw new Exception();
			//if(!LibDate.dateToString(dateTmp).equals(message.getValeur()))
			//throw new Exception();
		}
		catch (Exception e ){
			logger.error("Erreur : ctrl_Date", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * ctrl_Date N°105
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public void ctrl_Date(String date) throws ExceptLgr {
		MessCtrlLgr message = new MessCtrlLgr();
		message.setValeur(date);
		ctrl_Date(message);
	}


	/**
	 * ctrl_EstNonVide N°107 et 102
	 * @param message TMessCtrlLgr
	 * @throws TExceptLgr
	 */
	public void ctrl_EstNonVide(MessCtrlLgr message) throws ExceptLgr {
		try {
			//if ((message.getValeur()==null)||(message.getValeur().equals(LgrConstantes.C_STR_VIDE))) {
			if (LibChaine.empty(message.getValeur())) {	
				message.setMessageAffiche("Le champ "+JPABdLgr.getTitreChamp(/*message.getNomTable()+"."+*/message.getNomChamp())+" est vide.");
				throw new ExceptLgr();
			}
		}
		catch (ExceptLgr exceptLgr ){
			logger.error("Erreur : ctrl_EstNonVide", exceptLgr);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
		catch (Exception e ){
			logger.error("Erreur : ctrl_EstNonVide", e);
			throw new ExceptLgr(LgrConstantes.C_ERREUR_INCONNUE,message.getCodeErreur(),afficheMessage,JOptionPane.WARNING_MESSAGE);//,result);
		}
	}

	/**
	 * ctrl_CodePostal N°104
	 * @param valeur String
	 * @param codeErreur int
	 * @param nomChamp String
	 * @throws TExceptLgr
	 */
	public void ctrl_CodePostal(MessCtrlLgr message) throws ExceptLgr {
		try {
			message.setMessageAffiche("Le code postal " + message.getValeur() +
			" n''est pas correct");
			if ((message.getValeur()==null)||(message.getValeur().length() < 5))
				throw new ExceptLgr(); 
			ctrl_Longueur(message);
		}
		catch (Exception e ){
			logger.error("Erreur : ctrl_CodePostal", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
	}

	public void ctrl_Longueur(MessCtrlLgr message) throws ExceptLgr {
		try {
			try {
				ResultSet rs = getDbMetaData().getColumns(null, null, message.getNomTable(), message.getNomChampDB());
			} catch (Exception e) {
				ResultSet rs = getDbMetaData().getColumns(null, null, message.getNomTable(), message.getNomChampDB());
			}
			ResultSet rs = getDbMetaData().getColumns(null, null, message.getNomTable(), message.getNomChampDB());
			rs.next();
			int longueurTmp=    rs.getInt(7);
			int precisionTmp= rs.getInt(9);
			message.setMessageAffiche("La longueur du champ " + message.getNomChampDB() +
					" ne doit pas dépasser "+String.valueOf(longueurTmp)+" caractères");
			if (!LibChaine.emptyNumeric(message.getValeur())){
				if (message.getValeur().trim().indexOf(".")!=-1) {
					longueurTmp++;
				}
				if (message.getValeur().length() > longueurTmp ) 
					throw new ExceptLgr(); //,result);
				if(precisionTmp>0){
					String valeur =message.getValeur().trim().substring(message.getValeur().trim().indexOf(".")+1);
//					valeur=valeur.substring(valeur.indexOf(decimalSeparateur.getDecimalSeparator())+1);
					if (valeur.compareTo(message.getValeur().trim())==0)valeur="";
					message.setMessageAffiche("Le nombre de chiffre après la virgule du champ " + message.getNomChampDB() +
							" ne doit pas dépasser "+String.valueOf(precisionTmp)+" caractères");

					if (valeur.length() >precisionTmp) 
						throw new ExceptLgr(); //,result);
				}
			}
		}
		catch (Exception e ){
			logger.error("Erreur : ctrl_Longueur", e);
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
	}


	public void ctrl_Majuscule(MessCtrlLgr message) throws ExceptLgr {
		message.setValeur(message.getValeur().toUpperCase());
	}



	/**
	 * ctrl_ExisteDansTable N°103
	 * @param valeur String
	 * @param codeErreur int
	 * @param nomTable String
	 * @param nomChamp String
	 * @throws TExceptLgr
	 */
	public void ctrl_ExisteDansTable(MessCtrlLgr message) throws ExceptLgr {
		//EntityManager em = getEntityManager();
		EntityManager em = emf.createEntityManager();
		try {//try finally
//			//dataSetRech.close();
//			if (message.getNomTableEtrangere()!=null){
//				String rqt =
//					"Select a from " +
//					message.getEntityClass().getName() +
//					//message.getNomTableEtrangere() +
//					" a where " +
//					message.getNomChampEtranger() +
//					" like '" +
//					LibChaineSQL.formatStringSQL(message.getValeur()) + "'";
//				Query q = em.createQuery(rqt);
//				if (q.getResultList().size()==0) {
//					message.setMessageAffiche("La valeur '" + message.getValeur() +
//							"' n''existe pas dans la table " + message.getNomTableEtrangere());
//					throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
//				}
//			}
			if(!message.getValeur().equals("")){
			String rqt =
				"Select a from " + message.getEntityClass().getName()  +" a where " + message.getNomChamp()  + "='" +
				LibChaineSQL.formatStringSQL(message.getValeur()) + "'";
			Query q = em.createQuery(rqt);
			if (q.getResultList().size() == 0){
				message.setMessageAffiche("La valeur '" + message.getValeur() +
						"' n''existe pas dans la table " + message.getNomTable());
				throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
			}
			}
		}//fin du try
		finally{
//			em.close();
		};
	}


	/**
	 * renvoie vrai si la clé est utilisée dans la table étrangère
	 */
	public boolean ctrl_ExisteDansTableEtrangere(String tableEtrangere, String champEtranger, String valeur) {
		//EntityManager em = getEntityManager();
		EntityManager em = emf.createEntityManager();
		boolean res = false;
		String comparaison = new String("like"); 
		try {//try finally
//			dataSetRech.close();
			if (tableEtrangere!=null){
				String rqt =
					"Select * from " +
					tableEtrangere +
					" where " +
					champEtranger +
					" like '" +
					LibChaineSQL.formatStringSQL(valeur) + "'";
				Query q = em.createQuery(rqt);
//				dataSetRech.open();
				res=(q.getResultList().size() != 0) ;
//				dataSetRech.getQuery();
			}
			return res;
		}//fin du try
		finally{
//			em.close();
		}
	}

	/**
	 * renvoie vrai si la clé est utilisée dans la table étrangère
	 */
	public boolean ctrl_ExisteDansTableEtrangere(String tableEtrangere, String champEtranger,int typeChampEtranger, String valeur) {
		//EntityManager em = getEntityManager();
		EntityManager em = emf.createEntityManager();
		boolean res = false;
		String debComparaison = new String(" = "); 
		String finComparaison = new String(" "); 		
		try {//try finally
//			dataSetRech.close();
			if (tableEtrangere!=null){
				switch(typeChampEtranger) {
				case Variant.STRING:
				case Variant.DATE:
					debComparaison=" like'";
					finComparaison="' ";
				}
				String rqt =
					"Select * from " +
					tableEtrangere +
					" where " +
					champEtranger +
					debComparaison+
					LibChaineSQL.formatStringSQL(valeur) +finComparaison;
				Query q = em.createQuery(rqt);
//				dataSetRech.open();
				res=(q.getResultList().size() != 0) ;
			}
			return res;
		}//fin du try
		finally{
//			em.close();
		}
	}

	/**
	 * Renvoi vrai si une valeur n'existe pas déjà dans la table (utile lors de l'insertion pour préserver l'unicité)
     * A Controler
	 * ctrl_ExistePasDansTable
	 * @param valeur String
	 * @param codeErreur int
	 * @param nomTable String
	 * @param nomChamp String
	 * @throws TExceptLgr
	 */
	public void ctrl_ExistePasDansTable(MessCtrlLgr message) throws ExceptLgr {
		//EntityManager em = getEntityManager();
		EntityManager em = emf.createEntityManager();
		try {//try finally
//			dataSetRech.close();
			String rqt =
				"Select a from " + message.getEntityClass().getName()  +" a where " + message.getNomChamp()  + "='" +
				LibChaineSQL.formatStringSQL(message.getValeur()) + "'";
//			dataSetRech.open();
			Query q = em.createQuery(rqt);
			if (q.getResultList().size() > 0){
				message.setMessageAffiche("Cette valeur existe déjà dans la table "+message.getNomTable());
				throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
			}
		}//fin du try
		finally{
//			em.close();
		};
	}

	/**
	 * Renvoi vrai si une valeur n'existe pas déjà dans la table (utile lors de la modification pour préserver l'unicité)
	 * A Controler
	 * ctrl_ExistePasDansTableEx N°101
	 * @param valeur String
	 * @param valeurExclue String
	 * @param codeErreur int
	 * @param nomTable String
	 * @param nomChamp String
	 * @throws TExceptLgr
	 */
	public void ctrl_ExistePasDansTableEx(MessCtrlLgr message) throws ExceptLgr {
		//EntityManager em = getEntityManager();
		EntityManager em = emf.createEntityManager();
		try {//try finally
//			dataSetRech.close();
			String rqt =
				"Select a from "+ message.getEntityClass().getName() +" a where " + message.getNomChamp() + " like '"+LibChaineSQL.formatStringSQL(message.getValeur())+"'"
				+ " and  ("+message.getNomChampId()+" <> '"+message.getID_Valeur()+"')";
//			dataSetRech.open(); 
			Query q = em.createQuery(rqt);
			if (q.getResultList().size() > 0){
				message.setMessageAffiche("Cette valeur existe déjà dans la table "+message.getNomTable());
				throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
			}
		}//fin du try
		catch(Exception e){
			message.setMessageAffiche("Cette valeur existe déjà dans la table "+message.getNomTable());
			throw new ExceptLgr(message,message.getMessageAffiche(),message.getCodeErreur(),(afficheMessage && !message.isDejaAffiche()),JOptionPane.WARNING_MESSAGE);
		}
		finally{
//			if(em.isOpen())
//				em.close();
		};
	}

	HashMap getFValeurRetour(){return null;} //property ValeurRetour : TFieldList read LitValeurRetour; // Contient la liste des champs ainsi que les valeurs de l'enregistrment courant

}
