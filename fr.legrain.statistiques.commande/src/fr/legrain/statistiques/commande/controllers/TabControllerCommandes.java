/**
 * ClientController.java
 */
package fr.legrain.statistiques.commande.controllers;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
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
public class TabControllerCommandes extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaBoncdeDAO taBoncdeDAO = null;
	private TaBoncde taBoncde = null;
	private List<CommandesTransfoIHM> modelDocumentTab1 = null;
	private List<CommandesTransfoIHM> modelDocumentTab1Export = null;
	private List<CommandesTransfoIHM> modelDocumentTab2 = null;
	private List<CommandesTransfoIHM> modelDocumentTab2Export = null;
	private List<TaBoncde> listeCommandesTransfos;
	private List<TaBoncde> listeCommandesNonTransfos;
	private String [] idColonnes;
	private List<ModelObject> modele = null;
	private FormPageControllerPrincipal masterController = null;
	private PaFormPage vue = null;
	private boolean evenementInitialise = false;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;
	private LgrTableViewer TabClientsViewer2;
	
	
	/* Constructeur */
	public TabControllerCommandes(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;
	}

	
	/**
	 * Initialise l'affichage du classement
	 * @param nbResult le nombre de résultats affichés dans le tableau
	 */
	public void initialiseModelIHM() {
		// Initialisation des éléments à afficher à l'écran

		taBoncdeDAO = new TaBoncdeDAO(masterController.getMasterDAOEM());
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		listeCommandesTransfos = taBoncdeDAO.findCommandesTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		listeCommandesNonTransfos = taBoncdeDAO.findCommandesNonTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<CommandesTransfo> listCommandesPremierTab = new LinkedList<CommandesTransfo>();
		for (TaBoncde commandes : listeCommandesTransfos) {
			listCommandesPremierTab.add(
					new CommandesTransfo(
							commandes.getCodeDocument(),
							commandes.getDateDocument(), 
							commandes.getLibelleDocument(),
							commandes.getMtHtCalc()) 

			);
		}
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<CommandesTransfo> listCommandesSecondTab = new LinkedList<CommandesTransfo>();
		for (TaBoncde commandes : listeCommandesNonTransfos) {
			listCommandesSecondTab.add(
					new CommandesTransfo(
							commandes.getCodeDocument(),
							commandes.getDateDocument(), 
							commandes.getLibelleDocument(),
							commandes.getMtHtCalc()) 

			);
		}

		modelDocumentTab1 = new MapperCommandesIHM().listeEntityToDto(listCommandesPremierTab);
		modelDocumentTab2 = new MapperCommandesIHM().listeEntityToDto(listCommandesSecondTab);
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
							getTabClientsViewer().findPositionNomChamp("codeCommandes")
					);

					String idEditor = "fr.legrain.editor.boncde.swt.multi";
				
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});
			
			vue.getCompositeSectionTableauDroit().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauDroit().getTable().getSelection()[0].getText(
							getTabClientsViewer2().findPositionNomChamp("codeCommandes")
					);

					String idEditor = "fr.legrain.editor.boncde.swt.multi";
				
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
		setObjetIHM(CommandesTransfoIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Date","Libellé","Montant HT"};
		// Taille des colonnes
		String [] tailleColonnes =  {"75","145","100","100"};
		// Id relatives dans la classe associée
		idColonnes = new String[] {"codeCommandes","dateCommandes","libelleCommandes","montantCommandes"};
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
		
		ClientsLabelProvider.bind(TabClientsViewer, new WritableList(modelDocumentTab1, CommandesTransfoIHM.class),
				BeanProperties.values(idColonnes));
		ClientsLabelProvider.bind(TabClientsViewer2, new WritableList(modelDocumentTab2, CommandesTransfoIHM.class),
				BeanProperties.values(idColonnes));

		initActions();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}
	
	class CommandesTransfo {
		
		String code = null;
		Date date = null;
		String libelle = null;
		BigDecimal montant = null;

		/**
		 * Constructeur de Meilleur Client
		 * @param code le code du commandes
		 * @param date la date du commandes
		 * @param libelle le libelle du commandes
		 * @param montant le montant du commandes
		 */
		public CommandesTransfo( String code, Date date, String libelle, BigDecimal montant) {
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
	class CommandesTransfoIHM extends ModelObject {
		String codeCommandes = null;
		Date dateCommandes = null;
		String libelleCommandes = null;
		BigDecimal montantCommandes = null;

		/**
		 * Un (best)client est défini par :
		 * @param codeTiers son codetiers
		 * @param nom son nom
		 * @param departement son département
		 * @param bigDecimal son chiffre d'Affaires
		 */
		public CommandesTransfoIHM(String code, Date date, String libelle, BigDecimal montant) {
			super();
			this.codeCommandes = code;
			this.dateCommandes = date;
			this.libelleCommandes = libelle;
			this.montantCommandes = montant;
		}

		public String getCodeCommandes() {
			return codeCommandes;
		}

		public void setCodeCommandes(String codeTiers) {
			firePropertyChange("codeCommandes", this.codeCommandes, this.codeCommandes = codeCommandes);
		}

		public String getLibelleCommandes() {
			return libelleCommandes;
		}

		public void setLibelleCommandes(String libelle) {
			firePropertyChange("libelleCommandes", this.libelleCommandes, this.libelleCommandes = libelle);
		}


		public Date getDateCommandes() {
			return dateCommandes;
		}

		public void setDateCommandes(Date date) {
			firePropertyChange("dateCommandes", this.dateCommandes, this.dateCommandes = date);
		}

		public BigDecimal getMontantCommandes() {
			return montantCommandes;
		}

		public void setMontantCommandes(BigDecimal montant) {
			firePropertyChange("montantCommandes", this.montantCommandes, this.montantCommandes = montant);
		}

	}

	class MapperCommandesIHM implements IlgrMapper<CommandesTransfoIHM, CommandesTransfo> {

		public List<CommandesTransfoIHM> listeEntityToDto(LinkedList<CommandesTransfo> l) {
			List<CommandesTransfoIHM> res = new ArrayList<CommandesTransfoIHM>();
			for (CommandesTransfo client : l) {
				res.add(entityToDto(client));
			}
			return res;
		}

		public CommandesTransfoIHM entityToDto(CommandesTransfo e) {
			return new CommandesTransfoIHM(e.getCode(),e.getDate(),e.getLibelle(),e.getMontant());
		}

		@Override
		public CommandesTransfo dtoToEntity(CommandesTransfoIHM e) {
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
			LinkedList<CommandesTransfo> listComm = new LinkedList<CommandesTransfo>();
			for (int i = 0; i < listeCommandesNonTransfos.size(); i ++){
				TaBoncde commandes = listeCommandesNonTransfos.get(i);
				listComm.add(
						new CommandesTransfo(
								commandes.getCodeDocument(),
								commandes.getDateDocument(), 
								commandes.getLibelleDocument(),
								commandes.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentTab2Export = new MapperCommandesIHM().listeEntityToDto(listComm);
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
					.getCodeCommandes() != null ? modelDocumentTab2Export
							.get(i).getCodeCommandes()
							+ ";"
							: ";";

							filecontent += modelDocumentTab2Export.get(i)
							.getLibelleCommandes() != null ? modelDocumentTab2Export
									.get(i).getLibelleCommandes()
									+ ";"
									: ";";
									filecontent += modelDocumentTab2Export.get(i)
									.getDateCommandes() != null ? modelDocumentTab2Export
											.get(i).getDateCommandes()
											+ ";" : ";";
											filecontent += modelDocumentTab2Export.get(i)
											.getMontantCommandes() != null ? modelDocumentTab2Export
													.get(i).getMontantCommandes()
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
			LinkedList<CommandesTransfo> listComm = new LinkedList<CommandesTransfo>();
			for (int i = 0; i < listeCommandesTransfos.size(); i ++){
				TaBoncde commandes = listeCommandesTransfos.get(i);
				listComm.add(
						new CommandesTransfo(
								commandes.getCodeDocument(),
								commandes.getDateDocument(), 
								commandes.getLibelleDocument(),
								commandes.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentTab1Export = new MapperCommandesIHM().listeEntityToDto(listComm);
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
				for (int i = 0; i < modelDocumentTab1Export.size(); i++) {
					filecontent += "\n";
					filecontent += modelDocumentTab1Export.get(i)
					.getCodeCommandes() != null ? modelDocumentTab1Export
							.get(i).getCodeCommandes()
							+ ";"
							: ";";

							filecontent += modelDocumentTab1Export.get(i)
							.getLibelleCommandes() != null ? modelDocumentTab1Export
									.get(i).getLibelleCommandes()
									+ ";"
									: ";";
									filecontent += modelDocumentTab1Export.get(i)
									.getDateCommandes() != null ? modelDocumentTab1Export
											.get(i).getDateCommandes()
											+ ";" : ";";
											filecontent += modelDocumentTab1Export.get(i)
											.getMontantCommandes() != null ? modelDocumentTab1Export
													.get(i).getMontantCommandes()
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
