package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.CTabItem;

import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.controllers.FormPageController;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;

public class PaFormPage extends FormPage {
	// -- ids formulaire --
	public static String id = "fr.legrain.recherchermulticrit";
	public static String title = "Recherche";
	
	// -- Form et Toolkit --
	private ScrolledForm form = null;
	private FormToolkit toolkit = null;
	private FormPageController controllerPage = null;
	
	// -- Chemins des icônes --
	public static String iconPath = "/icons/chart_bar.png";
	
	// -- Sections --
	private Section sctnEtape1;
	private Section sctnEtape2;
	private Section sctnSauverCharger;
	
	// -- Composites --
	private PaCompositeSectionEtape1 composite_Etape1 = null;
	private PaCompositeSectionSauverCharger composite_SauverCharger = null;
	private PaCompositeSectionEtape2 composite_Etape2 = null;
	

	/**
	 * Create the form page.
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
		// -- En tête de la fenêtre --
		toolkit = managedForm.getToolkit();
		form = managedForm.getForm();
		form.setText("Recherche par critères");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		form.getBody().setLayout(new GridLayout(2, false));
		
		
		// -- Première Etape --
		sctnEtape1 = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnEtape1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.paintBordersFor(sctnEtape1);
		sctnEtape1.setText("Etape 1 : Je choisis ce que j'affiche");
		sctnEtape1.setExpanded(true);
		
		composite_Etape1 = new PaCompositeSectionEtape1(toolkit.createComposite(sctnEtape1),toolkit);
		sctnEtape1.setClient(composite_Etape1.getCompo());
		
		// -- Sauver Charger --
		sctnSauverCharger = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnSauverCharger.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.paintBordersFor(sctnSauverCharger);
		sctnSauverCharger.setText("Etape facultative : Je charge / sauvegarde mes résultats");
		sctnSauverCharger.setExpanded(true);
		
		composite_SauverCharger = new PaCompositeSectionSauverCharger(toolkit.createComposite(sctnSauverCharger),toolkit);
		sctnSauverCharger.setClient(composite_SauverCharger.getCompo());
		
			
		// -- Deuxième Etape --
		sctnEtape2 = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnEtape2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		toolkit.paintBordersFor(sctnEtape2);
		sctnEtape2.setText("Etape 2 : Je choisis les critères");
		sctnEtape2.setExpanded(true);
		
		composite_Etape2 = new PaCompositeSectionEtape2(toolkit.createComposite(sctnEtape2),toolkit);
		sctnEtape2.setClient(composite_Etape2.getCompo());
		
		controllerPage.appel();
		
		

	}

	public void setControllerPage(FormPageController leController) {
		this.controllerPage = leController;
		
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

	public Section getSctnEtape1() {
		return sctnEtape1;
	}

	public Section getSctnEtape2() {
		return sctnEtape2;
	}

	public PaCompositeSectionEtape1 getComposite_Etape1() {
		return composite_Etape1;
	}

	public PaCompositeSectionEtape2 getComposite_Etape2() {
		return composite_Etape2;
	}



	public FormToolkit getToolkit() {
		return toolkit;
	}



	public ScrolledForm getForm() {
		return form;
	}



	public Section getSctnSauverCharger() {
		return sctnSauverCharger;
	}



	public PaCompositeSectionSauverCharger getComposite_SauverCharger() {
		return composite_SauverCharger;
	}
}
