/**
 * DocumentsController.java
 */
package fr.legrain.statistiques.controllers.a_supprimer;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.JaugeController;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Classe controller de la section documents
 * @author nicolas²
 *
 */
public class JaugeControllerPrincipal extends JaugeController{
	static Logger logger = Logger.getLogger(JaugeControllerPrincipal.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaFacture taFacture = null;
	private TaDevisDAO taDevisDAO = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBonlivDAO taBonlivDAO = null;
	

	
	public JaugeControllerPrincipal(FormPageControllerPrincipal masterContoller,
			PaFormPage vue, EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	public void initialiseModelIHM() {

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				//On efface le(s) précédent(s) graphique(s)
				if (vue.getCompositeSectionJauge().getCompoInterieur().getChildren().length > 0) {
					vue.getCompositeSectionJauge().getCompoInterieur().getChildren()[0].dispose();
				}
				datedeb = Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb());
				datefin = Outils.extractDate(vue.getCompositeSectionParam().getCdatefin());
			}
		});


		taFactureDAO = new TaFactureDAO(masterController.getMasterDAOEM());
		// On récupère les factures
		double nbFacturesTot = taFactureDAO.toutesFacturesEntreDeuxDates(datedeb, datefin).size();
		List<TaFacture> listeFactures = taFactureDAO.rechercheDocumentNonTotalementRegle(datedeb,datefin);


		// On les comptabilise
		int nbFactures = listeFactures.size();
		double mtFactures = 0;
		for (int i = 0; i<nbFactures; i++){
			mtFactures += listeFactures.get(i).getResteAReglerComplet().doubleValue();
		}

		// On calcule l'indice
		if (nbFacturesTot != 0) {
			indiceJauge = (nbFactures/nbFacturesTot)*100;
		} else {
			indiceJauge = 0;
		}
		
		// Titre du graphique
		titreJauge = "Indice des factures non payées";

		// Nouvel élément Object
		JaugeIHM fact = new JaugeIHM(new BigDecimal(String.valueOf(nbFactures))
		,new BigDecimal(String.valueOf(mtFactures))
		,null);
		setSelectedObject(fact);

	}

}
