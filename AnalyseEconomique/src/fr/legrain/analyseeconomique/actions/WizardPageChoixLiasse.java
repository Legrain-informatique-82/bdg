package fr.legrain.analyseeconomique.actions;

import java.io.File;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.DernierDocument;
import fr.legrain.liasseFiscale.wizards.Document;
import fr.legrain.liasseFiscale.wizards.EnumTypeDoc;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardPageChoixLiasse 
									extends WizardPage
									implements ILgrWizardPage {
	
	static final public String PAGE_NAME = "WizardPageChoixLiasse";
	static final private String PAGE_TITLE = "Choix d'une liasse";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };

    private String liasseSelectionnee = null;// = "kjhkj/2008";

	public WizardPageChoixLiasse(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}
	
	public void createControl(Composite parent) {
		CompositePageChoixLiasse control = new CompositePageChoixLiasse(parent,SWT.NULL);
		
		refreshTree(control);
		
		liasseSelectionnee = new DernierDocument().recupereDernierDoc(((WizardAnalyseEconomique)getWizard()).getModel());
		
		if(liasseSelectionnee!=null)
			selectionArbre(control,liasseSelectionnee);
		
		ImageDescriptor imageDossier = LiasseFiscalePlugin.getImageDescriptor("/icons/newfolder_wiz.png");
		control.getBtnNouveauDossier().setImage(imageDossier.createImage());
		control.getBtnNouveauDossier().setToolTipText(control.getBtnNouveauDossier().getText());
		
		ImageDescriptor imageDocument = LiasseFiscalePlugin.getImageDescriptor("/icons/new_untitled_text_file.gif");
		control.getBtnNouveauDoc().setImage(imageDocument.createImage());
		control.getBtnNouveauDoc().setToolTipText(control.getBtnNouveauDoc().getText());
		
		ImageDescriptor imageSuppr = LiasseFiscalePlugin.getImageDescriptor("/icons/delete_edit.gif");
		control.getBtnSuppr().setImage(imageSuppr.createImage());
		control.getBtnSuppr().setToolTipText(control.getBtnSuppr().getText());
		
		control.getBtnNouveauDossier().setVisible(false);
		control.getBtnNouveauDoc().setVisible(false);
		control.getBtnSuppr().setVisible(false);
		
		control.getTreeDossier().addListener(SWT.Selection, listenerValidate);
		
		control.getTreeDossier().addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if(isPageComplete()){
					if(getWizard().canFinish()) { //ok par ce qu'il n'y a qu'une seule page dans le wizard
						finishPage();
						if(getWizard().performFinish()) {
							getWizard().getContainer().getShell().close();
						}
					}
				}
			}
		});
		
		this.control = control;
		setControl(control);
	}
	
	public void selectionArbre(CompositePageChoixLiasse control, String s) {
		String[] chemin = s.split("/");
		TreeItem[] arbre = control.getTreeDossier().getItems();
		int j = 0;
		for (int i = 0; i < chemin.length; i++) {
			j = 0;
			boolean trouve = false;
			while(j<arbre.length && !trouve) {
				if(arbre[j].getText().equals(chemin[i])) {
					trouve = true;
				}
				j++;
			}
			if(arbre[j-1].getItems().length>0)
				arbre = arbre[j-1].getItems();
		}
		control.getTreeDossier().setSelection(arbre[j-1]);
	}
	
	private void refreshTree(CompositePageChoixLiasse control) {
		control.getTreeDossier().removeAll();
		for (String s : ((WizardAnalyseEconomique)getWizard()).getModel().listeDossier()) {
			TreeItem treeItem = new TreeItem(control.getTreeDossier(),SWT.NULL);
			treeItem.setText(s);
			rempliArbre(((WizardAnalyseEconomique)getWizard()).getModel().getCheminDossiers(),treeItem, treeItem.getText());
		}
	}
	
	/**
	 * Remplissage de l'arbre représentant les dossiers. Affiche uniquement les dossiers et les liasses
	 * @param cheminBase
	 * @param item
	 */
	private void rempliArbre(String cheminBase, TreeItem item, String text) {
		File itemRoot = new File(cheminBase+"/"+text);
		File sousDossier = null;
		List<String> tree = null;
		if(itemRoot.list().length>0) { //il y a des sous dossier à ajouter sous <<item>>
			if ( tree != null ) tree.clear();
			tree  = ((WizardAnalyseEconomique)getWizard()).getModel().listeDossier(itemRoot.getAbsolutePath());
			for (String s : tree) {
				if(estUneLiasse(itemRoot.getAbsolutePath()+"/"+s)){
					TreeItem t = new TreeItem(item,SWT.NULL);
					t.setText(s);
					t.setData(itemRoot.getAbsolutePath()+"/"+s); //stocker le chemin du fichier
				}
				sousDossier = new File(itemRoot.getAbsoluteFile()+"/"+s);
				if(sousDossier.list().length>0) {//Le sous dossier contient lui aussi des sous dossier
					rempliArbre(itemRoot.getAbsolutePath(),item,s);	
				}
			}
		}
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		//OK si la sélection est sur un document et non un dossier "d'organisation" du prog
		TreeItem selection = null;
		
		selection = ((CompositePageChoixLiasse)control).getTreeDossier().getSelection()[0];
		if(!estUneLiasse(selection)){
			return false;
		}
		if(selection.getData()==null) {
			//le chemin absolu du document n'existe pas
			return false;
		}
		
        setErrorMessage(null);
        setMessage(null);
        
        return true;
	}
	
	public boolean estUneLiasse(TreeItem selection) {
		boolean result = false;
		if(selection.getData()!=null) {
			result = estUneLiasse(selection.getData().toString());
		}
		return result;
	}
	
	public boolean estUneLiasse(String selection) {
		boolean result = false;
		Document d = new Document();
		if(new File(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC).exists()) {
			d = d.lectureXML(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC);

			if(d.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
				result = true;
			}
		}
		return result;
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
		}
	}

	public void saveToModel() {
		//Récupération du dossier sélectionné
		TreeItem selection = ((CompositePageChoixLiasse)control).getTreeDossier().getSelection()[0];

		((WizardAnalyseEconomique)getWizard()).getModel().setNomDossier(selection.getText());
		
		((WizardAnalyseEconomique)getWizard()).getModel().setCheminDocument(selection.getData().toString());
	}

}
