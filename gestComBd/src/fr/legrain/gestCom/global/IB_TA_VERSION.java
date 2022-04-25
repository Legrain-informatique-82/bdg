package fr.legrain.gestCom.global;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.Module_Tiers.Ctrl_Tables.Ctrl_COMMENTAIRE;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.Update;
import fr.legrain.lib.data.ValeurChamps;

/**
 * <p>Title: Gestion Commerciale</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Le Grain A.A
 * @version 1.0
 */
public class IB_TA_VERSION extends IB_APPLICATION {
	static Logger logger = Logger.getLogger(IB_TA_VERSION.class.getName());
	
	public IB_TA_VERSION() {
		super();
		try {
			this.fIBQuery.setQuery(new QueryDescriptor(fIBBase,Const.C_Debut_Requete+ Const.C_NOM_TA_VERSION, true));
			this.fIBQuery.open();
			fIBBase.setAutoCommit(false);
			
			nomTable = Const.C_NOM_TA_VERSION;
			
			TCtrlGeneraux = new Ctrl_COMMENTAIRE();
			TCtrlGeneraux.setFIBBase(this.getFIBBase());
			TCtrlGeneraux.setFIBQuery(this.getFIBQuery());
			TCtrlGeneraux.setFFichierIni(Const.C_FICHIER_INI_CTRL);
			TCtrlGeneraux.setFichierIni_ID(Const.C_FICHIER_INI_IDBD);
			setFichierListeTitre(Const.C_FICHIER_LISTE_TITRE_BD);
			setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
			
			champIdTable=TCtrlGeneraux.getID_TABLE(nomTable);
			
			this.getFIBQuery().setRowId(champIdTable, true);
			this.setModeInsertionDirecte(true);
			update = new Update();
			update.setNomTable(nomTable);
			update.setIdChamps(champIdTable);
			listeMAJVue = new HashMap();
			
			//[id dans la vue][table etrangere][id champs étranger][contraintes (Obligatoire(1) ou Facultatif(0))]
			//listeRelation.put("CODE_TIERS",new String[] {"ID_TIERS","TA_TIERS","ID_TIERS","1"});
			
		}
		catch (Exception e) {
			logger.error("ERREUR : IB_TA_VERSION",e);
		}
	}
	
	
	public boolean verifCleEtrangereUtilisee( String nomChampEtranger, String nomTableEtrangere, String Valeur) {
		boolean res = false;
		String nouvelleValeur =new String("");
		try {
			if (Valeur==null)nouvelleValeur=this.getFChamp_Query(TCtrlGeneraux.getID_TABLE(nomTable));
			else nouvelleValeur=Valeur;
			res =TCtrlGeneraux.ctrl_ExisteDansTableEtrangere(nomTableEtrangere,nomChampEtranger,nouvelleValeur);
			return res;
		}
		catch (Exception e) {
			logger.error("Erreur : verifCleEtrangereUtilisee", e);
			return false;
		}
	}
	
	public void ctrlSaisieSpecifique(ValeurChamps valeurChamps,MessCtrlLgr ex) throws ExceptLgr {	
		try{
			switch (this.getFModeObjet().getMode()) {
			case C_MO_CONSULTATION: break;
			
			case C_MO_IMPORTATION:            				
			case C_MO_INSERTION:            	
				TCtrlGeneraux.ctrlSaisie(ex);//pour contrôle de l'unicité de l'id_tiers dans la table commentaire
				break;
			case C_MO_EDITION: 

				TCtrlGeneraux.ctrlSaisie(ex);
				break;
			case C_MO_SUPPRESSION: 

				TCtrlGeneraux.ctrlSaisie(ex);
				break;
			default:				
				break;
			}
		}catch (Exception e){
			logger.error("Erreur : ctrlSaisieSpecifique", e);
			throw new ExceptLgr(e.getMessage());
		}
	}
	
	public void enregistrement(VERSION version,boolean commited) throws ExceptLgr {
		try{    	  
			//if (!verifChamp(Const.C_LIBL_COMMENTAIRE,infoEntreprise.getLIBL_COMMENTAIRE(),null,null)) throw new ExceptLgr();
			//if (!verifChamp(Const.C_CODE_TIERS,infoEntreprise.getCODE_TIERS(),null,null)) throw new ExceptLgr();
			
			switch (this.getFModeObjet().getMode()) {
			case C_MO_INSERTION:{
				this.inserer();
				this.enregistrer();
			}
			break;
			case C_MO_EDITION:{
				//à la modification, on vérifie avant si cet enregistrement n'est pas lié à un autre, dans quel
				//cas, on demande une confirmation de modification avant.//TA_R_ADR (table de relation avec le tiers) 
				this.modfication(version,commited);
			}
			break;
			default:throw new ExceptLgr(MessagesGestCom.getString("Message.Erreur.dataset.Edition"),0,true,0); //$NON-NLS-1$
			}
			if (commited){
				commitLgr();
				//annuleModification(true);
			
			}
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : enregistrement", e);
			rollbackLgr();
			throw new ExceptLgr(e.getMessage());
		}//fin du catch
	}
	
	//insertion d'un nouveau tiers dans la table
	public void insertion() throws ExceptLgr {
		try{
			this.fListeChampValue.clear();
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : insertion", e);
		}//fin du catch
	}
	
	//modfication d'un nouveau tiers dans la table
	public void modfication(VERSION version,boolean commited) throws ExceptLgr {
		try {
			this.modifier();
			//if (!verifChamp(Const.C_LIBL_COMMENTAIRE,infoEntreprise.getLIBL_COMMENTAIRE(),null,null)) throw new ExceptLgr();
			//if (!verifChamp(Const.C_CODE_TIERS,infoEntreprise.getCODE_TIERS(),null,null)) throw new ExceptLgr();
			this.setChamp_Obj_Query();
			this.enregistrer();
			if(commited){
				commitLgr();
				//annuleModification(true);
			}
		}
		catch (Exception e) {
			logger.error("Erreur : modfication", e);
			rollbackLgr();
			throw new ExceptLgr(e.getMessage());
		}
	}
	
	
	public void suppression() throws ExceptLgr {
		try {
			//à la suppression, on vérifie avant si cet enregistrement n'est pas lié à un autre, dans quel
			//cas, on empêche la suppression.//TA_R_ADR (table de relation avec le tiers)
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
			ctrlSaisieSpecifique(Const.C_CODE_TIERS,null,null);
			
			this.supprimer();
			commitLgr(); 
		}
		catch(Exception e) {
			logger.error("Erreur : suppression", e);
			rollbackLgr();
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			throw new ExceptLgr(e.getMessage());
		}
	}
	
	
	/**
	 * @throws Throwable the <code>Exception</code> raised by this method
	 */
	protected void finalize() throws Throwable { //destructor Destroy; override;
			super.finalize();
	}
	public VERSION infosVersion(String idVersion,VERSION version) {
		if(version==null) version = new VERSION();
		StringBuffer requete = new StringBuffer("");
		try {
			requete.append("select ")
			.append(" * ").append(" from ").append(Const.C_NOM_TA_VERSION);
			//.append(" where ").append(Const.C_ID_VERSION_VERSION).append(" = ").append(idVersion);
			PreparedStatement rqt = getFIBBase().getJdbcConnection().prepareStatement(requete.toString());
			ResultSet res = rqt.executeQuery();
			if(res.next()){
				version.setNUM_VERSION(res.getString(Const.C_NUM_VERSION_VERSION));
				version.setOLD_VERSION(res.getString(Const.C_OLD_VERSION_VERSION));
			} 
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return version;
	}
	
}
