package fr.legrain.liasseFiscale.db;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;

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
import fr.legrain.lib.gui.DestroyListener;


/**
 * @author Le Grain S.A
 */

public class IbLiasse extends IBQuLgr {
	static Logger logger = Logger.getLogger(IbLiasse.class.getName());
	protected CtrlLiasse TCtrlGeneraux = new CtrlLiasse();
	static String IP_ACCES = new String("0");
	public static final String C_DB_LIASSE = "DB_LIASSE";
	
	
	public IbLiasse() {		
		boolean dejaOuverte = false;
		try {
			//setFIBBase(OuvertureBase.getDatabase(Const.C_URL_BDD+new File(Const.C_FICHIER_BDD).getCanonicalPath(),Const.C_USER,Const.C_PASS , Const.C_DRIVER_JDBC));
			//System.out.println(new File(Const.C_FICHIER_BDD).getCanonicalPath());
			//System.out.println(Const.C_FICHIER_BDD);
			dejaOuverte=OuvertureBase.getDatabase(C_DB_LIASSE)!=null;
			
//			Database database = new Database();
//			database.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
//			ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD,ConstLiasse.C_USER,ConstLiasse.C_PASS , false,ConstLiasse.C_DRIVER_JDBC));
//			OuvertureBase.setDatabase(database);
			
			setFIBBase(OuvertureBase.getDatabase(C_DB_LIASSE,ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD,ConstLiasse.C_USER,ConstLiasse.C_PASS , ConstLiasse.C_DRIVER_JDBC));
			
			if (modif.getListeGestionModif()==null)
				modif.setListeGestionModif(ConstLiasse.C_FICHIER_MODIF);
			
			if (!dejaOuverte){
				modif=new GestionModif(ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD,ConstLiasse.C_USER,ConstLiasse.C_PASS , ConstLiasse.C_DRIVER_JDBC);
				recupIpAcces(OuvertureBase.getDatabase(C_DB_LIASSE,ConstLiasse.C_URL_BDD+ConstLiasse.C_FICHIER_BDD,ConstLiasse.C_USER,ConstLiasse.C_PASS , ConstLiasse.C_DRIVER_JDBC));
				modif.initIp(getIP_ACCES());
				// setIpAccesConnection(LibConversion.stringToInteger(getIP_ACCES()));
			}
		}catch(Exception e){
			logger.error("Erreur : IbLiasse", e);	
		}
	}
	
	/**
	 * Retourne une référence sur la base de donnée
	 * @return - com.borland.dx.sql.dataset.Database;
	 */
	static public Database findDatabase() {
		return OuvertureBase.getDatabase(C_DB_LIASSE);
	}
	
	/**
	 * Force le changement de connection à la base de données (reconnection) à partir
	 * des identifiants contenue dans les constantes <code>Const</code>.
	 */
	static public void changementConnectionBdd() {
		try {
			suppressionAcces(OuvertureBase.getDatabase(C_DB_LIASSE));
			OuvertureBase.connection(OuvertureBase.getDatabase(C_DB_LIASSE),ConstLiasse.C_URL_BDD+new File(ConstLiasse.C_FICHIER_BDD).getCanonicalPath(),
					ConstLiasse.C_USER,ConstLiasse.C_PASS , ConstLiasse.C_DRIVER_JDBC);
//			OuvertureBase.getDatabase().setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
//			Const.C_URL_BDD+new File(Const.C_FICHIER_BDD).getCanonicalPath(),
//			Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
			recupIpAcces(OuvertureBase.getDatabase(C_DB_LIASSE));
		} catch (IOException e1) {
			logger.error("Erreur : IbLiasse", e1);	
		} catch (Exception e) {
			logger.error("Erreur : IbLiasse", e);
		}
	}
	
	
	/**
	 * Force le changement de connection à la base de données (reconnection) à partir
	 * des identifiants contenue dans les constantes <code>Const</code>.
	 */
	static public void fermetureConnectionBdd() {
		try {
			if (OuvertureBase.getDatabase(C_DB_LIASSE)!=null){
				suppressionAcces(OuvertureBase.getDatabase(C_DB_LIASSE));
				NettoyageBase(OuvertureBase.getDatabase(C_DB_LIASSE));
				OuvertureBase.getDatabase(C_DB_LIASSE).closeConnection();
				OuvertureBase.setDatabase(C_DB_LIASSE,null);
				logger.info("fermetureConnectionBdd");
			}
		} catch (Exception e) {
			logger.error("Erreur : fermetureConnectionBdd", e);
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
	
	
	
	static public void razAcces(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "RAZ_ACCES() }");
				cs.execute();
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
				base.getJdbcConnection().commit();
			}
		} catch (Exception e1) {
			logger.error("Erreur : SUPPRESSION_ACCES", e1);	
		}
	}
	
	static public void NettoyageBase(Database base) throws Exception {
		try {
			if (base!=null){
				CallableStatement cs;
				cs = base.getJdbcConnection().prepareCall("{call " + "NETTOYAGE() }");
				cs.execute();
				base.getJdbcConnection().commit();
				logger.info("NettoyageBase");
			}
		} catch (Exception e1) {
			logger.error("Erreur : NettoyageBase", e1);	
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
	
	
	public String genereCode() throws Exception{
//		try{
//		boolean nonAutorise = false;
//		String res=null;
//		int compteur=-1;
//		GenCode code = new GenCode();
//		code.setListeGestCode(Const.C_FICHIER_GESTCODE);		
//		while (!nonAutorise) {
//		compteur ++;
//		res=code.GenereCode(nomTable,compteur);
//		nonAutorise=autoriseUtilisationCodeGenere(res);	
//		};
//		return res;
//		}catch(Exception e){
//		return "";
//		}
		return null;
	}
	
	
}
