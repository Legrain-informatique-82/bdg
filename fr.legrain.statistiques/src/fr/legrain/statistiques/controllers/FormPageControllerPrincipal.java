package fr.legrain.statistiques.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.statistiques.ecrans.PaFormPage;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.controllers.TabControllerArticles.BestArticles;
import fr.legrain.statistiques.controllers.TabControllerClients.BestClients;
import fr.legrain.statistiques.controllers.a_supprimer.JaugeControllerPrincipal;
import fr.legrain.tiers.dao.TaTiers;


public class FormPageControllerPrincipal  {

	static Logger logger = Logger.getLogger(FormPageControllerPrincipal.class.getName());


	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	// Vue
	protected PaFormPage vue = null;

	// Controllers
	protected ParamControllerMini paramControllerMini = null;
	private MontantControllerPrincipal caMontantController = null;
	private GraphControllerMini graphControllerMini = null;
	private TabControllerClients clientsController = null;
	private TabControllerArticles articlesController = null;
	private DocumentsControllerPrincipal documentsController = null;
	private JaugeController facturesController = null;
	private RepartitionControllerMini	repartitionControllerMini =  null;
	protected Date datedeb;
	protected Date datefin;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void appel(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerPrincipal() {
		super();
	}


	public FormPageControllerPrincipal(PaFormPage vue) {
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		this.vue = vue;
		paramControllerMini = new ParamControllerMini(this,vue, null);
		caMontantController = new MontantControllerPrincipal(this,vue,null);
		graphControllerMini = new GraphControllerPrincipal(this,vue,null);
		clientsController = new TabControllerClients(this,vue,null);
		articlesController = new TabControllerArticles(this,vue,null);
		documentsController = new DocumentsControllerPrincipal(this,vue,null);
		facturesController = new JaugeControllerPrincipal(this,vue,null);
		repartitionControllerMini = new RepartitionControllerPrincipal(this,vue,null);
	}

	private void initialisationModel(boolean tout) {
		try {
			// if(masterDAO!=null && masterEntity!=null) {

			if(tout) {
				// Initialisation des paramètres
				paramControllerMini.initialiseModelIHM();
				
			}
			// Initialisation du chiffre d'affaires
			caMontantController.initialiseModelIHM();
			caMontantController.bind();

			// Initialisation du graphique
			graphControllerMini.initialiseModelIHM();
			graphControllerMini.bind();

			// Initialisation des meilleurs clients
			clientsController.initialiseModelIHM(10); // TOP10
			clientsController.bind();

			// Initialisation des meilleurs articles
			articlesController.initialiseModelIHM(10); // TOP10
			articlesController.bind();

			// Initialisation des documents
			documentsController.initialiseModelIHM();
			documentsController.bind();
			
			// Initialisation de la carte
			repartitionControllerMini.initialiseModelIHM();
			repartitionControllerMini.bind();
			
			//  @busy : Mise en attente du Form (icone chargement) pendant le traitement des données de factures
			vue.getForm().setBusy(true);
			
			Thread t = new Thread(){
//			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					// Initialisation des factures
					facturesController.initialiseModelIHM();
					facturesController.bind(); // @busy : La fin du chargement se fait dans le bind
				}
//			});
			};
			t.start();
			
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


	public EntityManager getMasterDAOEM() {
		return masterDAO;
	}


	public void setMasterDAOEM(EntityManager masterDAO) {
		this.masterDAO = masterDAO;
	}






	/* ------------------- Affichage Section Clients ------------------- */


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	class BestClientsIHM extends ModelObject {
		String codeTiers = null;
		String nom = null;
		String departement = null;
		BigDecimal chiffreAffaire = null;

		/**
		 * Un (best)client est défini par :
		 * @param codeTiers son codetiers
		 * @param nom son nom
		 * @param departement son département
		 * @param bigDecimal son chiffre d'Affaires
		 */
		public BestClientsIHM(String codeTiers, String nom, String departement, BigDecimal bigDecimal) {
			super();
			this.codeTiers = codeTiers;
			this.nom = nom;
			this.departement = departement;
			this.chiffreAffaire = bigDecimal;
		}

		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			firePropertyChange("nom", this.nom, this.nom = nom);
		}


		public String getDepartement() {
			return departement;
		}

		public void setDepartement(String departement) {
			firePropertyChange("departement", this.departement, this.departement = departement);
		}

		public BigDecimal getChiffreAffaire() {
			return chiffreAffaire;
		}

		public void setChiffreAffaire(BigDecimal chiffreAffaire) {
			firePropertyChange("chiffreAffaire", this.chiffreAffaire, this.chiffreAffaire = chiffreAffaire);
		}

	}

	class MapperBestClientsIHMBestClients implements IlgrMapper<BestClientsIHM, BestClients> {

		public List<BestClientsIHM> listeEntityToDto(LinkedList<BestClients> l) {
			List<BestClientsIHM> res = new ArrayList<BestClientsIHM>();
			for (BestClients client : l) {
				res.add(entityToDto(client));
			}
			return res;
		}

		public BestClientsIHM entityToDto(BestClients e) {
			return new BestClientsIHM(e.getCodecli(),e.getNom(),e.getDep(),e.getHt());
		}

		@Override
		public BestClients dtoToEntity(BestClientsIHM e) {
			// TODO Auto-generated method stub
			return null;
		}
	}


	/* ------------------- Affichage Section Articles ------------------- */

	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	class BestArticlesIHM extends ModelObject {
		//Infos sur l'article
		String codeArticle = null;
		String libelle = null;
		BigDecimal prixUnitaire = null;
		// Infos calculées
		BigDecimal quantite = null;
		BigDecimal chiffreAffaires = null;

		/**
		 * Un (best)client est défini par :
		 * @param codeArticle le code de l'article
		 * @param libelle le libelle de l'article
		 * @param prixUnitaire le prix unitaire del 'article
		 * @param quantite la quantite achetée d'articles sur la période
		 * @param chiffreAffaires le ca généré par l'article sur la période
		 */
		public BestArticlesIHM(String codeArticle, String libelle, BigDecimal prixUnitaire,
				BigDecimal quantite, BigDecimal chiffreAffaires) {
			super();
			//Infos sur l'article
			this.codeArticle = codeArticle;
			this.libelle = libelle;
			this.prixUnitaire = prixUnitaire;
			// Infos calculées
			this.quantite = quantite;
			this.chiffreAffaires = chiffreAffaires;
		}

		public String getCodeArticle() {
			return codeArticle;
		}

		public void setCodeArticles(String codeArticle) {
			firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = codeArticle);
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			firePropertyChange("libelle", this.libelle, this.libelle = libelle);
		}


		public BigDecimal getPrixUnitaire() {
			return prixUnitaire;
		}

		public void setPrixUnitaire(BigDecimal prixUnitaire) {
			firePropertyChange("prixUnitaire", this.prixUnitaire, this.prixUnitaire = prixUnitaire);
		}

		public BigDecimal getQuantite() {
			return quantite;
		}

		public void setQuantite(BigDecimal quantite) {
			firePropertyChange("quantite", this.quantite, this.quantite = quantite);
		}

		public BigDecimal getChiffreAffaires() {
			return chiffreAffaires;
		}

		public void setChiffreAffaire(BigDecimal chiffreAffaires) {
			firePropertyChange("chiffreAffaires", this.chiffreAffaires, this.chiffreAffaires = chiffreAffaires);
		}

	}

	class MapperBestArticlesIHMBestArticles implements IlgrMapper<BestArticlesIHM, BestArticles> {

		public List<BestArticlesIHM> listeEntityToDto(LinkedList<BestArticles> l) {
			List<BestArticlesIHM> res = new ArrayList<BestArticlesIHM>();
			for (BestArticles article : l) {
				res.add(entityToDto(article));
			}
			return res;
		}

		public BestArticlesIHM entityToDto(BestArticles e) {
			return new BestArticlesIHM(e.getCodeArticle(),e.getLibelle(),e.getPrixUnitaire(),e.getQuantite(),e.getChiffreAffaires());
		}


		@Override
		public BestArticles dtoToEntity(BestArticlesIHM e) {
			// TODO Auto-generated method stub
			return null;
		}

	}




}
