/**
 * PaSectionParam.java
 */
package fr.legrain.etats.ecrans;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.etats.Activator;
import fr.legrain.etats.controllers.Etat;


public class PaCompositeGroupeParametres {
	// icons
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	
	// Composite
	private Composite compo = null;

	// Elements graphiques
	private Label label = null;
	private ToolBarManager sectionToolbar = null;
	private Button btnRefesh = null;
	private ImageHyperlink ihlRefresh = null;
	private PaFormPage paFormPage;

	public PaCompositeGroupeParametres(Composite compo,FormToolkit toolkit) {
		// Init du composite
		this.compo = compo;

//		label = toolkit.createLabel(compo, "Etat"); 
//		label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,4,1));
//
//		ihlRefresh = toolkit.createImageHyperlink(compo, SWT.NULL);
//		//ihlRefresh.setText("test");
//		//ihlRefresh.setActiveImage(Activator.getImageDescriptor(PaCompositeGroupeEtat1.iconRefreshPath).createImage());
//		ihlRefresh.setImage(Activator.getImageDescriptor(PaCompositeGroupeParametres.iconRefreshPath).createImage());
//		ihlRefresh.setHoverImage(Activator.getImageDescriptor(PaCompositeGroupeParametres.iconPath).createImage());
//		//ihlRefresh.setHoverImage(Activator.getImageDescriptor(PaCompositeGroupeEtat1.iconPath).createImage());
//		
//		ImageHyperlink h1 = toolkit.createImageHyperlink(compo, SWT.NULL);
//		h1.setText("test 2");

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 1;
		compo.setLayout(layout);
		
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}
	
	

	public Composite getCompo() {
		return compo;
	}

	public Label getLabel() {
		return label;
	}
	
	public Button getBtnRefesh() {
		return btnRefesh;
	}
	
	public ImageHyperlink getIhlRefresh() {
		return ihlRefresh;
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}



	public void setPaFormPage(PaFormPage paFormPage) {
		this.paFormPage = paFormPage;
	}
}
