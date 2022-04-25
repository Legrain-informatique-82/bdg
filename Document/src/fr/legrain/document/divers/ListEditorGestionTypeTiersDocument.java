package fr.legrain.document.divers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Tiers.SWTTypeTiers;
import fr.legrain.gestCom.librairiesEcran.swt.ListEditorAddRemoveEditLgr;
import fr.legrain.gestCom.librairiesEcran.swt.ModelObjet;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.validation.ConvertBigDecimal2String;
import fr.legrain.lib.validation.ConvertInteger2String;
import fr.legrain.lib.validation.ConvertString2BigDecimal;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;


public class ListEditorGestionTypeTiersDocument extends ListEditorAddRemoveEditLgr {
	static Logger logger = Logger.getLogger(ListEditorGestionTypeTiersDocument.class.getName());
	ModelObjet<SWTTypeTiersDocument> model;
	Combo textField;

	private IObservableValue selected;
	private DataBindingContext dbc;
	protected Map<Control, Object> listeBindSpec;

	public ListEditorGestionTypeTiersDocument(String name, String labelText,
			String addButtonText, String removeButtonText,
			 Composite parent,
			final Realm realm) {

		super(name, labelText, addButtonText, removeButtonText,  parent,
				realm,true);

		// Create the text field.
		textField = new Combo(addRemoveGroup, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		textField.setVisible(false);
		textField.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
				
			}

			public void widgetSelected(SelectionEvent e) {
				boolean trouve=false;
				int i=0;
				SWTTypeTiersDocument element=null;
				while(i<model.getListeObjet().size()&& !trouve){
					if(model.getListeObjet().get(i).getCodeTTiers()!=null)
					 trouve=(model.getListeObjet().get(i).getCodeTTiers().equals(textField.getText().split(",")[0]));
					i++;
				}
				if(!trouve){
					SWTTypeTiersDocument alias = new SWTTypeTiersDocument();		
					model.getListeObjet().add(alias);
					WritableList writableList = new WritableList(realm, model
							.getListeObjet(), SWTTypeTiersDocument.class);
					viewer.setInput(writableList);
					viewer.refresh();
					viewer.setSelection(new StructuredSelection(alias));
					
					((SWTTypeTiersDocument)selected.getValue()).setCodeTTiers(textField.getText().split(",")[0]);
					((SWTTypeTiersDocument)selected.getValue()).setLibelleTTiers(textField.getText().split(",")[1]);
				}
				viewer.refresh();
				viewer.getTable().forceFocus();
				textField.setVisible(false);
				refresh();
			}
			
		});
		
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		textData.horizontalSpan = 1;	
		textData.verticalAlignment = GridData.BEGINNING;
		textField.setLayoutData(textData);


		

		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();
		mapComposantChamps.put(textField, Const.C_CODE_T_TIERS);


		ObservableListContentProvider viewerContent = new ObservableListContentProvider();
		this.columnHeaders =new String[]{Const.C_CODE_T_TIERS,Const.C_LIBELLE_T_TIERS};
		this.columnTitres =new String[]{"Type tiers","libellé"};

		IObservableMap[] attributeMaps = BeansObservables.observeMaps(
				viewerContent.getKnownElements(), SWTTypeTiersDocument.class,this.columnHeaders);
		this.labelProvider =new ObservableMapLabelProvider(attributeMaps);
		this.viewer = new LgrTableViewer(this.table);
		viewer.setLabelProvider(this.labelProvider);
		selected = ViewersObservables.observeSingleSelection(viewer);
		this.contentProvider = viewerContent;

		this.initializeColumns();
		this.initializeViewer();
		this.table.getColumn(0).setWidth(100);
		this.table.getColumn(1).setWidth(200);

		dbc = new DataBindingContext(realm);
		bindingFormMaitreDetail(dbc, realm, selected,SWTTypeTiersDocument.class);

	}

	protected String createList(LinkedList<SWTTypeTiersDocument> items) {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTTypeTiersDocument edition : items) {
			retour+=edition.getCodeTTiers()+","+edition.getLibelleTTiers()+";";
		}
		return retour; 
	}

	protected String createList() {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTTypeTiersDocument alias : model.getListeObjet()) {
			retour+=alias.getCodeTTiers()+","+alias.getLibelleTTiers()+";";
		}
		return retour; 
	}

	protected LinkedList<SWTTypeTiersDocument> parseString(String listeStore) {
		LinkedList liste = new LinkedList<SWTTypeTiersDocument>();
		if (model==null)
			model = new ModelObjet<SWTTypeTiersDocument>(SWTTypeTiersDocument.class,",");
		String[] list =listeStore.split(";");
		for (int i = 0; i < list.length; i++) {
			if(!list[i].equals(""))
				liste.add(model.remplirObjet(list[i]));
		}	
		return liste;
	}




	// Adds the string in the text field to the list.
	protected void add() {
		textField.setVisible(true);
		textField.setFocus();
	}



	// Adds the string in the text field to the list.
	protected void remove() {	
		model.getListeObjet().remove( selected.getValue());
		WritableList writableList = new WritableList(realm, model.getListeObjet(), SWTTypeTiersDocument.class);
		viewer.setInput(writableList);
		viewer.refresh();
		if(viewer.getTable().getItemCount()>0)
			viewer.selectionGrille(0);
		refresh();
	}

	protected void doStore() {
		String s = createList();
		if (s != null)
			getPreferenceStore().setValue(getPreferenceName(), s);
	}

	protected void doLoad() {
		String items = getPreferenceStore().getString(getPreferenceName());
		String storeType = "";
		
		TaTTiersDAO taTTiersDAO = new TaTTiersDAO();
		List<TaTTiers> listeTypeEntity = taTTiersDAO.selectAll();
		for (TaTTiers taTTiers : listeTypeEntity) {
			storeType+=taTTiers.getCodeTTiers()+","+taTTiers.getLibelleTTiers()+";";
		}
		
//		LinkedList<SWTTypeTiers> listeType= SWT_IB_TA_T_TIERS.listeTTiers();
//		for (SWTTypeTiers typeTiers : listeType) {
//			storeType+=typeTiers.getCodeTTiers()+","+typeTiers.getLibelleTTiers()+";";
//		}		
		textField.setItems(storeType.split(";"));
		setList(items);
	}

	protected void doLoadDefault() {
		TaTTiersDAO taTTiersDAO = new TaTTiersDAO();
		List<TaTTiers> listeTypeEntity = taTTiersDAO.selectAll();
		String storeType = "";
		for (TaTTiers taTTiers : listeTypeEntity) {
			storeType+=taTTiers.getCodeTTiers()+","+taTTiers.getLibelleTTiers()+";";
		}
//		LinkedList<SWTTypeTiers> listeType= SWT_IB_TA_T_TIERS.listeTTiers();
//		String storeType = "";
//		for (SWTTypeTiers typeTiers : listeType) {
//				storeType+=typeTiers.getCodeTTiers()+","+typeTiers.getLibelleTTiers()+";";
//		}
		textField.setItems(storeType.split(";"));
		String items=storeType ; 
		setList(items);
	}
	

	// Parses the string into seperate list items and adds them to the list.
	protected void setList(String items) {
		if (model==null)
			model = new ModelObjet<SWTTypeTiersDocument>(SWTTypeTiersDocument.class,",");

		model.setListeObjet( parseString(items));

		WritableList writableList = new WritableList(realm, model.getListeObjet()
				, SWTTypeTiersDocument.class);
		viewer.setInput(writableList);
		viewer.refresh();
		if(model.getListeObjet().size()>0)
			viewer.setSelection(new StructuredSelection(model.getListeObjet().get(0)));
		refresh();
	}

	public void bindingFormMaitreDetail(DataBindingContext dbc, Realm realm, IObservableValue selectedObject, Class selectedObjectClass) {
		try {
			//UI to model
			UpdateValueStrategy uiToModel;
			// model to UI
			UpdateValueStrategy modelToUI;
			String nomChamp = null;
			if(listeBindSpec == null) {
				listeBindSpec = new HashMap<Control, Object>();
			}

			for (Control c : mapComposantChamps.keySet()) {
				uiToModel =  new UpdateValueStrategy();
				modelToUI =  new UpdateValueStrategy() {

					@Override
					protected IStatus doSet(IObservableValue observableValue, Object value) {
						IStatus retour =  super.doSet(observableValue, value);
						return retour;
					}

				};

				uiToModel =  new UpdateValueStrategy();//.setAfterConvertValidator(new CtrlInterface(ibTaTableStandard,mapComposantChamps.get(c)));

				if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(BigDecimal.class) ) {
					uiToModel.setConverter(new ConvertString2BigDecimal());
					modelToUI.setConverter(new ConvertBigDecimal2String());
				} else if( selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType().equals(Integer.class) ) {
//					uiToModel.setConverter(new ConvertString2Integer());
					modelToUI.setConverter(new ConvertInteger2String());
				}

				listeBindSpec.put(c, uiToModel);
				nomChamp = correctionNomChamp(mapComposantChamps.get(c));
				if(c instanceof Text) {
					dbc.bindValue(SWTObservables.observeText((Text)c, SWT.FocusOut),
							BeansObservables.observeDetailValue(realm, selectedObject, nomChamp, selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType()),uiToModel,modelToUI
					);

				} else if(c instanceof Button) {
					dbc.bindValue(SWTObservables.observeSelection((Button)c),
							BeansObservables.observeDetailValue(realm, selectedObject, nomChamp, Boolean.class),uiToModel,modelToUI
					);
				}

			}
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (NoSuchFieldException e) {
			logger.error("",e);
		}
	}

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

	private void refresh(){
		int index = viewer.getTable().getSelectionIndex();
		remove.setEnabled(index >= 0);

	}
}
