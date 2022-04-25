package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaLDevis;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocDevisVersDevis extends AbstractGenereDocVersDevis{
	
	protected TaDevis castDocumentSource() {
		return (TaDevis)ds;
	}
	
	protected TaDevis castDocumentDest() {
		return (TaDevis)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaDevis)dd).setTaTPaiement(((TaDevis)ds).getTaTPaiement());
			((TaDevis)dd).setDateEchDocument(((TaDevis)ds).getDateEchDocument());
//			((TaDevis)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaDevis)dd).setRemHtDocument(((TaDevis)ds).getRemHtDocument());
			((TaDevis)dd).setTxRemHtDocument(((TaDevis)ds).getTxRemHtDocument());
			((TaDevis)dd).setRemTtcDocument(((TaDevis)ds).getRemTtcDocument());
			((TaDevis)dd).setTxRemTtcDocument(((TaDevis)ds).getTxRemTtcDocument());
			((TaDevis)dd).setNbEDocument(((TaDevis)ds).getNbEDocument());
			((TaDevis)dd).setTtc(((TaDevis)ds).getTtc());
			//((TaDevis)dd).setExport(((TaDevis)ds).getExport());
			((TaDevis)dd).setCommentaire(((TaDevis)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaDevis)ds).getLignes()) {
				TaLDevis temp = (TaLDevis)copieLigneDocument(ligne, new TaLDevis()); 
				temp.setTaDocument(((TaDevis)dd));
				((TaDevis)dd).addLigne(temp);
			}

			TaInfosDevis infos = (TaInfosDevis)copieInfosDocument(((TaDevis)ds).getTaInfosDocument(),new TaInfosDevis());
			infos.setTaDocument(((TaDevis)dd));
			((TaDevis)dd).setTaInfosDocument(infos);

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
			TaDevisDAO daoDocumentFinal = new TaDevisDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaDevis)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaDevis)dd).setCommentaire(commentaire);
				
				((TaDevis)dd).calculeTvaEtTotaux();	
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
