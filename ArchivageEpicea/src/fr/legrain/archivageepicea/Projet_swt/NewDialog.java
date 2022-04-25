package fr.legrain.archivageepicea.Projet_swt;
import org.eclipse.jface.viewers.TableViewer;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Table table1;
	private TableColumn tableColumn1;
	private TableItem tableItem1;
	private TableColumn tableColumn5;
	private TableColumn tableColumn4;
	private TableColumn tableColumn3;
	private TableColumn tableColumn2;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			NewDialog inst = new NewDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NewDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(650, 211);
			{
				table1 = new Table(dialogShell, SWT.SINGLE | SWT.FULL_SELECTION | SWT.EMBEDDED | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
					
				table1.setHeaderVisible(true);
				table1.setLinesVisible(true);
				FormData table1LData = new FormData();
				table1LData.width = 504;
				table1LData.height = 122;
				table1LData.left =  new FormAttachment(72, 1000, 0);
				table1LData.top =  new FormAttachment(0, 1000, 30);
				table1.setLayoutData(table1LData);
				{
					tableColumn1 = new TableColumn(table1, SWT.NONE);
					tableColumn1.setText("tableColumn1");
					tableColumn1.setWidth(114);
				}
				{
					tableColumn2 = new TableColumn(table1, SWT.NONE);
					tableColumn2.setText("tableColumn2");
					tableColumn2.setWidth(60);
				}
				{
					for (int i=0; i<30; i++){
					tableItem1 = new TableItem(table1, SWT.NONE);
					tableItem1.setText(new String[]{String.valueOf(i),"aa"});
					
					}
				}

			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TableItem getTableItem1() {
		return tableItem1;
	}

}
