package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaInfosAvoir;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocFactureVersAvoir extends AbstractGenereDocVersAvoir{
	
	protected TaFacture castDocumentSource() {
		return (TaFacture)ds;
	}
	
	protected TaAvoir castDocumentDest() {
		return (TaAvoir)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaAvoir)dd).setTaTPaiement(((TaFacture)ds).getTaTPaiement());
			((TaAvoir)dd).setDateEchDocument(((TaFacture)ds).getDateEchDocument());
//			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAvoir)dd).setRemHtDocument(((TaFacture)ds).getRemHtDocument());
			((TaAvoir)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaFacture)ds).getTxRemHtDocument()
			((TaAvoir)dd).setRemTtcDocument(((TaFacture)ds).getRemTtcDocument());
			((TaAvoir)dd).setTxRemTtcDocument(((TaFacture)ds).getTxRemTtcDocument());
			((TaAvoir)dd).setNbEDocument(((TaFacture)ds).getNbEDocument());
			((TaAvoir)dd).setTtc(((TaFacture)ds).getTtc());
//			((TaAvoir)dd).setExport(((TaFacture)ds).getExport());
			((TaAvoir)dd).setCommentaire(((TaFacture)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaFacture)ds).getLignes()) {
				TaLAvoir temp = (TaLAvoir)copieLigneDocument(ligne, new TaLAvoir()); 
				temp.setTaDocument(((TaAvoir)dd));
				((TaAvoir)dd).addLigne(temp);
			}

			TaInfosAvoir infos = (TaInfosAvoir)copieInfosDocument(((TaFacture)ds).getTaInfosDocument(),new TaInfosAvoir());
			infos.setTaDocument(((TaAvoir)dd));
			((TaAvoir)dd).setTaInfosDocument(infos);

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
		TaFacture documentInit = ((TaFacture)ds);
		try {
			TaAvoirDAO daoDocumentFinal = new TaAvoirDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAvoir)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAvoir)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaAvoir(((TaAvoir)dd));
				taRDocument.setTaFacture(documentInit);
				((TaAvoir)dd).getTaRDocuments().add(taRDocument);
				}
				((TaAvoir)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taFacture.idDocument="+idDoc+" and x.taAvoir is not null";
		return jpql;
	}
}
