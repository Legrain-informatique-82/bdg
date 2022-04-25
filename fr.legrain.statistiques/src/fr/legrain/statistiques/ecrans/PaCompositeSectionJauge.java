/**
 * 
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionJauge {
	// Composite
	private Composite compo = null;
	private Composite compoInterieur = null ;
	
	private Label lblNew1 = null;
	private Label lblNew2 = null;
	
	private Label lblNew1Content = null;
	private Label lblNew2Content = null;
	
	
	public PaCompositeSectionJauge(Composite compo,FormToolkit toolkit) {
		// Initilisation du composite
		this.compo = compo;
		toolkit.paintBordersFor(compo);
		
		
		/* Partie "nombre de devis non transformés */
		lblNew1 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew1Content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew1Content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

		/* Partie "montant des factures non réglées */
		lblNew2 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew2Content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew2Content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		/* Composite contenant le graphique */
		compoInterieur = toolkit.createComposite(compo);
		compoInterieur.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		compoInterieur.setLayout(layout);
		compo.setLayout(layout);
	}
	

	/* getter sur le composite */
	public Composite getCompo(){
		return compo;
	}
	
	/* getter sur le composite intérieur */
	public Composite getCompoInterieur(){
		return compoInterieur;
	}

	/* getter sur le mt des factures non réglées */
	public Label getLblNew1Content() {
		return lblNew1Content;
	}


	/* getter sur le mt des factures non réglées */
	public Label getLblNew2Content() {
		return lblNew2Content;
	}


	public Label getLblNew1() {
		return lblNew1;
	}


	public Label getLblNew2() {
		return lblNew2;
	}
	

	
}

