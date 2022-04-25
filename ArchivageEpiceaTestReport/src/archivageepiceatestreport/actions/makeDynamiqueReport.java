package archivageepiceatestreport.actions;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.designer.nls.Messages;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.impl.ReportContent;
import org.eclipse.birt.report.model.api.AutoTextHandle;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DataItemHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.LabelHandle;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.OdaDataSourceHandle;
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ResultSetColumnHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.metadata.PropertyDefn;

import com.ibm.icu.util.ULocale;

public class makeDynamiqueReport {

	private ReportDesignHandle designHandle = null;
	private ElementFactory designFactory = null;//for understand look P242
	private StructureFactory structFactory = null;
	//Configure the Engine and start the Platform
	private DesignConfig config = new DesignConfig( );
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private IDesignEngineFactory factory = null;
 
	

	
	
//	private int gridCols;
//	private int gridRows;
	private ArrayList nameTableCol;
	private ArrayList nameTableFooter;
	
	private String querySql = null;
	private String pathBIRT_HOME = null;
	private String pathFileReport = null;
	private final String DATA_SOURCE = "org.eclipse.birt.report.data.oda.jdbc";
	private final String DATA_SET = "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet";
	
	private String NAME_REPORT = null;
	private String DriverClass = null;
	private String URLFireBird = null;
	private String User = null;
	private String Password = null;
	
	Map<String,attribuElement> attribuElemTableHeader = null;
	Map<String,attribuElement> attribuElemTableDetail = null;
	Map<String,ArrayList<String>> attribuTableFooter = null;
	
	Map<String,attribuElement> attribuElemTableFooter = null;
	
	
	public makeDynamiqueReport(ArrayList nameTableCol,String querySql,String pathBIRT_HOME,
							   String pathFileReport,String driverClass,String fireBird,
							   String user, String password,String name_report){
		this.nameTableCol = nameTableCol;
		this.querySql = querySql;
		this.pathBIRT_HOME = pathBIRT_HOME;
		this.pathFileReport = pathFileReport;
		URLFireBird = fireBird;
		User = user;
		Password = password;
		NAME_REPORT = name_report;
	}
	public void buildDesignConfig(){
		/**
		 * pathBIRT_HOME = "C:/TestReport/birt-runtime-2_2_2/ReportEngine";
		 */
		config.setProperty("BIRT_HOME",pathBIRT_HOME);
		try{
			Platform.startup( config );
			factory = (IDesignEngineFactory)Platform
			.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( config );

		}catch( Exception ex){
 			ex.printStackTrace();
		}
		session = engine.newSessionHandle( ULocale.FRENCH ) ;
		/**
		 * for check out file of report exsit or not!
		 */
		File file = new File(pathFileReport);
		if(file.exists()){
			file.delete();
		}
		/**
		 * pathFileReport = "c:/tmp/sample.rptdesign";
		 * designFactory = designHandle.getElementFactory( );
		 */
		designHandle = session.createDesign(pathFileReport);
		designFactory = designHandle.getElementFactory( );
		
		DesignElementHandle element = designFactory.newSimpleMasterPage( "Page Master" ); 
		
		
		try {
			element.setProperty("type", "a4");
			element.setStringProperty("topMargin", "1cm");
			element.setStringProperty("leftMargin", "1cm");
			element.setStringProperty("bottomMargin", "1cm");
			element.setStringProperty("rightMargin", "1cm");
			
			GridHandle gridFooter = designFactory.newGridItem( "gridFooter", 3 /* cols */, 1 /* row */ );
			gridFooter.setStringProperty("width", "20%");
			((SimpleMasterPageHandle)element).getPageFooter().add(gridFooter);
			
			RowHandle rowFooter = (RowHandle)gridFooter.getRows().get(0);
			CellHandle cellGridFooter1 = (CellHandle) rowFooter.getCells( ).get( 0 );
			AutoTextHandle autoText = designFactory.newAutoText(null);
			autoText.setStringProperty("type", "page-number");
			cellGridFooter1.getContent( ).add( autoText );
			
			CellHandle cellGridFooter3 = (CellHandle) rowFooter.getCells( ).get( 1 );
			LabelHandle label_1 = designFactory.newLabel( null );
			label_1.setText("/");
			cellGridFooter3.getContent( ).add( label_1 );
			
			CellHandle cellGridFooter2 = (CellHandle) rowFooter.getCells( ).get( 2 );
			AutoTextHandle autoText2 = designFactory.newAutoText(null);
			autoText2.setStringProperty("type", "total-page");
			cellGridFooter2.getContent( ).add( autoText2 );
			
			
		} catch (SemanticException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			designHandle.getMasterPages( ).add( element );
			
		} catch (ContentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			buildDataSource();
			buildDataSet();
			
			
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void buildDataSource() throws SemanticException{
		//OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource("Data Source", "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		OdaDataSourceHandle dsHandle = designFactory.newOdaDataSource("Data Source", DATA_SOURCE);
		/**
		 * DriverClass => "org.firebirdsql.jdbc.FBDriver"
		 * URLFireBird => "jdbc:firebirdsql://localhost/C:/Archivage_epicea/archepi/JQJQJQJJJJ.FDB"
		 * User => "admin"
		 * Password => "###_PASSWORD_FB_BDG_###"
		 */
		dsHandle.setProperty( "odaDriverClass",DriverClass);
		dsHandle.setProperty( "odaURL", URLFireBird);
		dsHandle.setProperty( "odaUser", User );
		dsHandle.setProperty( "odaPassword", Password );

		designHandle.getDataSources( ).add( dsHandle );
	}
	public void buildDataSet() throws SemanticException{
		//OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds","org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet" );
		OdaDataSetHandle dsHandle = designFactory.newOdaDataSet( "ds",DATA_SET );
        dsHandle.setDataSource( "Data Source" );
       
        
        dsHandle.setQueryText( querySql );

		designHandle.getDataSets( ).add( dsHandle );
	}
		
	/**
	 * definitionColoneEntete et definitionColoneCorps are LinkedHashMap
	 * The type of Map keep order values when we inert values into the type of Map!
	 * those others Map can't keep order!
	 */
	public void makeReportTableDB(int widthTable,String widthTableUnit,String NameTable,
								  Map<String,attribuElement> definitionColoneEntete, 
								  Map<String,attribuElement> definitionColoneCorps,
								  int NombreHeader,int NombreDetail, int NombreFooter, 
								  int NombreColGroupe,String limiteNombreligneTable){
		TableHandle table = designFactory.newTableItem( "table", nameTableCol.size(),
														NombreHeader,NombreDetail,NombreFooter);
		try {
			table.setWidth( widthTable+widthTableUnit );
			table.setStringProperty("pageBreakInterval", limiteNombreligneTable);
			updateBorder(table);
 			table.setDataSet( designHandle.findDataSet( "ds" ) );
 			
 			PropertyHandle computedSet = table.getColumnBindings( ); 
			ComputedColumn  cs1 = null;

			int j =0;
			for (String NameCol : definitionColoneEntete.keySet()) {
				
				cs1 = StructureFactory.createComputedColumn();
				//cs1 = StructureFactory.newComputedColumn(designHandle, "");
				cs1.setName(NameCol);

				if(NameCol.equalsIgnoreCase("DATE")){
 					//cs1.setExpression("dataSetRow[\"" + NameCol +"_EP"+ "\"]");
					cs1.setExpression("dataSetRow[\"" + NameCol + "\"]");
 					cs1.setProperty("dataType", definitionColoneEntete.get(NameCol).getTYPE_COL());
 				}
 				else{
 					cs1.setExpression("dataSetRow[\"" + NameCol + "\"]");
 					cs1.setProperty("dataType", definitionColoneEntete.get(NameCol).getTYPE_COL());
 					//System.out.println(definitionColoneEntete.get(NameCol).getTYPE_COL());
 				}
				computedSet.addItem(cs1);
				j++;
			}
			
			j=0;
			for (String NameCol : definitionColoneEntete.keySet()) {
				ColumnHandle colTable = (ColumnHandle)table.getColumns().get(j);
				String WidthCol = definitionColoneEntete.get(NameCol).getWidthTableCol()+
								  definitionColoneEntete.get(NameCol).getUNITE_WIDTH();
				colTable.setStringProperty("width", WidthCol);
				j++;
			}
			
			RowHandle tableheader = (RowHandle) table.getHeader( ).get( 0 );
			j=0;
			for (String NameCol : definitionColoneEntete.keySet()) {

				LabelHandle label1 = designFactory.newLabel( (String)nameTableCol.get(j) );	
				label1.setText(NameCol);
				CellHandle cell = (CellHandle) tableheader.getCells( ).get( j );
				cell.setStringProperty("textAlign", definitionColoneEntete.get(NameCol).getTEXT_ALIGN());
				cell.setStringProperty("fontWeight",definitionColoneEntete.get(NameCol).getFONT_WIDTH());
				updateBorder(cell);
				cell.getContent( ).add( label1 );
				j++;
			}	
			
			// table detail
 			RowHandle tabledetail = (RowHandle) table.getDetail( ).get( 0 );
 			//System.out.println(table.getFooter().getContents().size());
 			j=0;
 			//for( int j=0; j < nameTableCol.size(); j++){
 			for (String NameCol : definitionColoneCorps.keySet()) {
 								
 				CellHandle cell = (CellHandle) tabledetail.getCells( ).get( j );
 				
 				DataItemHandle data = designFactory.newDataItem( "data_"+NameCol );
 				data.setResultSetColumn(NameCol);
 			
 				StyleHandle dataItemStyle = data.getPrivateStyle();
 				
 				if(NameCol.equalsIgnoreCase("DATE")){
 					/**
 	 				 * for update format of data
 	 				 * example ==> 15 juin 08==>15/06/08
 	 				 */
 					dataItemStyle.setDateTimeFormatCategory(DesignChoiceConstants.DATETIEM_FORMAT_TYPE_SHORT_DATE/*or "Short Date"*/);
 					dataItemStyle.setDateTimeFormat(DesignChoiceConstants.DATETIEM_FORMAT_TYPE_SHORT_DATE/* or "Short Date" */);
 				}
 				if(definitionColoneCorps.get(NameCol).getUNITE_CASH().equalsIgnoreCase("EURO")){
 					/**
 					 * for update format of Number
 					 * for example : 1222==>1,222€
 					 */
 					dataItemStyle.setNumberFormatCategory(DesignChoiceConstants.NUMBER_FORMAT_TYPE_CURRENCY);
 					//dataItemStyle.setNumberFormat(DesignChoiceConstants.NUMBER_FORMAT_TYPE_STANDARD);
 					Currency currency;
 					currency = Currency.getInstance(Locale.FRANCE);
 					dataItemStyle.setNumberFormat("###0.00"+currency.getSymbol());
 				}
 				cell.setStringProperty("textAlign", definitionColoneCorps.get(NameCol).getTEXT_ALIGN());
 				updateBorder(cell);
 				cell.getContent( ).add( data );
 				j++;
 			}
 			//*********table footer*********//
 			int NoRowFooter = 0;
 			for (String NameFooter : attribuElemTableFooter.keySet()) {
 				RowHandle rowtablefooter = (RowHandle)table.getFooter().get(NoRowFooter);
 				/**
 				 * for Frist--I put all of cellFooterTable in listCellTableFooter
 				 */
 				ArrayList<CellHandle> listCellTableFooter = new ArrayList<CellHandle>();
 				for(int i = 0;i<nameTableCol.size();i++){
 					CellHandle cell2= (CellHandle) rowtablefooter.getCells( ).get( i);
 					listCellTableFooter.add(cell2);
 					updateBorder(listCellTableFooter.get(i));
 				}
 				/**
 				 * for merge cells
 				 */
 				for (int i = 0; i < listCellTableFooter.size(); i++) {
 					if(i<4){
 						listCellTableFooter.get(i).drop();
 					}
 					if(i==4){
 						LabelHandle labelfooter = designFactory.newLabel( null );
 		 				listCellTableFooter.get(4).setColumnSpan(5);
 		 				listCellTableFooter.get(4).setRowSpan(1);
 		 				listCellTableFooter.get(4).getContent( ).add( labelfooter );
 		 				labelfooter.setText(NameFooter);
 		 				listCellTableFooter.get(4).setStringProperty("textAlign", attribuElemTableFooter.get(NameFooter).getTEXT_ALIGN());
 		 				listCellTableFooter.get(4).setStringProperty("fontWeight",attribuElemTableFooter.get(NameFooter).getFONT_WIDTH());
 					}
 					

 				}
 				NoRowFooter++;
 			}

// 			for(int count=0;count<NombreFooter;count++){
// 				RowHandle rowtablefooter = (RowHandle)table.getFooter().get(count);
// 				ArrayList<CellHandle> listCellTableFooter = new ArrayList<CellHandle>();
// 				for(int i = 0;i<nameTableCol.size();i++){
// 					CellHandle cell2= (CellHandle) rowtablefooter.getCells( ).get( i);
// 					listCellTableFooter.add(cell2);
// 					updateBorder(listCellTableFooter.get(i));
// 					
// 				}
// 				for (int i = 0; i < listCellTableFooter.size(); i++) {
// 					if(i<4){
// 						listCellTableFooter.get(i).drop();
// 					}
// 					if(i==4){
// 						LabelHandle labelfooter = designFactory.newLabel( null );
// 		 				listCellTableFooter.get(4).setColumnSpan(5);
// 		 				listCellTableFooter.get(4).setRowSpan(1);
// 		 				listCellTableFooter.get(4).getContent( ).add( labelfooter );
// 		 				labelfooter.setText((String)nameTableFooter.get(count));
// 		 				//updateBorder(listCellTableFooter.get(4));
// 					}
// 				}
// 			}

 			
 			designHandle.getBody( ).add( table );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void makeReportTableDBDefeut(Map<String,attribuElement>definitionColoneEntete,
										Map<String,attribuElement>definitionColoneCorps){
		
		makeReportTableDB(100,"%","Table", definitionColoneEntete, definitionColoneCorps,1,1,1,0,"40");			
	}
	/**
	 * 
	 * @param objetDesignElementHandle
	 * @throws SemanticException
	 */
	public void updateBorder(DesignElementHandle objetDesignElementHandle) throws SemanticException
	{
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID);
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_LEFT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_TOP_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_RIGHT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
		
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_RIGHT_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_LEFT_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_TOP_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
		objetDesignElementHandle.setProperty(StyleHandle.BORDER_BOTTOM_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
	}
	/**
	 * for make grid in a REPORT
	 * =================================
	 * @param gridCols =>nombre de cols
	 * @param gridRows =>nombre de rows
	 * @throws SemanticException 
	 */
	public GridHandle makeReportGrid(int gridCols,int gridRows,int widthGrid,String widthGridUnit) throws SemanticException{
		GridHandle grid = designFactory.newGridItem( null,gridCols, gridRows);
		designHandle.getBody( ).add( grid );
		grid.setWidth( widthGrid+widthGridUnit );
		int widthCol = widthGrid/gridCols;
		for(int i=1; i < gridCols; i++){
			ColumnHandle col = (ColumnHandle)grid.getColumns().get(i);
			col.setStringProperty("width",widthCol+widthGridUnit);
		}
		return grid;
	}
	public void makeReportHeaderGrid(int gridCols,int gridRows,int widthGrid,String widthGridUnit,
			Map<String,attribuElement>definitionGrid) {
			
		GridHandle gridHeader;
		try {
			gridHeader = makeReportGrid(gridCols,gridRows,widthGrid,widthGridUnit);
//			for(int i =0;i<gridRows;i++){
//				RowHandle row = (RowHandle) gridHeader.getRows( ).get( i );
//				CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
//				cell.setProperty(StyleHandle.FONT_WEIGHT_PROP,DesignChoiceConstants.FONT_WEIGHT_BOLD);
//				cell.setProperty(StyleHandle.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);
//				cell.setProperty(StyleHandle.FONT_SIZE_PROP, DesignChoiceConstants.FONT_SIZE_XX_LARGE);
//
//				cell.setColumnSpan(gridCols-1);
//				LabelHandle label = designFactory.newLabel( null );
//				cell.getContent( ).add( label );
//				label.setText(titleHeader); 
//				
//				CellHandle cell2 = (CellHandle) row.getCells( ).get( 1);
//				LabelHandle labe2 = designFactory.newLabel( null );
//				cell2.getContent( ).add( labe2 );
//				labe2.setText("Label2"); 
//			}
			int NoRowGrid = 0;
			for (String NameCol : definitionGrid.keySet()) {
				RowHandle row = (RowHandle) gridHeader.getRows( ).get( NoRowGrid );
				CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
				cell.setColumnSpan(gridCols);
				
				LabelHandle label = designFactory.newLabel("GridHead"+NoRowGrid);
				cell.getContent( ).add( label );
				label.setText(NameCol);
				cell.setStringProperty("textAlign", definitionGrid.get(NameCol).getTEXT_ALIGN());
				cell.setStringProperty("fontWeight",definitionGrid.get(NameCol).getFONT_WIDTH());
				cell.setStringProperty("fontSize", definitionGrid.get(NameCol).getFONT_SIZE());
				cell.setStringProperty("textUnderline","underline");
				
				NoRowGrid++;
			}
//			RowHandle row = (RowHandle) gridHeader.getRows( ).get( 0 );
//			CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
//			cell.setProperty(StyleHandle.FONT_WEIGHT_PROP,DesignChoiceConstants.FONT_WEIGHT_BOLD);
//			cell.setProperty(StyleHandle.TEXT_ALIGN_PROP, DesignChoiceConstants.TEXT_ALIGN_CENTER);
//			cell.setProperty(StyleHandle.FONT_SIZE_PROP, DesignChoiceConstants.FONT_SIZE_XX_LARGE);
//			cell.setColumnSpan(gridCols-1);
//			LabelHandle label = designFactory.newLabel( null );
//			cell.getContent( ).add( label );
//			label.setText(titleHeader); 
//			
//			CellHandle cell2 = (CellHandle) row.getCells( ).get( 1);
//			LabelHandle labe2 = designFactory.newLabel( null );
//			cell2.getContent( ).add( labe2 );
//			labe2.setText("Label2"); 
			
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void makeReportLabel(RowHandle row,int nombreCell){
		
		for(int i=0; i < nombreCell; i++){
			
		}
		
	}
	public void SavsAsDesignHandle(){
		try {
			designHandle.saveAs(pathFileReport);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		designHandle.close();
	}
	public ReportDesignHandle getDesignHandle() {
		return designHandle;
	}
	public void setDesignHandle(ReportDesignHandle designHandle) {
		this.designHandle = designHandle;
	}
	public ElementFactory getDesignFactory() {
		return designFactory;
	}
	public void setDesignFactory(ElementFactory designFactory) {
		this.designFactory = designFactory;
	}
	public StructureFactory getStructFactory() {
		return structFactory;
	}
	public void setStructFactory(StructureFactory structFactory) {
		this.structFactory = structFactory;
	}
//	public int getGridCols() {
//		return gridCols;
//	}
//	public void setGridCols(int gridCols) {
//		this.gridCols = gridCols;
//	}
//	public int getGridRows() {
//		return gridRows;
//	}
//	public void setGridRows(int gridRows) {
//		this.gridRows = gridRows;
//	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public ArrayList getNameTableCol() {
		return nameTableCol;
	}
	public void setNameTableCol(ArrayList nameTableCol) {
		this.nameTableCol = nameTableCol;
	}
	public DesignConfig getConfig() {
		return config;
	}
	public void setConfig(DesignConfig config) {
		this.config = config;
	}
	public String getPathBIRT_HOME() {
		return pathBIRT_HOME;
	}
	public void setPathBIRT_HOME(String pathBIRT_HOME) {
		this.pathBIRT_HOME = pathBIRT_HOME;
	}
	public IDesignEngine getEngine() {
		return engine;
	}
	public void setEngine(IDesignEngine engine) {
		this.engine = engine;
	}
	public SessionHandle getSession() {
		return session;
	}
	public void setSession(SessionHandle session) {
		this.session = session;
	}
	public IDesignEngineFactory getFactory() {
		return factory;
	}
	public void setFactory(IDesignEngineFactory factory) {
		this.factory = factory;
	}
	public String getPathFileReport() {
		return pathFileReport;
	}
	public void setPathFileReport(String pathFileReport) {
		this.pathFileReport = pathFileReport;
	}
	public String getDriverClass() {
		return DriverClass;
	}
	public void setDriverClass(String driverClass) {
		DriverClass = driverClass;
	}
	public String getURLFireBird() {
		return URLFireBird;
	}
	public void setURLFireBird(String fireBird) {
		URLFireBird = fireBird;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Map<String, attribuElement> getAttribuElemTableHeader() {
		return attribuElemTableHeader;
	}
	public void setAttribuElemTableHeader(
			Map<String, attribuElement> attribuElemTableHeader) {
		this.attribuElemTableHeader = attribuElemTableHeader;
	}
	public Map<String, attribuElement> getAttribuElemTableDetail() {
		return attribuElemTableDetail;
	}
	public void setAttribuElemTableDetail(
			Map<String, attribuElement> attribuElemTableDetail) {
		this.attribuElemTableDetail = attribuElemTableDetail;
	}
	public ArrayList getNameTableFooter() {
		return nameTableFooter;
	}
	public void setNameTableFooter(ArrayList nameTableFooter) {
		this.nameTableFooter = nameTableFooter;
	}
	public Map<String, ArrayList<String>> getAttribuTableFooter() {
		return attribuTableFooter;
	}
	public void setAttribuTableFooter(
			Map<String, ArrayList<String>> attribuTableFooter) {
		this.attribuTableFooter = attribuTableFooter;
	}
	public Map<String, attribuElement> getAttribuElemTableFooter() {
		return attribuElemTableFooter;
	}
	public void setAttribuElemTableFooter(
			Map<String, attribuElement> attribuElemTableFooter) {
		this.attribuElemTableFooter = attribuElemTableFooter;
	}


}
