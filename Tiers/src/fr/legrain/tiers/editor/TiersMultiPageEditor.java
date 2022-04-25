package fr.legrain.tiers.editor;



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
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
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
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheAdresse;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.ParamAfficheCommercial;
import fr.legrain.tiers.ecran.ParamAfficheEmail;
import fr.legrain.tiers.ecran.ParamAfficheEntreprise;
import fr.legrain.tiers.ecran.ParamAfficheFamille;
import fr.legrain.tiers.ecran.ParamAfficheInfosJuridique;
import fr.legrain.tiers.ecran.ParamAfficheLiens;
import fr.legrain.tiers.ecran.ParamAfficheNoteTiers;
import fr.legrain.tiers.ecran.ParamAfficheParamCorrespondance;
import fr.legrain.tiers.ecran.ParamAfficheParamCreeDocTiers;
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
import fr.legrain.tiers.ecran.SWTPaParamCorrespondanceController;
import fr.legrain.tiers.ecran.SWTPaParamCreationMultiDocController;
import fr.legrain.tiers.ecran.SWTPaTelephoneController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.ecran.SWTPaWebController;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class TiersMultiPageEditor extends AbstractLgrMultiPageEditor implements 
ILgrRetourEditeur, IChangementDePageListener,IChangementMasterEntityListener , IChangementDeSelectionListener,
IAnnuleToutListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.tiers.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(TiersMultiPageEditor.class.getName());
	
	protected ILgrEditor editeurCourant;
	protected int currentPage;
	protected TaTiers masterEntity = null;
	protected TaTiersDAO masterDAO = null;
	protected EntityManager em = null;
	
	protected EditorTiers editorTiers = null;
	protected EditorAdresse editorAdresse = null;
	protected EditorTelephone editorTelephone = null;
	protected EditorWeb editorWeb = null;
	protected EditorEmail editorEmail = null;
	protected EditorCommercial editorCommercial = null;
//	protected EditorComplement editorComplement = null;
	protected EditorConditionPaiement editorConditionPaiement = null;
	protected EditorBanque editorBanque = null;
	protected EditorLiens editorLiens = null;
	protected EditorFamille editorFamille = null;
	protected EditorEntreprise editorEntreprise = null;
	protected EditorNoteTiers editorNoteTiers = null;
	protected EditorInfosJuridique editorInfosJuridique = null;
	protected EditorParamCreeDocTiers editorParamCreeDocTiers = null;
	protected EditorParamCorrespondance editorParamCorrespondance = null;
	
	private List<IEditorTiersExtension> listeEditeurExtension = null;
	
	public static final String PREFIXE_NOM_EDITEUR = "Tiers";
	
	/** Version contact "crado", à remplacer par un ensemble de points d'extension*/
	public static boolean versionContactCrado = false;
	
	/**
	 * Creates a multi-page editor example.
	 */
	public TiersMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		/** Version contact "crado", à remplacer par un ensemble de points d'extension*/
		BundleContext context = Platform.getBundle(gestComBdPlugin.PLUGIN_ID).getBundleContext();
		IServiceBranding service = null;
		ServiceReference<?> serviceReference = context.getServiceReference(IServiceBranding.class.getName());
		if(serviceReference!=null) {
			service = (IServiceBranding) context.getService(serviceReference); 
		}

		String typeVersionCrado = null;
		if(service!=null && service.getBranding()!=null && service.getBranding().getTypeVersion()!=null) {
			typeVersionCrado = service.getBranding().getTypeVersion();
		}
		if(typeVersionCrado!=null && typeVersionCrado.equals(Branding.C_VERSION_CONTACT)) {
			versionContactCrado = true;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////

	}	

//	@Override
//	protected void createHeaderContents(IManagedForm headerForm) {
//		// TODO Auto-generated method stub
//		new Composite1(headerForm.getForm().getParent(), SWT.NONE);
//	}


//	/**
//	 * Creates page 0 of the multi-page editor,
//	 * which contains a text editor.
//	 */
//	void createPage0() {
//		try {
//			editor = new SWTEditorFacture();
//			int index = addPage(editor, getEditorInput());
//			//setPageText(index, editor.getTitle());
//			setPageText(index, "TestEditor");
//		} catch (PartInitException e) {
//			ErrorDialog.openError(
//				getSite().getShell(),"Error creating nested text editor",null,e.getStatus());
//		}
//	}
	
	protected void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor
			String labelPageTiers = "Tiers";
			String labelPageAdresse = "Adresse";
			String labelPageTelephone = "Téléphone";
			String labelPageWeb = "Web";
			String labelPageEmail = "Email";
			String labelPageCommercial = "Commercial";
			String labelPageComplement = "Complément";
			String labelPageConditionPaiement = "Condition de paiement";
			String labelPageBanque = "Banque";
			String labelPageLiens = "Liens";
			String labelPageFamille = "Famille";
			String labelPageEntreprise = "Entreprise";
			String labelPageNoteTiers = "Notes";
			String labelPageInfosJuridique = "Infos juridiques";
			String labelPageParamCreeDocTiers = "Paramètres création document";
			String labelPageParamCorrespondance = "Paramètres correspondance";
			
			String iconPageTiers = "/icons/group.png";
			String iconPageAdresse = "/icons/book_open.png";
			String iconPageTelephone = "/icons/phone.png";
			String iconPageWeb = "/icons/world_link.png";
			String iconPageEmail = "/icons/email.png";
			String iconPageCommercial = "/icons/logo_lgr_16.png";
			String iconPageComplement = "/icons/logo_lgr_16.png";
			String iconPageConditionPaiement = "/icons/creditcards.png";
			String iconPageBanque = "/icons/money.png";
			String iconPageLiens = "/icons/folder_link.png";
			String iconPageFamille = "/icons/logo_lgr_16.png";
			String iconPageEntreprise = "/icons/logo_lgr_16.png";
			String iconPageNoteTiers = "/icons/note.png";
			String iconPageInfosJuridique = "/icons/logo_lgr_16.png";
			String iconPageParamCreeDocTiers="/icons/logo_lgr_16.png";
			String iconPageParamCorrespondance="/icons/logo_lgr_16.png";
			
			em = new TaTiersDAO().getEntityManager();
			editorTiers = new EditorTiers(this,em);
			editorAdresse = new EditorAdresse(this,em);
			editorTelephone = new EditorTelephone(this,em);
			editorWeb = new EditorWeb(this,em);
			editorEmail = new EditorEmail(this,em);
			editorCommercial = new EditorCommercial(this,em);
//			editorComplement = new EditorComplement(this,em);
//			editorConditionPaiement = new EditorConditionPaiement(this,em);
			editorBanque = new EditorBanque(this,em);
			editorLiens = new EditorLiens(this,em);
			editorFamille = new EditorFamille(this,em);
			editorEntreprise = new EditorEntreprise(this,em);
			editorNoteTiers = new EditorNoteTiers(this,em);
			editorInfosJuridique = new EditorInfosJuridique(this,em);
			if(!versionContactCrado) {
				editorParamCreeDocTiers = new EditorParamCreeDocTiers(this, em);
			}
			editorParamCorrespondance = new EditorParamCorrespondance(this, em);
			
			listePageEditeur.add(editorTiers);
			listePageEditeur.add(editorAdresse);
			listePageEditeur.add(editorTelephone);
			listePageEditeur.add(editorWeb);
			listePageEditeur.add(editorEmail);
			listePageEditeur.add(editorLiens);
			listePageEditeur.add(editorCommercial);
			listePageEditeur.add(editorEntreprise);
//			listePageEditeur.add(editorComplement);
//			listePageEditeur.add(editorConditionPaiement);
			listePageEditeur.add(editorBanque);
			
			listePageEditeur.add(editorFamille);
			listePageEditeur.add(editorNoteTiers);
			listePageEditeur.add(editorInfosJuridique);
			if(!versionContactCrado) {
				listePageEditeur.add(editorParamCreeDocTiers);
			}
			listePageEditeur.add(editorParamCorrespondance);
			
			
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
			index = addPage(editorLiens, getEditorInput());
			setPageText(index, labelPageLiens);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageLiens).createImage());
			
			index = addPage(editorCommercial, getEditorInput());
			setPageText(index, labelPageCommercial);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageCommercial).createImage());
	
			index = addPage(editorEntreprise, getEditorInput());
			setPageText(index, labelPageEntreprise);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageEntreprise).createImage());

//			index = addPage(editorComplement, getEditorInput());
//			setPageText(index, labelPageComplement);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageComplement).createImage());
//			index = addPage(editorConditionPaiement, getEditorInput());
//			setPageText(index, labelPageConditionPaiement);
//			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageConditionPaiement).createImage());
			index = addPage(editorBanque, getEditorInput());
			setPageText(index, labelPageBanque);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageBanque).createImage());
			
			index = addPage(editorFamille, getEditorInput());
			setPageText(index, labelPageFamille);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageFamille).createImage());
			
			index = addPage(editorNoteTiers, getEditorInput());
			setPageText(index, labelPageNoteTiers);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageNoteTiers).createImage());
			
			index = addPage(editorInfosJuridique, getEditorInput());
			setPageText(index, labelPageInfosJuridique);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageInfosJuridique).createImage());
			
			if(!versionContactCrado) {
				index = addPage(editorParamCreeDocTiers, getEditorInput());
				setPageText(index, labelPageParamCreeDocTiers);
				setPageImage(index, TiersPlugin.getImageDescriptor(iconPageParamCreeDocTiers).createImage());
			}
			
			index = addPage(editorParamCorrespondance, getEditorInput());
			setPageText(index, labelPageParamCorrespondance);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageParamCorrespondance).createImage());
			
			
			//liaison entre la selection du controller principal et le multipage editor
			editorTiers.getController().addChangementDeSelectionListener(this);
			((SWTPaTiersController)editorTiers.getController()).addChangementMasterEntityListener(this);

			mapEditorController.put(editorTiers, editorTiers.getController());
			mapEditorController.put(editorAdresse, editorAdresse.getController());
			mapEditorController.put(editorTelephone, editorTelephone.getController());
			mapEditorController.put(editorWeb, editorWeb.getController());
			mapEditorController.put(editorEmail, editorEmail.getController());
			mapEditorController.put(editorLiens, editorLiens.getController());
			mapEditorController.put(editorCommercial, editorCommercial.getController());
//			mapEditorController.put(editorComplement, editorComplement.getController());
//			mapEditorController.put(editorConditionPaiement, editorConditionPaiement.getController());
			mapEditorController.put(editorBanque, editorBanque.getController());
			mapEditorController.put(editorFamille, editorFamille.getController());
			mapEditorController.put(editorEntreprise, editorEntreprise.getController());
			mapEditorController.put(editorNoteTiers, editorNoteTiers.getController());
			mapEditorController.put(editorInfosJuridique, editorInfosJuridique.getController());
			if(!versionContactCrado) {
				mapEditorController.put(editorParamCreeDocTiers, editorParamCreeDocTiers.getController());
			}
			mapEditorController.put(editorParamCorrespondance, editorParamCorrespondance.getController());
			
			editorTiers.getController().addChangementDePageListener(this);
			editorAdresse.getController().addChangementDePageListener(this);
			editorTelephone.getController().addChangementDePageListener(this);
			editorWeb.getController().addChangementDePageListener(this);
			editorEmail.getController().addChangementDePageListener(this);
			editorLiens.getController().addChangementDePageListener(this);
			editorCommercial.getController().addChangementDePageListener(this);
//			editorComplement.getController().addChangementDePageListener(this);
//			editorConditionPaiement.getController().addChangementDePageListener(this);
			editorBanque.getController().addChangementDePageListener(this);
			editorFamille.getController().addChangementDePageListener(this);
			editorEntreprise.getController().addChangementDePageListener(this);
			editorNoteTiers.getController().addChangementDePageListener(this);
			editorInfosJuridique.getController().addChangementDePageListener(this);
			if(!versionContactCrado) {
				editorParamCreeDocTiers.getController().addChangementDePageListener(this);
			}
			editorParamCorrespondance.getController().addChangementDePageListener(this);
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
//TODO a remettre quand tous les editeur embarques seront finis
			masterDAO = ((SWTPaTiersController)editorTiers.getController()).getDao();
			((SWTPaAdresseController)editorAdresse.getController()).setMasterDAO(masterDAO);
			((SWTPaTelephoneController)editorTelephone.getController()).setMasterDAO(masterDAO);
			((SWTPaWebController)editorWeb.getController()).setMasterDAO(masterDAO);
			((SWTPaEmailController)editorEmail.getController()).setMasterDAO(masterDAO);
			((SWTPaLiensController)editorLiens.getController()).setMasterDAO(masterDAO);
			((SWTPaCommercialController)editorCommercial.getController()).setMasterDAO(masterDAO);
//			((SWTPaComplController)editorComplement.getController()).setMasterDAO(masterDAO);
//			((SWTPaConditionPaiementController)editorConditionPaiement.getController()).setMasterDAO(masterDAO);
			((SWTPaBanqueController)editorBanque.getController()).setMasterDAO(masterDAO);
			((SWTPaFamilleTiersController)editorFamille.getController()).setMasterDAO(masterDAO);
			((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterDAO(masterDAO);
			((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterDAO(masterDAO);
			((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterDAO(masterDAO);
			if(!versionContactCrado) {
				((SWTPaParamCreationMultiDocController)editorParamCreeDocTiers.getController()).setMasterDAO(masterDAO);
			}
			((SWTPaParamCorrespondanceController)editorParamCorrespondance.getController()).setMasterDAO(masterDAO);
			
			editorAdresse.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorTelephone.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorWeb.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorEmail.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorLiens.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorCommercial.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorComplement.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
//			editorConditionPaiement.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorBanque.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorFamille.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorEntreprise.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorNoteTiers.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorInfosJuridique.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			editorInfosJuridique.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			if(!versionContactCrado) {
				editorParamCreeDocTiers.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			}
			editorParamCorrespondance.getController().addDeclencheCommandeControllerListener(editorTiers.getController());
			
			editorTiers.getController().addAnnuleToutListener(this);
			editorAdresse.getController().addAnnuleToutListener(this);
			editorTelephone.getController().addAnnuleToutListener(this);
			editorWeb.getController().addAnnuleToutListener(this);
			editorEmail.getController().addAnnuleToutListener(this);
			editorLiens.getController().addAnnuleToutListener(this);
			editorCommercial.getController().addAnnuleToutListener(this);
			editorBanque.getController().addAnnuleToutListener(this);
			editorFamille.getController().addAnnuleToutListener(this);
			editorEntreprise.getController().addAnnuleToutListener(this);
			editorNoteTiers.getController().addAnnuleToutListener(this);
			editorInfosJuridique.getController().addAnnuleToutListener(this);
			if(!versionContactCrado) {
				editorParamCreeDocTiers.getController().addAnnuleToutListener(this);
			}
			editorParamCorrespondance.getController().addAnnuleToutListener(this);
			
			//Boolean affiche_onglets = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.P_ONGLETS_DOC);
			//if(!affiche_onglets)
			//	hideTabs();		
			
			editeurCourant = editorTiers;
			//changeEditeurCourant(editorTiers);
			
			createContributors();
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),"Error creating nested text editor",null,e.getStatus());
		}
	}
	
	/**
 	 * If there is just one page in the multi-page editor part,
 	 * this hides the single tab at the bottom.
 	 * <!-- begin-user-doc -->
 	 * <!-- end-user-doc -->
 	 * @generated
 	 */
 	protected void hideTabs() {
		//if (getPageCount() <= 0) {
 		//	setPageText(0, ""); //$NON-NLS-1$
 			if (getContainer() instanceof CTabFolder) {
 				((CTabFolder)getContainer()).setTabHeight(0);
 			}
		//}
 	}
	
	public void changeEditeurCourant(ILgrEditor ed) {
		
		//if(editeurCourant.validate()) {
			for (ILgrEditor e : listePageEditeur) {
				if(e!=ed) e.setEnabled(false);
			}
			ed.setEnabled(true);
			editeurCourant = ed;
			setCurrentPage(listePageEditeur.indexOf(editeurCourant));
		//}
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPageQueEditeur();
	//	createPage0();
	//	super.createPages();
	}
	
	
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
	//	ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		findMasterController().setActiveAide(true);
		getEditor(0).doSave(monitor);
	}
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
//		IEditorPart editor = getEditor(0);
//		editor.doSaveAs();
//		setPageText(0, editor.getTitle());
//		setInput(editor.getEditorInput());
	}
	
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
//		if (!(editorInput instanceof IFileEditorInput))
//			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		int oldPageIndex =   getCurrentPage();
		if (oldPageIndex != -1 && listePageEditeur.size() > oldPageIndex
				&& listePageEditeur.get(oldPageIndex) instanceof ILgrEditor
				&& oldPageIndex != newPageIndex) {
			// Check the old page
			ILgrEditor oldFormPage = (ILgrEditor) listePageEditeur.get(oldPageIndex);
			if (oldFormPage.canLeaveThePage() == false 
					|| masterEntity==null) {
				setActivePage(oldPageIndex);
				return;
			}
		}
		
		
		super.pageChange(newPageIndex);
		
		editeurCourant = listePageEditeur.get(newPageIndex);
		setCurrentPage(listePageEditeur.indexOf(editeurCourant));
		
		if (oldPageIndex != -1 && listePageEditeur.size() > oldPageIndex
				&& listePageEditeur.get(oldPageIndex) instanceof ILgrEditor
				&& oldPageIndex != newPageIndex) {
			if(mapEditorController.get(getActiveEditor())!=null) { //on est bien sur un editeur "classique" et non une extension particuliere
				//evite l'appel a refresh lors de l'ouverture du multipage editor (la commande n'a pas encore de handler actif)
				if ((mapEditorController.get(getActiveEditor()).getDaoStandard().getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) != 0)
						&& (mapEditorController.get(getActiveEditor()).getDaoStandard().getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) != 0)) {
					//on n'est pas en edition, ni en insertion
					mapEditorController.get(getActiveEditor()).executeCommand(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_REFRESH_ID);
				}
			}
		}
		
		if(getActiveEditor() instanceof IEditorTiersExtension) {
			((IEditorTiersExtension)getActiveEditor()).activate();
		}


	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;	
	}


	public Object retour() {
		// TODO Auto-generated method stub
		return null;
	}


	public void utiliseRetour(Object r) {
		// TODO Auto-generated method stub
		if(editeurCourant instanceof ILgrRetourEditeur)
			((ILgrRetourEditeur)editeurCourant).utiliseRetour(r);
	}


	@Override
	protected Composite createPageContainer(Composite parent) {
		// TODO Auto-generated method stub
		return super.createPageContainer(parent);
	}

	public ILgrEditor findSuivant() {
		if(listePageEditeur.size()>getCurrentPage()+1) {
			if(listePageEditeur.get(getCurrentPage()+1)!=null) {
				return listePageEditeur.get(getCurrentPage()+1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public ILgrEditor findPrecedent() {
		if(getCurrentPage()>0) {
			if(listePageEditeur.get(getCurrentPage()-1)!=null) {
				return listePageEditeur.get(getCurrentPage()-1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void annuleTout(AnnuleToutEvent evt) {
//		boolean autreOngletEnModif = false;
//		for (ILgrEditor ed : mapEditorController.keySet()) {
//			//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
//			switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
//			case C_MO_INSERTION:
//			case C_MO_EDITION:
//				if(mapEditorController.get(ed).getSourceDeclencheCommandeController()==null)
//					autreOngletEnModif = true;
//				break;
//			}
//		}
		
//		if(autreOngletEnModif) {
			MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
					"Annulation",
					//null, "Il existe d'autres modifications/insertions en cours pour ce tiers. Voulez vous les annuler aussi ?",
					null, "Voulez vous annuler les modifications en cours ?",
					MessageDialog.QUESTION, 
					//new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL},
					new String [] {IDialogConstants.YES_LABEL,IDialogConstants.YES_TO_ALL_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.NO_TO_ALL_LABEL,IDialogConstants.CANCEL_LABEL},
					0);

			//final int dialogResult = dialog.open();
			int pourTous = -1;

			/*
			 * yes => boucle avec question
			 * yes_all => boucle sans question (annulation silencieuse)
			 * no => enregistre avec question
			 * no_all => enregistre tout
			 */

			//if(dialogResult ==0) {
			int dialogResult = -1;
			
			if(evt.isSilencieu())
				dialogResult = 1;
			
				for (ILgrEditor ed : mapEditorController.keySet()) {
					//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
					switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
					case C_MO_INSERTION:
					case C_MO_EDITION:
						if(!mapEditorController.get(ed).equals(evt.getSource())) {
							
							setActiveEditor(ed);
							
							if(dialogResult==-1)
								dialogResult = dialog.open();
							
							if(dialogResult ==0 || dialogResult==1) {
								
								
								boolean silencieu = mapEditorController.get(ed).isSilencieu();
								mapEditorController.get(ed).setSilencieu(true);
								//mapEditorController.get(ed).setSilencieu(evt.isSilencieu());
								mapEditorController.get(ed).setAnnuleToutEnCours(true);
								mapEditorController.get(ed).declencheCommandeController(new DeclencheCommandeControllerEvent(this,JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_ANNULER_ID));
								mapEditorController.get(ed).setAnnuleToutEnCours(false);
								mapEditorController.get(ed).setSilencieu(silencieu);
								if( dialogResult==0)
									dialogResult = -1;
							} else if(dialogResult==2 || dialogResult==3) {
								//no
								if( dialogResult==2)
									dialogResult = -1;
							} else if(dialogResult==4) {
								//cancel
								break;
							}


						}
						break;
					}
				}
			//}
//		}
	}
	
	public void enregistreTout(AnnuleToutEvent evt) {
//		MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
//				"Enregistrement",
//				null, "Voulez vous enregistrer les modifications ?",
//				MessageDialog.QUESTION, 
//				new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.CANCEL_LABEL},
//				0);
//		
//		final int dialogResult = dialog.open();
		
		/*
		 * yes => boucle avec question
		 * yes_all => boucle sans question (annulation silencieuse)
		 * no => enregistre avec question
		 * no_all => enregistre tout
		 */
		
//		if(dialogResult ==0) {
//			
//		}
		boolean pourTous = false;
		for (ILgrEditor ed : mapEditorController.keySet()) {
			//if(mapEditorController.get(ed).getDaoStandard().getModeObjet()
			switch (mapEditorController.get(ed).getDaoStandard().getModeObjet().getMode()) {
			case C_MO_INSERTION:
			case C_MO_EDITION:
				boolean silencieu = mapEditorController.get(ed).isSilencieu();
//				if(!pourTous) {
//					setActiveEditor(ed);
//				}
				
//				if(!pourTous) {
//					if(!mapEditorController.get(ed).equals(evt.getSource())) {
//						MessageDialog dialog = new MessageDialog(mapEditorController.get(ed).getVue().getShell(), 
//								"Attention",
//								null, "Enregistrer ?",
//								MessageDialog.QUESTION, 
//								new String [] {IDialogConstants.YES_LABEL,IDialogConstants.YES_TO_ALL_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.NO_TO_ALL_LABEL,IDialogConstants.CANCEL_LABEL},
//								0);
//						final int dialogResult = dialog.open();
//						if(dialogResult ==0) {
//						} else if(dialogResult ==1) {
//							pourTous = true;
//						} else if(dialogResult ==2) {
//						} else if(dialogResult ==3) {
//						} else if(dialogResult ==4) {
//						}
//						silencieu = true;
//					}
//				}
				
				
				if(!mapEditorController.get(ed).equals(evt.getSource())) {
//					if(!(mapEditorController.get(ed) instanceof SWTPaTiersController)) {
					mapEditorController.get(ed).setSilencieu(evt.isSilencieu());
					mapEditorController.get(ed).setPrepareEnregistrement(true);
					mapEditorController.get(ed).declencheCommandeController(new DeclencheCommandeControllerEvent(this,JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_ENREGISTRER_ID));
					mapEditorController.get(ed).setPrepareEnregistrement(false);
					mapEditorController.get(ed).setSilencieu(silencieu);
//					}
				}
				break;
			}
		}
	}
	
	@Override
	public int promptToSaveOnClose() {
		try {
			onClose();
		} catch (Exception e) {
			return ISaveablePart2.CANCEL;
		}
		return ISaveablePart2.NO; 
//		MessageDialog dialog = new MessageDialog(getEditorSite().getShell(), 
//				"Enregistrement",
//				null, "Voulez vous sauvegarder les modifications ?",
//				MessageDialog.QUESTION, 
//				new String [] {IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.CANCEL_LABEL},
//				0);
//		MessageDialogWithToggle dialogt;
//		final int dialogResult = dialog.open();
//		
//		if(dialogResult ==0) {
//			if(true) { //bouchon, appeler une methode de verif des données
//				return ISaveablePart2.YES;
//			} else {
//				//showMessageErreur
//				return ISaveablePart2.CANCEL;
//			}
//		} else if(dialogResult ==1) {
//			annuleTout(new AnnuleToutEvent(this,true));
//			return ISaveablePart2.NO;
//		} else {
//			return ISaveablePart2.CANCEL;
//		}
	}

	public void changementDePage(ChangementDePageEvent evt) {
		if(evt.getSens() == ChangementDePageEvent.PRECEDENT) {
			if(findPrecedent()!=null) {
//				changeEditeurCourant(findPrecedent());
				setActiveEditor(findPrecedent());
			}
		} else if(evt.getSens() == ChangementDePageEvent.SUIVANT) {
			if(findSuivant()!=null) {
				//changeEditeurCourant(findSuivant());
				setActiveEditor(findSuivant());
			}
		} else if(evt.getSens() == ChangementDePageEvent.DEBUT) {
				//changeEditeurCourant(findSuivant());
			if(listePageEditeur.get(0)!=null)	
				setActiveEditor(listePageEditeur.get(0));
		} else if(evt.getSens() == ChangementDePageEvent.FIN) {
			if(listePageEditeur.size()!=0 && listePageEditeur.get(listePageEditeur.size())!=null)
				setActiveEditor(listePageEditeur.get(listePageEditeur.size()-1));
		} else if(evt.getSens() == ChangementDePageEvent.AUTRE) {
			if(listePageEditeur.get(evt.getPosition())!=null)
				setActiveEditor(listePageEditeur.get(evt.getPosition()));
			
		}
	}

	/*
	 * Reaction au changement de selection dans l'editeur principal
	 * (non-Javadoc)
	 * @see fr.legrain.gestCom.librairiesEcran.workbench.IChangementDeSelectionListener#changementDeSelection(fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent)
	 */
	public void changementDeSelection(ChangementDeSelectionEvent evt) {
		masterEntity = ((SWTPaTiersController)editorTiers.getController()).getTaTiers();
		if(masterEntity!=null && masterEntity.getCodeTiers()!=null) {
			setPartName(PREFIXE_NOM_EDITEUR+" : "+masterEntity.getCodeTiers());
		} else {
			setPartName("");
		}
		//maj de l'editeur adresse
		ParamAfficheAdresse paramAfficheAdresse = new ParamAfficheAdresse();
		paramAfficheAdresse.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheAdresse.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaAdresseController)editorAdresse.getController()).setMasterEntity(masterEntity);
		((SWTPaAdresseController)editorAdresse.getController()).configPanel(paramAfficheAdresse);
		//maj de l'editeur telephone
		ParamAfficheTelephone paramAfficheTelephone = new ParamAfficheTelephone();
		paramAfficheTelephone.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheTelephone.setIdTiers(masterEntity.getIdTiers());
		((SWTPaTelephoneController)editorTelephone.getController()).setMasterEntity(masterEntity);
		((SWTPaTelephoneController)editorTelephone.getController()).configPanel(paramAfficheTelephone);
		//maj de l'editeur web
		ParamAfficheWeb paramAfficheWeb = new ParamAfficheWeb();
		paramAfficheWeb.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheWeb.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaWebController)editorWeb.getController()).setMasterEntity(masterEntity);
		((SWTPaWebController)editorWeb.getController()).configPanel(paramAfficheWeb);
		//maj de l'editeur email
		ParamAfficheEmail paramAfficheEmail = new ParamAfficheEmail();
		paramAfficheEmail.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheEmail.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaEmailController)editorEmail.getController()).setMasterEntity(masterEntity);
		((SWTPaEmailController)editorEmail.getController()).configPanel(paramAfficheEmail);
		//maj de l'editeur Liens
		ParamAfficheLiens paramAfficheLiens = new ParamAfficheLiens();
		paramAfficheLiens.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheLiens.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaLiensController)editorLiens.getController()).setMasterEntity(masterEntity);
		((SWTPaLiensController)editorLiens.getController()).configPanel(paramAfficheLiens);
		//maj de l'editeur CompteBanque
		ParamAfficheBanque paramAfficheBanque = new ParamAfficheBanque();
		paramAfficheBanque.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheBanque.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaBanqueController)editorBanque.getController()).setMasterEntity(masterEntity);
		((SWTPaBanqueController)editorBanque.getController()).configPanel(paramAfficheBanque);
		//maj de l'editeur commercial
		ParamAfficheCommercial paramAfficheCommercial = new ParamAfficheCommercial();
		paramAfficheCommercial.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheCommercial.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaCommercialController)editorCommercial.getController()).setMasterEntity(masterEntity);
		((SWTPaCommercialController)editorCommercial.getController()).configPanel(paramAfficheCommercial);
		//maj de l'editeur famille
		ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
		paramAfficheFamille.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheFamille.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaFamilleTiersController)editorFamille.getController()).setMasterEntity(masterEntity);
		((SWTPaFamilleTiersController)editorFamille.getController()).configPanel(paramAfficheFamille);

		//maj de l'editeur entreprise
		ParamAfficheEntreprise paramAfficheEntreprise = new ParamAfficheEntreprise();
		paramAfficheEntreprise.setRaffraichi(false);
		if(masterEntity!=null)paramAfficheEntreprise.setIdTiers(LibConversion.integerToString(masterEntity.getIdTiers()));
		((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterEntity(masterEntity);
		((SWTPaEntrepriseController)editorEntreprise.getController()).configPanel(paramAfficheEntreprise);
		
		//maj de l'editeur infos juridique
		ParamAfficheInfosJuridique paramAfficheInfosJuridique = new ParamAfficheInfosJuridique();
		paramAfficheInfosJuridique.setRaffraichi(false);
//		paramAfficheInfosJuridique.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterEntity(masterEntity);
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).configPanel(paramAfficheInfosJuridique);
		
		//maj de l'editeur infos juridique
		ParamAfficheNoteTiers paramAfficheNoteTiers = new ParamAfficheNoteTiers();
		paramAfficheNoteTiers.setRaffraichi(false);
//		paramAfficheInfosJuridique.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterEntity(masterEntity);
		((SWTPaNoteTiersController)editorNoteTiers.getController()).configPanel(paramAfficheNoteTiers);

		if(!versionContactCrado) {
			//maj de l'editeur 
			ParamAfficheParamCreeDocTiers paramAfficheParamCreeDocTiers = new ParamAfficheParamCreeDocTiers();
			paramAfficheParamCreeDocTiers.setRaffraichi(false);
			((SWTPaParamCreationMultiDocController)editorParamCreeDocTiers.getController()).setMasterEntity(masterEntity);
			((SWTPaParamCreationMultiDocController)editorParamCreeDocTiers.getController()).configPanel(paramAfficheParamCreeDocTiers);
		}
		
		//maj de l'editeur infos juridique
		ParamAfficheParamCorrespondance paramAfficheParamCorrespondance = new ParamAfficheParamCorrespondance();
		paramAfficheParamCorrespondance.setRaffraichi(false);
//		paramAfficheInfosJuridique.setIdTiers(new Integer(masterEntity.getIdTiers()));
		((SWTPaParamCorrespondanceController)editorParamCorrespondance.getController()).setMasterEntity(masterEntity);
		((SWTPaParamCorrespondanceController)editorParamCorrespondance.getController()).configPanel(paramAfficheParamCorrespondance);
		
		if(listeEditeurExtension!=null){
			for (IEditorTiersExtension editor : listeEditeurExtension) {
				editor.setMasterEntity(masterEntity);
				editor.activate();
			}
		}
	}
	@Override
	protected void onClose() throws Exception {
		// TODO Auto-generated method stub
		try {
			if (!findMasterController().onClose())
				throw new Exception();
		} finally {
			em.close();
		}
	}
//	@Override
//	protected void addPages() {
//		// TODO Auto-generated method stub
//		try {
//			int index = addPage(new FormPageA(this,"aa", "desc"));
//			setPageText(index, "formPage");
//			index = addPage(new FormPageA(this,"bb", "descb"));
//			setPageText(index, "formPageb");
//		} catch (PartInitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void changementMasterEntity(ChangementMasterEntityEvent evt) {
		masterEntity = ((SWTPaTiersController)editorTiers.getController()).getTaTiers();
		if(masterEntity!=null && masterEntity.getCodeTiers()!=null) {
			setPartName(masterEntity.getCodeTiers());
		} else {
			setPartName("");
		}
		((SWTPaAdresseController)editorAdresse.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur telephone
		((SWTPaTelephoneController)editorTelephone.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur web
		((SWTPaWebController)editorWeb.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur email
		((SWTPaEmailController)editorEmail.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur Liens
		((SWTPaLiensController)editorLiens.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur CompteBanque
		((SWTPaBanqueController)editorBanque.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur commercial
		((SWTPaCommercialController)editorCommercial.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur famille
		((SWTPaFamilleTiersController)editorFamille.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur entreprise
		((SWTPaEntrepriseController)editorEntreprise.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur note
		((SWTPaNoteTiersController)editorNoteTiers.getController()).setMasterEntity(masterEntity);
		//maj de l'editeur info juridique
		((SWTPaInfosJuridiqueController)editorInfosJuridique.getController()).setMasterEntity(masterEntity);
		if(!versionContactCrado) {
			//maj de l'editeur parametre creation document
			((SWTPaParamCreationMultiDocController)editorParamCreeDocTiers.getController()).setMasterEntity(masterEntity);
		}
		((SWTPaParamCorrespondanceController)editorParamCorrespondance.getController()).setMasterEntity(masterEntity);
		
		
		for (IEditorTiersExtension editor : listeEditeurExtension) {
			editor.setMasterEntity(masterEntity);
		}
	}
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("Tiers.editorPageTiers"); //$NON-NLS-1$
		if (point != null) {
			//Map<Integer, List<IMultiPaneEditorContributor>> seq2contributors = new HashMap<Integer, List<IMultiPaneEditorContributor>>();
			ImageDescriptor icon = null;
			IExtension[] extensions = point.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String editorClass = confElements[jj].getAttribute("editorClass");//$NON-NLS-1$
						String editorName = confElements[jj].getAttribute("editorLabel");//$NON-NLS-1$
						String editorIcon = confElements[jj].getAttribute("editorIcon");//$NON-NLS-1$
						String contributorName = confElements[jj].getContributor().getName();
						
						if (editorClass == null || editorName == null)
							continue;

						Object o = confElements[jj].createExecutableExtension("editorClass");
						if(editorIcon!=null) {
							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
						}
						addEditor((ILgrEditor)o,editorName,icon);
						
//						// test if the contributor applies to this editor
//						boolean isApplicable = false;
//						Class<?> subject = this.getClass();
//						while (subject != EditorPart.class && !isApplicable) {
//							isApplicable = editorClass.equals(subject.getName());
//							subject = subject.getSuperclass();
//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void addEditor(ILgrEditor e,String label) {
		 addEditor(e,label,null);
	}
	
	private void addEditor(ILgrEditor e,String label,ImageDescriptor icon) {
		String labelPage = label;		
		listePageEditeur.add(e);
		
		int index = 0;
		try {
			index = addPage(e, getEditorInput());
		} catch (PartInitException e1) {
			logger.error("",e1);
		}
		setPageText(index, labelPage);
		setPageImage(index, icon.createImage());
		
		if (e instanceof IEditorTiersExtension) {
			if(listeEditeurExtension==null) {
				listeEditeurExtension = new ArrayList<IEditorTiersExtension>();
			}
			listeEditeurExtension.add((IEditorTiersExtension)e);
			((IEditorTiersExtension)e).setMasterDAO(masterDAO);
			((IEditorTiersExtension)e).setMasterEntity(masterEntity);
		}
	}
}
