package fr.legrain.document.divers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Platform;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.pointLgr.dao.TaComptePoint;

public class TypeDoc {
	
	public static final String TYPE_TOUS = "Tous";// "Facture"; 
	public static final String TYPE_FACTURE = TaFacture.TYPE_DOC;// "Facture"; 
	public static final String TYPE_DEVIS = TaDevis.TYPE_DOC; //"Devis";
	public static final String TYPE_BON_LIVRAISON = TaBonliv.TYPE_DOC; //"Bon de livraison";
	public static final String TYPE_APPORTEUR = TaApporteur.TYPE_DOC; //"Apporteur";
	public static final String TYPE_BON_COMMANDE =TaBoncde.TYPE_DOC; //"Bon de commande";
	public static final String TYPE_AVOIR = TaAvoir.TYPE_DOC; //"Avoir";
	public static final String TYPE_PROFORMA = TaProforma.TYPE_DOC; //"Proforma";
	public static final String TYPE_ACOMPTE = TaAcompte.TYPE_DOC; //"Acompte";
	public static final String TYPE_PRELEVEMENT = TaPrelevement.TYPE_DOC; //"Prelevement";
	public static final String TYPE_REGLEMENT = TaReglement.TYPE_DOC; //"Reglement";
	public static final String TYPE_REMISE = TaRemise.TYPE_DOC; //"Remise";
	public static final String TYPE_AVIS_ECHEANCE = TaAvisEcheance.TYPE_DOC; //"AvisEcheance";
	
	public static final String TYPE_COMPTE_POINT_BONUS = TaComptePoint.TYPE_DOC; //"AvisEcheance";
	public static final String TYPE_SUPPORT_ABONNEMENT = TaSupportAbon.TYPE_DOC; //"AvisEcheance";
	
	
	public static final String TYPE_FACTURE_BUNDLEID = "Facture"; 
	public static final String TYPE_DEVIS_BUNDLEID = "Devis";
	public static final String TYPE_BON_LIVRAISON_BUNDLEID = "BonLivraison";
	public static final String TYPE_APPORTEUR_BUNDLEID = "Apporteur";
	public static final String TYPE_BON_COMMANDE_BUNDLEID = "Boncde";
	public static final String TYPE_AVOIR_BUNDLEID = "Avoir";
	public static final String TYPE_PROFORMA_BUNDLEID = "Proforma";
	public static final String TYPE_ACOMPTE_BUNDLEID = "Acompte";
	public static final String TYPE_PRELEVEMENT_BUNDLEID = "Prelevement";
	public static final String TYPE_REMISE_BUNDLEID = "Remise";
	public static final String TYPE_REGLEMENT_BUNDLEID = "fr.legrain.reglement";
	public static final String TYPE_AVIS_ECHEANCE_BUNDLEID = "avisEcheance";
	
	public static final String TYPE_COMPTE_POINT_BONUS_BUNDLEID = TaComptePoint.TYPE_DOC;
	public static final String TYPE_SUPPORT_ABONNEMENT_BUNDLEID = TaSupportAbon.TYPE_DOC;
	
	//liste de tous les plugins contenant un document réellement présent
	private Map<String,String> typeDocCompletPresent = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
	
	
	//liste de tous les plugins contenant un document
	private Map<String,String> typeDocComplet = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
	
	
	
	//liste de tous les bundle contenant un document
	private Map<String,String> typeBundleComplet = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
		
	
	//liste de tous les plugins contenant un document réellement present pour l'impression
	private Map<String,String> typeDocImpressionPresent = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
	
	
	//liste de tous les plugins contenant un document réellement present pour l'impression
	private Map<String,String> typeDocImpression = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
	
	
	//liste des plugin reelement present dans l'application
	private Map<String,String> typeDocPresentSelectionAcompte = new LinkedHashMap<String, String>(); //<bundle ID><libelle>
	
	//liste des editeurs pour chaque document
	private Map<String,String> editorDoc = new HashMap<String, String>(); //<libelle><id editor>
	
	
	private List<String[]> editorDocPossibleCreationDocument = new LinkedList<String[]>(); //<libelle><id editor>
	


	private TypeDoc() {}
	
	private static TypeDoc instance = null;
	
	public static TypeDoc getInstance() {
		if(instance==null) {
			instance = new TypeDoc(); 
			
			instance.typeBundleComplet.put(TYPE_FACTURE,TYPE_FACTURE_BUNDLEID); 
			instance.typeBundleComplet.put(TYPE_DEVIS,TYPE_DEVIS_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_BON_LIVRAISON,TYPE_BON_LIVRAISON_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_APPORTEUR,TYPE_APPORTEUR_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_BON_COMMANDE,TYPE_BON_COMMANDE_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_AVOIR,TYPE_AVOIR_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_PROFORMA,TYPE_PROFORMA_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_ACOMPTE,TYPE_ACOMPTE_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_PRELEVEMENT,TYPE_PRELEVEMENT_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_REMISE,TYPE_REMISE_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_REGLEMENT,TYPE_REGLEMENT_BUNDLEID);
			instance.typeBundleComplet.put(TYPE_AVIS_ECHEANCE,TYPE_AVIS_ECHEANCE_BUNDLEID);
			
			instance.typeDocComplet.put(TYPE_FACTURE_BUNDLEID, TYPE_FACTURE); 
			instance.typeDocComplet.put(TYPE_DEVIS_BUNDLEID, TYPE_DEVIS);
			instance.typeDocComplet.put(TYPE_BON_LIVRAISON_BUNDLEID, TYPE_BON_LIVRAISON);
			instance.typeDocComplet.put(TYPE_APPORTEUR_BUNDLEID,TYPE_APPORTEUR);
			instance.typeDocComplet.put(TYPE_BON_COMMANDE_BUNDLEID,TYPE_BON_COMMANDE);
			instance.typeDocComplet.put(TYPE_AVOIR_BUNDLEID,TYPE_AVOIR);
			instance.typeDocComplet.put(TYPE_PROFORMA_BUNDLEID,TYPE_PROFORMA);
			instance.typeDocComplet.put(TYPE_ACOMPTE_BUNDLEID,TYPE_ACOMPTE);
			instance.typeDocComplet.put(TYPE_PRELEVEMENT_BUNDLEID,TYPE_PRELEVEMENT);
			instance.typeDocComplet.put(TYPE_REMISE_BUNDLEID,TYPE_REMISE);
			instance.typeDocComplet.put(TYPE_REGLEMENT_BUNDLEID,TYPE_REGLEMENT);
			instance.typeDocComplet.put(TYPE_AVIS_ECHEANCE_BUNDLEID,TYPE_AVIS_ECHEANCE);
			
			
			instance.typeDocImpression.put(TYPE_FACTURE_BUNDLEID, TYPE_FACTURE); 
			instance.typeDocImpression.put(TYPE_DEVIS_BUNDLEID, TYPE_DEVIS);
			instance.typeDocImpression.put(TYPE_BON_LIVRAISON_BUNDLEID, TYPE_BON_LIVRAISON);
			instance.typeDocImpression.put(TYPE_APPORTEUR_BUNDLEID,TYPE_APPORTEUR);
			instance.typeDocImpression.put(TYPE_BON_COMMANDE_BUNDLEID,TYPE_BON_COMMANDE);
			instance.typeDocImpression.put(TYPE_AVOIR_BUNDLEID,TYPE_AVOIR);
			instance.typeDocImpression.put(TYPE_PROFORMA_BUNDLEID,TYPE_PROFORMA);
			instance.typeDocImpression.put(TYPE_ACOMPTE_BUNDLEID,TYPE_ACOMPTE);
			instance.typeDocImpression.put(TYPE_PRELEVEMENT_BUNDLEID,TYPE_PRELEVEMENT);
			instance.typeDocImpression.put(TYPE_AVIS_ECHEANCE_BUNDLEID,TYPE_AVIS_ECHEANCE);
			
			instance.editorDoc.put(TYPE_FACTURE, "fr.legrain.editor.facture.swt.multi");
			instance.editorDoc.put(TYPE_DEVIS, "fr.legrain.editor.devis.swt.multi");
			instance.editorDoc.put(TYPE_BON_LIVRAISON, "fr.legrain.editor.Bl.swt.multi");
			instance.editorDoc.put(TYPE_APPORTEUR, "fr.legrain.editor.apporteur.swt.multi");
			instance.editorDoc.put(TYPE_BON_COMMANDE, "fr.legrain.editor.boncde.swt.multi");
			instance.editorDoc.put(TYPE_AVOIR, "fr.legrain.editor.avoir.swt.multi");
			instance.editorDoc.put(TYPE_PROFORMA, "fr.legrain.editor.proforma.swt.multi");
			instance.editorDoc.put(TYPE_ACOMPTE, "fr.legrain.editor.acompte.swt.multi");
			instance.editorDoc.put(TYPE_PRELEVEMENT, "fr.legrain.editor.prelevement.swt.multi");
			instance.editorDoc.put(TYPE_REGLEMENT, "fr.legrain.reglement.editor.EditorGestionReglement");
			instance.editorDoc.put(TYPE_REMISE, "fr.legrain.remise.multi");
			instance.editorDoc.put(TYPE_AVIS_ECHEANCE, "fr.legrain.editor.avisEcheance.swt.multi");

			//gestion de la création des documents
			//c'est ici qu'il faut en enlever ou en rajouter
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_DEVIS, TYPE_FACTURE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_DEVIS, TYPE_BON_LIVRAISON});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_DEVIS, TYPE_BON_COMMANDE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_DEVIS, TYPE_PROFORMA});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_FACTURE, TYPE_BON_LIVRAISON});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_FACTURE, TYPE_AVOIR});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_FACTURE, TYPE_AVIS_ECHEANCE});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PROFORMA, TYPE_FACTURE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PROFORMA, TYPE_BON_LIVRAISON});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PROFORMA, TYPE_BON_COMMANDE});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_BON_COMMANDE, TYPE_FACTURE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_BON_COMMANDE, TYPE_BON_LIVRAISON});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_BON_LIVRAISON, TYPE_FACTURE});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PRELEVEMENT, TYPE_FACTURE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PRELEVEMENT, TYPE_BON_LIVRAISON});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_PRELEVEMENT, TYPE_BON_COMMANDE});
			
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_AVIS_ECHEANCE, TYPE_FACTURE});
			instance.editorDocPossibleCreationDocument.add(new String[] {TYPE_AVIS_ECHEANCE, TYPE_PRELEVEMENT});
			
			for (String bundleId : instance.typeDocComplet.keySet()) {
				if(Platform.getBundle(bundleId)!=null){
					if(bundleId.equals(TYPE_DEVIS_BUNDLEID)||
							bundleId.equals(TYPE_BON_COMMANDE_BUNDLEID)||
							bundleId.equals(TYPE_PROFORMA_BUNDLEID)){
						instance.typeDocPresentSelectionAcompte.put(bundleId, instance.typeDocComplet.get(bundleId));
					}
					instance.typeDocCompletPresent.put(bundleId, instance.typeDocComplet.get(bundleId));
				}
			}
			for (String bundleId : instance.typeDocImpression.keySet()) {
				if(Platform.getBundle(bundleId)!=null)
					instance.typeDocImpressionPresent.put(bundleId, instance.typeDocImpression.get(bundleId));
			}
			
		} 
		return instance;
	}
	
	public Map<String, String> getTypeDocCompletPresent() {
		return typeDocCompletPresent;
	}

	public Map<String, String> getTypeDocPresentSelectionAcompte() {
		return typeDocPresentSelectionAcompte;
	}

	public Map<String, String> getEditorDoc() {
		return editorDoc;
	}

	public List<String[]> getEditorDocPossibleCreationDocument() {
		return editorDocPossibleCreationDocument;
	}

	public Map<String, String> getTypeDocComplet() {
		return typeDocComplet;
	}

	public void setTypeDocComplet(Map<String, String> typeDocComplet) {
		this.typeDocComplet = typeDocComplet;
	}

	public Map<String, String> getTypeDocImpressionPresent() {
		return typeDocImpressionPresent;
	}

	public void setTypeDocImpressionPresent(
			Map<String, String> typeDocImpressionPresent) {
		this.typeDocImpressionPresent = typeDocImpressionPresent;
	}

	public Map<String, String> getTypeBundleComplet() {
		return typeBundleComplet;
	}

	public void setTypeBundleComplet(Map<String, String> typeBundleComplet) {
		this.typeBundleComplet = typeBundleComplet;
	}






}
