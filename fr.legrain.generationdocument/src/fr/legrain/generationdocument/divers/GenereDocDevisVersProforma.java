package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaInfosProforma;
import fr.legrain.documents.dao.TaLProforma;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocDevisVersProforma extends AbstractGenereDocVersProforma{
	
	protected TaDevis castDocumentSource() {
		return (TaDevis)ds;
	}
	
	protected TaProforma castDocumentDest() {
		return (TaProforma)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaProforma)dd).setTaTPaiement(((TaDevis)ds).getTaTPaiement());
			((TaProforma)dd).setDateEchDocument(((TaDevis)ds).getDateEchDocument());
			((TaProforma)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaProforma)dd).setRemHtDocument(((TaDevis)ds).getRemHtDocument());
			((TaProforma)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaDevis)ds).getTxRemHtDocument()
			((TaProforma)dd).setRemTtcDocument(((TaDevis)ds).getRemTtcDocument());
			((TaProforma)dd).setTxRemTtcDocument(((TaDevis)ds).getTxRemTtcDocument());
			((TaProforma)dd).setNbEDocument(((TaDevis)ds).getNbEDocument());
			((TaProforma)dd).setTtc(((TaDevis)ds).getTtc());
			((TaProforma)dd).setExport(((TaDevis)ds).getExport());
			((TaProforma)dd).setCommentaire(((TaDevis)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaDevis)ds).getLignes()) {
				TaLProforma temp = (TaLProforma)copieLigneDocument(ligne, new TaLProforma()); 
				temp.setTaDocument(((TaProforma)dd));
				((TaProforma)dd).addLigne(temp);
			}

			TaInfosProforma infos = (TaInfosProforma)copieInfosDocument(((TaDevis)ds).getTaInfosDocument(),new TaInfosProforma());
			infos.setTaDocument(((TaProforma)dd));
			((TaProforma)dd).setTaInfosDocument(infos);

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
		TaDevis documentInit = ((TaDevis)ds);
		try {
			TaProformaDAO daoDocumentFinal = new TaProformaDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaProforma)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaProforma)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaProforma(((TaProforma)dd));
				taRDocument.setTaDevis(documentInit);
				((TaProforma)dd).getTaRDocuments().add(taRDocument);
				}
				((TaProforma)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		int idDoc = ds.getIdDocument();
		String jpql = "select x from TaRDocument x where x.taDevis.idDocument="+idDoc+" and x.taProforma is not null";
		return jpql;
	}

}
