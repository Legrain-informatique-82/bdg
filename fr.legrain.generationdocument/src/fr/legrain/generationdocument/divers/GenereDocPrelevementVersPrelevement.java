package fr.legrain.generationdocument.divers;


import fr.legrain.documents.dao.TaInfosPrelevement;
import fr.legrain.documents.dao.TaLPrelevement;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocPrelevementVersPrelevement extends AbstractGenereDocVersPrelevement{
	
	protected TaPrelevement castDocumentSource() {
		return (TaPrelevement)ds;
	}
	
	protected TaPrelevement castDocumentDest() {
		return (TaPrelevement)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaPrelevement)dd).setTaTPaiement(((TaPrelevement)ds).getTaTPaiement());
			((TaPrelevement)dd).setDateEchDocument(((TaPrelevement)ds).getDateEchDocument());
//			((TaPrelevement)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaPrelevement)dd).setRemHtDocument(((TaPrelevement)ds).getRemHtDocument());
			((TaPrelevement)dd).setTxRemHtDocument(((TaPrelevement)ds).getTxRemHtDocument());
			((TaPrelevement)dd).setRemTtcDocument(((TaPrelevement)ds).getRemTtcDocument());
			((TaPrelevement)dd).setTxRemTtcDocument(((TaPrelevement)ds).getTxRemTtcDocument());
			((TaPrelevement)dd).setNbEDocument(((TaPrelevement)ds).getNbEDocument());
			((TaPrelevement)dd).setTtc(((TaPrelevement)ds).getTtc());
			//((TaPrelevement)dd).setExport(((TaPrelevement)ds).getExport());
			((TaPrelevement)dd).setCommentaire(((TaPrelevement)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaPrelevement)ds).getLignes()) {
				TaLPrelevement temp = (TaLPrelevement)copieLigneDocument(ligne, new TaLPrelevement()); 
				temp.setTaDocument(((TaPrelevement)dd));
				((TaPrelevement)dd).addLigne(temp);
			}

			TaInfosPrelevement infos = (TaInfosPrelevement)copieInfosDocument(((TaPrelevement)ds).getTaInfosDocument(),new TaInfosPrelevement());
			infos.setTaDocument(((TaPrelevement)dd));
			((TaPrelevement)dd).setTaInfosDocument(infos);

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
			TaPrelevementDAO daoDocumentFinal = new TaPrelevementDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaPrelevement)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaPrelevement)dd).setCommentaire(commentaire);
				
				((TaPrelevement)dd).calculeTvaEtTotaux();	
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
