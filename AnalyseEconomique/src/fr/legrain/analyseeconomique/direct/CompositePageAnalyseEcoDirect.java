package fr.legrain.analyseeconomique.direct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;


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
public class CompositePageAnalyseEcoDirect extends org.eclipse.swt.widgets.Composite {
	private Group grpImport;
	private Group grpInfosDossier;
	private Text tfCheminCompta;
	private Button btnCheminCompta;
	private Label laAgence;
	private Combo cbAgence;
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
		CompositePageAnalyseEcoDirect inst = new CompositePageAnalyseEcoDirect(shell, SWT.NULL);
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

	public CompositePageAnalyseEcoDirect(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(428, 465);
			{
				grpImport = new Group(this, SWT.NONE);
				GridLayout grpImportLayout = new GridLayout();
				grpImportLayout.numColumns = 2;
				grpImport.setLayout(grpImportLayout);
				GridData grpImportLData = new GridData();
				grpImportLData.horizontalAlignment = GridData.FILL;
				grpImportLData.grabExcessHorizontalSpace = true;
				grpImport.setLayoutData(grpImportLData);
				grpImport.setText("Import Comptabilité");
				{
					tfCheminCompta = new Text(grpImport, SWT.BORDER);
					GridData tfCheminComptaLData = new GridData();
					tfCheminComptaLData.horizontalAlignment = GridData.FILL;
					tfCheminComptaLData.grabExcessHorizontalSpace = true;
					tfCheminCompta.setLayoutData(tfCheminComptaLData);
				}
				{
					btnCheminCompta = new Button(grpImport, SWT.PUSH | SWT.CENTER);
					GridData btnCheminComptaLData = new GridData();
					btnCheminComptaLData.horizontalAlignment = GridData.END;
					btnCheminCompta.setLayoutData(btnCheminComptaLData);
					btnCheminCompta.setText("Parcourrir");
				}
			}
			{
				grpInfosDossier = new Group(this, SWT.NONE);
				GridLayout grpInfosDossierLayout = new GridLayout();
				grpInfosDossierLayout.numColumns = 3;
				grpInfosDossier.setLayout(grpInfosDossierLayout);
				GridData grpInfosDossierLData = new GridData();
				grpInfosDossierLData.horizontalAlignment = GridData.FILL;
				grpInfosDossierLData.grabExcessHorizontalSpace = true;
				grpInfosDossierLData.verticalAlignment = GridData.FILL;
				grpInfosDossierLData.grabExcessVerticalSpace = true;
				grpInfosDossier.setLayoutData(grpInfosDossierLData);
				grpInfosDossier.setText("Dossier / Liasse");
				{
					laDossier = new Label(grpInfosDossier, SWT.NONE);
					laDossier.setText("Dossier");
				}
				{
					tfDossier = new Text(grpInfosDossier, SWT.BORDER);
					GridData tfDossierLData = new GridData();
					tfDossierLData.horizontalSpan = 2;
					tfDossierLData.grabExcessHorizontalSpace = true;
					tfDossierLData.heightHint = 13;
					tfDossierLData.widthHint = 102;
					tfDossier.setLayoutData(tfDossierLData);
				}
				{
					laAnneeFiscale = new Label(grpInfosDossier, SWT.NONE);
					laAnneeFiscale.setText("Annee Fiscale");
				}
				{
					tfAnneeFiscale = new Text(grpInfosDossier, SWT.BORDER);
					GridData tfAnneeFiscaleLData = new GridData();
					tfAnneeFiscaleLData.horizontalSpan = 2;
					tfAnneeFiscaleLData.grabExcessHorizontalSpace = true;
					tfAnneeFiscaleLData.widthHint = 102;
					tfAnneeFiscaleLData.heightHint = 13;
					tfAnneeFiscale.setLayoutData(tfAnneeFiscaleLData);
				}
				{
					laRegime = new Label(grpInfosDossier, SWT.NONE);
					laRegime.setText("Regime : ");
				}
				{
					raRegimeAgri = new Button(grpInfosDossier, SWT.RADIO | SWT.LEFT);
					raRegimeAgri.setText("Agricole");
				}
				{
					raRegimeBIC = new Button(grpInfosDossier, SWT.RADIO | SWT.LEFT);
					raRegimeBIC.setText("BIC");
				}
				{
					cbVerrouLiasse = new Button(grpInfosDossier, SWT.CHECK | SWT.LEFT);
					GridData cbVerrouLiasseLData = new GridData();
					cbVerrouLiasseLData.horizontalSpan = 3;
					cbVerrouLiasse.setLayoutData(cbVerrouLiasseLData);
					cbVerrouLiasse.setText("Liasse verrouille");
				}
				{
					cbEcraser = new Button(grpInfosDossier, SWT.CHECK | SWT.LEFT);
					GridData cbEcraserLData = new GridData();
					cbEcraserLData.horizontalSpan = 3;
					cbEcraser.setLayoutData(cbEcraserLData);
					cbEcraser.setText("Ecraser les données précédentes");
				}
			}
			{
				grpServeur = new Group(this, SWT.NONE);
				GridLayout grpServeurLayout = new GridLayout();
				grpServeurLayout.numColumns = 2;
				grpServeur.setLayout(grpServeurLayout);
				GridData grpServeurLData = new GridData();
				grpServeurLData.horizontalAlignment = GridData.FILL;
				grpServeurLData.grabExcessHorizontalSpace = true;
				grpServeur.setLayoutData(grpServeurLData);
				grpServeur.setText("Serveur");
				{
					cbSite = new Button(grpServeur, SWT.CHECK | SWT.LEFT);
					GridData cbSiteLData = new GridData();
					cbSiteLData.horizontalSpan = 2;
					cbSite.setLayoutData(cbSiteLData);
					cbSite.setText("Ouverture automatique du site");
				}
				{
					cbFTP = new Button(grpServeur, SWT.CHECK | SWT.LEFT);
					GridData cbFTPLData = new GridData();
					cbFTPLData.horizontalSpan = 2;
					cbFTP.setLayoutData(cbFTPLData);
					cbFTP.setText("Transfert automatique sur le serveur");
				}
				{
					laAgence = new Label(grpServeur, SWT.NONE);
					laAgence.setText("Agence");
				}
				{
					cbAgence = new Combo(grpServeur, SWT.READ_ONLY);
				}
			}
			this.layout();
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
		return tfCheminCompta;
	}

	public Button getBtnCheminCompta() {
		return btnCheminCompta;
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

}
