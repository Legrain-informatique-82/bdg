package fr.legrain.relancefacture.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.relancefacture.Activator;


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
		String lettreDoc2="modelLettre2.doc";
		String lettreDoc3="modelLettre3.doc";
		String lettreOpen="modelLettre.odt";
		String lettreOpen2="modelLettre2.odt";
		String lettreOpen3="modelLettre3.odt";
		//Création et ouverture des dossiers
		UtilWorkspace uw =new UtilWorkspace();
		uw.openProjectLocationPath();
		File fileNew=new File( uw.openProjectLocationPath()+"/ModelesRelance");
		String valeur =null;
		fileNew.mkdirs();
		
		//Taitement pour Word
		if((new File(fileNew.getPath()+"/"+lettreDoc).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreDoc).exists()){
			new File(fileNew.getPath()+"/"+lettreDoc).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreDoc,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreDoc).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE1_WORD,valeur);
		
		if((new File(fileNew.getPath()+"/"+lettreDoc2).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreDoc2).exists()){
			new File(fileNew.getPath()+"/"+lettreDoc2).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreDoc2,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreDoc2).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE2_WORD,valeur);
		
		if((new File(fileNew.getPath()+"/"+lettreDoc3).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreDoc3).exists()){
			new File(fileNew.getPath()+"/"+lettreDoc3).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreDoc3,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreDoc3).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE3_WORD,valeur);
		
		//traitement pour OpenOffice
		if((new File(fileNew.getPath()+"/"+lettreOpen).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreOpen).exists()){
			new File(fileNew.getPath()+"/"+lettreOpen).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreOpen,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreOpen).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE1_OPEN,valeur);
		
		if((new File(fileNew.getPath()+"/"+lettreOpen2).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreOpen2).exists()){
			new File(fileNew.getPath()+"/"+lettreOpen2).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreOpen2,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreOpen2).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE2_OPEN,valeur);
		
		if((new File(fileNew.getPath()+"/"+lettreOpen3).exists()&& forcer) ||!new File(fileNew.getPath()+"/"+lettreOpen3).exists()){
			new File(fileNew.getPath()+"/"+lettreOpen3).delete();
			valeur =creeFichierModeleDefautDansDossier(lettreOpen3,fileNew.getPath())	;
		}else 
			valeur=new File(fileNew.getPath()+"/"+lettreOpen3).getPath();
		store.setDefault(PreferenceConstants.P_RELANCE3_OPEN,valeur);

	}

	private static String creeFichierModeleDefautDansDossier(String modele,String path){
		String fichierPath=null;
		FileChannel in = null; // canal d'entrée
		FileChannel out = null; // canal de sortie
		try {
		  // Init
		  in = new FileInputStream(new File(Const.pathRepertoireSpecifiques(Activator.PLUGIN_ID, "ModelesRelance")+"/"+modele)).getChannel();
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
