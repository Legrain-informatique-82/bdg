package fr.legrain.gestCom.Appli;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.borland.dx.sql.dataset.Database;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.GenCode;
import fr.legrain.lib.data.GestionModif;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.OuvertureBase;
import fr.legrain.lib.data.ParamInitIBQuLgr;
import fr.legrain.lib.data.ResultInitIBQuLgr;
import fr.legrain.lib.data.ValeurChamps;


/**
 * <p>Title: Gestion Commerciale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Le Grain A.A
 * @version 1.0
 */

public class IB_APPLICATION extends IBQuLgr {
    static Logger logger = Logger.getLogger(IB_APPLICATION.class.getName());
	protected Ctrl_Application TCtrlGeneraux = new Ctrl_Application();
	static String IP_ACCES = new String("0");
	public static final String C_DB_GEST_COM = "DB_GEST_COM";
	

	public IB_APPLICATION() {		
		boolean dejaOuverte = false;
		try {
			dejaOuverte=OuvertureBase.getDatabase(C_DB_GEST_COM)!=null;
			setFIBBase(OuvertureBase.getDatabase(C_DB_GEST_COM,Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC));

//			if (modif.getListeGestionModif()==null)
				modif.setListeGestionModif(Const.C_FICHIER_MODIF);
			if (modif.getBase()==null || !modif.getBase().isOpen()){
				if (modif.getBase()==null)
					modif=new GestionModif(Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
				else
					modif.connection(Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
				modif.initIp(recupIpAccesSimple(modif.getBase()));
				logger.info("Ouverture base GestionModif");
			   // setIpAccesConnection(LibConversion.stringToInteger(getIP_ACCES()));
			}
		}catch(Exception e){
		logger.error("Erreur : IB_APPLICATION", e);	
		}
		
	}
	
	

	/**
	 * Retourne une référence sur la base de donnée
	 * @return - com.borland.dx.sql.dataset.Database;
	 */
	static public Database findDatabase() {		
		Database base =OuvertureBase.getDatabase(IB_APPLICATION.C_DB_GEST_COM);
		try {
			if(base==null ){
				return OuvertureBase.getDatabase(IB_APPLICATION.C_DB_GEST_COM,Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
			}
			else if (base.getJdbcConnection().isClosed()){
				OuvertureBase.connection(base,Const.C_URL_BDD+Const.C_FICHIER_BDD,
						Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
				return base;
			}
			else {
				return base;
			}
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Force le changement de connection à la base de données (reconnection) à partir
	 * des identifiants contenue dans les constantes <code>Const</code>.
	 */
	static public void changementConnectionBdd() {
		try {
			Database base=OuvertureBase.getDatabase(C_DB_GEST_COM);
			if(base!=null){
				suppressionAcces(base);
				base.closeConnection();
				base.getJdbcConnection().close();
				OuvertureBase.setDatabase(C_DB_GEST_COM,null);
			}
			base =findDatabase();
			if (base.getJdbcConnection().isClosed()){
				OuvertureBase.connection(base,Const.C_URL_BDD+Const.C_FICHIER_BDD,
					  Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
			}
			recupIpAcces(base);
		} catch (IOException e1) {
			logger.error("Erreur : IB_APPLICATION", e1);	
		} catch (Exception e) {
			logger.error("Erreur : IB_APPLICATION", e);
		}
	}
	

	/**
	 * Force le changement de connection à la base de données (reconnection) à partir
	 * des identifiants contenue dans les constantes <code>Const</code>.
	 */
	static public void fermetureConnectionBdd() {
		try {
			Database base =OuvertureBase.getDatabase(C_DB_GEST_COM);
			if (base!=null){
				suppressionAcces(base);
				NettoyageBase(base);
				base.closeConnection();
				if (base.getJdbcConnection()!=null)
					base.getJdbcConnection().close();
				OuvertureBase.setDatabase(C_DB_GEST_COM,null);
				logger.info("fermetureConnectionBdd");
			}
			modif.deConnection();
		} catch (Exception e) {
			logger.error("Erreur : fermetureConnectionBdd", e);
		}
	}	
	
	static public void SimpleDeConnectionBdd() {
		try {
			Database base =OuvertureBase.getDatabase(C_DB_GEST_COM);
			if (base!=null){
				suppressionAcces(base);
				NettoyageBase(base);
				base.closeConnection();
				base.getJdbcConnection().close();
				OuvertureBase.setDatabase(C_DB_GEST_COM,null);
				logger.info("fermetureConnectionBdd");
			}
			modif.deConnection();
		} catch (Exception e) {
			logger.error("Erreur : fermetureConnectionBdd", e);
		}
	}
	
	static public void SimpleReConnectionBdd() {
		try {
			Database base =findDatabase();
//			if (base.getJdbcConnection().isClosed())
//			OuvertureBase.connection(base,Const.C_URL_BDD+Const.C_FICHIER_BDD,
//					  Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
			base.setAutoCommit(false);
			modif.deConnection();
			modif.connection(Const.C_URL_BDD+Const.C_FICHIER_BDD,
					  Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC);
			modif.initIp(recupIpAccesSimple(modif.getBase()));
			recupIpAcces(base);
		} catch (IOException e1) {
			logger.error("Erreur : IB_APPLICATION", e1);	
		} catch (Exception e) {
			logger.error("Erreur : IB_APPLICATION", e);
		}
	}

	
	static public void recupIpAcces(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{? = call " + "RECUP_IP_ACCES() }");
				cs.registerOutParameter(1,java.sql.Types.VARCHAR);
				cs.execute();
				setIP_ACCES(cs.getString(1));
				razAcces(base);
				NettoyageBase(base);
				MajGenerateur(base);
				EnregistreAcces(base);
			}
		} catch (Exception e1) {
			logger.error("Erreur : recupIpAcces", e1);	
		}
	}
	
	static public String recupIpAccesSimple(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{? = call " + "RECUP_IP_ACCES() }");
				cs.registerOutParameter(1,java.sql.Types.VARCHAR);
				cs.execute();
				return cs.getString(1);
			}
		} catch (Exception e1) {
			logger.error("Erreur : recupIpAcces", e1);	
		}
		return null;
	}
	
	static public Boolean verifMultiConnection() throws Exception {
		//select distinct mon.mon$attachment_id from mon$attachments mon
		//		where mon.mon$attachment_id<>current_connection
		String query="select  distinct (mon.mon$attachment_id) as id from mon$attachments mon  " 
				+"where mon.mon$attachment_id<>current_connection";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//
			Database base =findDatabase();
			if (base!=null){
				preparedStatement = base
					.createPreparedStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					return resultSet.getInt("id")!=0 && 
						resultSet.getInt("id")!= LibConversion.stringToInteger(modif.getIpConnection()) ;
				}		
			}
			fermetureConnectionBdd();
		} catch (Exception e1) {
			logger.error("Erreur : verifMultiConnection", e1);	
		}
		return false;
	}
	
	
	static public void razAcces(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "RAZ_ACCES() }");
				cs.execute();
				if(!base.getJdbcConnection().getAutoCommit())
					base.getJdbcConnection().commit();
				logger.info("razAcces");
			}
		} catch (Exception e1) {
			logger.error("Erreur : razAcces", e1);	
		}
	}//
	
	
	
	static public void EnregistreAcces(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "ENREGISTRE_ACCES() }");
				cs.execute();
				if(!base.getJdbcConnection().getAutoCommit())
					base.getJdbcConnection().commit();
			}
		} catch (Exception e1) {
			logger.error("Erreur : EnregistreAcces", e1);	
		}
	}//
	
	static public void MajGenerateur(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "MAJ_GENERATEUR() }");
				cs.execute();
				if(!base.getJdbcConnection().getAutoCommit())
					base.getJdbcConnection().commit();
				logger.info("MajGenerateur");
			}
		} catch (Exception e1) {
			logger.error("Erreur : MajGenerateur", e1);	
		}
	}//	
	static public void suppressionAcces(Database base) throws Exception {
		try {			
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "SUPPRESSION_ACCES() }");
				cs.execute();
				if(!base.getJdbcConnection().getAutoCommit())
					base.getJdbcConnection().commit();
			}
		} catch (Exception e1) {
			logger.error("Erreur : SUPPRESSION_ACCES", e1);	
		}
	}
	
	/**
	 * Supprime les verouillage et les enregistrements dans les tables temporaires ayant un certains nombre de jour d'anciennete.
	 * @param base - la base sur laquelle on a une connection
	 * @param delais - anciennete en nombre de jours des enregistrements a supprimer
	 * @throws Exception
	 */
	static public void NettoyageBase(Database base,int delais) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				//cs = base.getJdbcConnection().prepareCall("{call " + "NETTOYAGE() }");
				cs = base.getJdbcConnection().prepareCall("{call " + "NETTOYAGE(?) }");
				cs.setInt(1, delais);
				cs.execute();
				if(!base.getJdbcConnection().getAutoCommit())
					base.getJdbcConnection().commit();
				logger.info("NettoyageBase");
			}
		} catch (Exception e1) {
			logger.error("Erreur : NettoyageBase", e1);	
		}
	}
	
	/**
	 * Supprime les verouillage et les enregistrements dans les tables temporaires ayant plus d'un jour d'anciennete.
	 * @param base
	 * @throws Exception
	 */
	static public void NettoyageBase(Database base) throws Exception {
		NettoyageBase(base,1);
	}
	/**
	 * Supprime les verouillage et les enregistrements dans les tables temporaires ayant plus d'un jour d'anciennete.
	 * @param base
	 * @throws Exception
	 */
	static public void NettoyageBase() throws Exception {
		Database base =OuvertureBase.getDatabase(C_DB_GEST_COM);
		if(base==null)base=findDatabase();
		if (base!=null){
			NettoyageBase(base,1);
		}
	}
	public ResultInitIBQuLgr init(Object objetAppelant, ParamInitIBQuLgr param) {
		/**@todo Implement this fr.legrain.lib.data.IBQuLgr abstract method*/
		throw new java.lang.UnsupportedOperationException("Method init() not yet implemented.");
	}

	public void ctrlSaisieSpecifique(String nomChamp, String valeur, MessCtrlLgr ex) throws ExceptLgr {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	public void ctrlSaisieSpecifique(ValeurChamps valeurChamps, MessCtrlLgr ex) throws ExceptLgr {
		// TODO Raccord de méthode auto-généré
		
	}

	public static String getIP_ACCES() {
		return IP_ACCES;
	}

	public static void setIP_ACCES(String ip_acces) {
		IP_ACCES = ip_acces;
	}

	//#JPA classe a supprimer completement
//	public static String genereCode(String nomTable,Boolean verif_Connection) throws Exception{
//		try{
//			boolean nonAutorise = false;
//			String res=null;
//			int compteur=-1;
//			GenCode code = new GenCode();
//			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
//			INFO_ENTREPRISE infoEntreprise =null;
//			infoEntreprise=IB_TA_INFO_ENTREPRISE.infosEntreprise(infoEntreprise);
//			while (!nonAutorise) {
//				compteur ++;
//				res=code.GenereCode(nomTable,compteur,infoEntreprise.getCODEXO_INTRA_INFO_ENTREPRISE());
//				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable,verif_Connection);	
//			};
//			code.reinitialiseListeGestCode();
//			return res;
//		}catch(Exception e){
//			return "";
//		}
//	}
//
//	public static String genereCode(String nomTable) throws Exception{
//		try{
//			boolean nonAutorise = false;
//			String res=null;
//			int compteur=-1;
//			GenCode code = new GenCode();
//			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
//			INFO_ENTREPRISE infoEntreprise =null;
//			infoEntreprise=IB_TA_INFO_ENTREPRISE.infosEntreprise(infoEntreprise);
//			while (!nonAutorise) {
//				compteur ++;
//				res=code.GenereCode(nomTable,compteur,infoEntreprise.getCODEXO_INTRA_INFO_ENTREPRISE());
//				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable);	
//			};
//			code.reinitialiseListeGestCode();
//			return res;
//		}catch(Exception e){
//			return "";
//		}
//	}
//	public static GenCode recupCodeDocument(String nomTable) throws Exception{
//		try{
//			boolean nonAutorise = false;
//			String res=null;
//			int compteur=-1;
//			GenCode code = new GenCode();
//			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
//			INFO_ENTREPRISE infoEntreprise =null;
//			infoEntreprise=IB_TA_INFO_ENTREPRISE.infosEntreprise(infoEntreprise);
//			while (!nonAutorise) {
//				compteur ++;
//				res=code.GenereCode(nomTable,compteur,infoEntreprise.getCODEXO_INTRA_INFO_ENTREPRISE());
//				nonAutorise=autoriseUtilisationCodeGenere(res,nomTable);	
//			};
//			code.reinitialiseListeGestCode();
//			return code;
//		}catch(Exception e){
//			return null;
//		}
//	}	
//	
//	public String genereCode() throws Exception{
//		try{
//			boolean nonAutorise = false;
//			String res=null;
//			int compteur=-1;
//			GenCode code = new GenCode();
//			code.setListeGestCode(Const.C_FICHIER_GESTCODE);
//			INFO_ENTREPRISE infoEntreprise =null;
//			infoEntreprise=IB_TA_INFO_ENTREPRISE.infosEntreprise(infoEntreprise);
//			while (!nonAutorise) {
//				compteur ++;
//				res=code.GenereCode(nomTable,compteur,infoEntreprise.getCODEXO_INTRA_INFO_ENTREPRISE());
//				nonAutorise=autoriseUtilisationCodeGenere(res);	
//			};
//			code.reinitialiseListeGestCode();
//			return res;
//		}catch(Exception e){
//			return "";
//		}
//	}


	static public boolean connectUser(String user,String password){
		boolean retour = true;
		try {
			
			Database base = findDatabase();
			OuvertureBase.connection(base,Const.C_URL_BDD+Const.C_FICHIER_BDD,
					user,password , Const.C_DRIVER_JDBC);
			base.setAutoCommit(false);
			base.closeConnection();
		} catch (Exception e1) {
			logger.error("Erreur : connectUser", e1);
			return false;
		}
		return retour;
	}


}
