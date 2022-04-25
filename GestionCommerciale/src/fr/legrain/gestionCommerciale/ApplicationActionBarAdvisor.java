package fr.legrain.gestionCommerciale;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction introAction;
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction helpAction;
    private IWorkbenchAction preferencesAction;
    //private IContributionItem nouveauAction;
    
    private IContributionItem perspectivesShortList;
    private IContributionItem viewsShortList;
    
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	protected void makeActions(IWorkbenchWindow window) {
		introAction = ActionFactory.INTRO.create(window);
		exitAction = ActionFactory.QUIT.create(window);
		aboutAction = ActionFactory.ABOUT.create(window);
		helpAction = ActionFactory.HELP_CONTENTS.create(window);
		preferencesAction = ActionFactory.PREFERENCES.create(window);
		//nouveauAction = ActionFactory.NEW.create(window);
		
		//register(introAction);
		register(exitAction);
		register(aboutAction);
		register(helpAction);
		register(preferencesAction);
		//register(nouveauAction);
		
		perspectivesShortList = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window);
		viewsShortList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
		//nouveauAction = ContributionItemFactory.NEW_WIZARD_SHORTLIST.create(window);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		//Menus
		MenuManager fileMenu = new MenuManager("&Fichier", IWorkbenchActionConstants.M_FILE);
		MenuManager helpMenu = new MenuManager("&Aide", IWorkbenchActionConstants.M_HELP);
//		MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
		MenuManager windowMenu = new MenuManager("&Affichage", IWorkbenchActionConstants.M_WINDOW);
		MenuManager outilMenu = new MenuManager("&Outils", "outils");
		MenuManager parametreMenu = new MenuManager("&Paramètres", "types");
		
		//Sous menus
		MenuManager perspectivesMenu = new MenuManager("&Perspectives", "perspectives");
		MenuManager viewsMenu = new MenuManager("&Vues", "vues");
		//MenuManager nouveauMenu = new MenuManager("&Nouveau", "Nouveau");

		menuBar.add(fileMenu);
		menuBar.add(new MenuManager("Dossier","Dossier"));
		menuBar.add(new MenuManager("Tiers","tiers"));
		menuBar.add(new MenuManager("Articles","Articles"));
		menuBar.add(new MenuManager("Stocks","Stocks"));
		menuBar.add(parametreMenu);		
//		menuBar.add(new MenuManager("Facture","facture"));
//		menuBar.add(new MenuManager("Devis","devis"));
//		menuBar.add(new MenuManager("Bon de Livraison","Bl"));	
		menuBar.add(new MenuManager("Documents","documents"));
		menuBar.add(new MenuManager("Gestion","gestion"));
		menuBar.add(new MenuManager("Saisie Caisse","SaisieCaisse"));
		menuBar.add(new MenuManager("Exportation","ExportationMenu"));
		menuBar.add(new MenuManager("Boutique","boutique"));
		menuBar.add(new MenuManager("Liasse fiscale","liasse"));
		menuBar.add(new MenuManager("Open Mail","openmail"));
		menuBar.add(new MenuManager("Itinéraire","Google Map"));
		menuBar.add(new MenuManager("Archivage Epicéa","Archivage Epicéa"));
		menuBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//menuBar.add(new MenuManager("Mise à jour","maj"));
		
		menuBar.add(outilMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);
		
		fileMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		//fileMenu.add(nouveauMenu);
		//nouveauMenu.add(nouveauAction);
		fileMenu.add(exitAction);
		
		windowMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		
		outilMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		outilMenu.add(preferencesAction);
//		windowMenu.add(preferencesAction);
		perspectivesMenu.add(perspectivesShortList);
		windowMenu.add(perspectivesMenu);
		viewsMenu.add(viewsShortList);
		windowMenu.add(viewsMenu);

		helpMenu.add(introAction);
		helpMenu.add(new Separator());
		
		helpMenu.add(helpAction);
		helpMenu.add(new Separator());
		helpMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		helpMenu.add(new Separator("update"));
		helpMenu.add(aboutAction);
	}
	
	protected void fillCoolBar(ICoolBarManager coolBar) {
		int style = SWT.FLAT | SWT.RIGHT;
		
        IToolBarManager toolbar = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));  
        
        
        /*
         * Comme pour les menus, il faut créer les Toolbar connues à l'avance par programmation 
         * pour etre sur de leur ordre d'apparition.
         * Chaque Toolbar est ensuite redefinie dans un fichier plugin.xml mais cette etape est facultative si elles ont deja ete creer en java.
         * Cela permet d'etre sur que la toolbar s'affiche même s'il y a un probleme dans le java (mais l'ordre n'apparition ne serait plus garanti)
         * Toute fois, il faut que l'id en java et celui dans le plugin.xml corresponde
         */
        IToolBarManager toolbarTiers = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbarTiers, "toolbar.tiers"));
        
        IToolBarManager toolbararticles = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbararticles, "toolbar.articles")); 
        
        IToolBarManager toolbarFacture = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbarFacture, "toolbar.facture")); 
        
        IToolBarManager toolbarDevis = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbarDevis, "toolbar.devis")); 

        IToolBarManager toolbarSauvegarde = new ToolBarManager(style);
        coolBar.add(new ToolBarContributionItem(toolbarSauvegarde, "toolbar.sauvegarde")); 
    }
	
}
