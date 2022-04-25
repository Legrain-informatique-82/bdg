package googlemap.navigateur;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.tiers.dao.TaAdresse;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class GoogleMapInterface {
	
	static Logger logger = Logger.getLogger(GoogleMapInterface.class.getName());

	private static final String debutURLGoogleMap = "http://maps.google.fr/maps?f=q&hl=fr&geocode=&q=";
	private static final String debutURLGoogleMapIti = "http://maps.google.fr/maps?f=d&hl=fr&geocode=&saddr=";
	
	private static final String finURLGoogleMap = "&sll=48.825138,2.349712&sspn=0.008843,0.018775&ie=UTF8&z=16&iwloc=addr";
	private static final String finURLGoogleMapIti = "&mra=pe&mrcr=0&sll=44.377851,0.344832&sspn=1.281903,2.403259&ie=UTF8&z=11";

	public void afficheGoogleMap(){

		// adresse ciblant un Tiers
		TaAdresse ad= new TaAdresse();
		ad.setAdresse1Adresse("290 avenue Charles de Gaulle");
		ad.setAdresse2Adresse("");
		ad.setAdresse3Adresse("");
		ad.setVilleAdresse("Montauban");
			afficheMap(ad);

		// adresse ciblant le propriétaire du logiciel.
//		TaInfoEntreprise infoEntreprise =new TaInfoEntreprise();
//		infoEntreprise.setAdresse1InfoEntreprise("87 rue bobillot");
//		infoEntreprise.setVilleInfoEntreprise("Paris");
//		afficheMapIti(ad,infoEntreprise);
	}

	/**
	 *Permet d'ouvrir une google map ciblant l'adresse passée en parametre
	 */
	public static void afficheMap(TaAdresse adresse) {

		if(adresse !=null){
			String url=debutURLGoogleMap;
			if(adresse.getAdresse1Adresse()!=null && !adresse.getAdresse1Adresse().equals("")) {	
				url+=adresse.getAdresse1Adresse().replace(" ", "+");}
			if(adresse.getAdresse2Adresse()!=null && !adresse.getAdresse2Adresse().equals("")) {
				url+="+"+adresse.getAdresse2Adresse().replace(" ", "+");
			}
			if(adresse.getAdresse3Adresse()!=null && !adresse.getAdresse3Adresse().equals("")) {
				url+="+"+adresse.getAdresse3Adresse().replace(" ", "+");
			}
			if(adresse.getCodepostalAdresse()!=null && !adresse.getCodepostalAdresse().equals("")) {
				url+="+"+adresse.getCodepostalAdresse().replace(" ", "+");
			}
			if(adresse.getVilleAdresse()!=null && !adresse.getVilleAdresse().equals("")) {
				url+="+"+adresse.getVilleAdresse().replace(" ", "+");
			}
			if(adresse.getPaysAdresse()!=null && !adresse.getPaysAdresse().equals("")) {
				url+="+"+adresse.getPaysAdresse().replace(" ", "+");
			}

			//url+=finURLGoogleMap;

			afficheGMDansOnglet(url);
		}
	}

	/**
	 * permet d'ouvrir une google map donnant un itinéraire entre le propriétaire du logiciel (infoEntreprise) et un
	 * de ses clients (adresse) passées en parametre
	 */
	public static void afficheMapIti(TaAdresse adresse, TaInfoEntreprise infoEntreprise) {
		if(infoEntreprise!=null && adresse!=null) {	
			String url=debutURLGoogleMapIti;

			if(infoEntreprise.getAdresse1InfoEntreprise()!=null && !infoEntreprise.getAdresse1InfoEntreprise().equals("")) {
				url+=infoEntreprise.getAdresse1InfoEntreprise().replace(" ", "+");
				if(infoEntreprise.getAdresse2InfoEntreprise() != null && !infoEntreprise.getAdresse2InfoEntreprise().equals("")) {
					url+="+"+infoEntreprise.getAdresse2InfoEntreprise().replace(" ", "+");
				}
				if(infoEntreprise.getAdresse3InfoEntreprise()!=null && !infoEntreprise.getAdresse3InfoEntreprise().equals("")) {
					url+="+"+infoEntreprise.getAdresse3InfoEntreprise().replace(" ", "+");
				}
				if(infoEntreprise.getCodepostalInfoEntreprise()!=null && !infoEntreprise.getCodepostalInfoEntreprise().equals("")) {
					url+="+"+infoEntreprise.getCodepostalInfoEntreprise().replace(" ", "+");
				}
				if(infoEntreprise.getVilleInfoEntreprise()!=null && !infoEntreprise.getVilleInfoEntreprise().equals("")) {
					url+="+"+infoEntreprise.getVilleInfoEntreprise().replace(" ", "+");
				}
				if(infoEntreprise.getPaysInfoEntreprise()!=null && !infoEntreprise.getPaysInfoEntreprise().equals("")) {
					url+="+"+infoEntreprise.getPaysInfoEntreprise().replace(" ", "+");
				}

				url+="&daddr=";
				if(adresse.getAdresse1Adresse()!=null && !adresse.getAdresse1Adresse().equals("")) {	
					url+=adresse.getAdresse1Adresse().replace(" ", "+");}
				if(adresse.getAdresse2Adresse()!=null && !adresse.getAdresse2Adresse().equals("")) {
					url+="+"+adresse.getAdresse2Adresse().replace(" ", "+");
				}
				if(adresse.getAdresse3Adresse()!=null && !adresse.getAdresse3Adresse().equals("")) {
					url+="+"+adresse.getAdresse3Adresse().replace(" ", "+");
				}
				if(adresse.getCodepostalAdresse()!=null && !adresse.getCodepostalAdresse().equals("")) {
					url+="+"+adresse.getCodepostalAdresse().replace(" ", "+");
				}
				if(adresse.getVilleAdresse()!=null && !adresse.getVilleAdresse().equals("")) {
					url+="+"+adresse.getVilleAdresse().replace(" ", "+");
				}
				if(adresse.getPaysAdresse()!=null && !adresse.getPaysAdresse().equals("")) {
					url+="+"+adresse.getPaysAdresse().replace(" ", "+");
				}

				//url+=finURLGoogleMapIti;

				afficheGMDansOnglet(url);

			}
		}
	}

	public static void afficheGMDansOnglet(final String url) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					PlatformUI.getWorkbench().getBrowserSupport()
					.createBrowser(
							IWorkbenchBrowserSupport.AS_EDITOR,
							"myId",
							"Google Map ",
							"Google Map"
					).openURL(new URL(url));

				} catch (PartInitException e) {
					logger.error("",e);
				} catch (MalformedURLException e) {
					logger.error("",e);

				}
			}	
		});
	}
}
