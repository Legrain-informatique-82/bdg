package fr.legrain.generationdocument.divers;


import java.math.BigDecimal;

import fr.legrain.documents.dao.TaInfosProforma;
import fr.legrain.documents.dao.TaLProforma;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocPrelevementVersProforma extends AbstractGenereDocVersProforma{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaProforma castDocumentDest() {
		return (TaProforma)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaProforma)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaProforma)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
//			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaProforma)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaProforma)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaPrelevement)ds).getTxRemHtDocument()
			((TaProforma)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaProforma)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaProforma)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaProforma)dd).setTtc(((TaPrelevement)ds).getTtc());
//			((TaAvoir)dd).setExport(((TaPrelevement)ds).getExport());
			((TaProforma)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLProforma temp = (TaLProforma)copieLigneDocument(ligne, new TaLProforma()); 
				temp.setTaDocument(((TaProforma)dd));
				((TaProforma)dd).addLigne(temp);
			}

			TaInfosProforma infos = (TaInfosProforma)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosProforma());
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
		TaPrelevement documentInit = ((TaPrelevement)ds);
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
				taRDocument.setTaPrelevement(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taPrelevement.idDocument="+idDoc+" and x.taProforma is not null";
		return jpql;
	}
}
