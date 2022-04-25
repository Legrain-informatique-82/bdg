package fr.legrain.generationdocument.divers;

import javax.persistence.EntityTransaction;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaInfosAcompte;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;

public abstract  class AbstractGenereDocVersAcompte extends AbstractGenereDoc{
	
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosAcompte> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosAcompte>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosAcompte> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosAcompte>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosAcompte> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosAcompte>();
	private LgrDozerMapper<IHMIdentiteTiers,TaInfosAcompte> mapperUIToModelIHMIdentiteTiersVersInfosDoc = new LgrDozerMapper<IHMIdentiteTiers, TaInfosAcompte>();

	private LgrDozerMapper<TaAcompte,TaInfosAcompte> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaAcompte, TaInfosAcompte>();	
	
	@Override
	public void enregistreNouveauDocumentSpecifique(IDocumentTiers dd) {
		try {
			if(dd!=null) {
				TaAcompteDAO daoDocumentFinal = new TaAcompteDAO();
				EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
				daoDocumentFinal.begin(transaction);
				daoDocumentFinal.inserer(((TaAcompte)dd));				
				((TaAcompte)dd).calculeTvaEtTotaux();	

				daoDocumentFinal.enregistrerMerge(((TaAcompte)dd));		
				daoDocumentFinal.commit(transaction);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void initInfosDocument(IDocumentTiers dd) {
		if(((TaAcompte)dd).getTaInfosDocument()==null) {
			((TaAcompte)dd).setTaInfosDocument(new TaInfosAcompte());
		}
	}

	@Override
	public void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseFactVersInfosDoc.map(infos, ((TaAcompte)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseLivVersInfosDoc.map(infos, ((TaAcompte)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelCPaiementVersInfosDoc.map(infos, ((TaAcompte)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelDocumentVersInfosDoc.map(((TaAcompte)dd), ((TaAcompte)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelIHMIdentiteTiersVersInfosDoc.map(infos, ((TaAcompte)dd).getTaInfosDocument());
	}
}
