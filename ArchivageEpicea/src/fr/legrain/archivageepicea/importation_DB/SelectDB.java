package fr.legrain.archivageepicea.importation_DB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class SelectDB {

	/**
	 * pour stocker les nom de table
	 */
	private List<String> nomBD = new ArrayList<String>();

	public List<String> nomDossier(String pathFirebird){
		if(new File(pathFirebird).exists()){
			File[] nameFiles = new File(pathFirebird).listFiles();

			nomBD.clear();
			for ( int i = 0; i < nameFiles.length; i++ ) {
				int indexChar = nameFiles[i].getName().indexOf(".");
				if(indexChar != -1){
					//indexChar=filesTxt[i].getName().indexOf(".");
					nomBD.add(nameFiles[i].getName().substring(0,indexChar));
				}
			}
		}
		return nomBD;
	}

}
