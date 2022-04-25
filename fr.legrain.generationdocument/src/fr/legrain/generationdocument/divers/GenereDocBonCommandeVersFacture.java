package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			//((TaFacture)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBoncde)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaBoncde)ds).getTtc());
			((TaFacture)dd).setExport(((TaBoncde)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosFacture());
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
		if(ld.getTaArticle()!=null && ld.getTaArticle().getTaRTaTitreTransport()!=null) {
			((TaLFacture)ld).setCodeTitreTransport(ls.getTaArticle().getTaRTaTitreTransport().getTaTitreTransport().getCodeTitreTransport());
			if(((TaLFacture)ld).getTaArticle().getTaRTaTitreTransport().getQteTitreTransport()!=null) {
				((TaLFacture)ld).setQteTitreTransport(((TaLFacture)ld).getTaArticle().getTaRTaTitreTransport().getQteTitreTransport().multiply(((TaLFacture)ld).getQteLDocument()));
			}
		} else {
			((TaLFacture)ld).setCodeTitreTransport("");
			((TaLFacture)ld).setQteTitreTransport(null);
		}
		return ld;
	}

	@Override
	public IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,String codeTiers, boolean generationModele) {
		TaBoncde documentInit = ((TaBoncde)ds);
		try {
			TaFactureDAO daoDocumentFinal = new TaFactureDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaFacture)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaFacture)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaFacture(((TaFacture)dd));
				taRDocument.setTaBoncde(documentInit);
				((TaFacture)dd).getTaRDocuments().add(taRDocument);
				}
				((TaFacture)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taBoncde.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
