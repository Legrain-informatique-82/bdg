package fr.legrain.gestCom.librairiesEcran.swt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.gui.PaResultatVisu;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.grille.LgrTableViewer;
//import fr.legrain.visualisation.controller.ModelGeneralObjetVisualisation;
//import fr.legrain.visualisation.controller.Resultat;

public class OngletResultatController {

	static Logger logger = Logger.getLogger(OngletResultatController.class.getName());

	private PaResultatVisu vue;
	private LgrTableViewer tableViewerResultat;
	private String[] listeChamp;
	private String[] titreChamp;
	private String[] tailleChamp;
	private String queryDeb;
	private String queryWhere;
	private String queryHaving;
	private String queryOrderBy;
	private ArrayList<String> listeDBOOL;
	private String groupBy;
	private String[] listeParam;
	private String paramProcedure;

	protected String identifiant;
	protected String idEditor;
	protected String idPlugin;

	protected Object listeInput;
	protected ISelection selectionRqt;
	protected String impression;

	public OngletResultatController(PaResultatVisu vue,String[]listeChamp ,
			String[] tailleChamp,String[]titreChamp , String queryDeb,String paramProcedure,String queryWhere,String queryHaving, ISelection selectionRqt, 
			String identifiant, String idEditor, Object listeInput,String impression,String idPlugin,String groupBy,String orderBy,String[] listeParam) {
		this.vue = vue;
		this.listeChamp = listeChamp;
		this.titreChamp = titreChamp;
		this.tailleChamp = tailleChamp;
		this.queryDeb = queryDeb;
		this.queryWhere = queryWhere;
		this.queryHaving = queryHaving;
		this.queryOrderBy =orderBy; 
		this.identifiant = identifiant;
		this.idEditor = idEditor;
		setListeInput(listeInput);
		this.selectionRqt = selectionRqt;
		this.impression = impression;
		this.idPlugin = idPlugin;
		this.groupBy = groupBy;
		this.listeParam = listeParam;
		this.paramProcedure = paramProcedure;

		bindResultat(titreChamp,tailleChamp);
		//initGrilleResultat(queryDeb+paramProcedure+queryWhere+" "+groupBy+" "+queryHaving+" "+queryOrderBy);
		initController();
	}

	private void initController() {
		vue.getGrilleResultat().addMouseListener(new MouseAdapter(){

			public void mouseDoubleClick(MouseEvent e) {
				String valeurIdentifiant = vue.getGrilleResultat().getSelection()[0].getText(findPositionNomChamp(identifiant));
				ouvreDocument(valeurIdentifiant);
			}

		});
	}

	public void bindResultat(String[] listeChamps,String[] tailleChamps) {			
		tableViewerResultat = new LgrTableViewer(vue.getGrilleResultat());
		tableViewerResultat.createTableCol( vue.getGrilleResultat(),titreChamp,tailleChamp);
	}
	
//	protected void rechercheJPA(String requete) {
//		try {
//			Query ejbQuery = JPABdLgr.getEntityManager().createQuery(requete);
//			List<Object> l = ejbQuery.getResultList();
//			ModelGeneralObjetVisualisation<Object> model = new ModelGeneralObjetVisualisation<Object>(l,Resultat.class);
//			model.remplirListe();
//			
//			
//		} catch (RuntimeException re) {
//			logger.error("",re);
//		}
//	}

	public void initGrilleResultat(String requete){
		ResultSet rs;
		try {
			vue.getGrilleResultat().removeAll();
			ResultSetMetaData rsMetadata = IB_APPLICATION.findDatabase().createPreparedStatement(requete).getMetaData();
			TableItem item = null;
			rs = IB_APPLICATION.findDatabase().createStatement().executeQuery(requete);

			for (int k = 1; k < rs.getMetaData().getColumnCount(); k++) {
				switch(rs.getMetaData().getColumnType(k+1)) {
				case Types.INTEGER :
				case Types.NUMERIC:
				case Types.FLOAT:
					vue.getGrilleResultat().getColumn(k).setAlignment(SWT.RIGHT);
					break;
				}
			}
			listeDBOOL=new ArrayList<String>();
			//parcourir le metadata pour regarder s'il y a des champs DBOOL
			for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
				if(rsMetadata.getColumnType(i)==Types.SMALLINT){				
					ResultSet rsTypes = IB_APPLICATION.findDatabase().
					createStatement().executeQuery("select distinct(RDB$FIELD_SOURCE) from " +
							"RDB$RELATION_FIELDS	where  RDB$FIELD_NAME = '"+rsMetadata.getColumnName(i)+"' " +
									"and RDB$FIELD_SOURCE = 'DBOOL'");
					if(rsTypes.next()){
						listeDBOOL.add(rsMetadata.getColumnName(i));	
					}
				}
			}
			
			int j = 1;
			rs = IB_APPLICATION.findDatabase().createStatement().executeQuery(requete);

			String[] totaux = null;
			while (rs.next()) {
				if(totaux==null) totaux = new String[rs.getMetaData().getColumnCount()];				
				item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
				String[] valeur = new String[rs.getMetaData().getColumnCount()];
				for (int h = 1; h <= rs.getMetaData().getColumnCount(); h++) {
					if(rs.getMetaData().getColumnType(h)==Types.DATE ||rs.getMetaData().getColumnType(h)==Types.TIMESTAMP)
						valeur[h-1]=valueDate(rs.getDate(h));
					else
						if(rs.getMetaData().getColumnType(h)==Types.SMALLINT && 
								isChampDbool(rs.getMetaData().getColumnName(h))){
							//if(rs.getBoolean(h))
								valeur[h-1]=LibConversion.booleanToStringFrancais(rs.getBoolean(h));
							}
						else
						valeur[h-1]=rs.getString(h);
					switch(rs.getMetaData().getColumnType(h)) {
					case Types.INTEGER :
						totaux[h-1] = String.valueOf(LibConversion.stringToInteger(totaux[h-1]) + rs.getInt(h));
						break;
					case Types.NUMERIC:
						totaux[h-1] = String.valueOf(LibConversion.stringToDouble(totaux[h-1]) + rs.getDouble(h));
						break;
					case Types.FLOAT:
						totaux[h-1] = String.valueOf(LibConversion.stringToDouble(totaux[h-1]) + rs.getDouble(h));
						break;
					default:
						totaux[h-1] = null;						
					}
				}
				item.setText(valeur);
				j++;
			}
			if(totaux!=null){
			for (int i = 0; i < totaux.length; i++) {
				totaux[i]=String.valueOf(
					LibCalcul.arrondi(LibConversion.stringToDouble(totaux[i])));
			}
			Color color =new Color(vue.getDisplay(),94,190,228);
			Font f =  new Font(vue.getDisplay(),"Tahoma",8,SWT.BOLD);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item.setBackground(color);
			item.setText("Totaux : ");
			item.setFont(f);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item.setBackground(color);
			if (totaux!=null)
				item.setText(totaux);
			item.setFont(f);
			}
		} catch (Exception e) {
			logger.error("", e);
		}			
	}

	public boolean isChampDbool(String champ){
		if(listeDBOOL!=null){
			for (int i = 0; i < listeDBOOL.size(); i++) {
				if(listeDBOOL.get(i).equalsIgnoreCase(champ))
					return true;
			}
		}
		return false;
		
	}
	
	public String valueDate(Date valeur) {
		String dateString = "";
		if(valeur!=null){
			java.util.Date a = valeur;
			java.text.DateFormat dataFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");

			dateString = dataFormat.format(a).toString();
		}
		return dateString;
	}
	
	public void dispose(){
		tableViewerResultat.destroy();
		listeDBOOL = null;
		listeChamp = null;
		titreChamp = null;
		tailleChamp = null;
		listeInput = null;
		selectionRqt = null;
	}

	public void ouvreDocument(String valeurIdentifiant){
		IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
		if(editor==null) {
			try {
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new IEditorInput(){

					public boolean exists() {
						return false;
					}
					public ImageDescriptor getImageDescriptor() {
						return null;
					}
					public String getName() {
						return "";
					}
					public IPersistableElement getPersistable() {
						return null;
					}
					public String getToolTipText() {
						return "";
					}
					public Object getAdapter(Class adapter) {
						return null;
					}
				}, idEditor);

				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);


				ParamAffiche paramAffiche = new ParamAffiche();
				if(e instanceof AbstractLgrMultiPageEditor) {
					paramAffiche.setCodeDocument(valeurIdentifiant);
					((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
				} else {
					((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
					paramAffiche.setCodeDocument(valeurIdentifiant);
					((LgrEditorPart)e).getController().configPanel(paramAffiche);
				}

			} catch (PartInitException e1) {
				logger.error("",e1);
			}
		} else {
			if(MessageDialog.openQuestion(
					vue.getShell(),
					"Affichage document",
			"Voulez-vous abandonner le document en cours de saisie ?")){
				ParamAffiche paramAffiche = new ParamAffiche();
				if(editor instanceof AbstractLgrMultiPageEditor) {
					paramAffiche.setCodeDocument(valeurIdentifiant);
					((AbstractLgrMultiPageEditor)editor).findMasterController().configPanel(paramAffiche);
				}
			}
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editor);
		}
	}

	public int findPositionColonne(String titreColonne){
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<vue.getGrilleResultat().getColumns().length){
			if(vue.getGrilleResultat().getColumns()[i].getText().equals(titreColonne)) {
				trouve = true;
			}
			i++;
		}
		if(trouve)
			return i-1;
		else
			return -1;
	}

	public String findTitreColonne(String titreColonne){
		int pos = findPositionColonne(titreColonne);
		if(pos!=-1) {
			return vue.getGrilleResultat().getColumns()[pos].getText();
		} else {
			return null;
		}
	}

	public int findPositionNomChamp(String nomChamp){
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<listeChamp.length){
			if(listeChamp[i].equalsIgnoreCase(nomChamp)) {
				trouve = true;
			}
			i++;
		}
		if(trouve)
			return i-1;
		else
			return -1;
	}

	public String findNomChamp(String nomChamp){
		int pos = findPositionNomChamp(nomChamp);
		if(pos!=-1) {
			return listeChamp[pos];
		} else {
			return null;
		}
	}

	public LgrTableViewer getTableViewerResultat() {
		return tableViewerResultat;
	}

	public void setTableViewerResultat(LgrTableViewer tableViewerResultat) {
		this.tableViewerResultat = tableViewerResultat;
	}

	public PaResultatVisu getVue() {
		return vue;
	}

	public void setVue(PaResultatVisu vue) {
		this.vue = vue;
	}

	public String[] getTitreChamp() {
		return titreChamp;
	}

	public void setTitreChamp(String[] titreChamp) {
		this.titreChamp = titreChamp;
	}

	public String[] getTailleChamp() {
		return tailleChamp;
	}

	public void setTailleChamp(String[] tailleChamp) {
		this.tailleChamp = tailleChamp;
	}

	public String getQueryDeb() {
		return queryDeb;
	}

	public void setQueryDeb(String query) {
		this.queryDeb = query;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getIdEditor() {
		return idEditor;
	}

	public void setIdEditor(String idEditor) {
		this.idEditor = idEditor;
	}

	public Object getListeInput() {
		return listeInput;
	}

	public void setListeInput(Object listeInput) {
		this.listeInput = listeInput;
	}

	public ISelection getSelectionRqt() {
		return selectionRqt;
	}

	public void setSelectionRqt(ISelection selectionRqt) {
		this.selectionRqt = selectionRqt;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
	}

	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	public String getIdPlugin() {
		return idPlugin;
	}

	public void setIdPlugin(String idPlugin) {
		this.idPlugin = idPlugin;
	}

	public ArrayList<String> getListeDBOOL() {
		return listeDBOOL;
	}

	public void setListeDBOOL(ArrayList<String> listeDBOOL) {
		this.listeDBOOL = listeDBOOL;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}


	public String[] getListeParam() {
		return listeParam;
	}

	public void setListeParam(String[] listeParam) {
		this.listeParam = listeParam;
	}

	public String getParamProcedure() {
		return paramProcedure;
	}

	public void setParamProcedure(String paramProcedure) {
		this.paramProcedure = paramProcedure;
	}

	public String getQueryHaving() {
		return queryHaving;
	}

	public void setQueryHaving(String queryHaving) {
		this.queryHaving = queryHaving;
	}

	public String getQueryOrderBy() {
		return queryOrderBy;
	}

	public void setQueryOrderBy(String queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}


}
