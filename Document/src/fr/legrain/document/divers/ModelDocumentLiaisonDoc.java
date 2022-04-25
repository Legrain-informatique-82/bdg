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
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
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
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.LibConversion;

public class ModelDocumentLiaisonDoc<DTO> extends ModelGeneralObjet{
	
	AbstractLgrDAO dao=null;
	//private LinkedList<IHMEnteteDocument> listeObjet = new LinkedList<IHMEnteteDocument>();
	public ModelDocumentLiaisonDoc(AbstractLgrDAO dao, Class<DTO> typeObjet ,String selectedType) {
		super(dao , typeObjet);
		this.selectedTypeSelection=selectedType;
		this.dao=dao;
	}

	TypeDoc typeDocPresent = TypeDoc.getInstance();
	EntityManager em =new TaFactureDAO().getEntityManager();
//	private Collection <IDocumentTiers> listeEntity = new LinkedList<IDocumentTiers>();
	private TaFactureDAO daoFacture=null;
	private TaAvoirDAO daoAvoir=null;
	private TaBonlivDAO daoBonliv=null;
	private TaBoncdeDAO daoBoncde=null;
	private TaApporteurDAO daoApporteur=null;
	private TaProformaDAO daoProforma=null;
	private TaDevisDAO daoDevis=null;
	private TaPrelevementDAO daoPrelevement=null;
	private TaAcompteDAO daoAcompte=null;
	private String selectedTypeSelection=null;
	
	public LinkedList<IHMEnteteDocument> remplirListe(Date dateDeb,Date dateFin,String tiers,String selectedType1,String selectedType2,boolean dejaLie,EntityManager em) {
		//this.selectedTypeSelection=selectedTypeSelection;
		this.setListeEntity(new LinkedList<IDocumentTiers>());
		IDocumentTiers document;
		getListeEntity().clear();
		getListeObjet().clear();
		Boolean accepte=true;
		List<IDocumentTiers> listeDocumentsFinal =	new ArrayList<IDocumentTiers>(0);
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_BON_LIVRAISON)){
			daoBonliv=new TaBonlivDAO(em);
			List<TaBonliv> listeDocuments =daoBonliv.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_BON_LIVRAISON);
				if(!dejaLie)
					accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_BON_COMMANDE)){
			daoBoncde=new TaBoncdeDAO(em);
			List<TaBoncde> listeDocuments =daoBoncde.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_BON_COMMANDE);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_FACTURE)){
			daoFacture=new TaFactureDAO(em);
			List<TaFacture> listeDocuments =daoFacture.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_FACTURE);
				if(!dejaLie)
					accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}	
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_ACOMPTE)){
			daoAcompte=new TaAcompteDAO(em);
			List<TaAcompte> listeDocuments =daoAcompte.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_ACOMPTE);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_DEVIS)){
			daoDevis=new TaDevisDAO(em);
			List<TaDevis> listeDocuments =daoDevis.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_DEVIS);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_AVOIR)){
			daoAvoir=new TaAvoirDAO(em);
			List<TaAvoir> listeDocuments =daoAvoir.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_AVOIR);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_PROFORMA)){
			daoProforma=new TaProformaDAO(em);
			List<TaProforma> listeDocuments =daoProforma.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_PROFORMA);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_PRELEVEMENT)){
			daoPrelevement=new TaPrelevementDAO(em);
			List<TaPrelevement> listeDocuments =daoPrelevement.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_PRELEVEMENT);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}
		if(selectedType1.equalsIgnoreCase(TypeDoc.TYPE_APPORTEUR)){
			daoApporteur=new TaApporteurDAO(em);
			List<TaApporteur> listeDocuments =daoApporteur.findByCodeTiersAndDate(tiers,dateDeb,dateFin);
			for (IDocumentTiers taDocument : listeDocuments) {
				//taDocument.setTypeDocument(TypeDoc.TYPE_APPORTEUR);
				if(!dejaLie)accepte=!rechercheRDocument(taDocument,selectedType2);
//				if(!accepte)break;
				if(accepte)listeDocumentsFinal.add((IDocumentTiers)taDocument);
				accepte=true;
			}
			setListeEntity(listeDocumentsFinal);
		}		
			if(getListeEntity()!=null && getListeEntity().size()>0)
				getListeObjet().addAll(this.remplirListe());
		
		return getListeObjet();
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<IHMEnteteDocument> remplirListe() {
		IHMEnteteDocument ihm=null;
		LinkedList<IHMEnteteDocument> listeObjet=new LinkedList<IHMEnteteDocument>();
		
		listeObjet.clear();
		Boolean saisieTTC=null;
		for (Object doc : getListeEntity()) {
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
			ihm.setTypeDocument(((IDocumentTiers)doc).getTypeDocument());
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
	  return false;	
	}

	public LinkedList<IHMEnteteDocument> remplirListe(IDocumentTiers masterEntity,EntityManager em) {
		this.setListeEntity(new LinkedList<IDocumentTiers>());
		IDocumentTiers document;
		getListeEntity().clear();
		getListeObjet().clear();
		if(masterEntity != null) {
			for(TaRDocument rDocument : masterEntity.getTaRDocuments()){
				
				if(rDocument.getTaFacture()!=null){	
					document=((IDocumentTiers)rDocument.getTaFacture());
					if(document!=masterEntity){
						document.getTypeDocument();
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaAvoir()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaAvoir());
					if(document!=masterEntity){
						document.getTypeDocument();
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaDevis()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaDevis());
					if(document!=masterEntity){
						document.getTypeDocument();
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaAcompte()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaAcompte());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaApporteur()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaApporteur());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaBoncde()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaBoncde());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaBonliv()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaBonliv());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaPrelevement()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaPrelevement());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
				if(rDocument.getTaProforma()!=null  ){	
					document=((IDocumentTiers)rDocument.getTaProforma());
					if(document!=masterEntity){
						getListeEntity().add(document);
					}
				}
			}
			if(getListeEntity()!=null && getListeEntity().size()>0)
				getListeObjet().addAll(this.remplirListe());
		}
		return getListeObjet();
	}
}
