package fr.legrain.generationdocument.divers;


import java.math.BigDecimal;

import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaLApporteur;


import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocFactureVersApporteur extends AbstractGenereDocVersApporteur{
	
	protected TaFacture castDocumentSource() {
		return (TaFacture)ds;
	}
	
	protected TaApporteur castDocumentDest() {
		return (TaApporteur)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaApporteur)dd).setTaTPaiement(((TaFacture)ds).getTaTPaiement());
			((TaApporteur)dd).setDateEchDocument(((TaFacture)ds).getDateEchDocument());
//			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaApporteur)dd).setRemHtDocument(((TaFacture)ds).getRemHtDocument());
			((TaApporteur)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaFacture)ds).getTxRemHtDocument()
			((TaApporteur)dd).setRemTtcDocument(((TaFacture)ds).getRemTtcDocument());
			((TaApporteur)dd).setTxRemTtcDocument(((TaFacture)ds).getTxRemTtcDocument());
			((TaApporteur)dd).setNbEDocument(((TaFacture)ds).getNbEDocument());
			((TaApporteur)dd).setTtc(((TaFacture)ds).getTtc());
//			((TaAvoir)dd).setExport(((TaFacture)ds).getExport());
			((TaApporteur)dd).setCommentaire(((TaFacture)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaFacture)ds).getLignes()) {
				TaLApporteur temp = (TaLApporteur)copieLigneDocument(ligne, new TaLApporteur()); 
				temp.setTaDocument(((TaApporteur)dd));
				((TaApporteur)dd).addLigne(temp);
			}

			TaInfosApporteur infos = (TaInfosApporteur)copieInfosDocument(((TaFacture)ds).getTaInfosDocument(),new TaInfosApporteur());
			infos.setTaDocument(((TaApporteur)dd));
			((TaApporteur)dd).setTaInfosDocument(infos);

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
			TaApporteurDAO daoDocumentFinal = new TaApporteurDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaApporteur)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaApporteur)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaApporteur(((TaApporteur)dd));
				taRDocument.setTaFacture(documentInit);
				((TaApporteur)dd).getTaRDocuments().add(taRDocument);
				}
				((TaApporteur)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taFacture.idDocument="+idDoc+" and x.taApporteur is not null";
		return jpql;
	}
}
