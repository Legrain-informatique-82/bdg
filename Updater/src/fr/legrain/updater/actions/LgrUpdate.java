package fr.legrain.updater.actions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.update.search.BackLevelFilter;
import org.eclipse.update.search.EnvironmentFilter;
import org.eclipse.update.search.UpdateSearchRequest;
import org.eclipse.update.search.UpdateSearchScope;
import org.eclipse.update.ui.UpdateJob;
import org.eclipse.update.ui.UpdateManagerUI;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.libLgrMail.MailUpdate;
import fr.legrain.libLgrMail.MailUtil;
import fr.legrain.updater.UpdaterPlugin;
import fr.legrain.updater.dao.TaMailMaj;
import fr.legrain.updater.dao.TaMailMajDAO;
import fr.legrain.updater.preferences.PreferenceConstants;

public class LgrUpdate  {
	
	static Logger logger = Logger.getLogger(LgrUpdate.class.getName());
	
	private MailUtil mailUtil= new MailUtil();
	
	/**
	 * The constructor.
	 */
	public LgrUpdate() {
	}
	
//	/**
//	 * Récupération des mails de mise à jour sur le serveur
//	 * et enregistrement de la liste des mises à jour correspondantes dans la base de données
//	 * @return la liste des mails de mise à jour recuperee sur le serveur
//	 */
//	public List<MailUpdate> recupMail() {
//		String popServeur = null;//"mail.logiciel-gestion.fr";
//		String popUser = null;//"client1";
//		String popUserPassword = null;//"pwdcl10505";
//		
//		popServeur = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_POP);
//		popUser = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
//		popUserPassword = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASS);
////		Preferences preferences = new ConfigurationScope().getNode(Application.PLUGIN_ID);
////	    boolean auto_login = preferences.getBoolean(PreferenceConstants.P_BOOLEAN, true);
////		System.out.println("test : "+test);
//		
//		final List<MailUpdate> updates = mailUtil.findMailUpdate(
//				popServeur, popUser, popUserPassword, true);
//		
//		//IB_TA_MAIL_MAJ ibTaMailMaj = new IB_TA_MAIL_MAJ();
//		SWT_IB_TA_MAIL_MAJ ibTaMailMaj = new SWT_IB_TA_MAIL_MAJ();
//		SWTMailMaj mailMaj = new SWTMailMaj();
//		for (MailUpdate update : updates) {
//			mailMaj.setDATE_MAIL_MAJ(/*LibDate.dateToString(*/update.getDate()/*)*/);
//			mailMaj.setFAIT_MAIL_MAJ(null);
//			mailMaj.setA_FAIRE_MAIL_MAJ(true);
//			mailMaj.setFROM_MAIL_MAJ(update.getFrom());
//			mailMaj.setNOM_SITE_MAIL_MAJ(update.getNomSite());
//			mailMaj.setSUJET_MAIL_MAJ(update.getSujet());
//			mailMaj.setURL_MAIL_MAJ(update.getUrl().toString());
//			try {
//				ibTaMailMaj.insertion();
//				ibTaMailMaj.enregistrement(mailMaj);
//			} catch (ExceptLgr e) {
//				logger.error(e);
//			}finally{
//				ibTaMailMaj=null;
//			}
//		}
//		return updates;
//	}

//	/**
//	 * The action has been activated. The argument of the
//	 * method represents the 'real' action sitting
//	 * in the workbench UI.
//	 * @see IWorkbenchWindowActionDelegate#run
//	 */
//	public void run(IAction action) {
//		logger.debug("---- test Mail Update ----");
//		//new TestJavaMail().testPOP();	
//		//new TestJavaMail().testSMTP();
//		
//		String popServeur = null;//"mail.logiciel-gestion.fr";
//		String popUser = null;//"client1";
//		String popUserPassword = null;//"pwdcl10505";
//		
//		popServeur = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_POP);
//		popUser = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
//		popUserPassword = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASS);
////		Preferences preferences = new ConfigurationScope().getNode(Application.PLUGIN_ID);
////	    boolean auto_login = preferences.getBoolean(PreferenceConstants.P_BOOLEAN, true);
////		System.out.println("test : "+test);
//		
//		final List<MailUpdate> updates = mailUtil.findMailUpdate(
//				popServeur, popUser, popUserPassword, true);
//		
//		//IB_TA_MAIL_MAJ ibTaMailMaj = new IB_TA_MAIL_MAJ();
//		SWT_IB_TA_MAIL_MAJ ibTaMailMaj = null;
//		try {
//			ibTaMailMaj = new SWT_IB_TA_MAIL_MAJ();
//			SWTMailMaj mailMaj = new SWTMailMaj();
//			for (MailUpdate update : updates) {
//				mailMaj.setDateMailMaj(/*LibDate.dateToString(*/update.getDate()/*)*/);
//				mailMaj.setFaitMailMaj(null);
//				mailMaj.setAFaireMailMaj(true);
//				mailMaj.setFromMailMaj(update.getFrom());
//				mailMaj.setNomSiteMailMaj(update.getNomSite());
//				mailMaj.setSujetMailMaj(update.getSujet());
//				mailMaj.setUrlMailMaj(update.getUrl().toString());
//				try {
//					ibTaMailMaj.insertion();
//					ibTaMailMaj.enregistrement(mailMaj);
//				} catch (ExceptLgr e) {
//					logger.error(e);
//				}
//			}
//		}finally{
//			ibTaMailMaj=null;
//		}
//		
//		afficheUpdateLgr(updates,null);
//		logger.debug("---- test Mail Update ----");
//	}
	
	/**
	 * Recuperation de la liste des mails contenant les adresses des serveurs de mise a jour
	 * et enregistrement de la liste des mises à jour correspondantes dans la base de données
	 * Les mails sont telecharge sur le serveur mail en utilisant le protocole POP.
	 * Les mails sont enregistres dans la base de donnees
	 * @return la liste des mails de mise à jour recuperee sur le serveur
	 */
	public List<MailUpdate> recupMailUpdate() {
		logger.debug("---- test Mail Update ----");
		//new TestJavaMail().testPOP();	
		//new TestJavaMail().testSMTP();
		
		String popServeur = null;//"mail.logiciel-gestion.fr";
		String popUser = null;//"client1";
		String popUserPassword = null;//"pwdcl10505";
		
		popServeur = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_POP);
		popUser = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
		popUserPassword = UpdaterPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASS);
//		Preferences preferences = new ConfigurationScope().getNode(Application.PLUGIN_ID);
//	    boolean auto_login = preferences.getBoolean(PreferenceConstants.P_BOOLEAN, true);
//		System.out.println("test : "+test);
		
		final List<MailUpdate> updates = mailUtil.findMailUpdate(
				popServeur, popUser, popUserPassword, true);
		if(updates!=null)
			logger.debug("Nombre de mail recupere : "+updates.size());
		else
			logger.debug("Nombre de mail recupere : 0");
		
		//IB_TA_MAIL_MAJ ibTaMailMaj = new IB_TA_MAIL_MAJ();
		//SWT_IB_TA_MAIL_MAJ ibTaMailMaj = null;
		TaMailMajDAO dao = null;
		try {
			dao = new TaMailMajDAO();
			TaMailMaj mailMaj = new TaMailMaj();
			EntityTransaction transaction = null;
			for (MailUpdate update : updates) {
				transaction = dao.getEntityManager().getTransaction();
				dao.begin(transaction);
				mailMaj.setDateMailMaj(update.getDate());
				mailMaj.setFaitMailMaj(null);
				mailMaj.setAFaireMailMaj(1);
				mailMaj.setFromMailMaj(update.getFrom());
				mailMaj.setNomSiteMailMaj(update.getNomSite());
				mailMaj.setSujetMailMaj(update.getSujet());
				mailMaj.setUrlMailMaj(update.getUrl().toString());
				try {
					dao.inserer(mailMaj);
					dao.enregistrerMerge(mailMaj);
					
					dao.commit(transaction);
					transaction = null;
				} catch (ExceptLgr e) {
					logger.error(e);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			dao=null;
		}
		
		return updates;
	}
	
	/**
	 * Effectue une recherche des mises à jour disponible sur les serveurs
	 * indiqués dans les <code>MailUpdate</code>. si des mises a jour sont disponible,
	 * le module de maj d'eclipse est affiche
	 * @param updates - liste des mails contenant les adresses des serveurs de mise a jour
	 */
	public void afficheUpdateLgr(final List<MailUpdate> updates, Shell shell) {
		if(shell==null) shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final Shell finalShell = shell;
		if(updates!=null) {
			BusyIndicator.showWhile(shell.getDisplay(),
					new Runnable() {
				public void run() {
					UpdateJob job = new UpdateJob(
							"Recherche de mises à jour", getSearchRequest(updates));
					UpdateManagerUI.openInstaller(finalShell, job);
				}
			});
		} else {
			logger.debug("---- pas de maj ----");
		}
	}
	

//	public void openInstaller() {
//		BusyIndicator.showWhile(shell.getDisplay(),
//				new Runnable() {
//			public void run() {
//				UpdateJob job = new UpdateJob(
//						"Recherche de nouvelles extensions", getSearchRequest());
//				UpdateManagerUI.openInstaller(shell, job);
//			}
//		});
//	}

	/**
	 * Contruction d'un requete de mise a jour
	 */
	private UpdateSearchRequest getSearchRequest(Map<URL,String> sites) {
		UpdateSearchRequest result = new UpdateSearchRequest(
				UpdateSearchRequest.createDefaultSiteSearchCategory(),
				new UpdateSearchScope());
		result.addFilter(new BackLevelFilter());
		result.addFilter(new EnvironmentFilter());
		UpdateSearchScope scope = new UpdateSearchScope();
		for (URL url : sites.keySet()) {
			scope.addSearchSite(sites.get(url),url,null);
		}
		result.setScope(scope);
		return result;
	}
	
	/**
	 * Contruction d'un requete de mise a jour
	 * @param updates
	 * @return
	 */
	private UpdateSearchRequest getSearchRequest(List<MailUpdate> updates) {
		UpdateSearchRequest result = new UpdateSearchRequest(
				UpdateSearchRequest.createDefaultSiteSearchCategory(),
				new UpdateSearchScope());
		result.addFilter(new BackLevelFilter());
		result.addFilter(new EnvironmentFilter());
		UpdateSearchScope scope = new UpdateSearchScope();
		for (MailUpdate update : updates) {
			logger.debug("MAJ : "+update.getNomSite()+" : "+update.getUrl());
			scope.addSearchSite(update.getNomSite(),update.getUrl(),null);
		}
		result.setScope(scope);
		return result;
	}
	
	/**
	 * Contruction d'un requete de mise a jour
	 * @return
	 */
	private UpdateSearchRequest getSearchRequest() {
		UpdateSearchRequest result = new UpdateSearchRequest(
				UpdateSearchRequest.createDefaultSiteSearchCategory(),
				new UpdateSearchScope());
		result.addFilter(new BackLevelFilter());
		result.addFilter(new EnvironmentFilter());
		UpdateSearchScope scope = new UpdateSearchScope();
		try {
			//String homeBase = System.getProperty("hyperbola.homebase",
			//"http://eclipsercp.org/updates");
			//URL url = new URL(homeBase);
			URL url = new URL("http://eclipsercp.org/updates");
			scope.addSearchSite("Hyperbola site", url, null);
			scope.addSearchSite("Site local (test)", new URL("http://192.168.0.6/updates"), null);
		} catch (MalformedURLException e) {
			logger.debug(e);
		}
		result.setScope(scope);
		return result;
	}

}