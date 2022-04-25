package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocFactureVersFacture extends AbstractGenereDocVersFacture{
	
	protected TaFacture castDocumentSource() {
		return (TaFacture)ds;
	}
	
	protected TaFacture castDocumentDest() {
		return (TaFacture)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
//			((TaFacture)dd).setTaTPaiement(((TaFacture)ds).getTaTPaiement());
			((TaFacture)dd).setDateEchDocument(((TaFacture)ds).getDateEchDocument());
			((TaFacture)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaFacture)dd).setRemHtDocument(((TaFacture)ds).getRemHtDocument());
			((TaFacture)dd).setTxRemHtDocument(((TaFacture)ds).getTxRemHtDocument());
			((TaFacture)dd).setRemTtcDocument(((TaFacture)ds).getRemTtcDocument());
			((TaFacture)dd).setTxRemTtcDocument(((TaFacture)ds).getTxRemTtcDocument());
			((TaFacture)dd).setNbEDocument(((TaFacture)ds).getNbEDocument());
			((TaFacture)dd).setTtc(((TaFacture)ds).getTtc());
			//((TaFacture)dd).setExport(((TaFacture)ds).getExport());
			((TaFacture)dd).setCommentaire(((TaFacture)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaFacture)ds).getLignes()) {
				TaLFacture temp = (TaLFacture)copieLigneDocument(ligne, new TaLFacture()); 
				temp.setTaDocument(((TaFacture)dd));
				((TaFacture)dd).addLigne(temp);
			}

			TaInfosFacture infos = (TaInfosFacture)copieInfosDocument(((TaFacture)ds).getTaInfosDocument(),new TaInfosFacture());
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
		
		TaFacture documentInit = ((TaFacture)ds);
		try {
			TaFactureDAO daoDocumentFinal = new TaFactureDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaFacture)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaFacture)dd).setCommentaire(commentaire);
				
				((TaFacture)dd).calculeTvaEtTotaux();	
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
