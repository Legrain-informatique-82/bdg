package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaInfosAcompte;
import fr.legrain.documents.dao.TaLAcompte;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;

public  class GenereDocAcompteVersAcompte extends AbstractGenereDocVersAcompte{
	
	protected TaAcompte castDocumentSource() {
		return (TaAcompte)ds;
	}
	
	protected TaAcompte castDocumentDest() {
		return (TaAcompte)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaAcompte)dd).setTaTPaiement(((TaAcompte)ds).getTaTPaiement());
			((TaAcompte)dd).setDateEchDocument(((TaAcompte)ds).getDateEchDocument());
			((TaAcompte)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaAcompte)dd).setRemHtDocument(((TaAcompte)ds).getRemHtDocument());
			((TaAcompte)dd).setTxRemHtDocument(((TaAcompte)ds).getTxRemHtDocument());
			((TaAcompte)dd).setRemTtcDocument(((TaAcompte)ds).getRemTtcDocument());
			((TaAcompte)dd).setTxRemTtcDocument(((TaAcompte)ds).getTxRemTtcDocument());
			((TaAcompte)dd).setNbEDocument(((TaAcompte)ds).getNbEDocument());
			((TaAcompte)dd).setTtc(((TaAcompte)ds).getTtc());
			//((TaAcompte)dd).setExport(((TaAcompte)ds).getExport());
			((TaAcompte)dd).setCommentaire(((TaAcompte)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaAcompte)ds).getLignes()) {
				TaLAcompte temp = (TaLAcompte)copieLigneDocument(ligne, new TaLAcompte()); 
				temp.setTaDocument(((TaAcompte)dd));
				((TaAcompte)dd).addLigne(temp);
			}

			TaInfosAcompte infos = (TaInfosAcompte)copieInfosDocument(((TaAcompte)ds).getTaInfosDocument(),new TaInfosAcompte());
			infos.setTaDocument(((TaAcompte)dd));
			((TaAcompte)dd).setTaInfosDocument(infos);
			
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
		
		TaAcompte documentInit = ((TaAcompte)ds);
		try {
			TaAcompteDAO daoDocumentFinal = new TaAcompteDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaAcompte)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaAcompte)dd).setCommentaire(commentaire);
				
				((TaAcompte)dd).calculeTvaEtTotaux();	
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
