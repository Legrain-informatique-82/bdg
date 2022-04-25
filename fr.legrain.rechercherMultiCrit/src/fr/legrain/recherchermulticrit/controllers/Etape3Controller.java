package fr.legrain.recherchermulticrit.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.rits.cloning.Cloner;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.ImprimeObjet;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.EditionAppContext;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquette;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquetteArticleController;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquetteTiersController;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IlgrMapper;
import fr.legrain.gestCom.librairiesEcran.interfaces.IListeSelectionTiersProvider;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.gestionDossier.GestionDossierPlugin;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libLgrBirt.WebViewerUtil;
import fr.legrain.openmail.mail.OpenmailFAX;
import fr.legrain.openmail.mail.OpenmailMail;
import fr.legrain.openmail.sms.OpenmailSMS;
import fr.legrain.publipostage.gui.CompositePublipostage;
import fr.legrain.publipostage.gui.CompositePublipostageArticleController;
import fr.legrain.publipostage.gui.CompositePublipostageTiersController;
import fr.legrain.publipostage.gui.preferences.PreferenceConstants;
import fr.legrain.publipostage.gui.preferences.PreferenceInitializer;
import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.ArticleLabelProvider;
import fr.legrain.recherchermulticrit.DocumentLabelProvider;
import fr.legrain.recherchermulticrit.FichierDonneesArticleRechercheMultiCritere;
import fr.legrain.recherchermulticrit.TiersLabelProvider;
import fr.legrain.recherchermulticrit.ecrans.PaFormPageResultats;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.divers.FichierDonneesAdresseTiers;
import fr.legrain.visualisation.controller.ConstVisualisation;
import fr.legrain.visualisation.controller.Resultat;

public class Etape3Controller extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(Etape3Controller.class.getName());

	private static final int TYPE_TIERS = 1;
	private static final int TYPE_ARTICLES = 2;
	private static final int TYPE_DOCUMENTS = 3;

	private static final int TYPE_DETAIL_ADRESSE = 1;
	private static final int TYPE_DETAIL_EMAIL = 2;
	private static final int TYPE_DETAIL_SMS = 3;
	private static final int TYPE_DETAIL_FAX = 4;

	private static final int TYPE_CORRESPONDANCE_NORMAL = 1;
	private static final int TYPE_CORRESPONDANCE_COMMERCIAL = 2;
	private static final int TYPE_CORRESPONDANCE_ADMINISTRATIF = 3;

	private FormPageControllerResultats masterController = null;

	private PaFormPageResultats vue = null;

	private ArrayList<TaTiers> listeTiers = new ArrayList<TaTiers>();
	private ArrayList<TaArticle> listeArticle = new ArrayList<TaArticle>();
	private Collection<TaTiers> lalisteTiers = new ArrayList<TaTiers>();
	private Collection<TaArticle> lalisteArticle = new ArrayList<TaArticle>();

	// Elements utiles au tableau
	protected List<TiersIHM> modelDocumentTiers = null;
	private LgrTableViewer tabTiersViewer;
	private LgrTableViewer tabArticleViewer;
	private LgrTableViewer tabDocumentViewer;
	protected List<ArticleIHM> modelDocumentArticle = null;
	protected List<DocumentIHM> modelDocumentDocument = null;
	private String[] titreColonnes;
	private String[] tailleColonnes;
	private String[] idColonnes;

	//Liste pour gérer les différents onglet
	protected Map listeModeleOnglet = new LinkedHashMap(); //stocke le résultat de chaque onglet
	protected Map<CTabItem,Integer> listeTypeOnglet = new LinkedHashMap<CTabItem,Integer>(); //stocke le type de résultat de chaque onglet
	protected Map<CTabItem,String[]> listeIdColonnesOnglet = new LinkedHashMap<CTabItem,String[]>(); //stocke les id colonnes de chaque onglet (pour export)
	protected Map<CTabItem,String[]> listeIdColonnesExportOnglet = new LinkedHashMap<CTabItem,String[]>(); //stocke les id colonnes de chaque onglet (pour export)
	protected Map<CTabItem,Composite> listeformPubliOnglet = new LinkedHashMap<CTabItem,Composite>(); //stocke un référence au composite de parametrage du publipostage pour chaque onglet
	protected Map<CTabItem,Composite> listeformEtiqOnglet = new LinkedHashMap<CTabItem,Composite>(); //stocke un référence au composite de parametrage des étiquettes pour chaque onglet
	protected Map<CTabItem,List<TaTiers>> listeTiersOnglet = new LinkedHashMap<CTabItem,List<TaTiers>>(); //stocke la liste d'objet tiers pour un onglet de type tiers
	protected Map<CTabItem,List<TaArticle>> listeArticleOnglet = new LinkedHashMap<CTabItem,List<TaArticle>>(); //stocke la liste d'objet article pour un onglet de type article
	
	protected Map<CTabItem,Composite> listeCompositeGaucheOnglet = new LinkedHashMap<CTabItem,Composite>();
	protected Map<CTabItem,Composite> listeCompositeNouveauOnglet = new LinkedHashMap<CTabItem,Composite>();
	protected Map<CTabItem,Composite> listeCompositeDroitOnglet = new LinkedHashMap<CTabItem,Composite>();
	protected Map<CTabItem,Combo> listeComboCommOnglet = new LinkedHashMap<CTabItem,Combo>();
	protected Map<CTabItem,Table> listeTableDetailOnglet = new LinkedHashMap<CTabItem,Table>();
	protected Map<CTabItem,LgrTableViewer> listeTabTiersViewerDetailOnglet = new LinkedHashMap<CTabItem,LgrTableViewer>();

	// Elements graphiques
	private Shell shell;
	private ToolBar toolBar;
	private ToolItem itemExport;
	private ToolItem itemImpression;
	private ToolItem itemPubli;
	private ToolItem itemEtiq;
	private ToolItem itemMail;
	private ToolItem itemSMS;
	private ToolItem itemFax;
	private ToolItem itemCourrier;
	//private Text infoNbResult;
	private Label infoNbResult;
	private Table table;
	private CTabItem nouveau;
	private Composite composite_nouveau;
	private CompositePublipostage formPubli;
	private CompositeEtiquette formEtiq;

	private Combo comboTypeCommunication;

	private Composite composite_nouveau_gauche;
	private Composite composite_nouveau_droite;

	private Table tableDetail;

	private LgrTableViewer tabTiersViewerDetail;

	public static String C_VALEUR_COMBO_TYPE_COMM_NORMAL        = "Tous" ;
	public static String C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF = "Administratif";
	public static String C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL    = "Commercial";
	
	private static int C_TYPE_DETAIL_COMMUNICATION_PUBLIPOSTAGE = 1;
	private static int C_TYPE_DETAIL_COMMUNICATION_ETIQUETTE    = 2;
	private static int C_TYPE_DETAIL_COMMUNICATION_EMAIL        = 3;
	private static int C_TYPE_DETAIL_COMMUNICATION_SMS          = 4;
	private static int C_TYPE_DETAIL_COMMUNICATION_FAX          = 5;
	private int typeDetailCommActif = 0;

	// Variables de position
	private int compteur = 0;
	private int selected = 0;

	private Button btnShowFinalList = null;

//	public static String iconExport = "/icons/wand.png";
//	public static String iconImpression = "/icons/printer.png";
//	public static String iconEdit = "/icons/edit.png";
//	public static String iconEtiq = "/icons/etiq.png";
//	public static String iconPubli = "/icons/Word.png";
//
//	public static String iconEmail = "/icons/email.png";
//	public static String iconSMS = "/icons/phone.png";
//	public static String iconFax = "/icons/edit.png";
//	public static String iconCourrier = "/icons/phone.png";
//	public static String iconOpenMail = "/icons/icon_openmail.png";
	
	public static String iconExport = "/icons/exporter.png";
	public static String iconImpression = "/icons/imprimer.png";
	public static String iconEdit = "/icons/edit.png";
	public static String iconEtiq = "/icons/etiquette.png";
	public static String iconPubli = "/icons/publipostage.png";

	public static String iconEmail = "/icons/mail.png";
	public static String iconSMS = "/icons/sms.png";
	public static String iconFax = "/icons/fax.png";
	public static String iconCourrier = "/icons/phone.png";
	public static String iconOpenMail = "/icons/icon_openmail.png";

	/* Constructeur par défaut */
	public Etape3Controller(FormPageControllerResultats masterController,
			PaFormPageResultats vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterController;
	}

	public void initialiseModelIHM() {}

	@Override
	public void bind() {
		if (mapComposantChamps == null) {
			initMapComposantChamps();
		}
	}

	@Override
	protected void initMapComposantChamps() {}

	// ----------------------------------------------------------------------------------------
	// PARTIE CREATION D'ONGLETS
	// Création de l'onglet principal et des différents onglets en fonction du
	// type résultat
	// -----------------------------------------------------------------------------------------

	/**
	 * Méthode pour créer un onglet. La méthode ne prend en argument qu'une
	 * string pour vérifier la qualité de la requête
	 * 
	 * @param text
	 */
	public CTabItem creerOnglet() {
		nouveau = new CTabItem(vue.getComposite_Etape3().getDossierConteneur(), SWT.CLOSE);
		nouveau.setText("Feuille de Résultats " + compteur);
		composite_nouveau = vue.getToolkit().createComposite(vue.getComposite_Etape3().getDossierConteneur(), SWT.NONE);
		nouveau.setControl(composite_nouveau);
		composite_nouveau.setLayout(new GridLayout(2, false));
		listeCompositeNouveauOnglet.put(nouveau, composite_nouveau);

		int position = vue.getComposite_Etape3().getDossierConteneur().getItemCount() - 1;
		vue.getComposite_Etape3().getDossierConteneur().setSelection(position);

		// Création de la toolbar
		ToolBar toolBar2 = new ToolBar(composite_nouveau, SWT.NONE);
		toolBar2.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 2, 1));

		// -- Bouton d'exportation --
		itemExport = new ToolItem(toolBar2, SWT.PUSH);
		//itemExport.setText("Exporter");
		itemExport.setToolTipText("Exporter");
		itemExport.setImage(Activator.getImageDescriptor(iconExport).createImage());

		// -- Bouton d'impression --
		itemImpression = new ToolItem(toolBar2, SWT.PUSH);
		//itemImpression.setText("Imprimer");
		itemImpression.setToolTipText("Imprimer");
		itemImpression.setImage(Activator.getImageDescriptor(iconImpression).createImage());
		
		//Button test =  vue.getToolkit().createButton(composite_nouveau, "", SWT.PUSH);
		//test.setImage(Activator.getImageDescriptor(iconImpression).createImage());

		table = vue.getToolkit().createTable(composite_nouveau, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION); //$NON-NLS-1$
		GridData gridDataTab = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gridDataTab.minimumHeight = 150;
		gridDataTab.heightHint = 150;
		table.setLayoutData(gridDataTab);
		
		//infoNbResult = vue.getToolkit().createText(composite_nouveau, "",SWT.BORDER);
		infoNbResult = vue.getToolkit().createLabel(composite_nouveau, "",SWT.NONE);
		infoNbResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,false, 2, 1));
		//infoNbResult.setEditable(false);

		// Création du shell pour la toolbar
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		// Création de la toolbar
		toolBar = new ToolBar(composite_nouveau, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, false, 2, 1));

//		// -- Bouton d'exportation --
//		itemExport = new ToolItem(toolBar, SWT.PUSH);
//		itemExport.setText("Exporter");
//		itemExport.setImage(Activator.getImageDescriptor(iconExport)
//				.createImage());
//
//		// -- Bouton d'impression --
//		itemImpression = new ToolItem(toolBar, SWT.PUSH);
//		itemImpression.setText("Imprimer");
//		itemImpression.setImage(Activator.getImageDescriptor(iconImpression)
//				.createImage());

		composite_nouveau.layout();
		// Listener sur le bouton d'export
		addListenerExport(itemExport, nouveau);
		addListenerImpression(itemImpression, nouveau);
		addListenerTab();

		compteur++;

		return nouveau;

	}

	/**
	 * Méthode pour créer un onglet de résultats tiers. La méthode prend en
	 * argument la liste qui doit s'afficher dans le tableau de l'onglet
	 * 
	 * @param text
	 */
	public void creerOngletTiers(ArrayList<Object> laliste) {
		final CTabItem item = creerOnglet(); // On crée l'onglet, il ne reste qu'à insérer le tableau

		Composite compoComboListeTypeComm = vue.getToolkit().createComposite(composite_nouveau);

		compoComboListeTypeComm.setLayout(new GridLayout(2,false));
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.CENTER;
		compoComboListeTypeComm.setLayoutData(gd);
		compoComboListeTypeComm.moveAbove(toolBar);
		Label l = vue.getToolkit().createLabel(compoComboListeTypeComm, "Type de communication",SWT.NONE);
		comboTypeCommunication = new Combo(compoComboListeTypeComm, SWT.BORDER | SWT.READ_ONLY);
		comboTypeCommunication.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		comboTypeCommunication.add(C_VALEUR_COMBO_TYPE_COMM_NORMAL);
		comboTypeCommunication.add(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF);
		comboTypeCommunication.add(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL);
		comboTypeCommunication.select(0);
		listeComboCommOnglet.put(item, comboTypeCommunication);
		
		comboTypeCommunication.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println(comboTypeCommunication.getText());
				
				if(typeDetailCommActif == C_TYPE_DETAIL_COMMUNICATION_PUBLIPOSTAGE) {
					afficheParametreDetailPublipostage(item);
				} else if(typeDetailCommActif == C_TYPE_DETAIL_COMMUNICATION_ETIQUETTE) {
					afficheParametreDetailEtiquette(item);
				} else if(typeDetailCommActif == C_TYPE_DETAIL_COMMUNICATION_EMAIL) {
					afficheParametreDetailEMail(item);
				} else if(typeDetailCommActif == C_TYPE_DETAIL_COMMUNICATION_SMS) {
					afficheParametreDetailSMS(item);
				} else if(typeDetailCommActif == C_TYPE_DETAIL_COMMUNICATION_FAX) {
					afficheParametreDetailFax(item);
				}
	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		selected = TYPE_TIERS;
		listeTypeOnglet.put(item,TYPE_TIERS);
		listeTiers = new ArrayList<TaTiers>();
		for (int i = 0; i < laliste.size(); i++) {
			listeTiers.add((TaTiers) laliste.get(i));
		}
		for (int i = 0; i < laliste.size(); i++) {
			lalisteTiers.add((TaTiers) laliste.get(i));
		}

		listeTiersOnglet.put(item,listeTiers);

		// -- On ajoute le bouton de publipostage--
		itemPubli = new ToolItem(toolBar, SWT.PUSH);
		itemPubli.setText("Publipostage");
		itemPubli.setImage(Activator.getImageDescriptor(iconPubli).createImage());
		addListenerPubliTiers(itemPubli,item);

		// -- On ajoute le bouton de étiquette--
		itemEtiq = new ToolItem(toolBar, SWT.PUSH);
		itemEtiq.setText("Etiquettes");
		itemEtiq.setImage(Activator.getImageDescriptor(iconEtiq).createImage());
		addListenerEtiqTiers(itemEtiq,item);

		// -- On ajoute le bouton de mail--
		itemMail = new ToolItem(toolBar, SWT.PUSH);
		itemMail.setText("EMail");
		itemMail.setImage(Activator.getImageDescriptor(iconEmail).createImage());
		addListenerEmail(itemMail,item);

		// -- On ajoute le bouton de SMS--
		itemSMS = new ToolItem(toolBar, SWT.PUSH);
		itemSMS.setText("SMS");
		itemSMS.setImage(Activator.getImageDescriptor(iconSMS).createImage());
		addListenerSMS(itemSMS,item);

		// -- On ajoute le bouton de FAX--
		itemFax = new ToolItem(toolBar, SWT.PUSH);
		itemFax.setText("FAX");
		itemFax.setImage(Activator.getImageDescriptor(iconFax).createImage());
		addListenerFax(itemFax,item);

		//		// -- On ajoute le bouton de Courrier--
		//		itemCourrier = new ToolItem(toolBar, SWT.PUSH);
		//		itemCourrier.setText("Courrier");
		//		itemCourrier.setImage(Activator.getImageDescriptor(iconCourrier).createImage());

		composite_nouveau_gauche = vue.getToolkit().createComposite(composite_nouveau, SWT.NONE);
		GridData gridDataCompoGauche= new GridData(SWT.FILL, SWT.FILL, false, true, 1,1);
		composite_nouveau_gauche.setLayoutData(gridDataCompoGauche);
		listeCompositeGaucheOnglet.put(item, composite_nouveau_gauche);
		
		composite_nouveau_droite = vue.getToolkit().createComposite(composite_nouveau, SWT.NONE);
		GridData gridDataCompoDroit = new GridData(SWT.FILL, SWT.FILL, true, true, 1,1);
		composite_nouveau_droite.setLayoutData(gridDataCompoDroit);
		listeCompositeDroitOnglet.put(item, composite_nouveau_droite);
		
		composite_nouveau_gauche.setLayout(new GridLayout(1, false));
		composite_nouveau_droite.setLayout(new GridLayout(1, false));

		infoNbResult.setText("Nombre de tiers correspondant aux critères : "+ laliste.size());
		if (laliste.size() != 0) {
			modelDocumentTiers = new MapperTiersIHM().listeEntityToDto(laliste);
			listeModeleOnglet.put(item,modelDocumentTiers);
		}

		setObjetIHM(TiersIHM.class);

		// Titre des colonnes
		String[] titreColonnesTiers = { "Code", "Compte", "Nom Entreprise","Civilité",
				"Nom", "Prénom", "Téléphone", "Mail", "Web", "Code Postal",
				"Ville", "Pays", "Code Paiement", "Code Entité", "Type" };
		this.titreColonnes = titreColonnesTiers;
		// Taille des colonnes
		String[] tailleColonnesTiers = { "100", "100", "100", "100", "100","100",
				"100", "100", "100", "100", "100", "100", "100", "100", "100" };
		this.tailleColonnes = tailleColonnesTiers;

		// Id relatives dans la classe associée
		String[] idColonnesTiers = { "codeTiers", "compte", "nomEntreprise","codeTCivilite",
				"nomTiers", "prenomTiers", "telephone", "adresseEmail",
				"adresseWeb", "codepostalAdresse", "ville", "pays",
				"codePaiement", "codeEntite", "typeTiers" };
		String[] idColonnesExportTiers = { "codeTiers", "compte", "nomEntreprise","codeTCivilite",
				"nomTiers", "prenomTiers", "telephone", "adresseEmail",
				"adresseWeb", "adresse1Adresse", "adresse2Adresse", "adresse3Adresse","codepostalAdresse", "ville", "pays",
				"codePaiement", "codeEntite", "typeTiers" };
		this.idColonnes = idColonnesTiers;
		listeIdColonnesOnglet.put(item,idColonnesTiers);
		listeIdColonnesExportOnglet.put(item,idColonnesExportTiers);

		// Alignement des informations dans les cellules du tableau
		int[] alignement = { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE,
				SWT.RIGHT, SWT.NONE, SWT.NONE, SWT.RIGHT, SWT.NONE, SWT.NONE,
				SWT.NONE, SWT.NONE, SWT.NONE, };

		// Création de l'élément graphique du tableau et affichage à l'écran
		tabTiersViewer = new LgrTableViewer(table);
		tabTiersViewer.createTableCol(table, titreColonnes, tailleColonnes, 1, alignement);
		tabTiersViewer.setListeChamp(idColonnes);

		TiersLabelProvider.bind(tabTiersViewer, new WritableList(
				modelDocumentTiers, TiersIHM.class), BeanProperties
				.values(idColonnes));

		InfosPresentation[] infosTiers = {
				(new InfosPresentation("Code", "10", String.class, "string",
						false)),
						(new InfosPresentation("Compte", "10", String.class, "string",
								false)),
								(new InfosPresentation("Nom Entreprise", "10", String.class,
										"string", false)),
										(new InfosPresentation("Civilité", "10", String.class,
												"string", false)),										
										(new InfosPresentation("Nom", "10", String.class, "string",
												false)),
												(new InfosPresentation("Prénom", "10", String.class, "string",
														false)),
														(new InfosPresentation("Téléphone", "10", String.class,
																"string", false)),
																(new InfosPresentation("Mail", "10", String.class, "string",
																		false)),
																		(new InfosPresentation("Web", "10", String.class, "string",
																				false)),
																				(new InfosPresentation("Code Postal", "10", String.class,
																						"string", false)),
																						(new InfosPresentation("Ville", "10", String.class, "string",
																								false)),
																								(new InfosPresentation("Pays", "10", String.class, "string",
																										false)),
																										(new InfosPresentation("Code Paiement", "10", String.class,
																												"string", false)),
																												(new InfosPresentation("Code Entité", "10", String.class,
																														"string", false)),
																														(new InfosPresentation("Type", "10", String.class, "string",
																																false)) };
		this.infos = infosTiers;

		vue.getComposite_Etape3().getDossierConteneur().addCTabFolder2Listener(new CTabFolder2Listener() {

			@Override
			public void showList(CTabFolderEvent event) {}

			@Override
			public void restore(CTabFolderEvent event) {}

			@Override
			public void minimize(CTabFolderEvent event) {}

			@Override
			public void maximize(CTabFolderEvent event) {}

			@Override
			public void close(CTabFolderEvent event) {
				if(event.item.equals(item)) {
					logger.debug("Onlget fermé : "+item.getText());

					listeModeleOnglet.remove(item);
					listeTypeOnglet.remove(item);
					listeIdColonnesOnglet.remove(item);
					listeIdColonnesExportOnglet.remove(item);
					listeformPubliOnglet.remove(item);
					listeformEtiqOnglet.remove(item);
					listeTiersOnglet.remove(item);
					listeCompositeDroitOnglet.remove(item);
					listeCompositeGaucheOnglet.remove(item);
					listeComboCommOnglet.remove(item);
					listeTableDetailOnglet.remove(item);
					listeTabTiersViewerDetailOnglet.remove(item);
				}
			}
		});

		listeCompositeNouveauOnglet.get(item).layout();

	}

	/**
	 * Méthode pour créer un onglet de résultats tiers. La méthode prend en
	 * argument la liste qui doit s'afficher dans le tableau de l'onglet
	 * 
	 * @param text
	 */
	public void creerOngletArticle(ArrayList<Object> laliste) {
		CTabItem item = creerOnglet(); // On crée l'onglet, il ne reste qu'à insérer le tableau
		selected = TYPE_ARTICLES;
		listeTypeOnglet.put(item,TYPE_ARTICLES);

		listeArticle = new ArrayList<TaArticle>();
		for (int i = 0; i < laliste.size(); i++) {
			listeArticle.add((TaArticle) laliste.get(i));
		}
		for (int i = 0; i < laliste.size(); i++) {
			lalisteArticle.add((TaArticle) laliste.get(i));
		}

		listeArticleOnglet.put(item,listeArticle);

		itemPubli = new ToolItem(toolBar, SWT.PUSH);
		itemPubli.setText("Publipostage");
		itemPubli.setImage(Activator.getImageDescriptor(iconPubli).createImage());

		// -- On ajoute le bouton de étiquette--
		itemEtiq = new ToolItem(toolBar, SWT.PUSH);
		itemEtiq.setText("Etiquettes");
		itemEtiq.setImage(Activator.getImageDescriptor(iconEtiq).createImage());

		//		initPubliArticle();
		formPubli = new CompositePublipostage(composite_nouveau, SWT.BORDER);
		//initEtiqArticle();
		formEtiq = new CompositeEtiquette(composite_nouveau, SWT.BORDER);

		//remplissageEtiqArticle(item);
		CompositeEtiquetteArticleController etiquetteArticleController = new CompositeEtiquetteArticleController(formEtiq, listeArticleOnglet.get(item),null,
				Activator.getDefault().getPreferenceStore(),
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE_ARTICLE, 
				new FichierDonneesArticleRechercheMultiCritere()
				);

		addListenerEtiq(itemEtiq,item);

		PreferenceInitializer.initDefautProperties(false);
		//remplissagePubliArticle(item);
		CompositePublipostageArticleController publiArticleController = new CompositePublipostageArticleController(
				formPubli, 
				listeArticleOnglet.get(item),null,
				Activator.getDefault().getPreferenceStore(),
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD_ARTICLE,
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE_ARTICLE, 
				new FichierDonneesArticleRechercheMultiCritere(),
				fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
						PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD_ARTICLE),
						fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
								PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE_ARTICLE)
				);
		addListenerPubli(itemPubli,item);
		//addListenerPubli(itemPubli,item);

		listeformPubliOnglet.put(item,formPubli);
		listeformEtiqOnglet.put(item,formEtiq);
		
		composite_nouveau_gauche = vue.getToolkit().createComposite(composite_nouveau, SWT.NONE);
		GridData gridDataCompoGauche= new GridData(SWT.FILL, SWT.FILL, false, true, 1,1);
		composite_nouveau_gauche.setLayoutData(gridDataCompoGauche);
		listeCompositeGaucheOnglet.put(item, composite_nouveau_gauche);
		
		composite_nouveau_droite = vue.getToolkit().createComposite(composite_nouveau, SWT.NONE);
		GridData gridDataCompoDroit = new GridData(SWT.FILL, SWT.FILL, true, true, 1,1);
		composite_nouveau_droite.setLayoutData(gridDataCompoDroit);
		listeCompositeDroitOnglet.put(item, composite_nouveau_droite);
		
		composite_nouveau_gauche.setLayout(new GridLayout(1, false));
		composite_nouveau_droite.setLayout(new GridLayout(1, false));

		//listeformPubliOnglet.put(null,null);
		//listeformEtiqOnglet.put(null,null);
		infoNbResult.setText("Nombre d'articles correspondant aux critères : " + laliste.size());

		if (laliste.size() != 0) {
			modelDocumentArticle = new MapperArticleIHM()
			.listeEntityToDto(laliste);
			listeModeleOnglet.put(item,modelDocumentArticle);
		}

		setObjetIHM(ArticleIHM.class);

		// Titre des colonnes
		String[] titreColonnesArticles = { "Code Article", "Libellé",
				"Description", "Code Famille", "Libellé Famille", "Code TVA",
				"Tarif HT", "Tarif TTC", "Compte", "Stock Mini" };
		this.titreColonnes = titreColonnesArticles;

		// Taille des colonnes
		String[] tailleColonnesArticles = { "100", "100", "100", "100", "100",
				"100", "100", "100", "100", "100" };
		this.tailleColonnes = tailleColonnesArticles;
		// Id relatives dans la classe associée
		String[] idColonnesArticles = { "codearticle", "libelle",
				"description", "codefamille", "libellefamille", "codetva",
				"tarifht", "tarifttc", "compte", "stockmini" };
		this.idColonnes = idColonnesArticles;
		listeIdColonnesOnglet.put(item,idColonnesArticles);
		listeIdColonnesExportOnglet.put(item,idColonnesArticles);

		// Alignement des informations dans les cellules du tableau
		int[] alignement = { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE,
				SWT.NONE, SWT.RIGHT, SWT.RIGHT, SWT.NONE, SWT.NONE };

		// Création de l'élément graphique du tableau et affichage à l'écran
		tabArticleViewer = new LgrTableViewer(table);
		tabArticleViewer.createTableCol(table, titreColonnes, tailleColonnes,
				1, alignement);
		tabArticleViewer.setListeChamp(idColonnes);

		ArticleLabelProvider.bind(tabArticleViewer, new WritableList(
				modelDocumentArticle, ArticleIHM.class), BeanProperties
				.values(idColonnes));

		InfosPresentation[] infosArticles = {
				(new InfosPresentation("Code Article", "10", String.class,
						"string", false)),
						(new InfosPresentation("Libellé", "10", String.class, "string",
								false)),
								(new InfosPresentation("Description", "10", String.class,
										"string", false)),
										(new InfosPresentation("Code Famille", "10", String.class,
												"string", false)),
												(new InfosPresentation("Libellé Famille", "10", String.class,
														"string", false)),
														(new InfosPresentation("Code TVA", "10", String.class,
																"string", false)),
																(new InfosPresentation("Tarif HT", "10", BigDecimal.class,
																		"decimal", false)),
																		(new InfosPresentation("Tarif TTC", "10", BigDecimal.class,
																				"decimal", false)),
																				(new InfosPresentation("Compte", "10", String.class, "string",
																						false)),
																						(new InfosPresentation("Stock Mini", "10", BigDecimal.class,
																								"decimal", false)) };
		this.infos = infosArticles;

	}

	/**
	 * Méthode pour créer un onglet de résultats documents. La méthode prend en
	 * argument la liste qui doit s'afficher dans le tableau de l'onglet
	 * 
	 * @param text
	 */
	public void creerOngletDocument(ArrayList<Object> laliste) {
		CTabItem item = creerOnglet(); // On crée l'onglet, il ne reste qu'à insérer le tableau
		selected = TYPE_DOCUMENTS;
		listeTypeOnglet.put(item,TYPE_DOCUMENTS);
		listeTiersOnglet.put(null,null);

		listeformPubliOnglet.put(null,null);
		listeformEtiqOnglet.put(null,null);
		infoNbResult
		.setText("Nombre de documents correspondant aux critères : "
				+ laliste.size());
		if (laliste.size() != 0) {
			modelDocumentDocument = new MapperDocumentIHM()
			.listeEntityToDto(laliste);
			listeModeleOnglet.put(item,modelDocumentDocument);
		}

		setObjetIHM(DocumentIHM.class);

		// Titre des colonnes
		String[] titreColonnesDoc = { "Code Document", "Date Document",
				"Date échéance", "Date livraison", "Règlement", "Net HT",
				"Net TVA", "Net à Payer", "Reste à Payer" };
		this.titreColonnes = titreColonnesDoc;

		// Taille des colonnes
		String[] tailleColonnesDoc = { "100", "100", "100", "100", "100",
				"100", "100", "100", "100" };
		this.tailleColonnes = tailleColonnesDoc;
		// Id relatives dans la classe associée
		String[] idColonnesDoc = { "codedocument", "datedocument",
				"dateecheance", "datelivraison", "reglement", "netht",
				"nettva", "netapayer", "resteapayer" };
		this.idColonnes = idColonnesDoc;
		listeIdColonnesOnglet.put(item,idColonnesDoc);
		listeIdColonnesExportOnglet.put(item,idColonnesDoc);

		// Alignement des informations dans les cellules du tableau
		int[] alignement = { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE,
				SWT.RIGHT, SWT.RIGHT, SWT.RIGHT, SWT.RIGHT };

		// Création de l'élément graphique du tableau et affichage à l'écran
		tabDocumentViewer = new LgrTableViewer(table);
		tabDocumentViewer.createTableCol(table, titreColonnes, tailleColonnes,
				1, alignement);
		tabDocumentViewer.setListeChamp(idColonnes);

		DocumentLabelProvider.bind(tabDocumentViewer, new WritableList(
				modelDocumentDocument, DocumentIHM.class), BeanProperties
				.values(idColonnes));

		InfosPresentation[] infosDocuments = {
				(new InfosPresentation("Code Document", "10", String.class,
						"string", false)),
						(new InfosPresentation("Date Document", "10", Date.class,
								"date", false)),
								(new InfosPresentation("Date échéance", "10", Date.class,
										"date", false)),
										(new InfosPresentation("Date livraison", "10", Date.class,
												"date", false)),
												(new InfosPresentation("Règlement", "10", BigDecimal.class,
														"decimal", false)),
														(new InfosPresentation("Net HT", "10", BigDecimal.class,
																"decimal", false)),
																(new InfosPresentation("Net TVA", "10", BigDecimal.class,
																		"decimal", false)),
																		(new InfosPresentation("Net à Payer", "10", BigDecimal.class,
																				"decimal", false)),
																				(new InfosPresentation("Reste à Payer", "10", BigDecimal.class,
																						"decimal", false)) };

		this.infos = infosDocuments;

	}

	private void afficheListe(final CTabItem item, List<TiersIHM> listeTiersIHM, int typeDetail) {

		String[] titreColonnesTiers = null;
		String[] tailleColonnesTiers = null;
		String[] idColonnesTiers = null;
		int[] alignement = null;

		if(typeDetail==TYPE_DETAIL_ADRESSE) {
			// Titre des colonnes
			titreColonnesTiers = new String[] { "Code", "Nom Entreprise","Civilité", "Nom", "Prénom",
					"Adresse 1", "Adresse 2", "Adresse 3","CP", "Ville", "Pays"};

			// Taille des colonnes
			tailleColonnesTiers = new String[]  { "100", "100", "100", "100","100", "100", "100", "100", "100", "100", "100"};

			// Id relatives dans la classe associée
			idColonnesTiers = new String[]  { "codeTiers", "nomEntreprise","codeTCivilite", "nomTiers", "prenomTiers",
					"adresse1Adresse", "adresse2Adresse", "adresse3Adresse","codepostalAdresse", "ville", "pays" };

			// Alignement des informations dans les cellules du tableau
			alignement = new int[] { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE,SWT.RIGHT, SWT.NONE, SWT.NONE, SWT.RIGHT, SWT.NONE, SWT.NONE };

		} else 	if(typeDetail==TYPE_DETAIL_EMAIL) {
			// Titre des colonnes
			titreColonnesTiers = new String[] { "Code", "Nom Entreprise","Civilité", "Nom", "Prénom", "EMail"};

			// Taille des colonnes
			tailleColonnesTiers = new String[]  { "100", "100", "100","100", "100", "100" };
			//this.tailleColonnes = tailleColonnesTiers;

			// Id relatives dans la classe associée
			idColonnesTiers = new String[]  { "codeTiers",  "nomEntreprise","codeTCivilite", "nomTiers", "prenomTiers", "adresseEmail"};

			// Alignement des informations dans les cellules du tableau
			alignement = new int[] { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE };

		} else 	if(typeDetail==TYPE_DETAIL_FAX || typeDetail==TYPE_DETAIL_SMS) {
			// Titre des colonnes
			titreColonnesTiers = new String[] { "Code", "Nom Entreprise","codeTCivilite","Nom", "Prénom", "Téléphone" };

			// Taille des colonnes
			tailleColonnesTiers = new String[]  { "100", "100", "100", "100","100", "100" };

			// Id relatives dans la classe associée
			idColonnesTiers = new String[]  { "codeTiers", "nomEntreprise","codeTCivilite", "nomTiers", "prenomTiers", "telephone"};

			// Alignement des informations dans les cellules du tableau
			alignement = new int[] { SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE, SWT.NONE,  SWT.NONE };
		}

		// Création de l'élément graphique du tableau et affichage à l'écran
		if(listeTableDetailOnglet.get(item)==null) {
			tableDetail = vue.getToolkit().createTable(composite_nouveau_droite,
					SWT.SINGLE | SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION); //$NON-NLS-1$
			GridData gridDataTab = new GridData(SWT.FILL, SWT.FILL, true, true, 2,
					1);
			gridDataTab.minimumHeight = 150;
			//gridDataTab.heightHint = 150;
			tableDetail.setLayoutData(gridDataTab);
			//composite_nouveau_droite.layout();
			tableDetail.setVisible(false);
			listeTableDetailOnglet.put(item, tableDetail);
		}

		if(listeTableDetailOnglet.get(item).isVisible()) {
			listeTableDetailOnglet.get(item).setVisible(false);
		} else {
			listeTableDetailOnglet.get(item).setVisible(true);
		}

		if(listeTableDetailOnglet.get(item).isVisible()) {
			tabTiersViewerDetail = new LgrTableViewer(listeTableDetailOnglet.get(item));
			listeTabTiersViewerDetailOnglet.put(item, tabTiersViewerDetail);
			//tabTiersViewer.createTableCol(table, titreColonnes, tailleColonnes, 1,
			tabTiersViewerDetail.createTableCol(listeTableDetailOnglet.get(item), titreColonnesTiers, tailleColonnesTiers, 1,
					alignement);
			tabTiersViewerDetail.setListeChamp(idColonnesTiers);

			TiersLabelProvider.bind(tabTiersViewerDetail, new WritableList(
					//modelDocumentTiers, TiersIHM.class), BeanProperties
					listeTiersIHM, TiersIHM.class), BeanProperties
					//.values(idColonnes));
					.values(idColonnesTiers));

			tabTiersViewerDetail.setCheckedElements(listeTiersIHM.toArray());
		}
	}

	// ---------------------------------------------------------------------------
	// PARTIE CREATION D'OBJETS IHM
	// Création des objets permettant l'affichage des résultats dans le tableau
	// ---------------------------------------------------------------------------

	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les
	 * tiers
	 * 
	 * @author nicolas²
	 * 
	 */
	class TiersIHM extends ModelObject {
		int idTiers = 0;
		String codeTiers = null;
		String compte = null;
		String nomEntreprise = null;
		String codeTCivilite = null;
		String nomTiers = null;
		String prenomTiers = null;
		String telephone = null;
		String adresseEmail = null;
		String adresseWeb = null;
		String codepostalAdresse = null;
		String adresse1Adresse = null;
		String adresse2Adresse = null;
		String adresse3Adresse = null;
		String ville = null;
		String pays = null;
		String codePaiement = null;
		String codeEntite = null;
		String typeTiers = null;

		/**
		 * Un Tiers IHM est constitué de :
		 * 
		 * @param codeTiers
		 * @param compte
		 * @param nomEntreprise
		 * @param codeTCivilite
		 * @param nomTiers
		 * @param prenomTiers
		 * @param telephone
		 * @param adresseEmail
		 * @param adresseWeb
		 * @param codepostalAdresse
		 * @param ville
		 * @param pays
		 * @param codePaiement
		 * @param codeEntite
		 * @param typeTiers
		 */
		public TiersIHM(int idTiers, String codeTiers, String compte, String nomEntreprise,String codeTCivilite,
				String nomTiers, String prenomTiers, String telephone,
				String adresseEmail, String adresseWeb,
				String adresse1Adresse, String adresse2Adresse, String adresse3Adresse,
				String codepostalAdresse, String ville, String pays,
				String codePaiement, String codeEntite, String typeTiers) {
			super();
			this.idTiers = idTiers;
			this.codeTiers = codeTiers;
			this.compte = compte;
			this.nomEntreprise = nomEntreprise;
			this.codeTCivilite = codeTCivilite;
			this.nomTiers = nomTiers;
			this.prenomTiers = prenomTiers;
			this.telephone = telephone;
			this.adresseEmail = adresseEmail;
			this.adresseWeb = adresseWeb;
			this.adresse1Adresse = adresse1Adresse;
			this.adresse2Adresse = adresse2Adresse;
			this.adresse3Adresse = adresse3Adresse;
			this.codepostalAdresse = codepostalAdresse;
			this.ville = ville;
			this.pays = pays;
			this.codePaiement = codePaiement;
			this.codeEntite = codeEntite;
			this.typeTiers = typeTiers;
		}

		public int getIdTiers() {
			return idTiers;
		}

		public void setIdTiers(int idTiers) {
			firePropertyChange("idTiers", this.idTiers,this.idTiers = idTiers);
		}

		public String getCodeTiers() {
			return codeTiers;
		}

		public void setCodeTiers(String codeTiers) {
			firePropertyChange("codeTiers", this.codeTiers,this.codeTiers = codeTiers);
		}

		public String getCompte() {
			return compte;
		}

		public void setCompte(String compte) {
			firePropertyChange("compte", this.compte, this.compte = compte);
		}

		public String getNomEntreprise() {
			return nomEntreprise;
		}

		public void setNomEntreprise(String nomEntreprise) {
			firePropertyChange("nomEntreprise", this.nomEntreprise,
					this.nomEntreprise = nomEntreprise);
		}

		public String getNomTiers() {
			return nomTiers;
		}

		public void setNomTiers(String nomTiers) {
			firePropertyChange("nomTiers", this.nomTiers,
					this.nomTiers = nomTiers);
		}

		public String getPrenomTiers() {
			return prenomTiers;
		}

		public void setPrenomTiers(String prenomTiers) {
			firePropertyChange("prenomTiers", this.prenomTiers,
					this.prenomTiers = prenomTiers);
		}

		public String getAdresseEmail() {
			return adresseEmail;
		}

		public void setAdresseEmail(String adresseEmail) {
			firePropertyChange("adresseEmail", this.adresseEmail,
					this.adresseEmail = adresseEmail);
		}

		public String getAdresseWeb() {
			return adresseWeb;
		}

		public void setAdresseWeb(String adresseWeb) {
			firePropertyChange("adresseWeb", this.adresseWeb,
					this.adresseWeb = adresseWeb);
		}

		public String getCodepostalAdresse() {
			return codepostalAdresse;
		}

		public void setCodepostalAdresse(String codepostalAdresse) {
			firePropertyChange("codepostalAdresse", this.codepostalAdresse,
					this.codepostalAdresse = codepostalAdresse);
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			firePropertyChange("ville", this.ville, this.ville = ville);
		}

		public String getPays() {
			return pays;
		}

		public void setPays(String pays) {
			firePropertyChange("pays", this.pays, this.pays = pays);
		}

		public String getCodePaiement() {
			return codePaiement;
		}

		public void setCodePaiement(String codePaiement) {
			firePropertyChange("codePaiement", this.codePaiement,
					this.codePaiement = codePaiement);
		}

		public String getCodeEntite() {
			return codeEntite;
		}

		public void setCodeEntite(String codeEntite) {
			firePropertyChange("codeEntite", this.codeEntite,
					this.codeEntite = codeEntite);
		}

		public String getTypeTiers() {
			return typeTiers;
		}

		public void setTypeTiers(String typeTiers) {
			firePropertyChange("typeTiers", this.typeTiers,
					this.typeTiers = typeTiers);
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			firePropertyChange("telephone", this.telephone,
					this.telephone = telephone);
		}

		public String getAdresse1Adresse() {
			return adresse1Adresse;
		}

		public void setAdresse1Adresse(String adresse1Adresse) {
			firePropertyChange("adresse1Adresse", this.adresse1Adresse, this.adresse1Adresse = adresse1Adresse);
		}

		public String getAdresse2Adresse() {
			return adresse2Adresse;
		}

		public void setAdresse2Adresse(String adresse2Adresse) {
			firePropertyChange("adresse2Adresse", this.adresse2Adresse, this.adresse2Adresse = adresse2Adresse);
		}

		public String getAdresse3Adresse() {
			return adresse3Adresse;
		}

		public void setAdresse3Adresse(String adresse3Adresse) {
			firePropertyChange("adresse3Adresse", this.adresse3Adresse, this.adresse3Adresse = adresse3Adresse);
		}

		public String getCodeTCivilite() {
			return codeTCivilite;
		}

		public void setCodeTCivilite(String codeTCivilite) {
			firePropertyChange("codeTCivilite", this.codeTCivilite, this.codeTCivilite = codeTCivilite);
		}

	}

	class MapperTiersIHM implements IlgrMapper<TiersIHM, TaTiers> {

		public List<TiersIHM> listeEntityToDto(List<Object> l) {
			List<TiersIHM> res = new ArrayList<TiersIHM>();
			for (Object tiers : l) {
				res.add(entityToDto((TaTiers) tiers));
			}
			return res;
		}

		public TiersIHM entityToDto(TaTiers e) {
			int idTiers = e.getIdTiers();
			String codeTCivilite = e.getTaTCivilite() != null ? e
					.getTaTCivilite().getCodeTCivilite() : null;			
			String entreprise = e.getTaEntreprise() != null ? e
					.getTaEntreprise().getNomEntreprise() : null;
					String telephone = e.getTaTelephone() != null ? e.getTaTelephone()
							.getNumeroTelephone() : null;
							String mail = e.getTaEmail() != null ? e.getTaEmail()
									.getAdresseEmail() : null;
									String web = e.getTaWeb() != null ? e.getTaWeb().getAdresseWeb()
											: null;

									String adresse1 = e.getTaAdresse() != null ? e.getTaAdresse()
											.getAdresse1Adresse() : null;
											String adresse2 = e.getTaAdresse() != null ? e.getTaAdresse()
													.getAdresse2Adresse() : null;
													String adresse3 = e.getTaAdresse() != null ? e.getTaAdresse()
															.getAdresse3Adresse() : null;

															String codepostal = e.getTaAdresse() != null ? e.getTaAdresse()
																	.getCodepostalAdresse() : null;
																	String ville = e.getTaAdresse() != null ? e.getTaAdresse()
																			.getVilleAdresse() : null;
																			String pays = e.getTaAdresse() != null ? e.getTaAdresse()
																					.getPaysAdresse() : null;
																					String codepaiement = e.getTaCPaiement() != null ? e
																							.getTaCPaiement().getCodeCPaiement() : null;
																							String codeEntite = e.getTaTEntite() != null ? e.getTaTEntite()
																									.getCodeTEntite() : null;
																									String codeTTiers = e.getTaTTiers() != null ? e.getTaTTiers()
																											.getCodeTTiers() : null;

																											return new TiersIHM(idTiers, e.getCodeTiers(), e.getCompte(), entreprise,codeTCivilite, e
																													.getNomTiers(), e.getPrenomTiers(), telephone, mail, web,
																													adresse1,adresse2,adresse3,codepostal, ville, pays, codepaiement, codeEntite,
																													codeTTiers);
		}

		@Override
		public TaTiers dtoToEntity(TiersIHM e) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les
	 * articles
	 * 
	 * @author nicolas²
	 * 
	 */
	class ArticleIHM extends ModelObject {
		String codearticle = null;
		String libelle = null;
		String description = null;
		String codefamille = null;
		String libellefamille = null;
		String codetva = null;
		BigDecimal tarifht = null;
		BigDecimal tarifttc = null;
		String compte = null;
		BigDecimal stockmini = null;

		/**
		 * Un article ihm est composé de :
		 * 
		 * @param codeArticle
		 * @param libelle
		 * @param description
		 * @param codefamille
		 * @param libellefamille
		 * @param codetva
		 * @param tarifht
		 * @param tarifttc
		 * @param compte
		 * @param stockmini
		 */
		public ArticleIHM(String codearticle, String libelle,
				String description, String codefamille, String libellefamille,
				String codetva, BigDecimal tarifht, BigDecimal tarifttc,
				String compte, BigDecimal stockmini) {
			super();
			this.codearticle = codearticle;
			this.libelle = libelle;
			this.description = description;
			this.codefamille = codefamille;
			this.libellefamille = libellefamille;
			this.codetva = codetva;
			this.tarifht = tarifht;
			this.tarifttc = tarifttc;
			this.compte = compte;
			this.stockmini = stockmini;
		}

		public String getCodearticle() {
			return codearticle;
		}

		public void setCodearticle(String codearticle) {
			firePropertyChange("codearticle", this.codearticle,
					this.codearticle = codearticle);

		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			firePropertyChange("libelle", this.libelle, this.libelle = libelle);
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			firePropertyChange("description", this.description,
					this.description = description);
		}

		public String getCodefamille() {
			return codefamille;
		}

		public void setCodefamille(String codefamille) {
			firePropertyChange("codefamille", this.codefamille,
					this.codefamille = codefamille);
		}

		public String getLibellefamille() {
			return libellefamille;
		}

		public void setLibellefamille(String libellefamille) {
			firePropertyChange("libellefamille", this.libellefamille,
					this.libellefamille = libellefamille);
		}

		public String getCodetva() {
			return codetva;
		}

		public void setCodetva(String codetva) {
			firePropertyChange("codetva", this.codetva, this.codetva = codetva);
		}

		public BigDecimal getTarifht() {
			return tarifht;
		}

		public void setTarifht(BigDecimal tarifht) {
			firePropertyChange("tarifht", this.tarifht, this.tarifht = tarifht);
		}

		public BigDecimal getTarifttc() {
			return tarifttc;
		}

		public void setTarifttc(BigDecimal tarifttc) {
			firePropertyChange("tarifttc", this.tarifttc,
					this.tarifttc = tarifttc);
		}

		public String getCompte() {
			return compte;
		}

		public void setCompte(String compte) {
			firePropertyChange("compte", this.compte, this.compte = compte);
		}

		public BigDecimal getStockmini() {
			return stockmini;
		}

		public void setStockmini(BigDecimal stockmini) {
			firePropertyChange("stockmini", this.stockmini,
					this.stockmini = stockmini);
		}

	}

	class MapperArticleIHM implements IlgrMapper<ArticleIHM, TaArticle> {

		public List<ArticleIHM> listeEntityToDto(ArrayList<Object> l) {
			List<ArticleIHM> res = new ArrayList<ArticleIHM>();
			for (Object article : l) {
				res.add(entityToDto((TaArticle) article));
			}
			return res;
		}

		public ArticleIHM entityToDto(TaArticle e) {
			String codefamille = e.getTaFamille() != null ? e.getTaFamille()
					.getCodeFamille() : null;
					String libellefamille = e.getTaFamille() != null ? e.getTaFamille()
							.getLibcFamille() : null;
							String codetva = e.getTaTva() != null ? e.getTaTva().getCodeTva()
									: null;
							String compte = e.getTaTva() != null ? e.getTaTva().getNumcptTva()
									: null;
							BigDecimal tarifht = e.getTaPrix() != null ? e.getTaPrix()
									.getPrixPrix() : null;
									BigDecimal tarifttc = e.getTaPrix() != null ? e.getTaPrix()
											.getPrixttcPrix() : null;

											return new ArticleIHM(e.getCodeArticle(), e.getLibellecArticle(), e
													.getLibellelArticle(), codefamille, libellefamille,
													codetva, tarifht, tarifttc, compte, e.getStockMinArticle());
		}

		@Override
		public TaArticle dtoToEntity(ArticleIHM e) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Classe permettant la création et l'exploitation du tableau contenant les
	 * documents
	 * 
	 * @author nicolas²
	 * 
	 */
	class DocumentIHM extends ModelObject {
		String codedocument = null;
		Date datedocument = null;
		Date dateecheance = null;
		Date datelivraison = null;
		BigDecimal reglement = null;
		BigDecimal netht = null;
		BigDecimal nettva = null;
		BigDecimal netapayer = null;
		BigDecimal resteapayer = null;

		/**
		 * Un document IHM est composé de :
		 * 
		 * @param codedocument
		 * @param datedocument
		 * @param dateecheance
		 * @param datelivraison
		 * @param reglement
		 * @param netht
		 * @param nettva
		 * @param netapayer
		 * @param resteapayer
		 */
		public DocumentIHM(String codedocument, Date datedocument,
				Date dateecheance, Date datelivraison, BigDecimal reglement,
				BigDecimal netht, BigDecimal nettva, BigDecimal netapayer,
				BigDecimal resteapayer) {
			super();
			this.codedocument = codedocument;
			this.datedocument = datedocument;
			this.dateecheance = dateecheance;
			this.datelivraison = datelivraison;
			this.reglement = reglement;
			this.netht = netht;
			this.nettva = nettva;
			this.netapayer = netapayer;
			this.resteapayer = resteapayer;
		}

		public String getCodedocument() {
			return codedocument;
		}

		public void setCodedocument(String codedocument) {
			firePropertyChange("codedocument", this.codedocument,
					this.codedocument = codedocument);
		}

		public Date getDatedocument() {
			return datedocument;
		}

		public void setDatedocument(Date datedocument) {
			firePropertyChange("datedocument", this.datedocument,
					this.datedocument = datedocument);
		}

		public Date getDateecheance() {
			return dateecheance;
		}

		public void setDateecheance(Date dateecheance) {
			firePropertyChange("dateecheance", this.dateecheance,
					this.dateecheance = dateecheance);
		}

		public Date getDatelivraison() {
			return datelivraison;
		}

		public void setDatelivraison(Date datelivraison) {
			firePropertyChange("datelivraison", this.datelivraison,
					this.datelivraison = datelivraison);
		}

		public BigDecimal getReglement() {
			return reglement;
		}

		public void setReglement(BigDecimal reglement) {
			firePropertyChange("reglement", this.reglement,
					this.reglement = reglement);
		}

		public BigDecimal getNetht() {
			return netht;
		}

		public void setNetht(BigDecimal netht) {
			firePropertyChange("netht", this.netht, this.netht = netht);
		}

		public BigDecimal getNettva() {
			return nettva;
		}

		public void setNettva(BigDecimal nettva) {
			firePropertyChange("nettva", this.nettva, this.nettva = nettva);
		}

		public BigDecimal getNetapayer() {
			return netapayer;
		}

		public void setNetapayer(BigDecimal netapayer) {
			firePropertyChange("netapayer", this.netapayer,
					this.netapayer = netapayer);
		}

		public BigDecimal getResteapayer() {
			return resteapayer;
		}

		public void setResteapayer(BigDecimal resteapayer) {
			firePropertyChange("resteapayer", this.resteapayer,
					this.resteapayer = resteapayer);
		}

	}

	class MapperDocumentIHM implements IlgrMapper<DocumentIHM, Object> {

		public List<DocumentIHM> listeEntityToDto(ArrayList<Object> l) {
			List<DocumentIHM> res = new ArrayList<DocumentIHM>();
			for (Object document : l) {
				if (document instanceof TaFacture) {
					res.add(entityToDtoDoc((TaFacture) document));
				} else if (document instanceof TaDevis) {
					res.add(entityToDtoDoc((TaDevis) document));
				} else if (document instanceof TaBoncde) {
					res.add(entityToDtoDoc((TaBoncde) document));
				} else if (document instanceof TaAvoir) {
					res.add(entityToDtoDoc((TaAvoir) document));
				} else if (document instanceof TaApporteur) {
					res.add(entityToDtoDoc((TaApporteur) document));
				} else if (document instanceof TaAcompte) {
					res.add(entityToDtoDoc((TaAcompte) document));
				} else if (document instanceof TaProforma) {
					res.add(entityToDtoDoc((TaProforma) document));
				} else if (document instanceof TaBonliv) {
					res.add(entityToDtoDoc((TaBonliv) document));
				}
			}
			return res;
		}

		public DocumentIHM entityToDtoDoc(TaFacture e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), e.getResteAReglerEcran());
		}

		public DocumentIHM entityToDtoDoc(TaDevis e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaBoncde e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaApporteur e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaProforma e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaBonliv e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(),
					null, e.getDateLivDocument(), null, e.getNetHtCalc(), e
					.getNetTvaCalc(), e.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaAvoir e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), null);
		}

		public DocumentIHM entityToDtoDoc(TaAcompte e) {
			return new DocumentIHM(e.getCodeDocument(), e.getDateDocument(), e
					.getDateEchDocument(), e.getDateLivDocument(), e
					.getRegleDocument(), e.getNetHtCalc(), e.getNetTvaCalc(), e
					.getNetAPayer(), e.getResteARegler());
		}

		@Override
		public TaArticle dtoToEntity(DocumentIHM e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DocumentIHM entityToDto(Object e) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	// -------------------------------------------------------
	// PARTIE CREATION DES LISTENERS
	// Création des listeners sur les boutons de la toolbar
	// -------------------------------------------------------

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'exportation
	 * 
	 * @param button
	 *            le toolItem d'exportation
	 */
	private void addListenerExport(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
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
					//String[] idColonnes = listeIdColonnesOnglet.get(vue.getComposite_Etape3().getDossierConteneur().getSelectionIndex());
					String[] idColonnes = listeIdColonnesExportOnglet.get(item);
					for (int i = 0; i < idColonnes.length - 1; i++) {
						filecontent += idColonnes[i] + ";";
					}
					filecontent += idColonnes[idColonnes.length - 1];

					int selected = listeTypeOnglet.get(item);

					// Contenu du tableau
					if (selected == TYPE_TIERS) { // liste de tiers
						List<TiersIHM> modelDocumentTiers = (List<TiersIHM>)listeModeleOnglet.get(item);
						for (int i = 0; i < modelDocumentTiers.size(); i++) {
							filecontent += "\n";
							filecontent += modelDocumentTiers.get(i)
									.getCodeTiers() != null ? modelDocumentTiers
											.get(i).getCodeTiers()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getCompte() != null ? modelDocumentTiers
											.get(i).getCompte()
											+ ";" : ";";
							filecontent += modelDocumentTiers.get(i)
									.getNomEntreprise() != null ? modelDocumentTiers
											.get(i).getNomEntreprise()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getCodeTCivilite() != null ? modelDocumentTiers
											.get(i).getCodeTCivilite()
											+ ";"
											: ";";							
							filecontent += modelDocumentTiers.get(i)
									.getNomTiers() != null ? modelDocumentTiers
											.get(i).getNomTiers()
											+ ";" : ";";
							filecontent += modelDocumentTiers.get(i)
									.getPrenomTiers() != null ? modelDocumentTiers
											.get(i).getPrenomTiers()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getTelephone() != null ? modelDocumentTiers
											.get(i).getTelephone()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getAdresseEmail() != null ? modelDocumentTiers
											.get(i).getAdresseEmail()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getAdresseWeb() != null ? modelDocumentTiers
											.get(i).getAdresseWeb()
											+ ";"
											: ";";

							filecontent += modelDocumentTiers.get(i)
									.getAdresse1Adresse() != null ? modelDocumentTiers
											.get(i).getAdresse1Adresse()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getAdresse2Adresse() != null ? modelDocumentTiers
											.get(i).getAdresse2Adresse()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getAdresse3Adresse() != null ? modelDocumentTiers
											.get(i).getAdresse3Adresse()
											+ ";"
											: ";";

							filecontent += modelDocumentTiers.get(i)
									.getCodepostalAdresse() != null ? modelDocumentTiers
											.get(i).getCodepostalAdresse()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i).getVille() != null ? modelDocumentTiers
									.get(i).getVille()
									+ ";"
									: ";";
							filecontent += modelDocumentTiers.get(i).getPays() != null ? modelDocumentTiers
									.get(i).getPays()
									+ ";"
									: ";";
							filecontent += modelDocumentTiers.get(i)
									.getCodePaiement() != null ? modelDocumentTiers
											.get(i).getCodePaiement()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getCodeEntite() != null ? modelDocumentTiers
											.get(i).getCodeEntite()
											+ ";"
											: ";";
							filecontent += modelDocumentTiers.get(i)
									.getTypeTiers() != null ? modelDocumentTiers
											.get(i).getTypeTiers()
											: "";
						}
					} else if (selected == TYPE_ARTICLES) { // liste d'articles
						List<ArticleIHM> modelDocumentArticle = (List<ArticleIHM>)listeModeleOnglet.get(item);
						for (int i = 0; i < modelDocumentArticle.size(); i++) {
							filecontent += "\n";
							filecontent += modelDocumentArticle.get(i)
									.getCodearticle() != null ? modelDocumentArticle
											.get(i).getCodearticle()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getLibelle() != null ? modelDocumentArticle
											.get(i).getLibelle()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getDescription() != null ? modelDocumentArticle
											.get(i).getDescription()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getCodefamille() != null ? modelDocumentArticle
											.get(i).getCodefamille()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getLibellefamille() != null ? modelDocumentArticle
											.get(i).getLibellefamille()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getCodetva() != null ? modelDocumentArticle
											.get(i).getCodetva()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getTarifht() != null ? modelDocumentArticle
											.get(i).getTarifht().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getTarifttc() != null ? modelDocumentArticle
											.get(i).getTarifttc().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentArticle.get(i)
									.getCompte() != null ? modelDocumentArticle
											.get(i).getCompte()
											+ ";" : ";";
							filecontent += modelDocumentArticle.get(i)
									.getStockmini() != null ? modelDocumentArticle
											.get(i).getStockmini().toString()
											: "";
						}
					} else if (selected == TYPE_DOCUMENTS) { // liste de documents
						List<DocumentIHM> modelDocumentDocument = (List<DocumentIHM>)listeModeleOnglet.get(item);
						for (int i = 0; i < modelDocumentDocument.size(); i++) {
							filecontent += "\n";
							filecontent += modelDocumentDocument.get(i)
									.getCodedocument() != null ? modelDocumentDocument
											.get(i).getCodedocument()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getDatedocument() != null ? modelDocumentDocument
											.get(i).getDatedocument().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getDateecheance() != null ? modelDocumentDocument.get(i).getDateecheance().toString()+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getDatelivraison() != null ? modelDocumentDocument
											.get(i).getDatelivraison().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getReglement() != null ? modelDocumentDocument
											.get(i).getReglement().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getNetht() != null ? modelDocumentDocument
											.get(i).getNetht().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getNettva() != null ? modelDocumentDocument
											.get(i).getNettva().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getNetapayer() != null ? modelDocumentDocument
											.get(i).getNetapayer().toString()
											+ ";"
											: ";";
							filecontent += modelDocumentDocument.get(i)
									.getResteapayer() != null ? modelDocumentDocument
											.get(i).getResteapayer().toString()
											: "";
						}
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

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'exportation
	 * 
	 * @param button
	 *            le toolItem d'exportation
	 */
	private void addListenerImpression(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.err.println("selection : "+vue.getComposite_Etape3().getDossierConteneur().getSelectionIndex());
				imprJPA(item); // On lance la méthode d'impression
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private class ListeProvider implements IListeSelectionTiersProvider {
		private LgrTableViewer v = null;
		private List<TaTiers> l = null;
		private int type = 0;		

		public ListeProvider(LgrTableViewer v, List<TaTiers> l, int type) {
			this.v = v;
			this.l =l;
			this.type = type;
		}

		@Override
		public List<TaTiers> getListeTiers() {
			TiersIHM[] selection = Arrays.copyOf(v.getCheckedElements(), v.getCheckedElements().length, TiersIHM[].class);
			List<TaTiers> aConserver = new ArrayList<TaTiers>();
			List<TaTiers> res = l;

			boolean trouve = false;
			Iterator<TaTiers> ite = l.iterator();
			TaTiers tiers = null;
			for (int j=0; j<selection.length; j++) {
				trouve = false;
				ite = l.iterator();
				while(ite.hasNext() && !trouve) {
					tiers = ite.next();
					if(selection[j].getIdTiers()==tiers.getIdTiers()) {
						trouve = true;
					} 
				}

				if(trouve) {
					aConserver.add(tiers);
				}
			}
			res = aConserver;

			return res;
		}
	}
	
	private void videParametre(final CTabItem item) {
		if(listeformPubliOnglet.get(item)!=null) listeformPubliOnglet.get(item).dispose();
		listeformPubliOnglet.remove(item);
		formPubli = null;

		if(listeformEtiqOnglet.get(item)!=null) listeformEtiqOnglet.get(item).dispose();
		listeformEtiqOnglet.remove(item);
		formEtiq = null;

		if(btnShowFinalList!=null) btnShowFinalList.dispose();
		btnShowFinalList = null;

		if(listeCompositeGaucheOnglet.get(item)!=null) {
			Control[] c = listeCompositeGaucheOnglet.get(item).getChildren();
			for(int i=0;i<c.length;i++) {
				if(c[i]!=null && !c[i].isDisposed()) {
					c[i].dispose();
				}
			}
		}

		if(listeTableDetailOnglet.get(item)!=null) listeTableDetailOnglet.get(item).setVisible(false);
	}
	
	public void afficheParametreDetailPublipostage(final CTabItem item) {
		typeDetailCommActif = C_TYPE_DETAIL_COMMUNICATION_PUBLIPOSTAGE;
		videParametre(item);
		if(formPubli==null) {
			formPubli = new CompositePublipostage(listeCompositeGaucheOnglet.get(item), SWT.BORDER);
			GridData gridDataFormPubli = new GridData();
			gridDataFormPubli.widthHint = 500;
			formPubli.setLayoutData(gridDataFormPubli);

			listeformPubliOnglet.put(item,formPubli);

			final List<TiersIHM> listeDTODetail = new ArrayList<TiersIHM>();
			List<TaTiers> listeEntityDetail = null;
			if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_NORMAL)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_NORMAL);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_ADMINISTRATIF);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_COMMERCIAL);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			}

			PreferenceInitializer.initDefautProperties(false);

			afficheListe(item, listeDTODetail,TYPE_DETAIL_ADRESSE);
			listeCompositeNouveauOnglet.get(item).layout();
			
			CompositePublipostageTiersController publipostageTiersController = new CompositePublipostageTiersController(
					formPubli, 
					listeTiersOnglet.get(item),new ListeProvider(listeTabTiersViewerDetailOnglet.get(item), listeEntityDetail, TYPE_DETAIL_ADRESSE),
					Activator.getDefault().getPreferenceStore(),
					PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD,
					PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE, 
					new FichierDonneesAdresseTiers(),
					fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
							PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD),
							fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
									PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE)
					);

			formPubli.setVisible(true);
			formPubli.layout();
			listeCompositeGaucheOnglet.get(item).layout();
			listeCompositeNouveauOnglet.get(item).layout();
			masterController.getVue().reflow();
		} else {
			videParametre(item);
		}
	}
	
	public void afficheParametreDetailEtiquette(final CTabItem item) {
		typeDetailCommActif = C_TYPE_DETAIL_COMMUNICATION_ETIQUETTE;
		videParametre(item);
		if(formEtiq==null) {
			formEtiq = new CompositeEtiquette(listeCompositeGaucheOnglet.get(item), SWT.BORDER);
			listeformEtiqOnglet.put(item,formEtiq);
			
			final List<TiersIHM> listeDTODetail = new ArrayList<TiersIHM>();
			List<TaTiers> listeEntityDetail = null;
			if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_NORMAL)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_NORMAL);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_ADMINISTRATIF);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL)) {
				listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_ADRESSE ,TYPE_CORRESPONDANCE_COMMERCIAL);
				listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
						(List<Object>)(List<?>) listeEntityDetail
						));
			}

			afficheListe(item, listeDTODetail, TYPE_DETAIL_ADRESSE);
			listeCompositeNouveauOnglet.get(item).layout();
			
			PreferenceInitializer.initDefautProperties(false);
			CompositeEtiquetteTiersController etiquetteTiersController = new CompositeEtiquetteTiersController(formEtiq,
					listeTiersOnglet.get(item),new ListeProvider(listeTabTiersViewerDetailOnglet.get(item), listeEntityDetail, TYPE_DETAIL_ADRESSE),
					null,
					Activator.getDefault().getPreferenceStore(),
					PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, 
					new FichierDonneesAdresseTiers()
					);

			formEtiq.setVisible(true);
			formEtiq.layout();
			listeCompositeGaucheOnglet.get(item).layout();
			listeCompositeNouveauOnglet.get(item).layout();
			masterController.getVue().reflow();
		} else {
			videParametre(item);
		}
	}
	
	public void afficheParametreDetailEMail(final CTabItem item) {
		typeDetailCommActif = C_TYPE_DETAIL_COMMUNICATION_EMAIL;
		videParametre(item);
		Composite c = new Composite(listeCompositeGaucheOnglet.get(item), SWT.BORDER);
		c.setLayout(new GridLayout());
		Label l = new Label(listeCompositeGaucheOnglet.get(item),SWT.NULL);
		Button btnValider = new Button(c, SWT.PUSH);
		btnValider.setText("Envoyer les emails");
		btnValider.setImage(Activator.getImageDescriptor(iconOpenMail).createImage());
		
		final List<TiersIHM> listeDTODetail = new ArrayList<TiersIHM>();
		List<TaTiers> listeEntityDetail = null;
		if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_NORMAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_EMAIL ,TYPE_CORRESPONDANCE_NORMAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_EMAIL ,TYPE_CORRESPONDANCE_ADMINISTRATIF);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_EMAIL ,TYPE_CORRESPONDANCE_COMMERCIAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		}

		btnValider.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String dest = "";
				
				TiersIHM[] selection = Arrays.copyOf(listeTabTiersViewerDetailOnglet.get(item).getCheckedElements(), listeTabTiersViewerDetailOnglet.get(item).getCheckedElements().length, TiersIHM[].class);
				String[] tabDest = new String[selection.length];
				List<TiersIHM> modelDocumentTiers = Arrays.asList(selection);
				
				int i = 0;
				for (TiersIHM tiersIHM : modelDocumentTiers) {
					if (tiersIHM.getAdresseEmail()!=null) {
						tabDest[i] = tiersIHM.getAdresseEmail();
						i++;
					}
					System.out.println(tiersIHM.getAdresseEmail());
				}

				OpenmailMail om = new OpenmailMail();
				
				om.sendMail( 
						tabDest, null, null, 
						null,true
						);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		afficheListe(item, listeDTODetail, TYPE_DETAIL_EMAIL);
		listeCompositeNouveauOnglet.get(item).layout();

		c.layout();
		listeCompositeGaucheOnglet.get(item).layout();
		listeCompositeNouveauOnglet.get(item).layout();
		masterController.getVue().reflow();

	}
	
	public void afficheParametreDetailSMS(final CTabItem item) {
		typeDetailCommActif = C_TYPE_DETAIL_COMMUNICATION_SMS;
		videParametre(item);
		Composite c = new Composite(listeCompositeGaucheOnglet.get(item), SWT.BORDER);
		c.setLayout(new GridLayout());
		Button btnValider = new Button(c, SWT.PUSH);
		btnValider.setText("Envoyer les SMS");
		btnValider.setImage(Activator.getImageDescriptor(iconOpenMail).createImage());
		
		final List<TiersIHM> listeDTODetail = new ArrayList<TiersIHM>();
		List<TaTiers> listeEntityDetail = null;
		if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_NORMAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_SMS ,TYPE_CORRESPONDANCE_NORMAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_SMS ,TYPE_CORRESPONDANCE_ADMINISTRATIF);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_SMS ,TYPE_CORRESPONDANCE_COMMERCIAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		}

		btnValider.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String dest = "";
				
				TiersIHM[] selection = Arrays.copyOf(listeTabTiersViewerDetailOnglet.get(item).getCheckedElements(), listeTabTiersViewerDetailOnglet.get(item).getCheckedElements().length, TiersIHM[].class);
				String[] tabDest = new String[selection.length];
				List<TiersIHM> modelDocumentTiers = Arrays.asList(selection);
				
				int i = 0;
				for (TiersIHM tiersIHM : modelDocumentTiers) {
					if (tiersIHM.getAdresseEmail()!=null) {
						tabDest[i] = tiersIHM.getTelephone();
						i++;
					}
					System.out.println(tiersIHM.getTelephone());
				}

				String body = "";

				OpenmailSMS om = new OpenmailSMS();
				om.sendSMS(tabDest, body,
						null, null, null
						);

				System.out.println("==== SMS ====");}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		afficheListe(item, listeDTODetail, TYPE_DETAIL_SMS);
		listeCompositeNouveauOnglet.get(item).layout();

		c.layout();
		listeCompositeGaucheOnglet.get(item).layout();
		listeCompositeNouveauOnglet.get(item).layout();
		masterController.getVue().reflow();
	}
	
	public void afficheParametreDetailFax(final CTabItem item) {
		typeDetailCommActif = C_TYPE_DETAIL_COMMUNICATION_FAX;
		videParametre(item);
		Composite c = new Composite(listeCompositeGaucheOnglet.get(item), SWT.BORDER);
		c.setLayout(new GridLayout());
		Button btnValider = new Button(c, SWT.PUSH);
		btnValider.setText("Envoyer les fax");
		btnValider.setImage(Activator.getImageDescriptor(iconOpenMail).createImage());
		
		final List<TiersIHM> listeDTODetail = new ArrayList<TiersIHM>();
		List<TaTiers> listeEntityDetail = null;
		if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_NORMAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_FAX ,TYPE_CORRESPONDANCE_NORMAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_ADMINISTRATIF)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_FAX ,TYPE_CORRESPONDANCE_ADMINISTRATIF);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		} else if(listeComboCommOnglet.get(item).getText().equals(C_VALEUR_COMBO_TYPE_COMM_COMMERCIAL)) {
			listeEntityDetail = (List<TaTiers>)(List<?>) transformModel(listeTiersOnglet.get(item),TYPE_DETAIL_FAX ,TYPE_CORRESPONDANCE_COMMERCIAL);
			listeDTODetail.addAll(new MapperTiersIHM().listeEntityToDto(
					(List<Object>)(List<?>) listeEntityDetail
					));
		}

		btnValider.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TiersIHM[] selection = Arrays.copyOf(listeTabTiersViewerDetailOnglet.get(item).getCheckedElements(), listeTabTiersViewerDetailOnglet.get(item).getCheckedElements().length, TiersIHM[].class);
				String[] tabDest = new String[selection.length];
				List<TiersIHM> modelDocumentTiers = Arrays.asList(selection);
				
				int i = 0;
				PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
				String frenchNumberStr = null;
				String num = null;
				PhoneNumber frenchNumberProto = null;
				for (TiersIHM tiersIHM : modelDocumentTiers) {
					if (tiersIHM.getAdresseEmail()!=null) {
						
						frenchNumberStr = tiersIHM.getTelephone();
						try {
							frenchNumberProto = phoneUtil.parse(frenchNumberStr, "FR");
							num = phoneUtil.format(frenchNumberProto, PhoneNumberFormat.E164);
						} catch (NumberParseException ex) {
							logger.error("NumberParseException was thrown: " + e.toString());
						}
						
						tabDest[i] = num;
						i++;
					}
					System.out.println(tiersIHM.getTelephone());
				}

				OpenmailFAX om = new OpenmailFAX();
				om.sendFax(tabDest,
						null,null,
						null, null, null
						);

				System.out.println("==== FAX ====");}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		afficheListe(item, listeDTODetail, TYPE_DETAIL_FAX);
		listeCompositeNouveauOnglet.get(item).layout();

		c.layout();
		listeCompositeGaucheOnglet.get(item).layout();
		listeCompositeNouveauOnglet.get(item).layout();
		masterController.getVue().reflow();
	}
	
	/**
	 * Méthode permettant l'ajout de listeners sur le bouton de publipostage
	 * 
	 * @param button
	 *            le toolItem de publipostage
	 */
	private void addListenerPubli(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Composite formPubli = listeformPubliOnglet.get(item);
				if(formPubli!=null) {
					if (!formPubli.isVisible()) { // Si le formulaire de
						// publipostage est masqué, on l'affiche
						formPubli.setVisible(true);
						formPubli.layout();
					} else { // A l'inverse, on le masque
						formPubli.setVisible(false);
						formPubli.layout();
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
	 * Méthode permettant l'ajout de listeners sur le bouton de publipostage
	 * 
	 * @param button
	 *            le toolItem de publipostage
	 */
	private void addListenerPubliTiers(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				afficheParametreDetailPublipostage(item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'étiquettage
	 * 
	 * @param button
	 *            le toolItem de publipostage
	 */
	private void addListenerEtiq(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Composite formEtiq = listeformEtiqOnglet.get(item);
				if(formEtiq!=null) {
					if (!formEtiq.isVisible()) { // Si le formulaire de publipostage
						// est masqué, on l'affiche
						formEtiq.setVisible(true);
						formEtiq.layout();
					} else { // A l'inverse, on le masque
						formEtiq.setVisible(false);
						formEtiq.layout();
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
	 * Méthode permettant l'ajout de listeners sur le bouton d'étiquettage
	 * 
	 * @param button
	 *            le toolItem de publipostage
	 */
	private void addListenerEtiqTiers(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				afficheParametreDetailEtiquette(item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	/**
	 * Branchement d'action sur le bouton email
	 * 
	 * @param button - le toolItem de publipostage
	 */
	private void addListenerEmail(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				afficheParametreDetailEMail(item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	/**
	 * Branchement d'action sur le bouton SMS
	 * 
	 * @param button - le toolItem de publipostage
	 */
	private void addListenerSMS(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				afficheParametreDetailSMS(item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	/**
	 * Branchement d'action sur le bouton Fax
	 * 
	 * @param button - le toolItem de publipostage
	 */
	private void addListenerFax(ToolItem button, final CTabItem item) {
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				afficheParametreDetailFax(item);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private void addListenerTab() {
		// Listeners sur la première case du tableau
		if (selected == TYPE_TIERS) { // Tiers sélectionnés
			// pour l'ouverture du document voir
			// OngletResultatControllerJPA.java
			table.addMouseListener(new MouseAdapter() {

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = table.getSelection()[0]
							.getText(tabTiersViewer.findPositionNomChamp(
									"codeTiers"));

					String idEditor = "fr.legrain.editor.tiers.multi";

					LgrPartUtil.ouvreDocument(valeurIdentifiant, idEditor);
				}

			});
		} else if (selected == TYPE_ARTICLES) { // Articles sélectionnés
			table.addMouseListener(new MouseAdapter() {

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = table.getSelection()[0]
							.getText(tabArticleViewer.findPositionNomChamp(
									"codeArticle"));

					String idEditor = "fr.legrain.editor.article.multi";

					LgrPartUtil.ouvreDocument(valeurIdentifiant, idEditor);
				}

			});
		}	else if (selected == TYPE_DOCUMENTS) { // Documents sélectionnés
			table.addMouseListener(new MouseAdapter() {

				public void mouseDoubleClick(MouseEvent e) {
					String valeurIdentifiant = table.getSelection()[0]
							.getText(tabDocumentViewer.findPositionNomChamp(
									"codedocument"));

					String typedoc = valeurIdentifiant.substring(0,2);
					String idEditor = null;

					if (typedoc.equals("FV")){
						idEditor = "fr.legrain.editor.facture.swt.multi";
					} else if (typedoc.equals("DE")){
						idEditor = "fr.legrain.editor.devis.swt.multi";
					} else if (typedoc.equals("BC")){
						idEditor = "fr.legrain.editor.boncde.swt.multi";
					} else if (typedoc.equals("AV")){
						idEditor = "fr.legrain.editor.avoir.swt.multi";
					} else if (typedoc.equals("AP")){
						idEditor = "fr.legrain.editor.apporteur.swt.multi";
					} else if (typedoc.equals("AC")){
						idEditor = "fr.legrain.editor.acompte.swt.multi";
					} else if (typedoc.equals("PR")){
						idEditor = "fr.legrain.editor.proforma.swt.multi";
					} else if (typedoc.equals("BL")){
						idEditor = "fr.legrain.editor.bonliv.swt.multi";
					}

					if (idEditor !=null){
						LgrPartUtil.ouvreDocument(valeurIdentifiant, idEditor);
					}
				}

			});
		}
	}


	// ----------------------------------------------------------------------------------------
	// PARTIE CREATION DES METHODES D'ACTION
	// Création des actions effectués suite a des pressions sur les boutons de
	// la toolbar
	// -----------------------------------------------------------------------------------------

	/**
	 * Méthode d'impression de la liste
	 */
	public void imprJPA(CTabItem item) {
		// Préparation des entités pour la génération du document
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(
				getEm());
		TaInfoEntreprise infoEntreprise = taInfoEntrepriseDAO.findInstance();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties();
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		String valueMap = null;
		String[] valueAttributes = null;
		LinkedList<String> listeAttribut = new LinkedList<String>();
		String typeBirt = null;
		String alignement = null;

		String birtDataTypeFloat = "float";
		String birtDataTypeInteger = "integer";
		String birtDataTypeString = "string";
		String birtDataTypeDecimal = "decimal";
		String birtAlignRight = "right";
		String birtAlignLeft = "left";

		/*
		 * Generation des informations decrivant la structure/presentation de la
		 * table. Les lignes generees doivent etre au meme format que celle du
		 * fichier "AttributeTableEdition.properties"
		 */
		for (int i = 0; i < infos.length; i++) {
			try {
				// titre
				valueMap = infos[i].getTitre() + ";" + infos[i].getTaille()
						+ ";center;medium;bold;%;string;vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablHead().put(
						Resultat.debutNomChamp + (i + 1),
						new AttributElementResport(valueAttributes));

				// contenu
				if (infos[i].getTypeString() != null) {
					if (infos[i].getTypeString().equals(
							ConstVisualisation.typeFloat)

							) {
						typeBirt = birtDataTypeFloat;
						alignement = birtAlignRight;
					} else if (infos[i].getTypeString().equals(
							ConstVisualisation.typeBigDecimal)
							|| infos[i].getTypeString().equals(
									ConstVisualisation.typeDouble)) {
						typeBirt = birtDataTypeDecimal;
						alignement = birtAlignRight;
					} else if (infos[i].getTypeString().equals(
							ConstVisualisation.typeInteger)) {
						typeBirt = birtDataTypeInteger;
						alignement = birtAlignRight;
						// }
					} else {
						typeBirt = birtDataTypeString;
						alignement = birtAlignLeft;
					}
				}

				valueMap = Resultat.debutNomChamp + (i + 1) + ";"
						+ infos[i].getTaille() + ";" + alignement
						+ ";medium;bold;%;" + typeBirt + ";vide;vide";
				valueAttributes = valueMap.split(";");
				fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail()
				.put(Resultat.debutNomChamp + (i + 1),
						new AttributElementResport(valueAttributes));

				listeAttribut.add(Resultat.debutNomChamp + (i + 1));
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		LinkedHashMap<String, AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties
				.getMapAttributeTablHead();
		LinkedHashMap<String, AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties
				.getMapAttributeTablDetail();

		// Passage de contexte au Birt Viewer
		ViewerPlugin.getDefault().getPluginPreferences().setValue(
				WebViewer.APPCONTEXT_EXTENSION_KEY,
				EditionAppContext.APP_CONTEXT_NAME);

		// Recuperation des objets a imprimer
		Collection<Resultat> collectionResultat = remplirListe(item);

		// Initialisation des colonnes numérique sans valeur avec des zéros,
		// sinon plante lors de l'extraction
		for (Resultat resultat : collectionResultat) {
			for (int i = 0; i < infos.length; i++) {
				if ((infos[i].getTypeString().equals(
						ConstVisualisation.typeBigDecimal)
						|| infos[i].getTypeString().equals(
								ConstVisualisation.typeFloat)
								|| infos[i].getTypeString().equals(
										ConstVisualisation.typeDouble) || infos[i]
												.getTypeString().equals(ConstVisualisation.typeInteger))
												&& (resultat.findValue(i + 1) == null || resultat
												.findValue(i + 1).equals(""))) {
					resultat.changeValue(i + 1, "0");
				}
			}
		}

		ImprimeObjet.clearListAndMap();
		ImprimeObjet.l.addAll(collectionResultat);

		/************** pour préparer plusieurs objects construisirent l'edition ***************/
		ImprimeObjet.m.put(Resultat.class.getSimpleName(), ImprimeObjet.l);

		/**************************************************************************************/

		/*
		 * La liste des attibuts de la classe qui sont utilise pour generer le
		 * script dans l'edition est en general retrouve a partir du mapping
		 * dozer et des champ affiches dans l'interface. Ici il n'y a pas de
		 * mapping dozer donc, il faut donner cette liste explicitement.
		 */
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(new Resultat(),
				listeAttribut);

		ConstEdition constEdition = new ConstEdition();
		String nomDossier = null;

		/**
		 * nombreLine ==> le nombre de ligne dans interface
		 */
		int nombreLine = collectionResultat.size();

		if (nombreLine == 0) {
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		} else {
			if (infoEntreprise.getIdInfoEntreprise() == 0) {
				nomDossier = ConstEdition.INFOS_VIDE;
			} else {
				nomDossier = infoEntreprise.getNomInfoEntreprise();
			}
			if (selected == 1) {
				constEdition.addValueList(tabTiersViewer, "TiersIHM");
			} else if (selected == 2) {
				constEdition.addValueList(tabArticleViewer, "DocumentIHM");
			} else if (selected == 3) {
				constEdition.addValueList(tabDocumentViewer, "ArticleIHM");
			}

			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION + "/"
					+ Const.C_NOM_PROJET_TMP + "/"
					+ Resultat.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			String nomVue = "visualisation";
			Path pathFileReport = new Path(folderEditionDynamique + "/"
					+ nomVue + ".rptdesign");
			final String pathFileReportDynamic = pathFileReport
					.toPortableString();

			MakeDynamiqueReport dynamiqueReport = new MakeDynamiqueReport(
					constEdition.getNameTableEcran(), constEdition
					.getNameTableBDD(), pathFileReportDynamic, nomVue,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE, nomDossier,
					infos.length);

			/**************************************************************/
			dynamiqueReport
			.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			dynamiqueReport.setNomObjet(Resultat.class.getSimpleName());
			dynamiqueReport.setSimpleNameEntity(Resultat.class.getSimpleName());
			/**************************************************************/
			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();

			attribuGridHeader.put("Etats paramétrés",
					new AttributElementResport("",
							ConstEdition.TEXT_ALIGN_CENTER,
							ConstEdition.FONT_SIZE_XX_LARGE,
							ConstEdition.FONT_WEIGHT_BOLD, "",
							ConstEdition.COLUMN_DATA_TYPE_STRING,
							ConstEdition.UNITS_VIDE, ""));

			attribuGridHeader.put(
					"Résultats correspondants à la feuille de résultats "
							+ (compteur - 1), new AttributElementResport("",
									ConstEdition.TEXT_ALIGN_CENTER,
									ConstEdition.FONT_SIZE_X_LARGE,
									ConstEdition.FONT_WEIGHT_BOLD, "",
									ConstEdition.COLUMN_DATA_TYPE_STRING,
									ConstEdition.UNITS_VIDE, ConstEdition.COLOR_GRAY));

			// ConstEdition.CONTENT_COMMENTS =
			// ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
			ConstEdition.CONTENT_COMMENTS = "COMMENTAIRE";
			dynamiqueReport.initializeBuildDesignReportConfig();
			dynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			dynamiqueReport.makeReportHeaderGrid(3, 5, 100,
					ConstEdition.UNITS_PERCENTAGE, attribuGridHeader);

			dynamiqueReport.biuldTableReport("100",
					ConstEdition.UNITS_PERCENTAGE, nomVue, 1, 1, 2, "5000",
					mapAttributeTablHead, mapAttributeTablDetail);
			dynamiqueReport.savsAsDesignHandle();

			String url = WebViewerUtil.debutURL();

			url += "run?__report=";

			url += pathFileReportDynamic;

			url += "&__document=doc" + new Date().getTime();
			url += "&__format=pdf";
			logger.debug("URL edition: " + url);

			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						LgrPartListener.getLgrPartListener().setLgrActivePart(
								null);
						PlatformUI.getWorkbench().getBrowserSupport()
						.createBrowser(
								IWorkbenchBrowserSupport.AS_EDITOR,
								"myId", "Visualisation", "").openURL(
										new URL(finalURL));
					} catch (PartInitException e) {
						logger.error("", e);
					} catch (MalformedURLException e) {
						logger.error("", e);
					}
				}
			});
		}
	}

	private InfosPresentation[] infos = null;

	class InfosPresentation {
		private String titre;
		private String taille;
		private Class type;
		private String typeString;
		private boolean total;

		public InfosPresentation(String titre, String taille, Class type,
				String typeString, boolean total) {
			super();
			this.titre = titre;
			this.taille = taille;
			this.type = type;
			this.typeString = typeString;
			this.total = total;
		}

		public InfosPresentation() {

		}

		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public String getTaille() {
			return taille;
		}

		public void setTaille(String taille) {
			this.taille = taille;
		}

		public Class getType() {
			return type;
		}

		public void setType(Class type) {
			this.type = type;
		}

		public String getTypeString() {
			return typeString;
		}

		public void setTypeString(String typeString) {
			this.typeString = typeString;
		}

		public boolean isTotal() {
			return total;
		}

		public void setTotal(boolean total) {
			this.total = total;
		}

	}

	public LinkedList<Resultat> remplirListe(CTabItem item) {
		LinkedList<Resultat> listeRes = new LinkedList<Resultat>();
		// Contenu du tableau
		int selected = listeTypeOnglet.get(item);
		if (selected == TYPE_TIERS) { // liste de tiers
			List<TiersIHM> modelDocumentTiers = (List<TiersIHM>)listeModeleOnglet.get(item);
			for (int i = 0; i < modelDocumentTiers.size(); i++) {
				String[] temp = {
						(modelDocumentTiers.get(i).getCodeTiers() != null ? modelDocumentTiers
								.get(i).getCodeTiers()
								: ""),
								(modelDocumentTiers.get(i).getCompte() != null ? modelDocumentTiers
										.get(i).getCompte()
										: ""),
										(modelDocumentTiers.get(i).getNomEntreprise() != null ? modelDocumentTiers
												.get(i).getNomEntreprise()
												: ""),
												(modelDocumentTiers.get(i).getCodeTCivilite() != null ? modelDocumentTiers
														.get(i).getCodeTCivilite()
														: ""),												
												(modelDocumentTiers.get(i).getNomTiers() != null ? modelDocumentTiers
														.get(i).getNomTiers()
														: ""),
														(modelDocumentTiers.get(i).getPrenomTiers() != null ? modelDocumentTiers
																.get(i).getPrenomTiers()
																: ""),
																(modelDocumentTiers.get(i).getTelephone() != null ? modelDocumentTiers
																		.get(i).getTelephone()
																		: ""),
																		(modelDocumentTiers.get(i).getAdresseEmail() != null ? modelDocumentTiers
																				.get(i).getAdresseEmail()
																				: ""),
																				(modelDocumentTiers.get(i).getAdresseWeb() != null ? modelDocumentTiers
																						.get(i).getAdresseWeb()
																						: ""),
																						(modelDocumentTiers.get(i).getCodepostalAdresse() != null ? modelDocumentTiers
																								.get(i).getCodepostalAdresse()
																								: ""),
																								(modelDocumentTiers.get(i).getVille() != null ? modelDocumentTiers
																										.get(i).getVille()
																										: ""),
																										(modelDocumentTiers.get(i).getPays() != null ? modelDocumentTiers
																												.get(i).getPays()
																												: ""),
																												(modelDocumentTiers.get(i).getCodePaiement() != null ? modelDocumentTiers
																														.get(i).getCodePaiement()
																														: ""),
																														(modelDocumentTiers.get(i).getCodeEntite() != null ? modelDocumentTiers
																																.get(i).getCodeEntite()
																																: ""),
																																(modelDocumentTiers.get(i).getTypeTiers() != null ? modelDocumentTiers
																																		.get(i).getTypeTiers()
																																		: "") };
				listeRes.add(new Resultat(temp));
			}
		} else if (selected == TYPE_ARTICLES) { // liste d'articles
			List<ArticleIHM> modelDocumentArticle = (List<ArticleIHM>)listeModeleOnglet.get(item);
			for (int i = 0; i < modelDocumentArticle.size(); i++) {
				String[] temp = {
						(modelDocumentArticle.get(i).getCodearticle() != null ? modelDocumentArticle
								.get(i).getCodearticle()
								: ""),
								(modelDocumentArticle.get(i).getLibelle() != null ? modelDocumentArticle
										.get(i).getLibelle()
										: ""),
										(modelDocumentArticle.get(i).getDescription() != null ? modelDocumentArticle
												.get(i).getDescription()
												: ""),
												(modelDocumentArticle.get(i).getCodefamille() != null ? modelDocumentArticle
														.get(i).getCodefamille()
														: ""),
														(modelDocumentArticle.get(i).getLibellefamille() != null ? modelDocumentArticle
																.get(i).getLibellefamille()
																: ""),
																(modelDocumentArticle.get(i).getCodetva() != null ? modelDocumentArticle
																		.get(i).getCodetva()
																		: ""),
																		(modelDocumentArticle.get(i).getTarifht().toString() != null ? modelDocumentArticle
																				.get(i).getTarifht().toString()
																				: ""),
																				(modelDocumentArticle.get(i).getTarifttc().toString() != null ? modelDocumentArticle
																						.get(i).getTarifttc().toString()
																						: ""),
																						(modelDocumentArticle.get(i).getCompte() != null ? modelDocumentArticle
																								.get(i).getCompte()
																								: ""),
																								(modelDocumentArticle.get(i).getStockmini().toString() != null ? modelDocumentArticle
																										.get(i).getStockmini().toString()
																										: "") };
				listeRes.add(new Resultat(temp));
			}

		} else if (selected == TYPE_DOCUMENTS) { // liste de documents
			List<DocumentIHM> modelDocumentDocument = (List<DocumentIHM>)listeModeleOnglet.get(item);
			for (int i = 0; i < modelDocumentDocument.size(); i++) {
				String[] temp = {
						(modelDocumentDocument.get(i).getCodedocument() != null ? modelDocumentDocument
								.get(i).getCodedocument()
								: ""),
								(modelDocumentDocument.get(i).getDatedocument()
										.toString() != null ? modelDocumentDocument
												.get(i).getDatedocument().toString() : ""),
												(modelDocumentDocument.get(i).getDateecheance()
														.toString() != null ? modelDocumentDocument
																.get(i).getDateecheance().toString() : ""),
																(modelDocumentDocument.get(i).getDatelivraison()
																		.toString() != null ? modelDocumentDocument
																				.get(i).getDatelivraison().toString() : ""),
																				(modelDocumentDocument.get(i).getReglement().toString() != null ? modelDocumentDocument
																						.get(i).getReglement().toString()
																						: ""),
																						(modelDocumentDocument.get(i).getNetht().toString() != null ? modelDocumentDocument
																								.get(i).getNetht().toString()
																								: ""),
																								(modelDocumentDocument.get(i).getNettva().toString() != null ? modelDocumentDocument
																										.get(i).getNettva().toString()
																										: ""),
																										(modelDocumentDocument.get(i).getNetapayer().toString() != null ? modelDocumentDocument
																												.get(i).getNetapayer().toString()
																												: ""),
																												(modelDocumentDocument.get(i).getResteapayer()
																														.toString() != null ? modelDocumentDocument
																																.get(i).getResteapayer().toString() : "") };
				listeRes.add(new Resultat(temp));
			}
		}
		return listeRes;
	}

//	private List<TiersIHM> transformModelIHM(List<TiersIHM> model, int typeDetail, int typeCorrespondance) {
//		if(typeDetail==TYPE_DETAIL_ADRESSE){
//			if(typeCorrespondance==TYPE_CORRESPONDANCE_ADMINISTRATIF){
//
//			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_COMMERCIAL){
//
//			}
//		}
//		return null;
//	}

	private List<Object> transformModel(List<TaTiers> model, int typeDetail, int typeCorrespondance) {
		List<Object> res = new ArrayList<Object>();
		if(typeDetail==TYPE_DETAIL_ADRESSE){
			if(typeCorrespondance==TYPE_CORRESPONDANCE_ADMINISTRATIF){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaAdresse a : taTiers.getTaAdresses()) {
						if(LibConversion.intToBoolean(a.getCommAdministratifAdresse())) {
							if(nb!=0) {
								//copier/déplacer l'adresse dans l'adresse par défaut
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdAdresse());
								clone.setTaAdresse(a);
								res.add(clone);
							} else {
								//si ce n'est pas la 1ère adresse à ajouter pour ce tiers, le dupliquer et ajouter l'adresse
								taTiers.setTaAdresse(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdAdresse());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_COMMERCIAL){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaAdresse a : taTiers.getTaAdresses()) {
						if(LibConversion.intToBoolean(a.getCommCommercialAdresse())) {
							if(nb!=0) {
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdAdresse());
								clone.setTaAdresse(a);
								res.add(clone);
							} else {
								taTiers.setTaAdresse(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdAdresse());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_NORMAL){
				for (TaTiers taTiers : model) {
					if(taTiers.getTaAdresse()!=null) {
						//il y a une adresse par defaut, on affiche celle là
						TaTiers clone = copyTiersAdresse(taTiers, taTiers.getTaAdresse().getIdAdresse());
						clone.setTaAdresse(taTiers.getTaAdresse());
						res.add(clone);
					}
				}
			}
		}
		else if(typeDetail==TYPE_DETAIL_EMAIL){
			if(typeCorrespondance==TYPE_CORRESPONDANCE_ADMINISTRATIF){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaEmail a : taTiers.getTaEmails()) {
						if(LibConversion.intToBoolean(a.getCommAdministratifEmail())) {
							if(nb!=0) {
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdEmail());
								clone.setTaEmail(a);
								res.add(clone);
							} else {
								taTiers.setTaEmail(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdEmail());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_COMMERCIAL){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaEmail a : taTiers.getTaEmails()) {
						if(LibConversion.intToBoolean(a.getCommCommercialEmail())) {
							if(nb!=0) {
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdEmail());
								clone.setTaEmail(a);
								res.add(clone);
							} else {
								taTiers.setTaEmail(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdEmail());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_NORMAL){
				for (TaTiers taTiers : model) {
					if(taTiers.getTaEmail()!=null) {
						//il y a un email par defaut, on affiche celui là
						TaTiers clone = copyTiersAdresse(taTiers, taTiers.getTaEmail().getIdEmail());
						clone.setTaEmail(taTiers.getTaEmail());
						res.add(clone);
					}
				}
			}
		}
		else if(typeDetail==TYPE_DETAIL_FAX || typeDetail==TYPE_DETAIL_SMS){
			if(typeCorrespondance==TYPE_CORRESPONDANCE_ADMINISTRATIF){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaTelephone a : taTiers.getTaTelephones()) {
						if(LibConversion.intToBoolean(a.getCommAdministratifTelephone())) {
							if(nb!=0) {
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdTelephone());
								clone.setTaTelephone(a);
								res.add(clone);
							} else {
								taTiers.setTaTelephone(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdTelephone());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_COMMERCIAL){
				for (TaTiers taTiers : model) {
					int nb = 0;
					for(TaTelephone a : taTiers.getTaTelephones()) {
						if(LibConversion.intToBoolean(a.getCommCommercialTelephone())) {
							if(nb!=0) {
								TaTiers clone = copyTiersAdresse(taTiers, a.getIdTelephone());
								clone.setTaTelephone(a);
								res.add(clone);
							} else {
								taTiers.setTaTelephone(a);
								taTiers.setIdTiers(taTiers.getIdTiers()+a.getIdTelephone());
								res.add(taTiers);
								nb++;
							}
						}
					}
				}
			} else if(typeCorrespondance==TYPE_CORRESPONDANCE_NORMAL){
				for (TaTiers taTiers : model) {
					if(taTiers.getTaTelephone()!=null) {
						//il y a un telephone par defaut, on affiche celui là
						TaTiers clone = copyTiersAdresse(taTiers, taTiers.getTaTelephone().getIdTelephone());
						clone.setTaTelephone(taTiers.getTaTelephone());
						res.add(clone);
					}
				}
			}
		}
		return res;
	}

	private TaTiers copyTiersAdresse(TaTiers tiers, int variableID) {

		Cloner cloner=new Cloner();

		//TaTiers clone=cloner.deepClone(tiers);


		TaTiers clone = new TaTiers();

		clone.setIdTiers(tiers.getIdTiers()+variableID);
		clone.setCodeTiers(tiers.getCodeTiers());
		clone.setNomTiers(tiers.getNomTiers());
		clone.setPrenomTiers(tiers.getPrenomTiers());
		if(tiers.getTaEntreprise()!=null) {
			TaEntreprise t = new TaEntreprise();
			t.setNomEntreprise(tiers.getTaEntreprise().getNomEntreprise());
			clone.setTaEntreprise(t);
		}
		if(tiers.getTaTCivilite()!=null) {
			TaTCivilite t = new TaTCivilite();
			t.setCodeTCivilite(tiers.getTaTCivilite().getCodeTCivilite());
			clone.setTaTCivilite(t);
		}
		return clone;
	}

}
