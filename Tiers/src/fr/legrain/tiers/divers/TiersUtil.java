package fr.legrain.tiers.divers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaLiens;
import fr.legrain.tiers.dao.TaLiensDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.preferences.PreferenceConstants;

public class TiersUtil {
	
	static Logger logger = Logger.getLogger(TiersUtil.class);
	
	private static final String repertoireCourrierTiers = "/courriers/";
	
	private TaTiersDAO dao = null;
	private TaLiensDAO daoLiens = null;
	
	public TiersUtil(EntityManager em) {
		if(em!=null) {
			dao = new TaTiersDAO(em);
			daoLiens = new TaLiensDAO(em);
		} else {
			dao = new TaTiersDAO();
			daoLiens = new TaLiensDAO();
		}
	}
	
	public String repertoireCourrierTiers(TaTiers tiers) {
		boolean conserverCourrierTiers = TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CONSERVER_DOCUMENT_GENERE_A_PARTIR_TIERS);
		if(conserverCourrierTiers)
			return repertoireCourrier(tiers,repertoireCourrierTiers+tiers.getCodeTiers());
		else
			return Const.C_CHEMIN_REP_TMP_COMPLET;
	}
	
	public String repertoireCourrierDocument(TaTiers tiers, String repDoc) {
		boolean conserverCourrierDoc = TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CONSERVER_DOCUMENT_GENERE_A_PARTIR_DOC);
		if(conserverCourrierDoc)
			return repertoireCourrier(tiers,repDoc);
		else
			return Const.C_CHEMIN_REP_TMP_COMPLET;
	}
	
	public boolean repertoireCourrierTiersDefini(TaTiers tiers) {
		String typeRep = TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TIERS_TYPE_REP_COURRIER);
		TaLiens liens = null;
		if(typeRep!=null && !typeRep.equals("")) {
			liens = daoLiens.findLiensTiers(tiers.getCodeTiers(), typeRep);
		}
		if(liens!=null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String repertoireCourrier(TaTiers tiers, String finChemin) {
		String typeRep = TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TIERS_TYPE_REP_COURRIER);
		TaLiens liens = null;
		if(typeRep!=null && !typeRep.equals("")) {
			liens = daoLiens.findLiensTiers(tiers.getCodeTiers(), typeRep);
		}
		if(liens!=null) {
			if(finChemin.endsWith("/"+tiers.getCodeTiers())) //le repertoire est defini pour ce tiers, donc on supprime le code tiers du chemin
				return (liens.getAdresseLiens()+finChemin).substring(0,(liens.getAdresseLiens()+finChemin).length()-tiers.getCodeTiers().length());
			else
				return liens.getAdresseLiens()+finChemin;
		} else {
			return repertoireCourrierDefaut(finChemin);
		}
	}
	
	public String repertoireCourrierDefaut(String finChemin) {
		String repCourrierDefaut = TiersPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TIERS_REP_COURRIER_DEFAUT);
		if(!repCourrierDefaut.equals("")) {
			return repCourrierDefaut+finChemin;
		} else {
			return TiersPlugin.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.TIERS_REP_COURRIER_DEFAUT)+finChemin;
		}
	}
	
	public String repertoireCourrierDefaut() {
		return repertoireCourrierDefaut("");
	}
	
	public void ouvreRepertoireCourrierTiers(TaTiers tiers) {
		ouvreRepertoireCourrier(tiers, repertoireCourrierTiers+tiers.getCodeTiers());
	}
	
	public void ouvreRepertoireCourrierDocument(TaTiers tiers, String repDoc) {
		ouvreRepertoireCourrier(tiers, repDoc);
	}
	
	public void ouvreRepertoireCourrier(TaTiers tiers, String finChemin) {
		final String finalURL = repertoireCourrier(tiers,finChemin);
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				File file = new File(finalURL);
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.OPEN)) {
						try {
							file.mkdirs();
							if(file.exists())
								desktop.open(file);
							//							else
							//								MessageDialog.openError(vue.getShell(), "Erreur", 
							//										"Le chemin est invalide ou inaccessible pour l'instant.");
						} catch (IOException e) {
							logger.error("",e);
						}
					}
				}
			}
		});
	}

	/**
	 * http://www.javalobby.org/java/forums/t17036.html
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}


}
