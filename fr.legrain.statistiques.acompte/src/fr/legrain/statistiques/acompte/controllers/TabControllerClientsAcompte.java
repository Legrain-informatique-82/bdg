/**
 * ClientController.java
 */
package fr.legrain.statistiques.acompte.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.statistiques.ClientsLabelProvider;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.controllers.TabControllerClients;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Controller permettant la génération des meilleurs/moins bons clients
 * @author nicolas²
 *
 */
public class TabControllerClientsAcompte extends TabControllerClients {

	public TabControllerClientsAcompte(
			FormPageControllerPrincipal masterContoller, PaFormPage vue,
			EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaAcompteDAO taAcompteDAO = null;
	private TaAcompte taAcompte = null;
	private List<ModelObject> modele = null;
	private boolean evenementInitialise = false;
	private int nbResult;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;
	
	

	
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		// On initialise le nombre de résultats
		this.nbResult = nbResult;


		taAcompteDAO = new TaAcompteDAO(masterController.getMasterDAOEM());
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		super.listeClients = taAcompteDAO.findMeilleursClientsParCA(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()),
				nbResult);
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<BestClients> listBestClients = new LinkedList<BestClients>();
		for (Object object : listeClients) {
			listBestClients.add(
					new BestClients(
							(BigDecimal)((Object[])object)[0],
							(String)((Object[])object)[1], 
							(String)((Object[])object)[2],
							(String)((Object[])object)[3]) 

			);
		}

		modelDocument = new MapperBestClientsIHMBestClients().listeEntityToDto(listBestClients);
	}

	

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(BestClientsIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Nom","Code Postal","CA"};
		// Taille des colonnes
		String [] tailleColonnes =  {"75","145","100","100"};
		// Id relatives dans la classe associée
		super.idColonnes = new String[] {"codeTiers","nom","departement","chiffreAffaire"};
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT};
		
		// Création de l'élément graphique du tableau et affichage à l'écran
		TabClientsViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		TabClientsViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,
				tailleColonnes,1,alignement);
		TabClientsViewer.setListeChamp(idColonnes);
		
		ClientsLabelProvider.bind(TabClientsViewer, new WritableList(modelDocument, BestClientsIHM.class),
				BeanProperties.values(idColonnes));

		initActions();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}
	


	public LgrTableViewer getTabClientsViewer() {
		return TabClientsViewer;
	}
}
