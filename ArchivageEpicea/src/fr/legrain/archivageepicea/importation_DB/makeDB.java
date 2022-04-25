package fr.legrain.archivageepicea.importation_DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.firebirdsql.management.FBManager;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

//import fr.legrain.lib.data.BdLgr;

public class makeDB {

	// connectionDB connDB = new connectionDB();
	// Connection Conn = connDB.makeConnectSql(pathDB)
	private boolean boolError = false;
	private boolean visible = true;
	private FBManager fbManager = new FBManager();
	private String DB_SERVER_URL = "localhost";
	private int DB_SERVER_PORT = 3050;
	public String DB_PATH;
	private String DB_USER = "admin";
	private String DB_PASSWORD = "###_PASSWORD_FB_BDG_###";// "masterkey";
	private String JDBC_DRIVER = "org.firebirdsql.jdbc.FBDriver";
	public String DB_NAME;
	private String DEBUT_URL_JDBC = "jdbc:firebirdsql";
	public String querySql = "SELECT * FROM ecritures ";
	public File fileFdb;
	public File fileTxt;// pour stocker path file de l'ogrigne
	public final String tableEcritures = "Ecritures";
	public final String tableHTva = "HTva";
	public final String tablePieces = "Pieces";
	public final String tablePlanAux = "PlanAux";
	public final String tablePlanCpt = "PlanCpt";
	public final String tablePointage = "Pointage";
	public final String tableResteDC = "ResteDC";
	public final String tableBalance = "balance";
	public final String tableBalanceTiers = "balanceTiers";
	public String pathDB;
	public List<String> nomTables = new ArrayList<String>();
	public List<String> typeCol = new ArrayList<String>();
	private List<String> nameCol = new ArrayList<String>();
	private List<String> megaArray = new ArrayList<String>();

	public String lectureLigne = null;
	public Map<Integer, String[]> contener = new TreeMap<Integer, String[]>();
	public Map<String, Map<Integer, String[]>> map = new TreeMap<String, Map<Integer, String[]>>();

	public BufferedReader br;
	public Boolean bddExiste = false;
	public Boolean infoExiste = false;
	public Shell shellC = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getShell();

	// 1ere ligne des fichiers textes
	public Map<Integer, String> contenerEntete = new TreeMap<Integer, String>();
	// public Map<String,Map<Integer,String>> mapEntete = new
	// TreeMap<String,Map<Integer,String>>();
	// public Map<String,List<String>> mapEntete = new
	// TreeMap<String,List<String>>();
	public Map<String, String> mapEntete = new TreeMap<String, String>();

	public void getChargeTxt() throws IOException {
		String nomTable = null;

		File[] filesTxt = fileTxt.listFiles();

		for (int i = 0; i < fileTxt.listFiles().length; i++) {

			String SEPARATEUR = ";";
			contener = new TreeMap<Integer, String[]>();
			int count = 0;
			// Ajout de toutes les tables dans nom_table
			// On élimine infodossier
			if (!filesTxt[i].getName().contains("infodossier")) {
				// Si le fichier contient _ on le place dans nom table (fichier _Epuree)
				if (filesTxt[i].getName().indexOf("_") != -1) {
					nomTable = filesTxt[i].getName().substring(0,
							filesTxt[i].getName().indexOf("_"));
					// si contient - (plan aux et balances)
				}else if(filesTxt[i].getName().indexOf("-")!=-1){
					// Si c'est une balance
					if (filesTxt[i].getName().contains("balance")){
						nomTable = filesTxt[i].getName().substring(0,
								(filesTxt[i].getName().indexOf("-")+5)).replace("-", "_");
						// Si c'est un plan aux ou cpt
					}else{
						nomTable = filesTxt[i].getName().substring(0,
								filesTxt[i].getName().indexOf("-"));
					}
				}
					
				
				
//				if (indexChar == -1) {
//					indexChar = filesTxt[i].getName().indexOf("-");
//					if (indexChar == -1) {
//						indexChar = filesTxt[i].getName().indexOf(".");
//					} else {
//						if (filesTxt[i].getName().contains("balance"))
//							indexChar = indexChar + 5;
//
//					}
					String ligne;
					String lligne = null;
					try {
						// lecteurAvecBuffer = new BufferedReader
						// (new FileReader(filesTxt[i]));
						String encoding = "ISO-8859-1";
						FileInputStream fis = new FileInputStream(filesTxt[i]);
						InputStreamReader isr = new InputStreamReader(fis,
								encoding);
						br = new BufferedReader(isr);
						ligne = br.readLine();

						int k = 0;
						String[] retour = null;

						while (ligne != null) {
							// si il y a une apostrophe, la convertir en
							// @apostrophe@
							int ind = ligne.indexOf("'");
							if (ind != -1) {
								ligne = ligne.replace("'", "@apostrophe@");
								// System.out.println(ligne);
							}

							if (ligne.endsWith(";")) {
								ligne += "@FinListe;";
							}
							retour = ligne.split(SEPARATEUR, 0);
							count++;
							if (count == 1) {
								lligne = ligne.replace(";", ",");
								// contenerEntete.put(k, lligne);
								// megaArray.add(lligne);
							} else {
								k++;
								// System.out.println(nomTable + ligne);
								contener.put(k, retour);
								// System.out.println(contener);
							}
							ligne = br.readLine();
						}
						br.close();
						isr.close();
						fis.close();
					} catch (FileNotFoundException exc) {
						System.out.println("Erreur d'ouverture");
					}
					map.put(nomTable, contener);
					// Ici mettre les entêtes
					// mapEntete.put(nomTable, contenerEntete);
					// mapEntete.put(nomTable, megaArray);
					mapEntete.put(nomTable, lligne);
			//	}
			}

		}

	}

	/*
	 * pour verifier les contenus de DB si - il y a des donnÃ©es qui sont meme
	 * return => true sinon return => false
	 */
	public boolean selectSql(Connection conn) {

		// Connection Conn = makeConnectSql();
		Statement stmt = null;
		String typeColonne = null;
		for (String key : map.keySet()) {

			DatabaseMetaData dmd;
			try {

				dmd = conn.getMetaData();
				ResultSet resultat = dmd.getColumns(conn.getCatalog(), null,
						key, "%");
				ResultSetMetaData rsmd = resultat.getMetaData();
				typeCol.clear();
				if (resultat.next() == true) {
					/**
					 * le type de la premier colonne de chaque table typeColonne
					 */
					typeColonne = resultat.getObject(6).toString();
				}
				// String rqt = "SELECT "+nameColonne+" FROM "+key;
				String rqt = "SELECT * FROM " + key;
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(rqt);

				int numeroLigne = 1;

				while (rs.next() && !infoExiste) {
					// while(rs.next()){
					if (typeColonne.equalsIgnoreCase("INTEGER")) {
						String resultSqlInt = String.valueOf(rs.getInt(1));
						for (Integer i : map.get(key).keySet()) {
							// System.out.println(map.get(key).get(i)[0]);
							if (map.get(key).get(i)[0].equals(resultSqlInt)) {
								infoExiste = true;
								// System.out.println("found!!");
								break;
							} else {
								break;
							}
						}

					}
					if (typeColonne.equalsIgnoreCase("VARCHAR")) {
						String resultSqlString = rs.getString(1);
						for (Integer i : map.get(key).keySet()) {
							if (map.get(key).get(i)[0].equals(resultSqlString)) {
								infoExiste = true;
								break;
							}
						}
					}
					if (typeColonne.equalsIgnoreCase("FLOAT")) {
						String resultSqlFloat = String.valueOf(rs.getFloat(1));
						for (Integer i : map.get(key).keySet()) {
							if (map.get(key).get(i)[0].equals(resultSqlFloat)) {
								infoExiste = true;
								break;
							}
						}
					}
					// numeroLigne++;
				}
				// System.out.println("out while!!");
				break;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return infoExiste;
	}

	/*
	 * add contenus to tables in DB si il y a donnees qui sont incorrecte, les
	 * donnees seront supprimees, et plus, on vient de creer le DB sera
	 * supprimÃ©
	 * 
	 * si c'est la premiÃ¨re fois de chargement de fichier, la repertoire sera
	 * suprrime aussi sinon, on ne supprime que la DB.
	 * 
	 * avant de supprimer, verifier les tables sont vide,ou pas? si vide, on les
	 * supprime sinon ,
	 */
	public void insertDataTables(Connection conn) throws SQLException {

		int numcols;
		String rqt = "INSERT INTO ";

		// Connection Conn = makeConnectSql();
		conn.setAutoCommit(false);

		Statement stmt = null;

		try {
			for (String key : map.keySet()) {

				// rÃ©cupÃ©ration des types de colonne

				DatabaseMetaData dmd = conn.getMetaData();

				ResultSet resultat = dmd.getColumns(conn.getCatalog(), null,
						key, "%");
				// // affichage des informations
				// ResultSetMetaData rsmd = resultat.getMetaData();
				// typeCol.clear();
				// while (resultat.next()) {
				//
				// // recup du type
				// Object val = resultat.getObject(6);
				//
				// String a = val.toString();
				// // System.out.println(a);
				//
				// typeCol.add(a);
				// }

				for (Integer i : map.get(key).keySet()) {
					// mapEntete.get(key).toString() Contient les noms des
					// champs de la BDD
					String remplace = (mapEntete.get(key).toString()
							.replaceAll("\\bDate\\b", "\"DATE\""));

					// Piece : format incorrect champs QUAND

					rqt = "INSERT INTO ";
					rqt += key + " ";
					rqt += " (" + remplace + ") ";
					rqt += " VALUES ( ";

					stmt = conn.createStatement();
					Iterator<String> it = typeCol.iterator();

					String list = "";
					if (it.hasNext()) {
						list = (String) it.next();
					}
					for (int j = 0; j < map.get(key).get(i).length; j++) {
						// Deb nouveau traitement

						// Date, tous les champs étant une date
						Pattern p = Pattern
								.compile("(\\d{2})/(\\d{2})/(\\d{4})");
						Matcher m = p.matcher(map.get(key).get(i)[j]);
						boolean b = m.matches();
						if (b) {
							map.get(key).get(i)[j] = map.get(key).get(i)[j]
									.replace("/", ".");
						}

						// timestamp -> 29/04/2009 09:55:25
						p = Pattern
								.compile("(\\d{2})/(\\d{2})/(\\d{4})(\\s)(\\d{2}):(\\d{2}):(\\d{2})");
						m = p.matcher(map.get(key).get(i)[j]);
						b = m.matches();
						if (b) {
							map.get(key).get(i)[j] = map.get(key).get(i)[j]
									.replace("/", ".");
							if (it.hasNext()) {
								list = (String) it.next();
							}
						}
						// Small int (vrai et faux)
						p = Pattern.compile("Faux");
						m = p.matcher(map.get(key).get(i)[j]);
						b = m.matches();
						if (b) {
							map.get(key).get(i)[j] = map.get(key).get(i)[j]
									.replace("Faux", "0");
							if (it.hasNext()) {
								list = (String) it.next();
							}
						}
						// Small int (vrai et faux
						p = Pattern.compile("Vrai");
						m = p.matcher(map.get(key).get(i)[j]);
						b = m.matches();
						if (b) {
							map.get(key).get(i)[j] = map.get(key).get(i)[j]
									.replace("Vrai", "1");
							if (it.hasNext()) {
								list = (String) it.next();
							}
						}

						// if ((list.equalsIgnoreCase("DATE"))
						// || (list.equalsIgnoreCase("TIMESTAMP"))) {
						// int inda = map.get(key).get(i)[j].indexOf("/");
						// if (inda != -1) {
						// map.get(key).get(i)[j] = map.get(key).get(i)[j]
						// .replace("/", ".");
						// }
						//
						// if (it.hasNext()) {
						// list = (String) it.next();
						// }
						// // boolean
						// } else if (list.equalsIgnoreCase("SMALLINT")) {
						// if (list.equalsIgnoreCase("SMALLINT")) {
						// int inda = +map.get(key).get(i)[j].toLowerCase()
						// .indexOf("faux");
						// if (inda != -1) {
						// map.get(key).get(i)[j] = map.get(key).get(i)[j]
						// .toLowerCase().replace("faux", "0");
						//
						// }
						// int ind = +map.get(key).get(i)[j].toLowerCase()
						// .indexOf("vrai");
						// if (ind != -1) {
						// map.get(key).get(i)[j] = map.get(key).get(i)[j]
						// .toLowerCase().replace("vrai", "1");
						//
						// }
						//
						// if (it.hasNext()) {
						// list = (String) it.next();
						// }
						// } else {
						if (it.hasNext()) {
							list = (String) it.next();
						}
						// }

						if ((map.get(key).get(i)[j].equals(""))
								|| (map.get(key).get(i)[j].equals("@FinListe"))) {
							rqt += "null";
						}

						else {
							rqt += "'" + map.get(key).get(i)[j] + "'";
						}
						// Fin incompatible
						if (j < map.get(key).get(i).length - 1) {
							rqt += ",";
						} else {
							if (map.get(key).get(i)[j].equals("")) {
								rqt += ",null);";
							} else {
								rqt += ");";
							}

						}
					}
					System.out.println(rqt);
					stmt.execute(rqt);
				}

			}
			conn.commit();
			MessageDialog.openInformation(shellC, "ALTER",
					"L'importation s'est dÃ©roulÃ©e sans problÃ¨mes");

		} catch (SQLException e) {
			e.printStackTrace();

			MessageDialog
					.openError(
							shellC,
							"Alter",
							"le dossier n'est pas correct, veillez le retourner par e-mail à  l'adresse suivante : support@legrain.fr");
			conn.rollback();
			String sql = "SELECT ID_Piece FROM Ecritures";

			Statement statementCheckDB = conn.createStatement();
			ResultSet rs = statementCheckDB.executeQuery(sql);
			boolean isEmpty = true;
			while (rs.next()) {
				isEmpty = false;
			}
			if (isEmpty) {
				// String sqlDrop = "DROP DATABASE "+ nameFire;

				try {
					connectionDB.Deconnection(DB_PATH + "/" + DB_NAME);
					// conn.close();
					fbManager.dropDatabase(DB_PATH + "/" + DB_NAME, DB_USER,
							DB_PASSWORD);
					/*
					 * pour verifier DB_PATH est un repertoire,ou pas? si oui,on
					 * vois que il y a des fichier,ou pas? si il y en a, on ne
					 * fait rien. si il n'y en a rien, on supprime ce repertoire
					 */
					if (new File(DB_PATH).isDirectory()) {
						// File[] arrayFileFireDB = pathFireDB.listFiles();
						if (new File(DB_PATH).listFiles().length == 0) {
							new File(DB_PATH).delete();
							visible = false;
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	public void setValuepathDB() {
		pathDB = DB_PATH + "/" + DB_NAME;
		// pathDB = pathDB.replaceFirst("/C:/","C://");
	}

	public void setValuefileTxt(File ficher) {
		fileTxt = ficher;
	}

	// static Logger logger = Logger.getLogger(BdLgr.class.getName());

	/*
	 * DB_NAME est nom de dossier.55555 for make database
	 */
	public void makeTables() {
		// String pathDB = DB_PATH +"/"+DB_NAME+".FDB";
		File MyFirebied = new File(DB_PATH);
		/*
		 * aprÃ¨s la premiÃ¨re fois le folder exist
		 */
		if (MyFirebied.exists()) {
			File[] filesFdb = MyFirebied.listFiles();
			for (int i = 0; i < filesFdb.length; i++) {
				int indexChar = filesFdb[i].getName().indexOf(".");
				String partName = filesFdb[i].getName().substring(0, indexChar)
						+ ".FDB";// le nom de DB
				/*
				 * si la DB existe, on ne fabrique pas les tables
				 */
				if (partName.equals(DB_NAME)) {
					bddExiste = true;
					break;
				}
				/*
				 * si lA DB n'existe pas
				 */
				else {
					try {

						fbManager.start();
						fbManager.createDatabase(pathDB, DB_USER, DB_PASSWORD);
						// makeDomaineSql(conn);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// addTables();
					// break;
				}

			}

		}
		/*
		 * pour la premiÃ¨re fois charger les fichier de xxx.FDB
		 */
		else {
			// String pathDB = DB_PATH +"/"+DB_NAME+".FDB";//chimein de fdb
			// String pathDB = "/home/lee/Bureau/aaa"
			// +"/"+DB_NAME+".FDB";//chimein de fdb
			fbManager.setServer(DB_SERVER_URL);
			fbManager.setPort(DB_SERVER_PORT);
			try {
				MyFirebied.mkdir();
				/**
				 * change le droit de repertoire
				 */
				Process p;
				if (System.getProperty("os.name").toLowerCase().equals("linux")) {

					try {
						p = Runtime
								.getRuntime()
								.exec(
										new String[] {
												"gksu",
												"chmod 777 "
														+ MyFirebied
																.getAbsolutePath() },
										null,
										new File(MyFirebied.getAbsolutePath()));
						p.waitFor();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {

					fbManager.start();
					fbManager.createDatabase(pathDB, DB_USER, DB_PASSWORD);
					// makeDomaineSql(conn);
					// After you have created the database you can set its
					// default character set with a command like:
					// connection.prepareStatement("UPDATE rdb$database SET rdb$character_set_name='ISO8859_1'").execute();
					// Statement s = connection.createStatement();

				} catch (Exception e) {
					// logger.error("",e);
					// System.out.println();
					e.printStackTrace();
				}

			} catch (SecurityException e) {
				// TODO: handle exception
			}
		}
	}

	public void setValuesNomTables() {

		File[] filesTxt = fileTxt.listFiles();
		// List<String> nomTables = new ArrayList<String>();
		for (int i = 0; i < filesTxt.length; i++) {

			int indexChar = filesTxt[i].getName().indexOf("_");

			if (indexChar == -1) {
				indexChar = filesTxt[i].getName().indexOf("-");
				if (indexChar == -1) {
					indexChar = filesTxt[i].getName().indexOf(".");
				} else {
					if (filesTxt[i].getName().contains("balance"))
						indexChar = indexChar + 5;

				}

			}

			nomTables.add(filesTxt[i].getName().substring(0, indexChar));
		}

	}

	public void addTables(Connection conn) {
		Integer indexChar;
		/**
		 * pour sortir les npm de tables EX => Ecritures_Epurees-11-04-2008.txt
		 * =>Ecritures..
		 */
		setValuesNomTables();

		/**
		 * connecter avec la DB
		 */
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ClassQuerySql stringQuerySql = new ClassQuerySql();
		Statement stmt = null;
		try {
			// stmt = makeConnectSql().createStatement();
			// connectionDB connDB = new connectionDB();
			stmt = conn.createStatement();
			// stmt.execute(stringQuerySql.makeTableHistorique);
			// System.out.println(stringQuerySql.makeTableHistorique);
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String elementNomTables : nomTables) {
			try {

				if (elementNomTables.equals(tableEcritures)) {
					stmt.execute(stringQuerySql.makeTableEcriture);

				} else if (elementNomTables.equals(tableHTva)) {
					stmt.execute(stringQuerySql.makeTableHtva);
				} else if (elementNomTables.equals(tablePieces)) {
					stmt.execute(stringQuerySql.makeTablePieces);
				} else if (elementNomTables.equals(tablePlanAux)) {
					stmt.execute(stringQuerySql.makeTablePlanAux);
				} else if (elementNomTables.equals(tablePlanCpt)) {
					stmt.execute(stringQuerySql.makeTablePlanCpt);
				} else if (elementNomTables.equals(tablePointage)) {
					stmt.execute(stringQuerySql.makeTablePointage);
				} else if (elementNomTables.equals(tableResteDC)) {
					stmt.execute(stringQuerySql.makeTableResteDC);
				} else {
					indexChar = elementNomTables.indexOf("-");
					if (indexChar != -1) {
						String[] array_elementNomTable = elementNomTables
								.split("-");
						System.out.print(array_elementNomTable[0]);
						System.out.print(array_elementNomTable[1]);
						// Si c'est = à balance ou balanceTiers on le fait.
						if (elementNomTables.equals(tableBalance + '-'
								+ array_elementNomTable[1])) {
							// stringQuerySql.set_annee(array_elementNomTable[1]);
							String makeTableBalance = stringQuerySql
									.makeTableBalance(array_elementNomTable[1]);
							stmt.execute(makeTableBalance);
						} else if (elementNomTables.equals(tableBalanceTiers
								+ '-' + array_elementNomTable[1])) {
							// stringQuerySql.set_annee(array_elementNomTable[1]);
							String makeTableBalanceTiers = stringQuerySql
									.makeTableBalanceTiers(array_elementNomTable[1]);
							// stmt.execute(stringQuerySql.makeTableBalanceTiers);
							stmt.execute(makeTableBalanceTiers);
						}

					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addProcedures(Connection conn, String queryProcedure) {
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			stmt = conn.createStatement();
			stmt.execute(queryProcedure);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// //stmt = makeConnectSql().createStatement();
		// //connectionDB connDB = new connectionDB();
		//			
		// // stmt.execute(stringQuerySql.makeTableHistorique);
		// //System.out.println(stringQuerySql.makeTableHistorique);
		// } catch (SQLException e) {
		//			
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public void makeDomaineSql(Connection conn) {
		// Connection Conn = makeConnectSql();
		ClassQuerySql stringQuerySql = new ClassQuerySql();

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt
					.execute("CREATE DOMAIN BOOL AS Smallint DEFAULT 0 CHECK (value between 0 and 1);");
			stmt.execute(stringQuerySql.makeTableHistorique1);
			stmt.execute(stringQuerySql.makeTableHistorique2);
			stmt.execute(stringQuerySql.makeTableHistorique3);
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setValueDB_NAME(String valueName) {
		this.DB_NAME = valueName;

	}

	public void setValueDB_PATH(String valueName) {
		this.DB_PATH = valueName;

	}

	public String getValueDB_NAME() {

		return DB_NAME;
	}

	public boolean isVisible() {
		return visible;
	}

}
