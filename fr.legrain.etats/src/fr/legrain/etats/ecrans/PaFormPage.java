package fr.legrain.etats.ecrans;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.etats.Activator;
import fr.legrain.etats.controllers.FormPageControllerPrincipal;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 *
 */

public class PaFormPage extends org.eclipse.ui.forms.editor.FormPage {


	public static String id = "fr.legrain.statistiques.ecrans.PaFormPage";
	public static String title = "Etats";

	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";

	protected Section sctnGroupeEtat2 = null;
	protected Section sctnGroupeEtat1 = null;
	protected Section sctnListe = null;
	protected Section sctnParam = null;

	protected PaCompositeGroupeEtat1 compositeGroupeEtat2 = null;
	protected PaCompositeGroupeEtat1 compositeGroupeEtat1 = null;
	protected PaCompositeListeEtat1 compositeListe = null;
	protected PaCompositeGroupeParametres compositeParam = null;


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
		form.getBody().setLayout(new GridLayout(3, false));
		 
		createSections();
		
		if(controllerPage!=null)
			controllerPage.appel();

	}
	
	public void createSections() {
		createSectionGroupeEtat1(toolkit,form);
		createSectionListe(toolkit,form);
		createSectionParam(toolkit,form);
		//createSectionGroupeEtat2(toolkit,form);
		
		compositeListe.setCompoParam(compositeParam);
		compositeListe.setPaFormPage(this);
		
		compositeGroupeEtat1.setCompoListe(compositeListe);
	}
	
	protected void createSectionGroupeEtat1(FormToolkit toolkit, final ScrolledForm form) {
		sctnGroupeEtat1 = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		// Layout // Mise en Forme
		GridData gd_sctnParamtresDuTableau = new GridData(SWT.BEGINNING, SWT.FILL, false, false, 1, 1);
		gd_sctnParamtresDuTableau.widthHint = 600;
		gd_sctnParamtresDuTableau.minimumWidth = 600;
		sctnGroupeEtat1.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnGroupeEtat1);
		sctnGroupeEtat1.setText("Factures et avoirs");

		// Initialisation du composite et des éléments graphiques basiques
		compositeGroupeEtat1 = new PaCompositeGroupeEtat1(toolkit.createComposite(sctnGroupeEtat1),toolkit);
		sctnGroupeEtat1.setClient(compositeGroupeEtat1.getCompo());

		sctnGroupeEtat1.setTextClient(compositeGroupeEtat1.getSectionToolbar().createControl(sctnGroupeEtat1)); 
		//		compositeSectionParam.getSectionToolbar().add(myAction);
		//		compositeSectionParam.getSectionToolbar().update(true);
	}
	
	protected void createSectionGroupeEtat2(FormToolkit toolkit, final ScrolledForm form) {
		sctnGroupeEtat2 = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		GridData gd_sctnParamtresDuTableau = new GridData(SWT.BEGINNING, SWT.FILL, false, false, 1, 1);
		//gd_sctnParamtresDuTableau.heightHint = 50;
		gd_sctnParamtresDuTableau.widthHint = 600;
		gd_sctnParamtresDuTableau.minimumWidth = 600;
		sctnGroupeEtat2.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnGroupeEtat2);
		sctnGroupeEtat2.setText("Groupe état 2");

		// Initialisation du composite et des éléments graphiques basiques
		compositeGroupeEtat2 = new PaCompositeGroupeEtat1(toolkit.createComposite(sctnGroupeEtat2),toolkit);
		sctnGroupeEtat2.setClient(compositeGroupeEtat2.getCompo());

		sctnGroupeEtat2.setTextClient(compositeGroupeEtat2.getSectionToolbar().createControl(sctnGroupeEtat2)); 
	}
	
	protected void createSectionListe(FormToolkit toolkit, final ScrolledForm form) {
		sctnListe = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		GridData gd_sctnParamtresDuTableau = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		//gd_sctnParamtresDuTableau.heightHint = 20;
		sctnListe.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnListe);
		sctnListe.setText("Liste état");
		
		//sctnListe.setBackground(new org.eclipse.swt.graphics.Color(toolkit.getColors().getDisplay(),30,30,30));
		//sctnListe.setBackgroundImage(new Image(toolkit.getColors().getDisplay(),"/usr/share/backgrounds/The_Forbidden_City_by_Daniel_Mathis.jpg"));
		//sctnListe.setActiveToggleColor(new org.eclipse.swt.graphics.Color(toolkit.getColors().getDisplay(),100,150,200));
		
//		sctnListe.setTitleBarBackground(new org.eclipse.swt.graphics.Color(toolkit.getColors().getDisplay(),100,150,200));
//		sctnListe.setTitleBarForeground(new org.eclipse.swt.graphics.Color(toolkit.getColors().getDisplay(),100,150,255));
		
		

		// Initialisation du composite et des éléments graphiques basiques
		compositeListe = new PaCompositeListeEtat1(toolkit.createComposite(sctnListe),toolkit);
		sctnListe.setClient(compositeListe.getCompo());

		sctnListe.setTextClient(compositeListe.getSectionToolbar().createControl(sctnListe)); 
		//		compositeSectionParam.getSectionToolbar().add(myAction);
		//		compositeSectionParam.getSectionToolbar().update(true);
	}
	
	protected void createSectionParam(FormToolkit toolkit, final ScrolledForm form) {
		sctnParam = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		GridData gd_sctnParamtresDuTableau = new GridData(SWT.END, SWT.FILL, false, false, 1, 2);
		gd_sctnParamtresDuTableau.widthHint = 600;
		gd_sctnParamtresDuTableau.minimumWidth = 600;
		sctnParam.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnParam);
		sctnParam.setText("Paramètres");
		
		// Initialisation du composite et des éléments graphiques basiques
		compositeParam = new PaCompositeGroupeParametres(toolkit.createComposite(sctnParam),toolkit);
		sctnParam.setClient(compositeParam.getCompo());

		sctnParam.setTextClient(compositeParam.getSectionToolbar().createControl(sctnParam)); 
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

	public Section getSctnGroupeEtat2() {
		return sctnGroupeEtat2;
	}

	public void setSctnGroupeEtat2(Section sctnParamtresDuTableau) {
		this.sctnGroupeEtat2 = sctnParamtresDuTableau;
	}

	public PaCompositeGroupeEtat1 getCompositeGroupeEtat2() {
		return compositeGroupeEtat2;
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


	public PaCompositeGroupeEtat1 getCompositeGroupeEtat1() {
		return compositeGroupeEtat1;
	}


	public PaCompositeListeEtat1 getCompositeListe() {
		return compositeListe;
	}


	public PaCompositeGroupeParametres getCompositeParam() {
		return compositeParam;
	}
}
