package fr.legrain.gestCom.librairiesEcran.swt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.lib.validation.CDateTimeObservableValue;
import fr.legrain.lib.validation.ConvertBigDecimal2String;
import fr.legrain.lib.validation.ConvertInteger2String;
import fr.legrain.lib.validation.ConvertString2BigDecimal;
import fr.legrain.lib.validation.ConvertString2Integer;
import fr.legrain.lib.validation.JPACtrlInterface;

public class LgrDatabindingUtil {
	
	static Logger logger = Logger.getLogger(LgrDatabindingUtil.class.getName());
	
	private Map<Table,LgrTableViewer> mapLgrTableViewer = null;
	
	public LgrDatabindingUtil() {}
	
	public LgrDatabindingUtil(JPABaseControllerSWTStandard controllerPourValidation) {
		this.controllerPourValidation = controllerPourValidation;
	}
	
	private JPABaseControllerSWTStandard controllerPourValidation = null;
	
	public Map<Table, LgrTableViewer> getMapLgrTableViewer() {
		return mapLgrTableViewer;
	}

	public IObservableValue bindTable(Table table, List model, Class classeEntite, String[] titreColonne, String[] tailleColonne, String[] nomChampsEntite,int champDeTri){
		LgrTableViewer tableViewer = new LgrTableViewer(table);
		
		if(mapLgrTableViewer==null) {
			mapLgrTableViewer = new HashMap<Table, LgrTableViewer>();
		}
		mapLgrTableViewer.put(table, tableViewer);


		tableViewer.createTableCol(table, titreColonne, tailleColonne,champDeTri);
		String[] listeChamp = nomChampsEntite;
		
		tableViewer.setListeChamp(listeChamp);
		tableViewer.tri(classeEntite, listeChamp,titreColonne);

		// Set up data binding.
		LgrViewerSupport.bind(tableViewer, 
				new WritableList(model, classeEntite),
				BeanProperties.values(listeChamp)
		);
		IObservableValue selectedObject = ViewersObservables.observeSingleSelection(tableViewer);

//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		tableViewer.selectionGrille(0);
		
		return selectedObject;
	}
	public IObservableValue bindTable(Table table, List model, Class classeEntite, String[] titreColonne, String[] tailleColonne, String[] nomChampsEntite){
		 return bindTable(table,model,classeEntite,titreColonne,tailleColonne,nomChampsEntite,0);
	}
	
	/**
	 * Creation des Bindings pour chaque champ du formulaire en fonction de <code>mapComposantChamps</code>.
	 * Remplissage de <code>listeBindSpec</code>
	 * @param dbc
	 * @param realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormMaitreDetail(Map<Control,String> mapComposantChamps,DataBindingContext dbc, Realm realm, IObservableValue selectedObject, Class selectedObjectClass) {
		try {
			//UI to model
			UpdateValueStrategy uiToModel;
			// model to UI
			UpdateValueStrategy modelToUI;
			String nomChamp = null;
//			if(listeBindSpec == null) {
//				listeBindSpec = new HashMap<Control, Object>();
//			}
			
			for (Control c : mapComposantChamps.keySet()) {
				uiToModel =  new UpdateValueStrategy();
				modelToUI =  new UpdateValueStrategy() {

					
					@Override
					protected IStatus doSet(IObservableValue observableValue, Object value) {
						boolean verrouLocal=VerrouInterface.isVerrouille();
						VerrouInterface.setVerrouille(true);
						if(controllerPourValidation!=null)
							controllerPourValidation.initEtatComposant();
						IStatus retour =  super.doSet(observableValue, value);
						VerrouInterface.setVerrouille(verrouLocal);
						return retour;
					}
					
				};
				
				uiToModel =  new UpdateValueStrategy();//.setAfterConvertValidator(new CtrlInterface(ibTaTableStandard,mapComposantChamps.get(c)));
				
				if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(BigDecimal.class) ) {
					uiToModel.setConverter(new ConvertString2BigDecimal());
					modelToUI.setConverter(new ConvertBigDecimal2String());
				} else if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(Integer.class) ) {
					uiToModel.setConverter(new ConvertString2Integer());
					modelToUI.setConverter(new ConvertInteger2String());
				}

//				listeBindSpec.put(c, uiToModel);
				nomChamp = correctionNomChamp(mapComposantChamps.get(c));
				if(c instanceof Text) {
						dbc.bindValue(SWTObservables.observeText((Text)c, SWT.FocusOut),
								BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType()),uiToModel,modelToUI
						);

				} else if(c instanceof Button) {
					dbc.bindValue(SWTObservables.observeSelection((Button)c),
							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, Boolean.class),uiToModel,modelToUI
					);
				}
//				else if(c instanceof CDateTime) {
//					dbc.bindValue(new CDateTimeObservableValue((cdatetimeLgr)c),
//							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType()),uiToModel,modelToUI
//							//BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
//					);
//				}
				else if(c instanceof DateTime) {
					dbc.bindValue(SWTObservables.observeSelection((DateTime)c),
							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, Date.class),uiToModel,modelToUI
					);
				}
				//branchement des validators apres le binding pour eviter le 1er controle qui risque d'etre faux
				if(controllerPourValidation!=null)
					uiToModel.setAfterConvertValidator(new JPACtrlInterface(controllerPourValidation,mapComposantChamps.get(c)));
			}
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (NoSuchFieldException e) {
			logger.error("",e);
		}
	}
	
	/** Binding d'un formulaire simple : pas de maitre/détail
	 * @param dbc - DataBindingContext
	 * @param realm - Realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormSimple(AbstractLgrDAO ibTaTableStandard,Map<Control,String> mapComposantChamps, DataBindingContext dbc, Realm realm, Object selectedObject, Class selectedObjectClass) {
		bindingFormSimple(ibTaTableStandard,mapComposantChamps, dbc, realm, selectedObject, selectedObjectClass, null);
	}
	
	/** Binding d'un formulaire simple : pas de maitre/détail
	 * @param dbc - DataBindingContext
	 * @param realm - Realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormSimple(AbstractLgrDAO ibTaTableStandard,Map<Control,String> mapComposantChamps, DataBindingContext dbc, Realm realm, Object selectedObject, Class selectedObjectClass, Map<Control,LgrUpdateValueStrategy> mapComposantUpdateValueStrategy) {
		try {

			//UI to model
			UpdateValueStrategy uiToModel;
			// model to UI
			UpdateValueStrategy modelToUI;
			String nomChamp = null;
//			if(listeBindSpec == null) {
//				listeBindSpec = new HashMap<Control, Object>();
//			}
			for (final Control c : mapComposantChamps.keySet()) {
				
				uiToModel =  new UpdateValueStrategy();
				modelToUI =  new UpdateValueStrategy() {
					@Override
					protected IStatus doSet(IObservableValue observableValue, Object value) {
						boolean verrouLocal=VerrouInterface.isVerrouille();
						VerrouInterface.setVerrouille(true);
						if(controllerPourValidation!=null)
							controllerPourValidation.initEtatComposant();
						IStatus retour =  super.doSet(observableValue, value);
						VerrouInterface.setVerrouille(verrouLocal);
						return retour;
					}
				};

				uiToModel = new UpdateValueStrategy();

				if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(BigDecimal.class) ) {
					uiToModel.setConverter(new ConvertString2BigDecimal());
					modelToUI.setConverter(new ConvertBigDecimal2String());
				}		
				if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(Double.class) ) {
//					uiToModel.setConverter(new ConvertString2Integer());
					modelToUI.setConverter(new ConvertInteger2String());
				}
				
				if( mapComposantUpdateValueStrategy != null 
						&&  mapComposantUpdateValueStrategy.get(c)!= null) {
					uiToModel.setConverter(mapComposantUpdateValueStrategy.get(c).getUiToModelConverter());
					modelToUI.setConverter(mapComposantUpdateValueStrategy.get(c).getModelToUIConverter());
				}
				
				nomChamp = correctionNomChamp(mapComposantChamps.get(c));
				if(c instanceof Text) {
					dbc.bindValue(SWTObservables.observeText((Text)c, SWT.FocusOut),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				} else if(c instanceof Label) {
					dbc.bindValue(SWTObservables.observeText((Label)c),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				} else if(c instanceof Button) {
					dbc.bindValue(SWTObservables.observeSelection((Button)c),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				} 
//				else if(c instanceof CDateTime) {
//					dbc.bindValue(new CDateTimeObservableValue((cdatetimeLgr)c),
//							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
//					);
//				}
				else if(c instanceof DateTime) {
					dbc.bindValue(SWTObservables.observeSelection((DateTime)c),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				}
				//branchement des validators apres le binding pour eviter le 1er controle qui risque d'etre faux
				if(controllerPourValidation!=null)
					uiToModel.setAfterConvertValidator(new JPACtrlInterface(controllerPourValidation,nomChamp));
			}
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (NoSuchFieldException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * Pour le databinding, le nom des proprietes d'un objet doivent respecter les conventions de nommage Java (java beans / java.beans.BeanInfo).<br>
	 * Le nom d'une propriete devrait donc commencer par une minuscule.<br>
	 * Dans le programme, le nom des champs est de la forme "ABC_DEF_GHI" donc il peut y avoir des problèmes.<br>
	 * Dans le cas ou il y a plusieurs lettre, il n'y a pas de probleme ABC_DEF, BeanInfo laisse tout en majuscule, le champ peut donc etre trouve pendant le databinding<br>
	 * S'il n'y a qu'une seule lettre avant le "_", il y a un probleme A_BCDEF, BeanInfo retourne a_BCDEF, le champ ne peut donc pas etre trouve.<br>
	 * 
	 * @param String - chaine de caractère
	 * @return - si la chaine est de la forme "A_BCD" retourne la meme chaine avec le caractere avant le underscore en minuscule,<br>
	 *  dans tous les autres cas, retourne la meme chaine que celle passe en parametre.
	 */
	private String correctionNomChamp(String a) {
		String resultat;
		String fin = a.substring(1);
		String debut = a.substring(0,1);
		if(fin.startsWith("_"))
			resultat = debut.toLowerCase()+fin;
		else
			resultat = a;
		return resultat;
	}
}
