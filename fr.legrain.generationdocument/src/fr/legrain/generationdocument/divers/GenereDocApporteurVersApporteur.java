package fr.legrain.generationdocument.divers;

import java.math.BigDecimal;


import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaLApporteur;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocApporteurVersApporteur extends AbstractGenereDocVersApporteur{
	
	protected TaApporteur castDocumentSource() {
		return (TaApporteur)ds;
	}
	
	protected TaApporteur castDocumentDest() {
		return (TaApporteur)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaApporteur)dd).setTaTPaiement(((TaApporteur)ds).getTaTPaiement());
			((TaApporteur)dd).setDateEchDocument(((TaApporteur)ds).getDateEchDocument());
			((TaApporteur)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaApporteur)dd).setRemHtDocument(((TaApporteur)ds).getRemHtDocument());
			((TaApporteur)dd).setTxRemHtDocument(((TaApporteur)ds).getTxRemHtDocument());
			((TaApporteur)dd).setRemTtcDocument(((TaApporteur)ds).getRemTtcDocument());
			((TaApporteur)dd).setTxRemTtcDocument(((TaApporteur)ds).getTxRemTtcDocument());
			((TaApporteur)dd).setNbEDocument(((TaApporteur)ds).getNbEDocument());
			((TaApporteur)dd).setTtc(((TaApporteur)ds).getTtc());
			((TaApporteur)dd).setExport(((TaApporteur)ds).getExport());
			((TaApporteur)dd).setCommentaire(((TaApporteur)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaApporteur)ds).getLignes()) {
				TaLApporteur temp = (TaLApporteur)copieLigneDocument(ligne, new TaLApporteur()); 
				temp.setTaDocument(((TaApporteur)dd));
				((TaApporteur)dd).addLigne(temp);
			}

			TaInfosApporteur infos = (TaInfosApporteur)copieInfosDocument(((TaApporteur)ds).getTaInfosDocument(),new TaInfosApporteur());
			infos.setTaDocument(((TaApporteur)dd));
			((TaApporteur)dd).setTaInfosDocument(infos);
			
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
		
		TaApporteur documentInit = ((TaApporteur)ds);
		try {
			TaApporteurDAO daoDocumentFinal = new TaApporteurDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaApporteur)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaApporteur)dd).setCommentaire(commentaire);
				
				((TaApporteur)dd).calculeTvaEtTotaux();	
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
