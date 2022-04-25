package fr.legrain.tiers.statistiques.editors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class CAControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(CAControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	public CAControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
		
		TaFactureDAO taFactureDAO = new TaFactureDAO(masterDAO.getEntityManager());
		List<TaFacture> listeFacture = taFactureDAO.findByCodeTiersAndDate(
				masterEntity.getCodeTiers(),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb()),
				LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin())
				);


		double caTotal = 0;

		for (TaFacture taFacture : listeFacture) {
			caTotal += taFacture.getMtHtCalc().doubleValue();
		}
		caTotal = LibCalcul.arrondi(caTotal);
		DefaultFormPageController.CAIHM ca = masterController.new CAIHM(new BigDecimal(String.valueOf(caTotal)));
		
		setSelectedObject(ca);
	}
	
	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DefaultFormPageController.CAIHM.class);
		bindForm(mapComposantChamps, DefaultFormPageController.CAIHM.class, getSelectedObject(), vue.getSectionCA().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionCA().getText(), "chiffreAffaire");
	}

}
