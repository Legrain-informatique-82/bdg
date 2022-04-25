package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaLBonliv;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonLivraisonVersBonLivraison extends AbstractGenereDocVersBonLivraison{
	
	protected TaBonliv castDocumentSource() {
		return (TaBonliv)ds;
	}
	
	protected TaBonliv castDocumentDest() {
		return (TaBonliv)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaBonliv)dd).setTaTPaiement(((TaBonliv)ds).getTaTPaiement());
//			((TaBonliv)dd).setDateEchDocument(((TaBonliv)ds).getDateEchDocument());
//			((TaBonliv)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBonliv)dd).setRemHtDocument(((TaBonliv)ds).getRemHtDocument());
			((TaBonliv)dd).setTxRemHtDocument(((TaBonliv)ds).getTxRemHtDocument());
			((TaBonliv)dd).setRemTtcDocument(((TaBonliv)ds).getRemTtcDocument());
			((TaBonliv)dd).setTxRemTtcDocument(((TaBonliv)ds).getTxRemTtcDocument());
			((TaBonliv)dd).setNbEDocument(((TaBonliv)ds).getNbEDocument());
			((TaBonliv)dd).setTtc(((TaBonliv)ds).getTtc());
//			((TaBonliv)dd).setExport(((TaBonliv)ds).getExport());
			((TaBonliv)dd).setCommentaire(((TaBonliv)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBonliv)ds).getLignes()) {
				TaLBonliv temp = (TaLBonliv)copieLigneDocument(ligne, new TaLBonliv()); 
				temp.setTaDocument(((TaBonliv)dd));
				((TaBonliv)dd).addLigne(temp);
			}

			TaInfosBonliv infos = (TaInfosBonliv)copieInfosDocument(((TaBonliv)ds).getTaInfosDocument(),new TaInfosBonliv());
			infos.setTaDocument(((TaBonliv)dd));
			((TaBonliv)dd).setTaInfosDocument(infos);

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
			TaBonlivDAO daoDocumentFinal = new TaBonlivDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaBonliv)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaBonliv)dd).setCommentaire(commentaire);
				
				((TaBonliv)dd).calculeTvaEtTotaux();	
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
