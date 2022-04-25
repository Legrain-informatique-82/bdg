package fr.legrain.generationLabelEtiquette.ecrans;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.handlers.ParamWizardEtiquettes;
import fr.legrain.generationLabelEtiquette.wizard.HeadlessEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.WizardController;
import fr.legrain.generationLabelEtiquette.wizard.WizardDialogModelLabels;
import fr.legrain.generationLabelEtiquette.wizard.WizardModelLables;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrFileBundleLocator;
import fr.legrain.lib.data.IFichierDonnees;
import fr.legrain.lib.data.ISelectionLgr;
import fr.legrain.lib.data.LibDate;

public class CompositeEtiquetteArticleController {
	
	static Logger logger = Logger.getLogger(CompositeEtiquetteArticleController.class.getName());
	
	private CompositeEtiquette vue = null;
	private List<TaArticle> listeFinale = null;
	private IPreferenceStore store;
	private String clePreferenceDerniereValeur;
	private IFichierDonnees<TaArticle> donneesArticle;
	private ISelectionLgr<TaArticle> selection;
	
	public CompositeEtiquetteArticleController(CompositeEtiquette vue, List<TaArticle> listeFinale, ISelectionLgr<TaArticle> selection,
			IPreferenceStore store, String clePreferenceDerniereValeur, IFichierDonnees<TaArticle> donneesArticle) {
		this.vue = vue;
		this.listeFinale = listeFinale;
		this.donneesArticle = donneesArticle;
		
		this.store = store;
		this.clePreferenceDerniereValeur = clePreferenceDerniereValeur;
		this.selection = selection;
		
		remplissageEtiqArticle();
	}
	
	/**
	 * Remplissage de l'encadré pour le publipostage, dans le cadre d'un
	 * résultat typé tiers
	 */
	private void remplissageEtiqArticle() {
		
		GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
		WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette);
		String[] listeParamEtiquette = wizardModelLables.listeParamEtiquette(WizardController.DOSSIER_PARAM_ARTICLE);
		System.err.println(listeParamEtiquette.length);
		vue.getCbListeParamEtiquette().setItems(listeParamEtiquette);

		String dernierModeleEtiquetteUtilise = store.getString(clePreferenceDerniereValeur);

		if (!dernierModeleEtiquetteUtilise.equals("")) {
			for (int i = 0; i < listeParamEtiquette.length; i++) {
				if (listeParamEtiquette[i]
						.equals(dernierModeleEtiquetteUtilise)) {
					vue.getCbListeParamEtiquette().select(i);
				}
			}
		}

		// Initialisation des listeners
		initListenersEtiqArticle();

	}
	
	private void initListeValeur() {
		if(listeFinale==null && selection!=null && selection.getSelection()!=null) {
			listeFinale = new ArrayList<TaArticle>();
			listeFinale.addAll(selection.getSelection());
		} else if (selection!=null && selection.getSelection()!=null) {
			listeFinale.clear();
			listeFinale.addAll(selection.getSelection());
		}
	}
	
	private void initListenersEtiqArticle() {

		vue.getBtnImprimerEtiquette().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				try {
					//List<TaTiers> listeFinale = listeTiers;
//					List<TaArticle> listeFinale = listeArticleOnglet.get(item);
					initListeValeur();

					if(listeFinale!=null) {
						String repert = new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
						String cheminFichierDonnees = new File(
								Const.C_CHEMIN_REP_TMP_COMPLET + "/Etiquettes"
								+ "-"+ LibDate.dateToString(new Date(), "")
								+ ".txt").getPath();
						String modele = vue.getCbListeParamEtiquette().getText();

						// String cheminFichierMotCle = new
						// File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette",
						// "modelEtiquette")+"/motcle.properties").getPath();
						String cheminFichierMotCle = LgrFileBundleLocator
						.bundleToFile(
								generationlabeletiquette.Activator.getDefault().getBundle(),
								"/modelEtiquette/motcle_articles.properties")
								.getPath();
						
						donneesArticle.creationFichierDonnees(listeFinale,
								repert, cheminFichierDonnees,true);

						store.setValue(clePreferenceDerniereValeur,vue.getCbListeParamEtiquette().getText());

						ParamWizardEtiquettes p = null;
						p = new ParamWizardEtiquettes();

						if (!modele
								.equals(HeadlessEtiquette.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE)) {
							p.setChangeStartingPage(true);
						}

						p.setModelePredefini(modele);
						p.setModeIntegre(true);
						p.setCheminFichierDonnees(cheminFichierDonnees);
						p.setCheminFichierMotsCle(cheminFichierMotCle);
						p.setType(WizardController.DOSSIER_PARAM_ARTICLE);
						p.setSeparateur(";");

						// GenerationFileEtiquette generationFileEtiquette = new
						// GenerationFileEtiquette();
						// WizardModelLables wizardModelLables = new
						// WizardModelLables(generationFileEtiquette,p);
						WizardModelLables wizardModelLables = new WizardModelLables(
								new GenerationFileEtiquette());
						wizardModelLables.initParam(p);
						WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels(
								PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell(),
								wizardModelLables);
						wizardDialogModelLabels.open();
					}

				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});

		vue.getBtnModifierEtiquette().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				try {

					initListeValeur();
					
					String repert = new File(Platform.getInstanceLocation()
							.getURL().getFile()).getPath();
					String cheminFichierDonnees = new File(
							Const.C_CHEMIN_REP_TMP_COMPLET + "/Etiquettes"
									+ "-"
									+ LibDate.dateToString(new Date(), "")
									+ ".txt").getPath();
					String modele = vue.getCbListeParamEtiquette().getText();

					// String cheminFichierMotCle = new
					// File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette",
					// "modelEtiquette")+"/motcle.properties").getPath();
					String cheminFichierMotCle = LgrFileBundleLocator
							.bundleToFile(
									generationlabeletiquette.Activator
											.getDefault().getBundle(),
									"/modelEtiquette/motcle_articles.properties")
							.getPath();
					
					donneesArticle.creationFichierDonnees(listeFinale,
							repert, cheminFichierDonnees,true);
					// creationFichierDonnees(listeFinale,repert,cheminFichierDonnees);

					ParamWizardEtiquettes p = null;
					p = new ParamWizardEtiquettes();
					p.setModelePredefini(modele);
					p.setModeIntegre(true);
					p.setCheminFichierDonnees(cheminFichierDonnees);
					p.setCheminFichierMotsCle(cheminFichierMotCle);
					p.setType(WizardController.DOSSIER_PARAM_ARTICLE);
					p.setSeparateur(";");

					store.setValue(clePreferenceDerniereValeur,modele);

					GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
					WizardModelLables wizardModelLables = new WizardModelLables(
							generationFileEtiquette, p);
					wizardModelLables.initParam(p);
					WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(),
							wizardModelLables);
					wizardDialogModelLabels.open();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});

	}

}
