package fr.legrain.gestCom.Module_Stocks.IB_TABLES;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessagesGestCom {
	private static final String BUNDLE_NAME = "fr.legrain.gestCom.Module_Stocks.IB_TABLES.MessagesGestCom"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private MessagesGestCom() {
	}

	public static String getString(String key) {
		// TODO Raccord de méthode auto-généré
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
