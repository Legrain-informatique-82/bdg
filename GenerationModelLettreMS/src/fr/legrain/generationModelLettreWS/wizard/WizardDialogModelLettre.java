package fr.legrain.generationModelLettreWS.wizard;

import java.io.File;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;
import fr.legrain.generationModelLettreMS.divers.FonctionGeneral;
import fr.legrain.generationModelLettreMS.divers.ParametrePublicPostage;
import fr.legrain.gestCom.Appli.Const;

public class WizardDialogModelLettre extends WizardDialog {

	private WizardModelLettre wizardModelLettre;

	public WizardDialogModelLettre(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		// TODO Auto-generated constructor stub
		wizardModelLettre = (WizardModelLettre) newWizard;

	}

	@Override
	protected void backPressed() {
		// TODO Auto-generated method stub
		super.backPressed();
	}

	@Override
	protected void finishPressed() {
		// TODO Auto-generated method stub
		super.finishPressed();
		WizardController wizardController = wizardModelLettre.getWizardController();
		ParametrePublicPostage parametrePublicPostage = wizardController.getParametrePublicPostage();
		FonctionGeneral fonctionGeneral = wizardController.getFonctionGeneral();
		try {
			String valueSeparateur = parametrePublicPostage.getValueSeparateur();
			fonctionGeneral.open();
			fonctionGeneral.searcheMotCleLettreMS(valueSeparateur);
			fonctionGeneral.menuFileSave();

			fonctionGeneral.run();
			
			fonctionGeneral.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		boolean isSavePublicpostage = parametrePublicPostage.isSavePublicpostage();
		boolean isSavePublicpostagePagePlugin = parametrePublicPostage.isSavePublicpostagePagePlugin();
		boolean isChoixCcomboParamPublicPostage = wizardController.isChoixCcomboParamPublicPostage();
		String newNameParamPublicPostage = wizardController.getNewNameParamPublicPostage();
		String nameParamPublicPostage = parametrePublicPostage.getNameParamPublicPostage();
		
//		if(isChoixCcomboParamPublicPostage){
//			if(!isSavePublicpostage && !isSavePublicpostagePagePlugin){
//				
//			}
//		}
		
		if(isSavePublicpostage || isSavePublicpostagePagePlugin){
			
			if(!newNameParamPublicPostage.equals(nameParamPublicPostage)){
				String pathFileParamEtiquette = Const.PATH_FOLDER_MODEL_LETTRE_DOSSIER+"/"+Const.FOLDER_PARAM_LETTRE_WO+
												"/"+nameParamPublicPostage+ConstModelLettre.TYPE_FILE_XML;
				File fileParamPublicPostage = new File(pathFileParamEtiquette);
				fileParamPublicPostage.delete();
			}
			String pathFileParamEtiquette = Const.PATH_FOLDER_MODEL_LETTRE_DOSSIER+"/"+Const.FOLDER_PARAM_LETTRE_WO+
											"/"+newNameParamPublicPostage+ConstModelLettre.TYPE_FILE_XML;
			
			fonctionGeneral.writeObjectCastor(parametrePublicPostage, pathFileParamEtiquette);
		}


	}

	@Override
	protected void nextPressed() {
		// TODO Auto-generated method stub
		wizardModelLettre.nextPressed((IModelWizardPage) getCurrentPage());
		super.nextPressed();
	}

	public void resize(IWizardPage p) {
		this.updateSize(p);
	}

}
