package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.data.LibChaineSQL;

import fr.legrain.lib.gui.grille.LgrTableViewer;

/**
 * A field editor for displaying and storing a list of strings. 
 * Buttons are provided for adding items to the list and removing 
 * items from the list.
 */
public class ListEditorAddRemoveEditLgr extends FieldEditor {
	static Logger logger = Logger.getLogger(ListEditorAddRemoveEditLgr.class.getName());

	private static final String DEFAULT_ADD_LABEL = "Add";
	private static final String DEFAULT_REMOVE_LABEL = "Remove selection";
	//private static final String DEFAULT_SEPERATOR = System.getProperty("line.separator");
	private static final String DEFAULT_SEPERATOR = ";";
	private static final int VERTICAL_DIALOG_UNITS_PER_CHAR = 8;
	private static final int HORIZONTAL_DIALOG_UNITS_PER_CHAR = 4;
	private static final int LIST_HEIGHT_IN_CHARS = 10;
	private static final int LIST_HEIGHT_IN_DLUS = 
		LIST_HEIGHT_IN_CHARS * VERTICAL_DIALOG_UNITS_PER_CHAR;
    /**
     * The <code>Table</code> used to present the selectable tabular data
     */
    protected Table table;
    /**
     * The <code>TableViewer</code> used as controller
     */
    protected LgrTableViewer viewer;

    /**
     * The content provider used to query the table data
     */
    protected IStructuredContentProvider contentProvider;

    /**
     * The label provider used to convert domain objects to ui specific textual
     * representations.
     */
    protected ITableLabelProvider labelProvider;

    /**
     * The input or model object that holds the data of the
     * <code>TableViewer</code>
     */
    protected Object input;
    protected Realm realm;
    /**
     * The column headers to display in the <code>Table</code>
     * </p>
     */
    protected String[] columnHeaders;
    protected String[] columnTitres;
	protected Map<Control,String> mapComposantChamps = null;
 
    /**
     * The index of the column to store/retrieve the value for. If set to -1 the
     * complete row represented as domain object is stored/retrieved. This is
     * done by calling toString() on the respective domain object.
     */
    protected int selectionColumn;

    /**
     * The last selected value of the <code>Table</code>
     */
    protected Object oldValue;

	// The top-level control for the field editor.
    protected Composite top;
    protected Composite addRemoveGroup;
	// The text field for inputting new tags.
    protected Text textField;
	// The button for adding the contents of
	// the text field to the list.
    protected Button add;
    //protected Button edit;
	// The button for removing the currently-selected list item.
    protected Button remove;
	// The string used to seperate list items 
	// in a single String representation.
    protected String seperatorLigne = DEFAULT_SEPERATOR;
	
    protected ControlListener controlColonne=null;
    
    protected boolean scrolled = true;
	public ListEditorAddRemoveEditLgr(
		String name,
		String labelText,
		Composite parent) {
			super(name, labelText, parent);		
	}
	
	public ListEditorAddRemoveEditLgr(String name, String labelText,
			 Composite parent,
			Realm realm,boolean scrolled) {
		try{
        this.realm = realm;
        this.scrolled = scrolled;
        this.init(name, labelText);
        this.createControl(parent);

	}catch (Exception e) {
		logger.error("", e);
	}
	}
	
	public ListEditorAddRemoveEditLgr(String name, String labelText,
			String addButtonText, String removeButtonText,
			Composite parent,
			Realm realm,boolean scrolled) {
		try{
        this.realm = realm;
        this.scrolled = scrolled;
        this.init(name, labelText);
        this.createControl(parent);
 			setAddButtonText(addButtonText);
			setRemoveButtonText(removeButtonText);
	}catch (Exception e) {
		logger.error("", e);
	}
	}
	
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	protected void adjustForNumColumns(int numColumns) {
		((GridData)top.getLayoutData()).horizontalSpan = numColumns;
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid
	 * (Composite, int)
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		try{
		top = parent;
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = numColumns;
		top.setLayoutData(gd);
	
		Label label = getLabelControl(top);
		GridData labelData = new GridData();
		labelData.horizontalSpan = numColumns;
		label.setLayoutData(labelData);

//        this.table = new Table(top, SWT.FULL_SELECTION | SWT.BORDER
//                | SWT.V_SCROLL | SWT.H_SCROLL | SWT.CHECK);
		if(scrolled){
	        this.table = new Table(top, SWT.FULL_SELECTION | SWT.BORDER
	                | SWT.V_SCROLL | SWT.H_SCROLL );
		}else{
			this.table = new Table(top, SWT.FULL_SELECTION | SWT.BORDER);
		}
		// Create a grid data that takes up the extra 
		// space in the dialog and spans both columns.
		GridData listData = new GridData(GridData.FILL_HORIZONTAL);
		listData.heightHint = 
			convertVerticalDLUsToPixels(this.table, LIST_HEIGHT_IN_DLUS);
		listData.horizontalSpan = numColumns;
		
		this.table.setLayoutData(listData);      
        this.table.setHeaderVisible(true);
        this.table.setLinesVisible(false);
        this.table.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e)
            {
                valueChanged();
            }

            public void widgetSelected(SelectionEvent e)
            {
                valueChanged();
            }
        });


		// Create a composite for the add and remove 
		// buttons and the input text field.
		addRemoveGroup = new Composite(top, SWT.NONE);
	
		GridData addRemoveData = new GridData(GridData.FILL_HORIZONTAL);
		addRemoveData.horizontalSpan = numColumns;
		addRemoveGroup.setLayoutData(addRemoveData);
	
		GridLayout addRemoveLayout = new GridLayout();
		addRemoveLayout.numColumns = numColumns;
		addRemoveLayout.marginHeight = 0;
		addRemoveLayout.marginWidth = 0;
		addRemoveGroup.setLayout(addRemoveLayout);
		
		// Create a composite for the add and remove buttons.
		Composite buttonGroup = new Composite(addRemoveGroup, SWT.NONE);
		buttonGroup.setLayoutData(new GridData());
	
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns=2;
		buttonLayout.marginHeight = 0;
		buttonLayout.marginWidth = 0;
		buttonGroup.setLayout(buttonLayout);
	
		// Create the add button.
		add = new Button(buttonGroup, SWT.NONE);
		add.setText(DEFAULT_ADD_LABEL);
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				add();
			}	
		});		
		GridData addData = new GridData(GridData.FILL_HORIZONTAL);
		addData.heightHint = convertVerticalDLUsToPixels(
			add,
			IDialogConstants.BUTTON_HEIGHT);
		addData.widthHint = convertHorizontalDLUsToPixels(
			add,
			IDialogConstants.BUTTON_WIDTH);	
		add.setLayoutData(addData);	
		
		
		// Create the remove button.
		remove = new Button(buttonGroup, SWT.NONE);
		remove.setEnabled(false);
		remove.setText(DEFAULT_REMOVE_LABEL);
		remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {	
				remove();
			}
		});
		GridData removeData = new GridData(GridData.FILL_HORIZONTAL);
		removeData.heightHint = convertVerticalDLUsToPixels(
			remove,
			IDialogConstants.BUTTON_HEIGHT);
		removeData.widthHint = convertHorizontalDLUsToPixels(
			remove,
			IDialogConstants.BUTTON_WIDTH);
		remove.setLayoutData(removeData);	
				
		}catch (Exception e) {
			logger.error("",e);
		}
	}
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	protected void doLoad() {
	}
	
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	protected void doLoadDefault() {
	}
	
	// Parses the string into seperate list items and adds them to the list.
	protected void setList(String items) {
	}
	
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	protected void doStore() {
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	public int getNumberOfControls() {
		// The button composite and the text field.
		return 5;
	}

	// Adds the string in the text field to the list.
	protected void add() {
	}
	
	
	protected void initCheckedReseau() {
	}
	
	protected void remove() {		
	}
	/**
	 *  Sets the label for the button that adds
	 * the contents of the text field to the list.
	 */
	public void setAddButtonText(String text) {
		add.setText(text);
	}
	
//	public void setEditButtonText(String text) {
//		edit.setText(text);
//	}
	
	/**
	 *  Sets the label for the button that removes
	 * the selected item from the list.
	 */
	public void setRemoveButtonText(String text) {
		remove.setText(text);
	}
	 
	/**
	 * Sets the string that seperates items in the list when the
	 * list is stored as a single String in the preference store.
	 */
	public void setSeperator(String seperator) {
		this.seperatorLigne = seperator;	
	}
	
	
	protected String createList() {
		return null;			
	}
	
	/**
	 *  Parses the single String representation of the list
	 * into an array of list items.
	 */
	private String[] parseString(String stringList) {
		return null;
	}
	
	// Sets the enablement of the remove button depending
	// on the selection in the list.
	private void selectionChanged() {
	int index = viewer.getTable().getSelectionIndex();
		remove.setEnabled(index >= 0);
	}


//
	
    public void setSelectionColumn(int columnIndex)
    {
        this.selectionColumn = columnIndex;
    }
    
    public int getSelectionColumn()
    {
        return (this.selectionColumn);
    }
    
    public void setColumnWidth(int columnIndex, int width)
    {
        if (columnIndex >= 0 && columnIndex < this.columnHeaders.length)
        {
            TableColumn column = this.table.getColumn(columnIndex);
            column.setWidth(width);
        }
    }
    
    public int getColumnWidth(int columnIndex)
    {
        if (columnIndex >= 0 && columnIndex < this.columnHeaders.length)
        {
            TableColumn column = this.table.getColumn(columnIndex);
            return (column.getWidth());
        }
        else
        {
            return (0);
        }
    }
    
    protected void valueChanged()
    {
        this.setPresentsDefaultValue(false);

        IStructuredSelection selection = (IStructuredSelection) this.viewer
                .getSelection();
        String newValue;
        if (selection.isEmpty())
        {
            newValue = "";
        }
        else if (this.selectionColumn == -1)
        {
            newValue = selection.getFirstElement().toString();
        }
        else
        {
            newValue = this.labelProvider.getColumnText(selection
                    .getFirstElement(), this.selectionColumn);
        }
        if (newValue.equals(oldValue))
        {
            this.fireValueChanged(VALUE, oldValue, newValue);
            oldValue = newValue;
        }
        initCheckedReseau();
    }

    /**
     * Initializes this <code>TableFieldEditor</code>'s
     * <code>TableViewer</code>.
     */
    protected void initializeViewer()
    {
        
        this.viewer.setContentProvider(this.contentProvider);
        this.viewer.setLabelProvider(this.labelProvider);

        this.viewer.setColumnProperties(this.columnHeaders);


        /* Pack all columnHeaders */
        TableColumn column;
        for (int i = 0; i < this.columnHeaders.length; i++)
        {
            column = this.table.getColumn(i);
            column.pack();
        }
        //this.viewer.setInput(this.input);

    }

    /**
     * Initializes the table columnHeaders by setting their widths and adjusting
     * their settings.
     */
    protected void initializeColumns()
    {
        TableColumn column;
        for (int i = 0; i < this.columnHeaders.length; i++)
        {
            column = new TableColumn(this.table, SWT.LEFT);
            column.setText(this.columnTitres[i]);
            column.setToolTipText(this.columnTitres[i]);
            if(controlColonne!=null)
            	column.addControlListener(controlColonne);
        }
    }
    
    protected void setSelection(String selectionStr)
    {
        if (this.viewer != null)
        {
            Object[] items = this.contentProvider.getElements(this.viewer
                    .getInput());
            boolean selected = false;
            if (this.selectionColumn == -1)
            {
                for (int i = 0; i < items.length && !selected; i++)
                {
                    if (selectionStr.equals(items[i].toString()))
                    {
                        StructuredSelection selection = new StructuredSelection(
                                items[i]);
                        this.viewer.setSelection(selection);
                        selected = true;
                    }
                }
            }
            else
            {
                for (int i = 0; i < items.length && !selected; i++)
                {
                    if (selectionStr.equals(this.labelProvider.getColumnText(
                            items[i], this.selectionColumn)))
                    {
                        StructuredSelection selection = new StructuredSelection(
                                items[i]);
                        this.viewer.setSelection(selection);
                        selected = true;
                    }
                }
            }
        }
    }

	public Text getTextField() {
		return textField;
	}

	public void setTextField(Text textField) {
		this.textField = textField;
	}

	public Button getAdd() {
		return add;
	}

	public Button getRemove() {
		return remove;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean isScrolled() {
		return scrolled;
	}

	public void setScrolled(boolean scrolled) {
		this.scrolled = scrolled;
	}

	public ControlListener getControlColonne() {
		return controlColonne;
	}

	public void setControlColonne(ControlListener controlColonne) {
		this.controlColonne = controlColonne;
	}


}
