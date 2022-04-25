package fr.legrain.tiers.statistiques.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class IdentiteControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(IdentiteControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	
	public IdentiteControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
		setSelectedObject(masterController.new MapperIdentite().entityToDto(masterEntity));
	}
	
	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(DefaultFormPageController.IdentiteIHM.class);
		bindForm(mapComposantChamps, DefaultFormPageController.IdentiteIHM.class, getSelectedObject(), vue.getSectionIdentite().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");

	}

}
