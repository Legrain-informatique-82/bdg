package fr.legrain.document.divers;

import java.util.LinkedList;

import javax.persistence.EntityManager;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IDocumentTiersComplet;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.lib.data.AbstractLgrDAO;

public class ModelDocument extends ModelGeneralObjet{

	public ModelDocument(AbstractLgrDAO dao, Class typeObjet) {
		super(dao , typeObjet);
		// TODO Auto-generated constructor stub
	}

	TypeDoc typeDocPresent = TypeDoc.getInstance();
	EntityManager em =new TaFactureDAO().getEntityManager();
//	private Collection <IDocumentTiers> listeEntity = new LinkedList<IDocumentTiers>();
	private TaFactureDAO daoFacture=new TaFactureDAO(em);
	private TaAvoirDAO daoAvoir=new TaAvoirDAO(em);
	
	public LinkedList<IHMEnteteFacture> remplirListe(IDocumentTiers taDocument,EntityManager em) {
		this.setListeEntity(new LinkedList<IDocumentTiersComplet>());
		IDocumentTiers document;
		getListeEntity().clear();
		getListeObjet().clear();
		if(taDocument != null) {
			if(taDocument.getTypeDocument()==TaReglement.TYPE_DOC){
				for(TaRReglement taRReglement : ((TaReglement)taDocument).getTaRReglements()){
					if(taRReglement.getTaFacture()!=null){	
						document=((IDocumentTiers)taRReglement.getTaFacture());
						//document.setTypeDocument(TypeDoc.TYPE_FACTURE);
						getListeEntity().add(document);
					}
					//				if(taRReglement.getTaAvoir()!=null  ){	
					//					document=((IDocumentTiersComplet)taRReglement.getTaAvoir());
					//					document.setTypeDocument(TypeDoc.TYPE_AVOIR);
					//					getListeEntity().add(document);
					//				}
				}
			}
			else
				if(taDocument.getTypeDocument()==TaAvoir.TYPE_DOC){
					for(TaRAvoir taRavoir : ((TaAvoir)taDocument).getTaRAvoirs()){
						if(taRavoir.getTaFacture()!=null){	
							document=((IDocumentTiers)taRavoir.getTaFacture());
							//document.setTypeDocument(TypeDoc.TYPE_FACTURE);
							getListeEntity().add(document);
						}
					}
				}
				else
					if(taDocument.getTypeDocument()==TaAcompte.TYPE_DOC){
						for(TaRAcompte taRacompte : ((TaAcompte)taDocument).getTaRAcomptes()){
							if(taRacompte.getTaFacture()!=null){	
								document=((IDocumentTiers)taRacompte.getTaFacture());
								getListeEntity().add(document);
							}
						}
					}			
			if(getListeEntity()!=null && getListeEntity().size()>0){
				for (Object doc : getListeEntity()) {
					IHMEnteteFacture ihm = new IHMEnteteFacture();
					ihm.setCodeDocument(((IDocumentTiersComplet)doc).getCodeDocument());
					ihm.setLibelleDocument(((IDocumentTiersComplet)doc).getLibelleDocument());
					ihm.setCodeTiers(((IDocumentTiersComplet)doc).getTaTiers().getCodeTiers());
					ihm.setDateDocument(((IDocumentTiersComplet)doc).getDateDocument());
//					ihm.setDateEchDocument(((IDocumentTiersComplet)doc).getDateEchDocument());
					ihm.setNetTtcCalc(((IDocumentTiersComplet)doc).getNetTtcCalc());
					ihm.setRegleCompletDocument(((IDocumentTiersComplet)doc).getRegleCompletDocument());
					ihm.setResteAReglerComplet(((IDocumentTiersComplet)doc).getResteAReglerComplet());
					ihm.setTypeDocument(((IDocumentTiersComplet)doc).getTypeDocument());
					getListeObjet().add(ihm);
				}
			}
				//this.remplirListe();
		}
		return getListeObjet();
	}
	
//	public LinkedList<IHMEnteteDocument> remplirListe(TaAvoir taDocument,EntityManager em) {
//		this.setListeEntity(new LinkedList<IDocumentTiersComplet>());
//		IDocumentTiersComplet document;
//		getListeEntity().clear();
//		getListeObjet().clear();
//		if(taDocument != null) {
//			for(TaRAvoir taRavoir : taDocument.getTaRAvoirs()){
//				if(taRavoir.getTaFacture()!=null){	
//					document=((IDocumentTiersComplet)taRavoir.getTaFacture());
//					document.setTypeDocument(TypeDoc.TYPE_FACTURE);
//					getListeEntity().add(document);
//				}
//			}
//			if(getListeEntity()!=null && getListeEntity().size()>0)
//				this.remplirListe();
//		}
//		return getListeObjet();
//	}


}
