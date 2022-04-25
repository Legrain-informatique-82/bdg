package fr.legrain.document.divers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class ModelRecupDocumentCreationDoc<DTO> extends ModelGeneralObjet{
	
	AbstractLgrDAO dao=null;
	//private LinkedList<IHMEnteteDocument> listeObjet = new LinkedList<IHMEnteteDocument>();
	public ModelRecupDocumentCreationDoc(AbstractLgrDAO dao, Class<DTO> typeObjet ) {
		super(dao , typeObjet);
		this.dao=dao;
	}

	TypeDoc typeDocPresent = TypeDoc.getInstance();
	EntityManager em =new TaFactureDAO().getEntityManager();
//	private Collection <IDocumentTiers> listeEntity = new LinkedList<IDocumentTiers>();
	private TaTiersDAO daoTiers=new TaTiersDAO(em);
	private TaFactureDAO daoFacture=null;
	private TaAvoirDAO daoAvoir=null;
	private TaBonlivDAO daoBonliv=null;
	private TaBoncdeDAO daoBoncde=null;
	private TaApporteurDAO daoApporteur=null;
	private TaProformaDAO daoProforma=null;
	private TaDevisDAO daoDevis=null;
	private TaPrelevementDAO daoPrelevement=null;
	private TaAcompteDAO daoAcompte=null;
	private TaAvisEcheanceDAO daoAvisEcheance=null;
	private String selectedTypeSelection=null;

	public LinkedList<IDocumentTiers> remplirListe(Date dateDeb,Date dateFin,List<TaTiers> listeTiers,String selectedTypeSelection,
			String selectedTypeCreation) {
		LinkedList<IDocumentTiers> list=new LinkedList<IDocumentTiers>();
		LinkedList<IDocumentTiers> listTemp=new LinkedList<IDocumentTiers>();
		for (TaTiers taTiers : listeTiers) {
			listTemp=remplirListe(dateDeb,dateFin,listeTiers,selectedTypeSelection,selectedTypeCreation);
			for (IDocumentTiers ihmEnteteDocument : listTemp) {
				list.add(ihmEnteteDocument);
			}
		}
		return list;
	}
	public LinkedList<IDocumentTiers> remplirListe(Date dateDeb,Date dateFin,String codeTiers,String selectedTypeSelection,
			String selectedTypeCreation,IDocumentTiers doc) {
		this.selectedTypeSelection=selectedTypeSelection;
		this.setListeEntity(new LinkedList<IDocumentTiers>());
		IDocumentTiers document;
		getListeEntity().clear();
		getListeObjet().clear();
		Boolean accepte=true;
		LinkedList<IDocumentTiers> listeDocumentsFinal =	new LinkedList<IDocumentTiers>();
		
		
		//if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_BON_LIVRAISON)){
			daoBonliv=new TaBonlivDAO();
			daoBonliv.setEm(daoBonliv.getEntityManager());
			List<IDocumentTiers> listeDocuments =daoBonliv.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
					selectedTypeCreation,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				listeDocumentsFinal.add((IDocumentTiers)taDocument);
			}
			setListeEntity(listeDocumentsFinal);
		//}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_FACTURE)){
//			daoFacture=new TaFactureDAO();
//			daoFacture.setEm(daoFacture.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoFacture.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}	
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_ACOMPTE)){
//			daoAcompte=new TaAcompteDAO();
//			daoAcompte.setEm(daoAcompte.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoAcompte.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_DEVIS)){
//			daoDevis=new TaDevisDAO();
//			daoDevis.setEm(daoDevis.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoDevis.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_AVOIR)){
//			daoAvoir=new TaAvoirDAO();
//			daoAvoir.setEm(daoAvoir.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoAvoir.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_PROFORMA)){
//			daoProforma=new TaProformaDAO();
//			daoProforma.setEm(daoProforma.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoProforma.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_PRELEVEMENT)){
//			daoPrelevement=new TaPrelevementDAO();
//			daoPrelevement.setEm(daoPrelevement.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoPrelevement.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}
//		if(this.selectedTypeSelection.equalsIgnoreCase(TypeDoc.TYPE_APPORTEUR)){
//			daoApporteur=new TaApporteurDAO();
//			daoApporteur.setEm(daoApporteur.getEntityManager());
//			List<IDocumentTiers> listeDocuments =daoApporteur.selectDocNonTransformeRequete(doc,codeTiers,selectedTypeSelection,
//					selectedTypeCreation,dateDeb,dateFin);
//			for (IDocumentTiers taDocument : listeDocuments) {
//				listeDocumentsFinal.add((IDocumentTiers)taDocument);
//			}
//			setListeEntity(listeDocumentsFinal);
//		}		
			if(getListeEntity()!=null && getListeEntity().size()>0 )
				getListeObjet().addAll(this.remplirListe());
		
		return listeDocumentsFinal;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<IHMEnteteDocument> remplirListe() {
		IHMEnteteDocument ihm=null;
		LinkedList<IHMEnteteDocument> listeObjet=new LinkedList<IHMEnteteDocument>();
		
		listeObjet.clear();
		Boolean saisieTTC=null;
		for (Object doc : getListeEntity()) {
//			"codeDocument","dateDocument","dateLivDocument","codeTiers","nomTiers","netTtcCalc",
			//"txRemHtDocument","txRemTtcDocument","accepte"}
			if(saisieTTC==null)saisieTTC=LibConversion.intToBoolean(((IDocumentTiers)doc).getTtc());
			ihm=new IHMEnteteDocument();
			ihm.setIdTiers(((IDocumentTiers)doc).getTaTiers().getIdTiers());
			ihm.setIdDocument(((IDocumentTiers)doc).getIdDocument());
			ihm.setCodeDocument(((IDocumentTiers)doc).getCodeDocument());
			ihm.setDateDocument(((IDocumentTiers)doc).getDateDocument());
			ihm.setDateLivDocument(((IDocumentTiers)doc).getDateLivDocument());
			ihm.setCodeTiers(((IDocumentTiers)doc).getTaTiers().getCodeTiers());
			ihm.setNomTiers(((IDocumentTiers)doc).getTaTiers().getNomTiers());
			ihm.setNetTtcCalc(((IDocumentTiers)doc).getNetTtcCalc());
			ihm.setTxRemHtDocument(((IDocumentTiers)doc).getTxRemHtDocument());
			ihm.setTxRemTtcDocument(((IDocumentTiers)doc).getTxRemTtcDocument());
			if((((IDocumentTiers)doc).getTxRemHtDocument().compareTo(BigDecimal.valueOf(0))!=0
					||((IDocumentTiers)doc).getTxRemTtcDocument().compareTo(BigDecimal.valueOf(0))!=0)||
					(saisieTTC.compareTo(LibConversion.intToBoolean(((IDocumentTiers)doc).getTtc()))==0))
			ihm.setAccepte(false);
			else ihm.setAccepte(true);
			ihm.setTtc(LibConversion.intToBoolean(((IDocumentTiers)doc).getTtc()));
			listeObjet.add(ihm);
			ihm=null;
		}
		return listeObjet;
	}

	public Boolean rechercheRDocument(IDocumentTiers doc, String selectedTypeDoc){
		if(selectedTypeDoc==TypeDoc.TYPE_ACOMPTE)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaAcompte()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_APPORTEUR)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaApporteur()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_AVOIR)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaAvoir()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_BON_COMMANDE)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaBoncde()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_BON_LIVRAISON)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaBonliv()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_DEVIS)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaDevis()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_FACTURE)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaFacture()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_PRELEVEMENT)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaPrelevement()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_PROFORMA)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaProforma()!=null){
					return true;
					}
			}
		if(selectedTypeDoc==TypeDoc.TYPE_AVIS_ECHEANCE)
			for (TaRDocument relation : doc.getTaRDocuments()) {
				if(relation.getTaAvisEcheance()!=null){
					return true;
					}
			}		
	  return false;	
	}
}
