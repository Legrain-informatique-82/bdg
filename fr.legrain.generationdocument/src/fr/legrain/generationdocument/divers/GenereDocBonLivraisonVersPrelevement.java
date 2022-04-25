package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaInfosPrelevement;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersPrelevement extends AbstractGenereDocVersPrelevement{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaPrelevement castDocumentDest() {
		return (TaPrelevement)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaPrelevement)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
			//((TaPrelevement)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
			((TaPrelevement)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaPrelevement)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaPrelevement)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBonliv)ds).getTxRemHtDocument()
			((TaPrelevement)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaPrelevement)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			((TaPrelevement)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaPrelevement)dd).setTtc(((TaBonliv)ds).getTtc());
			//((TaPrelevement)dd).setExport(((TaBonliv)ds).getExport());
			((TaPrelevement)dd).setCommentaire(((TaBonliv)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				TaLPrelevement temp = (TaLPrelevement)copieLigneDocument(ligne, new TaLPrelevement()); 
				temp.setTaDocument(((TaPrelevement)dd));
				((TaPrelevement)dd).addLigne(temp);
			}

			TaInfosPrelevement infos = (TaInfosPrelevement)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosPrelevement());
			infos.setTaDocument(((TaPrelevement)dd));
			((TaPrelevement)dd).setTaInfosDocument(infos);

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
			TaPrelevementDAO daoDocumentFinal = new TaPrelevementDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaPrelevement)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaPrelevement)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaPrelevement(((TaPrelevement)dd));
				taRDocument.setTaBonliv(documentInit);
				((TaPrelevement)dd).getTaRDocuments().add(taRDocument);
				}
				((TaPrelevement)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taBonliv.idDocument="+idDoc+" and x.taPrelevement is not null";
		return jpql;
	}

}
