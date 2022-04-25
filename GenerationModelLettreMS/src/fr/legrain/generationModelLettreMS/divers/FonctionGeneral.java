package fr.legrain.generationModelLettreMS.divers;

import fr.legrain.generationModelLettreMS.divers.OleWordBasic;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.FireBirdManager;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import generationmodellettrems.Activator;




import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.osgi.framework.Bundle;
import org.apache.commons.io.FileUtils;

public class FonctionGeneral {
	
	/**
	 * ces variable est pour conserver les values de WizardPage
	 */
	private String pathFileDataExtraction = null;
	private String pathFileModelLettre = null;
	private String pathFileMotCle = null;
	private String namePlugin = null;
	private List<String> motCleModelLettre = new ArrayList<String>();
	private List<String> nameChampExtraction = new LinkedList<String>();
	/**
	 * quelle position les mots cle de lettre dans file d'extraction 
	 */
	private Map<Integer,String> motCleLettreEtPosition = new HashMap<Integer,String>();
	
	private String typeOffice;
	// pathOffice ==> where Open Office is installer
	private String pathOffice;
	private String portOpenOffice;
	private String pathSavePublipostage;
	private String pathFilePublipostage;
	
//	private boolean isPrint;
//	private boolean isShowPublipostage;
	
	private List<String> allLinesFileExtraction = new ArrayList<String>();
	
//	private String typeOffice;
	/**
	 * mapModelLettre ==> String est nom de Plugin
	 * 					  List<String> est path de model de lettre sur cette plugin
	 */
	private Map<String, List<String>> mapModelLettre = new HashMap<String, List<String>>();
	private String typeFileOffice = null;
	static Logger logger = Logger.getLogger(FonctionGeneral.class.getName());
	
			
	/************* WS Office *************/
	private Shell shell;
	private OleFrame frame;
	private OleClientSite clientSite;
	private OleAutomation dispInterface;
	private OleAutomation application;
	private OleWordBasic wordBasic;
	private File file;
	private MenuItem fileItems;
	private MenuItem containerItems;
	/*************************************/
	private Map<String,String> mapFileParamPublicPostage = new HashMap<String,String>(); 
	
	public FonctionGeneral() {
		super();
	}
	
	public void startSeverOpenOfficeLunix(){
		
		try {
//			Process p1 = Runtime.getRuntime().exec(
//					new String[]{
//									"gksu",
//									pathOffice+ConstModelLettre.WIN_LINUX_SOFFICE+
//									ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE.replace("?", portOpenOffice)
//								},
//					null
//			);
//			p1.waitFor();
			String command = pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE+
			 				 ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOpenOffice);
			Process p1 = Runtime.getRuntime().exec
			//("/usr/lib/openoffice/program/soffice -headless -nofirststartwizard -accept=socket,host=localhost,port=8100;urp;StarOffice.Service"); 
			(command);
			Thread.sleep(10000);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void startSeverOpenOfficeWindows(){
		
		try {

			String cmd = pathOffice+ConstModelLettre.WIN_LINUX_SOFFICE+
			//ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE.replace("?", portOpenOffice);
			ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOpenOffice);
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);
			//p.waitFor();
			Thread.sleep(10000);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 
	 * @param src ==> model office
	 * @param dest ==> file office de publipostage
	 */
	public void copyModelOffice(String src,String dest){
		
		Calendar calendar = Calendar.getInstance();
		String nameFileOffice = String.valueOf(calendar.getTimeInMillis());
		
		String nameFile = null;
		String typeFile = null;
		
		int positionLastPoint = src.lastIndexOf(".");
		if(positionLastPoint != -1){
			nameFile = src.substring(0, positionLastPoint);
			typeFile = src.substring(positionLastPoint);
		}
		
		pathFilePublipostage = dest+"/"+nameFileOffice+typeFile;
		File fileScr = new File(src);
		File fileDest = new File(pathFilePublipostage);
		FileUtils fileUtils = new FileUtils();
		try {
			fileUtils.copyFile(fileScr, fileDest);
		} catch (IOException e) {
			logger.error("",e);
		}
		
	}

	
	/**
	 * 
	 * @param pathFile ==> chemin de open office 
	 * @return ==> EX : "file:///c:/..."  or "file:///home/lee/..."
	 */
	public String convertPathFileToUri(String pathFile){
		URL url = null;
		try {
			url = new File(pathFile).toURI().toURL();
			
		} catch (MalformedURLException e1) {
			logger.error("",e1);
		}

		URI uri = null;
		try {
			uri = new URI("file","",url.getFile(),null,null);
			
		} catch (URISyntaxException e) {
			logger.error("",e);
		}
		return uri.toString();
	}
	

	/**
	 * 
	 * @param filePath ==> path file de properties
	 * @param key ==> cle de properties
	 * @param positionMotCleLettre ==> mot cle de Lettre positionne
	 * @return
	 */
	public boolean readValueMotCleLettre(String filePath,String key,int positionMotCleLettre) {
		boolean isFound = true;
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
//			if(value==null){
//				isFound = false;
//			}else {
//				motCleModelLettre.add(value);
//				motCleLettreEtPosition.put(positionMotCleLettre,value);
//			}
			if(value != null){
				motCleModelLettre.add(value);
				motCleLettreEtPosition.put(positionMotCleLettre,value);
			}
			return isFound;
		} catch (Exception e) {
			logger.error("",e);
			return false;
		}
	}
	/**
	 * verifier entre AttributeModelLettre.properties et le nombre de valeur dans le file d'extraction
	 * EX : nombre de valeurs >= mots de cle dans la mode de lettre 
	 * 		et plus , Entre valeurs d'extraction et mots de cle sont correspondants.
	 *
	 * @return
	 */
	public boolean verityFileExtractionAndModelLettre(WizardPage wizardPage,String message,String valueSeparateur){
		
		motCleModelLettre.clear();
		motCleLettreEtPosition.clear();
		getInfosFileExtraction(valueSeparateur);
		
		boolean flag = true;
		for (int i = 0; i < nameChampExtraction.size(); i++) {
			String nomChamp = nameChampExtraction.get(i);
			flag = readValueMotCleLettre(pathFileMotCle, nomChamp,i);
			if(!flag){
				wizardPage.setMessage(message, DialogPage.ERROR);
				break;
			}
		}
		
		return flag;
	}
	
	
	/**
	 * trouver les mot clé dans WS office ,et plus, les remplace
	 */
	public void searcheMotCleLettreMS(String valueSeparateur){
		String valueRmplace = "";
		String motCleLettre = "";
		for (int i = 1; i < allLinesFileExtraction.size(); i++) {
//			String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(";",-1);
			String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(valueSeparateur,-1);
			for (int j = 0; j < arrayValuesChamp.length; j++) {
				if(motCleLettreEtPosition.containsKey(j)){
					motCleLettre = motCleLettreEtPosition.get(j);
					if(motCleLettreEtPosition.containsKey(j)){
						valueRmplace = arrayValuesChamp[j];
					}
					wordBasic.replace(motCleLettre, valueRmplace, application);
				}
			}
			if(i!= allLinesFileExtraction.size()-1){
				wordBasic.InsertFile(pathFileModelLettre);
			}
		}
	}


	/**
	 * get infos nom de champ file de l'extraction
	 */
	public void getInfosFileExtraction(String valueSeparteur){
		nameChampExtraction.clear();
		allLinesFileExtraction.clear();		
		String value = "";
		try {
			FileUtils fileUtils = new FileUtils();
			File file = new File(pathFileDataExtraction);
			allLinesFileExtraction = fileUtils.readLines(file, "UTF-8");
			String listNameChamp = allLinesFileExtraction.get(0);
			String[] arrayNameChamp = listNameChamp.split(valueSeparteur,-1);
			for (int i = 0; i < arrayNameChamp.length; i++) {
				value = remplaceEspace(arrayNameChamp[i]);
				nameChampExtraction.add(value);
			}
		} catch (IOException e) {
			logger.error("",e);
		}

	}
	
	public String remplaceEspace(String valueRemplace){
		String value = valueRemplace;
		if(value.indexOf(" ") != -1){
			value = valueRemplace.replace(" ", "_");
		}
		return value;
	}
	public static String pathFichierModelLettre(String path,String namePlugin){
		
		String pathFileModelLettre = null;
		String tmp = new Path(ConstModelLettre.FOLDER_MODEL_LETTRE).toOSString();
		Bundle bundleModelLettre=null;
		if(new Path(path).toOSString().contains(tmp))
			bundleModelLettre = Platform.getBundle(namePlugin);
		//		else	
		//			bundleEditions = Platform.getBundle(Const.PLUGIN_EDITIONSPECIFIQUES);
		if(bundleModelLettre==null)return null;
		URL urlModelLettre;
		try {

			urlModelLettre = FileLocator.find(bundleModelLettre,new Path(path),null);
			if(urlModelLettre!=null){
				urlModelLettre = FileLocator.toFileURL(urlModelLettre);

				URI uriModelLettre = new URI("file", urlModelLettre.getAuthority(),
						urlModelLettre.getPath(), urlModelLettre.getQuery(), null);
				File modelLettre = new File(uriModelLettre);
				pathFileModelLettre = modelLettre.getAbsolutePath();
				//return pathFileModelLettre;
			}else{
				return null;
			}
		} catch (java.io.IOException e) {
			logger.error("",e);
		} catch (URISyntaxException e) {
			logger.error("",e);
		}
		return pathFileModelLettre;
	}
	
	public static String pathFolderModelLettre(String path,String namePlugin){
		
		String pathFolderModelLettre = null;
		String tmp = new Path(ConstModelLettre.FOLDER_MODEL_LETTRE).toOSString();
		Bundle bundleModelLettre=null;
		if(new Path(path).toOSString().contains(tmp))
			bundleModelLettre = Platform.getBundle(namePlugin);
		//		else	
		//			bundleEditions = Platform.getBundle(Const.PLUGIN_EDITIONSPECIFIQUES);
		if(bundleModelLettre==null)return null;
		URL urlModelLettre;
		try {
			urlModelLettre = FileLocator.find(bundleModelLettre,new Path(path),null);
			if(urlModelLettre!=null){
				urlModelLettre = FileLocator.toFileURL(urlModelLettre);

				URI uriModelLettre = new URI("file", urlModelLettre.getAuthority(),
						urlModelLettre.getPath(), urlModelLettre.getQuery(), null);
				File modelLettre = new File(uriModelLettre);
				pathFolderModelLettre = modelLettre.getAbsolutePath();
				//return pathFileModelLettre;
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathFolderModelLettre;
		
	}
	/**
	 * @param typeOffice ==> type d'office (Word Office)
	 * en fonction du type d'office pour obtenir path model lettre
	 */
	public void getInfosModelLetter(String typeOffice){
		mapModelLettre.clear();
		
//		String pathDefautModelLetter = getDefautPathModelLetter(typeOffice);
		String pathDefautModelLetter = ConstModelLettre.FOLDER_MODEL_LETTRE + typeOffice+"/";
		
		//String[] pathDefautModelLetter = getDefautPathModelLetter(ConstModelLettre.ARRAY_TYPE_FILE_MODEL_WO);
		String pathExteriortModelLetter = getExteriorPathModelLetter(typeOffice);
		tpyeFileOffice(typeOffice);
		
		String fileDefautModelLetter = null;
		Bundle[] bundles = Activator.getDefault().getArrayBundle();
		
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundleElement = bundles[i];
			String nomPlugin = bundleElement.getSymbolicName();
			String pathModelLettreDefaut = null;
			List<String> listPathFileModelLettre = new ArrayList<String>();
////			for (int j = 0; j < pathDefautModelLetter.length; j++) {
////				pathModelLettreDefaut = pathFichierModelLettre(pathDefautModelLetter[j],nomPlugin);
//				if(pathModelLettreDefaut != null){
//					listPathFileModelLettre.add(pathModelLettreDefaut);
//				}
////			}
			
			pathModelLettreDefaut = pathFolderModelLettre(pathDefautModelLetter,nomPlugin);
			if(pathModelLettreDefaut != null){
				listPathFileModelLettre = getFileModelLettre(pathModelLettreDefaut,ConstModelLettre.TYPE_FILE_OFFICE_WS);
			}
			
			File fileExteriortModelLetter = new File(pathExteriortModelLetter+"/"+nomPlugin);
			String ExteriortModelLetterPlugin = fileExteriortModelLetter.getAbsolutePath()+"/";
			if(fileExteriortModelLetter.exists()){
//				FilenameFilter typeFileOfficeFilter = new TypeFileOfficeFilter();
//				String[] arrayFileExteriortModelLetter = fileExteriortModelLetter.list(typeFileOfficeFilter);
//				if(arrayFileExteriortModelLetter.length!=0){
//					for (int j = 0; j < arrayFileExteriortModelLetter.length; j++) {
//						listPathFileModelLettre.add(ExteriortModelLetterPlugin+arrayFileExteriortModelLetter[j]);
//					}
//				}
				
				List<String> listExteriortModelLetter = new ArrayList<String>();
				listExteriortModelLetter = getFileModelLettre(ExteriortModelLetterPlugin, 
						ConstModelLettre.TYPE_FILE_OFFICE_WS);
				for (String ExteriortModelLetter : listExteriortModelLetter) {
					listPathFileModelLettre.add(ExteriortModelLetter);
				}
				
			}
			if(listPathFileModelLettre.size()!=0){
				mapModelLettre.put(nomPlugin, listPathFileModelLettre);
			}
			
		} 

	}

	public List<String> getFileModelLettre(String pathFolder,String[] arrayTypeFile){
		List<String> listPathFileModleLettre = new ArrayList<String>();
		
		File file = new File(pathFolder);
		
		File[] listFile = file.listFiles();
	
		for (File elementFile : listFile) {
			String pathFile = elementFile.getAbsolutePath();
			for (int i = 0; i < arrayTypeFile.length; i++) {
				if(pathFile.endsWith(arrayTypeFile[i])){
					listPathFileModleLettre.add(pathFile);
				}
			}
		}
		return listPathFileModleLettre;
	}
	
	public String tpyeFileOffice(String typeOffice){
		String typeFile = null;
		if(typeOffice.equals(Const.MODEL_LETTRE_OO)){
			typeFile = ConstModelLettre.TYPE_FILE_MODEL_OO;
		}else if(typeOffice.equals(Const.MODEL_LETTRE_WO)){
			typeFile = ConstModelLettre.TYPE_FILE_MODEL_WO;
		}
		typeFileOffice = typeFile;
		return typeFile;
	}
	/**
	 * obtenir chemin de l'edition defaut EX : /ModelLettre/OO/defautModelLettre.doc or .docx 
	 * @param typeOffice ==> which type of office EX : OO(OpenOffice) or WO()WordOffice
	 * 		  typeOffice get par preference	
	 * @return
	 */
	//public String[] getDefautPathModelLetter(String[] typeFileOffice) {
	public String getDefautPathModelLetter(String typeFileOffice) {
		return ConstModelLettre.FICHE_FILE_MODELE_LETTRE.replace("?", typeOffice);
//		String[] fileDefautModelLettreWO = new String[typeFileOffice.length];
//		for (int i = 0; i < typeFileOffice.length; i++) {
//			 fileDefautModelLettreWO[i] = ConstModelLettre.FOLDER_DEFAUT_MODELE_LETTRE_WO+
//			 							  ConstModelLettre.NAME_FILE_DEFAUT_MODELE_LETTRE+
//			 							  typeFileOffice[i];			  
//		}
//		return fileDefautModelLettreWO;
	}
	/**
	 * obtenir chemin de l'edition à côté de la dossier
	 * EX : runtime-GestionCommercialeComplet.product/ModelLettre/OO
	 *  @param typeOffice ==> which type of office EX : OO(OpenOffice) or WO()WordOffice
	 * 		   typeOffice get par preference	
	 * @return
	 */
	public String getExteriorPathModelLetter(String typeOffice) {
		return Const.PATH_FOLDER_MODEL_LETTRE_DOSSIER+"/"+typeOffice;
	}


	/**
	 * filter files en fonction de type de l'office
	 * EX : OpenOffice is xxx.odt
	 * 	    WordOffice is xxx.doc
	 * @author lee
	 *
	 */
	private class TypeFileOfficeFilter implements FilenameFilter{

		String pathFileModelLetter = null;
		@Override
		public boolean accept(File arg0, String arg1) {
			//return false;
			return arg1.endsWith(typeFileOffice);
		}
		
	}

	/************fonction ws office*********/
	
	public void open() {
		copyModelOffice(pathFileModelLettre,pathSavePublipostage);
		// Init shell
		shell = new Shell();
		shell.setLayout(new FillLayout());
		// Add a menubar
		createMenuBar();
		//pour obtenir path file de WS pour open
		//if (!getFile(SWT.OPEN)) return;
		
		/** zhaolin **/
		file  = new File(pathFilePublipostage);
		// Create the Document:
		// Every Document must have an associated OleFrame
		frame = new OleFrame(shell, SWT.NONE);
		// add your own File, Window and Container menus - optional
		frame.setFileMenus(new MenuItem[] {fileItems});
		frame.setContainerMenus(new MenuItem[] {containerItems});
		// Finally, create the Document Container and init the Document
		try {
			clientSite = new OleClientSite(frame, SWT.NONE, file);
		} catch (SWTException e) {
			MessageBox message = new MessageBox(shell);
			message.setMessage("Unable to open the selected document. "+e.getMessage());
			message.open();
			shell.dispose();
			return;
		}
		// Create The Word Basic Automation object
		initWordBasicIF();
		// Open the Document in the InPlaceActive state
		clientSite.doVerb(OLE.OLEIVERB_SHOW);
//		if(isShowPublipostage){
			shell.open();
//		}
	}
	public void run() {
		Display display = shell.getDisplay();
		while (!shell.isDisposed() && shell.isVisible() ) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void close() {
		// cleanup automation objects
		if (wordBasic != null)
			wordBasic.dispose();
		wordBasic = null;
		if (application != null)
			application.dispose();
		application = null;
		if (dispInterface != null)
			dispInterface.dispose();
		dispInterface = null;
		if ((shell != null) && (!shell.isDisposed()))
			shell.dispose();
		shell = null;
	}
	private void initWordBasicIF() {
		// Get generic IDispatch interface
		dispInterface = new OleAutomation(clientSite);
		// Get Application
		int[] appId = dispInterface.getIDsOfNames(new String[]{"Application"});
		if (appId == null) OLE.error(OLE.ERROR_APPLICATION_NOT_FOUND);
		Variant pVarResult = dispInterface.getProperty(appId[0]);
		if (pVarResult == null) OLE.error(OLE.ERROR_APPLICATION_NOT_FOUND);
		application = pVarResult.getAutomation();
		// Get Word Basic
		int[] wbId = application.getIDsOfNames(new String[]{"WordBasic"});
		if (wbId == null) OLE.error(OLE.ERROR_APPLICATION_NOT_FOUND);
		Variant pVarResult2 = application.getProperty(wbId[0]);
		if (pVarResult2 == null) OLE.error(OLE.ERROR_APPLICATION_NOT_FOUND);
		wordBasic = new OleWordBasic( pVarResult2.getAutomation());
		
		/** zhaolin **/
		
		/** zhaolin **/
	}
	private Menu createMenuBar() {
		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		// File menu items
		fileItems = new MenuItem(bar, SWT.CASCADE);
		fileItems.setText("&File");
		Menu menu = new Menu(bar);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("&Save");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileSave();
			}
		});
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("&Save As...");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileSaveAs();
			}
		});
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("&Exit");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				menuFileExit();
			}
		});
		fileItems.setMenu(menu);
		// Container menu items
		containerItems = new MenuItem(bar, SWT.CASCADE);
		containerItems.setText("&Java");
		menu = new Menu(bar);
//		item = new MenuItem(menu, SWT.PUSH);
//		item.setText("&Copy to Clipboard");
//		item.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event e) {
//				copyToClipboard();
//			}
//		});
//		item = new MenuItem(menu, SWT.PUSH);
//		item.setText("&Check Spelling");
//		item.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event e) {
//				spellCheck();
//			}
//		});
//		item = new MenuItem(menu, SWT.PUSH);
//		item.setText("C&hange font");
//		item.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event e) {
//				changeFont();
//			}
//		});
//		item = new MenuItem(menu, SWT.PUSH);
//		item.setText("&Insert Text");
//		item.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event e) {
//				insertText();
//			}
//		});
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("&Insert File");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String pathWord = "C:/test_ws/sauvegarde/modelLettre.doc";
				wordBasic .InsertFile(pathWord);
			}
		});
		
		item = new MenuItem(menu, SWT.PUSH);
		item.setText("&Remplace Word");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				wordBasic .replace("<NOMTIERS>", "lee",application);
			}
		});
		containerItems.setMenu(menu);
		return bar;
	}
	
	private void menuFileExit() {
		shell.close();
	}
	
	public void menuFileSave() {
		if (file != null && file.exists()) {
			File tempFile = new File(file.getAbsolutePath() + ".tmp");
			file.renameTo(tempFile);
			//if (clientSite.save(file, true)) {
			if (clientSite.save(file, true)){
				// save was successful so discard the backup
				tempFile.delete();
			} else {
				// save failed so restore the backup
				tempFile.renameTo(file);
			}
		} else {
			menuFileSaveAs();
		}
	}
	/**
	 * for print les document
	 */
	public void menuFilePrint() {
		clientSite.exec(OLE.OLECMDID_PRINT, OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
	}
	private void menuFileSaveAs() {
		if (!getFile(SWT.SAVE)) return;
		//clientSite.save(file, true);
		clientSite.save(file, true);
	}
	/**
	 * pour obtenir path de file WS
	 * @param style
	 * @return
	 */
	private boolean getFile(int style) {
		FileDialog dialog = new FileDialog(shell, style);
		dialog.setFilterExtensions(new String[]{"*.doc"});
		dialog.setFilterNames(new String[] {"Word Documents (*.doc)"});
		dialog.setFilterPath("C:");
		String fileName = dialog.open();
		if (fileName == null) {
			return false;
		}
		file = new File(fileName);
		shell.setText(file.getName());
		return true;
	}
	/**
	 * 
	 * @param pathFolder ==> path of folder parametre publicPosatge
	 * EX : /home/lee/runtime-GestionCommercialeComplet.product/2432009/ModelLettre/paramModelLettreOO
	 * @return
	 */
	public String[] arrayPathFileParamModelLettreMS(String pathFolder,String typeFile){
		
		mapFileParamPublicPostage.clear();
		File folder = new File(pathFolder);
		FilenameFilter filter = new TypeFileXmlFilter(typeFile);
		File[] arrayFileParamEtiquette = folder.listFiles(filter);
		int size = arrayFileParamEtiquette.length;
		String[] arrayNameFileParamEtiquette = new String[size+1];
		
		arrayNameFileParamEtiquette[0] = ConstModelLettre.CHOIX_DEFAUT_CCOMB_PARAM_PUBLIPOSTAGE;
		for (int i = 0; i < arrayFileParamEtiquette.length; i++) {
			int position = arrayFileParamEtiquette[i].getName().indexOf(".");
			String nameFileParamEtiquette = arrayFileParamEtiquette[i].getName().substring(0, position);
			String pathFileParamEtiquette = arrayFileParamEtiquette[i].getAbsolutePath();
			mapFileParamPublicPostage.put(nameFileParamEtiquette, pathFileParamEtiquette);
			arrayNameFileParamEtiquette[i+1] = nameFileParamEtiquette;
		}
		//return folder.list(filter);
		return arrayNameFileParamEtiquette;
	}
	private class TypeFileXmlFilter implements FilenameFilter{

		private String typeFile;
		
		public TypeFileXmlFilter(String typeFile) {
			super();
			this.typeFile = typeFile;
		}

		@Override
		public boolean accept(File dir, String nameFile) {
			// TODO Auto-generated method stub
			//return false;
			
			return nameFile.endsWith(typeFile);
			//return nameFile.endsWith(".dat");
		}
		
	}
	
	public ParametrePublicPostage readObjectCastor(String fileName){
		ParametrePublicPostage parametrePublicPostage = null;
		try {
			FileReader reader = new FileReader(fileName);
			parametrePublicPostage = (ParametrePublicPostage)Unmarshaller.unmarshal(ParametrePublicPostage.class,reader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parametrePublicPostage;
		
	}
	public void writeObjectCastor(ParametrePublicPostage parametrePublicPostage ,String fileName){
		try {
			FileWriter writer = new FileWriter(fileName);
			Marshaller.marshal(parametrePublicPostage, writer);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/***********************************/
	public boolean checkValueString(String valueCheck){
		boolean flag = false;
		if(valueCheck != null){
			if(valueCheck.length() != 0){
				flag = true;
			}
		}
		return flag;
	}
	/***********************************/
	public String getPathFileDataExtraction() {
		return pathFileDataExtraction;
	}
	public void setPathFileDataExtraction(String pathFileDataExtraction) {
		this.pathFileDataExtraction = pathFileDataExtraction;
	}
	public String getPathFileModelLettre() {
		return pathFileModelLettre;
	}
	public void setPathFileModelLettre(String pathFileModelLettre) {
		this.pathFileModelLettre = pathFileModelLettre;
	}
	public String getNamePlugin() {
		return namePlugin;
	}
	public void setNamePlugin(String namePlugin) {
		this.namePlugin = namePlugin;
	}
	public String getPathFileMotCle() {
		return pathFileMotCle;
	}
	public void setPathFileMotCle(String pathFileMotCle) {
		this.pathFileMotCle = pathFileMotCle;
	}
	public String getTypeOffice() {
		return typeOffice;
	}
	public void setTypeOffice(String typeOffice) {
		this.typeOffice = typeOffice;
	}
	public String getPathOffice() {
		return pathOffice;
	}
	public void setPathOffice(String pathOffice) {
		this.pathOffice = pathOffice;
	}
	public String getPortOpenOffice() {
		return portOpenOffice;
	}
	public void setPortOpenOffice(String portOpenOffice) {
		this.portOpenOffice = portOpenOffice;
	}
	public List<String> getMotCleModelLettre() {
		return motCleModelLettre;
	}
	public void setMotCleModelLettre(List<String> motCleModelLettre) {
		this.motCleModelLettre = motCleModelLettre;
	}
	public List<String> getNameChampExtraction() {
		return nameChampExtraction;
	}
	public void setNameChampExtraction(List<String> nameChampExtraction) {
		this.nameChampExtraction = nameChampExtraction;
	}
//	public boolean isPrint() {
//		return isPrint;
//	}
//	public void setPrint(boolean isPrint) {
//		this.isPrint = isPrint;
//	}
//	public boolean isShowPublipostage() {
//		return isShowPublipostage;
//	}
//	public void setShowPublipostage(boolean isShowPublipostage) {
//		this.isShowPublipostage = isShowPublipostage;
//	}
	public Map<String, List<String>> getMapModelLettre() {
		return mapModelLettre;
	}
	public void setMapModelLettre(Map<String, List<String>> mapModelLettre) {
		this.mapModelLettre = mapModelLettre;
	}
	public String getPathSavePublipostage() {
		return pathSavePublipostage;
	}
	public void setPathSavePublipostage(String pathSavePublipostage) {
		this.pathSavePublipostage = pathSavePublipostage;
	}
	public String getPathFilePublipostage() {
		return pathFilePublipostage;
	}
	public void setPathFilePublipostage(String pathFilePublipostage) {
		this.pathFilePublipostage = pathFilePublipostage;
	}

	public OleWordBasic getWordBasic() {
		return wordBasic;
	}

	public OleAutomation getApplication() {
		return application;
	}

	public OleAutomation getDispInterface() {
		return dispInterface;
	}

	public void setDispInterface(OleAutomation dispInterface) {
		this.dispInterface = dispInterface;
	}

	public Map<String, String> getMapFileParamPublicPostage() {
		return mapFileParamPublicPostage;
	}

	public void setMapFileParamPublicPostage(
			Map<String, String> mapFileParamPublicPostage) {
		this.mapFileParamPublicPostage = mapFileParamPublicPostage;
	}
}
