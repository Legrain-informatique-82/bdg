package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.LinkedList;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import fr.legrain.lib.data.SWTEdition;
import fr.legrain.lib.gui.grille.LgrTableViewer;

/**
 * A <code>FieldEditor</code> implementation that supports the selection of
 * tabular data.
 * 
 */
public class TableFieldEditor extends FieldEditor
{
    /**
     * The <code>Table</code> used to present the selectable tabular data
     */
    private Table table;

    /**
     * The <code>TableViewer</code> used as controller
     */
    private LgrTableViewer viewer;

    /**
     * The content provider used to query the table data
     */
    private IStructuredContentProvider contentProvider;

    /**
     * The label provider used to convert domain objects to ui specific textual
     * representations.
     */
    private ITableLabelProvider labelProvider;

    /**
     * The input or model object that holds the data of the
     * <code>TableViewer</code>
     */
    private Object input;

    /**
     * The column headers to display in the <code>Table</code>
     * </p>
     */
    private String[] columnHeaders;

 
    /**
     * The index of the column to store/retrieve the value for. If set to -1 the
     * complete row represented as domain object is stored/retrieved. This is
     * done by calling toString() on the respective domain object.
     */
    private int selectionColumn;

    /**
     * The last selected value of the <code>Table</code>
     */
    private Object oldValue;

    /**
     * Creates a new <code>TableFieldEditor</code> instance.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     * @param contentProvider the <code>IStructuredContentProvider</code> used
     *            to query the table data
     * @param labelProvider the <code>ITableLabelProvider</code> used to
     *            convert domain objects to ui specific textual representations
     * @param columnHeaders an array of <code>String</code> objects
     *            representing the column headers
     * @param input the input or model object which holds the data for this
     *            <code>TableFieldEditor</code>
     */
    public TableFieldEditor(String name, String labelText, Composite parent,
            IStructuredContentProvider contentProvider,
            ITableLabelProvider labelProvider, String[] columnHeaders,
            Object input)
    {
        this.contentProvider = contentProvider;
        this.labelProvider = labelProvider;
        this.columnHeaders = columnHeaders;
        this.input = input;
        this.init(name, labelText);
        this.createControl(parent);
    }

	
	protected String createList(LinkedList<SWTEdition> items) {
		// TODO Auto-generated method stub
		String retour="";
		for (SWTEdition edition : items) {
			retour+=edition.getNOM()+";";
		}
		return retour; 
	}
	
	
	
	protected LinkedList<SWTEdition> parseString(String stringList) {
		LinkedList liste = new LinkedList<SWTEdition>();
		String[] list =stringList.split(";");
		for (int i = 0; i < list.length; i++) {
			liste.add(new SWTEdition(list[i]));
		}	
		return liste;
	}
	
    /**
     * Returns the number of controls in this <code>TableFieldEditor</code>.
     * Returns <code>1</code> as the <code>Table</code> is the only control.
     * 
     * @return <code>1</code>
     * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
     */
    public int getNumberOfControls()
    {
        return 1;
    }

    /**
     * Sets the selection column to the specified <code>columnIndex</code>.
     * The index represents the column whose value is stored/retrieved in this
     * <code>TableFieldEditor</code>. If set to -1 the complete row
     * represented as domain object is stored/retrieved. This is done by calling
     * {@link #toString()} on the respective domain object.
     * 
     * @param columnIndex the column whose value is stored/retrieved in this
     *            <code>TableFieldEditor</code>
     * @see #getSelectionColumn()
     */
    public void setSelectionColumn(int columnIndex)
    {
        this.selectionColumn = columnIndex;
    }

    /**
     * Gets the selection column which represents the index of the column whose
     * value is stored/retrieved in this <code>TableFieldEditor</code>. A
     * value of <code>-1</code> means that the complete row represented as
     * domain object is stored/retrieved. This is done by calling
     * {@link #toString()} on the respective domain object.
     * 
     * @return the column whose value is stored/retrieved in this
     *         <code>TableFieldEditor</code>
     * @see #setSelectionColumn(int)
     */
    public int getSelectionColumn()
    {
        return (this.selectionColumn);
    }

    /**
     * Gets the currently selected value of this <code>TableFieldEditor</code>.
     * The value returned by this method depends on the selection column set up
     * as returned by {@link #getSelectionColumn()}. If the selection column is
     * set to <code>-1</code> the complete row represented as domain object is
     * returned by calling {@link #toString()} on it. Otherwise the respective
     * column value is queried and returned using the
     * <code>ITableLabelProvider</code> bound to this
     * <code>TableFieldEditor</code>.
     * 
     * @return the currently selected value or an empty <code>String</code> if
     *         no selection
     * @see #setSelectionColumn(int)
     * @see #getSelectionColumn()
     */
    public String getSelection()
    {
        IStructuredSelection selection = (IStructuredSelection) this.viewer
                .getSelection();
        if (selection.isEmpty())
        {
            return "";
        }
        else if (this.selectionColumn == -1)
        {
            /* Row selection */
            return selection.getFirstElement().toString();
        }
        else
        {
            /* Column selection */
            return this.labelProvider.getColumnText(selection
                    .getFirstElement(), this.selectionColumn);
        }
    }





 
    /**
     * Sets the width of the column at the specified <code>columnIndex</code>
     * to the given <code>width</code>. If no <code>TableColumn</code>
     * exists at the specified <code>columnIndex</code> the method does
     * nothing.
     * 
     * @param columnIndex the index of the column to set the width for
     * @param width the width of the column in pixel
     * @see #getColumnWidth(int)
     */
    public void setColumnWidth(int columnIndex, int width)
    {
        if (columnIndex >= 0 && columnIndex < this.columnHeaders.length)
        {
            TableColumn column = this.table.getColumn(columnIndex);
            column.setWidth(width);
        }
    }

    /**
     * Gets the width in pixel of the column at the specified
     * <code>columnIndex</code>. If no <code>TableColumn</code> exists at
     * the specified <code>columnIndex</code> the method returns <i>0</i>.
     * 
     * @param columnIndex the index of the column to get the width for
     * @return the column width
     * @see #setColumnWidth(int, int)
     */
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

    /**
     * Adjusts the horizontal span of this <code>TableFieldEditor</code>'s
     * basic controls. The number of columnHeaders will always be equal to or
     * greater than the value returned by this editor's
     * <code>getNumberOfControls</code> method.
     * 
     * @param numColumns the number of columnHeaders
     * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
     */
    protected void adjustForNumColumns(int numColumns)
    {
        GridData gd = (GridData) this.table.getLayoutData();
        gd.horizontalSpan = numColumns - 1;
        gd.grabExcessHorizontalSpace = gd.horizontalSpan <= 1;
    }

    /**
     * Fills this <code>TableFieldEditor</code>'s basic controls into the
     * given parent.
     * 
     * @param parent the composite used as a parent for the basic controls; the
     *            parent's layout must be a <code>GridLayout</code>
     * @param numColumns the number of columnHeaders
     * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite,
     *      int)
     */
    protected void doFillIntoGrid(Composite parent, int numColumns)
    {
        this.getLabelControl(parent);

        this.table = new Table(parent, SWT.FULL_SELECTION | SWT.BORDER
                | SWT.V_SCROLL | SWT.H_SCROLL | SWT.CHECK);
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

        this.initializeColumns();
        this.initializeViewer();

        GridData gd = new GridData();
        gd.horizontalSpan = numColumns - 1;
        gd.horizontalAlignment = GridData.FILL;
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        this.table.setLayoutData(gd);
    }

    /**
     * Initializes this <code>TableFieldEditor</code> with the preference
     * value from the preference store.
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doLoad()
     */
    protected void doLoad()
    {
        String value = this.getPreferenceStore().getString(
                this.getPreferenceName());
        this.setSelection(value);
        this.oldValue = value;
    }

    /**
     * Initializes this <code>TableFieldEditor</code> with the default
     * preference value from the preference store.
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
     */
    protected void doLoadDefault()
    {
        String defaultValue = this.getPreferenceStore().getDefaultString(
                this.getPreferenceName());
        this.setSelection(defaultValue);
        this.valueChanged();
    }

    /**
     * Stores the preference value from this <code>TableFieldEditor</code>
     * into the preference store.
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doStore()
     */
    protected void doStore()
    {
        this.getPreferenceStore().setValue(this.getPreferenceName(),
                this.getSelection());
    }

    /**
     * Informs this field editor's listener, if it has one, about a change to
     * the value (<code>VALUE</code> property) provided that the old and new
     * values are different. This hook is <em>not</em> called when the value
     * is initialized (or reset to the default value) from the preference store.
     */
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
    }

    /**
     * Initializes this <code>TableFieldEditor</code>'s
     * <code>TableViewer</code>.
     */
    private void initializeViewer()
    {
        this.viewer = new LgrTableViewer(this.table);
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
        this.viewer.setInput(this.input);

    }

    /**
     * Initializes the table columnHeaders by setting their widths and adjusting
     * their settings.
     */
    private void initializeColumns()
    {
        TableColumn column;
        for (int i = 0; i < this.columnHeaders.length; i++)
        {
            column = new TableColumn(this.table, SWT.LEFT);
            column.setText(this.columnHeaders[i]);
            column.setToolTipText(this.columnHeaders[i]);
        }
    }

    /**
     * Sets the selection of this <code>TableFieldEditor</code> to the row or
     * element matching the specified <code>selectionStr</code>.
     * 
     * @param selectionStr the <code>String</code> that identifies the row or
     *            element to select
     */
    private void setSelection(String selectionStr)
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
}
