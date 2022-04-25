/**
 * 
 */
package fr.legrain.recherchermulticrit.controllers;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;

/**
 * @author posttest
 *
 */
public class FormPageController {
	
	static Logger logger = Logger.getLogger(FormPageController.class.getName());
	
	// Tableau contenant les types de résultats que l'utilisateur peut sélectionner
	public static String[] listeResultats = {"<Choisir>","Tiers","Articles","Documents"};
	
	/** Version contact "crado", à remplacer par un ensemble de points d'extension*/
	public static boolean versionContactCrado = false;
	
	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	// Vue
	protected PaFormPage vue = null;

	// Controllers
	private Etape1Controller etape1Controller = null;
	private Etape2Controller etape2Controller = null;
	private SauverChargerController sauverChargerController = null;

	private FormPageMessenger messengerPage = null;
	
	
	
	/* Constructeur */
	public FormPageController(PaFormPage vue) {
		this.vue = vue;
		etape1Controller = new Etape1Controller(this,vue,null);
		etape2Controller = new Etape2Controller(this,vue,null);
		sauverChargerController = new SauverChargerController(this,vue,null);
		
	}
	
	private void initialisationModel(boolean tout) {
		try {
			
			///////////////////////////////////////////////////////////////
			/** Version contact "crado", à remplacer par un ensemble de points d'extension*/
			BundleContext context = Platform.getBundle(gestComBdPlugin.PLUGIN_ID).getBundleContext();
			IServiceBranding service = null;
			ServiceReference<?> serviceReference = context.getServiceReference(IServiceBranding.class.getName());
			if(serviceReference!=null) {
				service = (IServiceBranding) context.getService(serviceReference); 
			}

			String typeVersionCrado = null;
			if(service!=null && service.getBranding()!=null && service.getBranding().getTypeVersion()!=null) {
				typeVersionCrado = service.getBranding().getTypeVersion();
			}
			if(typeVersionCrado!=null && typeVersionCrado.equals(Branding.C_VERSION_CONTACT)) {
				versionContactCrado = true;
			}
			if(versionContactCrado) {
				listeResultats = new String[]{"Tiers"};
			}
			////////////////////////////////////////////////////////////////
			
			// if(masterDAO!=null && masterEntity!=null) {
			if(tout) {
				// Initialisation de l'étape 1
				etape1Controller.initialiseModelIHM();
				
			}
			etape2Controller.initialiseModelIHM();
			sauverChargerController.initialiseModelIHM();
			
			


		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}
	
	/**
	 * Méthode permettant d'affecter un "messenger" au controller
	 * Le rôle du messenger est de faire transiter les informations entre les
	 * différents onglets
	 * @param leMessenger
	 */
	public void setMessengerPage(FormPageMessenger leMessenger) {
		this.messengerPage = leMessenger;
		
	}
	
	
	public void refreshAll() {
		initialisationModel(false);
	}

	public void appel(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}

	public Etape1Controller getEtape1Controller() {
		return etape1Controller;
	}

	public Etape2Controller getEtape2Controller() {
		return etape2Controller;
	}

	public PaFormPage getVue() {
		return vue;
	}

	public FormPageMessenger getMessengerPage() {
		return messengerPage;
	}

	public SauverChargerController getSauverChargerController() {
		return sauverChargerController;
	}


}
