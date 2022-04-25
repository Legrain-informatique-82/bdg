package fr.legrain.generationdocument.divers;

import javax.persistence.EntityTransaction;

import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaInfosApporteur;
import fr.legrain.documents.dao.TaInfosDevis;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;

public abstract  class AbstractGenereDocVersApporteur extends AbstractGenereDoc{
	
	
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosApporteur> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosApporteur>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosApporteur> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosApporteur>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosApporteur> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosApporteur>();
	private LgrDozerMapper<IHMIdentiteTiers,TaInfosApporteur> mapperUIToModelIHMIdentiteTiersVersInfosDoc = new LgrDozerMapper<IHMIdentiteTiers, TaInfosApporteur>();

	private LgrDozerMapper<TaApporteur,TaInfosApporteur> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaApporteur, TaInfosApporteur>();	
	
	@Override
	public void enregistreNouveauDocumentSpecifique(IDocumentTiers dd) {
		try {
			if(dd!=null) {
				TaApporteurDAO daoDocumentFinal = new TaApporteurDAO();
				EntityTransaction transaction = daoDocumentFinal.getEntityManager().getTransaction();
				daoDocumentFinal.begin(transaction);
				daoDocumentFinal.inserer(((TaApporteur)dd));				
				((TaApporteur)dd).calculeTvaEtTotaux();	

				daoDocumentFinal.enregistrerMerge(((TaApporteur)dd));		
				daoDocumentFinal.commit(transaction);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void initInfosDocument(IDocumentTiers dd) {
		if(((TaApporteur)dd).getTaInfosDocument()==null) {
			((TaApporteur)dd).setTaInfosDocument(new TaInfosApporteur());
		}
	}

	@Override
	public void mapUIToModelAdresseFactVersInfosDoc(IHMAdresseInfosFacturation infos, IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseFactVersInfosDoc.map(infos, ((TaApporteur)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelAdresseLivVersInfosDoc(IHMAdresseInfosLivraison infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelAdresseLivVersInfosDoc.map(infos, ((TaApporteur)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelCPaiementVersInfosDoc(IHMInfosCPaiement infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelCPaiementVersInfosDoc.map(infos, ((TaApporteur)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelDocumentVersInfosDoc(IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelDocumentVersInfosDoc.map(((TaApporteur)dd), ((TaApporteur)dd).getTaInfosDocument());
	}

	@Override
	public void mapUIToModelIHMIdentiteTiersVersInfosDoc(IHMIdentiteTiers infos,IDocumentTiers dd) {
		initInfosDocument(dd);
		mapperUIToModelIHMIdentiteTiersVersInfosDoc.map(infos, ((TaApporteur)dd).getTaInfosDocument());
	}
}
