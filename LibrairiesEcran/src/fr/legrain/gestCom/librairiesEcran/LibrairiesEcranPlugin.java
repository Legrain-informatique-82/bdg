package fr.legrain.gestCom.librairiesEcran;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The main plugin class to be used in the desktop.
 */
public class LibrairiesEcranPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static LibrairiesEcranPlugin plugin;

	public static final  String IMAGE_ANNULER = "IMAGE_ANNULER";
	public static final  String IMAGE_ENREGISTRER = "IMAGE_ENREGISTRER";
	public static final  String IMAGE_FERMER = "IMAGE_FERMER";
	public static final  String IMAGE_INSERER = "IMAGE_INSERER";
	public static final  String IMAGE_MODIFIER = "IMAGE_MODIFIER";
	public static final  String IMAGE_SUPPRIMER = "IMAGE_SUPPRIMER";
	public static final  String IMAGE_IMPRIMER ="IMAGE_IMPRIMER";
	public static final  String IMAGE_ACCEPTER = "IMAGE_ACCEPTER";
	public static final  String IMAGE_EXCLAMATION = "IMAGE_EXCLAMATION";
	public static final  String IMAGE_RECHERCHER = "IMAGE_RECHERCHER";
	public static final  String IMAGE_RECHERCHER_JUMELLE = "IMAGE_RECHERCHER_JUMELLE";
	public static final  String IMAGE_GENERE = "IMAGE_GENERE";
	public static final String IMAGE_EXPORT = "IMAGE_EXPORT";
	public static final String IMAGE_REINITIALISER = "IMAGE_REINITIALISER";
	
	public static final  String IMAGE_SMS ="IMAGE_SMS";
	public static final  String IMAGE_EMAIL ="IMAGE_EMAIL";

	public static final String IMAGE_ADRESSE_FAC = "IMAGE_ADRESSE_FAC";
	public static final String IMAGE_ADRESSE_LIV = "IMAGE_ADRESSE_LIV";
	public static final String IMAGE_CONDITION_PAIEMENT = "IMAGE_CONDITION_PAIEMENT";
	public static final String IMAGE_IDENTITE_TIERS = "IMAGE_IDENTITE_TIERS";

	public static final String IMAGE_GEN_DOCUMENT = "IMAGE_GEN_DOCUMENT";
	public static final String IMAGE_GEN_MODELE = "IMAGE_GEN_MODELE";
	public static final String IMAGE_TOOLS = "IMAGE_TOOLS";
	public static final String IMAGE_REPERTOIRE = "IMAGE_REPERTOIRE";
	public static final String IMAGE_ERROR = "IMAGE_ERROR";
	public static final String IMAGE_TICK = "IMAGE_TICK";
	public static final  String IMAGE_DEVIS="IMAGE_DEVIS";
	


	protected String C_IMAGE_ANNULER = "/icons/cancel.png";
	protected String C_IMAGE_ENREGISTRER = "/icons/disk.png";
	protected String C_IMAGE_FERMER = "/icons/door_out.png";
	protected String C_IMAGE_INSERER = "/icons/add.png";
	protected String C_IMAGE_MODIFIER = "/icons/page_white_edit.png";
	protected String C_IMAGE_SUPPRIMER = "/icons/delete.png";
	protected String C_IMAGE_IMPRIMER = "/icons/printer.png";
	protected String C_IMAGE_ACCEPTER = "/icons/accept.png";
	protected String C_IMAGE_EXCLAMATION = "/icons/exclamation.png";
	protected String C_IMAGE_RECHERCHER = "/icons/magnifier.png";
	protected String C_IMAGE_RECHERCHER_JUMELLE = "/icons/page_white_find.png";
	protected String C_IMAGE_REINITIALISER = "/icons/arrow_refresh.png";
	protected String C_IMAGE_GENERE = "/icons/page_white_gear.png";
	final String C_IMAGE_EXPORT = "/icons/export_wiz.gif";
	protected String C_IMAGE_ADRESSE_FAC="/icons/book_open.png";
	protected String C_IMAGE_ADRESSE_LIV="/icons/lorry.png";
	protected String C_IMAGE_CONDITION_PAIEMENT="/icons/creditcards.png";
	protected String C_IMAGE_IDENTITE_TIERS="/icons/user.png";

	protected String C_IMAGE_GEN_DOCUMENT="/icons/table_relationship.png";
	protected String C_IMAGE_GEN_MODELE="/icons/template.png";
	protected String C_IMAGE_TOOLS="/icons/wrench.png";
	protected String C_IMAGE_REPERTOIRE="/icons/folder.png";
	protected String C_IMAGE_ERROR="/icons/error.png";
	protected String C_IMAGE_TICK="/icons/tick.png";
	
	protected String C_IMAGE_SMS="/icons/phone.png";
	protected String C_IMAGE_EMAIL="/icons/email.png";
	protected String C_IMAGE_DEVIS="/icons/money.png";
	
	
	public static ImageRegistry ir = new ImageRegistry();

	/**
	 * The constructor.
	 */
	public LibrairiesEcranPlugin() {
		plugin = this;
	}


	
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
 
		ir.put(IMAGE_ANNULER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ANNULER));
		ir.put(IMAGE_ACCEPTER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ACCEPTER));
		ir.put(IMAGE_ENREGISTRER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ENREGISTRER));
		ir.put(IMAGE_EXCLAMATION, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_EXCLAMATION));
		ir.put(IMAGE_FERMER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_FERMER));
		ir.put(IMAGE_IMPRIMER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_IMPRIMER));
		ir.put(IMAGE_INSERER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_INSERER));
		ir.put(IMAGE_MODIFIER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_MODIFIER));
		ir.put(IMAGE_RECHERCHER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_RECHERCHER));
		ir.put(IMAGE_SUPPRIMER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_SUPPRIMER));		
		ir.put(IMAGE_GENERE, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_GENERE));
		ir.put(IMAGE_EXPORT, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_EXPORT));
		ir.put(IMAGE_REINITIALISER, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_REINITIALISER));
		ir.put(IMAGE_RECHERCHER_JUMELLE, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_RECHERCHER_JUMELLE));

		ir.put(IMAGE_ADRESSE_FAC, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ADRESSE_FAC));
		ir.put(IMAGE_ADRESSE_LIV, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ADRESSE_LIV));
		ir.put(IMAGE_CONDITION_PAIEMENT, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_CONDITION_PAIEMENT));
		ir.put(IMAGE_IDENTITE_TIERS, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_IDENTITE_TIERS));

		ir.put(IMAGE_GEN_DOCUMENT, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_GEN_DOCUMENT));
		ir.put(IMAGE_GEN_MODELE, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_GEN_MODELE));
		ir.put(IMAGE_TOOLS, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_TOOLS));
		ir.put(IMAGE_REPERTOIRE, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_REPERTOIRE));
		ir.put(IMAGE_ERROR, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ERROR));
		ir.put(IMAGE_TICK, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_TICK));
		
		ir.put(IMAGE_SMS, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_SMS));
		ir.put(IMAGE_EMAIL, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_EMAIL));
		ir.put(IMAGE_DEVIS, LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_DEVIS));
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static LibrairiesEcranPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("LibrairiesEcran", path);
	}
}
