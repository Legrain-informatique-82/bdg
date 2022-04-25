package fr.legrain.publipostage.gui.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.publipostage.gui.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		initDefautProperties(false);
		//initValuesProperties();
		
	}

	public static void initDefautProperties(boolean forcer){
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String lettreDoc="modelLettre.doc";
		String lettreOpen="modelLettre.odt";
		
		String lettreArticleDoc="modelLettreArticle.doc";
		String lettreArticleOpen="modelLettreArticle.odt";
		
		String[][] autresModeles = new String[][] { 
				{lettreArticleDoc, PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD_ARTICLE},
				{lettreArticleOpen, PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE_ARTICLE}
				};
		
		//Création et ouverture des dossiers
		UtilWorkspace uw =new UtilWorkspace();
		uw.openProjectLocationPath();
		File fileNew=new File( uw.openProjectLocationPath()+"/Modeles");
		String valeur =null;
		fileNew.mkdirs();
		if((new File(fileNew.getPath()+"/"+lettreDoc).exists()&& forcer)||!new File(fileNew.getPath()+"/"+lettreDoc).exists()){
			new File(fileNew.getPath()+"/"+lettreDoc).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreDoc,fileNew.getPath())	;
		}else
			valeur=new File(fileNew.getPath()+"/"+lettreDoc).getPath();
		store.setDefault(PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD,valeur);
		if((new File(fileNew.getPath()+"/"+lettreOpen).exists()&& forcer)||!new File(fileNew.getPath()+"/"+lettreOpen).exists()){
			new File(fileNew.getPath()+"/"+lettreOpen).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreOpen,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreOpen).getPath();
		store.setDefault(PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE,valeur);
		
		//Ajout des autres modèles
		for (int i = 0; i < autresModeles.length; i++) {
			String nomFichierModele = autresModeles[i][0];
			String constante = autresModeles[i][1];
			
			if((new File(fileNew.getPath()+"/"+nomFichierModele).exists()&& forcer)||!new File(fileNew.getPath()+"/"+nomFichierModele).exists()){
				new File(fileNew.getPath()+"/"+nomFichierModele).delete();
				valeur =creeFichierModeleDefautDansDossier(nomFichierModele,fileNew.getPath())	;
			}
			else 
				valeur=new File(fileNew.getPath()+"/"+nomFichierModele).getPath();
			store.setDefault(constante,valeur);
		}

	}

	private static String creeFichierModeleDefautDansDossier(String modele,String path){
		String fichierPath=null;
		FileChannel in = null; // canal d'entrée
		FileChannel out = null; // canal de sortie
		try {
		  // Init
		  in = new FileInputStream(new File(Const.pathRepertoireSpecifiques(Activator.PLUGIN_ID, "Modeles")+"/"+modele)).getChannel();
		  out = new FileOutputStream(new File(path+"/"+modele)).getChannel();

		  // Copie depuis le in vers le out
		  in.transferTo(0, in.size(), out);
		  fichierPath=new File(path+"/"+modele).getPath();
		} catch (Exception e) {
		  //
		} finally { // finalement on ferme
		  if(in != null) {
		  	try {
			  in.close();
			} catch (IOException e) {}
		  }
		  if(out != null) {
		  	try {
			  out.close();
			} catch (IOException e) {}
		  }
		  return fichierPath;
		}
	}
}
