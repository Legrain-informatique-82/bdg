/**
 * PaCompositeSectionCA.java
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author posttest
 *
 */
public class PaCompositeSectionMontant {
	
	private Composite compo = null;
	private Label label = null;
	private Label infolabel = null;

	public PaCompositeSectionMontant(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

		infolabel = toolkit.createLabel(compo, "Montant HT sur la période : "); //$NON-NLS-1$
		label = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,false,2,1));
		label.setFont(new Font(Display.getDefault(),"tahoma",16,SWT.RIGHT));

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		compo.setLayout(layout);
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
	
}
