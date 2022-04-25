package fr.legrain.gestCom.librairiesEcran.swt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.lib.data.ModelObject;

public abstract class AbstractControllerMini extends AbstractControllerMiniNonEditable {
	
	static Logger logger = Logger.getLogger(AbstractControllerMini.class.getName());	
	
	private Class objetIHM = null;
	private Object selectedObject = null;
	
	private List<ModelObject> modele = null;
	
	public AbstractControllerMini(){
		
	}
	
	public AbstractControllerMini(Composite vue, EntityManager em, Map<Control, String> mapComposantChamps) {
		super(vue, em, mapComposantChamps);
		this.mapComposantChamps = mapComposantChamps;
		this.vue = vue;
		
	}
	
	public void initialiseComposite() {
		
	}
	
	
	public void bind(){
		bindForm(mapComposantChamps, objetIHM, selectedObject, vue.getDisplay());
	}

	public Class getObjetIHM() {
		return objetIHM;
	}

	public void setObjetIHM(Class objetIHM) {
		this.objetIHM = objetIHM;
	}

	public Object getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}

}
