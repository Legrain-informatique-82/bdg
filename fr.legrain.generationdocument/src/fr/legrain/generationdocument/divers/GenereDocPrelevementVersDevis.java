package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaLDevis;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocPrelevementVersDevis extends AbstractGenereDocVersDevis{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaDevis castDocumentDest() {
		return (TaDevis)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaDevis)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaDevis)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
			((TaDevis)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaDevis)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaDevis)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaPrelevement)ds).getTxRemHtDocument()
			((TaDevis)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaDevis)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaDevis)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaDevis)dd).setTtc(((TaPrelevement)ds).getTtc());
			((TaDevis)dd).setExport(((TaPrelevement)ds).getExport());
			((TaDevis)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLDevis temp = (TaLDevis)copieLigneDocument(ligne, new TaLDevis()); 
				temp.setTaDocument(((TaDevis)dd));
				((TaDevis)dd).addLigne(temp);
			}

			TaInfosDevis infos = (TaInfosDevis)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosDevis());
			infos.setTaDocument(((TaDevis)dd));
			((TaDevis)dd).setTaInfosDocument(infos);

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
			TaDevisDAO daoDocumentFinal = new TaDevisDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaDevis)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaDevis)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaDevis(((TaDevis)dd));
				taRDocument.setTaPrelevement(documentInit);
				((TaDevis)dd).getTaRDocuments().add(taRDocument);
				}
				((TaDevis)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taPrelevement.idDocument="+idDoc+" and x.taDevis is not null";
		return jpql;
	}

}
