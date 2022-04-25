package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.VerrouDocument;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.fichier.WizardDialogDocFichier;
import fr.legrain.liasseFiscale.wizards.fichier.WizardDocFichier;

public class WizardPageDocumentFiscal 
									/*extends WizardPage*/
									extends WizardSelectionPage
									implements ILgrWizardPage {
	
	static final public String PAGE_NAME = "WizardPageDocumentFiscal";
	static final private String PAGE_TITLE = "Création d'un nouveau document fiscal";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    private String cheminChoisiComplet;
    
    private final String WIZ_LIASSE_FISCALE = "wizard_liasse_fiscale";
    private final String WIZ_TVA = "wizard_tva";
    private String nextWizard = null;

	public WizardPageDocumentFiscal(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		this.addWizard(WIZ_LIASSE_FISCALE,new WizardLiasseFiscale());
		this.addWizard(WIZ_TVA,new WizardTVA());
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////--WizardSelectionPage--//////////////////////////////////////
	private HashMap<String,IWizardNode> wizards = new HashMap<String,IWizardNode>();
	
	public void addWizard(String name, IWizardNode wizard) {
		wizards.put(name,wizard);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
	
	public void createControl(Composite parent) {
		CompositePageDocumentFiscal control = new CompositePageDocumentFiscal(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageDocumentFiscal controlfinal = control;
		
		refreshTree(control);
		
		ImageDescriptor imageDossier = LiasseFiscalePlugin.getImageDescriptor("/icons/newfolder_wiz.png");
		control.getBtnNouveauDossier().setImage(imageDossier.createImage());
		control.getBtnNouveauDossier().setToolTipText(control.getBtnNouveauDossier().getText());
		
		ImageDescriptor imageDocument = LiasseFiscalePlugin.getImageDescriptor("/icons/new_untitled_text_file.gif");
		control.getBtnNouveauDoc().setImage(imageDocument.createImage());
		control.getBtnNouveauDoc().setToolTipText(control.getBtnNouveauDoc().getText());
		
		ImageDescriptor imageSuppr = LiasseFiscalePlugin.getImageDescriptor("/icons/delete_edit.gif");
		control.getBtnSuppr().setImage(imageSuppr.createImage());
		control.getBtnSuppr().setToolTipText(control.getBtnSuppr().getText());
		//control.getBtnSuppr().setVisible(false);
		
		ImageDescriptor imageVerrou = LiasseFiscalePlugin.getImageDescriptor("/icons/lock.png");
		control.getBtnVerrouiller().setImage(imageVerrou.createImage());
		control.getBtnVerrouiller().setToolTipText(control.getBtnVerrouiller().getText());
		
		control.getTreeDossier().addListener(SWT.Selection, listenerValidate);
		
		control.getBtnNouveauDossier().addSelectionListener(
				new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						try {
						WizardDossier w = new WizardDossier();
						new WizardDialogDossier(shell,w).open();
						refreshTree(controlfinal);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {}
				}
		);
		
		control.getBtnNouveauDoc().addSelectionListener(
				new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						if(controlfinal.getTreeDossier().getSelection().length>0) {
							saveToModel();
							WizardDoc w = new WizardDoc();
							w.setParent((WizardDocumentFiscal)getWizard());
							new WizardDialogDoc(shell,w).open();
							refreshTree(controlfinal);
						} else {
							MessageDialog.openError(getShell(), "Erreur", "Veuillez sélectionner un dossier.");
						}
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {}
				}
		);
		
		control.getBtnSuppr().addSelectionListener(
				new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						File f = new File(cheminChoisiComplet);
						if(MessageDialog.openConfirm(shell,"Attention","Voulez vous vraiment supprimer le répertoire :\n"+f.getName())) {
							deleteDir(f);
							refreshTree(controlfinal);
						}
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {}
				}
		);
		
		control.getBtnVerrouiller().addSelectionListener(
				new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						WizardDocumentFiscalModel.verrouilleDocument(cheminChoisiComplet);
						validatePage();
					}
					
					public void widgetDefaultSelected(SelectionEvent e) {}
				}
				
		);
		
//		control.getBtnImport().setVisible(false);
//		control.getBtnImport().setEnabled(false);
		ImageDescriptor imageImport = LiasseFiscalePlugin.getImageDescriptor("/icons/page_white_go.png");
		control.getBtnImport().setImage(imageImport.createImage());
		control.getBtnImport().setToolTipText(control.getBtnImport().getText());
		control.getBtnImport().addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						//saveToModel();
						try {
						WizardDocFichier w = new WizardDocFichier();
						w.setParent((WizardDocumentFiscal)getWizard());
						new WizardDialogDocFichier(shell,w).open();
						refreshTree(controlfinal);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}

				}
		);
		
		this.control = control;
		setControl(control);
	}
	
	private void refreshTree(CompositePageDocumentFiscal control) {
		control.getTreeDossier().removeAll();
		for (String s : ((WizardDocumentFiscal)getWizard()).getModel().listeDossier()) {
			TreeItem treeItem = new TreeItem(control.getTreeDossier(),SWT.NULL);
			treeItem.setText(s);
			rempliArbre(((WizardDocumentFiscal)getWizard()).getModel().getCheminDossiers(),treeItem);
		}
	}
	
	/**
	 * Remplissage de l'arbre représentant les dossiers
	 * @param cheminBase
	 * @param item
	 */
	private void rempliArbre(String cheminBase, TreeItem item) {
		File itemRoot = new File(cheminBase+"/"+item.getText());
		File sousDossier = null;
		List<String> tree = null;
		if(itemRoot.list().length>0) { //il y a des sous dossier à ajouter sous <<item>>
			if ( tree != null ) tree.clear();
			tree  = ((WizardDocumentFiscal)getWizard()).getModel().listeDossier(itemRoot.getAbsolutePath());
			for (String s : tree) {
				TreeItem t = new TreeItem(item,SWT.NULL);
				t.setText(s);
				sousDossier = new File(itemRoot.getAbsoluteFile()+"/"+s);
				if(sousDossier.list().length>0) //Le sous dossier contient lui aussi des sous dossier
					rempliArbre(itemRoot.getAbsolutePath(),t);					
			}
		}
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		//OK si la sélection est sur un document et non un dossier "d'organisation" du prog
		TreeItem selection = null;
		setSelectedNode(null);
		((CompositePageDocumentFiscal)control).getBtnVerrouiller().setEnabled(false);
		
		selection = ((CompositePageDocumentFiscal)control).getTreeDossier().getSelection()[0];
		if(!estUnDocument(selection)){
			return false;
		} else {
			if(!WizardDocumentFiscalModel.estVerrouille(cheminChoisiComplet)) {
				((CompositePageDocumentFiscal)control).getBtnVerrouiller().setEnabled(true);
			}
		}
		
        setErrorMessage(null);
        setMessage(null);
        
        choixWizard(selection);
        return true;
	}
	
	/**
	 * Selection du wizard suivant en fonction de l'element selectionne.
	 * @param nomDossierDoc
	 * @return - L'ID du wizard suivant
	 */
	public String choixWizard(TreeItem selection) {
		//TODO identification du type de document et choix du wizard en conséquence.
		nextWizard = null;
		Document d = new Document();
		d = d.lectureXML(cheminRepSelection(selection)+"/"+ConstLiasse.C_FICHIER_ID_DOC);
		
		if(d.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
			nextWizard = WIZ_LIASSE_FISCALE;
		} else if(d.getTypeDocument().compareTo(EnumTypeDoc.TVA)==0) {
			nextWizard = WIZ_TVA;
		}
		setSelectedNode(wizards.get(nextWizard));
		return nextWizard;
	}
	
	/**
	 * Initialisation d'une partie d'une partie du modèle du prochain wizard dans le noeud
	 * @param nextWizard - ID du prochain wizard
	 */
	public void initNextWizard(String nextWizard) {
		if(nextWizard.equals(WIZ_LIASSE_FISCALE)) {
			((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().copyProperties(((WizardDocumentFiscal)getWizard()).getModel());
	        ((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setCheminDocument(((WizardDocumentFiscal)getWizard()).getModel().getCheminDocument());
	 
		} else if(nextWizard.equals(WIZ_TVA)) {
			;
		}
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
			initNextWizard(nextWizard);
		}
	}

	public void saveToModel() {
		//Récupération du dossier sélectionné
		TreeItem item;
	//	if(((CompositePageDocumentFiscal)control).getTreeDossier().getSelection().length>0) {
			item = ((CompositePageDocumentFiscal)control).getTreeDossier().getSelection()[0];
			while(item.getParentItem()!=null) {
				item = item.getParentItem();
			}
			((WizardDocumentFiscal)getWizard()).getModel().setNomDossier(item.getText());
		
			((WizardDocumentFiscal)getWizard()).getModel().setCheminDocument(cheminChoisiComplet);
	//	} else {
	//		MessageDialog.openError(getShell(), "Erreur", "Veuillez sélectionner un dossier.")
	//	}
	}
	
	/**
	 * @param item
	 * @return - Le complet par rapport à la racine de l'arbre du dossier selectionné
	 */
	private String cheminRepSelection(TreeItem item) {
		String cheminDossiers = ((WizardDocumentFiscal)getWizard()).getModel().getCheminDossiers();
		ArrayList<String> listeRepParents = new ArrayList<String>();
		String repertoireCourant = item.getText(); //nom du répertoire actuellement sélectionné
		
//		Recuperation des repertoires parents
		while(item.getParentItem()!=null) {
			item = item.getParentItem();
			listeRepParents.add(item.getText());
		}
		
		//construction du chemin
		for (int i = listeRepParents.size()-1; i >=0 ; i--) {
			cheminDossiers+="/"+listeRepParents.get(i);
		}
		
		cheminDossiers+="/"+repertoireCourant;
		 
		return cheminDossiers;
	}
	
	/**
	 * @param item
	 * @return - vrai ssi item représente un répertoire de document fiscal valide
	 */
	private boolean estUnDocument(TreeItem item) {
		String cheminDossiers = null;
		cheminDossiers = cheminRepSelection(item);
		
		//Recherche du fichier identifiant un répertoire "document fiscal" valide
		File f = new File(cheminDossiers);
		File[] listeFichier = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.getName().equals(ConstLiasse.C_FICHIER_ID_DOC))
					return true;
				else
					return false;
			}
		});
		
		if(listeFichier!=null) {
			cheminChoisiComplet = cheminDossiers;
			return (listeFichier.length>0);
		} else
			return false;
	}
	
	
//	/**
//	 * Verrouille le document dont le chemin est passé en paramètre
//	 * @param cheminDocument
//	 */
//	private void verrouilleDocument(String cheminDocument) {
//		VerrouDocument verrou = null;
//		verrou = new VerrouDocument();
//		
//		verrou.sortieXML(cheminDocument+"/"+ConstLiasse.C_FICHIER_VERROU_DOC);
//		
//		validatePage();
//		
//	}
	
//	private boolean estVerrouille(String cheminDocument) {
//		//String cheminDossiers = null;
//		//cheminDossiers = cheminRepSelection(item);
//		
//		//Recherche du fichier identifiant un répertoire "document fiscal" valide
//		File f = new File(cheminDocument);
//		File[] listeFichier = f.listFiles(new FileFilter() {
//			public boolean accept(File pathname) {
//				if(pathname.getName().equals(ConstLiasse.C_FICHIER_VERROU_DOC))
//					return true;
//				else
//					return false;
//			}
//		});
//		
//		if(listeFichier!=null) {
//			cheminChoisiComplet = cheminDocument;
//			return (listeFichier.length>0);
//		} else
//			return false;
//	}
	
	
	/**
	 * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     * @param dir - répertoire à effacer
     * @return - vrai ssi le dossier a été effacer
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }

}
