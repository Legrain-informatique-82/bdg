/**
 * GoogleMapChart.java
 */
package fr.legrain.statistiques;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.browser.Browser;

/**
 * Classe permettant de définir un graphique de type Map Chart
 * Grâce à l'API Google API
 * Attention : possibilité d'être modifié par google dans les années à venir
 * Attention_bis : contrôles limités sur la classe => Code html (String passées en arguments des méthodes)
 * @author nicolas²
 * 
 * 
 * Doc pour le mode nouveau mode HTML5 + SVG : http://code.google.com/intl/fr-FR/apis/chart/interactive/docs/gallery/geochart.html
 * Doc pour l'ancien mode Flash : http://code.google.com/intl/fr-FR/apis/chart/image/docs/gallery/new_map_charts.html
 *
 */
public class GoogleMapChart {
	
	/** Affichage de la carte en Flash */
	public static int MODE_FLASH_GOOGLE_MAP_CHART = 1;
	/** Affichage de la carte en SVG */
	public static int MODE_SVG_GOOGLE_GEO_CHART = 2;
	
	private int mode = MODE_FLASH_GOOGLE_MAP_CHART;
	//private int mode = MODE_SVG_GOOGLE_GEO_CHART;
	
	/* Mapping Regions */
	private Map<String,String> mapRegions;
	
	/* Page générée */
	private String pageHTML="";
	
	/* -- PARTIES FIXES DE LA PAGE -- */
	// En-tête
	private final static String enTetePageFlash = "<!DOCTYPE html> <html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"content-type\"  content=\"text/html; charset=utf-8\"/> <script type=\"text/javascript\"  src=\"http://www.google.com/jsapi\"> </script> <script type=\"text/javascript\"> google.load('visualization', '1', {packages: ['geomap']}); </script> "; 
	private final static String enTetePageSvg = "<!DOCTYPE html> <html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"content-type\"  content=\"text/html; charset=utf-8\"/> <script type=\"text/javascript\"  src=\"http://www.google.com/jsapi\"> </script> <script type=\"text/javascript\"> google.load('visualization', '1', {packages: ['geochart']}); </script> ";
	private final static String finEnTete = "</head>";
	// Pied de PAge
	private final static String piedPage = "</html>";
	
	/* -- PARTIES EDITABLES DE LA PAGE -- */
	// Corps de la page 
	private String font = "Arial"; 	// Police
	private int width = 600;		// Largeur de la carte
	private int heigth = 400 ;		// hauteur de la carte
	private  String corpsPage = ""; // Corps de la page généré grâce aux valeurs @refreshCorps()
	
	
	// Données de la carte
	private int numberRows = 0;		// Nombre de lignes du tableau de données
	private int currentRow = 0;		// Parseur du tableau
	private String rows = "";		// Partie correspondante à la taille du tableau de données @refreshRows()
	private String values = "";		// Valeurs permettant de générer la carte ( à remplir cf : @addValue )
	// Colonnes
	private String label = "Chiffre d'Affaires";
	private String columns = "";	// Colonne contenant le label de la Région
	
	/* -- OPTIONS DE LA CARTE -- */
	private String region = "FR";		// Région sur laquelle s'applique la carte, de base FR (cf : iso3166-2)
	private String datamode = "regions";// Mode d'affichage des données (marquers/regions)
	private String resolutionSVG = "provinces"; // Mode d'affichage des données (marquers/regions) mais utilisé pour le SVG
	private String options = "";		// Génération des options pour la carte @refreshOptions()
	
	/* -- SCRIPT A EXECUTER SUR LA PAGE -- */
	private String script = ""; 		// Script regénéré @refreshScript()
	
	/**
	 * Teste la présence du Flash Player dans le browser passé en paramètre.<br>
	 * Pour Internet Explorer, test la création d'un objet ActiveXObject,<br>
	 * Pour les autres navigateurs (Firefox, Opera, Safari, ...) test la présence d'un plugin flash à partir des types MIME pris en compte par le navigateur.<br>
	 * Ce test est très simple, il est possible de faire des tests surement plus fiable et plus précis (N° de version, ...) à partir du detection kit d'Adobe.<br>
	 * <br>
	 * Infos :<br>
	 * http://stackoverflow.com/questions/998245/how-can-i-detect-if-flash-is-installed-and-if-not-display-a-hidden-div-that-info<br>
	 * http://forum.alsacreations.com/topic-5-16333-1-Dtection-flash-par-javascript.html<br>
	 * http://www.adobe.com/fr/products/flashplayer/download/detection_kit/<br>
	 * 
	 * @param b - Composant navigateur SWT
	 * @return vrai si b à accès au Flash Player
	 */
	private boolean flashInstalle(Browser b) {
		boolean reponse = false;
		
		if(b.getText().equals("")) {
			//si browser vide, l'interprétation du script semble ne pas fonctionner. Initialisation avec une page vierge
			b.setText("<html></html>");
		}
		
		String jsTest = //"function testFlash() {" +
				"var hasFlash = false;"+
				"try {"+
					"var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');"+
					"if(fo) hasFlash = true;"+
				"}catch(e){"+
				"  if(navigator.mimeTypes [\"application/x-shockwave-flash\"] != undefined) hasFlash = true;"+
				"}"+
				"return hasFlash;";
			//"}";
		
		Object o = b.evaluate(jsTest);
		
		if(o != null && o instanceof Boolean) 
			reponse = (Boolean)o;
		
		return reponse;
	}
	
	/**
	 * Test le mode d'affichage Flash ou SVG en fonction des capacités du navigateur
	 * @param b
	 * @return - le mode sélectionné après le test (MODE_FLASH_GOOGLE_MAP_CHART ou MODE_SVG_GOOGLE_GEO_CHART)
	 */
	private int initMode(Browser b) {
		if(b!=null) {
			if(flashInstalle(b)) {
				mode = MODE_FLASH_GOOGLE_MAP_CHART;
			} else {
				mode = MODE_SVG_GOOGLE_GEO_CHART;
			}
		}
		return mode;
	}

	/**
	 * Constructeur par défaut du MapChart, mode Flash par defaut
	 * PageHTML "vierge" tant que les données n'ont pas été initialisées
	 */
	public GoogleMapChart(){
		mode = MODE_FLASH_GOOGLE_MAP_CHART;
		this.pageHTML = "L'affichage de la carte requiert des données non présentes actuellement.";
	}
	
	/**
	 * Constructeur par défaut du MapChart
	 * PageHTML "vierge" tant que les données n'ont pas été initialisées
	 * @param mode - Flash => MODE_FLASH_GOOGLE_MAP_CHART ou SVG => MODE_SVG_GOOGLE_GEO_CHART
	 */
	public GoogleMapChart(int mode){
		if(mode==MODE_FLASH_GOOGLE_MAP_CHART || mode==MODE_SVG_GOOGLE_GEO_CHART) {
			this.mode = mode;
		}
		this.pageHTML = "L'affichage de la carte requiert des données non présentes actuellement.";
	}
	
	/**
	 * Le mode d'affichage Flash ou SVG est déterminé à partir des capacités de b
	 * @param b
	 */
	public GoogleMapChart(Browser b){
		initMode(b);
		this.pageHTML = "L'affichage de la carte requiert des données non présentes actuellement.";
	}
	
	/**
	 * Constructeur du MapChart
	 * @param numberRows nombre de données
	 * TODO: implémenter le passage d'un tableau de données
	 */
	public GoogleMapChart(List<ResultRepartition> listeVentes){
		this();
		addListe(listeVentes);
	}
	
	/**
	 * Constructeur du MapChart
	 * @param numberRows nombre de données
	 * TODO: implémenter le passage d'un tableau de données
	 */
	public GoogleMapChart(List<ResultRepartition> listeVentes, int mode){
		this(mode);
		addListe(listeVentes);
	}
	
	public GoogleMapChart(List<ResultRepartition> listeVentes, Browser b){
		this(b);
		addListe(listeVentes);
	}
		
	
	
	/**
	 * Méthode permettant l'ajout de données à la carte via une liste de ResultRepartition
	 * @param listeVentes la liste des ventes par régions
	 */
	public void addListe(List<ResultRepartition> listeVentes) {
		this.numberRows += listeVentes.size();
		for(int i=0; currentRow <=numberRows && i<listeVentes.size();i++){ // On peut encore ajouter des données
			if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
				values += " data.setValue("+currentRow+", 0 , \""+listeVentes.get(i).getCode()+"\"); data.setValue("+currentRow+", 1 ,"+listeVentes.get(i).getMontant()+"); data.setValue("+currentRow+", 2 ,\""+listeVentes.get(i).getNom()+"\");";
			} else { //SVG
				values += " data.setValue("+currentRow+", 0 , \""+listeVentes.get(i).getNom()+"\"); data.setValue("+currentRow+", 1 ,"+listeVentes.get(i).getMontant()+"); ";
			}
			currentRow++;
		}
		
	}
	
	
	/**
	 * Méthode permettant l'ajout de données à la carte
	 * @param codeRegion code de la region de la carte concernée au format iso 3166-2
	 * @param montantVente montant des ventes pour la région concernée
	 * @param nomRegion nom de la région
	 */
	public void addValue(String codeRegion, int montantVente, String nomRegion) {
		if (currentRow <= numberRows){ // On peut encore ajouter des données
			if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
				values += " data.setValue("+currentRow+", 0 , \""+codeRegion+"\"); data.setValue("+currentRow+", 1 ,  "+montantVente+"); data.setValue("+currentRow+", 2 , \""+nomRegion+"\"); ";
			} else { //SVG
				values += " data.setValue("+currentRow+", 0 , \""+nomRegion+"\"); data.setValue("+currentRow+", 1 ,  "+montantVente+"); ";
			}
			currentRow++;
		}
		
	}

	
	/**
	 * Méthode permettant de remettre les valeurs à 0
	 * Par la même occasion, l'iterateur est remis lui aussi à 0
	 */
	public void resetValues() {
		values = "";
		currentRow = 0;
		
	}
	
	/* Accesseur sur le nombre de lignes */
	public void setNumberRows(int numberRows) {
		this.numberRows = numberRows;
	}
	
	/**
	 * Accesseur sur la pageHTML
	 * @return 	si l'iterateur et le nombre de lignes ont été initialisés la page complète
	 * 			sinon une page d'erreur
	 */
	public String getPageHTML() {
		if (currentRow > 0 && numberRows > 0){
			String entete = null;
			if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
				entete = enTetePageFlash;
			} else { //SVG
				entete = enTetePageSvg;
			}
			refreshColumns();
			refreshRows();
			refreshOptions();
			refreshScript();
			refreshCorps();		
			pageHTML = entete+script+finEnTete+corpsPage+piedPage;
		} else {
			pageHTML = "L'affichage de la carte requiert des données non présentes actuellement.";
		}
		return pageHTML;
	}
	
	public void refreshScript() {
		if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
			script =  "<script type=\"text/javascript\"> function drawVisualization() { var data = new google.visualization.DataTable();"+rows+columns+values+options+" var geomap = new google.visualization.GeoMap(document.getElementById('visualization')); geomap.draw(data, options);} google.setOnLoadCallback(drawVisualization); </script>";
		} else { //SVG
			script =  "<script type=\"text/javascript\"> function drawRegionsMap() { var data = new google.visualization.DataTable();"+rows+columns+values+options+" var geochart = new google.visualization.GeoChart(document.getElementById('map_canvas')); geochart.draw(data, options);} google.setOnLoadCallback(drawRegionsMap); </script>";
		}
	}
	
	public void refreshRows() {
		 rows = "data.addRows("+numberRows+");"; 
	}

	public void refreshCorps() {
		if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
			corpsPage = "<body style=\"font-family:"+font+";border: 0 none;\">  <div id=\"visualization\"   style=\"width: "+width+"px; height: "+heigth+"px;\">  </div> </body>";
		} else { //SVG
			corpsPage = "<body style=\"font-family:"+font+";border: 0 none;\">  <div id=\"map_canvas\"   style=\"width: "+width+"px; height: "+heigth+"px;\">  </div> </body>";
		}	
	}
	
	public void refreshOptions(){
		if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
			options = "var options = {};  options['region'] = '"+region+"';  options['dataMode'] = '"+datamode+"';";
		} else { //SVG
			options = "var options = {};  options['region'] = '"+region+"';  options['resolution'] = '"+resolutionSVG+"';";
		}
	}
	
	public void refreshColumns(){
		if(mode==MODE_FLASH_GOOGLE_MAP_CHART) {
		columns = " data.addColumn('string', 'Code Region');" + // Colonne contenant le code region iso3166-2
			" data.addColumn('number', \""+label+"\");" +             // Colonne contenant le nombre de ventes
			" data.addColumn('string','Region'); ";
		} else { //SVG
			columns = " data.addColumn('string', 'Region');" + // Colonne contenant le nom de la région iso3166-2
			" data.addColumn('number', \""+label+"\");" ;             // Colonne contenant le nombre de ventes
		}
	}


	/* -- OPTIONS DE PARAMETRAGE AVANCE -- */
	public void setRegion(String region) {
		this.region = region;
	}

	public void setDatamode(String datamode) {
		this.datamode = datamode;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	// Classe repartition
	public static class ResultRepartition {
		// Infos repartition
		BigDecimal montant = null;
		String code = null;
		String nom = null;

		/**
		 * Constructeur de Resultat de répartition
		 * @param montant le montant des ventes sur la région
		 * @param code le code région
		 * @param nom le nom de la région
		 */
		public ResultRepartition(BigDecimal montant, String code, String nom) {
			super();
			this.montant = montant;
			this.code = code;
			this.nom = nom;
		}
		
		public BigDecimal getMontant() {
			return montant;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getNom() {
			return nom;
		}

	}




	

}
