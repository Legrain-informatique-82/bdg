package fr.legrain.tiers.statistiques.editors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class SoldeControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(SoldeControllerMini.class.getName());	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	public SoldeControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
		double solde = 0;
		double soldeFacture = 0;
		double soldeReglement = 0;
		double soldeAvoir = 0;
		double soldeAcompte = 0;
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
		Date dateDeb=infos.getDatedebRegInfoEntreprise();
		if(dateDeb==null)dateDeb=LibDate.stringToDate2("01/01/1900");
		
		String dateFin=LibDate.dateToString(LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()));
		TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());
		List<TaFacture> listeFacture = taFactureDAO.findByCodeTiersAndDate(
				masterEntity.getCodeTiers(),
				dateDeb,
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
				);
		
		TaReglementDAO taReglementDAO = new TaReglementDAO(masterDAO.getEntityManager());
		List<TaReglement> listeReglement = taReglementDAO.rechercheDocument(
				dateDeb,
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),masterEntity.getCodeTiers()
				);
		
		TaAvoirDAO taAvoirDAO = new TaAvoirDAO(masterDAO.getEntityManager());
		List<TaAvoir> listeAvoir = taAvoirDAO.rechercheDocument(
				dateDeb,
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),masterEntity.getCodeTiers()
				);
		
		TaAcompteDAO taAcompteDAO = new TaAcompteDAO(masterDAO.getEntityManager());
		List<TaAcompte> listeAcompte = taAcompteDAO.rechercheDocument(
				dateDeb,
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin()),masterEntity.getCodeTiers()
				);

		for (TaFacture taFacture : listeFacture) {
			soldeFacture += taFacture.getNetAPayer().doubleValue();
		}
		
		for (TaReglement doc : listeReglement) {
			soldeReglement += doc.getNetTtcCalc().doubleValue();
		}
		for (TaAcompte doc : listeAcompte) {
			soldeAcompte += doc.getNetTtcCalc().doubleValue();
		}
		for (TaAvoir doc : listeAvoir) {
			soldeAvoir += doc.getNetTtcCalc().doubleValue();
		}
		
		solde=(soldeReglement+soldeAvoir+soldeAcompte) - soldeFacture;
		solde = LibCalcul.arrondi(solde);
		
		vue.getCompositeSectionSolde().getLabelSolde().setText("Solde au "+dateFin+" :");
		DefaultFormPageController.SoldeIHM ca = masterController.new SoldeIHM(new BigDecimal(String.valueOf(solde)));
		setSelectedObject(ca);
	}
	
	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DefaultFormPageController.SoldeIHM.class);
		bindForm(mapComposantChamps, DefaultFormPageController.SoldeIHM.class, getSelectedObject(), vue.getSectionSolde().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionSolde().getText(), "soldeTiers");
	}

}
