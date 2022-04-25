package fr.legrain.generationLabelEtiquette.wizard;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.divers.ParameterEtiquette;
import fr.legrain.gestCom.Appli.Const;

public class HeadlessEtiquette {
	
	static Logger logger = Logger.getLogger(HeadlessEtiquette.class.getName());
	
	public static final String C_COMMAND_IMPRIMER_ETIQUETTE_ID = "fr.legrain.GenerationLabelEtiquette.imprimer";
	
	public static final String C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_TYPE_ID = "GenerationLabelEtiquette.commandParameter3";
	public static final String C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_MODELE_ID = "GenerationLabelEtiquette.commandParameter4";
	
	public static final String CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE = ConstantModelLabels.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE;
	public static final String CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE = ConstantModelLabels.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE;
	
	private GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
	private ParameterEtiquette parameterEtiquette = null;
	
//	public void test() {
//		lectureParam("test_45");
//		getParameterEtiquette().setPathFileExtraction("/home/nicolas/public/lgrdoss/BureauDeGestion/Etiquettes-07012011.txt");
////		getParameterEtiquette().setPathFileMotCle(null);
//		print();
//	}
	
	public void lectureParam(String nomModeleEtiquette) {
		generationFileEtiquette.arrayPathFileParamEtiquette(Const.PATH_FOLDER_PARAMETRES_ETIQUETTE);
		parameterEtiquette = generationFileEtiquette.readObjectCastor(generationFileEtiquette.getMapFileParamEtiquette().get(nomModeleEtiquette));
	}

	public void print() {
		
		//repertoire de travail pour creer le fichier birt .rptdesign
		String pathFolderSaveEtiquete = Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP;
		
		//creation d'un nouveau fichier .rptdesign
		final String pathFileEtiquette = generationFileEtiquette.getPathFileEtiquette(pathFolderSaveEtiquete);
		generationFileEtiquette.initializeBuildDesignReportConfig(pathFileEtiquette);
		
		//initialisation de la master page
		generationFileEtiquette.makeMasterPage(parameterEtiquette.getLeftMargin(), parameterEtiquette.getRightMargin(),
				parameterEtiquette.getTopMargin(), parameterEtiquette.getBottomMargin(),
				parameterEtiquette.getLargeurPapier(), parameterEtiquette.getHauteurPapier(), parameterEtiquette.getTypePaper());
		
		final int nombreColumnsPage = parameterEtiquette.getNombreColumns().intValue();
		final int nombreRowsPage = parameterEtiquette.getNombreRows().intValue();

		//Calcul des dimension réelle de la grille BIRT qui contiendra les cellules représentant les étiquettes
		final Float heightGrid = (parameterEtiquette.getHauteurPapier()-parameterEtiquette.getTopMargin() - parameterEtiquette.getBottomMargin());
		final Float widthGrid = (parameterEtiquette.getLargeurPapier()-parameterEtiquette.getLeftMargin() - parameterEtiquette.getRightMargin());
		
		//Calcul de la taille des étiquettes
		generationFileEtiquette.updateTextFormatEtiquette(parameterEtiquette.getNombreRows(),parameterEtiquette.getNombreColumns(),
				heightGrid,widthGrid,parameterEtiquette.getLargeurEspace(),parameterEtiquette.getHauteurEspace());

		//lecture du fichier contenant les données
		String pathFileExtraction = parameterEtiquette.getPathFileExtraction(); 
		generationFileEtiquette.getInfosFileExtraction(pathFileExtraction);
		
		//lecture du fichier contenant les mots clé
		generationFileEtiquette.getInfosMotCleEtiquette(parameterEtiquette.getValueSeparateur());
		generationFileEtiquette.readValueMotCleEtiquette(parameterEtiquette.getPathFileMotCle());
		
		//convertion du modèle de texte (mise en page étiquette) au format Birt (ex: remplace "\n" par "<BR>")
		generationFileEtiquette.convertStringToBirtHtml(parameterEtiquette.getTextModelEtiquette());
		
		//initialise la liste "allLinesFileExtractionFormatBirt", contient une entrée pour chaque etiquette.
		//Chaque entrée de cette liste contient le texte final d'un étiquette (mis en forme et mots clé remplacés)
		generationFileEtiquette.updateListInfosFileExtraction(parameterEtiquette.getValueSeparateur());
		final int linesFileExtraction = generationFileEtiquette.getAllLinesFileExtractionFormatBirt().size();

		String tmpStyleGras = "";
		String tmpStyleItalic = "";
		if(parameterEtiquette.isGras()){
			tmpStyleGras = "bold";
		}
		if(parameterEtiquette.isItalic()){
			tmpStyleItalic = "italic";
		}
		final String styleGras = tmpStyleGras;
		final String styleItalic = tmpStyleItalic;

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {

				Thread t = new Thread(){
					public void run() {


						Job job = new Job("Préparation de l'impression") {
							protected IStatus run(IProgressMonitor monitor) {
								//final int ticks = finalIdFactureAImprimer.length;
								monitor.beginTask("Préparation de l'impression",monitor.UNKNOWN);
								try {

									generationFileEtiquette.makePage(linesFileExtraction,nombreRowsPage,nombreColumnsPage,heightGrid.floatValue(),widthGrid.floatValue(),
											parameterEtiquette.getLargeurEspace().floatValue(),parameterEtiquette.getHauteurEspace().floatValue(),
											parameterEtiquette.getLeftPadding().floatValue(),parameterEtiquette.getRightPadding().floatValue(),parameterEtiquette.getTopPadding().floatValue(),parameterEtiquette.getBottomPadding().floatValue(),
											parameterEtiquette.isCellBorder(),parameterEtiquette.getSizeEtiquette(),styleGras,styleItalic,
											parameterEtiquette.getDecalage(),parameterEtiquette.getQuantite());

									generationFileEtiquette.savsAsDesignHandle(pathFileEtiquette);

									generationFileEtiquette.threadShowLableEtiquette(pathFileEtiquette,"Etiquettes");
								} catch (Exception e) {
									logger.error("",e);
								}
								finally {
									monitor.done();
								}
								return Status.OK_STATUS;
							}

						};
						job.setPriority(Job.SHORT);
						//job.setUser(true);
						job.schedule(); 
						try {
							job.join();
						} catch (InterruptedException e) {
							logger.error("Erreur à l'impression ",e);
						}

					}
				};
				t.start();
			}

		});
		
	}
	
	/**
	 * Génération d'un sous menu, affichant les différents type de paramètre pré-enregistrer pour les étiquettes
	 * @param menuPrincipal - menu dans lequel ce sous menu doit être intégré
	 * @param labelSousMenu - libellé à afficher sur le menuItem du sous menu
	 * @param type - constante intégrée dans les paramètres passés aux commandes du sous menu.<br> Permet par exemple de différencier le sous menu
	 * dans le cas ou celui-ci serait inclus plusieurs fois dans le même écran.<br> Ex: 1 sous menu pour les etiquettes d'adresse de facturation 
	 * et un autre pour celles de livraison.<br> Le contenu de cette variable est libre, il suffit d'utiliser la même valeur à la création du sous menu
	 * et dans le Handler associé à la commande.
	 * @return - Le sous menu créer
	 */
	public Menu menuEtiquette(Menu menuPrincipal, String labelSousMenu, String type, String typeDonnees) {
		Menu mEtiquette = new Menu(menuPrincipal);
		
		String chemin = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE;
		if(typeDonnees.equals(WizardController.DOSSIER_PARAM_TIERS)) {
			chemin+="/"+WizardController.DOSSIER_PARAM_TIERS;
		} else if(typeDonnees.equals(WizardController.DOSSIER_PARAM_ARTICLE)) {
			chemin+="/"+WizardController.DOSSIER_PARAM_ARTICLE;
		}
		
		final String[] tabTypeEtiquette = generationFileEtiquette.arrayPathFileParamEtiquette(chemin,null,new String[]{ConstantModelLabels.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE});
		
		MenuItem subMenuItem = new MenuItem(menuPrincipal,SWT.CASCADE);
		subMenuItem.setMenu(mEtiquette);
		subMenuItem.setText(labelSousMenu);
		
		IContributionItem menuItem = null;
		Map<String,String> param = null;
		for (int k = 0; k < tabTypeEtiquette.length; k++) {

			param = new HashMap<String,String>();
			param.put(C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_TYPE_ID, type);
			param.put(C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_MODELE_ID, tabTypeEtiquette[k]);
			CommandContributionItemParameter p = new CommandContributionItemParameter(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(), "id_"+tabTypeEtiquette[k], 
					C_COMMAND_IMPRIMER_ETIQUETTE_ID, 
					param, null, null, null, 
					 tabTypeEtiquette[k],null, null, SWT.PUSH, null, true);
			menuItem = new CommandContributionItem(p);

			menuItem.fill(mEtiquette,-1);
		}	
		return mEtiquette;
	}

	public ParameterEtiquette getParameterEtiquette() {
		return parameterEtiquette;
	}

	public void setParameterEtiquette(ParameterEtiquette parameterEtiquette) {
		this.parameterEtiquette = parameterEtiquette;
	}
}
