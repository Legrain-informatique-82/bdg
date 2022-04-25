package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;

public  class GenereDocPrelevementVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaPrelevement)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaPrelevement)ds).getTtc());
			((TaFacture)dd).setExport(((TaPrelevement)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosFacture());
			infos.setTaDocument(((TaFacture)dd));
			((TaFacture)dd).setTaInfosDocument(infos);

			return dd;
		} catch (Exception e) {
			logger.error("",e);
		}
		return dd;
	}

	@Override
	public IInfosDocumentTiers copieInfosDocumentSpecifique(IInfosDocumentTiers is, IInfosDocumentTiers id) {
		return id;
	}

	@Override
	public ILigneDocumentTiers copieLigneDocumentSpecifique(ILigneDocumentTiers ls, ILigneDocumentTiers ld) {
		return ld;
	}

	@Override
	public IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,String codeTiers , boolean generationModele) {
		TaPrelevement documentInit = ((TaPrelevement)ds);
		try {
			TaFactureDAO daoDocumentFinal = new TaFactureDAO(getEm());
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);

				((TaFacture)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaFacture)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaFacture(((TaFacture)dd));
				taRDocument.setTaPrelevement(documentInit);
				((TaFacture)dd).getTaRDocuments().add(taRDocument);
				}
				((TaFacture)dd).calculeTvaEtTotaux();	
				if(!generationModele){
					TaRReglement taReglement = new TaRReglement();
					TaReglementDAO daoReglement = new TaReglementDAO(getEm());
					TaTPaiementDAO daoTPaiement = new TaTPaiementDAO(getEm());
					TaReglement reglement = new TaReglement();
					reglement=daoReglement.enregistrerMerge(reglement);
					TaTPaiement tPaiement = daoTPaiement.findByCode("PREL");
					taReglement.setTaReglement(reglement);
					reglement.setTaTiers(dd.getTaTiers());
					reglement.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise(tPaiement));
					taReglement.getTaReglement().setCodeDocument(new TaReglementDAO(getEm()).genereCode());
					taReglement.setAffectation(dd.getNetTtcCalc());
					reglement.setNetTtcCalc(dd.getNetTtcCalc());
					reglement.setDateDocument(dd.getDateDocument());
					taReglement.getTaReglement().setDateDocument(dd.getDateDocument());
					taReglement.getTaReglement().setEtat(IHMEtat.integre);
					taReglement.getTaReglement().setDateLivDocument(dd.getDateDocument());
					taReglement.getTaReglement().setTaTPaiement(tPaiement);
					reglement.setLibelleDocument(reglement.getTaTPaiement().getLibTPaiement());
					taReglement.setTaFacture((TaFacture)dd);
					((TaFacture)dd).addRReglement(taReglement);
				}
				((TaFacture)dd).setRegleDocument(dd.getNetTtcCalc());
				((TaFacture)dd).setRegleCompletDocument(dd.getNetTtcCalc());
				((TaFacture)dd).setNetAPayer(BigDecimal.valueOf(0));
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taPrelevement.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
