package fr.legrain.edition.actions;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.AutoTextHandle;
import org.eclipse.birt.report.model.api.CachedMetaDataHandle;
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
import org.eclipse.birt.report.model.api.PropertyHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.ScriptDataSetHandle;
import org.eclipse.birt.report.model.api.ScriptDataSourceHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.SimpleMasterPageHandle;
import org.eclipse.birt.report.model.api.StructureFactory;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TableHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.elements.structures.CachedMetaData;
import org.eclipse.birt.report.model.api.elements.structures.ColumnHint;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.ResultSetColumn;

import com.ibm.icu.util.ULocale;

import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.lib.data.LibDate;

public class MakeDynamiqueReport {

	static Logger logger = Logger.getLogger(MakeDynamiqueReport.class.getName()); 
	
	private ReportDesignHandle designHandle = null;
	private ElementFactory designFactory = null;//for understand look P242
	private StructureFactory structFactory = null;
	//Configure the Engine and start the Platform
	private DesignConfig config = new DesignConfig( );
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private IDesignEngineFactory factory = null;
	/** 12/01/2010 (supprimer)**/
//	private ConstEdition consEdition = new ConstEdition("width","orientation");


	private String DataSourceScript = "DataSourceScript";// the name of Script DataSource 
	private String DataSetScript = "DataSetScript";// the name of Script DataSource


	//	private int gridCols;
	//	private int gridRows;
	private ArrayList<String> nameTableCol;
	private ArrayList<String> nameTableFooter;
	private ArrayList<String> nameTableDetail;
	/**
	 * Nombre de colonne de la table, dans la majorite des ecrans, cette valeur est automatique determine
	 * a partir du nombre de colonne affiche dans l'ecran concerné == Nombre de colonne du tableviewer (décrite dans le fichier properties)
	 * Dans l'écran de Sélection/Recherche, il n'y a pas de controller et donc de tableviewer affecté à chaque recherche.
	 * Il faut donc passé le nombre colonne qui servent a représenter le résultat de la recherche.
	 */
	private int nombreColonneTable = 0;

	//private String querySql = null;
	private String pathBIRT_HOME = null;
	private String pathFileReport = null;
	private final String DATA_SOURCE = "org.eclipse.birt.report.data.oda.jdbc";
	private final String DATA_SET = "org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet";

	private String NAME_REPORT = null;
	private String DriverClass = null;
//	private String URLFireBird = null;
//	private String User = null;
//	private String Password = null;
	private String choixPageOrientation = null;
	private int pageBreakInterval;
	private String NOM_DOSSIER =null;
	private String simpleNameEntity =null;
	
	/**
	 * des new attribute
	 */
	private FonctionGetInfosXmlAndProperties fonctionGetInfosXml = null;
	private String nomObjet = null;//pour script de l'edition 

	Map<String,AttributElementResport> attribuElemTableHeader = null;
	Map<String,AttributElementResport> attribuElemTableDetail = null;
	Map<String,ArrayList<String>> attribuTableFooter = null;
	Map<String,AttributElementResport> attribuElemTableFooter = null;

	public void initializeBuildDesignReportConfig(){
		//config.setProperty("BIRT_HOME",pathBIRT_HOME);
		try{
			Platform.startup( config );
			factory = (IDesignEngineFactory)Platform
			.createFactoryObject( IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY );
			engine = factory.createDesignEngine( config );

		}catch( Exception ex){
			ex.printStackTrace();
		}
		session = engine.newSessionHandle( ULocale.FRENCH ) ;
		File file = new File(pathFileReport);
		if(file.exists()){
			file.delete();
		}
		designHandle = session.createDesign(pathFileReport);
		makeReportAdvanced(designHandle,ConstEdition.CONTENT_COMMENTS);
		designFactory = designHandle.getElementFactory( );
	}

	public void makeReportAdvanced(ReportDesignHandle designHandleAvanced,String commentaire){
		try {
			designHandleAvanced.setProperty(ConstEdition.COMMENTS,commentaire);
		} catch (SemanticException e) {
			logger.error("",e);
		}
	}
	
	public void makePageMater(String topMargin,String leftMargin,String bottomMargin,
							  String rightMargin,String widthFooter){
		//initializeBuildDesignReportConfig();

		DesignElementHandle element = designFactory.newSimpleMasterPage( "Page Master" );
		
		try {

			element.setFloatProperty("headerHeight",0.25);
			element.setFloatProperty("footerHeight",0.25);
			
			element.setProperty(ConstEdition.TYPE, DesignChoiceConstants.PAGE_SIZE_A4/*"a4"*/);
			if(choixPageOrientation==ConstEdition.PAGE_ORIENTATION_PORTRAIT){
				element.setProperty(ConstEdition.PAGE_ORIENTATION/*"orientation"*/, DesignChoiceConstants.PAGE_ORIENTATION_PORTRAIT);
			}else{
				element.setProperty(/*ConstEdition.PAGE_ORIENTATION*/"orientation", DesignChoiceConstants.PAGE_ORIENTATION_LANDSCAPE);	
			}
			element.setStringProperty(ConstEdition.TOP_MARGIN/*"topMargin"*/,
					topMargin+ConstEdition.UNITS_CM/*"1cm"*/);
			element.setStringProperty(ConstEdition.LEFT_MARGIN /*"leftMargin"*/,
					leftMargin+ConstEdition.UNITS_CM/*"1cm"*/);
			element.setStringProperty(ConstEdition.BOTTOM_MARGIN/*"bottomMargin"*/, 
					bottomMargin+ConstEdition.UNITS_CM/*"1cm"*/);
			element.setStringProperty(ConstEdition.RIGHT_MARGIN/*"rightMargin"*/,
					rightMargin+ConstEdition.UNITS_CM/*"1cm"*/);


			GridHandle gridFooter = designFactory.newGridItem( "gridFooter", 3 /* cols */, 1 /* row */ );
			String width = null;
			for(int i=0; i < 3; i++){
				ColumnHandle col = (ColumnHandle)gridFooter.getColumns().get(i);
				if(i==0){
					width = "10%";
				}
				if(i==1){
					width = "80%";
				}
				if(i==2){
					width = "10%";
				}
				col.setStringProperty(ConstEdition.WIDTH,width);
			}
			gridFooter.setStringProperty(ConstEdition.WIDTH, widthFooter+ConstEdition.UNITS_PERCENTAGE/*"20%"*/);
			((SimpleMasterPageHandle)element).getPageFooter().add(gridFooter);

			RowHandle rowFooter = (RowHandle)gridFooter.getRows().get(0);
			CellHandle cellGridFooter1 = (CellHandle) rowFooter.getCells( ).get( 2 );
			cellGridFooter1.setProperty(StyleHandle.TEXT_ALIGN_PROP,ConstEdition.TEXT_ALIGN_RIGHT);
			/*
			 * for add GridHandle(number of page) in the first Cell 
			 */
			GridHandle gridFooterPage = designFactory.newGridItem( "gridFooterPage", 3 /* cols */, 1 /* row */ );

			AutoTextHandle autoText = designFactory.newAutoText(null);
			cellGridFooter1.getContent( ).add(gridFooterPage);

			RowHandle rowFooterPage = (RowHandle)gridFooterPage.getRows().get(0);
			CellHandle cellGridFooterPage1 = (CellHandle) rowFooterPage.getCells( ).get( 0 );
			cellGridFooterPage1.setProperty(StyleHandle.TEXT_ALIGN_PROP,ConstEdition.TEXT_ALIGN_RIGHT);
			autoText.setStringProperty(ConstEdition.TYPE, ConstEdition.AUTO_TEXT_PAGE_NUMBER);
			cellGridFooterPage1.getContent( ).add( autoText );

			CellHandle cellGridFooterPage2 = (CellHandle) rowFooterPage.getCells( ).get( 1 );
			cellGridFooterPage2.setProperty(StyleHandle.TEXT_ALIGN_PROP,ConstEdition.TEXT_ALIGN_RIGHT);
			LabelHandle label_1 = designFactory.newLabel( null );
			label_1.setText(ConstEdition.SYMBOL_BIAS);
			cellGridFooterPage2.getContent( ).add( label_1 );

			CellHandle cellGridFooterPage3 = (CellHandle) rowFooterPage.getCells( ).get( 2 );
			cellGridFooterPage3.setProperty(StyleHandle.TEXT_ALIGN_PROP,ConstEdition.TEXT_ALIGN_RIGHT);
			AutoTextHandle autoText2 = designFactory.newAutoText(null);
			autoText2.setStringProperty(ConstEdition.TYPE, ConstEdition.AUTO_TEXT_TOTAL_PAGE);
			cellGridFooterPage3.getContent( ).add( autoText2 );

			CellHandle cellGridFooter2 = (CellHandle) rowFooter.getCells( ).get( 1 );
			LabelHandle labelNomDossier = designFactory.newLabel( null );
			labelNomDossier.setText(ConstEdition.TEXT_PAGE_FOOTER+NOM_DOSSIER);
			cellGridFooter2.setProperty(StyleHandle.TEXT_ALIGN_PROP, ConstEdition.TEXT_ALIGN_CENTER);
			cellGridFooter2.getContent().add(labelNomDossier);

			CellHandle cellGridFooter3 = (CellHandle) rowFooter.getCells( ).get( 0 );
			LabelHandle labelDateEdition = designFactory.newLabel( null );
			cellGridFooter3.setProperty(StyleHandle.TEXT_ALIGN_PROP,ConstEdition.TEXT_ALIGN_LEFT);
			LibDate dateDossier = new LibDate();
			String s =  dateDossier.dateToString(new Date());
			labelDateEdition.setText(s);
			cellGridFooter3.getContent().add(labelDateEdition);

		} catch (SemanticException e2) {
			logger.error("",e2);
		}
		try {
			designHandle.getMasterPages( ).add( element );

		} catch (ContentException e1) {
			logger.error("",e1);
		} catch (NameException e1) {
			logger.error("",e1);
		}

		buildScriptDataSource();
		
		buildScriptDataSet();
	}

	public void makeTable(){

	}
	
	public MakeDynamiqueReport(ArrayList nameTableCol,ArrayList nameTableDetail,
			String pathFileReport,String name_report,String pageOrientation,
			String nomDossier){
		this.nameTableCol = nameTableCol;
		this.nameTableDetail = nameTableDetail;
		this.pathFileReport = pathFileReport;
		NAME_REPORT = name_report;
		choixPageOrientation = pageOrientation;
		//this.pageBreakInterval = pageBreakInterval;
		NOM_DOSSIER = nomDossier;
		//this.simpleNameEntity = simpleNameEntity;
	}
	
	/**
	 * @param nombreColonneTable - Nombre de colonne de la table, dans la majorite des ecrans, cette valeur est automatique determine
	 * a partir du nombre de colonne affiche dans l'ecran concerné == Nombre de colonne du tableviewer (décrite dans le fichier properties)
	 * Dans l'écran de Sélection/Recherche, il n'y a pas de controller et donc de tableviewer affecté à chaque recherche.
	 * Il faut donc passé le nombre colonne qui servent a représenter le résultat de la recherche.
	 * <br>
	 * <b>
	 * Ce constructeur ne devrait donc etre utilisé que dans le module de Selection/Recherche
	 * </b>
	 */
	public MakeDynamiqueReport(ArrayList nameTableCol,ArrayList nameTableDetail,
			String pathFileReport,String name_report,String pageOrientation,String nomDossier,int nombreColonneTable){
		this.nameTableCol = nameTableCol;
		this.nameTableDetail = nameTableDetail;
		this.pathFileReport = pathFileReport;
		NAME_REPORT = name_report;
		choixPageOrientation = pageOrientation;
		//this.pageBreakInterval = pageBreakInterval;
		NOM_DOSSIER = nomDossier;
		this.nombreColonneTable = nombreColonneTable;
	}

	/**
	 * pour DataSource qui est Script Data Source 
	 */
	public void buildScriptDataSource(){
		try {
			ScriptDataSourceHandle scriptDataSourceHandle = designFactory.newScriptDataSource(DataSourceScript);
			designHandle.getDataSources().add(scriptDataSourceHandle);
		} catch (ContentException e) {
			logger.error("",e);
		} catch (NameException e) {
			logger.error("",e);
		}
	}
	/**
	 * pour DataSet qui est Script Data Set 
	 */
	public void buildScriptDataSet(){
		
		
		try {
			ScriptDataSetHandle scriptDataSetHandle = designFactory.newScriptDataSet(DataSetScript);
			scriptDataSetHandle.setDataSource(DataSourceScript);
			designHandle.getDataSets().add(scriptDataSetHandle);
			
			/***************************************
			 * pour construire une partie de xml edtion
			 * 
			 * <list-property name="resultSetHints">
			 *  ...
			 * </list-property>
			 * <list-property name="columnHints"> 
			 *  ...
			 * </list-property>
			 * <structure name="cachedMetaData">
             *    <list-property name="resultSet">
             *      ...
             *    </list-property>
             * </structure>
			 ************************************/
			PropertyHandle propertyHandleRSH =	scriptDataSetHandle.getPropertyHandle(ScriptDataSetHandle.RESULT_SET_HINTS_PROP);
			PropertyHandle propertyHandleCH =	scriptDataSetHandle.getPropertyHandle(ScriptDataSetHandle.COLUMN_HINTS_PROP);
			
			ResultSetColumn resultSetHints;
			ColumnHint columnHint;
			
			
			CachedMetaData cachedMetaData = StructureFactory.createCachedMetaData();
			CachedMetaDataHandle cachedMetaDataHandle = scriptDataSetHandle.setCachedMetaData(cachedMetaData);
			ResultSetColumn resultSet;
			
			LinkedHashMap<String, String> mapListChampEtType = fonctionGetInfosXml.getMapListChampEtType();
			LinkedHashMap<String, String> mapListChampEtMethode = fonctionGetInfosXml.getMapListChampEtMethode();
			LinkedHashMap<String, String> mapListChampEtRow = fonctionGetInfosXml.getMapListChampEtRow();
			int position = 0;
			for (String nomChamp : mapListChampEtType.keySet()) {
				position++;
				resultSetHints = StructureFactory.createResultSetColumn();
				resultSetHints.setPosition(position);
				resultSetHints.setColumnName(nomChamp);
				resultSetHints.setDataType(mapListChampEtType.get(nomChamp));
				propertyHandleRSH.addItem(resultSetHints);
				
				columnHint = StructureFactory.createColumnHint();
				columnHint.setProperty("columnName", nomChamp);
				propertyHandleCH.addItem(columnHint);
				
				resultSet = StructureFactory.createResultSetColumn();
				resultSet.setPosition(position);
				resultSet.setColumnName(nomChamp);
				resultSet.setDataType(mapListChampEtType.get(nomChamp));
				cachedMetaDataHandle.getResultSet().addItem(resultSet);
				
				
			}

			
//			CachedMetaData cachedMetaData = StructureFactory.createCachedMetaData();
//			CachedMetaDataHandle cachedMetaDataHandle = scriptDataSetHandle.setCachedMetaData(cachedMetaData);
//			ResultSetColumn resultSet;
//			position = 0;
//			for (String nomChamp : mapListChampEtType.keySet()) {
//				position++;
//				resultSet = StructureFactory.createResultSetColumn();
//				resultSet.setPosition(position);
//				resultSet.setColumnName(nomChamp);
//				resultSet.setDataType(mapListChampEtType.get(nomChamp));
//				cachedMetaDataHandle.getResultSet().addItem(resultSet);
//			}
			

			/************************** methode Script Open **************************/
			scriptDataSetHandle.setOpen("count=0;\n"+
										"imprimeObjet = new Packages.fr.legrain.edition.ImprimeObjet();\n"+
										//"liste = imprimeObjet.l;");
										"liste = imprimeObjet.m.get(\""+this.simpleNameEntity+"\");");
			/************************** methode Script fetch *************************/
			scriptDataSetHandle.setFetch(makeScriptFetchReport(mapListChampEtMethode,mapListChampEtRow));
			
			
		} catch (SemanticException e) {
			logger.error("error :", e);
		}
	}
	/**
	 * pour construire script de fetch dans l'edition
	 * EX: if(count==0) {
			article = liste.get(count);
			if(article.getCodeArticle() == null){row["CODE_ARTICLE"]="";}
			else{row["CODE_ARTICLE"]=article.getCodeArticle();}	
			
			row["CODE_ARTICLE"] = article.getCodeArticle();
			row["LIBELLEC_ARTICLE"] = article.getLibellecArticle();
			row["DESCRIPTION"] = article.getLibellelArticle();
			row["CODE_FAMILLE"] = article.getTaFamille().getCodeFamille();
			row["COMPTE"] = article.getNumcptArticle();
			if(article.getTaTva() == null || article.getTaTva().getCodeTva() == null){
				row["CODE_TVA"] = "";
			}else{
				row["CODE_TVA"] = article.getTaTva().getCodeTva();
			}
			row["EXIGIBILITE"] = article.getTaTTva().getCodeTTva();
			row["OBSERVATION"] = article.getDiversArticle();
			row["COMMENAIRE"] = article.getCommentaireArticle();
			row["STOCK_MIN"] = article.getStockMinArticle();
			count++;
			return true;
		}
			return false;
	 */

	public String makeScriptFetchReport(Map<String,String> mapListChampEtMethode,Map<String,String> mapListChampEtRow){
		//String scriptFetchEdtion = "if(count==0) {\n" +
		String scriptFetchEdtion = "if(count< liste.size()) {\n" +
					nomObjet+"= liste.get(count);\n";
		
		for (String nomChamp : mapListChampEtMethode.keySet()) {
	
			scriptFetchEdtion += makePartiScriptFetchReport(nomObjet,mapListChampEtMethode.get(nomChamp),
								 mapListChampEtRow.get(nomChamp)); 
			
//			scriptFetchEdtion += "if("+nomObjet+"."+mapListChampEtMethode.get(nomChamp)+" == null){"+mapListChampEtRow.get(nomChamp)+"=\"\";}\n" +
//								"else{"+mapListChampEtRow.get(nomChamp)+"="+nomObjet+"."+mapListChampEtMethode.get(nomChamp)+";}\n";
			
//			scriptFetchEdtion += mapListChampEtRow.get(nomChamp)+"="+nomObjet+"."+mapListChampEtMethode.get(nomChamp)+
//			                     ";\n";
		}
		scriptFetchEdtion+="count++;\n" +
						   "return true;\n" +
						   "}\n" +
				           "return false;";
		return scriptFetchEdtion;
	}
	/**
	 * 
	 * @param nomMethode ==> getTaTva().getCodeTva()
	 * @return
	 * EX : 
	 *     if(article.getTaTva() == null || article.getTaTva().getCodeTva() == null){
	 *			row["CODE_TVA"] = "";
	 *		}else{
	 *			row["CODE_TVA"] = article.getTaTva().getCodeTva();
	 *		}
	 */
	public String makePartiScriptFetchReport(String nomEntity,String nomMethode,String row){
		List<String > listMethode = new ArrayList<String>();
		String parti = "";
		String partiNomMethode = "";
		String[] arrayNomMethode = nomMethode.split("\\.");
		for (int i = 0; i < arrayNomMethode.length; i++) {
			partiNomMethode +=  "."+arrayNomMethode[i];
			listMethode.add(partiNomMethode);
		}
		
		for (int i = 0; i < listMethode.size(); i++) {
			if(i!=0){
				parti += " || "+nomEntity+listMethode.get(i)+" == null";
			}
			else{
				parti +=nomEntity+listMethode.get(i)+" == null";
			}
		}
		String partiScriptReport = "if("+parti+"){\n"+row+"=\"\";\n}\n"+"else{\n" +
									row+"="+nomEntity+"."+nomMethode+";\n}\n";
		
		return partiScriptReport;
	}
	public void biuldTableReport(String widthTable,String UnitWidthTable,String nameTable,
			int NombreHeader,int NombreDetail,int NombreFooter,
			String limiteNombreligneTable,LinkedHashMap<String,AttributElementResport> attributeTableHead,
			LinkedHashMap<String,AttributElementResport> attributeTableDetail){

		try {
			int nbCol = nombreColonneTable==0 ? nameTableCol.size() : nombreColonneTable;
			TableHandle table = designFactory.newTableItem( "table", nbCol,
					NombreHeader,NombreDetail,NombreFooter);
			table.setWidth( widthTable+UnitWidthTable);
			table.setStringProperty("pageBreakInterval", limiteNombreligneTable);
			updateBorder(table);
			table.setDataSet(designHandle.findDataSet(DataSetScript));

			PropertyHandle computedSet = table.getColumnBindings( ); 
			ComputedColumn  cs1 = null;
			for (String NameColoneTable : attributeTableDetail.keySet()) {
				cs1 = StructureFactory.createComputedColumn();
				cs1.setName(NameColoneTable);

				cs1.setExpression("dataSetRow[\"" + NameColoneTable + "\"]");
				cs1.setProperty(ComputedColumn.DATA_TYPE_MEMBER, attributeTableDetail.get(NameColoneTable).getTYPE_COL());

				computedSet.addItem(cs1);
			}

			/**
			 * pour definir width de colonne de table
			 */
			int j=0;
			for (String NameCol : attributeTableHead.keySet()) {
				ColumnHandle colTable = (ColumnHandle)table.getColumns().get(j);
				String WidthCol = attributeTableHead.get(NameCol).getWidthTableCol()+attributeTableHead.get(NameCol).getUNITE_WIDTH();
				colTable.setStringProperty(/*ConstEdition.PROPERTY_WIDTH*/"width", WidthCol);
				j++;
			}

			/**
			 * Definir des attributes pour head de table 
			 */
			RowHandle tableheader = (RowHandle)table.getHeader().get(0);
			j=0;
			for (String NameCol : attributeTableHead.keySet()) {
				//LabelHandle label1 = designFactory.newLabel((String)nameTableCol.get(j));	
				LabelHandle label1 = designFactory.newLabel(NameCol);
				label1.setText(attributeTableHead.get(NameCol).getName());
				CellHandle cell = (CellHandle) tableheader.getCells().get( j );
				cell.setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, attributeTableHead.get(NameCol).getTEXT_ALIGN());
				cell.setStringProperty(StyleHandle.FONT_WEIGHT_PROP/*"fontWeight"*/,attributeTableHead.get(NameCol).getFONT_WIDTH());
				updateBorder(cell);
				cell.getContent( ).add( label1 );
				j++;
			}
			/**
			 * Definir des attributes pour detail de table 
			 */
			RowHandle tabledetail = (RowHandle) table.getDetail().get(0);
			j=0;
			for (String NameCol : attributeTableDetail.keySet()) {
				CellHandle cell = (CellHandle) tabledetail.getCells().get(j);
				DataItemHandle data = designFactory.newDataItem("data_"+NameCol );
				data.setResultSetColumn(NameCol);

				StyleHandle dataItemStyle = data.getPrivateStyle();
				if(attributeTableDetail.get(NameCol).getUNITE_CASH().equalsIgnoreCase("EURO")){
					/**
					 * for update format of Number
					 * for example : 1222==>1,222€
					 */
					dataItemStyle.setNumberFormatCategory(DesignChoiceConstants.NUMBER_FORMAT_TYPE_CURRENCY);
					Currency currency;
					currency = Currency.getInstance(Locale.FRANCE);
					dataItemStyle.setNumberFormat("###0.00"+currency.getSymbol(Locale.FRANCE));
				}
				cell.setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, attributeTableDetail.get(NameCol).getTEXT_ALIGN());
				updateBorder(cell);
				cell.getContent( ).add( data );
				j++;
			}
			//*********table footer*********//
			int NoRowFooter = 0;
			if(attribuElemTableFooter!=null){
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
							listCellTableFooter.get(4).setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, 
									attribuElemTableFooter.get(NameFooter).getTEXT_ALIGN());
							listCellTableFooter.get(4).setStringProperty(StyleHandle.FONT_WEIGHT_PROP/*"fontWeight"*/,
									attribuElemTableFooter.get(NameFooter).getFONT_WIDTH());
						}
					}
					NoRowFooter++;
				}
			}
			designHandle.getBody( ).add(table);
		} catch (SemanticException e) {
			logger.error("error", e);
		}
	}

	/**
	 * definitionColoneEntete et definitionColoneCorps are LinkedHashMap
	 * The type of Map keep order values when we inert values into the type of Map!
	 * those others Map can't keep order!
	 */
//	public void makeReportTableDB(int widthTable,String widthTableUnit,String NameTable,
//			Map<String,AttributElementResport> definitionColoneEntete, 
//			Map<String,AttributElementResport> definitionColoneCorps,
//			int NombreHeader,int NombreDetail, int NombreFooter, 
//			int NombreColGroupe,String limiteNombreligneTable){
//		TableHandle table = designFactory.newTableItem( "table", nameTableCol.size(),
//				NombreHeader,NombreDetail,NombreFooter);
//		try {
//			table.setWidth( widthTable+widthTableUnit );
//			table.setStringProperty("pageBreakInterval", limiteNombreligneTable);
//			updateBorder(table);
//			table.setDataSet( designHandle.findDataSet( "ds" ) );
//
//			PropertyHandle computedSet = table.getColumnBindings( ); 
//			ComputedColumn  cs1 = null;
//
//			//ArrayList<ComputedColumn> attribuCsl =new ArrayList<ComputedColumn>();
//			//int j =0;
//			for (String NameCol : definitionColoneCorps.keySet()) {
//
//				cs1 = StructureFactory.createComputedColumn();
//				//cs1 = StructureFactory.newComputedColumn(designHandle, "");
//				cs1.setName(NameCol);
//				//cs1.setProperty("dataType", definitionColoneCorps.get(NameCol).getTYPE_COL());
//				//attribuCsl.add(cs1);
//				//System.out.println(NameCol);
//				if(NameCol.equalsIgnoreCase("DATE")){
//					//cs1.setExpression("dataSetRow[\"" + NameCol +"_EP"+ "\"]");
//					cs1.setExpression("dataSetRow[\"" + NameCol + "\"]");
//					cs1.setProperty(ComputedColumn.DATA_TYPE_MEMBER/*"dataType"*/, definitionColoneCorps.get(NameCol).getTYPE_COL());
//				}
//				//				else if(definitionColoneCorps.get(NameCol).getUNITE_CASH().equalsIgnoreCase(ConstEdition.UNITS_PERCENTAGE)){
//				//					cs1.setExpression("dataSetRow[\"" + NameCol + "\"]");
//				// 					cs1.setProperty("dataType", definitionColoneCorps.get(NameCol).getTYPE_COL());
//				//				}
//				/**
//				 * for add dynamic image
//				 */
//				else if(NameCol.equalsIgnoreCase("ACTIF_TIERS")){
//					cs1.setExpression("var value = \"\"; " +
//							"if(dataSetRow[\"" + NameCol + "\"]==1){" +
//							"value=\"OUI\";}" +
//					"else{value=\"NON\";}");
//
//				}
//				else if(NameCol.equalsIgnoreCase("TTC_TIERS")){
//					cs1.setExpression("var value = \"\"; " +
//							"if(dataSetRow[\"" + NameCol + "\"]==1){" +
//							"value=\"OUI\";}" +
//					"else{value=\"NON\";}");
//				}
//				else{
//
//					cs1.setExpression("dataSetRow[\"" + NameCol + "\"]");
//					//cs1.setProperty(ComputedColumn.DATA_TYPE_MEMBER/*"dataType"*/, definitionColoneCorps.get(NameCol).getTYPE_COL());
//				}
//				computedSet.addItem(cs1);
//
//			}
//
//			int j=0;
//			for (String NameCol : definitionColoneEntete.keySet()) {
//				ColumnHandle colTable = (ColumnHandle)table.getColumns().get(j);
//				String WidthCol = definitionColoneEntete.get(NameCol).getWidthTableCol()+
//				definitionColoneEntete.get(NameCol).getUNITE_WIDTH();
//
//				colTable.setStringProperty(consEdition.PROPERTY_WIDTH/*"width"*/, WidthCol);
//				//colTable.setProperty("width", DesignChoiceConstants.)
//				j++;
//			}
//
//			RowHandle tableheader = (RowHandle) table.getHeader( ).get( 0 );
//			j=0;
//			for (String NameCol : definitionColoneEntete.keySet()) {
//
//				LabelHandle label1 = designFactory.newLabel( (String)nameTableCol.get(j) );	
//				label1.setText(NameCol);
//				CellHandle cell = (CellHandle) tableheader.getCells( ).get( j );
//				cell.setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, definitionColoneEntete.get(NameCol).getTEXT_ALIGN());
//				cell.setStringProperty(StyleHandle.FONT_WEIGHT_PROP/*"fontWeight"*/,definitionColoneEntete.get(NameCol).getFONT_WIDTH());
//				updateBorder(cell);
//				cell.getContent( ).add( label1 );
//				j++;
//			}	
//
//			// table detail
//			RowHandle tabledetail = (RowHandle) table.getDetail( ).get( 0 );
//			j=0;
//			//for( int j=0; j < nameTableCol.size(); j++){
//			for (String NameCol : definitionColoneCorps.keySet()) {
//				//System.out.println(NameCol);		
//				CellHandle cell = (CellHandle) tabledetail.getCells( ).get( j );
//
//				DataItemHandle data = designFactory.newDataItem( "data_"+NameCol );
//				data.setResultSetColumn(NameCol);
//
//
//				StyleHandle dataItemStyle = data.getPrivateStyle();
//
//				if(NameCol.equalsIgnoreCase("DATE")){
//					/**
//					 * for update format of data
//					 * example ==> 15 juin 08==>15/06/08
//					 */
//					dataItemStyle.setDateTimeFormatCategory(DesignChoiceConstants.DATETIEM_FORMAT_TYPE_SHORT_DATE/*or "Short Date"*/);
//					dataItemStyle.setDateTimeFormat(DesignChoiceConstants.DATETIEM_FORMAT_TYPE_SHORT_DATE/* or "Short Date" */);
//				}
//				if(definitionColoneCorps.get(NameCol).getUNITE_CASH().equalsIgnoreCase("EURO")){
//					/**
//					 * for update format of Number
//					 * for example : 1222==>1,222€
//					 */
//					dataItemStyle.setNumberFormatCategory(DesignChoiceConstants.NUMBER_FORMAT_TYPE_CURRENCY);
//					Currency currency;
//					currency = Currency.getInstance(Locale.FRANCE);
//					dataItemStyle.setNumberFormat("###0.00"+currency.getSymbol());
//				}
//				if(definitionColoneCorps.get(NameCol).getUNITE_CASH().equalsIgnoreCase(ConstEdition.UNITS_PERCENTAGE)){
//					StringFormatter formatPercentage = new StringFormatter();					
//					dataItemStyle.setStringFormat(formatPercentage.format(ConstEdition.FORMAT_TAUX_TVA)+ConstEdition.UNITS_PERCENTAGE);
//
//				}
//				cell.setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, definitionColoneCorps.get(NameCol).getTEXT_ALIGN());
//				updateBorder(cell);
//				cell.getContent( ).add( data );
//				j++;
//			}
//			//*********table footer*********//
//			int NoRowFooter = 0;
//			if(attribuElemTableFooter!=null){
//				for (String NameFooter : attribuElemTableFooter.keySet()) {
//					RowHandle rowtablefooter = (RowHandle)table.getFooter().get(NoRowFooter);
//					/**
//					 * for Frist--I put all of cellFooterTable in listCellTableFooter
//					 */
//					ArrayList<CellHandle> listCellTableFooter = new ArrayList<CellHandle>();
//					for(int i = 0;i<nameTableCol.size();i++){
//						CellHandle cell2= (CellHandle) rowtablefooter.getCells( ).get( i);
//						listCellTableFooter.add(cell2);
//						updateBorder(listCellTableFooter.get(i));
//					}
//					/**
//					 * for merge cells
//					 */
//					for (int i = 0; i < listCellTableFooter.size(); i++) {
//						if(i<4){
//							listCellTableFooter.get(i).drop();
//						}
//						if(i==4){
//							LabelHandle labelfooter = designFactory.newLabel( null );
//							listCellTableFooter.get(4).setColumnSpan(5);
//							listCellTableFooter.get(4).setRowSpan(1);
//							listCellTableFooter.get(4).getContent( ).add( labelfooter );
//							labelfooter.setText(NameFooter);
//							listCellTableFooter.get(4).setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, 
//									attribuElemTableFooter.get(NameFooter).getTEXT_ALIGN());
//							listCellTableFooter.get(4).setStringProperty(StyleHandle.FONT_WEIGHT_PROP/*"fontWeight"*/,
//									attribuElemTableFooter.get(NameFooter).getFONT_WIDTH());
//						}
//					}
//					NoRowFooter++;
//				}
//			}			
//			designHandle.getBody( ).add( table );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void makeReportTableDBDefeut(Map<String,AttributElementResport>definitionColoneEntete,
//			Map<String,AttributElementResport>definitionColoneCorps){
//
//		makeReportTableDB(100,"%","Table", definitionColoneEntete, definitionColoneCorps,1,1,1,0,"40");			
//	}
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
		for(int i=0; i < gridCols; i++){
			ColumnHandle col = (ColumnHandle)grid.getColumns().get(i);
			col.setStringProperty(/*ConstEdition.PROPERTY_WIDTH*/"width",widthCol+widthGridUnit);
		}
		return grid;
	}
	
	public void makeReportHeaderGrid(int gridCols,int gridRows,int widthGrid,String widthGridUnit,
			Map<String,AttributElementResport>definitionGrid) {

		GridHandle gridHeader;
		try {
			gridHeader = makeReportGrid(gridCols,gridRows,widthGrid,widthGridUnit);
			int NoRowGrid = 0;
			for (String NameCol : definitionGrid.keySet()) {
				RowHandle row = null;
				if(!definitionGrid.get(NameCol).getCOLCOR().equalsIgnoreCase("")){
					row = (RowHandle) gridHeader.getRows( ).get( NoRowGrid );
					String colorRow = Color.gray.toString();//=>gray
					//System.out.println(colorRow);
					//row.setStringProperty("backgroundColor","gray");
					row.setProperty(StyleHandle.BACKGROUND_COLOR_PROP,ConstEdition.COLOR_GRAY);
				}else {
					row = (RowHandle) gridHeader.getRows( ).get( NoRowGrid );
				}

				CellHandle cell = (CellHandle) row.getCells( ).get( 0 );
				cell.setColumnSpan(gridCols);

				LabelHandle label = designFactory.newLabel("GridHead"+NoRowGrid);
				cell.getContent( ).add( label );
				label.setText(NameCol);
				cell.setStringProperty(StyleHandle.TEXT_ALIGN_PROP/*"textAlign"*/, definitionGrid.get(NameCol).getTEXT_ALIGN());
				cell.setStringProperty(StyleHandle.FONT_WEIGHT_PROP/*"fontWeight"*/,definitionGrid.get(NameCol).getFONT_WIDTH());
				cell.setStringProperty(StyleHandle.FONT_SIZE_PROP/*"fontSize"*/, definitionGrid.get(NameCol).getFONT_SIZE());
				cell.setStringProperty(StyleHandle.TEXT_UNDERLINE_PROP/*"textUnderline"*/,"underline");

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
			logger.error("",e);
		}

	}

	public void makeDynamiqueGrid(int cols,int rows,String unitWidth,String widthGrid){
	}
	
	public void makeReportLabel(RowHandle row,int nombreCell){
		for(int i=0; i < nombreCell; i++){

		}
	}
	
	public void savsAsDesignHandle(){
		try {
			designHandle.saveAs(pathFileReport);
		} catch (IOException e) {
			logger.error("",e);
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
//	public String getQuerySql() {
//		return querySql;
//	}
//	public void setQuerySql(String querySql) {
//		this.querySql = querySql;
//	}
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
//	public String getURLFireBird() {
//		return URLFireBird;
//	}
//	public void setURLFireBird(String fireBird) {
//		URLFireBird = fireBird;
//	}
//	public String getUser() {
//		return User;
//	}
//	public void setUser(String user) {
//		User = user;
//	}
//	public String getPassword() {
//		return Password;
//	}
//	public void setPassword(String password) {
//		Password = password;
//	}
	public Map<String, AttributElementResport> getAttribuElemTableHeader() {
		return attribuElemTableHeader;
	}
	public void setAttribuElemTableHeader(
			Map<String, AttributElementResport> attribuElemTableHeader) {
		this.attribuElemTableHeader = attribuElemTableHeader;
	}
	public Map<String, AttributElementResport> getAttribuElemTableDetail() {
		return attribuElemTableDetail;
	}
	public void setAttribuElemTableDetail(
			Map<String, AttributElementResport> attribuElemTableDetail) {
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
	public Map<String, AttributElementResport> getAttribuElemTableFooter() {
		return attribuElemTableFooter;
	}
	public void setAttribuElemTableFooter(
			Map<String, AttributElementResport> attribuElemTableFooter) {
		this.attribuElemTableFooter = attribuElemTableFooter;
	}
	public String getNAME_REPORT() {
		return NAME_REPORT;
	}
	public void setNAME_REPORT(String name_report) {
		NAME_REPORT = name_report;
	}

	public FonctionGetInfosXmlAndProperties getFonctionGetInfosXml() {
		return fonctionGetInfosXml;
	}

	public void setFonctionGetInfosXml(FonctionGetInfosXmlAndProperties fonctionGetInfosXml) {
		this.fonctionGetInfosXml = fonctionGetInfosXml;
	}

	public String getNomObjet() {
		return nomObjet;
	}

	public void setNomObjet(String nomObjet) {
		this.nomObjet = nomObjet;
	}

	public String getSimpleNameEntity() {
		return simpleNameEntity;
	}

	public void setSimpleNameEntity(String simpleNameEntity) {
		this.simpleNameEntity = simpleNameEntity;
	}


}
