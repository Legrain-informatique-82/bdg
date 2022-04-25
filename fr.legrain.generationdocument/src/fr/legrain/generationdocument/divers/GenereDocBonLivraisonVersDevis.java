package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaLDevis;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersDevis extends AbstractGenereDocVersDevis{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaDevis castDocumentDest() {
		return (TaDevis)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaDevis)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
//			((TaDevis)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
			((TaDevis)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaDevis)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaDevis)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBonliv)ds).getTxRemHtDocument()
			((TaDevis)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaDevis)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			((TaDevis)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaDevis)dd).setTtc(((TaBonliv)ds).getTtc());
//			((TaDevis)dd).setExport(((TaBonliv)ds).getExport());
			((TaDevis)dd).setCommentaire(((TaBonliv)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				TaLDevis temp = (TaLDevis)copieLigneDocument(ligne, new TaLDevis()); 
				temp.setTaDocument(((TaDevis)dd));
				((TaDevis)dd).addLigne(temp);
			}

			TaInfosDevis infos = (TaInfosDevis)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosDevis());
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
		TaBonliv documentInit = ((TaBonliv)ds);
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
				taRDocument.setTaBonliv(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taBonliv.idDocument="+idDoc+" and x.taDevis is not null";
		return jpql;
	}

}
