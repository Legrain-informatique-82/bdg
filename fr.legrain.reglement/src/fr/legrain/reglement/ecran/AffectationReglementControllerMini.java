//package fr.legrain.reglement.ecran;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//
//import org.apache.log4j.Logger;
//import org.eclipse.core.commands.IHandler;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//
//import fr.legrain.documents.dao.TaFacture;
//import fr.legrain.documents.dao.TaFactureDAO;
//import fr.legrain.documents.dao.TaRReglement;
//import fr.legrain.documents.dao.TaRReglementDAO;
//import fr.legrain.documents.dao.TaReglement;
//import fr.legrain.documents.dao.TaReglementDAO;
//import fr.legrain.gestCom.Appli.IlgrMapper;
//import fr.legrain.gestCom.Appli.LgrDozerMapper;
//import fr.legrain.gestCom.Appli.ModelGeneralObjet;
//import fr.legrain.gestCom.Module_Document.IHMReglement;
//import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
//import fr.legrain.lib.data.ModelObject;
//import fr.legrain.tiers.dao.TaTiers;
//import fr.legrain.tiers.dao.TaTiersDAO;
//
//public class AffectationReglementControllerMini extends AbstractControllerMini {
//	
//	static Logger logger = Logger.getLogger(AffectationReglementControllerMini.class.getName());	
//	
//	private Class objetIHM = null;
////	private Object selectedObject = null;
//	
//	private TaReglement masterEntity = null;
//	private TaReglementDAO masterDAO = null;
//	
//	//private List<ModelObject> modele = null;
//	private ModelGeneralObjet<IHMReglement,TaReglementDAO,TaReglement> modele = null;
//	
//	private SWTPaReglementMultipleController masterController = null;
//	
//	private PaAffectationReglement vue = null;
//	
//	private LgrDozerMapper<IHMReglement,TaReglement> mapperUIToModel  = new LgrDozerMapper<IHMReglement,TaReglement>();
//	private LgrDozerMapper<TaReglement,IHMReglement> mapperModelToUI  = new LgrDozerMapper<TaReglement,IHMReglement>();
//	
//	
//	public AffectationReglementControllerMini(SWTPaReglementMultipleController masterContoller, PaAffectationReglement vue, EntityManager em) {
//		this.vue = vue;
//		this.masterController = masterContoller;
//	}
//	
//	public void initialiseModelIHM(TaReglement masterEntity,TaReglementDAO masterDAO) {
//		//setSelectedObject(masterController.new MapperIdentite().entityToDto(masterEntity));
//		setSelectedObject(mapperModelToUI.map(masterEntity,IHMReglement.class));
//		
//		masterDAO = new TaReglementDAO();
//		
//		modele = new ModelGeneralObjet<IHMReglement,TaReglementDAO,TaReglement>(masterDAO,IHMReglement.class);
//	}
//	
//	@Override
//	public void bind(){
//		if(mapComposantChamps==null) {
//			initMapComposantChamps();
//		}
//		
//		masterDAO = new TaReglementDAO();
//		modele = new ModelGeneralObjet<IHMReglement,TaReglementDAO,TaReglement>(masterDAO,IHMReglement.class);
//		
//		
//		setObjetIHM(IHMReglement.class);
//		//bindForm(mapComposantChamps, IHMReglement.class, getSelectedObject(), vue.getDisplay());
//		
//		/*selectedReglement = lgrDatabindingUtil.*/
//		bindTable(vue.getTableReglementNonAffecte(),modele.remplirListe(),IHMReglement.class,
//				new String[] {"Code règlement","Code document","Date règlement","Date encaissement","Affectation"},
//				new String[] {"100","250","100","150","150"},
//				new String[] {"codeReglement","codeDocument","dateReglement","dateEncaissement","affectation"}
//		);
//	}
//
//
//	@Override
//	protected void initMapComposantChamps() {
//		mapComposantChamps = new HashMap<Control, String>();
////		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelCode(), "codeTiers");
////		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelNom(), "nomTiers");
////		mapComposantChamps.put(vue.getCompositeSectionIdentite().getLabelPrenom(), "prenomTiers");
//
//	}
//	
//	@Override
//	protected void initActions() {
////		mapCommand = new HashMap<String, IHandler>();
////		
////		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
////		
////		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
////		
////		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
////
//////		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
//////		mapActions.put(null, tabActionsAutres);
//	}
//
//}
