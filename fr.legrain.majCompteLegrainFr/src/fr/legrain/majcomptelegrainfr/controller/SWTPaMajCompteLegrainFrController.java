package fr.legrain.majcomptelegrainfr.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbon;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.majcomptelegrainfr.divers.FonctionGeneral;
import fr.legrain.majcomptelegrainfr.ecrans.PaMajCompteLegrainFr;
import fr.legrain.majcomptelegrainfr.preferences.PreferenceInitializerProject;


public class SWTPaMajCompteLegrainFrController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaMajCompteLegrainFrController.class.getName());		
	private PaMajCompteLegrainFr vue = null; // vue	
	private TaSupportAbonDAO dao = null;//new TaFactureDAO();
	private TaAbonnementDAO daoAbonnement = null;
	private Object ecranAppelant = null;
    private FileWriter fw = null;
    private BufferedWriter bw = null;
	private LgrModifyListener tfModifyListener = new LgrModifyListener();
	private String finDeLigne = "\r\n";
	private FonctionGeneral fonctionEnvoie=new FonctionGeneral();
	
	public SWTPaMajCompteLegrainFrController(PaMajCompteLegrainFr vue) {
		this(vue,null);
	}

	public SWTPaMajCompteLegrainFrController(PaMajCompteLegrainFr vue,EntityManager em) {
		try{
			initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		dao = new TaSupportAbonDAO(getEm());
		daoAbonnement = new TaAbonnementDAO(getEm());
		
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);

			vue.getShell().addTraverseListener(new Traverse());

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		}finally{
			initCursor(SWT.CURSOR_ARROW);
		}
	}


	private class LgrModifyListener implements ModifyListener, SelectionListener{

		public void modifyText(ModifyEvent e) {
			modif(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			modif(e);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			modif(e);
		}
	}

	private void modif(TypedEvent e) {
		try {
			
		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(((AbstractLgrDAO) dao));
			

			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);


			initEtatBouton();
			
		} catch (Exception e) {
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.layout(true);
	}


	public Composite getVue() {return vue;}

	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			param.setFocus(((AbstractLgrDAO) dao).getModeObjet().getFocusCourant());
		}
		return null;
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {
	}

	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {
		super.initEtatBouton();
		boolean trouve =false;

		trouve = daoStandard.selectCount()>0;

		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
		case C_MO_INSERTION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_EDITION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_CONSULTATION:
			actionInserer.setEnabled(!trouve);
			actionModifier.setEnabled(trouve);
			actionEnregistrer.setEnabled(false);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(true);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		default:
			break;
		}

		initFocusSWT(((AbstractLgrDAO) dao),mapInitFocus);
	}	


	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();

		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();


//		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getBtnCreation());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getBtnCreation());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getBtnCreation());

		vue.getBtnCreation().addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actCreerFichierEtEnvoie();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
		vue.getBtnMAJ().addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actMiseAJourComptes();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});		
	}

	

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
//		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
//		initCommands();
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaMajCompteLegrainFrController getThis(){
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}


	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
//		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception{}

	@Override
	protected void actModifier() throws Exception{}

	@Override
	protected void actSupprimer() throws Exception{}

	@Override
	protected void actFermer() throws Exception {
		if (onClose()) {
			closeWorkbenchPart();
		}
	}

	@Override
	protected void actAnnuler() throws Exception{
		if (focusDansEcran())actionFermer.run();
	}

	@Override
	protected void actImprimer() throws Exception{
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}


	@Override
	protected void actAide(String message) throws Exception {
}
	@Override
	protected void actEnregistrer() throws Exception{
		try {
			actMiseAJourComptes();
		} catch (Exception e) {
			MessageDialog.openInformation(vue.getShell(), "Mise à jour des comptes Legrain.fr", "Abandon");
			dao.getEntityManager().getTransaction().rollback();
			logger.error("", e);
		}

	}

	public AbstractLgrDAO getDao() {
		return ((AbstractLgrDAO) dao);
	}

	@Override
	public void initEtatComposant() {}


	@Override
	protected void actRefresh() throws Exception {
	}

	@Override
	protected void initMapComposantDecoratedField() {
	}

	@Override
	protected void sortieChamps() {


	}
	
public void actCreerFichierEtEnvoie() {

	//Boucle sur tous les supports pour récupérer une liste
	boolean accept=false;
	PreferenceInitializerProject.initDefautProperties();
	Date newDate=LibDate.incrementDate(new Date(), 0, 1, -5);
	List<TaSupportAbon> liste= dao.selectSupportPourMiseAJourCompteLegrainFr();
	List<SWTSupportAbon> listeFinale = new LinkedList<SWTSupportAbon>();
	for (TaSupportAbon taSupportAbon : liste) {
		SWTSupportAbon support = new SWTSupportAbon();
		support.setCodeSupportAbon(taSupportAbon.getCodeSupportAbon());
		support.setCodeTiers(taSupportAbon.getTaTiers().getCodeTiers());
		support.setIdTSupport(taSupportAbon.getTaTSupport().getIdTSupport());
		if(taSupportAbon.getTaAbonnements().size()>0){
			TaAbonnement abon= daoAbonnement.selectDernierAbonnement(taSupportAbon.getIdSupportAbon());
			if(abon!=null){
				if(abon.getDateFin().compareTo(newDate)>0){
					accept=true;
				}
				support.setDateDebAbon(abon.getDateDebut());
				support.setDateFinAbon(abon.getDateFin());
			}
			if(taSupportAbon.getTaArticle()!=null)
				support.setCodeArticle(taSupportAbon.getTaArticle().getCodeArticle());	
		}
		if(accept)listeFinale.add(support);
		accept=false;
	}
//	String cheminFichier=Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/FichierSupports.csv";
	
	fonctionEnvoie.getInfosPreferencesProject();
	
	try {
		fw = new FileWriter(fonctionEnvoie.getCheminFichier());

//	File reportFile = new File(cheminFichier);
//	setLocationFichier(reportFile.getAbsolutePath());
	bw = new BufferedWriter(fw);
	for (SWTSupportAbon swtSupportAbon : listeFinale) {
		String deb="";
		String fin="";
		deb=LibDate.getAnnee(swtSupportAbon.getDateDebAbon())+"-"+LibDate.getMois(swtSupportAbon.getDateDebAbon())+"-"+LibDate.getJour(swtSupportAbon.getDateDebAbon());	
		fin=LibDate.getAnnee(swtSupportAbon.getDateFinAbon())+"-"+LibDate.getMois(swtSupportAbon.getDateFinAbon())+"-"+LibDate.getJour(swtSupportAbon.getDateFinAbon());
		fw.write(swtSupportAbon.getCodeTiers()+";"+
	swtSupportAbon.getCodeSupportAbon()+";"+
				swtSupportAbon.getIdTSupport()+";"+
				deb+";"+
				fin+";"+
				swtSupportAbon.getCodeArticle());
		fw.write(finDeLigne);
	}
	fw.close();
	bw.close();
	//fonctionEnvoie.setCheminFichier("C:/LGRDOSS/BureauDeGestion/tmp/majSupports.csv");

		PlatformUI.getWorkbench().getProgressService().run(true, false, fonctionEnvoie);
		if(fonctionEnvoie.isFlagSauvegardeFTP()){
			MessageDialog.openInformation(vue.getShell(), "Transfert du fichier", "Le transfert est terminée. Vous pouvez effectuer la mise à jour.");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("", e);
		MessageDialog.openInformation(vue.getShell(), "Transfert du fichier", "Le transfert a échoué.");
	} 
	
}



public static String get(String url) throws IOException{ 
String source ="";
URL oracle = new URL(url);
URLConnection yc = oracle.openConnection();
BufferedReader in = new BufferedReader(
new InputStreamReader(
yc.getInputStream()));
String inputLine;
 
while ((inputLine = in.readLine()) != null)
source +=inputLine;
in.close();
return source;
}

public void actMiseAJourComptes(){
	try {	
//	String url="http://moncompte.legrain.fr/scripts/maj_supports.php?verif=IM4ooXa1Aey7xoojzeY6Quie";
//	String url="http://www.perdu.com?pseudonyme=Idleman&pasword=danstesreves";
		fonctionEnvoie.getInfosPreferencesProject();
//	String message = get(fonctionEnvoie.getUrl());
	
	//MessageDialog.openInformation(vue.getShell(), "retour mise à jour", message) ;
	final String finalURL = fonctionEnvoie.getUrl()+"&date="+new Date().getTime();
	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		public void run() {
			try {
				PlatformUI.getWorkbench().getBrowserSupport()
				.createBrowser(
						IWorkbenchBrowserSupport.AS_EDITOR,
						"myId",
						"Mise à jour des comptes legrain.fr",//finalURL,
						finalURL
				).openURL(new URL(finalURL));

			} catch (PartInitException e) {
				logger.error("",e);
			} catch (MalformedURLException e) {
				logger.error("",e);

			}
		}	
	});	
	} catch (Exception e) {
		logger.error("", e);
	}
}

}
