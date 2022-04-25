package fr.legrain.articles.statistiques.editors;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.statistiques.Activator;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.ModelObject;


public class ParamControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(ParamControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private List<ModelObject> modele = null;

	private DefaultFormPageController masterController = null;

	private DefaultFormPage vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;


	public ParamControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

		taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
	}

	public void initialiseModelIHM(TaArticle masterEntity,TaArticleDAO masterDAO) {
		//		setSelectedObject(new MapperIdentite().entityToDto(masterEntity));
		initActions();
	}

	private Action refreshAction = new Action("Recalculer",Activator.getImageDescriptor(DefaultFormPage.iconPath)) { 
		@Override 
		public void run() { 
			Date dateDeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			Date dateFin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin());
			logger.debug(dateDeb+"******"+dateFin);
			masterController.refreshAll();
		}
	};

	private boolean toolBarInitialise = false;

	@Override
	protected void initActions() {	
		//if(masterDAO!=null && masterEntity!=null) {
		if(!toolBarInitialise) {
			vue.getCompositeSectionParam().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionParam().getSectionToolbar().update(true);
			
			vue.getCompositeSectionParam().getBtnRefesh().setImage(Activator.getImageDescriptor(DefaultFormPage.iconRefreshPath).createImage());
			vue.getCompositeSectionParam().getBtnRefesh().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					refreshAction.run();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
//			vue.getCompositeSectionParam().getIhlRefresh().addHyperlinkListener(new HyperlinkAdapter() {
//				
//				@Override
//				public void linkActivated(HyperlinkEvent e) {
//					refreshAction.run();
//				}
//			});
			
			toolBarInitialise = true;
		}

		//initialisation des dates
//		vue.getCompositeSectionParam().getCdateDeb().setSelection(taInfoEntreprise.getDatedebInfoEntreprise());
//		vue.getCompositeSectionParam().getCdatefin().setSelection(taInfoEntreprise.getDatefinInfoEntreprise());
		LibDateTime.setDate(vue.getCompositeSectionParam().getCdateDeb(), taInfoEntreprise.getDatedebInfoEntreprise());
		LibDateTime.setDate(vue.getCompositeSectionParam().getCdatefin(), taInfoEntreprise.getDatefinInfoEntreprise());
		//		}
	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		//		setObjetIHM(IdentiteIHM.class);
		//		bindForm(mapComposantChamps, IdentiteIHM.class, getSelectedObject(), vue.getSectionIdentite().getDisplay());
	}


	@Override
	protected void initMapComposantChamps() {
		//		mapComposantChamps = new HashMap<Control, String>();
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
		//		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");

	}

}
