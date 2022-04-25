package fr.legrain.tiers.statistiques.editors;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.statistiques.Activator;

public class ParamControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(ParamControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private List<ModelObject> modele = null;

	private DefaultFormPageController masterContoller = null;

	private DefaultFormPage vue = null;

	private TaInfoEntrepriseDAO taInfoEntrepriseDAO = null;
	private TaInfoEntreprise taInfoEntreprise = null;


	public ParamControllerMini(DefaultFormPageController masterContoller, DefaultFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterContoller = masterContoller;

		taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
	}

	public void initialiseModelIHM(TaTiers masterEntity,TaTiersDAO masterDAO) {
		//		setSelectedObject(new MapperIdentite().entityToDto(masterEntity));
		initActions();
	}

	private Action refreshAction = new Action("Recalculer",Activator.getImageDescriptor(DefaultFormPage.iconPath)) { 
		@Override 
		public void run() { 
			Date dateDeb = LibDateTime.getDate(vue.getCompositeSectionParam().getCdateDeb());
			Date dateFin = LibDateTime.getDate(vue.getCompositeSectionParam().getCdatefin());
			logger.debug(dateDeb+"******"+dateFin);
			masterContoller.refreshAll();
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
