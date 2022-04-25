package fr.legrain.gestCom.librairiesEcran.workbench;

import org.eclipse.swt.widgets.Composite;

public interface IMessageEditeur {
	
	public static final String C_EXT_ATT_TYPE_AFFICHAGE_COMPOSITE = "COMPOSITE";
	public static final String C_EXT_ATT_TYPE_AFFICHAGE_BROWSER = "BROWSER";
	
	public void afficheMessage(Composite parent);
}
