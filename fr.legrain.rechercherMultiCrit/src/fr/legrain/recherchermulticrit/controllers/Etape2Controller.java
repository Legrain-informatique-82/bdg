package fr.legrain.recherchermulticrit.controllers;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.gestCom.librairiesEcran.swt.AbstractControllerMini;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.GroupeLigne;
import fr.legrain.recherchermulticrit.Ligne;
import fr.legrain.recherchermulticrit.LigneArticle;
import fr.legrain.recherchermulticrit.LigneDocument;
import fr.legrain.recherchermulticrit.LigneTiers;
import fr.legrain.recherchermulticrit.Requete;
import fr.legrain.recherchermulticrit.ecrans.PaCompositeSectionEtape1;
import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.requete.dao.TaLigneRequete;
import fr.legrain.requete.dao.TaLigneRequeteDAO;
import fr.legrain.requete.dao.TaRequete;
import fr.legrain.requete.dao.TaRequeteDAO;
import fr.legrain.tiers.dao.TaTiers;


public class Etape2Controller extends AbstractControllerMini {

	static Logger logger = Logger.getLogger(Etape1Controller.class.getName());	


	private List<GroupeLigne> groupes = new ArrayList<GroupeLigne>();	

	private FormPageController masterController = null;

	private PaFormPage vue = null;

	private Requete larequete = null;

	private int selectedGroup = 0;


	/* Constructeur par défaut */
	public Etape2Controller(FormPageController masterContoller, PaFormPage vue, EntityManager em) {
		this.vue = vue;
		this.masterController = masterContoller;

	}

	public void initialiseModelIHM() {
		initActions();

	}



	@Override
	protected void initActions() {	
		// Listener sur le bouton ajout tiers
		addListenerGroupe(vue.getComposite_Etape2().getItemAddGroupe());
		addListenerDelGroupe(vue.getComposite_Etape2().getItemDelGroupe());
		addListenerTiers(vue.getComposite_Etape2().getItemTiers());
		if(!masterController.versionContactCrado) {
			addListenerArticle(vue.getComposite_Etape2().getItemArticle());
			addListenerDocument(vue.getComposite_Etape2().getItemDocument());
		} else {
			/** Version contact "crado", à remplacer par un ensemble de points d'extension*/
			vue.getComposite_Etape2().getItemArticle().setEnabled(false);
			vue.getComposite_Etape2().getItemDocument().setEnabled(false);
			vue.getComposite_Etape2().getItemArticle().dispose();
			vue.getComposite_Etape2().getItemDocument().dispose();
		}
		addListenerRecherche(vue.getComposite_Etape2().getBtnRechercher());
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
	 * Méthode permettant l'ajout de listeners sur le bouton d'ajout critere Tiers
	 * @param button le toolItem d'ajout de critere Tiers
	 */
	private void addListenerTiers(ToolItem button){
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (groupes.size()==0){
					vue.getComposite_Etape2().getItemCombo().setEnabled(true);
					ajoutGroupe();
				}
				new LigneTiers(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController);

				// On rafraichit le layout et on ajuste la fenêtre
				scrollAjust();
				surbrillance();
				vue.getComposite_Etape2().getComposite().layout();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

	}


	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'ajout critere article
	 * @param button le toolItem d'ajout de critere article
	 */
	private void addListenerArticle(ToolItem button){
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (groupes.size()==0){
					vue.getComposite_Etape2().getItemCombo().setEnabled(true);
					ajoutGroupe();				}
				new LigneArticle(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController);

				// On rafraichit le layout et on ajuste la fenêtre
				scrollAjust();
				surbrillance();
				vue.getComposite_Etape2().getComposite().layout();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

	}

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'ajout critere document
	 * @param button le toolItem d'ajout de critere document
	 */
	private void addListenerDocument(final ToolItem button){
		// Ajout du Listener permettant l'ouverture du menu déroulant
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// On crée la zone qui va contenir le menu
				Rectangle rect = button.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = vue.getComposite_Etape2().getToolBar().toDisplay(pt);
				// On integre le menu dans la zone
				vue.getComposite_Etape2().getMenu().setLocation(pt.x, pt.y);
				vue.getComposite_Etape2().getMenu().setVisible(true);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


		// On ajoute un listener pour chaque bouton du menu
		for (int i = 0 ; i<vue.getComposite_Etape2().getMenu().getItems().length;i++){
			// On récupère la position du bouton dans le menu
			final int position = i;
			// On ajoute le listener
			vue.getComposite_Etape2().getMenu().getItem(position).addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					if (groupes.size()==0){
						vue.getComposite_Etape2().getItemCombo().setEnabled(true);
						ajoutGroupe();
					}
					// On ajoute le critère document correspondant
					new LigneDocument(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController,
							vue.getComposite_Etape2().getMenu().getItem(position).getText());
					// On rafraîchit le layout pour faire apparaître le nouveau crtiere
					scrollAjust();
					surbrillance();
					vue.getComposite_Etape2().getComposite().layout();
				}

			});
		}
	}

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton d'ajout de groupe
	 * @param button le toolItem d'ajout de groupe
	 */
	private void addListenerGroupe(ToolItem button){
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (groupes.size() == 0){
					vue.getComposite_Etape2().getItemCombo().setEnabled(true);
				}
				ajoutGroupe();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

	}

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton de sélection de groupe
	 * @param button le toolItem de sélection de groupe
	 */
	private void addListenerSelectGroupe(final ToolItem button){
		// Ajout du Listener permettant l'ouverture du menu déroulant
		button.addSelectionListener( new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// On crée la zone qui va contenir le menu
				Rectangle rect = button.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = vue.getComposite_Etape2().getToolBar().toDisplay(pt);
				// On integre le menu dans la zone
				vue.getComposite_Etape2().getMenuGr().setLocation(pt.x, pt.y);
				vue.getComposite_Etape2().getMenuGr().setVisible(true);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


		// On ajoute un listener pour chaque bouton du menu
		for (int i = 0 ; i<vue.getComposite_Etape2().getMenuGr().getItems().length;i++){
			// On récupère la position du bouton dans le menu
			final int position = i;
			int realPosition = 0;
			// On ajoute le listener
			vue.getComposite_Etape2().getMenuGr().getItem(position).addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					// On ajoute le critère document correspondant
					int realPosition = position+1;
					vue.getComposite_Etape2().getItemCombo().setText("Groupe Actif : "+realPosition);
					selectedGroup = position; 
					// On met le menu en surbrillance
					surbrillance();

					vue.getComposite_Etape2().getComposite().layout();
				}

			});	
		}

	}

	/**
	 * Méthode permettant l'ajout d'un listener sur groupe
	 * Un clic sur le groupe sélectionne le groupe
	 */
	private void addListenerOnGroupe(Composite composite,final int numero) {
		composite.addMouseListener(new MouseAdapter() {

			public void mouseDown(MouseEvent e) {
				vue.getComposite_Etape2().getItemCombo().setText("Groupe Actif : "+numero);
				selectedGroup = numero-1; 
				// On met le menu en surbrillance
				surbrillance();

				vue.getComposite_Etape2().getComposite().layout();
			}

		});
	}

	public List<GroupeLigne> getGroupes() {
		return groupes;
	}

	/**
	 * Méthode permettant l'ajout d'un nouveau groupe
	 */
	private void ajoutGroupe() {
		// On ajoute le groupe a la liste
		groupes.add(new GroupeLigne(groupes.size()+1,vue.getComposite_Etape2().getComposite(),vue.getToolkit()));
		// On crée un item portant le nom du groupe dans le bouton de sélection
		MenuItem item = new MenuItem(vue.getComposite_Etape2().getMenuGr(), SWT.PUSH);
		item.setText("Groupe "+groupes.size());
		item.setImage(Activator.getImageDescriptor(vue.getComposite_Etape2().iconSelGroup).createImage());
		vue.getComposite_Etape2().getItemCombo().setText("Groupe Actif : "+groupes.size());
		selectedGroup = groupes.size()-1;

		Control[] childrens = groupes.get(groupes.size()-1).getGroupe_composite().getChildren();
		int numero = groupes.get(groupes.size()-1).getNumero();
		addListenerOnGroupe(groupes.get(groupes.size()-1).getGroupe_composite(),numero);

		// On met à jour le nom des groupes
		refreshAllGroupNames();



		// On rafraichit le layout
		scrollAjust();
		vue.getComposite_Etape2().getComposite().layout();
	}

	/**
	 * Méthode permettant l'ajout de listeners sur le bouton de suppression de groupe
	 * @param button le toolItem de suppression de groupe
	 */
	private void addListenerDelGroupe(ToolItem button){
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {			
				if (groupes.size() != 0){	
					selectedGroup = groupes.size()-1;
					surbrillance();
					String  ttlBox = "Attention";
					String  msgBox = "Supprimer le Groupe "
						+Integer.toString(selectedGroup+1)+" revient à supprimer"
						+" tous les critères inscrits dans ce dernier. \n "
						+"Êtes vous sûr de vouloir continuer ?";
					boolean confirm = 
						MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlBox, msgBox);
					if (confirm) {
						// On supprime le composite de la liste
						groupes.get(selectedGroup).getGroupe_composite().dispose();
						// On supprime le groupe de la liste de groupes
						groupes.remove(selectedGroup);

						// On supprime l'item du menu concernant le groupe
						vue.getComposite_Etape2().getMenuGr().getItem(selectedGroup).dispose();		

						// On remet à jour le groupe sélectionné
						selectedGroup = findLastGroupNotNull();
						vue.getComposite_Etape2().getItemCombo().setText("Groupe Actif : "+Integer.toString(selectedGroup+1));

						if(groupes.size() == 0){
							vue.getComposite_Etape2().getItemCombo().setEnabled(false);
						}

						// On met à jour le nom des groupes
						refreshAllGroupNames();


						// On rafraichit le layout
						scrollAjust();
						vue.getComposite_Etape2().getComposite().layout();
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
	 * Méthode permettant de trouver le dernier groupe non vide
	 * @return le numero du groupe non vide
	 */
	private int findLastGroupNotNull (){
		boolean trouve = false;
		int position = groupes.size()-1;
		for(;!trouve && position>=0;position--){
			trouve = !groupes.get(position).getGroupe_composite().isDisposed();
		}
		return position+1;
	}

	/**
	 * Méthode permettant de remettre à jour le nom des groupes 
	 * après une suppression de groupe
	 */
	private void refreshAllGroupNames() {
		int position = 0;
		for(int i = 0 ; i < groupes.size(); i++){
			position = i+1;
			groupes.get(i).refreshNumGroupe(position);
			vue.getComposite_Etape2().getMenuGr().getItem(i).setText("Groupe "+position);
		}
		// On met à jour les listeners
		addListenerSelectGroupe(vue.getComposite_Etape2().getItemCombo());
		surbrillance();
	}

	/**
	 * Méthode permettant de mettre en surbrillance un groupe
	 * après une suppression de groupe
	 */
	private void surbrillance() {
		// On crée les couleurs de surbrillance
		Color actif = new Color(PlatformUI.getWorkbench().getDisplay(),232,238,245);
		Color inactif = new Color(PlatformUI.getWorkbench().getDisplay(),255,255,255);
		// On parcourt l'ensemble des groupes en initialisant la couleur par défaut (inactif)
		for(int i = 0 ; i < groupes.size(); i++){
			groupes.get(i).getGroupe_composite().setBackground(inactif);
			if ( i!= 0 ) { // cas du premier groupe sans ligne spéciale
				groupes.get(i).getLignespec().setBackground(inactif);
			}
			for (int j = 0 ; j<groupes.get(i).getGroupe().size(); j++){
				groupes.get(i).getGroupe().get(j).getComposite().setBackground(inactif);
			}
		}
		// On met en surbrillance le groupe sélectionné
		groupes.get(selectedGroup).getGroupe_composite().setBackground(actif);
		if ( selectedGroup!= 0 ) {
			groupes.get(selectedGroup).getLignespec().setBackground(actif);
		}			
		for (int j = 0 ; j<groupes.get(selectedGroup).getGroupe().size(); j++){
			groupes.get(selectedGroup).getGroupe().get(j).getComposite().setBackground(actif);
		}
	}

	/**
	 * Méthode permettant de mettre à jour la taille du composite
	 * pour le scrolledcomposite qui le contient
	 */
	private void scrollAjust(){
		int width = -1; // Taille invalide
		int newWidth = vue.getComposite_Etape2().getComposite().getSize().x; // On récupère la taille du composite
		if (newWidth != width) {
			// Si la nouvelle taille est valide
			// On met à jour celle du scrolledcomposite
			vue.getComposite_Etape2().getSc().setMinHeight(vue.getComposite_Etape2().getComposite().computeSize(newWidth, SWT.DEFAULT).y);
			width = newWidth;
		}
	}

	/**
	 * Méthode permettant de lancer la recherche en fonction des critères remplis
	 * @param btnRechercher
	 */
	private void addListenerRecherche(Button button) {
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {			
				actionRechercher(); // lance la recherche
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}

	/**
	 * Méthode permettant de lancer l'action de recherche
	 * en fonction des critères inscrits
	 */
	public void actionRechercher(){
		boolean groupesOK = groupes.size() != 0 ; // booléen de verif contenu groupes
		boolean ligneOK = true;
		boolean aucuneErreurLignes = true;
		for(int i =0; i < groupes.size() && groupesOK; i++){
			groupesOK = groupes.get(i).getSize() != 0;
		}
		for(int i =0; i < groupes.size(); i++){
			for (int j=0; j< groupes.get(i).getGroupe().size(); j++) {
//				ligneOK = groupes.get(i).getGroupe().get(j).getValeur1().isEnabled() 
//						|| (!groupes.get(i).getGroupe().get(j).getValeur1().isEnabled() && groupes.get(i).getGroupe().get(j).getBooleen().isEnabled());
				groupes.get(i).getGroupe().get(j).getWarning().setVisible(!ligneOK);
				if (!ligneOK){
					aucuneErreurLignes = false;
				}
			}
		}
		if (!groupesOK){
			String ttlErreur = "Groupes non remplis";
			String msgErreur = "Au moins un des groupes ne contient pas de critères." +
			"\n Veuillez le(s) remplir ou le(s) supprimer.";
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreur, msgErreur);
		} else if (!aucuneErreurLignes){
			String ttlErreur = "Lignes non complètes";
			String msgErreur = "Vous devez compléter chaque ligne de critère ajoutée afin de valider la recherche.";
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreur, msgErreur);

		} else if (vue.getComposite_Etape1().getCombo().getText().equals("<Choisir>")){
			String ttlErreur = "Résultat non sélectionné";
			String msgErreur = "Vous devez sélectionner le type de résultat que vous souhaitez afficher (Etape 1).";
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), ttlErreur, msgErreur);

		} else { 

			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {	

					larequete = new Requete(vue.getComposite_Etape1().getCombo().getText(),groupes);
					ArrayList<Object> resultatRequete = larequete.getResultat();

					if (resultatRequete.size()==0){
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Aucun résultat trouvé", "Les critères de votre recherche ne correspondent à aucun résultat.");
					}

					if (resultatRequete.get(0) instanceof TaTiers){
						masterController.getMessengerPage().creerFeuilleTiers(resultatRequete);
					} else if (larequete.getResultat().get(0) instanceof TaArticle){
						masterController.getMessengerPage().creerFeuilleArticle(resultatRequete);
					} else {
						masterController.getMessengerPage().creerFeuilleDocument(resultatRequete);
					}
					masterController.getMessengerPage().changeOngletResultats();
				}
			});



		}
	}


	/**
	 * Méthode permettant de sauvegarder les critères de la recherche
	 * Dans la base de données
	 * @param nomPourLaBase le libellé de la requête dans la base
	 * @param enregistre, si vrai on enregistre dans la BD
	 * @return la requete enregistrée en bd
	 * @throws Exception 
	 */
	public TaRequete sauverCriteres(String nomPourLaBase,boolean enregistre) throws Exception {
		TaRequeteDAO daoRequete = new TaRequeteDAO();
		TaRequete nouvelleRequete;
		int id = daoRequete.whatIsMyId(nomPourLaBase);
		
		// Attributs de la requête
		// Utilisés uniquement dans le cas d'un remplacement de requête
		String description;
		int typeres;
		
		// On initialise la transaction avec le DAO
		EntityTransaction transaction = daoRequete.getEntityManager().getTransaction();
		daoRequete.begin(transaction);
		
		// On teste si une requête existe avec ce nom
		if (id==-1){ // La requête n'existe pas
			nouvelleRequete = new TaRequete(nomPourLaBase,vue.getComposite_SauverCharger().getDesc().getText(),
					vue.getComposite_Etape1().getCombo().getSelectionIndex());
		} else {
			// On récupère les infos utiles
			nouvelleRequete = daoRequete.findById(id);
			description = nouvelleRequete.getDescRqt();
			typeres = nouvelleRequete.getTypeResultat();
			daoRequete.remove(nouvelleRequete);	
			nouvelleRequete = new TaRequete(nomPourLaBase,description,
					vue.getComposite_Etape1().getCombo().getSelectionIndex());

		}
		String msgRetour = "";
		boolean poursuite = true;

		int etOuGroupe = 0;
		int numeroGroupe = 0;
		int compteurLigne = 0;

		// +++ Verifications +++
		// On vérifie qu'il existe au moins un groupe
		if (groupes.size() == 0){ 
			poursuite = false;
			msgRetour = "Impossible d'enregistrer une recherche vide.";
		} else if (daoRequete.existThisLib(nomPourLaBase)){
			msgRetour = "Ce nom existe déjà.";
		}

		// On vérifie qu'il n'existe pas une requête avec le même nom

		for(int groupe = 0; groupe < groupes.size() && poursuite; groupe++){ // On parcourt les groupes
			// On réinitialise le compteur de lignes
			compteurLigne = 0;
			// On prépare les infos pour le groupe
			if(groupe>0){
				etOuGroupe = groupes.get(groupe).getAndOrGroup().getSelectionIndex();
			}
			numeroGroupe = groupes.get(groupe).getNumero();
			// On parcourt les lignes
			for (int ligne = 0; ligne < groupes.get(groupe).getGroupe().size() ; ligne++ ){
				Ligne laLigne = groupes.get(groupe).getGroupe().get(ligne);
				// On ajoute la ligne
				TaLigneRequete nouvelleLigne = new TaLigneRequete (nouvelleRequete,
						numeroGroupe,etOuGroupe,compteurLigne,laLigne.getType().getText(),
						laLigne.getAndOr().getSelectionIndex(),
						laLigne.getCritere().getSelectionIndex(),
						laLigne.getComparaison().getSelectionIndex(),
						laLigne.getValeur1().getText(),
						laLigne.getValeur2().getText());
				compteurLigne++; // On incrémente le compteur de ligne
				nouvelleRequete.getTaLigneRequete().add(nouvelleLigne);
				nouvelleLigne.setRqt(nouvelleRequete);
			}	
			msgRetour = "Enregistrement effectué !";
		}
		// On enregistre la requete dans la db
		if(enregistre && poursuite){
			daoRequete.merge(nouvelleRequete);	
			daoRequete.commit(transaction);
		}

		// On affiche le message utilisateur en vert/rouge selon les cas d'erreur
		if(poursuite){
			vue.getComposite_SauverCharger().getInfoSauvegarde().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
			vue.getComposite_SauverCharger().getInfoSauvegarde().setText(msgRetour);
			vue.getComposite_SauverCharger().getInfoCharge().setText("");
		} else {
			vue.getComposite_SauverCharger().getInfoSauvegarde().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
			vue.getComposite_SauverCharger().getInfoSauvegarde().setText(msgRetour);
			vue.getComposite_SauverCharger().getInfoCharge().setText("");
		}

		return nouvelleRequete;
	}


	/**
	 * Méthode permettant de charger les critères de la recherche
	 * Dans l'interface graphique
	 * @param idrequete, l'id de la requete dans la base
	 * @return une chaine de caractères contenant le message d'erreur/validation
	 * correspondant au statut de la sauvegarde
	 */
	public void chargerCriteres(List<TaLigneRequete> list) {
		List<TaLigneRequete> listeLignes = new LinkedList<TaLigneRequete>();
		String msgRetour = "";
		boolean poursuite = true;

		int currentGroupe = -1;

		listeLignes = list;

		// +++ Verifications +++
		// On vérifie qu'il existe au moins un groupe
		if (listeLignes.size() == 0){ 
			poursuite = false;
			msgRetour = "La recherche sélectionnée est invalide.";
		}  else { // On initialise les groupes à zéro
			for(int i=0;i<groupes.size();i++){
				groupes.get(i).getGroupe_composite().dispose();
			}
			groupes = new ArrayList<GroupeLigne>();
			vue.getComposite_Etape2().getComposite().layout();
			for (int i=0;i<vue.getComposite_Etape2().getMenuGr().getItems().length;i++){
				vue.getComposite_Etape2().getMenuGr().getItem(i).dispose();
			}
			selectedGroup = 0;


		}

		for(int ligne = 0; ligne < listeLignes.size() && poursuite; ligne++){ // On parcourt les lignes
			TaLigneRequete laLigne = listeLignes.get(ligne);
			Ligne ligneAffiche;
			if (laLigne.getIdGroupe()!=currentGroupe+1){ // Nouveau groupe
				ajoutGroupe();
				currentGroupe = laLigne.getIdGroupe()-1;
				if (currentGroupe > 0){ // Le premier groupe n'a pas de "andOrGroup"
					groupes.get(currentGroupe).getAndOrGroup().select(laLigne.getAndOrGroupe());
				}
				selectedGroup = currentGroupe; // On sélectionne le groupe courant
			}
			if (laLigne.getTypeLigne().equals("Tiers")){ // Ligne Tiers
				// ajout de la ligne
				ligneAffiche = new LigneTiers(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController);
				scrollAjust(); // ajustement du composite

			} else if (laLigne.getTypeLigne().equals("Article")){// Ligne Article
				ligneAffiche = new LigneArticle(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController);
				scrollAjust();

			} else { // Ligne Document
				ligneAffiche = new LigneDocument(selectedGroup,vue.getComposite_Etape2().getComposite(),vue.getToolkit(),masterController,
						laLigne.getTypeLigne());
				scrollAjust();
			}

			// On met à jour les composants graphiques
			ligneAffiche.getAndOr().select(laLigne.getAndOrLigne());
			ligneAffiche.getCritere().select(laLigne.getCombo1());
			ligneAffiche.getCritere().setEnabled(true);
			ligneAffiche.getComparaison().select(laLigne.getCombo2());
			ligneAffiche.getComparaison().setEnabled(true);
			ligneAffiche.getValeur1().setText(laLigne.getValeur1());
			ligneAffiche.getValeur1().setEnabled(true);
			if (!laLigne.getValeur2().equals("")){
				ligneAffiche.getValeur2().setText(laLigne.getValeur2());
				ligneAffiche.getValeur2().setVisible(true);
			} else {
				ligneAffiche.getValeur2().setVisible(false);
			}

			// Le chargement s'est effectué avec succès, on l'inscrit en tant que message retour
			msgRetour = "Chargement effectué !";
		}
		surbrillance();
		vue.getComposite_Etape2().getComposite().layout();

		// On affiche le message retour en vert ou rouge suivant un cas d'erreur ou non
		if(poursuite){
			vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
			vue.getComposite_SauverCharger().getInfoCharge().setText(msgRetour);
			vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
			TaRequeteDAO daoRequete = new TaRequeteDAO();
			String nomRecherche = vue.getComposite_SauverCharger().getCombo().getText();
			int id  = daoRequete.whatIsMyId(nomRecherche);
			TaRequete laRequete = daoRequete.findById(id);
			vue.getComposite_SauverCharger().getDesc().setText(laRequete.getDescRqt());
			vue.getComposite_Etape1().getCombo().select(laRequete.getTypeResultat());
			vue.getComposite_SauverCharger().getNomSauvegarde().setText(laRequete.getLibRqt());
			if(selectedGroup>0){
				vue.getComposite_Etape2().getItemCombo().setEnabled(true);
			}
		} else {
			vue.getComposite_SauverCharger().getInfoCharge().setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
			vue.getComposite_SauverCharger().getInfoCharge().setText(msgRetour);
			vue.getComposite_SauverCharger().getInfoSauvegarde().setText("");
		}

	}

	public void setSelectedGroup(int selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
}



