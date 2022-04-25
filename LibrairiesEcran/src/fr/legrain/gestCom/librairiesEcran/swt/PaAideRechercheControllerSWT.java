package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataChangeListener;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.NavigationListener;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.RowFilterListener;
import com.borland.dx.dataset.RowFilterResponse;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.OuvertureBase;
import fr.legrain.lib.gui.BaseController;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.aide.PaAideRecherche;
import fr.legrain.lib.gui.aide.ParamAfficheAideRecherche;

public class PaAideRechercheControllerSWT extends BaseController implements
		NavigationListener, DataChangeListener {

	static Logger logger = Logger.getLogger(PaAideRechercheControllerSWT.class.getName());
	
	//TODO ajout d'un objet de retour pour recupérer la valeur des ecrans de créations
	
	private PaAideRecherche vue = null; // vue
	public JPopupMenu[] tabPopupsAide = null; 
	private Filtre filtreEcran = null;
	private String champsRechercheInitial = null; //champs sur lequel la recherche a été demandé
	private String champsId = null; //champs identifiant qui sert à renvoyer dans tous les cas la clé
	// de l'enregistrement
	private int[] hiddenColumns;
	
	//TODO ajout d'une info sur l'écran à appeler pour la creation
	public BaseControllerSWT refCreation = null; //controller de l'écran à appeler pour une création
	public ParamAffiche paramEcranCreation = null; //paramètres pour l'écran de création
	//public Class refCreation = null;

	// modèle
	private QueryDataSet query = null;
	private String champsRecherche = null; //champs sur lequel la recherche est effectué à un moment précis
	protected SimpleDateFormat df = new SimpleDateFormat(LgrConstantes.C_DATE_FORMAT);
	
	protected List<JComponent> listeComposantFocusable = null;
	
	/**
	 * Rafraichissement des données formant l'ensemble de recherche
	 */
	public void refresh() {
		query.refresh();
	}
	
	/**
	 * Positionement sur la première ligne où <code>champs</code> = <valeur>valeur</valeur> 
	 * @param champs - nom du champs de recherche
	 * @param valeur - valeur recherchée pour ce champs
	 */
	public void locate(String champs, String valeur) {
		DataRow courant = new DataRow(query,champs);
		courant.setInt(champs,LibConversion.stringToInteger(valeur));
		query.locate(courant,32);
	}


	public PaAideRechercheControllerSWT(PaAideRecherche vue) {
		try {
			this.vue = vue;
			initComposantsVue();
			// TODO Initialiser les popups partout
			tabPopupsAide =new JPopupMenu[]{vue.getPopupMenuAide()};
			if (getFocusCourant()!=null )getFocusCourant().requestFocus();
			
			listeComposantFocusable = new ArrayList();
			listeComposantFocusable.add(vue.getTfChoix());
			listeComposantFocusable.add(vue.getJdbTable());
			listeComponentFocusable(listeComposantFocusable);
			changeCouleur(vue);
		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideRechercheController", e);
		}
	}
	
	public PaAideRechercheControllerSWT() {
		try {
			this.vue = new PaAideRecherche();
			initComposantsVue();
			if (getFocusCourant()!=null )getFocusCourant().requestFocus();
			tabPopupsAide =new JPopupMenu[]{this.vue.getPopupMenuAide()};
			
			listeComposantFocusable = new ArrayList();
			listeComposantFocusable.add(vue.getTfChoix());
			listeComposantFocusable.add(vue.getJdbTable());
			listeComponentFocusable(listeComposantFocusable);
			changeCouleur(vue);
		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideRechercheController", e);
		}
	}
	
	public String getChampsRechercheInitial() {
		return champsRechercheInitial;
	}
	
	/**
	 * @inheritDoc
	 */
	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
//			this.query = ((ParamAfficheAideRechercheSWT)param).getQuery();
			query=new QueryDataSet();
			query.addNavigationListener(this);
			String nouvelleRequete = ((ParamAfficheAideRechercheSWT)param).getQuery().getQuery().getQueryString();
			query.setQuery(new QueryDescriptor(
					((ParamAfficheAideRechercheSWT)param).getDb(),
					nouvelleRequete,true));
			query.open();
			if(((ParamAfficheAideRechercheSWT)param).getQueryString()!=null) { 
				query.close();
				System.out.println(((ParamAfficheAideRechercheSWT)param).getQueryString());
				query.setQuery(new QueryDescriptor(
						((ParamAfficheAideRechercheSWT)param).getDb(),
						((ParamAfficheAideRechercheSWT)param).getQueryString()
				)
				);
				query.open();
			}
			vue.getJdbTable().setDataSet(query);
			this.champsRecherche = ((ParamAfficheAideRechercheSWT)param).getChampsRecherche();
			champsRechercheInitial = champsRecherche;
			champsId=((ParamAfficheAideRechercheSWT)param).getChampsIdentifiant();
			changeChampsRech(champsRecherche);
			
			if (((ParamAfficheAideRechercheSWT)param).getFocusDefaut()!=null)
			setFocusCourantHorsApplication(((ParamAfficheAideRechercheSWT)param).getFocusDefaut());
			else
				setFocusCourantHorsApplication(vue.getTfChoix());	
			getFocusCourant().requestFocus();
			//vue.getTfChoix().setText(((ParamAfficheAideRechercheSWT)param).getDebutRecherche());
			refCreation = ((ParamAfficheAideRechercheSWT)param).getRefCreation();
			paramEcranCreation = ((ParamAfficheAideRechercheSWT)param).getParamEcranCreation();
			vue.getTfChoix().getDocument().addDocumentListener(new Doc());
			vue.getJdbTable().initNomColonne(query);
			if(((ParamAfficheAideRechercheSWT)param).getHiddenColumns()!=null) {
				this.hiddenColumns = ((ParamAfficheAideRechercheSWT)param).getHiddenColumns();
				vue.getJdbTable().setHiddenColumns(((ParamAfficheAideRechercheSWT)param).getHiddenColumns());
			}
			
			try {
				//query.refilter();
				DataRow courant = new DataRow(query,getChampsRecherche());
				Column c = query.getColumn(getChampsRecherche());
				affectDataRow(courant,getChampsRecherche(),((ParamAfficheAideRechercheSWT)param).getDebutRecherche());
				//courant.setString(getChampsRecherche(),((ParamAfficheAideRechercheSWT)param).getDebutRecherche());
				if(c.getDataType()==Variant.STRING)
					query.locate(courant,1|32); //1=PARTIAL 32=FIRST
				else
					query.locate(courant,32); //1=PARTIAL 32=FIRST
			} catch (Exception e) {
				logger.error("",e);
			}
			
		}
		return null;
	}
	
	/**
	 * Change le champs sur lequel s'effectue la recherche
	 * @param champs - nom du champs
	 */
	public void changeChampsRech(String champs) {
		this.champsRecherche = champs;
		//vue.getLaRecherche().setText("Recherche sur "+IBQuLgr.getTitreChamp(champsRecherche));
		try {
			query.removeRowFilterListener(filtreEcran);
			filtreEcran = new Filtre();
			query.addRowFilterListener(filtreEcran);
			query.refilter();
		} catch (TooManyListenersException e) {
			logger.error("Erreur : configPanel", e);
		}
	}
	
	/**
	 * Filtre la recherche en fonction de <code>champsRecherche</code>
	 * @author nicolas
	 */
	protected class Filtre implements RowFilterListener {
		public void filterRow(ReadRow row, RowFilterResponse response) {
			Variant v = new Variant();
			if(!query.isOpen())
				query.open();
			if(champsRecherche!=null && vue.getTfChoix().getText()!=null) {
				row.getVariant(champsRecherche, v);
				if(v.toString().toLowerCase().startsWith(vue.getTfChoix().getText().toLowerCase()))
					response.add();
			} else {
				response.add();
			}
		}
	}
	
	protected class Doc implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {
			query.refilter();
		}

		public void removeUpdate(DocumentEvent e) {
			query.refilter();
		}

		public void changedUpdate(DocumentEvent e) {
			query.refilter();
		}
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	private void initComposantsVue() throws ExceptLgr {
		//this.vue.getTfChoix().setEditable(false);
		this.vue.getJdbTable().setEditable(false);
		this.vue.getJdbTable().setRowHeaderVisible(false);
		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		vue.getActionMap().put("up", new ActionUp("up"));
		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		vue.getActionMap().put("down", new ActionDown("down"));
		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		vue.getActionMap().put("down", new ActionDown("down"));
		
		vue.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK), "actionChangeChampsRech");
		vue.getActionMap().put("actionChangeChampsRech", new ActionChangeChampsRech("actionChangeChampsRech"));
		
	}

	/**
	 * MAJ du formulaire Adresse.
	 */
	public void actualiserForm() {
		Variant v = new Variant();
		if(!query.isOpen())
			query.open();
		query.getVariant(champsRecherche,query.getRow(), v);
		vue.getTfChoix().setText(v.toString());
		vue.getLaRecherche().setText("Recherche sur "+champsRecherche);
		query.refilter();
	}
	

	/**
	 * postRow
	 * @param event DataChangeEvent
	 */
	public void postRow(DataChangeEvent event) {}

	/**
	 * dataChanged
	 * @param event DataChangeEvent
	 */
	public void dataChanged(DataChangeEvent event) {}

	public void navigated(NavigationEvent event) {}

	public PaAideRecherche getVue() {
		return vue;
	}

	public String getChampsRecherche() {
		return champsRecherche;
	}

	public void setChampsRecherche(String champsRecherche) {
		this.champsRecherche = champsRecherche;
	}

	public QueryDataSet getQuery() {
		return query;
	}

	public void setQuery(QueryDataSet query) {
		this.query = query;
	}
	
	protected class ActionUp extends AbstractAction {
		public ActionUp(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				query.prior();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}
	
	protected class ActionDown extends AbstractAction {
		public ActionDown(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				query.next();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}
	
	protected class ActionChangeChampsRech extends AbstractAction {
		public ActionChangeChampsRech(String name) {
			super(name);
		}
		
		public void actionPerformed(ActionEvent e) {
			ArrayList<Integer> l =new ArrayList<Integer>();
			if(hiddenColumns!=null) {
				for(int i=0;i<hiddenColumns.length;i++) {
					l.add(hiddenColumns[i]);
				}
			}
			try {
				int posDebutChangement = query.getColumn(champsRecherche).getOrdinal();
				int j = posDebutChangement;
				while(j+1 < query.getColumnCount()&& !(l.indexOf(j+1)==-1))
					j++;
				if(j+1 >= query.getColumnCount()) { //on repart du début
					j=0;
					while(j+1 <= posDebutChangement && !(l.indexOf(j+1)==-1))
					j++;
				}
				champsRecherche = query.getColumn(j+1).getColumnName();				
				changeChampsRech(champsRecherche);
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}

	public String getChampsId() {
		return champsId;
	}

	public void setChampsId(String champsId) {
		this.champsId = champsId;
	}
		
	public void setFocusCourant(JComponent focusCourant) {
		setFocusCourantHorsApplication(focusCourant) ;
	}
	
	//basée sur fr.legrain.lib.data.IBQuLgr#renvoieValeurSurType()
	protected void affectDataRow(DataRow row, String nomColonne, String valeur)throws Exception {
//		
			switch (row.getColumn(nomColonne).getDataType()) {
			case Variant.STRING:
				row.setString(nomColonne,valeur);
				break;
			case Variant.INT:
				row.setInt(nomColonne,
						(LibConversion.stringToInteger(valeur)));
				break;
			case Variant.DOUBLE:	
				row.setDouble(nomColonne,
						(LibConversion.stringToDouble(valeur)));
				break;
			case Variant.SHORT:
				if ( valeur.equals("")){
					row.setAssignedNull(nomColonne);
				}else{
					if(valeur.equals("false")) {
						row.setShort(nomColonne,
								(LibConversion.stringToShort("0")));
					} else if (valeur.equals("true")){
						row.setShort(nomColonne,
								(LibConversion.stringToShort("1")));
					} else
						row.setShort(nomColonne,
								(LibConversion.stringToShort(valeur)));
				}
				break;
			case Variant.BIGDECIMAL:	
				row.setBigDecimal(nomColonne,
						(LibConversion.stringToBigDecimal(valeur)));
				break;
			case Variant.LONG:
				row.setLong(nomColonne,
						(LibConversion.stringToLong(valeur)));
				break;				
				
			case Variant.FLOAT:
				row.setFloat(nomColonne,
						(LibConversion.stringToFloat(valeur)));
				break;
			case Variant.DATE:
				
				//DateFormat dateFormat = DateFormat.getInstance();
				try {
					row.setDate(nomColonne,
							(df.parse(valeur).getTime()));
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
					row.setTimestamp(nomColonne,
							(df.parse(valeur).getTime()));
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
				row.setBoolean(nomColonne,
						(Boolean.getBoolean(valeur)));
				break;
			default:
				try {
					
//					JOptionPane.showMessageDialog(LgrConstantes.getFocusedLgr(), "Le DataType n''est pas défini :  "+row.getColumn(nomColonne)+"; Champ : "+nomColonne,
//							"ERREUR", JOptionPane.WARNING_MESSAGE);
					throw new Exception("Le DataType n''est pas défini :  "+row.getColumn(nomColonne)+"; Champ : "+nomColonne);
				}
			finally {
			}
			}
//		}
		
	}


}
