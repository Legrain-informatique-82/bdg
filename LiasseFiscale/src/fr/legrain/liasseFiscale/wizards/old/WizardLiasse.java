package fr.legrain.liasseFiscale.wizards.old;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.Wizard;

import fr.legrain.gestionCommerciale.ApplicationWorkbenchWindowAdvisor;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.nature.LiasseFiscaleNature;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
import fr.legrain.liasseFiscale.wizards.WizardLiasseModel;

public class WizardLiasse extends Wizard {
	
	static Logger logger = Logger.getLogger(WizardLiasse.class.getName());
	
	private WizardLiasseModel model = new WizardLiasseModel(); 
	
	private WizardPageDebut wizardPageDebut = new WizardPageDebut(WizardPageDebut.PAGE_NAME);
	private WizardPageAjoutLiasse wizardPageAjoutLiasse = new WizardPageAjoutLiasse(WizardPageAjoutLiasse.PAGE_NAME);
	private WizardPageNouveauDossier wizardPageNouveauDossier = new WizardPageNouveauDossier(WizardPageNouveauDossier.PAGE_NAME);
	private WizardPageRepriseLiasse wizardPageRepriseLiasse = new WizardPageRepriseLiasse(WizardPageRepriseLiasse.PAGE_NAME);
	private WizardPageCinq wizardPageCinq = new WizardPageCinq(WizardPageCinq.PAGE_NAME);
	//private WizardPageCreationDossier wizardPageSix = new WizardPageCreationDossier(WizardPageCreationDossier.PAGE_NAME);
	//private WizardPageDocumentFiscal wizardPageSept = new WizardPageDocumentFiscal(WizardPageDocumentFiscal.PAGE_NAME);
	
	public WizardLiasse() {
		super();
		setWindowTitle("Assistant - Liasse fiscale");
	
		addPage(wizardPageDebut);
		//Etape 1 - Création / choix de la liasse + fichier XML de base
		addPage(wizardPageAjoutLiasse);
		addPage(wizardPageNouveauDossier);
		addPage(wizardPageRepriseLiasse);
		//Etape 2 - Enrichissement du fichier XML
		addPage(wizardPageCinq);
		//Etape 3 - Répartition + fichier XML final
		    //addPage(wizardPageSix);
		//Etape 4 - Génaration du document final
		    //addPage(wizardPageSept);
		
		//setNeedsProgressMonitor(true);
	}
	
	public boolean canFinish() {
		if (this.getContainer().getCurrentPage() == wizardPageDebut
				|| this.getContainer().getCurrentPage() == wizardPageAjoutLiasse
				|| this.getContainer().getCurrentPage() == wizardPageNouveauDossier
				|| this.getContainer().getCurrentPage() == wizardPageRepriseLiasse) { 
			return false;
		}
		return true;
	}
	
	protected void backPressed(ILgrWizardPage page) {
	}
	
	protected void nextPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void etape1() {
		File dossier = new File(ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET+"/"+model.getNomDossier()+"/"+String.valueOf(model.getAnneeFiscale()));
		//File liasse = new File(ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET+"/"+model.getNomDossier()/*+"/"+String.valueOf(model.getAnneeFiscale())*/);

//ajout annee fiscale		
//		File dossier = new File(ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET+"/"+model.getNomDossier()+"/"+String.valueOf(model.getAnneeDocument()));
//		//File liasse = new File(ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET+"/"+model.getNomDossier()/*+"/"+String.valueOf(model.getAnneeLiasse())*/);
		
		dossier.mkdirs();
		File fichierCompta = new File(model.getCheminFichierCompta());
		if(fichierCompta.exists()) {
			//copier le fichier dans le répertoire de la liasse
		}
	}
	
	public void etape2() {
		
	}
	
	public void etape3() {
		
	}
	
	public void etape4() {
		
	}
	
	public WizardLiasseModel getModel() {
		return model;
	}

	public void testNature() {
		ApplicationWorkbenchWindowAdvisor.setTitre(ApplicationWorkbenchWindowAdvisor.getTitre()+"- test");
		String n = "fr.legrain.bdgNature";
		//n = "fr.legrain.liasseFiscale.nature.LiasseFiscaleNature";
		//n = "LiasseFiscale.fr.legrain.liasseFiscale.nature.LiasseFiscaleNature";
		n = LiasseFiscaleNature.ID;
		try {
			UtilWorkspace uw = new UtilWorkspace();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProjectNatureDescriptor[] descriptors =  workspace.getNatureDescriptors();
			for(int i=0;i<descriptors.length;i++) {
				System.out.println(i+" : "+descriptors[i].getNatureId());
			}
			
			
			
			IProject[] projets = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (int i = 0; i < projets.length; i++) {
				if(projets[i].isOpen()) {
					if(projets[i].isNatureEnabled(n)) {
						System.out.println(n);
					}
					System.out.println(projets[i].getName()+" open");
				} else {
					System.out.println(projets[i].getName()+" close");
				}
			}
			
			IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject("handleTest");
			IPath path = new Path("c:/test");
			String nomDuProjet = "handleTest";
			
			if(projectHandle.exists()) {
				projectHandle.delete(true,true,null);
			}
			
			if(!projectHandle.exists())
				uw.createProject(projectHandle,path,nomDuProjet);
			
			projectHandle.open(null);
			
			try {
				//projectHandle.refreshLocal(projectHandle.DEPTH_INFINITE,null);
				IProjectDescription description = projectHandle.getDescription();
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = n;
				description.setNatureIds(newNatures);
				projectHandle.setDescription(description, null);
			} catch (CoreException e) {
				// Something went wrong
				e.printStackTrace();
				System.out.println("0 : "+e.getStatus().getMessage());
				for( int i=0; i<e.getStatus().getChildren().length; i++) {
					System.out.println(i+" : "+e.getStatus().getChildren()[i].getMessage());
				}
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
		
}
