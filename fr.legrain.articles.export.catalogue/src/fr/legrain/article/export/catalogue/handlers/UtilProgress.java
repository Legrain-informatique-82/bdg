package fr.legrain.article.export.catalogue.handlers;

import java.io.File;

public class UtilProgress {
	
	/**
	 * Retourne la taille d'un fichier ou d'un répertoire en octet.
	 * @param File - folder
	 * @return 
	 */
	static public long getFileSize(File folder) {
		//totalFolder++; 
		System.out.println("Folder: " + folder.getName());
		long foldersize = 0;

		File[] filelist = folder.listFiles();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isDirectory()) {
				foldersize += getFileSize(filelist[i]);
			} else {
				//totalFile++;
				System.out.println(filelist[i].getName()+"  **  " + filelist[i].length());
				foldersize += filelist[i].length();
			}
		}
		System.out.println("====>  " + foldersize);
		return foldersize;
	}
	
}
