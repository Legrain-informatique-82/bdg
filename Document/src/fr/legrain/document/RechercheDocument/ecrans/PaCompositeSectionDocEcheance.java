/**
 * PaCompositeSectionClients.java
 */
package fr.legrain.document.RechercheDocument.ecrans;

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
public class PaCompositeSectionDocEcheance {
	// Composite
	private Composite compo = null;
	private Table table = null;
	private Table tableDetail = null;
	private ToolBarManager sectionToolbar = null;
	public static String iconpath = "/icons/printer.png";
	
	public PaCompositeSectionDocEcheance(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

		table = toolkit.createTable(compo, SWT.FULL_SELECTION|SWT.SINGLE|SWT.BORDER ); //$NON-NLS-1$
		GridData gridDataTab = new GridData(SWT.FILL,SWT.FILL,true,true,2,1);
		gridDataTab.minimumHeight = 300;
		gridDataTab.heightHint = 150;
		table.setLayoutData(gridDataTab);
		
		tableDetail = toolkit.createTable(compo, SWT.FULL_SELECTION|SWT.SINGLE|SWT.BORDER ); //$NON-NLS-1$
		GridData gridDataTabDetail = new GridData(SWT.FILL,SWT.FILL,true,true,2,1);
		gridDataTabDetail.minimumHeight = 300;
		gridDataTabDetail.heightHint = 150;
		tableDetail.setLayoutData(gridDataTabDetail);

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

	public Table getTableDetail() {
		return tableDetail;
	}
	
}
