package fr.legrain.facture.preferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;

import fr.legrain.document.divers.ListEditorGestionTypeNotesTiersDocument;
import fr.legrain.document.divers.ListEditorGestionTypeTiersDocument;
import fr.legrain.document.divers.ListEditorGestionWidthEdition;
import fr.legrain.edition.divers.FieldEditorPreferencePageLGR;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.ListEditorAddRemoveEditLgr;
import fr.legrain.gestCom.librairiesEcran.swt.StringCommentaireEditor;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class LgrFacturePreferencePage extends FieldEditorPreferencePageLGR{


	private ListEditorAddRemoveEditLgr listeTypeTiers = null;
	
	private ListEditorAddRemoveEditLgr listeWidthEdition = null;

	@Override
	protected void performDefaults() {
		PreferenceInitializerProject.initDefautProperties();
		super.performDefaults();
	}

	//static private PropertiesConfiguration listeGestCode = new PropertiesConfiguration();
	static Logger logger = Logger.getLogger(LgrFacturePreferencePage.class.getName());

	public LgrFacturePreferencePage() {
		super(GRID);
		String idPlugin = FacturePlugin.getDefault().getBundle().getSymbolicName();

		this.setNamePlugin(idPlugin);
		setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore());
		setDescription("Paramètres de codification des factures");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
				new StringFieldEditor(PreferenceConstants.TA_FACTURE_FIXE_1, "fixe", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TA_FACTURE_EXO, "exo", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.TA_FACTURE_COMPTEUR, "Compteur", getFieldEditorParent()));
		//		addField(
		//				new BooleanFieldEditor(PreferenceConstants.IMPRIMER_AUTO, "Impression automatique", getFieldEditorParent()));
		addField(
				new StringCommentaireEditor(PreferenceConstants.COMMENTAIRE, "Commentaire à insérer", getFieldEditorParent()));


		createListeTypeTiers();	
		
		createListeTypeNotesTiers();	

		createListeWidthEdition();
	}
	
	
	@Override
	protected void performApply() {
		super.performApply();


		/********************/
		PropertiesConfiguration listeGestCode = new PropertiesConfiguration() ;
		//File fProp = new File(Const.C_FICHIER_GESTCODE) ;

		// Charge le contenu de ton fichier properties dans un objet Properties
		FileInputStream stream;
		try {
			stream = new FileInputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.load(stream) ;
			stream.close();
			String taNouvelleValeur=null;
			//			IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStore();

			// Change la valeur de la clé taCle dans l'objet Properties
			taNouvelleValeur=FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_FACTURE_FIXE_1) ;
			listeGestCode.setProperty(PreferenceConstants.TA_FACTURE_FIXE_1,taNouvelleValeur) ;

			taNouvelleValeur=FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_FACTURE_EXO) ;
			listeGestCode.setProperty(PreferenceConstants.TA_FACTURE_EXO,taNouvelleValeur) ;

			taNouvelleValeur=FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TA_FACTURE_COMPTEUR) ;
			listeGestCode.setProperty(PreferenceConstants.TA_FACTURE_COMPTEUR,taNouvelleValeur) ;

			// Charge le contenu de ton objet Properties dans ton fichier properties
			FileOutputStream oStream = new FileOutputStream(Const.C_FICHIER_GESTCODE);
			listeGestCode.save(oStream,null) ;
			oStream.close();

		} catch (ConfigurationException e) {
			logger.error("",e);

		} catch (IOException e) {
			logger.error("",e);
		}
	}


	public void createListeTypeTiers(){
		try {//
			Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());

			listeTypeTiers = new ListEditorGestionTypeTiersDocument(
					PreferenceConstants.TYPE_TIERS_DOCUMENT, "Liste des tiers à utiliser",
					"Ajouter","Supprimer",getFieldEditorParent(),realm);
			addField(listeTypeTiers);
		} catch (Exception e1) {
			logger.error("", e1);
		}	
	}
	
	public void createListeTypeNotesTiers(){
		try {//
			Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());

			listeWidthEdition = new ListEditorGestionTypeNotesTiersDocument(
					PreferenceConstants.TYPE_NOTES_TIERS_DOCUMENT, "Liste des notes tiers à afficher",
					"Ajouter","Supprimer",getFieldEditorParent(),realm);
			addField(listeWidthEdition);
		} catch (Exception e1) {
			logger.error("", e1);
		}	
	}
	
	public void createListeWidthEdition(){
//		try {//
//			Realm realm = SWTObservables.getRealm(getFieldEditorParent().getDisplay());
//
//			listeWidthEdition = new ListEditorGestionWidthEdition("Largeur des champs dans l'édition", 
//					"Largeur des champs dans l'édition",getFieldEditorParent(),realm);
//			addField(listeWidthEdition);
//		} catch (Exception e1) {
//			logger.error("", e1);
//		}
		
	}
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		setPreferenceStore(FacturePlugin.getDefault().getPreferenceStore()); 
	}
}