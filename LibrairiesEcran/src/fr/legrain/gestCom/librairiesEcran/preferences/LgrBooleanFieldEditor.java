package fr.legrain.gestCom.librairiesEcran.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class LgrBooleanFieldEditor extends BooleanFieldEditor {

	public LgrBooleanFieldEditor(String utiliseBoutique, String string,
			Composite fieldEditorParent) {
		super(utiliseBoutique,string,fieldEditorParent);
	}

	public Button getCheckbox(Composite parent) {
		return getChangeControl(parent);
		
	}
}
