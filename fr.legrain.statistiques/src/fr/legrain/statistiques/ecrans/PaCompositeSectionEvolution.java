/**
 * PaCompositeSectionEvolution
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author posttest
 *
 */

public class PaCompositeSectionEvolution {
	private Composite compo = null;
//	private Text text = null;

	public PaCompositeSectionEvolution(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

//		toolkit.createLabel(compo, "test label"); //$NON-NLS-1$
//		text = toolkit.createText(compo, "test text", SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
//		createPieChart(compo);
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		compo.setLayout(layout);
	}

	public Composite getCompo() {
		return compo;
	}

//	public Text getText() {
//		return text;
//	}
}
