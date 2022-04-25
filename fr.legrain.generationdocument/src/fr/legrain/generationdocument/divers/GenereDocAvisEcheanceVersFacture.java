package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAvisEcheanceVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaAvisEcheance castDocumentSource() {
		return (TaAvisEcheance)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaAvisEcheance)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaAvisEcheance)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaAvisEcheance)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaAvisEcheance)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaAvisEcheance)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaAvisEcheance)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaAvisEcheance)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaAvisEcheance)ds).getTtc());
			((TaFacture)dd).setExport(((TaAvisEcheance)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaAvisEcheance)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAvisEcheance)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaAvisEcheance)ds).getTaInfosDocument(),new TaInfosFacture());
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
		TaAvisEcheance documentInit = ((TaAvisEcheance)ds);
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
				taRDocument.setTaAvisEcheance(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taAvisEcheance.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
