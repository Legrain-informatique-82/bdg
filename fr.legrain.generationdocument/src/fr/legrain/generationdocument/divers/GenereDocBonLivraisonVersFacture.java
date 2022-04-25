package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;
import java.util.Date;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaTLigne;
import fr.legrain.documents.dao.TaTLigneDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
//			((TaFacture)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(BigDecimal.valueOf(0));//((TaBonliv)ds).getTxRemHtDocument()
			((TaFacture)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			//((TaFacture)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaBonliv)ds).getTtc());
//			((TaFacture)dd).setExport(((TaBonliv)ds).getExport());
			//((TaFacture)dd).setCommentaire(((TaBonliv)ds).getCommentaire());
		
//			int i=0;
//			TaTLigne tl=null;
//			ds.calculeTvaEtTotaux();
			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				//if(i==0)tl=ligne.getTaTLigne();
//				if(libelleSeparateur!=null && i==0){
//					TaLFacture ligneSup= new TaLFacture(false);
//					ligneSup.initialiseLigne(true);
//					ligneSup.setLibLDocument(libelleSeparateur);
//					ligneSup.setTaDocument(((TaFacture)dd));
//					ligneSup.setTaTLigne(tl);
//					((TaFacture)dd).addLigne(ligneSup);
//					i++;
//				}
//				
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}
//			if(ligneSeparatrice){
//				TaLFacture ligneSup= new TaLFacture(false);
//				ligneSup.initialiseLigne(true);
//				ligneSup.setLibLDocument("");
//				ligneSup.setTaTLigne(tl);
//				ligneSup.setTaDocument(((TaFacture)dd));
//				((TaFacture)dd).addLigne(ligneSup);
//			}
			
			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosFacture());
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
		return null;
	}

	@Override
	public ILigneDocumentTiers copieLigneDocumentSpecifique(ILigneDocumentTiers ls, ILigneDocumentTiers ld) {
		if(ld.getTaArticle()!=null && ld.getTaArticle().getTaRTaTitreTransport()!=null) {
			((TaLFacture)ld).setCodeTitreTransport(ls.getTaArticle().getTaRTaTitreTransport().getTaTitreTransport().getCodeTitreTransport());
			if(((TaLFacture)ld).getTaArticle().getTaRTaTitreTransport().getQteTitreTransport()!=null) {
				((TaLFacture)ld).setQteTitreTransport(((TaLFacture)ld).getTaArticle().getTaRTaTitreTransport().getQteTitreTransport().multiply(((TaLFacture)ld).getQteLDocument()));
			}
		} else {
			((TaLFacture)ld).setCodeTitreTransport("");
			((TaLFacture)ld).setQteTitreTransport(null);
		}
		return ld;
	}

	@Override
	public IDocumentTiers genereDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd,String codeTiers, boolean generationModele) {
		TaBonliv documentInit = ((TaBonliv)ds);
		try {
			TaFactureDAO daoDocumentFinal = new TaFactureDAO();
			
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaFacture)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaFacture)dd).setDateDocument(((TaFacture)dd).dateDansExercice(getDateDoc()));
				((TaFacture)dd).setDateEchDocument(((TaFacture)dd).dateDansExercice(getDateDoc()));
				((TaFacture)dd).setDateLivDocument(documentInit.getDateDocument());
				((TaFacture)dd).setCommentaire(commentaire);
				if(ds.getRelationDocument()){
				TaRDocument taRDocument = new TaRDocument();
				taRDocument.setTaFacture(((TaFacture)dd));
				taRDocument.setTaBonliv(documentInit);
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
		String jpql = "select x from TaRDocument x where x.taBonliv.idDocument="+idDoc+" and x.taFacture is not null";
		return jpql;
	}

}
