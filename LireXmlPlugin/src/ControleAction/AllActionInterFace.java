package ControleAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import Interface.AfficheAllRelation;
import Interface.AjouteElemnet;
import Interface.ConstProjet;
import Interface.InterFaceMains;

public class AllActionInterFace {
	
	AjouteElemnet objetAjouteElemnet;
	InterFaceMains objetInterFaceMains;
	String PathFileChooser;
	String PathFileProduct;
	SAXBuilder sxb = new SAXBuilder();;
	Document document;
	Element racine;
	private String qualifier;
	
	/*
	 * mapFeaturesEclipse is for features of eclipse
	 * mapFeaturesBG is for feature of eclipse
	 */
	private Map<String, File> mapAllFeaturesEclipse = new HashMap<String, File>();
	private Map<String, File> mapAllFeaturesBG = new HashMap<String, File>();
	
	private Map<String, String> mapFeatures = new HashMap<String, String>();
	private Map<String, File> mapAllFeatures = new HashMap<String, File>();
	List<String> listTable = new ArrayList<String>();
	
	List<String> NameColTaListFaeture = new ArrayList<String>();
	List<String> NameColTaListPlugin = new ArrayList<String>();
	List<String> NameColTaDepFaeture = new ArrayList<String>();
	List<String> NameColTaDepFaeturePlugin = new ArrayList<String>();
	
	Map<String, Integer> mapNameColTaListFaeture = new HashMap<String, Integer>();
	Map<String, Integer> mapNameColTaListPlugin = new HashMap<String, Integer>();
	Map<String, Integer> mapNameColTaDepFaeture = new HashMap<String, Integer>();
	Map<String, Integer> mapNameColTaDepFaeturePlugin = new HashMap<String, Integer>();
	
	String PartSqlTaListFeature = null;
	String PartSqlTaListPlugin = null;
	String PartSqlTaDepFeature = null;
	String PartSqlTaDepFeaturePlugin = null;
	
	int returnVal;

	public String updateTextField(JTextField textField){
		String pathFeature = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle(ConstProjet.NAME_TITLE_FILE_CHOOSER_FEATURE);
		returnVal =fileChooser.showOpenDialog(objetInterFaceMains);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			
			pathFeature = fileChooser.getSelectedFile().toString();
			textField.setText(pathFeature);
			
		}
		return pathFeature;
	
	}
	/**
	 * for find all of files features
	 * @param objetInterFaceMains
	 */
	public void allNewActionInterface(final InterFaceMains objetInterFaceMains){
		objetInterFaceMains.getJButtonPath1().addActionListener(new ActionListener(){

			/**
			 * all of features (Eclipse)
			 * dirtory of eclipse 
			 */
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String featurePath1 = updateTextField(objetInterFaceMains.getJTextFieldPath1());
				if(featurePath1!=null){
					addAllMapFeature(new File(featurePath1),ConstProjet.TYPE_FEATURE_ECLIPSE);
				}
			}
		});
		/**
		 * all of features (workspace)
		 */
		objetInterFaceMains.getJButtonPath2().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String featurePath2 = updateTextField(objetInterFaceMains.getJTextFieldPath2());
				if(featurePath2!=null){
					addAllMapFeature(new File(featurePath2),ConstProjet.TYPE_FEATURE_BG);
				}
			}
		});
		
	}
	public void actionValiderEtAnnuler(final InterFaceMains objetInterFaceMains){
		objetInterFaceMains.getJButtonValider().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				remplirDB();
			}
			
		});
		objetInterFaceMains.getJButtonAnnuler().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				exitProgramma();
			}
			
		});
	}
	public void exitProgramma(){
		System.exit(0);
	}
	public void remplirDB(){
		AfficheAllRelation AAR = new AfficheAllRelation();
		AAR.setQualifier(qualifier);
		AAR.newAffichageRelation(mapAllFeaturesEclipse,mapAllFeaturesBG);
		
		Connection conn = ControlConnection.makeConnection(ConstProjet.MYSQL_DB, ConstProjet.NAME_DB);
		
		listTable.add(ConstProjet.TA_LIST_FEATURES);
		listTable.add(ConstProjet.TA_LIST_PLUGINS);
		listTable.add(ConstProjet.TA_DEP_FEATURE);
		listTable.add(ConstProjet.TA_DEP_FEATURES_PLUGINS);
		
		for (String nametable : listTable) {
			checkNameTable(conn,nametable);
			getNameCol(conn,nametable);
		}
		
		getPartSql(NameColTaListFaeture,ConstProjet.TA_LIST_FEATURES);//INSERT INTO TA_LIST_FEATRUES(ID_FEATURE,NOM_FEATURE,VERSION_FEATURE) VALUES (?,?,?)
		getPartSql(NameColTaListPlugin,ConstProjet.TA_LIST_PLUGINS);
		getPartSql(NameColTaDepFaeture,ConstProjet.TA_DEP_FEATURE);
		getPartSql(NameColTaDepFaeturePlugin,ConstProjet.TA_DEP_FEATURES_PLUGINS);
			
		insertTable(conn,listTable,AAR);
	}
	
	public AllActionInterFace(){
		
	}
	public AllActionInterFace(AjouteElemnet objetAjouteElemnet,final InterFaceMains objetInterFaceMains) {
		super();
		this.objetAjouteElemnet = objetAjouteElemnet;
		this.objetInterFaceMains = objetInterFaceMains;
		objetAjouteElemnet.getMenuFileItem().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle(ConstProjet.NAME_TITLE_FILE_CHOOSER);
				returnVal =fileChooser.showOpenDialog(objetInterFaceMains);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					PathFileChooser = fileChooser.getSelectedFile().toString();
					PathFileProduct = PathFileChooser+File.separator+ConstProjet.NAME_FILE_PRODUCT;
					System.out.println(PathFileProduct);
//					sxb = new SAXBuilder();
//					try {
//						document = sxb.build(new File(PathFileProduct));
//					} catch (JDOMException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					racine = document.getRootElement();
//					getAllFeatures();
					
					/**
					 * analyse XML par JAVA(SAXParserFactory)
					 */
					String ParentPathFileChooser = new File(PathFileChooser).getParent();
					AllMapFeature(new File(ParentPathFileChooser));
					//etFeatures(new File(PathFileProduct));
					//Connection conn = ControlConnection.makeConnection(ConstProjet.MYSQL_DB, ConstProjet.NAME_DB);

					//System.out.println(PartSqlTaListFaeture);
					AfficheAllRelation AAR = new AfficheAllRelation();
					//AAR.setConn(conn);
					AAR.affichageRelation(mapAllFeatures);
					
					Connection conn = ControlConnection.makeConnection(ConstProjet.MYSQL_DB, ConstProjet.NAME_DB);
					
					listTable.add(ConstProjet.TA_LIST_FEATURES);
					listTable.add(ConstProjet.TA_LIST_PLUGINS);
					listTable.add(ConstProjet.TA_DEP_FEATURE);
					listTable.add(ConstProjet.TA_DEP_FEATURES_PLUGINS);
					
					/**
					 * chech out tables et create tables 
					 */
					for (String nametable : listTable) {
						checkNameTable(conn,nametable);
						getNameCol(conn,nametable);
					}
					
					getPartSql(NameColTaListFaeture,ConstProjet.TA_LIST_FEATURES);//INSERT INTO TA_LIST_FEATRUES(ID_FEATURE,NOM_FEATURE,VERSION_FEATURE) VALUES (?,?,?)
					getPartSql(NameColTaListPlugin,ConstProjet.TA_LIST_PLUGINS);
					getPartSql(NameColTaDepFaeture,ConstProjet.TA_DEP_FEATURE);
					getPartSql(NameColTaDepFaeturePlugin,ConstProjet.TA_DEP_FEATURES_PLUGINS);
					
										
					
					insertTable(conn,listTable,AAR);
					
							
				}
			}
			
		});
	}
	/**
	 * 
	 * @param conn
	 * @param ListNameTable ==> all of tables in the DB
	 * @param objetAfficheAllRelation 
	 * 
	 */
	public void insertTable(Connection conn,List<String> ListNameTable,AfficheAllRelation objetAfficheAllRelation){
		for (String nameTable : ListNameTable) {
			if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_PLUGINS)){
				for (ClassTaPlugin objetClassTaPlugin : objetAfficheAllRelation.getObjetTaPlugin()) {
					try {
						PreparedStatement PS = conn.prepareStatement(PartSqlTaListPlugin);
						PS.setInt(1, objetClassTaPlugin.getID_PLUGIN());
						PS.setString(2, objetClassTaPlugin.getNOM_PLUGIN());
						PS.setString(3, objetClassTaPlugin.getVERSION_PLUGIN());
						PS.setString(4, objetClassTaPlugin.getOS());
						PS.setString(5, objetClassTaPlugin.getWS());
						PS.setString(6, objetClassTaPlugin.getNL());
						PS.setString(7, objetClassTaPlugin.getARCH());
						PS.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_FEATURES)){
				for (ClassTaFeature objetClassTaFeature : objetAfficheAllRelation.getObjetTaFeature()) {
					try {
						PreparedStatement PS = conn.prepareStatement(PartSqlTaListFeature);
						PS.setInt(1, objetClassTaFeature.getID_FEATURE());
						PS.setString(2, objetClassTaFeature.getNOM_FEATURE());
						PS.setString(3, objetClassTaFeature.getLABLE_FEATURE());
						PS.setString(4, objetClassTaFeature.getVERSION_FEATURE());
						PS.setString(5, objetClassTaFeature.getPROVIDER_NAME());
						PS.setString(6, objetClassTaFeature.getOS());
						PS.setString(7, objetClassTaFeature.getWS());
						PS.setString(8, objetClassTaFeature.getNL());
						PS.setString(9, objetClassTaFeature.getARCH());
						PS.setString(10, objetClassTaFeature.getDESCRIPTION_URL());
						PS.setString(11, objetClassTaFeature.getCOPYRIGHT_URL());
						PS.setString(12, objetClassTaFeature.getLICENSE_URL());
						PS.setString(13, objetClassTaFeature.getUPDATE_LABEL());
						PS.setString(14, objetClassTaFeature.getUPDATE_URL());
						PS.setString(15, objetClassTaFeature.getTYPE_FEATURE());
						PS.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURE)){
				for (ClassTaDepFeature objetClassTaDepFeature : objetAfficheAllRelation.getObjetTaDepFeature()) {
					try {
						PreparedStatement PS = conn.prepareStatement(PartSqlTaDepFeature);
//						PS.setInt(1, objetClassTaDepFeature.getID());
//						PS.setInt(2, objetClassTaDepFeature.getID_FEATURE_BASE());
//						PS.setInt(3, objetClassTaDepFeature.getID_FEATURE_DEP());
						
						PS.setInt(1, objetClassTaDepFeature.getID());
						PS.setString(2, objetClassTaDepFeature.getNOM_FEATURE_BASE());
						PS.setString(3, objetClassTaDepFeature.getNOM_FEATURE_DEP());
						PS.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURES_PLUGINS)){
				for (ClassTaDepFeaturePlugin objetClassTaDepFeaturePlugin : objetAfficheAllRelation.getObjetTaDepFeaturePlugin()) {
					try {
						PreparedStatement PS = conn.prepareStatement(PartSqlTaDepFeaturePlugin);
//						PS.setInt(1, objetClassTaDepFeaturePlugin.getID());
//						PS.setInt(2, objetClassTaDepFeaturePlugin.getID_FEATURE());
//						PS.setInt(3, objetClassTaDepFeaturePlugin.getID_PLUGIN());
						
						PS.setInt(1, objetClassTaDepFeaturePlugin.getID());
						PS.setString(2, objetClassTaDepFeaturePlugin.getNOM_FEATURE());
						PS.setString(3, objetClassTaDepFeaturePlugin.getNOM_PLUGIN());
						PS.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
		public void addAllMapFeature(File file,String typeFeature){
		
		File [] listFolder = file.listFiles();
		for (File file2 : listFolder) {
			String fileXml = file2.toString()+File.separator+ConstProjet.FEATURE_XML;
			File pathFile = new File(fileXml);
			if(pathFile.exists()){
				//System.out.println(pathFile.toString());
				
				try {
					Document document;
					document = sxb.build(pathFile);
					Element racine = document.getRootElement();
					String nom_feature = racine.getAttributeValue(ConstProjet.ID);
					String version_feature = racine.getAttributeValue(ConstProjet.VERSION);
					//System.out.println(nom_feature+"--"+version_feature);
					//mapAllFeatures.put(nom_feature, pathFile.toString());
					if(typeFeature.equalsIgnoreCase(ConstProjet.TYPE_FEATURE_ECLIPSE)){
						mapAllFeaturesEclipse.put(nom_feature, pathFile);
					}
					if(typeFeature.equalsIgnoreCase(ConstProjet.TYPE_FEATURE_BG)){
						mapAllFeaturesBG.put(nom_feature, pathFile);
					}
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
	}
		
	public void AllMapFeature(File file){
		
		File [] listFolder = file.listFiles();
		for (File file2 : listFolder) {
			String fileXml = file2.toString()+File.separator+ConstProjet.FEATURE_XML;
			File pathFile = new File(fileXml);
			if(pathFile.exists()){
				//System.out.println(pathFile.toString());
				
				try {
					Document document;
					document = sxb.build(pathFile);
					Element racine = document.getRootElement();
					String nom_feature = racine.getAttributeValue(ConstProjet.ID);
					String version_feature = racine.getAttributeValue(ConstProjet.VERSION);
					//System.out.println(nom_feature+"--"+version_feature);
					//mapAllFeatures.put(nom_feature, pathFile.toString());
					mapAllFeatures.put(nom_feature, pathFile);
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
	}
	public void getAllFeatures() {

		List listFeatures = racine.getChildren(ConstProjet.FEATURES);
		Iterator i = listFeatures.iterator();
		while (i.hasNext()) {
			Element elementCourant = (Element) i.next();
			System.out.println(elementCourant.getAttributeValue(ConstProjet.ID)+"****");
			
		}
		
	}
//	/**
//	 * 
//	 * @param file is the file of .product in the folder of GestionCommercial
//	 *  then fill in Map<String, String> mapFeatures include all of features.
//	 */
//	public void getFeatures(File file){
//		
//		SAXParserFactory spf = SAXParserFactory.newInstance();
//		SAXParser parser = null;
//		spf.setNamespaceAware(true);
//		spf.setValidating(true);
//		
//		try {
//			parser = spf.newSAXParser();
//			MySAXHandler handler = new MySAXHandler();
//			mapFeatures = handler.getAllFeatures();
//			try {
//				parser.parse(file, handler);
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
	/**
	 * 
	 * @param conn ==> connection
	 * @param nameTable ==> name of table
	 * for check a table exist, if exist ==> clear table
	 * 							whereas ==> create table
	 */
	public void checkNameTable(Connection conn,String nameTable){
		try {
			DatabaseMetaData DMD = conn.getMetaData();
			String [] types = {"TABLE"};
			ResultSet RS = DMD.getTables(null, null, nameTable,types);
			Statement statement = conn.createStatement();

			if(RS.next()){
				String T_N = RS.getString(ConstProjet.TABLE_NAME);//T_N is the name of table
				if(T_N.equalsIgnoreCase(nameTable)){
					//statement.execute("TRUNCATE TABLE "+nameTable);
					statement.execute("delete from "+nameTable);
				}
			}else{
				statement.execute(QuerySql.SqlCreateTables(nameTable));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * according to name of table fill list of name colonne
	 * @param conn
	 * @param nameTable
	 */
	public void getNameCol(Connection conn,String nameTable){
		
		List<String> nameCol = new ArrayList<String>(); 
		Map<String, Integer> MapNameTypeCol = new HashMap<String, Integer>();
		try {
			DatabaseMetaData DMD = conn.getMetaData();
			ResultSet RS = DMD.getColumns(null, null, nameTable, null);
			while (RS.next()) {
				String C_N = RS.getString(ConstProjet.COLUMN_NAME);//C_N is name of colonne
				int D_T = RS.getInt(ConstProjet.DATA_TYPE);//D_T is DATA_TYPE
				
				nameCol.add(C_N);
				MapNameTypeCol.put(C_N, D_T);
			}
			if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_FEATURES)){
				NameColTaListFaeture = nameCol;
				mapNameColTaListFaeture = MapNameTypeCol;
			}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_PLUGINS)){
				NameColTaListPlugin = nameCol;
				mapNameColTaListPlugin = MapNameTypeCol;
			}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURE)){
				NameColTaDepFaeture = nameCol;
				mapNameColTaDepFaeture = MapNameTypeCol;
			}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURES_PLUGINS)) {
				NameColTaDepFaeturePlugin = nameCol;
				mapNameColTaDepFaeturePlugin = MapNameTypeCol;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 *for link all name of table like ID_FEATURE,NOM_FEATURE,VERSION_FEATURE
	 * @param nameCol
	 */
	public void getPartSql(List<String> nameCols,String nameTable){
		int sizeList = nameCols.size();
		String partQuerSql = ""; 
		String part2QuerSql = ""; 
		for (int i = 0; i < sizeList; i++) {
			if(i<(sizeList-1)){
				partQuerSql += nameCols.get(i)+ConstProjet.SYMBOLE;
				part2QuerSql += ConstProjet.SYMBOLE2+ConstProjet.SYMBOLE;
			}
			if(i==(sizeList-1)){
				partQuerSql += nameCols.get(i);
				part2QuerSql += ConstProjet.SYMBOLE2;
			}
		}
		QuerySql objetQuerySql = new QuerySql();
		if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_FEATURES)){
			PartSqlTaListFeature = objetQuerySql.INSERT_TA_FEATRUES+partQuerSql+QuerySql.VALUES+
								   part2QuerSql+ConstProjet.SYMBOLE3;
			System.out.println(PartSqlTaListFeature);
		}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_LIST_PLUGINS)){
			PartSqlTaListPlugin = objetQuerySql.INSERT_TA_PLUGIN+partQuerSql+QuerySql.VALUES+
								   part2QuerSql+ConstProjet.SYMBOLE3;
			System.out.println(PartSqlTaListPlugin);
		}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURE)){
			PartSqlTaDepFeature = objetQuerySql.INSERT_TA_DEP_FEATURE+partQuerSql+QuerySql.VALUES+
								   part2QuerSql+ConstProjet.SYMBOLE3;
			
		}else if(nameTable.equalsIgnoreCase(ConstProjet.TA_DEP_FEATURES_PLUGINS)){
			PartSqlTaDepFeaturePlugin = objetQuerySql.INSERT_TA_DEP_FEATURES_PLUGINS+partQuerSql+QuerySql.VALUES+
								   part2QuerSql+ConstProjet.SYMBOLE3;
		}
		
		
	}
	/**
	 * 
	 * @param valuesTable ==> values of table
	 * @param typeNameCol ==> name of col and type of col  
	 * @param conn ==> connection
	 */
	public void fillTables(HashMap valuesTable,HashMap typeNameCol,Connection conn){
		
		for (Object nameKey : valuesTable.keySet()) {
			Object valueKey = nameKey;
			Object value = valuesTable.get(nameKey);
			
		}
	}

	
	public Map<String, String> getMapFeatures() {
		return mapFeatures;
	}

	public void setMapFeatures(Map<String, String> mapFeatures) {
		this.mapFeatures = mapFeatures;
	}

	public String getPathFileChooser() {
		return PathFileChooser;
	}

	public void setPathFileChooser(String pathFileChooser) {
		PathFileChooser = pathFileChooser;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}


}

	
