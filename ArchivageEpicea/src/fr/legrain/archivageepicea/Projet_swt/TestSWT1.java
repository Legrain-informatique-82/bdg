package fr.legrain.archivageepicea.Projet_swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.css.Counter;

public class TestSWT1 {
	private static Label libelle_1; //nom de text
	private static Label libelle_2; //nom de text
	private static Label libelle_3; //nom de text
	private static 	int counter = 0; //1 seule fenetre
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(300, 100);
		shell.setText("Outil recherche");
		
//		Label label = new Label(shell, SWT.CENTER);
//		label.setText("Bonjour!");
//		label.pack();

		Menu mainMenu=new Menu(shell,SWT.BAR);
		shell.setMenuBar(mainMenu);
		
		MenuItem fileItem=new MenuItem(mainMenu,SWT.CASCADE); 
        fileItem.setText("File&F"); 
        
        Menu fileMenu=new Menu(shell,SWT.DROP_DOWN); 
        fileItem.setMenu(fileMenu);
        
        
        MenuItem newFileItem=new MenuItem(fileMenu,SWT.CASCADE); 
        newFileItem.setText("Cherche&C");
        
//        newFileItem.addSelectionListener(new SelectionAdapter(){
//		    public void widgetSelected(SelectionEvent e){ //æŒ‰é’®çš„å
	}
}