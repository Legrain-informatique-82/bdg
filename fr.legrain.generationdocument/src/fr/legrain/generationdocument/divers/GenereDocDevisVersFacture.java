package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocDevisVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaDevis castDocumentSource() {
		return (TaDevis)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaDevis)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaDevis)ds).getDateEchDocument());
			//((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			//isa le 4/03/2010 suite à demande philippe
			((TaFacture)dd).setRegleDocument(((TaDevis)ds).getRegleDocument());
			//
			((TaFacture)dd).setRemHtDocument(((TaDevis)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaDevis)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaDevis)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaDevis)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaDevis)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaDevis)ds).getTtc());
			((TaFacture)dd).setExport(((TaDevis)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaDevis)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaDevis)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaDevis)ds).getTaInfosDocument(),new TaInfosFacture());
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
		TaDevis documentInit = ((TaDevis)ds);
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
				taRDocument.setTaDevis(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taDevis.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
