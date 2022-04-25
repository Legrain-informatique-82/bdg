/**
 * ClientController.java
 */
package fr.legrain.statistiques.proforma.controllers;

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

import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
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
public class TabControllerProforma extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(MontantControllerMini.class.getName());	

	private Class objetIHM = null;
	private TaRDocumentDAO taRDocumentDAO = null;
	private TaRDocument taRDocument = null;
	private TaProforma taProforma = null;
	private TaProformaDAO taProformaDAO = null;
	private List<ProformaTransfoIHM> modelDocumentTab1 = null;
	private List<ProformaTransfoIHM> modelDocumentTab2 = null;
	private List<ProformaTransfoIHM> modelDocumentTab1Export = null;
	private List<ProformaTransfoIHM> modelDocumentTab2Export = null;
	private List<TaProforma> listeProformaTransfos;
	private List<TaProforma> listeProformaNonTransfos;
	private List<ModelObject> modele = null;
	private String [] idColonnes;
	private FormPageControllerPrincipal masterController = null;
	private PaFormPage vue = null;
	private boolean evenementInitialise = false;
	private Realm realm;
	private LgrTableViewer TabClientsViewer;
	private LgrTableViewer TabClientsViewer2;
	
	
	/* Constructeur */
	public TabControllerProforma(FormPageControllerPrincipal masterContoller, PaFormPage vue, EntityManager em) {
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
		taProformaDAO = new TaProformaDAO(masterController.getMasterDAOEM());
		
		
		// On récupère le l'ensemble des factures sur la date passée en Paramètres
		listeProformaTransfos = taProformaDAO.findProformaTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		listeProformaNonTransfos = taProformaDAO.findProformaNonTransfos(
				Outils.extractDate(vue.getCompositeSectionParam().getCdateDeb()),
				Outils.extractDate(vue.getCompositeSectionParam().getCdatefin()));
		
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<ProformaTransfo> listProformaPremierTab = new LinkedList<ProformaTransfo>();
		for (TaProforma proforma : listeProformaTransfos) {
			listProformaPremierTab.add(
					new ProformaTransfo(
							proforma.getCodeDocument(),
							proforma.getDateDocument(), 
							proforma.getLibelleDocument(),
							proforma.getMtHtCalc()) 

			);
		}
		
		// Liste qui va contenir les informations utiles pour les factures
		LinkedList<ProformaTransfo> listProformaSecondTab = new LinkedList<ProformaTransfo>();
		for (TaProforma proforma : listeProformaNonTransfos) {
			listProformaSecondTab.add(
					new ProformaTransfo(
							proforma.getCodeDocument(),
							proforma.getDateDocument(), 
							proforma.getLibelleDocument(),
							proforma.getMtHtCalc()) 

			);
		}

		modelDocumentTab1 = new MapperProformaIHM().listeEntityToDto(listProformaPremierTab);
		modelDocumentTab2 = new MapperProformaIHM().listeEntityToDto(listProformaSecondTab);
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
							getTabClientsViewer().findPositionNomChamp("codeProforma")
					);

					String idEditor = "fr.legrain.editor.proforma.swt.multi";
				
					LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				}

			});
			
			vue.getCompositeSectionTableauDroit().getTable().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = vue.getCompositeSectionTableauDroit().getTable().getSelection()[0].getText(
							getTabClientsViewer2().findPositionNomChamp("codeProforma")
					);

					String idEditor = "fr.legrain.editor.proforma.swt.multi";
				
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
		setObjetIHM(ProformaTransfoIHM.class);

		// Titre des colonnes
		String [] titreColonnes = {"Code","Date","Libellé","Montant HT"};
		// Taille des colonnes
		String [] tailleColonnes =  {"75","145","100","100"};
		// Id relatives dans la classe associée
		idColonnes = new String[] {"codeProforma","dateProforma","libelleProforma","montantProforma"};
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
		
		ClientsLabelProvider.bind(TabClientsViewer, new WritableList(modelDocumentTab1, ProformaTransfoIHM.class),
				BeanProperties.values(idColonnes));
		ClientsLabelProvider.bind(TabClientsViewer2, new WritableList(modelDocumentTab2, ProformaTransfoIHM.class),
				BeanProperties.values(idColonnes));

		initActions();
	}


	@Override
	protected void initMapComposantChamps() {
		mapComposantChamps = new HashMap<Control, String>();

	}
	
	class ProformaTransfo {
		
		String code = null;
		Date date = null;
		String libelle = null;
		BigDecimal montant = null;

		/**
		 * Constructeur de Meilleur Client
		 * @param code le code du proforma
		 * @param date la date du proforma
		 * @param libelle le libelle du proforma
		 * @param montant le montant du proforma
		 */
		public ProformaTransfo( String code, Date date, String libelle, BigDecimal montant) {
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
	class ProformaTransfoIHM extends ModelObject {
		String codeProforma = null;
		Date dateProforma = null;
		String libelleProforma = null;
		BigDecimal montantProforma = null;

		/**
		 * Un (best)client est défini par :
		 * @param codeTiers son codetiers
		 * @param nom son nom
		 * @param departement son département
		 * @param bigDecimal son chiffre d'Affaires
		 */
		public ProformaTransfoIHM(String code, Date date, String libelle, BigDecimal montant) {
			super();
			this.codeProforma = code;
			this.dateProforma = date;
			this.libelleProforma = libelle;
			this.montantProforma = montant;
		}

		public String getCodeProforma() {
			return codeProforma;
		}

		public void setCodeProforma(String codeTiers) {
			firePropertyChange("codeProforma", this.codeProforma, this.codeProforma = codeProforma);
		}

		public String getLibelleProforma() {
			return libelleProforma;
		}

		public void setLibelleProforma(String libelle) {
			firePropertyChange("libelleProforma", this.libelleProforma, this.libelleProforma = libelle);
		}


		public Date getDateProforma() {
			return dateProforma;
		}

		public void setDateProforma(Date date) {
			firePropertyChange("dateProforma", this.dateProforma, this.dateProforma = date);
		}

		public BigDecimal getMontantProforma() {
			return montantProforma;
		}

		public void setMontantProforma(BigDecimal montant) {
			firePropertyChange("montantProforma", this.montantProforma, this.montantProforma = montant);
		}

	}

	class MapperProformaIHM implements IlgrMapper<ProformaTransfoIHM, ProformaTransfo> {

		public List<ProformaTransfoIHM> listeEntityToDto(LinkedList<ProformaTransfo> l) {
			List<ProformaTransfoIHM> res = new ArrayList<ProformaTransfoIHM>();
			for (ProformaTransfo client : l) {
				res.add(entityToDto(client));
			}
			return res;
		}

		public ProformaTransfoIHM entityToDto(ProformaTransfo e) {
			return new ProformaTransfoIHM(e.getCode(),e.getDate(),e.getLibelle(),e.getMontant());
		}

		@Override
		public ProformaTransfo dtoToEntity(ProformaTransfoIHM e) {
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
			LinkedList<ProformaTransfo> listDoc = new LinkedList<ProformaTransfo>();
			for (int i = 0; i < listeProformaNonTransfos.size(); i ++){
				TaProforma doc = listeProformaNonTransfos.get(i);
				listDoc.add(
						new ProformaTransfo(
								doc.getCodeDocument(),
								doc.getDateDocument(), 
								doc.getLibelleDocument(),
								doc.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentTab2Export = new MapperProformaIHM().listeEntityToDto(listDoc);
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
					.getCodeProforma() != null ? modelDocumentTab2Export
							.get(i).getCodeProforma()
							+ ";"
							: ";";

							filecontent += modelDocumentTab2Export.get(i)
							.getLibelleProforma() != null ? modelDocumentTab2Export
									.get(i).getLibelleProforma()
									+ ";"
									: ";";
									filecontent += modelDocumentTab2Export.get(i)
									.getDateProforma() != null ? modelDocumentTab2Export
											.get(i).getDateProforma()
											+ ";" : ";";
											filecontent += modelDocumentTab2Export.get(i)
											.getMontantProforma() != null ? modelDocumentTab2Export
													.get(i).getMontantProforma()
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
			LinkedList<ProformaTransfo> listDoc = new LinkedList<ProformaTransfo>();
			for (int i = 0; i < listeProformaTransfos.size(); i ++){
				TaProforma devis = listeProformaTransfos.get(i);
				listDoc.add(
						new ProformaTransfo(
								devis.getCodeDocument(),
								devis.getDateDocument(), 
								devis.getLibelleDocument(),
								devis.getMtHtCalc()) 

				);
			}
			// On crée le modele prêt à être exporté 
			
			modelDocumentTab1Export = new MapperProformaIHM().listeEntityToDto(listDoc);
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
					.getCodeProforma() != null ? modelDocumentTab1Export
							.get(i).getCodeProforma()
							+ ";"
							: ";";

							filecontent += modelDocumentTab1Export.get(i)
							.getLibelleProforma() != null ? modelDocumentTab1Export
									.get(i).getLibelleProforma()
									+ ";"
									: ";";
									filecontent += modelDocumentTab1Export.get(i)
									.getDateProforma() != null ? modelDocumentTab1Export
											.get(i).getDateProforma()
											+ ";" : ";";
											filecontent += modelDocumentTab1Export.get(i)
											.getMontantProforma() != null ? modelDocumentTab1Export
													.get(i).getMontantProforma()
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
