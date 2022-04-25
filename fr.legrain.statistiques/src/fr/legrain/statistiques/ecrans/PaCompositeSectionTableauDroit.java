/**
 * PaCompositeSectionArticles.java
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionTableauDroit {
	// Composite
	private Composite compo = null;
	private Table table = null;
	private ToolBarManager sectionToolbar = null;
	
	public static String iconpath = "/icons/folder_table.png";
	
	public PaCompositeSectionTableauDroit(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

		table = toolkit.createTable(compo, SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
		GridData gridDataTab = new GridData(SWT.FILL,SWT.FILL,true,false,2,1);
		gridDataTab.minimumHeight = 150;
		gridDataTab.heightHint = 150;
		table.setLayoutData(gridDataTab);
		

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 1;
		compo.setLayout(layout);
		
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}

	public Composite getCompo() {
		return compo;
	}

	public Table getTable() {
		return table;
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}
	
}
