package fr.legrain.tiers.statistiques.editors;

import org.eclipse.ui.forms.IManagedForm;

import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.tiers.statistiques.Activator;
import fr.legrain.tiers.statistiques.editors.essais.Messages;
import fr.legrain.tiers.statistiques.editors.essais.ScrolledPropertiesBlock;

public class EssaisFormPage extends FormPage {

	public static String id = "fr.legrain.tiers.statistiques.editors.EssaisFormPage";
	public static String title = Messages.getString("MasterDetailsPage.label");//"Essais Forms";
	private ScrolledPropertiesBlock block;
	
	public EssaisFormPage(FormEditor editor, String id, String title) {
	
//	public FirstFormPage(EditorPart editor, String id, String title) {
		
		super((FormEditor) editor, id, title);
		// TODO Auto-generated constructor stub
		block = new ScrolledPropertiesBlock(this);
	}
	
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		// TODO Auto-generated method stub
		//super.createFormContent(managedForm);
		try {
			ScrolledForm form = managedForm.getForm();
			form.setText(Messages.getString("MasterDetailsPage.title"));
//			form.setBackgroundImage(Activator.getImageDescriptor(Activator.IMG_FORM_BG).createImage());
			block.createContent(managedForm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
