package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaInfosProforma;
import fr.legrain.documents.dao.TaLProforma;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;



import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersProforma extends AbstractGenereDocVersProforma{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaProforma castDocumentDest() {
		return (TaProforma)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaProforma)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
//			((TaProforma)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
			((TaProforma)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaProforma)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaProforma)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBonliv)ds).getTxRemHtDocument()
			((TaProforma)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaProforma)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			((TaProforma)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaProforma)dd).setTtc(((TaBonliv)ds).getTtc());
//			((TaProforma)dd).setExport(((TaBonliv)ds).getExport());
			((TaProforma)dd).setCommentaire(((TaBonliv)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				TaLProforma temp = (TaLProforma)copieLigneDocument(ligne, new TaLProforma()); 
				temp.setTaDocument(((TaProforma)dd));
				((TaProforma)dd).addLigne(temp);
			}

			TaInfosProforma infos = (TaInfosProforma)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosProforma());
			infos.setTaDocument(((TaProforma)dd));
			((TaProforma)dd).setTaInfosDocument(infos);

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
		TaBonliv documentInit = ((TaBonliv)ds);
		try {
			TaProformaDAO daoDocumentFinal = new TaProformaDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaProforma)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaProforma)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaProforma(((TaProforma)dd));
				taRDocument.setTaBonliv(documentInit);
				((TaProforma)dd).getTaRDocuments().add(taRDocument);
				}
				((TaProforma)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taBonliv.idDocument="+idDoc+" and x.taProforma is not null";
		return jpql;
	}

}
