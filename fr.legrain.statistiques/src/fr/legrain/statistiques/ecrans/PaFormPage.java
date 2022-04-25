/**
 * PaFormPage.java						18/04/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.statistiques.ecrans;

import java.util.Date;

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

import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;

/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas²
 *
 */

public class PaFormPage extends org.eclipse.ui.forms.editor.FormPage {

	/* Variables publiques */
	// -- Id et titre du formulaire --
	public static String id = "fr.legrain.statistiques.ecrans.PaFormPage";
	public static String title = "Tableau de Bord";
	// -- Chemins des icônes --
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";

	/* Variables privées */
	// -- Sections --
	protected Section sctnParamtresDuTableau = null;
	protected Section sctnMontant = null;
	protected Section sctnDocuments = null;
	protected Section sctnTableauGauche = null;
	protected Section sctnJauge = null;
	protected Section sctnRepartition = null;
	protected Section sctnTableauDroit = null;
	protected Section sctnEvolutionDuChiffre = null;



	// -- Composites --
	protected PaCompositeSectionParam compositeSectionParam = null;
	protected PaCompositeSectionMontant compositeSectionMontant = null;
	protected PaCompositeSectionDoc compositeSectionDoc = null;
	protected PaCompositeSectionTableauGauche compositeSectionTableauGauche = null;
	protected PaCompositeSectionJauge compositeSectionJauge = null;
	protected PaCompositeSectionRepartition compositeSectionRepartition =  null;
	protected PaCompositeSectionTableauDroit compositeSectionTableauDroit = null;
	protected PaCompositeSectionEvolution compositeSectionEvolution = null;


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
			controllerPage.appel();

	}
	
	public void createSections() {
		/* Création des différentes sections */
		/* 1ère ligne */
		createSectionParam(toolkit,form);
		createSectionMontant(toolkit,form);
		createSectionDoc(toolkit,form);
		
		/* 2ème ligne */
		createSectionJauge(toolkit,form);
		createSectionEvolution(toolkit,form);
		
		createSectionRepartition(toolkit,form);
		
		/* 3ème ligne */
		createSectionTableauGauche(toolkit,form);
		createSectionTableauDroit(toolkit,form);

	}

	//****************************** Composite Section Paramètres ******************************//

	/* Section contenant les paramètres de la période */
	protected void createSectionParam(FormToolkit toolkit, final ScrolledForm form) {
		// Création de la section
		sctnParamtresDuTableau = toolkit.createSection(form.getBody(), Section.EXPANDED  | Section.TWISTIE | Section.TITLE_BAR);

		// Layout // Mise en Forme
		GridData gd_sctnParamtresDuTableau = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_sctnParamtresDuTableau.heightHint = 50;
		sctnParamtresDuTableau.setLayoutData(gd_sctnParamtresDuTableau);
		toolkit.paintBordersFor(sctnParamtresDuTableau);
		sctnParamtresDuTableau.setText("Paramètres du Tableau de Bord");

		// Initialisation du composite et des éléments graphiques basiques
		compositeSectionParam = new PaCompositeSectionParam(toolkit.createComposite(sctnParamtresDuTableau),toolkit);
		sctnParamtresDuTableau.setClient(compositeSectionParam.getCompo());


		sctnParamtresDuTableau.setTextClient(compositeSectionParam.getSectionToolbar().createControl(sctnParamtresDuTableau)); 
		//		compositeSectionParam.getSectionToolbar().add(myAction);
		//		compositeSectionParam.getSectionToolbar().update(true);

	}

	//****************************** Composite Section Chiffre d'Affaires ******************************//

	protected void createSectionMontant (FormToolkit toolkit, final ScrolledForm form){


		sctnMontant = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		sctnMontant.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));

		sctnMontant.setText("Chiffre d'affaires HT");


		compositeSectionMontant = new PaCompositeSectionMontant(toolkit.createComposite(sctnMontant),toolkit);
		compositeSectionMontant.getInfolabel().setText("Chiffre d'Affaires HT sur la période");

		sctnMontant.setClient(compositeSectionMontant.getCompo());
		sctnMontant.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}


	//****************************** Composite Section Documents ******************************//

	public void createSectionDoc (FormToolkit toolkit, final ScrolledForm form){

		sctnDocuments = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		sctnDocuments.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));

		sctnDocuments.setText("Documents transformés");
		//		sectionCA.setDescription("Chiffre d'affaire");

		compositeSectionDoc = new PaCompositeSectionDoc(toolkit.createComposite(sctnDocuments),toolkit);
		compositeSectionDoc.getLblNew1().setText("Devis transformés : ");
		compositeSectionDoc.getLblNew2().setText("Bons de commande transformés : ");
		compositeSectionDoc.getLblNew3().setText("Bons de livraison facturés : ");


		sctnDocuments.setClient(compositeSectionDoc.getCompo());
		sctnDocuments.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}

	//****************************** Composite Section Meilleurs Clients ******************************//

	public void createSectionTableauGauche (FormToolkit toolkit, final ScrolledForm form){

		sctnTableauGauche = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED);

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,false,false,2,1);
		gridData.minimumHeight = 150;
		gridData.heightHint = 150;
		sctnTableauGauche.setLayoutData(gridData);

		sctnTableauGauche.setText("Classement meilleurs clients");
		

		compositeSectionTableauGauche = new PaCompositeSectionTableauGauche(toolkit.createComposite(sctnTableauGauche),toolkit);
		
		sctnTableauGauche.setClient(compositeSectionTableauGauche.getCompo());
		sctnTableauGauche.setTextClient(compositeSectionTableauGauche.getSectionToolbar().createControl(sctnTableauGauche)); 
		sctnTableauGauche.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}

	//****************************** Composite Section Factures ******************************//


	public void createSectionJauge (FormToolkit toolkit, final ScrolledForm form){
		sctnJauge = toolkit.createSection(form.getForm().getBody(), Section.TWISTIE | Section.TITLE_BAR);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		sctnJauge.setLayoutData(gridData);		
		sctnJauge.setText("Suivi des Règlements");
		sctnJauge.setExpanded(true);
		toolkit.paintBordersFor(sctnJauge);

		compositeSectionJauge= new PaCompositeSectionJauge(toolkit.createComposite(sctnJauge),toolkit);
		compositeSectionJauge.getLblNew1().setText("Nombre de factures non totalement réglées : ");
		compositeSectionJauge.getLblNew2().setText("Montant des factures non totalement réglées : ");

		sctnJauge.setClient(compositeSectionJauge.getCompo());
	}



	//****************************** Composite Section Repartition ******************************//

	public void createSectionRepartition(FormToolkit toolkit, final ScrolledForm form){

		sctnRepartition = toolkit.createSection(form.getForm().getBody(), Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_repartition = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 2);
		sctnRepartition.setLayoutData(gd_repartition);
		toolkit.paintBordersFor(sctnRepartition);
		sctnRepartition.setText("Répartition des ventes par régions");
		sctnRepartition.setExpanded(true);
		sctnRepartition.setSize(300, 300);

		compositeSectionRepartition= new PaCompositeSectionRepartition(toolkit.createComposite(sctnRepartition),toolkit);

		sctnRepartition.setClient(compositeSectionRepartition.getCompo());
	}


	//****************************** Composite Section Meilleurs Articles ******************************//


	public void createSectionTableauDroit (FormToolkit toolkit, final ScrolledForm form){

		sctnTableauDroit = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR  | Section.EXPANDED); 

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,false,false,2,1);
		gridData.heightHint = 150;
		gridData.minimumHeight = 150;
		sctnTableauDroit.setLayoutData(gridData);

		sctnTableauDroit.setText("Classement meilleurs articles");
		//		sectionAutre.setDescription("Autre");
		
		compositeSectionTableauDroit = new PaCompositeSectionTableauDroit(toolkit.createComposite(sctnTableauDroit),toolkit);
		
		sctnTableauDroit.setClient(compositeSectionTableauDroit.getCompo());
		sctnTableauDroit.setTextClient(compositeSectionTableauDroit.getSectionToolbar().createControl(sctnTableauDroit)); 

		sctnTableauDroit.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
				
	}



	//****************************** Composite Section Evolution ******************************//

	public void createSectionEvolution(FormToolkit toolkit, final ScrolledForm form) {
		sctnEvolutionDuChiffre = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR| Section.EXPANDED);

		GridData gd_evolution = new GridData(SWT.FILL,SWT.FILL,true,true,3,1);
		gd_evolution.minimumHeight = 300;
		gd_evolution.heightHint = 300;
		
		sctnEvolutionDuChiffre.setLayoutData(gd_evolution);

		sctnEvolutionDuChiffre.setText("Evolution du chiffre d'affaires HT");
		//		sectionGraph.setDescription("Graphique");

		compositeSectionEvolution = new PaCompositeSectionEvolution(toolkit.createComposite(sctnEvolutionDuChiffre),toolkit);
		compositeSectionEvolution.getCompo().setLayoutData(gd_evolution);
		
		sctnEvolutionDuChiffre.setClient(compositeSectionEvolution.getCompo());
		sctnEvolutionDuChiffre.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}


	//****************************** Fin composites ******************************//


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

	/**
	 * Renvoi sous forme de string la date du début de la période
	 * @return une date sous forme jj/mm/aaaa
	 */
	private String datedebToString() {
		Date laDate = new Date();
		laDate = Outils.extractDate(getCompositeSectionParam().getCdateDeb());

		String laDateString = "";
		laDateString+= Integer.toString(laDate.getDate()) + "/" + 
		Integer.toString(laDate.getMonth()) + "/" +
		Integer.toString(laDate.getYear());
		return laDateString;
	}

	/**
	 * Renvoi sous forme de string la date de fin de la période
	 * @return une date sous forme jj/mm/aaaa
	 */
	private String datefinToString() {
		Date laDate = new Date();
		laDate = Outils.extractDate(getCompositeSectionParam().getCdatefin());

		String laDateString = "";
		laDateString+= Integer.toString(laDate.getDate()) + "/" + 
		Integer.toString(laDate.getMonth()) + "/" +
		Integer.toString(laDate.getYear());
		return laDateString;
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

	public PaCompositeSectionMontant getCompositeSectionMontant() {
		return compositeSectionMontant;
	}

	public PaCompositeSectionDoc getCompositeSectionDoc() {
		return compositeSectionDoc;
	}

	public PaCompositeSectionTableauGauche getCompositeSectionTableauGauche() {
		return compositeSectionTableauGauche;
	}

	public PaCompositeSectionJauge getCompositeSectionJauge() {
		return compositeSectionJauge;
	}

	public PaCompositeSectionRepartition getCompositeSectionRepartition() {
		return compositeSectionRepartition;
	}

	public PaCompositeSectionTableauDroit getCompositeSectionTableauDroit() {
		return compositeSectionTableauDroit;
	}

	public PaCompositeSectionEvolution getCompositeSectionEvolution() {
		return compositeSectionEvolution;
	}

	public FormPageControllerPrincipal getControllerPage() {
		return controllerPage;
	}

	public void setControllerPage(FormPageControllerPrincipal controllerPage) {
		this.controllerPage = controllerPage;
	}

	public Section getSctnMontant() {
		return sctnMontant;
	}

	public Section getSctnDocuments() {
		return sctnDocuments;
	}

	public Section getSctnJauge() {
		return sctnJauge;
	}

	public ScrolledForm getForm() {
		return form;
	}

	public Section getSctnTableauGauche() {
		return sctnTableauGauche;
	}

	public Section getSctnTableauDroit() {
		return sctnTableauDroit;
	}


}
