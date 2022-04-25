package fr.legrain.archivageepicea.importation_DB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class test_fonction {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//String nomDossier = "nom_dossier;gggggggg";
//		//String nomDossier = "";
//		System.out.println(nomDossier.indexOf(";"));
//		System.out.println(nomDossier.substring(0,11));
		File Myfile = new File("/home/lee/Bureau/Fonctionnement _Ddd/Dossier1_BDD/infodossier.txt");
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(Myfile,"r");
			String nomDossier = file.readLine();
			int indexChar = nomDossier.indexOf(";");
			String subNomDossier = nomDossier.substring(0,indexChar);//"nom_dossier"
			if(subNomDossier.matches("nom_dossier"))
			{
				String nameDossier = nomDossier.substring(indexChar+1);
				System.out.println(nameDossier);
			}
			else
			{
				System.out.println("no ok");
			}
			//System.out.println(nomDossier);
			while (nomDossier!=null) {
				nomDossier = file.readLine();
				//
				System.out.println(nomDossier);
				
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
