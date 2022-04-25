package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaInfosAvoir;
import fr.legrain.documents.dao.TaLAvoir;


import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersAvoir extends AbstractGenereDocVersAvoir{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaAvoir castDocumentDest() {
		return (TaAvoir)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaAvoir)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
//			((TaAvoir)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
//			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAvoir)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaAvoir)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBoncde)ds).getTxRemHtDocument()
			((TaAvoir)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaAvoir)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaAvoir)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaAvoir)dd).setTtc(((TaBoncde)ds).getTtc());
//			((TaAvoir)dd).setExport(((TaBoncde)ds).getExport());
			((TaAvoir)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLAvoir temp = (TaLAvoir)copieLigneDocument(ligne, new TaLAvoir()); 
				temp.setTaDocument(((TaAvoir)dd));
				((TaAvoir)dd).addLigne(temp);
			}

			TaInfosAvoir infos = (TaInfosAvoir)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosAvoir());
			infos.setTaDocument(((TaAvoir)dd));
			((TaAvoir)dd).setTaInfosDocument(infos);

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
		TaBoncde documentInit = ((TaBoncde)ds);
		try {
			TaAvoirDAO daoDocumentFinal = new TaAvoirDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAvoir)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAvoir)dd).setCommentaire(commentaire);
				
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaAvoir(((TaAvoir)dd));
				taRDocument.setTaBoncde(documentInit);
				((TaAvoir)dd).getTaRDocuments().add(taRDocument);
				}
				((TaAvoir)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taBoncde.idDocument="+idDoc+" and x.taAvoir is not null";
		return jpql;
	}

}
