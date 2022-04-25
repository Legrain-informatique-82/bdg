package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaInfosAvoir;
import fr.legrain.documents.dao.TaLAvoir;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAvoirVersAvoir extends AbstractGenereDocVersAvoir{
	
	protected TaAvoir castDocumentSource() {
		return (TaAvoir)ds;
	}
	
	protected TaAvoir castDocumentDest() {
		return (TaAvoir)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaAvoir)dd).setTaTPaiement(((TaAvoir)ds).getTaTPaiement());
			((TaAvoir)dd).setDateEchDocument(((TaAvoir)ds).getDateEchDocument());
			((TaAvoir)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAvoir)dd).setRemHtDocument(((TaAvoir)ds).getRemHtDocument());
			((TaAvoir)dd).setTxRemHtDocument(((TaAvoir)ds).getTxRemHtDocument());
			((TaAvoir)dd).setRemTtcDocument(((TaAvoir)ds).getRemTtcDocument());
			((TaAvoir)dd).setTxRemTtcDocument(((TaAvoir)ds).getTxRemTtcDocument());
			((TaAvoir)dd).setNbEDocument(((TaAvoir)ds).getNbEDocument());
			((TaAvoir)dd).setTtc(((TaAvoir)ds).getTtc());
			//((TaAvoir)dd).setExport(((TaAvoir)ds).getExport());
			((TaAvoir)dd).setCommentaire(((TaAvoir)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAvoir)ds).getLignes()) {
				TaLAvoir temp = (TaLAvoir)copieLigneDocument(ligne, new TaLAvoir()); 
				temp.setTaDocument(((TaAvoir)dd));
				((TaAvoir)dd).addLigne(temp);
			}

			TaInfosAvoir infos = (TaInfosAvoir)copieInfosDocument(((TaAvoir)ds).getTaInfosDocument(),new TaInfosAvoir());
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
		
		TaAvoir documentInit = ((TaAvoir)ds);
		try {
			TaAvoirDAO daoDocumentFinal = new TaAvoirDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAvoir)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAvoir)dd).setCommentaire(commentaire);
				
				((TaAvoir)dd).calculeTvaEtTotaux();	
			}
			return dd;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	public boolean dejaGenere(IDocumentTiers ds) {
		//on peut generer autant de document que l'on veut si ils sont du meme type
		return false;
	}

	@Override
	public String creationRequeteDejaGenere(IDocumentTiers ds) {
		return null;
	}

}
