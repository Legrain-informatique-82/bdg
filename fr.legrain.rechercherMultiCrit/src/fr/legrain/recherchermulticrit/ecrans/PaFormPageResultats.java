/**
 * PaFormPageResultats
 */
package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.recherchermulticrit.controllers.FormPageController;
import fr.legrain.recherchermulticrit.controllers.FormPageControllerResultats;

/**
 * Form Page contenant les onglets de résultats
 * @author nicolas²
 *
 */
public class PaFormPageResultats extends FormPage{
	
	// -- ids formulaire --
	public static String id = "fr.legrain.recherchermulticrit";
	public static String title = "Résultats";
	
	// -- Form et Toolkit --
	private ScrolledForm form = null;
	private FormToolkit toolkit = null;
	private FormPageControllerResultats controllerPage = null;

	
	// -- Sections --
	private Section sctnEtape3;
	
	// -- Composites --
	private PaCompositeSectionEtape3 composite_Etape3;

	/**
	 * @param id
	 * @param title
	 */
	public PaFormPageResultats(String id, String title) {
		super(id, title);
	}

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public PaFormPageResultats(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}
	
	/**
	 * Create contents of the form.
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// -- En tête de la fenêtre --
		toolkit = managedForm.getToolkit();
		form = managedForm.getForm();
		form.setText("Exploitation des résultats");
		Composite body = form.getBody();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(body);
		form.getBody().setLayout(new GridLayout(1, false));
		
		sctnEtape3 = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnEtape3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(sctnEtape3);
		sctnEtape3.setText("Etape 3 : J'exploite les résultats");
		sctnEtape3.setExpanded(true);
		
		composite_Etape3 = new PaCompositeSectionEtape3(toolkit.createComposite(sctnEtape3),toolkit);
		sctnEtape3.setClient(composite_Etape3.getCompo());
		
		controllerPage.appel();
	
		
	}

	public Section getSctnEtape3() {
		return sctnEtape3;
	}

	public PaCompositeSectionEtape3 getComposite_Etape3() {
		return composite_Etape3;
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

	public FormToolkit getToolkit() {
		return toolkit;
	}

	public void setControllerPage(
			FormPageControllerResultats masterController) {
		this.controllerPage = masterController;
		
	}

}
