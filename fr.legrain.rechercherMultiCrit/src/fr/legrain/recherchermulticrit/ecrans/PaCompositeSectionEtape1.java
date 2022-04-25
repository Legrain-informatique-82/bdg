/**
 * PaCompositeSectionEtape1.java
 */
package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.Activator;

/**
 * @author posttest
 *
 */
public class PaCompositeSectionEtape1 {
	
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	private Composite compo = null;
	private Label label = null;
	private Label infolabel = null;
	
	private Label lblJeSouhaiteAfficher =  null;
	private Combo combo = null;
	private Button button = null;

	public PaCompositeSectionEtape1(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

		// Elements graphiques 
		lblJeSouhaiteAfficher = toolkit.createLabel(compo, "Je souhaite afficher  des résultats contenant des ");
		lblJeSouhaiteAfficher.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo = new Combo(compo,SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		
		button = toolkit.createButton(compo, "Réinitialiser", SWT.NONE);
		button.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape1.iconRefreshPath).createImage());
		new Label(compo, SWT.NONE);
		
		
		// Layout
		GridLayout layout_Etape1 = new GridLayout();
		layout_Etape1.marginWidth = layout_Etape1.marginHeight = 10;
		layout_Etape1.numColumns = 2;
		compo.setLayout(layout_Etape1);
	}

	public Composite getCompo() {
		return compo;
	}

	public Label getText() {
		return label;
	}

	public Label getInfolabel() {
		return infolabel;
	}

	public Label getLblJeSouhaiteAfficher() {
		return lblJeSouhaiteAfficher;
	}

	public Combo getCombo() {
		return combo;
	}

	public Button getButton() {
		return button;
	}
	
}
