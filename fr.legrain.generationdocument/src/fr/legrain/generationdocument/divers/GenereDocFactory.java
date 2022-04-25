package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;

public class GenereDocFactory {
	
	public static AbstractGenereDoc create(IDocumentTiers src, IDocumentTiers dest) {
		AbstractGenereDoc genereDoc = null;
		if(src instanceof TaAcompte) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocAcompteVersFacture();
			} else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocAcompteVersBonLivraison();
			} else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocAcompteVersProforma();
			}else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocAcompteVersBonCommande();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocAcompteVersDevis();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocAcompteVersAvoir();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocAcompteVersApporteur();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocAcompteVersPrelevement();
			}
		}		
		if(src instanceof TaDevis) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocDevisVersFacture();
			} else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocDevisVersBonLivraison();
			} else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocDevisVersProforma();
			}else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocDevisVersBonCommande();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocDevisVersDevis();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocDevisVersAvoir();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocDevisVersApporteur();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocDevisVersPrelevement();
			}
		} else if(src instanceof TaFacture) {
			if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocFactureVersBonLivraison();
			} else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocFactureVersAvoir();
			}else if(dest instanceof TaFacture) {
				genereDoc = new GenereDocFactureVersFacture();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocFactureVersDevis();
			}else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocFactureVersProforma();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocFactureVersApporteur();
			}else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocFactureVersCommande();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocFactureVersPrelevement();
			}
			
		} else if(src instanceof TaBoncde) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocBonCommandeVersFacture();
			} else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocBonCommandeVersBonLivraison();
			}else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocBonCommandeVersBonCommande();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocBonCommandeVersApporteur();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocBonCommandeVersAvoir();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocBonCommandeVersDevis();
			}else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocBonCommandeVersProforma();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocBonCommandeVersPrelevement();
			}
			
		} else if(src instanceof TaAvoir) {
			if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocAvoirVersAvoir();
			}
		} else if(src instanceof TaApporteur) {
			if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocApporteurVersApporteur();
			}
	
		} else if(src instanceof TaProforma) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocProformaVersFacture();
			} else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocProformaVersBonLivraison();
			} else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocProformaVersBonCommande();
			}else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocProformaVersProforma();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocProformaVersApporteur();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocProformaVersAvoir();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocProformaVersDevis();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocProformaVersPrelevement();
			}
			
		}  else if(src instanceof TaPrelevement) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocPrelevementVersFacture();
			} else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocPrelevementVersBonLivraison();
			} else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocPrelevementVersBonCommande();
			}else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocPrelevementVersProforma();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocPrelevementVersApporteur();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocPrelevementVersAvoir();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocPrelevementVersDevis();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocPrelevementVersPrelevement();
			}
			
		}else if(src instanceof TaBonliv) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocBonLivraisonVersFacture();
			}else if(dest instanceof TaBonliv) {
				genereDoc = new GenereDocBonLivraisonVersBonLivraison();
			}else if(dest instanceof TaApporteur) {
				genereDoc = new GenereDocBonLivraisonVersApporteur();
			}else if(dest instanceof TaAvoir) {
				genereDoc = new GenereDocBonLivraisonVersAvoir();
			}else if(dest instanceof TaBoncde) {
				genereDoc = new GenereDocBonLivraisonVersCommande();
			}else if(dest instanceof TaDevis) {
				genereDoc = new GenereDocBonLivraisonVersDevis();
			}else if(dest instanceof TaProforma) {
				genereDoc = new GenereDocBonLivraisonVersProforma();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocBonLivraisonVersPrelevement();
			}
		}else if(src instanceof TaAvisEcheance) {
			if(dest instanceof TaFacture) {
				genereDoc = new GenereDocAvisEcheanceVersFacture();
			}else if(dest instanceof TaAvisEcheance) {
				genereDoc = new GenereDocAvisEcheanceVersAvisEcheance();
			}else if(dest instanceof TaPrelevement) {
				genereDoc = new GenereDocAvisEcheanceVersPrelevement();
			}
		}
		genereDoc.setRelationDocument(src.getRelationDocument());
		return genereDoc;
	}

}
