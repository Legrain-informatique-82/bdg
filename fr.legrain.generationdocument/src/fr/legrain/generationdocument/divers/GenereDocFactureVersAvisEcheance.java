package fr.legrain.generationdocument.divers;


import java.math.BigDecimal;

import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaInfosAvisEcheance;
import fr.legrain.documents.dao.TaLAvisEcheance;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocFactureVersAvisEcheance extends AbstractGenereDocVersAvisEcheance{
	
	protected TaFacture castDocumentSource() {
		return (TaFacture)ds;
	}
	
	protected TaAvisEcheance castDocumentDest() {
		return (TaAvisEcheance)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaAvisEcheance)dd).setTaTPaiement(((TaFacture)ds).getTaTPaiement());
			((TaAvisEcheance)dd).setDateEchDocument(((TaFacture)ds).getDateEchDocument());
//			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAvisEcheance)dd).setRemHtDocument(((TaFacture)ds).getRemHtDocument());
			((TaAvisEcheance)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaFacture)ds).getTxRemHtDocument()
			((TaAvisEcheance)dd).setRemTtcDocument(((TaFacture)ds).getRemTtcDocument());
			((TaAvisEcheance)dd).setTxRemTtcDocument(((TaFacture)ds).getTxRemTtcDocument());
			((TaAvisEcheance)dd).setNbEDocument(((TaFacture)ds).getNbEDocument());
			((TaAvisEcheance)dd).setTtc(((TaFacture)ds).getTtc());
//			((TaAvoir)dd).setExport(((TaFacture)ds).getExport());
			((TaAvisEcheance)dd).setCommentaire(((TaFacture)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaFacture)ds).getLignes()) {
				TaLAvisEcheance temp = (TaLAvisEcheance)copieLigneDocument(ligne, new TaLAvisEcheance()); 
				temp.setTaDocument(((TaAvisEcheance)dd));
				((TaAvisEcheance)dd).addLigne(temp);
			}

			TaInfosAvisEcheance infos = (TaInfosAvisEcheance)copieInfosDocument(((TaFacture)ds).getTaInfosDocument(),new TaInfosAvisEcheance());
			infos.setTaDocument(((TaAvisEcheance)dd));
			((TaAvisEcheance)dd).setTaInfosDocument(infos);

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
	public IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,String codeTiers, boolean generationModele) {
		TaFacture documentInit = ((TaFacture)ds);
		try {
			TaAvisEcheanceDAO daoDocumentFinal = new TaAvisEcheanceDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAvisEcheance)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAvisEcheance)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaAvisEcheance(((TaAvisEcheance)dd));
				taRDocument.setTaFacture(documentInit);
				((TaAvisEcheance)dd).getTaRDocuments().add(taRDocument);
				}
				((TaAvisEcheance)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taFacture.idDocument="+idDoc+" and x.taAvisEcheance is not null";
		return jpql;
	}
}
