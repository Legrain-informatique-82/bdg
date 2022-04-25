/**
 * PaCompositeSectionCA.java
 */
package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.Activator;
import fr.legrain.recherchermulticrit.GroupeLigne;
import fr.legrain.recherchermulticrit.Ligne;

/**
 * @author posttest
 *
 */
public class PaCompositeSectionEtape2 {

	// Tableau contenant les différents documents avec leur icône associée
	public static String[][] InfoDocuments = {	{"Facture","/icons/facture.png"},
		{"Devis","/icons/devis.png"},
		{"Commande","/icons/devis.png"},
		{"Avoir","/icons/table.png"},
		{"Apporteur","/icons/creditcards.png"},
		{"Acompte","/icons/money.png"},
		{"Proforma","/icons/devis.png"},
		{"Livraison","/icons/lorry.png"} };


	// Icones pour les différents boutons
	public static String iconAddTiers = "/icons/user.png";
	public static String iconAddArticle = "/icons/package.png";
	public static String iconAddDocument = "/icons/page_white_text.png";
	public static String iconRechercher = "/icons/find.png";
	public static String iconRefresh = "/icons/arrow_refresh_small.png";
	public static String iconAddGroup = "/icons/shape_square_add.png";
	public static String iconDelGroup = "/icons/shape_square_delete.png";
	public static String iconSelGroup = "/icons/shape_group.png";

	// Elements d'affichage basiques
	private Composite compo = null;
	private Label label = null;
	private Label infolabel = null;
	private Button btnRechercher = null;
	private Composite composite = null;
	private ScrolledComposite sc = null;

	// Elements d'affichage liés à la toolbar
	private ToolBar toolBar;
	private Shell shell;
	private ToolItem itemAddGroupe; 
	private ToolItem itemDelGroupe;
	private ToolItem itemTiers ;
	private ToolItem itemArticle ;
	private ToolItem itemDocument;
	private ToolItem itemCombo;
	private Menu menu;
	private Menu menuGr;




	public PaCompositeSectionEtape2(Composite compo,FormToolkit toolkit) {
		// Récupération du composite
		this.compo = compo;

		// Création du shell pour la toolbar
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		// Création de la toolbar
		toolBar = new ToolBar(compo, SWT.NONE );
		toolBar.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		// -- Combo de gestion des groupes --
		itemCombo = new ToolItem(toolBar, SWT.PUSH);
		itemCombo.setText("Groupe Actif : 1");
		itemCombo.setToolTipText("Le Groupe actif est le groupe dans lequel les critères seront ajoutés");
		itemCombo.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconSelGroup).createImage());
		itemCombo.setEnabled(false);

		// -- Bouton d'ajout de groupe --
		itemAddGroupe = new ToolItem(toolBar, SWT.PUSH);
		itemAddGroupe.setText("Créer Nouveau Groupe");
		itemAddGroupe.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconAddGroup).createImage());

		// -- Bouton de suppression de groupe --
		itemDelGroupe = new ToolItem(toolBar, SWT.PUSH);
		itemDelGroupe.setText("Supprimer Dernier Groupe");
		itemDelGroupe.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconDelGroup).createImage());

		// -- Bouton d'ajout de tiers --
		itemTiers = new ToolItem(toolBar, SWT.PUSH);
		itemTiers.setText("Ajouter Critère Tiers");
		itemTiers.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconAddTiers).createImage());

		// -- Bouton d'ajout d'article --
		itemArticle = new ToolItem(toolBar, SWT.PUSH);
		itemArticle.setText("Ajouter Critère Articles");
		itemArticle.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconAddArticle).createImage());

		// -- Bouton d'ajout Document --
		itemDocument = new ToolItem(toolBar, SWT.PUSH);
		itemDocument.setText("Ajouter Critère Document");
		itemDocument.setToolTipText("Cliquez pour choisir votre document");
		itemDocument.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconAddDocument).createImage());

		// -- Menu contenant la liste des différents documents --
		menu = new Menu(shell, SWT.POP_UP);
		// -- On parcours InfoDocuments pour obtenir le libellé du document ainsi que l'icone --
		for (int i = 0; i < InfoDocuments.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(InfoDocuments[i][0]);
			item.setImage(Activator.getImageDescriptor(InfoDocuments[i][1]).createImage());
		}

		// -- Menu contenant la liste des différents groupes --
		menuGr = new Menu(shell, SWT.POP_UP);


		// -- Composite qui accueille les critères --
		sc = new ScrolledComposite(compo,SWT.BORDER | SWT.V_SCROLL);

		GridData ld = new GridData();
		ld.horizontalAlignment = GridData.FILL;
		ld.grabExcessHorizontalSpace = true;
		ld.verticalAlignment = GridData.FILL;
		ld.grabExcessVerticalSpace = true;
		ld.horizontalSpan = 3;
		sc.setLayoutData(ld);
		sc.setExpandVertical(true);
		sc.setExpandHorizontal(true);
		composite = toolkit.createComposite(sc, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		sc.setContent(composite);


		// Layout
		GridLayout layout_Etape2 = new GridLayout();
		layout_Etape2.marginHeight = 10;
		layout_Etape2.marginWidth = 20;
		layout_Etape2.numColumns = 3;
		compo.setLayout(layout_Etape2);


		// -- Bouton de recherche --
		btnRechercher = toolkit.createButton(compo, "Lancer la recherche", SWT.NONE);
		btnRechercher.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRechercher.setImage(Activator.getImageDescriptor(PaCompositeSectionEtape2.iconRechercher).createImage());


	}

	public Composite getCompo() {
		return compo;
	}

	public Label getText() {
		return label;
	}

	public Label getInfolabel() {
		return infolabel;
	}

	public Label getLabel() {
		return label;
	}


	public Button getBtnRechercher() {
		return btnRechercher;
	}

	public Composite getComposite() {
		return composite;
	}

	public static String[][] getInfoDocuments() {
		return InfoDocuments;
	}

	public ToolItem getItemTiers() {
		return itemTiers;
	}

	public ToolItem getItemArticle() {
		return itemArticle;
	}

	public ToolItem getItemDocument() {
		return itemDocument;
	}

	public Menu getMenu() {
		return menu;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public Shell getShell() {
		return shell;
	}

	public ToolItem getItemAddGroupe() {
		return itemAddGroupe;
	}

	public ToolItem getItemCombo() {
		return itemCombo;
	}

	public Menu getMenuGr() {
		return menuGr;
	}


	public ToolItem getItemDelGroupe() {
		return itemDelGroupe;
	}

	public ScrolledComposite getSc() {
		return sc;
	}

}
