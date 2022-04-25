package fr.legrain.generationModelLettreOOo.divers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;

import sun.dc.pr.PathFiller;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextRangeCompare;
import com.sun.star.uno.Any;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

public class FonctionOpenOffice {
	
	static Logger logger = Logger.getLogger(FonctionOpenOffice.class.getName());
	
	private String pathOffice;
	private String portOffice;
	private String pathModelLetter = "/home/lee/Bureau/testFindMoyKey.odt";
	private String pathFileExtraction;
	
	private XComponent xcomponent;
	private XTextDocument aTextDocument;
	private XComponentContext xCC;
	private XUnoUrlResolver urlResolver;
	private XMultiComponentFactory xMCF;
	private XPropertySet xProperySet;
	private XComponentLoader xcomponentloader;
	private XText mxDoc;
	private XSearchable xSearchable; 
	private XSearchDescriptor xSearchDescriptor;
	private XIndexAccess lIdxAccess;
	private XTextRangeCompare xTextRangeCompare;
	private XDesktop desktop;
	
	private List<String> listMotKeyModelLetter = new LinkedList<String>();
	private List<String> listnameChampFileExtraction = new LinkedList<String>();
	private List<String> allLinesFileExtraction = new ArrayList<String>();
	
	private boolean flagNombreMotKey = false;
	private boolean flagNombreNameChamp = false;
	
	public FonctionOpenOffice(String pathOffice,String portOffice) {
		
		this.pathOffice = pathOffice;
		this.portOffice = portOffice;
	}
	public FonctionOpenOffice() {
		
	}

	public void prepareOpenOffice(String portOffice) {
		try {
			// Create an OO Component Context
			xCC = Bootstrap.createInitialComponentContext(null);
			// create a connector, so that it can contact the office
			urlResolver = UnoUrlResolver.create(xCC);
			Object initialObject = urlResolver.resolve(ConstModelLettre.PARAM_CONNECTION_OPEN_OFFICE.
					replace("?", portOffice));
			xMCF = (XMultiComponentFactory)UnoRuntime.queryInterface(XMultiComponentFactory.class,initialObject);
			// retrieve the component context as property (it is not yet
			// exported from the office). Query for the XPropertySet interface.
			xProperySet = (XPropertySet) UnoRuntime.queryInterface(
					XPropertySet.class, xMCF);
			
			// Get the default context from the office server.
			Object oDefaultContext = xProperySet.getPropertyValue("DefaultContext");
			
			// Query for the interface XComponentContext.
			xCC = (XComponentContext)UnoRuntime.queryInterface(XComponentContext.class, oDefaultContext);
			
			// now create the desktop service
			// NOTE: use the office component context here!
			Object desktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
//			XDesktop desktop = (XDesktop) xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
			xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface
								(XComponentLoader.class,desktop); 
			
			PropertyValue[] loadProps = new PropertyValue[1];
			loadProps[0] = new PropertyValue();
//			if(!isShowPublipostage){
			loadProps[0].Name = "Hidden";
//			}
			loadProps[0].Value = new Boolean(true); 
			String urlFileOffice = convertPathFileToUri(new File(pathModelLetter).getPath());
			
			xcomponent = xcomponentloader.loadComponentFromURL(urlFileOffice,"_blank",0,loadProps);
			aTextDocument = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xcomponent);
//			findAllMotKeyModelLetter(aTextDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
//			e.printStackTrace();
		} 
	}
//	public void findAllMotKeyModelLetter(XTextDocument aTextDocument) {
	public void findAllMotKeyModelLetter() {
		listMotKeyModelLetter.clear();
		
		mxDoc = aTextDocument.getText();
		
		xSearchable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class,aTextDocument);
		xSearchDescriptor = xSearchable.createSearchDescriptor();
		
		
		try {
			xSearchDescriptor.setPropertyValue("SearchWords",new Boolean(true));
			xSearchDescriptor.setPropertyValue("SearchRegularExpression",new Boolean(true));
			xSearchDescriptor.setPropertyValue("SearchCaseSensitive", new Boolean(false) );
			xSearchDescriptor.setSearchString(ConstModelLettre.SEARCH_REGULAR_EXPRESSION);
            XIndexAccess found = xSearchable.findAll(xSearchDescriptor); 
            for (int j = 0 ; j < found.getCount(); j++) 
            { 
               XTextRange range = (XTextRange)UnoRuntime.queryInterface( 
                  XTextRange.class, found.getByIndex(j) ); 
//               XTextRange range = (XTextRange)UnoRuntime.queryInterface(XTextRange.class, found); 
//               range.setString( range.getString().replace( ' ', '\u00a0' ) );
             
               listMotKeyModelLetter.add(range.getString());
//            	  Any o;
//                  o = (Any)found.getByIndex(j);
//                  XTextRange xTextRange = (XTextRange)o.getObject();
//                  String chaineTrouvee = xTextRange.getString();
//                  System.out.println("chaineTrouvee ==> "+chaineTrouvee);

            } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("probleme findAllMotKeyModelLetter()", e);
//			e.printStackTrace();
		} 
	}
	public void findAllNameChampFileExtraction(String separateur){
		FileUtils fileUtils = new FileUtils();
		File file = new File(pathFileExtraction);
		try {
			allLinesFileExtraction = fileUtils.readLines(file, "UTF-8");
			String listNameChamp = allLinesFileExtraction.get(0);
			String[] arrayNameChamp = listNameChamp.split(separateur,-1);
			for (int i = 0; i < arrayNameChamp.length; i++) {
				String value = remplaceEspace(arrayNameChamp[i]);
				listnameChampFileExtraction.add(value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error("", e);
		}
	}
	public String remplaceEspace(String valueRemplace){
		String value = valueRemplace;
		if(value.indexOf(" ") != -1){
			value = valueRemplace.replace(" ", "_");
		}
		return value;
	}
	public void startServerOpenOffice(String pathOffice,String portOffice) {	
		if(Platform.getOS().equals(Platform.OS_WIN32)){
			startSeverOpenOfficeWindows(pathOffice,portOffice);
		}else if(Platform.getOS().equals(Platform.OS_LINUX)){
			startSeverOpenOfficeLinux(pathOffice,portOffice);
		} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
	}
	
	public void startSeverOpenOfficeWindows(String pathOffice,String portOffice){
		try {
			String cmd = pathOffice+"/"+ConstModelLettre.WIN_LINUX_SOFFICE+
						 ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOffice);
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);
			Thread.sleep(10000);
			//p.waitFor();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	public void startSeverOpenOfficeLinux(String pathOffice,String portOffice){
		
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
							 ConstModelLettre.PARAM_START_SERVER_OPEN_OFFICE_PARAM1.replace("?", portOffice);
			Process p1 = Runtime.getRuntime().exec
//			("/usr/lib/openoffice/program/soffice -headless -nofirststartwizard -accept=socket,host=localhost,port=8100;urp;StarOffice.Service"); 
			(command);
			Thread.sleep(10000);
			 
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
		}
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
	
	public void closeDocument() {
		aTextDocument.dispose();
		desktop.terminate();
//		try {
//			if (xTextDocument != null) xTextDocument.dispose();
//			if (xDesktop != null) xDesktop.terminate();
//		} catch (Exception e) {}
//	
	}
	



	public String getPathModelLetter() {
		return pathModelLetter;
	}

	public void setPathModelLetter(String pathModelLetter) {
		this.pathModelLetter = pathModelLetter;
	}
	public String getPathOffice() {
		return pathOffice;
	}
	public void setPathOffice(String pathOffice) {
		this.pathOffice = pathOffice;
	}
	public String getPortOffice() {
		return portOffice;
	}
	public void setPortOffice(String portOffice) {
		this.portOffice = portOffice;
	}
	public String getPathFileExtraction() {
		return pathFileExtraction;
	}
	public void setPathFileExtraction(String pathFileExtraction) {
		this.pathFileExtraction = pathFileExtraction;
	}
	public List<String> getListMotKeyModelLetter() {
		return listMotKeyModelLetter;
	}
	public void setListMotKeyModelLetter(List<String> listMotKeyModelLetter) {
		this.listMotKeyModelLetter = listMotKeyModelLetter;
	}
	public List<String> getListnameChampFileExtraction() {
		return listnameChampFileExtraction;
	}
	public void setListnameChampFileExtraction(
			List<String> listnameChampFileExtraction) {
		this.listnameChampFileExtraction = listnameChampFileExtraction;
	}
	public List<String> getAllLinesFileExtraction() {
		return allLinesFileExtraction;
	}
	public void setAllLinesFileExtraction(List<String> allLinesFileExtraction) {
		this.allLinesFileExtraction = allLinesFileExtraction;
	}
	
	/*** ***/
	public boolean verifierNombreMotCleModelLettre(String pathModelLetter,String pathOpenOffice,String portOpenOffice){
		
		this.pathModelLetter = pathModelLetter;
		startServerOpenOffice(pathOpenOffice, portOpenOffice);
		prepareOpenOffice(portOpenOffice);
		findAllMotKeyModelLetter();
//		closeDocument();
		if(listMotKeyModelLetter.size() != 0){
			flagNombreMotKey = true;
		}
		//return flag;
		return flagNombreMotKey;
		
	}
	
	public boolean verifierNombreChampFileExtraction(String separateur){
		
		findAllNameChampFileExtraction(separateur);
		if(listnameChampFileExtraction.size() != 0){
			flagNombreNameChamp = true;
		}
		return flagNombreNameChamp;
	}
	
	public String[] ConvertListToArrayString(List<String> listString) {
		String[] arrayString = new String[listString.size()];
		
		for (int i = 0; i < listString.size(); i++) {
			arrayString[i] = listString.get(i);
		}
		return arrayString;
	}
	public boolean isFlagNombreMotKey() {
		return flagNombreMotKey;
	}
	public boolean isFlagNombreNameChamp() {
		return flagNombreNameChamp;
	}

}
