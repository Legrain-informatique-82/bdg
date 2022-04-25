package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaInfosPrelevement;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAvisEcheanceVersPrelevement extends AbstractGenereDocVersPrelevement{
	
	protected TaAvisEcheance castDocumentSource() {
		return (TaAvisEcheance)ds;
	}
	
	protected TaPrelevement castDocumentDest() {
		return (TaPrelevement)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaPrelevement)dd).setTaTPaiement(((TaAvisEcheance)ds).getTaTPaiement());
			((TaPrelevement)dd).setDateEchDocument(((TaAvisEcheance)ds).getDateEchDocument());
			((TaPrelevement)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaPrelevement)dd).setRemHtDocument(((TaAvisEcheance)ds).getRemHtDocument());
			((TaPrelevement)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaAvisEcheance)ds).getTxRemHtDocument()
			((TaPrelevement)dd).setRemTtcDocument(((TaAvisEcheance)ds).getRemTtcDocument());
			((TaPrelevement)dd).setTxRemTtcDocument(((TaAvisEcheance)ds).getTxRemTtcDocument());
			((TaPrelevement)dd).setNbEDocument(((TaAvisEcheance)ds).getNbEDocument());
			((TaPrelevement)dd).setTtc(((TaAvisEcheance)ds).getTtc());
			((TaPrelevement)dd).setExport(((TaAvisEcheance)ds).getExport());
			((TaPrelevement)dd).setCommentaire(((TaAvisEcheance)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAvisEcheance)ds).getLignes()) {
				TaLPrelevement temp = (TaLPrelevement)copieLigneDocument(ligne, new TaLPrelevement()); 
				temp.setTaDocument(((TaPrelevement)dd));
				((TaPrelevement)dd).addLigne(temp);
			}

			TaInfosPrelevement infos = (TaInfosPrelevement)copieInfosDocument(((TaAvisEcheance)ds).getTaInfosDocument(),new TaInfosPrelevement());
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
		TaAvisEcheance documentInit = ((TaAvisEcheance)ds);
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
				taRDocument.setTaAvisEcheance(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taAvisEcheance.idDocument="+idDoc+" and x.taPrelevement is not null";
		return jpql;
	}

}
