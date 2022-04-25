package fr.legrain.generationdocument.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.divers.ConstEditionDocument;
import fr.legrain.document.divers.TypeDoc;
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
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.generationdocument.divers.AbstractGenereDoc;
import fr.legrain.generationdocument.divers.GenereDocFactory;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.librairiesEcran.interfaces.IPreferencesExtension;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrPreferenceConstantsDocuments;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class CreationDocumentMultiple {
	static Logger logger = Logger.getLogger(CreationDocumentMultiple.class.getName());	
	private String nouveauLibelle="";
	private EntityManager em=null;
	private List<IDocumentTiers> listeDocument = null;
	private String selectedType="";
	private Date dateDocument = null;
	private Date dateLivDocument = null;
	private IDocumentTiers documentSrc = null;
	private ParamAfficheChoixGenerationDocument param;
	private String codeTiers=null;
	private Map<String,Object> listeGestionnaireExtension = new LinkedHashMap<String,Object>();
	private boolean relation=false;
	private boolean changeTiers=false;
	private boolean generationModele=false;


	public CreationDocumentMultiple(ParamAfficheChoixGenerationDocument param , EntityManager em){
				if(param instanceof ParamAfficheChoixGenerationDocument) {
				//typeSrc = ((ParamAfficheChoixGenerationDocument)param).getTypeSrc();
					selectedType =((ParamAfficheChoixGenerationDocument)param).getTypeDest();
				documentSrc = ((ParamAfficheChoixGenerationDocument)param).getDocumentSrc();
				listeDocument=((ParamAfficheChoixGenerationDocument)param).getListeDocumentSrc();
				setNouveauLibelle(((ParamAfficheChoixGenerationDocument)param).getLibelle());
				dateDocument=((ParamAfficheChoixGenerationDocument)param).getDateDocument();
				dateLivDocument=((ParamAfficheChoixGenerationDocument)param).getDateLivraison();
				if(((ParamAfficheChoixGenerationDocument)param).isTiersModifiable())
					codeTiers=((ParamAfficheChoixGenerationDocument)param).getCodeTiers();
				relation=((ParamAfficheChoixGenerationDocument)param).isRelation();
				generationModele=((ParamAfficheChoixGenerationDocument)param).isGenerationModele();
				setParam(param);
				
			}
				this.em = em;
				createContributors();
	}

	public IDocumentTiers creationDocument(boolean controleExistance) {
		IDocumentTiers retour=null;

		try {

			AbstractGenereDoc genereDocument = null;
			IDocumentTiers docGenere = null;
			final TypeDoc typeDocPresent = TypeDoc.getInstance();
			String idEditor ="";
			String typeCPaiement="";
			TaCPaiement taCPaiement1=null;
			TaCPaiement taCPaiement2=null;
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			setEm(daoTiers.getEntityManager());
			TaTiers tiers = null;
			String commentaireDefaut="";
			IPreferencesExtension preference=null;

			if(selectedType==TypeDoc.TYPE_AVOIR) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_AVOIR;
				docGenere = new TaAvoir();
			} else if(selectedType==TypeDoc.TYPE_DEVIS) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_DEVIS;
				docGenere = new TaDevis();
			} else if(selectedType==TypeDoc.TYPE_FACTURE) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_FACTURE;
				docGenere = new TaFacture();
			} else if(selectedType==TypeDoc.TYPE_PROFORMA) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_PROFORMA;
				docGenere = new TaProforma();
			} else if(selectedType==TypeDoc.TYPE_APPORTEUR) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_APORTEUR;
				docGenere = new TaApporteur();
			} else if(selectedType==TypeDoc.TYPE_BON_COMMANDE) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_BON_COMMANDE;
				docGenere = new TaBoncde();
			} else if(selectedType==TypeDoc.TYPE_BON_LIVRAISON) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON;
				docGenere = new TaBonliv();
			}else if(selectedType==TypeDoc.TYPE_ACOMPTE) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_ACOMPTE;
				docGenere = new TaAcompte();
			}else if(selectedType==TypeDoc.TYPE_PRELEVEMENT) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_PRELEVEMENT;
				docGenere = new TaPrelevement();
			}else if(selectedType==TypeDoc.TYPE_AVIS_ECHEANCE) {
				typeCPaiement=TaTCPaiement.C_CODE_TYPE_AVIS_ECHEANCE;
				docGenere = new TaAvisEcheance();
			}
			
			String bundleId=typeDocPresent.getTypeBundleComplet().get(selectedType);


			preference=(IPreferencesExtension)listeGestionnaireExtension.get(bundleId);
			if(preference!=null)commentaireDefaut=preference.getPreferenceStore().
					getString(LgrPreferenceConstantsDocuments.COMMENTAIRE);
			
			TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
			TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(getEm());
			if(taTCPaiementDAO.findByCode(typeCPaiement)!=null
					&& taTCPaiementDAO.findByCode(typeCPaiement).getTaCPaiement()!=null) {
				taCPaiement1=taTCPaiementDAO.findByCode(typeCPaiement).getTaCPaiement();
				taCPaiement2=taCPaiementDAO.findById(taCPaiement1.getIdCPaiement());
			}
			Boolean continuer=!controleExistance;
			typeDocPresent.getInstance();
			idEditor = typeDocPresent.getEditorDoc().get(selectedType);

			if(listeDocument.size()>0){
				listeDocument.get(0).setRelationDocument(true);
				genereDocument = GenereDocFactory.create(listeDocument.get(0), docGenere);
			}
//			genereDocument.setLigneSeparatrice(getParam().getLigneSeparatrice());
			genereDocument.setRecupLibelleSeparateurDoc(!getParam().getRepriseAucun());
			genereDocument.setLigneSeparatrice(genereDocument.getRecupLibelleSeparateurDoc());
			genereDocument.setEm(getEm());
			for (IDocumentTiers document : listeDocument) {
				document.setRelationDocument(controleExistance);
				if(controleExistance && !genereDocument.dejaGenere(document)) {
					continuer=true;
				}
				if(continuer){
					if(param.getRepriseReferenceSrc())
					genereDocument.setLibelleSeparateurDoc(document.getCodeDocument()+" du "+
							LibDate.dateToString(document.getDateDocument()));
					else
						genereDocument.setLibelleSeparateurDoc(document.getLibelleDocument());
					
					if(genereDocument.getRecupLibelleSeparateurDoc()==false){
						genereDocument.setLibelleSeparateurDoc("");
						genereDocument.setLigneSeparatrice(false);
					}
					
					if(!LibChaine.empty(nouveauLibelle)) {
						genereDocument.setLibelleDoc(nouveauLibelle);
					} else {
						genereDocument.setLibelleDoc(document.getLibelleDocument());
					}
					if(document.getTaTiers().getTaCPaiement()!=null && selectedType==TypeDoc.TYPE_FACTURE){
						taCPaiement2=document.getTaTiers().getTaCPaiement();
					}
					if(taCPaiement2!=null){
						genereDocument.setTaCPaiement(taCPaiement2);
					}

					Date date = param.getDateDocument();
					if (date.before(document.getDateDocument()))
						date=document.getDateDocument();
					genereDocument.setDateDoc(dateDansExercice(date));
					
					if((codeTiers!=null && !codeTiers.trim().equals("") && 
					daoTiers.findByCode(codeTiers)!=null)){
						changeTiers=(!codeTiers.equals(document.getTaTiers().getCodeTiers()));
						genereDocument.setCodeTiers(codeTiers);
					}else codeTiers=null;
					

					docGenere = genereDocument.genereDocument(document,docGenere,codeTiers,false,generationModele);
					

				} else {
					continuer=false;
					MessageDialog.openError(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Création document","Ce document a déjà été généré." +
							Const.finDeLigne+ "La génération du document à échouée.");
				}
			}
			if(continuer){
				if(dateDocument!=null){
					docGenere.setDateDocument(dateDocument);
				}
				if(dateLivDocument!=null){
					docGenere.setDateLivDocument(dateLivDocument);
				}
				if(docGenere.getCommentaire().trim().equals(""))
					docGenere.setCommentaire(commentaireDefaut);
				docGenere.setExport(0);
				
				genereDocument.enregistreDocument(docGenere);
				retour=docGenere;
//				if(MessageDialog.openConfirm(
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//						"Création document",
//						"Le document '"+docGenere.getCodeDocument()+"' à été généré correctement. Voulez-vous le visualiser ?"))
//				{
//					String valeurIdentifiant = docGenere.getCodeDocument();
//					ouvreDocument(valeurIdentifiant, idEditor);
//				}
			}
//		}
			
		} catch (Exception e) {
			logger.error("", e);
			retour=null;
		}
			return retour;

	}


	public String getNouveauLibelle() {
		return nouveauLibelle;
	}


	public void setNouveauLibelle(String nouveauLibelle) {
		this.nouveauLibelle = nouveauLibelle;
	}


	public EntityManager getEm() {
		return em;
	}


	public void setEm(EntityManager em) {
		this.em = em;
	}


	public List<IDocumentTiers> getListeDocument() {
		return listeDocument;
	}


	public void setListeDocument(List<IDocumentTiers> listeDocument) {
		this.listeDocument = listeDocument;
	}


	public String getSelectedType() {
		return selectedType;
	}


	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}


	public Date getDateDocument() {
		return dateDocument;
	}


	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}


	public Date getDateLivDocument() {
		return dateLivDocument;
	}


	public void setDateLivDocument(Date dateLivDocument) {
		this.dateLivDocument = dateLivDocument;
	}


	public Date dateDansExercice(Date valeur) throws Exception {
		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(new TaInfoEntrepriseDAO().getEntityManager());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		// si date inférieur à dateDeb dossier
		if (LibDate.compareTo(valeur, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
			return taInfoEntreprise.getDatedebInfoEntreprise();
		} else
			// si date supérieur à dateFin dossier
			if (LibDate.compareTo(valeur, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
				return taInfoEntreprise.getDatefinInfoEntreprise();
			}
		//return new Date();
		return valeur;
	}

	public IDocumentTiers getDocumentSrc() {
		return documentSrc;
	}

	public void setDocumentSrc(IDocumentTiers documentSrc) {
		this.documentSrc = documentSrc;
	}

	public ParamAfficheChoixGenerationDocument getParam() {
		return param;
	}

	public void setParam(ParamAfficheChoixGenerationDocument param) {
		this.param = param;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint pointImpressionDocument = registry.getExtensionPoint("GestionCommerciale.Preferences"); //$NON-NLS-1$

		//gestion des impressions de document
		if (pointImpressionDocument != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = pointImpressionDocument.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String ClassPreferencesAttribut = confElements[jj].getAttribute("ClassPreferences");//$NON-NLS-1$
						String contributorName = confElements[jj].getContributor().getName();
						
						if (ClassPreferencesAttribut == null )
							continue;
						Object ClassPreferences=confElements[jj].createExecutableExtension("ClassPreferences");
						if(ClassPreferences!=null)
							listeGestionnaireExtension.put(((IPreferencesExtension)ClassPreferences).getPluginName(),
									ClassPreferences );

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean isGenerationModele() {
		return generationModele;
	}

	public void setGenerationModele(boolean generationModele) {
		this.generationModele = generationModele;
	}
}
