/**
 * PaFormPage.java						18/04/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.pointsbonus.ecran;

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
import org.hibernate.dialect.PointbaseDialect;

import fr.legrain.pointsbonus.PluginPointBonus;
import fr.legrain.pointsbonus.controller.FormPageControllerPrincipalEcheance;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPageEcheance extends org.eclipse.ui.forms.editor.FormPage {

	// -- Id et titre du formulaire --
	public static String id = "fr.legrain.document.etat.facture.ecrans.PaFormPage";
	public static String title = "Gestion des échéances d'abonnement";
	// -- Chemins des icônes --
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";

	// -- Sections --
	protected Section sctnParamtresDuTableau = null;
	protected Section sctnTableauGauche = null;
	protected Section sctnDocuments = null;

	// -- Composites --
	protected PaCompositeSectionParamEcheance compositeSectionParam = null;
	protected PaCompositeSectionDocEcheance compositeSectionResultats = null;
	protected PaCompositeSectionComplementaireEcheance compositeSectionComplementaire = null;

	// -- Form Général --
	protected ScrolledForm form = null;
	protected FormToolkit toolkit;

	protected FormPageControllerPrincipalEcheance controllerPage = null;
	
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
	public PaFormPageEcheance(String id, String title) {
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

	public PaFormPageEcheance(FormEditor editor, String id, String title) {
		super((FormEditor) editor, id, title);
	}


	Action myAction = new Action("Action 1",PluginPointBonus.getImageDescriptor(iconPath)) { 
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
		form.setSize(974, 460);

		createSections();

		if(controllerPage!=null)
			controllerPage.init();

	}
	
	public void createSections() {
		/* 1ère ligne */
		createSectionParam(toolkit,form);
		
		createSectionComplementaire(toolkit,form);
		
		/* 2ème ligne */
		createSectionResultats(toolkit,form);
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
		compositeSectionParam = new PaCompositeSectionParamEcheance(toolkit.createComposite(sctnParamtresDuTableau),toolkit);
		sctnParamtresDuTableau.setClient(compositeSectionParam.getCompo());

		sctnParamtresDuTableau.setTextClient(compositeSectionParam.getSectionToolbar().createControl(sctnParamtresDuTableau)); 
	}

	//****************************** Composite Section Meilleurs Clients ******************************//

	public void createSectionResultats (FormToolkit toolkit, final ScrolledForm form){

		sctnTableauGauche = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,false,false,6,1);
		gridData.minimumHeight = 150;
		gridData.heightHint = 150;
		sctnTableauGauche.setLayoutData(gridData);

		sctnTableauGauche.setText("Liste des échéances d'abonnements à renouveler");
		

		compositeSectionResultats = new PaCompositeSectionDocEcheance(toolkit.createComposite(sctnTableauGauche),toolkit);
		
		sctnTableauGauche.setClient(compositeSectionResultats.getCompo());
		sctnTableauGauche.setTextClient(compositeSectionResultats.getSectionToolbar().createControl(sctnTableauGauche)); 
		sctnTableauGauche.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}
	
	public void createSectionComplementaire (FormToolkit toolkit, final ScrolledForm form){

		sctnDocuments = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		sctnDocuments.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,3,1));

		sctnDocuments.setText("Paramètres supplémentaires");
		//		sectionCA.setDescription("Chiffre d'affaire");

		compositeSectionComplementaire = new PaCompositeSectionComplementaireEcheance(toolkit.createComposite(sctnDocuments),toolkit);
		compositeSectionComplementaire.getLaGrille().setText("Liste des types d'abonnements");


		sctnDocuments.setClient(compositeSectionComplementaire.getCompo());
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

	public PaCompositeSectionParamEcheance getCompositeSectionParam() {
		return compositeSectionParam;
	}

	public PaCompositeSectionDocEcheance getCompositeSectionResultats() {
		return compositeSectionResultats;
	}

	public FormPageControllerPrincipalEcheance getControllerPage() {
		return controllerPage;
	}

	public void setControllerPage(FormPageControllerPrincipalEcheance controllerPage) {
		this.controllerPage = controllerPage;
	}

	public ScrolledForm getForm() {
		return form;
	}

	public Section getSctnTableauGauche() {
		return sctnTableauGauche;
	}


	public PaCompositeSectionComplementaireEcheance getCompositeSectionComplementaire() {
		return compositeSectionComplementaire;
	}


	public Section getSctnDocuments() {
		return sctnDocuments;
	}

}
