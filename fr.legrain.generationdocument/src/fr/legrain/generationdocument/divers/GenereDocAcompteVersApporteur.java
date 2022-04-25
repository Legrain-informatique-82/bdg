package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaLApporteur;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAcompteVersApporteur extends AbstractGenereDocVersApporteur{
	
	protected TaAcompte castDocumentSource() {
		return (TaAcompte)ds;
	}
	
	protected TaApporteur castDocumentDest() {
		return (TaApporteur)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaApporteur)dd).setTaTPaiement(((TaAcompte)ds).getTaTPaiement());
			((TaApporteur)dd).setDateEchDocument(((TaAcompte)ds).getDateEchDocument());
			((TaApporteur)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaApporteur)dd).setRemHtDocument(((TaAcompte)ds).getRemHtDocument());
			((TaApporteur)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaAcompte)ds).getTxRemHtDocument()
			((TaApporteur)dd).setRemTtcDocument(((TaAcompte)ds).getRemTtcDocument());
			((TaApporteur)dd).setTxRemTtcDocument(((TaAcompte)ds).getTxRemTtcDocument());
			((TaApporteur)dd).setNbEDocument(((TaAcompte)ds).getNbEDocument());
			((TaApporteur)dd).setTtc(((TaAcompte)ds).getTtc());
			((TaApporteur)dd).setExport(((TaAcompte)ds).getExport());
			((TaApporteur)dd).setCommentaire(((TaAcompte)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAcompte)ds).getLignes()) {
				TaLApporteur temp = (TaLApporteur)copieLigneDocument(ligne, new TaLApporteur()); 
				temp.setTaDocument(((TaApporteur)dd));
				((TaApporteur)dd).addLigne(temp);
			}

			TaInfosApporteur infos = (TaInfosApporteur)copieInfosDocument(((TaAcompte)ds).getTaInfosDocument(),new TaInfosApporteur());
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
		TaAcompte documentInit = ((TaAcompte)ds);
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
				taRDocument.setTaAcompte(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taAcompte.idDocument="+idDoc+" and x.taApporteur is not null";
		return jpql;
	}

}
