package fr.legrain.lib.data;

import java.io.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import java.awt.*;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;

/**
 * <p>Title: TBdLgr</p>
 * <p>Description: L'objet TBdLgr est l'ancêtre BD qui doit être utilisé lors de la création
 * d'objet gérant des tables. TBdLgr permet de disposer d'un objet com.borland.dx.sql.dataset.Database . </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public class BdLgr {
	static Logger logger = Logger.getLogger(BdLgr.class.getName());
	
	//ModeEcran:TModeEcran;
	public Object modeEcran;
	
	//ObjetAMettreAJour:TControl;
	public Object objetAMettreAJour;
	
	
	
	//Contient le nom du fichier de base de données
	protected String fDataBaseName;
	
	//True si c'est l'objet qui créé son propre objet IBBase et IBTransaction
	protected boolean fBaseInterne;
	
	//Contient l'objet de base de données
	//Contient le database dans lequel se trouveront les tables sur lesquelles chercher
	static protected Database fIBBase;

	//Contient l'objet transaction liés à la base de donnée
	/** @todo /FIBTransaction : TIBTransaction => deja dans Database ?*/
	//FIBTransaction : TIBTransaction;
	
	//Contient l'objet table par défaut
	protected QueryDataSet fIBQuery;
	protected QueryDescriptor fDescriptor;
	protected Update update;
	
	protected boolean modeInsertionDirecte = false;
	
	//Contient un DataSource pour utilisation interne
	//FDataSource:TDataSource;
	/** @todo FDataSource:TDataSource => deja dans le QueryDataSet ?*/
	protected DataSetView fDataSource;
	
	static protected ResourceBundle fListeTitre;
	
	static protected String fichierListeTitre;
	
//	//param par défaut pour la connexion à la base de données
//	static final String C_USER_BDD_DEFAUT        = "SYSDBA";
//	static final String C_PASS_BDD_DEFAUT        = "masterkey";
//	static final String C_URL_BASE_BDD_DEFAUT    = "jdbc:firebirdsql://localhost/";
//	static final String C_DRIVER_JDBC_BDD_DEFAUT = "org.firebirdsql.jdbc.FBDriver";
	
//	/**
//	 * Retourne le titre d'un champs tel que celui-ci doit être affiché dans le programme
//	 * @param value String
//	 * @return String
//	 */
//	static public String getTitreChamp(String value){
//		String result = value;
//		if(fListeTitre==null) {
//			if (new File(getFichierListeTitre()).exists()) {
//				try {
//					FileInputStream file = new FileInputStream(getFichierListeTitre());
//					fListeTitre = new PropertyResourceBundle(file);
//					file.close();
//				} catch (FileNotFoundException e) {
//					logger.error("", e);
//				} catch (IOException e) {
//					logger.error("", e);
//				}
//			}
//			else {
//				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","le fichier "+value+" n'existe pas");
//			}
//		}
//		if(fListeTitre!=null) {
//			result = fListeTitre.getString(value);
//		}
//		return result;
//	}
	
	/**
	 * Active ou désactive un trigger
	 * @param nomTrigger - nom du trigger à activer ou désactiver
	 * @param active - true => active le trigger, false => desactive le trigger
	 */
	public void activeTrigger(String nomTrigger, boolean active) {
		try {
			PreparedStatement ps = null;
			String debutRqt = null;
			if(fIBBase.getJdbcConnection()!=null) {
				debutRqt = "alter trigger ";
				if(active)
					ps = fIBBase.getJdbcConnection().prepareStatement(debutRqt+nomTrigger+" active");
				else
					ps = fIBBase.getJdbcConnection().prepareStatement(debutRqt+nomTrigger+" inactive");
				ps.execute();
			}
		} catch (SQLException e) {
			logger.debug("",e);
		}
	}
	
	/**
	 * Créé l'objet base de données ( et sa transaction) dont le nom du fichier à
	 * été affecté à la propriété DataBaseName.
	 * @param dataBaseName String - nom de la base de données / chemin du fichier de base de données
	 */
	private void createDataBase(String dataBaseName) {
		
		try {
			fBaseInterne = true;
			if (fIBBase == null) {
				fIBBase = new Database();
			}
			//if (FIBTransaction == null) {
			//  FIBTransaction: = TIBTransaction.Create(nil);
			//}
			fIBBase.closeConnection();  //FIBBase.CloseDataSets; FIBBase.Close;
			fIBBase.setDatabaseName(dataBaseName);
			fIBBase.setConnection(new com.borland.dx.sql.dataset.
					ConnectionDescriptor(LgrConstantes.C_URL_BASE_BDD_DEFAUT+fDataBaseName,
							LgrConstantes.C_USER_BDD_DEFAUT, LgrConstantes.C_PASS_BDD_DEFAUT,
							false,
							LgrConstantes.C_DRIVER_JDBC_BDD_DEFAUT));
			fIBBase.setAutoCommit(false);
			fIBBase.openConnection();
			/*
			 FIBBase.LoginPrompt: = false;
			 FIBBase.Params.Clear;
			 FIBBase.Params.Add('user_name = SYSDBA');
			 FIBBase.Params.Add('password = masterkey');
			 FIBBase.DefaultTransaction: = FIBTransaction;
			 FIBBase.Open;
			 */
		}
		catch (Exception e) {
			logger.error("Erreur : createDataBase", e);
			fBaseInterne = false;
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ATTENTION","Erreur à la création de la Base de données!");
			//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Erreur à la création de la Base de données!","ERREUR",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Ecrit dans la variable FdataBaseName et créé un objet base interne en appelant la procedure CreateDataBase.
	 * @param value String
	 */
	public void setFDataBaseName(String value) { //EcritDataBaseName
		//TmpCursor : TCursor;
		//TmpCursor: = Screen.Cursor;
		//Screen.Cursor: = crSQLWait;
		try {
			if (!new File(value).exists()) {
				MessageDialog.openWarning(Workbench.getInstance()
						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
						"La base de données " + value + " est inexistante");
				//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"La base de données " + value + " est inexistante","ERREUR",JOptionPane.ERROR_MESSAGE);
				//abort;
			}
			else {
				fDataBaseName = value;
				createDataBase(fDataBaseName);
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setFDataBaseName", e);
			fDataBaseName = "";
			// abort;
		}
		finally {
			//Screen.Cursor: = TmpCursor;
		}
	}
	
	/**
	 * Renvoi la valeur contenu dans FdataBaseName.
	 * @return String
	 */
	public String getFDataBaseName() { //LitDataBaseName
		return fDataBaseName;
	}
	
	/**
	 * Ecrit dans la variable FdataBase.
	 * @param value Database
	 */
	public void setFIBBase(Database value) { //EcritDataBase
		//FBManager f = null;
		Cursor TmpCursor;
		try {
			//TmpCursor = Cursor.getDefaultCursor();// Screen.Cursor;
			//Screen.Cursor := crSQLWait;
			fBaseInterne = false;
			fIBBase = value;
			if (value != null) {
				fIBBase.setAutoCommit(false);
				//FIBTransaction = value.DefaultTransaction;
				/** @todo Ajouter le chemin du fichier de la bdd ? */
				fDataBaseName = value.getDatabaseName(); //f.getFileName(); value.getMetaData().getURL();
			}
		}
		finally {
			//Screen.Cursor := TmpCursor;
		}
	}
	
	/**
	 * Retourne la variable FdataBase.
	 * @return Database
	 */
	public Database getFIBBase() { //LitDataBase
		return fIBBase;
	}
	
	/**
	 * Ecrit dans la variable FIBQuery.
	 * @param value QueryDataSet
	 */
	public void setFIBQuery(QueryDataSet value) { //EcritIbQuery
		fIBQuery = value;
//		if (fIBQuery != null) {
//			fIBQuery.open();
//		}
	}
	
	/**
	 * Retourne la variable FIBQuery
	 * @return QueryDataSet
	 */
	public QueryDataSet getFIBQuery() {return fIBQuery;} //LitIbQuery
	
//////////////////////function LitCoordonnees(Indice: Integer): Longint;
//////////////////////procedure EcritCoordonnees(Indice: Integer; Valeur: Longint);
	
	public BdLgr() { //constructor Create(AOwner :TComponent);
		try {
			fBaseInterne = false;
			//ModeEcran: = MeNormal;
			objetAMettreAJour = null;
			fDataSource = new DataSetView();
			//fListeTitre = new ResourceBundle();
			jbInit();
		}
		catch(Exception e) {
			logger.error("Erreur : BdLgr", e);
		}
	}
	
	/**
	 * @throws Throwable the <code>Exception</code> raised by this method
	 */
	protected void finalize() throws Throwable { 
		try {
//			if(fIBQuery!=null && fIBQuery.getDatabase()!=null){
//				fIBQuery.getDatabase().commit();
//				logger.info("commit");
//			}
		}
		catch (Exception e) {
			logger.error("Erreur : finalize", e);
			if(fIBQuery!=null && fIBQuery.getDatabase()!=null)
				fIBQuery.getDatabase().rollback();
		}
		//FIBTransaction:= nil;
		if (fBaseInterne) {
			//FIBTransaction.Free;
		}
		
//		if (fIBBase != null) {
//		fIBBase = null;
//		if (fBaseInterne) {
//		fIBBase.closeConnection();
//		fIBBase = null;
//		}
//		}
		fDataSource = null;
	}
	
	private void jbInit() throws Exception {
	}
	
	static public String getFichierListeTitre() {
		return fichierListeTitre;
	}
	static public void setFichierListeTitre(String fichierListeTitre) {
		BdLgr.fichierListeTitre = fichierListeTitre;
	}
	public boolean isModeInsertionDirecte() {
		return this.modeInsertionDirecte;
	}
	public void setModeInsertionDirecte(boolean modeInsertionDirecte) {
		this.modeInsertionDirecte = modeInsertionDirecte;
	}

	public QueryDescriptor getFDescriptor() {
		return fDescriptor;
	}

	public void setFDescriptor(QueryDescriptor descriptor) {
		fDescriptor = descriptor;
	}




	
}
