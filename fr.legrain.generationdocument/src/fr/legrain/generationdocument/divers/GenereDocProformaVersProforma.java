package fr.legrain.generationdocument.divers;

import fr.legrain.documents.dao.TaInfosProforma;
import fr.legrain.documents.dao.TaLProforma;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.ILigneDocumentTiers;

public  class GenereDocProformaVersProforma extends AbstractGenereDocVersProforma{
	
	protected TaProforma castDocumentSource() {
		return (TaProforma)ds;
	}
	
	protected TaProforma castDocumentDest() {
		return (TaProforma)dd;
	}
	
	@Override
	public IDocumentTiers copieDocumentSpecifique(IDocumentTiers ds, IDocumentTiers dd
			,Boolean ligneSeparatrice,String libelleSeparateur) {
		try {
			((TaProforma)dd).setTaTPaiement(((TaProforma)ds).getTaTPaiement());
			((TaProforma)dd).setDateEchDocument(((TaProforma)ds).getDateEchDocument());
//			((TaProforma)dd).setRegleDocument(BigDecimal.valueOf(0));
			((TaProforma)dd).setRemHtDocument(((TaProforma)ds).getRemHtDocument());
			((TaProforma)dd).setTxRemHtDocument(((TaProforma)ds).getTxRemHtDocument());
			((TaProforma)dd).setRemTtcDocument(((TaProforma)ds).getRemTtcDocument());
			((TaProforma)dd).setTxRemTtcDocument(((TaProforma)ds).getTxRemTtcDocument());
			((TaProforma)dd).setNbEDocument(((TaProforma)ds).getNbEDocument());
			((TaProforma)dd).setTtc(((TaProforma)ds).getTtc());
			//((TaProforma)dd).setExport(((TaProforma)ds).getExport());
			((TaProforma)dd).setCommentaire(((TaProforma)ds).getCommentaire());

			for (ILigneDocumentTiers ligne : ((TaProforma)ds).getLignes()) {
				TaLProforma temp = (TaLProforma)copieLigneDocument(ligne, new TaLProforma()); 
				temp.setTaDocument(((TaProforma)dd));
				((TaProforma)dd).addLigne(temp);
			}

			TaInfosProforma infos = (TaInfosProforma)copieInfosDocument(((TaProforma)ds).getTaInfosDocument(),new TaInfosProforma());
			infos.setTaDocument(((TaProforma)dd));
			((TaProforma)dd).setTaInfosDocument(infos);

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
		
		TaProforma documentInit = ((TaProforma)ds);
		try {
			TaProformaDAO daoDocumentFinal = new TaProformaDAO();
			if(documentInit!=null){
				documentInit.setLegrain(true);
//				dd=copieDocument(ds,dd);
				((TaProforma)dd).setCodeDocument(daoDocumentFinal.genereCode());
				((TaProforma)dd).setCommentaire(commentaire);
				
				((TaProforma)dd).calculeTvaEtTotaux();	
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
