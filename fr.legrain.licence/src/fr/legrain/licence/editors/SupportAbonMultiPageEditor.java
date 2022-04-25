package fr.legrain.licence.editors;



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

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
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
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.licence.controllers.SWTPaSupportAbonController;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaTiersDAO;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class SupportAbonMultiPageEditor extends AbstractLgrMultiPageEditor implements 
ILgrRetourEditeur, IChangementDePageListener,IChangementMasterEntityListener , IChangementDeSelectionListener,
IAnnuleToutListener{
//public class MultiPageEditor extends FormEditor {
//public class MultiPageEditor extends SharedHeaderFormEditor {
	
	public static final String ID_EDITOR = "fr.legrain.editor.supportAbon.multi"; //$NON-NLS-1$
	static Logger logger = Logger.getLogger(SupportAbonMultiPageEditor.class.getName());
	
	protected ILgrEditor editeurCourant;
	protected int currentPage;
	protected TaSupportAbon masterEntity = null;
	protected TaSupportAbonDAO masterDAO = null;
	protected EntityManager em = null;
	
	protected EditorSupportAbon editorSupportAbon = null;
	
	//private List<IEditorTiersExtension> listeEditeurExtension = null;
	
	public static final String PREFIXE_NOM_EDITEUR = "Supports du Tiers";
	
	/**
	 * Creates a multi-page editor example.
	 */
	public SupportAbonMultiPageEditor() {
		super();
		setID_EDITOR(ID_EDITOR);
	}	


	protected void createPageQueEditeur() {
		try {
			//Initialisation des pages/editeurs composant le multipage editor
			String labelPageTiers = "Support abonnement";			
			
			String iconPageTiers="/icons/logo_lgr_16.png";
			
			em = new TaSupportAbonDAO().getEntityManager();
			editorSupportAbon = new EditorSupportAbon(this,em);			
			
			listePageEditeur.add(editorSupportAbon);
			
			
			
			int index = addPage(editorSupportAbon, getEditorInput());
			setPageText(index, labelPageTiers);
			setPageImage(index, TiersPlugin.getImageDescriptor(iconPageTiers).createImage());
			
			
			//liaison entre la selection du controller principal et le multipage editor
			editorSupportAbon.getController().addChangementDeSelectionListener(this);
			//((SWTPaSupportAbonController)editorSupportAbon.getController()).addChangementMasterEntityListener(this);

			mapEditorController.put(editorSupportAbon, editorSupportAbon.getController());
			
			
			editorSupportAbon.getController().addChangementDePageListener(this);
			
			
			//liaison entre l'editeur principal et les editeur secondaire par injection de l'entite principale dans les editeurs secondaires
//TODO a remettre quand tous les editeur embarques seront finis
			masterDAO = ((SWTPaSupportAbonController)editorSupportAbon.getController()).getDao();
			
			
			
			
			editorSupportAbon.getController().addAnnuleToutListener(this);
			
			
			//Boolean affiche_onglets = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.P_ONGLETS_DOC);
			//if(!affiche_onglets)
			//	hideTabs();		
			
			editeurCourant = editorSupportAbon;
			//changeEditeurCourant(editorSupportAbon);
			
			//createContributors();
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
		
//		if(getActiveEditor() instanceof IEditorTiersExtension) {
//			((IEditorTiersExtension)getActiveEditor()).activate();
//		}


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
		masterEntity = ((SWTPaSupportAbonController)editorSupportAbon.getController()).getTaSupport();
		if(masterEntity!=null && masterEntity.getCodeSupportAbon()!=null) {
			setPartName(PREFIXE_NOM_EDITEUR+" : "+masterEntity.getTaTiers().getCodeTiers());
		} else {
			setPartName("");
		}
		
		
//		if(listeEditeurExtension!=null){
//			for (IEditorTiersExtension editor : listeEditeurExtension) {
//				editor.setMasterEntity(masterEntity);
//			}
//		}
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
		masterEntity = ((SWTPaSupportAbonController)editorSupportAbon.getController()).getTaSupport();
		if(masterEntity!=null && masterEntity.getCodeSupportAbon()!=null) {
			setPartName(masterEntity.getCodeSupportAbon());
		} else {
			setPartName("");
		}
		
		
//		for (IEditorTiersExtension editor : listeEditeurExtension) {
//			editor.setMasterEntity(masterEntity);
//		}
	}
	
//	private void createContributors() {
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		IExtensionPoint point = registry.getExtensionPoint("Tiers.editorPageTiers"); //$NON-NLS-1$
//		if (point != null) {
//			//Map<Integer, List<IMultiPaneEditorContributor>> seq2contributors = new HashMap<Integer, List<IMultiPaneEditorContributor>>();
//			ImageDescriptor icon = null;
//			IExtension[] extensions = point.getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
//				for (int jj = 0; jj < confElements.length; jj++) {
//					try {
//						String editorClass = confElements[jj].getAttribute("editorClass");//$NON-NLS-1$
//						String editorName = confElements[jj].getAttribute("editorLabel");//$NON-NLS-1$
//						String editorIcon = confElements[jj].getAttribute("editorIcon");//$NON-NLS-1$
//						String contributorName = confElements[jj].getContributor().getName();
//						
//						if (editorClass == null || editorName == null)
//							continue;
//
//						Object o = confElements[jj].createExecutableExtension("editorClass");
//						if(editorIcon!=null) {
//							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
//						}
//						addEditor((ILgrEditor)o,editorName,icon);
//						
////						// test if the contributor applies to this editor
////						boolean isApplicable = false;
////						Class<?> subject = this.getClass();
////						while (subject != EditorPart.class && !isApplicable) {
////							isApplicable = editorClass.equals(subject.getName());
////							subject = subject.getSuperclass();
////						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
	
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
		
//		if (e instanceof IEditorTiersExtension) {
//			if(listeEditeurExtension==null) {
//				listeEditeurExtension = new ArrayList<IEditorTiersExtension>();
//			}
//			listeEditeurExtension.add((IEditorTiersExtension)e);
//			((IEditorTiersExtension)e).setMasterDAO(masterDAO);
//			((IEditorTiersExtension)e).setMasterEntity(masterEntity);
//		}
	}
}
