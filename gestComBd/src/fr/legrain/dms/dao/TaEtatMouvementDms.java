package fr.legrain.dms.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class TaEtatMouvementDms   implements ITaEtatDms, java.io.Serializable {

	private Date   dateDeb;
	private Date   dateFin;
	private String codeTiers;
	private String codeTiersFin;

	private String nomTiers;
	private String codeDocument;
	private Date dateDocument;
	private String codeFamilleTiers;
	private String codeFamilleTiersFin;
	private String libcFamilleTiers;

	private String codeArticle;
	private String codeArticleFin;
	private String libellecArticle;
	private String codeFamille;
	private String codeFamilleFin;
	private String libcFamille;
	private String un1;
	private String un2;

	private String codeTauxTVA;
	private boolean localisationTVAFr;
	private boolean localisationTVAUE;
	private boolean localisationTVAHUE;
	private boolean localisationTVAFranchise;
	private boolean localisationSansTVA;
	
	private String mouvement;

	private Integer mois;
	private Integer annee;

	private BigDecimal qte1 = new BigDecimal(0);
	private BigDecimal qte1Report = new BigDecimal(0);
	private BigDecimal qte1Fin = new BigDecimal(0);
	private BigDecimal qte2 = new BigDecimal(0);
	private BigDecimal qte2Report = new BigDecimal(0);
	private BigDecimal qte2Fin = new BigDecimal(0);

	private BigDecimal ht;
	private BigDecimal tva;
	private BigDecimal ttc;
	
	private BigDecimal hauteur;
	private BigDecimal largeur;
	private BigDecimal poids;	
	
	private BigDecimal droitsAccises;	
	

	private Boolean exclusionQteSsUnite = false;
	private Boolean travailSurDateLivraison = false;



	public TaEtatMouvementDms() {}

	//sert pour le calcul complet de la DMS
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeDocument,
			Date dateDocument, String codeArticle, String codeFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.codeFamille=codeFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;
	}
	
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeDocument,
			Date dateDocument, String codeArticle, String codeFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.codeFamille=codeFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;

	}

	
	//sert pour le calcul complet de la DMS ===> avec libelle court article + rejout hauteur, largeur , poids (pour dossier Domaine Pascaud)
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeFamilleTiers, String libcFamilleTiers, String codeDocument,
			Date dateDocument, String codeArticle, String libellecArticle,String codeFamille, String libcFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeFamilleTiers = codeFamilleTiers;
		this.libcFamilleTiers = libcFamilleTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.libellecArticle= libellecArticle;
		this.codeFamille=codeFamille;
		this.libcFamille=libcFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;
	}
	
	
	//sert pour le calcul complet de la DMS ===> avec libelle court article + rejout hauteur, largeur , poids (pour dossier Domaine Pascaud)
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeFamilleTiers, String libcFamilleTiers, String codeDocument,
			Date dateDocument, String codeArticle, String libellecArticle,String codeFamille, String libcFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeFamilleTiers = codeFamilleTiers;
		this.libcFamilleTiers = libcFamilleTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.libellecArticle= libellecArticle;
		this.codeFamille=codeFamille;
		this.libcFamille=libcFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
	}

	//sert pour le calcul complet de la DMS ===> avec libelle court tiers
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeDocument,
			Date dateDocument, String codeArticle, String libellecArticle,String codeFamille, String libcFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.libellecArticle= libellecArticle;
		this.codeFamille=codeFamille;
		this.libcFamille=libcFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;
		
	}
	//sert pour le calcul complet de la DMS ===> avec libelle court tiers
	public TaEtatMouvementDms(String codeTiers, String nomTiers, String codeDocument,
			Date dateDocument, String codeArticle, String libellecArticle,String codeFamille, String libcFamille,
			String mouvement, Integer mois, Integer annee, BigDecimal qte1,
			String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc) {
		super();
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeDocument = codeDocument;
		this.dateDocument = dateDocument;
		this.codeArticle= codeArticle;
		this.libellecArticle= libellecArticle;
		this.codeFamille=codeFamille;
		this.libcFamille=libcFamille;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;
		this.qte1 = qte1;
		this.un1  = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		
	}

	//sert pour le calcul simplifié de la DMS (sans montants )
	public TaEtatMouvementDms(String codeFamille,String codeArticle, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeFamille = codeFamille;
		this.codeArticle = codeArticle;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;		
	}

	//sert pour le calcul simplifié de la DMS (sans montants )
	public TaEtatMouvementDms(String codeFamille,String codeArticle, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc) {
		super();
		this.codeFamille = codeFamille;
		this.codeArticle = codeArticle;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;	
	}
	//sert pour le calcul simplifié de la DMS (sans montants )  ===> avec libelle court article
	public TaEtatMouvementDms(String codeFamille, String libcFamille, String codeArticle, String libellecArticle, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeFamille = codeFamille;
		this.libcFamille = libcFamille;
		this.codeArticle = codeArticle;
		this.libellecArticle = libellecArticle;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;
	}
	
	//sert pour le calcul simplifié de la DMS (sans montants )  ===> avec libelle court article
	public TaEtatMouvementDms(String codeFamille, String libcFamille, String codeArticle, String libellecArticle, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc) {
		super();
		this.codeFamille = codeFamille;
		this.libcFamille = libcFamille;
		this.codeArticle = codeArticle;
		this.libellecArticle = libellecArticle;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
	}

	//sert pour le calcul simplifié de la DMS (sans montants )  ===> avec libelle court tiers
	public TaEtatMouvementDms(String codeFamille, String libcFamille, String codeArticle, String libellecArticle,
			String codeFamilleTiers, String libcFamilleTiers, String codeTiers, String nomTiers, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc,BigDecimal hauteur,BigDecimal largeur,BigDecimal poids) {
		super();
		this.codeFamille = codeFamille;
		this.libcFamille = libcFamille;
		this.codeArticle = codeArticle;
		this.libellecArticle = libellecArticle;
		this.codeFamilleTiers = codeFamilleTiers;
		this.libcFamilleTiers = libcFamilleTiers;
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.poids = poids;
	}

	//sert pour le calcul simplifié de la DMS (sans montants )  ===> avec libelle court tiers
	public TaEtatMouvementDms(String codeFamille, String libcFamille, String codeArticle, String libellecArticle,
			String codeFamilleTiers, String libcFamilleTiers, String codeTiers, String nomTiers, String mouvement, 
			Integer mois, Integer annee,
			BigDecimal qte1, String un1, BigDecimal qte2, String un2, BigDecimal ht,
			BigDecimal tva, BigDecimal ttc) {
		super();
		this.codeFamille = codeFamille;
		this.libcFamille = libcFamille;
		this.codeArticle = codeArticle;
		this.libellecArticle = libellecArticle;
		this.codeFamilleTiers = codeFamilleTiers;
		this.libcFamilleTiers = libcFamilleTiers;
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.mouvement = mouvement;
		this.mois = mois;
		this.annee = annee;		
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
		this.ht = ht;
		this.tva = tva;
		this.ttc = ttc;
	}
	//sert pour le calcul simplifié de la DMS (sans montants )
	public TaEtatMouvementDms(String codeFamille,String codeArticle, String mouvement, 
			BigDecimal qte1, String un1, BigDecimal qte2, String un2) {
		super();
		this.codeFamille = codeFamille;
		this.codeArticle = codeArticle;
		this.mouvement = mouvement;	
		this.qte1 = qte1;
		this.un1 = un1;
		this.qte2 = qte2;
		this.un2 = un2;
	}

	public BigDecimal getQte1() {
		return qte1;
	}

	public void setQte1(BigDecimal qte1) {
		this.qte1 = qte1;
	}

	public BigDecimal getQte2() {
		return qte2;
	}

	public void setQte2(BigDecimal qte2) {
		this.qte2 = qte2;
	}

	public BigDecimal getHt() {
		return ht;
	}

	public void setHt(BigDecimal ht) {
		this.ht = ht;
	}

	public BigDecimal getTva() {
		return tva;
	}

	public void setTva(BigDecimal tva) {
		this.tva = tva;
	}

	public BigDecimal getTtc() {
		return ttc;
	}

	public void setTtc(BigDecimal ttc) {
		this.ttc = ttc;
	}

	public String getMouvement() {
		return mouvement;
	}

	public void setMouvement(String mouvement) {
		this.mouvement = mouvement;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public Date getDateDocument() {
		return dateDocument;
	}

	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}

	public BigDecimal getQte1Report() {
		return qte1Report;
	}

	public void setQte1Report(BigDecimal qte1Report) {
		this.qte1Report = qte1Report;
	}

	public BigDecimal getQte1Fin() {
		return qte1Fin;
	}

	public void setQte1Fin(BigDecimal qte1Fin) {
		this.qte1Fin = qte1Fin;
	}

	public BigDecimal getQte2Report() {
		return qte2Report;
	}

	public void setQte2Report(BigDecimal qte2Report) {
		this.qte2Report = qte2Report;
	}

	public BigDecimal getQte2Fin() {
		return qte2Fin;
	}

	public void setQte2Fin(BigDecimal qte2Fin) {
		this.qte2Fin = qte2Fin;
	}

	@Override
	public Integer getAnnee() {
		return this.annee;
	}

	@Override
	public String getCodeArticle() {
		return this.codeArticle;
	}

	@Override
	public String getCodeFamille() {
		return this.codeFamille;
	}

	@Override
	public Integer getMois() {
		return this.mois;
	}

	@Override
	public String getUn1() {
		return this.un1;
	}

	@Override
	public String getUn2() {
		return this.un2;
	}

	@Override
	public void setAnnee(Integer annee) {
		this.annee=annee;
	}

	@Override
	public void setCodeArticle(String codeArticle) {
		this.codeArticle=codeArticle;
	}

	@Override
	public void setCodeFamille(String codeFamille) {
		this.codeFamille=codeFamille;
	}

	@Override
	public void setMois(Integer mois) {
		this.mois=mois;
	}

	@Override
	public void setUn1(String un1) {
		this.un1=un1;
	}

	@Override
	public void setUn2(String un2) {
		this.un2=un2;	
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Boolean getExclusionQteSsUnite() {
		return exclusionQteSsUnite;
	}

	public void setExclusionQteSsUnite(Boolean exclusionQteSsUnite) {
		this.exclusionQteSsUnite = exclusionQteSsUnite;
	}

	public String getLibellecArticle() {
		return libellecArticle;
	}

	public void setLibellecArticle(String nomArticle) {
		this.libellecArticle = nomArticle;
	}

	public String getLibcFamille() {
		return libcFamille;
	}

	public void setLibcFamille(String libcFamille) {
		this.libcFamille = libcFamille;
	}

	public String getCodeFamilleTiers() {
		return codeFamilleTiers;
	}

	public void setCodeFamilleTiers(String codeFamilleTiers) {
		this.codeFamilleTiers = codeFamilleTiers;
	}
	
	public String getCodeFamilleTiersFin() {
		return codeFamilleTiersFin;
	}

	public void setCodeFamilleTiersFin(String codeFamilleTiersFin) {
		this.codeFamilleTiersFin = codeFamilleTiersFin;
	}

	public String getLibcFamilleTiers() {
		return libcFamilleTiers;
	}

	public void setLibcFamilleTiers(String libcFamilleTiers) {
		this.libcFamilleTiers = libcFamilleTiers;
	}

	public String getCodeArticleFin() {
		return codeArticleFin;
	}

	public void setCodeArticleFin(String codeArticleFin) {
		this.codeArticleFin = codeArticleFin;
	}

	public String getCodeFamilleFin() {
		return codeFamilleFin;
	}

	public void setCodeFamilleFin(String codeFamilleFin) {
		this.codeFamilleFin = codeFamilleFin;
	}

	public String getCodeTauxTVA() {
		return codeTauxTVA;
	}

	public void setCodeTauxTVA(String codeTauxTVA) {
		this.codeTauxTVA = codeTauxTVA;
	}

	public boolean isLocalisationTVAFr() {
		return localisationTVAFr;
	}

	public void setLocalisationTVAFr(boolean localisationTVAFr) {
		this.localisationTVAFr = localisationTVAFr;
	}

	public boolean isLocalisationTVAUE() {
		return localisationTVAUE;
	}

	public void setLocalisationTVAUE(boolean localisationTVAUE) {
		this.localisationTVAUE = localisationTVAUE;
	}

	public boolean isLocalisationTVAHUE() {
		return localisationTVAHUE;
	}

	public void setLocalisationTVAHUE(boolean localisationTVAHUE) {
		this.localisationTVAHUE = localisationTVAHUE;
	}

	public boolean isLocalisationTVAFranchise() {
		return localisationTVAFranchise;
	}

	public void setLocalisationTVAFranchise(boolean localisationTVAFranchise) {
		this.localisationTVAFranchise = localisationTVAFranchise;
	}
	
	public String getCodeTiersFin() {
		return codeTiersFin;
	}

	public void setCodeTiersFin(String codeTiersFin) {
		this.codeTiersFin = codeTiersFin;
	}

	public Boolean getTravailSurDateLivraison() {
		return travailSurDateLivraison;
	}

	public void setTravailSurDateLivraison(Boolean travailSurDateLivraison) {
		this.travailSurDateLivraison = travailSurDateLivraison;
	}
	
	public int existeTiersEtArticleUnite(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielTiersEtArticle(taEtat))
				return i;
			i++;
		}
		return -1;
	}
	
	public int existeArticleUnite(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielArticle(taEtat))
				return i;
			i++;
		}
		return -1;
	}

	public int existeTiers(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielTiers(taEtat))
				return i;
			i++;
		}
		return -1;
	}

	public int existeFamilleUnite(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielFamille(taEtat))
				return i;
			i++;
		}
		return -1;
	}
	
	public int existeFamilleUniteArticle(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielFamilleEtArticle(taEtat))
				return i;
			i++;
		}
		return -1;
	}

	public int existeFamilleTiers(ITaEtatDms taEtat,List<TaEtatMouvementDms>  listeEtatFinal){
		int i =0;
		for (Object e : listeEtatFinal) {
			if(((TaEtatMouvementDms)e).equalsPartielFamilleTiers(taEtat))
				return i;
			i++;
		}
		return -1;
	}

	public boolean equalsPartiel(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamille() == null) {
			if (other.getCodeFamille() != null)
				return false;
		} else if (!getCodeFamille().equals(other.getCodeFamille()))
			return false;
		if (getCodeArticle() == null) {
			if (other.getCodeArticle() != null)
				return false;
		} else if (!getCodeArticle().equals(other.getCodeArticle()))
			return false;		
		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}

	public boolean equalsPartielFamille(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamille() == null) {
			if (other.getCodeFamille() != null)
				return false;
		} else if (!getCodeFamille().equals(other.getCodeFamille()))
			return false;
		//		if (getCodeArticle() == null) {
		//			if (other.getCodeArticle() != null)
		//				return false;
		//		} else if (!getCodeArticle().equals(other.getCodeArticle()))
		//			return false;		
		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}
	
	public boolean equalsPartielFamilleEtArticle(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamille() == null) {
			if (other.getCodeFamille() != null)
				return false;
		} else if (!getCodeFamille().equals(other.getCodeFamille()))
			return false;
				if (getCodeArticle() == null) {
					if (other.getCodeArticle() != null)
						return false;
				} else if (!getCodeArticle().equals(other.getCodeArticle()))
					return false;		
		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}

	public boolean equalsPartielFamilleTiers(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamilleTiers() == null) {
			if (other.getCodeFamilleTiers() != null)
				return false;
		} else if (!getCodeFamilleTiers().equals(other.getCodeFamilleTiers()))
			return false;

		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}
	
	public boolean equalsPartielTiersEtArticle(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamille() == null) {
			if (other.getCodeFamille() != null)
				return false;
		}
		//		else if (!getCodeFamille().equals(other.getCodeFamille()))
		//			return false;
		if (getCodeTiers() == null) {
			if (other.getCodeTiers() != null)
				return false;
		} else if (!getCodeTiers().equals(other.getCodeTiers()))
			return false;	
		
		if (getCodeArticle() == null) {
			if (other.getCodeArticle() != null)
				return false;
		} else if (!getCodeArticle().equals(other.getCodeArticle()))
			return false;		
		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}

	public boolean equalsPartielArticle(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;
		if (getCodeFamille() == null) {
			if (other.getCodeFamille() != null)
				return false;
		}
		//		else if (!getCodeFamille().equals(other.getCodeFamille()))
		//			return false;
		if (getCodeArticle() == null) {
			if (other.getCodeArticle() != null)
				return false;
		} else if (!getCodeArticle().equals(other.getCodeArticle()))
			return false;		
		if (getUn1() == null || getUn1().equals("")) {
			if (other.getUn1() == null || other.getUn1().equals(""))
				return true;
			if (other.getUn1() != null)
				return false;
		} else if (!getUn1().equals(other.getUn1()))
			return false;
		if (getUn2() == null||getUn2() .equals("")) {
			if (other.getUn2() == null || other.getUn2().equals(""))
				return true;
			if (other.getUn2() != null ) 
				return false;
		} else if (!getUn2().equals(other.getUn2()))
			return false;
		return true;
	}

	public boolean equalsPartielTiers(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaEtatMouvementDms other = (TaEtatMouvementDms) obj;

		if (getCodeTiers() == null) {
			if (other.getCodeTiers() != null)
				return false;
		} else if (!getCodeTiers().equals(other.getCodeTiers()))
			return false;	
		
//		if (getCodeArticle() == null) {
//			if (other.getCodeArticle() != null)
//				return false;
//		} else if (!getCodeArticle().equals(other.getCodeArticle()))
//			return false;	

		return true;
	}

	public String toStringEnteteSQL() {
		String result = "";

		result += "dateDeb;";
		result += "dateFin;";

		result += "codeDocument;";
		result += "dateDocument;";

		result += "codeTiers;";
		result += "nomTiers;";

		result += "codeFamilleTiers;";
		result += "libcFamilleTiers;";

		result += "codeArticle;";
		result += "codeArticleFin;";
		result += "libellecArticle;";
		result += "codeFamille;";
		result += "codeFamilleFin;";
		result += "libcFamille;";
		result += "un1;";
		result += "un2;";

		result += "codeTauxTVA;";
		result += "localisationTVAFr;";
		result += "localisationTVAUE;";
		result += "localisationTVAHUE;";
		result += "localisationTVAFranchise;";
		result += "mouvement;";
		result += "mois;";
		result += "annee;";

		result += "qte1;";
		result += "qte1Report;";
		result += "qte1Fin;";
		result += "qte2;";
		result += "qte2Report;";
		result += "qte2Fin;";
		result += "ht;";
		result += "tva;";
		result += "ttc;";

		result += "exclusionQteSsUnite;";

		return result;
	}
	
	public String toStringSQL() {
		String result = "";

		result += dateDeb+";";
		result += dateFin+";";

		result += codeDocument+";";
		result += dateDocument+";";

		result += codeTiers+";";
		result += nomTiers+";";

		result += codeFamilleTiers+";";
		result += libcFamilleTiers+";";

		result += codeArticle+";";
		result += codeArticleFin+";";
		result += libellecArticle+";";
		result += codeFamille+";";
		result += codeFamilleFin+";";
		result += libcFamille+";";
		result += un1+";";
		result += un2+";";

		result += codeTauxTVA+";";
		result += localisationTVAFr+";";
		result += localisationTVAUE+";";
		result += localisationTVAHUE+";";
		result += localisationTVAFranchise+";";
		result += mouvement+";";
		result += mois+";";
		result += annee+";";

		result += qte1+";";
		result += qte1Report+";";
		result += qte1Fin+";";
		result += qte2+";";
		result += qte2Report+";";
		result += qte2Fin+";";
		result += ht+";";
		result += tva+";";
		result += ttc+";";

		result += exclusionQteSsUnite+";";

		return result;
	}

	public BigDecimal getHauteur() {
		return hauteur;
	}

	public void setHauteur(BigDecimal hauteur) {
		this.hauteur = hauteur;
	}

	public BigDecimal getLargeur() {
		return largeur;
	}

	public void setLargeur(BigDecimal largeur) {
		this.largeur = largeur;
	}

	public BigDecimal getPoids() {
		return poids;
	}

	public void setPoids(BigDecimal poids) {
		this.poids = poids;
	}

	public BigDecimal getDroitsAccises() {
		return droitsAccises;
	}

	public void setDroitsAccises(BigDecimal droitsAccises) {
		this.droitsAccises = droitsAccises;
	}

	public boolean isLocalisationSansTVA() {
		return localisationSansTVA;
	}

	public void setLocalisationSansTVA(boolean localisationSansTVA) {
		this.localisationSansTVA = localisationSansTVA;
	}
}
