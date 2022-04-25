/**
 * PaCompositeSectionDoc.java
 */
package fr.legrain.licence.ecrans;

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
public class PaCompositeSectionDoc {
	// Composite
	private Composite compo = null;
	private Label lblNew1_content = null;
	private Label lblNew1 = null;
	private Label lblNew2_content = null;
	private Label lblNew2 = null;
	private Label lblNew3_content = null;
	private Label lblNew3 = null;
	private Label lblNew4 = null;
	private Label lblNew4_content = null;
	private Label lblNew5 = null;
	private Label lblNew5_content = null;
	private Label lblNew6 = null;
	
	public PaCompositeSectionDoc(Composite compo,FormToolkit toolkit) {
		// Initilisation du composite
		this.compo = compo;
		toolkit.paintBordersFor(compo);
		
		lblNew1 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew1_content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew1_content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		lblNew2 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew2_content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew2_content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		lblNew3 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew3_content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew3_content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		lblNew4 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew4_content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew4_content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		lblNew5 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		lblNew5_content = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
		lblNew5_content.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		lblNew6 = toolkit.createLabel(compo, ""); //$NON-NLS-1$
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		compo.setLayout(layout);
	}
	

	/* getter sur le composite */
	public Composite getCompo(){
		return compo;
	}
	
	/* getter sur le contenu du premier label */
	public Label getLblNew1Content() {
		return lblNew1_content;
	}
	
	/*  getter sur le contenu du 3e label */
	public Label getLblNew3Content() {
		return lblNew3_content;
	}
	
	/*  getter sur le contenu du 2e label */
	public Label getLblNew2Content() {
		return lblNew2_content;
	}

	/*  getter sur le contenu du 4e label */
	public Label getLblNew4Content() {
		return lblNew4_content;
	}


	public Label getLblNew1() {
		return lblNew1;
	}


	public Label getLblNew2() {
		return lblNew2;
	}


	public Label getLblNew3() {
		return lblNew3;
	}


	public Label getLblNew4() {
		return lblNew4;
	}


	public Label getLblNew5() {
		return lblNew5;
	}


	public Label getLblNew5Content() {
		return lblNew5_content;
	}
	
	
	public Label getLblNew6() {
		return lblNew6;
	}

}
