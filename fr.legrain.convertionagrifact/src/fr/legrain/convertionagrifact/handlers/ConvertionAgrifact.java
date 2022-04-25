package fr.legrain.convertionagrifact.handlers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaCommentaire;
import fr.legrain.tiers.dao.TaCompl;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTCiviliteDAO;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTelDAO;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.dao.TaWeb;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ConvertionAgrifact extends AbstractHandler {
	
	static Logger logger = Logger.getLogger(ConvertionAgrifact.class);
	
	/**
	 * The constructor.
	 */
	public ConvertionAgrifact() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		
		// **********voir les tables de paramètres à vider avant l'importation comme table tva, famille, typepaiement,unite***********//
		
		//importArticle();
		//importClient();

		
		
		//corrigeTvaArticle();

		return null;
	}
	
	
	
	public Connection connectionSqlServeur(){
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//String connectionUrl = "jdbc:sqlserver://localhost;database=BERTRINE;";
		//Connection con = DriverManager.getConnection(connectionUrl);
		
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser("demo");
		ds.setPassword("");
		ds.setServerName("localhost");
		ds.setPortNumber(1433); 
		ds.setDatabaseName("BERTRINE");
		Connection con=null;
		try {
			con = ds.getConnection();
		} catch (SQLServerException e) {
			return null;
		}
		return con;
	}

	public void importArticle() throws ExecutionException {
		
		try {		
			TaTiersDAO daoTiers = new TaTiersDAO();
			Connection con=connectionSqlServeur();
			Statement s = con.createStatement();
			Statement sSup = con.createStatement();	
			
			
			
			//Récupération des familles
			ResultSet rsFamille = s.executeQuery("select * from Famille");
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			
			TaFamilleDAO daoFamille = new TaFamilleDAO(daoTiers.getEntityManager());
			daoFamille.razFamille();
			daoTiers.getEntityManager().getTransaction().commit();
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			while (rsFamille.next()) {
				try {
					if(rsFamille.getString("Famille")!=null && !rsFamille.getString("Famille").equals("")  && !daoFamille.exist(rsFamille.getString("Famille").toUpperCase())){
						TaFamille famille=new TaFamille();
						famille.setIdFamille(rsFamille.getInt("nFamille"));
						famille.setCodeFamille(rsFamille.getString("Famille").toUpperCase());
						famille.setLibcFamille(rsFamille.getString("Famille"));
						famille.setLiblFamille(rsFamille.getString("Famille"));
						famille=daoFamille.enregistrerMerge(famille);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsUnite = s.executeQuery("select * from ListeChoix where NomChoix like ('ListeUnite')");
			TaUnite unite=new TaUnite();
			TaUniteDAO daoUnite = new TaUniteDAO(daoTiers.getEntityManager());
			while (rsUnite.next()) {
				try {
					if(rsUnite.getString("valeur")!=null && !rsUnite.getString("valeur").equals("") && !daoUnite.exist(rsUnite.getString("valeur").toUpperCase())){
						unite.setCodeUnite(rsUnite.getString("valeur").toUpperCase());
						unite=daoUnite.enregistrerMerge(unite);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsTva = s.executeQuery("select * from Tva");
			TaTva tva=new TaTva();
			TaTvaDAO daoTva = new TaTvaDAO(daoTiers.getEntityManager());
			while (rsTva.next()) {
				try {
					if(rsTva.getString("TTva")!=null && !rsTva.getString("TTva").equals("") &&   !daoTva.exist(rsTva.getString("TTva").toUpperCase())){
						tva.setCodeTva(rsTva.getString("TTva").toUpperCase());
						tva.setLibelleTva(rsTva.getString("TLibelle"));
						if(rsTva.getString("TCpt")!=null && !rsTva.getString("TCpt").equals(""))tva.setNumcptTva(rsTva.getString("TCpt"));
						else tva.setNumcptTva("?");
						tva.setTauxTva(rsTva.getBigDecimal("TTaux"));
						
						tva=daoTva.enregistrerMerge(tva);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsArticle = s.executeQuery("select * from Produit ");
			
			TaArticleDAO daoArticle = new TaArticleDAO(daoTiers.getEntityManager());
			
			while (rsArticle.next()) {
				try {
					TaArticle article=new TaArticle();
					if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
					if(rsArticle.getString("CodeProduit")!=null && !daoArticle.exist(rsArticle.getString("CodeProduit").toUpperCase())){						
						article.setCodeArticle(rsArticle.getString("CodeProduit").toUpperCase());
						if(rsArticle.getString("Libelle")!=null){
							if( rsArticle.getString("Libelle").length()>100)article.setLibellecArticle(rsArticle.getString("Libelle").substring(0,100));
							else article.setLibellecArticle(rsArticle.getString("Libelle"));
						}
						if(article.getLibellecArticle()==null)article.setLibellecArticle(article.getCodeArticle());
						if(rsArticle.getString("LibelleLong")!=null){
							if( rsArticle.getString("LibelleLong").length()>255)article.setLibellelArticle(rsArticle.getString("LibelleLong").substring(0,255));
							else article.setLibellelArticle(rsArticle.getString("LibelleLong"));
						}
						
						if(rsArticle.getString("NCompteV")!=null){
							if( rsArticle.getString("NCompteV").length()>8)article.setNumcptArticle(rsArticle.getString("NCompteV").substring(0, 8));
							else article.setNumcptArticle(rsArticle.getString("NCompteV"));
						}
						article.setStockMinArticle(rsArticle.getBigDecimal("SeuilStock"));
						if(rsArticle.getString("TTVa")!=null && daoTva.exist(rsArticle.getString("TTVa").toUpperCase()))article.setTaTva(daoTva.findByCode(rsArticle.getString("TTVa").toUpperCase()));
						if(rsArticle.getString("Famille1")!=null )article.setTaFamille(daoFamille.findById(rsArticle.getInt("Famille1")));
						
						if(rsArticle.getBigDecimal("PrixVHT")!=null ||rsArticle.getBigDecimal("PrixVTTC")!=null){
							TaPrix prix =new TaPrix();
							BigDecimal tauxTva=BigDecimal.valueOf(1);
							if(article.getTaTva()!=null && article.getTaTva().getTauxTva()!=null)tauxTva=article.getTaTva().getTauxTva();
							if(rsArticle.getBoolean("IsPrixHT")){
								prix.setPrixPrix(rsArticle.getBigDecimal("PrixVHT"));
								prix.setPrixttcPrix(prix.getPrixPrix().multiply(tauxTva).divide(BigDecimal.valueOf(100)));								
							}else{
								prix.setPrixttcPrix(rsArticle.getBigDecimal("PrixVTTC"));
								prix.setPrixPrix(prix.getPrixttcPrix().divide(new BigDecimal(1).
										add(tauxTva.divide(new BigDecimal(100))),MathContext.DECIMAL32));
							}
							if(rsArticle.getString("Unite1")!=null && !rsArticle.getString("Unite1").equals("")){
								 unite=new TaUnite();
								 if(daoUnite.exist(rsArticle.getString("Unite1").toUpperCase())) unite=(daoUnite.findByCode(rsArticle.getString("Unite1").toUpperCase()));
								 else{//creation
									 unite.setCodeUnite(rsArticle.getString("Unite1").toUpperCase());
									 unite=daoUnite.enregistrerMerge(unite);
								 }
								 prix.setTaUnite(unite);								 
							}
							if(rsArticle.getString("Unite2")!=null && !rsArticle.getString("Unite2").equals("")){
								unite=new TaUnite();
								 if(daoUnite.exist(rsArticle.getString("Unite2").toUpperCase())) unite=daoUnite.findByCode(rsArticle.getString("Unite2").toUpperCase());
								 else{//creation
									 unite.setCodeUnite(rsArticle.getString("Unite2").toUpperCase());
									 unite=daoUnite.enregistrerMerge(unite);
								 }
							}
							if(prix!=null){
								prix.setTaArticle(article);
								article.addPrix(prix);
								article.setTaPrix(prix);
							}
						}
						
						//article.setTaRapportUnite(rsArticle.getString("Nom"));
						TaTTvaDAO daoTTva = new TaTTvaDAO(daoTiers.getEntityManager());
						article.setTaTTva(daoTTva.findByCode("D"));
						if(rsArticle.getBoolean("Inactif"))	article.setActif(0);else article.setActif(1); 
						
						
						article=daoArticle.enregistrerMerge(article);	
						daoTiers.getEntityManager().getTransaction().commit();
					}
				} catch (Exception e) {
				}
			}			
			
			//daoTiers.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			logger.error("execut failed", e);
		
		}
	}
	
	public void importClient() throws ExecutionException {
		
		try {		
			TaTiersDAO daoTiers = new TaTiersDAO();
			Connection con=connectionSqlServeur();
			Statement s = con.createStatement();
			Statement sSup = con.createStatement();			
			
			ResultSet rsTTel = s.executeQuery("select * from TelephoneEntreprise");
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			TaTTel tTel=new TaTTel();
			TaTTelDAO daoTTel = new TaTTelDAO(daoTiers.getEntityManager());
			while (rsTTel.next()) {
				try {
					if(rsTTel.getString("Type")!=null && !rsTTel.getString("Type").equals("") 
							&& !daoTTel.exist(rsTTel.getString("Type").toUpperCase())){
						tTel.setCodeTTel(rsTTel.getString("Type").toUpperCase());
						tTel.setLiblTTel(rsTTel.getString("Type"));
						tTel=daoTTel.enregistrerMerge(tTel);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
//			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
//			ResultSet rsGroupe = s.executeQuery("select * from Groupe");
//			TaFamille famille=new TaFamille();
//			TaFamilleDAO daoFamille = new TaFamilleDAO(daoTiers.getEntityManager());
//			while (rsGroupe.next()) {
//				try {
//					if(rsFamille.getString("Famille")!=null && !rsFamille.getString("Famille").equals("")  && !daoFamille.exist(rsGroupe.getString("Famille"))){
//						famille.setCodeFamille(rsGroupe.getString("Famille"));
//						famille.setLibcFamille(rsGroupe.getString("Famille"));
//						famille.setLiblFamille(rsGroupe.getString("Famille"));
//						famille=daoFamille.enregistrerMerge(famille);						
//					}
//				} catch (Exception e) {
//				}
//			}
//			daoTiers.getEntityManager().getTransaction().commit();
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsCivilite = s.executeQuery("select * from ListeChoix where NomChoix like ('FormeJuridique')");
			TaTCiviliteDAO daoCivilite = new TaTCiviliteDAO(daoTiers.getEntityManager());
			while (rsCivilite.next()) {
				try {
					TaTCivilite civilite=new TaTCivilite();
					if(rsCivilite.getString("valeur")!=null && !rsCivilite.getString("valeur").equals("")  && !daoCivilite.exist(rsCivilite.getString("valeur"))){
						civilite.setCodeTCivilite(rsCivilite.getString("valeur"));
						civilite=daoCivilite.enregistrerMerge(civilite);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsFamilleTiers = s.executeQuery("select * from ListeChoix where NomChoix like ('ListeCategorie')");
			TaFamilleTiers familleTiers=new TaFamilleTiers();
			TaFamilleTiersDAO daofamilleTiers = new TaFamilleTiersDAO(daoTiers.getEntityManager());
			while (rsFamilleTiers.next()) {
				try {
					if(rsFamilleTiers.getString("valeur")!=null && !rsFamilleTiers.getString("valeur").equals("") &&  !daofamilleTiers.exist(rsFamilleTiers.getString("valeur").toUpperCase())){
						familleTiers.setCodeFamille(rsFamilleTiers.getString("valeur").toUpperCase());
						familleTiers.setLibcFamille(rsFamilleTiers.getString("valeur"));
						familleTiers=daofamilleTiers.enregistrerMerge(familleTiers);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			rsFamilleTiers = s.executeQuery("select * from ListeChoix where NomChoix like ('ListeCibleCommercial')");
			familleTiers=new TaFamilleTiers();
			daofamilleTiers = new TaFamilleTiersDAO(daoTiers.getEntityManager());
			while (rsFamilleTiers.next()) {
				try {
					if(rsFamilleTiers.getString("valeur")!=null && !rsFamilleTiers.getString("valeur").equals("") &&   !daofamilleTiers.exist(rsFamilleTiers.getString("valeur").toUpperCase())){
						familleTiers.setCodeFamille(rsFamilleTiers.getString("valeur").toUpperCase());
						familleTiers.setLibcFamille(rsFamilleTiers.getString("valeur"));
						familleTiers=daofamilleTiers.enregistrerMerge(familleTiers);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rstypePaiement = s.executeQuery("select * from ListeChoix where NomChoix like ('ListeModeReglement')");
			TaTPaiement typePaiement=new TaTPaiement();
			TaTPaiementDAO daoTypePaiement = new TaTPaiementDAO(daoTiers.getEntityManager());
			while (rstypePaiement.next()) {
				try {
					if(rstypePaiement.getString("valeur")!=null && !rstypePaiement.getString("valeur").equals("") &&   !daoTypePaiement.exist(rstypePaiement.getString("valeur").toUpperCase())){
						typePaiement.setCodeTPaiement(rstypePaiement.getString("valeur").toUpperCase());
						typePaiement.setLibTPaiement(rstypePaiement.getString("valeur"));
						typePaiement=daoTypePaiement.enregistrerMerge(typePaiement);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
//			
//			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
//			ResultSet rsTarif = s.executeQuery("select * from Tarif");
//			TaFamille famille=new TaFamille();
//			TaFamilleDAO daoFamille = new TaFamilleDAO(daoTiers.getEntityManager());
//			while (rsTarif.next()) {
//				try {
//					if(rsTarif.getString("Famille")!=null && !daoFamille.exist(rsTarif.getString("Famille").toUpperCase())){
//						famille.setCodeFamille(rsTarif.getString("Famille").toUpperCase());
//						famille.setLibcFamille(rsTarif.getString("Famille"));
//						famille.setLiblFamille(rsTarif.getString("Famille"));
//						famille=daoFamille.enregistrerMerge(famille);						
//					}
//				} catch (Exception e) {
//				}
//			}
//			daoTiers.getEntityManager().getTransaction().commit();
			
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsTva = s.executeQuery("select * from Tva");
			TaTva tva=new TaTva();
			TaTvaDAO daoTva = new TaTvaDAO(daoTiers.getEntityManager());
			while (rsTva.next()) {
				try {
					if(rsTva.getString("TTva")!=null && !rsTva.getString("TTva").equals("") &&   !daoTva.exist(rsTva.getString("TTva").toUpperCase())){
						tva.setCodeTva(rsTva.getString("TTva").toUpperCase());
						tva.setLibelleTva(rsTva.getString("TLibelle"));
						if(rsTva.getString("TCpt")!=null && !rsTva.getString("TCpt").equals(""))tva.setNumcptTva(rsTva.getString("TCpt"));
						else tva.setNumcptTva("?");
						tva.setTauxTva(rsTva.getBigDecimal("TTaux"));
						
						tva=daoTva.enregistrerMerge(tva);						
					}
				} catch (Exception e) {
				}
			}
			daoTiers.getEntityManager().getTransaction().commit();
			
			
			if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
			ResultSet rsEntreprise = s.executeQuery("select * from Entreprise where Client =  'true'");
			TaTiers tiers=new TaTiers();
			String codeTiersTemp="";
			String code="";
			int i=0;
			while (rsEntreprise.next()) {
				if(rsEntreprise.getString("Nom")!=null && !rsEntreprise.getString("Nom").equals("")){
				try {
					if(!daoTiers.getEntityManager().getTransaction().isActive())
						daoTiers.getEntityManager().getTransaction().begin();
					
//					tiers.setNomTiers(rsEntreprise.getString("TypeEntreprise"));
//					tiers.setNomTiers(rsEntreprise.getString("nOrganisme"));
//					tiers.setNomTiers(rsEntreprise.getString("nMaisonMere"));
					tiers=new TaTiers();
//					codeTiersTemp=rsEntreprise.getString("Nom").replace(" ","");
//					if(codeTiersTemp.length()>7)codeTiersTemp=rsEntreprise.getString("Nom").replace(" ","").substring(0, 7);
//					while (daoTiers.exist(codeTiersTemp)){
//						  i=1;
//						  while (daoTiers.exist(codeTiersTemp)) {
//						   i=i+1;
//						  codeTiersTemp=codeTiersTemp.substring(0,6)+i;
//						  }	
//					}
					codeTiersTemp=daoTiers.genereCode();
					if(codeTiersTemp!=null && !daoTiers.exist(codeTiersTemp)){						
						tiers.setCodeTiers(codeTiersTemp);
						tiers.setCodeCompta(rsEntreprise.getString("NCompteC").replace("+", ""));
						tiers.setNomTiers(rsEntreprise.getString("Nom"));
						TaTCivilite civilite=null;
						if(rsEntreprise.getString("Civilite")!=null && !rsEntreprise.getString("Civilite").equals("") &&  
								daoCivilite.exist(rsEntreprise.getString("Civilite")))
							civilite=daoCivilite.findByCode(rsEntreprise.getString("Civilite"));
						tiers.setTaTCivilite(civilite);
						tiers.setCompte("411");
						tiers.setTaTTiers(new TaTTiersDAO().findByCode("C"));
						
						if(rsEntreprise.getString("NTVAIntraCom")!=null){
							TaCompl compl = new TaCompl();
							compl.setTvaIComCompl(rsEntreprise.getString("NTVAIntraCom"));
							tiers.setTaCompl(compl);
						}

//						if(rsEntreprise.getBigDecimal("Remise")!=null && rsEntreprise.getBigDecimal("Remise").signum()!=0){
//							TaTTarifDAO daoTarif = new TaTTarifDAO(daoTiers.getEntityManager());
//							if(daoTarif.findByCode())
//							TaTTarif tarif = null;
//						}
						if(rsEntreprise.getString("Adresse")!=null && rsEntreprise.getString("Ville")!=null){
							TaAdresse adresse =new TaAdresse();
							String[] tab=rsEntreprise.getString("Adresse").split("/n", -1);
							if(tab.length>0)adresse.setAdresse1Adresse(tab[0]);
							if(tab.length>1)adresse.setAdresse2Adresse(tab[1]);
							if(tab.length>2)adresse.setAdresse3Adresse(tab[2]);
							if(rsEntreprise.getString("Ville")!=null)adresse.setVilleAdresse(rsEntreprise.getString("Ville"));
							if(rsEntreprise.getString("Pays")!=null)adresse.setPaysAdresse(rsEntreprise.getString("Pays"));else adresse.setPaysAdresse("France");
							if(rsEntreprise.getString("CodePostal")!=null)adresse.setCodepostalAdresse(rsEntreprise.getString("CodePostal"));else adresse.setCodepostalAdresse("00000");
							adresse.setTaTAdr(new TaTAdrDAO(daoTiers.getEntityManager()).findByCode("FACT"));
							adresse.setCommAdministratifAdresse(1);
							adresse.setCommCommercialAdresse(1);
							adresse.setTaTiers(tiers);
							tiers.addAdresse(adresse);
						}

						
						ResultSet rstelephone = sSup.executeQuery("select * from TelephoneEntreprise where nEntreprise ="+rsEntreprise.getInt("nEntreprise") +
								" and numero is not null and  type like 'Siège' ");
//						if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
						
//						TaTelephoneDAO daoTelephone = new TaTelephoneDAO(daoTiers.getEntityManager());
						while (rstelephone.next()) {
							try {
								TaTelephone telephone=new TaTelephone();
								tTel=null;
								if(rstelephone.getString("Numero")!=null && !rstelephone.getString("Numero").equals("")  ){
									telephone.setNumeroTelephone(rstelephone.getString("Numero").toUpperCase());
									if(rstelephone.getString("Type")!=null && daoTTel.exist(rstelephone.getString("Type").toUpperCase())){
										tTel=daoTTel.findByCode(rstelephone.getString("Type").toUpperCase());
										telephone.setTaTTel(tTel);
									}									
									telephone.setCommAdministratifTelephone(1);
									telephone.setCommCommercialTelephone(1);
									telephone.setTaTiers(tiers);
									tiers.addTelephone(telephone);
//									telephone=daoTelephone.enregistrerMerge(telephone);						
								}
							} catch (Exception e) {
							}
						}
//						daoTiers.getEntityManager().getTransaction().commit();

						
//						tiers.setNomTiers(rsEntreprise.getString("FormeJuridique"));
						
						if(rsEntreprise.getString("TypeClient")!=null && !rsEntreprise.getString("TypeClient").equals("") ){
							familleTiers=null;
							if( !daofamilleTiers.exist(rsEntreprise.getString("TypeClient").toUpperCase())){
								familleTiers=new TaFamilleTiers();
								familleTiers.setCodeFamille(rsEntreprise.getString("TypeClient").toUpperCase());
								familleTiers.setLibcFamille(rsEntreprise.getString("TypeClient"));
								familleTiers.setLiblFamille(rsEntreprise.getString("TypeClient"));
								familleTiers=daofamilleTiers.enregistrerMerge(familleTiers);
							}else
								familleTiers=daofamilleTiers.findByCode(rsEntreprise.getString("TypeClient").toUpperCase());
							tiers.addFamilleTiers(familleTiers);
						}
						
//						tiers.setNomTiers(rsEntreprise.getString("CibleCommercial"));
						if(rsEntreprise.getString("ModePaiement")!=null && !rsEntreprise.getString("ModePaiement").equals("") ){
							typePaiement=null;
							if(!daoTypePaiement.exist(rsEntreprise.getString("ModePaiement").toUpperCase())){
								typePaiement=new TaTPaiement();
								typePaiement.setCodeTPaiement(rsEntreprise.getString("ModePaiement").toUpperCase());
								typePaiement.setCompte("512");
								typePaiement.setLibTPaiement(rsEntreprise.getString("ModePaiement"));
								typePaiement=daoTypePaiement.enregistrerMerge(typePaiement);

							}else
								typePaiement=daoTypePaiement.findByCode(rsEntreprise.getString("ModePaiement").toUpperCase());
							tiers.setTaTPaiement(typePaiement);

							if(rsEntreprise.getString("FinMois")!=null||rsEntreprise.getString("Echeance")!=null){
								TaCPaiement cPaiement=null;
								TaCPaiementDAO daoCPaiement = new TaCPaiementDAO(daoTiers.getEntityManager());
								TaTCPaiementDAO daoTaTCPaiement = new TaTCPaiementDAO(daoTiers.getEntityManager());
								if( !daoCPaiement.exist(rsEntreprise.getString("ModePaiement").toUpperCase())){
									cPaiement=new TaCPaiement();
									cPaiement.setCodeCPaiement(rsEntreprise.getString("ModePaiement").toUpperCase());
									cPaiement.setFinMoisCPaiement(rsEntreprise.getInt("FinMois"));
									cPaiement.setReportCPaiement(rsEntreprise.getInt("Echeance"));
									cPaiement.setLibCPaiement(rsEntreprise.getString("ModePaiement"));
									cPaiement.setTaTCPaiement(daoTaTCPaiement.findByCode("Tiers"));
									cPaiement=daoCPaiement.enregistrerMerge(cPaiement);
								}else
									cPaiement=daoCPaiement.findByCode(rsEntreprise.getString("ModePaiement").toUpperCase());
								tiers.setTaCPaiement(cPaiement);
							}
						}
//						tiers.setNomTiers(rsEntreprise.getString("Banque"));
//						tiers.setNomTiers(rsEntreprise.getString("RIB"));
						
						if(rsEntreprise.getString("Email")!=null && !rsEntreprise.getString("Email").equals("") ){
							TaEmail email = new TaEmail();
							email.setAdresseEmail(rsEntreprise.getString("Email"));
							email.setCommAdministratifEmail(1);
							email.setCommCommercialEmail(1);
							tiers.addEmail(email);
						}
						
						if(rsEntreprise.getString("SiteInternet")!=null && !rsEntreprise.getString("SiteInternet").equals("") ){
							TaWeb web = new TaWeb();
							web.setAdresseWeb(rsEntreprise.getString("SiteInternet"));
							tiers.addWeb(web);
						}
						
						if(rsEntreprise.getString("Observations")!=null && !rsEntreprise.getString("Observations").equals("") ){
							TaCommentaire commentaire=new TaCommentaire();
							commentaire.setCommentaire(rsEntreprise.getString("Observations"));
							tiers.setTaCommentaire(commentaire);
						}
						tiers.setTtcTiers(LibConversion.booleanToInt(rsEntreprise.getBoolean("FacturationTTC")));
						tiers.setActifTiers(LibConversion.booleanToInt(!rsEntreprise.getBoolean("Inactif")));
						
						//tiers.setNomTiers(rsEntreprise.getString("Remise"));
						
						TaTTvaDoc tvaDoc=null;
						TaTTvaDocDAO daoTTvaDoc=new TaTTvaDocDAO(daoTiers.getEntityManager());
						if(rsEntreprise.getString("TTva")!=null && !rsEntreprise.getString("TTva").equals("") ){							
							if(daoTTvaDoc.exist(rsEntreprise.getString("TTva").toUpperCase()))tvaDoc=daoTTvaDoc.findByCode(rsEntreprise.getString("TTva").toUpperCase());							
						}else {
							tvaDoc=daoTTvaDoc.findByCode("F");
						}
						tiers.setTaTTvaDoc(tvaDoc);
//						tiers.setNomTiers(rsEntreprise.getString("SuffixePostal"));
						//tiers.setNomTiers(rsEntreprise.getString("nTarif"));
						
						/*****enregistrement du tiers*******/
						tiers=daoTiers.enregistrerMerge(tiers);
						/**********************************/
						daoTiers.getEntityManager().getTransaction().commit();
						
						if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
						if(rsEntreprise.getString("AdresseLiv")!=null && rsEntreprise.getString("VilleLiv")!=null){
							TaAdresseDAO daoAdresse=new TaAdresseDAO(daoTiers.getEntityManager());
							TaAdresse adresseLiv =new TaAdresse();
							String[] tab=rsEntreprise.getString("AdresseLiv").split("/n", -1);
							if(tab.length>0)adresseLiv.setAdresse1Adresse(tab[0]);
							if(tab.length>1)adresseLiv.setAdresse2Adresse(tab[1]);
							if(tab.length>2)adresseLiv.setAdresse3Adresse(tab[2]);
							if(rsEntreprise.getString("VilleLiv")!=null)adresseLiv.setVilleAdresse(rsEntreprise.getString("VilleLiv"));
							if(rsEntreprise.getString("PaysLiv")!=null)adresseLiv.setPaysAdresse(rsEntreprise.getString("PaysLiv"));else adresseLiv.setPaysAdresse("France");
							if(rsEntreprise.getString("CodePostalLiv")!=null)adresseLiv.setCodepostalAdresse(rsEntreprise.getString("CodePostalLiv"));else adresseLiv.setCodepostalAdresse("00000");
							adresseLiv.setTaTAdr(new TaTAdrDAO(daoTiers.getEntityManager()).findByCode("LIV"));
							adresseLiv.setCommAdministratifAdresse(0);
							adresseLiv.setCommCommercialAdresse(0);
							adresseLiv.setTaTiers(tiers);
							adresseLiv=daoAdresse.enregistrerMerge(adresseLiv);
						}
						
						rstelephone = sSup.executeQuery("select * from TelephoneEntreprise where nEntreprise ="+rsEntreprise.getInt("nEntreprise") +
								" and numero is not null and  type not like 'Siège'");
//						if(!daoTiers.getEntityManager().getTransaction().isActive())daoTiers.getEntityManager().getTransaction().begin();
						
						TaTelephoneDAO daoTelephone = new TaTelephoneDAO(daoTiers.getEntityManager());
						while (rstelephone.next()) {
							TaTelephone telephone=new TaTelephone();
							try {
								if(rstelephone.getString("Numero")!=null && !rstelephone.getString("Numero").equals("")  ){
									telephone.setNumeroTelephone(rstelephone.getString("Numero").toUpperCase());
									if(rstelephone.getString("Type")!=null && daoTTel.exist(rstelephone.getString("Type").toUpperCase())){
										tTel=daoTTel.findByCode(rstelephone.getString("Type").toUpperCase());
										telephone.setTaTTel(tTel);
									}									
									telephone.setCommAdministratifTelephone(1);
									telephone.setCommCommercialTelephone(1);
									telephone.setTaTiers(tiers);
									telephone=daoTelephone.enregistrerMerge(telephone);						
								}
							} catch (Exception e) {
							}
						}
						
						daoTiers.getEntityManager().getTransaction().commit();
					}
				} catch (Exception e) {
					logger.error("", e);
					throw e;
				}
				}
			}


		} catch (Exception e) {
			logger.error("execut failed", e);
		
		}
	}	

	
	public void corrigeTvaArticle() throws ExecutionException {

		try {		
			TaArticleDAO daoArticle = new TaArticleDAO();
			Connection con=connectionSqlServeur();
			Statement s = con.createStatement();
			Statement sSup = con.createStatement();	


			if(!daoArticle.getEntityManager().getTransaction().isActive())daoArticle.getEntityManager().getTransaction().begin();
			ResultSet rsArticle = s.executeQuery("select * from Produit order by CodeProduit");
			TaArticle article;
			while (rsArticle.next()) {
				try {
					if(rsArticle.getString("CodeProduit")!=null ){
						article=daoArticle.findByCode(rsArticle.getString("CodeProduit").toUpperCase());
						if(article!=null){
							if(rsArticle.getBigDecimal("PrixVHT")!=null ||rsArticle.getBigDecimal("PrixVTTC")!=null){
								if(rsArticle.getBoolean("IsPrixHT")){
									article.getTaPrix().setPrixPrix(rsArticle.getBigDecimal("PrixVHT"));
									article.getTaPrix().setPrixttcPrix(article.getTaPrix().getPrixPrix().multiply(article.getTaTva().getTauxTva()).divide(BigDecimal.valueOf(100)));								
								}else{
									article.getTaPrix().setPrixttcPrix(rsArticle.getBigDecimal("PrixVTTC"));
									article.getTaPrix().setPrixPrix(article.getTaPrix().getPrixttcPrix().divide(new BigDecimal(1).
											add(article.getTaTva().getTauxTva().divide(new BigDecimal(100))),MathContext.DECIMAL32));
								}
								
							}
						}
					}

					
				} catch (Exception e) {
					logger.error("", e);
					throw e;
				}
			}
			daoArticle.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			logger.error("execut failed", e);

		}
	}
}
