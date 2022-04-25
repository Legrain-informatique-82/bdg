/**
 * Ligne.java
 */
package fr.legrain.recherchermulticrit;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.controllers.FormPageController;

/**
 * Classe Ligne correspondant à une ligne de critère pour formulaire de recherche
 * @author nicolas²
 *
 */
public class Ligne {

	private Ligne cetteLigne = this;

	// Icones
	public static String iconDelete = "/icons/bin_empty.png";
	public static String iconUp = "/icons/arrow_up.png";
	public static String iconDown = "/icons/arrow_down.png";
	public static String iconWarning = "/icons/error.png";

	protected Composite composite;
	protected Composite parent;
	protected GroupeLigne groupe;
	private FormToolkit toolkit;

	// Grid Data appliqué à tous les composants
	protected static final GridData gridDataBasique = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);

	FormPageController masterController;

	protected Button type = null;
	protected Button delete = null;
	protected Button up = null;
	protected Button down = null;
	protected Combo andOr = null;
	protected Combo critere = null;
	protected Combo comparaison = null;
	protected Combo booleen = null;
	protected Text valeur1 = null;
	protected Text valeur2 = null;
	protected Label warning = null;
	
	public static final String BOOLEAN_TRUE_TEXT = "Oui";
	public static final String BOOLEAN_TRUE_VALUE = "1";
	public static final String BOOLEAN_FALSE_TEXT = "Non";
	public static final String BOOLEAN_FALSE_VALUE = "0";
	public static final String BOOLEAN_LIBELLE_CRITERE = "Oui/Non";

	public Ligne(int numGroupe,Composite parent, FormToolkit toolkit,FormPageController masterController) {
		this.parent = parent;
		this.masterController = masterController;
		this.groupe = masterController.getEtape2Controller().getGroupes().get(numGroupe);
		this.toolkit = toolkit;
		groupe.addLigne(this);
		composite = toolkit.createComposite(groupe.getGroupe_composite());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		initComposants();
		initListeners();
		groupe.layout();


	}

	protected void initComposants() {		

		// Initialisation du warning indiquant l'erreur sur la ligne
		warning = new Label(composite,SWT.NONE);
		warning.setImage(Activator.getImageDescriptor(iconWarning).createImage());
		warning.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		warning.setVisible(false);
		
		// Initialisation du bouton pour monter le critère
		up = new Button(composite,SWT.NONE);
		up.setImage(Activator.getImageDescriptor(iconUp).createImage());
		up.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// Initialisation du bouton pour descendre le critère
		down = new Button(composite,SWT.NONE);
		down.setImage(Activator.getImageDescriptor(iconDown).createImage());
		down.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// Initialisation du bouton de suppression du critère
		delete = new Button(composite,SWT.NONE);
		delete.setImage(Activator.getImageDescriptor(iconDelete).createImage());
		delete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));


		// Ce n'est pas la première ligne
		// Initialisation du combo et/ou
		andOr = new Combo(composite,SWT.READ_ONLY);
		andOr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));;
		andOr.add("ET");
		andOr.add("OU");
		andOr.select(0);
		if ( groupe.getSize() == 1 ){ 
			andOr.setVisible(false);
		}

		// Initialisation du Type de critère
		type = new Button (composite,SWT.NONE);
		type.setEnabled(false);
		type.setLayoutData(gridDataBasique);

		// Initialisation du combo sur les différents critères
		critere = new Combo(composite,SWT.READ_ONLY);
		critere.setLayoutData(gridDataBasique);

		// Initialisation du combo sur les options de comparaison
		comparaison = new Combo(composite,SWT.READ_ONLY);
		comparaison.setLayoutData(gridDataBasique);
		comparaison.setEnabled(false);
		
		// Initialisation des champs à remplir
		valeur1 = new Text(composite,SWT.BORDER);
		valeur1.setLayoutData(gridDataBasique);
		valeur1.setEnabled(false);
		
		// Initialisation du combo pour les booleen
		booleen = new Combo(composite,SWT.READ_ONLY);
		booleen.setLayoutData(gridDataBasique);
		booleen.setItems(new String[] {BOOLEAN_TRUE_TEXT,BOOLEAN_FALSE_TEXT});
		booleen.setEnabled(false);

		valeur2 = new Text(composite,SWT.BORDER);
		valeur2.setLayoutData(gridDataBasique);
		valeur2.setVisible(false);

		// Initialisation du Layout		
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 10;
		composite.setLayout(layout);
		composite.layout();
	}
	
	/**
	 * Méthode permettant l'initialisation des listeners sur les boutons spécifiques
	 */
	protected void initListeners(){

		// -- LISTENER SUR LE BOUTON DOWN --
		down.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// On récupère la position de la ligne
				int numeroLigne = groupe.lastPos(cetteLigne);
				if (numeroLigne != groupe.getSize()-1){ 		// La ligne n'est pas en dernière position
					groupe.downLigne(cetteLigne);				// On descend la ligne dans le groupe
					numeroLigne = groupe.lastPos(cetteLigne);	// On récupère le nouveau numero de ligne
					composite.moveBelow(groupe.getGroupe().get(numeroLigne-1).getComposite()); // On descend la ligne à l'écran
					// On supprime le "et/ou" en cas de première ligne
					if (numeroLigne == 1){
						andOr.setVisible(true);
						groupe.getGroupe().get(numeroLigne-1).getAndOr().setVisible(false);
					}
					groupe.layout(); // On rafraichit le parent
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// -- LISTENER SUR LE BOUTON UP --
		up.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// On récupère la position de la ligne
				int numeroLigne = groupe.lastPos(cetteLigne);
				if (numeroLigne != 0){ 							// La ligne n'est pas en première position
					groupe.upLigne(cetteLigne);					// On monte la ligne dans le groupe
					numeroLigne = groupe.lastPos(cetteLigne); 	// On met à jour le numero de ligne
					composite.moveAbove(groupe.getGroupe().get(numeroLigne+1).getComposite()); // On met à jour la ligne à l'écran
					// On supprime le "et/ou" en cas de première ligne
					if (numeroLigne == 0){
						andOr.setVisible(false);
						groupe.getGroupe().get(numeroLigne+1).getAndOr().setVisible(true);
					}
					groupe.layout(); // On rafraichit le parent
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Listener sur le bouton de suppression
		delete.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// On récupère le numero de la ligne
				int numeroLigne=groupe.lastPos(cetteLigne);
				groupe.remove(numeroLigne); // On supprime la ligne du groupe
				composite.dispose();		// On supprime la ligne à l'écran
				groupe.getGroupe().get(0).getAndOr().setVisible(false);
				groupe.layout();			// On rafraichit
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		// Listener sur le premier combo
		critere.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (critere.getSelectionIndex()!=0){
					comparaison.select(0);
					comparaison.setEnabled(true);
					valeur1.setEnabled(false);
					valeur2.setVisible(false);
					booleen.setVisible(false);
				} else {
					valeur1.setText("");
					valeur2.setText("");
					valeur1.setEnabled(false);
					valeur2.setVisible(false);
					comparaison.select(0);
					comparaison.setEnabled(false);
					booleen.setVisible(false);
					booleen.setEnabled(false);
				}
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		// Listener sur le second combo
		comparaison.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( comparaison.getSelectionIndex()!=0){
					valeur1.setVisible(true);
					valeur1.setEnabled(true);
					warning.setVisible(false);
					if(comparaison.getText().equals("est compris entre")){
						valeur2.setVisible(true);
					} else if(comparaison.getText().equals("est vide")){
						valeur1.setEnabled(false);
						valeur1.setVisible(false);
						valeur2.setEnabled(false);
						valeur2.setVisible(false);
					} else if(comparaison.getText().equals(BOOLEAN_LIBELLE_CRITERE)){
						valeur1.setEnabled(false);
						valeur1.setVisible(false);
						booleen.setVisible(true);
						booleen.setEnabled(true);
					} else {
						valeur2.setText("");
						valeur2.setVisible(false);
						booleen.setVisible(false);
					}
				} else {
					valeur1.setText("");
					valeur2.setText("");
					valeur1.setEnabled(false);
					valeur2.setVisible(false);
					
				}
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});


	}
	
	
	public String transformSQLandOr(String champ) {
		return champ.equals("ET")?"AND":"OR";
	}

	public Composite getComposite() {
		return composite;
	}
	
	public Combo getAndOr() {
		return andOr;
	}

	public FormToolkit getToolkit() {
		return toolkit;
	}

	public Button getType() {
		return type;
	}

	public Button getDelete() {
		return delete;
	}

	public Combo getCritere() {
		return critere;
	}

	public Combo getComparaison() {
		return comparaison;
	}

	public Text getValeur1() {
		return valeur1;
	}

	public Text getValeur2() {
		return valeur2;
	}

	public Label getWarning() {
		return warning;
	}

	public Combo getBooleen() {
		return booleen;
	}




}
