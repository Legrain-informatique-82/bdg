package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaLDevis;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersDevis extends AbstractGenereDocVersDevis{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaDevis castDocumentDest() {
		return (TaDevis)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaDevis)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
//			((TaDevis)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
//			((TaDevis)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaDevis)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaDevis)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBoncde)ds).getTxRemHtDocument()
			((TaDevis)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaDevis)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaDevis)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaDevis)dd).setTtc(((TaBoncde)ds).getTtc());
//			((TaDevis)dd).setExport(((TaBoncde)ds).getExport());
			((TaDevis)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLDevis temp = (TaLDevis)copieLigneDocument(ligne, new TaLDevis()); 
				temp.setTaDocument(((TaDevis)dd));
				((TaDevis)dd).addLigne(temp);
			}

			TaInfosDevis infos = (TaInfosDevis)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosDevis());
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
		TaBoncde documentInit = ((TaBoncde)ds);
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
				taRDocument.setTaBoncde(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taBoncde.idDocument="+idDoc+" and x.taDevis is not null";
		return jpql;
	}

}
