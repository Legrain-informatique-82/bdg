package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

public class ListEditorSimple extends ListEditor{

	public ListEditorSimple(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String createList(String[] items) {
		String  retour="";
		for (int i = 0; i < items.length; i++) {
			retour+=items[i]+";";
		}		
		return retour;
	}

	@Override
	protected String getNewInputObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] parseString(String stringList) {
		String[] list =stringList.split(";");
		return list;
	}
	
}
