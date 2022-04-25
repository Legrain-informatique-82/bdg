package fr.legrain.sauvegardeFTP.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.sauvegardeFTP.Activator;
import fr.legrain.sauvegardeFTP.divers.FonctionGeneral;

public class SauvegardeFTPPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	private FonctionGeneral general;
	private CompositePreferencePage preferencePage;
	private CompositeFieldEditor compositeFieldEditor;
	
	public SauvegardeFTPPreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(PreferenceConstants.DESCRIPTION);
	}
	
	public SauvegardeFTPPreferencePage(int style){
		super(style);
	}
	
	public SauvegardeFTPPreferencePage(String title, ImageDescriptor image,
			int style){
		super(title,image,style);
	}

	public SauvegardeFTPPreferencePage(String title, int style){
		super(title,style);
	}
	
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub

//		general = new FonctionGeneral("21");
		general = new FonctionGeneral();
		general.getInfosFileProperties(Const.C_FICHIER_PREFERENCE_PAGE_FTP);
		preferencePage = new CompositePreferencePage(getFieldEditorParent(),SWT.NULL);
		preferencePage.getTextPortServerFTP().setText("21");
		
		compositeFieldEditor = new CompositeFieldEditor(getFieldEditorParent(), 
													"parametreFTP","",general,preferencePage);
//		compositeFieldEditor.get
		
		addField(compositeFieldEditor);
		
	}
	/** pour Button Apply **/
	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		performOk();
		
	}


	
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performOk() {
		//super.performApply();
		general.sauvegardeInfosValueProperties(Const.C_FICHIER_PREFERENCE_PAGE_FTP,preferencePage);
		return super.performOk();
	}

	

}
