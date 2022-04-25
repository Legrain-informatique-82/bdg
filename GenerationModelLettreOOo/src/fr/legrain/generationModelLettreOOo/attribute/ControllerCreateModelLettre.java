package fr.legrain.generationModelLettreOOo.attribute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationModelLettreOOo.divers.ConstModelLettre;
import fr.legrain.generationModelLettreOOo.divers.FonctionGeneral;
import fr.legrain.generationModelLettreOOo.divers.FonctionOpenOffice;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import generationmodellettreooo.Activator;

public class ControllerCreateModelLettre {
	
	static Logger logger = Logger.getLogger(ControllerCreateModelLettre.class.getName());
	
	private Shell shell;
	private CompositeCreateAttributeLettre compositeCreateAttributeLettre;
	
	private String pathModelLetter;
	private String pathFileExtraction;
	private String pathFolderFileAttribute;
	private String pathFileAttribute;
	
	private String pathOffice;
	private String portOffice;
	
	private FonctionOpenOffice fonctionOpenOffice;
	
	private boolean flagBtValider = false;
	

	private String valueMotKeyselected = "";
	private int indexMotKeyselected;
	
	private String valueNameChampSelected = "";
	private int indexNameChampSelected;
	
	private java.util.List<String> listMotKey = new LinkedList<String>();
	private String[] valusListMotKey = null;
	private String[] valusListNameChamp = null;
	
	private boolean flagTextMotKey = false;
	private boolean flagTextNomeChamp = false;
	private boolean flagTextPathAttribute = false;
	private boolean flagTextPathFileAttributeLetter = false;
	
	private boolean flagChoixCreateAttributeLettre = true;
	private boolean flagChoixUpdateAttributeLettre = false;
	
	public ControllerCreateModelLettre(Shell shell,String pathOffice,String portOffice,
			FonctionOpenOffice fonctionOpenOffice) {
		super();
		this.shell = shell;
		this.pathOffice = pathOffice;
		this.portOffice = portOffice;
		this.fonctionOpenOffice = fonctionOpenOffice;
	}
	
	public void init() {
	
		
		this.compositeCreateAttributeLettre = new CompositeCreateAttributeLettre(shell, SWT.NULL);	
		compositeCreateAttributeLettre.getBtValider().setEnabled(false);
		Button btChoixCreateattributeLettre = compositeCreateAttributeLettre.getBtChoixCreateattributeLettre();
		btChoixCreateattributeLettre.setSelection(flagChoixCreateAttributeLettre);
		
		updateEtatElementSwt(flagChoixCreateAttributeLettre,flagChoixUpdateAttributeLettre,
				compositeCreateAttributeLettre);
				
		initialiseImageButton(compositeCreateAttributeLettre);
		initActionElement(compositeCreateAttributeLettre);		
		addSelectionListenerForList(compositeCreateAttributeLettre);
		initActionElementBtImage(compositeCreateAttributeLettre);
	}
	
	public void initActionElementBtImage(final CompositeCreateAttributeLettre compositeCreateAttributeLettre){
		
		
		
		Button btUpModelLettre = compositeCreateAttributeLettre.getBtUpModelLettre();
		Button btDownModelLettre = compositeCreateAttributeLettre.getBtDownModelLettre();
		
		Button btUpNameChamp = compositeCreateAttributeLettre.getBtUpNameChamp();
		Button btDownNameChamp = compositeCreateAttributeLettre.getBtDownNameChamp();
		
		final List swtListMotKey = compositeCreateAttributeLettre.getListMotKeyModelLettre();
		final List swtListNameChamp = compositeCreateAttributeLettre.getListNameChamp();
		
		btUpModelLettre.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				String org= valusListMotKey[indexMotKeyselected-1];
				valusListMotKey[indexMotKeyselected]= org;
				valusListMotKey[indexMotKeyselected-1]=valueMotKeyselected;
				swtListMotKey.setItems(valusListMotKey);
				swtListMotKey.setSelection(indexMotKeyselected-1);
				actionSwtListMotKey(compositeCreateAttributeLettre);
			}
		});
		
		btDownModelLettre.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				String org= valusListMotKey[indexMotKeyselected+1];
				valusListMotKey[indexMotKeyselected]= org;
				valusListMotKey[indexMotKeyselected+1]=valueMotKeyselected;
				swtListMotKey.setItems(valusListMotKey);
				swtListMotKey.setSelection(indexMotKeyselected+1);
				actionSwtListMotKey(compositeCreateAttributeLettre);
			}
		});
		
		btUpNameChamp.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				String org= valusListNameChamp[indexNameChampSelected-1];
				valusListNameChamp[indexNameChampSelected]= org;
				valusListNameChamp[indexNameChampSelected-1]=valueNameChampSelected;
				swtListNameChamp.setItems(valusListNameChamp);
				swtListNameChamp.setSelection(indexNameChampSelected-1);
				actionSwtListNameChamp(compositeCreateAttributeLettre);
			}
		});
		
		btDownNameChamp.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				String org= valusListNameChamp[indexNameChampSelected+1];
				valusListNameChamp[indexNameChampSelected]= org;
				valusListNameChamp[indexNameChampSelected+1]=valueNameChampSelected;
				swtListNameChamp.setItems(valusListNameChamp);
				swtListNameChamp.setSelection(indexNameChampSelected+1);
				actionSwtListNameChamp(compositeCreateAttributeLettre);
			}
		});
		
	}
	public void modifyAllButton(CompositeCreateAttributeLettre compositeCreateAttributeLettre,boolean flag){
		Button btAnnuler = compositeCreateAttributeLettre.getBtAnnuler();
		Button btDownModelLettre = compositeCreateAttributeLettre.getBtDownModelLettre();
		Button btDownNameChamp = compositeCreateAttributeLettre.getBtDownNameChamp();
		Button btUpModelLettre = compositeCreateAttributeLettre.getBtUpModelLettre();
		Button btUpNameChamp = compositeCreateAttributeLettre.getBtUpNameChamp();
		Button btOpenFolderPathModelLettre = compositeCreateAttributeLettre.getBtOpenFolderPathModelLettre();
		Button btOpenFolderPathFileExtraction = compositeCreateAttributeLettre.getBtOpenFolderPathFileExtraction();
		btAnnuler.setEnabled(flag);
		btUpModelLettre.setEnabled(flag);
		btUpNameChamp.setEnabled(flag);
		btDownModelLettre.setEnabled(flag);
		btDownNameChamp.setEnabled(flag);
		btOpenFolderPathModelLettre.setEnabled(flag);
		btOpenFolderPathFileExtraction.setEnabled(flag);
	}
	
	public void updateEtatElementSwt(boolean flagCreate,boolean flagUpdate,
			CompositeCreateAttributeLettre compositeCreateAttributeLettre){
		
		Text textPathFileExtraction = compositeCreateAttributeLettre.getTextPathFileExtraction();
		Text textPathModelLettre = compositeCreateAttributeLettre.getTextPathModelLettre();
		Text txPathAttributeLettre = compositeCreateAttributeLettre.getTxPathAttributeLettre();
		Button btOpenFolderPathModelLettre = compositeCreateAttributeLettre.getBtOpenFolderPathModelLettre();
		Button btOpenFolderPathFileExtraction = compositeCreateAttributeLettre.getBtOpenFolderPathFileExtraction();
		Button btOpenFolderPathAttributeLettre = compositeCreateAttributeLettre.getBtOpenFolderPathAttributeLettre();
		
		Text txPathFileAttributeLettre = compositeCreateAttributeLettre.getTxPathFileAttributeLettre();
		Button btOpenFilePathAttributeLettre = compositeCreateAttributeLettre.getBtOpenFilePathAttributeLettre();
		
		textPathFileExtraction.setEnabled(flagCreate);
		textPathModelLettre.setEnabled(flagCreate);
		txPathAttributeLettre.setEnabled(flagCreate);
		textPathFileExtraction.setText("");
		textPathModelLettre.setText("");
		txPathAttributeLettre.setText("");
		pathModelLetter = "";
		pathFileExtraction = "";
		pathFolderFileAttribute = "";
		
		textPathFileExtraction.setEditable(false);
		textPathModelLettre.setEditable(false);
		txPathAttributeLettre.setEditable(false);
		
		btOpenFolderPathModelLettre.setEnabled(flagCreate);
		btOpenFolderPathFileExtraction.setEnabled(flagCreate);
		btOpenFolderPathAttributeLettre.setEnabled(flagCreate);
		
		txPathFileAttributeLettre.setEnabled(flagUpdate);
		txPathFileAttributeLettre.setEditable(false);
		txPathFileAttributeLettre.setText("");
		pathFileAttribute = "";
		btOpenFilePathAttributeLettre.setEnabled(flagUpdate);
		
		compositeCreateAttributeLettre.getBtValider().setEnabled(false);
		
		List swtListMotKey = compositeCreateAttributeLettre.getListMotKeyModelLettre();
		List swtListNameChamp = compositeCreateAttributeLettre.getListNameChamp();
		swtListMotKey.removeAll();
		swtListNameChamp.removeAll();
		
		Table tbFileExtraction = compositeCreateAttributeLettre.getTbInfoFileExtraction();
		tbFileExtraction.removeAll();
		int count = 0;

		for(;tbFileExtraction.getColumns().length>0;){

			TableColumn tableColumn = tbFileExtraction.getColumns()[0];
			tableColumn.dispose();
		}
	}
	public void saveFileAttributeLetter(String pathPropsFile) {
		Properties props = new Properties();
		OutputStream propsFile;
		try {
			System.out.println(pathPropsFile);
			propsFile = new FileOutputStream(pathPropsFile);
			for (int i = 0; i < valusListMotKey.length; i++) {
				props.setProperty(valusListMotKey[i],valusListNameChamp[i]);
			}
			props.store(propsFile, "Properties File to the Test Application");
            propsFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadFileAttributeLetter(String pathPropsFile) {
		Properties props = new Properties();
		InputStream propsFile;
	    int count = 0;
		try {
			propsFile = new FileInputStream(pathPropsFile);
			props.load(propsFile);
			valusListMotKey = new String[props.stringPropertyNames().size()];
			valusListNameChamp = new String[props.stringPropertyNames().size()];
			for(String key : props.stringPropertyNames()) { 
				String value = props.getProperty(key); 
				valusListMotKey[count] = key;
				valusListNameChamp[count] = value;
				count++;
			} 
			propsFile.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	public void initActionElement(final CompositeCreateAttributeLettre compositeCreateAttributeLettre){
		final List swtListMotKey = compositeCreateAttributeLettre.getListMotKeyModelLettre();
		final List swtListNameChamp = compositeCreateAttributeLettre.getListNameChamp();
		final Button btValider = compositeCreateAttributeLettre.getBtValider();
		btValider.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String contentMessage = "";
				boolean flag = false;
				boolean flagMessage = true;
				if(valusListMotKey.length > valusListNameChamp.length){
					contentMessage = "nombre de Mot key > nombre de Nom champs";
					flag = true;
				}else if(valusListMotKey.length < valusListNameChamp.length){
					contentMessage = "nombre de Mot key < nombre de Nom champs";
					flag = true;
				}
				if(flag){
					logger.info("...");
					flagMessage = MessageDialog.openConfirm(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							ConstModelLettre.MESSAGE_TITLE,contentMessage);
				}
//				if(flagMessage){
//					System.out.println("pathFileAttribute==>" + pathFileAttribute);
//					saveFileAttributeLetter(pathFileAttribute);
//					compositeCreateAttributeLettre.getShell().close();
//				}
			}
			
		});
		Button btOpenFilePathAttributeLettre = compositeCreateAttributeLettre.getBtOpenFilePathAttributeLettre();
		final Text txPathFileAttributeLettre = compositeCreateAttributeLettre.getTxPathFileAttributeLettre();
		btOpenFilePathAttributeLettre.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//DirectoryDialog dlg = new DirectoryDialog(shell);
				FileDialog dlg = new FileDialog(shell,SWT.NONE);
				dlg.setText("Choisir file attribute ");
				dlg.setFilterExtensions(new String[]{"*.properties"});
//				dlg.setMessage("Choisir a directory");
				String dir = dlg.open();
		        if (dir != null) {
		  
		        	pathFileAttribute = dir;
		        	txPathFileAttributeLettre.setText(pathFileAttribute);
		        	flagTextPathFileAttributeLetter = true;
		        	flagBtValider = flagTextPathFileAttributeLetter;
		        	btValider.setEnabled(flagBtValider);
		        	loadFileAttributeLetter(pathFileAttribute);
		        	swtListMotKey.setItems(valusListMotKey);
		        	swtListNameChamp.setItems(valusListNameChamp);
		        }
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
				
			}
		});

		final Button btChoixCreateattributeLettre = compositeCreateAttributeLettre.getBtChoixCreateattributeLettre();
		final Button btChoixUpdateAttributeLettre = compositeCreateAttributeLettre.getBtChoixUpdateAttributeLettre();
		
		btChoixCreateattributeLettre.addSelectionListener(new SelectionAdapter() {
              public void widgetSelected(SelectionEvent e) {
            	  Button bt = (Button)e.getSource();
            	  if(bt.getSelection()){
            		  flagChoixUpdateAttributeLettre = false;
            		  flagChoixCreateAttributeLettre = true;
            		  btChoixUpdateAttributeLettre.setSelection(flagChoixUpdateAttributeLettre);
            	  }else{
            		  flagChoixUpdateAttributeLettre = true;
            		  flagChoixCreateAttributeLettre = false;
            		  btChoixUpdateAttributeLettre.setSelection(flagChoixUpdateAttributeLettre);
            		
            	  } 
            	  updateEtatElementSwt(flagChoixCreateAttributeLettre,flagChoixUpdateAttributeLettre,
            			  compositeCreateAttributeLettre);
              }
            });
		
		btChoixUpdateAttributeLettre.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent e) {
          	  Button bt = (Button)e.getSource();
        	  if(bt.getSelection()){
        		  flagChoixUpdateAttributeLettre = true;
        		  flagChoixCreateAttributeLettre = false;
        		  btChoixCreateattributeLettre.setSelection(flagChoixCreateAttributeLettre);
        	  }else{
        		  flagChoixUpdateAttributeLettre = false;
        		  flagChoixCreateAttributeLettre = true;
        		  btChoixCreateattributeLettre.setSelection(flagChoixCreateAttributeLettre);
        	  }
        	  updateEtatElementSwt(flagChoixCreateAttributeLettre,flagChoixUpdateAttributeLettre,
        			  compositeCreateAttributeLettre);
            }
          });

		Button btOpenFolderPathModelLettre = compositeCreateAttributeLettre.getBtOpenFolderPathModelLettre();
		Button btOpenFolderPathFileExtraction = compositeCreateAttributeLettre.getBtOpenFolderPathFileExtraction();
		final Text textPathModelLetter = compositeCreateAttributeLettre.getTextPathModelLettre();
		final Text textPathFileExtraction = compositeCreateAttributeLettre.getTextPathFileExtraction();
		
		
		Button btOpenFolderPathAttributeLettre = compositeCreateAttributeLettre.getBtOpenFolderPathAttributeLettre();
		final Text txPathAttributeLettre = compositeCreateAttributeLettre.getTxPathAttributeLettre();
		
		Button btAnnuler = compositeCreateAttributeLettre.getBtAnnuler();
		
		btAnnuler.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				compositeCreateAttributeLettre.getShell().close();
			}
			
		});
		
		btOpenFolderPathAttributeLettre.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//DirectoryDialog dlg = new DirectoryDialog(shell);
				FileDialog dlg = new FileDialog(shell,SWT.SAVE);
				dlg.setText("Choisir chemin stocker file attribute ");
				dlg.setFilterExtensions(new String[]{"*.properties"});
				String pathFile = dlg.open();
				File file = new File(pathFile);
				boolean flagMessage = false;
				if(file.exists()){
					flagMessage = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							ConstModelLettre.MESSAGE_TITLE,"file exsite! voulez-vous remplacer ?");
					if(flagMessage){
						pathFileAttribute = pathFile;
						txPathAttributeLettre.setText(pathFileAttribute);
						flagTextPathAttribute = true;
						flagBtValider = flagTextPathAttribute && flagTextMotKey && flagTextNomeChamp;
					}else{
						pathFileAttribute = "";
						txPathAttributeLettre.setText(pathFileAttribute);
						flagTextPathAttribute = false;
						flagBtValider = flagTextPathAttribute && flagTextMotKey && flagTextNomeChamp;
					}
				}else{
					if(!pathFile.endsWith(".properties")){
		        		pathFile += ".properties";
		        	}
		        	pathFileAttribute = pathFile;
		        	txPathAttributeLettre.setText(pathFileAttribute);
		        	flagTextPathAttribute = true;
		        	flagBtValider = flagTextPathAttribute && flagTextMotKey && flagTextNomeChamp;
				}
				btValider.setEnabled(flagBtValider);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
				
			}
		});
		
		btOpenFolderPathModelLettre.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fileDialog= new FileDialog(shell);
				fileDialog.setText("Choix la model lettre");
				fileDialog.setFilterExtensions(new String[]{"*.odt"});
				pathModelLetter = fileDialog.open();
//				
				Thread operationThread = new Thread() {
			        public void run() {
			        	compositeCreateAttributeLettre.getDisplay().syncExec(new Runnable() {
			                public void run() {
			                	compositeCreateAttributeLettre.getShell().setCursor(new Cursor(
			                			compositeCreateAttributeLettre.getDisplay(),SWT.CURSOR_WAIT));

			                	modifyAllButton(compositeCreateAttributeLettre,false);
			                	fonctionOpenOffice.verifierNombreMotCleModelLettre(pathModelLetter,
			                			pathOffice, portOffice);

			                	compositeCreateAttributeLettre.getShell().setCursor(new Cursor(
			                			compositeCreateAttributeLettre.getDisplay(),SWT.CURSOR_ARROW));

			                	modifyAllButton(compositeCreateAttributeLettre,true);
			                	
			                	if(!fonctionOpenOffice.isFlagNombreMotKey()){
			                		logger.info("il y a pas le mot key");
			                		MessageDialog.openWarning(shell,"Attention",
			                		"il n'y a aucun mot key dans le model lettre !");
			                	}else{
			                		textPathModelLetter.setText(pathModelLetter);
			                		valusListMotKey = fonctionOpenOffice.ConvertListToArrayString(
			                				fonctionOpenOffice.getListMotKeyModelLetter());
			                		swtListMotKey.setItems(valusListMotKey);
//			                		FontData defaultFont = new FontData("Courier",10,SWT.BOLD);
//			                		swtListMotKey.setFont(defaultFont);
			                		valueMotKeyselected = valusListMotKey[0];
			                		indexMotKeyselected = 0;
			                		swtListMotKey.setSelection(indexMotKeyselected);
			                		flagTextMotKey = true;
			    		        	flagBtValider = flagTextPathAttribute && flagTextMotKey && flagTextNomeChamp;
			    		        	btValider.setEnabled(flagBtValider);
			                	}
			                }
			            });
			        }
			    };
			    operationThread.start();
			    try {
					operationThread.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
				
			}
		});
		btOpenFolderPathFileExtraction.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fileDialog= new FileDialog(shell);
				fileDialog.setText("Choix fichier extraction");
				fileDialog.setFilterExtensions(new String[]{"*.txt"});
				pathFileExtraction = fileDialog.open();
				
				Thread operationThread = new Thread() {
			        public void run() {
			        	compositeCreateAttributeLettre.getDisplay().syncExec(new Runnable() {
			                public void run() {
			                	compositeCreateAttributeLettre.getShell().setCursor(new Cursor(
			                			compositeCreateAttributeLettre.getDisplay(),SWT.CURSOR_WAIT));

			                	modifyAllButton(compositeCreateAttributeLettre,false);
								fonctionOpenOffice.setPathFileExtraction(pathFileExtraction);
			                	fonctionOpenOffice.verifierNombreChampFileExtraction(";");

			                	compositeCreateAttributeLettre.getShell().setCursor(new Cursor(
			                			compositeCreateAttributeLettre.getDisplay(),SWT.CURSOR_ARROW));

			                	modifyAllButton(compositeCreateAttributeLettre,true);
			                	
			    				if(!fonctionOpenOffice.isFlagNombreNameChamp()){
			    					logger.info("il y a pas le nom champ");
			    					MessageDialog.openWarning(shell,"Attention","il n'y a aucun le nom champ");
			    				}else{
			    					textPathFileExtraction.setText(pathFileExtraction);
			    					valusListNameChamp = fonctionOpenOffice.ConvertListToArrayString(
			    							fonctionOpenOffice.getListnameChampFileExtraction());
			    					swtListNameChamp.setItems(valusListNameChamp);
//			    					FontData defaultFont = new FontData("Courier",10,SWT.BOLD);
//			    					swtListMotKey.setFont(defaultFont);
			    					valueNameChampSelected = valusListNameChamp[0];
			    					indexNameChampSelected = 0;
			    					swtListNameChamp.setSelection(indexNameChampSelected);
			    					Table tbFileExtraction = compositeCreateAttributeLettre.getTbInfoFileExtraction();			    					
			    					addElementTable(tbFileExtraction);
			    					flagTextNomeChamp = true;
			    		        	flagBtValider = flagTextPathAttribute && flagTextMotKey && flagTextNomeChamp;
			    		        	btValider.setEnabled(flagBtValider);
			    					
			    				}
			                }
			            });
			        }
			    };
			    operationThread.start();
			    try {
					operationThread.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
	} 
	public void addElementTable(Table table) {

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
	
		String[] headerTable = fonctionOpenOffice.getAllLinesFileExtraction().get(0).split(";",-1);
		String[] tableItem1  = fonctionOpenOffice.getAllLinesFileExtraction().get(1).split(";",-1);
		String[] tableItem2  = fonctionOpenOffice.getAllLinesFileExtraction().get(2).split(";",-1);
		
	    
		TableViewer tbv = new TableViewer(table);
		TableLayout layout = new TableLayout(); 
		table.setLayout(layout); 
		TableColumn column;
		for (int i = 0; i < headerTable.length; i++) {
			
			column = new TableColumn(table, SWT.NONE);
			column.setResizable(true);
			column.setText(headerTable[i]);
			column.setWidth(150);
			layout.addColumnData(new ColumnWeightData(150, true));

		}
		
		TableItem item1 = new TableItem(table, SWT.NONE);
	    item1.setText(tableItem1);
	    TableItem item2 = new TableItem(table, SWT.NONE);
	    item2.setText(tableItem2);
	}
	public void actionSwtListMotKey(CompositeCreateAttributeLettre compositeCreateAttributeLettre){
		Button btUpModelLettre = compositeCreateAttributeLettre.getBtUpModelLettre();
		Button btDownModelLettre= compositeCreateAttributeLettre.getBtDownModelLettre();
		List swtListMotKey = compositeCreateAttributeLettre.getListMotKeyModelLettre();
		
		String[] selectedItems = swtListMotKey.getSelection();
		for (int i = 0; i < selectedItems.length; i++) {
			valueMotKeyselected = selectedItems[i]; 
		}
		int[] arraySelectedIndices = swtListMotKey.getSelectionIndices();
        for (int loopIndex = 0; loopIndex < arraySelectedIndices.length; loopIndex++){
        	indexMotKeyselected = arraySelectedIndices[loopIndex];
        }
        if(indexMotKeyselected == 0){
        	btUpModelLettre.setEnabled(false);
        }else{
        	btUpModelLettre.setEnabled(true);
        }
        if(indexMotKeyselected == (swtListMotKey.getItemCount()-1)){
        	btDownModelLettre.setEnabled(false);
        }else{
        	btDownModelLettre.setEnabled(true);
        }
	}
	public void actionSwtListNameChamp(CompositeCreateAttributeLettre compositeCreateAttributeLettre){
		Button btUpNameChamp = compositeCreateAttributeLettre.getBtUpNameChamp();
		Button btDownNameChamp = compositeCreateAttributeLettre.getBtDownNameChamp();
		List swtListNameChamp = compositeCreateAttributeLettre.getListNameChamp();
		
		String[] selectedItems = swtListNameChamp.getSelection();
		for (int i = 0; i < selectedItems.length; i++) {
			valueNameChampSelected = selectedItems[i]; 
		}
		int[] arraySelectedIndices = swtListNameChamp.getSelectionIndices();
        for (int loopIndex = 0; loopIndex < arraySelectedIndices.length; loopIndex++){
        	indexNameChampSelected = arraySelectedIndices[loopIndex];
        }
        if(indexNameChampSelected == 0){
        	btUpNameChamp.setEnabled(false);
        }else{
        	btUpNameChamp.setEnabled(true);
        }
        if(indexNameChampSelected == (swtListNameChamp.getItemCount()-1)){
        	btDownNameChamp.setEnabled(false);
        }else{
        	btDownNameChamp.setEnabled(true);
        }
	}
	public void addSelectionListenerForList(final CompositeCreateAttributeLettre compositeCreateAttributeLettre) {
		
	
		List swtListMotKey = compositeCreateAttributeLettre.getListMotKeyModelLettre();
		
		swtListMotKey.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actionSwtListMotKey(compositeCreateAttributeLettre);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		List swtListNameChamp = compositeCreateAttributeLettre.getListNameChamp();

		swtListNameChamp.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actionSwtListNameChamp(compositeCreateAttributeLettre);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
	}
	public void findMotKeyDansModelLettre() {
//		FonctionGeneral fonctionGeneral = new FonctionGeneral(this.pathOffice,this.portOffice);
//		fonctionGeneral.startServerOpenOffice();
		
	}
	public void initialiseListMotKeyAndNameChamp(CompositeCreateAttributeLettre compositeCreateAttributeLettre,
			String[] arrayMotKey,String[] arrayNameChamp){
		
	}
	
	
	public void initialiseImageButton(CompositeCreateAttributeLettre compositeCreateAttributeLettre) {
		
		final String C_IMAGE_ARROW_DOWN = "/icons/arrow_down.png";
		final String C_IMAGE_ARROW_UP = "/icons/arrow_up.png";
		
		final String C_IMAGE_VALIDER = "/icons/accept.png";
		final String C_IMAGE_ANNULER = "/icons/cancel.png";
		
		if(compositeCreateAttributeLettre instanceof CompositeCreateAttributeLettre){
			
			compositeCreateAttributeLettre.getBtUpModelLettre().
				setImage(Activator.getImageDescriptor(C_IMAGE_ARROW_UP).createImage());
			compositeCreateAttributeLettre.getBtUpModelLettre().setEnabled(false);
			compositeCreateAttributeLettre.getBtDownModelLettre().
				setImage(Activator.getImageDescriptor(C_IMAGE_ARROW_DOWN).createImage());
			
			compositeCreateAttributeLettre.getBtUpNameChamp().
				setImage(Activator.getImageDescriptor(C_IMAGE_ARROW_UP).createImage());
			compositeCreateAttributeLettre.getBtUpNameChamp().setEnabled(false);
			compositeCreateAttributeLettre.getBtDownNameChamp().
				setImage(Activator.getImageDescriptor(C_IMAGE_ARROW_DOWN).createImage());
			
			compositeCreateAttributeLettre.getBtAnnuler().
				setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ANNULER).createImage());
			compositeCreateAttributeLettre.getBtValider().
				setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_VALIDER).createImage());
		}
	}
}