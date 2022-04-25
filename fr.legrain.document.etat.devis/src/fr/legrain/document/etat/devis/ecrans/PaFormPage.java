/**
 * PaFormPage.java						18/04/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.document.etat.devis.ecrans;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.document.etat.devis.Activator;
import fr.legrain.document.etat.devis.controllers.FormPageControllerPrincipal;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPage extends org.eclipse.ui.forms.editor.FormPage {

	// -- Id et titre du formulaire --
	public static String id = "fr.legrain.document.etat.facture.ecrans.PaFormPage";
	public static String title = "Echéance";
	// -- Chemins des icônes --
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";

	// -- Sections --
	protected Section sctnParamtresDuTableau = null;
	protected Section sctnTableauGauche = null;
	protected Section sctnDocuments = null;

	// -- Composites --
	protected PaCompositeSectionParam compositeSectionParam = null;
	protected PaCompositeSectionDocEcheance compositeSectionTableauGauche = null;
	protected PaCompositeSectionDoc compositeSectionDoc = null;

	// -- Form Général --
	protected ScrolledForm form = null;
	protected FormToolkit toolkit;

	protected FormPageControllerPrincipal controllerPage = null;
	
	public void init(IEditorSite site, IEditorInput input) {
		//Contournement du Bug bdg #1388
		//http://www.eclipse.org/forums/index.php/m/275950/
		super.init(site, input);
		// bjeong (11/17/04): provide null selection provider since the
		//default selection provider performs recursive calls.
		// I feel that this is a bug.
		getSite().setSelectionProvider(null);
	}


	/**
	 * Create the form page || Constructeur
	 * @param id
	 * @param title
	 */
	public PaFormPage(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page || Constructeur
	 * @param editor
	 * @param id
	 * @param title
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter id "Some id"
	 * @wbp.eval.method.parameter title "Some title"
	 */

	public PaFormPage(FormEditor editor, String id, String title) {
		super((FormEditor) editor, id, title);
	}


	Action myAction = new Action("Action 1",Activator.getImageDescriptor(iconPath)) { 
		@Override 
		public void run() { 
			System.err.println("Action 1");
		}
	};

	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {

		/* En-tête*/
		toolkit = managedForm.getToolkit();
		form = managedForm.getForm();
		form.setText(title); // Message de l'en-tête
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		form.getBody().setLayout(new GridLayout(6, false));

		createSections();

		if(controllerPage!=null)
			controllerPage.init();

	}
	
	public void createSections() {
		/* 1ère ligne */
		createSectionParam(toolkit,form);
		
		createSectionDoc(toolkit,form);
		
		/* 2ème ligne */
		createSectionTableauGauche(toolkit,form);
	}

	//****************************** Composite Section Paramètres ******************************//

	/* Section contenant les paramètres de la période */
	protected void createSectionParam(FormToolkit toolkit, final ScrolledForm form) {
		// Création de la section
		sctnParamtresDuTableau = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		// Layout // Mise en Forme
		GridData gd_sctnParamtresDuTableau = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_sctnParamtresDuTableau.heightHint = 50;
		sctnParamtresDuTableau.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnParamtresDuTableau);
		sctnParamtresDuTableau.setText("Paramètres");

		// Initialisation du composite et des éléments graphiques basiques
		compositeSectionParam = new PaCompositeSectionParam(toolkit.createComposite(sctnParamtresDuTableau),toolkit);
		sctnParamtresDuTableau.setClient(compositeSectionParam.getCompo());

		sctnParamtresDuTableau.setTextClient(compositeSectionParam.getSectionToolbar().createControl(sctnParamtresDuTableau)); 
	}

	//****************************** Composite Section Meilleurs Clients ******************************//

	public void createSectionTableauGauche (FormToolkit toolkit, final ScrolledForm form){

		sctnTableauGauche = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,false,false,6,1);
		gridData.minimumHeight = 150;
		gridData.heightHint = 150;
		sctnTableauGauche.setLayoutData(gridData);

		sctnTableauGauche.setText("Factures");
		

		compositeSectionTableauGauche = new PaCompositeSectionDocEcheance(toolkit.createComposite(sctnTableauGauche),toolkit);
		
		sctnTableauGauche.setClient(compositeSectionTableauGauche.getCompo());
		sctnTableauGauche.setTextClient(compositeSectionTableauGauche.getSectionToolbar().createControl(sctnTableauGauche)); 
		sctnTableauGauche.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}
	
	public void createSectionDoc (FormToolkit toolkit, final ScrolledForm form){

		sctnDocuments = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		sctnDocuments.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,3,1));

		sctnDocuments.setText("Totaux");
		//		sectionCA.setDescription("Chiffre d'affaire");

		compositeSectionDoc = new PaCompositeSectionDoc(toolkit.createComposite(sctnDocuments),toolkit);
		compositeSectionDoc.getLblNew1().setText("Total HT : ");
		compositeSectionDoc.getLblNew2().setText("Total TTC : ");
//		compositeSectionDoc.getLblNew3().setText("Bons de livraison facturés : ");


		sctnDocuments.setClient(compositeSectionDoc.getCompo());
		sctnDocuments.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}

	/* Refonte de la page */
	public void reflow() {
		if(form!=null) {
			form.getDisplay().asyncExec(new Runnable() {
				public void run() {
					//reflowPending = false;
					if (!form.isDisposed())
						form.reflow(true);
				}
			});
		}
	}

	public Section getSctnParamtresDuTableau() {
		return sctnParamtresDuTableau;
	}

	public void setSctnParamtresDuTableau(Section sctnParamtresDuTableau) {
		this.sctnParamtresDuTableau = sctnParamtresDuTableau;
	}

	public PaCompositeSectionParam getCompositeSectionParam() {
		return compositeSectionParam;
	}

	public PaCompositeSectionDocEcheance getCompositeSectionTableauGauche() {
		return compositeSectionTableauGauche;
	}

	public FormPageControllerPrincipal getControllerPage() {
		return controllerPage;
	}

	public void setControllerPage(FormPageControllerPrincipal controllerPage) {
		this.controllerPage = controllerPage;
	}

	public ScrolledForm getForm() {
		return form;
	}

	public Section getSctnTableauGauche() {
		return sctnTableauGauche;
	}


	public PaCompositeSectionDoc getCompositeSectionDoc() {
		return compositeSectionDoc;
	}


	public Section getSctnDocuments() {
		return sctnDocuments;
	}

}
