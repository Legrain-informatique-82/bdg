package fr.legrain.edition.divers;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 * 
 * Date : March 2008
 */
public class PreferencePersoFieldEditorExample {
	public PreferencePersoFieldEditorExample() {
		Display myDisplay = new Display();

		PreferenceManager mgr = new PreferenceManager();

//		PreferenceNode three = new PreferenceNode("three",
//				new PreferenceFieldEditorPageThree());
//		PreferenceNode four = new PreferenceNode("four",
//				new PreferenceFieldEditorPageFour());
//
//		mgr.addToRoot(three);
//		mgr.addToRoot(four);

		PreferenceDialog myPreferenceDialog = new PreferenceDialog(null, mgr);

		PreferenceStore ps = new PreferenceStore("persofieldprefs.properties");
		try {
			ps.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		myPreferenceDialog.setPreferenceStore(ps);
		myPreferenceDialog.open();

		try {
			ps.save();
		} catch (IOException e) {
			e.printStackTrace();
		}

		myDisplay.dispose();
	}

	public static void main(String[] args) {
		new PreferencePersoFieldEditorExample();
	}
}