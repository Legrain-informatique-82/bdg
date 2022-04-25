
package fr.legrain.gestCom.Module_Stocks.IB_TABLES;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Stocks.SWTStocks;
import fr.legrain.gestCom.Module_Stocks.Ctrl_TABLES.Ctrl_TA_STOCK;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
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
public class SWT_IB_TA_STOCK  extends IB_APPLICATION  implements PropertyChangeListener{
	static Logger logger = Logger.getLogger(SWT_IB_TA_STOCK.class.getName());
	public String requeteBase =Const.C_Debut_Requete+ Const.C_NOM_VU_STOCK;
	private QueryDataSet queryRecherche = new QueryDataSet();
	public SWT_IB_TA_STOCK() {
		super();
		try {
			this.fIBQuery.setQuery(new QueryDescriptor(fIBBase,requeteBase, true));
			this.fIBQuery.open();
			fIBBase.setAutoCommit(false);
			
			nomTable = Const.C_NOM_TA_STOCK;
			
			TCtrlGeneraux = new Ctrl_TA_STOCK();
			TCtrlGeneraux.setFIBBase(this.getFIBBase());
			TCtrlGeneraux.setFIBQuery(this.getFIBQuery());
			TCtrlGeneraux.setFFichierIni(Const.C_FICHIER_INI_CTRL);
			TCtrlGeneraux.setFichierIni_ID(Const.C_FICHIER_INI_IDBD);
			TCtrlGeneraux.setFichierListeTitre(Const.C_FICHIER_LISTE_TITRE_BD);
			setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
			
			champIdTable=TCtrlGeneraux.getID_TABLE(nomTable);
			
			this.getFIBQuery().setRowId(champIdTable, true);
			this.setModeInsertionDirecte(true);
			update = new Update();
			update.setNomTable(nomTable);
			update.setIdChamps(champIdTable);
			listeMAJVue = new HashMap();
			
//			//[id dans la vue][table etrangere][id champs étranger][contraintes (Obligatoire(1) ou Facultatif(0))]
			listeRelation.put("ID_ARTICLE_STOCK",null);
			listeRelation.put("CODE_ARTICLE",new String[] {"ID_ARTICLE_STOCK","TA_ARTICLE","ID_ARTICLE","1"});

		}
		catch (Exception e) {
			logger.error("Erreur : SWT_IB_TA_STOCK", e);
		}
	}
	
	
	
	
	public void ctrlSaisieSpecifique(ValeurChamps valeurChamps,MessCtrlLgr ex) throws ExceptLgr {	
		try{
			switch (this.getFModeObjet().getMode()) {
			case C_MO_CONSULTATION: break;
			
			case C_MO_IMPORTATION:            				
			case C_MO_INSERTION:
			case C_MO_EDITION:				
          	
				if(valeurChamps.getChamps().equals(Const.C_ID_ARTICLE_STOCK)) {
					if (!LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),Const.C_NOM_TA_ARTICLE,valeurChamps.getValeur()))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.ArticleIncoherent"),0,true,0);		        		
					return;            		
				};
				if(valeurChamps.getChamps().equals(Const.C_CODE_ARTICLE)) {
					if (!LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if (!verifCleEtrangereUtilisee(valeurChamps.getChamps(),Const.C_NOM_TA_ARTICLE,valeurChamps.getValeur()))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.ArticleIncoherent"),0,true,0);		        		
					return;            		
				};
				if (valeurChamps.getChamps().equals(Const.C_QTE1_STOCK)){
					if (LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if(LibChaine.emptyNumeric(getChamp_Obj(Const.C_QTE2_STOCK)))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.QtéIncoherente"),0,true,0);			
					return;
				}
				if (valeurChamps.getChamps().equals(Const.C_QTE2_STOCK)){
					if (LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if(LibChaine.emptyNumeric(getChamp_Obj(Const.C_QTE1_STOCK)))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.QtéIncoherente"),0,true,0);			
					return;
				}
				if(valeurChamps.getChamps().equals(Const.C_UN1_STOCK)) {
					if (!LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if (!verifCleEtrangereUtilisee(Const.C_CODE_UNITE,Const.C_NOM_TA_UNITE,valeurChamps.getValeur()))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.UniteIncoherente"),0,true,0);		        		
					return;            		
				};

				if(valeurChamps.getChamps().equals(Const.C_UN2_STOCK)) {
					if (!LibChaine.emptyNumeric(valeurChamps.getValeur()))
						if (!verifCleEtrangereUtilisee(Const.C_CODE_UNITE,Const.C_NOM_TA_UNITE,valeurChamps.getValeur()))
							throw new ExceptLgr(MessagesGestCom.getString("IB_TA_STOCK.Message.UniteIncoherente"),0,true,0);		        		
					return;            		
				};
				
				TCtrlGeneraux.ctrlSaisie(ex);
				break;
			case C_MO_SUPPRESSION:
				break;
			default:				
				break;
			}
		}catch (Exception e){
			logger.error("Erreur : ctrlSaisieSpecifique", e);
			throw new ExceptLgr(e.getMessage());
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
	
	
	public void enregistrement(SWTStocks stocks) throws ExceptLgr {
		try{ 			
			//if (!verifChamp(Const.C_ID_STOCK,stocks.getID_STOCK(),null,null))throw new ExceptLgr();			
			if (!verifChamp(Const.C_CODE_ARTICLE,stocks.getCodeArticle(),null,null))throw new ExceptLgr();
			if (LibChaine.emptyNumeric(stocks.getCodeArticle()))
				if (!verifChamp(Const.C_ID_ARTICLE_STOCK,stocks.getIdArticleStock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_DATE_STOCK,stocks.getDateStock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_LIBELLE_STOCK,stocks.getLibelleStock(),null,null))throw new ExceptLgr();			
			if (!verifChamp(Const.C_MOUV_STOCK,stocks.getMouvStock(),null,null))throw new ExceptLgr();			
			if (!verifChamp(Const.C_QTE1_STOCK,stocks.getQte1Stock(),null,null))throw new ExceptLgr();			
			if (!verifChamp(Const.C_QTE2_STOCK,stocks.getQte2Stock(),null,null))throw new ExceptLgr();			
			
			
			switch (this.getFModeObjet().getMode()) {
			case C_MO_INSERTION:{
				this.inserer();
				this.enregistrer();
			}
			break;
			case C_MO_EDITION:{
				//à la modification, on vérifie avant si cet enregistrement n'est pas lié à un autre, dans quel
				//cas, on demande une confirmation de modification avant.//TA_R_ADR (table de relation avec le tiers) 
				this.modfication(stocks);
			}
			break;
			default:throw new ExceptLgr(MessagesGestCom.getString("Message.Erreur.dataset.Edition"),0,true,0); //$NON-NLS-1$
			}
			commitLgr();
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : enregistrement", e);
			rollbackLgr();
			throw new ExceptLgr(e.getMessage());
		}//fin du catch
	}
	
	//insertion d'un nouveau prix dans la table
	public void insertion(SWTStocks stocks) throws ExceptLgr {
		try{
			this.fListeChampValue.clear();
			if (stocks!=null){
				if (!LibChaine.emptyNumeric(stocks.getIdArticleStock())&& verifChamp(Const.C_ID_ARTICLE_STOCK,stocks.getIdArticleStock(),null,null)){
					stocks.setCodeArticle(selectCleEtrangere(Const.C_NOM_TA_ARTICLE,Const.C_CODE_ARTICLE,Const.C_ID_ARTICLE,LibConversion.integerToString(stocks.getIdArticleStock())));
				}
				if (!LibChaine.emptyNumeric(stocks.getCodeArticle()) && !verifChamp(Const.C_CODE_ARTICLE,stocks.getCodeArticle(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(stocks.getLibelleStock()) && !verifChamp(Const.C_LIBELLE_STOCK,stocks.getLibelleStock(),null,null))throw new ExceptLgr();
				if (!LibChaine.emptyNumeric(stocks.getMouvStock()) && !verifChamp(Const.C_MOUV_STOCK,stocks.getMouvStock(),null,null))throw new ExceptLgr();							
				//if (!LibChaine.emptyNumeric(stocks.getDATE_STOCK()) && !verifChamp(Const.C_DATE_STOCK,stocks.getDATE_STOCK(),null,null))throw new ExceptLgr();							
				if (!LibChaine.emptyNumeric(stocks.getQte1Stock()) && !verifChamp(Const.C_QTE1_STOCK,stocks.getQte1Stock(),null,null))throw new ExceptLgr();							
				if (!LibChaine.emptyNumeric(stocks.getQte2Stock()) && !verifChamp(Const.C_QTE2_STOCK,stocks.getQte2Stock(),null,null))throw new ExceptLgr();							
				this.setChamp_Obj_Query();				
			}
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
		}//fin du try
		catch(Exception e) {
			logger.error("Erreur : insertion", e);
		}//fin du catch
	}
	
	//modfication d'un nouveau tiers dans la table
	public void modfication(SWTStocks stocks) {
		try{
			this.modifier();
			//Vérifs simples
			if (!verifChamp(Const.C_CODE_ARTICLE,stocks.getCodeArticle(),null,null))throw new ExceptLgr();
			if (LibChaine.emptyNumeric(stocks.getCodeArticle()))
				if (!verifChamp(Const.C_ID_ARTICLE_STOCK,stocks.getIdArticleStock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_LIBELLE_STOCK,stocks.getLibelleStock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_MOUV_STOCK,stocks.getMouvStock(),null,null))throw new ExceptLgr();			
			if (!verifChamp(Const.C_DATE_STOCK,stocks.getDateStock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_QTE2_STOCK,stocks.getQte1Stock(),null,null))throw new ExceptLgr();
			if (!verifChamp(Const.C_QTE2_STOCK,stocks.getQte2Stock(),null,null))throw new ExceptLgr();
			
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
			//if (!recordModifiable(nomTable,getChamp_Obj(champIdTable))){
				//throw new ExceptLgr(MessagesGestCom.getString("IB_TA_PRIX.Message.Supprimer"),0,true,JOptionPane.ERROR); //$NON-NLS-1$
			//};
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
			ctrlSaisieSpecifique(Const.C_ID_STOCK,getChamp_Obj(Const.C_ID_STOCK),null);
			this.supprimer();
			commitLgr(); 
		}
		catch(Exception e) {
			this.getFModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			logger.error("Erreur : suppression", e);
			rollbackLgr();
			throw new ExceptLgr(e.getMessage());
		}
	}
	

	
	/**
	 * @throws Throwable the <code>Exception</code> raised by this method
	 */
	protected void finalize() throws Throwable { //destructor Destroy; override;
			super.finalize();
	}	
	
	/**
	 * mise à jour de la liste à partir de l'objet model sur l'ensemble d'un enregistrement
	 * Elle ne tient pas compte du mode
	 * @throws Exception
	 */
	public void setChamp_Model_Obj(SWTStocks swtStocks)throws Exception{
		try{			
			fListeChampValue.clear();			
			if(swtStocks.getCodeArticle()!=null){
				fListeChampValue.put(Const.C_CODE_ARTICLE,swtStocks.getCodeArticle());
			}
			if(swtStocks.getDateStock()!=null){
				fListeChampValue.put(Const.C_DATE_STOCK,swtStocks.getDateStock());
			}
			if(swtStocks.getIdArticleStock()!=null){
				fListeChampValue.put(Const.C_ID_ARTICLE_STOCK,swtStocks.getIdArticleStock());
			}
			if(swtStocks.getIdStock()!=null){
				fListeChampValue.put(Const.C_ID_STOCK,swtStocks.getIdStock());
			}
			if(swtStocks.getLibelleStock()!=null){
				fListeChampValue.put(Const.C_LIBELLE_STOCK,swtStocks.getLibelleStock());
			}
			if(swtStocks.getMouvStock()!=null){
				fListeChampValue.put(Const.C_MOUV_STOCK,swtStocks.getMouvStock());
			}
			if(swtStocks.getQte1Stock()!=null){
				fListeChampValue.put(Const.C_QTE1_STOCK,swtStocks.getQte1Stock());
			}
			if(swtStocks.getQte2Stock()!=null){
				fListeChampValue.put(Const.C_QTE2_STOCK,swtStocks.getQte2Stock());
			}


		}
		catch (Exception e){
			logger.error("Erreur : setChamp_Model_Obj", e);
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * mise à jour de la liste à partir de l'objet model sur l'ensemble d'un enregistrement
	 * Elle ne tient pas compte du mode
	 * @throws Exception
	 */
	public void setInterface(SWTStocks swtStocks)throws Exception{
		try{			
			if(fListeChampValue!=null && swtStocks!=null){
				if(fListeChampValue.get(Const.C_CODE_ARTICLE)!=null)
					swtStocks.setCodeArticle((String)LibConversion.RenvoieTypeModel(Variant.STRING, fListeChampValue.get(Const.C_CODE_ARTICLE)));

				if(fListeChampValue.get(Const.C_DATE_STOCK)!=null)
					swtStocks.setDateStock((Date)LibConversion.RenvoieTypeModel(Variant.DATE,fListeChampValue.get(Const.C_DATE_STOCK)));

				if(fListeChampValue.get(Const.C_ID_STOCK)!=null)
					swtStocks.setIdStock((Integer)LibConversion.RenvoieTypeModel(Variant.INT,fListeChampValue.get(Const.C_ID_STOCK)));

				if(fListeChampValue.get(Const.C_ID_ARTICLE_STOCK)!=null)
					swtStocks.setIdArticleStock((Integer)LibConversion.RenvoieTypeModel(Variant.INT,fListeChampValue.get(Const.C_ID_ARTICLE_STOCK)));

				if(fListeChampValue.get(Const.C_LIBELLE_STOCK)!=null)
					swtStocks.setLibelleStock((String)LibConversion.RenvoieTypeModel(Variant.STRING,fListeChampValue.get(Const.C_LIBELLE_STOCK)));

				if(fListeChampValue.get(Const.C_MOUV_STOCK)!=null)
					swtStocks.setMouvStock((String)LibConversion.RenvoieTypeModel(Variant.STRING,fListeChampValue.get(Const.C_MOUV_STOCK)));

				if(fListeChampValue.get(Const.C_QTE1_STOCK)!=null)
					swtStocks.setQte1Stock((BigDecimal)LibConversion.RenvoieTypeModel(Variant.BIGDECIMAL,fListeChampValue.get(Const.C_QTE1_STOCK)));

				if(fListeChampValue.get(Const.C_QTE2_STOCK)!=null)
					swtStocks.setQte2Stock((BigDecimal)LibConversion.RenvoieTypeModel(Variant.BIGDECIMAL,fListeChampValue.get(Const.C_QTE2_STOCK)));

			}
		}
		catch (Exception e){
			logger.error("Erreur : setInterface", e);
			throw new Exception(e.getMessage());
		}
	}




	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Raccord de méthode auto-généré
		
	}




	public QueryDataSet getQueryRecherche() {
		return queryRecherche;
	}




	public void setQueryRecherche(QueryDataSet queryRecherche) {
		this.queryRecherche = queryRecherche;
	}
	

}
