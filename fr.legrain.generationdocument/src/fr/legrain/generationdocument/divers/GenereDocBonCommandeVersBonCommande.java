package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaInfosBoncde;
import fr.legrain.documents.dao.TaLBoncde;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocBonCommandeVersBonCommande extends AbstractGenereDocVersBonCommande{
	
	protected TaBoncde castDocumentSource() {
		return (TaBoncde)ds;
	}
	
	protected TaBoncde castDocumentDest() {
		return (TaBoncde)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaBoncde)dd).setTaTPaiement(((TaBoncde)ds).getTaTPaiement());
			((TaBoncde)dd).setDateEchDocument(((TaBoncde)ds).getDateEchDocument());
//			((TaBoncde)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaBoncde)dd).setRemHtDocument(((TaBoncde)ds).getRemHtDocument());
			((TaBoncde)dd).setTxRemHtDocument(((TaBoncde)ds).getTxRemHtDocument());
			((TaBoncde)dd).setRemTtcDocument(((TaBoncde)ds).getRemTtcDocument());
			((TaBoncde)dd).setTxRemTtcDocument(((TaBoncde)ds).getTxRemTtcDocument());
			((TaBoncde)dd).setNbEDocument(((TaBoncde)ds).getNbEDocument());
			((TaBoncde)dd).setTtc(((TaBoncde)ds).getTtc());
			//((TaBoncde)dd).setExport(((TaBoncde)ds).getExport());
			((TaBoncde)dd).setCommentaire(((TaBoncde)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaBoncde)ds).getLignes()) {
				TaLBoncde temp = (TaLBoncde)copieLigneDocument(ligne, new TaLBoncde()); 
				temp.setTaDocument(((TaBoncde)dd));
				((TaBoncde)dd).addLigne(temp);
			}

			TaInfosBoncde infos = (TaInfosBoncde)copieInfosDocument(((TaBoncde)ds).getTaInfosDocument(),new TaInfosBoncde());
			infos.setTaDocument(((TaBoncde)dd));
			((TaBoncde)dd).setTaInfosDocument(infos);

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
		
		TaBoncde documentInit = ((TaBoncde)ds);
		try {
			TaBoncdeDAO daoDocumentFinal = new TaBoncdeDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaBoncde)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaBoncde)dd).setCommentaire(commentaire);
				
				((TaBoncde)dd).calculeTvaEtTotaux();	
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
