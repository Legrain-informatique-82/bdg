package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javassist.util.HotSwapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaLAcompte;
import fr.legrain.documents.dao.TaLApporteur;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.documents.dao.TaLBonliv;
import fr.legrain.documents.dao.TaLDevis;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaLProforma;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaTLigne;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public abstract class AbstractGenereDoc {
	
	static Logger logger = Logger.getLogger(AbstractGenereDoc.class.getName());
	
	protected TaTAdrDAO taTAdrDAO = new TaTAdrDAO();
	
	protected String libelleDoc="";
	protected Date dateDoc;
	protected String commentaire="";
	protected String code="";
	protected String codeTiers="";

	private String typeAdresseFacturation="";
	private String typeAdresseLivraison="";
	
	protected Boolean relationDocument=true;
	protected Boolean ligneSeparatrice=false;
	protected String libelleSeparateurDoc="";
	protected Boolean RecupLibelleSeparateurDoc=false;
//	protected Integer report=0;
//	protected Integer finDeMois=0;
	protected TaCPaiement taCPaiement=new TaCPaiement();
	protected TaTTvaDoc taTTvaDoc=new TaTTvaDoc();
	private EntityManager em=null;



	protected LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	protected LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	protected LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();
	protected LgrDozerMapper<TaTiers,IHMIdentiteTiers> mapperModelToUIIHMIdentiteTiersInfosDocument = new LgrDozerMapper<TaTiers,IHMIdentiteTiers>();

	protected IDocumentTiers ds;
	protected IDocumentTiers dd;

	TaTiersDAO daoTiers = new TaTiersDAO();
//	private static AbstractGenereDoc getGenereDoc(IDocumentTiers ds,Class c) {
//		if(ds instanceof TaFacture && c.)
//		return null;
//		
//	}
	
	/**
	 * Copie un document dans un autre
	 * @param ds - document source
	 * @param dd - document destination
	 * @return - le document destination
	 */
	public IDocumentTiers copieDocument(IDocumentTiers ds, IDocumentTiers dd){
		copieDocumentGeneral(ds,dd);
		copieDocumentSpecifique(ds,dd,getLigneSeparatrice(),getLibelleSeparateurDoc());
		
		if(getLigneSeparatrice()){
			try {
				TaTLigne tl=new TaTLigneDAO().findByCode("C");
				creationNewLigne(tl,"");
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return dd;
	}
	
	public abstract IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,Boolean ligneSeparatrice,String libelleSeparateur);
	
	public IDocumentTiers copieDocumentGeneral(IDocumentTiers ds, IDocumentTiers dd){
		try {
			dd.setTaTiers(ds.getTaTiers());
			dd.setCodeDocument("");
			dd.setDateDocument(ds.getDateDocument());
			dd.setDateLivDocument(ds.getDateLivDocument());
			dd.setLibelleDocument(ds.getLibelleDocument());
			
			int i=0;
			TaTLigne tl=new TaTLigneDAO().findByCode("C");
			ds.calculeTvaEtTotaux();
			for (ILigneDocumentTiers ligne : ds.getLignesGeneral()) {
				//if(i==0)tl=ligne.getTaTLigne();
				if(getLibelleSeparateurDoc()!=null && !getLibelleSeparateurDoc().trim().equals("") && i==0){
					ILigneDocumentTiers ligneSup= creationNewLigne(tl,getLibelleSeparateurDoc());
					i++;
				}
			}
//			if(getLigneSeparatrice()){
//				ILigneDocumentTiers ligneSup= creationNewLigne(tl,"");
//			}

			
		} catch(Exception cnse) {
			logger.error("",cnse);
		}

		return dd;
	}
	
	
	public ILigneDocumentTiers copieLigneDocument(ILigneDocumentTiers ls, ILigneDocumentTiers ld) {
		copieLigneDocumentGeneral(ls,ld);
		copieLigneDocumentSpecifique(ls,ld);
		return ld;
	}
	
	public abstract ILigneDocumentTiers copieLigneDocumentSpecifique(ILigneDocumentTiers ls, ILigneDocumentTiers ld);
	
	public ILigneDocumentTiers copieLigneDocumentGeneral(ILigneDocumentTiers ls, ILigneDocumentTiers ld) {
		try {
			BigDecimal valeur=null;
			BigDecimal htOrigine=null;
			ld.setTaTLigne(ls.getTaTLigne());
			ld.setTaArticle(ls.getTaArticle());
			ld.setNumLigneLDocument(ls.getNumLigneLDocument());
			ld.setLibLDocument(ls.getLibLDocument());
			ld.setQteLDocument(ls.getQteLDocument());
			ld.setQte2LDocument(ls.getQte2LDocument());
			ld.setU1LDocument(ls.getU1LDocument());
			ld.setU2LDocument(ls.getU2LDocument());
			ld.setPrixULDocument(ls.getPrixULDocument());
			ld.setTauxTvaLDocument(ls.getTauxTvaLDocument());
			ld.setCompteLDocument(ls.getCompteLDocument());
			ld.setCodeTvaLDocument(ls.getCodeTvaLDocument());
			ld.setCodeTTvaLDocument(ls.getCodeTTvaLDocument());
			if(ls.getPrixULDocument()!=null && ls.getQteLDocument()!=null)
				htOrigine=ls.getPrixULDocument().multiply(ls.getQteLDocument()).setScale(2,BigDecimal.ROUND_HALF_UP);
			ld.setMtHtLDocument(ls.getMtHtLApresRemiseGlobaleDocument());
			ld.setMtTtcLDocument(ls.getMtTtcLApresRemiseGlobaleDocument());
			//ld.setMtHtLApresRemiseGlobaleDocument(ls.getMtHtLApresRemiseGlobaleDocument());
			//ld.setMtTtcLApresRemiseGlobaleDocument(ls.getMtTtcLApresRemiseGlobaleDocument());
			if(ls.getTaDocument().getTtc()==1){
				if(htOrigine!=null)
					ld.setRemHtLDocument(ls.getMtTtcLApresRemiseGlobaleDocument().subtract(htOrigine).abs());			
				if(htOrigine!=null && !htOrigine.equals(BigDecimal.valueOf(0)))
					valeur=ld.getRemHtLDocument().multiply(BigDecimal.valueOf(100)).divide(
						htOrigine,MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP);
			}else{
				if(htOrigine!=null)
					ld.setRemHtLDocument(ls.getMtHtLApresRemiseGlobaleDocument().subtract(htOrigine).abs());			
				if(htOrigine!=null && !htOrigine.equals(BigDecimal.valueOf(0)))
					valeur=ld.getRemHtLDocument().multiply(BigDecimal.valueOf(100)).divide(
						htOrigine,MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP);
			}
			
			ld.setRemTxLDocument(valeur);
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		return ld;
	}
	
	public IInfosDocumentTiers copieInfosDocument(IInfosDocumentTiers is, IInfosDocumentTiers id) {
		copieInfosDocumentGeneral(is,id);
		copieInfosDocumentSpecifique(is,id);
		return id;
	}
	
	public abstract IInfosDocumentTiers copieInfosDocumentSpecifique(IInfosDocumentTiers is, IInfosDocumentTiers id);
	
	public IInfosDocumentTiers copieInfosDocumentGeneral(IInfosDocumentTiers is, IInfosDocumentTiers id) {
		try {
			id.setAdresse1(is.getAdresse1());
			id.setAdresse2(is.getAdresse2());
			id.setAdresse3(is.getAdresse3());
			id.setCodepostal(is.getCodepostal());
			id.setVille(is.getVille());
			id.setPays(is.getPays());
			id.setAdresse1Liv(is.getAdresse1Liv());
			id.setAdresse2Liv(is.getAdresse2Liv());
			id.setAdresse3Liv(is.getAdresse3Liv());
			id.setCodepostalLiv(is.getCodepostalLiv());
			id.setVilleLiv(is.getVilleLiv());
			id.setPaysLiv(is.getPaysLiv());
			id.setCodeCompta(is.getCodeCompta());
			id.setCompte(is.getCompte());
			id.setNomTiers(is.getNomTiers());
			id.setPrenomTiers(is.getPrenomTiers());
			id.setSurnomTiers(is.getSurnomTiers());
			id.setCodeTCivilite(is.getCodeTCivilite());
			id.setCodeTEntite(is.getCodeTEntite());
			id.setTvaIComCompl(is.getTvaIComCompl());
			id.setCodeCPaiement("888");
			id.setCodeCPaiement(taCPaiement.getCodeCPaiement());
			id.setLibCPaiement(taCPaiement.getLibCPaiement());
			id.setReportCPaiement(taCPaiement.getReportCPaiement());
			id.setFinMoisCPaiement(taCPaiement.getFinMoisCPaiement());
			id.setNomEntreprise(is.getNomEntreprise());
			id.setCodeTTvaDoc(is.getCodeTTvaDoc());
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		return id;
	}
	
	/**
	 * Génère un document à partir d'un autre
	 * @param ds
	 * @param dd
	 * @return
	 */
	public IDocumentTiers genereDocument(IDocumentTiers ds, IDocumentTiers dd, String codeTiers) {
		return genereDocument(ds,dd,codeTiers,true,false);
	}
	
	public void enregistreDocument(IDocumentTiers dd){
		enregistreNouveauDocument(dd);
	}
	/**
	 * Génère un document à partir d'un autre
	 * @param ds
	 * @param dd
	 * @return
	 */
	public IDocumentTiers genereDocument(IDocumentTiers ds, IDocumentTiers dd, String codeTiers , Boolean enregistre , boolean generationModele) {
		this.ds = ds;
		this.dd = dd;
		dd=copieDocument(ds,dd);
		genereDocumentGeneral(ds,dd,codeTiers);
		genereDocumentSpecifique(ds,dd,codeTiers,generationModele);
		dd.calculDateEcheanceAbstract(taCPaiement.getReportCPaiement(),taCPaiement.getFinMoisCPaiement());
		if(dd.getDateLivDocument()==null) dd.setDateLivDocument(dd.getDateDocument());
		if(codeTiers!=null) {
			if(!this.codeTiers.equals(dd.getTaTiers().getCodeTiers())){
				TaTiers tiers = daoTiers.findByCode(this.codeTiers);
				dd.setTaTiers(tiers);
				changementDeTiers(dd,true);
			}
			
			
		}
		if (enregistre) enregistreNouveauDocument(dd);
		return dd;
	}
	
	public abstract IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd, String codeTiers, boolean generationModele);
	
	public IDocumentTiers genereDocumentGeneral(IDocumentTiers ds, IDocumentTiers dd, String codeTiers) {
		if(libelleDoc!=null) {
			dd.setLibelleDocument(libelleDoc);
		}
		if(dateDoc!=null) { //on a une date en parametre
			dd.setDateDocument(dateDoc);
		}
		
		return dd;
	}
	
	public void enregistreNouveauDocument(IDocumentTiers dd) {
		enregistreNouveauDocumentGeneral(dd);
		enregistreNouveauDocumentSpecifique(dd);
	}
	
	public abstract void enregistreNouveauDocumentSpecifique(IDocumentTiers dd);
	
	public void enregistreNouveauDocumentGeneral(IDocumentTiers dd) {
	}
	
//	public void enregistreRelationDocument(IDocumentTiers dd) {
//		enregistreRelationDocumentGeneral(dd);
//		enregistreRelationDocumentSpecifique(dd);
//	}
//	
//	public abstract void enregistreRelationDocumentSpecifique(IDocumentTiers dd);
//	
//	public void enregistreRelationDocumentGeneral(IDocumentTiers dd) {
//	}
	
	/**
	 * Initialise l'infosdocument du document passé en parametre si l'info document est null
	 * @param dd
	 */
	public abstract void initInfosDocument(IDocumentTiers dd);
	
	/**
	 * Initialisation des propriétés de la facture en fonction du codeTiers
	 */
	public  void changementDeTiers(IDocumentTiers dd,boolean changeTiers) {
		boolean leTiersADesAdresseLiv = false;
		boolean leTiersADesAdresseFact = false;
		typeAdresseFacturation=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION);
		typeAdresseLivraison=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV);

		if(dd.getTaTiers()!=null && changeTiers){
			IHMIdentiteTiers ihmidentiteTiers=new IHMIdentiteTiers();
			mapperModelToUIIHMIdentiteTiersInfosDocument.map(dd.getTaTiers(),ihmidentiteTiers);
			mapUIToModelIHMIdentiteTiersVersInfosDoc(ihmidentiteTiers, dd);
			//		taFacture.setTaInfosFactures(new TaInfosFacture());
			//		taFacture.getTaInfosFactures().setTaFacture(taFacture);
			//		taFacture.setTaInfosFactures(taFacture.getTaInfosFactures());

			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseLiv = dd.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le codeTiers a des adresse de ce type
			}
			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseFact = dd.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le codeTiers a des adresse de ce type
			}	
			
			Iterator<TaAdresse> ite = null;
			TaAdresse taAdresseLiv =null;
			IHMAdresseInfosLivraison ihmAdresseInfosLivraison = null;
			if(dd.getTaTiers().getTaAdresses()!=null)ite =  dd.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseLiv) { 
				//ajout des adresse de livraison au modele
				while (ihmAdresseInfosLivraison==null && ite.hasNext()){	
					taAdresseLiv =ite.next();
					if(taAdresseLiv.getTaTAdr()!=null && taAdresseLiv.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
						ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
						mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
						mapUIToModelAdresseLivVersInfosDoc(ihmAdresseInfosLivraison, dd);
					}
				}
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
				mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
				mapUIToModelAdresseLivVersInfosDoc(ihmAdresseInfosLivraison, dd);
				}

			TaAdresse taAdresseFact =null;
			IHMAdresseInfosFacturation ihmAdresseInfosFacturation =null;
			if(dd.getTaTiers().getTaAdresses()!=null)ite =  dd.getTaTiers().getTaAdresses().iterator();
			if(leTiersADesAdresseFact) { 
				while (ihmAdresseInfosFacturation==null && ite.hasNext()){
					taAdresseFact =ite.next();
					if(taAdresseFact.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
						ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
						mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
						mapUIToModelAdresseFactVersInfosDoc(ihmAdresseInfosFacturation, dd);
					}
				}				
			}else if( ite.hasNext()){
				taAdresseLiv =ite.next();
				ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
				mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
				mapUIToModelAdresseFactVersInfosDoc(ihmAdresseInfosFacturation, dd);
			}			
//			Iterator<TaAdresse> ite = null;
//			if(leTiersADesAdresseLiv) { 
//				//ajout des adresse de livraison au modele
//				TaAdresse taAdresseLiv =null;
//				IHMAdresseInfosLivraison ihmAdresseInfosLivraison = null;
//				ite =  dd.getTaTiers().getTaAdresses().iterator();
//				while (ihmAdresseInfosLivraison==null && ite.hasNext()){	
//					taAdresseLiv =ite.next();
//					if(taAdresseLiv.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//						ihmAdresseInfosLivraison = new IHMAdresseInfosLivraison();
//						mapperModelToUIAdresseLivInfosDocument.map(taAdresseLiv, ihmAdresseInfosLivraison);
//						mapUIToModelAdresseLivVersInfosDoc(ihmAdresseInfosLivraison, dd);
//					}
//				}
//			}
//
//			if(leTiersADesAdresseFact) { 
//				TaAdresse taAdresseFact =null;
//				IHMAdresseInfosFacturation ihmAdresseInfosFacturation =null;
//				ite =  dd.getTaTiers().getTaAdresses().iterator();
//				while (ihmAdresseInfosFacturation==null && ite.hasNext()){
//					taAdresseFact =ite.next();
//					if(taAdresseFact.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//						ihmAdresseInfosFacturation = new IHMAdresseInfosFacturation();
//						mapperModelToUIAdresseInfosDocument.map(taAdresseFact, ihmAdresseInfosFacturation);
//						mapUIToModelAdresseFactVersInfosDoc(ihmAdresseInfosFacturation, dd);
//					}
//				}				
//			}
			
			
			if(dd.getTaTiers().getTaCPaiement()==null)
				mapUIToModelCPaiementVersInfosDoc(new IHMInfosCPaiement(), dd);
			else{
				IHMInfosCPaiement ihmInfosCPaiement =new IHMInfosCPaiement();
				mapperModelToUICPaiementInfosDocument.map(dd.getTaTiers().getTaCPaiement(), ihmInfosCPaiement);
				mapUIToModelCPaiementVersInfosDoc(ihmInfosCPaiement, dd);
			}
			
			mapUIToModelDocumentVersInfosDoc(dd);	
		}
	}
	
	public boolean dejaGenere(IDocumentTiers ds) {
		boolean dejaGenere = false;
		String jpql = creationRequeteDejaGenere(ds);
		Query q = new JPABdLgr().getEntityManager().createQuery(jpql);
		List<TaRDocument> l = q.getResultList();
		System.err.println(l.size());
		if(l.size()>0) {
			dejaGenere = true;
		}
		return dejaGenere;
	}
	
	public abstract String creationRequeteDejaGenere(IDocumentTiers ds);
	
	public abstract void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos, IDocumentTiers dd);
	public abstract void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd);
	public abstract void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos, IDocumentTiers dd);
	public abstract void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos, IDocumentTiers dd);

	public abstract void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd);

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

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
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
	public Boolean getRelationDocument() {
		return relationDocument;
	}

	public void setRelationDocument(Boolean relationDocument) {
		this.relationDocument = relationDocument;
	}

	public Boolean getLigneSeparatrice() {
		return ligneSeparatrice;
	}

	public void setLigneSeparatrice(Boolean ligneSeparatrice) {
		this.ligneSeparatrice = ligneSeparatrice;
	}

	public String getLibelleSeparateurDoc() {
		return libelleSeparateurDoc;
	}

	public void setLibelleSeparateurDoc(String libelleSeparateurDoc) {
		this.libelleSeparateurDoc = libelleSeparateurDoc;
	}

	public TaTTvaDoc getTaTTvaDoc() {
		return taTTvaDoc;
	}

	public void setTaTTvaDoc(TaTTvaDoc taTTvaDoc) {
		this.taTTvaDoc = taTTvaDoc;
	}
	
//	public Integer getReport() {
//		return report;
//	}
//
//	public void setReport(Integer report) {
//		this.report = report;
//	}
//
//	public Integer getFinDeMois() {
//		return finDeMois;
//	}
//
//	public void setFinDeMois(Integer finDeMois) {
//		this.finDeMois = finDeMois;
//	}

	public TaCPaiement getTaCPaiement() {
		return taCPaiement;
	}

	public void setTaCPaiement(TaCPaiement taCPaiement) {
		this.taCPaiement = taCPaiement;
	}



	public ILigneDocumentTiers creationNewLigne(TaTLigne tl,String libelle) throws Exception{
		ILigneDocumentTiers lf=null;
		if(dd.getTypeDocument().equals(TaFacture.TYPE_DOC)){
			lf=new TaLFacture();
			((TaLFacture)lf).initialiseLigne(true);
			((TaLFacture)lf).setLibLDocument(libelle);
			((TaLFacture)lf).setTaDocument(((TaFacture)dd));
			((TaLFacture)lf).setTaTLigne(tl);
			((TaFacture)dd).addLigne((TaLFacture)lf);
		}
		if(dd.getTypeDocument().equals(TaAvoir.TYPE_DOC)){
			lf=new TaLAvoir();
			((TaLAvoir)lf).initialiseLigne(true);
			((TaLAvoir)lf).setLibLDocument(libelle);
			((TaLAvoir)lf).setTaDocument(((TaAvoir)dd));
			((TaLAvoir)lf).setTaTLigne(tl);
			((TaAvoir)dd).addLigne((TaLAvoir)lf);
		}
		if(dd.getTypeDocument().equals(TaApporteur.TYPE_DOC)){
			lf=new TaLApporteur();
			((TaLApporteur)lf).initialiseLigne(true);
			((TaLApporteur)lf).setLibLDocument(libelle);
			((TaLApporteur)lf).setTaDocument(((TaApporteur)dd));
			((TaLApporteur)lf).setTaTLigne(tl);
			((TaApporteur)dd).addLigne((TaLApporteur)lf);
		}
		if(dd.getTypeDocument().equals(TaBoncde.TYPE_DOC)){
			lf=new TaLBoncde();
			((TaLBoncde)lf).initialiseLigne(true);
			((TaLBoncde)lf).setLibLDocument(libelle);
			((TaLBoncde)lf).setTaDocument(((TaBoncde)dd));
			((TaLBoncde)lf).setTaTLigne(tl);
			((TaBoncde)dd).addLigne((TaLBoncde)lf);
		}
		if(dd.getTypeDocument().equals(TaBonliv.TYPE_DOC)){
			lf=new TaLBonliv();
			((TaLBonliv)lf).initialiseLigne(true);
			((TaLBonliv)lf).setLibLDocument(libelle);
			((TaLBonliv)lf).setTaDocument(((TaBonliv)dd));
			((TaLBonliv)lf).setTaTLigne(tl);
			((TaBonliv)dd).addLigne((TaLBonliv)lf);
		}
		if(dd.getTypeDocument().equals(TaDevis.TYPE_DOC)){
			lf=new TaLDevis();
			((TaLDevis)lf).initialiseLigne(true);
			((TaLDevis)lf).setLibLDocument(libelle);
			((TaLDevis)lf).setTaDocument(((TaDevis)dd));
			((TaLDevis)lf).setTaTLigne(tl);
			((TaDevis)dd).addLigne((TaLDevis)lf);
		}
		if(dd.getTypeDocument().equals(TaAcompte.TYPE_DOC)){
			lf=new TaLAcompte();
			((TaLAcompte)lf).initialiseLigne(true);
			((TaLAcompte)lf).setLibLDocument(libelle);
			((TaLAcompte)lf).setTaDocument(((TaAcompte)dd));
			((TaLAcompte)lf).setTaTLigne(tl);
			((TaAcompte)dd).addLigne((TaLAcompte)lf);
		}
		if(dd.getTypeDocument().equals(TaPrelevement.TYPE_DOC)){
			lf=new TaLPrelevement();
			((TaLPrelevement)lf).initialiseLigne(true);
			((TaLPrelevement)lf).setLibLDocument(libelle);
			((TaLPrelevement)lf).setTaDocument(((TaPrelevement)dd));
			((TaLPrelevement)lf).setTaTLigne(tl);
			((TaPrelevement)dd).addLigne((TaLPrelevement)lf);
		}
		if(dd.getTypeDocument().equals(TaProforma.TYPE_DOC)){
			lf=new TaLProforma();
			((TaLProforma)lf).initialiseLigne(true);
			((TaLProforma)lf).setLibLDocument(libelle);
			((TaLProforma)lf).setTaDocument(((TaProforma)dd));
			((TaLProforma)lf).setTaTLigne(tl);
			((TaProforma)dd).addLigne((TaLProforma)lf);
		}
		return lf;
	}

	public Boolean getRecupLibelleSeparateurDoc() {
		return RecupLibelleSeparateurDoc;
	}

	public void setRecupLibelleSeparateurDoc(Boolean recupLibelleSeparateurDoc) {
		RecupLibelleSeparateurDoc = recupLibelleSeparateurDoc;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


}
