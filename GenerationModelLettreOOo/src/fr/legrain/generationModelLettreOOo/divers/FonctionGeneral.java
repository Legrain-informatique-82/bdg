package fr.legrain.generationModelLettreOOo.divers;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.windows.WinRegistry;
import generationmodellettreooo.Activator;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
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
//import org.eclipse.swt.ole.win32.OLE;
//import org.eclipse.swt.ole.win32.OleAutomation;
//import org.eclipse.swt.ole.win32.OleClientSite;
//import org.eclipse.swt.ole.win32.OleFrame;
//import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
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

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.beans.XPropertySetInfo;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.deployment.XPackageTypeInfo;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.style.BreakType;
import com.sun.star.text.ControlCharacter;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XPropertyReplace;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;


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
	
	private boolean isPrint;
//	private boolean isShowPublipostage;
	
	private List<String> allLinesFileExtraction = new ArrayList<String>();
	
	
	private XComponent xcomponent;
	private XTextDocument aTextDocument;
	
//	private String typeOffice;
	/**
	 * mapModelLettre ==> String est nom de Plugin
	 * 					  List<String> est path de model de lettre sur cette plugin
	 */
	private Map<String, List<String>> mapModelLettre = new HashMap<String, List<String>>();
	private String typeFileOffice = null;
	private XComponentContext xContext;	
	static Logger logger = Logger.getLogger(FonctionGeneral.class.getName());
	
			
	/************* WS Office *************/
//	private Shell shell;
//	private OleFrame frame;
//	private OleClientSite clientSite;
//	private OleAutomation dispInterface;
//	private OleAutomation application;
//	private OleWordBasic wordBasic;
//	private File file;
//	private MenuItem fileItems;
//	private MenuItem containerItems;
	
	/********* new *******/
	private Map<String,String> mapFileParamPublicPostage = new HashMap<String,String>();
	/*************************************/
	public FonctionGeneral() {
		super();
	}
	
	public void startSeverOpenOfficeLinux(){
		
		try {
//			String[] command = new String[]{pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE, 
//					"-headless -nofirststartwizard -accept=socket,host=localhost,port=8100;urp;StarOffice.Service"};

//			Process p1 = Runtime.getRuntime().exec(
//					new String[]{
////									"gksu",
////									pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE+
////									ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE.replace("?", portOpenOffice)
//							pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE,
//							ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE.replace("?", portOpenOffice)
//									//"/usr/lib/openoffice/program/soffice -headless -nofirststartwizard -accept='socket,host=localhost,port=8100;urp;StarOffice.Service'"
//								},
//					null
//			);
//			Process p1 = Runtime.getRuntime().exec(command);
//			String[] commands = new String[] {"soffice","-headless","\"-accept=socket,host=localhost,port=8100;urp;\""};
//			Process p1 = Runtime.getRuntime().exec(commands);
//			p1.waitFor();
			String command = pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE+
							 ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOpenOffice);
			Process p1 = Runtime.getRuntime().exec
//			("/usr/lib/openoffice/program/soffice -headless -nofirststartwizard -accept=socket,host=localhost,port=8100;urp;StarOffice.Service"); 
			(command);
			Thread.sleep(10000);
			 
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void stopSeverOpenOfficeLinux(){
		String command = "killall soffice.bin";
		try {
			Process p1 = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	public void startSeverOpenOfficeWindows(){
		try {
			String cmd = pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE+
						 ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOpenOffice);
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);
			Thread.sleep(10000);
			//p.waitFor();
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
		
		//pathFilePublipostage = dest+File.separator+nameFileOffice+typeFile;
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
	public void connectOpenOffice(String ValueSeparateur,String defautSavePubli,
			String savePubli){
		
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			startSeverOpenOfficeWindows();
		}else if(Platform.getOS().equals(Platform.OS_LINUX)){
			startSeverOpenOfficeLinux();
		}else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
		
//		Calendar calendar = Calendar.getInstance();
//		String nameFileOffice = String.valueOf(calendar.getTimeInMillis());
//		//pathFilePublipostage = pathSavePublipostage+File.separator+nameFileOffice;
//		pathFilePublipostage = pathSavePublipostage+"/"+nameFileOffice;
//		if(typeOffice.equals("OO")){
//			pathFilePublipostage += ConstModelLettre.TYPE_FILE_MODEL_OO;
//		}
//		copyModelOffice(pathFileModelLettre,pathFilePublipostage);

		
		if(!defautSavePubli.equals(savePubli)){
			pathSavePublipostage = savePubli;
		}

		copyModelOffice(pathFileModelLettre,pathSavePublipostage);
		
		XComponentContext xCC;
		try {
			// Create an OO Component Context
			xCC = com.sun.star.comp.helper.Bootstrap.createInitialComponentContext(null);
			// create a connector, so that it can contact the office
			XUnoUrlResolver urlResolver = UnoUrlResolver.create(xCC);
			Object initialObject = urlResolver.resolve(ConstModelLettre.PARAM_CONNECTION_OPEN_OFFICE.replace("?", portOpenOffice));
			
			XMultiComponentFactory xMCF = (XMultiComponentFactory)
			UnoRuntime.queryInterface(XMultiComponentFactory.class,initialObject);
			
			// retrieve the component context as property (it is not yet
			// exported from the office). Query for the XPropertySet interface.
			XPropertySet xProperySet = (XPropertySet) UnoRuntime.queryInterface(
					XPropertySet.class, xMCF);
			
			// Get the default context from the office server.
			Object oDefaultContext = xProperySet.getPropertyValue("DefaultContext");
			
			// Query for the interface XComponentContext.
			xCC = (XComponentContext)UnoRuntime.queryInterface(XComponentContext.class, oDefaultContext);
			
			// now create the desktop service
			// NOTE: use the office component context here!
			Object desktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
//			XDesktop desktop = (XDesktop) xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
			XComponentLoader xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface
			(XComponentLoader.class,desktop); 
			
			PropertyValue[] loadProps = new PropertyValue[1];
			loadProps[0] = new PropertyValue();
//			if(!isShowPublipostage){
//				loadProps[0].Name = "Hidden";
//			}
			loadProps[0].Value = new Boolean(true); 
			
			//file:///....
			//String urlFileOffice = ConstModelLettre.URI_FILE+pathFilePublipostage;
			String urlFileOffice = convertPathFileToUri(new File(pathFilePublipostage).getPath());

			xcomponent = xcomponentloader.loadComponentFromURL(urlFileOffice,"_blank",0,loadProps);
			
			aTextDocument = (XTextDocument)UnoRuntime.queryInterface(
							com.sun.star.text.XTextDocument.class, xcomponent);
			
			searcheMotCleLettreOO(ValueSeparateur);
			
			saveFileOffice(xcomponent,urlFileOffice);
//			if(isPrint){
//				
//			}
//			if(!isShowPublipostage){
//				stopSeverOpenOfficeLinux();
//			}
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 
	 * @param pathFile ==> chemin de open office 
	 * @return ==> EX : "file:///c:/..."  or "file:///home/lee/..."
	 */
	public String convertPathFileToUri(String pathFilePublipostage){
		
//		return "file:///"+pathFile.replace(" ", "%20");
//		URL url = null;
//		try {
//			url = new File(pathFile).toURI().toURL();
//			url = new File(pathFile).toURL();
//			
//		} catch (MalformedURLException e1) {
//			logger.error("",e1);
//		} 
		String pathFile = new File(pathFilePublipostage).getPath();
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			pathFile ="/"+pathFile.replaceAll("\\\\", "/");
		} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
		URI uri = null;
		try {
			//pathFile = new File(pathFile).toString();
			//uri = new URI("file","",url.getFile(),null,null);
			uri = new URI("file","",pathFile,null,null);
			
		} catch (URISyntaxException e) {
			logger.error("",e);
		}
		
		return uri.toString();
	}

	public void saveFileOffice(XComponent xcomponent,String urlText){
		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,xcomponent); 
		PropertyValue storeProperties[] = new PropertyValue[1]; 

		storeProperties[0] = new PropertyValue(); 
		//					storeproperties[0].name = "filtername"; 
		//					storeproperties[0].value = "writer_pdf_Export"; 

		try { 
			xStorable.storeAsURL(urlText, storeProperties); 
		} catch( Exception e) { 
			logger.error("",e);
		} 
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
			String path = new File(filePath).getPath();
			InputStream in = new BufferedInputStream (new FileInputStream(path));
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
	 * trouver les mot clé dans open office , ensuit les remplace
	 */
	public void searcheMotCleLettreOO(String ValueSeparateur){
		String valueRmplace = "";
		String motCleLettre = "";
		//String urlFileModelOffice = ConstModelLettre.URI_FILE+pathFileModelLettre;
		String urlFileModelOffice = convertPathFileToUri(pathFileModelLettre);
		for (int i = 1; i < allLinesFileExtraction.size(); i++) {
			//String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(";",-1);
			String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(ValueSeparateur,-1);
			for (int j = 0; j < arrayValuesChamp.length; j++) {
				motCleLettre = motCleLettreEtPosition.get(j); 
				if(motCleLettreEtPosition.containsKey(j)){
					valueRmplace = arrayValuesChamp[j];
				}
				remplaceMotCleLettre(motCleLettre,valueRmplace);
			}
			if(i!= allLinesFileExtraction.size()-1){
				insertDocument(urlFileModelOffice);
			}
		}
	}
	
	/**
	 * 
	 */
	public void searcheMotCleLettre(String ValueSeparateur){
		String valueRmplace = "";
		String motCleLettre = "";
		String urlFileModelOffice = convertPathFileToUri(pathFileModelLettre);
		for (int i = 1; i < allLinesFileExtraction.size(); i++) {
			//String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(";",-1);
			String[] arrayValuesChamp = allLinesFileExtraction.get(i).split(ValueSeparateur,-1);
			for (int j = 0; j < arrayValuesChamp.length; j++) {
				motCleLettre = motCleLettreEtPosition.get(j); 
				if(motCleLettreEtPosition.containsKey(j)){
					valueRmplace = arrayValuesChamp[j];
				}
				remplaceMotCleLettre(motCleLettre,valueRmplace);
			}
			if(i!= allLinesFileExtraction.size()-1){
				insertDocument(urlFileModelOffice);
			}
		}
	}
	
	public void insertDocument(String urlFile){
		XPropertySet xOrigDocTextCursorProp;
		XText xText = aTextDocument.getText();
		
		// create a text cursor from the cells XText interface
		XTextCursor xTextCursor = xText.createTextCursor();
		xTextCursor.gotoEnd(false);
		
		xOrigDocTextCursorProp = (XPropertySet) UnoRuntime.queryInterface(
				XPropertySet.class,xTextCursor);
		try {
			xText.insertControlCharacter(xTextCursor, ControlCharacter.PARAGRAPH_BREAK, false);
			xOrigDocTextCursorProp.setPropertyValue("BreakType", BreakType.PAGE_BEFORE);
			
			XDocumentInsertable xDocInsert = (XDocumentInsertable)
			UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
			
			xDocInsert.insertDocumentFromURL(urlFile, null);
			
		} 
		catch (Exception e2) {
			logger.error("",e2);
		} 

		
	}
	/**
	 * remplace les mot clé 
	 */
	public void remplaceMotCleLettre(String motCle,String valueRmplace){
		if(motCle != null){
			
			XText mxDoc = aTextDocument.getText();
			XReplaceable xReplaceable = (XReplaceable) UnoRuntime.queryInterface(XReplaceable.class, aTextDocument);
			XReplaceDescriptor xRepDesc = xReplaceable.createReplaceDescriptor();
			
			// set a string to search for
			xRepDesc.setSearchString(motCle);
			

			// set the string to be inserted
			xRepDesc.setReplaceString(valueRmplace);
			
			/**
			 * 
			 */
//			PropertyValue[] aReplaceArgs = new PropertyValue[1];
//			// create PropertyValue struct
//			aReplaceArgs[0] = new PropertyValue();
//			// CharWeight should be bold
//			aReplaceArgs[0].Name = "CharWeight";
//			aReplaceArgs[0].Value = new Float(com.sun.star.awt.FontWeight.BOLD);
			
			
			// set our sequence with one property value as ReplaceAttribute
			XPropertyReplace xPropRepl = (XPropertyReplace) UnoRuntime.queryInterface(
					XPropertyReplace.class, xRepDesc);
//			try {
//				xPropRepl.setReplaceAttributes(aReplaceArgs);
//			} catch (IllegalArgumentException e) {
//				logger.error("",e);
//			} catch (UnknownPropertyException e) {
//				logger.error("",e);
//			}
			
			// replace
			long nResult = xReplaceable.replaceAll(xRepDesc);
		}

		
	}
	/**
	 * En ce moment, on ne sert pas cette fonction. 
	 * @param xText
	 * @throws com.sun.star.uno.Exception
	 * 
	 */
	public void manipulateText(XText xText) throws com.sun.star.uno.Exception {
        // simply set whole text as one string
        xText.setString("He lay flat on the brown, pine-needled floor of the forest, "
            + "his chin on his folded arms, and high overhead the wind blew in the tops "
            + "of the pine trees.");

        // create text cursor for selecting and formatting
        XTextCursor xTextCursor = xText.createTextCursor();
        XPropertySet xCursorProps = (XPropertySet)UnoRuntime.queryInterface(
            XPropertySet.class, xTextCursor);

        // use cursor to select "He lay" and apply bold italic
        xTextCursor.gotoStart(false);
        xTextCursor.goRight((short)6, true);        
        // from CharacterProperties
        xCursorProps.setPropertyValue("CharPosture",    
            com.sun.star.awt.FontSlant.ITALIC);
        xCursorProps.setPropertyValue("CharWeight", 
            new Float(com.sun.star.awt.FontWeight.BOLD)); 

        // add more text at the end of the text using insertString
        xTextCursor.gotoEnd(false);
        xText.insertString(xTextCursor, " The mountainside sloped gently where he lay; "
            + "but below it was steep and he could see the dark of the oiled road "
            + "winding through the pass. There was a stream alongside the road "
            + "and far down the pass he saw a mill beside the stream and the falling water "
            + "of the dam, white in the summer sunlight.", false);
        // after insertString the cursor is behind the inserted text, insert more text
        xText.insertString(xTextCursor, "\n  \"Is that the mill?\" he asked.", false);   
}
	/**
	 * get infos nom de champ file de l'extraction
	 */
	public void getInfosFileExtraction(String valueSeparateur){
		nameChampExtraction.clear();
		allLinesFileExtraction.clear();	
		
		try {
			FileUtils fileUtils = new FileUtils();
			File file = new File(pathFileDataExtraction);
			//ISO-8859-1, cp1252
			allLinesFileExtraction = fileUtils.readLines(file, "UTF-8");
			String listNameChamp = allLinesFileExtraction.get(0);
			String[] arrayNameChamp = listNameChamp.split(valueSeparateur,-1);
			for (int i = 0; i < arrayNameChamp.length; i++) {
				String value = remplaceEspace(arrayNameChamp[i]);
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
	 * @param typeOffice ==> type d'office
	 * en fonction du type d'office pour obtenir path model lettre
	 * chercher toue les plugins
	 */
	public void getInfosModelLetter(String typeOffice){
		mapModelLettre.clear();
				
//		String pathDefautModelLetter = getDefautPathModelLetter(typeOffice);
		String pathDefautModelLetter = ConstModelLettre.FOLDER_MODEL_LETTRE + typeOffice+"/";
		
		
		String pathExteriortModelLetter = getExteriorPathModelLetter(typeOffice);
		tpyeFileOffice(typeOffice);
		
		String fileDefautModelLetter = null;
		Bundle[] bundles = Activator.getDefault().getArrayBundle();
		
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundleElement = bundles[i];
			String nomPlugin = bundleElement.getSymbolicName();
			String pathModelLettreDefaut = null;
			List<String> listPathFileModelLettre = new ArrayList<String>();
			
			/*par defaut on fourni un model de lettre*/
//			pathModelLettreDefaut = pathFichierModelLettre(pathDefautModelLetter,nomPlugin);
//			if(pathModelLettreDefaut != null){
//				listPathFileModelLettre.add(pathModelLettreDefaut);
////				mapModelLettre.put(nomPlugin, listPathFileModelLettre);
//			}
			pathModelLettreDefaut = pathFolderModelLettre(pathDefautModelLetter,nomPlugin);
			if(pathModelLettreDefaut != null){
				listPathFileModelLettre = getFileModelLettre(pathModelLettreDefaut,ConstModelLettre.TYPE_FILE_OFFICE_OO);
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
				List<String> listExteriortModelLetterPlugin = new ArrayList<String>();
				
				listExteriortModelLetterPlugin = getFileModelLettre(ExteriortModelLetterPlugin,ConstModelLettre.TYPE_FILE_OFFICE_OO);
				
				for (String ExteriortModelLetter : listExteriortModelLetterPlugin) {
					listPathFileModelLettre.add(ExteriortModelLetter);
				}
			}
			if(listPathFileModelLettre.size()!=0){
				mapModelLettre.put(nomPlugin, listPathFileModelLettre);
			}
			
		} 

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
	 * obtenir chemin de l'edition defaut EX : /ModelLettre/OO/defautModelLettre.odt
	 * @param typeOffice ==> which type of office EX : OO(OpenOffice) or WO()WordOffice
	 * 		  typeOffice get par preference	
	 * @return
	 */
	public String getDefautPathModelLetter(String typeOffice) {
		return ConstModelLettre.FICHE_FILE_MODELE_LETRRE.replace("?", typeOffice);
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
	
	/**
	 * 
	 * @param pathFolder ==> path of folder parametre publicPosatge
	 * EX : /home/lee/runtime-GestionCommercialeComplet.product/2432009/ModelLettre/paramModelLettreOO
	 * @return
	 */
	public String[] arrayPathFileParamModelLettreOO(String pathFolder,String typeFile){
		
		mapFileParamPublicPostage.clear();
		File folder = new File(pathFolder);
		FilenameFilter filter = new TypeFileXmlFilter(typeFile);
		File[] arrayFileParamEtiquette = folder.listFiles(filter);
		int size = arrayFileParamEtiquette.length;
		//String[] arrayNameFileParamEtiquette = new String[size];
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
	
	public List<String> listPathFileModelLettreDefaut(String pathFolder,String[] arrayTypeFile){
		
		List<String> a = new ArrayList<String>(); 
		File folder = new File(pathFolder);
		for (int i = 0; i < arrayTypeFile.length; i++) {
			String typeFile = arrayTypeFile[i];
			FilenameFilter filter = new TypeFileXmlFilter(typeFile);
			File[] arrayFileModelLettre = folder.listFiles(filter);
					
			for (int j = 0; j < arrayFileModelLettre.length; i++) {
				String pathFile = arrayFileModelLettre[j].getAbsolutePath();
				a.add(pathFile);
			}
		}
		return a;
		
	}
	private class TypeFileXmlFilter implements FilenameFilter{

		private String typeFile;
		
		public TypeFileXmlFilter(String typeFile) {
			super();
			this.typeFile = typeFile;
		}

		@Override
		public boolean accept(File dir, String nameFile) {
			//return false;
			
			//return nameFile.endsWith(".xml");
			//return nameFile.endsWith(".dat");
			return nameFile.endsWith(typeFile);
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
	public ParametrePublicPostage readObjectCastor(String fileName){
		ParametrePublicPostage parametrePublicPostage = null;
		try {
			FileReader reader = new FileReader(fileName);
			parametrePublicPostage = (ParametrePublicPostage)Unmarshaller.unmarshal(ParametrePublicPostage.class,reader);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return parametrePublicPostage;
		
	}
	public void writeObjectCastor(ParametrePublicPostage parametrePublicPostage ,String fileName){
		try {
			FileWriter writer = new FileWriter(fileName);
			Marshaller.marshal(parametrePublicPostage, writer);
			writer.close();
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

	/***************************************************/

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
	public boolean isPrint() {
		return isPrint;
	}
	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}
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

	public Map<String, String> getMapFileParamPublicPostage() {
		return mapFileParamPublicPostage;
	}

	public void setMapFileParamPublicPostage(
			Map<String, String> mapFileParamPublicPostage) {
		this.mapFileParamPublicPostage = mapFileParamPublicPostage;
	}

//	public OleWordBasic getWordBasic() {
//		return wordBasic;
//	}
//
//	public OleAutomation getApplication() {
//		return application;
//	}
//
//	public OleAutomation getDispInterface() {
//		return dispInterface;
//	}
//
//	public void setDispInterface(OleAutomation dispInterface) {
//		this.dispInterface = dispInterface;
//	}
}
