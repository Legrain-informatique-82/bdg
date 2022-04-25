/**
 * ClientController.java
 */
package fr.legrain.statistiques.devis.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.statistiques.Activator;
import fr.legrain.statistiques.ClientsLabelProvider;
import fr.legrain.statistiques.Outils;
import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerMini;
import fr.legrain.statistiques.ecrans.PaCompositeSectionTableauDroit;
import fr.legrain.statistiques.ecrans.PaCompositeSectionTableauGauche;
import fr.legrain.statistiques.ecrans.PaFormPage;

/**
 * Controller permettant la génération des meilleurs/moins bons clients
 * @author nicolas²
 *
 */
public class TabControllerDevis extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaRDocument taRDocument = null;
	private TaDevis taDevis = null;
	private TaDevisDAO taDevisDAO = null;
	private List<DevisTransfoIHM> modelDocumentTab1 = null;
	private List<DevisTransfoIHM> modelDocumentTab2 = null;
	private List<DevisTransfoIHM> modelDocumentTab1Export = null;
	private List<DevisTransfoIHM> modelDocumentTab2Export = null;
	private List<ModelObject> modele = null;
	private List<TaDevis> listeDevisTransfos;
	private List<TaDevis> listeDevisNonTransfos;
	private String [] idColonnes;
	private FormPageControllerPrincipal masterController = null;
	private PaFormPage vue = null;
	private boolean evenementInitialise = false;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;
	private LgrTableViewer TabClientsViewer2;
	
	
	/* Constructeur */
	public TabControllerDevis(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}

	
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM() {
		// Initialisation des éléments à afficher à l'écran

		taRDocumentDAO = new TaRDocumentDAO(masterController.getMasterDAOEM());
		taDevisDAO = new TaDevisDAO(masterController.getMasterDAOEM());
		
		
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		listeDevisTransfos = taDevisDAO.findDevisTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		listeDevisNonTransfos = taDevisDAO.findDevisNonTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<DevisTransfo> listDevisPremierTab = new LinkedList<DevisTransfo>();
		for (TaDevis devis : listeDevisTransfos) {
			listDevisPremierTab.add(
					new DevisTransfo(
							devis.getCodeDocument(),
							devis.getDateDocument(), 
							devis.getLibelleDocument(),
							devis.getMtHtCalc()) 

			);
		}
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<DevisTransfo> listDevisSecondTab = new LinkedList<DevisTransfo>();
		for (TaDevis devis : listeDevisNonTransfos) {
			listDevisSecondTab.add(
					new DevisTransfo(
							devis.getCodeDocument(),
							devis.getDateDocument(), 
							devis.getLibelleDocument(),
							devis.getMtHtCalc()) 

			);
		}

		modelDocumentTab1 = new MapperDevisIHM().listeEntityToDto(listDevisPremierTab);
		modelDocumentTab2 = new MapperDevisIHM().listeEntityToDto(listDevisSecondTab);
	}

	/* Boolean initialisation toolbar (icon graphique) */
	private boolean toolBarInitialise = false;


	protected void initActions() {
		if(!toolBarInitialise) {
			vue.getCompositeSectionTableauDroit().getSectionToolbar().add(refreshActionDroit);
			vue.getCompositeSectionTableauDroit().getSectionToolbar().update(true);
			vue.getCompositeSectionTableauGauche().getSectionToolbar().add(refreshActionGauche);
			vue.getCompositeSectionTableauGauche().getSectionToolbar().update(true);


			toolBarInitialise = true;
		}
		if(!evenementInitialise) {
			//pour l'ouverture du document voir OngletResultatControllerJPA.java
			vue.getCompositeSectionTableauGauche().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauGauche().getTable().getSelection()[0].getText(
							getTabClientsViewer().findPositionNomChamp("codeDevis")
					);

					String idEditor = "fr.legrain.editor.devis.swt.multi";
				
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});
			
			vue.getCompositeSectionTableauDroit().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauDroit().getTable().getSelection()[0].getText(
							getTabClientsViewer2().findPositionNomChamp("codeDevis")
					);

					String idEditor = "fr.legrain.editor.devis.swt.multi";
				
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
		setObjetIHM(DevisTransfoIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Date","Libellé","Montant HT"};
		// Taille des colonnes
		String [] tailleColonnes =  {"75","145","100","100"};
		// Id relatives dans la classe associée
		idColonnes = new String[] {"codeDevis","dateDevis","libelleDevis","montantDevis"};
		// Alignement des informations dans les cellules du tableau
		int	   [] alignement = {SWT.NONE,SWT.NONE,SWT.RIGHT,SWT.RIGHT};
		
		// Création de l'élément graphique du tableau et affichage à l'écran
		TabClientsViewer = new LgrTableViewer(vue.getCompositeSectionTableauGauche().getTable());
		TabClientsViewer.createTableCol(vue.getCompositeSectionTableauGauche().getTable(),titreColonnes,
				tailleColonnes,1,alignement);
		TabClientsViewer.setListeChamp(idColonnes);
		
		TabClientsViewer2 = new LgrTableViewer(vue.getCompositeSectionTableauDroit().getTable());
		TabClientsViewer2.createTableCol(vue.getCompositeSectionTableauDroit().getTable(),titreColonnes,
				tailleColonnes,1,alignement);
		TabClientsViewer2.setListeChamp(idColonnes);
		
		ClientsLabelProvider.bind(TabClientsViewer, new WritableList(modelDocumentTab1, DevisTransfoIHM.class),
				BeanProperties.values(idColonnes));
		ClientsLabelProvider.bind(TabClientsViewer2, new WritableList(modelDocumentTab2, DevisTransfoIHM.class),
				BeanProperties.values(idColonnes));

		initActions();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}
	
	class DevisTransfo {
		
		String code = null;
		Date date = null;
		String libelle = null;
		BigDecimal montant = null;

		/**
		 * Constructeur de Meilleur Client
		 * @param code le code du devis
		 * @param date la date du devis
		 * @param libelle le libelle du devis
		 * @param montant le montant du devis
		 */
		public DevisTransfo( String code, Date date, String libelle, BigDecimal montant) {
			super();

			this.code = code;
			this.date = date;
			this.libelle = libelle;
			this.montant = montant;

		}

		public String getCode() {
			return code;
		}

		public Date getDate() {
			return date;
		}

		public String getLibelle() {
			return libelle;
		}

		public BigDecimal getMontant() {
			return montant;
		}


		

	}
	


	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les meilleurs clients
	 * @author nicolas²
	 *
	 */
	class DevisTransfoIHM extends ModelObject {
		String codeDevis = null;
		Date dateDevis = null;
		String libelleDevis = null;
		BigDecimal montantDevis = null;

		/**
		 * Un (best)client est défini par :
		 * @param codeTiers son codetiers
		 * @param nom son nom
		 * @param departement son département
		 * @param bigDecimal son chiffre d'Affaires
		 */
		public DevisTransfoIHM(String code, Date date, String libelle, BigDecimal montant) {
			super();
			this.codeDevis = code;
			this.dateDevis = date;
			this.libelleDevis = libelle;
			this.montantDevis = montant;
		}

		public String getCodeDevis() {
			return codeDevis;
		}

		public void setCodeDevis(String codeTiers) {
			firePropertyChange("codeDevis", this.codeDevis, this.codeDevis = codeDevis);
		}

		public String getLibelleDevis() {
			return libelleDevis;
		}

		public void setLibelleDevis(String libelle) {
			firePropertyChange("libelleDevis", this.libelleDevis, this.libelleDevis = libelle);
		}


		public Date getDateDevis() {
			return dateDevis;
		}

		public void setDateDevis(Date date) {
			firePropertyChange("dateDevis", this.dateDevis, this.dateDevis = date);
		}

		public BigDecimal getMontantDevis() {
			return montantDevis;
		}

		public void setMontantDevis(BigDecimal montant) {
			firePropertyChange("montantDevis", this.montantDevis, this.montantDevis = montant);
		}

	}

	class MapperDevisIHM implements IlgrMapper<DevisTransfoIHM, DevisTransfo> {

		public List<DevisTransfoIHM> listeEntityToDto(LinkedList<DevisTransfo> l) {
			List<DevisTransfoIHM> res = new ArrayList<DevisTransfoIHM>();
			for (DevisTransfo client : l) {
				res.add(entityToDto(client));
			}
			return res;
		}

		public DevisTransfoIHM entityToDto(DevisTransfo e) {
			return new DevisTransfoIHM(e.getCode(),e.getDate(),e.getLibelle(),e.getMontant());
		}

		@Override
		public DevisTransfo dtoToEntity(DevisTransfoIHM e) {
			// TODO Auto-generated method stub
			return null;
		}


	}
	
	public LgrTableViewer getTabClientsViewer() {
		return TabClientsViewer;
	}


	public LgrTableViewer getTabClientsViewer2() {
		return TabClientsViewer2;
	}
	
	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action refreshActionDroit = new Action("Exporter la liste",Activator.getImageDescriptor(PaCompositeSectionTableauDroit.iconpath)) { 
		@Override 
		public void run() {
			// On crée une nouvelle liste de Tiers
			LinkedList<DevisTransfo> listComm = new LinkedList<DevisTransfo>();
			for (int i = 0; i < listeDevisNonTransfos.size(); i ++){
				TaDevis commandes = listeDevisNonTransfos.get(i);
				listComm.add(
						new DevisTransfo(
								commandes.getCodeDocument(),
								commandes.getDateDocument(), 
								commandes.getLibelleDocument(),
								commandes.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentTab2Export = new MapperDevisIHM().listeEntityToDto(listComm);
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
				for (int i = 0; i < modelDocumentTab2Export.size(); i++) {
					filecontent += "\n";
					filecontent += modelDocumentTab2Export.get(i)
					.getCodeDevis() != null ? modelDocumentTab2Export
							.get(i).getCodeDevis()
							+ ";"
							: ";";

							filecontent += modelDocumentTab2Export.get(i)
							.getLibelleDevis() != null ? modelDocumentTab2Export
									.get(i).getLibelleDevis()
									+ ";"
									: ";";
									filecontent += modelDocumentTab2Export.get(i)
									.getDateDevis() != null ? modelDocumentTab2Export
											.get(i).getDateDevis()
											+ ";" : ";";
											filecontent += modelDocumentTab2Export.get(i)
											.getMontantDevis() != null ? modelDocumentTab2Export
													.get(i).getMontantDevis()
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
	
	private Action refreshActionGauche = new Action("Exporter la liste",Activator.getImageDescriptor(PaCompositeSectionTableauGauche.iconpath)) { 
		@Override 
		public void run() {
			// On crée une nouvelle liste de Tiers
			LinkedList<DevisTransfo> listDoc = new LinkedList<DevisTransfo>();
			for (int i = 0; i < listeDevisTransfos.size(); i ++){
				TaDevis devis = listeDevisTransfos.get(i);
				listDoc.add(
						new DevisTransfo(
								devis.getCodeDocument(),
								devis.getDateDocument(), 
								devis.getLibelleDocument(),
								devis.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			modelDocumentTab1Export = new MapperDevisIHM().listeEntityToDto(listDoc);
			// création de la fenêtre permettant l'enregistrement du fichier
			FileDialog dd = new FileDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.SAVE);
			// Configuration de la fenêtre
			dd.setText("Exporter en fichier csv");
			dd.setFileName("*.csv");
			dd.setFilterExtensions(new String[] { "*.csv" });
			dd.setOverwrite(true);

			// Récupération des informations
			String choix = null;
			String repDestination = GestionDossierPlugin.getDefault()
			.getPreferenceStore().getString("repertoire_w");
			if (repDestination.equals(""))
				repDestination = Platform.getInstanceLocation().getURL().getFile();
			dd.setFilterPath(LibChaine.pathCorrect(repDestination));
			choix = dd.open();
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
				for (int i = 0; i < modelDocumentTab1Export.size(); i++) {
					filecontent += "\n";
					filecontent += modelDocumentTab1Export.get(i)
					.getCodeDevis() != null ? modelDocumentTab1Export
							.get(i).getCodeDevis()
							+ ";"
							: ";";

							filecontent += modelDocumentTab1Export.get(i)
							.getLibelleDevis() != null ? modelDocumentTab1Export
									.get(i).getLibelleDevis()
									+ ";"
									: ";";
									filecontent += modelDocumentTab1Export.get(i)
									.getDateDevis() != null ? modelDocumentTab1Export
											.get(i).getDateDevis()
											+ ";" : ";";
											filecontent += modelDocumentTab1Export.get(i)
											.getMontantDevis() != null ? modelDocumentTab1Export
													.get(i).getMontantDevis()
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
