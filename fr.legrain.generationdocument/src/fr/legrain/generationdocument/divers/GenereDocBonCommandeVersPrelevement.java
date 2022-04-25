package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaInfosPrelevement;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersPrelevement extends AbstractGenereDocVersPrelevement{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaPrelevement castDocumentDest() {
		return (TaPrelevement)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaPrelevement)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
			((TaPrelevement)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
			((TaPrelevement)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaPrelevement)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaPrelevement)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBoncde)ds).getTxRemHtDocument()
			((TaPrelevement)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaPrelevement)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaPrelevement)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaPrelevement)dd).setTtc(((TaBoncde)ds).getTtc());
			((TaPrelevement)dd).setExport(((TaBoncde)ds).getExport());
			((TaPrelevement)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLPrelevement temp = (TaLPrelevement)copieLigneDocument(ligne, new TaLPrelevement()); 
				temp.setTaDocument(((TaPrelevement)dd));
				((TaPrelevement)dd).addLigne(temp);
			}

			TaInfosPrelevement infos = (TaInfosPrelevement)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosPrelevement());
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
		TaBoncde documentInit = ((TaBoncde)ds);
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
				taRDocument.setTaBoncde(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taBoncde.idDocument="+idDoc+" and x.taPrelevement is not null";
		return jpql;
	}

}
