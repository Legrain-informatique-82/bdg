package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaInfosBoncde;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocPrelevementVersBonCommande extends AbstractGenereDocVersBonCommande{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaBoncde castDocumentDest() {
		return (TaBoncde)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaBoncde)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaBoncde)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
//			((TaBoncde)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBoncde)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaBoncde)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaPrelevement)ds).getTxRemHtDocument()
			((TaBoncde)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaBoncde)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaBoncde)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaBoncde)dd).setTtc(((TaPrelevement)ds).getTtc());
			((TaBoncde)dd).setExport(((TaPrelevement)ds).getExport());
			((TaBoncde)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLBoncde temp = (TaLBoncde)copieLigneDocument(ligne, new TaLBoncde()); 
				temp.setTaDocument(((TaBoncde)dd));
				((TaBoncde)dd).addLigne(temp);
			}

			TaInfosBoncde infos = (TaInfosBoncde)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosBoncde());
			infos.setTaDocument(((TaBoncde)dd));
			((TaBoncde)dd).setTaInfosDocument(infos);

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
		TaPrelevement documentInit = ((TaPrelevement)ds);
		try {
			TaBoncdeDAO daoDocumentFinal = new TaBoncdeDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaBoncde)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaBoncde)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaBoncde(((TaBoncde)dd));
				taRDocument.setTaPrelevement(documentInit);
				((TaBoncde)dd).getTaRDocuments().add(taRDocument);
				}
				((TaBoncde)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taPrelevement.idDocument="+idDoc+" and x.taBoncde is not null";
		return jpql;
	}

}
