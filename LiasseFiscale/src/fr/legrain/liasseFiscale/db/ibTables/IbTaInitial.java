
package fr.legrain.liasseFiscale.db.ibTables;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.db.IbLiasse;
import fr.legrain.liasseFiscale.db.ctrlTables.CtrlTaRepart;
import fr.legrain.liasseFiscale.db.dao.Final;
import fr.legrain.liasseFiscale.db.dao.Initial;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.Update;
import fr.legrain.lib.data.ValeurChamps;

/**
 * @author Le Grain S.A
 */
public class IbTaInitial  extends IbLiasse {
	static Logger logger = Logger.getLogger(IbTaInitial.class.getName());
	
	public IbTaInitial() {
		super();
		try {
			this.fIBQuery.setQuery(new QueryDescriptor(fIBBase,ConstLiasse.C_Debut_Requete+ ConstLiasse.C_NOM_VU_INITIAL, true));
			this.fIBQuery.open();
			fIBBase.setAutoCommit(false);
			
			nomTable = ConstLiasse.C_NOM_TA_INITIAL;
			
			TCtrlGeneraux = new CtrlTaRepart();
			TCtrlGeneraux.setFIBBase(this.getFIBBase());
			TCtrlGeneraux.setFIBQuery(this.getFIBQuery());
			TCtrlGeneraux.setFFichierIni(ConstLiasse.C_FICHIER_INI_CTRL);
			TCtrlGeneraux.setFichierIni_ID(ConstLiasse.C_FICHIER_INI_IDBD);
//			setListeChampMaj(ConstLiasse.C_FICHIER_INI_CHAMPMAJ);
//			TCtrlGeneraux.setFichierListeTitre(ConstLiasse.C_FICHIER_LISTE_TITRE_BD);
			
			champIdTable=TCtrlGeneraux.getID_TABLE(nomTable);
			
			this.getFIBQuery().setRowId(champIdTable, true);
			this.setModeInsertionDirecte(true);
			update = new Update();
			update.setNomTable(nomTable);
			update.setIdChamps(champIdTable);
			listeMAJVue = new HashMap();
			
			//[id dans la vue][table etrangere][id champs étranger][contraintes (Obligatoire(1) ou Facultatif(0))]
		//	listeRelation.put("CODE_FAMILLE",new String[] {"ID_FAMILLE","TA_FAMILLE","ID_FAMILLE","0"});
		//	listeRelation.put("CODE_TVA",new String[] {"ID_TVA","TA_TVA","ID_TVA","0"});
		//	listeRelation.put("CODE_T_TVA",new String[] {"ID_T_TVA","TA_T_TVA","ID_T_TVA","0"});
		}
		catch (Exception e) {
			logger.error("Erreur : IbTaRepart", e);
		}
	}
	
	public void ctrlSaisieSpecifique(ValeurChamps valeurChamps,MessCtrlLgr ex) throws ExceptLgr {	
		try{
			switch (this.getFModeObjet().getMode()) {
			case C_MO_CONSULTATION: break;
			
			case C_MO_IMPORTATION:            				
			case C_MO_INSERTION:
			case C_MO_EDITION:
//				if(this.getFModeObjet().getMode()==ModeObjet.EnumModeObjet.C_MO_EDITION){
//					//si champ code article et valeur changée
//					if(valeurChamps.getChamps().equals(ConstLiasse.C_INITIAL_NUM_CPT )&& !valeurChamps.getValeur().equals(getChamp_Obj(ConstLiasse.C_FINAL_CLE)))
//						if (!recordModifiable(nomTable,getChamp_Obj(champIdTable))){
//							throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.ModificationIncoherente"),0,true,JOptionPane.ERROR); //$NON-NLS-1$
//						};
//				}
//				if(valeurChamps.getChamps().equals(ConstLiasse.C_INITIAL_NUM_CPT)) {
//					if (!LibChaine.emptyNumeric(valeurChamps.getValeur()))
//						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),ConstLiasse.C_FINAL_CLE,valeurChamps.getValeur()))
//							throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.FamilleIncoherente"),0,true,0);		        		
//					return;            		
//				};
//				if(valeurChamps.getChamps().equals(ConstLiasse.C_INITIAL_LIBELLE_CPT)) {
//					if (!LibChaine.emptyNumeric(valeurChamps.getValeur())){
//						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),ConstLiasse.C_FINAL_ID_DETAIL,valeurChamps.getValeur()))
//							throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.TvaIncoherente"),0,true,0);
//					}
//					return;            		
//				};
//				if(valeurChamps.getChamps().equals(ConstLiasse.C_INITIAL_MT_DEBIT)) {
//					if (!LibChaine.emptyNumeric(valeurChamps.getValeur())){
//						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),ConstLiasse.C_FINAL_VALEUR,valeurChamps.getValeur()))
//							throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.TypeTvaIncoherente"),0,true,0);
//					}
//					return;            		
//				};
//				if(valeurChamps.getChamps().equals(ConstLiasse.C_INITIAL_MT_CREDIT)) {
//					if (!LibChaine.emptyNumeric(valeurChamps.getValeur())){
//						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),ConstLiasse.C_FINAL_VALEUR,valeurChamps.getValeur()))
//							throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.TypeTvaIncoherente"),0,true,0);
//					}
//					return;            		
//				};
				
				TCtrlGeneraux.ctrlSaisie(ex);
//				if(valeurChamps.getChamps().equals(ConstLiasse.C_CODE_TVA)|| 
//						valeurChamps.getChamps().equals(ConstLiasse.C_CODE_T_TVA)||
//						valeurChamps.getChamps().equals(ConstLiasse.C_ID_T_TVA)||
//						valeurChamps.getChamps().equals(ConstLiasse.C_ID_TVA)) {
//				}
				
				break;
			case C_MO_SUPPRESSION: 
				TCtrlGeneraux.ctrlSaisie(ex);
				break;
			default:				
				break;
			}
		}catch (Exception e){
			logger.error("Erreur : ctrlSaisieSpecifique", e);
			throw new ExceptLgr();
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
	
	public void enregistrement(Initial initial) throws ExceptLgr {
		try{ 			
			if (!verifChamp(ConstLiasse.C_INITIAL_NUM_CPT,initial.getNumCpt(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_LIBELLE_CPT,initial.getLibelleCpt(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT,initial.getMtDebit(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT,initial.getMtCredit2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE,initial.getSolde(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_2,initial.getMtDebit2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT_2,initial.getMtCredit(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE_2,initial.getSolde2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_3,initial.getMtCredit3(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_3,initial.getMtDebit3(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE_3,initial.getSolde3(),null,null))throw new ExceptLgr();
					
			switch (this.getFModeObjet().getMode()) {
			case C_MO_INSERTION:{
				this.inserer();
				this.enregistrer();
			}
			break;
			case C_MO_EDITION:{
				//à la modification, on vérifie avant si cet enregistrement n'est pas lié à un autre, dans quel
				//cas, on demande une confirmation de modification avant.//TA_R_ADR (table de relation avec le tiers) 
				this.modification(initial);
			}
			break;
			default:throw new ExceptLgr(MessagesGestCom.getString("Message.Erreur.dataset.Edition"),0,true,0); //$NON-NLS-1$
			}
			commitLgr();
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : enregistrement", e);
			rollbackLgr();
			throw new ExceptLgr();
		}//fin du catch
	}
	
	//insertion d'un nouveau tiers dans la table
	public void insertion(Initial initial) throws ExceptLgr {
		try{
			this.fListeChampValue.clear();
			if (initial!=null){
				if (!LibChaine.emptyNumeric(initial.getNumCpt())&&!verifChamp(ConstLiasse.C_INITIAL_NUM_CPT,initial.getNumCpt(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getLibelleCpt())&&!verifChamp(ConstLiasse.C_INITIAL_LIBELLE_CPT,initial.getLibelleCpt(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtDebit())&&!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT,initial.getMtDebit(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtCredit())&&!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT,initial.getMtCredit(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getSolde())&&!verifChamp(ConstLiasse.C_INITIAL_SOLDE,initial.getSolde(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtDebit2())&&!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_2,initial.getMtDebit2(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtCredit2())&&!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT_2,initial.getMtCredit2(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getSolde2())&&!verifChamp(ConstLiasse.C_INITIAL_SOLDE_2,initial.getSolde2(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtDebit3())&&!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_3,initial.getMtDebit3(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getMtCredit3())&&!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT_3,initial.getMtCredit3(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(initial.getSolde3())&&!verifChamp(ConstLiasse.C_INITIAL_SOLDE_3,initial.getSolde3(),null,null))throw new ExceptLgr();
				
				this.setChamp_Obj_Query();
			}
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
			
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : insertion", e);
		}//fin du catch
	}
	
	//modfication d'un nouveau tiers dans la table
	public void modification(Initial initial) {
		try{
			
			this.modifier();
			//Vérifs simples
			if (!verifChamp(ConstLiasse.C_INITIAL_NUM_CPT,initial.getNumCpt(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_LIBELLE_CPT,initial.getLibelleCpt(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT,initial.getMtDebit(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT,initial.getMtCredit(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE,initial.getSolde(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_2,initial.getMtDebit2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT_2,initial.getMtCredit2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE_2,initial.getSolde2(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_DEBIT_3,initial.getMtDebit3(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_MT_CREDIT_3,initial.getMtCredit3(),null,null))throw new ExceptLgr();
			if (!verifChamp(ConstLiasse.C_INITIAL_SOLDE_3,initial.getSolde3(),null,null))throw new ExceptLgr();
				
			this.setChamp_Obj_Query();
			this.enregistrer();
			commitLgr();
		}
		catch(Exception e) {
			logger.error("Erreur : modfication", e);
			rollbackLgr();
		}
	}
	
	public void suppression() throws ExceptLgr {
		try {
//			//à la suppression, on vérifie avant si cet enregistrement n'est pas lié à un autre, dans quel
//			//cas, on empêche la suppression.//TA_R_ADR (table de relation avec le tiers)
			if (!recordModifiable(nomTable,getChamp_Obj(champIdTable))){
				throw new ExceptLgr(MessagesGestCom.getString("IbTaRepart.Message.SuppressionIncoherente"),0,true,JOptionPane.ERROR); //$NON-NLS-1$
			};
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
			
			this.supprimer();
			commitLgr(); 
		}
		catch(Exception e) {
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			logger.error("Erreur : suppression", e);
			rollbackLgr();
			throw new ExceptLgr();
		}
	}
	
	public static Initial infosInitial(String idInitial, Initial initial) {
		if(initial==null) initial = new Initial();
		StringBuffer requete = new StringBuffer("");
		try {
			requete.append("select ")
			.append(" * ").append(" from ").append(ConstLiasse.C_NOM_VU_INITIAL)
			.append(" where ").append(ConstLiasse.C_INITIAL_NUM_CPT).append(" = ").append(idInitial);
			PreparedStatement rqt = fIBBase.getJdbcConnection().prepareStatement(requete.toString());
			ResultSet res = rqt.executeQuery();
			if(res.next()){
				initial.setNumCpt(res.getString(ConstLiasse.C_INITIAL_NUM_CPT));
				initial.setLibelleCpt(res.getString(ConstLiasse.C_INITIAL_LIBELLE_CPT));
				initial.setMtDebit(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_DEBIT));
				initial.setMtCredit(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_CREDIT));
				initial.setSolde(res.getBigDecimal(ConstLiasse.C_INITIAL_SOLDE));
				initial.setMtDebit2(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_DEBIT_2));
				initial.setMtCredit2(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_CREDIT_2));
				initial.setSolde2(res.getBigDecimal(ConstLiasse.C_INITIAL_SOLDE_2));
				initial.setMtDebit3(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_DEBIT_3));
				initial.setMtCredit3(res.getBigDecimal(ConstLiasse.C_INITIAL_MT_CREDIT_3));
				initial.setSolde3(res.getBigDecimal(ConstLiasse.C_INITIAL_SOLDE_3));
	
			} 
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		
		return initial;
	}
	
	/**
	 * @throws Throwable the <code>Exception</code> raised by this method
	 */
	protected void finalize() throws Throwable { //destructor Destroy; override;
		super.finalize();
	}
}
