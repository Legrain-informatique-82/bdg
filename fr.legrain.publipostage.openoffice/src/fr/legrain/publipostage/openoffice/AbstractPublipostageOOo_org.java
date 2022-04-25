package fr.legrain.publipostage.openoffice;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.style.BreakType;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.table.XTableRows;
import com.sun.star.text.ControlCharacter;
import com.sun.star.text.XParagraphCursor;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextTablesSupplier;
import com.sun.star.text.XTextViewCursorSupplier;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XPropertyReplace;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

import fr.legrain.publipostage.Publipostage;
import fr.legrain.publipostage.divers.ParamPublipostage;

public abstract class AbstractPublipostageOOo_org extends Publipostage {

	static Logger logger = Logger.getLogger(AbstractPublipostageOOo_org.class.getName());
			
	public static final String WIN32_LINUX_SOFFICE ="soffice";
	public static final String PARAMS_START_SERVER_OPENOFFICE_PARAM1 = " -accept=socket,host=localhost,port=?;urp";
	public static final String PARAM_CONNECTION_OPEN_OFFICE = 
							"uno:socket,host=localhost,port=?;urp;StarOffice.ServiceManager";
	protected String portOpenOffice = null;
	
	protected String cheminOpenOffice = null;
	private Map<ParamPublipostage, XComponent> map = new HashMap<ParamPublipostage, XComponent>();
	
	protected XText xT;
	protected XComponent xC;
	protected XTextDocument xTD;
	protected XTextTable xTT;
	protected XComponentContext xCC;
	protected XMultiComponentFactory xMCF;
	protected XMultiServiceFactory xMSF;
	protected XSearchable xS; 
	protected XSearchDescriptor xSD;
	protected XTextTablesSupplier xTTS;
	protected XNameAccess xNA;
	protected XUnoUrlResolver xUUR;
	protected XStorable xStorable; 
	protected XComponentLoader xCL;
	protected PropertyValue[] loadProps ;
//	String docType = "swriter"; 
    protected String newDocURL = "private:factory/swriter"; 
	
    private OOoBean oOoBean = new OOoBean();
    
	public AbstractPublipostageOOo_org(
			List<ParamPublipostage> listeParamPublipostages) {
		super(listeParamPublipostages);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enregistrePublipostage(ParamPublipostage param) {
		// TODO Auto-generated method stub
		try { 
		
		
			String uriLettre = convertCheminFileVersUri(param.getCheminFichierFinal());
			XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,xC); 
			PropertyValue storeProperties[] = new PropertyValue[1]; 

			storeProperties[0] = new PropertyValue(); 
			//					storeproperties[0].name = "filtername"; 
			//					storeproperties[0].value = "writer_pdf_Export"; 


			xStorable.storeAsURL(uriLettre, storeProperties); 
			
		} catch( Exception e) { 
			logger.error("",e);
		} 
	}

	public void save(String savePath) {
		try { 
		
		String uriLettre = convertCheminFileVersUri(savePath);
		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,xC); 
		PropertyValue storeProperties[] = new PropertyValue[1]; 

		storeProperties[0] = new PropertyValue(); 
		//					storeproperties[0].name = "filtername"; 
		//					storeproperties[0].value = "writer_pdf_Export"; 

		
			xStorable.storeAsURL(uriLettre, storeProperties); 
		} catch( Exception e) { 
			logger.error("",e);
		} 
	}
	@Override
	public void fermeTraitementDeTexte() {
		// TODO Auto-generated method stub
	}

	@Override
	public void publipostage(ParamPublipostage param) {
		// TODO Auto-generated method stub
		/** zhaolin **/
//		oOoBean.createNewDocument(portOpenOffice);
//		XComponent xComponent = oOoBean.getxC();
//		map.put(param, xComponent);
		
		
		initTraitementDeTexte();
		map.put(param, xC);
		remplaceChamp(param);
		
	}
	
	@Override
	public void initTraitementDeTexte() {
		// TODO Auto-generated method stub
		try {
			xCC = Bootstrap.createInitialComponentContext(null);	
			xUUR = UnoUrlResolver.create(xCC);
			Object initialObject = xUUR.resolve(PARAM_CONNECTION_OPEN_OFFICE.replace("?",portOpenOffice));
			xMCF = (XMultiComponentFactory)UnoRuntime.queryInterface(XMultiComponentFactory.class,initialObject);
			XPropertySet xProperySet = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xMCF);
			Object oDefaultContext = xProperySet.getPropertyValue("DefaultContext");
			
			xCC = (XComponentContext)UnoRuntime.queryInterface(XComponentContext.class, oDefaultContext);
			// now create the desktop service
			// NOTE: use the office component context here!
			Object desktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xCC);
			xCL = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,desktop);
			
			loadProps = new PropertyValue[1];
			loadProps[0] = new PropertyValue();
//			if(!isShowPublipostage){
//				loadProps[0].Name = "Hidden";
//			}
			loadProps[0].Value = new Boolean(true); 
			
			xC = xCL.loadComponentFromURL(newDocURL,"_blank",0,loadProps);
			xMSF = ( XMultiServiceFactory ) UnoRuntime.queryInterface(XMultiServiceFactory.class, xC);
			xTD = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class,xC);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(" publipostage() has problem ! ", e);
		}
	}
	public void cloiseDocument(ParamPublipostage param) {
		
		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,map.get(param));
		XCloseable xCloseable  = (XCloseable)UnoRuntime.queryInterface(XCloseable.class, xStorable);
		 if ( xCloseable != null ) {
             try {
				xCloseable.close(false);
			} catch (CloseVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }

	}
	@Override
	public void fusionnePublipostage(List<ParamPublipostage> listePublipostage) {
		// TODO Auto-generated method stub
		try {
			initTraitementDeTexte();
			int positionPage = 0;
			for (ParamPublipostage param : listePublipostage) {
				insertPageOpenOffice(param.getCheminFichierFinal(), positionPage);
				cloiseDocument(param);
				positionPage++;
				
			}
			save(listePublipostage.get(0).getCheminRepertoireFinal()+"/"+getNomFichierFinalFusionne());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	@Override
	public void publipostages(String nomFichierFinal) {
		// TODO Auto-generated method stub
	}
	
	public void insertPageOpenOffice(String pathLettre,int positionPage) {
		try {

			XPropertySet xOrigDocTextCursorProp;
			XText xText = xTD.getText();
			XTextCursor xTextCursor = xText.createTextCursor();
			XDocumentInsertable xDocInsert = (XDocumentInsertable)
			UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
			// create a text cursor from the cells XText interface

			xTextCursor.gotoEnd(false);
	
			if(positionPage != 0){
				xOrigDocTextCursorProp = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,xTextCursor);
				xOrigDocTextCursorProp.setPropertyValue("BreakType",BreakType.PAGE_BEFORE);
				xTextCursor.gotoEnd(false);
				 xTextCursor.getText().insertControlCharacter(xTextCursor,ControlCharacter.PARAGRAPH_BREAK,false);
			}
			xTextCursor.gotoEnd(false);
			String uriModelLettre = convertCheminFileVersUri(pathLettre);
			xDocInsert.insertDocumentFromURL(uriModelLettre, new PropertyValue[0]);
			xTextCursor.gotoEnd(false); 
			
		}catch (Exception e) {
			logger.error("insertPageOpenOffice(String pathLettre,int positionPage) has problem ! )", e);
		}
	}
	@Override
	public void ajoutPageModeleTraitementDeTexte(ParamPublipostage param,int positionPage) {
		// TODO Auto-generated method stub
		insertPageOpenOffice(param.getCheminFichierModelLettre(), positionPage);
		
		/** zhaolin **/
//		String uriModelLettre = convertCheminFileVersUri(param.getCheminFichierModelLettre());
//		oOoBean.insertPageOpenOffice(uriModelLettre,positionPage);
		
	}


	public void makeLettrePubPosatage(ParamPublipostage param) {
		
		String uriCheminModelLettre = convertCheminFileVersUri(param.getCheminFichierModelLettre());
		int count = 1;
		
		if(param.isAfficheDetail()){
			for (String codeTiers : param.getDetails().keySet()) {
				LinkedList list =  param.getDetails().get(codeTiers);
				
			}
		}
		
		
	}
	abstract public void demarrerServeur();
	
//	@Override
	public void insereLigneDetail(int indexTable) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void remplaceChampTraitementDeTexte(ParamPublipostage param,
			String valeurRecherche, String nouvelleValeur) {

		XText mxDoc = xTD.getText();
		XReplaceable xReplaceable = (XReplaceable) UnoRuntime.queryInterface(XReplaceable.class, xTD);
		XReplaceDescriptor xRepDesc = xReplaceable.createReplaceDescriptor();
		// set a string to search for
		xRepDesc.setSearchString(valeurRecherche);
		// set the string to be inserted
		xRepDesc.setReplaceString(nouvelleValeur);		
		// set our sequence with one property value as ReplaceAttribute
		XPropertyReplace xPropRepl = (XPropertyReplace) UnoRuntime.queryInterface(
				XPropertyReplace.class, xRepDesc);

		long nResult = xReplaceable.replaceAll(xRepDesc);
	}
	@Override
	public void remplaceChampDetailTraitementDeTexte(ParamPublipostage param,
			String marqueurGroupe, int indexTable) {
		
//		remplaceDetailTable(param, marqueurGroupe, indexTable);
		remplaceDetailBalise(param, marqueurGroupe, indexTable);
		
	}
	
	public void remplaceDetailTable(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";
		xTTS = (XTextTablesSupplier)UnoRuntime.queryInterface(XTextTablesSupplier.class, xTD);
		xNA = xTTS.getTextTables(); 
		XIndexAccess xIndexedTables = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class,xNA);
		XPropertySet xTableProps = null; 
		Object table;
		try {
			table = xIndexedTables.getByIndex(indexTable-1);
			xTT = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, table);
			XTableRows tableRows = xTT.getRows();
			XCellRange xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, table);
			XCell xCellTable = xCellRange.getCellByPosition(0,0);//cols,rows
			XText xTextCell = (XText) UnoRuntime.queryInterface(XText.class, xCellTable);
			String texteModele = xTextCell.getString();
			
			int count = 1;
			for (String[] detail : param.getDetails().get(marqueurGroupe)) {
				ligneFinale = texteModele;
				for (int i = 0; i < detail.length; i++) {
					if(param.getMotCleLettreEtPostion().containsKey(i)){
						motCleLigneDetail = param.getMotCleLettreEtPostion().get(i);
						if(param.getMotCleLettreEtPostion().containsKey(i)){
							valueRmplace = detail[i];
						}
						ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);
					}
				}
				if(count != 1){
					tableRows.insertByIndex(count-1,1);
				}
				
				XCell xCell = xCellRange.getCellByPosition(0,count-1);
				XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
				xCellText.setString(ligneFinale);
				
				count++;
			}
			/*********************** ok **********************/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("", e);
		}
	}
	public void remplaceDetailBalise(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		boolean tableOuTexte = true; //si vrai table sinon texte
		
		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";
		
		try {
			XSearchable xSearchable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, xTD);
			XSearchDescriptor xSearchDescr = xSearchable.createSearchDescriptor();
			xSearchDescr.setPropertyValue("SearchRegularExpression", Boolean.TRUE);
			xSearchDescr.setPropertyValue("SearchCaseSensitive", new Boolean(true) );
			xSearchDescr.setSearchString(param.getBaliseDebutDetail()+"$");
			
			XInterface xInterface = (XInterface) xSearchable.findFirst(xSearchDescr);
			XTextRange xTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class,xInterface);
			
			XParagraphCursor xTextCursor = (XParagraphCursor) UnoRuntime.queryInterface(XParagraphCursor.class, xTextRange);
			
			xTextCursor.collapseToEnd();
			
			xTextCursor.goLeft(new Short(String.valueOf(param.getBaliseDebutDetail().length())).shortValue(), false);
			xTextCursor.collapseToStart();
			
			boolean baliseFinTrouve = false;
			while(xTextCursor.gotoNextParagraph(true) && !baliseFinTrouve) {
				xTextCursor.gotoEndOfParagraph(true);
				if(xTextCursor.getString().contains(param.getBaliseFinDetail())) {
					baliseFinTrouve = true;
				}
			}
			
			String texteModele = xTextCursor.getString();
			texteModele = texteModele.substring(param.getBaliseDebutDetail().length());
			texteModele = texteModele.substring(0,texteModele.lastIndexOf(param.getBaliseFinDetail()));
			
			texteModele = convertRetournLigne(texteModele);

			XTextTable xTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class,xMSF.createInstance("com.sun.star.text.TextTable"));
			xTable.initialize( 1, 1 );
			xTextCursor.getText().insertTextContent( xTextCursor, xTable, true );
			
			XTableRows tableRows = xTable.getRows();
			XCellRange xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTable);
			XCell xCellTable = xCellRange.getCellByPosition(0,0);//cols,rows
			
			int count = 1;
			for (String[] detail : param.getDetails().get(marqueurGroupe)) {
				ligneFinale = texteModele;
				for (int i = 0; i < detail.length; i++) {
					if(param.getMotCleLettreEtPostion().containsKey(i)){
						motCleLigneDetail = param.getMotCleLettreEtPostion().get(i);
						if(param.getMotCleLettreEtPostion().containsKey(i)){
							valueRmplace = detail[i];
						}
						ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);
					}
				}
				if(count != 1){
					tableRows.insertByIndex(count-1,1);
				}
				
				XCell xCell = xCellRange.getCellByPosition(0,count-1);
				XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
				xCellText.setString(ligneFinale);
				
				count++;
			}


		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		}
		
	}

	abstract public String convertCheminFileVersUri(String cheminFichier);
	abstract public String convertRetournLigne(String texteModele);
	
	public void remplaceDetailBalise() {
//		XSearchDescriptor xSearchDescriptor = x
	}
	public void setCheminOpenOffice(String cheminOpenOffice) {
		this.cheminOpenOffice = cheminOpenOffice;
	}


	public void setPortOpenOffice(String porteOpenOffice) {
		this.portOpenOffice = porteOpenOffice;
	}

}
