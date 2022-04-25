package fr.legrain.sauvegardeFTP.preferences;

import java.io.File;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.sauvegardeFTP.divers.FonctionGeneral;

public class CompositeFieldEditor extends FieldEditor {

	private Composite top;
	private FonctionGeneral general;
	
	private CompositePreferencePage compositePreferencePage; 
	
	public CompositeFieldEditor(Composite parent,String properties, String labelText,FonctionGeneral general,
			CompositePreferencePage preferencePage) {
		super();
		this.compositePreferencePage = preferencePage;
		this.general = general;
		this.top = parent;
		init(properties, labelText);
		createControl(parent);
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		// TODO Auto-generated method stub
		GridData layoutData = (GridData) top.getLayoutData();
		layoutData.horizontalSpan = numColumns;
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		// TODO Auto-generated method stub

		top = parent;
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		parent.setLayoutData(gd);
//		Label lableTitle = this.getLabelControl(parent);
		
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.grabExcessHorizontalSpace = true;
	
			
		general.updateValueTextCompositePreferencePage(compositePreferencePage);
		compositePreferencePage.setLayoutData(gd);
	}

	@Override
	protected void doLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doLoadDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doStore() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNumberOfControls() {
		// TODO Auto-generated method stub
		return 1;
	}

	public FonctionGeneral getGeneral() {
		return general;
	}

	public void setGeneral(FonctionGeneral general) {
		this.general = general;
	}

}
