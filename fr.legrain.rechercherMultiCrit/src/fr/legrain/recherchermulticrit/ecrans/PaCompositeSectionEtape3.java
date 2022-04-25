/**
 * PaCompositeSectionEtape1.java
 */
package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.Activator;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionEtape3 {
	
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	private Composite compo = null;
	private CTabFolder dossierConteneur;
	private Text infoNbResult;

	public PaCompositeSectionEtape3(Composite compo,FormToolkit toolkit) {
		this.compo = compo;		

		dossierConteneur = new CTabFolder(compo, SWT.TOP | SWT.MULTI |SWT.BORDER );
		dossierConteneur.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(dossierConteneur);
		dossierConteneur.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		dossierConteneur.setSimple(false);
		dossierConteneur.setLayout(new GridLayout(1, false));
		
		compo.setLayout(new GridLayout(1, false));
	}

	public Composite getCompo() {
		return compo;
	}

	public static String getIconRefreshPath() {
		return iconRefreshPath;
	}

	public CTabFolder getDossierConteneur() {
		return dossierConteneur;
	}


	
}
