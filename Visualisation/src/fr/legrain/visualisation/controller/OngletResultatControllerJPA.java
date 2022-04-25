package fr.legrain.visualisation.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.omg.CORBA.portable.ValueOutputStream;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.gui.PaResultatVisu;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.MyLabelProvider;
import fr.legrain.librairiesLeGrain.LibrairiesLeGrainPlugin;

public class OngletResultatControllerJPA {

	static Logger logger = Logger.getLogger(OngletResultatControllerJPA.class.getName());

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
	private String titre;
	
	protected String[] colonnesTotaux;
	protected String[] typesRetour;

	protected String identifiant;
	protected String idEditor;
	protected String idPlugin;
	
	protected String queryLang;
	protected String classeRecherche;
	

	protected Object listeInput;
	protected ISelection selectionRqt;
	protected String impression;
	
	private String[] totaux = null;
	private Integer nbResultat=0;

	
	public Integer getNbResultat() {
		return nbResultat;
	}

	public void setNbResultat(Integer nbResultat) {
		this.nbResultat = nbResultat;
	}

	private ModelGeneralObjetVisualisation<Resultat> modelImpression;

	public OngletResultatControllerJPA(PaResultatVisu vue,String[]listeChamp ,
			String[] tailleChamp,String[]titreChamp , String queryDeb,String paramProcedure,String queryWhere,String queryHaving, ISelection selectionRqt, 
			String identifiant, String idEditor, Object listeInput,String impression,String idPlugin,String groupBy,String orderBy,
			String[] listeParam,String titre,String[] colonnesTotaux,String[] typesRetour,String queryLang,String classeRecherche) {
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
		this.titre = titre;
		this.typesRetour = typesRetour;
		this.colonnesTotaux = colonnesTotaux;
		this.queryLang = queryLang;
		this.classeRecherche = classeRecherche;

		bindResultat(titreChamp,tailleChamp);
		//initGrilleResultat(queryDeb+paramProcedure+queryWhere+" "+groupBy+" "+queryHaving+" "+queryOrderBy);
		initGrilleResultat(queryDeb);
		initController();
	}

	private void initController() {
		vue.getGrilleResultat().addMouseListener(new MouseAdapter(){

			public void mouseDoubleClick(MouseEvent e) {
				String valeurIdentifiant = vue.getGrilleResultat().getSelection()[0].getText(findPositionNomChamp(identifiant));
//				ouvreDocument(valeurIdentifiant);
				LgrPartUtil.ouvreDocument(valeurIdentifiant, idEditor);
			}

		});
	}

	public void bindResultat(String[] listeChamps,String[] tailleChamps) {			
		tableViewerResultat = new LgrTableViewer(vue.getGrilleResultat());
		tableViewerResultat.createTableCol( vue.getGrilleResultat(),titreChamp,tailleChamp);
		//tableViewerResultat.tri(String.class,titreChamp,tailleChamp);

		initInfosPresentation();
	}
	
	private void initInfosPresentation() {
		infos = new InfosPresentation[titreChamp.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = new InfosPresentation();
			infos[i].titre=titreChamp[i];
			infos[i].taille=tailleChamp[i];
			infos[i].typeString=typesRetour[i];
			infos[i].total=false;
			for (int j = 0; j < colonnesTotaux.length; j++) {
				if(LibConversion.stringToInteger(colonnesTotaux[j])==i) {
					infos[i].total=true;
				}
			}
			
		}
		
	}
	
	private InfosPresentation[] infos = null;
	class InfosPresentation {
		private String titre;
		private String taille;
		private Class type;
		private String typeString;
		private boolean total;
		
		public InfosPresentation(String titre, String taille, Class type,
				String typeString, boolean total) {
			super();
			this.titre = titre;
			this.taille = taille;
			this.type = type;
			this.typeString = typeString;
			this.total = total;
		}

		public InfosPresentation() {
			
		}
		
		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public String getTaille() {
			return taille;
		}

		public void setTaille(String taille) {
			this.taille = taille;
		}

		public Class getType() {
			return type;
		}

		public void setType(Class type) {
			this.type = type;
		}

		public String getTypeString() {
			return typeString;
		}

		public void setTypeString(String typeString) {
			this.typeString = typeString;
		}

		public boolean isTotal() {
			return total;
		}

		public void setTotal(boolean total) {
			this.total = total;
		}
		
	}
	
	protected void rechercheJPA(String requete) {
		try {
//			requete = 
//			"select "
//		+	"substring(a.codepostalAdresse,1,2) as departement,"
//		+	"extract(year from f.dateDocument)as annee,"
//		+	"t.codeTiers as codeTiersFacture,"
//		+	"t.nomTiers as nomTiersFacture,"
//		+	"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netHtCalc/6.55957) else sum(f.netHtCalc) end,  "
//		+	"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTvaCalc/6.55957) else sum(f.netTvaCalc) end, " 
//		+	"case when extract ( year from f.dateDocument) <= 2001 then  sum(f.netTtcCalc/6.55957 ) else sum(f.netTtcCalc) end,"
////		+	"lf.taArticle.codeArticle, fa.codeFamille"
//		+ "art.codeArticle,fa.codeFamille "
//		+	" from "
////originale	+	"TaFacture f left join f.taTiers t left join t.taAdresse a left join f.lignes lf left join lf.taArticle.taFamille fa" 
//		+	"TaFacture f left join f.taTiers t left join t.taAdresse a left join f.lignes lf left join lf.taArticle art left join art.taFamille fa"
//		
//		
//		+	" group by substring(a.codepostalAdresse,1,2),extract(year from f.dateDocument),t.codeTiers,t.nomTiers" 
//		+ ",art.codeArticle,fa.codeFamille "
//	//	+		",lf.taArticle.codeArticle,fa.codeFamille" 
// ;
			Query ejbQuery = null;
			List<Resultat> l = null;
			if(queryLang!=null && queryLang.equals(ConstVisualisation.QUERY_LANG_SQL)) {
				//SQL
//				requete = "select code_article,NUMCPT_ARTICLE from ta_article";
				 ejbQuery = new JPABdLgr().getEntityManager().createNativeQuery(requete);
				l = ejbQuery.getResultList();
			} else if(queryLang!=null && queryLang.equals(ConstVisualisation.QUERY_LANG_JAVA)) {
				if(classeRecherche!=null) {
					String nomClasse = classeRecherche;
					IFonctionRecherche fr;
					fr = (IFonctionRecherche) ConstructorUtils.invokeConstructor(Class.forName(nomClasse), null);
					l = fr.recherche();
				}
			} else {
				//JPQL
				ejbQuery = new JPABdLgr().getEntityManager().createQuery(requete);
				//Les requetes JPA dans la visualisation interviennent sur plusieurs entite, il n'y donc pas d'entite correspondante.
				//Le resultat est un Object[] avec un objet pour chaque ligne de resultat.
				//Chacun de ces objets est lui meme un Object[] avec un object pour chaque colonne
				l = ejbQuery.getResultList();
			}
			
			modelImpression = new ModelGeneralObjetVisualisation<Resultat>(l,Resultat.class);
			logger.debug("====================================================================================");			
			logger.debug(requete);			
			logger.debug("====================================================================================");			
			logger.debug("=== nombre de ligne de resultat : "+l.size());
			logger.debug("====================================================================================");			
			setNbResultat(l.size());
			
			vue.getGrilleResultat().removeAll();
			TableItem item = null;
			Object valueColonne = null;
			boolean initAlignement = false;
			int nbCol = 0;
			for (Object objLigne : l) { //boucle lignes
				item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
				
				Object[] objLigneTableau = (Object[])objLigne;
				String[] ligneString = new String[objLigneTableau.length];
				
				if(nbCol==0) {
					nbCol=objLigneTableau.length;
					totaux = new String[nbCol];
				}
				
				
				for (int i = 0; i < objLigneTableau.length; i++) { //boucle colonnes
					valueColonne=objLigneTableau[i];
					try {
						if(valueColonne!=null 
								//&& PropertyUtils.getPropertyDescriptor(dest, Resultat.debutNomChamp+(i+1))!=null
								) {
//							if(valueColonne instanceof BigDecimal
//										|| valueColonne instanceof Integer
//										|| valueColonne instanceof Double
//										|| valueColonne instanceof Float) {
							if(infos[i].getTypeString().equals(ConstVisualisation.typeBigDecimal)
									|| infos[i].getTypeString().equals(ConstVisualisation.typeFloat)
									|| infos[i].getTypeString().equals(ConstVisualisation.typeDouble)
									|| infos[i].getTypeString().equals(ConstVisualisation.typeInteger)
									|| infos[i].getTypeString().equals(ConstVisualisation.typeBooleen) ) {
								if(!initAlignement) {
									vue.getGrilleResultat().getColumn(i).setAlignment(SWT.RIGHT);
								}
								
								if(infos[i].total) {

									if(valueColonne instanceof BigDecimal) {
										totaux[i]=LibConversion.bigDecimalToString(LibConversion.stringToBigDecimal(totaux[i]).add((BigDecimal)valueColonne));
										infos[i].type=BigDecimal.class;
									} else if(valueColonne instanceof Integer) {
										totaux[i]=LibConversion.integerToString(LibConversion.stringToInteger(totaux[i])+(Integer)valueColonne);
										infos[i].type=Integer.class;
									} else if(valueColonne instanceof Float) {
										//totaux[i]=LibConversion. (LibConversion.stringToFloat(totaux[i])+(Float)value);
										totaux[i]=LibConversion.floatToString(LibConversion.stringToFloat(totaux[i])+(Float)valueColonne);
										infos[i].type=Float.class;
									} else if(valueColonne instanceof Double) {
										//totaux[i]=LibConversion. (LibConversion.stringToFloat(totaux[i])+(Float)value);
										totaux[i]=LibConversion.doubleToString(LibConversion.stringToDouble(totaux[i])+(Double)valueColonne);
										infos[i].type=Double.class;
									}
								}
								
								valueColonne = LibCalcul.arrondi(valueColonne);
								totaux[i]= LibConversion.bigDecimalToString(LibCalcul.arrondi(LibConversion.stringToBigDecimal(totaux[i])));
								
								String valBool = changeBooleen(i, valueColonne);
								if(valBool!=null) {
									valueColonne = valBool;
									objLigneTableau[i] = valueColonne; //on change aussi la valeur dans le tableau d'objet JPA pour l'impression
									vue.getGrilleResultat().getColumn(i).setAlignment(SWT.CENTER);
									if(LibConversion.StringToBoolean(valBool.toLowerCase()))
									item.setImage(i,LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.CHECK));
									else
										item.setImage(i,LibrairiesLeGrainPlugin.ir.get(LibrairiesLeGrainPlugin.UNCHECK));
									valueColonne="";
								}
							} else if(valueColonne instanceof Date) {
								valueColonne = valueDate((Date)valueColonne);
								objLigneTableau[i] = valueColonne;
							}
							ligneString[i]=valueColonne.toString();
							
						} else {
							ligneString[i]=null;
						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				initAlignement = true;
				item.setText(ligneString);
			}
			
			
			Color color = new Color(vue.getDisplay(),94,190,228);
			Font f = new Font(vue.getDisplay(),"Tahoma",8,SWT.BOLD);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item.setBackground(color);
			item.setText(ConstVisualisation.LIBELLE_TOTAUX);
			item.setFont(f);
			item = new TableItem(vue.getGrilleResultat(),SWT.NONE);
			item.setBackground(color);
			if (totaux!=null)
				item.setText(totaux);
			item.setFont(f);
			
		} catch (Exception re) {
			logger.error("",re);
		}
	}
	
	private String changeBooleen(int position, Object v) {
		String ret = null;
		if(infos[position].typeString.equals(ConstVisualisation.typeBooleen)) {
			if(v instanceof BigDecimal) {
				ret = LibConversion.bigDecimalToString((BigDecimal)v);
			} else if(v instanceof Integer) {
				ret = LibConversion.integerToString((Integer)v);
			} else if(v instanceof Float) {
				ret = LibConversion.floatToString((Float)v);
			}
			ret = ret.replace("0", "Faux").replace("1", "Vrai");
		}
		return ret;
	}

	public void initGrilleResultat(final String requete){
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(vue.getDisplay(),SWT.CURSOR_WAIT));
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				rechercheJPA(requete);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(vue.getDisplay(),SWT.CURSOR_ARROW));
			}
		});
		//rechercheJPA(requete);
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(new Cursor(vue.getDisplay(),SWT.CURSOR_ARROW));			
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

//	public void ouvreDocument(String valeurIdentifiant){
//		IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
//		if(editor==null) {
//			try {
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new IEditorInput(){
//
//					public boolean exists() {
//						return false;
//					}
//					public ImageDescriptor getImageDescriptor() {
//						return null;
//					}
//					public String getName() {
//						return "";
//					}
//					public IPersistableElement getPersistable() {
//						return null;
//					}
//					public String getToolTipText() {
//						return "";
//					}
//					public Object getAdapter(Class adapter) {
//						return null;
//					}
//				}, idEditor);
//
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//
//				ParamAffiche paramAffiche = new ParamAffiche();
//				if(e instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
//				} else {
//					((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((JPALgrEditorPart)e).getController().configPanel(paramAffiche);
//				}
//
//			} catch (PartInitException e1) {
//				logger.error("",e1);
//			}
//		} else {
//			if(MessageDialog.openQuestion(
//					vue.getShell(),
//					"Affichage document",
//			"Voulez-vous abandonner le document en cours de saisie ?")){
//				ParamAffiche paramAffiche = new ParamAffiche();
//				if(editor instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor)editor).findMasterController().configPanel(paramAffiche);
//				}
//			}
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editor);
//		}
//	}

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

	public String[] getListeChamp() {
		return listeChamp;
	}

	public ModelGeneralObjetVisualisation<Resultat> getModelImpression() {
		return modelImpression;
	}

	public InfosPresentation[] getInfos() {
		return infos;
	}

	public String[] getTotaux() {
		return totaux;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}


}
