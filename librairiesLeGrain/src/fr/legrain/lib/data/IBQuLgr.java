package fr.legrain.lib.data;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TooManyListenersException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.internal.Workbench;

import com.borland.dx.dataset.AccessEvent;
import com.borland.dx.dataset.AccessListener;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataChangeListener;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.EditListener;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.NavigationListener;
import com.borland.dx.dataset.OpenListener;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.Resolver;
import com.borland.dx.dataset.ResolverAdapter;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;
import com.borland.dx.sql.dataset.TransactionSupport;
import com.borland.jb.util.ErrorResponse;
import com.sun.jmx.snmp.Timestamp;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.DestroyListener;

/**
 * <p>Title: TIBQuLgr </p>
 * <p>Description: IBQuLgr sert à établir le lien entre un objet enfant de IBQuLgr
 * et une table sous-jacente contenue dans la variable TbdLgr.FIBQuery.
 * La liste des champs est récupérée dynamiquement et vient alimenter la liste FlisteChampValue
 * dont les valeurs sont de type : [NomDuChamp=ValeurChamp].
 * Les modifications apporté à l'enregistrement en cours sont faites directement dans cette liste
 * et éventuellement controlées par un objet de contrôle prévu pour cela.
 * Lors de l'appel à la méthode d'enregistrement, les données sont expédiées à la table concernées.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public abstract class IBQuLgr  extends BdLgr implements DestroyListener  {
	
	protected EventListenerList listenerList = new EventListenerList();
	
	protected BdLgrEvent bdLgrEvent = null;
	
	//Contient le mode dans lequel se trouve l'objet : MoConsultation,MoEdition,MoInsertion,MoImportation,MoErreur
	private ModeObjet fModeObjet = new ModeObjet(); //public property ModeObjet : TModeObjet read LitModeObjet write EcritModeObjet;
	
//	private JComponent focusCourant = null;
	//Ne pas tenir compte (pour l'instant !)
	private boolean fAutoEdit;  //property AutoEdit:boolean read FAutoEdit write FAutoEdit
	
	//Contient l'adresse de la procedure à exécuter lors du déclenchement de l'évènement OnIBQuLgrInsertion
	private BdLgrListener fIBQuLgrInsertion = null;
	
	//Contient l'adresse de la procedure à exécuter lors du déclenchement de l'évènement OnIBQuLgrModification
	private BdLgrListener fIBQuLgrModification = null;
	
	//Contient l'adresse de la procedure à exécuter lors du déclenchement de l'évènement OnIBQuLgrenregistrer
	private BdLgrListener fIBQuLgrEnregistrer = null;
	
	//Contient l'adresse de la procedure à exécuter lors du déclenchement de l'évènement OnIBQuLgrannuler
	private BdLgrListener fIBQuLgrAnnuler = null;
	
	//Contient l'adresse de la procedure à exécuter lors du déclenchement de l'évènement OnIBQuLgrsupprimer
	private BdLgrListener fIBQuLgrSupprimer = null;
	
	private String fChamp_Query;
	
	protected HashMap fListeChampValue;
	
	//liste des champs et des valeurs pour MAJ de vue
	protected HashMap listeMAJVue; 
	
	protected ExceptLgr infoExceptLGR;
	
	protected SimpleDateFormat df = new SimpleDateFormat(LgrConstantes.C_DATE_FORMAT);
	
	//public static List<JComponent> mapChampsMajuscule = null;
	public String nomTable;
	public String valeurIdTable;
	public String champIdTable;
	public String champGenere;
	public String valeurGenere;
	
	public static GestionModif modif = new GestionModif();
	static private Properties listeChampMaj=new Properties(); 
	
	public HashMap listeRelation = new HashMap();
	
	//Paramètre pour la construction de la clause "where" de requete simple
	//clé = nom du champs, valeur = [type de comparaison (=,>,<,like)][valeur du champs]
	protected Map<String,String[]> paramWhereSQL = null;
	
	protected HashMap listeChampsCalcules = null;
	
	/**
	 * Paramétrage des instances de la classe.
	 * @param objetAppelant Object
	 * @param param ParamInitIBQuLgr
	 * @return ResultInitIBQuLgr
	 */
	public abstract ResultInitIBQuLgr init(Object objetAppelant, ParamInitIBQuLgr param);
	public abstract void ctrlSaisieSpecifique( ValeurChamps valeurChamps,MessCtrlLgr ex) throws ExceptLgr;

	
	public void setFModeObjet(ModeObjet value){
		this.fModeObjet = value;		
	}
	
	public ModeObjet getFModeObjet(){
		return this.fModeObjet;
	}
	
	/**
	 * Retourne une valeur présente dans le <code>QuerySataSet</code> en fonction
	 * du nom de son champ
	 * @param nomChamp String
	 * @return String
	 */
	protected String getFChamp_Query(String nomChamp){//private String LitChamp_Query(const NomChamp : string);
		String result = LgrConstantes.C_STR_VIDE;
		//    ProtectObjetNil([FIBQuery]);
		//    if not FIBQuery.Active then FIBQuery.Open;
		if(fIBQuery.getColumn(nomChamp)!=null)  //if FIBQuery.findfield(NomChamp) <> nil then
			/** @todo faire un switch sur le type de la colonne */
			switch(fIBQuery.getColumn(nomChamp).getDataType()) {
			case Variant.STRING:
				result = fIBQuery.getString(nomChamp); //Result:= FIBQuery.findfield(NomChamp).AsString
				break;
			case Variant.INT:
				result = String.valueOf(fIBQuery.getInt(nomChamp)); //Result:= FIBQuery.findfield(NomChamp).AsString
				break;
			case Variant.SHORT:
				result = String.valueOf(fIBQuery.getShort(nomChamp));
				break;
			case Variant.FLOAT:
				result = String.valueOf(fIBQuery.getFloat(nomChamp));
				break;
			case Variant.DATE:
				if(fIBQuery.getDate(nomChamp)!=null)
					result = df.format(fIBQuery.getDate(nomChamp));
				else
					result = null;
				break;
			case Variant.TIMESTAMP:
				try {
					if(fIBQuery.getTimestamp(nomChamp)!=null)
						result = df.format(fIBQuery.getTimestamp(nomChamp));
					else
						result = null;
				}
				catch (DataSetException ex) {
					logger.error("Erreur : renvoieValeurSurType", ex);
				}
				break;
			case Variant.BIGDECIMAL:
				result = String.valueOf(fIBQuery.getBigDecimal(nomChamp));
				break;
			case Variant.LONG:
				result = String.valueOf(fIBQuery.getLong(nomChamp));
				break;				
			case Variant.DOUBLE:
				result = String.valueOf(fIBQuery.getDouble(nomChamp));
				break;
			case Variant.BOOLEAN:
				result=String.valueOf(fIBQuery.getBoolean(nomChamp));
				break;				
			default : break;
			}
		else
			MessageDialog.openWarning(Workbench.getInstance()
					.getActiveWorkbenchWindow().getShell(), "ATTENTION",
					"Le champ ["+nomChamp+"] est introuvable !");
			//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Le champ ["+nomChamp+"] est introuvable !","ERREUR",JOptionPane.ERROR_MESSAGE);
		return result;
	}
	
	/**
	 * Insère, modifie la valeur d'un champs dans le <code>QueryDataSet</code>
	 * mise à jour de la Query à partir de la liste
	 * Elle se sert de {@link #affecteChampQuery(String, String) affecteChampQuery } 
	 * Cette procédure tient compte du mode, elle n'affecte que en insertion ou modification
	 * @param nomChamp String
	 * @param value String
	 */
	protected void setFChamp_Query(String nomChamp, String value)throws Exception{ //private void EcritChamp_Query(const NomChamp : string; value : String);
		try {
//			AutoModeEdition(Champ_Query[NomChamp], value);
			if (fIBQuery != null) {
//				if not FIBQuery.Active then FIBQuery.Open;
				if (fIBQuery.getColumn(nomChamp) != null) {
					switch (fModeObjet.getMode()) {
					case C_MO_CONSULTATION: // Pas de modification possible en mode Consultation
						break;
					case C_MO_EDITION:
						affecteChampQuery(nomChamp,getChamp_Obj(nomChamp));
						break;
					case C_MO_INSERTION:
						affecteChampQuery(nomChamp,getChamp_Obj(nomChamp));
						//affecteChampQuery(nomChamp,(String) fListeChampValue.get(fIBQuery.getColumn(nomChamp).getColumnName()));
						break;
					case C_MO_IMPORTATION:
						break;
					default:
						break;
					}
				}
				else {
					MessageDialog.openWarning(Workbench.getInstance()
							.getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "Le champ ["+nomChamp+"] est introuvable !");
					//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Le champ ["+nomChamp+"] est introuvable !","ERREUR",JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				MessageDialog.openWarning(Workbench.getInstance()
						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
						"IBQuery est null!");
				//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"IBQuery est null!","ERREUR",JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setFChamp_Query", e);
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Récupération des valeurs du QueryDataSet dans la liste en mémoire <code>FListeChampValue</code>
	 * @return Map
	 */
	//private ??
	public Map getQuery_Champ_Obj(){
		HashMap result = null;
		switch (fModeObjet.getMode()) {
		case C_MO_CONSULTATION: {
			if (fIBQuery != null) {
				try {
					fListeChampValue.clear();
					for (int i = 0; i< fIBQuery.getColumnCount() ;i++) {
						//FListeChampValue.Values[FIBQuery.addColumn().Strings[i]]: = Champ_Query[FIBQuery.FieldList.Strings[i]];
						fListeChampValue.put(fIBQuery.getColumn(i).getColumnName(),getFChamp_Query(fIBQuery.getColumn(i).getColumnName()));
					}
				}
				catch (Exception e) {
					logger.error("Erreur : getQuery_Champ_Obj", e);
					MessageDialog.openWarning(Workbench.getInstance()
							.getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "Erreur lors de l''initialisation de FListeChampValue");
					//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Erreur lors de l''initialisation de FListeChampValue","ERREUR",JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		}
		case C_MO_EDITION: {
			if (fIBQuery != null) {
				fListeChampValue.clear();
				for (int j = 0; j< fIBQuery.getColumnCount();j++) {
					//FListeChampValue.Values[FIBQuery.FieldList.Strings[i]]: = Champ_Query[FIBQuery.FieldList.Strings[i]];
					fListeChampValue.put(fIBQuery.getColumn(j).getColumnName(),getFChamp_Query(fIBQuery.getColumn(j).getColumnName()));
				}
			}
			break;
		}
		case C_MO_INSERTION: {
			if (fIBQuery != null) {
				fListeChampValue.clear();
				if (!fIBQuery.isEditingNewRow()){
					for (int k = 0; k< fIBQuery.getColumnCount();k++) {
						fListeChampValue.put(fIBQuery.getColumn(k).getColumnName(),LgrConstantes.C_STR_VIDE);
					}}
				else{
					for (int k = 0; k< fIBQuery.getColumnCount();k++) {
						fListeChampValue.put(fIBQuery.getColumn(k).getColumnName(),getFChamp_Query(fIBQuery.getColumn(k).getColumnName()));
					}
				}
			}
			// FIBQuery.Insert;
			// DMTA_DONATEUR.Insertion_TA_DONATEUR(FIBQuery);
			break;
		}
		case C_MO_IMPORTATION: {
			break;
		}
		default:
			break;
		}
		result = fListeChampValue;
		return result;
	}
	
	/**
	 * Retourne une valeur présente dans la liste en memoire <code>FListeChampValue</code> en fonction
	 * du nom de son champ
	 * @param nomChamp String
	 * @return String
	 */
	//private ??
	public String getChamp_Obj(String nomChamp){  //private String LitChamp_Obj(const NomChamp : string);
		String result = LgrConstantes.C_STR_VIDE;
		if (fListeChampValue.get(nomChamp) != null){			
			if(fListeChampValue.get(nomChamp) instanceof String) result=(String)fListeChampValue.get(nomChamp);
			if(fListeChampValue.get(nomChamp) instanceof Integer) result=LibConversion.integerToString((Integer)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Double) result=String.valueOf((Double)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof BigDecimal) result=String.valueOf((BigDecimal)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Short) result=String.valueOf((Short)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Float) result=String.valueOf((Float)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Long) result=String.valueOf((Long)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Date) result=String.valueOf((Date)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Timestamp) result=String.valueOf((Timestamp)fListeChampValue.get(nomChamp));
			if(fListeChampValue.get(nomChamp) instanceof Boolean) result=String.valueOf((Boolean)fListeChampValue.get(nomChamp));
			}
		return result;
	}
	
	
	
	protected void setChamp_Obj(String nomChamp, Date value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=LibDate.dateToString(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}	
	
	protected void setChamp_Obj(String nomChamp, Integer value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}	

	protected void setChamp_Obj(String nomChamp, BigDecimal value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}
	
	protected void setChamp_Obj(String nomChamp, Double value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}	
	
	protected void setChamp_Obj(String nomChamp, Short value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}	
	
	protected void setChamp_Obj(String nomChamp, Boolean value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
//		fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}
	//
	protected void setChamp_Obj(String nomChamp, Float value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		String tmp = null;
		if(value!=null) tmp=String.valueOf(value);
		setChamp_Obj(nomChamp,tmp);
		//fListeChampValue.put(nomChamp,tmp); /** @todo a supprimer du début */
	}	
	/**
	 * Insère, modifie la valeur d'un champs dans la liste en memoire <code>FListeChampValue</code>
	 * Mise à jour de la liste directement, tient compte du mode
	 * @param nomChamp String
	 * @param value String
	 */
	//private ??
	protected void setChamp_Obj(String nomChamp, String value){//private void EcritChamp_Obj(const NomChamp : string; value : String);
		//MessCtrlLgr ex = new MessCtrlLgr();
		try {			
			annulerCodeGenere(value,nomChamp);
			fListeChampValue.put(nomChamp,value); /** @todo a supprimer du début */
			rentreCodeGenere(value,nomChamp);
			switch (fModeObjet.getMode()) {
			case C_MO_CONSULTATION: {
				// Pas de modification possible en mode Consultation
				break;
			}
			case C_MO_EDITION:{ // effectuer ici le controle sur le champ
				// if Assigned(FCtrlChampObj) {
//				FCtrlChampObj(NomChamp,Value,0,FExceptLGR,InfoExceptLGR);
//				FListeChampValue.Values[NomChamp]:=Value;
//				} else
				{
					fListeChampValue.put(nomChamp,value); //FListeChampValue.Values[NomChamp]:=Value;
				}
				break;
			}
			case C_MO_INSERTION:{ // effectuer ici le controle sur le champ
				{
					fListeChampValue.put(nomChamp,value); //FListeChampValue.Values[NomChamp]:=Value;
				}
				break;
			}
			case C_MO_IMPORTATION:{
				break;
			}
			default : break ;
			}
		}
		catch(Exception e) {
			logger.error("Erreur : setChamp_Obj", e);
		}
	}
	
	/**
	 * Procedure qui fait passer l'objet en mode edition si :
	 * <li> this.ModeObjet = MoConsultation </li>
	 * <li> this.AutoEdit = true </li>
	 * @param valOrg Variant
	 * @param valNew Variant
	 */
	protected void autoModeEdition(Variant valOrg, Variant valNew){
		if ( (fModeObjet.getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION))!=0 && (fAutoEdit)) {
			if (!valOrg.equals(valNew))
				fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
		}
	}
	
	//protected property Champ_Query[const NomChamp : string] : string read LitChamp_Query write EcritChamp_Query;
	//protected property Champ_Obj[const NomChamp : string] : string read LitChamp_Obj write EcritChamp_Obj;
	//protected property Champ_Obj_Liste : TStringList read LitQuery_Champ_Obj;
	
	protected Map getChamp_Obj_Liste(){
		return getQuery_Champ_Obj();
	}
	
	/**
	 * Mise à jour de la query à partir de la liste sur l'ensemble d'un enregistrement
	 * elle se sert de la méthode {@link #setFChamp_Query(String, String) setFChamp_Query} 
	 * @throws Exception
	 */
	public void setChamp_Obj_Query()throws Exception{
		try{
			Iterator iteNomChamp = fListeChampValue.keySet().iterator();
			String cle = null;
			while (iteNomChamp.hasNext()) { //(int i = 0; i<FListeChampValue.size(); i++) { // i: = 0 to FListeChampValue.Count - 1 {
				//"transfert des données de l'objet vers le DataSet"
				cle = (String) iteNomChamp.next();
//				setFChamp_Query(cle, (String) fListeChampValue.get(cle));
				setFChamp_Query(cle, getChamp_Obj(cle));
			
			}
		}
		catch (Exception e){
			logger.error("Erreur : setChamp_Obj_Query", e);
			throw new Exception(e.getMessage());
		}
	}
	
	

	/**
	 * mise à jour de la liste à partir de la query sur l'ensemble d'un enregistrement
	 * Elle ne tient pas compte du mode
	 * @throws Exception
	 */
	public void setChamp_Query_Obj()throws Exception{
		try{
			fListeChampValue.clear();
			for(int i=0;i<fIBQuery.getColumnCount();i++) {
				fListeChampValue.put(fIBQuery.getColumn(i).getColumnName(),
						getFChamp_Query(fIBQuery.getColumn(i).getColumnName()));
			}
		}
		catch (Exception e){
			logger.error("Erreur : setChamp_Query_Obj", e);
			throw new Exception(e.getMessage());
		}
	}
	
	public IBQuLgr() {
		super();
		try {
			fIBQuery = new QueryDataSet();
			fListeChampValue = new HashMap();
			fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			//fModeObjet.setMode(TModeObjet.C_MO_INSERTION);
			//ENREGISTREMENT DES EVENEMENTS DE TABLE
//			if (mapChampsMajuscule == null)
//			mapChampsMajuscule=new ArrayList();
			fIBQuery.addAccessListener(new TableAccessListener(this));
			fIBQuery.addDataChangeListener(new TableDataChangeListener(this));
			fIBQuery.addEditListener(new TableEditListener(this));
			fIBQuery.addNavigationListener(new TableNavigationListener(this));
			fIBQuery.addOpenListener(new TableOpenListener(this)); // 1 seul listener de ce type possible sinon : TooManyListenersException
		}
		catch (TooManyListenersException ex) {
			logger.error("Erreur : IBQuLgr", ex);
		}
		
		//FDataSource.OnStateChange: = dsFDataSourceStateChange;
	}
	
	public void initMapChampsMajuscule(){
		
	}
	
	

	public boolean getFAutoEdit() {
		return fAutoEdit;
	}
	
	public void setFAutoEdit(boolean autoEdit) {
		this.fAutoEdit = autoEdit;
	}
	
	public void setFIBQuLgrInsertion(BdLgrListener l) {
		if (fIBQuLgrInsertion != null)
			removeBdLgrListener(fIBQuLgrInsertion);
		fIBQuLgrInsertion = l;
		addBdLgrListener(fIBQuLgrInsertion);
	}
	
	public BdLgrListener getFIBQuLgrInsertion() {
		return fIBQuLgrInsertion;
	}
	
	public void setFIBQuLgrEnregistrer(BdLgrListener l) {
		if (fIBQuLgrEnregistrer != null)
			removeBdLgrListener(fIBQuLgrEnregistrer);
		fIBQuLgrEnregistrer = l;
		addBdLgrListener(fIBQuLgrEnregistrer);
	}
	
	public BdLgrListener getFIBQuLgrEnregistrer() {
		return fIBQuLgrEnregistrer;
	}
	
	public void setFIBQuLgrAnnuler(BdLgrListener l) {
		if (fIBQuLgrAnnuler != null)
			removeBdLgrListener(fIBQuLgrAnnuler);
		fIBQuLgrAnnuler = l;
		addBdLgrListener(fIBQuLgrAnnuler);
	}
	
	public BdLgrListener getFIBQuLgrAnnuler() {
		return fIBQuLgrAnnuler;
	}
	
	public void setFIBQuLgrSupprimer(BdLgrListener l) {
		if (fIBQuLgrSupprimer != null)
			removeBdLgrListener(fIBQuLgrSupprimer);
		fIBQuLgrSupprimer = l;
		addBdLgrListener(fIBQuLgrSupprimer);
	}
	
	public BdLgrListener getFIBQuLgrSupprimer() {
		return fIBQuLgrSupprimer;
	}
	
	public void enregistrer() throws Exception{
		try{
			int Row=0;
			if(isModeInsertionDirecte()) {
				update.setMode(this.getFModeObjet());
				update.setIdValeur(String.valueOf(getFChamp_Query(update.getIdChamps())));
				update.execution(update.createQuery(listeMAJVue));
				fIBQuery.cancel();
				//	fIBQuery.saveChanges();
				fIBQuery.refresh();
//				DataRow courant = new DataRow(fIBQuery,update.getIdChamps());
//				courant.setInt(update.getIdChamps(),LibConversion.stringToInteger(update.getIdValeur()));
//				fIBQuery.locate(courant,32);
			}
			else {  
				Iterator iteNomChamp = fListeChampValue.keySet().iterator();
				String cle = null;
				if (fIBQuLgrEnregistrer != null) { //if assigned(FIBQuLgrEnregistrer)
					fIBQuLgrEnregistrer.enregistrer(new BdLgrEvent(this)); //FIBQuLgrEnregistrer([FIBQuery]);
					// ou fireTBdLgrEnregistrer(); sans le if pour signaler à tous les objets intéressés par l'évènement
				}
				else {
					fIBQuery.post();
					fIBQuery.saveChanges();
					Row=fIBQuery.getRow();
					fIBQuery.refresh();
					fIBQuery.goToRow(Row);
				}
			}
			if (isModeInsertionDirecte())
				valeurIdTable=update.getIdValeur();
			annuleModification(false);
			if (champGenere!=null && valeurGenere!=null)
				annulerCodeGenere(valeurGenere,champGenere);			
			fModeObjet.setMode(ModeObjet.EnumModeObjet.valueOf("C_MO_CONSULTATION"));
			if (listeMAJVue!=null)listeMAJVue.clear();
		}
		catch (Exception e) {
			logger.error("Erreur : enregistrer", e);
			rollbackLgr(); //FIBTransaction.RollbackRetaining;
			throw new ExceptLgr(e.getMessage());
		}		
	}
	
	public void supprimer() throws Exception {
		try{
			valeurIdTable=getFChamp_Query(champIdTable);
			if(isModeInsertionDirecte()) {
				update.setMode(this.getFModeObjet());
				update.setIdValeur(String.valueOf(getFChamp_Query(update.getIdChamps())));
				update.execution(update.createQuery(listeMAJVue));
			}
			else {
				if (fIBQuLgrSupprimer != null) { //if assigned(FIBQuLgrSupprimer)
					fIBQuLgrSupprimer.supprimer(new BdLgrEvent(this));
				}
				else {
					fIBQuery.deleteRow(); //FIBQuery.Delete;
					//commitLgr(); //FIBTransaction.CommitRetaining;
				}
			}
			//annuleModification(false);	
			fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if (listeMAJVue!=null)listeMAJVue.clear();
		}catch(Exception e){
			logger.error("Erreur : supprimer", e);
			rollbackLgr();
			fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			throw new ExceptLgr("Problème à la suppression.");
		}
	}
	
	public void modifier()throws Exception {
		try {
			if(!dataSetEnModif()){
				valeurIdTable=getFChamp_Query(champIdTable);
				if (autoriseModification()){					
					rentreEnModification();	
					if (fIBQuLgrModification != null) { //if assigned(FIBQuLgrModification)
						fIBQuLgrModification.modifier(new BdLgrEvent(this));
					}
					else {
						fIBQuery.editRow();
						setChamp_Obj_Query();
						fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
					}
				}else logger.info("est en cours de modification");
			}
		}
		catch (Exception e) {
			logger.error("Erreur : modifier", e);
			throw new ExceptLgr("Problème à la modification.");
		}
	}
	
	
	public boolean ouvreDataset() {
		boolean res = false;
		if (fDescriptor!=null && fDescriptor.getLoadOption()!=Load.ALL)
		{
			fDescriptor.setLoadOption(Load.ALL);
			if(fIBQuery.isOpen())fIBQuery.refresh();
		}
		if (!fIBQuery.isOpen()) {
				fIBQuery.open();
		} 
		if (champIdTable!=null)
			fIBQuery.setRowId(champIdTable,true);
		
		return res;
	}
	
	public static boolean autoriseUtilisationCodeGenere(String code,String nomTable) throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampGenere(nomTable);
		if (champVerif!=null)
			res= modif.autoriseModifCodeGenere(nomTable,champVerif,code,true);
		return res;
	}	
	
	public  boolean autoriseUtilisationCodeGenere(String code) throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampGenere(nomTable);
		if (champVerif!=null)
			res= modif.autoriseModifCodeGenere(nomTable,champVerif,code,true);
		return res;
	}

	public static boolean autoriseUtilisationCodeGenere(String code,String nomTable,Boolean verif_Connection) throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampGenere(nomTable);
		if (champVerif!=null)
			res= modif.autoriseModifCodeGenere(nomTable,champVerif,code,verif_Connection);
		return res;
	}	
	

	
	public boolean rentreCodeGenere(String code,String ChampCourant) throws Exception{
		try{
		boolean res = false;
		String champVerif=modif.recupChampGenere(this.nomTable);
		if (champVerif.equals(ChampCourant)|| ChampCourant==null)
			if (champVerif!=null && code !=null && !LibChaine.empty(code))
			{
				modif.rentreEnModif(nomTable,champVerif,code);
				champGenere=champVerif;
				valeurGenere=code;
				res=true;
			}
		return res;
		}catch(Exception e){
			return false;
		}
	}
	
	
	
	public void annulerCodeGenere(String code,String ChampCourant) throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampGenere(this.nomTable);
		if (champVerif.equals(ChampCourant)|| ChampCourant==null)
			if (champVerif!=null && code !=null && !LibChaine.empty(code))
			{
				modif.annuleModif(nomTable,champVerif,code,false);
			}
	}
	
	

	
	public boolean recordModifiable(String nomTable,String valeurChamp) {
		try{
			CallableStatement cs;
			boolean res=true;
			if (valeurChamp==null )throw new Exception("Valeur id_table non renseignée");
			if (!LibChaine.empty(valeurChamp)){
				cs = this.getFIBBase().getJdbcConnection().prepareCall("{? = call " + "RECORD_MODIFIABLE" +"(?,?)}");
				cs.registerOutParameter(1,Types.INTEGER);
				cs.setString(2,nomTable);
				cs.setInt(3,LibConversion.stringToInteger(valeurChamp));
				cs.execute();
				if (cs.getInt(1)>0)
					res=false;
			}
			return res;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean autoriseModification() throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			res= modif.autoriseModif(nomTable,champVerif,valeurIdTable);
		return res;
	}
	
	public void rentreEnModification() throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			modif.rentreEnModif(nomTable,champVerif,valeurIdTable);
	}
	
	public void annuleModification(boolean commited) throws Exception{
		boolean res = true;
		String champVerif=modif.recupChampVerifModification(this.nomTable);
		if (champVerif!=null)
			modif.annuleModif(nomTable,champVerif,valeurIdTable,commited);
	}	
	
	
	public void annuler(){
		try {
			if(dataSetEnModif()){
				valeurIdTable=getFChamp_Query(champIdTable);
				if (fIBQuLgrAnnuler != null) { //if assigned(FIBQuLgrAnnuler)
					fIBQuLgrAnnuler.annuler(new BdLgrEvent(this));
				}
				else {
					fIBQuery.cancel();
					fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					fIBQuery.refresh();
				}
				rollbackLgr();
				annuleModification(true);
				if (champGenere!=null && valeurGenere!=null)
					annulerCodeGenere(valeurGenere,champGenere);
			}
			//commitLgr();
		} catch(Exception e) {
			logger.error("Erreur : annuler", e);
			MessageDialog.openWarning(Workbench.getInstance()
					.getActiveWorkbenchWindow().getShell(), "ATTENTION",
					"Problème à l'annulation.");
			//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"Problème à l'annulation.","ERREUR",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void inserer(boolean before)throws Exception {
		try {
			if(!dataSetEnModif()){
				fIBQuery.insertRow(before);
				
				for (int i = 0; i < fIBQuery.columnCount(); i++) {
					//FListeChampValue.Values[FIBQuery.FieldList.Strings[i]] := C_STR_VIDE;
					//fListeChampValue.put(FIBQuery.getColumn(i).getColumnName(),Constantes.C_STR_VIDE);
					affecteChampQuery(i,(String) fListeChampValue.get(fIBQuery.getColumn(i).getColumnName()));
				}
				if (fIBQuLgrInsertion != null) { //if assigned(FIBQuLgrInsertion)
					fIBQuLgrInsertion.inserer(new BdLgrEvent(this));
				}
				else {
//					if not FIBQuery.Active then FIBQuery.Open;
//					if FIBQuery.State = dsInsert then MsgEtatDsInsert;
//					if FIBQuery.State = dsBrowse then FIBQuery.Insert;
				}
				fModeObjet.setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
			}
		}
		catch (Exception e) {
			logger.error("Erreur : inserer", e);
			//fModeObjet.setMode(ModeObjet.C_MO_ERREUR);
			throw new ExceptLgr("Problème à l'insertion.");
		}
	}
	
	public void inserer()throws Exception {
		inserer(false);
	}
	
	public boolean dataSetEnModif() {
		if(fModeObjet.getMode()==EnumModeObjet.C_MO_EDITION
				|| fModeObjet.getMode()==EnumModeObjet.C_MO_IMPORTATION
				|| fModeObjet.getMode()==EnumModeObjet.C_MO_INSERTION)
			return true;
		else
			return false;
	}
	
	public void addChangeModeListener(ChangeModeListener l) {
		fModeObjet.addChangeModeListener(l);
	}
	
	public void removeChangeModeListener(ChangeModeListener l) {
		fModeObjet.removeChangeModeListener(l);
	}
	
	public String selectCleEtrangere(String nomTableSelect, String champsSelect,int typeChampSelect, String champsValeur, String nouvelleValeur) throws SQLException{
		String debComparaison = new String(" = "); 
		String finComparaison = new String(" "); 		
		switch(typeChampSelect) {
		case Variant.STRING:
		case Variant.DATE:
			debComparaison=" like('";
			finComparaison="') ";
		}
		///
		String result = null;
		try {
			String r = "select "+champsSelect+" from "+nomTableSelect+" where "+ champsValeur + debComparaison + LibChaineSQL.formatStringSQL(nouvelleValeur) + finComparaison;
			PreparedStatement rqt = getFIBBase().getJdbcConnection().prepareStatement(r);
			ResultSet res = rqt.executeQuery();
			if(res.next()){
				
				//int i = res.getShort(1);
				result = res.getString(champsSelect);
			} 
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}	
	
	public String selectCleEtrangere(String nomTableSelect, String champsSelect, String champsValeur, String nouvelleValeur) throws SQLException{
		String result = null;
		try {
			String r = "select "+champsSelect+" from "+nomTableSelect+" where "+champsValeur+" like('" +
			LibChaineSQL.formatStringSQL(nouvelleValeur) + "')";
			//String r = "select "+champsSelect+" from "+nomTableSelect+" where "+champsValeur+" = '"+nouvelleValeur+"'";
			PreparedStatement rqt = getFIBBase().getJdbcConnection().prepareStatement(r);
			ResultSet res = rqt.executeQuery();
			if(res.next()){
				
				//int i = res.getShort(1);
				result = res.getString(champsSelect);
			} 
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}	
	
	/**
	 * Affecte <code>valeur</code> à <code>champ</code> en convertissant <code>valeur</code> en fonction du type de données
	 * contenu dans <code>champ</code>.
	 * Cette procédure ne tient pas compte du mode, elle affecte dans tous les cas
	 * @param champ String - nom du champ dans lequel il faut affecter la valeur
	 * @param valeur String - nouvelle valeur du champ
	 */
	//TODO Paramètre valeur non utilisé
	protected void affecteChampQuery(String champ, String valeur) throws	Exception {
//		renvoieValeurSurType(fIBQuery.getColumn(champ),fIBQuery,
//				(String) fListeChampValue.get(fIBQuery.getColumn(
//						champ).getColumnName()));	
		renvoieValeurSurType(fIBQuery.getColumn(champ),fIBQuery,
				getChamp_Obj(fIBQuery.getColumn(
						champ).getColumnName()));		
	}
	
	
	protected void renvoieValeurSurType(Column champ, DataSet dataset, String newValeur)throws Exception {
		if(fListeChampValue.get(champ.getColumnName())==null) {
			fIBQuery.setAssignedNull(champ.getColumnName());
		}
		else {
			
			switch (champ.getDataType()) {
			case Variant.STRING:
				annulerCodeGenere(dataset.getString(champ.getColumnName()),champ.getColumnName());
				dataset.setString(champ.getColumnName(),newValeur);
				if (newValeur!=null)
					rentreCodeGenere(newValeur,champ.getColumnName());
				break;
			case Variant.INT:
				dataset.setInt(champ.getColumnName(),
						(LibConversion.stringToInteger(newValeur)));
				break;
			case Variant.DOUBLE:	
				dataset.setDouble(champ.getColumnName(),
						(LibConversion.stringToDouble(newValeur)));
				break;
			case Variant.BIGDECIMAL:	
				dataset.setBigDecimal(champ.getColumnName(),
						(LibConversion.stringToBigDecimal(newValeur)));
				break;			
			case Variant.SHORT:
				if ( newValeur.equals("")){
					dataset.setAssignedNull(champ.getColumnName());
				}else{
					if(newValeur.equals("false")) {
						dataset.setShort(champ.getColumnName(),
								(LibConversion.stringToShort("0")));
					} else if (newValeur.equals("true")){
						dataset.setShort(champ.getColumnName(),
								(LibConversion.stringToShort("1")));
					} else
						dataset.setShort(champ.getColumnName(),
								(LibConversion.stringToShort(newValeur)));
				}
				break;
			case Variant.FLOAT:
				dataset.setFloat(champ.getColumnName(),
						(LibConversion.stringToFloat(newValeur)));
				break;
			case Variant.LONG:
				dataset.setLong(champ.getColumnName(),
						(LibConversion.stringToLong(newValeur)));
				break;				
			case Variant.DATE:
				
				//DateFormat dateFormat = DateFormat.getInstance();
				try {
					dataset.setDate(champ.getColumnName(),
							(df.parse(newValeur).getTime()));
				}
				/** @todo Gestion des exceptions */
				catch (ParseException ex) {
					logger.error("Erreur : renvoieValeurSurType", ex);
				}
				catch (DataSetException ex) {
					logger.error("Erreur : renvoieValeurSurType", ex);
				}
				break
				;
			case Variant.TIMESTAMP:
				
				//DateFormat dateFormat = DateFormat.getInstance();
				try {
					dataset.setTimestamp(champ.getColumnName(),
							(df.parse(newValeur).getTime()));
				}
				/** @todo Gestion des exceptions */
				catch (ParseException ex) {
					logger.error("Erreur : renvoieValeurSurType", ex);
				}
				catch (DataSetException ex) {
					logger.error("Erreur : renvoieValeurSurType", ex);
				}
				break
				;
			case Variant.BOOLEAN:
				dataset.setBoolean(champ.getColumnName(),
						(Boolean.getBoolean(newValeur)));
				break;
			default:
				try {
//					JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(), "Le DataType n''est pas défini :  "+champ.getDataType()+"; Champ : "+champ.getColumnName(),
//							"ERREUR", JOptionPane.WARNING_MESSAGE);
					throw new Exception("Le DataType n''est pas défini :  "+champ.getDataType()+"; Champ : "+champ.getColumnName());
				}
			finally {
			}
			}
		}
		
	}
	
	/**
	 * Mise à jour de la Query à partir de la liste
	 * Affecte <code>valeur</code> à <code>champ</code> en convertissant <code>valeur</code> en fonction du type de données
	 * contenu dans <code>champ</code>.
	 * Cette procédure ne tient pas compte du mode, elle affecte dans tous les cas
	 * @param champ int - position du champ dans lequel il faut affecter la valeur
	 * @param valeur String - nouvelle valeur du champ
	 * @see #affecteChampQuery(String, String) affecteChampQuery
	 */
	protected void affecteChampQuery(int champ, String valeur) throws
	Exception {
		try{
			affecteChampQuery(fIBQuery.getColumn(champ).getColumnName(), valeur);
		}
		catch(Exception ex) {
			logger.error("Erreur : affecteChampQuery", ex);
			throw new Exception(ex.getMessage());
		}
	}
	
	
	/**
	 * Mise a jour directe d'un champ dans la query et dans la liste. (Sans mode)
	 * @param champ - nom du champ dans la Query
	 * @param valeur - valeur a affecter
	 * @throws DataSetException
	 * @throws Exception
	 */
	public void swtAffecte(String champ, String valeur) throws DataSetException, Exception {
		//affectation dans le QueryDataset
		renvoieValeurSurType(fIBQuery.getColumn(champ),fIBQuery,valeur);
		
//		//affectation dans la liste
//		Iterator ite = fListeChampValue.keySet().iterator();
//		boolean trouve = false;
//		while(ite.hasNext() && !trouve) {
//			if(((String)ite.next()).equals(champ)) {
//				trouve = true;
//			}
//		}
		//if(trouve) {
			fListeChampValue.put(champ,valeur);
//		} else {
//			throw new ExceptLgr("Le champ "+champ+" n'existe pas dans la liste.");
//		}
	}
	/**
	 * Mise a jour directe d'un champ dans la query et dans la liste. (Sans mode)
	 * @param champ - nom du champ dans la Query
	 * @param valeur - valeur a affecter
	 * @throws DataSetException
	 * @throws Exception
	 */
	public void affecte(String champ, String valeur) throws DataSetException, Exception {
		//affectation dans le QueryDataset
		renvoieValeurSurType(fIBQuery.getColumn(champ),fIBQuery,valeur);
		
		//affectation dans la liste
		Iterator ite = fListeChampValue.keySet().iterator();
		boolean trouve = false;
		while(ite.hasNext() && !trouve) {
			if(((String)ite.next()).equals(champ)) {
				trouve = true;
			}
		}
		if(trouve) {
			fListeChampValue.put(champ,valeur);
		} else {
			throw new ExceptLgr("Le champ "+champ+" n'existe pas dans la liste.");
		}
	}
	
	/**
	 * Déplace le curseur d'un rang en avant à partir de la position actuelle,
	 * et actualise le contenu de la liste en mémoire
	 * @return boolean - vrai si le déplacement a été effectué
	 * @author nicolas
	 */
	public boolean next() {
		boolean res = false;
		if(fIBQuery.next()){
			getChamp_Obj_Liste();
			res = true;
		}
		return res;
	}
	
	/**
	 * Déplace le curseur d'un rang en arrière à partir de la position actuelle,
	 * et actualise le contenu de la liste en mémoire
	 * @return boolean - vrai si le déplacement a été effectué
	 * @author nicolas
	 */
	public boolean previous() {
		boolean res = false;
		if (fIBQuery.prior()) {
			getChamp_Obj_Liste();
			res = true;
		}
		return res;
	}
	
	/**
	 * Déplace le curseur de <code>pos</code> rang à partir de la position actuelle,
	 * et actualise le contenu de la liste en mémoire
	 * @param pos int - nombre de rang
	 * @return boolean - vrai si le déplacement a été effectué
	 * @author nicolas
	 */
	public boolean relative(int pos) {
		boolean res = false;
		if (fIBQuery.goToRow(pos)) {
			getChamp_Obj_Liste();
			res = true;
		}
		return res;
	}
	
	/**
	 * Déplace le curseur au début de l'ensemble de données,
	 * et actualise le contenu de la liste en mémoire
	 * @author nicolas
	 */
	public void first() {
		fIBQuery.first();
		getChamp_Obj_Liste();
	}
	
	/**
	 * Déplace le curseur a la fin de l'ensemble de données,
	 * et actualise le contenu de la liste en mémoire
	 * @author nicolas
	 */
	public void last() {
		fIBQuery.last();
		getChamp_Obj_Liste();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
	public void addBdLgrListener(BdLgrListener l) {
		listenerList.add(BdLgrListener.class, l);
	}
	
	public void removeBdLgrListener(BdLgrListener l) {
		listenerList.remove(BdLgrListener.class, l);
	}
	
//	Notify all listeners that have registered interest for
//	notification on this event type.  The event instance
//	is lazily created using the parameters passed into
//	the fire method.
	
	protected void fireBdLgrEnregistrer() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BdLgrListener.class) {
				// Lazily create the event:
				if (bdLgrEvent == null)
					bdLgrEvent = new BdLgrEvent(this);
				( (BdLgrListener) listeners[i + 1]).enregistrer(bdLgrEvent);
			}
		}
	}
	
	protected void fireBdLgrSupprimer() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BdLgrListener.class) {
				// Lazily create the event:
				if (bdLgrEvent == null)
					bdLgrEvent = new BdLgrEvent(this);
				( (BdLgrListener) listeners[i + 1]).supprimer(bdLgrEvent);
			}
		}
	}
	
	protected void fireBdLgrModifier() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BdLgrListener.class) {
				// Lazily create the event:
				if (bdLgrEvent == null)
					bdLgrEvent = new BdLgrEvent(this);
				( (BdLgrListener) listeners[i + 1]).modifier(bdLgrEvent);
			}
		}
	}
	
	protected void fireTBdLgrAnnuler() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BdLgrListener.class) {
				// Lazily create the event:
				if (bdLgrEvent == null)
					bdLgrEvent = new BdLgrEvent(this);
				( (BdLgrListener) listeners[i + 1]).annuler(bdLgrEvent);
			}
		}
	}
	
	protected void fireBdLgrInserer() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BdLgrListener.class) {
				// Lazily create the event:
				if (bdLgrEvent == null)
					bdLgrEvent = new BdLgrEvent(this);
				( (BdLgrListener) listeners[i + 1]).inserer(bdLgrEvent);
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//METHODES DES EVENEMENTS DE TABLE
	/**
	 * postRow - une ligne de données a changé et doit être posté
	 * @param event DataChangeEvent
	 */
	public void postRow(DataChangeEvent event) {
	}
	
	/**
	 * dataChanged - des données ont changé dans une ou plusieurs lignes
	 * @param event DataChangeEvent
	 */
	public void dataChanged(DataChangeEvent event) {
	}
	
	/**
	 * navigated - la ligne courante du DataSet a changé
	 * @param event NavigationEvent
	 */
	public void navigated(NavigationEvent event) {
	}
	
	/**
	 * accessChange - Evenement interne au DataSet => a ne pas utilisé correctement
	 * @param event AccessEvent
	 */
	public void accessChange(AccessEvent event) {
	}
	
	/**
	 * closed - Le DataSet été fermé correctement (after)
	 * @param dataSet DataSet
	 */
	public void closed(DataSet dataSet) {
	}
	
	/**
	 * closing - Le DataSet va être fermé (before)
	 * @param dataSet DataSet
	 */
	public void closing(DataSet dataSet) {
	}
	
	/**
	 * opened - Le DataSet été ouvert correctement (after)
	 * @param dataSet DataSet
	 */
	public void opened(DataSet dataSet) {
	}
	
	/**
	 * opening - Le DataSet va être ouvert (before)
	 * @param dataSet DataSet
	 */
	public void opening(DataSet dataSet) {
	}
	
	/**
	 * deleteError - Notifie qu'une exception a eu lieu pendant une opération de suppression de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void deleteError(DataSet dataSet, DataSetException ex,
			ErrorResponse response) {
	}
	
	/**
	 * updateError - Notifie qu'une exception a eu lieu pendant une opération de MAJ de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param row ReadWriteRow - la ligne qui contient l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void updateError(DataSet dataSet, ReadWriteRow row,
			DataSetException ex, ErrorResponse response) {
	}
	
	/**
	 * addError - Notifie qu'une exception a eu lieu pendant une opération d'ajout ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param row ReadWriteRow - la ligne qui contient l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void addError(DataSet dataSet, ReadWriteRow row, DataSetException ex,
			ErrorResponse response) {
	}
	
	/**
	 * editError - Notifie qu'une exception a eu lieu pendant une opération d'edition de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param column Column - La colonne qui contient l'erreur
	 * @param value Variant - La valeur qui a causé l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void editError(DataSet dataSet, Column column, Variant value,
			DataSetException ex, ErrorResponse response) {
	}
	
	/**
	 * Notifie qu'une nouvelle ligne (non postée) a été inséré dans le DataSet
	 * @param dataSet DataSet
	 */
	public void inserted(DataSet dataSet) {
	}
	
	/**
	 * Notifie qu'une nouvelle ligne (non postée) va être inséré dans le DataSet
	 * @param dataSet DataSet
	 */
	public void inserting(DataSet dataSet) {
	}
	
	/**
	 * Notifie que l'utilisateur commence à modifier une ligne existante
	 * @param dataSet DataSet
	 */
	public void modifying(DataSet dataSet) {
	}
	
	/**
	 * Notifie qu'une opération de suppression s'est correctement terminée.
	 * @param dataSet DataSet
	 */
	public void deleted(DataSet dataSet) {
	}
	
	/**
	 * Notifie qu'une ligne va être supprimé du DataSet
	 * @param dataSet DataSet
	 */
	public void deleting(DataSet dataSet) {
	}
	
	/**
	 * Notifie qu'une nouvelle ligne a correctement été inséré (postée) dans le DataSet
	 * @param dataSet DataSet
	 */
	public void added(DataSet dataSet) {
	}
	
	/**
	 * Notifie qu'une nouvelle ligne va être inséré (postée) dans le DataSet
	 * @param dataSet DataSet
	 * @param newRow ReadWriteRow
	 */
	public void adding(DataSet dataSet, ReadWriteRow newRow) {
	}
	
	/**
	 * Notifie q'une ligne modifiée a correctement été postée dans le DataSet
	 * @param dataSet DataSet
	 */
	public void updated(DataSet dataSet) {
	}
	
	/**
	 * Notifie q'une ligne modifiée va être posté dans le DataSet
	 * @param dataSet DataSet
	 * @param newRow ReadWriteRow - La ligne qui contient les données modifiées
	 * @param oldRow ReadRow - La ligne qui contient les données avant la modification
	 */
	public void updating(DataSet dataSet, ReadWriteRow newRow, ReadRow oldRow) {
	}
	
	/**
	 * Notifie que l'édition d'une nouvelle ligne ou d'une ligne existante du DataSet va être annulé.
	 * @param dataSet DataSet
	 */
	public void canceling(DataSet dataSet) {
	}
	
	public boolean verifChamp(String nomChamp, Integer nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.intValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Object nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			if(nouvelleValeur instanceof Float 
					|| nouvelleValeur instanceof Integer
					|| nouvelleValeur instanceof BigDecimal
					|| nouvelleValeur instanceof Double
					|| nouvelleValeur instanceof Short
					|| nouvelleValeur instanceof Boolean
					//|| nouvelleValeur instanceof Date
					|| nouvelleValeur instanceof String)
				return verifChamp(nomChamp,nouvelleValeur.toString(), nomChampEtranger,  nomTableEtrangere);
			else if(nouvelleValeur instanceof Date)
				return verifChamp(nomChamp,(Date)nouvelleValeur, nomChampEtranger,  nomTableEtrangere);
			else {
				//JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(),"L'objet n'est pas vérifiable : "+nouvelleValeur.toString(),"ERREUR",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Float nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.floatValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, BigDecimal nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.doubleValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Double nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.doubleValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Short nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.shortValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Boolean nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null)
			return verifChamp(nomChamp,String.valueOf(nouvelleValeur.booleanValue()), nomChampEtranger,  nomTableEtrangere);
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(String nomChamp, Date nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		String tmp = null;
		if(nouvelleValeur!=null) {
			return verifChamp(nomChamp,LibDate.dateToString(nouvelleValeur), nomChampEtranger,  nomTableEtrangere);
		}
		else
			return verifChamp(nomChamp,tmp, nomChampEtranger,  nomTableEtrangere);
		//return false;
	}
	
	public boolean verifChamp(ValeurChamps valeurChamps, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		boolean res = false;
		
		if(valeurChamps.getValeur()!=null) { //traitement des valeurs booleennes
			if(valeurChamps.getValeur().equals("true")) valeurChamps.setValeur("1");
			else if (valeurChamps.getValeur().equals("false")) valeurChamps.setValeur("0");
		}
		//try {
			MessCtrlLgr ex = new MessCtrlLgr();
			ex.setModeObjet(this.getFModeObjet());
			ex.setNomChamp(valeurChamps.getChamps().toUpperCase());      
			ex.setNomTable(nomTable);
			ex.setValeur(valeurChamps.getValeur());
			ex.setOld_Valeur(this.getFChamp_Query(valeurChamps.getChamps().toUpperCase()));
			ex.setID_Valeur(this.getFChamp_Query(champIdTable));
			
			if (nomChampEtranger != null && nomTableEtrangere != null) {
				ex.setNomChampEtranger(nomChampEtranger);
				ex.setNomTableEtrangere(nomTableEtrangere);
			}
			if(listeRelation.containsKey(ex.getNomChamp())) {
				if  (listeRelation.get(ex.getNomChamp())!=null ){
					update.setMode(this.getFModeObjet());
					MessCtrlLgr exTmp = new MessCtrlLgr();
					exTmp.setModeObjet(this.getFModeObjet());
					exTmp.setNomChamp(valeurChamps.getChamps().toUpperCase());
					exTmp.setNomTable(nomTable);
					exTmp.setValeur(valeurChamps.getValeur());
					exTmp.setOld_Valeur(this.getFChamp_Query(valeurChamps.getChamps().toUpperCase()));
					exTmp.setID_Valeur(this.getFChamp_Query(champIdTable));				
					exTmp.setNomChampEtranger(ex.getNomChamp());
					
					exTmp.setNomTableEtrangere(((String[])listeRelation.get(ex.getNomChamp().toString()))[1]);
					
					if (valeurChamps.getValeur()!=null && valeurChamps.getValeur().equals("") && ((String[])listeRelation.get(ex.getNomChamp().toString()))[3]=="1")
						throw new ExceptLgr("La valeur du champ "+exTmp.getNomChampEtranger()+
								" est vide ",0,true,0);
					else 
						if(valeurChamps.getValeur()!=null &&  ((String[])listeRelation.get(ex.getNomChamp().toString()))[3]!="-1")
						try {
							listeMAJVue.put(
									((String[])listeRelation.get(ex.getNomChamp().toUpperCase().toString()))[0],
									selectCleEtrangere(
											((String[])listeRelation.get(ex.getNomChamp().toString()))[1],
											((String[])listeRelation.get(ex.getNomChamp().toString()))[2],
											fIBQuery.getColumn(ex.getNomChamp().toString()).getDataType(),
											ex.getNomChamp(),
											valeurChamps.getValeur())
							);
						} catch (SQLException e) {
							throw new ExceptLgr("Problème de mise à jour directe",0,true,0);
						}
				}
			}else {
				if (valeurChamps.getValeur()!=null && (fIBQuery.getColumn(valeurChamps.getChamps().toUpperCase()).getDataType()==Variant.DATE ||
						fIBQuery.getColumn(valeurChamps.getChamps().toUpperCase()).getDataType()==Variant.TIMESTAMP)){
					valeurChamps.setValeur(LibDate.dateToString(LibDate.stringToDateNew(valeurChamps.getValeur())));
					listeMAJVue.put(valeurChamps.getChamps().toUpperCase(),LibDate.dateToStringSql(LibDate.stringToDate(valeurChamps.getValeur())));
				}
				else	
					listeMAJVue.put(valeurChamps.getChamps().toUpperCase(),valeurChamps.getValeur());
			}			//le controle spécifique déclenche ou non le contrôle général
			ctrlSaisieSpecifique(valeurChamps,ex);
			//ctrlSaisieSpecifique(valeurChamps.getChamps(),valeurChamps.getValeur(),ex);
			
			this.setChamp_Obj(valeurChamps.getChamps().toUpperCase(),valeurChamps.getValeur());
			res = true;
			return res;
		//}
		//catch (Exception ex) {
		//	logger.error("Erreur : verifChamp", ex);
		//	return false;
		//}
	}
	
	public boolean verifChamp(String nomChamp, String nouvelleValeur, String nomChampEtranger, String nomTableEtrangere) throws ExceptLgr, ParseException {
		ValeurChamps valeurChamps = new ValeurChamps (nouvelleValeur,nomChamp);
		return verifChamp( valeurChamps,  nomChampEtranger,  nomTableEtrangere);
	}
	
	
	public Map<String,String[]> getParamWhereSQL() {
		return paramWhereSQL;
	}
	
	public void setParamWhereSQL(Map<String,String[]> paramWhereSQL) {
		this.paramWhereSQL = paramWhereSQL;
		changeCloseWhere();
	}
	
	/**
	 * Creation de la nouvelle requete à partir de <code>paramWhereSQL</code> et ajout
	 * d'une close "where" à la fin de celle-ci. La nouvelle requete remplace la 
	 * précédente dans <code>fIBQuery</code>.
	 */
	protected void changeCloseWhere() {
		String nouvelleRequete = "";
		boolean wasOpen = false;
		nouvelleRequete = this.fIBQuery.getQuery().getQueryString();
		boolean debutRqt = false;
		if(!paramWhereSQL.isEmpty()) {
			if (!nouvelleRequete.toUpperCase().contains("WHERE")){
				nouvelleRequete += " where ";					
				debutRqt = true;
			}
			for(String champs : paramWhereSQL.keySet()) {
				if(!debutRqt)
					nouvelleRequete+=" and ";
				else
					debutRqt = false;
				nouvelleRequete += champs + " " + paramWhereSQL.get(champs)[0] 
				                                                            +" "+ paramWhereSQL.get(champs)[1];
			}
		}
		
		if(this.fIBQuery.isOpen()) {
			this.fIBQuery.close();
			wasOpen = true;
		}
		
		this.fIBQuery.setQuery(new QueryDescriptor(this.fIBQuery.getDatabase(),
				nouvelleRequete,
				this.fIBQuery.getQuery().isExecuteOnOpen())
		);
		
		if(wasOpen)
			this.fIBQuery.open();
	}
	
	public HashMap getListeChampsCalcules() {
		return listeChampsCalcules;
	}
	
	public void setListeChampsCalcules(HashMap listeChampsCalcules) {
		this.listeChampsCalcules = listeChampsCalcules;
	}
	
	public static GestionModif getModif() {
		return modif;
	}
	
	/**
	 * Positionnement sur une ligne en fonction de son id
	 * @param id - id recherché
	 * @return - vrai ssi la ligne a été trouver
	 */
	public boolean lgrLocateID(String id) {
		DataRow courant = new DataRow(fIBQuery,update.getIdChamps());
		courant.setInt(update.getIdChamps(),LibConversion.stringToInteger(id));
		return fIBQuery.locate(courant,32);
	}
	
	
	public void rollbackLgr() {
		logger.info("RollbackLgr");
		fIBBase.rollback();
		if(dataSetEnModif())
			try {
				rentreEnModification();
			} catch (Exception e) {
				// TODO Bloc catch auto-généré
				logger.debug(e);
			}
	}
	
	public void commitLgr() {
		logger.info("commitLgr");
		fIBBase.commit();
		try {
			annuleModification(true);
		} catch (Exception e) {
			// TODO Bloc catch auto-généré
			logger.debug(e);
		}
	}
	
	
	public void setListeChampMaj(String fileName){
		try {
			if (!new File(fileName).exists()) {
				MessageDialog.openWarning(Workbench.getInstance()
						.getActiveWorkbenchWindow().getShell(), "ATTENTION",
						"Le fichier .properties "+ fileName + " est inexistant");
			} else {
				FileInputStream file = new FileInputStream(fileName);
				listeChampMaj.load(file);
				file.close();
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setListeChampMaj", e);
		}
	}
	
	public static Properties getListeChampMaj() {
		return listeChampMaj;
	}
	
	
	
	
	
	/**
	 * @throws Throwable the <code>Exception</code> raised by this method
	 */
	protected void finalize() throws Throwable { //destructor Destroy; override;
		destroy();
		if (getFIBQuery()!=null){
			getFIBQuery().close();
			getFIBQuery().setQuery(null);
		}
	}	
//	
	public void destroy(DestroyEvent evt) {
		try {
			this.finalize();
		} catch (Throwable e) {
			logger.debug("",e);
		}		
	}
	
	public void destroy(){
		this.fModeObjet.destroy();
	}
	public String getNomTable() {
		return nomTable;
	}
	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}	
	
} //fin de la classe IBQuLgr


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


//EVENEMENTS DE LA TABLE
class TableDataChangeListener
implements DataChangeListener {
	IBQuLgr ibQuLgr;
	
	TableDataChangeListener(IBQuLgr ibQuLgr) {
		this.ibQuLgr = ibQuLgr;
	}
	
	/**
	 * postRow - une ligne de données a changé et doit être posté
	 * @param event DataChangeEvent
	 */
	public void postRow(DataChangeEvent event) {
		ibQuLgr.postRow(event);
	}
	
	/**
	 * dataChanged - des données ont changé dans une ou plusieurs lignes
	 * @param event DataChangeEvent
	 */
	public void dataChanged(DataChangeEvent event) {
		ibQuLgr.dataChanged(event);
	}
}

class TableNavigationListener
implements NavigationListener {
	IBQuLgr ibQuLgr;
	
	TableNavigationListener(IBQuLgr ibQuLgr) {
		this.ibQuLgr = ibQuLgr;
	}
	
	/**
	 * navigated - la ligne courante du DataSet a changé
	 * @param event NavigationEvent
	 */
	public void navigated(NavigationEvent event) {
		ibQuLgr.navigated(event);
	}
}


//class Transaction implements TransactionSupport{
//	IBQuLgr ibQuLgrLocal=null;
//	public Transaction(IBQuLgr ibQuLgr){
//		this.ibQuLgrLocal = ibQuLgr;
//	}
//	public void rollback()  {
//		if(ibQuLgrLocal.dataSetEnModif())
//			try {
//				ibQuLgrLocal.rentreEnModification();
//			} catch (Exception e) {
//				// TODO Bloc catch auto-généré
//				new ExceptLgr("");
//			}
//	}
//
//	public void commit() {
//		try {
//			ibQuLgrLocal.annuleModification(true);
//		} catch (Exception e) {
//			// TODO Bloc catch auto-généré
//			new ExceptLgr("");
//		}
//	}
//
//	public void start() {
//		// TODO Raccord de méthode auto-généré
//		
//	}
//	
//}

class TableAccessListener
implements AccessListener {
	IBQuLgr ibQuLgr;
	
	TableAccessListener(IBQuLgr ibQuLgr) {
		this.ibQuLgr = ibQuLgr;
	}
	
	/**
	 * accessChange - Evenement interne au DataSet => a ne pas utilisé correctement
	 * @param event AccessEvent
	 */
	public void accessChange(AccessEvent event) {
		ibQuLgr.accessChange(event);
	}
}

class TableOpenListener
implements OpenListener {
	IBQuLgr ibQuLgr;
	
	TableOpenListener(IBQuLgr ibQuLgr) {
		this.ibQuLgr = ibQuLgr;
	}
	
	/**
	 * closed - Le DataSet été fermé correctement (after)
	 * @param dataSet DataSet
	 */
	public void closed(DataSet dataSet) {
		ibQuLgr.closed(dataSet);
	}
	
	/**
	 * closing - Le DataSet va être fermé (before)
	 * @param dataSet DataSet
	 */
	public void closing(DataSet dataSet) {
		ibQuLgr.closing(dataSet);
	}
	
	/**
	 * opened - Le DataSet été ouvert correctement (after)
	 * @param dataSet DataSet
	 */
	public void opened(DataSet dataSet) {
		ibQuLgr.opened(dataSet);
	}
	
	/**
	 * opening - Le DataSet va être ouvert (before)
	 * @param dataSet DataSet
	 */
	public void opening(DataSet dataSet) {
		ibQuLgr.opening(dataSet);
	}
}


//class 


class TableEditListener
implements EditListener {
	IBQuLgr ibQuLgr;
	
	TableEditListener(IBQuLgr ibQuLgr) {
		this.ibQuLgr = ibQuLgr;
	}
	
	/**
	 * deleteError - Notifie qu'une exception a eu lieu pendant une opération de suppression de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void deleteError(DataSet dataSet, DataSetException ex,
			ErrorResponse response) {
		ibQuLgr.deleteError(dataSet,ex,response);
	}
	
	/**
	 * updateError - Notifie qu'une exception a eu lieu pendant une opération de MAJ de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param row ReadWriteRow - la ligne qui contient l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void updateError(DataSet dataSet, ReadWriteRow row,
			DataSetException ex, ErrorResponse response) {
		ibQuLgr.updateError(dataSet,row,ex,response);
	}
	
	/**
	 * addError - Notifie qu'une exception a eu lieu pendant une opération d'ajout ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param row ReadWriteRow - la ligne qui contient l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void addError(DataSet dataSet, ReadWriteRow row, DataSetException ex,
			ErrorResponse response) {
		ibQuLgr.addError(dataSet,row,ex,response);
	}
	
	/**
	 * editError - Notifie qu'une exception a eu lieu pendant une opération d'edition de ligne
	 * @param dataSet DataSet - le dataset qui est concerné par l'erreur
	 * @param column Column - La colonne qui contient l'erreur
	 * @param value Variant - La valeur qui a causé l'erreur
	 * @param ex DataSetException - Le type d'erreur
	 * @param response ErrorResponse - Le type de réponse
	 */
	public void editError(DataSet dataSet, Column column, Variant value,
			DataSetException ex, ErrorResponse response) {
		ibQuLgr.editError(dataSet,column,value,ex,response);
	}
	
	/**
	 * Notifie qu'une nouvelle ligne (non postée) a été inséré dans le DataSet
	 * @param dataSet DataSet
	 */
	public void inserted(DataSet dataSet) {
		ibQuLgr.inserted(dataSet);
	}
	
	/**
	 * Notifie qu'une nouvelle ligne (non postée) va être inséré dans le DataSet
	 * @param dataSet DataSet
	 */
	public void inserting(DataSet dataSet) {
		ibQuLgr.inserting(dataSet);
	}
	
	/**
	 * Notifie que l'utilisateur commence à modifier une ligne existante
	 * @param dataSet DataSet
	 */
	public void modifying(DataSet dataSet) {
		ibQuLgr.modifying(dataSet);
	}
	
	/**
	 * Notifie qu'une opération de suppression s'est correctement terminée.
	 * @param dataSet DataSet
	 */
	public void deleted(DataSet dataSet) {
		ibQuLgr.deleted(dataSet);
	}
	
	/**
	 * Notifie qu'une ligne va être supprimé du DataSet
	 * @param dataSet DataSet
	 */
	public void deleting(DataSet dataSet) {
		ibQuLgr.deleting(dataSet);
	}
	
	/**
	 * Notifie qu'une nouvelle ligne a correctement été inséré (postée) dans le DataSet
	 * @param dataSet DataSet
	 */
	public void added(DataSet dataSet) {
		ibQuLgr.added(dataSet);
	}
	
	/**
	 * Notifie qu'une nouvelle ligne va être inséré (postée) dans le DataSet
	 * @param dataSet DataSet
	 * @param newRow ReadWriteRow
	 */
	public void adding(DataSet dataSet, ReadWriteRow newRow) {
		ibQuLgr.adding(dataSet,newRow);
	}
	
	/**
	 * Notifie q'une ligne modifiée a correctement été postée dans le DataSet
	 * @param dataSet DataSet
	 */
	public void updated(DataSet dataSet) {
		ibQuLgr.updated(dataSet);
	}
	
	/**
	 * Notifie q'une ligne modifiée va être posté dans le DataSet
	 * @param dataSet DataSet
	 * @param newRow ReadWriteRow - La ligne qui contient les données modifiées
	 * @param oldRow ReadRow - La ligne qui contient les données avant la modification
	 */
	public void updating(DataSet dataSet, ReadWriteRow newRow, ReadRow oldRow) {
		ibQuLgr.updating(dataSet,newRow,oldRow);
	}
	
	/**
	 * Notifie que l'édition d'une nouvelle ligne ou d'une ligne existante du DataSet va être annulé.
	 * @param dataSet DataSet
	 */
	public void canceling(DataSet dataSet) {
		ibQuLgr.canceling(dataSet);
	}
	


//	public class ChampValeur extends java.lang.Object{
//	private String champs = null;
//	private String valeur = null;
//	
//	public String getChamps() {
//	return champs;
//	}
//	public void setChamps(String champs) {
//	this.champs = champs;
//	}
//	public String getValeur() {
//	return valeur;
//	}
//	public void setValeur(String valeur) {
//	this.valeur = valeur;
//	}
//	}
}


