package fr.legrain.gestionDossier.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Dossier.SWTInfoEntreprise;
import fr.legrain.gestCom.Module_Tiers.SWTEmail;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.gestionDossier.editor.IdentiteEntrepriseTiersMultiPageEditor;
import fr.legrain.gestionDossier.wizards.PaInfoEntrepriseSimpleSWT;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.editor.TiersMultiPageEditor;
import fr.legrain.tiers.preferences.PreferenceConstants;


public class SWTPaInfoEntrepriseSimpleController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaInfoEntrepriseSimpleController.class.getName());
	private PaInfoEntrepriseSimpleSWT vue = null;
	//private SWT_IB_TA_INFO_ENTREPRISE ibTaTable = new SWT_IB_TA_INFO_ENTREPRISE();
	private TaInfoEntrepriseDAO dao = null;//new TaInfoEntrepriseDAO();

	private Object ecranAppelant = null;
	private SWTInfoEntreprise swtInfoEntreprise;
	private SWTInfoEntreprise swtOldInfoEntreprise;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTInfoEntreprise.class;
	//	private ModelGeneral<SWTInfoEntreprise> modelInfoEntreprise = new ModelGeneral<SWTInfoEntreprise>(dao.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTInfoEntreprise,TaInfoEntrepriseDAO,TaInfoEntreprise> modelInfoEntreprise = null;// new ModelGeneralObjet<SWTInfoEntreprise,TaInfoEntrepriseDAO,TaInfoEntreprise>(dao,classModel);
	private Object selectedInfoEntreprise = new SWTInfoEntreprise();

	private MapChangeListener changeListener = new MapChangeListener();

	private LgrDozerMapper<SWTInfoEntreprise,TaInfoEntreprise> mapperUIToModel = new LgrDozerMapper<SWTInfoEntreprise,TaInfoEntreprise>();
	private LgrDozerMapper<TaInfoEntreprise,SWTInfoEntreprise> mapperModelToUI = new LgrDozerMapper<TaInfoEntreprise,SWTInfoEntreprise>();
	private TaInfoEntreprise taInfoEntreprise = null;
	
	public static final String C_COMMAND_DETAIL_INFOS_ENTREPRISE_ID = "fr.legrain.gestionCommerciale.infosentreprise.detail";
	public static final String C_COMMAND_DEVERROUILLE_INFOS_ENTREPRISE_ID = "fr.legrain.gestionCommerciale.infos.deverrouille";
	private HandlerDetail handlerDetail = new HandlerDetail();
	private HandlerDeverrouille handlerDeverrouille = new HandlerDeverrouille();
	
	public SWTPaInfoEntrepriseSimpleController(PaInfoEntrepriseSimpleSWT vue) {
		this(vue,null);
	}

	public SWTPaInfoEntrepriseSimpleController(PaInfoEntrepriseSimpleSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaInfoEntrepriseDAO(getEm());
		modelInfoEntreprise = new ModelGeneralObjet<SWTInfoEntreprise,TaInfoEntrepriseDAO,TaInfoEntreprise>(dao,classModel);
		setVue(vue);

		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());
		vue.getDateTimeDATEDEB_INFO_ENTREPRISE().addTraverseListener(new DateTraverse());
		vue.getDateTimeDATEFIN_INFO_ENTREPRISE().addTraverseListener(new DateTraverse());
		
		vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().addTraverseListener(new DateTraverse());
		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().addTraverseListener(new DateTraverse());

		vue.getDateTimeDATEDEB_INFO_ENTREPRISE().addFocusListener(dateFocusListener);
		vue.getDateTimeDATEFIN_INFO_ENTREPRISE().addFocusListener(dateFocusListener);
		
		vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().addFocusListener(dateFocusListener);
		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().addFocusListener(dateFocusListener);

		LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE());
		LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE());
		
		//		vue.getDateTimeDATEDEB_INFO_ENTREPRISE().addSelectionListener(new SelectionListener(){
		//			public void widgetDefaultSelected(SelectionEvent e) {
		//				// TODO Auto-generated method stub
		//				try{
		////					if(ibTaTable.getFModeObjet().getMode()!=ModeObjet.EnumModeObjet.C_MO_EDITION ){
		////						if(((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEDEB_INFO_ENTREPRISE().getSelection()!=null){
		////							if(((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEFIN_INFO_ENTREPRISE().getSelection().equals(
		////									((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEDEB_INFO_ENTREPRISE().getSelection())){
		////								((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEFIN_INFO_ENTREPRISE().setSelection(LibDate.
		////										incrementDate(((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEDEB_INFO_ENTREPRISE().getSelection(), -1, 0, 1));
		////								((SWTInfoEntreprise)selectedInfoEntreprise).setDATEFIN_INFO_ENTREPRISE(((PaInfoEntrepriseSimpleSWT)getVue()).getDateTimeDATEFIN_INFO_ENTREPRISE().getSelection());
		////							}
		////						}
		////					}
		//				}catch (Exception e1) {
		//					logger.error("", e1);
		//				}
		//			}
		//
		//			public void widgetSelected(SelectionEvent e) {
		//				widgetDefaultSelected(e);				
		//			}			
		//		});
		initController();
	}

	private void initController() {
		try {
			setDaoStandard(dao);
			setDbcStandard(dbc);
			desactiveChampIdentiteEntreprise();
			
			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);
			
			vue.getBtnDetail().setVisible(false);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);

			initEtatBouton();

//			desactiveChampIdentiteEntreprise();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}
	
	private void desactiveChampIdentiteEntreprise() {
		vue.getTfNOM_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfADRESSE1_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfADRESSE2_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfADRESSE3_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfCODEPOSTAL_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfVILLE_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfPAYS_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfTEL_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfFAX_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfSIRET_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfAPE_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfRCS_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfTVA_INTRA_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfCAPITAL_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfEMAIL_INFO_ENTREPRISE().setEnabled(false);
		vue.getTfWEB_INFO_ENTREPRISE().setEnabled(false);
		vue.getBtnDetail().setEnabled(false);
		
		//La date de debut de gestion des relances est pour l'instant invisible et identique à celle
		//de debut de gestion des reglements.
//		vue.getLaDATEDEB_REL_INFO_ENTREPRISE().setVisible(false);
//		vue.getCbActiveDateDebRel().setVisible(false);
//		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().setVisible(false);
//		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().setEnabled(false);
		
		vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().setEnabled(false);
		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().setEnabled(false);
		vue.getCbActiveDateDebRel().setEnabled(false);
		
		LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE());
		
		SelectionListener s = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(LibDate.compareTo(LibDateTime.getDate(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE()),
						LibDateTime.getDate(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE())) < 0) {
					//erreur, dateReg < dateRel
					LibDateTime.setDate(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE(),LibDateTime.getDate(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE()));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		};
		
		vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().addSelectionListener(s);
		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().addSelectionListener(s);
		
		final String message = "Attention, cette valeur ne peut être saisie qu'une seule fois. \n" +
		"Il vous sera impossible de la modifier par la suite. \n" +
		"Etes vous sûr de vouloir continuer ?";
		
		final MessageDialog m = new MessageDialog(vue.getShell(), "Attention", null,
				message, MessageDialog.WARNING, new String[]{"Oui","Non"}, 1);
		
		vue.getCbActiveDateDebReg().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(LibDateTime.isDateNull(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE())) {
					int result = m.open();
					if(result==1) {
						vue.getCbActiveDateDebReg().setSelection(false);
					} else {
						enabledDateReg(true);
					}
				} else {
					//vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().setSelection(null);
					LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		vue.getCbActiveDateDebRel().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(LibDateTime.isDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE())) {
					int result = m.open();
					if(result==1) {
						vue.getCbActiveDateDebRel().setSelection(false);
					} else {
						enabledDateRel(true);
					}
				} else {
					//vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().setSelection(null);
					LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private void enabledDateReg(boolean enabled) {
		vue.getCbActiveDateDebReg().setEnabled(enabled);
		vue.getLaDATEDEB_REG_INFO_ENTREPRISE().setEnabled(enabled);
		vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().setEnabled(enabled);
		vue.getCbActiveDateDebRel().setEnabled(enabled);
	}
	
	private void enabledDateRel(boolean enabled) {
		vue.getLaDATEDEB_REL_INFO_ENTREPRISE().setEnabled(enabled);
		vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().setEnabled(enabled);
	}
	
	/**
	 * 
	 * @param forceDeverrouillage - si vrai, dans tous les cas, les dates ne seront pas vérouille<br>
	 * si faux, le verrouillage dépend de la présence ou non de valeur.
	 */
	private void initVerrouDate(boolean forceDeverrouillage) {
		enabledDateReg(false);
//		if(LibDate.compareTo(new Date(), vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().getSelection()) != 0) {
		if(LibDateTime.isDateNull(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE())) {
//		if(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE().getSelection()!=null) {
			vue.getCbActiveDateDebReg().setEnabled(true);
		} else {
			//verrouiller complètement => date deja saisie
			vue.getCbActiveDateDebReg().setEnabled(false);
			vue.getCbActiveDateDebReg().setSelection(true);
		}

		enabledDateRel(false);
//		if(LibDate.compareTo(new Date(), vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().getSelection()) != 0) {
		if(LibDateTime.isDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE())) {
//		if(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE().getSelection()!=null) {
			vue.getCbActiveDateDebRel().setEnabled(true);
		} else {
			//verrouiller complètement => date deja saisie
			vue.getCbActiveDateDebRel().setEnabled(false);
			vue.getCbActiveDateDebRel().setSelection(true);
		}
		
		if(forceDeverrouillage) {
			enabledDateReg(true);
			enabledDateRel(true);
		}
		
	}
	
	@Override
	protected void initImageBouton() {
		super.initImageBouton();
		String imageDetail = "/icons/sitemap.png";
		vue.getBtnDetail().setImage(GestionDossierPlugin.getImageDescriptor(imageDetail).createImage());
		vue.layout(true);
	}

	public void bind(PaInfoEntrepriseSimpleSWT paTVASWT) {
		try {
			modelInfoEntreprise = new ModelGeneralObjet<SWTInfoEntreprise,TaInfoEntrepriseDAO,TaInfoEntreprise>(dao,classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			modelInfoEntreprise.remplirListe();
			if(!modelInfoEntreprise.getListeObjet().isEmpty())
				selectedInfoEntreprise = modelInfoEntreprise.getListeObjet().getFirst();

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedInfoEntreprise,classModel);
			
			initVerrouDate(false);

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//			dao.ouvreDataset();
			if (((ParamInfoEntreprise) param).getFocusDefautSWT() != null)
				((ParamInfoEntreprise) param).getFocusDefautSWT().setFocus();

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			VerrouInterface.setVerrouille(false);
			setSwtOldInfoEntreprise();

			initEtatBouton();
			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}

			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfADRESSE1_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_ADRESSE1_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfADRESSE2_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_ADRESSE2_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfADRESSE3_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_ADRESSE3_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfAPE_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_APE_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfCAPITAL_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_CAPITAL_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfCODEPOSTAL_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_CODEPOSTAL_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfCODEXO_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_CODEXO_INTRA_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfEMAIL_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_EMAIL_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfFAX_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_FAX_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfNOM_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_NOM_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfPAYS_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_PAYS_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfRCS_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_RCS_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfSIRET_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_SIRET_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfTEL_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_TEL_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfTVA_INTRA_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_TVA_INTRA_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfVILLE_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_VILLE_INFO_ENTREPRISE,null));
		mapInfosVerifSaisie.put(vue.getTfWEB_INFO_ENTREPRISE(), new InfosVerifSaisie(new TaInfoEntreprise(),Const.C_WEB_INFO_ENTREPRISE,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();

		mapComposantChamps.put(vue.getTfNOM_INFO_ENTREPRISE(), Const.C_NOM_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfADRESSE1_INFO_ENTREPRISE(), Const.C_ADRESSE1_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfADRESSE2_INFO_ENTREPRISE(), Const.C_ADRESSE2_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfADRESSE3_INFO_ENTREPRISE(), Const.C_ADRESSE3_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfCODEPOSTAL_INFO_ENTREPRISE(), Const.C_CODEPOSTAL_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfVILLE_INFO_ENTREPRISE(), Const.C_VILLE_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfPAYS_INFO_ENTREPRISE(), Const.C_PAYS_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfTEL_INFO_ENTREPRISE(), Const.C_TEL_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfFAX_INFO_ENTREPRISE(), Const.C_FAX_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfSIRET_INFO_ENTREPRISE(), Const.C_SIRET_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfAPE_INFO_ENTREPRISE(), Const.C_APE_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfRCS_INFO_ENTREPRISE(), Const.C_RCS_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfTVA_INTRA_INFO_ENTREPRISE(), Const.C_TVA_INTRA_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfCAPITAL_INFO_ENTREPRISE(), Const.C_CAPITAL_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfEMAIL_INFO_ENTREPRISE(), Const.C_EMAIL_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfWEB_INFO_ENTREPRISE(), Const.C_WEB_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getDateTimeDATEDEB_INFO_ENTREPRISE(), Const.C_DATEDEB_INTRA_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getDateTimeDATEFIN_INFO_ENTREPRISE(), Const.C_DATEFIN_INTRA_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE(), Const.C_DATEDEB_REG_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE(), Const.C_DATEDEB_REL_INFO_ENTREPRISE);
		mapComposantChamps.put(vue.getTfCODEXO_INFO_ENTREPRISE(), Const.C_CODEXO_INTRA_INFO_ENTREPRISE);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}
		
		listeComposantFocusable.add(vue.getBtnDetail());

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		// listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue.getTfCODEXO_INFO_ENTREPRISE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue.getTfCODEXO_INFO_ENTREPRISE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue.getTfCODEXO_INFO_ENTREPRISE());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_DETAIL_INFOS_ENTREPRISE_ID, handlerDetail);

		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		mapCommand.put(C_COMMAND_DEVERROUILLE_INFOS_ENTREPRISE_ID, handlerDeverrouille);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnDetail(), C_COMMAND_DETAIL_INFOS_ENTREPRISE_ID);
		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		// mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaInfoEntrepriseSimpleController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		boolean verrouLocal=VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Entreprise.Message.Enregistrer"))) {
				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				try {
					dao.annuler(taInfoEntreprise);
				} catch (Exception e) {
					logger.error("",e);
				}

				break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				dao.initValeurIdTable(taInfoEntreprise);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedInfoEntreprise)));
				//				vue.getDisplay().asyncExec(new Runnable() {
				//					public void run() {
				//						vue.getShell().setVisible(false);
				//					}
				//				});
				retour = false;
			}
		}
		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
			fireDestroy(new DestroyEvent(dao));
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
		}
		super.retourEcran(evt);
	}

	protected void initEtatBouton() {
		//super.initEtatBouton();
		initEtatBoutonCommand();
		boolean trouve = false;
		if(modelInfoEntreprise!=null) {
			trouve = modelInfoEntreprise.getListeObjet().size()>0;
		} else {
			trouve = daoStandard.selectAll().size()>0;
		}
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,!trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			break;
		default:
			break;
		}
		initFocusSWT(dao,mapInitFocus);
	}	
	
	protected class HandlerDetail extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actDetail();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	protected void actDetail() {
		taInfoEntreprise = dao.findById(((SWTInfoEntreprise) selectedInfoEntreprise).getIdInfoEntreprise());
		LgrPartUtil.ouvreDocument(taInfoEntreprise.getTaTiers().getCodeTiers(),IdentiteEntrepriseTiersMultiPageEditor.ID_EDITOR);
	}
	
	protected class HandlerDeverrouille extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actDeverrouille();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	protected void actDeverrouille() {
		MessageDialog.openWarning(vue.getShell(), "Attention", "Déverrouillage des dates de début de gestion des règlements et des relances.");
		initVerrouDate(true);
	}

	@Override
	protected void actInserer() throws Exception {
		Date dateDeb = new Date();
		Date dateFin = new Date(); 
		dateFin=LibDate.incrementDate(dateFin, -1, 0, 1);
		boolean verrouLocal=VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldInfoEntreprise();
				swtInfoEntreprise = new SWTInfoEntreprise();
				swtInfoEntreprise.setDatedebInfoEntreprise(dateDeb);
				swtInfoEntreprise.setDatefinInfoEntreprise(dateFin);
				swtInfoEntreprise.setDatedebRelInfoEntreprise(null);
				swtInfoEntreprise.setDatedebRelInfoEntreprise(null);
//				vue.getDateTimeDATEDEB_INFO_ENTREPRISE().setSelection(dateDeb);
//				vue.getDateTimeDATEFIN_INFO_ENTREPRISE().setSelection(dateFin);
				LibDateTime.setDate(vue.getDateTimeDATEDEB_INFO_ENTREPRISE(), dateDeb);
				LibDateTime.setDate(vue.getDateTimeDATEFIN_INFO_ENTREPRISE(), dateFin);
				

				LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REL_INFO_ENTREPRISE());
				LibDateTime.setDateNull(vue.getDateTimeDATEDEB_REG_INFO_ENTREPRISE());

				
				taInfoEntreprise = new TaInfoEntreprise();
				dao.inserer(taInfoEntreprise);
				modelInfoEntreprise.getListeObjet().add(swtInfoEntreprise);
				initEtatBouton();
			}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			VerrouInterface.setVerrouille(verrouLocal);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				setSwtOldInfoEntreprise();
				taInfoEntreprise = dao.findById(((SWTInfoEntreprise) selectedInfoEntreprise).getIdInfoEntreprise());
				dao.modifier(taInfoEntreprise);

				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		//		try {
		//			VerrouInterface.setVerrouille(true);
		//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
		//					.getString("Message.Attention"), MessagesEcran
		//					.getString("Message.suppression"));
		//			else
		//				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
		//						.getString("Message.Attention"), MessagesEcran
		//						.getString("PaInfoEntrepriseSimpleController.Message.Supprimer"))) {
		//					dao.getEntityManager().getTransaction().begin();
		//					TaInfoEntreprise u = dao.findById(((SWTInfoEntreprise) selectedInfoEntreprise).getID_INFO_ENTREPRISE());
		//					dao.supprimer(u);
		//					taInfoEntreprise=null;
		//					dao.getEntityManager().getTransaction().commit();
		//					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		//					actRefresh(); //ajouter pour tester jpa
		//				}
		//		} catch (ExceptLgr e1) {
		//			vue.getLaMessage().setText(e1.getMessage());
		//			logger.error("Erreur : actionPerformed", e1);
		//		} finally {
		//			initEtatBouton();
		//			VerrouInterface.setVerrouille(false);
		//		}
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Entreprise.Message.Annuler"))) {
					dao.annuler(taInfoEntreprise);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Entreprise.Message.Annuler"))) {
					dao.annuler(taInfoEntreprise);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actionFermer.run();
				break;
			default:
				break;
			}
			// initEtatBouton();
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {
		// // TODO procedure d'impression
		// JOptionPane.showMessageDialog(vue, vue.getFieldC_A_IMPLEMENTER,
		// MessagesEcran.getString("Message.Attention"),
		// JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		initFocusSWT(dao, mapInitFocus);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaInfoEntrepriseSimpleController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
			ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
			paramAfficheAideRecherche.setMessage(message);
			// Creation de l'ecran d'aide principal
			Shell s = new Shell(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					LgrShellUtil.styleLgr);
			PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
			SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
					paAide);
			JPABaseControllerSWTStandard controllerEcranCreation = null;
			ParamAffiche parametreEcranCreation = null;
			Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

			switch ((getThis().dao.getModeObjet().getMode())) {
			case C_MO_CONSULTATION:
				break;
			case C_MO_EDITION:
			case C_MO_INSERTION:
				break;
			default:
				break;
			}

			if (paramAfficheAideRecherche.getJPQLQuery() != null) {

				PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
						SWT.NULL);
				SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
						paAideRecherche1);

				// Parametrage de la recherche
				paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
				paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
				paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
				paramAfficheAideRecherche.setShellCreation(s2);
				paAideRechercheController1.configPanel(paramAfficheAideRecherche);
				// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

				// Ajout d'une recherche
				paAideController.addRecherche(paAideRechercheController1,
						paramAfficheAideRecherche.getTitreRecherche());

				// Parametrage de l'ecran d'aide principal
				ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
				ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

				// enregistrement pour le retour de l'ecran d'aide
				paAideController.addRetourEcranListener(getThis());
				// affichage de l'ecran d'aide principal (+ ses recherches)

				dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
				//				LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
				paAideController.configPanel(paramAfficheAide);
				dbc.getValidationStatusMap().addMapChangeListener(changeListener);

			}

		} finally {
			VerrouInterface.setVerrouille(false);
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTInfoEntreprise) selectedInfoEntreprise,taInfoEntreprise);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "INFO_ENTREPRISE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_DATEFIN_INTRA_INFO_ENTREPRISE)
					|| nomChamp.equals(Const.C_DATEDEB_INTRA_INFO_ENTREPRISE)) {

				if(nomChamp.equals(Const.C_DATEFIN_INTRA_INFO_ENTREPRISE)) {
					if(taInfoEntreprise.getDatefinInfoEntreprise()!=null && taInfoEntreprise.getDatedebInfoEntreprise()!=null) {
						if(taInfoEntreprise.getDatefinInfoEntreprise().before(taInfoEntreprise.getDatedebInfoEntreprise())) {
							taInfoEntreprise.setDatefinInfoEntreprise(taInfoEntreprise.getDatedebInfoEntreprise());
						}
					}
				}

				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			} else if(nomChamp.equals(Const.C_DATEDEB_REG_INFO_ENTREPRISE)
						|| nomChamp.equals(Const.C_DATEDEB_REL_INFO_ENTREPRISE)) {
				
					if(nomChamp.equals(Const.C_DATEDEB_REG_INFO_ENTREPRISE)) {
						
//						if( (taInfoEntreprise!=null && taInfoEntreprise.changementDateDebutReglement((Date)value))
//								|| (taInfoEntreprise==null && ((SWTInfoEntreprise) selectedInfoEntreprise).getDatedebRegInfoEntreprise()!=null)
//								) {
//							System.err.println("C_DATEDEB_REG_INFO_ENTREPRISE : changé");
//							modifMode();
//						} else {
//							System.err.println("C_DATEDEB_REG_INFO_ENTREPRISE pas changé");
//						}
						
//						if(taInfoEntreprise.getDatedebRegInfoEntreprise()!=null) {
//							System.err.println("C_DATEDEB_REG_INFO_ENTREPRISE : "+taInfoEntreprise.getDatedebRegInfoEntreprise());
//						} else {
//							System.err.println("C_DATEDEB_REG_INFO_ENTREPRISE vide");
//						}
					}

					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				
			}else {

				TaInfoEntreprise u = new TaInfoEntreprise();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTInfoEntreprise) selectedInfoEntreprise).getIdInfoEntreprise()!=null) {
					u.setIdInfoEntreprise(((SWTInfoEntreprise) selectedInfoEntreprise).getIdInfoEntreprise());
				}

				s = dao.validate(u,nomChamp,validationContext);
			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = null;
		try {
			boolean declanchementExterne = false;
			if(sourceDeclencheCommandeController==null) {
				declanchementExterne = true;
			}
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				if(declanchementExterne) {
					ctrlTousLesChampsAvantEnregistrementSWT();
				}

				transaction = dao.getEntityManager().getTransaction();
				dao.begin(transaction);

				
				if(declanchementExterne) {
					mapperUIToModel.map((SWTInfoEntreprise) selectedInfoEntreprise,taInfoEntreprise);
				}
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
					taInfoEntreprise=dao.enregistrerMerge(taInfoEntreprise);
					modelInfoEntreprise.getListeEntity().add(taInfoEntreprise);
			}
				else taInfoEntreprise=dao.enregistrerMerge(taInfoEntreprise);
					
				
				dao.commit(transaction);
				mapperModelToUI.map(taInfoEntreprise, (SWTInfoEntreprise)selectedInfoEntreprise);
				transaction = null;

				actRefresh(); //deja present
			}
		} catch (Exception e) {
			logger.error("",e);
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			initEtatBouton();
		}

	}
	


	
	public TaInfoEntrepriseDAO getDao() {
		return dao;
	}

	public void initEtatComposant() {
		//		if (ibTaTable.getFIBQuery().isOpen()){
		//			Iterator iteChamps = mapComposantChamps.keySet().iterator();
		//			Control champ = null;
		//			while (iteChamps.hasNext()) {
		//				champ=(Control)iteChamps.next();
		//				if(champ instanceof Text)				
		//					((Text)champ).setEditable(ibTaTable.recordModifiable(ibTaTable.nomTable,ibTaTable.getChamp_Obj(ibTaTable.champIdTable)));
		//			}
		//		}
	}

	public SWTInfoEntreprise getSwtOldInfoEntreprise() {
		return swtOldInfoEntreprise;
	}

	public void setSwtOldInfoEntreprise(SWTInfoEntreprise swtOldTva) {
		this.swtOldInfoEntreprise = swtOldTva;
	}

	public void setSwtOldInfoEntreprise() {
		if (selectedInfoEntreprise != null)
			this.swtOldInfoEntreprise = SWTInfoEntreprise.copy((SWTInfoEntreprise) selectedInfoEntreprise);
	}

	public void setVue(PaInfoEntrepriseSimpleSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfADRESSE1_INFO_ENTREPRISE(), vue.getFieldADRESSE1_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfADRESSE2_INFO_ENTREPRISE(), vue.getFieldADRESSE2_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfADRESSE3_INFO_ENTREPRISE(), vue.getFieldADRESSE3_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfAPE_INFO_ENTREPRISE(), vue.getFieldAPE_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfCAPITAL_INFO_ENTREPRISE(), vue.getFieldCAPITAL_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfCODEPOSTAL_INFO_ENTREPRISE(), vue.getFieldCODEPOSTAL_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfCODEXO_INFO_ENTREPRISE(), vue.getFieldCODEXO_INFO_ENTREPRISE());
		//		mapComposantDecoratedField.put(vue.getTfDATEDEB_INFO_ENTREPRISE(), vue.getFieldDATEDEB_INFO_ENTREPRISE());
		//		mapComposantDecoratedField.put(vue.getTfDATEFIN_INFO_ENTREPRISE(), vue.getFieldDATEFIN_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfEMAIL_INFO_ENTREPRISE(), vue.getFieldEMAIL_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfFAX_INFO_ENTREPRISE(), vue.getFieldFAX_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfNOM_INFO_ENTREPRISE(), vue.getFieldNOM_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfPAYS_INFO_ENTREPRISE(), vue.getFieldPAYS_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfRCS_INFO_ENTREPRISE(), vue.getFieldRCS_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfSIRET_INFO_ENTREPRISE(), vue.getFieldSIRET_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfTEL_INFO_ENTREPRISE(), vue.getFieldTEL_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfTVA_INTRA_INFO_ENTREPRISE(), vue.getFieldTVA_INTRA_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfVILLE_INFO_ENTREPRISE(), vue.getFieldVILLE_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfWEB_INFO_ENTREPRISE(), vue.getFieldWEB_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getDateTimeDATEDEB_INFO_ENTREPRISE(), vue.getFieldDATEDEB_INFO_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getDateTimeDATEFIN_INFO_ENTREPRISE(), vue.getFieldDATEFIN_INFO_ENTREPRISE());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere
		//		if(vue.getDateTimeDATEFIN_INFO_ENTREPRISE().equals(getFocusCourantSWT())){
		//			if(vue.getDateTimeDATEFIN_INFO_ENTREPRISE().getSelection().before(
		//					vue.getDateTimeDATEDEB_INFO_ENTREPRISE().getSelection()))
		//				try {
		//					dao.affecte(Const.C_DATEFIN_INTRA_INFO_ENTREPRISE, LibDate.dateToStringAA(vue.getDateTimeDATEDEB_INFO_ENTREPRISE().getSelection()));
		//				} catch (Exception e) {
		//					logger.error("",e);
		//				}				
		//		}
	}

	public boolean isUtilise(){
		return ((SWTInfoEntreprise)selectedInfoEntreprise).getIdInfoEntreprise()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTInfoEntreprise)selectedInfoEntreprise).getIdInfoEntreprise());		
	}

	@Override
	protected void actRefresh() throws Exception {

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			
		} else {
			if (taInfoEntreprise!=null && selectedInfoEntreprise!=null && (SWTInfoEntreprise) selectedInfoEntreprise!=null) {
				mapperModelToUI.map(taInfoEntreprise, (SWTInfoEntreprise) selectedInfoEntreprise);
			}
		}

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taInfoEntreprise!=null) { //enregistrement en cours de modification/insertion
			idActuel = taInfoEntreprise.getIdInfoEntreprise();
		} else if(selectedInfoEntreprise!=null && (SWTInfoEntreprise) selectedInfoEntreprise!=null) {
			idActuel = ((SWTInfoEntreprise) selectedInfoEntreprise).getIdInfoEntreprise();
		}			

	}



}
