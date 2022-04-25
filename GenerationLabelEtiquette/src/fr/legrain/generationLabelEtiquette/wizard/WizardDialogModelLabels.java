package fr.legrain.generationLabelEtiquette.wizard;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.divers.ParameterEtiquette;
import fr.legrain.gestCom.Appli.Const;


public class WizardDialogModelLabels extends WizardDialog {


	static Logger logger = Logger.getLogger(WizardDialogModelLabels.class.getName());
	
	private WizardModelLables wizardModelLables;

	public WizardDialogModelLabels(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizardModelLables = (WizardModelLables)newWizard;
	}
	@Override
	protected void backPressed() {
		super.backPressed();
	}

	@Override
	protected void finishPressed() {
		super.finishPressed();
		WizardController wizardController = wizardModelLables.getWizardController();
		
		boolean choixSaveEtiquette = wizardController.isChoixSaveEtiquette();
		
		ParameterEtiquette parameterEtiqutte = wizardController.getParameterEtiquette();
		
		final GenerationFileEtiquette generationFileEtiquette = wizardController.getGenerationFileEtiquette();
		CompositePageOptionFormatEtiquette pageOptionFormatEtiquette = wizardController.
																		getCompositePageOptionFormatEtiquette();
		
		String valueHtmlBirt = generationFileEtiquette.convertStringToBirtHtml(
				wizardController.getTextFormatEtiquette());

		String valueSeparateur = parameterEtiqutte.getValueSeparateur();
		
		generationFileEtiquette.updateListInfosFileExtraction(valueSeparateur);

		final String pathFileEtiquette = generationFileEtiquette.getPathFileEtiquette(wizardController.getPathFolderSaveEtiquete());
		
		generationFileEtiquette.initializeBuildDesignReportConfig(pathFileEtiquette);
		
		Float leftMargin = wizardController.getMarginLeft();
		Float rightMargin = wizardController.getMarginRight();
		Float topMargin = wizardController.getMarginTop();
		Float bottomMargin = wizardController.getMarginButtom();
		
		final Float leftPadding = wizardController.getPaddingLeft();
		final Float rightPadding = wizardController.getPaddingRight();
		final Float topPadding = wizardController.getPaddingTop();
		final Float bottomPadding = wizardController.getPaddingButtom();
		
		Float largeurPapier = wizardController.getLargeurPapier();
		Float hauteurPapier = wizardController.getHauteurPapier();
		
		final Float largeurEspace = wizardController.getLargeurEspace();
		final Float hauteurEspace = wizardController.getHauteurEspace();
		
		final boolean cellBorder = wizardController.isCellBorder();
		
		String pageSize = wizardController.getModelLables();
//		if(!wizardController.isFlagChoixPreDefinionModelLables()){
//			pageSize = ConstantModelLabels.TYPE_PAPER_CUSTOM;
//		}else{
//			pageSize = wizardController.getModelLables();
//		}
		
		final String sizeEtiquette = wizardController.getSizeEtiquette();
		String tmpStyleGras = "";
		String tmpStyleItalic = "";
		if(wizardController.isGras()){
			tmpStyleGras = "bold";
		}
		if(wizardController.isItalic()){
			tmpStyleItalic = "italic";
		}
		final String styleGras = tmpStyleGras;
		final String styleItalic = tmpStyleItalic;
		
		generationFileEtiquette.makeMasterPage(leftMargin, rightMargin, topMargin, bottomMargin,
											   largeurPapier, hauteurPapier, pageSize);
		
		String pathFileExtraction = wizardController.getPathFileExtraction(); 
		generationFileEtiquette.getInfosFileExtraction(pathFileExtraction);
		
		final int LinesFileExtraction = generationFileEtiquette.getAllLinesFileExtractionFormatBirt().size();
		
		final int decalage = wizardController.getDecalage().intValue();
		final int quantite = wizardController.getQuantite().intValue();
		
		final int nombreColumnsPage = wizardController.getNombrecolumns().intValue();
		final int nombreRowsPage = wizardController.getNombreRows().intValue();
		
		final Float heightGrid = (hauteurPapier-topMargin -bottomMargin);
		final Float widthGrid = (largeurPapier-leftMargin -rightMargin);
				
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {

				Thread t = new Thread(){
					public void run() {


						Job job = new Job("Préparation de l'impression") {
							protected IStatus run(IProgressMonitor monitor) {
								//			final int ticks = finalIdFactureAImprimer.length;
								monitor.beginTask("Préparation de l'impression",monitor.UNKNOWN);
								try {

									generationFileEtiquette.makePage(LinesFileExtraction,nombreRowsPage,nombreColumnsPage,heightGrid.floatValue(),widthGrid.floatValue(),
											largeurEspace.floatValue(),hauteurEspace.floatValue(),
											leftPadding.floatValue(),rightPadding.floatValue(),topPadding.floatValue(),bottomPadding.floatValue(),
											cellBorder,sizeEtiquette,styleGras,styleItalic,decalage,quantite);

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
		/**
		 * update value defaut dans le preference
		 */
//		try {
//			generationFileEtiquette.updateValuePreference(leftMargin, rightMargin, topMargin, bottomMargin,
//					leftPadding,rightPadding,topPadding,bottomPadding,nombreColumns,nombreRows,
//					largeurEspace,hauteurEspace,cellBorder);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String nameParamEtiquette = wizardController.getNameParamEtiquette();
		String newParamEtiquette = wizardController.getNewParamEtiquette();
		
		
		boolean flagNameParamEtiquette = nameParamEtiquette.equals(newParamEtiquette);
		
		
		
		String pathFileParamEtiquette = generationFileEtiquette.getMapFileParamEtiquette().
										get(nameParamEtiquette);
		String nameEtiquette = null;
		if(choixSaveEtiquette){
			if(pathFileParamEtiquette != null){
				if(!flagNameParamEtiquette){
					File file = new File(pathFileParamEtiquette);
					file.delete();
					nameEtiquette = newParamEtiquette;
				}
				else{
					nameEtiquette = nameParamEtiquette;
				}
			}else{
				nameEtiquette = newParamEtiquette;
			}
			
			String type = wizardController.getParam().getType();
			String chemin = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE;
		if(type.equals(wizardController.DOSSIER_PARAM_TIERS)) {
			chemin+="/"+wizardController.DOSSIER_PARAM_TIERS;
		} else if(type.equals(wizardController.DOSSIER_PARAM_ARTICLE)) {
			chemin+="/"+wizardController.DOSSIER_PARAM_ARTICLE;
		} else {
			
		}
			
			pathFileParamEtiquette = chemin+"/"+nameEtiquette+
									ConstantModelLabels.TYPE_FILE_XML;
			
			parameterEtiqutte = generationFileEtiquette.updateProprityObject(
				    sizeEtiquette,wizardController.isGras(),
		            wizardController.isItalic(),nameEtiquette,
		            pathFileParamEtiquette,wizardController.getTextFormatEtiquette(),parameterEtiqutte);

			generationFileEtiquette.writeObjectCastor(parameterEtiqutte, pathFileParamEtiquette);	
		}
//		else{
//			if(pathFileParamEtiquette != null){
//			
//				nameEtiquette = nameParamEtiquette;
//			
//				pathFileParamEtiquette = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE+"/"+nameEtiquette+
//									 	 ConstantModelLabels.TYPE_FILE_XML;
//				
//				parameterEtiqutte = generationFileEtiquette.updateProprityObject(
//					    sizeEtiquette,wizardController.isGras(),
//			            wizardController.isItalic(),nameEtiquette,
//			            pathFileParamEtiquette,parameterEtiqutte);
//				generationFileEtiquette.writeObjectCastor(parameterEtiqutte, pathFileParamEtiquette);
//				
//			}
//		}
		
//		if(pageOptionFormatEtiquette != null){
//			//String pathFileParamEtiquette = parameterEtiqutte.getPathFileParamEtiquette();
//			generationFileEtiquette.writeObjectCastor(parameterEtiqutte, pathFileParamEtiquette);
//		}
	}
	
	@Override
	protected void nextPressed() {
		wizardModelLables.nextPressed((IModelWizardPage)getCurrentPage());//activer fonction Button Next
		super.nextPressed();
	}
	
	public void resize(IWizardPage p) {
		this.updateSize(p);
	}

}
