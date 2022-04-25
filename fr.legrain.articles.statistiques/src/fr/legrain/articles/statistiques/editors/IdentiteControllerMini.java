package fr.legrain.articles.statistiques.editors;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Control;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;

public class IdentiteControllerMini extends AbstractControllerMini {
	
	static Logger logger = Logger.getLogger(IdentiteControllerMini.class.getName());	
	
	private Class objetIHM = null;
//	private Object selectedObject = null;
	
	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private List<ModelObject> modele = null;
	
	private DefaultFormPageController masterController = null;
	
	private DefaultFormPage vue = null;
	
	
	public IdentiteControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}
	
	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
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
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeArticle");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelLibelleC(), "libelleC");
		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelLibelleL(), "libelleL");

	}

}
