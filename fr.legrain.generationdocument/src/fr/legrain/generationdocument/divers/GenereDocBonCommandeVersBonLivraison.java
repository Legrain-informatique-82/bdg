package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaLBonliv;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersBonLivraison extends AbstractGenereDocVersBonLivraison{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaBonliv castDocumentDest() {
		return (TaBonliv)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaBonliv)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
//			((TaBonliv)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
//			((TaBonliv)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBonliv)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaBonliv)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBoncde)ds).getTxRemHtDocument()
			((TaBonliv)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaBonliv)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaBonliv)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaBonliv)dd).setTtc(((TaBoncde)ds).getTtc());
//			((TaBonliv)dd).setExport(((TaBoncde)ds).getExport());
			((TaBonliv)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLBonliv temp = (TaLBonliv)copieLigneDocument(ligne, new TaLBonliv()); 
				temp.setTaDocument(((TaBonliv)dd));
				((TaBonliv)dd).addLigne(temp);
			}

			TaInfosBonliv infos = (TaInfosBonliv)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosBonliv());
			infos.setTaDocument(((TaBonliv)dd));
			((TaBonliv)dd).setTaInfosDocument(infos);

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
			TaBonlivDAO daoDocumentFinal = new TaBonlivDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaBonliv)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaBonliv)dd).setCommentaire(commentaire);
				
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaBonliv(((TaBonliv)dd));
				taRDocument.setTaBoncde(documentInit);
				((TaBonliv)dd).getTaRDocuments().add(taRDocument);
				}
				((TaBonliv)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taBoncde.idDocument="+idDoc+" and x.taBonliv is not null";
		return jpql;
	}

}
