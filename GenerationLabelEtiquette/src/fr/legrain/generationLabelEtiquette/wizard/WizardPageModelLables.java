package fr.legrain.generationLabelEtiquette.wizard;


import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.divers.ParameterEtiquette;

public class WizardPageModelLables extends WizardPage implements
		IModelWizardPage {

	protected WizardPageModelLables(String pageName) {
		super(pageName);
		setTitle(ConstantModelLabels.TITLE_PAGE_MODEL_LABLES);
		setTitle(ConstantModelLabels.DESCRIPTION_PAGE_MODEL_LABLES);
	}

	@Override
	public void finishPage() {
		WizardController wizardController = getController();
		
		String pathFileMotCle = wizardController.getPathFileMotCle();
		boolean choixFileMotCle  = wizardController.isChoixFileMotCle();
		
		String valueSeparateur = wizardController.getValueSeparateur();
		boolean choixSeparateur = wizardController.isChoixSeparateur();
		
		GenerationFileEtiquette generationFileEtiquette = wizardController.getGenerationFileEtiquette();
		String pathFileExtraction = wizardController.getPathFileExtraction();
		
		generationFileEtiquette.getInfosFileExtraction(pathFileExtraction);
		
		
		
		generationFileEtiquette.getInfosMotCleEtiquette(valueSeparateur);
		
		generationFileEtiquette.readValueMotCleEtiquette(pathFileMotCle);
		
		
//		generationFileEtiquette.obtainOneLineFileExtraction(generationFileEtiquette.getAllLinesFileExtraction());
		
		boolean cellBorder = wizardController.isCellBorder();
		
		CompositePageOptionFormatEtiquette pageOptionFormatEtiquette = wizardController.getCompositePageOptionFormatEtiquette();
		
		Text textAreaEtiquette = pageOptionFormatEtiquette.getTextFormatEtiquette();
		//Group group = pageOptionFormatEtiquette.getGrpFormatModelLabels();
		
		int nombreColumns = wizardController.getNombrecolumns().intValue();
		int nombreRows = wizardController.getNombreRows().intValue();
		
		Float leftMargin = wizardController.getMarginLeft();
		Float rightMargin = wizardController.getMarginRight();
		Float topMargin = wizardController.getMarginTop();
		Float bottomMargin = wizardController.getMarginButtom();
		
		Float leftPadding = wizardController.getPaddingLeft();
		Float rightPadding = wizardController.getPaddingRight();
		Float topPadding = wizardController.getPaddingTop();
		Float bottomPadding = wizardController.getPaddingButtom();
		
		Float largeurPapier = wizardController.getLargeurPapier();
		Float hauteurPapier = wizardController.getHauteurPapier();
		
		String pageSize = wizardController.getModelLables();
//		boolean flagChoixPreDefinionModelLables = wizardController.isFlagChoixPreDefinionModelLables();
//		if(!flagChoixPreDefinionModelLables){
//			pageSize = ConstantModelLabels.TYPE_PAPER_CUSTOM;
//		}else{
//			pageSize = wizardController.getModelLables();
//		}
		
		Float largeurEspace = wizardController.getLargeurEspace();
		Float hauteurEspace = wizardController.getHauteurEspace();
		
		Float heightGrid = (hauteurPapier-topMargin -bottomMargin);
		Float widthGrid = (largeurPapier-leftMargin -rightMargin);
		

		
		generationFileEtiquette.updateTextFormatEtiquette(nombreRows,nombreColumns,heightGrid,widthGrid,largeurEspace,hauteurEspace);
		generationFileEtiquette.updateLayoutFormatEtiquette(textAreaEtiquette);
		
		
		textAreaEtiquette.setText(generationFileEtiquette.getMotCleEtiquette());
		
		
		
		String pathFileParamEtiquette = generationFileEtiquette.getMapFileParamEtiquette().
										get(wizardController.getNameParamEtiquette());
		
		
		ParameterEtiquette parameterEtiquette = null;
		
		if(pathFileParamEtiquette != null){
			
			parameterEtiquette = generationFileEtiquette.
												  readObjectCastor(pathFileParamEtiquette);
			textAreaEtiquette.setText(parameterEtiquette.getTextModelEtiquette());
			
//			parameterEtiqutte = generationFileEtiquette.updateProprityObject(cellBorder, 
//					 leftMargin,rightMargin, topMargin, bottomMargin, 
//					 leftPadding, rightPadding, topPadding, bottomPadding, 
//					 largeurPapier, hauteurPapier, largeurEspace, hauteurEspace, 
//					 pageSize, nombreColumns, nombreRows, pathFileExtraction,
//					 generationFileEtiquette.getMotCleEtiquette(),
//					 pathFileParamEtiquette,pathFileMotCle,choixFileMotCle,
//					 valueSeparateur,choixSeparateur,parameterEtiqutte);
			
			
//			generationFileEtiquette.writeObjectCastor(parameterEtiqutte, pathFileParamEtiquette);
//			wizardController.initialCompsitePageOptionFormatEtiquette(parameterEtiqutte);
//			wizardController.setParameterEtiqutte(parameterEtiqutte);
		}
		else{
			parameterEtiquette = new ParameterEtiquette();
			
			parameterEtiquette.setGras(false);
			parameterEtiquette.setItalic(false);
			parameterEtiquette.setSizeEtiquette("10");
			parameterEtiquette.setNameParameterEtiqutte("");
//			String[] arraySizeEtiquette = wizardController.makeItemCombo();
//			wizardController.getCompositePageOptionFormatEtiquette().getComboSizeEtiquette().setItems(arraySizeEtiquette);
//			wizardController.setSizeEtiquette("10");
		}
		
		parameterEtiquette = generationFileEtiquette.updateProprityObject(cellBorder, 
				 leftMargin,rightMargin, topMargin, bottomMargin, 
				 leftPadding, rightPadding, topPadding, bottomPadding, 
				 largeurPapier, hauteurPapier, largeurEspace, hauteurEspace, 
				 pageSize, nombreColumns, nombreRows, pathFileExtraction,
				 generationFileEtiquette.getMotCleEtiquette(),
				 pathFileParamEtiquette,pathFileMotCle,choixFileMotCle,
				 valueSeparateur,choixSeparateur,parameterEtiquette);
		
		wizardController.initialCompsitePageOptionFormatEtiquette(parameterEtiquette);
		wizardController.setParameterEtiqutte(parameterEtiquette);
		pageOptionFormatEtiquette.layout();
	}

	@Override
	public void saveToModel() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean validatePage() {
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		CompositePageOptionModelLabels composite = new CompositePageOptionModelLabels(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setShell(shell);
		getController().setCompositePageOptionModelLabels(composite);
		
		String type = "";
		if(getController().getParam()!=null) {
			if(getController().getParam().getModelePredefini()!=null) {
				type = getController().getParam().getType();
			}
		}
		getController().initialActionListenerPageModelLables(type);
		
		setControl(composite);
		setPageComplete(false);
		
		if(getController().getParam()!=null) {
			if(getController().getParam().getModelePredefini()!=null) {
				String nomModeleEtiquette = getController().getParam().getModelePredefini();
				boolean trouve = false;
				int i = 0;
				while(!trouve && i<composite.getComboChoixParamEtiquette().getItems().length) {
					if(nomModeleEtiquette.equals(composite.getComboChoixParamEtiquette().getItems()[i])){
						trouve = true;
					} else {
						i++;
					}
				}
				if(trouve) {
					composite.getComboChoixParamEtiquette().select(i);
					getController().setNameParamEtiquette(nomModeleEtiquette);
					String cheminFichierXMLParam = getController().getGenerationFileEtiquette().getMapFileParamEtiquette().get(nomModeleEtiquette);
					getController().changeValueCompositPageModelLabel(cheminFichierXMLParam,i);
				}
			}
			
			if(getController().getParam().isModeIntegre()) {
				//donnees
				composite.getTextPathFileExtraction().setEnabled(false);
				composite.getButtonPathFileExtraction().setEnabled(false);
				composite.getTextPathFileExtraction().setText(getController().getParam().getCheminFichierDonnees());
				
				//mot cle
				composite.getBtOptionMotCle().setEnabled(false);
				composite.getTextPathFileMotCle().setEnabled(false);
				composite.getButtonPathFileMotCle().setEnabled(false);
				composite.getTextPathFileMotCle().setText(getController().getParam().getCheminFichierMotsCle());
				getController().setPathFileMotCle(getController().getParam().getCheminFichierMotsCle());
				
				//separateur
				composite.getComboChoixFileSeparateur().setEnabled(false);
				composite.getBtOptionFileSeparateur().setEnabled(false);
				composite.getTextSeparatuer().setEnabled(false);
				composite.getTextSeparatuer().setText(getController().getParam().getSeparateur());
			}
		}
	}
	
	public WizardController getController(){
		return ((WizardModelLables)getWizard()).getWizardController();
	}

}
