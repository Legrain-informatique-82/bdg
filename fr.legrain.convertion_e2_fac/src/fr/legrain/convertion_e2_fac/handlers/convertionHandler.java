package fr.legrain.convertion_e2_fac.handlers;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaPrixDAO;
import fr.legrain.articles.dao.TaRapportUnite;
import fr.legrain.articles.dao.TaRapportUniteDAO;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosAvoir;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.documents.dao.TaLBonliv;
import fr.legrain.documents.dao.TaLDevis;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTLigne;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaCommentaire;
import fr.legrain.tiers.dao.TaCommentaireDAO;
import fr.legrain.tiers.dao.TaCompl;
import fr.legrain.tiers.dao.TaComplDAO;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTCiviliteDAO;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTelDAO;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class convertionHandler extends AbstractHandler {
	
	static Logger logger = Logger.getLogger(convertionHandler.class);
	private IWorkbenchWindow window;
	private String dir=null;
	/**
	 * The constructor.
	 */
	public convertionHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);		
		try {
			String cheminBase = "";
			DirectoryDialog dd = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),SWT.SAVE);
			dd.setText("Choix de la base E2Fac à convertir : ");
			dd.setFilterPath("C:/EnAttente/");
			cheminBase = dd.open();
			if(cheminBase!=null && !cheminBase.equals("")){
				Connection conn= connection(cheminBase);
				try {

					//****************************penser à modifier la base pour mettre les qtés sur 4 décimales *******************
					CreationTableE2Fac();
					articles(conn);
					clients(conn);
					commerciaux(conn);
					Documents(conn,TaFacture.TYPE_DOC);
					Documents(conn,TaAvoir.TYPE_DOC);
					Documents(conn,TaBonliv.TYPE_DOC);
					Documents(conn,TaDevis.TYPE_DOC);
					RelationBonLivFacture(conn);

					//********************Vérifier l'importation avec la procédure (VERIF_IMPORTATION)*********************************

					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("",e);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}

		return null;
	}
	

        
	public Connection connection(String DefaultDir){
        try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

           String connString="jdbc:odbc:Driver={Microsoft dBASE Driver (*.dbf)};DefaultDir="+ DefaultDir;//DeafultDir indicates the location of the db
           Connection connection=DriverManager.getConnection(connString);
           setDir(DefaultDir);
           return connection;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				
			} catch (SQLException e) {
				logger.error("",e);
			} 
		return null;
		  }
	
	
	public void articles(Connection connection) throws Exception{
//		for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
//		//System.out.print(resultSet.getObject(j) + "\t");
//		
//	}
		TaArticleDAO daoArticle = new TaArticleDAO();
		TaArticle article=new TaArticle();
		String tableArticle="ARTICLES.dbf";
		Statement stmt=null;
		ResultSet resultSet=null;
		try {
			
			TaFamilleDAO daoFamille = new TaFamilleDAO(daoArticle.getEntityManager());
			TaTvaDAO daoTva = new TaTvaDAO(daoArticle.getEntityManager());
			TaTTvaDAO daoTTva=new TaTTvaDAO(daoArticle.getEntityManager());
			TaUniteDAO daoUnite = new TaUniteDAO(daoArticle.getEntityManager());
			TaPrixDAO daoPrix = new TaPrixDAO(daoArticle.getEntityManager());
			TaRapportUniteDAO daoRapport = new TaRapportUniteDAO(daoArticle.getEntityManager());
			
			TaRapportUnite taRapportUnite=null;
			TaPrix taPrix = null;
			TaUnite taUnite1 = null;
			TaUnite taUnite2 = null;
			TaTTva taTTva=null;
			TaTva tva =null;
			TaFamille famille =null;
			String codearticle=null;
			
			String art_cod=null;
			String art_fam=null;
			String art_des=null;			
			BigDecimal art_ht1=null;
			BigDecimal art_ttc1=null;
			BigDecimal art_tva=null;			
			String art_cpt_tva=null;
			String art_un1=null;
			String art_un2=null;			
			BigDecimal art_1a2=null;			
			String art_cpt=null;
			String art_gtva=null;			
			BigDecimal art_ht2=null;
			BigDecimal art_ttc2=null;
			BigDecimal art_ht3=null;
			BigDecimal art_ttc3=null;
			BigDecimal art_prf=null;			
			String fam_des=null;			
			Integer fam_nar=null;			
			String art_div=null;

			

			
			String sql="select a.ART_COD, a.ART_DIV, a.ART_FAM, a.ART_DES, a.ART_HT1, a.ART_TTC1, a.ART_TVA, ('') as ART_CPT_TVA,"+
					"a.ART_UN1, a.ART_UN2, a.ART_1A2, a.ART_CPT, a.ART_GTVA, a.ART_HT2, a.ART_TTC2, a.ART_HT3, a.ART_TTC3, a.ART_PRF, f.FAM_DES, f.FAM_NAR "+
					"from "+tableArticle+" a left join FAMART.dbf f on(a.ART_FAM=f.FAM_COD) where a.ART_COD is not null order by a.ART_COD";
			//           String sql="select a.ART_COD from ARTICLES.dbf a left join FAMART.dbf f on(a.ART_FAM=f.FAM_COD) order by a.ART_COD";           
			stmt=connection.createStatement();
			resultSet=stmt.executeQuery(sql);

			daoArticle.getEntityManager().getTransaction().begin();
			Integer nb =0;
			while (resultSet.next()) {
				article = new TaArticle();
				
				art_cod=resultSet.getString("ART_COD");
				art_div=resultSet.getString("ART_DIV");
				art_fam=resultSet.getString("ART_FAM");
				art_des=resultSet.getString("ART_DES");
				
				art_ht1=resultSet.getBigDecimal("ART_HT1");
				art_ttc1=resultSet.getBigDecimal("ART_TTC1");
				art_tva=resultSet.getBigDecimal("ART_TVA");
				
				art_cpt_tva=resultSet.getString("ART_CPT_TVA");
				art_un1=resultSet.getString("ART_UN1");
				art_un2=resultSet.getString("ART_UN2");
				art_1a2=resultSet.getBigDecimal("ART_1A2");
				art_cpt=resultSet.getString("ART_CPT");
				art_gtva=resultSet.getString("ART_GTVA");
				
				art_ht2=resultSet.getBigDecimal("ART_HT2");
				art_ttc2=resultSet.getBigDecimal("ART_TTC2");
				art_ht3=resultSet.getBigDecimal("ART_HT3");
				art_ttc3=resultSet.getBigDecimal("ART_TTC3");
				art_prf=resultSet.getBigDecimal("ART_PRF");
				
				fam_des=resultSet.getString("FAM_DES");
				fam_nar=resultSet.getInt("FAM_NAR");
				
				 taRapportUnite=null;
				 taPrix = null;
				 taUnite1 = null;
				 taUnite2 = null;
				 taTTva=null;
				 tva =null;
				 famille =null;
				 codearticle=null;
				
				codearticle=art_cod;
				
				if(art_fam!=null && !art_fam.equals("")){
					if(daoFamille.exist(art_fam.toUpperCase()))famille=daoFamille.findByCode(art_fam.toUpperCase());
					else{
						famille=new TaFamille();
						famille.setCodeFamille(art_fam);
						famille.setLibcFamille(art_fam);
						famille.setLiblFamille(art_fam);
						famille=daoFamille.enregistrerMerge(famille);
					}
				}
				
				if(art_tva!=null && art_tva.compareTo(new BigDecimal(0))>0){
					tva=daoTva.findByTauxOrCreate(art_tva.toString(),false,daoArticle.getEntityManager());
				}

			
			
			if(art_gtva!=null && !art_gtva.equals("")){
				if(daoTTva.exist(art_gtva.toUpperCase()))taTTva=daoTTva.findByCode(art_gtva.toUpperCase());
				else{
					taTTva=daoTTva.findByCode("E");
				}
			}
			
			if(art_des==null)art_des="Article "+art_cod;
			
			
			  if (art_div!= null) {
				  while(codearticle.length()<13){
					  codearticle=codearticle+" ";
				  }
				  codearticle=codearticle+art_div;
			  }	
			  
			  
			  article.setCodeArticle(codearticle);
			  article.setLibellecArticle(art_des);
			  article.setLibellelArticle(art_des);
			  article.setTaTTva(taTTva);
			  article.setTaTva(tva);
			  article.setTaFamille(famille);
			  article.setNumcptArticle(art_cpt);
			  article.setDiversArticle(art_div);
			  article.setStockMinArticle(BigDecimal.ZERO);
			  article.setActif(1);
			  article=daoArticle.enregistrerMerge(article);
			  
			  if((art_ht1!=null )||(art_un1!=null && !art_un1.equals(""))){
				  if((art_un1!=null && !art_un1.equals(""))){
					  if(daoUnite.exist(art_un1.toUpperCase()))taUnite1=daoUnite.findByCode(art_un1.toUpperCase());
					  else {
						  taUnite1=new TaUnite();
						  taUnite1.setCodeUnite(art_un1.toUpperCase());
						  taUnite1.setLiblUnite(art_un1);
						  taUnite1=daoUnite.enregistrerMerge(taUnite1);
					  }
				  }
				  if((art_ht1!=null && taUnite1!=null)){
					  taPrix=new TaPrix();
					  taPrix.setPrixPrix(art_ht1);
					  taPrix.setPrixttcPrix(art_ttc1);
					  taPrix.setTaArticle(article);
					  taPrix.setTaUnite(taUnite1);
					  taPrix= daoPrix.enregistrerMerge(taPrix);
					  if(art_prf.signum()==0 || art_prf==null ||art_prf.compareTo(art_ht1)==0 ||
							  (art_prf.compareTo(art_ht2)!=0 && art_prf.compareTo(art_ht3)!=0)){
						  taPrix.setIdRefPrix(true);
						  article.setTaPrix(taPrix);
						  
					  }
					  article=daoArticle.enregistrerMerge(article);
				  }
			  }
			  if((art_ht2!=null )||(art_un2!=null && !art_un2.equals(""))){
				  if((art_un2!=null && !art_un2.equals(""))){
					  if(daoUnite.exist(art_un2.toUpperCase()))taUnite2=daoUnite.findByCode(art_un2.toUpperCase());
					  else {
						  taUnite2=new TaUnite();
						  taUnite2.setCodeUnite(art_un2.toUpperCase());
						  taUnite2.setLiblUnite(art_un2);
						  taUnite2=daoUnite.enregistrerMerge(taUnite2);
					  }
				  }
				  if((art_ht2!=null && taUnite2!=null)){
					  taPrix=new TaPrix();
					  taPrix.setPrixPrix(art_ht2);
					  taPrix.setPrixttcPrix(art_ttc2);
					  taPrix.setTaArticle(article);
					  taPrix.setTaUnite(taUnite2);
					  taPrix= daoPrix.enregistrerMerge(taPrix);
					  if(art_prf.signum()==0 || art_prf==null ||art_prf.compareTo(art_ht2)==0 ||
							  (art_prf.compareTo(art_ht1)!=0 && art_prf.compareTo(art_ht3)!=0)){
						  taPrix.setIdRefPrix(true);
						  article.setTaPrix(taPrix);
						  article=daoArticle.enregistrerMerge(article);
					  }
				  }
			  }

			  if(taUnite1!=null && taUnite2!=null && art_1a2!=null){
				  taRapportUnite=new TaRapportUnite();
				  taRapportUnite.setTaArticle(article);
				  taRapportUnite.setTaUnite1(taUnite1);
				  taRapportUnite.setTaUnite2(taUnite2);
				  taRapportUnite.setRapport(art_1a2);
				  taRapportUnite.setNbDecimal(2);
				  taRapportUnite.setSens(0);
				  taRapportUnite= daoRapport.enregistrerMerge(taRapportUnite);
				  article.setTaRapportUnite(taRapportUnite);
				  article=daoArticle.enregistrerMerge(article);
			  }
			  
			  if((art_ht3!=null && art_ht3.signum()!=0)){
				  if((art_ht3!=null && art_ht3.signum()>0)){
					  taPrix=new TaPrix();
					  taPrix.setPrixPrix(art_ht3);
					  taPrix.setPrixttcPrix(art_ttc3);
					  taPrix.setTaArticle(article);
					  taPrix.setTaUnite(null);
					  taPrix= daoPrix.enregistrerMerge(taPrix);
					  if(art_prf.signum()==0 || art_prf==null ||art_prf.compareTo(art_ht3)==0 ||
							  (art_prf.compareTo(art_ht1)!=0 && art_prf.compareTo(art_ht2)!=0)){
						  taPrix.setIdRefPrix(true);
						  article.setTaPrix(taPrix);
						  article=daoArticle.enregistrerMerge(article);
					  }
				  }
			  }			  
			  nb++;
			}
			daoArticle.getEntityManager().getTransaction().commit();

			sql="select count(*)as nb from "+tableArticle;
			resultSet=stmt.executeQuery(sql);
			while (resultSet.next()) {
				if( nb==resultSet.getInt("nb"))
					MessageDialog.openError(window.getShell(), "Importation Articles", "il y a "+resultSet.getInt("nb")+" articles /"+nb+" d'articles existants dans E2Fac");
				throw new Exception();
			}
//			resultSet.close();
			stmt.close();
		}  catch (SQLException e) {
			daoArticle.getEntityManager().getTransaction().rollback();
			if(stmt!=null && !stmt.isClosed())stmt.close();
			logger.error("",e);
		}
	}
	
	public void clients(Connection connection) throws Exception{
		
//		for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
//		//System.out.print(resultSet.getObject(j) + "\t");
//		
//	}
		TaTiersDAO daoTiers = new TaTiersDAO();
		TaTiers tiers=new TaTiers();
		Statement stmt=null;
		ResultSet resultSetTest=null;
		ResultSet resultSet= null;
		String tableClient = "clients.dbf";
		String tableCommercial = "cciaux.dbf";
		String tableParam = "PMTREG.dbf";
		try {

			TaTTelDAO daoTTel = new TaTTelDAO(daoTiers.getEntityManager());
			TaTAdrDAO daoTAdr = new TaTAdrDAO(daoTiers.getEntityManager());
			TaTTvaDocDAO daoTTvaDoc = new TaTTvaDocDAO(daoTiers.getEntityManager());
			
			TaFamilleTiersDAO daoFamilleTiers = new TaFamilleTiersDAO(daoTiers.getEntityManager());
			TaCommentaireDAO daoCommentaire = new TaCommentaireDAO(daoTiers.getEntityManager());
			TaComplDAO daoCompl = new TaComplDAO(daoTiers.getEntityManager());
			TaTTiersDAO daoTTiers = new TaTTiersDAO(daoTiers.getEntityManager());
			TaTelephoneDAO daoTelephone = new TaTelephoneDAO(daoTiers.getEntityManager());
			TaAdresseDAO daoAdresse = new TaAdresseDAO(daoTiers.getEntityManager());
			TaCompteBanqueDAO daoBanque = new TaCompteBanqueDAO(daoTiers.getEntityManager());
			TaTCiviliteDAO daoTCivilite = new TaTCiviliteDAO(daoTiers.getEntityManager());
			TaCPaiementDAO daoCpaiement = new TaCPaiementDAO(daoTiers.getEntityManager());
			
			
			TaTTel ttelFax=null;
			TaTAdr tadrFac = null;
			TaTAdr tadrLiv = null;
			TaTTvaDoc taTTvaDoc=null;
			TaFamilleTiers taFamilleTiers = null;
			TaCommentaire taCommentaire = null;
			TaCompl taCompl = null;
			TaTTiers taTTiers = null;
			TaTelephone taTelephone = null;
			TaAdresse taAdresse = null;
			TaCompteBanque taBanque = null;
			TaTCivilite taTCivilite = null;
			TaTiers taTiersCom = null;
			TaCPaiement taCPaiement = null;
			
			
			String codeTiers=null;			
			String cli_cod = null;
			String cli_nom = null;
			String cli_cpt = null;
			String cli_col = null;
			String cli_ad1 = null;
			String cli_ad2 = null;
			String cli_cp = null;
			String cli_vil = null;
			String cli_pay = null;
			String cli_tva = null;
			String cli_ass = null;
			String cli_obs = null;
			String cli_rsp = null;
			String cli_tel = null;
			String cli_fax = null;
			String cli_pmt = null;
			Integer cli_pri = null;
			String cli_bqe = null;
			String cli_rib = null;
			String cli_lad1 = null;
			String cli_lad2 = null;
			String cli_lcp = null;
			String cli_lvil = null;
			String cli_lpay = null;
			String cli_rep = null;
			String cli_civ = null;
			String cli_fam = null;
			String cli_tel0 = null;
			String cli_fax0 = null;
			String pmt_int = null;
			Integer pmt_delai = null;
			Integer pmt_jour = null;
			String pmt_lcr = null;
			Integer cli_loc = null;
			String cle_rib = null;
			String code_banque = null;
			String guichet = null;
			String compte_banque = null;
			String commentaire = null;
			String codetmp2 = null;
			String codetmp = null;
			
			
			String sqltest="select count(*)as doublons from "+tableClient+" c where exits(select c2.CLI_COD from clients.dbf c2 where c2.CLI_COD=c.CLI_COD" +
					" and c2.CLI_NOM<>c.CLI_NOM) ";
			stmt=connection.createStatement();
			Boolean continuer=true;
			resultSetTest=stmt.executeQuery(sqltest);
			while (resultSetTest.next()) {
				if(resultSetTest.getInt("doublons")!=0){
				MessageDialog.openError(window.getShell(), "Tiers en double", "il y a "+resultSetTest.getInt("doublons")+" tiers en double dans la table");
				continuer=false;
				}
			}
			
			if(continuer){
			String sql="select c.CLI_COD,c.CLI_NOM,c.CLI_CPT,c.CLI_COL,c.CLI_AD1,c.CLI_AD2,c.CLI_CP,c.CLI_VIL,c.CLI_PAY,c.CLI_TVA,"+
			" c.CLI_ASS,c.CLI_OBS,c.CLI_RSP,c.CLI_TEL,c.CLI_FAX,c.CLI_PMT,c.CLI_PRI,c.CLI_BQE,c.CLI_RIB,c.CLI_LAD1,"+
			" c.CLI_LAD2,c.CLI_LCP,c.CLI_LVIL,c.CLI_LPAY,c.CLI_REP,c.CLI_CIV,c.CLI_FAM,c.CLI_TEL0,c.CLI_FAX0,p.PMT_INT,"+
			" p.PMT_DELAI,p.PMT_JOUR,p.PMT_LCR,c.CLI_LOC"+
			" from "+tableClient+" c left join "+tableParam+" p on(c.CLI_PMT=p.PMT_COD)";
			File fichier = new File(getDir()+"/"+tableCommercial);
			if(fichier.exists()){
				sql+=" union select cc.CCL_COD,cc.CCL_NOM,cc.CCL_COD,"+
			" '421',cc.CCL_AD1,cc.CCL_AD2,cc.CCL_CP,"+
			" cc.CCL_VIL,cc.CCL_PAY,'','','','','','','', '','','','','', '','','','','','COM','','','',0,0,'',1 from "+tableCommercial+" cc ";	
			}
			
			sql+="	order by CLI_COD";
         
			resultSet=stmt.executeQuery(sql);

			daoTiers.getEntityManager().getTransaction().begin();
			ttelFax=daoTTel.findByCode("FAX");
			
			tadrFac=daoTAdr.findByCode("FACT");
			tadrLiv=daoTAdr.findByCode("LIV");
			
			Integer nb =0;
			while (resultSet.next()) {
			   tiers = new TaTiers();
				  
			
			 ttelFax=null;
			 tadrFac = null;
			 tadrLiv = null;
			 taTTvaDoc=null;
			 taFamilleTiers = null;
			 taCommentaire = null;
			 taCompl = null;
			 taTTiers = null;
			 taTelephone = null;
			 taAdresse = null;
			 taBanque = null;
			 taTCivilite = null;
			 taTiersCom = null;
			 taCPaiement = null;
			

			cli_cod=resultSet.getString("cli_cod");
			cli_nom=resultSet.getString("cli_nom");
			cli_cpt=resultSet.getString("cli_cpt");
			cli_col=resultSet.getString("cli_col");
			cli_ad1=resultSet.getString("cli_ad1");
			cli_ad2=resultSet.getString("cli_ad2");
			cli_cp=resultSet.getString("cli_cp");
			cli_vil=resultSet.getString("cli_vil");
			cli_pay=resultSet.getString("cli_pay");
			cli_tva=resultSet.getString("cli_tva");
			cli_ass=resultSet.getString("cli_ass");
			cli_obs=resultSet.getString("cli_obs");
			cli_rsp=resultSet.getString("cli_rsp");
			cli_tel =resultSet.getString("cli_tel");
			cli_fax=resultSet.getString("cli_fax");
			cli_pmt=resultSet.getString("cli_pmt");
			cli_pri=resultSet.getInt("cli_pri");
			cli_bqe=resultSet.getString("cli_bqe");
			cli_rib=resultSet.getString("cli_rib");
			cli_lad1=resultSet.getString("cli_lad1");
			cli_lad2=resultSet.getString("cli_lad2");
			cli_lcp=resultSet.getString("cli_lcp");
			cli_lvil=resultSet.getString("cli_lvil");
			cli_lpay=resultSet.getString("cli_lpay");
			cli_civ=resultSet.getString("cli_civ");
			cli_fam=resultSet.getString("cli_fam");
			cli_tel0=resultSet.getString("cli_tel0");
			cli_fax0=resultSet.getString("cli_fax0");
			pmt_int=resultSet.getString("pmt_int");
			pmt_delai=resultSet.getInt("pmt_delai");
			pmt_jour=resultSet.getInt("pmt_jour");
			pmt_lcr=resultSet.getString("pmt_lcr");
			cli_loc=resultSet.getInt("cli_loc");

			if(cli_loc == 1)taTTvaDoc=daoTTvaDoc.findByCode("F");
			if(cli_loc == 2)taTTvaDoc=daoTTvaDoc.findByCode("UE");
			if(cli_loc == 3)taTTvaDoc=daoTTvaDoc.findByCode("HUE");
			if(cli_loc == 4)taTTvaDoc=daoTTvaDoc.findByCode("N");
				
				codeTiers=cli_cod;
				
				  if (cli_pri == null)  cli_pri=0;
				  taTTiers= daoTTiers.findByCode("C");
				    
				if(cli_civ!=null && !cli_civ.equals("")){
					if(daoTCivilite.exist(cli_civ.toUpperCase()))taTCivilite=daoTCivilite.findByCode(cli_civ.toUpperCase());
					else{
						taTCivilite=new TaTCivilite();
						taTCivilite.setCodeTCivilite(cli_civ.toUpperCase());
						taTCivilite=daoTCivilite.enregistrerMerge(taTCivilite);
					}
				}
				if(cli_pmt!=null && !cli_pmt.equals("") && pmt_int != null){
					if(daoCpaiement.exist(cli_pmt.toUpperCase()))taCPaiement=daoCpaiement.findByCode(cli_pmt.toUpperCase());
					else{
						taCPaiement=new TaCPaiement();
						taCPaiement.setCodeCPaiement(cli_pmt.toUpperCase());
						taCPaiement.setLibCPaiement(pmt_int);
						taCPaiement.setFinMoisCPaiement(pmt_jour);
						taCPaiement.setReportCPaiement(pmt_delai);	
						taCPaiement.setTaTCPaiement(new TaTCPaiementDAO(daoTiers.getEntityManager()).findById(1));
						taCPaiement=daoCpaiement.enregistrerMerge(taCPaiement);
					}
				}
				
				if((cli_obs!=null && !cli_obs.equals(""))||(cli_rsp!=null && !cli_rsp.equals(""))){
				    if (cli_rsp != null)    commentaire =cli_rsp+"/rn";
				    if (cli_obs != null)    commentaire =commentaire+cli_obs;
						taCommentaire=new TaCommentaire();
						taCommentaire.setCommentaire(commentaire);
						taCommentaire=daoCommentaire.enregistrerMerge(taCommentaire);
					
				}

				if((cli_tva!=null && !cli_tva.equals(""))||(cli_ass!=null && !cli_ass.equals(""))){
						taCompl=new TaCompl();
						taCompl.setTvaIComCompl(cli_tva);
						taCompl.setAccise(cli_ass);
						taCompl=daoCompl.enregistrerMerge(taCompl);					
				}
				
				  if (cli_cpt == null)  cli_cpt=cli_cod;
				  if (cli_nom == null)  cli_nom= cli_cod;
				  if (cli_col == null)  cli_col="411";

				  tiers=new TaTiers();
				  tiers.setCodeTiers(codeTiers);
				  tiers.setActifTiers(1);
				  tiers.setCodeCompta(cli_cpt);
				  tiers.setCompte(cli_col);
				  tiers.setNomTiers(cli_nom);
				  tiers.setTtcTiers(cli_pri);
				  tiers.setTaCommentaire(taCommentaire);
				  tiers.setTaTCivilite(taTCivilite);
				  tiers.setTaTTiers(taTTiers);
				  tiers.setTaCPaiement(taCPaiement);
				  tiers.setTaCompl(taCompl);
				  tiers.setTaTTvaDoc(taTTvaDoc);
				  
					if(cli_fam!=null && !cli_fam.equals("")){
						if(daoFamilleTiers.exist(cli_fam.toUpperCase()))taFamilleTiers=daoFamilleTiers.findByCode(cli_fam.toUpperCase());
						else{
							taFamilleTiers=new TaFamilleTiers();
							taFamilleTiers.setCodeFamille(cli_fam.toUpperCase());
							taFamilleTiers.setLibcFamille(cli_fam);
							taFamilleTiers.setLiblFamille(cli_fam);
//							taFamilleTiers=daoFamilleTiers.enregistrerMerge(taFamilleTiers);
							tiers.addFamilleTiers(taFamilleTiers);
						}
					}				  

				  tiers=daoTiers.enregistrerMerge(tiers);
				  
				  compte_banque="00000000000";
				  
				  if (cli_bqe != null && !cli_bqe.equals("")){
					  int rangMax=cli_rib.length();
				    if (cli_rib != null && !cli_rib.equals("")){
				    	taBanque = new TaCompteBanque();
				    	if(rangMax>5)taBanque.setCodeBanque( cli_rib.substring(1, 5));else if(rangMax>1)taBanque.setCodeBanque( cli_rib.substring(1, rangMax));  //substr(:cli_rib,1,5);
				    	if(cli_rib.length()>10)taBanque.setCodeGuichet(cli_rib.substring(6, 10));else if(rangMax>6)taBanque.setCodeBanque( cli_rib.substring(6, rangMax)); //substr(:cli_rib,6,10);
				    	if(cli_rib.length()>21)taBanque.setCompte(cli_rib.substring(11, 21));else if(rangMax>11)taBanque.setCodeBanque( cli_rib.substring(11, rangMax)); //substr(:cli_rib,11,21);
				    	if(cli_rib.length()>24)taBanque.setCleRib(cli_rib.substring(22, 24));else if(rangMax>22)taBanque.setCodeBanque( cli_rib.substring(22, rangMax)); //substr(:cli_rib,22,24);
				      }	
				    rangMax=cli_bqe.length();
				    if(cli_bqe.length()>20)taBanque.setNomBanque(cli_bqe.substring(1, 20));else if(rangMax>1)taBanque.setCodeBanque( cli_bqe.substring(1, rangMax)); 
			    	taBanque.setTaTiers(tiers);
				    daoBanque.enregistrerMerge(taBanque);
				    tiers.addCompteBanque(taBanque);
				  }
				  
				  if(cli_cp == null &&(
						  (cli_ad1 != null && !cli_ad1.trim().equals("")) || (cli_ad2 != null && !cli_ad2.trim().equals("")) ||
						  (cli_vil !=null && !cli_vil.trim().equals(""))
						   || (cli_pay !=null && !cli_pay.trim().equals(""))  ))  cli_cp="-";

				  if (cli_vil == null)  cli_vil=" ";
				  if (cli_pay == null)   cli_pay="FRANCE";
				  if (cli_cp != null)  {
					  taAdresse=new TaAdresse();
					  taAdresse.setAdresse1Adresse(cli_ad1);
					  taAdresse.setAdresse2Adresse(cli_ad2);
					  taAdresse.setCodepostalAdresse(cli_cp);
					  taAdresse.setVilleAdresse(cli_vil);
					  taAdresse.setPaysAdresse(cli_pay);
					  taAdresse.setCommAdministratifAdresse(0);
					  taAdresse.setCommCommercialAdresse(0);
					  taAdresse.setTaTAdr(tadrFac);
					  taAdresse.setTaTiers(tiers);
					  taAdresse=daoAdresse.enregistrerMerge(taAdresse);
					  tiers.addAdresse(taAdresse);
					  
				  }
				  if (cli_lvil == null)  cli_lvil=" ";
				  if (cli_lpay == null)   cli_lpay="FRANCE";
				  if (cli_lcp != null)  {
					  taAdresse=new TaAdresse();
					  taAdresse.setAdresse1Adresse(cli_lad1);
					  taAdresse.setAdresse2Adresse(cli_lad2);
					  taAdresse.setCodepostalAdresse(cli_lcp);
					  taAdresse.setVilleAdresse(cli_lvil);
					  taAdresse.setPaysAdresse(cli_lpay);
					  taAdresse.setCommAdministratifAdresse(0);
					  taAdresse.setCommCommercialAdresse(0);
					  taAdresse.setTaTAdr(tadrLiv);
					  taAdresse.setTaTiers(tiers);
					  taAdresse=daoAdresse.enregistrerMerge(taAdresse);					  
				  }
				  
				  if (cli_tel != null) {
				  taTelephone = new TaTelephone();
				  taTelephone.setCommAdministratifTelephone(0);
				  taTelephone.setCommCommercialTelephone(0);
				  taTelephone.setNumeroTelephone(cli_tel);				 
				  taTelephone.setTaTiers(tiers);
				  taTelephone=daoTelephone.enregistrerMerge(taTelephone);
				  tiers.addTelephone(taTelephone);
				  }				  
				  
				  if (cli_fax != null) {
				  taTelephone = new TaTelephone();
				  taTelephone.setCommAdministratifTelephone(0);
				  taTelephone.setCommCommercialTelephone(0);
				  taTelephone.setNumeroTelephone(cli_fax);
				  taTelephone.setTaTTel(ttelFax);
				  taTelephone.setTaTiers(tiers);
				  taTelephone=daoTelephone.enregistrerMerge(taTelephone);
				  }
				  
				  if (cli_tel0 != null) {
				  taTelephone = new TaTelephone();
				  taTelephone.setCommAdministratifTelephone(0);
				  taTelephone.setCommCommercialTelephone(0);
				  taTelephone.setNumeroTelephone(cli_tel0);				 
				  taTelephone.setTaTiers(tiers);
				  taTelephone=daoTelephone.enregistrerMerge(taTelephone);
				  }				  
				  
				  if (cli_fax0 != null) {
				  taTelephone = new TaTelephone();
				  taTelephone.setCommAdministratifTelephone(0);
				  taTelephone.setCommCommercialTelephone(0);
				  taTelephone.setNumeroTelephone(cli_fax0);
				  taTelephone.setTaTTel(ttelFax);
				  taTelephone.setTaTiers(tiers);
				  taTelephone=daoTelephone.enregistrerMerge(taTelephone);
				  }				  
			nb++;
			}
			
			tiers=daoTiers.enregistrerMerge(tiers);
			daoTiers.getEntityManager().getTransaction().commit();
			sql="select count(*)as nb from "+tableClient;
			if(fichier.exists()){
				sql+="union select count(*)as nb from "+tableCommercial;
			}
			resultSet=stmt.executeQuery(sql);
			while (resultSet.next()) {
				if( nb==resultSet.getInt("nb"))
					MessageDialog.openError(window.getShell(), "Importation tiers", "il y a "+resultSet.getInt("nb")+" tiers /"+nb+" de tiers existants dans E2Fac");
				throw new Exception();
			}
			
//			if(resultSet!=null && !resultSet.isClosed())resultSet.close();
			}
			stmt.close();
		}  catch (SQLException e) {
			daoTiers.getEntityManager().getTransaction().rollback();
//			if(resultSetTest!=null && !resultSetTest.isClosed())resultSetTest.close();
//			if(resultSet!=null && !resultSet.isClosed())resultSet.close();
			if(stmt!=null && !stmt.isClosed())stmt.close();
			logger.error("",e);
		}
	}
	


	public void CreationTableE2Fac() throws Exception{

		JPABdLgr bdlgr=new JPABdLgr();
		PreparedStatement prep=null;
		ResultSet result=null;
		try {
			String requete=null;
			
//			requete="SELECT count(*)as nb FROM RDB$RELATIONS a where upper(a.RDB$RELATION_NAME) = = 'ARTICLES_FAC' ";
//			prep= bdlgr.getCnx().prepareStatement(requete);
//			result=prep.executeQuery();
//			if( result.next() && result.getInt("nb")==0){
//		    requete="create table articles_fac(id integer,ART_COD varchar(14),ART_DIV varchar(2),ART_FAM varchar(4),ART_DES varchar(53),ART_HT1 numeric(15,2)," +
//		    		"ART_TTC1 numeric(15,2),ART_TVA numeric(15,2),ART_CPT_TVA varchar(8),ART_UN1 varchar(14),ART_UN2 varchar(14),ART_1A2 numeric(15,2)," +
//		    		"ART_CPT varchar(8),ART_GTVA varchar(1),ART_HT2 numeric(15,2),ART_TTC2 numeric(15,2),ART_HT3 numeric(15,2),ART_TTC3 numeric(15,2)," +
//		    		"ART_PRF numeric(15,2),FAM_DES varchar(31),FAM_NAR integer)";		   
//			prep= bdlgr.getCnx().prepareStatement(requete);
//			prep.executeUpdate();
//			}
//			
//			requete="SELECT  count(*)as nb FROM RDB$RELATIONS a where upper(a.RDB$RELATION_NAME) =  'CLIENTS_E2FAC' ";
//			prep= bdlgr.getCnx().prepareStatement(requete);
//			result=prep.executeQuery();
//			if( result.next() && result.getInt("nb")==0){
//			requete="create table clients_E2Fac(id integer,CLI_COD varchar(8),CLI_NOM varchar(31),CLI_CPT varchar(8),CLI_COL varchar(9),CLI_AD1 varchar(31)," +
//					"CLI_AD2 varchar(31),CLI_CP varchar(6),CLI_VIL varchar(26),CLI_PAY varchar(21),CLI_TVA varchar(15),CLI_ASS varchar(13),CLI_OBS varchar(31)," +
//					"CLI_RSP varchar(31),CLI_TEL varchar(16),CLI_FAX varchar(16),CLI_PMT varchar(2),CLI_PRI varchar(2),CLI_BQE varchar(31),CLI_RIB varchar(24)," +
//					"CLI_LAD1 varchar(31),CLI_LAD2 varchar(31),CLI_LCP varchar(6),CLI_LVIL varchar(26),CLI_LPAY varchar(21),CLI_REP varchar(8)," +
//					"CLI_CIV varchar(13),CLI_FAM varchar(4),CLI_TEL0 varchar(16),CLI_FAX0 varchar(16),PMT_INT varchar(31),PMT_DELAI integer,PMT_JOUR integer," +
//					"PMT_LCR varchar(2),CLI_LOC integer)";
//			prep= bdlgr.getCnx().prepareStatement(requete);
//			prep.executeUpdate();
//			}
			requete="select count(*)as nb from RDB$RELATION_CONSTRAINTS a where RDB$RELATION_NAME='TA_COMPTE_BANQUE' and RDB$CONSTRAINT_NAME = 'UNQ1_TA_COMPTE_BANQUE'";
			prep= bdlgr.getCnx().prepareStatement(requete);	
			result=prep.executeQuery();
			if( result.next() && result.getInt("nb")!=0){
				requete="ALTER TABLE TA_COMPTE_BANQUE DROP CONSTRAINT UNQ1_TA_COMPTE_BANQUE";
				prep= bdlgr.getCnx().prepareStatement(requete);
				prep.executeUpdate();
			}
			
			
			requete="SELECT count(*)as nb FROM RDB$RELATION_FIELDS a where upper(a.RDB$RELATION_NAME) =  'TA_R_DOCUMENT' and  " +
					" a.RDB$FIELD_NAME ='ID_AVIS_ECHEANCE'";
			prep= bdlgr.getCnx().prepareStatement(requete);	
			result=prep.executeQuery();
			if( result.next() && result.getInt("nb")==0){
				requete="ALTER TABLE TA_R_DOCUMENT ADD ID_AVIS_ECHEANCE DID_FACULTATIF";
				prep= bdlgr.getCnx().prepareStatement(requete);
				prep.executeUpdate();
			}
			
			
			requete="SELECT  count(*)as nb FROM RDB$RELATIONS a where upper(a.RDB$RELATION_NAME) = 'DOCUMENTS_E2FAC' ";
			prep= bdlgr.getCnx().prepareStatement(requete);
			result=prep.executeQuery();
			if( result.next() && result.getInt("nb")==0){
					requete="create table Documents_E2Fac(FAC_NUM varchar(8),FAC_TYP varchar(2),FAC_DAT Date,FAC_ECH Date,FAC_COD varchar(8),FAC_NOM varchar(31)," +
							"FAC_CPT varchar(8),FAC_COL varchar(9),FAC_AD1 varchar(31),FAC_AD2 varchar(31),FAC_CP varchar(6),FAC_VIL varchar(26),FAC_PAY varchar(21)," +
							"FAC_REF varchar(41),FAC_PMT varchar(2),FAC_PRI varchar(2),FAC_HT numeric(15,2),FAC_TTC numeric(15,2),FAC_REMP numeric(15,2)," +
							"FAC_REM numeric(15,2),FAC_REMT numeric(15,2),FAC_ESCP numeric(15,2),FAC_ESC numeric(15,2),FAC_MES1 varchar(69),FAC_MES2 varchar(69)," +
							"FAC_MES3 varchar(69),FAC_CPTA integer,FAC_EDITE integer,FAC_FAC varchar(8),FAC_ACT numeric(15,2),FAC_CIAL varchar(8),FAC_MREG numeric(15,2)," +
							"fac_loc varchar(10))";
					prep= bdlgr.getCnx().prepareStatement(requete);
					prep.executeUpdate();
			}
			
			requete="SELECT  count(*)as nb FROM RDB$RELATIONS a where upper(a.RDB$RELATION_NAME) = 'LIGNES_DOCS_E2FAC' ";
			prep= bdlgr.getCnx().prepareStatement(requete);
			result=prep.executeQuery();
			if( result.next() && result.getInt("nb")==0){
			requete="create table lignes_Docs_E2Fac(Type_Docs varchar(1),FLI_NUM varchar(8),FLI_LIG integer,FLI_COD varchar(15),FLI_DES varchar(53)," +
					"FLI_QT1 numeric(15,4),FLI_QT2 numeric(15,4),FLI_PHT numeric(15,2),FLI_PTC numeric(15,2),FLI_REM numeric(15,2),FLI_MHT numeric(15,2)," +
					"FLI_MTC numeric(15,2),FLI_MTVA numeric(15,2),FLI_TXTVA numeric(15,2),FLI_CPT varchar(9),FLI_TMP varchar(2),FLI_EXIG varchar(2)," +
					"FLI_LOT varchar(16),FLI_NBL varchar(8),FLI_UN1 varchar(3),FLI_UN2 varchar(3))";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();
			}			
			
			
			requete="update RDB$RELATION_FIELDS set RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and (RDB$RELATION_NAME = 'TA_L_ACOMPTE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();

			requete="update RDB$RELATION_FIELDS set RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_ACOMPTE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and (RDB$RELATION_NAME = 'TA_L_AVOIR')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_AVOIR')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();

			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_APPORTEUR')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_APPORTEUR')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();

			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_BONCDE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();

			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_BONCDE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_BONLIV')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_BONLIV')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();



			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_DEVIS')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_DEVIS')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_FACTURE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_FACTURE')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_PRELEVEMENT')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_PRELEVEMENT')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_PROFORMA')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();


			requete="update RDB$RELATION_FIELDS set	RDB$FIELD_SOURCE = 'DLONG'	where (RDB$FIELD_NAME = 'QTE2_L_DOCUMENT') and	(RDB$RELATION_NAME = 'TA_L_PROFORMA')";
			prep= bdlgr.getCnx().prepareStatement(requete);
			prep.executeUpdate();
			
			
			if(result!=null)result.close();
			if(prep!=null)prep.close();
		}  catch (SQLException e) {
			logger.error("",e);
			if(result!=null)result.close();
			if(prep!=null)prep.close();
		}
	}

	
	public void RelationBonLivFacture(Connection connection) throws Exception{

		TaFactureDAO daoFacture = new TaFactureDAO();
		TaBonlivDAO daoBonliv = new TaBonlivDAO();
		TaRDocumentDAO daoRDocument = new TaRDocumentDAO();
		TaFacture facture = null;
		TaBonliv bonliv = null;
		TaRDocument rDocument = null;

		Statement stmt=null;
		ResultSet resultSetTest=null;
		ResultSet resultSet= null;
		
		String table="bon.dbf";
		try {



			String fac_fac = null;
			String fac_num = null;


			String sql="select d.fac_num,d.fac_fac from "+table+" d where d.fac_typ='B' and d.fac_fac is not null ";
			
			stmt=connection.createStatement();
			resultSet=stmt.executeQuery(sql);

			daoRDocument.getEntityManager().getTransaction().begin();


			while (resultSet.next()) {
				fac_fac=resultSet.getString("fac_fac");
				fac_num=resultSet.getString("fac_num");
				if(fac_fac!=null){
					if(daoFacture.exist(fac_fac))facture=daoFacture.findByCode(fac_fac);
					if(daoBonliv.exist(fac_num))bonliv=daoBonliv.findByCode(fac_num);
					rDocument = new TaRDocument();
					rDocument.setTaFacture(facture);
					rDocument.setTaBonliv(bonliv);
					daoRDocument.enregistrerMerge(rDocument);
				}
			}
			
			daoRDocument.getEntityManager().getTransaction().commit();
//			resultSet.close();
			stmt.close();
		}  catch (SQLException e) {
			daoRDocument.getEntityManager().getTransaction().rollback();
//			if(resultSetTest!=null)resultSetTest.close();
//			if(resultSet!=null)resultSet.close();
			if(stmt!=null)stmt.close();
			logger.error("",e);
		}
	}

	
	public void commerciaux(Connection connection) throws Exception{

		TaTiersDAO daoTiers = new TaTiersDAO();
		
		TaTiers tiers=new TaTiers();
		TaTiers tiersCom=new TaTiers();
		Statement stmt=null;
		ResultSet resultSetTest=null;
		ResultSet resultSet= null;

		try {



			String codeTiers=null;			
			String cli_cod = null;
			String cli_rep = null;


			String sql="select c.cli_cod,c.cli_rep from clients c where cli_rep is not null ";
			
			stmt=connection.createStatement();
			resultSet=stmt.executeQuery(sql);

			daoTiers.getEntityManager().getTransaction().begin();


			while (resultSet.next()) {
				tiers = new TaTiers();

				cli_cod=resultSet.getString("cli_cod");
				cli_rep=resultSet.getString("cli_rep");

				if(cli_rep !=null){
					tiers=daoTiers.findByCode(cli_cod);		
					tiersCom=daoTiers.findByCode(cli_rep);
					if(tiers==null || tiersCom==null){
						MessageDialog.openError(window.getShell(), "Tiers manquant", "il manque un des tiers pour créer le lien");
						new SQLException();
					}
					tiers.addCommercial(tiersCom);
				}
				tiers=daoTiers.enregistrerMerge(tiers);
			}
			
			daoTiers.getEntityManager().getTransaction().commit();
//			resultSet.close();
			stmt.close();
		}  catch (SQLException e) {
			daoTiers.getEntityManager().getTransaction().rollback();
//			if(resultSetTest!=null)resultSetTest.close();
//			if(resultSet!=null)resultSet.close();
			if(stmt!=null)stmt.close();
			logger.error("",e);
		}
	}

	
	public void Documents(Connection connection, String typeDoc) throws Exception{

		AbstractLgrDAO daoDocument = null;
		IDocumentTiers document=null;
		ILigneDocumentTiers taLDocument = null;
		IInfosDocumentTiers taInfosDocument = null;
		String nomTableDoc = null;
		String nomTableLigne = null;
		String libelleDoc = null;
		
		if(typeDoc.equals(TaFacture.TYPE_DOC)){
			daoDocument = new TaFactureDAO();
			document=new TaFacture();
			nomTableDoc="fac.dbf";
			nomTableLigne="facdet.dbf";
			libelleDoc="Facture E2-Fac N ";
		}
		
		if(typeDoc.equals(TaAvoir.TYPE_DOC)){
			daoDocument = new TaAvoirDAO();
			document=new TaAvoir();
			nomTableDoc="fac.dbf";
			nomTableLigne="facdet.dbf";			
			libelleDoc="Avoir E2-Fac N ";
		}
		
		if(typeDoc.equals(TaDevis.TYPE_DOC)){
			daoDocument = new TaDevisDAO();
			document=new TaDevis();
			nomTableDoc="dev.dbf";
			nomTableLigne="devdet.dbf";	
			libelleDoc="Devis E2-Fac N ";
		}
		
		if(typeDoc.equals(TaBonliv.TYPE_DOC)){
			daoDocument = new TaBonlivDAO();
			document=new TaBonliv();
			nomTableDoc="bon.dbf";
			nomTableLigne="bondet.dbf";	
			libelleDoc="Bon Livraison E2-Fac N ";
		}
		
		TaReglementDAO daoReglement = new TaReglementDAO(daoDocument.getEntityManager());
		TaReglement reglement = null;


		Statement stmt=null;
		Statement stmtLigne=null;
		ResultSet resultSetLigne=null;
		ResultSet resultSet= null;
//		QueryDataSet dataSetRech = new QueryDataSet();
		JPABdLgr bdlgr=new JPABdLgr();
		
		try {

			TaFamilleDAO daoFamille = new TaFamilleDAO(daoDocument.getEntityManager());
			TaTLigneDAO daoTaTLigne = new TaTLigneDAO(daoDocument.getEntityManager());
			TaTCiviliteDAO daoTCivilite = new TaTCiviliteDAO(daoDocument.getEntityManager());
			TaCPaiementDAO daoCPaiementDAO = new TaCPaiementDAO(daoDocument.getEntityManager());
			TaTTvaDocDAO daoTTvaDoc = new TaTTvaDocDAO(daoDocument.getEntityManager());
			TaTiersDAO daoTiers = new TaTiersDAO(daoDocument.getEntityManager());
			TaTvaDAO daoTva = new TaTvaDAO(daoDocument.getEntityManager());
			TaArticleDAO daoArticle = new TaArticleDAO(daoDocument.getEntityManager());
			

			TaTiers tiers=null;			
			TaTLigne taTLigneH=null;
			TaTLigne taTLigneC=null;
			TaTLigne taTLigne=null;
			TaTTvaDoc taTTvaDoc=null;
			TaCPaiement taCPaiement = null;
			TaTva taTva = null;
			TaArticle taArticle=null;
			
			
			Integer lignevide = 0;
			String fac_num =null;
			String fac_typ =null;
			Date fac_dat=null;
			Date fac_ech =null;
			String fac_cod =null;
			String fac_nom =null;
			String fac_cpt =null;
			String fac_col =null;
			String fac_ad1 =null;
			String fac_ad2 =null;
			String fac_cp =null;
			String fac_vil =null;
			String fac_pay =null;
			String fac_ref =null;
			String fac_pmt =null;
			String fac_pri =null;
			BigDecimal fac_ht =null;
			BigDecimal fac_ttc =null;
			BigDecimal fac_remp =null;
			BigDecimal fac_rem =null;
			BigDecimal fac_remt =null;
			BigDecimal fac_escp =null;
			BigDecimal fac_esc =null;
			String fac_mes1 =null;
			String fac_mes2 =null;
			String fac_mes3 =null;
			Integer fac_cpta =null;
			String fac_edite =null;
			String fac_fac =null;
			BigDecimal fac_act =null;
			String fac_cial =null;
			BigDecimal fac_mreg =null;
			Integer fac_loc =null;
			String commentaire =null;
			String civilite =null;
			String tvaintra =null;
			String fli_num =null;
			Integer fli_lig =null;
			String fli_cod =null;
			String fli_des =null;
			BigDecimal fli_qt1 =null;
			BigDecimal fli_qt2 =null;
			BigDecimal fli_pht =null;
			BigDecimal fli_ptc =null;
			BigDecimal fli_rem  =null;
			BigDecimal fli_mht =null;
			BigDecimal fli_mtc =null;
			BigDecimal fli_mtva =null;
			BigDecimal fli_txtva =null;
			String fli_cpt =null;
			String fli_tmp =null;
			String fli_exig =null;
			String fli_lot =null;
			String fli_nbl =null;
			String fli_un1 =null;
			String fli_un2 =null;
			Integer rang =null;
			BigDecimal remiseT =null;
			String codeTva =null;
			BigDecimal prix =null;
			String tvaIntra = null;
//			Integer ligneVide =null;
			String LocalisationTva =null;
			String type = null;
			boolean ttc=false;
			
			if(typeDoc.equals(TaFacture.TYPE_DOC)){
				type="F";
			}
			if(typeDoc.equals(TaAvoir.TYPE_DOC)){
				type="A";
			}
			if(typeDoc.equals(TaBonliv.TYPE_DOC)){
				type="B";
			}
			if(typeDoc.equals(TaDevis.TYPE_DOC)){
				type="P";
			}
 
			String sql="select distinct(fli_txtva) from "+nomTableLigne+" d " ;
			
			stmt=connection.createStatement();
			resultSet=stmt.executeQuery(sql);
			while (resultSet.next()) {
				fli_txtva=resultSet.getBigDecimal("fli_txtva");
				taTva=daoTva.findByTauxOrCreate(LibConversion.bigDecimalToString(fli_txtva),true,daoTva.getEntityManager());
				
			}
			Integer nb =0;
			sql="select d.fac_num,d.fac_typ,d.fac_dat,d.fac_ech,d.fac_cod,d.fac_nom,d.fac_cpt,d.fac_col "+
					",d.fac_ad1,d.fac_ad2,d.fac_cp,d.fac_vil,d.fac_pay,d.fac_ref,d.fac_pmt,d.fac_pri,d.fac_ht,d.fac_ttc,d.fac_remp "+
					",d.fac_rem,d.fac_remt,d.fac_escp,d.fac_esc,d.fac_mes1,d.fac_mes2,d.fac_mes3,d.fac_cpta "+
					",d.fac_edite,d.fac_fac,d.fac_act,d.fac_cial,d.fac_mreg,d.fac_loc from "+nomTableDoc+" d " +
							" where (d.fac_typ)='"+type+"' ";  // order by 1   // and d.fac_num  > '1200309'
			
			stmt=connection.createStatement();
			resultSet=stmt.executeQuery(sql);


			taTLigneH=daoTaTLigne.findByCode("H");
			taTLigneC=daoTaTLigne.findByCode("C");
			Integer modulo=0;
			while (resultSet.next()) {
				commentaire="";
				tvaintra="";
				tiers=null;			
				taTLigne=null;
				taTTvaDoc=null;
				taCPaiement = null;
				taTva = null;

					if(!daoDocument.getEntityManager().getTransaction().isActive())
						daoDocument.getEntityManager().getTransaction().begin();

				
				fac_num=resultSet.getString("fac_num");
				fac_typ=resultSet.getString("fac_typ");
				fac_dat=resultSet.getDate("fac_dat");
				fac_ech=resultSet.getDate("fac_ech");
				fac_cod=resultSet.getString("fac_cod");
				fac_nom=resultSet.getString("fac_nom");
				fac_cpt=resultSet.getString("fac_cpt");
				fac_col=resultSet.getString("fac_col");
				if(fac_col==null)fac_col="411";
				fac_ad1=resultSet.getString("fac_ad1");
				fac_ad2=resultSet.getString("fac_ad2");
				fac_cp=resultSet.getString("fac_cp");
				fac_vil=resultSet.getString("fac_vil");
				fac_pay=resultSet.getString("fac_pay");
				fac_ref=resultSet.getString("fac_ref");
				fac_pmt=resultSet.getString("fac_pmt");
				fac_pri=resultSet.getString("fac_pri");
				fac_ht=resultSet.getBigDecimal("fac_ht");
				fac_ttc=resultSet.getBigDecimal("fac_ttc");
				fac_remp=resultSet.getBigDecimal("fac_remp");
				fac_rem=resultSet.getBigDecimal("fac_rem");
				fac_remt=resultSet.getBigDecimal("fac_remt");
				fac_escp=resultSet.getBigDecimal("fac_escp");
				fac_esc=resultSet.getBigDecimal("fac_esc");
				fac_mes1=resultSet.getString("fac_mes1");
				fac_mes2=resultSet.getString("fac_mes2");
				fac_mes3=resultSet.getString("fac_mes3");
				fac_cpta=LibConversion.booleanToInt(LibConversion.StringToBoolean(resultSet.getString("fac_cpta")));
				fac_edite=resultSet.getString("fac_edite");
				if(fac_edite!=null && fac_edite.equals("*"))fac_edite="1";
				fac_fac=resultSet.getString("fac_fac");
				fac_act=resultSet.getBigDecimal("fac_act");
				fac_cial=resultSet.getString("fac_cial");
				fac_mreg=resultSet.getBigDecimal("fac_mreg");
				fac_loc=resultSet.getInt("fac_loc");
				ttc=LibConversion.StringToBoolean(fac_pri);
				
				if(fac_act!=null && fac_act.compareTo(BigDecimal.ZERO)!=0){
					if(fac_mreg==null)fac_mreg=BigDecimal.ZERO;
					fac_mreg=fac_mreg.add(fac_act);
				}
				
				
				if(fac_loc == 1)taTTvaDoc=daoTTvaDoc.findByCode("F");
				if(fac_loc == 2)taTTvaDoc=daoTTvaDoc.findByCode("UE");
				if(fac_loc == 3)taTTvaDoc=daoTTvaDoc.findByCode("HUE");
				if(fac_loc == 4)taTTvaDoc=daoTTvaDoc.findByCode("N");
				LocalisationTva=taTTvaDoc.getCodeTTvaDoc();
				
				if(fac_mreg == null) fac_mreg=BigDecimal.ZERO;
				if(daoCPaiementDAO.exist(fac_pmt))taCPaiement=daoCPaiementDAO.findByCode(fac_pmt);
				tiers=daoTiers.findByCode(fac_cod.toUpperCase());
				if(tiers.getTaCompl()!=null)tvaIntra=tiers.getTaCompl().getTvaIComCompl();
				
				commentaire="";
			       if (fac_ref != null)  commentaire = fac_ref +"\rn";
			       if (fac_mes1 != null)  commentaire = commentaire +fac_mes1+"\rn";
			       if (fac_mes2 != null)  commentaire = commentaire +fac_mes2+"\rn";
			       if (fac_mes3 != null)  commentaire = commentaire +fac_mes3+"\rn";
				
					
					if(typeDoc.equals(TaFacture.TYPE_DOC)){
						 document=new TaFacture(true);
						((TaFacture)document).setDateEchDocument(fac_ech);
						((TaFacture)document).setRegleDocument(fac_mreg);
						((TaFacture)document).setRemHtDocument(fac_rem);
						((TaFacture)document).setRemTtcDocument(fac_escp);
						((TaFacture)document).setNbEDocument(LibConversion.stringToInteger(fac_edite));	
						taInfosDocument=new TaInfosFacture();
						((TaInfosFacture)taInfosDocument).setTaDocument(((TaFacture)document));
						((TaFacture)document).setTaInfosDocument((TaInfosFacture)taInfosDocument);
					}
					if(typeDoc.equals(TaAvoir.TYPE_DOC)){
						 document=new TaAvoir(true);
						((TaAvoir)document).setDateEchDocument(fac_ech);
						((TaAvoir)document).setRegleDocument(fac_mreg);
						((TaAvoir)document).setRemHtDocument(fac_rem);
						((TaAvoir)document).setRemTtcDocument(fac_escp);
						((TaAvoir)document).setNbEDocument(LibConversion.stringToInteger(fac_edite));
						taInfosDocument=new TaInfosAvoir();
						((TaInfosAvoir)taInfosDocument).setTaDocument(((TaAvoir)document));
						((TaAvoir)document).setTaInfosDocument((TaInfosAvoir)taInfosDocument);
					}
					if(typeDoc.equals(TaDevis.TYPE_DOC)){
						 document=new TaDevis(true);
						((TaDevis)document).setDateEchDocument(fac_ech);
						((TaDevis)document).setRegleDocument(fac_mreg);
						((TaDevis)document).setRemHtDocument(fac_rem);
						((TaDevis)document).setRemTtcDocument(fac_escp);
						((TaDevis)document).setNbEDocument(LibConversion.stringToInteger(fac_edite));
						taInfosDocument=new TaInfosDevis();
						((TaInfosDevis)taInfosDocument).setTaDocument(((TaDevis)document));
						((TaDevis)document).setTaInfosDocument((TaInfosDevis)taInfosDocument);
					}
					if(typeDoc.equals(TaBonliv.TYPE_DOC)){
						 document=new TaBonliv(true);
//						((TaBonliv)document).setDateEchDocument(fac_ech);
//						((TaBonliv)document).setRegleDocument(fac_mreg);
						((TaBonliv)document).setRemHtDocument(fac_rem);
						((TaBonliv)document).setRemTtcDocument(fac_escp);
						((TaBonliv)document).setNbEDocument(LibConversion.stringToInteger(fac_edite));		
						taInfosDocument=new TaInfosBonliv();
						((TaInfosBonliv)taInfosDocument).setTaDocument(((TaBonliv)document));
						((TaBonliv)document).setTaInfosDocument((TaInfosBonliv)taInfosDocument);
					}

					taInfosDocument.setAdresse1(fac_ad1);
					taInfosDocument.setAdresse2(fac_ad2);
					taInfosDocument.setCodepostal(fac_cp);
					taInfosDocument.setVille(fac_vil);
					taInfosDocument.setPays(fac_pay);
					taInfosDocument.setAdresse1Liv(fac_ad1);
					taInfosDocument.setAdresse2Liv(fac_ad2);
					taInfosDocument.setCodepostalLiv(fac_cp);
					taInfosDocument.setVilleLiv(fac_vil);
					taInfosDocument.setPaysLiv(fac_pay);
					taInfosDocument.setCodeCompta(fac_cpt);
					taInfosDocument.setCompte(fac_col);
					taInfosDocument.setNomTiers(fac_nom);
					taInfosDocument.setCodeTEntite(civilite);
					taInfosDocument.setTvaIComCompl(tvaIntra);
					if(taCPaiement!=null){
						taInfosDocument.setCodeCPaiement(taCPaiement.getCodeCPaiement());
						taInfosDocument.setLibCPaiement(taCPaiement.getLibCPaiement());
						taInfosDocument.setReportCPaiement(taCPaiement.getReportCPaiement());
						taInfosDocument.setFinMoisCPaiement(taCPaiement.getFinMoisCPaiement());
					}
					taInfosDocument.setCodeTTvaDoc(LocalisationTva);

					 
				document.setCodeDocument(fac_num);
				document.setDateDocument(fac_dat);
				
				document.setDateLivDocument(fac_dat);
				document.setLibelleDocument(libelleDoc+fac_num);
				document.setTaTiers(tiers);
				document.setTxRemHtDocument(fac_remp);
				document.setTxRemTtcDocument(fac_esc);
				document.setTtc(LibConversion.booleanToInt(LibConversion.StringToBoolean(fac_pri)));
				document.setExport(fac_cpta);
				document.setCommentaire(commentaire);
				if(tiers.getTaTCivilite()!=null)civilite=tiers.getTaTCivilite().getCodeTCivilite();
				if(tiers.getTaCompl()!=null)tvaintra=tiers.getTaCompl().getTvaIComCompl();
				

				
				String sqlligne="select f.fac_typ,f.fac_num,l.fli_num,l.fli_lig,l.fli_cod,l.fli_des,l.fli_qt1,l.fli_qt2 "+
						",l.fli_pht,l.fli_ptc,l.fli_rem,l.fli_mht,l.fli_mtc,l.fli_mtva,l.fli_txtva,l.fli_cpt "+
						",l.fli_tmp,l.fli_exig,l.fli_lot,l.fli_nbl,l.fli_un1,l.fli_un2 from "+nomTableLigne+" l INNER join "+nomTableDoc+" f on (f.fac_num=l.fli_num)"+
						" where l.fli_num ='"+fac_num+"' and f.fac_typ ='"+type+"'  order by 1	";
				
		

				rang=0;
				stmtLigne=connection.createStatement();
				resultSetLigne=stmtLigne.executeQuery(sqlligne);
				while (resultSetLigne.next()) {
					taArticle=null;
					prix=BigDecimal.ZERO;
					remiseT=BigDecimal.ZERO;
					taTva=null;
					codeTva="";
					
//					type_docs=resultSetLigne.getString("fac_typ");
					fli_num=resultSetLigne.getString("fli_num");
					fli_lig=resultSetLigne.getInt("fli_lig");
					fli_cod=resultSetLigne.getString("fli_cod");
					fli_des=resultSetLigne.getString("fli_des");
					fli_qt1=resultSetLigne.getBigDecimal("fli_qt1");
					fli_qt2=resultSetLigne.getBigDecimal("fli_qt2");
					fli_pht=resultSetLigne.getBigDecimal("fli_pht");
					fli_ptc=resultSetLigne.getBigDecimal("fli_ptc");
					fli_rem=resultSetLigne.getBigDecimal("fli_rem");
					fli_mht=resultSetLigne.getBigDecimal("fli_mht");
					fli_mtc=resultSetLigne.getBigDecimal("fli_mtc");
					fli_mtva=resultSetLigne.getBigDecimal("fli_mtva");
					fli_txtva=resultSetLigne.getBigDecimal("fli_txtva");
					fli_cpt=resultSetLigne.getString("fli_cpt");
					fli_tmp=resultSetLigne.getString("fli_tmp");
					fli_exig=resultSetLigne.getString("fli_exig");
					fli_lot=resultSetLigne.getString("fli_lot");
					fli_nbl=resultSetLigne.getString("fli_nbl");
					fli_un1=resultSetLigne.getString("fli_un1");
					fli_un2=resultSetLigne.getString("fli_un2");
					
				       if (ttc==false)  prix=fli_pht;
				       else prix=fli_ptc;
				       if (fli_cpt == null) taTLigne = taTLigneC;
				       else taTLigne = taTLigneH;
				       
				       if((fli_qt1==null || fli_qt1.compareTo(BigDecimal.ZERO)==0)&& prix.compareTo(BigDecimal.ZERO)!=0)
				    	   fli_qt1=BigDecimal.valueOf(1);
				       
				       if (fli_rem != null && fli_rem.compareTo(BigDecimal.ZERO)!=0) remiseT = (fli_rem);
				       
				       BigDecimal mtTemp=null;
			    	   mtTemp=(prix.multiply(fli_qt1));
			    	   if(remiseT.compareTo(BigDecimal.ZERO)!=0)mtTemp=mtTemp.subtract(mtTemp.multiply(remiseT.divide(BigDecimal.valueOf(100))));				       
				       if(ttc){
				    	   if(mtTemp.compareTo(fli_mtc)!=0 && mtTemp.subtract(fli_mtc).abs().compareTo(BigDecimal.valueOf(0,05))>0){
				    		   if(remiseT.compareTo(BigDecimal.ZERO)!=0)prix=fli_mtc.subtract(mtTemp.multiply(remiseT.divide(BigDecimal.valueOf(100))));
				    		   else prix=fli_mtc;
				    		   fli_qt1=BigDecimal.valueOf(1);
				    	   }
				    	   
				       }else{
				    	   if(mtTemp.compareTo(fli_mht)!=0 && mtTemp.subtract(fli_mht).abs().compareTo(BigDecimal.valueOf(0.05))>0){
				    		   if(remiseT.compareTo(BigDecimal.ZERO)!=0) prix=fli_mht.subtract(mtTemp.multiply(remiseT.divide(BigDecimal.valueOf(100))));
				    		   else prix=fli_mht;
				    		   fli_qt1=BigDecimal.valueOf(1);
				    	   }
				       }
				       
				       if (LibConversion.StringToBoolean(fac_pri)==false) fli_rem= (prix.multiply(fli_qt1)).subtract(fli_mht);
				       else  fli_rem= (prix.multiply(fli_qt1)).subtract(fli_mtc);
				       
				       if(fli_txtva.compareTo(BigDecimal.ZERO)!=0) taTva=daoTva.findByTauxOrCreate(LibConversion.bigDecimalToString(fli_txtva),false,daoTva.getEntityManager());
				       if(taTva!=null)codeTva=taTva.getCodeTva().toUpperCase();
				       if(daoArticle.exist(fli_cod))taArticle=daoArticle.findByCode(fli_cod);
				       if(taArticle!=null && fli_des == null)lignevide=lignevide+1;
				       else lignevide=0;
				       
				       
				       if (lignevide<3) {
							if(typeDoc.equals(TaFacture.TYPE_DOC)){
						    	   taLDocument=new TaLFacture(true);
						    	   ((TaLFacture)taLDocument).setTaDocument((TaFacture)document);
							}
							if(typeDoc.equals(TaAvoir.TYPE_DOC)){
						    	   taLDocument=new TaLAvoir(true);
						    	   ((TaLAvoir)taLDocument).setTaDocument((TaAvoir)document);
							}
							if(typeDoc.equals(TaDevis.TYPE_DOC)){
						    	   taLDocument=new TaLDevis(true);
						    	   ((TaLDevis)taLDocument).setTaDocument((TaDevis)document);
							}
							if(typeDoc.equals(TaBonliv.TYPE_DOC)){
						    	   taLDocument=new TaLBonliv(true);
						    	   ((TaLBonliv)taLDocument).setTaDocument((TaBonliv)document);
							}
			    	   

				    	   taLDocument.setTaTLigne(taTLigne);
				    	   taLDocument.setTaArticle(taArticle);
				    	   taLDocument.setNumLigneLDocument(rang);
				    	   taLDocument.setLibLDocument(fli_des);
				    	   taLDocument.setQteLDocument(fli_qt1);
				    	   taLDocument.setQte2LDocument(fli_qt2);
				    	   taLDocument.setU1LDocument(fli_un1);
				    	   taLDocument.setU2LDocument(fli_un2);
				    	   taLDocument.setPrixULDocument(prix);
				    	   taLDocument.setTauxTvaLDocument(fli_txtva);
				    	   taLDocument.setCompteLDocument(fli_cpt);
				    	   taLDocument.setCodeTvaLDocument(codeTva);
				    	   taLDocument.setCodeTTvaLDocument(fli_exig);
				    	   taLDocument.setMtHtLDocument(fli_mht);
				    	   taLDocument.setMtTtcLDocument(fli_mtc);
				    	   taLDocument.setRemTxLDocument(remiseT);
				    	   taLDocument.setRemHtLDocument(fli_rem);
				    	   
							if(typeDoc.equals(TaFacture.TYPE_DOC)){
						    	   ((TaFacture)document).getLignes().add((TaLFacture)taLDocument);
							}
							if(typeDoc.equals(TaAvoir.TYPE_DOC)){
						    	   ((TaAvoir)document).getLignes().add((TaLAvoir)taLDocument);
							}
							if(typeDoc.equals(TaDevis.TYPE_DOC)){
						    	   ((TaDevis)document).getLignes().add((TaLDevis)taLDocument);
							}
							if(typeDoc.equals(TaBonliv.TYPE_DOC)){
						    	   ((TaBonliv)document).getLignes().add((TaLBonliv)taLDocument);
							}
				    	   
				    	  
				       }
				       rang=rang+1;


				       String sqlLigneE2="INSERT INTO LIGNES_DOCS_E2FAC (TYPE_DOCS, FLI_NUM, FLI_LIG, FLI_COD, FLI_DES, FLI_QT1, FLI_QT2, FLI_PHT, " +
						"FLI_PTC, FLI_REM, FLI_MHT, FLI_MTC, FLI_MTVA, FLI_TXTVA, FLI_CPT, FLI_TMP, FLI_EXIG, FLI_LOT, FLI_NBL, " +
						"FLI_UN1, FLI_UN2) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				       
						PreparedStatement prep= bdlgr.getCnx().prepareStatement(sqlLigneE2);
						prep.setString(1,fac_typ);
						prep.setString(2,fli_num);
						prep.setInt(3,fli_lig);
						prep.setString(4,fli_cod);
						prep.setString(5,fli_des);
						prep.setBigDecimal(6,fli_qt1);
						prep.setBigDecimal(7,fli_qt2);
						prep.setBigDecimal(8,fli_pht);
						prep.setBigDecimal(9,fli_ptc);
						prep.setBigDecimal(10,fli_rem);
						prep.setBigDecimal(11,fli_mht);
						prep.setBigDecimal(12,fli_mtc);
						prep.setBigDecimal(13,fli_mtva);
						prep.setBigDecimal(14,fli_txtva);
						prep.setString(15,fli_cpt);
						prep.setString(16,fli_tmp);
						prep.setString(17,fli_exig);
						prep.setString(18,fli_lot);
						prep.setString(19,fli_nbl);
						prep.setString(20,fli_un1);
						prep.setString(21,fli_un2);
						prep.executeUpdate();    
				}
				
				if(typeDoc.equals(TaFacture.TYPE_DOC)){			    	   
//			    	   ((TaFacture)document).calculMontantLigneDocument();
//			    	   ((TaFacture)document).dispatcherTvaAvantRemise();
//			    	   ((TaFacture)document).dispatcherTva();
//			    	   ((TaFacture)document).calculTotaux();
			    	   ((TaFacture)document).calculeTvaEtTotaux();
				}
				
				
				if(typeDoc.equals(TaAvoir.TYPE_DOC)){
//			    	   ((TaAvoir)document).calculMontantLigneDocument();
//			    	   ((TaAvoir)document).dispatcherTva();
//			    	   ((TaAvoir)document).calculTotaux();
			    	   ((TaAvoir)document).calculeTvaEtTotaux();
				}
				if(typeDoc.equals(TaDevis.TYPE_DOC)){
//			    	   ((TaDevis)document).calculMontantLigneDocument();
//			    	   ((TaDevis)document).dispatcherTva();
//			    	   ((TaDevis)document).calculTotaux();
			    	   ((TaDevis)document).calculeTvaEtTotaux();
				}
				if(typeDoc.equals(TaBonliv.TYPE_DOC)){
//			    	   ((TaBonliv)document).calculMontantLigneDocument();
//			    	   ((TaBonliv)document).dispatcherTva();
//			    	   ((TaBonliv)document).calculTotaux();
			    	   ((TaBonliv)document).calculeTvaEtTotaux();
				}

				
				if(fac_mreg!=null && fac_mreg.compareTo(BigDecimal.ZERO)!=0 && type.equals(TaFacture.TYPE_DOC)){
					TaRReglement taReglement = new TaRReglement();
					reglement = new TaReglement();
					reglement=daoReglement.enregistrerMerge(reglement);
					taReglement.setTaReglement(reglement);
					reglement.setTaTiers(tiers);
					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(daoDocument.getEntityManager()).genereCode());
					if(fac_mreg.compareTo(document.getNetTtcCalc())>0)
						taReglement.setAffectation(document.getNetTtcCalc());
					else
					taReglement.setAffectation(fac_mreg);
					reglement.setNetTtcCalc(fac_mreg);
					reglement.setDateDocument(document.getDateDocument());
					taReglement.getTaReglement().setDateDocument(document.getDateDocument());
					taReglement.getTaReglement().setEtat(IHMEtat.integre);
					taReglement.getTaReglement().setDateLivDocument(document.getDateDocument());
					taReglement.getTaReglement().setTaTPaiement(new TaTPaiementDAO(daoDocument.getEntityManager()).findByCode("C"));
					reglement.setTaCompteBanque(new TaCompteBanqueDAO(daoDocument.getEntityManager()).findByTiersEntreprise(taReglement.getTaReglement().getTaTPaiement()));					
					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
					taReglement.setTaFacture((TaFacture)document);
					((TaFacture)document).addRReglement(taReglement);
					((TaFacture)document).setRegleDocument(document.getNetTtcCalc());
					((TaFacture)document).setRegleCompletDocument(document.getNetTtcCalc());
					((TaFacture)document).setNetAPayer(BigDecimal.valueOf(0));	
				}
								
				if(typeDoc.equals(TaFacture.TYPE_DOC)){			    	   
			    	  document=(TaFacture)daoDocument.enregistrerMerge(((TaFacture)document));
				}
				
				
				if(typeDoc.equals(TaAvoir.TYPE_DOC)){
			    	  document=(TaAvoir)daoDocument.enregistrerMerge(((TaAvoir)document));
				}
				if(typeDoc.equals(TaDevis.TYPE_DOC)){
			    	  document=(TaDevis)daoDocument.enregistrerMerge(((TaDevis)document));
				}
				if(typeDoc.equals(TaBonliv.TYPE_DOC)){
			    	  document=(TaBonliv)daoDocument.enregistrerMerge(((TaBonliv)document));
				}

			
				if(!daoDocument.getEntityManager().getTransaction().isActive())
					daoDocument.getEntityManager().getTransaction().begin();

				String sqlDocumentsE2="INSERT INTO DOCUMENTS_E2FAC (FAC_NUM, FAC_TYP, FAC_DAT, FAC_ECH, FAC_COD, FAC_NOM, FAC_CPT, FAC_COL, FAC_AD1, FAC_AD2, FAC_CP, FAC_VIL, FAC_PAY, " +
				" FAC_REF, FAC_PMT, FAC_PRI, FAC_HT, FAC_TTC, FAC_REMP, FAC_REM, FAC_REMT, FAC_ESCP, FAC_ESC, FAC_MES1, FAC_MES2, FAC_MES3, FAC_CPTA, FAC_EDITE, " +
				" FAC_FAC, FAC_ACT, FAC_CIAL, FAC_MREG, FAC_LOC) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement prep= bdlgr.getCnx().prepareStatement(sqlDocumentsE2);
				prep.setString(1,fac_num);
				prep.setString(2,fac_typ);
				prep.setDate(3,fac_dat);
				prep.setDate(4,fac_ech);
				prep.setString(5,fac_cod);
				prep.setString(6,fac_nom);
				prep.setString(7,fac_cpt);
				prep.setString(8,fac_col);
				prep.setString(9,fac_ad1);
				prep.setString(10,fac_ad2);
				prep.setString(11,fac_cp);
				prep.setString(12,fac_vil);
				prep.setString(13,fac_pay);
				prep.setString(14,fac_ref);
				prep.setString(15,fac_pmt);
				prep.setString(16,fac_pri);
				prep.setBigDecimal(17,fac_ht);
				prep.setBigDecimal(18,fac_ttc);
				prep.setBigDecimal(19,fac_remp);
				prep.setBigDecimal(20,fac_rem);
				prep.setBigDecimal(21,fac_remt);
				prep.setBigDecimal(22,fac_escp);
				prep.setBigDecimal(23,fac_esc);
				prep.setString(24,fac_mes1);
				prep.setString(25,fac_mes2);
				prep.setString(26,fac_mes3);
				prep.setInt(27,fac_cpta);
				prep.setString(28,fac_edite);
				prep.setString(29,fac_fac);
				prep.setBigDecimal(30,fac_act);
				prep.setString(31,fac_cial);
				prep.setBigDecimal(32,fac_mreg);
				prep.setInt(33,fac_loc);
				
				prep.executeUpdate();
				if(modulo==150){
					daoDocument.getEntityManager().getTransaction().commit();
					modulo=-1;
				}
				logger.debug("facture enregistree "+document.getCodeDocument());
				nb++;
				modulo++;
			}
			
			if(daoDocument.getEntityManager().getTransaction().isActive())daoDocument.getEntityManager().getTransaction().commit();
//			sql="select count(*)as nb from "+nomTableDoc+ " d where (d.fac_typ)='"+type+"'";
//			resultSet=stmt.executeQuery(sql);
//			Integer nbE2Fac=0;
//			while (resultSet.next()) {
//				nbE2Fac=resultSet.getInt("nb");
//				if( nb!=nbE2Fac)
//					MessageDialog.openError(window.getShell(), "Importation documents", "il y a "+nb+" documents /"+nbE2Fac+" documents existants dans E2Fac pour le type "+type);
//				throw new Exception();
//			}
//			resultSet.close();
			stmt.close();
		}  catch (SQLException e) {
			daoDocument.getEntityManager().getTransaction().rollback();
			logger.error("facture abandonnée "+document.getCodeDocument(),e);
//			if(resultSetLigne!=null)resultSetLigne.close();
//			if(resultSet!=null)resultSet.close();
			if(stmt!=null)stmt.close();
		}
	}

	
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
}
