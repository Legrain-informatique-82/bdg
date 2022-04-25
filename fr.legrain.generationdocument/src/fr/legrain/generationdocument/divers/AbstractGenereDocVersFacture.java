package fr.legrain.generationdocument.divers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;

public abstract  class AbstractGenereDocVersFacture extends AbstractGenereDoc{
	
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosFacture> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosFacture> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosFacture> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosFacture>();
	private LgrDozerMapper<IHMIdentiteTiers,TaInfosFacture> mapperUIToModelIHMIdentiteTiersVersInfosDoc = new LgrDozerMapper<IHMIdentiteTiers, TaInfosFacture>();

	private LgrDozerMapper<TaFacture,TaInfosFacture> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaFacture, TaInfosFacture>();	
	@Override
	public void enregistreNouveauDocumentSpecifique(IDocumentTiers dd) {
		try {
			if(dd!=null) {
				TaFactureDAO daoDocumentFinal = new TaFactureDAO(getEm());
				EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
				daoDocumentFinal.begin(transaction);
				daoDocumentFinal.inserer(((TaFacture)dd));				
				((TaFacture)dd).calculeTvaEtTotaux();	

				daoDocumentFinal.enregistrerMerge(((TaFacture)dd));		
				daoDocumentFinal.commit(transaction);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void initInfosDocument(IDocumentTiers dd) {
		if(((TaFacture)dd).getTaInfosDocument()==null) {
			((TaFacture)dd).setTaInfosDocument(new TaInfosFacture());
		}
	}

	@Override
	public void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseFactVersInfosDoc.map(infos, ((TaFacture)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseLivVersInfosDoc.map(infos, ((TaFacture)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelCPaiementVersInfosDoc.map(infos, ((TaFacture)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelDocumentVersInfosDoc.map(((TaFacture)dd), ((TaFacture)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelIHMIdentiteTiersVersInfosDoc.map(infos, ((TaFacture)dd).getTaInfosDocument());
	}


}
