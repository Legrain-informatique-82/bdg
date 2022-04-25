package fr.legrain.generationdocument.divers;

import javax.persistence.EntityTransaction;

import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
import fr.legrain.documents.dao.TaInfosAvisEcheance;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;

public abstract  class AbstractGenereDocVersAvisEcheance extends AbstractGenereDoc{
	
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosAvisEcheance> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosAvisEcheance>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosAvisEcheance> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosAvisEcheance>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosAvisEcheance> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosAvisEcheance>();
	private LgrDozerMapper<IHMIdentiteTiers,TaInfosAvisEcheance> mapperUIToModelIHMIdentiteTiersVersInfosDoc = new LgrDozerMapper<IHMIdentiteTiers, TaInfosAvisEcheance>();

	private LgrDozerMapper<TaAvisEcheance,TaInfosAvisEcheance> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaAvisEcheance, TaInfosAvisEcheance>();	
	
	@Override
	public void enregistreNouveauDocumentSpecifique(IDocumentTiers dd) {
		try {
			if(dd!=null) {
				TaAvisEcheanceDAO daoDocumentFinal = new TaAvisEcheanceDAO();
				EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
				daoDocumentFinal.begin(transaction);
				daoDocumentFinal.inserer(((TaAvisEcheance)dd));				
				((TaAvisEcheance)dd).calculeTvaEtTotaux();	

				daoDocumentFinal.enregistrerMerge(((TaAvisEcheance)dd));		
				daoDocumentFinal.commit(transaction);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void initInfosDocument(IDocumentTiers dd) {
		if(((TaAvisEcheance)dd).getTaInfosDocument()==null) {
			((TaAvisEcheance)dd).setTaInfosDocument(new TaInfosAvisEcheance());
		}
	}

	@Override
	public void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseFactVersInfosDoc.map(infos, ((TaAvisEcheance)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseLivVersInfosDoc.map(infos, ((TaAvisEcheance)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelCPaiementVersInfosDoc.map(infos, ((TaAvisEcheance)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelDocumentVersInfosDoc.map(((TaAvisEcheance)dd), ((TaAvisEcheance)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelIHMIdentiteTiersVersInfosDoc.map(infos, ((TaAvisEcheance)dd).getTaInfosDocument());
	}
}
