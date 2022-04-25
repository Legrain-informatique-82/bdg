/**
 * PaCompositeSectionDoc.java
 */
package fr.legrain.abonnement.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionComplementaire {
	// Composite
	private Composite compo = null;
	private Table grille = null;
	private Label laGrille=null;
	
	public PaCompositeSectionComplementaire(Composite compo,FormToolkit toolkit) {
		// Initilisation du composite
		this.compo = compo;
		toolkit.paintBordersFor(compo);
		
		laGrille = toolkit.createLabel(compo, "Liste des types d'abonnements"); //$NON-NLS-1$
		grille = toolkit.createTable(compo, SWT.FULL_SELECTION|SWT.SINGLE|SWT.BORDER ); //$NON-NLS-1$
		GridData gridDataTab = new GridData(SWT.FILL,SWT.FILL,true,false,2,1);
		gridDataTab.minimumHeight = 150;
		gridDataTab.heightHint = 150;
		grille.setLayoutData(gridDataTab);		

		
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 1;
		compo.setLayout(layout);
	}
	

	/* getter sur le composite */
	public Composite getCompo(){
		return compo;
	}
	
	

	public Table getGrille() {
		return grille;
	}


	public Label getLaGrille() {
		return laGrille;
	}

}
