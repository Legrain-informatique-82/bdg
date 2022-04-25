package fr.legrain.recherchermulticrit.controllers;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.GroupeLigne;
import fr.legrain.recherchermulticrit.ecrans.PaCompositeSectionEtape1;
import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.services.Branding;
import fr.legrain.services.IServiceBranding;


public class Etape1Controller extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(Etape1Controller.class.getName());	

	private FormPageController masterController = null;

	private PaFormPage vue = null;


	/* Constructeur par défaut */
	public Etape1Controller(FormPageController masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	public void initialiseModelIHM() {
		initActions();
	}



	@Override
	protected void initActions() {	
		// Initialisation du listener sur le bouton de recalcul
		vue.getComposite_Etape1().getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String  ttlBox = "Réinitialisation des critères";
				String  msgBox = "Êtes vous sûr de vouloir réinitialiser l'ensemble des critères ?";				
				boolean reinitialiseOK = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlBox, msgBox);
				if (reinitialiseOK){
					vue.getComposite_Etape1().getCombo().select(0);
					vue.getComposite_SauverCharger().getCombo().select(0);
					vue.getComposite_SauverCharger().getDesc().setText("");
					vue.getComposite_SauverCharger().getInfoCharge().setText("");
					vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
					vue.getComposite_SauverCharger().getNomSauvegarde().setText("");
					List<GroupeLigne> groupes = masterController.getEtape2Controller().getGroupes();
					for(int i=0;i<groupes.size();i++){
						groupes.get(i).getGroupe_composite().dispose();
					}
					vue.getComposite_Etape2().getComposite().layout();
					for (int i=0;i<vue.getComposite_Etape2().getMenuGr().getItems().length;i++){
						vue.getComposite_Etape2().getMenuGr().getItem(i).dispose();
					}
					vue.getComposite_Etape2().getItemCombo().setEnabled(false);
					masterController.getEtape2Controller().setSelectedGroup(0); 
				}


			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Initialisation de la combo box
		for (String typeResult : masterController.listeResultats){
			vue.getComposite_Etape1().getCombo().add(typeResult);
		}
		vue.getComposite_Etape1().getCombo().select(0);

	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
	}


	@Override
	protected void initMapComposantChamps() {

	}


}

