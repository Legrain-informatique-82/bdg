package actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.presentations.util.ReplaceDragHandler;

import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.avisecheance.divers.Impression;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.dao.TaWeb;

public class MajDocument implements IWorkbenchWindowActionDelegate {

	private String finDeLigne = "\r\n";
	char sep = ';';
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void exporterTiersEtArticles(){
//		exporterArticles();
		exporterTiers();
	}
	public void exporterArticles(){
		String fichierExportation =Platform.getInstanceLocation().getURL().getFile()+"/ImportArticles.csv";
		StringBuffer ligne = new StringBuffer("");
		char c = ';';
		FileWriter fw = null;
		BufferedWriter bw = null ;
		try {
			fw = new FileWriter(fichierExportation);
			bw = new BufferedWriter(fw);
			fw.write("codeArticle;LibellecArticle;LibellelArticle;NumcptArticle;Actif;CommentaireArticle;DiversArticle;StockMinArticle;" +
					"CodeFamille;LibcFamille;PrixPrix;PrixttcPrix;CodeUnite;CodeUnite2;Rapport;sens;CodeTitreTransport;QteTitreTransport;" +
					"CodeTTva;LibTTva;CodeTva;NumcptTva;TauxTva;prix1HT;prix1TTC;prix2HT;prix2TTC;CodeTTarif;LiblTTarif;CodeTTarif1;LiblTTarif1;" +
					"CodeTTarif2;LiblTTarif2;codeFamille2;libelleFamille2;MatièrePremiere,ProduitFini");
			fw.write("\r\n");
			TaArticleDAO dao = new TaArticleDAO();
			List<TaArticle> liste= dao.selectAll();
      //commentaire isa le 18/11/2019
			//.....
			for (TaArticle taArticle : liste) {

				//				if(taArticle.getTaPrixes()!=null && taArticle.getTaPrixes().size()>1){
				System.out.println(taArticle.getCodeArticle());
				ligne = new StringBuffer("");
				
				ligne.append(remplaceNullAndPointVirgule(taArticle.getCodeArticle())+c);
				ligne.append(remplaceNullAndPointVirgule(taArticle.getLibellecArticle())+c);
				ligne.append(remplaceNullAndPointVirgule(taArticle.getLibellelArticle())+c);
				ligne.append(remplaceNullAndPointVirgule(taArticle.getNumcptArticle())+c);
				ligne.append(LibConversion.booleanToStringLettre(LibConversion.intToBoolean(taArticle.getActif()))+c);
				ligne.append(remplaceNullAndPointVirgule(taArticle.getCommentaireArticle()).trim()+c);

				ligne.append(remplaceNullAndPointVirgule(taArticle.getDiversArticle()).trim() +c);
				ligne.append(LibConversion.bigDecimalToString(taArticle.getStockMinArticle())+c);
				if(taArticle.getTaFamille()!=null){
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaFamille().getCodeFamille()) +c);
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaFamille().getLibcFamille()) +c);
				}else{
					ligne.append(c);
					ligne.append(c);
				}
				TaUnite u1=null;
				if(taArticle.getTaPrix()!=null){
					ligne.append(LibConversion.bigDecimalToString(taArticle.getTaPrix().getPrixPrix()) +c);
					ligne.append(LibConversion.bigDecimalToString(taArticle.getTaPrix().getPrixttcPrix()) +c);
					if(taArticle.getTaPrix().getTaUnite()!=null){
						u1=taArticle.getTaPrix().getTaUnite();
						ligne.append(remplaceNullAndPointVirgule(taArticle.getTaPrix().getTaUnite().getCodeUnite()) +c);
					}else ligne.append(c);

				}else{
					ligne.append(c);
					ligne.append(c);
					ligne.append(c);
				}
				
				if(taArticle.getTaRapportUnite()!=null && taArticle.getTaRapportUnite().getTaUnite1().getIdUnite()==(u1.getIdUnite())){
						ligne.append(remplaceNullAndPointVirgule(taArticle.getTaRapportUnite().getTaUnite2().getCodeUnite()) +c);
						ligne.append(LibConversion.bigDecimalToString(taArticle.getTaRapportUnite().getRapport()) +c);
						ligne.append(LibConversion.integerToString(taArticle.getTaRapportUnite().getSens()) +c);
				}
				else {
					ligne.append(c);//u2
					ligne.append(c);//rapport
					ligne.append(c);//sens
				}
				

				if(taArticle.getTaRTaTitreTransport()!=null){
					if(taArticle.getTaRTaTitreTransport().getTaTitreTransport()!=null)
						ligne.append(remplaceNullAndPointVirgule(taArticle.getTaRTaTitreTransport().getTaTitreTransport().getCodeTitreTransport())+c);
					else ligne.append(c);
					ligne.append(LibConversion.bigDecimalToString(taArticle.getTaRTaTitreTransport().getQteTitreTransport())+c);
				}else{
					ligne.append(c);//code titre
					ligne.append(c);//qte titre
				}
				


				if(taArticle.getTaTTva()!=null){
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaTTva().getCodeTTva())+c);
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaTTva().getLibTTva())+c);
				}else{
					ligne.append(c);
					ligne.append(c);
				}
				if(taArticle.getTaTva()!=null){
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaTva().getCodeTva())+c);
					ligne.append(remplaceNullAndPointVirgule(taArticle.getTaTva().getNumcptTva())+c);
					ligne.append(LibConversion.bigDecimalToString(taArticle.getTaTva().getTauxTva())+c);
				}else{
					ligne.append(c);
					ligne.append(c);
					ligne.append(c);
				}	

				TaPrix p1 = null;
				for (TaPrix ad : taArticle.getTaPrixes()) {// si diff du prix principal 
					if(ad.getIdPrix()!=(taArticle.getTaPrix().getIdPrix())){
						p1=ad;
					}
				}
				if(p1!=null){
					ligne.append(LibConversion.bigDecimalToString(p1.getPrixPrix())+c);
					ligne.append(LibConversion.bigDecimalToString(p1.getPrixttcPrix())+c);

				}else {
					ligne.append(c);//prixht1
					ligne.append(c);//prixttc1

				}
				TaPrix p2 = null;
				for (TaPrix ad : taArticle.getTaPrixes()) {// si diff du prix principal 
					if(ad.getIdPrix()!=(taArticle.getTaPrix().getIdPrix()) && ad.getIdPrix()!=(p1.getIdPrix())){
						p2=ad;
					}
				}
				if(p2!=null){
					ligne.append(LibConversion.bigDecimalToString(p2.getPrixPrix())+c);
					ligne.append(LibConversion.bigDecimalToString(p2.getPrixttcPrix())+c);
				}else {
					ligne.append(c);//prixht2
					ligne.append(c);//prixttc2
				}

				if(taArticle.getTaPrix()!=null && taArticle.getTaPrix().getTaTTarif()!=null && taArticle.getTaPrix().getTaTTarif().size()>0){
					TaTTarif tt=taArticle.getTaPrix().getTaTTarif().iterator().next();
					ligne.append(remplaceNullAndPointVirgule(tt.getCodeTTarif())+ c);//code type tarif (associé au prix par défaut)
					ligne.append(remplaceNullAndPointVirgule(tt.getLiblTTarif())+ c);//libelle type tarif (associé au prix par défaut)
				}else{
				ligne.append(c);//code type tarif (associé au prix par défaut)
				ligne.append(c);//libelle type tarif (associé au prix par défaut)
				}
				
				if(p1!=null && p1.getTaTTarif()!=null && p1.getTaTTarif().size()>0){
					TaTTarif tt=p1.getTaTTarif().iterator().next();
					ligne.append(remplaceNullAndPointVirgule(tt.getCodeTTarif())+ c);
					ligne.append(remplaceNullAndPointVirgule(tt.getLiblTTarif())+ c);
				}else{
				ligne.append(c);//code type tarif 1 (associé au prix 1)
				ligne.append(c);//libelle type tarif 1 (associé au prix 1)
				}
				
				if(p2!=null && p1.getTaTTarif()!=null && p2.getTaTTarif().size()>0){
					TaTTarif tt=p2.getTaTTarif().iterator().next();
					ligne.append(remplaceNullAndPointVirgule(tt.getCodeTTarif())+ c);
					ligne.append(remplaceNullAndPointVirgule(tt.getLiblTTarif())+ c);
				}else{
				ligne.append(c);//code type tarif 2 (associé au prix 2)
				ligne.append(c);//libelle type tarif 2 (associé au prix 2)
				}
				
				
				ligne.append(c);//code famille 2 (n'existe pas)
				ligne.append(c);// libelle famille 2 (n'existe pas)
				ligne.append(c);//Matière primiere (boolean n'existe pas)
				ligne.append(c);//Produit fini (boolean n'existe pas)

				fw.write(ligne.toString());
				fw.write("\r\n");

			}

			bw.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			

	}
	
	public String remplaceNullAndPointVirgule(String val){
		if(val==null) return "";
		val=val.replace(finDeLigne, " - ");
		val=val.replace("\r", " - ");
		val=val.replace(System.getProperty("line.separator"), " - ");
		return val.replaceAll(String.valueOf(sep)," - " );
	}
	
	public void exporterTiers(){
		String fichierExportation =Platform.getInstanceLocation().getURL().getFile()+"/ImportTiers.csv";
		StringBuffer ligne = new StringBuffer("");
// commentaire
		FileWriter fw = null;
		try {
			fw = new FileWriter(fichierExportation);
			BufferedWriter bw = new BufferedWriter(fw);
			fw.write("CodeTiers;NomTiers;PrenomTiers;CodeTEntite;LiblTEntite;CodeTTiers;NomEntreprise;CodeTCivilite;TvaIComCompl;" +
			"Accise;ApeInfoJuridique;CapitalInfoJuridique;RcsInfoJuridique;SiretInfoJuridique;CodeFamille;LiblFamille;" +
			"CodeCompta;CompteTiers;Adresse1Adresse;Adresse2Adresse;Adresse3Adresse;CodepostalAdresse;VilleAdresse;" +
			"PaysAdresse;CodeTAdr;NumeroTelephone1;AdresseEmail1;AdresseWeb1;TtcTiers;CodeCPaiement;LibCPaiement;" +
			"ReportCPaiement;FinMoisCPaiement;CodeTPaiement;LiblTPaiement;CompteTPaiement;ReportTPaiement;FinMoisTPaiement;" +
			"CodeTTvaDoc;Commentaire;ActifTiers;Adresse1AdresseFact;Adresse2AdresseFact;Adresse3AdresseFact;CodepostalAdresseFact;" +
			"VilleAdresseFact;PaysAdresseFact;CodeTAdrFact;Adresse1AdresseLiv;Adresse2AdresseLiv;Adresse3AdresseLiv;CodepostalAdresseLiv;" +
			"VilleAdresseLiv;PaysAdresseLiv;CodeTAdrLiv;NumeroTelephone;CodeTTel;AdresseEmail;CodeTEmail;AdresseWeb;CodeTWeb;NomBanque;" +
			"Adresse1Banque;Adresse2Banque; CpBanque;VilleBanque;CodeBanque;CodeGuichet;Titulaire;Compte;CleRib;Iban;CodeBIC;Cptcomptable;" +
			"CodeTBanque;CodeFamille2;LibcFamille2;CodeTTarif;LiblTTarif");
			fw.write("\r\n");
			TaTiersDAO dao = new TaTiersDAO();
			List<TaTiers> liste= dao.selectAll();
			for (TaTiers obj : liste) {
//				if(obj.getCodeTiers().equals("0089")){
				ligne = new StringBuffer("");
				
				System.out.println(obj.getCodeTiers());
				
				ligne.append(remplaceNullAndPointVirgule(obj.getCodeTiers())+sep);				
				ligne.append(remplaceNullAndPointVirgule(obj.getNomTiers())+sep);
				ligne.append(remplaceNullAndPointVirgule(obj.getPrenomTiers())+sep);
				if(obj.getTaTEntite()!=null){
				ligne.append(remplaceNullAndPointVirgule(obj.getTaTEntite().getCodeTEntite())+sep);
				ligne.append(remplaceNullAndPointVirgule(obj.getTaTEntite().getLiblTEntite())+sep);
				}else{
					ligne.append(sep);
					ligne.append(sep);
				}
				if(obj.getTaTTiers()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTTiers().getCodeTTiers())+sep);
				}else ligne.append(sep);
				
				if(obj.getTaEntreprise()!=null)
				ligne.append(remplaceNullAndPointVirgule(obj.getTaEntreprise().getNomEntreprise())+sep);
				else ligne.append(sep);
				
				if(obj.getTaTCivilite()!=null)
				ligne.append(remplaceNullAndPointVirgule(obj.getTaTCivilite().getCodeTCivilite())+sep);
				else ligne.append(sep);
				
				if(obj.getTaCompl()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaCompl().getTvaIComCompl())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaCompl().getAccise())+sep);
				}
				else {
					ligne.append(sep);
					ligne.append(sep);
				}
				
				if(obj.getTaInfoJuridique()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaInfoJuridique().getApeInfoJuridique())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaInfoJuridique().getCapitalInfoJuridique())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaInfoJuridique().getRcsInfoJuridique())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaInfoJuridique().getSiretInfoJuridique())+sep);
				}
				else {
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
				}
				if(obj.getTaFamilleTiers()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaFamilleTiers().getCodeFamille())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaFamilleTiers().getLibcFamille())+sep);
				}else{
					ligne.append(sep);
					ligne.append(sep);
				}
				ligne.append(remplaceNullAndPointVirgule(obj.getCodeCompta())+sep);
				ligne.append(remplaceNullAndPointVirgule(obj.getCompte())+sep);
				
				if(obj.getTaAdresse()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getAdresse1Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getAdresse2Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getAdresse3Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getCodepostalAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getVilleAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getPaysAdresse())+sep);
					if(obj.getTaAdresse().getTaTAdr()!=null)ligne.append(remplaceNullAndPointVirgule(obj.getTaAdresse().getTaTAdr().getCodeTAdr())+sep);
					else ligne.append(sep);
				}else {
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
				}
				
				if(obj.getTaTelephone()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTelephone().getNumeroTelephone())+sep);
				}else ligne.append(sep);
				
				if(obj.getTaEmail()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaEmail().getAdresseEmail())+sep);
				}else ligne.append(sep);
				
				if(obj.getTaWeb()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaWeb().getAdresseWeb())+sep);
				}else ligne.append(sep);
				
				ligne.append(LibConversion.booleanToStringLettre(LibConversion.intToBoolean(obj.getTtcTiers()))+sep);
				
				if(obj.getTaCPaiement()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaCPaiement().getCodeCPaiement())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaCPaiement().getLibCPaiement())+sep);
					ligne.append(LibConversion.integerToString(obj.getTaCPaiement().getReportCPaiement())+sep);
					ligne.append(LibConversion.integerToString(obj.getTaCPaiement().getFinMoisCPaiement())+sep);
				}else 
					{
						ligne.append(sep);
						ligne.append(sep);
						ligne.append(sep);
						ligne.append(sep);
					}
				if(obj.getTaTPaiement()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTPaiement().getCodeTPaiement())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTPaiement().getLibTPaiement())+sep);
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTPaiement().getCompte())+sep);
					ligne.append(LibConversion.integerToString(obj.getTaTPaiement().getReportTPaiement())+sep);
					ligne.append(LibConversion.integerToString(obj.getTaTPaiement().getFinMoisTPaiement())+sep);
				}else 
					{
						ligne.append(sep);
						ligne.append(sep);
						ligne.append(sep);
						ligne.append(sep);
						ligne.append(sep);
					}
				if(obj.getTaTTvaDoc()!=null){
					ligne.append(remplaceNullAndPointVirgule(obj.getTaTTvaDoc().getCodeTTvaDoc())+sep);
				}else{
					ligne.append("F"+sep);
				}
			
				if(obj.getTaCommentaire()!=null){
					String com=remplaceNullAndPointVirgule(obj.getTaCommentaire().getCommentaire().trim());
					com=com.replace(finDeLigne, " - ");
					com=com.replace("\r", " - ");
					com=com.replace(System.getProperty("line.separator"), " - ");
					ligne.append(com.trim()+sep);
				}else{
					ligne.append(sep);
				}
				ligne.append(LibConversion.booleanToStringLettre(LibConversion.intToBoolean(obj.getActifTiers()))+sep); //40
				
				
				TaAdresse adFact = null;
				TaAdresse adLiv = null;
				for (TaAdresse ad : obj.getTaAdresses()) {// si diff de l'adresse principale et adresse facturation
					if(ad.getIdAdresse()!=(obj.getTaAdresse().getIdAdresse()) && ad.getTaTAdr()!=null && ad.getTaTAdr().getCodeTAdr().equals("FACT")){
						adFact=ad;
					}
				}
				if(adFact!=null){
					ligne.append(remplaceNullAndPointVirgule(adFact.getAdresse1Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getAdresse2Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getAdresse3Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getCodepostalAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getVilleAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getPaysAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adFact.getTaTAdr().getCodeTAdr())+sep);//47
				}else {
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
				}
				
				for (TaAdresse ad : obj.getTaAdresses()) { // si diff de l'adresse principale et adresse livraison
					if(ad.getIdAdresse()!=(obj.getTaAdresse().getIdAdresse()) && ad.getTaTAdr()!=null && ad.getTaTAdr().getCodeTAdr().equals("LIV")){
						adLiv=ad;
					}
				}
				
				if(adLiv!=null){
					ligne.append(remplaceNullAndPointVirgule(adLiv.getAdresse1Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getAdresse2Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getAdresse3Adresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getCodepostalAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getVilleAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getPaysAdresse())+sep);
					ligne.append(remplaceNullAndPointVirgule(adLiv.getTaTAdr().getCodeTAdr())+sep);//54
				}else {
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
				}
				TaTelephone tel = null;
				for (TaTelephone ad : obj.getTaTelephones()) { // si diff du numero principal
					if(obj.getTaTelephone()!=null && ad.getIdTelephone()!=(obj.getTaTelephone().getIdTelephone())){
						tel=ad;
					}
				}
				if(tel!=null){
					ligne.append(remplaceNullAndPointVirgule(tel.getNumeroTelephone())+sep);
					if(tel.getTaTTel()!=null)
					ligne.append(remplaceNullAndPointVirgule(tel.getTaTTel().getCodeTTel())+sep);//56
					else ligne.append(sep);
				}else {
					ligne.append(sep);
					ligne.append(sep);
				}
				
				TaEmail email = null;
				for (TaEmail ad : obj.getTaEmails()) { // si diff de email principal
					if(obj.getTaEmail()!=null && ad.getIdEmail()!=(obj.getTaEmail().getIdEmail())){
						email=ad;
					}
				}
				if(email!=null){
					ligne.append(remplaceNullAndPointVirgule(email.getAdresseEmail())+sep);
					if(email.getTaTEmail()!=null)
					ligne.append(remplaceNullAndPointVirgule(email.getTaTEmail().getCodeTEmail())+sep);//58
					else ligne.append(sep);
				}else {
					ligne.append(sep);
					ligne.append(sep);
				}
				
				TaWeb web = null;
				for (TaWeb ad : obj.getTaWebs()) { // si diff de l'adresse web principale
					if(obj.getTaWeb()!=null && ad.getIdWeb()!=(obj.getTaWeb().getIdWeb())){
						web=ad;
					}
				}				
				if(web!=null){
					ligne.append(remplaceNullAndPointVirgule(web.getAdresseWeb())+sep);
					if(web.getTaTWeb()!=null)
					ligne.append(remplaceNullAndPointVirgule(web.getTaTWeb().getCodeTWeb())+sep);//60
					else ligne.append(sep);
				}else {
					ligne.append(sep);
					ligne.append(sep);
				}
				
				TaCompteBanque cb = null;
				for (TaCompteBanque ad : obj.getTaCompteBanques()) { // si diff de l'adresse web principale
					if(cb==null)cb=ad;
				}				
				if(cb!=null){
					ligne.append(remplaceNullAndPointVirgule(cb.getNomBanque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getAdresse1Banque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getAdresse2Banque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getVilleBanque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCpBanque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCodeBanque())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCodeGuichet())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getTitulaire())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCompte())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCleRib())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getIban())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCodeBIC())+sep);
					ligne.append(remplaceNullAndPointVirgule(cb.getCptcomptable())+sep);
					if(cb.getTaTBanque()!=null)ligne.append(remplaceNullAndPointVirgule(cb.getTaTBanque().getCodeTBanque())+sep);//74
					else ligne.append(sep);
				}else {
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
					ligne.append(sep);
				}
				
				TaFamilleTiers fam = null;
				for (TaFamilleTiers ad : obj.getTaFamilleTierses()) { // si diff de l'adresse web principale
					if(obj.getTaFamilleTiers()!=null && ad.getIdFamille()!=(obj.getTaFamilleTiers().getIdFamille())){
						fam=ad;
					}
				}				
				if(fam!=null){
					ligne.append(remplaceNullAndPointVirgule(fam.getCodeFamille())+sep);
					ligne.append(remplaceNullAndPointVirgule(fam.getLibcFamille())+sep);//76
				}else {
					ligne.append(sep);
					ligne.append(sep);
				}
				
				if(obj.getTaTTarif()!=null){
				ligne.append(remplaceNullAndPointVirgule(obj.getTaTTarif().getCodeTTarif())+sep);
				ligne.append(remplaceNullAndPointVirgule(obj.getTaTTarif().getLiblTTarif())+sep);//78
				}else{
					ligne.append(sep);
					ligne.append(sep);
				}
				ligne.append("\r\n");
				fw.write(ligne.toString());
//				}
			}

			bw.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			

	}


		
	
	
	public void run(IAction action) {
		exporterTiersEtArticles();
//		CreerReglementFacture(action);
//		ImprimerAvisEcheance(action);
//		MettreAJourLesTotauxDocuments(action);
		//MettreAJourLesPointsBonusAnnee(action);
//		MettreAJourLesPointsBonusUtilisesFactures(action);
//		MettreAJourLesPointsBonusUtilises(action);
	}
	
	public void MettreAJourLesPointsBonusUtilisesFactures(IAction action) {
		try {
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION CALCUL POINTS BONUS UTILISES !!!", "Voulez-vous vraiment mettre à jour les points bonus utilisés dans l'année ?"
							+"\r\n"+"Cette procédure peut prendre du temps.")){
				TaInfoEntrepriseDAO daoInfoEntreprise = new TaInfoEntrepriseDAO();
				TaInfoEntreprise infosEntreprise=daoInfoEntreprise.findInstance();
				List<TaAbonnement> liste=null;
				TaComptePointDAO daoComptePoint=new TaComptePointDAO();
				TaTiersDAO daoTiers = new TaTiersDAO();
				List<TaTiers>listeTiers =daoTiers.selectAll();
				for (TaTiers taTiers : listeTiers) {
					daoComptePoint.calculPointUtiliseFacture(taTiers.getIdTiers(), infosEntreprise.getDatedebInfoEntreprise());
				}

			}			

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void MettreAJourLesPointsBonusUtilises(IAction action) {
//		try {
//			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getShell(),
//					"ATTENTION CALCUL POINTS BONUS UTILISES !!!", "Voulez-vous vraiment mettre à jour les points bonus utilisés dans l'année ?"
//							+"\r\n"+"Cette procédure peut prendre du temps.")){
//				TaInfoEntrepriseDAO daoInfoEntreprise = new TaInfoEntrepriseDAO();
//				TaInfoEntreprise infosEntreprise=daoInfoEntreprise.findInstance();
//				List<TaAbonnement> liste=null;
//				TaComptePointDAO daoComptePoint=new TaComptePointDAO();
//				TaTiersDAO daoTiers = new TaTiersDAO();
//				List<TaTiers>listeTiers =daoTiers.selectAll();
//				for (TaTiers taTiers : listeTiers) {
//					daoComptePoint.calculPointUtilise(taTiers.getIdTiers(), infosEntreprise.getDatedebInfoEntreprise());
//				}
//
//			}			
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}

	}
	
	
	public void ListeSoldePointBonusTiers(IAction action) {
//		try {
//			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getShell(),
//					"ATTENTION CALCUL SOLDE POINTS BONUS PAR TIERS !!!", "Voulez-vous vraiment calculer le solde des points bonus de tous les tiers de l'année ?"
//							+"\r\n"+"Cette procédure peut prendre du temps.")){
//				List<TaAbonnement> liste=null;
//				TaComptePointDAO daoComptePoint=new TaComptePointDAO();
//				TaAbonnementDAO daoAbonnement = new TaAbonnementDAO(daoComptePoint.getEntityManager());
//				TaInfoEntrepriseDAO daoInfoEntreprise = new TaInfoEntrepriseDAO();
//				TaInfoEntreprise infosEntreprise=daoInfoEntreprise.findInstance();
//				liste=daoAbonnement.findAbonnementBetweenDateDebDateFin(infosEntreprise.getDatedebInfoEntreprise(),infosEntreprise.getDatefinInfoEntreprise(),"%");
//				for (TaAbonnement taAbonnement2 : liste) {
//					daoComptePoint.soldeReelTiers(taAbonnement2.getTaTiers(),true);
//				}
//
//			}		
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}

	}
	
	public void MettreAJourLesPointsBonusAnnee(IAction action) {
		try {
			TaComptePointDAO daoComptePoint=new TaComptePointDAO();
			daoComptePoint.getEntityManager().getTransaction().begin();
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION CALCUL POINTS BONUS !!!", "Voulez-vous vraiment mettre à jour les points bonus des abonnements de l'année ?"
							+"\r\n"+"Cette procédure peut prendre du temps.")){
				List<TaAbonnement> liste=null;

				TaAbonnementDAO daoAbonnement = new TaAbonnementDAO(daoComptePoint.getEntityManager());
				TaInfoEntrepriseDAO daoInfoEntreprise = new TaInfoEntrepriseDAO();
				TaInfoEntreprise infosEntreprise=daoInfoEntreprise.findInstance();
				liste=daoAbonnement.findAbonnementBetweenDateDebDateFin(infosEntreprise.getDatedebInfoEntreprise(),infosEntreprise.getDatefinInfoEntreprise(),"%");
				for (TaAbonnement taAbonnement2 : liste) {
					daoComptePoint.calculPointTotalAnnee(daoComptePoint.getEntityManager(),taAbonnement2,true);
				}

			}		
			daoComptePoint.getEntityManager().getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void MettreAJourLesTotauxDocuments(IAction action) {
		try {
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION", "Voulez-vous vraiment mettre à jour les totaux des documents ?"
							+"\r\n"+"Cette procédure peut prendre du temps.")){
				//gestion des bonliv
				TaBonlivDAO daoBonliv = new TaBonlivDAO();
				daoBonliv.begin(daoBonliv.getEntityManager().getTransaction());
				List<TaBonliv> lBonliv = daoBonliv.selectAll();
				for (TaBonliv taDocument : lBonliv) {
					taDocument.calculeTvaEtTotaux();
					daoBonliv.merge(taDocument);
				}
				daoBonliv.commit(daoBonliv.getEntityManager().getTransaction());
				//gestion des devis
				TaDevisDAO daoDevis = new TaDevisDAO();
				daoDevis.begin(daoDevis.getEntityManager().getTransaction());
				List<TaDevis> lDevis = daoDevis.selectAll();
				for (TaDevis taDocument : lDevis) {
					taDocument.calculeTvaEtTotaux();
					daoDevis.merge(taDocument);
				}
				daoDevis.commit(daoDevis.getEntityManager().getTransaction());


				//gestion des factures
				TaFactureDAO daoFacture = new TaFactureDAO();
				daoFacture.begin(daoFacture.getEntityManager().getTransaction());
				List<TaFacture> lFacture = daoFacture.selectAll();
				//List<TaFacture> lFacture  =daoFacture.rechercheDocument("0400117", "0400117");			
				for (TaFacture taDocument : lFacture) {
					taDocument.calculeTvaEtTotaux();
					daoFacture.merge(taDocument);
				}


				daoFacture.commit(daoFacture.getEntityManager().getTransaction());

				//gestion des factures
				TaAvoirDAO daoAvoir = new TaAvoirDAO();
				daoAvoir.begin(daoAvoir.getEntityManager().getTransaction());
				List<TaAvoir> lAvoir = daoAvoir.selectAll();
				for (TaAvoir taDocument : lAvoir) {
					taDocument.calculeTvaEtTotaux();
					daoAvoir.merge(taDocument);
				}
				daoAvoir.commit(daoAvoir.getEntityManager().getTransaction());

				//gestion des factures
				TaApporteurDAO daoApporteur = new TaApporteurDAO();
				daoApporteur.begin(daoApporteur.getEntityManager().getTransaction());
				List<TaApporteur> lApporteur = daoApporteur.selectAll();
				for (TaApporteur taDocument : lApporteur) {
					taDocument.calculeTvaEtTotaux();
					daoApporteur.merge(taDocument);
				}
				daoApporteur.commit(daoApporteur.getEntityManager().getTransaction());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void CreerReglementFacture(IAction action) {
		 TaRReglementDAO taRReglementDAO = new TaRReglementDAO();
		EntityTransaction transaction = taRReglementDAO.getEntityManager().getTransaction();
		String typePaiementDefaut=null;
		typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
				fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
if (typePaiementDefaut == null || typePaiementDefaut=="")
	typePaiementDefaut="C";
		try {
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION", "Voulez-vous vraiement règler toutes les factures non totalement règlées ?")){

				//gestion des factures
				TaFactureDAO daoFacture = new TaFactureDAO(taRReglementDAO.getEntityManager());

				 TaReglementDAO daoReglement = new TaReglementDAO(taRReglementDAO.getEntityManager());
					TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(taRReglementDAO.getEntityManager());
				 TaRReglement taRReglement;
//				List<TaFacture> lFacture = daoFacture.rechercheDocumentNonTotalementRegle(new Date(0), new Date());
				List<TaFacture> lFacture = daoFacture.rechercheDocumentNonTotalementRegle(new Date(0), new Date(), "CDIV", null);
				//List<TaFacture> lFacture  =daoFacture.rechercheDocument("0400117", "0400117");			
				for (TaFacture taDocument : lFacture) {
					taRReglementDAO.begin(transaction);
					taRReglement = new TaRReglement();
					TaReglement taReglement = new TaReglement();
					taReglement.setCodeDocument(daoReglement.genereCode());
					taReglement=daoReglement.enregistrerMerge(taReglement);
					taReglement.setTaTiers(taDocument.getTaTiers());
					//taRReglement=mapper.dtoToEntity(objet);

					taRReglement.setTaReglement(taReglement);
					TaTPaiement taTPaiement = daoTPaiement.findByCode(typePaiementDefaut);
					taReglement.setTaCompteBanque(new TaCompteBanqueDAO(taRReglementDAO.getEntityManager()).findByTiersEntreprise(taTPaiement));
					taRReglement.setAffectation(taDocument.getResteAReglerComplet());
					taReglement.setNetTtcCalc(taDocument.getResteAReglerComplet());
					taReglement.setDateDocument(new Date());
					taReglement.setDateLivDocument(taDocument.getDateLivDocument());
					taRReglement.getTaReglement().setEtat(0);
					taReglement.setTaTPaiement(daoTPaiement.findByCode(taTPaiement.getCodeTPaiement()));
					taReglement.setLibelleDocument(taDocument.getLibelleDocument());
					taRReglement.setTaFacture(daoFacture.findByCode(taDocument.getCodeDocument()));
					
					if(taRReglement.getVersionObj()==null)taRReglement.setVersionObj(0);
					
					taRReglement=taRReglementDAO.enregistrerMerge(taRReglement);
					taRReglementDAO.commit(transaction);
				}
				taRReglementDAO.commit(transaction);


			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void ImprimerAvisEcheance(IAction action) {
		try {
			
			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION EDITION DES AVIS D'ECHEANCE !!!", "Voulez-vous vraiment éditer les avis d'échéance ?"
							+"\r\n"+"Cette procédure peut prendre du temps.")){
				TaInfoEntrepriseDAO daoInfoEntreprise = new TaInfoEntrepriseDAO();
				TaInfoEntreprise infosEntreprise=daoInfoEntreprise.findInstance();
				TaAvisEcheanceDAO daoAvisEcheance = new TaAvisEcheanceDAO();
				TaFamilleTiersDAO daoFamilleTiers = new TaFamilleTiersDAO();
				TaFamilleTiers groupe = daoFamilleTiers.rechercheFamilleCogere();
				LinkedList<TaAvisEcheance> liste;
//				Date DateDebut=new Date("20/11/2018");
				Date DateDebut=LibDate.stringToDate("10/12/2018");
				
//				//telechargement et groupe cogere
//				liste=daoAvisEcheance.rechercheAvisTelechargement(DateDebut,
//						infosEntreprise.getDatefinInfoEntreprise(),true,groupe.getCodeFamille(),true);

			
				//telechargement			
				liste=daoAvisEcheance.rechercheAvisTelechargement(DateDebut,
						infosEntreprise.getDatefinInfoEntreprise(),true,null,false);

				
//				//Non telechargeable et groupe cogere
//				liste=daoAvisEcheance.rechercheAvisTelechargement(DateDebut,
//						infosEntreprise.getDatefinInfoEntreprise(),false,groupe.getCodeFamille(),true);

				
//				//Non telechargeable et groupe Autres
//				liste=daoAvisEcheance.rechercheAvisTelechargement(DateDebut,
//						infosEntreprise.getDatefinInfoEntreprise(),false,null,true);

				
				
				
				imprimerAvis(liste);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	protected void imprimerAvis(LinkedList<TaAvisEcheance> liste) throws Exception{
		final LinkedList<TaAvisEcheance>listeTemp1 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp2 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp3 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp4 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp5 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp6 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp7 =new LinkedList<TaAvisEcheance>();
		final LinkedList<TaAvisEcheance>listeTemp8 =new LinkedList<TaAvisEcheance>();
		int i=1;
		for (TaAvisEcheance taAvisEcheance : liste) {
			if(i<=110)listeTemp1.add(taAvisEcheance);
			if(i<=200 && i>110)listeTemp2.add(taAvisEcheance);
			if(i<=300 && i>200)listeTemp3.add(taAvisEcheance);
			if(i<=400 && i>300)listeTemp4.add(taAvisEcheance);
			if(i<=500 && i>400)listeTemp5.add(taAvisEcheance);
			if(i<=600 && i>500)listeTemp6.add(taAvisEcheance);
			if(i<=700 && i>600)listeTemp7.add(taAvisEcheance);
			if(i<=800 && i>700)listeTemp8.add(taAvisEcheance);
			i++;
		}
//		if(!listeTemp1.isEmpty())actImprimer(listeTemp1);		
//		if(!listeTemp2.isEmpty())actImprimer(listeTemp2);
//		if(!listeTemp3.isEmpty())actImprimer(listeTemp3);
//		if(!listeTemp4.isEmpty())actImprimer(listeTemp4);
//		if(!listeTemp5.isEmpty())actImprimer(listeTemp5);
//		if(!listeTemp6.isEmpty())actImprimer(listeTemp6);
		if(!listeTemp7.isEmpty())actImprimer(listeTemp7);
//		if(!listeTemp8.isEmpty())actImprimer(listeTemp8);

	}
	protected void actImprimer(LinkedList<TaAvisEcheance> listDocumentAImprimer) throws Exception {
		//Récupération des paramètres dans l'ihm
				//String[] idFactureAImprimer = null;
					TaAvisEcheanceDAO dao = new TaAvisEcheanceDAO();
				final boolean preview = true;
				final boolean printDirect = false;
				TaAvisEcheance avis;
				//listDocumentAImprimer=new LinkedList<IDocumentTiers>();
				Impression impressionDocument = new fr.legrain.avisecheance.divers.Impression();
				final BaseImpressionEdition impressionEdition = new BaseImpressionEdition(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),dao.getEntityManager());
//				for (IHMEnteteAvisEcheance obj : modelLDocument) {
//					if(obj.getAccepte()){
//					avis=taAvisEcheanceDAO.findById(obj.getIdDocument());
//					listDocumentAImprimer.add(avis);
//					}
//				}

				
				if(listDocumentAImprimer==null ||listDocumentAImprimer.size()==0){
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "Aucun document valide n'a été sélectionné !!!");
					throw new Exception("pas de factures à imprimer");
				}

				final LinkedList<IDocumentTiers> finalListDocumentAImprimer = new LinkedList<IDocumentTiers>();
				for (TaAvisEcheance taAvisEcheance : listDocumentAImprimer) {
					finalListDocumentAImprimer.add(taAvisEcheance);
				}
				final boolean finalPreview = preview;
				String fichierEdition = null;
				String nomOnglet = null;
				String nomEntity = null;
				String typeTraite = null;
				final LinkedList<TaRReglement> listeTraite=new LinkedList<TaRReglement>();
				listeTraite.clear();
				/** 01/03/2010 modifier les editions (zhaolin) **/
				IPreferenceStore preferenceStore = null;
				String namePlugin = null;
				/************************************************/
//					impressionDocument = new Impression(vue.getShell());
					fichierEdition = ConstEdition.FICHE_FILE_REPORT_AVIS_ECHEANCE;
					nomOnglet = "Avis d'échéance";
					nomEntity = TaAvisEcheance.class.getSimpleName();
					

					
				preferenceStore=impressionDocument.getPreferenceStore();
				namePlugin = impressionDocument.getPluginName();
				
				final String finalFichierEdition = fichierEdition;
				final String finalNomOnglet = nomOnglet;
				final String finalNomEntity = nomEntity;
				final String finalTypeTraite = typeTraite;
				
				/** 01/03/2010 modifier les editions (zhaolin) **/
				final String finalNamePlugin = namePlugin;
				final IPreferenceStore finalPreferenceStore = preferenceStore;
				final ConstEdition constEdition = new ConstEdition(null);

				PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell().getDisplay().asyncExec(new Thread() {
					public void run() {
						try {	
							impressionEdition.setConstEdition(constEdition);
							impressionEdition.setCollection(finalListDocumentAImprimer);
							impressionEdition.setTypeTraite(finalTypeTraite);
							impressionEdition.impressionEdition(finalPreferenceStore, finalNomEntity,/*true,*/ 
							        null, finalNamePlugin, finalFichierEdition, true, 
							        false, true, true , true,false,"");
							
							

						} catch (Exception e) {
							//logger.error("Erreur à l'impression ",e);
						} finally {
						}
					}
				});

			}

	public class Version{
		String libVersion="";
		 int numVersion=0;
		 
		 public Version(String libVersion, int numVersion) {
			super();
			this.libVersion = libVersion;
			this.numVersion = numVersion;
		}

		}
	
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}
	
	public void MettreAJourLesComptesInternet(IAction action) {
//		try {
//			if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getShell(),
//					"ATTENTION MAJ COMPTES LEGRAIN.FR !!!", "Voulez-vous vraiment mettre à jour les comptes 'legrain.fr' en ce qui concerne" +
//							" les dates de maintenances  ?"
//							+"\r\n"+"Cette procédure peut prendre du temps.")){
//
//						try {      
//							
//							StringBuffer ligne = new StringBuffer("");
//							
//							FileWriter fw = new FileWriter(fichierExportation);
//							BufferedWriter bw = new BufferedWriter(fw);
//				CallableStatement cs;
//				//			cs = ibTaFacture.getFIBBase().getJdbcConnection().prepareCall("{?,?,?,?,?,?,?,?,?,?,?,? = call CALCUL_TOTAL_DIRECT(?)}");
//				//			cs.registerOutParameter(1,Types.DOUBLE);
//				cs.execute();
//				
//				ligne.append(completeChaine(String.valueOf(numPieceExport),5,c,0));
//				fw.write(ligne.toString());
//				bw.close();
//							bw.close();
//						} catch(IOException ioe){
//							logger.error("",ioe);
//						} catch (SQLException e) {
//							logger.error("",e);
//						} catch (Exception e) {
//							logger.error("",e);
//						}
//					}		
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}

	}
	

	
	
	

}
