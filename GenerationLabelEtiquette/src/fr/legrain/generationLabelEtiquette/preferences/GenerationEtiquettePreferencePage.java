package fr.legrain.generationLabelEtiquette.preferences;

import generationlabeletiquette.Activator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;



public class GenerationEtiquettePreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private RadioGroupFieldEditor radioGroupFieldEditorEtiquette;
	
	
	public GenerationEtiquettePreferencePage() {
		//super();

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(PreferenceConstants.DESCRIPTION);
	}
	
	public GenerationEtiquettePreferencePage(int style) {
		super(style);
		// TODO Auto-generated constructor stub
	}
	
	public GenerationEtiquettePreferencePage(String title, int style) {
		super(title, style);
		// TODO Auto-generated constructor stub
	}
	
	public GenerationEtiquettePreferencePage(String title,ImageDescriptor image, int style) {
		super(title, image, style);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		
		addField(new StringFieldEditor(PreferenceConstants.LEFT_MARGIN,PreferenceConstants.MESSAGE_LEFT_MARGIN,getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.RIGHT_MARGIN,PreferenceConstants.MESSAGE_RIGHT_MARGIN,getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.TOP_MARGIN,PreferenceConstants.MESSAGE_TOP_MARGIN,getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.BOTTOM_MARGIN,PreferenceConstants.MESSAGE_BOTTOM_MARGIN,getFieldEditorParent()));

	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
