package fr.legrain.archivageepicea.Projet_swt;

import org.eclipse.swt.widgets.Button;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.utilities.WebappAccessor;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.osgi.framework.Bundle;

import fr.legrain.archivageepicea.Activator;
import fr.legrain.archivageepicea.importation_DB.ClassQuerySql;
import fr.legrain.archivageepicea.importation_DB.SelectDB;
import fr.legrain.archivageepicea.importation_DB.VerficationFichier;
import fr.legrain.archivageepicea.importation_DB.connectionDB;
import fr.legrain.archivageepicea.importation_DB.makeDB;
import fr.legrain.archivageepicea.preferences.PreferenceConstants;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.SwtCompositeReport_new;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.libLgrBirt.WebViewerUtil;

public class MoteurInterface {
	private Interface inter = null;
	static Logger logger = Logger.getLogger(MoteurInterface.class.getName());
	// private String pathFileFirebird =
	// "/home/lee/Bureau/Fonctionnement_Ddd/archepi";
	// private String pathFileOpen = "/home/lee/Bureau/Fonctionnement_Ddd";
	// private String pathFileFirebird =
	// "/home/legrain/Bureau/PartageVirtuel/Archivage_epicea/tables/archepi";
	// private String pathFileOpen =
	// "/home/legrain/Bureau/PartageVirtuel/Archivage_epicea/tables";

	/**
	 * These Paths are for stocker file of FDB and a lot of dossiers
	 */
	// private String pathFileFirebird = "C:/Archivage_epicea/archepi";
	// private String pathFileOpen = "C:/Archivage_epicea";
	private String pathFileFirebird = null;
	private String pathFileOpen = null;
	private String pathFileReport = null;
	private String namePlugin = null;

	
	// Erreurs de saisie des champs dans le frm
	private String eDebutCompte = "Le champ \"Du compte\" doit contenir des caracteres numériques ou un tiers (dans ce cas, le champ doit débuter par un +) ";
	private String eFinCompte = "Le champ \"Au compte\" doit contenir des caracteres numériques ou un tiers (dans ce cas, le champ doit débuter par un +) ";
	private String eDebutTiers = "Le champ \"Du compte\" doit contenir  un tiers (le champ doit débuter par un +) ";
	private String eFinTiers = "Le champ \"Au compte\" doit contenir  un tiers (le champ doit débuter par un +) ";
	private String eDebutMontant = "Le champ \"Du montant\" doit contenir des caracteres numériques.";
	private String eFinMontant = "Le champ \"Au montant\" doit contenir des caracteres numériques.";
	private String eVide = "Vous devez renseigner l'un de ces champs. ";
	private String eMontantIniSupMontantFinal = " Le montant \"Au montant\" est inférieur au montant : \"Du Montant\" ";
	/**
	 * on the basis of the file,we make the name of table
	 */
	private static final String pathParametre = "/parametre/ListeChampRecherche.properties";

	private SelectDB selectPathDB = new SelectDB();
	/**
	 * for select a FDB
	 */
	// private List<String> nameFileDB =
	// selectPathDB.nomDossier(pathFileFirebird);
	private List<String> nameFileDB = null;
	private String nameDB = null;
	private Connection conn = null;
	private File reportFile;

	/**
	 * quand double click un ligne de ECRITURES objectLine stock un ligne qui
	 * est Object[]
	 */
	private Object[] objectLine = null;
	/*
	 * les valeurs des cases
	 */
	private String valueDateDebut = null;
	private String valueDateFin = null;
	private String valueCompteDebut = null;
	private String valueCompteFin = null;
	private String valueMontantDebut = null;
	private String valueMontantFin = null;
	private String valueReferenceDebut = null;
	private String valueReferenceFin = null;
	private String valueLibelle = null;
	private String nombreLigne = "10";
	private int nombreLineTable;

	private String EURO = "€";
	private String PERCENT = "%";
	private Map<Integer, Integer> LigneHistorique = new TreeMap<Integer, Integer>();
	private Map<Integer, String> histRqtSql = new TreeMap<Integer, String>();

	/**
	 * distinct E.ID,
	 */
	private String rqt = "SELECT E.*,P.REFERENCE FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece) WHERE ";
	// final String sqlPiecesE =
	// "SELECT P.journal,P.reference,P.compte,P.\"DATE\",P.libelle FROM Pieces P WHERE ID =";
	final String sqlPiecesE = "SELECT * FROM Pieces WHERE ID=";
	private String querySqlEcritures = "SELECT * FROM Ecritures WHERE ID_PIECE=";
	private String sqlEcrituresCompte = "SELECT E.*,P.REFERENCE FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece) WHERE E.COMPTE=";

	/**
	 * 
	 * To make some procedures distinct E.ID,
	 */
	final private String queryDefautProcedureEcriture = "SELECT E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece)";
	private String queryProcedureEcriture = null;

	final private String queryDefautProcedureOngletPiece = "SELECT P.journal,P.reference,P.compte,P.\"DATE\",P.libelle FROM Pieces P WHERE ID =";
	private String queryProcedureOngletPiece = null;
	final private String queryDefautProcedureOngletPieceEcriture = "SELECT E.COMPTE,E.TIERS,E.LIBELLE,E.QT1,E.QT2,E.DEBIT,E.CREDIT FROM ECRITURES E WHERE ID_PIECE=";
	private String queryProcedureOngletPieceEcriture = null;

	final private String queryDefautProcedureOngletCompte = "SELECT P.REFERENCE,E.LIBELLE,E.\"DATE\",E.QT1,E.QT2,E.DEBIT,E.CREDIT FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece) WHERE E.COMPTE=";
	private String queryProcedureOngletCompte = null;
	/**
	 * 
	 * Folds of Report for stocker files of report!
	 */
	private String ReportEcriture = "Ecriture_rechercher";
	private String ReportCompte = "Compte_rechercher";
	private String ReportPiece = "Piece_rechercher";

	public MoteurInterface(Interface inter) {
		this.inter = inter;
		initCheminDossier();
		nameFileDB = selectPathDB.nomDossier(pathFileFirebird);
		initInterface();
	}

	private void initCheminDossier() {
		pathFileOpen = Activator.getDefault().getPreferenceStore().getString(
				PreferenceConstants.P_PATH_ARCHIVAGE_DOSSIER);
		pathFileFirebird = Const.C_RCP_INSTANCE_LOCATION
				+ Const.C_REPERTOIRE_PROJET.replaceFirst("/", "") + "/archepi";
		// pathFileReport = (Const.C_RCP_INSTANCE_LOCATION+
		// Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")).
		// replaceFirst("/C:/", "C://")+"/Edtions";
		pathFileReport = Const.C_RCP_INSTANCE_LOCATION
				+ Const.C_REPERTOIRE_PROJET.replaceFirst("/", "") + "/Editions";

		Path FileReport = new Path(pathFileReport);
		pathFileReport = FileReport.toPortableString();
		// String File_BDD = Const.;
		Path FileFirebird = new Path(pathFileFirebird);
		pathFileFirebird = FileFirebird.toPortableString();

		// pathFileFirebird =
		// "C:/runtime-GestionCommercialeComplet.product/dossier/archepi";
		File foldReport = new File(pathFileReport);
		if (!foldReport.exists()) {
			foldReport.mkdir();
		}
		Bundle bundleCourant = Activator.getDefault().getBundle();
		namePlugin = bundleCourant.getSymbolicName();
		// System.out.println(bundleCourant.getSymbolicName());
	}

	/**
	 * for delete all content in the table of history
	 */
	public void addEffacerHistoriqueAll() {

		inter.getEffacer().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
				Statement stmtt;
				try {
					stmtt = conn.createStatement();
					String rqt = "delete from HISTORIQUE";
					stmtt.execute(rqt);
					stmtt.close();
					addContenuTableHistorique();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * for hide the table of history
	 */
	public void addMasquerHist() {
		inter.getMasquer().addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
				if (inter.getHistorique().isVisible()) {
					inter.getHistorique().setVisible(false);
					inter.getEffacer().setVisible(false);

				} else {
					inter.getHistorique().setVisible(true);
					inter.getEffacer().setVisible(true);
				}
				inter.getHistorique().layout();
				inter.getCompositeLeftbas().layout();
			}
		});
	}

	/**
	 * for delete a line in the table of history when press the key ==> "Suppr"
	 * link action <public void keyPressed(KeyEvent e)>
	 * 
	 * when release a key ==> " a kry" link action <public void
	 * keyPressed(KeyEvent e)>
	 */
	public void addActionHistoriqueDelete() {
		inter.getHistorique().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == 127) {
					Statement stmtt;
					try {
						stmtt = conn.createStatement();
						String rqt = "Delete FROM HISTORIQUE WHERE ID='"
								+ LigneHistorique.get(inter.getHistorique()
										.getSelectionIndex() + 1) + " '";
						stmtt.execute(rqt);
						stmtt.close();
						addContenuTableHistorique();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void addActionCombo() {
		inter.getCombo().addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				inter.getCompositeLeftBottom().setVisible(true);
				inter.getCompositeLeftbas().setVisible(true);
				nameDB = nameFileDB.get(inter.getCombo().getSelectionIndex());
				try {
					conn = connectionDB.makeConnectSql(pathFileFirebird + "/"
							+ nameDB + ".FDB");
					addContenuTableHistorique();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * add a action to the table of history
	 */
	public void addActionHistoriqueClick() {
		inter.getHistorique().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
			}

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				if (inter.getHistorique().getItemCount() != 0) {
					super.mouseDoubleClick(e);

					Statement stmtt;
					Statement stmt;
					try {
						stmtt = conn.createStatement();
						stmt = conn.createStatement();
						String rqt = histRqtSql.get(
								inter.getHistorique().getSelectionIndex() + 1)
								.replace("@@", "\"").replace("Â£Â£", "'");
						final TableOngletResultat tabItem = makeCompositeOngeltTable(
								"ECRITURES", 750, 400, 1);
						ResultSet rs = stmtt.executeQuery(rqt);

						File foldEditionPlugin = new File(pathFileReport + "/"
								+ namePlugin + "/Ecriture_rechercher");
						if (!foldEditionPlugin.exists()) {
							foldEditionPlugin.mkdirs();
						}
						int nombreFileEdition = foldEditionPlugin.listFiles().length;
						/**
						 * for update parametre de edtion
						 */
						if (rqt.indexOf("WHERE") != 0) {
							// queryProcedureEcriture =
							// "SELECT E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE  FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece)";
							queryProcedureEcriture = queryDefautProcedureEcriture;
							queryProcedureEcriture += rqt.substring(rqt
									.indexOf("WHERE"));
							// System.out.println(queryProcedureEcriture);
						}
						/*
						 * pour stocker un ligne ==> Object[] dataLigne ensuite
						 * on met chaque ligne dans la liste: List<Object[]>
						 * data
						 */
						List<Object[]> data = new ArrayList<Object[]>();
						Object[] dataLigne = null;

						data = tabItem.analyseDonnee(rs, data, dataLigne);
						/*
						 * Sert Ã  connaitre la position de la colonne
						 */
						ArrayList<Integer> pos = new ArrayList<Integer>();

						/*
						 * Sert Ã  stocker les noms des colonnes dont on a
						 * besoin
						 */
						List<String> listeTitreChamp1 = tabItem
								.listeTitreChampBd(tabItem.getNomOnglet(),
										reportFile.getAbsolutePath());
						for (String string : listeTitreChamp1) {
							int i = 1;
							boolean trouve = false;
							while (!trouve
									&& i <= rs.getMetaData().getColumnCount()) {
								if (rs.getMetaData().getColumnName(i).equals(
										string)) {
									trouve = true;
								} else {
									i++;
								}
							}
							if (trouve)
								pos.add(i);
						}
						tabItem.setDataTableAll(data, pos, tabItem
								.getOngelEcriture().getTable());

						rs.close();
						stmtt.close();
						ResultSet ra = stmt
								.executeQuery("SELECT * FROM HISTORIQUE");

						while (ra.next()) {
							if (ra.getInt(1) == LigneHistorique.get(inter
									.getHistorique().getSelectionIndex() + 1)) {
								if (ra.getString(3) == null) {
									valueDateDebut = "";
								} else {
									valueDateDebut = ra.getString(3);
								}
								if (ra.getString(4) == null) {
									valueDateFin = "";
								} else {
									valueDateFin = ra.getString(4);
								}

								valueCompteDebut = ra.getString(5);
								valueCompteFin = ra.getString(6);
								valueMontantDebut = ra.getString(7);
								valueMontantFin = ra.getString(8);
								valueReferenceDebut = ra.getString(9);
								valueReferenceFin = ra.getString(10);
								valueLibelle = ra.getString(11);

								tabItem.BasEcritures(
										tabItem.getOngelEcriture(),
										valueDateDebut, valueDateFin,
										valueCompteDebut, valueCompteFin,
										valueMontantDebut, valueMontantFin,
										valueReferenceDebut, valueReferenceFin,
										valueLibelle, nameDB,
										TableOngletResultat.getDebitEtotal(),
										TableOngletResultat.getCreditEtotal());

							}
						}
						ra.close();
						stmt.close();
						tabItem.getOngelEcriture().getTable().addMouseListener(
								new MouseAdapter() {
									@Override
									public void mouseDown(MouseEvent e) {
										// TODO Auto-generated method stub
										super.mouseDown(e);
									}

									@Override
									public void mouseDoubleClick(MouseEvent e) {
										// TODO Auto-generated method stub
										super.mouseDoubleClick(e);
										/*
										 * function de valueLine(tabItem) pour
										 * return ID_PIECE
										 * 
										 * objectLine est la ligne que l'on
										 * choisi
										 */
										int idPieces = Integer
												.parseInt(valueLine(
														tabItem,
														tabItem
																.getOngelEcriture()
																.getTable()));
										ongletPieces(idPieces, tabItem);
									}

									@Override
									public void mouseUp(MouseEvent e) {
										// TODO Auto-generated method stub
										super.mouseUp(e);
									}
								});

						ConstEdition constEdition = new ConstEdition();
						nombreLineTable = constEdition.nombreLineTable(tabItem
								.getTableViewer());
						addActionButtonImprimer(tabItem, foldEditionPlugin,
								nombreFileEdition);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}
		});

	}

	public void addContenuTableHistorique() {
		try {
			inter.getHistorique().clearAll();
			inter.getHistorique().removeAll();

			Statement stmtt;
			stmtt = conn.createStatement();
			String rqa = "SELECT  * FROM HISTORIQUE a  ORDER BY ID DESC rows '"
					+ nombreLigne + "';";
			ResultSet rs = stmtt.executeQuery(rqa);

			// ici, mettre les infos pour remplir la table.

			inter.getHistorique().setHeaderVisible(true);
			inter.getHistorique().setLinesVisible(true);

			String[] nomColonneHistorique = { "requète n°", "Période Début",
					"Période Fin", "Du compte", "Au compte", "Du montant",
					"Au montant", "Référence départ", "Référence fin",
					"libellé" };
			if (inter.getHistorique().getColumns().length == 0) {
				for (int i = 0; i < 10; i++) {
					TableColumn colonne = new TableColumn(
							inter.getHistorique(), SWT.LEFT);
					colonne.setText(nomColonneHistorique[i]);
					colonne.setWidth(150);

				}
			}
			String dateD = null;
			String dateF = null;
			String comD = null;
			String comF = null;
			String monD = null;
			String monF = null;
			String refD = null;
			String refF = null;
			String lib = null;
			int i = 1;
			while (rs.next()) {
				if (rs.getString(3) == null || rs.getString(3).equals("")) {
					dateD = "NC";
				} else {
					dateD = rs.getString(3);
				}

				if (rs.getString(4) == null || rs.getString(4).equals("")) {
					dateF = "NC";
				} else {
					dateF = rs.getString(4);
				}

				if (rs.getString(5).equals("")) {
					comD = "NC";
				} else {
					comD = rs.getString(5);
				}

				if (rs.getString(6).equals("")) {
					comF = "NC";
				} else {
					comF = rs.getString(6);
				}

				if (rs.getString(7).equals("")) {
					monD = "NC";
				} else {
					monD = rs.getString(7);
				}

				if (rs.getString(8).equals("")) {
					monF = "NC";
				} else {
					monF = rs.getString(8);
				}

				if (rs.getString(9).equals("")) {
					refD = "NC";
				} else {
					refD = rs.getString(9);
				}

				if (rs.getString(10).equals("")) {
					refF = "NC";
				} else {
					refF = rs.getString(10);
				}

				if (rs.getString(11).equals("")) {
					lib = "NC";
				} else {
					lib = rs.getString(11);
				}

				LigneHistorique.put(i, rs.getInt(1));
				histRqtSql.put(i, rs.getString(2));

				TableItem tblItem = new TableItem(inter.getHistorique(),
						SWT.NONE);
				tblItem.setText(new String[] { String.valueOf(i), dateD, dateF,
						comD, comF, monD, monF, refD, refF, lib });
				i++;
			}

			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}

	public void addContenuCombo() {
		inter.getCombo().removeAll();
		for (String name : nameFileDB) {
			inter.getCombo().add(name);
		}
	}

	public boolean checkPathFileFdb(List<String> fileFdb) {
		boolean boolCheck = false;
		if (fileFdb.isEmpty()) {
			boolCheck = true;
		}
		return boolCheck;
	}

	public void addActionShell() {
		inter.getShell().addShellListener(new ShellListener() {

			public void shellActivated(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			/**
			 * disconnect link with FDB
			 * 
			 * @param e
			 */
			public void shellClosed(ShellEvent e) {
				// TODO Auto-generated method stub
				if (conn != null) {
					connectionDB.Deconnection(connectionDB.getKeyMap());
				}
				connectionDB.DeconnectAll();

			}

			public void shellDeactivated(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			public void shellDeiconified(ShellEvent e) {
				// TODO Auto-generated method stub

			}

			public void shellIconified(ShellEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * addActionMenu() is the action of Button of btMenu
	 */
	public void addActionMenu() {

		inter.getBtnMenu().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {

				String nomRepertoire;
				DirectoryDialog dialog = new DirectoryDialog(inter.getShell(),
						SWT.OPEN);
				dialog.setFilterPath(pathFileOpen);
				dialog.setMessage("Charger des fichiers");
				nomRepertoire = dialog.open();

				if ((nomRepertoire != null) && (nomRepertoire.length() != 0)) {
					File MyFolder = new File(nomRepertoire);// nomRepertoire=>name
					VerficationFichier VF = new VerficationFichier();
					VF.nomRepertoire = MyFolder;
					// if (VF.verifierNombreFile())// true
					// {
					if (VF.verifierAutreDossier()) {
						try {
							VF.nomDossier = VF.verifierInfoDossier();
							if (VF.nomDossier != null) {
								// System.out.println(VF.nomDossier);
								inter.setCursor(Display.getCurrent()
										.getSystemCursor(SWT.CURSOR_WAIT));

								makeDB objectFireDB = new makeDB();
								objectFireDB.shellC = inter.getShell();
								objectFireDB.setValueDB_NAME(VF.nomDossier);// VF.nomDossier=>
								String parentFolder = MyFolder.getParent();

								objectFireDB.setValueDB_PATH(parentFolder
										+ "/archepi");

								File MyFolderFire = new File(parentFolder);
								objectFireDB.fileFdb = MyFolderFire;
								// objectFireDB.DB_PATH = parentFolder +
								// "/archepi";
								objectFireDB.DB_PATH = pathFileFirebird;
								objectFireDB.DB_NAME = VF.nomDossier + ".FDB";
								objectFireDB.setValuepathDB();
								objectFireDB.setValuefileTxt(MyFolder);
								objectFireDB.getChargeTxt();
								objectFireDB.makeTables();// creer le DB
								Connection connnectSql = connectionDB
										.makeConnectSql(objectFireDB.DB_PATH
												+ "/" + objectFireDB.DB_NAME);

								// On vÃ©rifie que la Bdd n'existe pas
								if (!objectFireDB.bddExiste) {
									objectFireDB.makeDomaineSql(connnectSql);
									// Si elle n'existe pas, on appelle les
									// fonctions suivantes pour creer et
									// remplir la table.
									objectFireDB.addTables(connnectSql);
									/**
									 * for make some procedures
									 */
									objectFireDB
											.addProcedures(
													connnectSql,
													new ClassQuerySql().makeProcedureEcriture);
									objectFireDB
											.addProcedures(
													connnectSql,
													new ClassQuerySql().makeProcedurePiece);
									objectFireDB
											.addProcedures(
													connnectSql,
													new ClassQuerySql().makeProcedurePieceEcriture);
									objectFireDB
											.addProcedures(
													connnectSql,
													new ClassQuerySql().makeProcedureCompte);

									objectFireDB.insertDataTables(connnectSql);
									inter.setCursor(Display.getCurrent()
											.getSystemCursor(SWT.CURSOR_ARROW));
								}
								// Si la base Ã©xiste dÃ©jÃ 
								else {
									if (objectFireDB.selectSql(connnectSql) == true) {
										// System.out
										// .println("Ces valeurs-- sont dÃ©jÃ  prÃ©sentes dans la base de donnÃ©es");
										MessageDialog
												.openError(
														inter.getShell(),
														"ALTER",
														"Ces données sont déjà présentes dans la base de données, l'importation a été annulé!");
									} else {
										System.out.println("not found!!");
										objectFireDB
												.insertDataTables(connnectSql);
									}

								}
								nameFileDB = selectPathDB
										.nomDossier(pathFileFirebird);
								addContenuCombo();
								if (inter.getCompositeLeftTop().isVisible() == false) {

									if (!checkPathFileFdb(nameFileDB)) {
										// addContenuCombo();
										// addActionButtonRecherche();
									}
									if (objectFireDB.isVisible() == true) {
										inter.getCompositeLeftTop().setVisible(
												true);
									}

								}

							}

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					// }else{
					// MessageDialog.openError(inter.getShell(), "ALTER",
					// "Votre dossier n'est pas une épuration Epicea ou est incomplet. Dans ce dernier cas, veillez le retourner par e-mail à l'adresse suivante : support@legrain.fr");
					//
					// }
				}
			}
		});
	}

	/**
	 * add a action to Button rechercher
	 */
	public void addActionButtonRecherche() {
		inter.getButtonRecherche().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Boolean rqtCorrecte = true;
				if (false) {
					System.out.println("I'm here!!");
					String a = "ss";
				} else {
					if (checkContenu()) {
						MessageDialog.openError(inter.getShell(), "",
								eVide);
					} else {
						rqt = "SELECT E.*,P.REFERENCE FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece) WHERE ";
						valueCompteDebut = inter.getTextCompteDebut().getText();
						valueCompteFin = inter.getTextCompteFin().getText();
						valueLibelle = inter.getTextLibelle().getText();
						valueMontantDebut = inter.getTextMontantDebut()
								.getText();
						valueMontantFin = inter.getTextMontantFin().getText();
						valueReferenceDebut = inter.getTextRefernceDebut()
								.getText();
						valueReferenceFin = inter.getTextRefernceFin()
								.getText();

						if (inter.getPeriodeDebut().getSelection() == null) {
							valueDateDebut = null;
						} else {
							valueDateDebut = LibDate.dateToString(inter
									.getPeriodeDebut().getSelection());
						}

						if (inter.getPeriodeFin().getSelection() == null) {
							valueDateFin = null;
						} else {
							valueDateFin = LibDate.dateToString(inter
									.getPeriodeFin().getSelection());
						}

						switch (LibDate.compareTo(valueDateDebut, valueDateFin)) {
						case 1:
							if (valueDateDebut != null && valueDateFin != null) {
								rqt += " E.\"DATE\" like '%' ";
							}
							break;
						case 0:
							if (valueDateDebut != null && valueDateFin != null) {
								rqt += " E.\"DATE\" = '"
										+ valueDateDebut.replace("/", ".")
										+ "' ";
							}
							break;
						case -1:
							if (valueDateDebut != null && valueDateFin != null) {
							
								rqt += " E.\"DATE\" BETWEEN" + "'"
										+ valueDateDebut.replace("/", ".")
										+ "' AND '"
										+ valueDateFin.replace("/", ".") + "'";
							}
							break;
						default:
							break;
						}
						try {
							// construction requÃªte
							// ===> dÃ©but requÃªte
							// Recherche par date, voir aussi dans le switch
							if (valueDateDebut == null && valueDateFin == null) {
								rqt += " E.\"DATE\" like '%' ";
							}

							if (valueDateFin == null && valueDateDebut != null) {
								rqt += " E.\"DATE\" >= '"
										+ valueDateDebut.replace("/", ".")
										+ "'";
							}
							if (valueDateDebut == null && valueDateFin != null) {
								rqt += " E.\"DATE\" <= '"
										+ valueDateFin.replace("/", ".") + "' ";
							}

							// Comptes ou Tiers

							// Si recherche par Tiers
							if (valueCompteDebut.contains("+")
									|| valueCompteFin.contains("+")) {

								if (valueCompteDebut.contains("+")
										&& valueCompteFin.contains("+")) {
									if (valueCompteDebut
											.compareToIgnoreCase(valueCompteFin) <= 0) {
										rqt += "and  E.TIERS BETWEEN '"
												+ valueCompteDebut
														.toUpperCase()
												+ "' AND '"
												+ valueCompteFin.toUpperCase()
												+ "'";
									}
								} else if (valueCompteDebut.isEmpty()
										&& valueCompteFin.contains("+")) {
									rqt += "and E.TIERS <= " + "'"
											+ valueCompteFin.toUpperCase()
											+ '[' + "'";

								} else if (!valueCompteDebut.isEmpty()
										&& valueCompteFin.contains("+")) {
									rqtCorrecte = false;
									MessageDialog
											.openError(inter.getShell(), "",
													eDebutTiers);
								} else if ((valueCompteDebut.contains("+"))
										&& (valueCompteFin.isEmpty())) {
									rqt += "and E.TIERS   >= '"
											+ valueCompteDebut.toUpperCase()
											+ "' ";
									System.out.println(valueCompteDebut);
									System.out.println(valueCompteFin);
								} else if (!valueCompteFin.isEmpty()
										&& valueCompteDebut.contains("+")) {
									rqtCorrecte = false;
									MessageDialog
											.openError(inter.getShell(), "",
													eFinTiers);
								}

							}
							// Si recherche par comptes

							else {
								// mÃ©thode test valeur alphabet ==>
								// VerifieAlphabet()
								// Renvoit true si le string peut se convertir
								// en
								// Double,
								// false s'il ne peut pas (prÃ©sence d'une
								// lettre)

								if (!VerifieAlphabet(valueCompteDebut)
										&& !valueCompteDebut.equals("")) {
									valueCompteDebut = "";
									rqtCorrecte = false;
									MessageDialog
											.openError(
													inter.getShell(),
													"",
													eDebutCompte);
								}

								if (!VerifieAlphabet(valueCompteFin)
										&& !valueCompteFin.equals("")) {
									valueCompteFin = "";
									rqtCorrecte = false;
									MessageDialog
											.openError(
													inter.getShell(),
													"",
													eFinCompte);
								}

								if (!valueCompteDebut.equals("")
										&& !valueCompteFin.equals("")) {
									if (valueCompteDebut
											.compareToIgnoreCase(valueCompteFin) <= 0) {
										rqt += "and  cast(E.COMPTE  AS integer ) BETWEEN '"
												+ valueCompteDebut
												+ "' AND '"
												+ valueCompteFin + "'";
									}
								}
								if (valueCompteDebut.equals("")
										&& !valueCompteFin.equals("")) {
									rqt += "and cast(E.COMPTE  AS integer ) <= "
											+ "'" + valueCompteFin + "'";
								}
								if (valueCompteFin.equals("")
										&& !valueCompteDebut.equals("")) {
									rqt += "and cast(E.COMPTE  AS integer )  >= '"
											+ valueCompteDebut + "' ";
								}
							}

							// Recherche par montant
							if (!VerifieAlphabet(valueMontantDebut)
									&& !valueMontantDebut.equals("")) {
								valueMontantDebut = "";
								rqtCorrecte = false;
								MessageDialog
										.openError(inter.getShell(), "",
												eDebutMontant);
							}
							if (!VerifieAlphabet(valueMontantFin)
									&& !valueMontantFin.equals("")) {
								valueMontantFin = "";
								rqtCorrecte = false;
								MessageDialog
										.openError(inter.getShell(), "",
												eFinMontant	);
							}
							if (!valueMontantDebut.equals("")
									&& !valueMontantFin.equals("")) {

								try {
									
									Double valueFinal = Double.parseDouble(valueMontantFin);
									Double valueIni = Double.parseDouble(valueMontantDebut);
									if(valueFinal < valueIni){
										MessageDialog
										.openError(inter.getShell(), "",
												eMontantIniSupMontantFinal	);
							
									}else{
										rqt += "and  (E.Debit+E.Credit) BETWEEN '"
											+ valueMontantDebut + " ' AND '"
											+ valueMontantFin + "'";
									}
									
								} catch (Exception e2) {
									// TODO: handle exception
									System.err.println(e2);
								}
							}
							if (valueMontantDebut.equals("")
									&& !valueMontantFin.equals("")) {
								rqt += "and (E.Debit+E.Credit) <= '"
										+ valueMontantFin + "'";
							}
							if (valueMontantFin.equals("")
									&& !valueMontantDebut.equals("")) {
								rqt += "and (E.Debit+E.Credit)  >= '"
										+ valueMontantDebut + "' ";
							}
							
						
						
			
							// Recherche par rÃ©fÃ©rence
							if (!valueReferenceDebut.equals("")
									&& !valueReferenceFin.equals("")) {
								if (valueReferenceDebut
										.compareToIgnoreCase(valueReferenceFin) <= 0) {
									rqt += "and P.reference BETWEEN '"
											+ valueReferenceDebut.toUpperCase()
													.replace("*", "%")
											+ "%' AND '"
											+ valueReferenceFin.toUpperCase()
													.replace("*", "%") + "['";
								}
							}
							if (valueReferenceDebut.equals("")
									&& !valueReferenceFin.equals("")) {
								rqt += "and  P.REFERENCE <= '"
										+ valueReferenceFin.toUpperCase()
												.replace("*", "%") + "['";
							}
							if (valueReferenceFin.equals("")
									&& !valueReferenceDebut.equals("")) {
								rqt += "and P.REFERENCE >= '"
										+ valueReferenceDebut.toUpperCase()
												.replace("*", "%") + "%'";
							}

							// Recherche par libellÃ©
							if (valueLibelle.equals("")) {
								rqt += "and E.LIBELLE like '%' ";
							} else {
								rqt += "and E.LIBELLE like '%"
										+ valueLibelle.toUpperCase().replace(
												"'", "@apostrophe@").replace(
												"*", "%") + "%' ";
							}
							rqt += " order by P.REFERENCE ";
							// System.out.println(rqt);
							if (rqt.indexOf("WHERE") != 0) {
								// queryProcedureEcriture =
								// "SELECT E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE  FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece)";
								queryProcedureEcriture = queryDefautProcedureEcriture;
								queryProcedureEcriture += rqt.substring(rqt
										.indexOf("WHERE"));
								System.out.println("queryProcedureEcriture--"
										+ queryProcedureEcriture);
							}
							System.out.println(rqt);
						} catch (Exception e15) {
							// TODO: handle exception
							System.err.println(e15);
							System.out.println("rqt--" + rqt);
						}

						// <==== fin requÃªte
						if (rqtCorrecte) {
							Statement stmt;
							try {
								stmt = conn.createStatement();
								if (valueDateDebut == null) {
									valueDateDebut = "";
								}
								if (valueDateFin == null) {
									valueDateFin = "";
								}
								stmt
										.execute("INSERT INTO HISTORIQUE (HISTRQT, PERD, PERF, COMD, COMF, MOND, MONF, REFD, REFF, LIB) VALUES ('"
												+ rqt.replace("\"", "@@")
														.replace("'", "Â£Â£")
												+ "','"
												+ valueDateDebut.toString()
												+ "','"
												+ valueDateFin.toString()
												+ "','"
												+ valueCompteDebut.toString()
												+ "','"
												+ valueCompteFin.toString()
												+ "','"
												+ valueMontantDebut.toString()
												+ "','"
												+ valueMontantFin.toString()
												+ "','"
												+ valueReferenceDebut
														.toString()
												+ "','"
												+ valueReferenceFin.toString()
												+ "','"
												+ valueLibelle.toString()
												+ "')");

								addContenuTableHistorique();
								inter.getHistorique().layout();
								inter.getCompositeLeftbas();

								// System.out.println("---"+rqt);
								ResultSet rs = stmt.executeQuery(rqt);

								// rÃ©initialisation de la requÃªte poyur une
								// nouvelle
								// recherche distinct E.ID,
								rqt = "SELECT E.*,P.REFERENCE FROM Ecritures E JOIN Pieces P ON (P.ID=E.ID_Piece) WHERE ";
								final TableOngletResultat tabItem = makeCompositeOngeltTable(
										"ECRITURES", 750, 400, 1);
								/**
								 * foldEditionPlugin ==> for stocker fichiers of
								 * edtion here for edtion of ecriture recherche
								 * 
								 * C://runtime-GestionCommercialeComplet.product
								 * /dossier/Edtions/ArchivageEpicea/
								 * Ecriture_rechercher
								 */
								File foldEditionPlugin = new File(
										pathFileReport + "/" + namePlugin + "/"
												+ ReportEcriture);

								if (!foldEditionPlugin.exists()) {
									foldEditionPlugin.mkdirs();
								}

								/*
								 * pour stocker un ligne ==> Object[] dataLigne
								 * ensuite on met chaque ligne dans la liste:
								 * List<Object[]> data
								 */
								List<Object[]> data = new ArrayList<Object[]>();
								Object[] dataLigne = null;

								data = tabItem.analyseDonnee(rs, data,
										dataLigne);
								/*
								 * Sert Ã  connaitre la position de la colonne
								 */
								ArrayList<Integer> pos = new ArrayList<Integer>();

								/*
								 * Sert Ã  stocker les noms des colonnes dont on
								 * a besoin
								 */
								List<String> listeTitreChamp1 = tabItem
										.listeTitreChampBd(tabItem
												.getNomOnglet(), reportFile
												.getAbsolutePath());
								for (String string : listeTitreChamp1) {
									int i = 1;
									boolean trouve = false;
									while (!trouve
											&& i <= rs.getMetaData()
													.getColumnCount()) {
										if (rs.getMetaData().getColumnName(i)
												.equals(string)) {
											trouve = true;
											System.out.println(string);
										} else {
											i++;
										}
									}
									if (trouve) {
										pos.add(i);
										System.out.println("i--" + i);
									}
								}

								/*
								 * tabItem est onglet
								 */
								// tabItem.setDataTable1(data, pos);
								tabItem.setDataTableAll(data, pos, tabItem
										.getOngelEcriture().getTable());
								/*
								 * pour ranger les values de table
								 */
								// sortingTable soTableValue = new
								// sortingTable();
								// soTableValue.actionSortingTable(tabItem.getTable(),data);
								stmt.close();
								tabItem.BasEcritures(
										tabItem.getOngelEcriture(),
										valueDateDebut, valueDateFin,
										valueCompteDebut, valueCompteFin,
										valueMontantDebut, valueMontantFin,
										valueReferenceDebut, valueReferenceFin,
										valueLibelle, nameDB,
										TableOngletResultat.getDebitEtotal(),
										TableOngletResultat.getCreditEtotal());

								tabItem.getOngelEcriture().getTable()
										.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseDown(MouseEvent e) {
												// TODO Auto-generated method
												// stub
												super.mouseDown(e);
											}

											@Override
											public void mouseDoubleClick(
													MouseEvent e) {
												// TODO Auto-generated method
												// stub
												super.mouseDoubleClick(e);
												/*
												 * function de
												 * valueLine(tabItem) pour
												 * return ID_PIECE
												 * 
												 * objectLine est la ligne que
												 * l'on choisi
												 */
												int idPieces = Integer
														.parseInt(valueLine(
																tabItem,
																tabItem
																		.getOngelEcriture()
																		.getTable()));
												// System.out.println("idPieces "+idPieces);

												ongletPieces(idPieces, tabItem);
											}

											@Override
											public void mouseUp(MouseEvent e) {
												// TODO Auto-generated method
												// stub
												super.mouseUp(e);
											}
										});

								int nombreFileEdition = foldEditionPlugin
										.listFiles().length;

								ConstEdition constEdition = new ConstEdition();
								nombreLineTable = constEdition
										.nombreLineTable(tabItem
												.getTableViewer());

								addActionButtonImprimer(tabItem,
										foldEditionPlugin, nombreFileEdition);

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (Exception ex) {
								// TODO Auto-generated catch block
								ex.printStackTrace();
							}
						}

					}
				}

			}

		});
	}

	public void addActionButtonImprimer(final TableOngletResultat tabItem,
			final File foldReport, final int nombreFileEdition) {

		final String pathFileEdtion = foldReport.toString();
		/**
		 * Add action to show choix of file report
		 */
		tabItem.getOngelEcriture().getButtonEcriturePrint()
				.addSelectionListener(new SelectionListener() {

					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						widgetSelected(e);
					}

					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						/**
						 * for SwtDialog.java
						 */
						if (nombreLineTable == 0) {
							MessageDialog.openWarning(inter.getShell(),
									ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
									ConstEdition.EDITION_VIDE);
						} else {

							final Shell dialogShell = new Shell(inter
									.getShell(),
							// SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
									SWT.RESIZE | SWT.CLOSE | SWT.MAX
											| SWT.APPLICATION_MODAL);
							dialogShell.setText(ConstEdition.TITLE_SHELL);
							dialogShell.setLayout(new FillLayout());

							final SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(
									dialogShell, SWT.NULL);

							// final SwtDialogReport dialogReport = new
							// SwtDialogReport(inter.getShell(),
							// SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
							// /**
							// * SwtDialogReport_old
							// */
							// // final SwtDialogReport_old dialogReport = new
							// SwtDialogReport_old(inter.getShell(),
							// // SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
							//
							File[] listFileRoport = null;
							listFileRoport = foldReport.listFiles();
							//					
							//
							final ArrayList<Button> listButton = new ArrayList<Button>();
							final Map<String, String> nameFileReport = new LinkedHashMap<String, String>();
							if (listFileRoport.length != 0) {

								for (File PathFileReport : listFileRoport) {
									System.out
											.println(PathFileReport.getName());

									String nameFile = PathFileReport.getName()
											.replaceFirst(".rptdesign", "");

									nameFileReport.put(nameFile, PathFileReport
											.toString());

									Button radioEdtion = new Button(
											dialogReport.getGroupFileReport(),
											SWT.RADIO | SWT.LEFT);

									radioEdtion.setText(nameFile.toString());
									listButton.add(radioEdtion);

								}
							}
							dialogReport.getButtonAnnulerPrint()
									.addSelectionListener(
											new SelectionListener() {

												@Override
												public void widgetDefaultSelected(
														SelectionEvent e) {
													// TODO Auto-generated
													// method stub
													widgetSelected(e);

												}

												@Override
												public void widgetSelected(
														SelectionEvent e) {
													// TODO Auto-generated
													// method stub
													System.out
															.println("Doit fermer le dialogShell");
													dialogShell.close();
												}

											});
							dialogReport.getButtonValiderPrint()
									.addSelectionListener(
											new SelectionListener() {

												public void widgetDefaultSelected(
														SelectionEvent e) {
													// TODO Auto-generated
													// method stub
													widgetSelected(e);
												}

												public void widgetSelected(
														SelectionEvent e) {
													// TODO Auto-generated
													// method stub

													String nameDBEcriture = tabItem
															.getOngelEcriture()
															.getLabelNameDB()
															.getText();
													String nombreEcriture = tabItem
															.getOngelEcriture()
															.getLabelNombreDB()
															.getText();
													String EcritureDate1 = tabItem
															.getOngelEcriture()
															.getLabelDate1()
															.getText();
													String EcritureDate2 = tabItem
															.getOngelEcriture()
															.getLabelDate2()
															.getText();
													String EcritureCompte1 = tabItem
															.getOngelEcriture()
															.getLabelCompte1()
															.getText();
													String EcritureCompte2 = tabItem
															.getOngelEcriture()
															.getLabelCompte2()
															.getText();
													String EcritureMontant1 = tabItem
															.getOngelEcriture()
															.getLabelMontant1()
															.getText();
													String EcritureMontant2 = tabItem
															.getOngelEcriture()
															.getLabelMontant2()
															.getText();
													String EcritureReference1 = tabItem
															.getOngelEcriture()
															.getLabelReference1()
															.getText();
													String EcritureReference2 = tabItem
															.getOngelEcriture()
															.getLabelReference2()
															.getText();
													String EcritureLibelle = tabItem
															.getOngelEcriture()
															.getLabelContenuInfoLibelle()
															.getText();
													String EcritureTotauxDebit = tabItem
															.getOngelEcriture()
															.getLabelTotauxDebit()
															.getText();
													String EcritureTotauxCredit = tabItem
															.getOngelEcriture()
															.getLabelTotauxCredit()
															.getText();
													String EcritureSoldeDebit = tabItem
															.getOngelEcriture()
															.getLabelSoldeDebit()
															.getText();
													String EcritureSoldeCredit = tabItem
															.getOngelEcriture()
															.getLabelSoldeCredit()
															.getText();

													String pathFileReportForm = null;
													if (dialogReport
															.getRadioReportDefaut()
															.getSelection()) {
														pathFileReportForm = null;

													} else {
														for (int i = 0; i < listButton
																.size(); i++) {
															if (listButton
																	.get(i)
																	.getSelection()) {
																String nameButton = listButton
																		.get(i)
																		.getText();
																pathFileReportForm = nameFileReport
																		.get(nameButton);
															}
														}
													}
													String fromatFileReport = null;
													Boolean extraction = false;

													if (dialogReport
															.getButtonRadioPDF()
															.getSelection()) {
														fromatFileReport = ConstEdition.FORMAT_PDF;
													} else if (dialogReport
															.getButtonRadioHTML()
															.getSelection()) {
														fromatFileReport = ConstEdition.FORMAT_HTML;
													} else if (dialogReport
															.getButtonRadioDOC()
															.getSelection()) {
														fromatFileReport = ConstEdition.FORMAT_DOC;
													} else if (dialogReport
															.getButtonRadioPPT()
															.getSelection()) {
														fromatFileReport = ConstEdition.FORMAT_PPT;
													} else if (dialogReport
															.getButtonRadioXLS()
															.getSelection()) {
														fromatFileReport = ConstEdition.FORMAT_XLS;
													} else {
														fromatFileReport = ConstEdition.FORMAT_EXTRACTION;
														extraction = true;
													}

													try {
														actionPrintEcriture(
																nameDBEcriture,
																nombreEcriture,
																EcritureDate1,
																EcritureDate2,
																EcritureCompte1,
																EcritureCompte2,
																EcritureMontant1,
																EcritureMontant2,
																EcritureReference1,
																EcritureReference2,
																EcritureLibelle,
																EcritureTotauxDebit,
																EcritureTotauxCredit,
																EcritureSoldeDebit,
																EcritureSoldeCredit,
																pathFileReportForm,
																fromatFileReport,
																extraction);
													} catch (Exception e1) {
														// TODO Auto-generated
														// catch block
														e1.printStackTrace();
													}
													dialogShell.close();
												}

											});

							dialogShell.open();
						}
					}

				});

	}

	public void addActionButtonImprimerOngletPiece(
			final TableOngletResultat tabItem) {
		tabItem.getOngelPiece().getButtonPiecePrint().addSelectionListener(
				new SelectionListener() {

					String PieceTauxTva = tabItem.getOngelPiece()
							.getLabelValueEcritureTauxTva().getText();
					String PieceMontantTva = tabItem.getOngelPiece()
							.getLabelValueEcritureMontantTva().getText();
					String PieceDateLivraison = tabItem.getOngelPiece()
							.getLabelValueEcriutreDateLivraison().getText();
					String PieceCodeTva = tabItem.getOngelPiece()
							.getLabelValueEcritureCodeTva().getText();
					String PieceTypeTva = tabItem.getOngelPiece()
							.getLabelValueEcritureTypeTva().getText();
					String PieceNombreEcriture = tabItem.getOngelPiece()
							.getLabelValueNombreEcriture().getText();
					String PieceTotauxDebit = tabItem.getOngelPiece()
							.getLabelPieceTotauxDebit().getText();
					String PieceTotauxCredit = tabItem.getOngelPiece()
							.getLabelPieceTotauxCredit().getText();
					String PieceMontantDebit = tabItem.getOngelPiece()
							.getLabelPieceMontantDebit().getText();
					String PieceMontantCredit = tabItem.getOngelPiece()
							.getLabelPieceMontantCredit().getText();

					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						widgetSelected(e);
					}

					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						try {
							System.out.println("PieceDateLivraison---"
									+ PieceDateLivraison);
							actionPrintPiece(PieceTauxTva, PieceMontantTva,
									PieceDateLivraison, PieceCodeTva,
									PieceTypeTva, PieceNombreEcriture,
									PieceTotauxDebit, PieceTotauxCredit,
									PieceMontantDebit, PieceMontantCredit);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});
	}

	public void addActionButtonImprimerOngletCompte(
			final TableOngletResultat tabItem) {
		tabItem.getOngelCompte().getButtonComptePrint().addSelectionListener(
				new SelectionListener() {

					String CompteName = tabItem.getOngelCompte()
							.getLabelNumeroCompte().getText();
					String CompteNombreEcriture = tabItem.getOngelCompte()
							.getLabelTotalEcritureCompte().getText();
					String CompteDateTM = tabItem.getOngelCompte()
							.getLabelTotauxMonvementDate().getText();
					String CompteTmDebit = tabItem.getOngelCompte()
							.getLabelDebitCompte().getText();
					String CompteTmCredit = tabItem.getOngelCompte()
							.getLabelCreditCompte().getText();
					String CompteSfp = tabItem.getOngelCompte()
							.getLabelSoldeFinDate().getText();
					String CompteSfpDebit = tabItem.getOngelCompte()
							.getLabelDebitSoldeCompte().getText();
					String CompteSfpCredit = tabItem.getOngelCompte()
							.getLabelCreditSoldeCompte().getText();

					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						widgetSelected(e);
					}

					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						try {
							// System.out.println("PieceDateLivraison---"+PieceDateLivraison);
							actionPrintCompte(CompteName, CompteNombreEcriture,
									CompteDateTM, CompteTmDebit,
									CompteTmCredit, CompteSfp, CompteSfpDebit,
									CompteSfpCredit);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});
	}

	/**
	 * Sert quant on clique 2 fois sur une ligne de l'onglet ECRITURES et pour
	 * afficher les donnees de l'onglet Pieces
	 */
	public Boolean VerifieAlphabet(String string) {
		Boolean alphabet;
		try {
			Double.valueOf(string);
			alphabet = true;

		} catch (Exception e) {
			alphabet = false;
		}
		return alphabet;

	}

	/**
	 * 
	 * @param tabItemPieces
	 * @param ID_PIECE
	 */
	public void makeOngletTable(final TableOngletResultat tabItemPieces,
			int ID_PIECE) {

		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySqlEcritures + ID_PIECE);
			tabItemPieces.StrucDownTablePieces(tabItemPieces.getOngelPiece());

			queryProcedureOngletPieceEcriture = queryDefautProcedureOngletPieceEcriture;
			queryProcedureOngletPieceEcriture += ID_PIECE;

			// System.out.println("queryProcedureOngletPieceEcriture : "+queryProcedureOngletPieceEcriture);
			List<Object[]> data = new ArrayList<Object[]>();
			Object[] dataLigne = null;

			data = tabItemPieces.analyseDonnee(rs, data, dataLigne);

			ArrayList<Integer> posPieces = new ArrayList<Integer>();
			List<String> listeTitreChampPieces;
			try {
				listeTitreChampPieces = tabItemPieces.listeTitreChampBd(
						"PIECESC", reportFile.getAbsolutePath());
				for (String string : listeTitreChampPieces) {
					int i = 1;
					boolean trouve = false;
					while (!trouve && i <= rs.getMetaData().getColumnCount()) {
						if (rs.getMetaData().getColumnName(i).equals(string)) {
							trouve = true;
						} else {
							i++;
						}
					}
					if (trouve)
						posPieces.add(i);
				}

				tabItemPieces.setDataTableAll(data, posPieces, tabItemPieces
						.getOngelPiece().getTableDownPiece());
				/*
				 * calcul de debit et credit
				 */
				tabItemPieces.calculDebitCredit(data, posPieces);
				tabItemPieces.ajoutContenuCompositeDownGroup(tabItemPieces
						.getOngelPiece());
				tabItemPieces.ajoutContenuCompositeBottom(tabItemPieces
						.getOngelPiece());

				/*
				 * ajouter une action de double clicks
				 */

				final List<Object[]> dataEcritureCompte = new ArrayList<Object[]>();
				Object[] dataLigneEcritureCompte = null;

				tabItemPieces.getOngelPiece().getTableDownPiece()
						.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseDown(e);
							}

							@Override
							public void mouseDoubleClick(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseDoubleClick(e);
								/*
								 * ajouter une nouvelle onglet pour
								 */

								try {
									String valueCompte = valueCompte(tabItemPieces);
									/*
									 * afficher onglet de Compte
									 */
									ongletCompte(valueCompte);

								} catch (Exception e1) {
									e1.printStackTrace();
									// TODO: handle exception
								}

							}

							@Override
							public void mouseUp(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseUp(e);
							}
						});
				stmt.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			addActionButtonImprimerOngletPiece(tabItemPieces);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String valueDate(List<Object[]> dataCompte, int idObjet) {
		String dateString = "";
		java.util.Date a = (Date) dataCompte.get(idObjet)[5];
		java.text.DateFormat dataFormat = new java.text.SimpleDateFormat(
				"dd/MM/yyyy");

		dateString = dataFormat.format(a).toString();
		return dateString;
	}

	public void ongletCompte(String numeroCompte) {
		Statement stmtCompte = null;
		System.out.println(numeroCompte);
		try {
			stmtCompte = conn.createStatement();
			ResultSet rsCompte = stmtCompte.executeQuery(sqlEcrituresCompte
					+ numeroCompte + " ORDER BY E.\"DATE\"");

			queryProcedureOngletCompte = queryDefautProcedureOngletCompte;
			queryProcedureOngletCompte += numeroCompte;
			final TableOngletResultat tabItemCompte = makeCompositeOngeltTable(
					"COMPTE", 750, 400, 3);

			List<Object[]> dataCompte = new ArrayList<Object[]>();
			Object[] dataLigneCompte = null;

			dataCompte = tabItemCompte.analyseDonnee(rsCompte, dataCompte,
					dataLigneCompte);
			ArrayList<Integer> posCompte = new ArrayList<Integer>();

			List<String> listeTitreChampCompte;

			try {
				listeTitreChampCompte = tabItemCompte.listeTitreChampBd(
						tabItemCompte.getNomOnglet(), reportFile
								.getAbsolutePath());

				for (String string : listeTitreChampCompte) {
					int i = 1;
					boolean trouve = false;
					while (!trouve
							&& i <= rsCompte.getMetaData().getColumnCount()) {
						if (rsCompte.getMetaData().getColumnName(i).equals(
								string)) {
							trouve = true;
						} else {
							i++;
						}
					}
					if (trouve)
						posCompte.add(i);
				}
				tabItemCompte.setDataTableAll(dataCompte, posCompte,
						tabItemCompte.getOngelCompte().getTable());
				String dateDebut = valueDate(dataCompte, 0);
				String dateFin = valueDate(dataCompte, dataCompte.size() - 1);

				tabItemCompte.calculDebitCreditCompte(dataCompte, posCompte);
				tabItemCompte.StrucDownOngletCompte(tabItemCompte
						.getOngelCompte(), dateDebut, dateFin, numeroCompte);
				tabItemCompte.getOngelCompte().getTable().addMouseListener(
						new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseDown(e);
							}

							@Override
							public void mouseDoubleClick(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseDoubleClick(e);
								/*
								 * function de valueLine(tabItem) pour return
								 * ID_PIECE
								 * 
								 * objectLine est la ligne que l'on choisi
								 */
								int idPieces = Integer.parseInt(valueLine(
										tabItemCompte, tabItemCompte
												.getOngelCompte().getTable()));

								ongletPieces(idPieces, tabItemCompte);
							}

							@Override
							public void mouseUp(MouseEvent e) {
								// TODO Auto-generated method stub
								super.mouseUp(e);
							}

						});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/**
			 * add action for print
			 */
			addActionButtonImprimerOngletCompte(tabItemCompte);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ongletPieces(int idPieces, TableOngletResultat tabItem) {
		Statement stmtPieces = null;
		try {
			stmtPieces = conn.createStatement();
			ResultSet rsPieces = stmtPieces.executeQuery(sqlPiecesE + idPieces);

			queryProcedureOngletPiece = queryDefautProcedureOngletPiece;
			queryProcedureOngletPiece += idPieces;

			TableOngletResultat tabItemPieces = makeCompositeOngeltTable(
					"PIECES", 720, 50, 2);
			List<Object[]> dataPieces = new ArrayList<Object[]>();
			Object[] dataLignePieces = null;
			dataPieces = tabItemPieces.analyseDonnee(rsPieces, dataPieces,
					dataLignePieces);

			ArrayList<Integer> posPieces = new ArrayList<Integer>();
			List<String> listeTitreChampPieces;
			try {
				listeTitreChampPieces = tabItemPieces.listeTitreChampBd(
						tabItemPieces.getNomOnglet(), reportFile
								.getAbsolutePath());
				for (String string : listeTitreChampPieces) {
					int i = 1;
					boolean trouve = false;
					while (!trouve
							&& i <= rsPieces.getMetaData().getColumnCount()) {
						if (rsPieces.getMetaData().getColumnName(i).equals(
								string)) {
							trouve = true;
						} else {
							i++;
						}
					}
					if (trouve)
						posPieces.add(i);
				}

				tabItemPieces.setDataTableAll(dataPieces, posPieces,
						tabItemPieces.getOngelPiece().getTableTopPiece());
				/*
				 * changer des valeurs de codeTva...
				 */
				tabItemPieces.setCodeTva(tabItem.getCodeTva());
				tabItemPieces.setTypeTva(tabItem.getTypeTva());
				tabItemPieces.setTauxTva(tabItem.getTauxTva());
				tabItemPieces.setDateLivraison(tabItem.getDateLivraison());
				tabItemPieces.setMontantTva(tabItem.getMontantTva());

				/*
				 * dans onglet de Pieces il y a deux tables .
				 * 
				 * makeOngletTable(tabItemPieces,idPieces) pour remplir
				 * List<Object[]> data est deuxieme de table
				 */
				makeOngletTable(tabItemPieces, idPieces);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * obtenir une numero de compte quand on click deux fois sur table de compte
	 */
	public String valueCompte(TableOngletResultat tabItemEcritureCompte) {
		String numeroCompte = "";
		Object[] objectLine = tabItemEcritureCompte.getMapTableAll().get(
				tabItemEcritureCompte.getOngelPiece().getTableDownPiece()).get(
				tabItemEcritureCompte.getOngelPiece().getTableDownPiece()
						.getSelectionIndex());
		numeroCompte = objectLine[6].toString();

		return numeroCompte;
	}

	public String valueLine(TableOngletResultat tabItem, Table table) {
		// public String valueLine(Table table) {
		String valueDoubleClick = null;
		/*
		 * pour obtenir une valeur d'une cellule tabItem.getData() obtiens
		 * List<Object[]> qui contiens les donnees du table chaque Object[]
		 * present un ligne du table
		 * 
		 * tabItem.getTable().getSelectionIndex() obient quelle ligne qui est
		 * choisit
		 * 
		 * tabItem.getData().get(tabItem.getTable().getSelectionIndex()) obtien
		 * un ligne de Object[] que l'on choisit
		 */
		// tabItem.getTable().getSelectionIndex();
		// Object[] objectLine =
		// tabItem.getDataTable1().get(tabItem.getTable().getSelectionIndex());
		// objectLine =
		// tabItem.getDataTable1().get(tabItem.getOngelEcriture().getTable().getSelectionIndex());
		// objectLine =
		// tabItem.getMapTableAll().get(tabItem.getOngelEcriture().getTable()).get(tabItem.getOngelEcriture().getTable().getSelectionIndex());
		objectLine = tabItem.getMapTableAll().get(table).get(
				table.getSelectionIndex());
		// System.out.println(objectLine.length);
		try {
			for (int i = 0; i < objectLine.length; i++) {
				if (objectLine[i] == null) {
					objectLine[i] = "";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// for(int i = 0; i < objectLine.length; i++)
		// System.out.println(i+" : "+objectLine[i].toString());
		/*
		 * 16 est Code Tva 17 est Type Tva 18 est Taux Tva 19 est Date Tva 30
		 * est Montant Tva
		 */

		// tabItem.setTauxTva(objectLine[17].toString());
		// tabItem.setMontantTva(objectLine[29].toString());
		// tabItem.setTypeTva(objectLine[16].toString());
		// tabItem.setDateLivraison(objectLine[18].toString());
		// tabItem.setCodeTva(objectLine[15].toString());
		// valueDoubleClick = objectLine[1].toString();

		tabItem.setTauxTva(objectLine[18].toString());
		tabItem.setMontantTva(objectLine[30].toString());
		tabItem.setTypeTva(objectLine[17].toString());
		tabItem.setDateLivraison(objectLine[19].toString());
		tabItem.setCodeTva(objectLine[16].toString());
		valueDoubleClick = objectLine[2].toString();

		return valueDoubleClick;
	}

	/**
	 * for get hold of the path of current
	 */
	private void initChemin() throws IOException, URISyntaxException {
		/**
		 * pathParametre est dans le repertoire de plugin
		 * 
		 * UrlPathFile esy un url de pathParametre seulement pour eclipse
		 */

		URL UrlPathFileEclipse = FileLocator.find(Platform
				.getBundle(Activator.PLUGIN_ID), new Path(pathParametre), null);
		/*
		 * pour extrem fichier comme heriariche de pligin
		 */
		UrlPathFileEclipse = FileLocator.toFileURL(UrlPathFileEclipse);
		/*
		 * la tete de URL et URI a des protocoles qui convertissent URL
		 * d'eclipse vers URI de java,
		 */
		URI UriPathFileJava = new URI(FileLocator.resolve(UrlPathFileEclipse)
				.toString());

		/*
		 * pour servir d'obtenir le nom de chemin de << File reportFile >>
		 */
		reportFile = new File(UriPathFileJava);
	}

	/**
	 * 
	 * @param nomOnglet
	 * @param weight
	 * @param height
	 * @param choix
	 *            est seulement pour ongel de pieces
	 * @param idPiece
	 *            est seulement pour onglet de pieces
	 * @return
	 */
	public TableOngletResultat makeCompositeOngeltTable(String nomOnglet,
			int weight, int height, int choixCompsite) {
		try {
			/*
			 * pour creer les colonnes et les noms des colonnes
			 */

			TableOngletResultat tabItem = new TableOngletResultat(inter
					.getTabFolder(), SWT.CLOSE, nomOnglet, reportFile
					.getAbsolutePath(), weight, height, choixCompsite);

			inter.getTabFolder().setSelection(tabItem);
			return tabItem;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkContenu() {
		boolean bool = false;
		if (inter.getPeriodeDebut().getSelection() == null
				&& inter.getPeriodeFin().getSelection() == null
				&& inter.getTextCompteDebut().getText().equals("")
				&& inter.getTextCompteFin().getText().equals("")
				&& inter.getTextMontantDebut().getText().equals("")
				&& inter.getTextCompteFin().getText().equals("")
				&& inter.getTextRefernceDebut().getText().equals("")
				&& inter.getTextRefernceFin().getText().equals("")
				&& inter.getTextLibelle().getText().equals("")) {
			bool = true;
		}
		return bool;
	}

	public void getValueDate() {

	}

	public void afficheInfos() {
		System.out.println(this.nameDB);
	}

	private void initInterface() {
		try {

			inter.getCompositeLeftBottom().setVisible(false);
			inter.getCompositeLeftbas().setVisible(false);
			addActionMenu();
			addActionCombo();
			addActionButtonRecherche();
			addActionHistoriqueDelete();
			addEffacerHistoriqueAll();
			addMasquerHist();
			addActionHistoriqueClick();
			inter.getPeriodeDebut().setSelection(null);
			inter.getPeriodeFin().setSelection(null);
			/*
			 * pour obtenir le fichier de proprties
			 */
			initChemin();

			addActionShell();

			if (!checkPathFileFdb(nameFileDB)) {
				addContenuCombo();

			} else {
				inter.getCompositeLeftTop().setVisible(false);
				MessageDialog.openError(inter.getShell(), "ALTER",
						"Vous devez importer un dossier!");
				/**
				 * ajouter un dialogue pour remplacer message
				 */
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public void actionPrintEcriture(String nameDB, String nombreLigne,
			String Date1, String Date2, String Compte1, String Compte2,
			String Montant1, String Montant2, String Reference1,
			String Reference2, String Libelle, String TotauxDebit,
			String TotauxCredit, String SoldeDebit, String SoldeCredit,
			String pathEdtion, String typeFileEdition, Boolean isExtraction)
			throws Exception {

		// final int idFacture = ((SWTEnteteFacture) swtFacture.getEntete())
		// .getID_FACTURE();
		// final String codeFacture = swtFacture.getEntete().getCODE();

		try {

			// WebViewerUtil.startWebViewer();
			// DEBUT LANCEMENT WebViewer
			// logger.debug("lancement birt viewer");
			// WebappAccessor.start("/birt" , "org.eclipse.birt.report.viewer",
			// new Path("/"));
			// logger.debug( WebappAccessor.getHost() + " - " +
			// WebappAccessor.getPort() );
			// String url =
			// "http://"+WebappAccessor.getHost()+":"+WebappAccessor.getPort()+"/birt/";

			// System.setProperty( "RUN_UNDER_ECLIPSE", "true" );
			// DtpManifestExplorer.getInstance( ).getExtensionManifests( );
			// FIN LANCEMENT WebViewer
			String url = WebViewerUtil.debutURL();

//			System.setProperty("RUN_UNDER_ECLIPSE", "true");
			// DtpManifestExplorer.getInstance( ).getExtensionManifests( );
			// WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/testSampleDB.rptdesign",
			// WebViewer.HTML,false);

			// String url = "http://localhost:8080/birt/";

			// BrowserManager.getInstance().createBrowser(false).displayURL("http://www.google.fr");
			// EmbeddedBrowser birtEmbeddedBrowser = new EmbeddedBrowser();
			// IBrowser b = BrowserManager.getInstance().createBrowser(false);

			if (isExtraction) {
				url += "frameset?__report=";
			} else {
				url += "run?__report=";
			}

			// url+="frameset?__report=";

			// url+="C:/Projet/Java/Eclipse/GestionCommerciale/Facture/report/";

			// url+="C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/";
			// logger.debug(TestBirtPlugin.getDefault().find(new
			// Path("/")).getPath());
			// url+="tiers.rptdesign";
			// url+="facture.rptdesign";
			if (pathEdtion == null) {
				Bundle bundleCourant = Activator.getDefault().getBundle();
				String reportFileLocation = "/report/reportEcriture.rptdesign";
				// if(((EnteteFacture)facture.getEntete()).getTTC()!=0)
				// //facture
				// TTC
				// reportFileLocation = "/report/factureTTC.rptdesign";
				URL urlReportFile = Platform.asLocalURL(bundleCourant
						.getEntry(reportFileLocation));
				URI uriReportFile = new URI("file", urlReportFile
						.getAuthority(), urlReportFile.getPath(), urlReportFile
						.getQuery(), null);

				File reportFile = new File(uriReportFile);

				url += reportFile.getAbsolutePath();

				logger.debug("Report file asLocalURL URL  : " + urlReportFile);
				logger.debug("Report file asLocalURL File : "
						+ reportFile.getAbsolutePath());
			} else {
				url += pathEdtion;

				// logger.debug("Report file asLocalURL URL  : " +
				// urlReportFile);
				logger.debug("Report file asLocalURL File : " + pathEdtion);
			}

			// url+="/Facture/report/facture.rptdesign";

			// url+="tiersv2.rptdesign";
			// url+="new_report.rptdesign";
			// url+="new_report_1.rptdesign";
			// url+="testSampleDB.rptdesign";

			// System.out.println("&ParaSqlEcriture--"+queryProcedureEcriture);
			String procedureEcriture = URLEncoder.encode(
					queryProcedureEcriture, "UTF-8");
			url += "&ParaSqlEcriture=" + procedureEcriture;
			url += "&ParamNomDossier=" + nameDB;
			url += "&ParamNombreEcriture=" + nombreLigne;
			url += "&ParamDate1Ecriutre=" + Date1;
			url += "&ParamDate2Ecriutre=" + Date2;
			url += "&ParamCompte1Ecriture=" + Compte1;

			url += "&ParamCompte2Ecriture=" + Compte2;
			url += "&ParamMontant1Ecriture=" + Montant1;
			url += "&ParamMontant2Ecriture=" + Montant2;
			url += "&ParamReference1Ecriture=" + Reference1;
			url += "&ParamReference2Ecriture=" + Reference2;
			url += "&ParamLibelleEcriture=" + Libelle;
			TotauxDebit = TotauxDebit.substring(0,
					TotauxDebit.length() - EURO.length()).replace(",", ".");// 100.00?
			TotauxCredit = TotauxCredit.substring(0,
					TotauxCredit.length() - EURO.length()).replace(",", ".");// 100.00?
			SoldeDebit = SoldeDebit.substring(0,
					SoldeDebit.length() - EURO.length()).replace(",", ".");// 100.00?
			SoldeCredit = SoldeCredit.substring(0,
					SoldeCredit.length() - EURO.length()).replace(",", ".");// 100.00?

			url += "&ParamTotauxDebit=" + TotauxDebit;
			url += "&ParamTotauxCredit=" + TotauxCredit;
			url += "&ParamSoldeDebit=" + SoldeDebit;
			url += "&ParamSoldeCredit=" + SoldeCredit;

			// //
			// url+="&id_facture="+((EnteteFacture)facture.getEntete()).getID_FACTURE();
			// url += "&paramUrlJDBC="
			// + IB_APPLICATION.findDatabase().getConnection()
			// .getConnectionURL();
			//
			// SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE
			// .infosEntreprise("1", null);
			// url += "&capital=" + infoEntreprise.getCAPITAL_INFO_ENTREPRISE();
			// url += "&ape=" + infoEntreprise.getAPE_INFO_ENTREPRISE();
			// url += "&siret=" + infoEntreprise.getSIRET_INFO_ENTREPRISE();
			// url += "&rcs=" + infoEntreprise.getRCS_INFO_ENTREPRISE();
			// url += "&nomEntreprise=" +
			// infoEntreprise.getNOM_INFO_ENTREPRISE();
			url += "&__document=doc" + new Date().getTime();
			if (!isExtraction
					&& !typeFileEdition
							.equalsIgnoreCase(ConstEdition.FORMAT_HTML)) {
				url += "&__format=" + typeFileEdition;
			}
			String nameDbWithExtention = pathFileFirebird + "/" + nameDB
					+ ".FDB";
			url += "&nomBase=" + connectionDB.cheminBdd(nameDbWithExtention);

			// url += "&__format=pdf";
			// url+="&__format=doc";

			// b.displayURL(url);
			// birtEmbeddedBrowser.displayUrl(url);

			logger.debug("URL edition: " + url);
			// CustomBrowser birtCustomBrowser = new CustomBrowser();
			// WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/new_report.rptdesign",
			// WebViewer.HTML,false);
			// WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/tiers.rptdesign",
			// WebViewer.HTML,false);
			// org.eclipse.birt.report.viewer.browsers.BrowserManager.getInstance().createBrowser(false).displayURL(url);
			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getBrowserSupport()
								.createBrowser(
										IWorkbenchBrowserSupport.AS_EDITOR,
										"myId", "Edition archivage Epicea "
										// + codeFacture,
										, "").openURL(new URL(finalURL));
						// oldFacture = new OldFacture();//réinitialisation de
						// cet objet
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
			// oldFacture = new OldFacture();//réinitialisation de cet objet
			// org.eclipse.birt.report.viewer.
			// org.eclipse.birt.report.viewer.browsers.custom.

			// } catch (CoreException e) {
			// logger.error(e);
		} catch (Exception ex) {
			// logger.error(e);
			logger.error(ex);
		}
	}

	public void actionPrintPiece(String tauxTva, String montantTva,
			String dateLivraison, String codeTva, String TypeTva,
			String NombreEcriture, String totauxDebit, String totauxCredit,
			String montantDebit, String montanCredit) throws Exception {
		try {
			String url = WebViewerUtil.debutURL();

//			System.setProperty("RUN_UNDER_ECLIPSE", "true");

			url += "run?__report=";

			Bundle bundleCourant = Activator.getDefault().getBundle();// pour
																		// obtenir
																		// le
																		// plugin
																		// courant
			String reportFileLocation = "/report/reportPiece.rptdesign";

			URL urlReportFile = Platform.asLocalURL(bundleCourant
					.getEntry(reportFileLocation));
			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFile = new File(uriReportFile);

			url += reportFile.getAbsolutePath();

			logger.debug("Report file asLocalURL URL  : " + urlReportFile);
			logger.debug("Report file asLocalURL File : "
					+ reportFile.getAbsolutePath());

			// System.out.println("queryProcedureOngletPiece--"+queryProcedureOngletPiece);
			String procedureOngletPiece = URLEncoder.encode(
					queryProcedureOngletPiece, "UTF-8");
			String ProcedureOngletPieceEcriture = URLEncoder.encode(
					queryProcedureOngletPieceEcriture, "UTF-8");

			tauxTva = tauxTva.substring(0, tauxTva.length() - PERCENT.length())
					.replace(",", ".");
			montantTva = montantTva.substring(0, montantTva.length()
					- EURO.length());

			totauxDebit = totauxDebit.substring(0,
					totauxDebit.length() - EURO.length()).replace(",", ".");// 100.00?
			totauxCredit = totauxCredit.substring(0,
					totauxCredit.length() - EURO.length()).replace(",", ".");// 100.00?
			montantDebit = montantDebit.substring(0,
					montantDebit.length() - EURO.length()).replace(",", ".");// 100.00?
			montanCredit = montanCredit.substring(0,
					montanCredit.length() - EURO.length()).replace(",", ".");// 100.00?

			url += "&ParaSqlPiece=" + procedureOngletPiece;
			url += "&ParaSqlPieceEcriture=" + ProcedureOngletPieceEcriture;
			url += "&ParamTauxTva=" + tauxTva;
			url += "&ParamMontantTva=" + montantTva;
			url += "&ParamDateLivraison=" + dateLivraison;
			url += "&ParamCodeTva=" + codeTva;
			url += "&ParamTypeTva=" + TypeTva;
			url += "&ParamNombreEcriture=" + NombreEcriture;
			url += "&ParamTotauxDebit=" + totauxDebit;
			url += "&ParamTotauxCredit=" + totauxCredit;
			url += "&ParamMontantPieceDebit=" + montantDebit;
			url += "&ParamMontantPieceCredit=" + montanCredit;
			url += "&__document=doc" + new Date().getTime();
			url += "&__format=pdf";
			String nameDbWithExtention = pathFileFirebird + "/" + nameDB
					+ ".FDB";
			url += "&nomBase=" + connectionDB.cheminBdd(nameDbWithExtention);

			// url +=
			// "&nomBase="+IB_APPLICATION.findDatabase().getConnection().getConnectionURL();
			// url +=
			// "&nomBase=jdbc:firebirdsql://localhost//home/julien/runtime-GestionCommercialeComplet.product/demo/archepi/00304.FDB";
			// url +=
			// "&nomBase=jdbc:firebirdsql:localhost:/home/julien/runtime-GestionCommercialeComplet.product/demo/archepi/00304.FDB";

			logger.debug("URL edition: " + url);
			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI
								.getWorkbench()
								.getBrowserSupport()
								.createBrowser(
										IWorkbenchBrowserSupport.AS_EDITOR,
										"myId", "Edition archivage Epicea ", "")
								.openURL(new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	public void actionPrintCompte(String compteName, String nombreEcriture,
			String dateTM, String TmDebit, String TmCredit, String SfpDate,
			String SfpDebit, String SfpCredit) throws Exception {
		try {
			String url = WebViewerUtil.debutURL();

//			System.setProperty("RUN_UNDER_ECLIPSE", "true");

			url += "run?__report=";

			Bundle bundleCourant = Activator.getDefault().getBundle();// pour
																		// obtenir
																		// le
																		// plugin
																		// courant
			String reportFileLocation = "/report/reportCompte.rptdesign";

			URL urlReportFile = Platform.asLocalURL(bundleCourant
					.getEntry(reportFileLocation));
			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
					urlReportFile.getPath(), urlReportFile.getQuery(), null);
			File reportFile = new File(uriReportFile);

			url += reportFile.getAbsolutePath();
			// System.out.println("url==>"+reportFile.getAbsolutePath());

			logger.debug("Report file asLocalURL URL  : " + urlReportFile);
			logger.debug("Report file asLocalURL File : "
					+ reportFile.getAbsolutePath());

			// System.out.println("queryProcedureOngletPiece--"+queryProcedureOngletPiece);
			String procedureOngletCompte = URLEncoder.encode(
					queryProcedureOngletCompte, "UTF-8");

			TmDebit = TmDebit.substring(0, TmDebit.length() - EURO.length())
					.replace(",", ".");
			TmCredit = TmCredit.substring(0, TmCredit.length() - EURO.length())
					.replace(",", ".");// 100.00?
			SfpDebit = SfpDebit.substring(0, SfpDebit.length() - EURO.length())
					.replace(",", ".");// 100.00?
			SfpCredit = SfpCredit.substring(0,
					SfpCredit.length() - EURO.length()).replace(",", ".");// 100.00?

			url += "&ParaSqlCompte=" + procedureOngletCompte;
			url += "&ParamCompteName=" + compteName;
			url += "&ParamCompteNombreEcriture=" + nombreEcriture;
			url += "&ParamTotauxMonvement=" + dateTM;
			url += "&ParamCompteTmDebit=" + TmDebit;
			url += "&ParamCompteTmCredit=" + TmCredit;
			url += "&ParamSoldeFinPeriode=" + SfpDate;
			url += "&ParamCompteSfpDebit=" + SfpDebit;
			url += "&ParamCompteSfpCredit=" + SfpCredit;
			url += "&__document=doc" + new Date().getTime();
			url += "&__format=pdf";
			String nameDbWithExtention = pathFileFirebird + "/" + nameDB
					+ ".FDB";
			url += "&nomBase=" + connectionDB.cheminBdd(nameDbWithExtention);

			logger.debug("URL edition: " + url);
			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI
								.getWorkbench()
								.getBrowserSupport()
								.createBrowser(
										IWorkbenchBrowserSupport.AS_EDITOR,
										"myId", "Edition archivage Epicea ", "")
								.openURL(new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
