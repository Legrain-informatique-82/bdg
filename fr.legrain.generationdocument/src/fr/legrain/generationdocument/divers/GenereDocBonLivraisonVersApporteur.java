package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaLApporteur;





import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersApporteur extends AbstractGenereDocVersApporteur{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaApporteur castDocumentDest() {
		return (TaApporteur)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaApporteur)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
//			((TaApporteur)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
			((TaApporteur)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaApporteur)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaApporteur)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBonliv)ds).getTxRemHtDocument()
			((TaApporteur)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaApporteur)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			((TaApporteur)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaApporteur)dd).setTtc(((TaBonliv)ds).getTtc());
//			((TaApporteur)dd).setExport(((TaBonliv)ds).getExport());
			((TaApporteur)dd).setCommentaire(((TaBonliv)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				TaLApporteur temp = (TaLApporteur)copieLigneDocument(ligne, new TaLApporteur()); 
				temp.setTaDocument(((TaApporteur)dd));
				((TaApporteur)dd).addLigne(temp);
			}

			TaInfosApporteur infos = (TaInfosApporteur)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosApporteur());
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
		TaBonliv documentInit = ((TaBonliv)ds);
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
				taRDocument.setTaBonliv(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taBonliv.idDocument="+idDoc+" and x.taApporteur is not null";
		return jpql;
	}

}
