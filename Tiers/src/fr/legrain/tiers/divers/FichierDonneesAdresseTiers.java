package fr.legrain.tiers.divers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

import fr.legrain.lib.data.IFichierDonnees;
import fr.legrain.tiers.dao.TaTiers;

public class FichierDonneesAdresseTiers implements IFichierDonnees<TaTiers>{
	
	static Logger logger = Logger.getLogger(FichierDonneesAdresseTiers.class.getName());
	
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	
	public void creationFichierDonnees(List<TaTiers>liste, String repertoireModele, String nomFichier, boolean html) {
		creationFichierDonneesAdresse(liste, repertoireModele, nomFichier,html);
	}
	
	public void creationFichierDonneesAdresse(List<TaTiers>liste, String repertoireModele, String nomFichier, boolean html) {
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

				for (TaTiers tiers : liste) {
					if(tiers.getAccepte()){
						String ligne="";

						ligne=tiers.getCodeTiers()+separateur;
						if(tiers.getNomTiers()!=null)ligne+=tiers.getNomTiers()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse1Adresse()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse2Adresse()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse3Adresse()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getCodepostalAdresse()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getVilleAdresse()+separateur;else ligne+=separateur;
						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getPaysAdresse()+separateur;else ligne+=separateur;
						if(tiers.getTaTCivilite()!=null)
							ligne+=tiers.getTaTCivilite().getCodeTCivilite()+separateur;else ligne+=separateur;
						if(tiers.getTaTEntite()!=null)
							ligne+=tiers.getTaTEntite().getCodeTEntite()+separateur;else ligne+=separateur;
						if(tiers.getTaEntreprise()!=null)
							ligne+=tiers.getTaEntreprise().getNomEntreprise()+separateur;else ligne+=separateur;
						ligne+=tiers.getPrenomTiers()+separateur;
						//					ligne+="200"+separateur;
						//					ligne+="200"+separateur;
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
