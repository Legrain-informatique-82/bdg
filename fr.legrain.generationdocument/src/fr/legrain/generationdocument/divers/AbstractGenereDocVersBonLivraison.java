package fr.legrain.generationdocument.divers;

import javax.persistence.EntityTransaction;

import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;

public abstract  class AbstractGenereDocVersBonLivraison extends AbstractGenereDoc{
	
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosBonliv> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosBonliv>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosBonliv> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosBonliv>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosBonliv> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosBonliv>();
	private LgrDozerMapper<IHMIdentiteTiers,TaInfosBonliv> mapperUIToModelIHMIdentiteTiersVersInfosDoc = new LgrDozerMapper<IHMIdentiteTiers, TaInfosBonliv>();

	private LgrDozerMapper<TaBonliv,TaInfosBonliv> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaBonliv, TaInfosBonliv>();	
	
	@Override
	public void enregistreNouveauDocumentSpecifique(IDocumentTiers dd) {
		try {
			if(dd!=null) {
				TaBonlivDAO daoDocumentFinal = new TaBonlivDAO();
				EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
				daoDocumentFinal.begin(transaction);
				daoDocumentFinal.inserer(((TaBonliv)dd));				
				((TaBonliv)dd).calculeTvaEtTotaux();	

				daoDocumentFinal.enregistrerMerge(((TaBonliv)dd));		
				daoDocumentFinal.commit(transaction);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void initInfosDocument(IDocumentTiers dd) {
		if(((TaBonliv)dd).getTaInfosDocument()==null) {
			((TaBonliv)dd).setTaInfosDocument(new TaInfosBonliv());
		}
	}

	@Override
	public void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseFactVersInfosDoc.map(infos, ((TaBonliv)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseLivVersInfosDoc.map(infos, ((TaBonliv)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelCPaiementVersInfosDoc.map(infos, ((TaBonliv)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelDocumentVersInfosDoc.map(((TaBonliv)dd), ((TaBonliv)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelIHMIdentiteTiersVersInfosDoc.map(infos, ((TaBonliv)dd).getTaInfosDocument());
	}

}
