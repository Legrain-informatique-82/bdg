/**
 * PaSectionParam.java
 */
package fr.legrain.etats.ecrans;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import fr.legrain.etats.controllers.Etat;


public class PaCompositeListeEtat1 {
	// icons
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	
	// Composite
	private Composite compo = null;

	// Elements graphiques
	private Label label = null;
	private ToolBarManager sectionToolbar = null;
	private Button btnEtat = null;
	
	private FormToolkit toolkit = null;
	
	private PaCompositeGroupeParametres compoParam = null;
	
	private List<Etat> etats = new LinkedList<Etat>();
	
	private PaFormPage paFormPage;
	
	private static Image oldImage = null;

	public static void applyGradientBG(Composite composite) {
		Rectangle rect = composite.getClientArea();
		Image newImage = new Image(composite.getDisplay(), 1, Math.max(1,rect.height));
		GC gc = new GC(newImage);
		gc.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gc.setBackground(new org.eclipse.swt.graphics.Color(composite.getDisplay(), 228, 234, 243));
		gc.fillGradientRectangle(0, 0, 1, rect.height, true);
		gc.dispose();
		composite.setBackgroundImage(newImage);

		if (oldImage != null)
			oldImage.dispose();
		oldImage = newImage;
	}

	public PaCompositeListeEtat1(Composite compo, FormToolkit toolkit) {
		this.compo = compo;
		this.toolkit = toolkit;

		applyGradientBG(this.compo);
		//compo.setBackgroundImage(Activator.getImageDescriptor(PaCompositeGroupeEtat1.iconPath).createImage());

		Composite cmp = toolkit.createComposite(compo);
		//createEtatButton(cmp,toolkit);
		
		//testCompositeParam(cmp);
		//test(compo);

		
		//GridLayout layout = new GridLayout();
		RowLayout layout = new RowLayout();
		layout.type = SWT.HORIZONTAL;
		//layout.marginWidth = layout.marginHeight = 0;
		//layout.numColumns = 4;
		compo.setLayout(layout);
		
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}
	
//	public void testCompositeParam(Composite c) {
//		Etat etat = new EtatTest();
//		etat.initIHM(c);
//	}
	
	//public void createEtatButton(final Composite c, FormToolkit toolkit) {
	public void createEtatButton() {
		Control[] controls = compo.getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].dispose();
		}
		//etats.add(new EtatTest());
		for (final Etat e : etats) {
			//Button b = btnEtat = toolkit.createButton(compo, e.getName(), SWT.PUSH); //$NON-NLS-1$
			Button b = btnEtat = toolkit.createButton(compo, "", SWT.PUSH); //$NON-NLS-1$
//			GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
//			btnRefeshGridData.widthHint = 100;
//			btnRefeshGridData.heightHint = 100;
//			btnRefeshGridData.horizontalAlignment = SWT.FILL;
			RowData btnRefeshGridData = new RowData(100, 100);
			btnEtat.setImage(e.getIcon());
			btnEtat.setToolTipText(e.getName());
			btnEtat.setLayoutData(btnRefeshGridData);
			
			btnEtat.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent ev) {
					selectionEtat(e);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent ev) {
					widgetSelected(ev);
				}
			});			
		}
		compo.layout();
		paFormPage.reflow();
	}
	
	public void selectionEtat(Etat e) {
		e.initIHM(compoParam.getCompo(),toolkit);
		paFormPage.reflow();
	}

	public Composite getCompo() {
		return compo;
	}

	public Label getLabel() {
		return label;
	}
	
	public Button getBtnEtat() {
		return btnEtat;
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}

	public void setCompoParam(PaCompositeGroupeParametres compoParam) {
		this.compoParam = compoParam;
	}

	public void setEtats(List<Etat> etats) {
		this.etats = etats;
	}

	public void setPaFormPage(PaFormPage paFormPage) {
		this.paFormPage = paFormPage;
	}
}
