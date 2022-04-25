package fr.legrain.saisiecaisse.divers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.swt.widgets.Shell;

import com.sun.org.apache.bcel.internal.generic.LSTORE;
import com.sun.org.apache.bcel.internal.generic.NEW;

import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.saisieCaisse.dao.TaAchatTTC;
import fr.legrain.saisieCaisse.dao.TaDepot;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaOperation;
import fr.legrain.saisieCaisse.dao.TaSumDepot;
import fr.legrain.saisieCaisse.dao.TaSumOperation;
import fr.legrain.saisiecaisse.saisieCaissePlugin;


public class Impression {
	//Shell shellParent = null;
	static Logger logger = Logger.getLogger(Impression.class.getName());
	
	//private List<TaSumOperation>  listeOperationsMois = new ArrayList<TaSumOperation>();
	private List<TaSumOperation>  listeOperationsMois = new ArrayList<TaSumOperation>();
	
	private List<TaAchatTTC> listeAchat = new ArrayList<TaAchatTTC>() ;
	private List<Object> periode = new LinkedList<Object>() ;
	private List<TaDepot> listeDepot = new ArrayList<TaDepot>();
	private List<TaSumDepot> listeReportFondCaisse = new ArrayList<TaSumDepot>();
	
	private List<TaOperation> listeOperationJour = new ArrayList<TaOperation>();
	private List<TaEtablissement> listTaEtablissement = new ArrayList<TaEtablissement>();
	
	private List<TaSumDepot> listeReportJournaux = new ArrayList<TaSumDepot>();
	
	private List<TaSumDepot> listeFinJournaux = new ArrayList<TaSumDepot>();
	
	//private	Map<String, Float> mapFinJournaux = new HashMap<String, Float>();
	private	Map<String, TaSumDepot> mapFinJournaux = new HashMap<String, TaSumDepot>();
	
	private ConstEdition constEdition;
	private String codeTypeCaisse = null;
	
	private LinkedHashMap<String,Float> map1 = new LinkedHashMap<String, Float>();
	
	private LinkedHashMap<String,Float> mapDepotMontant = new LinkedHashMap<String, Float>();

//	public Impression(Shell s){
//		setShellParent(s);	
//	}
	public Impression(){	
		
	}
//	public Impression(List<TaSumOperation> listeOperationsMois,ConstEdition constEdition,List<Object> periode,
//					  List<TaDepot> listeDepot,List<TaAchatTTC> listeAchat,List<TaSumDepot> listeReportFondCaisse,
//					  List<TaEtablissement> ListTaEtablissement) {
//		super();
//		this.listeOperationsMois = listeOperationsMois;
//		this.constEdition = constEdition;
//		this.periode = periode;
//		this.listeDepot = listeDepot;
//		this.listeAchat = listeAchat;
//		
//	}

	
//	public Impression(List<TaOperation> listeOperationJour,ConstEdition constEdition,List<Object> periode,
//			List<TaDepot> listeDepot,List<TaAchatTTC> listeAchat,List<TaEtablissement> listTaEtablissement,
//			List<TaSumDepot> listeReportJournaux){
//		this.periode = periode;
//		this.listeOperationJour = listeOperationJour;
//		this.listeDepot = listeDepot;
//		this.listeAchat = listeAchat;
//		this.listTaEtablissement = listTaEtablissement;
//		this.listeReportJournaux = listeReportJournaux;
//	}

	public void cleanListReport(){
		logger.debug("cleanListReport");
		ImprimeObjet.list2.clear();
		ImprimeObjet.list4.clear();
		map1.clear();
		ImprimeObjet.LinkedList1.clear();
		ImprimeObjet.LinkedList2.clear();
		
		ImprimeObjet.mapEdition.clear();
		ImprimeObjet.mapEditionComplexe.clear();
		ImprimeObjet.mapEditionTabel.clear();
		listeFinJournaux.clear();
		mapFinJournaux.clear();
		mapDepotMontant.clear();
	}
	
	public void imprimerCaisseJour(String fileEditionDefaut ){
		
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,saisieCaissePlugin.PLUGIN_ID);
		
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		
		cleanListReport();
		
		
		
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_DATE_JOUR, periode);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_OPERATION_JOUR, listeOperationJour);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_ETABLISSEMENT_JOUR, listTaEtablissement);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_REPORT_JOUR, listeReportJournaux);
		
		for (int i = 0; i < listeDepot.size(); i++) {
			TaDepot taDepot = listeDepot.get(i);
			String codeTpaiement = taDepot.getTaTPaiement().getCodeTPaiement();
			float montantDepot = taDepot.getMontantDepot().floatValue();
			if(!mapDepotMontant.containsKey(codeTpaiement)){
				mapDepotMontant.put(codeTpaiement, montantDepot);
			}else{
				float value = mapDepotMontant.get(codeTpaiement)+montantDepot;
				mapDepotMontant.put(codeTpaiement, value);
			}
		}
		
		//listeFinJournaux.addAll(listeReportJournaux);
		
		for (int i = 0; i < listeReportJournaux.size(); i++) {
			TaSumDepot taSumDepot = listeReportJournaux.get(i);
			String codeTypePaiement = taSumDepot.getCodeTPaiement();
			String liblTPaiement = taSumDepot.getLiblTPaiement();
			BigDecimal montantOperation = taSumDepot.getMontantOperation();
			TaSumDepot copyTaSumDepot = new TaSumDepot();
			copyTaSumDepot.setCodeTPaiement(codeTypePaiement);
			copyTaSumDepot.setLiblTPaiement(liblTPaiement);
			copyTaSumDepot.setMontantOperation(montantOperation);
			//float montantOperation = taSumDepot.getMontantOperation().floatValue();
			//mapFinJournaux.put(codeTypePaiement, montantOperation);
			mapFinJournaux.put(codeTypePaiement, copyTaSumDepot);
		}
		
		for (int i = 0; i < listeOperationJour.size(); i++) {
			TaOperation taOperation = (TaOperation) listeOperationJour.get(i);
			String codeTypePaiement = taOperation.getTaTPaiement().getCodeTPaiement();
			String codeTypeOperation = taOperation.getTaTOperation().getCodeTOperation();
			String libelleTypeOperation = taOperation.getTaTOperation().getLiblTOperation();
			float valueOperation = taOperation.getMontantOperation().floatValue();
			if(!mapFinJournaux.containsKey(codeTypePaiement)){
				TaSumDepot taSumDepot = new TaSumDepot();
				taSumDepot.setCodeTPaiement(codeTypePaiement);
				taSumDepot.setLiblTPaiement(libelleTypeOperation);
				taSumDepot.setMontantOperation(BigDecimal.valueOf(valueOperation));
				//listeFinJournaux.
				mapFinJournaux.put(codeTypePaiement, taSumDepot);
				
				
			}else{
				TaSumDepot taSumDepot = mapFinJournaux.get(codeTypePaiement);
				float partValue =  taSumDepot.getMontantOperation().floatValue()+valueOperation;
				taSumDepot.setMontantOperation(BigDecimal.valueOf(partValue));
				mapFinJournaux.put(codeTypePaiement, taSumDepot);
			}
			
			if(!ImprimeObjet.mapEditionTabel.containsKey(codeTypeOperation)){
				List<Float> listValueChiffre = new LinkedList<Float>();
				listValueChiffre.add(valueOperation);
				ImprimeObjet.mapEditionTabel.put(codeTypeOperation, listValueChiffre);
			}else{
				Float valueExist = (Float)ImprimeObjet.mapEditionTabel.get(codeTypeOperation).get(0);
				float partSum = valueExist+valueOperation;
				List<Float> listValueChiffre = new LinkedList<Float>();
				listValueChiffre =  (List<Float>) ImprimeObjet.mapEditionTabel.get(codeTypeOperation);
				//listValueChiffre.add(0, partSum);
				listValueChiffre.clear();
				listValueChiffre.add(partSum);
				ImprimeObjet.mapEditionTabel.put(codeTypeOperation, listValueChiffre);
			}
			
		}
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_ACHAT_JOUR, listeAchat);
					
		
		for (String keyMapFinJournaux : mapFinJournaux.keySet()) {
			TaSumDepot taSumDepot = mapFinJournaux.get(keyMapFinJournaux);
			float montantOperation = taSumDepot.getMontantOperation().floatValue();
			String codeTPaiement = taSumDepot.getCodeTPaiement();
			if(mapDepotMontant.containsKey(codeTPaiement)){
				montantOperation -= mapDepotMontant.get(codeTPaiement);
				taSumDepot.setMontantOperation(BigDecimal.valueOf(montantOperation));
			}
			listeFinJournaux.add(taSumDepot);
		}
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_FIN_JOUR, listeFinJournaux);
		ImprimeObjet.mapEditionComplexe.put(ConstEditionSaisieCaisse.SAISIECAISSE_CHIFFRE_JOUR, ImprimeObjet.mapEditionTabel);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_DEPOT_JOUR, listeDepot);
		
		
		
//		for (int i = 0; i < listeDepot.size(); i++) {
//			TaDepot taDepot = listeDepot.get(i);
//			ImprimeObjet.list2.add(taDepot);
//		}
//		
//		for (int i = 0; i < listeAchat.size(); i++) {
//			TaAchatTTC taAchatTTC = listeAchat.get(i);
//			ImprimeObjet.list3.add(taAchatTTC);
//		}
		
		AffichageEdition affichageEdition = new AffichageEdition();
		
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut,ConstEditionSaisieCaisse.TITLE_EDITION_SAISIECAISSE,
				ConstEdition.FORMAT_PDF,false);
		
		
		
	}
	public void setAppcontextReport(){
		ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY,
						EditionAppContext.APP_CONTEXT_NAME);
	}
	public void imprimer(String fileEditionDefaut ){
		
		//ViewerPlugin.getDefault().getPluginPreferences().setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
		setAppcontextReport();
		String reportFileLocationDefaut = ConstEdition.pathFichierEditionsSpecifiques(fileEditionDefaut,saisieCaissePlugin.PLUGIN_ID);
		if (reportFileLocationDefaut == null){
			reportFileLocationDefaut = fileEditionDefaut;
		}
		
		cleanListReport();
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_DATE_MONTH, periode);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_OPERATION_MONTH, listeOperationsMois);
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_ETABLISSEMENT_JOUR, listTaEtablissement);
		

		
		/**
		 * il faut le corriger  
		 */
		for (int i = 0; i < listeReportFondCaisse.size(); i++) {
			TaSumDepot taSumDepot = listeReportFondCaisse.get(i);
			codeTypeCaisse = taSumDepot.getCodeTPaiement();
			
			ImprimeObjet.LinkedList1.add(ConstEditionSaisieCaisse.SOLDE_DEPART);
			if(taSumDepot.getMontantOperation()!=null){
				ImprimeObjet.LinkedList2.add(taSumDepot.getMontantOperation().floatValue());
			}else{
				ImprimeObjet.LinkedList2.add(new Float(0));
			}
			
			ImprimeObjet.list4.add(taSumDepot);
		}
		
		
		for (int i = 0; i < listeOperationsMois.size(); i++) {
			TaSumOperation taEtatSaisieCaisseSynthese =  listeOperationsMois.get(i);
			String codeTypeOperation = taEtatSaisieCaisseSynthese.getCodeTOperation();
			float valueOperation = taEtatSaisieCaisseSynthese.getMontantOperation().floatValue();
			
			if(taEtatSaisieCaisseSynthese.getCodeTPaiement().equals(codeTypeCaisse)){
				float montantOperation = taEtatSaisieCaisseSynthese.getMontantOperation().floatValue();
				
				if(!map1.containsKey(codeTypeOperation)){
					map1.put(codeTypeOperation, montantOperation);
				}else{
					float partSum = map1.get(codeTypeOperation)+montantOperation;
					map1.put(codeTypeOperation, partSum);
				}
			}
			ImprimeObjet.l.add(taEtatSaisieCaisseSynthese);
			
			
			if(!ImprimeObjet.mapEditionTabel.containsKey(codeTypeOperation)){
				List<Float> listValueChiffre = new LinkedList<Float>();
				listValueChiffre.add(valueOperation);
				ImprimeObjet.mapEditionTabel.put(codeTypeOperation, listValueChiffre);
			}else{
				Float valueExist = (Float)ImprimeObjet.mapEditionTabel.get(codeTypeOperation).get(0);
				float partSum = valueExist+valueOperation;
				List<Float> listValueChiffre = new LinkedList<Float>();
				listValueChiffre =  (List<Float>) ImprimeObjet.mapEditionTabel.get(codeTypeOperation);
				//listValueChiffre.add(0, partSum);
				listValueChiffre.clear();
				listValueChiffre.add(partSum);
				ImprimeObjet.mapEditionTabel.put(codeTypeOperation, listValueChiffre);
			}
			
		}
		ImprimeObjet.mapEditionComplexe.put(ConstEditionSaisieCaisse.SAISIECAISSE_RECAP_MONTH, ImprimeObjet.mapEditionTabel);
		for (String keyMap : map1.keySet()) {
			ImprimeObjet.LinkedList1.add(keyMap);
			ImprimeObjet.LinkedList2.add(map1.get(keyMap));
		}
		

		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_ACHAT_MONTH, listeAchat);
		float sumMontantAchat = 0;
		for (int i = 0; i < listeAchat.size(); i++) {
			TaAchatTTC taAchatTTC = listeAchat.get(i);
			float montantAchat = taAchatTTC.getMontant().floatValue();
			sumMontantAchat+=montantAchat;
		}
		
		ImprimeObjet.LinkedList1.add(ConstEditionSaisieCaisse.ACHATS_TTC);
		ImprimeObjet.LinkedList2.add(0-sumMontantAchat);
		
		
		ImprimeObjet.mapEdition.put(ConstEditionSaisieCaisse.SAISIECAISSE_DEPOT_MONTH, listeDepot);
		float sumMontantDepot = 0;
		for (int i = 0; i < listeDepot.size(); i++) {
			TaDepot taDepot = listeDepot.get(i);
			String codeTypePaie = taDepot.getTaTPaiement().getCodeTPaiement();
			if(codeTypePaie.equals(codeTypeCaisse)){
				float montantDepot = taDepot.getMontantDepot().floatValue();
				sumMontantDepot += montantDepot;
				
			}
			//ImprimeObjet.list2.add(taDepot);
		}
		ImprimeObjet.LinkedList1.add(ConstEditionSaisieCaisse.DEPOT_BANQUE);
		ImprimeObjet.LinkedList2.add(0-sumMontantDepot);
		
		float sumSoldeFin = 0;
		for (int i = 0; i < ImprimeObjet.LinkedList2.size(); i++) {
			float value = ImprimeObjet.LinkedList2.get(i);
			sumSoldeFin += value;
		}
		ImprimeObjet.LinkedList1.add(ConstEditionSaisieCaisse.SOLDE_FIN);
		ImprimeObjet.LinkedList2.add(sumSoldeFin);
		
		
		AffichageEdition affichageEdition = new AffichageEdition();
		
		affichageEdition.showEditionDynamiqueDefaut(reportFileLocationDefaut,ConstEditionSaisieCaisse.TITLE_EDITION_SAISIECAISSE,
				ConstEdition.FORMAT_PDF,false);
		
	}

//	public Shell getShellParent() {
//		return shellParent;
//	}
//
//	public void setShellParent(Shell shellParent) {
//		this.shellParent = shellParent;
//	}
	public void setListeOperationsMois(List<TaSumOperation> listeOperationsMois) {
		this.listeOperationsMois = listeOperationsMois;
	}
	public void setListeAchat(List<TaAchatTTC> listeAchat) {
		this.listeAchat = listeAchat;
	}
	public void setListeDepot(List<TaDepot> listeDepot) {
		this.listeDepot = listeDepot;
	}
	public void setListeReportFondCaisse(List<TaSumDepot> listeReportFondCaisse) {
		this.listeReportFondCaisse = listeReportFondCaisse;
	}
	public void setListeOperationJour(List<TaOperation> listeOperationJour) {
		this.listeOperationJour = listeOperationJour;
	}
	public void setListTaEtablissement(List<TaEtablissement> listTaEtablissement) {
		this.listTaEtablissement = listTaEtablissement;
	}
	public void setListeReportJournaux(List<TaSumDepot> listeReportJournaux) {
		this.listeReportJournaux = listeReportJournaux;
	}
	public void setListeFinJournaux(List<TaSumDepot> listeFinJournaux) {
		this.listeFinJournaux = listeFinJournaux;
	}
	public void setConstEdition(ConstEdition constEdition) {
		this.constEdition = constEdition;
	}
	public void setPeriode(List<Object> periode) {
		this.periode = periode;
	}

}
