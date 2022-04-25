/**
 * Articles.java
 */
package fr.legrain.statistiques.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.ArticlesLabelProvider;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaCompositeSectionTableauDroit;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Controller permettant la génération des meilleurs/moins bons articles
 * @author nicolas²
 *
 */
public class TabControllerArticles extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaFactureDAO taFactureDAO = null;
	private TaFacture taFacture = null;
	protected List<BestArticlesIHM> modelDocument = null;
	protected List<BestArticlesIHM> modelDocumentExport = null;
	protected List<Object> listeArticles;
	private List<ModelObject> modele = null;
	protected FormPageControllerPrincipal masterController = null;
	protected PaFormPage vue = null;
	private boolean evenementInitialise = false;
	protected int nbResult;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;

	protected String [] titreColonnes;
	protected String [] tailleColonnes;
	protected String [] idColonnes;
	protected int	   [] alignement;


	/* Constructeur */
	public TabControllerArticles(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}


	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM(int nbResult) {
		// Initialisation des éléments à afficher à l'écran
		// On initialise le nombre de résultats
		this.nbResult = nbResult;


		taFactureDAO = new TaFactureDAO(masterController.getMasterDAOEM());
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		listeArticles = taFactureDAO.findMeilleursArticlesParCA(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()),
				nbResult,false);

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<BestArticles> listBestArticles = new LinkedList<BestArticles>();
		for (int i = 0; i < listeArticles.size() && i < nbResult; i ++){
			Object object = listeArticles.get(i);
			listBestArticles.add(
					new BestArticles(
							(String)((Object[])object)[0], 
							(String)((Object[])object)[1],
							(BigDecimal)((Object[])object)[2],
							(BigDecimal)((Object[])object)[3],
							(BigDecimal)((Object[])object)[4]));
		}

		vue.getSctnTableauDroit().setText("Classement des "+nbResult
				+" meilleurs articles (sur total de "+listeArticles.size()+" articles)");
		modelDocument = new MapperBestArticlesIHMBestArticles().listeEntityToDto(listBestArticles);

		// Titre des colonnes
		titreColonnes = new String[] {"Code","Libellé","PU","QTE","CA"};
		// Taille des colonnes
		tailleColonnes = new String[]  {"75","145","50","75","75"};
		// Id relatives dans la classe associée
		idColonnes = new String[]  {"codeArticle","libelle","prixUnitaire","quantite","chiffreAffaires"};
		// Alignement des informations dans les cellules du tableau
		alignement = new int[] {SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT,SWT.RIGHT};
	}


	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;


	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionTableauDroit().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionTableauDroit().getSectionToolbar().update(true);


			toolBarInitialise = true;
		}
		
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionTableauDroit().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauDroit().getTable().getSelection()[0].getText(
							getTabClientsViewer().findPositionNomChamp("codeArticle")
					);

					String idEditor = "fr.legrain.editor.article.multi";

					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}



			});

			evenementInitialise = true;
		}
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

	public class BestArticles {
		//Infos sur l'article
		String codeArticle = null;
		String libelle = null;
		BigDecimal prixUnitaire = null;
		// Infos calculées
		BigDecimal quantite = null;
		BigDecimal chiffreAffaires = null;

		/**
		 * Constructeur de Meilleur Article
		 * @param codeArticle le code de l'article
		 * @param libelle le libelle de l'article
		 * @param prixUnitaire le prix unitaire del 'article
		 * @param quantite la quantite achetée d'articles sur la période
		 * @param chiffreAffaires le ca généré par l'article sur la période
		 */
		public BestArticles( String codeArticle, String libelle, BigDecimal prixUnitaire,
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

		public String getLibelle() {
			return libelle;
		}

		public BigDecimal getPrixUnitaire() {
			return prixUnitaire;
		}

		public BigDecimal getQuantite() {
			return quantite;
		}

		public BigDecimal getChiffreAffaires() {
			return chiffreAffaires;
		}





	}

	/* ------------------- Affichage Section Articles ------------------- */

	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	public class BestArticlesIHM extends ModelObject {
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

	public class MapperBestArticlesIHMBestArticles implements IlgrMapper<BestArticlesIHM, BestArticles> {

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

	public LgrTableViewer getTabClientsViewer() {
		return TabClientsViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Exporter la liste",Activator.getImageDescriptor(PaCompositeSectionTableauDroit.iconpath)) { 
		@Override 
		public void run() { 
			// On crée une nouvelle liste de Tiers
			LinkedList<BestArticles> listBestArticles = new LinkedList<BestArticles>();
			for (int i = 0; i < listeArticles.size(); i ++){
				Object object = listeArticles.get(i);
				listBestArticles.add(
						new BestArticles(
								(String)((Object[])object)[0], 
								(String)((Object[])object)[1],
								(BigDecimal)((Object[])object)[2],
								(BigDecimal)((Object[])object)[3],
								(BigDecimal)((Object[])object)[4])


				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentExport = new MapperBestArticlesIHMBestArticles().listeEntityToDto(listBestArticles);
			// création de la fenêtre permettant l'enregistrement du fichier
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.SAVE);
			// Configuration de la fenêtre
			dd.setText("Exporter en fichier csv");
			dd.setFileName("*.csv");
			dd.setFilterExtensions(new String[] { "*.csv" });
			dd.setOverwrite(true);

			// Récupération des informations
			String repDestination = GestionDossierPlugin.getDefault()
			.getPreferenceStore().getString("repertoire_w");
			if (repDestination.equals(""))
				repDestination = Platform.getInstanceLocation().getURL()
				.getFile();
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			String choix = dd.open();
			System.err.println(choix);

			// Si la fenetre de dialogue n'a pas été fermée volontairement
			if (choix != null) {
				String filecontent = "";
				// Première ligne contenant les id des colonnes
				for (int i = 0; i < idColonnes.length - 1; i++) {
					filecontent += idColonnes[i] + ";";
				}
				filecontent += idColonnes[idColonnes.length - 1];

				// Contenu du tableau
				for (int i = 0; i < modelDocumentExport.size(); i++) {
					filecontent += "\n";
					filecontent += modelDocumentExport.get(i)
					.getCodeArticle() != null ? modelDocumentExport
							.get(i).getCodeArticle()
							+ ";"
							: ";";

							filecontent += modelDocumentExport.get(i)
							.getLibelle() != null ? modelDocumentExport
									.get(i).getLibelle()
									+ ";"
									: ";";
									filecontent += modelDocumentExport.get(i)
									.getChiffreAffaires() != null ? modelDocumentExport
											.get(i).getChiffreAffaires()
											+ ";" : ";";
											filecontent += modelDocumentExport.get(i)
											.getPrixUnitaire() != null ? modelDocumentExport
													.get(i).getPrixUnitaire()
													+ ";"
													: ";";
													filecontent += modelDocumentExport.get(i)
													.getQuantite() != null ? modelDocumentExport
															.get(i).getQuantite()
															+ ";"
															: ";";
				}

				PrintStream out;
				try {
					out = new PrintStream(new FileOutputStream(choix),
							true, "UTF-8");
					out.print(filecontent);
					out.flush();
					out.close();
					out = null;
				} catch (UnsupportedEncodingException e1) {
					logger.error("",e1);
				} catch (FileNotFoundException e1) {
					logger.error("",e1);
				}
			}
		}
	};





}
