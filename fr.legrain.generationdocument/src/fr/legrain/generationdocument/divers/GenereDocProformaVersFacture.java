package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocProformaVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaProforma castDocumentSource() {
		return (TaProforma)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaProforma)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaProforma)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaProforma)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaProforma)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaProforma)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaProforma)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaProforma)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaProforma)ds).getTtc());
			((TaFacture)dd).setExport(((TaProforma)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaProforma)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaProforma)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaProforma)ds).getTaInfosDocument(),new TaInfosFacture());
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
		return ld;
	}

	@Override
	public IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,String codeTiers, boolean generationModele) {
		TaProforma documentInit = ((TaProforma)ds);
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
				taRDocument.setTaProforma(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taProforma.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
