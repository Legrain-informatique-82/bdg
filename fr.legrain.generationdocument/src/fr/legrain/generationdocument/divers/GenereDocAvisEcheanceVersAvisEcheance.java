package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaInfosAvisEcheance;
import fr.legrain.documents.dao.TaLAvisEcheance;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocAvisEcheanceVersAvisEcheance extends AbstractGenereDocVersProforma{
	
	protected TaAvisEcheance castDocumentSource() {
		return (TaAvisEcheance)ds;
	}
	
	protected TaAvisEcheance castDocumentDest() {
		return (TaAvisEcheance)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaAvisEcheance)dd).setTaTPaiement(((TaAvisEcheance)ds).getTaTPaiement());
			((TaAvisEcheance)dd).setDateEchDocument(((TaAvisEcheance)ds).getDateEchDocument());
//			((TaAvisEcheance)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAvisEcheance)dd).setRemHtDocument(((TaAvisEcheance)ds).getRemHtDocument());
			((TaAvisEcheance)dd).setTxRemHtDocument(((TaAvisEcheance)ds).getTxRemHtDocument());
			((TaAvisEcheance)dd).setRemTtcDocument(((TaAvisEcheance)ds).getRemTtcDocument());
			((TaAvisEcheance)dd).setTxRemTtcDocument(((TaAvisEcheance)ds).getTxRemTtcDocument());
			((TaAvisEcheance)dd).setNbEDocument(((TaAvisEcheance)ds).getNbEDocument());
			((TaAvisEcheance)dd).setTtc(((TaAvisEcheance)ds).getTtc());
			//((TaAvisEcheance)dd).setExport(((TaAvisEcheance)ds).getExport());
			((TaAvisEcheance)dd).setCommentaire(((TaAvisEcheance)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAvisEcheance)ds).getLignes()) {
				TaLAvisEcheance temp = (TaLAvisEcheance)copieLigneDocument(ligne, new TaLAvisEcheance()); 
				temp.setTaDocument(((TaAvisEcheance)dd));
				((TaAvisEcheance)dd).addLigne(temp);
			}

			TaInfosAvisEcheance infos = (TaInfosAvisEcheance)copieInfosDocument(((TaAvisEcheance)ds).getTaInfosDocument(),new TaInfosAvisEcheance());
			infos.setTaDocument(((TaAvisEcheance)dd));
			((TaAvisEcheance)dd).setTaInfosDocument(infos);

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
		
		TaAvisEcheance documentInit = ((TaAvisEcheance)ds);
		try {
			TaAvisEcheanceDAO daoDocumentFinal = new TaAvisEcheanceDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAvisEcheance)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAvisEcheance)dd).setCommentaire(commentaire);
				
				((TaAvisEcheance)dd).calculeTvaEtTotaux();	
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
