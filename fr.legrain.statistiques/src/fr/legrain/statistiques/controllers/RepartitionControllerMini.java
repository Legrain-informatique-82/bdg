/**
 * GraphController.java
 */
package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.ibm.icu.util.Calendar;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.departement.dao.TaDepartements;
import fr.legrain.departement.dao.TaDepartementsDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.DataSetChart;
import fr.legrain.libLgrBirt.chart.MultiSerieBarChart;
import fr.legrain.libLgrBirt.chart.PieChart;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.statistiques.GoogleMapChart;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.GoogleMapChart.ResultRepartition;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * @author nicolas²
 *
 */
public abstract class RepartitionControllerMini extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(RepartitionControllerMini.class.getName());	

	private Class objetIHM = null;
	//	private Object selectedObject = null;
	protected TaDepartementsDAO taDepartementsDAO = null;
	private TaDepartements taDepartements = null;

	private List<ModelObject> modele = null;

	protected FormPageControllerPrincipal masterController = null;

	protected GoogleMapChart mapChart = null;
	
	protected PaFormPage vue = null;

	public RepartitionControllerMini(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}



	public abstract void initialiseModelIHM();

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}

	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}
	


}
