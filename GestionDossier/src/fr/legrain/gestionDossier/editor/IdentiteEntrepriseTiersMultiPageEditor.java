package fr.legrain.gestionDossier.editor;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IAnnuleToutListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDePageListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ILgrRetourEditeur;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersInfosEntrepriseSWT;
import fr.legrain.tiers.ecran.PaTiersInfosEntrepriseSWT;
import fr.legrain.tiers.ecran.ParamAfficheAdresse;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.ParamAfficheCommercial;
import fr.legrain.tiers.ecran.ParamAfficheEmail;
import fr.legrain.tiers.ecran.ParamAfficheEntreprise;
import fr.legrain.tiers.ecran.ParamAfficheFamille;
import fr.legrain.tiers.ecran.ParamAfficheInfosJuridique;
import fr.legrain.tiers.ecran.ParamAfficheLiens;
import fr.legrain.tiers.ecran.ParamAfficheNoteTiers;
import fr.legrain.tiers.ecran.ParamAfficheTelephone;
import fr.legrain.tiers.ecran.ParamAfficheWeb;
import fr.legrain.tiers.ecran.SWTPaAdresseController;
import fr.legrain.tiers.ecran.SWTPaBanqueController;
import fr.legrain.tiers.ecran.SWTPaCommercialController;
import fr.legrain.tiers.ecran.SWTPaEmailController;
import fr.legrain.tiers.ecran.SWTPaEntrepriseController;
import fr.legrain.tiers.ecran.SWTPaFamilleTiersController;
import fr.legrain.tiers.ecran.SWTPaInfosJuridiqueController;
import fr.legrain.tiers.ecran.SWTPaLiensController;
import fr.legrain.tiers.ecran.SWTPaNoteTiersController;
import fr.legrain.tiers.ecran.SWTPaTelephoneController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.ecran.SWTPaTiersInfosEntrepriseController;
import fr.legrain.tiers.ecran.SWTPaWebController;
import fr.legrain.tiers.editor.EditorAdresse;
import fr.legrain.tiers.editor.EditorBanque;
import fr.legrain.tiers.editor.EditorCommercial;
import fr.legrain.tiers.editor.EditorEmail;
import fr.legrain.tiers.editor.EditorEntreprise;
import fr.legrain.tiers.editor.EditorFamille;
import fr.legrain.tiers.editor.EditorInfosJuridique;
import fr.legrain.tiers.editor.EditorLiens;
import fr.legrain.tiers.editor.EditorNoteTiers;
import fr.legrain.tiers.editor.EditorTelephone;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.EditorTiersInfosEntreprise;
import fr.legrain.tiers.editor.EditorWeb;
import fr.legrain.tiers.editor.TiersInfosEntrepriseMultiPageEditor;
import fr.legrain.tiers.editor.TiersMultiPageEditor;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class IdentiteEntrepriseTiersMultiPageEditor extends TiersInfosEntrepriseMultiPageEditor {
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.infosentreprise.tiers.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(IdentiteEntrepriseTiersMultiPageEditor.class.getName());
	
//	private ILgrEditor editeurCourant;
//	private int currentPage;
//	private TaTiers masterEntity = null;
//	private TaTiersDAO masterDAO = null;
//	private EntityManager em = null;
	
//	private EditorTiers editorTiers = null;
//	private EditorAdresse editorAdresse = null;
//	private EditorTelephone editorTelephone = null;
//	private EditorWeb editorWeb = null;
//	private EditorEmail editorEmail = null;
//	private EditorCommercial editorCommercial = null;
////	private EditorComplement editorComplement = null;
//	private EditorConditionPaiement editorConditionPaiement = null;
//	private EditorBanque editorBanque = null;
//	private EditorLiens editorLiens = null;
//	private EditorFamille editorFamille = null;
//	private EditorEntreprise editorEntreprise = null;
//	private EditorNoteTiers editorNoteTiers = null;
//	
//	private List<IEditorTiersExtension> listeEditeurExtension = null;
	
	/**
	 * Creates a multi-page editor example.
	 */
	public IdentiteEntrepriseTiersMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}	
	
	protected void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor
			String labelPageTiers = "Entreprise";
			String labelPageAdresse = "Adresse";
			String labelPageTelephone = "Téléphone";
			String labelPageWeb = "Web";
			String labelPageEmail = "Email";
//			String labelPageCommercial = "Commercial";
//			String labelPageComplement = "Complément";
//			String labelPageConditionPaiement = "Condition de paiement";
			String labelPageBanque = "Banque";
//			String labelPageLiens = "Liens";
//			String labelPageFamille = "Famille";
//			String labelPageEntreprise = "Entreprise";
//			String labelPageNoteTiers = "Notes";
			String labelPageInfosJuridique = "Infos juridiques";
			
			String iconPageTiers = "/icons/group.png";
			String iconPageAdresse = "/icons/book_open.png";
			String iconPageTelephone = "/icons/phone.png";
			String iconPageWeb = "/icons/world_link.png";
			String iconPageEmail = "/icons/email.png";
//			String iconPageCommercial = "/icons/legrain.gif";
//			String iconPageComplement = "/icons/legrain.gif";
//			String iconPageConditionPaiement = "/icons/creditcards.png";
			String iconPageBanque = "/icons/money.png";
//			String iconPageLiens = "/icons/money.png";
//			String iconPageFamille = "/icons/legrain.gif";
//			String iconPageEntreprise = "/icons/legrain.gif";
//			String iconPageNoteTiers = "/icons/note.png";
			String iconPageInfosJuridique = "/icons/logo_lgr_16.png";
			
			em = new TaTiersDAO().getEntityManager();
			editorTiers = new EditorTiersInfosEntreprise(this,em);
			editorAdresse = new EditorAdresse(this,em);
			editorTelephone = new EditorTelephone(this,em);
			editorWeb = new EditorWeb(this,em);
			editorEmail = new EditorEmail(this,em);
//			editorCommercial = new EditorCommercial(this,em);
			editorBanque = new EditorBanque(this,em);
//			editorLiens = new EditorLiens(this,em);
//			editorFamille = new EditorFamille(this,em);
//			editorEntreprise = new EditorEntreprise(this,em);
//			editorNoteTiers = new EditorNoteTiers(this,em);
			editorInfosJuridique = new EditorInfosJuridique(this,em);
			
			listePageEditeur.add(editorTiers);
			listePageEditeur.add(editorAdresse);
			listePageEditeur.add(editorTelephone);
			listePageEditeur.add(editorWeb);
			listePageEditeur.add(editorEmail);
//			listePageEditeur.add(editorLiens);
//			listePageEditeur.add(editorCommercial);
//			listePageEditeur.add(editorEntreprise);
			listePageEditeur.add(editorBanque);
			
//			listePageEditeur.add(editorFamille);
//			listePageEditeur.add(editorNoteTiers);
			listePageEditeur.add(editorInfosJuridique);
			
			
			int index = addPage(editorTiers, getEditorInput());
			setPageText(index, labelPageTiers);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageTiers).createImage());
			index = addPage(editorAdresse, getEditorInput());
			setPageText(index, labelPageAdresse);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageAdresse).createImage());
			index = addPage(editorTelephone, getEditorInput());
			setPageText(index, labelPageTelephone);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageTelephone).createImage());
			index = addPage(editorWeb, getEditorInput());
			setPageText(index, labelPageWeb);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageWeb).createImage());
			index = addPage(editorEmail, getEditorInput());
			setPageText(index, labelPageEmail);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageEmail).createImage());
//			index = addPage(editorLiens, getEditorInput());
//			setPageText(index, labelPageLiens);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageLiens).createImage());
//			
//			index = addPage(editorCommercial, getEditorInput());
//			setPageText(index, labelPageCommercial);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageCommercial).createImage());
//	
//			index = addPage(editorEntreprise, getEditorInput());
//			setPageText(index, labelPageEntreprise);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageEntreprise).createImage());

			index = addPage(editorBanque, getEditorInput());
			setPageText(index, labelPageBanque);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageBanque).createImage());
			
//			index = addPage(editorFamille, getEditorInput());
//			setPageText(index, labelPageFamille);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageFamille).createImage());
//			
//			index = addPage(editorNoteTiers, getEditorInput());
//			setPageText(index, labelPageNoteTiers);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageNoteTiers).createImage());
			
			index = addPage(editorInfosJuridique, getEditorInput());
			setPageText(index, labelPageInfosJuridique);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageInfosJuridique).createImage());
		
			//liaison entre la selection du controller principal et le multipage editor
			editorTiers.getController().addChangementDeSelectionListener(this);
			((SWTPaTiersInfosEntrepriseController)editorTiers.getController()).addChangementMasterEntityListener(this);

			/////////////////////
			////              ///
			/////////////////////
			((PaTiersInfosEntrepriseSWT)editorTiers.getController().getVue()).getGrille().setVisible(false);
			((PaTiersInfosEntrepriseSWT)editorTiers.getController().getVue()).getLaTitreGrille().setVisible(false);
			((PaTiersInfosEntrepriseSWT)editorTiers.getController().getVue()).getCompositeForm().setWeights(new int[]{0,100});
//			((PaTiersInfosEntrepriseSWT)editorTiers.getController().getVue()).getCompositeForm().setEnabled(false);
//			((SWTPaTiersInfosEntrepriseController)editorTiers.getController()).get
			
			mapEditorController.put(editorTiers, editorTiers.getController());
			mapEditorController.put(editorAdresse, editorAdresse.getController());
			mapEditorController.put(editorTelephone, editorTelephone.getController());
			mapEditorController.put(editorWeb, editorWeb.getController());
			mapEditorController.put(editorEmail, editorEmail.getController());
//			mapEditorController.put(editorLiens, editorLiens.getController());
//			mapEditorController.put(editorCommercial, editorCommercial.getController());
			mapEditorController.put(editorBanque, editorBanque.getController());
//			mapEditorController.put(editorFamille, editorFamille.getController());
//			mapEditorController.put(editorEntreprise, editorEntreprise.getController());
//			mapEditorController.put(editorNoteTiers, editorNoteTiers.getController());
			mapEditorController.put(editorInfosJuridique, editorInfosJuridique.getController());

			
			editorTiers.getController().addChangementDePageListener(this);
			editorAdresse.getController().addChangementDePageListener(this);
			editorTelephone.getController().addChangementDePageListener(this);
			editorWeb.getController().addChangementDePageListener(this);
			editorEmail.getController().addChangementDePageListener(this);
//			editorLiens.getController().addChangementDePageListener(this);
//			editorCommercial.getController().addChangementDePageListener(this);
			editorBanque.getController().addChangementDePageListener(this);
//			editorFamille.getController().addChangementDePageListener(this);
//			editorEntreprise.getController().addChangementDePageListener(this);
//			editorNoteTiers.getController().addChangementDePageListener(this);
			editorInfosJuridique.getController().addChangementDePageListener(this);
			
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
//TODO a remettre quand tous les editeur embarques seront finis
			masterDAO = ((SWTPaTiersInfosEntrepriseController)editorTiers.getController()).getDao();
			((SWTPaAdresseController)editorAdresse.getController()).setMasterDAO(masterDAO);
			((SWTPaTelephoneController)editorTelephone.getController()).setMasterDAO(masterDAO);
			((SWTPaWebController)editorWeb.getController()).setMasterDAO(masterDAO);
			((SWTPaEmailController)editorEmail.getController()).setMasterDAO(masterDAO);
//			((SWTPaLiensController)editorLiens.getController()).setMasterDAO(masterDAO);
//			((SWTPaCommercialController)editorCommercial.getController()).setMasterDAO(masterDAO);
			((SWTPaBanqueController)editorBanque.getController()).setMasterDAO(masterDAO);
//			((SWTPaFamilleTiersController)editorFamille.getController()).setMasterDAO(masterDAO);
//			((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterDAO(masterDAO);
//			((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterDAO(masterDAO);
			((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterDAO(masterDAO);
			
			editorAdresse.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorTelephone.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorWeb.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorEmail.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorLiens.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorCommercial.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorBanque.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorFamille.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorEntreprise.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorNoteTiers.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorInfosJuridique.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			
			editorTiers.getController().addAnnuleToutListener(this);
			editorAdresse.getController().addAnnuleToutListener(this);
			editorTelephone.getController().addAnnuleToutListener(this);
			editorWeb.getController().addAnnuleToutListener(this);
			editorEmail.getController().addAnnuleToutListener(this);
//			editorLiens.getController().addAnnuleToutListener(this);
//			editorCommercial.getController().addAnnuleToutListener(this);
			editorBanque.getController().addAnnuleToutListener(this);
//			editorFamille.getController().addAnnuleToutListener(this);
//			editorEntreprise.getController().addAnnuleToutListener(this);
//			editorNoteTiers.getController().addAnnuleToutListener(this);
			editorInfosJuridique.getController().addAnnuleToutListener(this);
			
			editeurCourant = editorTiers;
			
			//changeEditeurCourant(editorTiers);
			
			//createContributors();
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),"Error creating nested text editor",null,e.getStatus());
		}
	}
	
	/*
	 * Reaction au changement de selection dans l'editeur principal
	 * (non-Javadoc)
	 * @see fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener#changementDeSelection(fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent)
	 */
	public void changementDeSelection(ChangementDeSelectionEvent evt) {
		masterEntity = ((SWTPaTiersInfosEntrepriseController)editorTiers.getController()).getTaTiers();
//		if(masterEntity!=null && masterEntity.getCodeTiers()!=null) {
//			setPartName("Détails Infos Entrepirse "+masterEntity.getCodeTiers());
//		} else {
			setPartName("Identité entreprise");
//		}
		//maj de l'editeur adresse
		ParamAfficheAdresse paramAfficheAdresse = new ParamAfficheAdresse();
		paramAfficheAdresse.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaAdresseController)editorAdresse.getController()).setMasterEntity(masterEntity);
		((SWTPaAdresseController)editorAdresse.getController()).configPanel(paramAfficheAdresse);
		//maj de l'editeur telephone
		ParamAfficheTelephone paramAfficheTelephone = new ParamAfficheTelephone();
		paramAfficheTelephone.setIdTiers(masterEntity.getIdTiers());
		((SWTPaTelephoneController)editorTelephone.getController()).setMasterEntity(masterEntity);
		((SWTPaTelephoneController)editorTelephone.getController()).configPanel(paramAfficheTelephone);
		//maj de l'editeur web
		ParamAfficheWeb paramAfficheWeb = new ParamAfficheWeb();
		paramAfficheWeb.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaWebController)editorWeb.getController()).setMasterEntity(masterEntity);
		((SWTPaWebController)editorWeb.getController()).configPanel(paramAfficheWeb);
		//maj de l'editeur email
		ParamAfficheEmail paramAfficheEmail = new ParamAfficheEmail();
		paramAfficheEmail.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaEmailController)editorEmail.getController()).setMasterEntity(masterEntity);
		((SWTPaEmailController)editorEmail.getController()).configPanel(paramAfficheEmail);
//		//maj de l'editeur Liens
//		ParamAfficheLiens paramAfficheLiens = new ParamAfficheLiens();
//		paramAfficheLiens.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
//		((SWTPaLiensController)editorLiens.getController()).setMasterEntity(masterEntity);
//		((SWTPaLiensController)editorLiens.getController()).configPanel(paramAfficheLiens);
		//maj de l'editeur CompteBanque
		ParamAfficheBanque paramAfficheBanque = new ParamAfficheBanque();
		paramAfficheBanque.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaBanqueController)editorBanque.getController()).setMasterEntity(masterEntity);
		((SWTPaBanqueController)editorBanque.getController()).configPanel(paramAfficheBanque);
//		//maj de l'editeur commercial
//		ParamAfficheCommercial paramAfficheCommercial = new ParamAfficheCommercial();
//		paramAfficheCommercial.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
//		((SWTPaCommercialController)editorCommercial.getController()).setMasterEntity(masterEntity);
//		((SWTPaCommercialController)editorCommercial.getController()).configPanel(paramAfficheCommercial);
//		//maj de l'editeur famille
//		ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
//		paramAfficheFamille.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
//		((SWTPaFamilleTiersController)editorFamille.getController()).setMasterEntity(masterEntity);
//		((SWTPaFamilleTiersController)editorFamille.getController()).configPanel(paramAfficheFamille);
//
//		//maj de l'editeur entreprise
//		ParamAfficheEntreprise paramAfficheEntreprise = new ParamAfficheEntreprise();
//		paramAfficheEntreprise.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
//		((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterEntity(masterEntity);
//		((SWTPaEntrepriseController)editorEntreprise.getController()).configPanel(paramAfficheEntreprise);
//		
//		//maj de l'editeur note
//		ParamAfficheNoteTiers paramAfficheNote = new ParamAfficheNoteTiers();
//		paramAfficheNote.setIdTiers(new Integer(masterEntity.getIdTiers()));
//		((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterEntity(masterEntity);
//		((SWTPaNoteTiersController)editorNoteTiers.getController()).configPanel(paramAfficheNote);
		
		//maj de l'editeur infos juridique
		ParamAfficheInfosJuridique paramAfficheInfosJuridique = new ParamAfficheInfosJuridique();
//		paramAfficheInfosJuridique.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterEntity(masterEntity);
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).configPanel(paramAfficheInfosJuridique);
		
//		if(listeEditeurExtension!=null){
//			for (IEditorTiersExtension editor : listeEditeurExtension) {
//				editor.setMasterEntity(masterEntity);
//			}
//		}
	}

	@Override
	public void changementMasterEntity(ChangementMasterEntityEvent evt) {
		masterEntity = ((SWTPaTiersInfosEntrepriseController)editorTiers.getController()).getTaTiers();
//		if(masterEntity!=null && masterEntity.getCodeTiers()!=null) {
//			setPartName(masterEntity.getCodeTiers());
//		} else {
			setPartName("Identité entreprise");
//		}
		((SWTPaAdresseController)editorAdresse.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur telephone
		((SWTPaTelephoneController)editorTelephone.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur web
		((SWTPaWebController)editorWeb.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur email
		((SWTPaEmailController)editorEmail.getController()).setMasterEntity(masterEntity);
//		//maj de l'editeur Liens
//		((SWTPaLiensController)editorLiens.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur CompteBanque
		((SWTPaBanqueController)editorBanque.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur commercial
//		((SWTPaCommercialController)editorCommercial.getController()).setMasterEntity(masterEntity);
//		//maj de l'editeur famille
//		((SWTPaFamilleTiersController)editorFamille.getController()).setMasterEntity(masterEntity);
//		//maj de l'editeur entreprise
//		((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterEntity(masterEntity);
//		//maj de l'editeur note
//		((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur info juridique
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterEntity(masterEntity);
		
//		for (IEditorTiersExtension editor : listeEditeurExtension) {
//			editor.setMasterEntity(masterEntity);
//		}
	}
	
}
