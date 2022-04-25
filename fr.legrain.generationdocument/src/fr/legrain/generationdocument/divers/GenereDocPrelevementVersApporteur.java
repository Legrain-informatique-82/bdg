package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;




import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaLApporteur;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocPrelevementVersApporteur extends AbstractGenereDocVersApporteur{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaApporteur castDocumentDest() {
		return (TaApporteur)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaApporteur)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaApporteur)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
			((TaApporteur)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaApporteur)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaApporteur)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaPrelevement)ds).getTxRemHtDocument()
			((TaApporteur)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaApporteur)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaApporteur)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaApporteur)dd).setTtc(((TaPrelevement)ds).getTtc());
			((TaApporteur)dd).setExport(((TaPrelevement)ds).getExport());
			((TaApporteur)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLApporteur temp = (TaLApporteur)copieLigneDocument(ligne, new TaLApporteur()); 
				temp.setTaDocument(((TaApporteur)dd));
				((TaApporteur)dd).addLigne(temp);
			}

			TaInfosApporteur infos = (TaInfosApporteur)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosApporteur());
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
		TaPrelevement documentInit = ((TaPrelevement)ds);
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
				taRDocument.setTaPrelevement(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taPrelevement.idDocument="+idDoc+" and x.taApporteur is not null";
		return jpql;
	}

}
