/**
 * Articles.java
 */
package fr.legrain.statistiques.apporteur.controllers;

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

import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.statistiques.ArticlesLabelProvider;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.controllers.TabControllerArticles;
import fr.legrain.statistiques.controllers.TabControllerArticles.BestArticles;
import fr.legrain.statistiques.controllers.TabControllerArticles.BestArticlesIHM;
import fr.legrain.statistiques.controllers.TabControllerArticles.MapperBestArticlesIHMBestArticles;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Controller permettant la génération des meilleurs/moins bons articles
 * @author nicolas²
 *
 */
public class TabControllerArticlesApporteur extends TabControllerArticles {

	public TabControllerArticlesApporteur(
			FormPageControllerPrincipal masterContoller, PaFormPage vue,
			EntityManager em) {
		super(masterContoller, vue, em);
		// TODO Auto-generated constructor stub
	}


	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaApporteurDAO taApporteurDAO = null;
	private TaApporteur taApporteur = null;
	private List<ModelObject> modele = null;
	private boolean evenementInitialise = false;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;
	
	protected String [] titreColonnes;
	protected String [] tailleColonnes;
	protected int	   [] alignement;
	
	

	
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		// On initialise le nombre de résultats
		this.nbResult = nbResult;


		taApporteurDAO = new TaApporteurDAO(masterController.getMasterDAOEM());
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		super.listeArticles = taApporteurDAO.findMeilleursArticlesParCA(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()),
				nbResult);
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<BestArticles> listBestArticles = new LinkedList<BestArticles>();
		for (Object object : listeArticles) {
			listBestArticles.add(
					new BestArticles(
							(String)((Object[])object)[0], 
							(String)((Object[])object)[1],
							(BigDecimal)((Object[])object)[2],
							(BigDecimal)((Object[])object)[3],
							(BigDecimal)((Object[])object)[4])

			);
		}

		modelDocument = new MapperBestArticlesIHMBestArticles().listeEntityToDto(listBestArticles);
		
		// Titre des colonnes
		titreColonnes = new String[] {"Code","Libellé","PU","QTE","CA"};
		// Taille des colonnes
		tailleColonnes = new String[]  {"75","145","50","75","75"};
		// Id relatives dans la classe associée
		super.idColonnes = new String[]  {"codeArticle","libelle","prixUnitaire","quantite","chiffreAffaires"};
		// Alignement des informations dans les cellules du tableau
		alignement = new int[] {SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};
	}

	

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
		setObjetIHM(BestArticlesIHM.class);


		
		// Création de l'élément graphique du tableau et affichage à l'écran
		TabClientsViewer = new LgrTableViewer(vue.getCompositeSectionTableauDroit().getTable());
		TabClientsViewer.createTableCol(vue.getCompositeSectionTableauDroit().getTable(),titreColonnes,
				tailleColonnes,1,alignement);
		TabClientsViewer.setListeChamp(idColonnes);
		
		ArticlesLabelProvider.bind(TabClientsViewer, new WritableList(modelDocument, BestArticlesIHM.class),
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
