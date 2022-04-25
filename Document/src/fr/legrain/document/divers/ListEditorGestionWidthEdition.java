package fr.legrain.document.divers;

import java.awt.Rectangle;
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
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Tiers.SWTTypeTiers;
import fr.legrain.gestCom.librairiesEcran.preferences.LgrPreferenceConstantsDocuments;
import fr.legrain.gestCom.librairiesEcran.swt.ListEditorAddRemoveEditLgr;
import fr.legrain.gestCom.librairiesEcran.swt.ListEditorSimple;
import fr.legrain.gestCom.librairiesEcran.swt.ModelObjet;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.validation.ConvertBigDecimal2String;
import fr.legrain.lib.validation.ConvertInteger2String;
import fr.legrain.lib.validation.ConvertString2BigDecimal;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;


public class ListEditorGestionWidthEdition extends ListEditorAddRemoveEditLgr {
	static Logger logger = Logger.getLogger(ListEditorGestionWidthEdition.class.getName());
	ModelObjet<SWTChampWidth> model;
	//Combo textField;

	private IObservableValue selected;
	private DataBindingContext dbc;
	protected Map<Control, Object> listeBindSpec;

	public ListEditorGestionWidthEdition(String name, String labelText, final Composite parent,
			final Realm realm) {

		super(name, labelText,   parent,realm,false);
//		this.getAdd().setVisible(false);
//		this.getRemove().setVisible(false);
//		
//		parent.addControlListener(new ControlListener() {	
//			@Override
//			public void controlResized(ControlEvent e) {
//			        org.eclipse.swt.graphics.Rectangle area = parent.getClientArea();
//			        Point preferredSize = table.computeSize(400, SWT.DEFAULT);
//			        int width = area.width - 2*table.getBorderWidth();
//			        if (preferredSize.y > area.height + table.getHeaderHeight()) {
//			          // Subtract the scrollbar width from the total column width
//			          // if a vertical scrollbar will be required
//			          Point vBarSize = table.getVerticalBar().getSize();
//			          width -= vBarSize.x;
//			        }
//			        Point oldSize = table.getSize();
//			        if (oldSize.x > area.width) {
//			          // table is getting smaller so make the columns 
//			          // smaller first and then resize the table to
//			          // match the client area width
//			        	getTable().getColumn(2).setWidth(width/3);
//			        	getTable().getColumn(1).setWidth(width - getTable().getColumn(0).getWidth());
//			          table.setSize(area.width, area.height);
//			        } else {
//			          // table is getting bigger so make the table 
//			          // bigger first and then make the columns wider
//			          // to match the client area width
//			          table.setSize(area.width, area.height);
//			          getTable().getColumn(0).setWidth(width/3);
//			          getTable().getColumn(1).setWidth(width - getTable().getColumn(0).getWidth());
//			        }
//			      }
//			
//			
//			@Override
//			public void controlMoved(ControlEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//	
//		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
//		textData.horizontalSpan = 1;	
//		textData.verticalAlignment = GridData.BEGINNING;
//		//textField.setLayoutData(textData);
//
//
//		ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//		this.columnHeaders =new String[]{"reference","designation","qte","u1","PU","remise","montant","tva"};
//		
//		this.columnTitres =new String[]{LgrPreferenceConstantsDocuments.EDITION_CHAMP_1,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_2,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_3,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_4,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_5,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_6,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_7,
//				 LgrPreferenceConstantsDocuments.EDITION_CHAMP_8};
//
//		IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//				viewerContent.getKnownElements(), SWTChampWidth.class,this.columnHeaders);
//		this.labelProvider =new ObservableMapLabelProvider(attributeMaps);
//		this.viewer = new LgrTableViewer(this.table);
//		viewer.setLabelProvider(this.labelProvider);
//		selected = ViewersObservables.observeSingleSelection(viewer);
//		this.contentProvider = viewerContent;
//		this.table.computeSize(400, SWT.DEFAULT);
//		this.initializeColumns();
//		this.initializeViewer();
//		this.table.getColumn(0).setWidth(50);
//		this.table.getColumn(1).setWidth(50);
//		this.table.getColumn(2).setWidth(50);
//		this.table.getColumn(3).setWidth(50);
//		this.table.getColumn(4).setWidth(50);
//		this.table.getColumn(5).setWidth(50);
//		this.table.getColumn(6).setWidth(50);
//		this.table.getColumn(7).setWidth(50);
//		
//		
//		if (mapComposantChamps == null)
//			mapComposantChamps = new LinkedHashMap<Control, String>();
//		dbc = new DataBindingContext(realm);
//		bindingFormMaitreDetail(dbc, realm, selected,SWTChampWidth.class);

	}

	protected String createList(LinkedList<SWTChampWidth> items) {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTChampWidth edition : items) {
			retour+=edition.getReference()+","+edition.getDesignation()+","+edition.getQte()+","+edition.getU1()+","+edition.getPU()+","
		+edition.getRemise()+","+edition.getMontant()+","+edition.getTva()+";";
		}
		return retour; 
	}

	protected String createList() {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTChampWidth alias : model.getListeObjet()) {
			retour+=alias.getReference()+","+alias.getDesignation()+","+alias.getQte()+","+alias.getU1()+","+alias.getPU()+","
					+alias.getRemise()+","+alias.getMontant()+","+alias.getTva()+";";
		}
		return retour; 
	}

	protected LinkedList<SWTChampWidth> parseString(String listeStore) {
		LinkedList liste = new LinkedList<SWTChampWidth>();
		if (model==null)
			model = new ModelObjet<SWTChampWidth>(SWTChampWidth.class,",");
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
		if(items.equals(""))doLoadDefault();
		else
			setList(items);
	}

	protected void doLoadDefault() {
		String items="15,44,8,6,8,5,10,4;";
		setList(items);
	}
	

	// Parses the string into seperate list items and adds them to the list.
	protected void setList(String items) {
		if (model==null)
			model = new ModelObjet<SWTChampWidth>(SWTChampWidth.class,",");

		model.setListeObjet( parseString(items));

		WritableList writableList = new WritableList(realm, model.getListeObjet()
				, SWTChampWidth.class);
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

private class controleColonne implements ControlListener{

	@Override
	public void controlMoved(ControlEvent e) {
	}

	@Override
	public void controlResized(ControlEvent e) {
		
	}
	
}
}
