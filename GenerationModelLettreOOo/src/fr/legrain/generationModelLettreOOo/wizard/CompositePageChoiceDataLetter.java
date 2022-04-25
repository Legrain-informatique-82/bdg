package fr.legrain.generationModelLettreOOo.wizard;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import com.cloudgarden.resource.SWTResourceManager;



/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class CompositePageChoiceDataLetter extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Group grpImport;
	private Group grpImportMotCle;
	private Group grpSavePublipostage;
	private Group grpInfosDossier;
	private Group grpOptionPrintAndShow;
	
	private Text tfPathFileExtraction;
	private Text tfPathFileMotCle;
	private Text tfPathSavePublipostage;
	private Button btnPathFileExtraction;
	private Button btnPathFileMotCle;
	private Button btnPathSavePublipostage;
	private Label laAgence;
	private Combo cbAgence;
	private Button btShowOO;
	private Button btOptionPrint;
	private Button buttonCheckBoxMotCle;
	private Button buttonCheckBoxPublipostage;
	private Group grpServeur;
	private Button cbFTP;
	private Button cbSite;
	private Label laAnneeFiscale;
	private Button cbEcraser;
	private Button cbVerrouLiasse;
	private Label laRegime;
	private Button raRegimeBIC;
	private Button raRegimeAgri;
	private Text tfDossier;
	private Label laDossier;
	private Text tfAnneeFiscale;
	
	/*********** new **********/
	private Group grpOptionPublipostage;
	private Label labelChoixParamPublipostage;
	private CCombo comboChoixParamPublipostage;
	private Button btDeleteParamPublipostage;
	
	private Group grpFileSeparateur;
	private Label labelChoixFileSeparateur;
	private CCombo comboChoixFileSeparateur;
	private Button btOptionFileSeparateur;
	private Label labelSeparateur;
	private Text textSeparatuer;
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		CompositePageChoiceDataLetter inst = new CompositePageChoiceDataLetter(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public CompositePageChoiceDataLetter(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			{
				grpOptionPublipostage = new Group(this, SWT.NONE);
				GridLayout grpOptionPublipostageLayout = new GridLayout();
				grpOptionPublipostageLayout.numColumns = 4;
				grpOptionPublipostage.setLayout(grpOptionPublipostageLayout);
				grpOptionPublipostage.setText("Paramètres déjà utilisés");
				GridData grpOptionPublipostageData = new GridData();
				grpOptionPublipostageData.horizontalAlignment = GridData.FILL;
				grpOptionPublipostageData.grabExcessHorizontalSpace = true;
				grpOptionPublipostageData.horizontalSpan = 4;
				grpOptionPublipostage.setLayoutData(grpOptionPublipostageData);
				grpOptionPublipostage.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelChoixParamPublipostage = new Label(grpOptionPublipostage, SWT.NONE);
					labelChoixParamPublipostage.setText("Choix de paramètre de publipostage :");
					GridData labelChoixParamPublipostageData = new GridData();
					labelChoixParamPublipostageData.horizontalAlignment = GridData.FILL;
					labelChoixParamPublipostageData.horizontalSpan = 2;
					labelChoixParamPublipostage.setLayoutData(labelChoixParamPublipostageData);
				}
				{
					comboChoixParamPublipostage = new CCombo(grpOptionPublipostage, SWT.BORDER);
					GridData comboChoixParamPublipostageData = new GridData();
					comboChoixParamPublipostageData.widthHint = 171;
					comboChoixParamPublipostageData.horizontalSpan = 2;
					comboChoixParamPublipostageData.verticalAlignment = GridData.FILL;
					comboChoixParamPublipostage.setLayoutData(comboChoixParamPublipostageData);
					comboChoixParamPublipostage.setEnabled(false);
					comboChoixParamPublipostage.setEditable(false);
				}
				{
					btDeleteParamPublipostage = new Button(grpOptionPublipostage, SWT.PUSH | SWT.CENTER);
					btDeleteParamPublipostage.setText("Supprimer");
					GridData btDeleteParamPublipostageData = new GridData();
					btDeleteParamPublipostageData.horizontalSpan = 2;
					btDeleteParamPublipostageData.grabExcessHorizontalSpace = true;
					btDeleteParamPublipostage.setLayoutData(btDeleteParamPublipostageData);
				}
			}
			{
				grpImport = new Group(this, SWT.NONE);
				GridLayout grpImportLayout = new GridLayout();
				grpImportLayout.numColumns = 2;
				grpImport.setLayout(grpImportLayout);
				GridData grpImportLData = new GridData();
				grpImportLData.horizontalAlignment = GridData.FILL;
				grpImportLData.grabExcessHorizontalSpace = true;
				grpImport.setLayoutData(grpImportLData);
				grpImport.setText("Fichier de données");
				grpImport.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					tfPathFileExtraction = new Text(grpImport, SWT.BORDER);
					GridData tfCheminComptaLData = new GridData();
					tfCheminComptaLData.horizontalAlignment = GridData.FILL;
					tfCheminComptaLData.grabExcessHorizontalSpace = true;
					tfPathFileExtraction.setLayoutData(tfCheminComptaLData);
				}
				{
					btnPathFileExtraction = new Button(grpImport, SWT.PUSH | SWT.CENTER);
					GridData btnCheminComptaLData = new GridData();
					btnCheminComptaLData.horizontalAlignment = GridData.END;
					btnPathFileExtraction.setLayoutData(btnCheminComptaLData);
					btnPathFileExtraction.setText("Parcourrir");
				}
			}
			{
				grpImportMotCle = new Group(this, SWT.NONE);
				GridLayout grpImportLayout = new GridLayout();
				grpImportLayout.numColumns = 2;
				grpImportMotCle.setLayout(grpImportLayout);
				GridData grpImportLData = new GridData();
				grpImportLData.horizontalAlignment = GridData.FILL;
				grpImportLData.grabExcessHorizontalSpace = true;
				grpImportMotCle.setLayoutData(grpImportLData);
				grpImportMotCle.setText("Fichier de mots clés");
				grpImportMotCle.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					tfPathFileMotCle = new Text(grpImportMotCle, SWT.BORDER);
					GridData tfCheminComptaLData = new GridData();
					tfCheminComptaLData.horizontalAlignment = GridData.FILL;
					tfCheminComptaLData.grabExcessHorizontalSpace = true;
					tfPathFileMotCle.setLayoutData(tfCheminComptaLData);
				}
				{
					btnPathFileMotCle = new Button(grpImportMotCle, SWT.PUSH | SWT.CENTER);
					GridData btnCheminComptaLData = new GridData();
					btnCheminComptaLData.horizontalAlignment = GridData.END;
					btnPathFileMotCle.setLayoutData(btnCheminComptaLData);
					btnPathFileMotCle.setText("Parcourrir");
					btnPathFileMotCle.setEnabled(false);
				}
				{
					buttonCheckBoxMotCle = new Button(grpImportMotCle, SWT.CHECK | SWT.LEFT);
					buttonCheckBoxMotCle.setText("Utiliser un fichier de mots clés personnalisé");
					GridData buttonCheckBoxMotCleLData = new GridData();
					buttonCheckBoxMotCle.setLayoutData(buttonCheckBoxMotCleLData);
				}
			}
//			{
//				grpOptionPrintAndShow = new Group(this, SWT.NONE);
//				GridLayout grpOptionPrintLayout = new GridLayout();
//				grpOptionPrintLayout.numColumns = 2;
//				grpOptionPrintAndShow.setLayout(grpOptionPrintLayout);
//				GridData grpImportLData = new GridData();
//				grpImportLData.horizontalAlignment = GridData.FILL;
//				grpImportLData.grabExcessHorizontalSpace = true;
//				grpOptionPrintAndShow.setLayoutData(grpImportLData);
//				grpOptionPrintAndShow.setText("Options d'impression et d'affichage");
//				{
//					btOptionPrint = new Button(grpOptionPrintAndShow, SWT.CHECK | SWT.LEFT);
//					btOptionPrint.setText("Imprimer dirctement");
//					GridData btOptionPrintLData = new GridData();
//					btOptionPrint.setLayoutData(btOptionPrintLData);
//				}
//				{
//					btShowOO = new Button(grpOptionPrintAndShow, SWT.CHECK | SWT.LEFT);
//					btShowOO.setText("Afficher le publipostage");
//					GridData btShowOOLData = new GridData();
//					btShowOO.setLayoutData(btShowOOLData);
//				}
//			}
			{
				grpSavePublipostage = new Group(this, SWT.NONE);
				GridLayout grpSavePublipostageLayout = new GridLayout();
				grpSavePublipostageLayout.numColumns = 2;
				grpSavePublipostage.setLayout(grpSavePublipostageLayout);
				GridData grpSavePublipostageData = new GridData();
				grpSavePublipostageData.horizontalAlignment = GridData.FILL;
				grpSavePublipostageData.grabExcessHorizontalSpace = true;
				grpSavePublipostage.setLayoutData(grpSavePublipostageData);
				grpSavePublipostage.setText("Emplacement du document final (fusioné)");
				grpSavePublipostage.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					tfPathSavePublipostage = new Text(grpSavePublipostage, SWT.BORDER);
					GridData tfPathSavePublipostageData = new GridData();
					tfPathSavePublipostageData.horizontalAlignment = GridData.FILL;
					tfPathSavePublipostageData.grabExcessHorizontalSpace = true;
					tfPathSavePublipostage.setLayoutData(tfPathSavePublipostageData);
				}
				{
					btnPathSavePublipostage = new Button(grpSavePublipostage, SWT.PUSH | SWT.CENTER);
					GridData PathSavePublipostage = new GridData();
					PathSavePublipostage.horizontalAlignment = GridData.END;
					btnPathSavePublipostage.setLayoutData(PathSavePublipostage);
					btnPathSavePublipostage.setText("Parcourrir");
					btnPathSavePublipostage.setEnabled(false);
				}
				{
					buttonCheckBoxPublipostage = new Button(grpSavePublipostage, SWT.CHECK | SWT.LEFT);
					buttonCheckBoxPublipostage.setText("Spécifier l'emplacement du document final");
					GridData buttonCheckBoxPublipostageData = new GridData();
					buttonCheckBoxMotCle.setLayoutData(buttonCheckBoxPublipostageData);
				}
			}
			{
//				grpInfosDossier = new Group(this, SWT.NONE);
//				GridLayout grpInfosDossierLayout = new GridLayout();
//				grpInfosDossierLayout.numColumns = 3;
//				grpInfosDossier.setLayout(grpInfosDossierLayout);
//				GridData grpInfosDossierLData = new GridData();
//				grpInfosDossierLData.horizontalAlignment = GridData.FILL;
//				grpInfosDossierLData.grabExcessHorizontalSpace = true;
//				grpInfosDossierLData.verticalAlignment = GridData.FILL;
//				grpInfosDossierLData.grabExcessVerticalSpace = true;
//				grpInfosDossier.setLayoutData(grpInfosDossierLData);
//				grpInfosDossier.setText("Dossier / Liasse");
//				{
//					laDossier = new Label(grpInfosDossier, SWT.NONE);
//					laDossier.setText("Dossier");
//				}
//				{
//					tfDossier = new Text(grpInfosDossier, SWT.BORDER);
//					GridData tfDossierLData = new GridData();
//					tfDossierLData.horizontalSpan = 2;
//					tfDossierLData.grabExcessHorizontalSpace = true;
//					tfDossierLData.heightHint = 13;
//					tfDossierLData.widthHint = 102;
//					tfDossier.setLayoutData(tfDossierLData);
//				}
//				{
//					laAnneeFiscale = new Label(grpInfosDossier, SWT.NONE);
//					laAnneeFiscale.setText("Annee Fiscale");
//				}
//				{
//					tfAnneeFiscale = new Text(grpInfosDossier, SWT.BORDER);
//					GridData tfAnneeFiscaleLData = new GridData();
//					tfAnneeFiscaleLData.horizontalSpan = 2;
//					tfAnneeFiscaleLData.grabExcessHorizontalSpace = true;
//					tfAnneeFiscaleLData.widthHint = 102;
//					tfAnneeFiscaleLData.heightHint = 13;
//					tfAnneeFiscale.setLayoutData(tfAnneeFiscaleLData);
//				}
//				{
//					laRegime = new Label(grpInfosDossier, SWT.NONE);
//					laRegime.setText("Regime : ");
//				}
//				{
//					raRegimeAgri = new Button(grpInfosDossier, SWT.RADIO | SWT.LEFT);
//					raRegimeAgri.setText("Agricole");
//				}
//				{
//					raRegimeBIC = new Button(grpInfosDossier, SWT.RADIO | SWT.LEFT);
//					raRegimeBIC.setText("BIC");
//				}
//				{
//					cbVerrouLiasse = new Button(grpInfosDossier, SWT.CHECK | SWT.LEFT);
//					GridData cbVerrouLiasseLData = new GridData();
//					cbVerrouLiasseLData.horizontalSpan = 3;
//					cbVerrouLiasse.setLayoutData(cbVerrouLiasseLData);
//					cbVerrouLiasse.setText("Liasse verrouille");
//				}
//				{
//					cbEcraser = new Button(grpInfosDossier, SWT.CHECK | SWT.LEFT);
//					GridData cbEcraserLData = new GridData();
//					cbEcraserLData.horizontalSpan = 3;
//					cbEcraser.setLayoutData(cbEcraserLData);
//					cbEcraser.setText("Ecraser les données précédentes");
//				}
			}
			{
//				grpServeur = new Group(this, SWT.NONE);
//				GridLayout grpServeurLayout = new GridLayout();
//				grpServeurLayout.numColumns = 2;
//				grpServeur.setLayout(grpServeurLayout);
//				GridData grpServeurLData = new GridData();
//				grpServeurLData.horizontalAlignment = GridData.FILL;
//				grpServeurLData.grabExcessHorizontalSpace = true;
//				grpServeur.setLayoutData(grpServeurLData);
//				grpServeur.setText("Serveur");
//				{
//					cbSite = new Button(grpServeur, SWT.CHECK | SWT.LEFT);
//					GridData cbSiteLData = new GridData();
//					cbSiteLData.horizontalSpan = 2;
//					cbSite.setLayoutData(cbSiteLData);
//					cbSite.setText("Ouverture automatique du site");
//				}
//				{
//					cbFTP = new Button(grpServeur, SWT.CHECK | SWT.LEFT);
//					GridData cbFTPLData = new GridData();
//					cbFTPLData.horizontalSpan = 2;
//					cbFTP.setLayoutData(cbFTPLData);
//					cbFTP.setText("Transfert automatique sur le serveur");
//				}
//				{
//					laAgence = new Label(grpServeur, SWT.NONE);
//					laAgence.setText("Agence");
//				}
//				{
//					cbAgence = new Combo(grpServeur, SWT.READ_ONLY);
//				}
			}
			{
				grpFileSeparateur = new Group(this, SWT.NONE);
				GridLayout grpFileSeparateurLayout = new GridLayout();
				grpFileSeparateurLayout.numColumns = 4;
				grpFileSeparateur.setLayout(grpFileSeparateurLayout);
				grpFileSeparateur.setText("Option séparateur données");
				GridData grpFileSeparateurData = new GridData();
				grpFileSeparateurData.horizontalAlignment = GridData.FILL;
				grpFileSeparateurData.grabExcessHorizontalSpace = true;
				grpFileSeparateurData.horizontalSpan = 4;
				grpFileSeparateur.setLayoutData(grpFileSeparateurData);
				grpFileSeparateur.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelChoixFileSeparateur = new Label(grpFileSeparateur, SWT.NONE);
					labelChoixFileSeparateur.setText("Choix du séparateur :");
					GridData labelChoixFileSeparateurData = new GridData();
					labelChoixFileSeparateur.setLayoutData(labelChoixFileSeparateurData);
				}
				{
					comboChoixFileSeparateur = new CCombo(grpFileSeparateur, SWT.NONE | SWT.BORDER);
					GridData comboChoixFileSeparateurLData = new GridData();
					comboChoixFileSeparateurLData.heightHint = 20;
					comboChoixFileSeparateurLData.widthHint = 86;
					comboChoixFileSeparateur.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
					comboChoixFileSeparateur.setLayoutData(comboChoixFileSeparateurLData);
					comboChoixFileSeparateur.setEditable(false);
				}
				{
					btOptionFileSeparateur = new Button(grpFileSeparateur, SWT.CHECK | SWT.LEFT);
					btOptionFileSeparateur.setText("Choix du séparateur");
					GridData btOptionFileSeparateurData = new GridData();
					btOptionFileSeparateurData.horizontalSpan = 4;
					btOptionFileSeparateurData.horizontalAlignment = GridData.FILL;
					btOptionFileSeparateurData.grabExcessHorizontalSpace = true;
					btOptionFileSeparateur.setLayoutData(btOptionFileSeparateurData);
				}
				{
					labelSeparateur = new Label(grpFileSeparateur, SWT.NONE);
					labelSeparateur.setText("Séparateur :");
					GridData labelSeparateurData = new GridData();
					labelSeparateur.setLayoutData(labelSeparateurData);
				}
				{
					textSeparatuer = new Text(grpFileSeparateur, SWT.BORDER);
					GridData textSeparatuerLData = new GridData();
					textSeparatuerLData.grabExcessHorizontalSpace = true;
					textSeparatuer.setLayoutData(textSeparatuerLData);
				}
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getGrpImport() {
		return grpImport;
	}

	public Group getGrpInfosDossier() {
		return grpInfosDossier;
	}

	public Text getTfCheminCompta() {
		return tfPathFileExtraction;
	}

	public Button getBtnCheminCompta() {
		return btnPathFileExtraction;
	}

	public Label getLaAnneeFiscale() {
		return laAnneeFiscale;
	}

	public Button getCbEcraser() {
		return cbEcraser;
	}

	public Button getCbVerrouLiasse() {
		return cbVerrouLiasse;
	}

	public Label getLaRegime() {
		return laRegime;
	}

	public Button getRaRegimeBIC() {
		return raRegimeBIC;
	}

	public Button getRaRegimeAgri() {
		return raRegimeAgri;
	}

	public Text getTfDossier() {
		return tfDossier;
	}

	public Label getLaDossier() {
		return laDossier;
	}

	public Text getTfAnneeFiscale() {
		return tfAnneeFiscale;
	}
	
	public Group getGrpServeur() {
		return grpServeur;
	}
	
	public Button getCbSite() {
		return cbSite;
	}
	
	public Button getCbFTP() {
		return cbFTP;
	}
	
	public Combo getCbAgence() {
		return cbAgence;
	}
	
	public Label getLaAgence() {
		return laAgence;
	}

	public Text getTfPathFileExtraction() {
		return tfPathFileExtraction;
	}

	public void setTfPathFileExtraction(Text tfPathFileExtraction) {
		this.tfPathFileExtraction = tfPathFileExtraction;
	}

	public Button getBtnPathFileExtraction() {
		return btnPathFileExtraction;
	}

	public void setBtnPathFileExtraction(Button btnPathFileExtraction) {
		this.btnPathFileExtraction = btnPathFileExtraction;
	}

	public Group getGrpImportMotCle() {
		return grpImportMotCle;
	}

	public void setGrpImportMotCle(Group grpImportMotCle) {
		this.grpImportMotCle = grpImportMotCle;
	}

	public Text getTfPathFileMotCle() {
		return tfPathFileMotCle;
	}

	public void setTfPathFileMotCle(Text tfPathFileMotCle) {
		this.tfPathFileMotCle = tfPathFileMotCle;
		tfPathFileMotCle.setEnabled(false);
	}

	public Button getBtnPathFileMotCle() {
		return btnPathFileMotCle;
	}


	public Button getButtonCheckBoxMotCle() {
		return buttonCheckBoxMotCle;
	}

	public void setButtonCheckBoxMotCle(Button buttonCheckBoxMotCle) {
		this.buttonCheckBoxMotCle = buttonCheckBoxMotCle;
	}

	public Button getBtOptionPrint() {
		return btOptionPrint;
	}

	public void setBtOptionPrint(Button btOptionPrint) {
		this.btOptionPrint = btOptionPrint;
	}

	public void setBtnPathFileMotCle(Button btnPathFileMotCle) {
		this.btnPathFileMotCle = btnPathFileMotCle;
	}

	public Group getGrpOptionPrintAndShow() {
		return grpOptionPrintAndShow;
	}

	public void setGrpOptionPrintAndShow(Group grpOptionPrintAndShow) {
		this.grpOptionPrintAndShow = grpOptionPrintAndShow;
	}

	public Button getBtShowOO() {
		return btShowOO;
	}

	public void setBtShowOO(Button btShowOO) {
		this.btShowOO = btShowOO;
	}

	public Text getTfPathSavePublipostage() {
		return tfPathSavePublipostage;
	}

	public void setTfPathSavePublipostage(Text tfPathSavePublipostage) {
		this.tfPathSavePublipostage = tfPathSavePublipostage;
	}

	public Button getBtnPathSavePublipostage() {
		return btnPathSavePublipostage;
	}

	public void setBtnPathSavePublipostage(Button btnPathSavePublipostage) {
		this.btnPathSavePublipostage = btnPathSavePublipostage;
	}

	public Button getButtonCheckBoxPublipostage() {
		return buttonCheckBoxPublipostage;
	}

	public void setButtonCheckBoxPublipostage(Button buttonCheckBoxPublipostage) {
		this.buttonCheckBoxPublipostage = buttonCheckBoxPublipostage;
	}

	public CCombo getComboChoixParamPublipostage() {
		return comboChoixParamPublipostage;
	}

	public Button getBtDeleteParamPublipostage() {
		return btDeleteParamPublipostage;
	}

	public CCombo getComboChoixFileSeparateur() {
		return comboChoixFileSeparateur;
	}

	public void setComboChoixFileSeparateur(CCombo comboChoixFileSeparateur) {
	}

	public Button getBtOptionFileSeparateur() {
		return btOptionFileSeparateur;
	}

	public void setBtOptionFileSeparateur(Button btOptionFileSeparateur) {
		this.btOptionFileSeparateur = btOptionFileSeparateur;
	}

	public Text getTextSeparatuer() {
		return textSeparatuer;
	}

	public void setTextSeparatuer(Text textSeparatuer) {
		this.textSeparatuer = textSeparatuer;
	}

}
