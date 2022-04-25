/**
 * ClientController.java
 */
package fr.legrain.statistiques.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libLgrBirt.chart.UtilSerie;
import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.ClientsLabelProvider;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.ecrans.PaCompositeSectionTableauGauche;
import fr.legrain.statistiques.ecrans.PaFormPage;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.gestionDossier.GestionDossierPlugin;

/**
 * Controller permettant la génération des meilleurs/moins bons clients
 * @author nicolas²
 *
 */
public class TabControllerClients extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaFactureDAO taFactureDAO = null;
	private TaFacture taFacture = null;
	protected List<BestClientsIHM> modelDocument = null;
	protected List<BestClientsIHM> modelDocumentExport = null;
	protected List<Object> listeClients;
	private List<ModelObject> modele = null;
	protected  FormPageControllerPrincipal masterController = null;
	protected PaFormPage vue = null;
	private boolean evenementInitialise = false;
	protected  int nbResult;
	protected String [] idColonnes;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;


	/* Constructeur */
	public TabControllerClients(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
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
		listeClients = taFactureDAO.findMeilleursClientsParCA(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()),
				nbResult,false);

		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<BestClients> listBestClients = new LinkedList<BestClients>();
		for (int i = 0; i < listeClients.size() && i < nbResult; i ++){
			Object object = listeClients.get(i);
			listBestClients.add(
					new BestClients(
							(BigDecimal)((Object[])object)[0],
							(String)((Object[])object)[1], 
							(String)((Object[])object)[2],
							(String)((Object[])object)[3]) 

			);
		}

		if(nbResult>0){
			vue.getSctnTableauGauche().setText("Classement des "+nbResult
					+" meilleurs clients (sur total de "+listeClients.size()+" clients)");
		}

		if(nbResult<0){
			vue.getSctnTableauGauche().setText("Classement des "+Math.abs(nbResult)
					+" moins bons clients (sur total de "+listeClients.size()+" clients)");
		}

		modelDocument = new MapperBestClientsIHMBestClients().listeEntityToDto(listBestClients);
	}

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;


	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionTableauGauche().getSectionToolbar().add(refreshAction);
			vue.getCompositeSectionTableauGauche().getSectionToolbar().update(true);


			toolBarInitialise = true;
		}
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionTableauGauche().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauGauche().getTable().getSelection()[0].getText(
							getTabClientsViewer().findPositionNomChamp("codeTiers")
					);

					String idEditor = "fr.legrain.editor.tiers.multi";

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
		setObjetIHM(BestClientsIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Nom","Code Postal","CA"};
		// Taille des colonnes
		String [] tailleColonnes =  {"75","145","100","100"};
		// Id relatives dans la classe associée
		String[] idColonnesTemp = {"codeTiers","nom","departement","chiffreAffaire"};
		this.idColonnes = idColonnesTemp;
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

	public class BestClients {
		// date de la facture
		BigDecimal ht = null;
		// autres infos
		String codecli = null;
		String nom = null;
		String dep = null;

		/**
		 * Constructeur de Meilleur Client
		 * @param ht montant ht de la facture
		 * @param codecli le code du client
		 * @param nom le nom du client
		 * @param dep le département de résidence du client
		 */
		public BestClients( BigDecimal ht, String codecli, 
				String nom, String dep) {
			super();
			// date facture
			// infos quantitatives
			this.ht = ht;
			this.codecli = codecli;
			this.nom = nom;
			this.dep = dep;

		}


		public BigDecimal getHt() {
			return ht;
		}

		public String getCodecli() {
			return codecli;
		}

		public String getNom() {
			return nom;
		}

		public String getDep() {
			return dep;
		}


	}

	/* ------------------- Affichage Section Clients ------------------- */


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	public class BestClientsIHM extends ModelObject {
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

	public class MapperBestClientsIHMBestClients implements IlgrMapper<BestClientsIHM, BestClients> {

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

	public LgrTableViewer getTabClientsViewer() {
		return TabClientsViewer;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshAction = new Action("Exporter la liste",Activator.getImageDescriptor(PaCompositeSectionTableauGauche.iconpath)) { 
		@Override 
		public void run() {
			// On crée une nouvelle liste de Tiers
			LinkedList<BestClients> listBestClients = new LinkedList<BestClients>();
			for (int i = 0; i < listeClients.size(); i ++){
				Object object = listeClients.get(i);
				listBestClients.add(
						new BestClients(
								(BigDecimal)((Object[])object)[0],
								(String)((Object[])object)[1], 
								(String)((Object[])object)[2],
								(String)((Object[])object)[3]) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentExport = new MapperBestClientsIHMBestClients().listeEntityToDto(listBestClients);
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
					.getCodeTiers() != null ? modelDocumentExport
							.get(i).getCodeTiers()
							+ ";"
							: ";";

							filecontent += modelDocumentExport.get(i)
							.getNom() != null ? modelDocumentExport
									.get(i).getNom()
									+ ";"
									: ";";
									filecontent += modelDocumentExport.get(i)
									.getDepartement() != null ? modelDocumentExport
											.get(i).getDepartement()
											+ ";" : ";";
											filecontent += modelDocumentExport.get(i)
											.getChiffreAffaire() != null ? modelDocumentExport
													.get(i).getChiffreAffaire()
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
