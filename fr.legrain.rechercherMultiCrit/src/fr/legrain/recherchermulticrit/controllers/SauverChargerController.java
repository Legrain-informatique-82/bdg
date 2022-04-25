package fr.legrain.recherchermulticrit.controllers;



import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.ecrans.PaCompositeSectionEtape1;
import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.requete.dao.TaLigneRequete;
import fr.legrain.requete.dao.TaLigneRequeteDAO;
import fr.legrain.requete.dao.TaRequete;
import fr.legrain.requete.dao.TaRequeteDAO;


public class SauverChargerController extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(Etape3Controller.class.getName());

	private FormPageController masterController = null;

	private PaFormPage vue = null;



	/* Constructeur par défaut */
	public SauverChargerController(FormPageController masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	public void initialiseModelIHM() {
		initActions();
	}




	@Override
	protected void initActions() {			
		// Initialisation de la combo box
		// -- Récupération des requêtes dans la BD --
		initCombo();
		addListenerChargement(vue.getComposite_SauverCharger().getButtonCharger());
		addListenerSuppression(vue.getComposite_SauverCharger().getButtonSupprimer());
		addListenerSauvegarde(vue.getComposite_SauverCharger().getButtonSauver());
		addListenerExport(vue.getComposite_SauverCharger().getButtonExporter());
		addListenerImport(vue.getComposite_SauverCharger().getButtonImporter());
	}

	@Override
	public void bind(){
		if(mapComposantChamps==null) {
			initMapComposantChamps();
		}
	}


	@Override
	protected void initMapComposantChamps() {

	}

	/**
	 * Méthode permettant de sauver une recherche
	 * @param btnDeSauvegarde
	 */
	private void addListenerSauvegarde(Button btnDeSauvegarde) {
		btnDeSauvegarde.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {		
				String nomSauvegarde = vue.getComposite_SauverCharger().getNomSauvegarde().getText();
				String resultat = "";
				TaRequeteDAO daoRequete = new TaRequeteDAO();
				if (nomSauvegarde!=""){
					try {
						masterController.getEtape2Controller().sauverCriteres(nomSauvegarde,true);
					} catch (Exception e1) {
						logger.error("",e1);
					}
					initCombo();
				} else {
					vue.getComposite_SauverCharger().getInfoSauvegarde().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					vue.getComposite_SauverCharger().getInfoSauvegarde().setText("" +
					"Le nom de la sauvegarde ne peut être vide.");
					vue.getComposite_SauverCharger().getInfoCharge().setText("");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant de charger une recherche
	 * @param btnDeCharge
	 */
	private void addListenerChargement(Button btnDeCharge) {
		btnDeCharge.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {		
				TaRequeteDAO daoRequete = new TaRequeteDAO();
				TaLigneRequeteDAO daoLigne = new TaLigneRequeteDAO();
				String nomSauvegarde = vue.getComposite_SauverCharger().getCombo().getText();
				int id = 0;
				String resultat = "";
				if (nomSauvegarde!=""){
					id = daoRequete.whatIsMyId(nomSauvegarde);
					if (id>=0){
						masterController.getEtape2Controller().chargerCriteres(daoLigne.selectByIdRequete(id));
					} else {
						vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
						vue.getComposite_SauverCharger().getInfoCharge().setText("" +
						"Impossible de charger la recherche.");
						vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
					}
				} else {
					vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					vue.getComposite_SauverCharger().getInfoCharge().setText("" +
					"Impossible de charger la recherche.");
					vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant de charger une recherche
	 * @param btnDeCharge
	 */
	private void addListenerSuppression(Button btnDeSuppr) {
		btnDeSuppr.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {		
				TaRequeteDAO daoRequete = new TaRequeteDAO();
				String nomSauvegarde = vue.getComposite_SauverCharger().getCombo().getText();
				if (nomSauvegarde!=""){
					TaRequete entity = daoRequete.whatIsMyIdFull(nomSauvegarde);
					String ttlErreur = "Suppression de la recherche";
					String msgErreur = "Êtes vous sûr(e) de vouloir supprimer la recherche sélectionnée ?";
					Boolean reponse = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreur, msgErreur);
					if(entity!=null && reponse){
						EntityTransaction transaction = daoRequete.getEntityManager().getTransaction();
						try {
							daoRequete.begin(transaction);
							daoRequete.remove(entity);
							daoRequete.commit(transaction);
							initCombo();
							vue.getComposite_SauverCharger().getButtonSupprimer().removeSelectionListener(this);
							addListenerSuppression(vue.getComposite_SauverCharger().getButtonSupprimer());
						} catch (Exception e1) {
							logger.error("",e1);
						}
					} else {
						vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
						vue.getComposite_SauverCharger().getInfoCharge().setText("" +
						"Suppression interrompue.");
						vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
					}					
				} else {
					vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					vue.getComposite_SauverCharger().getInfoCharge().setText("" +
					"Impossible de supprimer.");
					vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant d'exporter une recherche
	 * @param btnDExport
	 */
	private void addListenerExport(Button btnDExport) {
		btnDExport.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {		
				// création de la fenêtre permettant l'enregistrement du fichier
				FileDialog dd = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.SAVE);
				// Configuration de la fenêtre
				dd.setText("Exporter la recherche en cours");
				dd.setFileName("*.xml");
				dd.setFilterExtensions(new String[] { "*.xml" });
				dd.setOverwrite(true);

				// Récupération des informations
				String repDestination = GestionDossierPlugin.getDefault()
				.getPreferenceStore().getString("repertoire_w");
				if (repDestination.equals(""))
					repDestination = Platform.getInstanceLocation().getURL()
					.getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				String name = dd.getFileName();
				System.err.println(choix);
				if (choix!=null){
					PrintStream out;
					try {
						out = new PrintStream(new FileOutputStream(choix),
								true, "UTF-8");
						JAXBContext jaxbContext;
						Marshaller marshaller;
						TaRequeteDAO daoRequete = new TaRequeteDAO();
						TaRequete test;
						test = masterController.getEtape2Controller().sauverCriteres(name,false);
						jaxbContext = JAXBContext.newInstance(TaRequete.class);		
						marshaller = jaxbContext.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						marshaller.marshal(test, out);
						out.flush();
						out.close();
						out = null;
					} catch (UnsupportedEncodingException e1) {
						logger.error("",e1);
					} catch (FileNotFoundException e1) {
						logger.error("",e1);
					} catch (Exception e1) {
						logger.error("",e1);
					}
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant d'importer une recherche
	 * @param btnDeCharge
	 */
	private void addListenerImport(Button btnDImport) {
		btnDImport.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {		

				FileDialog dd = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.NONE);
				// Configuration de la fenêtre
				dd.setText("Charger un fichier de recherche");
				dd.setFilterExtensions(new String[] { "*.xml" });

				// Récupération des informations
				String repDestination = GestionDossierPlugin.getDefault()
				.getPreferenceStore().getString("repertoire_w");
				if (repDestination.equals(""))
					repDestination = Platform.getInstanceLocation().getURL()
					.getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				String name = dd.getFileName();
				System.err.println(choix);
				if (choix!=null){
					JAXBContext jc;
					try {
						jc = JAXBContext.newInstance(TaRequete.class);

						Unmarshaller u = jc.createUnmarshaller();
						TaRequete laRequete = (TaRequete)u.unmarshal( 
								new FileInputStream(choix) );
						// charger
						List<TaLigneRequete> listeLignes = new ArrayList<TaLigneRequete>(laRequete.getTaLigneRequete());
						masterController.getEtape2Controller().chargerCriteres(listeLignes);
					} catch (JAXBException e1) {
						logger.error("",e1);
					} catch (FileNotFoundException e1) {
						logger.error("",e1);
					}
				}
				name = name.substring(0,name.length()-4);
				vue.getComposite_SauverCharger().getNomSauvegarde().setText(name);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant d'initaliser la combo contenant les différentes recherches
	 */
	private void initCombo(){
		vue.getComposite_SauverCharger().getCombo().removeAll();
		TaRequeteDAO daoRequete = new TaRequeteDAO();	
		List<TaRequete> listeRequetes = daoRequete.selectAll();
		for (TaRequete laRqt : listeRequetes){
			vue.getComposite_SauverCharger().getCombo().add(laRqt.getLibRqt());
		}
		vue.getComposite_SauverCharger().getCombo().select(0);
	}



}

