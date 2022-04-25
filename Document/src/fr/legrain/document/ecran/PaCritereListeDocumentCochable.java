package fr.legrain.document.ecran;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.fieldassist.CDateTimeControlCreator;
import fr.legrain.lib.gui.fieldassist.DateTimeControlCreator;
import fr.legrain.libMessageLGR.LgrMess;

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
public class PaCritereListeDocumentCochable extends org.eclipse.swt.widgets.Composite {

	 {
		 //Register as a resource user - SWTResourceManager will
		 //handle the obtaining and disposing of resources
		 SWTResourceManager.registerResourceUser(this);
	 }
	 
	 private Group groupParam;
	private Group groupTout;
	private Group groupRadio;
	private Label laTiers;
	private Button cbExporte;
	private Button cbNonExporte;
	private Button cbTous;
	private Combo cbListeDocument;
	private Label laListeCompte;
	private Text tfTiers;
	private Label laFin;
	private Label laDateDebut;
	private Group groupFactureNonRegle;

	private ExpandBar expandBarGroup;
	private Label laMessage;
	private DateTime tfDateDebutPeriode;
	private DateTime tfDateFinPeriode;
	private Button btnValiderParam;
	private Button btnSuivant;
	private Button btnFermer;
	private Composite paBtn;
	
	private DecoratedField fieldTiers;
	private DecoratedField fieldDateDebutPeriode;
	private DecoratedField fieldDateFinPeriode;

	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	 */	
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaCritereListeDocumentCochable inst = new PaCritereListeDocumentCochable(shell, SWT.NULL);
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

	public PaCritereListeDocumentCochable(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 3;
			this.setLayout(thisLayout);
			{
		
				groupTout = new Group(this, SWT.NONE);
				GridLayout groupToutLayout = new GridLayout();
				groupToutLayout.numColumns = 3;
				groupTout.setLayout(groupToutLayout);
				GridData groupToutLData = new GridData();
				groupToutLData.horizontalAlignment = GridData.FILL;
				groupToutLData.verticalAlignment = GridData.FILL;
				groupToutLData.grabExcessVerticalSpace = true;
				groupToutLData.grabExcessHorizontalSpace = true;
				groupTout.setLayoutData(groupToutLData);
				groupTout.setText("Etat d'exportation des documents");
				groupTout.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));

				groupParam = new Group(groupTout, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 5;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParamLData.grabExcessHorizontalSpace = true;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Paramètres");
				{
					laDateDebut = new Label(groupParam, SWT.NONE);
					GridData laDateDebutLData = new GridData();
					laDateDebutLData.widthHint = 149;
					laDateDebutLData.heightHint = 22;
					laDateDebut.setLayoutData(laDateDebutLData);
					laDateDebut.setText("Début");
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.heightHint = 23;
				if(!decore) {
					tfDateDebutPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					tfDateDebutPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateDebutPeriode = new DecoratedField(groupParam, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDateDebutPeriode = (DateTime)fieldDateDebutPeriode.getControl();
					fieldDateDebutPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}
				}
				
				{
					laFin = new Label(groupParam, SWT.NONE);
					GridData laFinLData = new GridData();
					laFinLData.widthHint = 42;
					laFinLData.heightHint = 22;
					laFin.setLayoutData(laFinLData);
					laFin.setText("Fin");
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 23;
					cdatetimeLgr2LData.horizontalSpan = 2;
				if(!decore) {
					tfDateFinPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					tfDateFinPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateFinPeriode = new DecoratedField(groupParam, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDateFinPeriode = (DateTime)fieldDateFinPeriode.getControl();
					fieldDateFinPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}

				}

				{
					laListeCompte = new Label(groupParam, SWT.NONE);
					GridData laCodeRelanceLData = new GridData();
					laListeCompte.setLayoutData(laCodeRelanceLData);
					laListeCompte.setText("Type de document");
				}
				{
					GridData cbListeDocLData = new GridData();
					cbListeDocLData.heightHint = 22;
					cbListeDocLData.horizontalAlignment = GridData.FILL;
					cbListeDocument = new Combo(groupParam, SWT.NONE);
					cbListeDocument.setLayoutData(cbListeDocLData);
				}

				{
					laTiers = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laTiersLData.heightHint = 13;
					laTiers.setLayoutData(laTiersLData);
					laTiers.setText("Tiers");
				}
				{
					GridData tfTiersLData = new GridData();
//					tfTiersLData.heightHint = 19;
					tfTiersLData.heightHint = 16;
					tfTiersLData.widthHint = 103;
					if(!decore) {
						tfTiers = new Text(groupParam, SWT.BORDER);
						tfTiers.setLayoutData(tfTiersLData);
//						tfTiers.setText("CODE_TIERS");
					} else {
						fieldTiers = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfTiers = (Text)fieldTiers.getControl();
						fieldTiers.getLayoutControl().setLayoutData(tfTiersLData);
					}
				}
				{
					btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.grabExcessHorizontalSpace = true;
					btnValiderParamLData.widthHint = 136;
					btnValiderParamLData.verticalAlignment = GridData.FILL;
					btnValiderParam.setLayoutData(btnValiderParamLData);
					btnValiderParam.setText("Valider");
				}
				groupRadio = new Group(groupTout, SWT.NONE);
				GridLayout groupRadioLayout = new GridLayout();
				groupRadio.setLayout(groupRadioLayout);
				GridData groupRadioLData = new GridData();
				groupRadioLData.horizontalAlignment = GridData.FILL;
				groupRadioLData.verticalAlignment = GridData.FILL;
				groupRadioLData.grabExcessHorizontalSpace = true;
				groupRadio.setLayoutData(groupRadioLData);
				groupRadio.setText("Export");
				{
					cbExporte = new Button(groupRadio, SWT.RADIO | SWT.LEFT);
					cbExporte.setText("Déjà exporté");
				}
				{
					cbNonExporte = new Button(groupRadio, SWT.RADIO | SWT.LEFT);
					cbNonExporte.setText("Non exporté");
				}
				{
					cbTous = new Button(groupRadio, SWT.RADIO | SWT.LEFT);
					cbTous.setText("Tous");
				}
				
//				{
//					cbExporte = new Button(groupParam, SWT.CHECK | SWT.LEFT);
//					GridData cbExporteLData = new GridData();
//					cbExporte.setLayoutData(cbExporteLData);
//					cbExporte.setText("Déjà exporté");
//				}
			}
			{
				groupFactureNonRegle = new Group(groupTout, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.horizontalSpan = 3;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("liste des documents");
				{
					GridData expandBar1LData = new GridData();
					expandBar1LData.horizontalAlignment = GridData.FILL;
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBar1LData.verticalAlignment = GridData.FILL;
					expandBar1LData.horizontalSpan = 2;
					expandBar1LData.grabExcessVerticalSpace = true;
					expandBarGroup = new ExpandBar(groupFactureNonRegle, SWT.V_SCROLL);
					expandBarGroup.setLayoutData(expandBar1LData);
				}
			}

			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.heightHint = 35;
				paBtnLData.horizontalSpan = 3;
				paBtn = new Composite(groupTout, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 2;
				paBtn.setLayout(paBtnLayout);
				paBtn.setLayoutData(paBtnLData);
				{
					btnSuivant = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnSuivantLData = new GridData();
					btnSuivantLData.horizontalAlignment = GridData.FILL;
					btnSuivantLData.verticalAlignment = GridData.FILL;
					btnSuivantLData.grabExcessHorizontalSpace = true;
					btnSuivant.setLayoutData(btnSuivantLData);
					btnSuivant.setText("< Précédent");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnFermerLData = new GridData();
					btnFermerLData.horizontalAlignment = GridData.FILL;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.grabExcessHorizontalSpace = true;
					btnFermer.setLayoutData(btnFermerLData);
					btnFermer.setText("Fermer");
				}
			}
			{
				GridData laMessageLData = new GridData();
				laMessageLData.horizontalAlignment = GridData.FILL;
				laMessageLData.grabExcessHorizontalSpace = true;
				laMessageLData.horizontalSpan = 2;
				laMessage = new Label(groupTout, SWT.NONE);
				laMessage.setLayoutData(laMessageLData);
			}

			this.layout();
			pack();
			this.setSize(810, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DateTime getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}

	public DateTime getTfDateFinPeriode() {
		return tfDateFinPeriode;
	}


	public Group getGroupParam() {
		return groupParam;
	}


	public Group getGroupFactureNonRegle() {
		return groupFactureNonRegle;
	}



	public Label getLaDateDebut() {
		return laDateDebut;
	}

	public Label getLaFin() {
		return laFin;
	}

	public Label getLaTiers() {
		return laTiers;
	}

	public Text getTfTiers() {
		return tfTiers;
	}

	public Composite getPaBtn() {
		return paBtn;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}

	public Button getBtnPrecedent() {
		return btnSuivant;
	}

	public Button getBtnValiderParam() {
		return btnValiderParam;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	

	public ExpandBar getExpandBarGroup() {
		return expandBarGroup;
	}
	

	public DecoratedField getFieldDateDebutPeriode() {
		return fieldDateDebutPeriode;
	}

	public DecoratedField getFieldDateFinPeriode() {
		return fieldDateFinPeriode;
	}
	
	

	public Combo getCbListeDocument() {
		return cbListeDocument;
	}

	public DecoratedField getFieldTiers() {
		return fieldTiers;
	}
	
	
	public Button getCbExporte() {
		return cbExporte;
	}


	public Button getCbNonExporte() {
		return cbNonExporte;
	}

	public Button getCbTous() {
		return cbTous;
	}

}
