package fr.legrain.visualisation.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import fr.legrain.lib.data.ModelObject;

public class Resultat extends ModelObject {
	//resultat d'un requete JPA de visu
	//a completer
/*

CREATE TABLE TA_VISUALISATION(
  ID DID3 NOT NULL,
  CODE_REQUETE DLIB255NN,
  REQUETE DLIB_COMMENTAIRE,
  CHAMPS DLIB_COMMENTAIRE,
  TITRE_CHAMPS DLIB_COMMENTAIRE,
  TAILLE_CHAMPS DLIB255,
  MODULE DLIB255,
  IMPRESSION DLIB255,
  IDENTIFIANT DLIB255NN,
  ID_EDITEUR DLIB255NN,
  ID_PLUGIN DLIB255NN,
  CLAUSE_WHERE DLIB_COMMENTAIRE,
  GROUPBY DLIB_COMMENTAIRE,
  CLAUSE_HAVING DLIB_COMMENTAIRE,
  PROC DBOOL DEFAULT 0,
  PARAM DLIB255,
  TYPES_RETOUR DLIB_COMMENTAIRE,
  TOTAUX DLIB255,
  PRIMARY KEY (ID)
);

INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX) VALUES ('5', 'Liste des articles JPA', 'select a.codeArticle,a.libellecArticle,a.numcptArticle,a.diversArticle,a.commentaireArticle,a.stockMinArticle,
a.taPrix.prixPrix,a.taPrix.taUnite.codeUnite,a.taFamille.codeFamille,a.taTva.codeTva,a.taTva.tauxTva,a.taTva.numcptTva, a.taTTva.codeTTva from TaArticle a', 'a.codeArticle;a.libellecArticle;a.numcptArticle;a.diversArticle;a.commentaireArticle;a.stockMinArticle;
a.taPrix.prixPrix;a.taPrix.taUnite.codeUnite;a.taFamille.codeFamille;a.taTva.codeTva;a.taTva.tauxTva;a.taTva.numcptTva;a.taTTva.codeTTva', 'Code;Libelle;Compte;Divers;Commentaire;Stock mini;Prix;Unite;Famille;Code TVA;Taux TVA;Compte TVA;Type TVA', '50;100;50;100;100;20;50;20;50;50;50;50;50', 'article', '/report/article.rptdesign', 'a.codeArticle', 'fr.legrain.articles.editor.EditorArticle', 'Articles', NULL, NULL, NULL, '0', NULL, 's;s;s;s;s;i;i;s;s;s;f;i;s', NULL);
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX) VALUES ('6', 'Chiffre d affaire JPA', 'select ta.codeArticle,sum(tlf.mtHtLDocument) as ht,sum(tlf.mtTtcLDocument) as ttc from TaLFacture tlf left join tlf.taArticle ta left join tlf.taDocument tf', 'ta.codeArticle;sum(tlf.mtHtLDocument)!having;sum(tlf.mtTtcLDocument)!having', 'Code;Montant HT;Montant TTC', '100;100;100', 'article', '/report/article.rptdesign', 'ta.codeArticle', 'fr.legrain.articles.editor.EditorArticle', 'Articles', 'where ta.codeArticle is not null and tf.dateDocument between (select tie.datedebInfoEntreprise from TaInfoEntreprise tie) and (select tie.datefinInfoEntreprise from TaInfoEntreprise tie)', 'group by ta.codeArticle', NULL, '0', NULL, 's;f;f', '1;2');
INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX) VALUES ('7', 'Extraction tiers JPA', 'select t.nomTiers,t.prenomTiers,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse,  a.codepostalAdresse, a.villeAdresse, a.paysAdresse,substring(a.codepostalAdresse,1,2),tc.codeTCivilite,t.surnomTiers, t.codeTiers,e.nomEntreprise,t.actifTiers,t.ttcTiers,tt.codeTTiers,  tt.libelleTTiers,c.commentaire,te.codeTEntite, co.tvaIComCompl,  co.siretCompl,tel.numeroTelephone,mail.adresseEmail,w.adresseWeb,t.quandCreeTiers,max(f.dateDocument) from fr.legrain.tiers.dao.TaTiers t  left join t.taAdresse a left join t.taTCivilite tc left join t.taEntreprise e left join t.taTTiers tt left join t.taCommentaire c left join t.taTEntite te left join t.taCompl co left join t.taTelephone tel left join t.taEmail mail left join t.taWeb w,TaFacture f left join f.taTiers tf where tf.idTiers=t.idTiers', 't.nomTiers;t.prenomTiers;a.adresse1Adresse;a.adresse2Adresse;a.adresse3Adresse;a.codepostalAdresse;a.villeAdresse;a.paysAdresse;substring(a.codepostalAdresse,1,2);tc.codeTCivilite;t.surnomTiers;t.codeTiers;e.nomEntreprise;t.actifTiers;t.ttcTiers;tt.codeTTiers;tt.libelleTTiers;c.commentaire;te.codeTEntite;co.tvaIComCompl;co.siretCompl;tel.numeroTelephone;mail.adresseEmail;w.adresseWeb;t.quandCreeTiers;max(f.dateDocument)!having;max(bonliv.dateDocument)!having;max(devis.dateDocument)!having', 'Nom;Prénom;Adresse 1;Adresse 2;Adresse 3;Code poastal;Ville;Pays;Département;Civilité;Surnom;Code tiers;Nom entreprise;Actif;TTC;Type tiers;Libellé type;Commentaire;Type entité;Tva intra;Siret;Tel;Email;Web;Date création;Date dernière facture;Date dernier bonliv;Date dernier devis', '50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50', 'tiers', '/report/extraction_Tiers.rptdesign', 't.codeTiers', 'fr.legrain.tiers.editor.EditorTiers', 'Tiers', NULL, 'group by t.nomTiers,t.prenomTiers,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse, a.codepostalAdresse, a.villeAdresse, a.paysAdresse, substring(a.codepostalAdresse,1,2), tc.codeTCivilite, t.surnomTiers, t.codeTiers, e.nomEntreprise, t.actifTiers, t.ttcTiers, tt.codeTTiers, tt.libelleTTiers, c.commentaire, te.codeTEntite, co.tvaIComCompl, co.siretCompl, tel.numeroTelephone, mail.adresseEmail, w.adresseWeb, t.quandCreeTiers', NULL, '0', NULL, 's;s;s;s;s;s;s;s;i;s;s;s;s;b;b;s;s;s;s;s;s;s;s;s;d;d;d;d', NULL);

*/
	static Logger logger = Logger.getLogger(Resultat.class.getName());
	
	private String valeur1;
	private String valeur2;
	private String valeur3;
	private String valeur4;
	private String valeur5;
	private String valeur6;
	private String valeur7;
	private String valeur8;
	private String valeur9;
	private String valeur10;
	private String valeur11;
	private String valeur12;
	private String valeur13;
	private String valeur14;
	private String valeur15;
	private String valeur16;
	private String valeur17;
	private String valeur18;
	private String valeur19;
	private String valeur20;
	private String valeur21;
	private String valeur22;
	private String valeur23;
	private String valeur24;
	private String valeur25;
	private String valeur26;
	private String valeur27;
	private String valeur28;
	private String valeur29;
	private String valeur30;
	
	public static final String debutNomChamp = "valeur";
	
	public static final int nbValeurMax = 30;
	
	

	
	public Resultat() {
		for (int i = 0; i < nbValeurMax; i++) {
			try {
				PropertyUtils.setProperty(this, Resultat.debutNomChamp+(i+1), "");
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

	}

	public Resultat(String defaut) {
		for (int i = 0; i < nbValeurMax; i++) {
			try {
				PropertyUtils.setProperty(this, Resultat.debutNomChamp+(i+1), defaut);
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}
	}

	
	public Resultat(String[] valeurs) {
		if(valeurs.length<=nbValeurMax) {
			for (int i = 0; i < valeurs.length; i++) {
				try {
					PropertyUtils.setProperty(this, Resultat.debutNomChamp+(i+1), valeurs[i]);
				} catch (IllegalAccessException e) {
					logger.error("",e);
				} catch (InvocationTargetException e) {
					logger.error("",e);
				} catch (NoSuchMethodException e) {
					logger.error("",e);
				}
			}
		} else {
			logger.error("Le nombre d'element dans le tableau est superieur au nombre de valeur possible");
		}
	}
	
	/**
	 * 
	 * @param position - position de la valeur à modifier, la numérotation commence à 1
	 * @param newValue - nouvelle valeur
	 */
	public void changeValue(int position, String newValue) {
		try {
			PropertyUtils.setProperty(this, Resultat.debutNomChamp+(position), newValue);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 
	 * @param position - position de la valeur à modifier, la numérotation commence à 1
	 * @param newValue - nouvelle valeur
	 */
	public String findValue(int position) {
		String retour = null;
		try {
			retour = (String)PropertyUtils.getProperty(this, Resultat.debutNomChamp+(position));
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return retour;
	}

	public String getValeur1() {
		return valeur1;
	}
	public void setValeur1(String valeur1) {
		this.valeur1 = valeur1;
	}
	public String getValeur2() {
		return valeur2;
	}
	public void setValeur2(String valeur2) {
		this.valeur2 = valeur2;
	}
	public String getValeur3() {
		return valeur3;
	}
	public void setValeur3(String valeur3) {
		this.valeur3 = valeur3;
	}
	public String getValeur4() {
		return valeur4;
	}
	public void setValeur4(String valeur4) {
		this.valeur4 = valeur4;
	}
	public String getValeur5() {
		return valeur5;
	}
	public void setValeur5(String valeur5) {
		this.valeur5 = valeur5;
	}
	public String getValeur6() {
		return valeur6;
	}
	public void setValeur6(String valeur6) {
		this.valeur6 = valeur6;
	}
	public String getValeur7() {
		return valeur7;
	}
	public void setValeur7(String valeur7) {
		this.valeur7 = valeur7;
	}
	public String getValeur8() {
		return valeur8;
	}
	public void setValeur8(String valeur8) {
		this.valeur8 = valeur8;
	}
	public String getValeur9() {
		return valeur9;
	}
	public void setValeur9(String valeur9) {
		this.valeur9 = valeur9;
	}
	public String getValeur10() {
		return valeur10;
	}
	public void setValeur10(String valeur10) {
		this.valeur10 = valeur10;
	}
	public String getValeur11() {
		return valeur11;
	}
	public void setValeur11(String valeur11) {
		this.valeur11 = valeur11;
	}
	public String getValeur12() {
		return valeur12;
	}
	public void setValeur12(String valeur12) {
		this.valeur12 = valeur12;
	}
	public String getValeur13() {
		return valeur13;
	}
	public void setValeur13(String valeur13) {
		this.valeur13 = valeur13;
	}
	public String getValeur14() {
		return valeur14;
	}
	public void setValeur14(String valeur14) {
		this.valeur14 = valeur14;
	}
	public String getValeur15() {
		return valeur15;
	}
	public void setValeur15(String valeur15) {
		this.valeur15 = valeur15;
	}
	public String getValeur16() {
		return valeur16;
	}
	public void setValeur16(String valeur16) {
		this.valeur16 = valeur16;
	}
	public String getValeur17() {
		return valeur17;
	}
	public void setValeur17(String valeur17) {
		this.valeur17 = valeur17;
	}
	public String getValeur18() {
		return valeur18;
	}
	public void setValeur18(String valeur18) {
		this.valeur18 = valeur18;
	}
	public String getValeur19() {
		return valeur19;
	}
	public void setValeur19(String valeur19) {
		this.valeur19 = valeur19;
	}
	public String getValeur20() {
		return valeur20;
	}
	public void setValeur20(String valeur20) {
		this.valeur20 = valeur20;
	}
	public String getValeur21() {
		return valeur21;
	}
	public void setValeur21(String valeur21) {
		this.valeur21 = valeur21;
	}
	public String getValeur22() {
		return valeur22;
	}
	public void setValeur22(String valeur22) {
		this.valeur22 = valeur22;
	}
	public String getValeur23() {
		return valeur23;
	}
	public void setValeur23(String valeur23) {
		this.valeur23 = valeur23;
	}
	public String getValeur24() {
		return valeur24;
	}
	public void setValeur24(String valeur24) {
		this.valeur24 = valeur24;
	}
	public String getValeur25() {
		return valeur25;
	}
	public void setValeur25(String valeur25) {
		this.valeur25 = valeur25;
	}
	public String getValeur26() {
		return valeur26;
	}
	public void setValeur26(String valeur26) {
		this.valeur26 = valeur26;
	}
	public String getValeur27() {
		return valeur27;
	}
	public void setValeur27(String valeur27) {
		this.valeur27 = valeur27;
	}
	public String getValeur28() {
		return valeur28;
	}
	public void setValeur28(String valeur28) {
		this.valeur28 = valeur28;
	}
	public String getValeur29() {
		return valeur29;
	}
	public void setValeur29(String valeur29) {
		this.valeur29 = valeur29;
	}
	public String getValeur30() {
		return valeur30;
	}
	public void setValeur30(String valeur30) {
		this.valeur30 = valeur30;
	}
	public static String getDebutNomChamp() {
		return debutNomChamp;
	}

}
