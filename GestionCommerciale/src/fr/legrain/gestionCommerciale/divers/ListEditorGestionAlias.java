package fr.legrain.gestionCommerciale.divers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.legrain.gestCom.librairiesEcran.swt.ListEditorAddRemoveEditLgr;
import fr.legrain.gestCom.librairiesEcran.swt.ModelObjet;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.validation.ConvertBigDecimal2String;
import fr.legrain.lib.validation.ConvertInteger2String;
import fr.legrain.lib.validation.ConvertString2BigDecimal;


public class ListEditorGestionAlias extends ListEditorAddRemoveEditLgr {
	static Logger logger = Logger.getLogger(ListEditorGestionAlias.class.getName());
	ModelObjet<SWTGestionAlias> modelGestionAlias;
	Text textField2;
	Text textField3;
	Button btnField4;

	public static String C_SERVEUR ="SERVEUR";
	public static String C_ALIAS ="ALIAS";
	public static String C_CHEMINBASE ="CHEMINBASE";
	public static String C_RESEAU ="RESEAU";

	private IObservableValue selectedAlias;
	private DataBindingContext dbc;
	protected Map<Control, Object> listeBindSpec;

	public ListEditorGestionAlias(String name, String labelText,
			String addButtonText, String removeButtonText, Composite parent,
			Realm realm) {

		super(name, labelText, addButtonText, removeButtonText,  parent,
				realm,true);

		// Create the text field.
		textField = new Text(addRemoveGroup, SWT.BORDER);
		
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		textData.horizontalSpan = 1;	
		textData.verticalAlignment = GridData.BEGINNING;
		textField.setLayoutData(textData);

		// Create the text field2.
		textField2 = new Text(addRemoveGroup, SWT.BORDER);

		GridData textData2 = new GridData(GridData.FILL_HORIZONTAL);
		textData2.horizontalSpan = 1;	
		textData2.verticalAlignment = GridData.BEGINNING;
		textField2.setLayoutData(textData2);
		
		// Create the text field2.
		textField3 = new Text(addRemoveGroup, SWT.BORDER);

		GridData textData3 = new GridData(GridData.FILL_HORIZONTAL);
		textData3.horizontalSpan = 3;	
		textData3.verticalAlignment = GridData.BEGINNING;
		textField3.setLayoutData(textData3);

		//gestion dossier réseau
	
		btnField4 =new Button(addRemoveGroup, SWT.CHECK | SWT.LEFT);
		GridData textData4 = new GridData(GridData.FILL_HORIZONTAL);
		textData4.horizontalSpan = 4;	
		textData4.verticalAlignment = GridData.BEGINNING;
		btnField4.setLayoutData(textData4);
		btnField4.setText("Dossier en réseau");
		btnField4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				checked();
			}	
		});
		
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();
		mapComposantChamps.put(textField, C_SERVEUR);
		mapComposantChamps.put(textField2,C_ALIAS);
		mapComposantChamps.put(textField3, C_CHEMINBASE);
		mapComposantChamps.put(btnField4, C_RESEAU);

		ObservableListContentProvider viewerContent = new ObservableListContentProvider();
		this.columnHeaders =new String[]{C_SERVEUR,C_ALIAS,C_CHEMINBASE,C_RESEAU};
		this.columnTitres =new String[]{"Serveur","Dossier","Chemin de la base de données","Réseau"};

		IObservableMap[] attributeMaps = BeansObservables.observeMaps(
				viewerContent.getKnownElements(), SWTGestionAlias.class,this.columnHeaders);
		this.labelProvider =new ObservableMapLabelProvider(attributeMaps);
		this.viewer = new LgrTableViewer(this.table);
		viewer.setLabelProvider(this.labelProvider);
		selectedAlias = ViewersObservables.observeSingleSelection(viewer);
		this.contentProvider = viewerContent;

		this.initializeColumns();
		this.initializeViewer();
		this.table.getColumn(0).setWidth(100);
		this.table.getColumn(1).setWidth(100);
		this.table.getColumn(2).setWidth(300);
		dbc = new DataBindingContext(realm);
		bindingFormMaitreDetail(dbc, realm, selectedAlias,SWTGestionAlias.class);

	}

	protected String createList(LinkedList<SWTGestionAlias> items) {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTGestionAlias edition : items) {
			retour+=edition.getSERVEUR()+","+edition.getALIAS()+","+
			edition.getCHEMINBASE()+","+edition.getRESEAU()+";";
		}
		return retour; 
	}

	protected String createList() {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTGestionAlias alias : modelGestionAlias.getListeObjet()) {
			retour+=alias.getSERVEUR()+","+alias.getALIAS()+","+alias.getCHEMINBASE()
			+","+alias.getRESEAU()+";";
		}
		return retour; 
	}

	protected LinkedList<SWTGestionAlias> parseString(String listeStore) {
		LinkedList liste = new LinkedList<SWTGestionAlias>();
		if (modelGestionAlias==null)
			modelGestionAlias = new ModelObjet<SWTGestionAlias>(SWTGestionAlias.class,",");
		String[] list =listeStore.split(";");
		for (int i = 0; i < list.length; i++) {
			if(!list[i].equals(""))
			{
				if(list[i].split(",").length>3 && list[i].split(",")[3].compareToIgnoreCase("null")==0)
					list[i].split(",")[3]="false";
				else if(list[i].split(",").length==3){
					String newValue=(list[i]+",false");
					list[i]=newValue;
				}
				liste.add(modelGestionAlias.remplirObjet(list[i]));
			}
		}	
		return liste;
	}


	protected void initCheckedReseau() {
		if(((SWTGestionAlias)selectedAlias.getValue()).getRESEAU().compareToIgnoreCase("false")==0)
			btnField4.setSelection(false);
			else btnField4.setSelection(true);
	}

	// Adds the string in the text field to the list.
	protected void add() {
		SWTGestionAlias alias = new SWTGestionAlias();		
		modelGestionAlias.getListeObjet().add(alias);
		WritableList writableList = new WritableList(realm, modelGestionAlias
				.getListeObjet(), SWTGestionAlias.class);
		viewer.setInput(writableList);
		viewer.refresh();
		viewer.setSelection(new StructuredSelection(alias));
		textField.setFocus();
	}

	// 
	protected void checked() {
		if(btnField4.getSelection())
		((SWTGestionAlias)selectedAlias.getValue()).setRESEAU("true");
		else ((SWTGestionAlias)selectedAlias.getValue()).setRESEAU("false");
	}

	// Adds the string in the text field to the list.
	protected void remove() {	
		String oldAlias =((SWTGestionAlias)selectedAlias.getValue()).getALIAS();
		String oldChemin=((SWTGestionAlias)selectedAlias.getValue()).getCHEMINBASE();
		modelGestionAlias.getListeObjet().remove( selectedAlias.getValue());
		WritableList writableList = new WritableList(realm, modelGestionAlias.getListeObjet(), SWTGestionAlias.class);
		viewer.setInput(writableList);
		viewer.refresh();
		if(viewer.getTable().getItemCount()>0)
			viewer.selectionGrille(0);
		refresh();
		UtilWorkspace uw = new UtilWorkspace();
		//uw.supprimeAliasBD(uw.C_FICHIER_CONFIG_FIREBIRD, oldAlias, oldChemin);
	}

	protected void doStore() {
		String s = createList();
		if (s != null)
			getPreferenceStore().setValue(getPreferenceName(), s);
//		UtilWorkspace uw = new UtilWorkspace();		
//		for (SWTGestionAlias alias : modelGestionAlias.getListeObjet()) {
//			if (alias.getSERVEUR().equalsIgnoreCase("localhost")){
//				if(uw.trouveAliasBD(uw.C_FICHIER_CONFIG_FIREBIRD_LINUX,alias.getALIAS(), alias.getCHEMINBASE()))
//					uw.modifieAliasBD(uw.C_FICHIER_CONFIG_FIREBIRD,alias.getALIAS(), alias.getCHEMINBASE());
//					else	
//						uw.configureAliasBD(uw.C_FICHIER_CONFIG_FIREBIRD,alias.getALIAS(), alias.getCHEMINBASE());	
//			}else
//			{//C_FICHIER_CONFIG_FIREBIRD_LINUX
//				if(uw.trouveAliasBD(alias.getSERVEUR()+uw.C_FICHIER_CONFIG_FIREBIRD_LINUX,alias.getALIAS(), alias.getCHEMINBASE()))
//					uw.modifieAliasBD(alias.getSERVEUR()+uw.C_FICHIER_CONFIG_FIREBIRD_LINUX,alias.getALIAS(), alias.getCHEMINBASE());
//					else	
//						uw.configureAliasBD(alias.getSERVEUR()+uw.C_FICHIER_CONFIG_FIREBIRD_LINUX,alias.getALIAS(), alias.getCHEMINBASE().replace(alias.getSERVEUR()+":", ""));	
//			}
//		}
	}

	protected void doLoad() {
		String items = getPreferenceStore().getString(getPreferenceName());
		//items=aliasConfig(items);
		setList(items);
	}

	protected void doLoadDefault() {
		String items="" ; 
		//items=aliasConfig(items);
		setList(items);
	}
	
//    private String aliasConfig(String items){
//    	UtilWorkspace uw = new UtilWorkspace();
//		ArrayList<String> listeConfigAlias=uw.remonteAllAliasBD(uw.C_FICHIER_CONFIG_FIREBIRD);
//		for (String alias : listeConfigAlias) {
//			String[]value =alias.split(",");			
//			if(!items.contains(","+value[1]+","))
//				items+=alias;
//		}
//		return items;
//    }
	// Parses the string into seperate list items and adds them to the list.
	protected void setList(String items) {
		if (modelGestionAlias==null)
			modelGestionAlias = new ModelObjet<SWTGestionAlias>(SWTGestionAlias.class,",");

		modelGestionAlias.setListeObjet( parseString(items));

		WritableList writableList = new WritableList(realm, modelGestionAlias.getListeObjet()
				, SWTGestionAlias.class);
		viewer.setInput(writableList);
		viewer.refresh();
		if(modelGestionAlias.getListeObjet().size()>0)
			viewer.setSelection(new StructuredSelection(modelGestionAlias.getListeObjet().get(0)));
		refresh();
		valueChanged();
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
						//boolean verrouLocal=VerrouInterface.isVerrouille();
						//VerrouInterface.setVerrouille(true);
						//initEtatComposant();
						IStatus retour =  super.doSet(observableValue, value);
						//VerrouInterface.setVerrouille(verrouLocal);
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

				//branchement des validators apres le binding pour eviter le 1er controle qui risque d'etre faux
//				uiToModel.setAfterConvertValidator(new CtrlInterface(ibTaTableStandard,mapComposantChamps.get(c)));
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
