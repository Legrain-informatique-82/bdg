package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaLBonliv;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocProformaVersBonLivraison extends AbstractGenereDocVersBonLivraison{
	
	protected TaProforma castDocumentSource() {
		return (TaProforma)ds;
	}
	
	protected TaBonliv castDocumentDest() {
		return (TaBonliv)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaBonliv)dd).setTaTPaiement(((TaProforma)ds).getTaTPaiement());
//			((TaBonliv)dd).setDateEchDocument(((TaProforma)ds).getDateEchDocument());
//			((TaBonliv)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBonliv)dd).setRemHtDocument(((TaProforma)ds).getRemHtDocument());
			((TaBonliv)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaProforma)ds).getTxRemHtDocument()
			((TaBonliv)dd).setRemTtcDocument(((TaProforma)ds).getRemTtcDocument());
			((TaBonliv)dd).setTxRemTtcDocument(((TaProforma)ds).getTxRemTtcDocument());
			((TaBonliv)dd).setNbEDocument(((TaProforma)ds).getNbEDocument());
			((TaBonliv)dd).setTtc(((TaProforma)ds).getTtc());
//			((TaBonliv)dd).setExport(((TaProforma)ds).getExport());
			((TaBonliv)dd).setCommentaire(((TaProforma)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaProforma)ds).getLignes()) {
				TaLBonliv temp = (TaLBonliv)copieLigneDocument(ligne, new TaLBonliv()); 
				temp.setTaDocument(((TaBonliv)dd));
				((TaBonliv)dd).addLigne(temp);
			}

			TaInfosBonliv infos = (TaInfosBonliv)copieInfosDocument(((TaProforma)ds).getTaInfosDocument(),new TaInfosBonliv());
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
		TaProforma documentInit = ((TaProforma)ds);
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
				taRDocument.setTaProforma(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taProforma.idDocument="+idDoc+" and x.taBonliv is not null";
		return jpql;
	}

}
