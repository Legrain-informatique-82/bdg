package fr.legrain.publipostage.openoffice;

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
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.style.BreakType;
import com.sun.star.table.BorderLine;
import com.sun.star.table.TableBorder;
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

import fr.legrain.publipostage.divers.ParamPublipostage;

public class OOoBean {

	static Logger logger = Logger.getLogger(OOoBean.class.getName());
	
	public static final String PARAM_CONNECTION_OPEN_OFFICE = 
		"uno:socket,host=localhost,port=?;urp;StarOffice.ServiceManager";
	public static final String WIN32_LINUX_SOFFICE ="soffice";
	
	private XText xT;
	private XComponent xC;
	private XTextDocument xTD;
	private XTextTable xTT;
	private XComponentContext xCC;
	private XMultiComponentFactory xMCF;
	private XMultiServiceFactory xMSF;
	private XSearchable xS; 
	private XSearchDescriptor xSD;
	private XTextTablesSupplier xTTS;
	private XNameAccess xNA;
	private XUnoUrlResolver xUUR;
	private XStorable xStorable; 
	private XCloseable xCloseable;
	private XComponentLoader xCL;
	private XTableRows xtableRows;
	private XCellRange xCellRange;
	private XCell xCell;
	private XSearchable xSearchable;
	private XReplaceable xReplaceable;
	private XParagraphCursor xParagraphCursor;
	private PropertyValue[] loadProps ;
	private PropertyValue storeProperties[];
	
	
	private String newDocURL = "private:factory/swriter";
	
	public void enregistrePublipostage(String uriLettre) {
		
		xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,xC);
		storeProperties = new PropertyValue[1]; 
		storeProperties[0] = new PropertyValue(); 
//		storeproperties[0].name = "filtername"; 
//		storeproperties[0].value = "writer_pdf_Export"; 
		try {
			xStorable.storeAsURL(uriLettre, storeProperties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("enregistrePublipostage()", e);
			e.printStackTrace();
		} 
	}
	
	public void OuvreDocument(String portOpenOffice,String fichier) {
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
			
			xC = xCL.loadComponentFromURL(fichier,"_blank",0,loadProps);
			xMSF = ( XMultiServiceFactory ) UnoRuntime.queryInterface(XMultiServiceFactory.class, xC);
			xTD = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class,xC);
			xT = xTD.getText();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("createNewDocument()", e);
		}
	}
	
	public void createNewDocument(String portOpenOffice) {
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
			xT = xTD.getText();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("createNewDocument()", e);
		}
	}
	
	public void closeDocument(XComponent xComponent) {
		try {
			if (xStorable != null) {
				xCloseable = (XCloseable)UnoRuntime.queryInterface(XCloseable.class, xComponent);
				if ( xCloseable != null ) {
					xCloseable.close(false);
				}
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("closeDocument(XComponent xComponent)", e);
		} 
	}
	
	public void insertPageOpenOffice(String uriModelLettre,int positionPage) {
		try {
			/** ok **/
//			XPropertySet xOrigDocTextCursorProp;
//			XTextCursor xTextCursor = xT.createTextCursor();
//			xTextCursor.gotoEnd(false);
//			XDocumentInsertable xDocInsert = (XDocumentInsertable)UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
//			xOrigDocTextCursorProp = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,xTextCursor);
//			
//			if(positionPage != 0){
////				xTextCursor.getText().insertControlCharacter(xTextCursor,ControlCharacter.PARAGRAPH_BREAK,false);	
//				xT.insertControlCharacter(xTextCursor,ControlCharacter.PARAGRAPH_BREAK,false);
//				xOrigDocTextCursorProp.setPropertyValue("BreakType",BreakType.PAGE_BEFORE);
//				xTextCursor.gotoEnd(false);
//				
//			}
////			xT.insertControlCharacter(xTextCursor,  ControlCharacter.PARAGRAPH_BREAK, false);
////			xOrigDocTextCursorProp.setPropertyValue("BreakType", BreakType.PAGE_BEFORE);
////			xTextCursor.gotoEnd(false);
//			//xDocInsert.insertDocumentFromURL(uriModelLettre, new PropertyValue[0]);
//			xDocInsert.insertDocumentFromURL(uriModelLettre, null);
////			xTextCursor.gotoEnd(false); 
			/** ok **/
			XPropertySet xOrigDocTextCursorProp;
			XTextCursor xTextCursor = xT.createTextCursor();
			XDocumentInsertable xDocInsert = (XDocumentInsertable)
			UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
			// create a text cursor from the cells XText interface

			xTextCursor.gotoEnd(false);
	
			if(positionPage != 0){
				xT.insertControlCharacter(xTextCursor,ControlCharacter.PARAGRAPH_BREAK,false);
				xOrigDocTextCursorProp = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class,xTextCursor);
				xOrigDocTextCursorProp.setPropertyValue("BreakType",BreakType.PAGE_BEFORE);
				xTextCursor.gotoEnd(false);
			}
			xTextCursor.gotoEnd(false);
			xDocInsert.insertDocumentFromURL(uriModelLettre, new PropertyValue[0]);
			xTextCursor.gotoEnd(false); 
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insertPageOpenOffice(String pathLettre,int positionPage," +
					"String uriModelLettre) has problem !",e);
		}
	}
	
	public void replaceTextBalise(String toFindText, String newText) {
		try {
			xReplaceable = (XReplaceable) UnoRuntime.queryInterface(XReplaceable.class, xTD);
			XReplaceDescriptor xRepDesc = xReplaceable.createReplaceDescriptor();
			// set a string to search for
			xRepDesc.setSearchString(toFindText);
			// set the string to be inserted
			xRepDesc.setReplaceString(newText);
//			PropertyValue[] aReplaceArgs = new PropertyValue[1];
//			// create PropertyValue struct
//			aReplaceArgs[0] = new PropertyValue();
//			// CharWeight should be bold
//			aReplaceArgs[0].Name = "CharWeight";
//			aReplaceArgs[0].Value = new Float(com.sun.star.awt.FontWeight.BOLD);
			// set our sequence with one property value as ReplaceAttribute
			XPropertyReplace xPropRepl = (XPropertyReplace) UnoRuntime.queryInterface(
					XPropertyReplace.class, xRepDesc);
			
//			xPropRepl.setReplaceAttributes(aReplaceArgs);
			// replace
			long nResult = xReplaceable.replaceAll(xRepDesc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("replaceTextBalise(String toFindText, String newText) problem !", e);
		}

	}
	
	
	public String getTextCellTableExiste(int indexTable,int cellRowIdx,int cellColIdx) {
		String valueCell = "";
		try {
			xTTS = (XTextTablesSupplier)UnoRuntime.queryInterface(XTextTablesSupplier.class, xTD);
			xNA = xTTS.getTextTables(); 
			XIndexAccess xIndexedTables = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class,xNA);
			XPropertySet xTableProps = null; 
			Object table = xIndexedTables.getByIndex(indexTable-1);
			xTT = (XTextTable) UnoRuntime.queryInterface(XTextTable.class, table);
			xtableRows = xTT.getRows();
			xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, table);
			xCell = xCellRange.getCellByPosition(cellRowIdx,cellColIdx);
			XText xTextCell = (XText) UnoRuntime.queryInterface(XText.class, xCell);
			valueCell = xTextCell.getString();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getTextCellTableExiste(int indexTable,int cellRowIdx,int cellColIdx) problem !", e);
		}
		
		return valueCell;
	}
	
	public void addRow(int index) {
		xtableRows.insertByIndex(index,1);
	}
	public void putTxtToCell(int index,String txt) {
		try {
			xCell = xCellRange.getCellByPosition(0,index);
			XText xCellText = (XText) UnoRuntime.queryInterface(XText.class, xCell);
			xCellText.setString(txt);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("addRow(int index) problem !", e);
		}
		
	}

	public String findTxtBetweenDetail(String baliseDebutDetail,String baliseFinDetail) {
		String txtBetweenDetail = "";
		
		try {
			xSearchable = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, xTD);
			XSearchDescriptor xSearchDescr = xSearchable.createSearchDescriptor();
			xSearchDescr.setPropertyValue("SearchRegularExpression", Boolean.TRUE);
			xSearchDescr.setPropertyValue("SearchCaseSensitive", new Boolean(true) );
			xSearchDescr.setSearchString(baliseDebutDetail+"$");
			
			XInterface xInterface = (XInterface) xSearchable.findFirst(xSearchDescr);
			XTextRange xTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class,xInterface);
			
			xParagraphCursor = (XParagraphCursor) UnoRuntime.queryInterface(XParagraphCursor.class, xTextRange);
			
			if(xParagraphCursor!=null) {
				//début de la balise de détail trouvé
				xParagraphCursor.collapseToEnd();

				xParagraphCursor.goLeft(new Short(String.valueOf(baliseDebutDetail.length())).shortValue(), false);
				xParagraphCursor.collapseToStart();

				boolean baliseFinTrouve = false;
				while(xParagraphCursor.gotoNextParagraph(true) && !baliseFinTrouve) {
					xParagraphCursor.gotoEndOfParagraph(true);
					if(xParagraphCursor.getString().contains(baliseFinDetail)) {
						baliseFinTrouve = true;
					}
				}

				txtBetweenDetail = xParagraphCursor.getString();
				txtBetweenDetail = txtBetweenDetail.substring(baliseDebutDetail.length());
				txtBetweenDetail = txtBetweenDetail.substring(0,txtBetweenDetail.lastIndexOf(baliseFinDetail));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findTxtBetweenDetail(String baliseDebutDetail,String baliseFinDetail)", e);
		}
		return txtBetweenDetail;

	}

	public void creatTable() {
		try {
			xTT = (XTextTable) UnoRuntime.queryInterface(XTextTable.class,xMSF.createInstance("com.sun.star.text.TextTable"));
			xTT.initialize(1,1);
			
			/** pour modifier bordure de Table 
			 *  ne fonctionner pas
			 **/
//			XPropertySet xTextTablePS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTT );
//			hideTableBorders(xTextTablePS);
			
			xParagraphCursor.getText().insertTextContent(xParagraphCursor,xTT,true);
			
			xtableRows = xTT.getRows();
			xCellRange = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, xTT);
			xCell = xCellRange.getCellByPosition(0,0);//cols,rows
			XPropertySet xCellPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xCell);
			hideCellBorders(xCellPS);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("creatTable() problem !", e);
		}
		
	}
	public void hideCellBorders(XPropertySet xPS) {
		try {
			// Border color examples: 
			// 0xFF0000 => red | 0x00FF00 => green | 0x0000FF => blue 
			// 0xFFFF00 => yellow | 0xFF00FF => magenta | 0x00FFFF => cyan 
			// 0xFFFFFF => white | 0x000000 => black
			
			BorderLine noBorderLine = new BorderLine(); 
			noBorderLine.Color = 0x000000; 
			noBorderLine.InnerLineWidth = 0; 
			noBorderLine.OuterLineWidth = 0; 
			noBorderLine.LineDistance = 0; 
			
			xPS.setPropertyValue("TopBorder", noBorderLine);
			xPS.setPropertyValue("BottomBorder", noBorderLine);
			xPS.setPropertyValue("LeftBorder", noBorderLine);
			xPS.setPropertyValue("RightBorder", noBorderLine);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("hideCellBorders(XPropertySet xPS) problem !", e);
		} 

	}
	public void hideTableBorders(XPropertySet xPS) {
		try {
			 
			BorderLine noBorderLine = new BorderLine(); 
			noBorderLine.Color = 0x000000; 
//			noBorderLine.Color = 0;
			noBorderLine.InnerLineWidth = 0; 
			noBorderLine.OuterLineWidth = 0; 
			noBorderLine.LineDistance = 0; 

			TableBorder tableBorder = new TableBorder(); 
			tableBorder.TopLine = noBorderLine; 
			tableBorder.IsTopLineValid = false; 

			tableBorder.BottomLine = noBorderLine; 
			tableBorder.IsBottomLineValid = false; 

			tableBorder.LeftLine = noBorderLine; 
			tableBorder.IsLeftLineValid = false; 

			tableBorder.RightLine = noBorderLine; 
			tableBorder.IsRightLineValid = false; 

			tableBorder.HorizontalLine = noBorderLine; 
			tableBorder.IsHorizontalLineValid = false; 

			tableBorder.VerticalLine = noBorderLine; 
			tableBorder.IsVerticalLineValid = false; 

			xPS.setPropertyValue("TableBorder", tableBorder); 
		} catch (Exception e) {
			logger.error("hideTableBorders(XPropertySet xPS) problem !", e);
		}
	} 
	public XComponent getxC() {
		return xC;
	}

	public void setxC(XComponent xC) {
		this.xC = xC;
	}
	
	
}
