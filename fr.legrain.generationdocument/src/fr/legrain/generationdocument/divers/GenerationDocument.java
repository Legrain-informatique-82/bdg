package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaInfosPrelevement;
import fr.legrain.documents.dao.TaLDevis;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTLigne;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.generationDocumentLGR.dao.ArticlesMaintenance;
import fr.legrain.generationDocumentLGR.dao.TaWlgr;
import fr.legrain.generationDocumentLGR.dao.TaWlgrDAO;
import fr.legrain.generationDocumentLGR.dao.TaWlgrNMoins1;
import fr.legrain.generationDocumentLGR.dao.TaWlgrNMoins1DAO;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class GenerationDocument {
	static Logger logger = Logger.getLogger(GenerationDocument.class.getName());
	private String module="FACTURE";
	private String libelleDoc="";
	private Date dateDoc;
	private String commentaire="";
	private String code="";
	private String codeTiers="";
	private String typeAdresseFacturation="";
	private String typeAdresseLivraison="";
	private String typePaiement="";
	private Boolean relationDocument=true;
	

	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();

	private LgrDozerMapper<IHMInfosCPaiement,TaInfosFacture> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosFacture> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosFacture> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosFacture>();
	
	private LgrDozerMapper<TaFacture,TaInfosFacture> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaFacture, TaInfosFacture>();
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosPrelevement> mapperUIToModelCPaiementVersInfosPrel = new LgrDozerMapper<IHMInfosCPaiement, TaInfosPrelevement>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosPrelevement> mapperUIToModelAdresseFactVersInfosPrel = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosPrelevement>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosPrelevement> mapperUIToModelAdresseLivVersInfosPrel = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosPrelevement>();
	private LgrDozerMapper<TaPrelevement,TaInfosPrelevement> mapperUIToModePrelVersInfosDoc = new LgrDozerMapper<TaPrelevement,TaInfosPrelevement>();
	
	private EntityManager em = null;
	public GenerationDocument(){
		
	}
	
//	public TaFacture genere(){
//		TaFacture documentInit = null;
//		TaFacture documentFinal = null;
//		try {
//			TaFactureDAO daoDocument = new TaFactureDAO();
//			TaTiersDAO daoTiers = new TaTiersDAO();
//			TaTiers tiers = null;
//			EntityTransaction transaction = daoDocument.getEntityManager().getTransaction();
//			daoDocument.begin(transaction);
//			
//			documentInit=daoDocument.findByCode(this.code);
//			if(documentInit!=null){
//				documentInit.setLegrain(true);
//				documentFinal =documentInit.clone();
//				documentFinal.setCodeDocument(daoDocument.genereCode());
//				documentFinal.setLibelleDocument(this.libelleDoc);
//				documentFinal.setCommentaire(this.commentaire);
//				documentFinal.setDateEchDocument(this.dateDoc);
//				documentFinal.setDateDocument(this.dateDoc);
//				documentFinal.setDateLivDocument(this.dateDoc);
//
//				if(!this.codeTiers.equals(documentFinal.getTaTiers().getCodeTiers())){
//					tiers=daoTiers.findByCode(this.codeTiers);
//					documentFinal.setTaTiers(tiers);
//					changementDeTiers(documentFinal);
//				}
//				
//				daoDocument.inserer(documentFinal);				
//				documentFinal.calculeTvaEtTotaux();	
//				
//				daoDocument.enregistrerMerge(documentFinal);				
//			}
//			daoDocument.commit(transaction);
//			return documentFinal;
//		} catch (Exception e) {
//			logger.error("",e);
//			return null;
//		}
//	}
	
	public TaFacture genereLGR(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		
		//TaFacture documentInit = null;
		TaFacture documentFinal = null;
		TaWlgrDAO daoWlgr = new TaWlgrDAO(getEm());
		try {
			TaWlgr taWlgr=daoWlgr.findByCode(this.codeTiers);

			List<ArticlesMaintenance> listArticleMaintenance =daoWlgr.selectArticleMaintenanceClient(this.codeTiers);
			if(listArticleMaintenance.size()>0){
			TaFactureDAO daoDocument = new TaFactureDAO(getEm());
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			TaTLigneDAO daoTLigne = new TaTLigneDAO(getEm());
			TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(getEm());
			TaReglementDAO daoReglement = new TaReglementDAO(getEm());
			TaLFacture temp = null;
			TaTiers tiers = null;
			EntityTransaction transaction = daoDocument.getEntityManager().getTransaction();
			daoDocument.begin(transaction);
			
			//documentInit=daoDocument.findByCode(this.code);
			//if(documentInit!=null){
//				documentInit.setLegrain(true);
				documentFinal =new TaFacture(true);
				documentFinal.setCodeDocument(daoDocument.genereCode());
				documentFinal.setLibelleDocument(this.libelleDoc);
				documentFinal.setCommentaire(this.commentaire);
				documentFinal.setDateEchDocument(this.dateDoc);
				documentFinal.setDateDocument(this.dateDoc);
				documentFinal.setDateLivDocument(this.dateDoc);

				tiers=daoTiers.findByCode(this.codeTiers);
				
				documentFinal.setTaTiers(tiers);
				
				//changementDeTiers(documentFinal);
				int nbLigne=0;
				//calculer les lignes en fonction de la table TA_WLGR
						ArticlesMaintenance articleMaintenance=listArticleMaintenance.get(0);
					TaArticleDAO daoArticle = new TaArticleDAO(getEm());

					/*Article Epicéa*/
					if(articleMaintenance.getTotalHtE2().signum()!=0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleE2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducE2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeE2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducE2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtE2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setRemHtLDocument(mtHtRemise.subtract(articleMaintenance.getTotalHtE2()));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtE2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
						if(articleMaintenance.getParticipationCogere().signum()<0){
//		                    prix=:participation/:qte;
//		                    taux_rem=0;
//		                    select a.id_article,a.libellec_article,a.numcpt_article,a.code_tva,a.taux_tva,a.code_unite from v_article a where
//		                    code_article like('COGE09')
							temp = new TaLFacture(true);
							temp.setTaDocument(documentFinal);
							nbLigne++;
							article =daoArticle.findByCode("COGE13");
							temp.setTaArticle(article);							
							temp.setQteLDocument(BigDecimal.valueOf(1));
							temp.setPrixULDocument(articleMaintenance.getParticipationCogere().
									divide(temp.getQteLDocument(),MathContext.DECIMAL32));
							temp.setMtHtLDocument(articleMaintenance.getParticipationCogere());
							temp.setTaTLigne(daoTLigne.findByCode("H"));
							if(article.getTaTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
							if(article.getTaTTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
							temp.setCompteLDocument(article.getNumcptArticle());
							temp.setLibLDocument(article.getLibellecArticle());
							temp.setNumLigneLDocument(nbLigne);
							temp.setTaDocument(documentFinal);
							if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
								temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
							documentFinal.addLigne(temp);							
						}
					}					
					/*Article E2-fac*/
					if(articleMaintenance.getTotalHtF2().signum()!=0 && articleMaintenance.getArticleF2()!=null){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleF2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducF2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeF2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducF2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtF2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtF2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					 /*Article Sara */
					if(articleMaintenance.getTotalHtS2().signum()!=0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleS2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducS2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeS2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducS2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtS2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtS2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					 /*reduc */
					if(articleMaintenance.getBonusHt().signum()<0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article =daoArticle.findByCode("RPB13");
						temp.setTaArticle(article);							
						temp.setQteLDocument(BigDecimal.valueOf(1));
						temp.setPrixULDocument(articleMaintenance.getBonusHt());
						temp.setMtHtLDocument(articleMaintenance.getBonusHt());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);							
					}
					TaInfosFacture infos = new TaInfosFacture();;
					infos.setTaDocument(documentFinal);
					documentFinal.setTaInfosDocument(infos);
					
					changementDeTiers(documentFinal);
					
				daoDocument.inserer(documentFinal);
				
				changementDeTiers(documentFinal);
				
				documentFinal.calculeTvaEtTotaux();
				if(!this.typePaiement.equals("")){
					TaRReglement taReglement = new TaRReglement();
					TaReglement reglement = new TaReglement();
					reglement=daoReglement.enregistrerMerge(reglement);
					taReglement.setTaReglement(reglement);
					reglement.setTaTiers(tiers);
					TaTPaiement taTPaiement = daoTPaiement.findByCode(this.typePaiement);
					reglement.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise(taTPaiement));
					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(getEm()).genereCode());
					if(taWlgr.getTtc_du().compareTo(documentFinal.getNetTtcCalc())>0)
						taReglement.setAffectation(documentFinal.getNetTtcCalc());
					else
					taReglement.setAffectation(taWlgr.getTtc_du());
					reglement.setNetTtcCalc(taWlgr.getTtc_du());
					reglement.setDateDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setDateDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setEtat(IHMEtat.integre);
					taReglement.getTaReglement().setDateLivDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setTaTPaiement(taTPaiement);
					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
					taReglement.setTaFacture(documentFinal);
					documentFinal.addRReglement(taReglement);
//					documentFinal.setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
				}
				
				documentFinal.setRegleDocument(documentFinal.getNetTtcCalc());
				documentFinal.setRegleCompletDocument(documentFinal.getNetTtcCalc());
				documentFinal.setNetAPayer(BigDecimal.valueOf(0));
				documentFinal=daoDocument.enregistrerMerge(documentFinal);
				if(documentFinal.getTaInfosDocument()==null)
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "", "le document du tiers "+this.getCodeTiers()+" a une infos facture nulle, appelez le service maintenace");

				daoDocument.commit(transaction);
			
			daoWlgr.begin(transaction);			
			taWlgr.setTraite(Short.valueOf("1"));
			taWlgr.setCodeDoc(documentFinal.getCodeDocument());
			daoWlgr.enregistrerMerge(taWlgr);
			daoWlgr.commit(transaction);
			//update ta_wlgr set traite = 1,code_doc=:code_document where code_client like(:code_tiers_input);
			}
			return documentFinal;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	
	public TaPrelevement generePrelevementLGR(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		
		//TaFacture documentInit = null;
		TaPrelevement documentFinal = null;
		TaWlgrDAO daoWlgr = new TaWlgrDAO(getEm());
		try {
			TaWlgr taWlgr=daoWlgr.findByCode(this.codeTiers);

			List<ArticlesMaintenance> listArticleMaintenance =daoWlgr.selectArticleMaintenanceClient(this.codeTiers);
			if(listArticleMaintenance.size()>0){
			TaPrelevementDAO daoDocument = new TaPrelevementDAO(getEm());
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			TaTLigneDAO daoTLigne = new TaTLigneDAO(getEm());
			TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(getEm());
			TaReglementDAO daoReglement = new TaReglementDAO(getEm());
			TaLPrelevement temp = null;
			TaTiers tiers = null;
			EntityTransaction transaction = daoDocument.getEntityManager().getTransaction();
			daoDocument.begin(transaction);
			
			//documentInit=daoDocument.findByCode(this.code);
			//if(documentInit!=null){
//				documentInit.setLegrain(true);
				documentFinal =new TaPrelevement(true);
				documentFinal.setCodeDocument(daoDocument.genereCode());
				documentFinal.setLibelleDocument(this.libelleDoc);
				documentFinal.setCommentaire(this.commentaire);
				documentFinal.setDateEchDocument(this.dateDoc);
				documentFinal.setDateDocument(this.dateDoc);
				documentFinal.setDateLivDocument(this.dateDoc);
				tiers=daoTiers.findByCode(this.codeTiers);
				
				documentFinal.setTaTiers(tiers);
				
				//changementDeTiers(documentFinal);
				int nbLigne=0;
				//calculer les lignes en fonction de la table TA_WLGR
						ArticlesMaintenance articleMaintenance=listArticleMaintenance.get(0);
					TaArticleDAO daoArticle = new TaArticleDAO(getEm());

					/*Article Epicéa*/
					if(articleMaintenance.getTotalHtE2().signum()!=0){
						temp = new TaLPrelevement(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleE2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducE2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeE2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducE2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtE2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setRemHtLDocument(mtHtRemise.subtract(articleMaintenance.getTotalHtE2()));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtE2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
						if(articleMaintenance.getParticipationCogere().signum()<0){
//		                    prix=:participation/:qte;
//		                    taux_rem=0;
//		                    select a.id_article,a.libellec_article,a.numcpt_article,a.code_tva,a.taux_tva,a.code_unite from v_article a where
//		                    code_article like('COGE09')
							temp = new TaLPrelevement(true);
							temp.setTaDocument(documentFinal);
							nbLigne++;
							article =daoArticle.findByCode("COGE13");
							temp.setTaArticle(article);							
							temp.setQteLDocument(BigDecimal.valueOf(1));
							temp.setPrixULDocument(articleMaintenance.getParticipationCogere().
									divide(temp.getQteLDocument(),MathContext.DECIMAL32));
							temp.setMtHtLDocument(articleMaintenance.getParticipationCogere());
							temp.setTaTLigne(daoTLigne.findByCode("H"));
							if(article.getTaTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
							if(article.getTaTTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
							temp.setCompteLDocument(article.getNumcptArticle());
							temp.setLibLDocument(article.getLibellecArticle());
							temp.setNumLigneLDocument(nbLigne);
							temp.setTaDocument(documentFinal);
							if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
								temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
							documentFinal.addLigne(temp);							
						}
					}					
					/*Article E2-fac*/
					if(articleMaintenance.getTotalHtF2().signum()!=0 && articleMaintenance.getArticleF2()!=null){
						temp = new TaLPrelevement(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleF2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducF2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeF2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducF2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtF2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtF2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					 /*Article Sara */
					if(articleMaintenance.getTotalHtS2().signum()!=0){
						temp = new TaLPrelevement(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleS2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducS2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeS2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducS2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtS2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtS2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					 /*reduc */
					if(articleMaintenance.getBonusHt().signum()<0){
						temp = new TaLPrelevement(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article =daoArticle.findByCode("RPB13");
						temp.setTaArticle(article);							
						temp.setQteLDocument(BigDecimal.valueOf(1));
						temp.setPrixULDocument(articleMaintenance.getBonusHt());
						temp.setMtHtLDocument(articleMaintenance.getBonusHt());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);							
					}
					TaInfosPrelevement infos = new TaInfosPrelevement();;
					infos.setTaDocument(documentFinal);
					documentFinal.setTaInfosDocument(infos);
					
					changementDeTiersPrelevement(documentFinal);
					
				daoDocument.inserer(documentFinal);				
				documentFinal.calculeTvaEtTotaux();
//				if(!this.typePaiement.equals("")){
//					TaRReglement taReglement = new TaRReglement();
//					TaReglement reglement = new TaReglement();
//					reglement=daoReglement.enregistrerMerge(reglement);
//					taReglement.setTaReglement(reglement);
//					reglement.setTaTiers(tiers);
//					reglement.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise());
//					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(getEm()).genereCode());
//					if(taWlgr.getTtc_du().compareTo(documentFinal.getNetTtcCalc())>0)
//						taReglement.setAffectation(documentFinal.getNetTtcCalc());
//					else
//					taReglement.setAffectation(taWlgr.getTtc_du());
//					reglement.setNetTtcCalc(taWlgr.getTtc_du());
//					reglement.setDateDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setDateDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setEtat(IHMEtat.integre);
//					taReglement.getTaReglement().setDateLivDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
//					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
//					taReglement.setTaFacture(documentFinal);
//					documentFinal.addRReglement(taReglement);
////					documentFinal.setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
//				}
				
				documentFinal.setRegleDocument(BigDecimal.valueOf(0));
				//documentFinal.setRegleCompletDocument(documentFinal.getNetTtcCalc());
				//documentFinal.setNetAPayer(BigDecimal.valueOf(0));
				daoDocument.enregistrerMerge(documentFinal);
					
			daoDocument.commit(transaction);
			
			daoWlgr.begin(transaction);			
			taWlgr.setTraite(Short.valueOf("1"));
			//taWlgr.setPrel(Short.valueOf("1"));
			taWlgr.setCodeDoc(documentFinal.getCodeDocument());
			daoWlgr.enregistrerMerge(taWlgr);
			daoWlgr.commit(transaction);
			//update ta_wlgr set traite = 1,code_doc=:code_document where code_client like(:code_tiers_input);
			}
			return documentFinal;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
//	public IDocumentTiers genereLGR(EntityManager em){
//		if(em!=null) {
//			setEm(em);
//		}
//		
//		//TaFacture documentInit = null;
//		TaFacture documentFinal = null;
//		TaWlgrDAO daoWlgr = new TaWlgrDAO(getEm());
//		try {
//			TaWlgr taWlgr=daoWlgr.findByCode(this.codeTiers);
//
//			List<ArticlesMaintenance> listArticleMaintenance =daoWlgr.selectArticleMaintenanceClient(this.codeTiers);
//			if(listArticleMaintenance.size()>0){
//			TaFactureDAO daoDocument = new TaFactureDAO(getEm());
//			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
//			TaTLigneDAO daoTLigne = new TaTLigneDAO(getEm());
//			TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(getEm());
//			TaReglementDAO daoReglement = new TaReglementDAO(getEm());
//			TaLFacture temp = null;
//			TaTiers tiers = null;
//			EntityTransaction transaction = daoDocument.getEntityManager().getTransaction();
//			daoDocument.begin(transaction);
//			
//			//documentInit=daoDocument.findByCode(this.code);
//			//if(documentInit!=null){
////				documentInit.setLegrain(true);
//				documentFinal =new TaFacture(true);
//				documentFinal.setCodeDocument(daoDocument.genereCode());
//				documentFinal.setLibelleDocument(this.libelleDoc);
//				documentFinal.setCommentaire(this.commentaire);
//				documentFinal.setDateEchDocument(this.dateDoc);
//				documentFinal.setDateDocument(this.dateDoc);
//				documentFinal.setDateLivDocument(this.dateDoc);
//				tiers=daoTiers.findByCode(this.codeTiers);
//				
//				documentFinal.setTaTiers(tiers);
//				
//				//changementDeTiers(documentFinal);
//				int nbLigne=0;
//				//calculer les lignes en fonction de la table TA_WLGR
//						ArticlesMaintenance articleMaintenance=listArticleMaintenance.get(0);
//					TaArticleDAO daoArticle = new TaArticleDAO(getEm());
//
//					/*Article Epicéa*/
//					if(articleMaintenance.getTotalHtE2().signum()!=0){
//						temp = new TaLFacture(true);
//						temp.setTaDocument(documentFinal);
//						nbLigne++;
//						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleE2());
//						temp.setTaArticle(article);
////			            if (:taux_rem is null ) then taux_rem=0;
////			            if (:qte is null) then qte = 1;
////			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
//						temp.setRemTxLDocument(articleMaintenance.getReducE2());
//						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeE2().doubleValue()));
//						BigDecimal taux = articleMaintenance.getReducE2().divide(BigDecimal.valueOf(100));
//						BigDecimal mtHtRemise = articleMaintenance.getTotalHtE2().divide((
//								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
//						
//						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
//						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
//						temp.setRemHtLDocument(mtHtRemise.subtract(articleMaintenance.getTotalHtE2()));
//						temp.setMtHtLDocument(articleMaintenance.getTotalHtE2());
//						temp.setTaTLigne(daoTLigne.findByCode("H"));
//						if(article.getTaTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
//						if(article.getTaTTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
//						temp.setCompteLDocument(article.getNumcptArticle());
//						temp.setLibLDocument(article.getLibellecArticle());
//						temp.setNumLigneLDocument(nbLigne);
//						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
//							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
//						documentFinal.addLigne(temp);
//						if(articleMaintenance.getParticipationCogere().signum()<0){
////		                    prix=:participation/:qte;
////		                    taux_rem=0;
////		                    select a.id_article,a.libellec_article,a.numcpt_article,a.code_tva,a.taux_tva,a.code_unite from v_article a where
////		                    code_article like('COGE09')
//							temp = new TaLFacture(true);
//							temp.setTaDocument(documentFinal);
//							nbLigne++;
//							article =daoArticle.findByCode("COGE13");
//							temp.setTaArticle(article);							
//							temp.setQteLDocument(BigDecimal.valueOf(1));
//							temp.setPrixULDocument(articleMaintenance.getParticipationCogere().
//									divide(temp.getQteLDocument(),MathContext.DECIMAL32));
//							temp.setMtHtLDocument(articleMaintenance.getParticipationCogere());
//							temp.setTaTLigne(daoTLigne.findByCode("H"));
//							if(article.getTaTva()!=null)
//							temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
//							if(article.getTaTTva()!=null)
//							temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
//							temp.setCompteLDocument(article.getNumcptArticle());
//							temp.setLibLDocument(article.getLibellecArticle());
//							temp.setNumLigneLDocument(nbLigne);
//							temp.setTaDocument(documentFinal);
//							if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
//								temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
//							documentFinal.addLigne(temp);							
//						}
//					}					
//					/*Article E2-fac*/
//					if(articleMaintenance.getTotalHtF2().signum()!=0 && articleMaintenance.getArticleF2()!=null){
//						temp = new TaLFacture(true);
//						temp.setTaDocument(documentFinal);
//
//						nbLigne++;
//						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleF2());
//						temp.setTaArticle(article);
////			            if (:taux_rem is null ) then taux_rem=0;
////			            if (:qte is null) then qte = 1;
////			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
//						temp.setRemTxLDocument(articleMaintenance.getReducF2());
//						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeF2().doubleValue()));
//						BigDecimal taux = articleMaintenance.getReducF2().divide(BigDecimal.valueOf(100));
//						BigDecimal mtHtRemise = articleMaintenance.getTotalHtF2().divide((
//								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
//						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
//						temp.setMtHtLDocument(articleMaintenance.getTotalHtF2());
//						temp.setTaTLigne(daoTLigne.findByCode("H"));
//						if(article.getTaTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
//						if(article.getTaTTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
//						temp.setCompteLDocument(article.getNumcptArticle());
//						temp.setLibLDocument(article.getLibellecArticle());
//						temp.setNumLigneLDocument(nbLigne);
//						temp.setTaDocument(documentFinal);
//						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
//							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
//						documentFinal.addLigne(temp);
//					}
//					 /*Article Sara */
//					if(articleMaintenance.getTotalHtS2().signum()!=0){
//						temp = new TaLFacture(true);
//						temp.setTaDocument(documentFinal);
//
//						nbLigne++;
//						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleS2());
//						temp.setTaArticle(article);
////			            if (:taux_rem is null ) then taux_rem=0;
////			            if (:qte is null) then qte = 1;
////			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
//						temp.setRemTxLDocument(articleMaintenance.getReducS2());
//						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeS2().doubleValue()));
//						BigDecimal taux = articleMaintenance.getReducS2().divide(BigDecimal.valueOf(100));
//						BigDecimal mtHtRemise = articleMaintenance.getTotalHtS2().divide((
//								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
//						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
//						temp.setMtHtLDocument(articleMaintenance.getTotalHtS2());
//						temp.setTaTLigne(daoTLigne.findByCode("H"));
//						if(article.getTaTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
//						if(article.getTaTTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
//						temp.setCompteLDocument(article.getNumcptArticle());
//						temp.setLibLDocument(article.getLibellecArticle());
//						temp.setNumLigneLDocument(nbLigne);
//						temp.setTaDocument(documentFinal);
//						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
//							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
//						documentFinal.addLigne(temp);
//					}
//					 /*reduc */
//					if(articleMaintenance.getBonusHt().signum()<0){
//						temp = new TaLFacture(true);
//						temp.setTaDocument(documentFinal);
//						nbLigne++;
//						TaArticle article =daoArticle.findByCode("RPB13");
//						temp.setTaArticle(article);							
//						temp.setQteLDocument(BigDecimal.valueOf(1));
//						temp.setPrixULDocument(articleMaintenance.getBonusHt());
//						temp.setMtHtLDocument(articleMaintenance.getBonusHt());
//						temp.setTaTLigne(daoTLigne.findByCode("H"));
//						if(article.getTaTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
//						if(article.getTaTTva()!=null)
//						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
//						temp.setCompteLDocument(article.getNumcptArticle());
//						temp.setLibLDocument(article.getLibellecArticle());
//						temp.setNumLigneLDocument(nbLigne);
//						temp.setTaDocument(documentFinal);
//						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
//							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
//						documentFinal.addLigne(temp);							
//					}
//					TaInfosFacture infos = new TaInfosFacture();;
//					infos.setTaDocument(documentFinal);
//					documentFinal.setTaInfosDocument(infos);
//					
//					changementDeTiers(documentFinal);
//					
//				daoDocument.inserer(documentFinal);				
//				documentFinal.calculeTvaEtTotaux();
//				if(!this.typePaiement.equals("")){
//					TaRReglement taReglement = new TaRReglement();
//					TaReglement reglement = new TaReglement();
//					reglement=daoReglement.enregistrerMerge(reglement);
//					taReglement.setTaReglement(reglement);
//					reglement.setTaTiers(tiers);
//					reglement.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise());
//					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(getEm()).genereCode());
//					if(taWlgr.getTtc_du().compareTo(documentFinal.getNetTtcCalc())>0)
//						taReglement.setAffectation(documentFinal.getNetTtcCalc());
//					else
//					taReglement.setAffectation(taWlgr.getTtc_du());
//					reglement.setNetTtcCalc(taWlgr.getTtc_du());
//					reglement.setDateDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setDateDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setEtat(IHMEtat.integre);
//					taReglement.getTaReglement().setDateLivDocument(documentFinal.getDateDocument());
//					taReglement.getTaReglement().setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
//					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
//					taReglement.setTaFacture(documentFinal);
//					documentFinal.addRReglement(taReglement);
////					documentFinal.setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
//				}
//				
//				documentFinal.setRegleDocument(documentFinal.getNetTtcCalc());
//				documentFinal.setRegleCompletDocument(documentFinal.getNetTtcCalc());
//				documentFinal.setNetAPayer(BigDecimal.valueOf(0));
//				daoDocument.enregistrerMerge(documentFinal);
//					
//			daoDocument.commit(transaction);
//			
//			daoWlgr.begin(transaction);			
//			taWlgr.setTraite(Short.valueOf("1"));
//			taWlgr.setCodeDoc(documentFinal.getCodeDocument());
//			daoWlgr.enregistrerMerge(taWlgr);
//			daoWlgr.commit(transaction);
//			//update ta_wlgr set traite = 1,code_doc=:code_document where code_client like(:code_tiers_input);
//			}
//			return documentFinal;
//		} catch (Exception e) {
//			logger.error("",e);
//			return null;
//		}
//	}
	public TaFacture genereLGRNMoins1(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		
		//TaFacture documentInit = null;
		TaFacture documentFinal = null;
		TaWlgrNMoins1DAO daoWlgr = new TaWlgrNMoins1DAO(getEm());
		try {
			TaWlgrNMoins1 taWlgrNMoins1=daoWlgr.findByCode(this.codeTiers);

			List<ArticlesMaintenance> listArticleMaintenance =daoWlgr.selectArticleMaintenanceClient(this.codeTiers);
			if(listArticleMaintenance.size()>0){
			TaFactureDAO daoDocument = new TaFactureDAO(getEm());
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			TaTLigneDAO daoTLigne = new TaTLigneDAO(getEm());
			TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(getEm());
			TaReglementDAO daoReglement = new TaReglementDAO(getEm());
			TaLFacture temp = null;
			TaTiers tiers = null;
			EntityTransaction transaction = daoDocument.getEntityManager().getTransaction();
			daoDocument.begin(transaction);
			
			//documentInit=daoDocument.findByCode(this.code);
			//if(documentInit!=null){
//				documentInit.setLegrain(true);
				documentFinal =new TaFacture(true);
				documentFinal.setCodeDocument(daoDocument.genereCode());
				documentFinal.setLibelleDocument(this.libelleDoc);
				documentFinal.setCommentaire(this.commentaire);
				documentFinal.setDateEchDocument(this.dateDoc);
				documentFinal.setDateDocument(this.dateDoc);
				documentFinal.setDateLivDocument(this.dateDoc);
				tiers=daoTiers.findByCode(this.codeTiers);
				
				documentFinal.setTaTiers(tiers);
				//changementDeTiers(documentFinal);
				int nbLigne=0;
				//calculer les lignes en fonction de la table TA_WLGR
						ArticlesMaintenance articleMaintenance=listArticleMaintenance.get(0);
					TaArticleDAO daoArticle = new TaArticleDAO(getEm());

					/*Article Epicéa*/
					if(articleMaintenance.getTotalHtE2().signum()!=0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleE2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducE2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeE2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducE2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtE2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setRemHtLDocument(mtHtRemise.subtract(articleMaintenance.getTotalHtE2()));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtE2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
						if(articleMaintenance.getParticipationCogere().signum()<0){
//		                    prix=:participation/:qte;
//		                    taux_rem=0;
//		                    select a.id_article,a.libellec_article,a.numcpt_article,a.code_tva,a.taux_tva,a.code_unite from v_article a where
//		                    code_article like('COGE09')
							temp = new TaLFacture(true);
							temp.setTaDocument(documentFinal);
							nbLigne++;
							article =daoArticle.findByCode("COGE12");
							temp.setTaArticle(article);							
							temp.setQteLDocument(BigDecimal.valueOf(1));
							temp.setPrixULDocument(articleMaintenance.getParticipationCogere().
									divide(temp.getQteLDocument(),MathContext.DECIMAL32));
							temp.setMtHtLDocument(articleMaintenance.getParticipationCogere());
							temp.setTaTLigne(daoTLigne.findByCode("H"));
							if(article.getTaTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
							if(article.getTaTTva()!=null)
							temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
							temp.setCompteLDocument(article.getNumcptArticle());
							temp.setLibLDocument(article.getLibellecArticle());
							temp.setNumLigneLDocument(nbLigne);
							temp.setTaDocument(documentFinal);
							if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
								temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
							documentFinal.addLigne(temp);							
						}
					}					
					/*Article E2-fac*/
					if(articleMaintenance.getTotalHtF2().signum()!=0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleF2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducF2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeF2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducF2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtF2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(LibCalcul.arrondi(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32),4));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtF2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					 /*Article Sara */
					if(articleMaintenance.getTotalHtS2().signum()!=0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);

						nbLigne++;
						TaArticle article = daoArticle.findByCode(articleMaintenance.getArticleS2());
						temp.setTaArticle(article);
//			            if (:taux_rem is null ) then taux_rem=0;
//			            if (:qte is null) then qte = 1;
//			            prix = (:mtht/(1-(:taux_rem/100)))/:qte;
						temp.setRemTxLDocument(articleMaintenance.getReducS2());
						temp.setQteLDocument(LibConversion.DoubleToBigDecimal(articleMaintenance.getDureeS2().doubleValue()));
						BigDecimal taux = articleMaintenance.getReducS2().divide(BigDecimal.valueOf(100));
						BigDecimal mtHtRemise = articleMaintenance.getTotalHtS2().divide((
								BigDecimal.valueOf(1).subtract((taux))),MathContext.DECIMAL32);
						temp.setPrixULDocument(mtHtRemise.divide(temp.getQteLDocument(),MathContext.DECIMAL32));
						temp.setMtHtLDocument(articleMaintenance.getTotalHtS2());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);
					}
					/*reduc */
					if(articleMaintenance.getBonusHt().signum()<0){
						temp = new TaLFacture(true);
						temp.setTaDocument(documentFinal);
						nbLigne++;
						TaArticle article =daoArticle.findByCode("RPB12");
						temp.setTaArticle(article);							
						temp.setQteLDocument(BigDecimal.valueOf(1));
						temp.setPrixULDocument(articleMaintenance.getBonusHt());
						temp.setMtHtLDocument(articleMaintenance.getBonusHt());
						temp.setTaTLigne(daoTLigne.findByCode("H"));
						if(article.getTaTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTva().getCodeTva());
						if(article.getTaTTva()!=null)
						temp.setCodeTTvaLDocument(article.getTaTTva().getCodeTTva());
						temp.setCompteLDocument(article.getNumcptArticle());
						temp.setLibLDocument(article.getLibellecArticle());
						temp.setNumLigneLDocument(nbLigne);
						temp.setTaDocument(documentFinal);
						if(article.getTaPrix()!=null && article.getTaPrix().getTaUnite()!=null)
							temp.setU1LDocument(article.getTaPrix().getTaUnite().getCodeUnite());
						documentFinal.addLigne(temp);							
					}
					TaInfosFacture infos = new TaInfosFacture();;
					infos.setTaDocument(documentFinal);
					documentFinal.setTaInfosDocument(infos);
					
					changementDeTiers(documentFinal);
					
				daoDocument.inserer(documentFinal);				
				documentFinal.calculeTvaEtTotaux();
				if(!this.typePaiement.equals("")){
					TaRReglement taReglement = new TaRReglement();
					TaReglement reglement = new TaReglement();
					reglement=daoReglement.enregistrerMerge(reglement);
					taReglement.setTaReglement(reglement);
					reglement.setTaTiers(tiers);
					TaTPaiement taTPaiement = daoTPaiement.findByCode(this.typePaiement);
					reglement.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise(taTPaiement));
					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(getEm()).genereCode());
					
					if(taWlgrNMoins1.getTtc_du().compareTo(documentFinal.getNetTtcCalc())>0)
						taReglement.setAffectation(documentFinal.getNetTtcCalc());
					else
					taReglement.setAffectation(taWlgrNMoins1.getTtc_du());
					reglement.setNetTtcCalc(taWlgrNMoins1.getTtc_du());
					
//					taReglement.setAffectation(documentFinal.getNetTtcCalc());
//					reglement.setNetTtcCalc(documentFinal.getNetTtcCalc());
					reglement.setDateDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setDateDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setEtat(IHMEtat.integre);
					taReglement.getTaReglement().setDateLivDocument(documentFinal.getDateDocument());
					taReglement.getTaReglement().setTaTPaiement(taTPaiement);
					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
					taReglement.setTaFacture(documentFinal);
					documentFinal.addRReglement(taReglement);
//					documentFinal.setTaTPaiement(daoTPaiement.findByCode(this.typePaiement));
				}
				
				documentFinal.setRegleDocument(documentFinal.getNetTtcCalc());
				documentFinal.setRegleCompletDocument(documentFinal.getNetTtcCalc());
				documentFinal.setNetAPayer(BigDecimal.valueOf(0));
				daoDocument.enregistrerMerge(documentFinal);
					
			daoDocument.commit(transaction);
			
			daoWlgr.begin(transaction);			
			taWlgrNMoins1.setTraite(Short.valueOf("1"));
			taWlgrNMoins1.setCodeDoc(documentFinal.getCodeDocument());
			daoWlgr.enregistrerMerge(taWlgrNMoins1);
			daoWlgr.commit(transaction);
			//update ta_wlgr set traite = 1,code_doc=:code_document where code_client like(:code_tiers_input);
			}
			return documentFinal;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	public TaLFacture copieLignesDevisDansLignesFacture(TaLDevis taLDevis) {
		TaLFacture ligne = new TaLFacture();
		try {
			ligne.setTaTLigne(taLDevis.getTaTLigne());
			ligne.setTaDocument(null);
			ligne.setTaArticle(taLDevis.getTaArticle());
			ligne.setNumLigneLDocument(taLDevis.getNumLigneLDocument());
			ligne.setLibLDocument(taLDevis.getLibLDocument());
			ligne.setQteLDocument(taLDevis.getQteLDocument());
			ligne.setQte2LDocument(taLDevis.getQte2LDocument());
			ligne.setU1LDocument(taLDevis.getU1LDocument());
			ligne.setU2LDocument(taLDevis.getU2LDocument());
			ligne.setPrixULDocument(taLDevis.getPrixULDocument());
			ligne.setTauxTvaLDocument(taLDevis.getTauxTvaLDocument());
			ligne.setCompteLDocument(taLDevis.getCompteLDocument());
			ligne.setCodeTvaLDocument(taLDevis.getCodeTvaLDocument());
			ligne.setCodeTTvaLDocument(taLDevis.getCodeTTvaLDocument());
			ligne.setMtHtLDocument(taLDevis.getMtHtLDocument());
			ligne.setMtTtcLDocument(taLDevis.getMtTtcLDocument());
			ligne.setRemTxLDocument(taLDevis.getRemTxLDocument());
			ligne.setRemHtLDocument(taLDevis.getRemHtLDocument());			
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		// on renvoie le clone
		return ligne;
	}
	
	public TaInfosFacture copieInfosDevisDansInfosFacture(TaInfosDevis taInfosDevis) {
		TaInfosFacture infos = new TaInfosFacture();
		try {
			infos.setTaDocument(null);
			infos.setAdresse1(taInfosDevis.getAdresse1());
			infos.setAdresse2(taInfosDevis.getAdresse2());
			infos.setAdresse3(taInfosDevis.getAdresse3());
			infos.setCodepostal(taInfosDevis.getCodepostal());
			infos.setVille(taInfosDevis.getVille());
			infos.setPays(taInfosDevis.getPays());
			infos.setAdresse1Liv(taInfosDevis.getAdresse1Liv());
			infos.setAdresse2Liv(taInfosDevis.getAdresse2Liv());
			infos.setAdresse3Liv(taInfosDevis.getAdresse3Liv());
			infos.setCodepostalLiv(taInfosDevis.getCodepostalLiv());
			infos.setVilleLiv(taInfosDevis.getVilleLiv());
			infos.setPaysLiv(taInfosDevis.getPaysLiv());
			infos.setCodeCompta(taInfosDevis.getCodeCompta());
			infos.setCompte(taInfosDevis.getCompte());
			infos.setNomTiers(taInfosDevis.getNomTiers());
			infos.setPrenomTiers(taInfosDevis.getPrenomTiers());
			infos.setSurnomTiers(taInfosDevis.getSurnomTiers());
			infos.setCodeTCivilite(taInfosDevis.getCodeTCivilite());
			infos.setCodeTEntite(taInfosDevis.getCodeTEntite());
			infos.setTvaIComCompl(taInfosDevis.getTvaIComCompl());
			infos.setCodeCPaiement(taInfosDevis.getCodeCPaiement());
			infos.setLibCPaiement(taInfosDevis.getLibCPaiement());
			infos.setReportCPaiement(taInfosDevis.getReportCPaiement());
			infos.setFinMoisCPaiement(taInfosDevis.getFinMoisCPaiement());
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		// on renvoie le clone
		return infos;
	}
	public TaFacture copieDevisDansFacture(TaDevis taDevis) {
		TaFacture doc = new TaFacture(true);
		try {
			//doc = (TaFacture)super.clone();
			//doc.setTaTPaiement(taDevis.getTaTPaiement());
			doc.setTaTiers(taDevis.getTaTiers());
//			doc.setTaCPaiement(taDevis.getTaCPaiement());
			doc.setCodeDocument("");
			doc.setDateDocument(taDevis.getDateDocument());
			doc.setDateEchDocument(taDevis.getDateEchDocument());
			doc.setDateLivDocument(taDevis.getDateLivDocument());
			doc.setLibelleDocument(taDevis.getLibelleDocument());
			doc.setRegleDocument(BigDecimal.valueOf(0));
			doc.setRemHtDocument(taDevis.getRemHtDocument());
			doc.setTxRemHtDocument(taDevis.getTxRemHtDocument());
			doc.setRemTtcDocument(taDevis.getRemTtcDocument());
			doc.setTxRemTtcDocument(taDevis.getTxRemTtcDocument());
			doc.setNbEDocument(taDevis.getNbEDocument());
			doc.setTtc(taDevis.getTtc());
			doc.setExport(taDevis.getExport());
			doc.setCommentaire(taDevis.getCommentaire());

			
			for (TaLDevis ligne : taDevis.getLignes()) {
				TaLFacture temp =copieLignesDevisDansLignesFacture(ligne); 
				temp.setTaDocument(doc);
				doc.addLigne(temp);
			}
			TaInfosFacture infos = copieInfosDevisDansInfosFacture(taDevis.getTaInfosDocument());
			infos.setTaDocument(doc);
			doc.setTaInfosDocument(infos);
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		// on renvoie le clone
		return doc;
	}

	
	public TaFacture genereFactureSurDevis(){
		TaDevis documentInit = null;
		TaFacture documentFinal = null;
		try {
			TaDevisDAO daoDocumentInitial = new TaDevisDAO(getEm());
			TaFactureDAO daoDocumentFinal = new TaFactureDAO(getEm());
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			TaTiers tiers = null;
			EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
			daoDocumentInitial.begin(transaction);
			
			documentInit=daoDocumentInitial.findByCode(this.code);
			if(documentInit!=null){
				documentInit.setLegrain(true);
				documentFinal=copieDevisDansFacture(documentInit);
				documentFinal.setCodeDocument(daoDocumentFinal.genereCode());
				documentFinal.setCommentaire(commentaire);
				
				daoDocumentFinal.inserer(documentFinal);				
				documentFinal.calculeTvaEtTotaux();	
				
				daoDocumentFinal.enregistrerMerge(documentFinal);				
			}
			daoDocumentFinal.commit(transaction);
			return documentFinal;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	/**
	 * Initialisation des propriétés de la facture en fonction du codeTiers
	 */
	public void changementDeTiersPrelevement(TaPrelevement doc) {
		boolean leTiersADesAdresseLiv = false;
		boolean leTiersADesAdresseFact = false;
		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		if(doc.getTaTiers()!=null){
			
//			taFacture.setTaInfosFactures(new TaInfosFacture());
//			taFacture.getTaInfosFactures().setTaFacture(taFacture);
//			taFacture.setTaInfosFactures(taFacture.getTaInfosFactures());
			
			mapperUIToModePrelVersInfosDoc.map(doc,doc.getTaInfosDocument());
			doc.getTaInfosDocument().setNomTiers(doc.getTaTiers().getNomTiers());
			
			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseLiv = doc.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le codeTiers a des adresse de ce type
			}
			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseFact = doc.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le codeTiers a des adresse de ce type
			}
			
			Iterator<TaAdresse> ite = null;
			TaAdresse taAdresseLiv =null;
			IHMAdresseInfosLivraison ihmAdresseInfosLivraison = null;
			if(doc.getTaTiers().getTaAdresses()!=null)ite =  doc.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseLiv) { 
				//ajout des adresse de livraison au modele
				while (ihmAdresseInfosLivraison==null && ite.hasNext()){	
					taAdresseLiv =ite.next();
					if(taAdresseLiv.getTaTAdr()!=null && taAdresseLiv.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
						ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
						mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
						mapperUIToModelAdresseLivVersInfosPrel.map(ihmAdresseInfosLivraison, doc.getTaInfosDocument());
					}
				}
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
				mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
				mapperUIToModelAdresseLivVersInfosPrel.map(ihmAdresseInfosLivraison, doc.getTaInfosDocument());
			}

			TaAdresse taAdresseFact =null;
			IHMAdresseInfosFacturation ihmAdresseInfosFacturation =null;
			if(doc.getTaTiers().getTaAdresses()!=null)ite =  doc.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseFact) { 
				while (ihmAdresseInfosFacturation==null && ite.hasNext()){
					taAdresseFact =ite.next();
					if(taAdresseFact.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
						ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
						mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
						mapperUIToModelAdresseFactVersInfosPrel.map(ihmAdresseInfosFacturation, doc.getTaInfosDocument());
					}
				}				
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
				mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
				mapperUIToModelAdresseFactVersInfosPrel.map(ihmAdresseInfosFacturation, doc.getTaInfosDocument());
			}
			
			
			if(doc.getTaTiers().getTaCPaiement()==null)
				mapperUIToModelCPaiementVersInfosPrel.map(new IHMInfosCPaiement(), doc.getTaInfosDocument());
			else{
				IHMInfosCPaiement ihmInfosCPaiement =new IHMInfosCPaiement();
				mapperModelToUICPaiementInfosDocument.map(doc.getTaTiers().getTaCPaiement(), ihmInfosCPaiement);
				mapperUIToModelCPaiementVersInfosPrel.map(ihmInfosCPaiement, doc.getTaInfosDocument());
			}
			mapperUIToModePrelVersInfosDoc.map(doc, doc.getTaInfosDocument());	
		}
	}
	
	/**
	 * Initialisation des propriétés de la facture en fonction du codeTiers
	 */
	public void changementDeTiers(TaFacture taFacture) {
		boolean leTiersADesAdresseLiv = false;
		boolean leTiersADesAdresseFact = false;
		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		if(taFacture.getTaTiers()!=null){
			if(taFacture.getTaTiers().getTaTTvaDoc()!=null && taFacture.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc()!=null)
			taFacture.getTaInfosDocument().setCodeTTvaDoc(taFacture.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
			else taFacture.getTaInfosDocument().setCodeTTvaDoc("F");
//			taFacture.setTaInfosFactures(new TaInfosFacture());
//			taFacture.getTaInfosFactures().setTaFacture(taFacture);
//			taFacture.setTaInfosFactures(taFacture.getTaInfosFactures());
			
			mapperUIToModelDocumentVersInfosDoc.map(taFacture,taFacture.getTaInfosDocument());
			taFacture.getTaInfosDocument().setNomTiers(taFacture.getTaTiers().getNomTiers());
			
			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseLiv = taFacture.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le codeTiers a des adresse de ce type
			}
			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseFact = taFacture.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le codeTiers a des adresse de ce type
			}
			
			Iterator<TaAdresse> ite = null;
			TaAdresse taAdresseLiv =null;
			IHMAdresseInfosLivraison ihmAdresseInfosLivraison = null;
			if(taFacture.getTaTiers().getTaAdresses()!=null)ite =  taFacture.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseLiv) { 
				//ajout des adresse de livraison au modele
				while (ihmAdresseInfosLivraison==null && ite.hasNext()){	
					taAdresseLiv =ite.next();
					if(taAdresseLiv.getTaTAdr()!=null && taAdresseLiv.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
						ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
						mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
						mapperUIToModelAdresseLivVersInfosDoc.map(ihmAdresseInfosLivraison, taFacture.getTaInfosDocument());
					}
				}
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
				mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
				mapperUIToModelAdresseLivVersInfosDoc.map(ihmAdresseInfosLivraison, taFacture.getTaInfosDocument());
				}

			TaAdresse taAdresseFact =null;
			IHMAdresseInfosFacturation ihmAdresseInfosFacturation =null;
			if(taFacture.getTaTiers().getTaAdresses()!=null)ite =  taFacture.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseFact) { 
				while (ihmAdresseInfosFacturation==null && ite.hasNext()){
					taAdresseFact =ite.next();
					if(taAdresseFact.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
						ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
						mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
						mapperUIToModelAdresseFactVersInfosDoc.map(ihmAdresseInfosFacturation, taFacture.getTaInfosDocument());
					}
				}				
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
				mapperModelToUIAdresseInfosDocument.map(taAdresseLiv, ihmAdresseInfosFacturation);
				mapperUIToModelAdresseFactVersInfosDoc.map(ihmAdresseInfosFacturation, taFacture.getTaInfosDocument());
			}
	
			
			if(taFacture.getTaTiers().getTaCPaiement()==null)
				mapperUIToModelCPaiementVersInfosDoc.map(new IHMInfosCPaiement(), taFacture.getTaInfosDocument());
			else{
				IHMInfosCPaiement ihmInfosCPaiement =new IHMInfosCPaiement();
				mapperModelToUICPaiementInfosDocument.map(taFacture.getTaTiers().getTaCPaiement(), ihmInfosCPaiement);
				mapperUIToModelCPaiementVersInfosDoc.map(ihmInfosCPaiement, taFacture.getTaInfosDocument());
			}
			mapperUIToModelDocumentVersInfosDoc.map(taFacture, taFacture.getTaInfosDocument());	
		}
	}
		
	
	public void creeDocumentSurDocument(){
//		begin
//		execute procedure maj_generateur;
//		execute procedure vide_document_temp(:module_source);
//		code_document_tmp='';
//		    /*query_in ='select code_facture,id_facture from ta_facture where  id_facture =5 or id_facture =6 or id_facture =7 ';*/
//		    if(upper(:module_dest)='FACTURE')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_facture,1) ;
//		        end
//		    if(upper(:module_dest)='AVOIR')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_AVOIR,1) ;
//		        end
//		    if(upper(:module_dest)='APPORTEUR')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_APPORTEUR,1) ;
//		        end
//		    if(upper(:module_dest)='PROFORMA')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_PROFORMA,1) ;
//		        end                        
//		    if(upper(:module_dest)='DEVIS')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_devis,1) ;
//		        end
//		    if(upper(:module_dest)='BONCDE')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_BONCDE,1) ;
//		        end        
//		    if(upper(:module_dest)='BONLIV')then
//		        begin
//		            id_doc_new=GEN_ID(num_id_bonliv,1) ;
//		        end
//		    code_document_tmp= CODEFIXE;
//		    for execute statement :query_in
//		     into :code_document,id_document do
//		     begin
//		         execute procedure recup_lignes_document(:module_source,:module_dest,0,:code_document) returning_values :id_document ;
//		     end
//		    nb_ligne=0;
//		    if(upper(:module_dest)='FACTURE')then
//		        begin
//		            update ta_l_facture_temp set id_l_facture =GEN_ID(num_id_l_facture,1) where id_facture = :id_document;
//		            update ta_l_facture_temp set id_facture =:id_doc_new where id_facture = :id_document;
//
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_facture (id_facture,code_facture,date_facture,date_ech_facture,
//		            date_liv_facture,libelle_facture,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_facture,tx_rem_ht_facture,rem_ttc_facture,tx_rem_ttc_facture
//		            ,nb_e_facture,ttc,export)';
//
//		            query_loc2 = ' insert into ta_infos_facture  (id_infos_facture,id_facture,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		             select GEN_ID(num_id_infos_facture,1),'||:id_doc_new;
//		        end
//
//		    if(upper(:module_dest)='AVOIR')then
//		        begin
//		            update ta_l_AVOIR_temp set id_l_AVOIR =GEN_ID(num_id_l_AVOIR,1) where id_AVOIR = :id_document;
//		            update ta_l_AVOIR_temp set id_AVOIR =:id_doc_new where id_AVOIR = :id_document;
//
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_AVOIR (id_AVOIR,code_AVOIR,date_AVOIR,date_ech_AVOIR,
//		            date_liv_AVOIR,libelle_AVOIR,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_AVOIR,tx_rem_ht_AVOIR,rem_ttc_AVOIR,tx_rem_ttc_AVOIR
//		            ,nb_e_AVOIR,ttc,export)';
//
//		            query_loc2 = ' insert into ta_infos_AVOIR  (id_infos_AVOIR,id_AVOIR,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		             select GEN_ID(num_id_infos_AVOIR,1),'||:id_doc_new;
//		        end
//
//		    if(upper(:module_dest)='APPORTEUR')then
//		        begin
//		            update ta_l_APPORTEUR_temp set id_l_APPORTEUR =GEN_ID(num_id_l_APPORTEUR,1) where id_APPORTEUR = :id_document;
//		            update ta_l_APPORTEUR_temp set id_APPORTEUR =:id_doc_new where id_APPORTEUR = :id_document;
//
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_APPORTEUR (id_APPORTEUR,code_APPORTEUR,date_APPORTEUR,date_ech_APPORTEUR,
//		            date_liv_APPORTEUR,libelle_APPORTEUR,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_APPORTEUR,tx_rem_ht_APPORTEUR,rem_ttc_APPORTEUR,tx_rem_ttc_APPORTEUR
//		            ,nb_e_APPORTEUR,ttc,export)';
//
//		            query_loc2 = ' insert into ta_infos_APPORTEUR  (id_infos_APPORTEUR,id_APPORTEUR,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		             select GEN_ID(num_id_infos_APPORTEUR,1),'||:id_doc_new;
//		        end
//
//		    if(upper(:module_dest)='PROFORMA')then
//		        begin
//		            update ta_l_PROFORMA_temp set id_l_PROFORMA =GEN_ID(num_id_l_PROFORMA,1) where id_PROFORMA = :id_document;
//		            update ta_l_PROFORMA_temp set id_PROFORMA =:id_doc_new where id_PROFORMA = :id_document;
//
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_PROFORMA (id_PROFORMA,code_PROFORMA,date_PROFORMA,date_ech_PROFORMA,
//		            date_liv_PROFORMA,libelle_PROFORMA,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_PROFORMA,tx_rem_ht_PROFORMA,rem_ttc_PROFORMA,tx_rem_ttc_PROFORMA
//		            ,nb_e_PROFORMA,ttc,export)';
//
//		            query_loc2 = ' insert into ta_infos_PROFORMA  (id_infos_PROFORMA,id_PROFORMA,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		             select GEN_ID(num_id_infos_PROFORMA,1),'||:id_doc_new;
//		        end
//
//		    if(upper(:module_dest)='DEVIS')then
//		        begin
//		            for select id_l_devis from ta_l_devis_temp where id_devis = :id_document into :id_ligne
//		            do
//		            begin
//		               update ta_l_devis_temp set NUM_LIGNE_L_devis =:nb_ligne where id_l_devis = :id_ligne ;
//		               nb_ligne = :nb_ligne +1;
//		            end
//		            update ta_l_devis_temp set id_l_devis =GEN_ID(num_id_l_devis,1) where id_devis = :id_document;
//		            update ta_l_devis_temp set id_devis =:id_doc_new where id_devis = :id_document;
//		        
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_devis (id_devis,code_devis,date_devis,date_ech_devis,
//		            date_liv_devis,libelle_devis,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_devis,tx_rem_ht_devis,rem_ttc_devis,tx_rem_ttc_devis
//		            ,nb_e_devis,ttc,export)';
//
//
//		            query_loc2 = ' insert into ta_infos_devis  (id_infos_devis,id_devis,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		            select GEN_ID(num_id_infos_devis,1) ,'||:id_doc_new ;
//		        end
//		    if(upper(:module_dest)='BONCDE')then
//		        begin
//		            for select id_l_BONCDE from ta_l_BONCDE_temp where id_BONCDE = :id_document into :id_ligne
//		            do
//		            begin
//		               update ta_l_BONCDE_temp set NUM_LIGNE_L_BONCDE =:nb_ligne where id_l_BONCDE = :id_ligne ;
//		               nb_ligne = :nb_ligne +1;
//		            end
//		            update ta_l_BONCDE_temp set id_l_BONCDE =GEN_ID(num_id_l_BONCDE,1) where id_BONCDE = :id_document;
//		            update ta_l_BONCDE_temp set id_BONCDE =:id_doc_new where id_BONCDE = :id_document;
//		        
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_BONCDE (id_BONCDE,code_BONCDE,date_BONCDE,date_ech_BONCDE,
//		            date_liv_BONCDE,libelle_BONCDE,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_BONCDE,tx_rem_ht_BONCDE,rem_ttc_BONCDE,tx_rem_ttc_BONCDE
//		            ,nb_e_BONCDE,ttc,export)';
//
//
//		            query_loc2 = ' insert into ta_infos_BONCDE  (id_infos_BONCDE,id_BONCDE,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		            select GEN_ID(num_id_infos_BONCDE,1) ,'||:id_doc_new ;
//		        end        
//		    if(upper(:module_dest)='BONLIV')then
//		        begin
//		            for select id_l_bonLiv from ta_l_bonLiv_temp where id_bonliv = :id_document into :id_ligne
//		            do
//		            begin
//		               update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv =:nb_ligne where id_l_bonLiv = :id_ligne ;
//		               nb_ligne = :nb_ligne +1;
//		            end
//		            update ta_l_bonLiv_temp set id_l_bonLiv =GEN_ID(num_id_l_bonLiv,1) where id_bonliv = :id_document;
//		            update ta_l_bonLiv_temp set id_bonLiv =:id_doc_new where id_bonliv = :id_document;
//		        
//		           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            query_loc1 = ' insert into ta_bonLiv (id_bonLiv,code_bonLiv,date_bonLiv,date_ech_bonLiv,
//		            date_liv_bonLiv,libelle_bonLiv,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
//		            ,id_c_paiement,rem_ht_bonLiv,tx_rem_ht_bonLiv,rem_ttc_bonLiv,tx_rem_ttc_bonLiv
//		            ,nb_e_bonLiv,ttc,export)';
//
//
//		            query_loc2 = ' insert into ta_infos_bonLiv  (id_infos_bonLiv,id_bonLiv,adresse1,adresse2
//		            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
//		            select GEN_ID(num_id_infos_bonLiv,1) ,'||:id_doc_new ;
//		        end           /* Récupérer les infos à partir du 1er document*/
//		            /*Créer le document dest*/
//		            if (upper(:module_source)='FACTURE') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_facture,doc2.date_ech_facture,
//		                    doc2.date_liv_facture,doc2.libelle_facture,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_facture,doc2.tx_rem_ht_facture,doc2.rem_ttc_facture,doc2.tx_rem_ttc_facture
//		                    ,doc2.nb_e_facture,doc2.ttc,doc2.export from ta_facture doc2 where doc2.id_facture ='|| :id_document;
//		        
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_facture where id_facture ='|| :id_document;
//		                end
//
//		            if (upper(:module_source)='AVOIR') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_AVOIR,doc2.date_ech_AVOIR,
//		                    doc2.date_liv_AVOIR,doc2.libelle_AVOIR,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_AVOIR,doc2.tx_rem_ht_AVOIR,doc2.rem_ttc_AVOIR,doc2.tx_rem_ttc_AVOIR
//		                    ,doc2.nb_e_AVOIR,doc2.ttc,doc2.export from ta_AVOIR doc2 where doc2.id_AVOIR ='|| :id_document;
//		        
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_AVOIR where id_AVOIR ='|| :id_document;
//		                end
//
//		            if (upper(:module_source)='APPORTEUR') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_APPORTEUR,doc2.date_ech_APPORTEUR,
//		                    doc2.date_liv_APPORTEUR,doc2.libelle_APPORTEUR,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_APPORTEUR,doc2.tx_rem_ht_APPORTEUR,doc2.rem_ttc_APPORTEUR,doc2.tx_rem_ttc_APPORTEUR
//		                    ,doc2.nb_e_APPORTEUR,doc2.ttc,doc2.export from ta_APPORTEUR doc2 where doc2.id_APPORTEUR ='|| :id_document;
//		        
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_APPORTEUR where id_APPORTEUR ='|| :id_document;
//		                end
//		                
//		            if (upper(:module_source)='PROFORMA') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_PROFORMA,doc2.date_ech_PROFORMA,
//		                    doc2.date_liv_PROFORMA,doc2.libelle_PROFORMA,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_PROFORMA,doc2.tx_rem_ht_PROFORMA,doc2.rem_ttc_PROFORMA,doc2.tx_rem_ttc_PROFORMA
//		                    ,doc2.nb_e_PROFORMA,doc2.ttc,doc2.export from ta_PROFORMA doc2 where doc2.id_PROFORMA ='|| :id_document;
//		        
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_PROFORMA where id_PROFORMA ='|| :id_document;
//		                end                                
//		                
//		            if (upper(:module_source)='DEVIS') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_devis,doc2.date_ech_devis,
//		                    doc2.date_liv_devis,doc2.libelle_devis,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_devis,doc2.tx_rem_ht_devis,doc2.rem_ttc_devis,doc2.tx_rem_ttc_devis
//		                    ,doc2.nb_e_devis,doc2.ttc,doc2.export from ta_devis doc2 where doc2.id_devis ='|| :id_document;
//
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_devis where id_devis ='|| :id_document;
//		                end
//		            if (upper(:module_source)='BONCDE') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_BONCDE,doc2.date_ech_BONCDE,
//		                    doc2.date_liv_BONCDE,doc2.libelle_BONCDE,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_BONCDE,doc2.tx_rem_ht_BONCDE,doc2.rem_ttc_BONCDE,doc2.tx_rem_ttc_BONCDE
//		                    ,doc2.nb_e_BONCDE,doc2.ttc,doc2.export from ta_BONCDE doc2 where doc2.id_BONCDE ='|| :id_document;
//
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_BONCDE where id_BONCDE ='|| :id_document;
//		                end                
//		            if (upper(:module_source)='BONLIV') then
//		                begin
//		                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_bonLiv,doc2.date_bonLiv,
//		                    doc2.date_liv_bonLiv,doc2.libelle_bonLiv,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
//		                    ,doc2.id_c_paiement,doc2.rem_ht_bonLiv,doc2.tx_rem_ht_bonLiv,doc2.rem_ttc_bonLiv,doc2.tx_rem_ttc_bonLiv
//		                    ,doc2.nb_e_bonLiv,doc2.ttc,0 from ta_bonLiv doc2 where doc2.id_bonLiv ='|| :id_document;
//
//		                    query_loc2 = query_loc2 ||',adresse1,adresse2
//		                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
//		                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
//		                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
//		                    from ta_infos_bonLiv where id_bonLiv ='|| :id_document;
//		                end
//		     if (:query_loc1<>'') then execute statement :query_loc1;
//		     if (:query_loc2<>'') then execute statement :query_loc2;
//
//		    for execute statement :query_in
//		     into :code_document,id_document do
//		     begin
//		              if (upper(:module_dest)='FACTURE') then
//		                  begin
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_FACTURE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end
//		                  
//		              if (upper(:module_dest)='AVOIR') then
//		                  begin
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_AVOIR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end
//		              if (upper(:module_dest)='APPORTEUR') then
//		                  begin
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_APPORTEUR)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end
//		              if (upper(:module_dest)='PROFORMA') then
//		                  begin
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_PROFORMA)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end                                                      
//		                  
//
//
//		              if (upper(:module_dest)='DEVIS') then
//		                  begin
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_DEVIS)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end
//		              if (upper(:module_dest)='BONCDE') then
//		                  begin
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONLIV') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_BONCDE)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end                  
//		              if (upper(:module_dest)='BONLIV') then
//		                  begin
//		                     if (upper(:module_source)='DEVIS') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='PROFORMA') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_PROFORMA,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='BONCDE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_BONCDE,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='AVOIR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_AVOIR,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='APPORTEUR') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_APPORTEUR,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                     if (upper(:module_source)='FACTURE') then
//		                       insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_BONLIV)
//		                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
//		                  end
//		     end
//		execute procedure vide_document_temp(:module_source);
//		  suspend;
//		end		
	}
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getLibelleDoc() {
		return libelleDoc;
	}

	public void setLibelleDoc(String libelleDoc) {
		this.libelleDoc = libelleDoc;
	}

	public Date getDateDoc() {
		return dateDoc;
	}

	public void setDateDoc(Date dateDoc) {
		this.dateDoc = dateDoc;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeTiers() {
		return codeTiers;
	}
	public void setCodeTiers(String tiers) {
		this.codeTiers = tiers;
	}
	public String getTypeAdresseFacturation() {
		return typeAdresseFacturation;
	}
	public void setTypeAdresseFacturation(String typeAdresseFacturation) {
		this.typeAdresseFacturation = typeAdresseFacturation;
	}
	public String getTypeAdresseLivraison() {
		return typeAdresseLivraison;
	}
	public void setTypeAdresseLivraison(String typeAdresseLivraison) {
		this.typeAdresseLivraison = typeAdresseLivraison;
	}

	public String getTypePaiement() {
		return typePaiement;
	}

	public void setTypePaiement(String typePaiement) {
		this.typePaiement = typePaiement;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public Boolean getRelationDocument() {
		return relationDocument;
	}

	public void setRelationDocument(Boolean relationDocument) {
		this.relationDocument = relationDocument;
	}

	
}
