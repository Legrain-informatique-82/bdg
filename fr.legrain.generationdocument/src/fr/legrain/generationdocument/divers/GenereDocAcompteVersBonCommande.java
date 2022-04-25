package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaInfosBoncde;
import fr.legrain.documents.dao.TaLBoncde;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAcompteVersBonCommande extends AbstractGenereDocVersBonCommande{
	
	protected TaAcompte castDocumentSource() {
		return (TaAcompte)ds;
	}
	
	protected TaBoncde castDocumentDest() {
		return (TaBoncde)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaBoncde)dd).setTaTPaiement(((TaAcompte)ds).getTaTPaiement());
			((TaBoncde)dd).setDateEchDocument(((TaAcompte)ds).getDateEchDocument());
			((TaBoncde)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBoncde)dd).setRemHtDocument(((TaAcompte)ds).getRemHtDocument());
			((TaBoncde)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaAcompte)ds).getTxRemHtDocument()
			((TaBoncde)dd).setRemTtcDocument(((TaAcompte)ds).getRemTtcDocument());
			((TaBoncde)dd).setTxRemTtcDocument(((TaAcompte)ds).getTxRemTtcDocument());
			((TaBoncde)dd).setNbEDocument(((TaAcompte)ds).getNbEDocument());
			((TaBoncde)dd).setTtc(((TaAcompte)ds).getTtc());
			((TaBoncde)dd).setExport(((TaAcompte)ds).getExport());
			((TaBoncde)dd).setCommentaire(((TaAcompte)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAcompte)ds).getLignes()) {
				TaLBoncde temp = (TaLBoncde)copieLigneDocument(ligne, new TaLBoncde()); 
				temp.setTaDocument(((TaBoncde)dd));
				((TaBoncde)dd).addLigne(temp);
			}

			TaInfosBoncde infos = (TaInfosBoncde)copieInfosDocument(((TaAcompte)ds).getTaInfosDocument(),new TaInfosBoncde());
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
		TaAcompte documentInit = ((TaAcompte)ds);
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
				taRDocument.setTaAcompte(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taAcompte.idDocument="+idDoc+" and x.taBoncde is not null";
		return jpql;
	}

}
