package fr.legrain.facture.divers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.tiers.dao.TaTiers;

public class FichierDonneesAdresseFacture {
	
	static Logger logger = Logger.getLogger(FichierDonneesAdresseFacture.class.getName());
	
	public static final int TYPE_ADRESSE_FACTURATION = 1;
	public static final int TYPE_ADRESSE_LIVRAISON = 2;
	
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	private int typeAdresse = 0;
	
	public FichierDonneesAdresseFacture(int type) {
		typeAdresse = TYPE_ADRESSE_FACTURATION;
		
		if(type==TYPE_ADRESSE_FACTURATION) {
			typeAdresse = TYPE_ADRESSE_FACTURATION;
		} else if(type==TYPE_ADRESSE_LIVRAISON) {
			typeAdresse = TYPE_ADRESSE_LIVRAISON;
		}
		
	}
	
	public FichierDonneesAdresseFacture() {
		typeAdresse = TYPE_ADRESSE_FACTURATION;
	}
	
	public void creationFichierDonneesAdresse(List<TaFacture>liste, String repertoireModele, String nomFichier) {
		if(liste!=null && liste.size()>0){
//			FileWriter fw = null;
			BufferedWriter bw = null;
			String cheminFichier="";
			try {
				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
				//cheminFichier=new File(repertoireModele+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
				cheminFichier=nomFichier;
//				fw = new FileWriter(cheminFichier);

				//param.setCheminFichierMotCle(new File(Const.pathRepertoireSpecifiques("fr.legrain.publipostageTiers", "Modeles")+"/motcle.properties").getPath());

				String encoding = "UTF-8";
				//String encoding = "ISO-8859-1";

				//bw = new BufferedWriter(fw);
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cheminFichier),encoding));
				bw.write("codeTiers;nomTiers;adresse1;adresse2;adresse3;codepostal;ville;pays" +
						";codeTCivilite;codeTEntite;nomEntreprise;prenomTiers" 
						//+";solde_total_pts;solde_pts_2010"
						+separateur+finDeLigne);

				for (TaFacture f : liste) {
					if(f.getAccepte()){
						String ligne="";

						ligne=f.getTaTiers().getCodeTiers()+separateur;
						if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getNomTiers()+separateur;else ligne+=separateur;
						
						if(typeAdresse==TYPE_ADRESSE_FACTURATION) {
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse1()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse2()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse3()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getCodepostal()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getVille()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getPays()+separateur;else ligne+=separateur;
						} else if(typeAdresse==TYPE_ADRESSE_LIVRAISON) {
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse1Liv()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse1Liv()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getAdresse3Liv()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getCodepostalLiv()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getVilleLiv()+separateur;else ligne+=separateur;
							if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getPaysLiv()+separateur;else ligne+=separateur;
						}
						
						if(f.getTaInfosDocument()!=null)
							ligne+=f.getTaInfosDocument().getCodeTCivilite()+separateur;else ligne+=separateur;
						if(f.getTaInfosDocument()!=null)
							ligne+=f.getTaInfosDocument().getCodeTEntite()+separateur;else ligne+=separateur;
						if(f.getTaInfosDocument()!=null)
							ligne+=f.getTaInfosDocument().getNomEntreprise()+separateur;else ligne+=separateur;
						if(f.getTaInfosDocument()!=null)ligne+=f.getTaInfosDocument().getPrenomTiers()+separateur;
						//					ligne+="200"+separateur;
						//					ligne+="200"+separateur;
						ligne = ligne.replaceAll("null", ""); //suppression des NULL
						bw.write(ligne);
						bw.write(finDeLigne);
					}
				}

				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
			} catch(IOException ioe){
				logger.error("",ioe);
			} 
			finally{
				try {
					if(bw!=null) bw.close();
//					if(fw!=null) fw.close();

				} catch (Exception e) {
					
				}
			}
		}
	}
}
