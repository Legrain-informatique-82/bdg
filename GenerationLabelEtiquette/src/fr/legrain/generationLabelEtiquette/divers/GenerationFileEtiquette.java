package fr.legrain.generationLabelEtiquette.divers;


import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.CellHandle;
import org.eclipse.birt.report.model.api.ColumnHandle;
import org.eclipse.birt.report.model.api.DesignConfig;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.GridHandle;
import org.eclipse.birt.report.model.api.IDesignEngine;
import org.eclipse.birt.report.model.api.IDesignEngineFactory;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.RowHandle;
import org.eclipse.birt.report.model.api.SessionHandle;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.TextItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.command.ContentException;
import org.eclipse.birt.report.model.api.command.NameException;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import com.ibm.icu.util.ULocale;

import fr.legrain.edition.actions.AffichageEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.generationLabelEtiquette.preferences.PreferenceConstants;
import fr.legrain.generationLabelEtiquette.wizard.WizardController;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import generationlabeletiquette.Activator;


public class GenerationFileEtiquette {

	static Logger logger = Logger.getLogger(GenerationFileEtiquette.class.getName());
			
	private String nameFileEtiquette; 
	private List<String> allLinesFileExtraction = new LinkedList<String>();
	private List<String> allLinesFileExtractionFormatBirt = new LinkedList<String>();
	
	private int decalage = 0;
	private int quantite = 1;
	
	private DesignConfig config = null;
	private IDesignEngineFactory factory = null;
	private IDesignEngine engine = null;
	private SessionHandle session = null;
	private ReportDesignHandle designHandle = null;
	private ElementFactory designFactory = null;
	
	private float floatWidthColEtiquette;
	private float floatHeightColEtiquette;
	
	private WizardController wizardController;
	
	private String oneLineFileExtraction;
	
	private String motCleEtiquette="";
	
	private String startContentGridReport = "";
	
	private List<String> nameChampExtractionEtiquette = new LinkedList<String>();
	private List<String> listNameMotcleEtiquette = new LinkedList<String>();
	private Map<Integer,String> motCleEtiquetteEtPosition = new HashMap<Integer,String>();
	
	private Map<String,String> mapFileParamEtiquette = new HashMap<String,String>();
	
	private String valueFormatEtiquetteBirtHtml = "";
	
	public String getPathFileEtiquette(String pathFolderEtiquette){
		Calendar calendar = Calendar.getInstance();
		String nameFileOffice = String.valueOf(calendar.getTimeInMillis());
		nameFileEtiquette = nameFileOffice+ConstantModelLabels.TYPE_ETIQUETTE_FILE_REPORT;	
		
		return pathFolderEtiquette+"/"+nameFileEtiquette;
		
	}
	
	public void initializeBuildDesignReportConfig(String pathFile){
		//File file  = new File(pathFileEtiquette);
		config = new DesignConfig();
		try {
			Platform.startup( config );
			factory = (IDesignEngineFactory)Platform.createFactoryObject
									(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
			engine = factory.createDesignEngine(config);
			session = engine.newSessionHandle( ULocale.FRENCH ) ;
			designHandle = session.createDesign(pathFile);
			designFactory = designHandle.getElementFactory( );
		} catch (BirtException e) {
			logger.error("", e);
		}
	}
	
	public void makeMasterPage(Float leftMargin,Float rightMargin,Float topMargin,Float bottomMargin,
			Float largeurPapier,Float hauteurPapier, String pageSize){
		DesignElementHandle element = designFactory.newSimpleMasterPage("PageMasterEtiquette");
		
		try {
			element.setProperty(ConstantModelLabels.PROPERTY_TYPE, pageSize);
//			element.setProperty(ConstantModelLabels.PROPERTY_TYPE, ConstantModelLabels.TYPE_PAPER_CUSTOM);
			
			if(pageSize.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_CUSTOM)){
	
				//element.setFloatProperty(ConstantModelLabels.PROPERTY_HEIGHT,convertCmToInch(hauteurPapier));
				//element.setFloatProperty(ConstantModelLabels.PROPERTY_WIDTH,convertCmToInch(largeurPapier));
				//DimensionUtil util = new DimensionUtil();
				DimensionValue valueHauteurPapier = new DimensionValue(hauteurPapier, "cm");
				element.setProperty(ConstantModelLabels.PROPERTY_WIDTH,valueHauteurPapier);
				
				DimensionValue valueLargeurPapier = new DimensionValue(largeurPapier, "cm");
				element.setProperty(ConstantModelLabels.PROPERTY_WIDTH,valueLargeurPapier);
				
			}
			element.setProperty(ConstantModelLabels.PAGE_ORIENTATION/*orientation*/, DesignChoiceConstants.PAGE_ORIENTATION_PORTRAIT);
			
			DimensionValue valueTopMargin = new DimensionValue(topMargin, "cm");
			element.setProperty(ConstantModelLabels.TOP_MARGIN,valueTopMargin);
			
			DimensionValue valueBottomMargin = new DimensionValue(bottomMargin, "cm");
			element.setProperty(ConstantModelLabels.BOTTOM_MARGIN,valueBottomMargin);
			
			DimensionValue valueLeftMargin = new DimensionValue(leftMargin, "cm");
			element.setProperty(ConstantModelLabels.LEFT_MARGIN,valueLeftMargin);
			
			DimensionValue valueRightMargin = new DimensionValue(rightMargin, "cm");
			element.setProperty(ConstantModelLabels.RIGHT_MARGIN,valueRightMargin);
			
//			element.setFloatProperty(ConstantModelLabels.TOP_MARGIN,convertCmToInch(topMargin));
//			element.setFloatProperty(ConstantModelLabels.BOTTOM_MARGIN,convertCmToInch(bottomMargin));
//			element.setFloatProperty(ConstantModelLabels.LEFT_MARGIN,convertCmToInch(leftMargin));
//			element.setFloatProperty(ConstantModelLabels.RIGHT_MARGIN,convertCmToInch(rightMargin));
			
			element.setFloatProperty("headerHeight",0);
			element.setFloatProperty("footerHeight",0);
				
			designHandle.getMasterPages().add(element);
		} catch (SemanticException e) {
			logger.error("", e);
		}
		
	}
	
	public void makePage(int LinesFileExtraction,int nombreRows,int nombreColumns,float heightGrid,float widthGrid,
			float largeurEspace,float hauteurEspace,
			float leftPadding,float rightPadding,float topPadding,float bottomPadding,
			boolean cellBorder,String sizeEtiquette,String styleGras,String styleItalic,int decalage,int quantite){
		
		makePage(null,LinesFileExtraction, nombreRows, nombreColumns, heightGrid, widthGrid,
				 largeurEspace, hauteurEspace,
				 leftPadding, rightPadding, topPadding, bottomPadding,
				 cellBorder, sizeEtiquette, styleGras, styleItalic,decalage,quantite);
		
	}
	public void makePage(IProgressMonitor monitor,int LinesFileExtraction,int nombreRows,int nombreColumns,float heightGrid,float widthGrid,
			float largeurEspace,float hauteurEspace,
			float leftPadding,float rightPadding,float topPadding,float bottomPadding,
			boolean cellBorder,String sizeEtiquette,String styleGras,String styleItalic,int decalage,int quantite){
		
//		GridHandle gridEtiquette = makePageGrid(LinesFileExtraction,nombreRows,nombreColumns,heightGrid,widthGrid,
//												largeurEspace,hauteurEspace);
		this.decalage = decalage;
		this.quantite = quantite;
		List<GridHandle> listGridEtiquette = makePageGrid(LinesFileExtraction,nombreRows,nombreColumns,heightGrid,widthGrid,
														  largeurEspace,hauteurEspace);
	
		addConttentGrid(listGridEtiquette,nombreRows,nombreColumns,
					leftPadding,rightPadding,topPadding,bottomPadding,
					cellBorder,sizeEtiquette,styleGras,styleItalic);
			
	}
	
	public void updateTextFormatEtiquette(int nombreRows,int nombreColumns,float heightGrid,float widthGrid,
			float largeurEspace,float hauteurEspace){
		
		int nombreColumnsEspace = nombreColumns - 1;
		int nombreColumnsEtiquette = nombreColumns + nombreColumnsEspace;
		
		int nombreRowsEspace = nombreRows - 1;
		int nombreRowsEtiquette = nombreRows + nombreRowsEspace;
		
		float widthTotalEtiquette = widthGrid - (float)(nombreColumnsEspace*largeurEspace);
		float heighTotaltEtiquette = heightGrid - (float)(nombreRowsEspace*hauteurEspace);
		
//		floatWidthColEtiquette = widthTotalEtiquette/(float)nombreColumns;
//		floatHeightColEtiquette = heighTotaltEtiquette/(float)nombreRows;
		
		floatWidthColEtiquette = new BigDecimal(widthTotalEtiquette/(float)nombreColumns).
								                setScale(1, RoundingMode.DOWN).floatValue();
		floatHeightColEtiquette = new BigDecimal(heighTotaltEtiquette/(float)nombreRows).
        							     		setScale(1, RoundingMode.DOWN).floatValue();
	}
	
	public void updateLayoutFormatEtiquette(Text textZoneEtiquette){

		textZoneEtiquette.setLayoutData(String.format(ConstantModelLabels.LAYOUT_TEXT_ETIQUETTE,
				String.valueOf(floatWidthColEtiquette),String.valueOf(floatHeightColEtiquette)));
		
		//textZoneEtiquette.setLayoutData("width 5cm!,height 5cm!");
		//group.layout();
	}
	
	public List<GridHandle> makePageGrid(int linesFileExtraction,int nombreRows,int nombreColumns,float heightGrid,float widthGrid,
			float largeurEspace,float hauteurEspace){
		
		int nombreColumnsEspace = nombreColumns - 1; //nombre d'espace sur une ligne
		int nombreColumnsEtiquette = nombreColumns + nombreColumnsEspace; //nombre total de colonne sur une ligne : donnees + esapce
		
		quantite = quantite<=0 ? 1 : quantite;
		float flaotNombreGrid = ((float)linesFileExtraction*quantite+decalage)/nombreColumns; //nombre total de grille (1 grille = 1 ligne d'etiquette) a créer
		int nombreGrid = new BigDecimal(flaotNombreGrid).setScale(0, RoundingMode.UP).intValue();
		
		List<GridHandle> listGrid = new LinkedList<GridHandle>();
		
		try {
			for (int i = 1; i <= nombreGrid; i++) {
				String nameGrid = "gridEtiquette" + i;
				GridHandle grid = designFactory.newGridItem(nameGrid,nombreColumnsEtiquette,1);
				if (i % nombreRows != 0) {
					DimensionValue dmsEspaceGrid = new DimensionValue(hauteurEspace, DesignChoiceConstants.UNITS_CM);
					grid.setProperty(ConstantModelLabels.MARGIN_BOTTOM_GRID,dmsEspaceGrid);	
				}else{
					grid.setStringProperty(ConstantModelLabels.PAGE_BREAK_AFTER,"always");
				}
				designHandle.getBody().add(grid);
				grid.setWidth("100" + ConstantModelLabels.UNITS_PERCENTAGE);
				grid.setHeight("100" + ConstantModelLabels.UNITS_PERCENTAGE);
				
				
				RowHandle row = (RowHandle)grid.getRows().get(0);
				DimensionValue dmsHeighRow = new DimensionValue(floatHeightColEtiquette, DesignChoiceConstants.UNITS_CM);
				row.setProperty(ConstantModelLabels.PROPERTY_HEIGHT,dmsHeighRow);
				
				for (int j = 0; j < nombreColumnsEtiquette; j++) {
					int value = j%2;
					ColumnHandle col = (ColumnHandle)grid.getColumns().get(j);
					float WidthColEtiquette;
					if(value == 0){
						WidthColEtiquette = floatWidthColEtiquette;
					}else{
						WidthColEtiquette = largeurEspace;
					}
					DimensionValue dmsWidth = new DimensionValue(WidthColEtiquette, DesignChoiceConstants.UNITS_CM);
					col.setProperty(ConstantModelLabels.PROPERTY_WIDTH,dmsWidth);
				}
				
				listGrid.add(grid);
			}
		} catch (Exception e) {
			logger.error("", e);
		}	

		return listGrid;	
	}

	public void addConttentGrid(List<GridHandle> listGridEtiquette,int rowsGrid,int columnsGrid,
			float leftPadding,float rightPadding,float topPadding,float bottomPadding,
			boolean cellBorder,String sizeEtiquette,String styleGras,String styleItalic){
		
		int nombreColumnsEspace = columnsGrid - 1;
		int nombreColumnsEtiquette = columnsGrid + nombreColumnsEspace;
		
		int count = 0;
		int numLigneData = 0;
		
		int decLigne = new BigDecimal(decalage/columnsGrid).setScale(0, RoundingMode.UP).intValue();
		decLigne = decLigne>=1?decLigne:0;
		int decCol = new BigDecimal(decalage%columnsGrid).setScale(0, RoundingMode.UP).intValue();
		decCol = decCol>=1?decCol:0;
		
		int nbUtil = 0;
		for (int i = 0+decLigne; i < listGridEtiquette.size(); i++) {
			GridHandle gridHandle = listGridEtiquette.get(i);
			RowHandle row = (RowHandle)gridHandle.getRows().get(0);
			
			for (int j = 0+decCol; j < nombreColumnsEtiquette; j++) {
				decCol = 0; //n'est utilise que pour la premiere ligne d'etiquette
				if(j%2 == 0){
					CellHandle cell = (CellHandle) row.getCells().get(j);
					TextItemHandle textItemHandle = designFactory.newTextItem(count+""+j);
//					if(cellBorder){
//						addBorderCell(cell);
//					}
					updatePaddingCell(cell, leftPadding, rightPadding, topPadding, bottomPadding);
					try {
						cell.getContent().add(textItemHandle);
						String textCell = "";
						
						//if(count<allLinesFileExtractionFormatBirt.size()*quantite){
						if(count<allLinesFileExtractionFormatBirt.size()*quantite){
						
							if(cellBorder){
								addBorderCell(cell);
							}
							textCell = allLinesFileExtractionFormatBirt.get(numLigneData);
							nbUtil++;
							if(nbUtil==quantite) { //on remet à 0 pour le suivant
								nbUtil = 0;
								numLigneData++;
							}
						}
						textItemHandle.setContent(textCell);
						textItemHandle.setStringProperty("contentType", "html");
						textItemHandle.setStringProperty("fontSize", sizeEtiquette+"pt");
						textItemHandle.setStringProperty("fontWeight", styleGras);
						textItemHandle.setStringProperty("fontStyle", styleItalic);
					} catch (ContentException e) {
						logger.error("", e);
					} catch (NameException e) {
						logger.error("", e);
					} catch (SemanticException e) {
						logger.error("", e);
					}
					count++;
				}
			}	
		}		
		
	}
	
	public void addBorderCell(CellHandle cellHandle){
		updateBorder(cellHandle);
	}
	
	public void updatePaddingCell(CellHandle cellHandle,
					float leftPadding,float rightPadding,float topPadding,float bottomPadding){
		
		try {
			
			DimensionValue valuePaddingLeft = new DimensionValue(leftPadding, "cm");
			cellHandle.setProperty("paddingLeft",valuePaddingLeft);
			
			DimensionValue valuePaddingRight = new DimensionValue(rightPadding, "cm");
			cellHandle.setProperty("paddingRight",valuePaddingRight);
			
			DimensionValue valuePaddingTop = new DimensionValue(topPadding, "cm");
			cellHandle.setProperty("paddingTop",valuePaddingTop);
			
			DimensionValue valuePaddingBottom = new DimensionValue(bottomPadding, "cm");
			cellHandle.setProperty("paddingBottom",valuePaddingBottom);
			
//			cellHandle.setFloatProperty("paddingLeft", convertCmToPoints(leftPadding));
//			cellHandle.setFloatProperty("paddingRight", convertCmToPoints(rightPadding));
//			cellHandle.setFloatProperty("paddingTop", convertCmToPoints(topPadding));
//			cellHandle.setFloatProperty("paddingBottom", convertCmToPoints(bottomPadding));
			
		} catch (SemanticException e) {
			logger.error("", e);
		}
	}
	
	public void updateBorder(DesignElementHandle objetDesignElementHandle){
		try {
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_BOTTOM_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID);
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_LEFT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_TOP_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_RIGHT_STYLE_PROP,DesignChoiceConstants.LINE_STYLE_SOLID );

			objetDesignElementHandle.setProperty(StyleHandle.BORDER_RIGHT_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_LEFT_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_TOP_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
			objetDesignElementHandle.setProperty(StyleHandle.BORDER_BOTTOM_WIDTH_PROP,DesignChoiceConstants.LINE_WIDTH_THIN);
		} catch (SemanticException e) {
			logger.error("", e);
		}
		
	}
	
	public void savsAsDesignHandle(String pathFile){
		try {
			designHandle.saveAs(pathFile);
		} catch (IOException e) {
			logger.error("", e);
		}
		designHandle.close();
	}
	
	public String convertFloatToString(float valueFloat){
		String value = null;
		BigDecimal bigDecimal = LibCalcul.arrondi(new BigDecimal(valueFloat));
		value = LibConversion.bigDecimalToString(bigDecimal);
		return value;
	}

	public void getInfosFileExtraction(String pathFileExtraction){
		allLinesFileExtraction.clear();
		FileUtils fileUtils = new FileUtils();
		File file = new File(pathFileExtraction);
		try {
			//allLinesFileExtraction = fileUtils.readLines(file, "UTF-8");
			List<String> line = fileUtils.readLines(file, "UTF-8");
			
			Iterator<String> ite = line.iterator();
			String valeur = "";
			String stock = "";
			while (ite.hasNext()) {
				valeur = ite.next();
				if(!valeur.endsWith(";")) { //changement de ligne dans le fichier mais toujours à l'intérieur de la même ligne de valeur
					stock += valeur;
				} else {
					if(!stock.equals("")) {
						allLinesFileExtraction.add(stock+System.getProperty("line.separator")+valeur);
						stock = "";
					} else
						allLinesFileExtraction.add(valeur);
				}
			}
			
		} catch (IOException e) {
			logger.error("", e);
		}
		
	}
	
	/**
	 * Pour obtenir les mot clé dans le fichier d'extraction
	 * manipuler sur premiere line du fichier d'extraction
	 * ex :nomTiers.prenomTiers.adresse1Adresse.adresse2Adresse.codepostalAdresse.villeAdresse.paysAdresse
	 * 
	 */
	public void getInfosMotCleEtiquette(String valueSeparateur){
		nameChampExtractionEtiquette.clear();
		String[] arrayMotCleEtiquette = allLinesFileExtraction.get(0).split(valueSeparateur);
		for (int i = 0; i < arrayMotCleEtiquette.length; i++) {
			String value = remplaceEspace(arrayMotCleEtiquette[i]);
			nameChampExtractionEtiquette.add(value);
//			int lastPositionPoint = arrayMotCleEtiquette[i].lastIndexOf(".");
//			String partiValue = arrayMotCleEtiquette[i].substring(lastPositionPoint+1)+"~";
//			motCleEtiquette += partiValue;
//			if(i==0){
//				String partiValue = arrayMotCleEtiquette[i].substring(lastPositionPoint+1);
//				motCleEtiquette += partiValue;
//			}else{
//				String partiValue = arrayMotCleEtiquette[i].substring(lastPositionPoint);
//				motCleEtiquette += partiValue;
//			}
		}
	}
	
	/**
	 * pour ramplace espace par sous-ligne
	 * EX : file vient de Epica ==>  nom Tiers => nom_Tiers
	 * @return
	 */
	public String remplaceEspace(String value){
		String valueReturn = "";
		if(value.indexOf(" ") != -1){
			valueReturn = value.replace(" ", "_");
		}
		else{
			valueReturn = value;
		}
		return valueReturn;
	}
	
	/*
	 * pour change allLinesFileExtraction vers 
	 * lists qui contient infos correspondants 
	 * Birt 
	 */
	public void updateListInfosFileExtraction(String valueSeparateur){
		
		allLinesFileExtractionFormatBirt.clear();
		String value = null;
		String[] line = null;
		String data= null;
		for (int i = 1; i < allLinesFileExtraction.size(); i++) {
			value = valueFormatEtiquetteBirtHtml;
			data  = allLinesFileExtraction.get(i);
			data  = data.replaceAll("null", "");
			line  = data.split(valueSeparateur,-1);
			int count = 0;
			for (Integer positionMotCle : motCleEtiquetteEtPosition.keySet()) {
				String motCle = motCleEtiquetteEtPosition.get(positionMotCle);
				Pattern pattern = Pattern.compile(motCle);
				Matcher matcher = pattern.matcher(value);
				//String afterRemplace = "";
				if(matcher.find()){
					value = value.replace(motCle, line[positionMotCle]);
				}
				count++;
			}
			allLinesFileExtractionFormatBirt.add(value);
						
		}
	}
	
	public void readValueMotCleEtiquette(String filePathAttributEtiquette){
		listNameMotcleEtiquette.clear();
		motCleEtiquetteEtPosition.clear();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePathAttributEtiquette));
			props.load(in);
			for (int i = 0; i < nameChampExtractionEtiquette.size(); i++) {
				String value = props.getProperty(nameChampExtractionEtiquette.get(i));
				if(value != null){
					listNameMotcleEtiquette.add(value);
					motCleEtiquetteEtPosition.put(i, value);
				}
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		/*
		 * motCleEtiquette qui affiche dans le zone textArea de format
		 * etiquette
		 */
		motCleEtiquette = "";
		for (int i = 0; i < listNameMotcleEtiquette.size(); i++) {
			motCleEtiquette += listNameMotcleEtiquette.get(i)+" ";
		}
	}
	
	/**
	 * @param listContent ==> all lines of file extraction
	 * pour touver one line qui est plus long
	 */
	public void obtainOneLineFileExtraction(List<String> listContent) {
		int lenghtLine = 0;
		int numeroLine = 0;
		for (int i = 1; i < listContent.size(); i++) {
			if(listContent.get(i).length()>lenghtLine){
				lenghtLine = listContent.get(i).length();
				numeroLine = i;
				oneLineFileExtraction = listContent.get(i);
			}
		}
	}
	
	public void threadShowLableEtiquette(final String pathFileEtiquette,final String nameOnglet){
		Thread t = new Thread() {
			public void run() {
				try {
					showLableEtiquette(pathFileEtiquette,nameOnglet);
				}catch (Exception e) {
					logger.error("", e);
				} finally {
					//					ibTaTable.activeTrigger(trigger,true);
					//					ibTaTable.commitLgr();
				}
			}
		};
		t.start();
	}
	
	public void showLableEtiquette(String pathFileEtiquette,final String nameOnglet){
		
//		//File file = new File(pathFileEtiquette);
//		String url = WebViewerUtil.debutURL();
////		System.setProperty("RUN_UNDER_ECLIPSE", "true");
//		url += "run?__report=";
//		url += pathFileEtiquette;
//		url += "&__document=doc"+new Date().getTime();
//		url += "&__format=pdf";
//		final String finalURL = url;
//		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				try {
//					PlatformUI.getWorkbench().getBrowserSupport().createBrowser(
//									IWorkbenchBrowserSupport.AS_EDITOR,
//									"myIdEtiquette",
//									nameOnglet
//									//+ codeFacture,
//									,"").openURL(new URL(finalURL));
//				} catch (PartInitException e) {
//					logger.error("", e);
//				} catch (MalformedURLException e) {
//					logger.error("", e);
//				}
//			}
//			
//		});
		
		AffichageEdition ae = new AffichageEdition();
		ae.showEditionDynamiqueDefaut(pathFileEtiquette,nameOnglet,ConstEdition.FORMAT_PDF,false);
		
//		Job job = new Job("Préparation de l'impression") {
//			protected IStatus run(IProgressMonitor monitor) {
////				final int ticks = finalIdFactureAImprimer.length;
//				monitor.beginTask("Préparation de l'impression",monitor.UNKNOWN);
//				try {
//					LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//					try {
//						PlatformUI.getWorkbench().getBrowserSupport().createBrowser(
//										IWorkbenchBrowserSupport.AS_EDITOR,
//										"myId",
//										nameOnglet
//										//+ codeFacture,
//										,"").openURL(new URL(finalURL));
//					} catch (PartInitException e) {
//						logger.error("", e);
//					} catch (MalformedURLException e) {
//						logger.error("", e);
//					}
//				} catch (Exception e) {
//					logger.error("", e);n
//				}
//				finally {
//					monitor.done();
//				}
//				return Status.OK_STATUS;
//			}
//		};
//		job.setPriority(Job.SHORT);
//		//job.setUser(true);
//		job.schedule(); 
//		try {
//			job.join();
//		} catch (InterruptedException e) {
//			logger.error("Erreur à l'impression ",e);
//		}
	}
	
	public void updateValuePreference(float leftMargin,float rightMargin,float topMargin,float bottomMargin,
			float leftPadding,float rightPadding,float topPadding,float bottomPadding,
			int nombreColumns,int nombreRows,float largeurEspace,float hauteurEspace,
			boolean cellBorder){
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setValue(PreferenceConstants.LEFT_MARGIN,leftMargin);
		store.setValue(PreferenceConstants.RIGHT_MARGIN,rightMargin);
		store.setValue(PreferenceConstants.TOP_MARGIN,topMargin);
		store.setValue(PreferenceConstants.BOTTOM_MARGIN,bottomMargin);
		
		store.setValue(PreferenceConstants.LEFT_PADDING,leftPadding);
		store.setValue(PreferenceConstants.RIGHT_PADDING,rightPadding);
		store.setValue(PreferenceConstants.TOP_PADDING,topPadding);
		store.setValue(PreferenceConstants.BOTTOM_PADDING,bottomPadding);
		
		store.setValue(PreferenceConstants.ROWS_ETIQUETTE,nombreColumns);
		store.setValue(PreferenceConstants.COLUMS_ETIQUETTE,nombreRows);
		
		store.setValue(PreferenceConstants.LARGEUR_ESPACE,largeurEspace);
		store.setValue(PreferenceConstants.HAUTEUR_ESPACE,hauteurEspace);
		
		store.setValue(PreferenceConstants.CELL_BORDER,cellBorder);
		
		Plugin plugin = Activator.getDefault();
		plugin.savePluginPreferences();
		
	}
	
	/**
	 * 
	 * @param nombreColumns ==> 
	 * @param nombreLineFine ==> nombre de ligne dans file d'extraction
	 * @return
	 */
	public int getNombreRowsLables(int nombreRows,int nombreColumns,int nombreLineFine){
		float floatNombreLineFine = (float)(nombreLineFine-1);
		float floatnombreColumns = (float)nombreColumns;
		
		BigDecimal decimal = new BigDecimal(floatNombreLineFine/floatnombreColumns).setScale(0, BigDecimal.ROUND_UP);
		if(decimal.intValue()<nombreRows){
			return nombreRows;
		}else{
			return decimal.intValue();
		}
		
	}
	
	public float convertCmToInch(Float valueCm){
		float value = (float)(valueCm*0.39);
		BigDecimal valueBigDecimal = new BigDecimal(value).setScale(3, BigDecimal.ROUND_DOWN);
		//BigDecimal valueBigDecimal = new BigDecimal(value);
		return valueBigDecimal.floatValue();
	}
	
	public float convertCmToPoints(Float valueCm){
		float value = (float)(valueCm*ConstantModelLabels.ONE_POINTS);
		BigDecimal valueBigDecimal = new BigDecimal(value).setScale(3, BigDecimal.ROUND_DOWN);
		//BigDecimal valueBigDecimal = new BigDecimal(value);
		return valueBigDecimal.floatValue();
	}
	

	/**
	 * 
	 * @param value ==> string de la fromat de l'etiquette 
	 * @return ==> un text sous fromat de  Birt 
	 */
	public String convertStringToBirtHtml(String value){
		valueFormatEtiquetteBirtHtml = "";
		valueFormatEtiquetteBirtHtml = value.replaceAll("\n", "<BR>");
		return valueFormatEtiquetteBirtHtml;
	}
	
	/**
	 * Retourne la liste des fichiers XML contenant des paramétrages d'étiquettes présent dans le répertoire
	 * passé en paramètre.
	 * 
	 * @param pathFolder ==> path of folder parametre etiquette
	 * EX : /home/lee/runtime-GestionCommercialeComplet.product/2432009/parametresEtiquette
	 * @return
	 */
	public String[] arrayPathFileParamEtiquette(String pathFolder, String[] ajoutDebut, String[] ajoutFin){
		
		mapFileParamEtiquette.clear();
		File folder = new File(pathFolder);
		FilenameFilter filter = new TypeFileXmlFilter();
		File[] arrayFileParamEtiquette = folder.listFiles(filter);
		//String[] arrayNameFileParamEtiquette = new String[arrayFileParamEtiquette.length];
		int nbElemSupp = 0;
		if(ajoutDebut!=null)
			nbElemSupp += ajoutDebut.length;
		if(ajoutFin!=null)
			nbElemSupp += ajoutFin.length;
		String[] arrayNameFileParamEtiquette = new String[arrayFileParamEtiquette.length+nbElemSupp];
		
		int cpt = 0;
		if(ajoutDebut!=null) {
			for (int i = 0; i < ajoutDebut.length; i++) {
				arrayNameFileParamEtiquette[cpt] = ajoutDebut[i]; 
				cpt++;
			}
		}
		//arrayNameFileParamEtiquette[0] = ConstantModelLabels.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE;
		//arrayNameFileParamEtiquette[1] = ConstantModelLabels.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE;
		
		for (int i = 0; i < arrayFileParamEtiquette.length; i++) {
			int position = arrayFileParamEtiquette[i].getName().indexOf(".");
			String nameFileParamEtiquette = arrayFileParamEtiquette[i].getName().substring(0, position);
			String pathFileParamEtiquette = arrayFileParamEtiquette[i].getAbsolutePath();
			mapFileParamEtiquette.put(nameFileParamEtiquette, pathFileParamEtiquette);
			arrayNameFileParamEtiquette[cpt] = nameFileParamEtiquette;
			cpt++;
		}
		
		if(ajoutFin!=null) {
			for (int i = 0; i < ajoutFin.length; i++) {
				arrayNameFileParamEtiquette[cpt] = ajoutFin[i]; 
				cpt++;
			}
		}
		//return folder.list(filter);
		return arrayNameFileParamEtiquette;
	}
	
	public String[] arrayPathFileParamEtiquette(String pathFolder){
		return arrayPathFileParamEtiquette(pathFolder,new String[]{ConstantModelLabels.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE},null);
	}
	
	private class TypeFileXmlFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String nameFile) {
			//return false;
			return nameFile.endsWith(".xml");
			//return nameFile.endsWith(".dat");
		}
		
	}
	
	/*==================================================================================================*/
	
	/**
	 * 
	 * @param pathFileName
	 * @param object
	 */
	public void writeObjectSerializable(Object object,String pathFileName){
		ObjectOutputStream out = null;
		try {
			FileOutputStream fos = new FileOutputStream(pathFileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(object);
			fos.close();
			out.close();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public Object readObjetSerializable(String pathFileName){
		Object obj = null;
		ObjectInputStream in = null;

		try {
			FileInputStream fis = new FileInputStream(pathFileName);
			in = new ObjectInputStream(fis);
			obj = in.readObject();
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return obj;
	}
	
	/*==================================================================================================*/
	public void writeObject(ParameterEtiquette parameterEtiqutte ,String fileName){
		try {
//			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//			XMLEncoder encoder = new XMLEncoder(bufferedOutputStream);
//			//XMLEncoder encoder = new XMLEncoder(fileOutputStream);
//			encoder.writeObject(parameterEtiqutte);
//			encoder.flush();
//			encoder.close();
//			bufferedOutputStream.close();
//			fileOutputStream.close();
			
		} 
//		catch (FileNotFoundException e1) {
//			logger.error("", e1);
//		}
		catch (Exception e1) {
			logger.error("", e1);
		}
	}
	
	public ParameterEtiquette readObjet(String filename){
//	//public Object readObjet(String filename){
//
//		ParameterEtiqutte o = null;
//		XMLDecoder decoder = null;
////		ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();   
//		FileInputStream fileInputStream = null;
//		BufferedInputStream bufferedInputStream = null;
//		try {
//			fileInputStream = new FileInputStream(filename);
//			bufferedInputStream = new BufferedInputStream(fileInputStream);
//			//FileInputStream fos = new FileInputStream(filename);
//			//decoder = new XMLDecoder(fos);
//			decoder = new XMLDecoder(bufferedInputStream);
////			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutPutStream.toByteArray());   
////			decoder = new XMLDecoder(byteInputStream);   
//			o = (ParameterEtiqutte)decoder.readObject();
//			decoder.close();
//			bufferedInputStream.close();
//			fileInputStream.close();
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		
//		//Object o = decoder.readObject();
//		
//		return o;
        XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
        //Foo o = (Foo)decoder.readObject();
        ParameterEtiquette o = (ParameterEtiquette)decoder.readObject();
        decoder.close();
      
        return o;
	}

	/************************ Now Use ***************************/
	public void writeObjectCastor(ParameterEtiquette parameterEtiqutte ,String fileName){
		try {
			FileWriter writer = new FileWriter(fileName);
			Marshaller.marshal(parameterEtiqutte, writer);
			writer.close();
		} catch (Exception e) {
			logger.error("", e);
			Shell shell =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			
			MessageDialog.openError(shell, ConstantModelLabels.MESSAGE_ERROR_TITLE_NAME_ETIQUETTE, 
					ConstantModelLabels.MESSAGE_ERROR_INFOS_NAME_ETIQUETTE);
		}
		
	}
	
	public ParameterEtiquette readObjectCastor(String fileName){
		ParameterEtiquette parameterEtiqutte = null;
		try {
			FileReader reader = new FileReader(fileName);
			parameterEtiqutte = (ParameterEtiquette)Unmarshaller.unmarshal(ParameterEtiquette.class,reader);
		} catch (Exception e) {
			logger.error("", e);
		}
		return parameterEtiqutte;
	}
	
	/*==================================================================================================*/

	public ParameterEtiquette updateProprityObject(String sizeEtiquette,boolean isGras,boolean isItalic,
												  String nameParamEtiquette,String pathFileParamEtiquette,String textModelEtiquette,
												  ParameterEtiquette parameterEtiqutte){
		
		parameterEtiqutte.setSizeEtiquette(sizeEtiquette);
		parameterEtiqutte.setGras(isGras);
		parameterEtiqutte.setItalic(isItalic);
		parameterEtiqutte.setNameParameterEtiqutte(nameParamEtiquette);
		parameterEtiqutte.setPathFileParamEtiquette(pathFileParamEtiquette);
		parameterEtiqutte.setTextModelEtiquette(textModelEtiquette);
		return parameterEtiqutte;
	}
	
	public ParameterEtiquette updateProprityObject(boolean cellBorder,
			Float leftMargin,Float rightMargin,Float topMargin,
			Float bottomMargin,Float leftPadding,Float rightPadding,Float topPadding,Float bottomPadding,
			Float largeurPapier,Float hauteurPapier,Float largeurEspace,Float hauteurEspace,String pageSize,
			Integer nombreColumns,Integer nombreRows,String pathFileExtraction,String textModelEtiquette,
			String pathFileParamEtiquette,String pathFileMotCle,boolean choixFileMotCle,
			String valueSeparateur,boolean choixSeparateur,ParameterEtiquette parameterEtiqutte){
		
		//parameterEtiqutte.setNameParameterEtiqutte(newParamEtiquette);
		parameterEtiqutte.setCellBorder(cellBorder);
		
		parameterEtiqutte.setLeftMargin(leftMargin);
		parameterEtiqutte.setRightMargin(rightMargin);
		parameterEtiqutte.setTopMargin(topMargin);
		parameterEtiqutte.setBottomMargin(bottomMargin);
		
		parameterEtiqutte.setLeftPadding(leftPadding);
		parameterEtiqutte.setRightPadding(rightPadding);
		parameterEtiqutte.setTopPadding(topPadding);
		parameterEtiqutte.setBottomPadding(bottomPadding);
		
		parameterEtiqutte.setLargeurPapier(largeurPapier);
		parameterEtiqutte.setHauteurPapier(hauteurPapier);
		
		parameterEtiqutte.setLargeurEspace(largeurEspace);
		parameterEtiqutte.setHauteurEspace(hauteurEspace);
		
		parameterEtiqutte.setTypePaper(pageSize);
		
		parameterEtiqutte.setNombreRows(nombreRows);
		parameterEtiqutte.setNombreColumns(nombreColumns);
		
		parameterEtiqutte.setTextModelEtiquette(textModelEtiquette);
		
		parameterEtiqutte.setPathFileParamEtiquette(pathFileParamEtiquette);
		
//		parameterEtiqutte.setFlagChoixPreDefinionModelLables(flagChoixPreDefinionModelLables);
		
		
		parameterEtiqutte.setPathFileExtraction(pathFileExtraction);
		
		parameterEtiqutte.setPathFileMotCle(pathFileMotCle);
		parameterEtiqutte.setChoixMotCle(choixFileMotCle);
		
		parameterEtiqutte.setValueSeparateur(valueSeparateur);
		parameterEtiqutte.setChoixSeparateur(choixSeparateur);
		
		return parameterEtiqutte;
	}
	
	public List<String> getAllLinesFileExtraction() {
		return allLinesFileExtraction;
	}

	public String getOneLineFileExtraction() {
		return oneLineFileExtraction;
	}

	public void setOneLineFileExtraction(String oneLineFileExtraction) {
		this.oneLineFileExtraction = oneLineFileExtraction;
	}

	public WizardController getWizardController() {
		return wizardController;
	}

	public void setWizardController(WizardController wizardController) {
		this.wizardController = wizardController;
	}

	public float getFloatWidthColEtiquette() {
		return floatWidthColEtiquette;
	}

	public void setFloatWidthColEtiquette(float floatWidthColEtiquette) {
		this.floatWidthColEtiquette = floatWidthColEtiquette;
	}

	public float getFloatHeightColEtiquette() {
		return floatHeightColEtiquette;
	}

	public void setFloatHeightColEtiquette(float floatHeightColEtiquette) {
		this.floatHeightColEtiquette = floatHeightColEtiquette;
	}

	public String getMotCleEtiquette() {
		return motCleEtiquette;
	}

	public void setMotCleEtiquette(String motCleEtiquette) {
		this.motCleEtiquette = motCleEtiquette;
	}

	public String getStartContentGridReport() {
		return startContentGridReport;
	}

	public void setStartContentGridReport(String startContentGridReport) {
		this.startContentGridReport = startContentGridReport;
	}

	public List<String> getNameChampExtractionEtiquette() {
		return nameChampExtractionEtiquette;
	}

	public void setNameChampExtractionEtiquette(
			List<String> nameChampExtractionEtiquette) {
		this.nameChampExtractionEtiquette = nameChampExtractionEtiquette;
	}

	public List<String> getListNameMotcleEtiquette() {
		return listNameMotcleEtiquette;
	}

	public void setListNameMotcleEtiquette(List<String> listNameMotcleEtiquette) {
		this.listNameMotcleEtiquette = listNameMotcleEtiquette;
	}

	public Map<Integer, String> getMotCleEtiquetteEtPosition() {
		return motCleEtiquetteEtPosition;
	}

	public void setMotCleEtiquetteEtPosition(
			Map<Integer, String> motCleEtiquetteEtPosition) {
		this.motCleEtiquetteEtPosition = motCleEtiquetteEtPosition;
	}

	public String getValueFormatEtiquetteBirtHtml() {
		return valueFormatEtiquetteBirtHtml;
	}

	public void setValueFormatEtiquetteBirtHtml(String valueFormatEtiquetteBirtHtml) {
		this.valueFormatEtiquetteBirtHtml = valueFormatEtiquetteBirtHtml;
	}

	public List<String> getAllLinesFileExtractionFormatBirt() {
		return allLinesFileExtractionFormatBirt;
	}

	public void setAllLinesFileExtractionFormatBirt(
			List<String> allLinesFileExtractionFormatBirt) {
		this.allLinesFileExtractionFormatBirt = allLinesFileExtractionFormatBirt;
	}

	public Map<String, String> getMapFileParamEtiquette() {
		return mapFileParamEtiquette;
	}

	public void setMapFileParamEtiquette(Map<String, String> mapFileParamEtiquette) {
		this.mapFileParamEtiquette = mapFileParamEtiquette;
	}
}
