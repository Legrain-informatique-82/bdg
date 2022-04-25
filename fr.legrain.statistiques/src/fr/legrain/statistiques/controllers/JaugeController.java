/**
 * FacturesController.java
 */
package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.apache.log4j.Logger;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.statistiques.LgrUpdateValueStrategyAffichageMilliersAvecEuros;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.DocumentsControllerMini.DocIHM;
import fr.legrain.statistiques.ecrans.PaFormPage;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.libLgrBirt.chart.MeterChart;
import fr.legrain.libLgrBirt.chart.PieChart;
import fr.legrain.libLgrBirt.chart.test.data.test.SwtLiveChartRotationViewer;
/**
 * Classe controller de la section factures
 * @author nicolas²
 *
 */

public abstract class JaugeController extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(DocumentsControllerPrincipal.class.getName());	

	private Class objetIHM = null;
	protected TaFactureDAO taFactureDAO = null;
	private TaFacture taFacture = null;
	protected FormPageControllerPrincipal masterController = null;
	protected PaFormPage vue = null;

	protected Date datedeb;
	protected Date datefin;
	protected double indiceJauge;
	
	protected String titreJauge="";

	/* Constructeur */
	public JaugeController(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}

	public abstract void initialiseModelIHM();



	@Override
	public void bind(){
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				if(mapComposantChamps==null) {
					initMapComposantChamps();
				}
				setObjetIHM(JaugeIHM.class);
				mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
				mapComposantUpdateValueStrategy.put(vue.getCompositeSectionJauge().getLblNew2Content(),new LgrUpdateValueStrategyAffichageMilliersAvecEuros());

				bindForm(mapComposantUpdateValueStrategy,mapComposantChamps, JaugeIHM.class, getSelectedObject(), vue.getSctnDocuments().getDisplay());				
				
				// Création du graphique issu des données collectées
				MeterChart meterChart = new MeterChart(vue.getCompositeSectionJauge().getCompoInterieur(), SWT.NONE, 
						indiceJauge,titreJauge);			

				// Construction du graphique
				meterChart.build();
				// Mise à jour du composite
				vue.getCompositeSectionJauge().getCompoInterieur().layout();
				vue.getForm().setBusy(false);// @busy : Fin de l'icone chargement du Form Principal
			}
		});
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control,String>();
		mapComposantChamps.put(vue.getCompositeSectionJauge().getLblNew1Content(), "document1");
		mapComposantChamps.put(vue.getCompositeSectionJauge().getLblNew2Content(), "document2");

	}
	
	/**
	 * Classe permettant l'affichage des informations documents
	 * @version 1.1 : généralisation, possibilité d'utilisation pour section factures par exemple
	 */
	public class JaugeIHM extends ModelObject {
		BigDecimal document1 = null;
		BigDecimal document2 = null;
		BigDecimal document3 = null;


		public JaugeIHM() {}

		public JaugeIHM(BigDecimal document1, BigDecimal document2, BigDecimal document3) {
			super();
			this.document1 = document1;
			this.document2 = document2;
			this.document3 = document3;
		}

		public BigDecimal getDocument1() {
			return document1;
		}

		public void setDocument1(BigDecimal document1) {
			firePropertyChange("document1", this.document1, this.document1 = document1);
		}

		public BigDecimal getDocument2() {
			return document2;
		}

		public void setDocument2(BigDecimal document2) {
			firePropertyChange("document2", this.document2, this.document2 = document2);
		}

		public BigDecimal getDocument3() {
			return document3;
		}

		public void setDocument3(BigDecimal document3) {
			firePropertyChange("document3", this.document3, this.document3 = document3);
		}

	}


}

