package fr.legrain.archivageepicea.importation_DB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class VerficationFichier {

	public Boolean a; 
	public String nomEcritures;
	public String nomHtva;
	public String nomPieces;
	public String nomPlanAux;
	public String nomPlanCpt;
	public String nomPointage;
	public String nomResteDc;
	public final String nomInfoDossier = "infodossier.txt";
	public String nomDossier;
	public File nomRepertoire;
	public final int nombreFile = 150;
	
	/*
	 * verifier le nombre de dossier
	 */
	public Boolean verifierNombreFile()
	{
		a = false;
		File[] files = nomRepertoire.listFiles();
		if((files.length)==nombreFile){
			a = true;
		}
		return a;
	}
	/*
	 * verifier et recuperer le nom de nomDossier
	 */
	public String verifierInfoDossier() throws IOException
	{
		nomDossier = null;
		File[] files = nomRepertoire.listFiles();
		
		for ( int i = 0; i < files.length; i++ ) {
			if(files[i].getName().equals(nomInfoDossier)){
				
				File Myfile = new File(nomRepertoire.getAbsolutePath()+"/"+nomInfoDossier);
				RandomAccessFile file = null;
				try {
					file = new RandomAccessFile(Myfile,"r");
					String lineNomDossier = file.readLine();
					if(!lineNomDossier.isEmpty())
					{
						int indexChar = lineNomDossier.indexOf(";");
						String subNomDossier = lineNomDossier.substring(0,indexChar);//"nom_dossier"
						if(subNomDossier.matches("nom_dossier"))
						{
							nomDossier = lineNomDossier.substring(indexChar+1);//ghghjg
						}
					}
//					while (file.readLine()!=null) {
//						nomDossier = file.readLine();
//						int indexChar = nomDossier.indexOf(";");
//						
//					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
		return nomDossier;
	}
	/*
	 * 
	 */
	public Boolean verifierAutreDossier()
	{
		a = false;
		File[] files = nomRepertoire.listFiles();
		for ( int i = 0; i < files.length; i++ ) {
			
			int indexChar = files[i].getName().indexOf("_");
			if(indexChar!=-1)
			{
				String subNomAutreFile = files[i].getName().substring(0,indexChar);
				if(subNomAutreFile.equals("Ecritures")){
					a= true;
					break;
				}
			}
			
		}
		return a;
	}
}
