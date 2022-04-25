package fr.legrain.articles.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
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

import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquette;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquetteArticleController;
import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.publipostage.gui.CompositePublipostage;
//import fr.legrain.recherchermulticrit.ecrans.CompositePublipostage;

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
public class PaArticleSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_ARTICLE;
	private Label laLIBELLEC_ARTICLE;
	private Label laDIVERS_ARTICLE;
	private Label laCOMMENTAIRE_ARTICLE;
	private Label laLIBELLEL_ARTICLE;
	private Label laCODE_FAMILLE;
	private Label laNUMCPT_ARTICLE;
	private Label laCODE_TVA;
	private Label laCODE_T_TVA;
	private Label laSTOCK_MIN_ARTICLE;
	private Label laLongueur;
	private Label laLargeur;
	private Label laHauteur;
	private Label laPoids;
	private Composite composite3;
	private Button btnAjoutPrix;
	private Group groupPrix;
	private Group groupArticle;
	private Group groupRapportUnite;
	private Group groupCompta;
	private Group groupStock;
	private Group groupDimension;
	private Group groupUnite;
	private Group groupObservation;

	private Text tfCOMMENTAIRE_ARTICLE;
	private Text tfDIVERS_ARTICLE;
	private Text tfLIBELLEC_ARTICLE;
	private Text tfCODE_FAMILLE;
	private Text tfNUMCPT_ARTICLE;
	private Text tfCODE_TVA;
	private Text tfCODE_T_TVA;
	private Text tfSTOCK_MIN_ARTICLE;
	private Text tfCODE_ARTICLE;
	private Text tfLIBELLEL_ARTICLE;
	private Text tfLongueur;
	private Text tfLargeur;
	private Text tfHauteur;
	private Text tfPoids;
	
	private Group groupCapsule;
	private Label laTitreTransportU1 = null;
	private Label laNombreTitreTransportU1 = null;
	private Label laUniteTitreTransportU1 = null;
	private Combo cbUniteTitreTransportU1;
	private Text tfTitreTransportU1 = null;
	private Text tfNombreTitreTransportU1 = null;
	private DecoratedField fieldTitreTransportU1 = null;
	private DecoratedField fieldNombreTitreTransportU1 = null;

	private DecoratedField fieldCOMMENTAIRE_ARTICLE;
	private DecoratedField fieldDIVERS_ARTICLE;
	private DecoratedField fieldLIBELLEC_ARTICLE;
	private DecoratedField fieldCODE_FAMILLE;
	private DecoratedField fieldNUMCPT_ARTICLE;
	private DecoratedField fieldCODE_TVA;
	private DecoratedField fieldCODE_T_TVA;
	private DecoratedField fieldSTOCK_MIN_ARTICLE;
	private DecoratedField fieldCODE_ARTICLE;
	private DecoratedField fieldLIBELLEL_ARTICLE;
	private DecoratedField fieldLongueur;
	private DecoratedField fieldLargeur;
	private DecoratedField fieldHauteur;
	private DecoratedField fieldPoids;
	
	private Label laCODE_UNITE;
	private Label laPRIX_PRIX;
	private Label laPRIXTTC_PRIX;

	private Text tfCODE_UNITE;
	private Text tfPRIXTTC_PRIX;
	private Text tfPRIX_PRIX;
	
	private DecoratedField fieldCODE_UNITE;
	private DecoratedField fieldPRIXTTC_PRIX;
	private DecoratedField fieldPRIX_PRIX;

	private Label laCODE_UNITE2;
	private Label laRAPPORT;
	private Label laSensRapport;
	private Label laDECIMAL;
	private Label laActif;
	
	private Text tfCODE_UNITE2;
	private Text tfRAPPORT;
	private Text tfDECIMAL;
	private Combo cbSensRapport;
	private Button cbActif;
	
	private DecoratedField fieldCODE_UNITE2;
	private DecoratedField fieldRAPPORT;
	private DecoratedField fieldDECIMAL;
	
	private Label laU1PhraseRapport; 
	private Label laRapportPhraseRapport;
	private Label laEgalePhraseRapport;
	private Label laU2PhraseRapport; 
	
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;

	/**
	 * Auto-generated main method to display this 
	 * fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
		
	}

	/**
	 * Auto-generated method to display this 
	 * fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaArticleSWT inst = new PaArticleSWT(shell, SWT.NULL);
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

	public PaArticleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					groupArticle = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupArticleLayout = new GridLayout();
					groupArticleLayout.numColumns = 2;
					groupArticle.setLayout(groupArticleLayout);
					GridData groupArticleLData = new GridData();
					groupArticleLData.grabExcessHorizontalSpace = true;
					groupArticleLData.horizontalAlignment = GridData.FILL;
					groupArticleLData.verticalAlignment = GridData.FILL;
					groupArticleLData.verticalSpan = 2;
					groupArticle.setLayoutData(groupArticleLData);
					groupArticle.setText("Article");
				}
				
				{
					groupUnite = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupStockLayout = new GridLayout();
					groupStockLayout.numColumns = 2;
					groupUnite.setLayout(groupStockLayout);
					GridData groupStockLData = new GridData();
					groupStockLData.grabExcessHorizontalSpace = true;
					groupStockLData.horizontalAlignment = GridData.FILL;
					groupStockLData.verticalAlignment = GridData.FILL;
					groupUnite.setLayoutData(groupStockLData);
					groupUnite.setText("Unités");
				}
				
				{
					groupRapportUnite = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupStockLayout = new GridLayout();
					groupStockLayout.numColumns = 5;
					groupRapportUnite.setLayout(groupStockLayout);
					GridData groupStockLData = new GridData();
					groupStockLData.verticalSpan = 2;
					groupStockLData.horizontalSpan = 2;
					groupStockLData.grabExcessHorizontalSpace = true;
					groupStockLData.horizontalAlignment = GridData.FILL;
					groupStockLData.verticalAlignment = GridData.FILL;
					groupRapportUnite.setLayoutData(groupStockLData);
					groupRapportUnite.setText("Rapport entre U1 et U2");
				}
				
				{
					groupPrix = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupPrixLayout = new GridLayout();
					groupPrixLayout.numColumns = 2;
					groupPrix.setLayout(groupPrixLayout);
					GridData groupPrixLData = new GridData();
					groupPrixLData.grabExcessHorizontalSpace = true;
					groupPrixLData.horizontalAlignment = GridData.FILL;
					groupPrixLData.verticalAlignment = GridData.FILL;
					groupPrix.setLayoutData(groupPrixLData);
					groupPrix.setText("Tarif de référence pour U1");
				}
				
				{
					groupCompta = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupComptaLayout = new GridLayout();
					groupComptaLayout.numColumns = 2;
					groupCompta.setLayout(groupComptaLayout);
					GridData groupComptaLData = new GridData();
					groupComptaLData.grabExcessHorizontalSpace = true;
					groupComptaLData.horizontalAlignment = GridData.FILL;
					groupComptaLData.verticalAlignment = GridData.FILL;
					groupComptaLData.horizontalSpan = 2;
					groupCompta.setLayoutData(groupComptaLData);
					groupCompta.setText("Comptabilité");
				}
				{
					groupStock = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupRapportLayout = new GridLayout();
					groupRapportLayout.numColumns = 2;
					groupStock.setLayout(groupRapportLayout);
					GridData groupRapportLData = new GridData();
					groupRapportLData.grabExcessHorizontalSpace = true;
					groupRapportLData.horizontalAlignment = GridData.FILL;
					groupRapportLData.verticalAlignment = GridData.FILL;
					groupRapportLData.horizontalSpan = 2;
					groupStock.setLayoutData(groupRapportLData);
					groupStock.setText("Stocks");
				}
				{
					groupDimension = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupRapportLayout = new GridLayout();
					groupRapportLayout.numColumns = 2;
					groupDimension.setLayout(groupRapportLayout);
					GridData groupRapportLData = new GridData();
					groupRapportLData.grabExcessHorizontalSpace = true;
					//groupRapportLData.verticalSpan = 2;
					groupRapportLData.horizontalAlignment = GridData.FILL;
					groupRapportLData.verticalAlignment = GridData.FILL;
					groupDimension.setLayoutData(groupRapportLData);
					groupDimension.setText("Dimensions");
				}
				
				{
					groupObservation = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupRapportLayout = new GridLayout();
					groupRapportLayout.numColumns = 1;
					groupObservation.setLayout(groupRapportLayout);
					GridData groupRapportLData = new GridData();
					groupRapportLData.grabExcessHorizontalSpace = true;
					groupRapportLData.horizontalSpan = 3;
					groupRapportLData.horizontalAlignment = GridData.FILL;
					groupRapportLData.verticalAlignment = GridData.FILL;
					groupObservation.setLayoutData(groupRapportLData);
					groupObservation.setText("Observation");
				}
				
				{
					laCODE_UNITE = new Label(groupUnite, SWT.NONE);
					laCODE_UNITE.setText("Code U1");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfCODE_UNITE = new Text(groupUnite, SWT.BORDER);
						tfCODE_UNITE.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldCODE_UNITE = new DecoratedField(groupUnite, SWT.BORDER, new TextControlCreator());
					tfCODE_UNITE = (Text)fieldCODE_UNITE.getControl();
					fieldCODE_UNITE.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laPRIX_PRIX = new Label(groupPrix, SWT.NONE);
					laPRIX_PRIX.setText("Tarif HT");
				}
				{
					GridData tfPRIX_PRIXLData = new GridData();
					tfPRIX_PRIXLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfPRIX_PRIX = new Text(groupPrix, SWT.BORDER);
						tfPRIX_PRIX.setLayoutData(tfPRIX_PRIXLData);
					} else {					
					fieldPRIX_PRIX = new DecoratedField(groupPrix, SWT.BORDER, new TextControlCreator());
					tfPRIX_PRIX = (Text)fieldPRIX_PRIX.getControl();
					fieldPRIX_PRIX.getLayoutControl().setLayoutData(tfPRIX_PRIXLData);
					}
				}
				{
					laPRIXTTC_PRIX = new Label(groupPrix, SWT.NONE);
					laPRIXTTC_PRIX.setText("Tarif TTC");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfPRIXTTC_PRIX = new Text(groupPrix, SWT.BORDER);
						tfPRIXTTC_PRIX.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
					fieldPRIXTTC_PRIX = new DecoratedField(groupPrix, SWT.BORDER, new TextControlCreator());
					tfPRIXTTC_PRIX = (Text)fieldPRIXTTC_PRIX.getControl();
					fieldPRIXTTC_PRIX.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				
				//rapport unite
				{
					laCODE_UNITE2 = new Label(groupUnite, SWT.NONE);
					laCODE_UNITE2.setText("Code U2");
				}
				{
					GridData tfCODE_UNITE2LData = new GridData();
					tfCODE_UNITE2LData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITE2LData.grabExcessHorizontalSpace = true;
					//tfCODE_UNITE2LData.horizontalSpan = 4;
					if(!decore) {
						tfCODE_UNITE2 = new Text(groupUnite, SWT.BORDER);
						tfCODE_UNITE2.setLayoutData(tfCODE_UNITE2LData);
					} else {					
					fieldCODE_UNITE2 = new DecoratedField(groupUnite, SWT.BORDER, new TextControlCreator());
					tfCODE_UNITE2 = (Text)fieldCODE_UNITE2.getControl();
					fieldCODE_UNITE2.getLayoutControl().setLayoutData(tfCODE_UNITE2LData);
					}
				}
				{
					laRAPPORT = new Label(groupRapportUnite, SWT.NONE);
					laRAPPORT.setText("Rapport");
				}
				{
					GridData tfRAPPORTLData = new GridData();
					tfRAPPORTLData.horizontalAlignment = GridData.FILL;
					tfRAPPORTLData.horizontalSpan = 4;
					if(!decore) {
						tfRAPPORT = new Text(groupRapportUnite, SWT.BORDER);
						tfRAPPORT.setLayoutData(tfRAPPORTLData);
					} else {					
					fieldRAPPORT = new DecoratedField(groupRapportUnite, SWT.BORDER, new TextControlCreator());
					tfRAPPORT = (Text)fieldRAPPORT.getControl();
					fieldRAPPORT.getLayoutControl().setLayoutData(tfRAPPORTLData);
					}
				}
				{
					laDECIMAL = new Label(groupRapportUnite, SWT.NONE);
					laDECIMAL.setText("Arrondi");
				}
				{
					GridData tfDECIMALLData = new GridData();
					tfDECIMALLData.horizontalAlignment = GridData.FILL;
					tfDECIMALLData.horizontalSpan = 4;
					if(!decore) {
						tfDECIMAL = new Text(groupRapportUnite, SWT.BORDER);
						tfDECIMAL.setLayoutData(tfDECIMALLData);
					} else {					
					fieldDECIMAL = new DecoratedField(groupRapportUnite, SWT.BORDER, new TextControlCreator());
					tfDECIMAL = (Text)fieldDECIMAL.getControl();
					fieldDECIMAL.getLayoutControl().setLayoutData(tfDECIMALLData);
					}
				}
				
//				{
//					laSensRapport = new Label(groupRapportUnite, SWT.NONE);
//					laSensRapport.setText("Sens");
//				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laU1PhraseRapport = new Label(groupRapportUnite, SWT.NONE);
					laU1PhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laU1PhraseRapport.setText("U1");
				}
				{
					GridData tfNB_DECIMALLData = new GridData();
					tfNB_DECIMALLData.widthHint = 150;
					cbSensRapport = new Combo(groupRapportUnite, SWT.READ_ONLY);
					cbSensRapport.setLayoutData(tfNB_DECIMALLData);
				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laRapportPhraseRapport = new Label(groupRapportUnite, SWT.NONE);
					laRapportPhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laRapportPhraseRapport.setText("0000000");
				}
				{
					laEgalePhraseRapport = new Label(groupRapportUnite, SWT.NONE);
					laEgalePhraseRapport.setText(" = ");
				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					laU2PhraseRapportLData.grabExcessHorizontalSpace = true;
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laU2PhraseRapport = new Label(groupRapportUnite, SWT.NONE);
					laU2PhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laU2PhraseRapport.setText("U2");
				}
				///
				{
					laCODE_ARTICLE = new Label(groupArticle, SWT.NONE);
					laCODE_ARTICLE.setText("Code");
				}

				{
					GridData tfCODE_ARTICLELData = new GridData();
					tfCODE_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfCODE_ARTICLELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfCODE_ARTICLE = new Text(groupArticle, SWT.BORDER);
						tfCODE_ARTICLE.setLayoutData(tfCODE_ARTICLELData);
					} else {					
					fieldCODE_ARTICLE = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
					tfCODE_ARTICLE = (Text)fieldCODE_ARTICLE.getControl();
					fieldCODE_ARTICLE.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
					}
				}
				{
					laLIBELLEC_ARTICLE = new Label(groupArticle, SWT.NONE);
					laLIBELLEC_ARTICLE.setText("Libellé");
				}
				{
					GridData tfLIBELLEC_ARTICLELData = new GridData();
					tfLIBELLEC_ARTICLELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLEC_ARTICLE = new Text(groupArticle, SWT.BORDER);
						tfLIBELLEC_ARTICLE.setLayoutData(tfLIBELLEC_ARTICLELData);
					} else {					
					fieldLIBELLEC_ARTICLE = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
					tfLIBELLEC_ARTICLE = (Text)fieldLIBELLEC_ARTICLE.getControl();
					fieldLIBELLEC_ARTICLE.getLayoutControl().setLayoutData(tfLIBELLEC_ARTICLELData);
					}
				}
				{
					laLIBELLEL_ARTICLE = new Label(groupArticle, SWT.NONE);
					laLIBELLEL_ARTICLE.setText("Description");
				}
				{
					GridData tfLIBELLEL_ARTICLELData = new GridData();
					tfLIBELLEL_ARTICLELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLEL_ARTICLE = new Text(groupArticle, SWT.BORDER);
						tfLIBELLEL_ARTICLE.setLayoutData(tfLIBELLEL_ARTICLELData);
					} else {					
					fieldLIBELLEL_ARTICLE = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
					tfLIBELLEL_ARTICLE = (Text)fieldLIBELLEL_ARTICLE.getControl();
					fieldLIBELLEL_ARTICLE.getLayoutControl().setLayoutData(tfLIBELLEL_ARTICLELData);
					}
				}
				{
					laCODE_FAMILLE = new Label(groupArticle, SWT.NONE);
					laCODE_FAMILLE.setText("Code famille");
				}
				{
					GridData tfCODE_FAMILLELData = new GridData();
					tfCODE_FAMILLELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_FAMILLE = new Text(groupArticle, SWT.BORDER);
						tfCODE_FAMILLE.setLayoutData(tfCODE_FAMILLELData);
					} else {					
					fieldCODE_FAMILLE = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
					tfCODE_FAMILLE = (Text)fieldCODE_FAMILLE.getControl();
					fieldCODE_FAMILLE.getLayoutControl().setLayoutData(tfCODE_FAMILLELData);
					}
				}
				{
					laNUMCPT_ARTICLE = new Label(groupCompta, SWT.NONE);
					laNUMCPT_ARTICLE.setText("Compte");
				}
				{
					GridData tfNUMCPT_ARTICLELData = new GridData();
					tfNUMCPT_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfNUMCPT_ARTICLELData.grabExcessHorizontalSpace = true;
//					tfNUMCPT_ARTICLELData.heightHint = 13;
					//tfNUMCPT_ARTICLELData.widthHint = 177;
					if(!decore) {
						tfNUMCPT_ARTICLE = new Text(groupCompta, SWT.BORDER);
						tfNUMCPT_ARTICLE.setLayoutData(tfNUMCPT_ARTICLELData);
					} else {					
					fieldNUMCPT_ARTICLE = new DecoratedField(groupCompta, SWT.BORDER, new TextControlCreator());
					tfNUMCPT_ARTICLE = (Text)fieldNUMCPT_ARTICLE.getControl();
					fieldNUMCPT_ARTICLE.getLayoutControl().setLayoutData(tfNUMCPT_ARTICLELData);
					}
				}
				{
					laCODE_TVA = new Label(groupArticle, SWT.NONE);
					laCODE_TVA.setText("Code Tva");
				}
				{
					GridData tfCODE_TVALData = new GridData();
					tfCODE_TVALData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_TVA = new Text(groupArticle, SWT.BORDER);
						tfCODE_TVA.setLayoutData(tfCODE_TVALData);
					} else {					
					fieldCODE_TVA = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
					tfCODE_TVA = (Text)fieldCODE_TVA.getControl();
					fieldCODE_TVA.getLayoutControl().setLayoutData(tfCODE_TVALData);
					}
				}
				{
					laCODE_T_TVA = new Label(groupCompta, SWT.NONE);
					laCODE_T_TVA.setText("Exigibilité Tva");
				}
				{
					GridData tfCODE_T_TVALData = new GridData();
					tfCODE_T_TVALData.horizontalAlignment = GridData.FILL;
					//tfCODE_T_TVALData.heightHint = 13;
					if(!decore) {
						tfCODE_T_TVA = new Text(groupCompta, SWT.BORDER);
						tfCODE_T_TVA.setLayoutData(tfCODE_T_TVALData);
					} else {					
					fieldCODE_T_TVA = new DecoratedField(groupCompta, SWT.BORDER, new TextControlCreator());
					tfCODE_T_TVA = (Text)fieldCODE_T_TVA.getControl();
					fieldCODE_T_TVA.getLayoutControl().setLayoutData(tfCODE_T_TVALData);
					}
				}
				{
					laActif = new Label(groupArticle, SWT.NONE);
					laActif.setText("Actif");
				}
				{
					GridData cbACTIF_TIERSLData = new GridData();
					cbACTIF_TIERSLData.widthHint = 22;
					cbACTIF_TIERSLData.heightHint = 16;
					cbActif = new Button(groupArticle, SWT.CHECK | SWT.LEFT);
					cbActif.setLayoutData(cbACTIF_TIERSLData);
				}
				
//				{
//					laDIVERS_ARTICLE = new Label(paCorpsFormulaire, SWT.NONE);
//					GridData laDIVERS_ARTICLELData = new GridData();
//					laDIVERS_ARTICLELData.horizontalSpan = 1;
//					laDIVERS_ARTICLE.setLayoutData(laDIVERS_ARTICLELData);
//					laDIVERS_ARTICLE.setText("Observation");
//				}
				{
					GridData tfDIVERS_ARTICLELData = new GridData();
					tfDIVERS_ARTICLELData.grabExcessHorizontalSpace = true;
					tfDIVERS_ARTICLELData.grabExcessVerticalSpace = true;
					tfDIVERS_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfDIVERS_ARTICLELData.verticalAlignment = GridData.FILL;
					//tfDIVERS_ARTICLELData.heightHint = 83;
					//tfDIVERS_ARTICLELData.horizontalSpan = 3;
					if(!decore) {
						tfDIVERS_ARTICLE = new Text(groupObservation, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
						tfDIVERS_ARTICLE.setLayoutData(tfDIVERS_ARTICLELData);
					} else {					
						fieldDIVERS_ARTICLE = new DecoratedField(groupObservation, SWT.MULTI
								| SWT.WRAP
								| SWT.H_SCROLL
								| SWT.V_SCROLL
								| SWT.BORDER, new TextControlCreator());
						tfDIVERS_ARTICLE = (Text)fieldDIVERS_ARTICLE.getControl();
						fieldDIVERS_ARTICLE.getLayoutControl().setLayoutData(tfDIVERS_ARTICLELData);
					}
				}
				{
					laCOMMENTAIRE_ARTICLE = new Label(
							paCorpsFormulaire,
							SWT.NONE);
					laCOMMENTAIRE_ARTICLE.setText("Commentaire");
				}
				{
					GridData tfCOMMENTAIRE_ARTICLELData = new GridData();
					tfCOMMENTAIRE_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfCOMMENTAIRE_ARTICLELData.heightHint = 83;
					if(!decore) {
						tfCOMMENTAIRE_ARTICLE = new Text(paCorpsFormulaire,
								SWT.MULTI
								| SWT.WRAP
								| SWT.H_SCROLL
								| SWT.V_SCROLL
								| SWT.BORDER);
								tfCOMMENTAIRE_ARTICLE.setLayoutData(tfCOMMENTAIRE_ARTICLELData);
					} else {					
					fieldCOMMENTAIRE_ARTICLE = new DecoratedField(paCorpsFormulaire, SWT.MULTI
							| SWT.WRAP
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER, new TextControlCreator());
					tfCOMMENTAIRE_ARTICLE = (Text)fieldCOMMENTAIRE_ARTICLE.getControl();
					fieldCOMMENTAIRE_ARTICLE.getLayoutControl().setLayoutData(tfCOMMENTAIRE_ARTICLELData);
					}
				}
				{
					laSTOCK_MIN_ARTICLE = new Label(groupStock, SWT.NONE);
					laSTOCK_MIN_ARTICLE.setText("Stock mini");
				}
				{
					GridData tfSTOCK_MIN_ARTICLELData = new GridData();
					tfSTOCK_MIN_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfSTOCK_MIN_ARTICLELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfSTOCK_MIN_ARTICLE = new Text(groupStock, SWT.BORDER);
						tfSTOCK_MIN_ARTICLE.setLayoutData(tfSTOCK_MIN_ARTICLELData);
					} else {					
					fieldSTOCK_MIN_ARTICLE = new DecoratedField(groupStock, SWT.BORDER, new TextControlCreator());
					tfSTOCK_MIN_ARTICLE = (Text)fieldSTOCK_MIN_ARTICLE.getControl();
					fieldSTOCK_MIN_ARTICLE.getLayoutControl().setLayoutData(tfSTOCK_MIN_ARTICLELData);
					}
				}

				{
					laHauteur = new Label(groupDimension, SWT.NONE);
					laHauteur.setText("Hauteur");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfHauteur = new Text(groupDimension, SWT.BORDER);
						tfHauteur.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldHauteur = new DecoratedField(groupDimension, SWT.BORDER, new TextControlCreator());
					tfHauteur = (Text)fieldHauteur.getControl();
					fieldHauteur.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laLargeur = new Label(groupDimension, SWT.NONE);
					laLargeur.setText("Largeur");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfLargeur = new Text(groupDimension, SWT.BORDER);
						tfLargeur.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldLargeur = new DecoratedField(groupDimension, SWT.BORDER, new TextControlCreator());
					tfLargeur = (Text)fieldLargeur.getControl();
					fieldLargeur.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laLongueur = new Label(groupDimension, SWT.NONE);
					laLongueur.setText("Longueur");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfLongueur = new Text(groupDimension, SWT.BORDER);
						tfLongueur.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldLongueur = new DecoratedField(groupDimension, SWT.BORDER, new TextControlCreator());
					tfLongueur = (Text)fieldLongueur.getControl();
					fieldLongueur.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laPoids = new Label(groupDimension, SWT.NONE);
					laPoids.setText("Poids");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfPoids = new Text(groupDimension, SWT.BORDER);
						tfPoids.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldPoids = new DecoratedField(groupDimension, SWT.BORDER, new TextControlCreator());
					tfPoids = (Text)fieldPoids.getControl();
					fieldPoids.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					composite3 = new Composite(paCorpsFormulaire, SWT.NONE);
					GridLayout composite3Layout = new GridLayout();
					composite3Layout.makeColumnsEqualWidth = true;
					composite3Layout.numColumns = 2;
					composite3Layout.marginHeight = 10;
					GridData composite3LData = new GridData();
					composite3LData.widthHint = 263;
					composite3LData.heightHint = 43;
					composite3LData.horizontalSpan = 2;
					composite3.setLayoutData(composite3LData);
					composite3.setLayout(composite3Layout);
					{
						btnAjoutPrix = new Button(composite3, SWT.PUSH
								| SWT.CENTER);
						GridData btnAjoutCommercialLData = new GridData();
						btnAjoutCommercialLData.heightHint = 27;
						btnAjoutCommercialLData.grabExcessHorizontalSpace = true;
						btnAjoutCommercialLData.horizontalAlignment = GridData.FILL;
						btnAjoutPrix.setLayoutData(btnAjoutCommercialLData);
						btnAjoutPrix.setText("Prix (Alt+P)");
						btnAjoutPrix.setSize(124, 27);
					}
				}
			}

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(711, 2035);
			GridData paGrilleLData = new GridData();
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			compositeFormLData.grabExcessHorizontalSpace = true;
			super.getPaFomulaire().setLayoutData(paFomulaireLData);
			GridData grilleLData = new GridData();
			grilleLData.verticalAlignment = GridData.FILL;
			grilleLData.horizontalAlignment = GridData.FILL;
			grilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			
			super.getGrille().setLayoutData(grilleLData);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void extensionTitreTransport(PaArticleSWT vue) {
		
		GridData gd = (GridData)groupRapportUnite.getLayoutData();
		gd.horizontalSpan = 1;
		groupRapportUnite.setLayoutData(gd);
		
		{
			groupCapsule = new Group(paCorpsFormulaire, SWT.NONE);
			GridLayout groupStockLayout = new GridLayout();
			groupStockLayout.numColumns = 2;
			groupCapsule.setLayout(groupStockLayout);
			GridData groupStockLData = new GridData();
			groupStockLData.grabExcessHorizontalSpace = true;
			groupStockLData.horizontalAlignment = GridData.FILL;
			groupStockLData.verticalAlignment = GridData.FILL;
			//groupStockLData.horizontalSpan = 2;
			groupStockLData.verticalSpan = 2;
			groupCapsule.setLayoutData(groupStockLData);
			groupCapsule.setText("CRD pour U1");
		}
		//groupCapsule.getParent().moveBelow(tfQTE_L_FACTURE.getParent()); //getParent à cause des champs décorés
		groupCapsule.moveBelow(groupRapportUnite);
		
		{
			laTitreTransportU1 = new Label(vue.getGroupCapsule(), SWT.NONE);
			laTitreTransportU1.setText("Type CRD");
		}
		{
			GridData tfTitreTransportU1LData = new GridData();
			tfTitreTransportU1LData.horizontalAlignment = GridData.FILL;
			tfTitreTransportU1LData.grabExcessHorizontalSpace = true;
			if(!decore) {
				tfTitreTransportU1 = new Text(vue.getGroupCapsule(), SWT.BORDER);
				tfTitreTransportU1.setLayoutData(tfTitreTransportU1LData);
			} else {					
				fieldTitreTransportU1 = new DecoratedField(vue.getGroupCapsule(), SWT.BORDER, new TextControlCreator());
				tfTitreTransportU1 = (Text)fieldTitreTransportU1.getControl();
				fieldTitreTransportU1.getLayoutControl().setLayoutData(tfTitreTransportU1LData);
			}
		}
		
		{
			laNombreTitreTransportU1 = new Label(vue.getGroupCapsule(), SWT.NONE);
			laNombreTitreTransportU1.setText("Nb. CRD. / Unité");
		}
		{
			GridData tfTitreTransportU1LData = new GridData();
			tfTitreTransportU1LData.horizontalAlignment = GridData.FILL;
			tfTitreTransportU1LData.grabExcessHorizontalSpace = true;
			if(!decore) {
				tfNombreTitreTransportU1 = new Text(vue.getGroupCapsule(), SWT.BORDER);
				tfNombreTitreTransportU1.setLayoutData(tfTitreTransportU1LData);
			} else {					
				fieldNombreTitreTransportU1 = new DecoratedField(vue.getGroupCapsule(), SWT.BORDER, new TextControlCreator());
				tfNombreTitreTransportU1 = (Text)fieldNombreTitreTransportU1.getControl();
				fieldNombreTitreTransportU1.getLayoutControl().setLayoutData(tfTitreTransportU1LData);
			}
		}
//		
//		{
//			laUniteTitreTransportU1 = new Label(vue.getGroupCapsule(), SWT.NONE);
//			laUniteTitreTransportU1.setText("Unité titre transport");
//		}
//		
//		{
//			GridData tfNB_DECIMALLData = new GridData();
//			tfNB_DECIMALLData.widthHint = 150;
//			cbUniteTitreTransportU1 = new Combo(vue.getGroupCapsule(), SWT.READ_ONLY);
//			cbUniteTitreTransportU1.setLayoutData(tfNB_DECIMALLData);
//		}
		vue.layout();
	}

	public Button getBtnAjoutPrix() {
		return btnAjoutPrix;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Composite getComposite3() {
		return composite3;
	}

	public Label getLaDIVERS_ARTICLE() {
		return laDIVERS_ARTICLE;
	}

	public Label getLaCODE_FAMILLE() {
		return laCODE_FAMILLE;
	}

	public Label getLaNUMCPT_ARTICLE() {
		return laNUMCPT_ARTICLE;
	}

	public Label getLaLIBELLEC_ARTICLE() {
		return laLIBELLEC_ARTICLE;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Label getLaLIBELLEL_ARTICLE() {
		return laLIBELLEL_ARTICLE;
	}

	public Label getLaCOMMENTAIRE_ARTICLE() {
		return laCOMMENTAIRE_ARTICLE;
	}

	public Label getLaSTOCK_MIN_ARTICLE() {
		return laSTOCK_MIN_ARTICLE;
	}

	public Label getLaCODE_T_TVA() {
		return laCODE_T_TVA;
	}

	public Label getLaCODE_TVA() {
		return laCODE_TVA;
	}

	public Text getTfDIVERS_ARTICLE() {
		return tfDIVERS_ARTICLE;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Text getTfCODE_FAMILLE() {
		return tfCODE_FAMILLE;
	}

	public Text getTfNUMCPT_ARTICLE() {
		return tfNUMCPT_ARTICLE;
	}

	public Text getTfLIBELLEC_ARTICLE() {
		return tfLIBELLEC_ARTICLE;
	}

	public Text getTfLIBELLEL_ARTICLE() {
		return tfLIBELLEL_ARTICLE;
	}

	public Text getTfCOMMENTAIRE_ARTICLE() {
		return tfCOMMENTAIRE_ARTICLE;
	}

	public Text getTfSTOCK_MIN_ARTICLE() {
		return tfSTOCK_MIN_ARTICLE;
	}

	public Text getTfCODE_T_TVA() {
		return tfCODE_T_TVA;
	}

	public Text getTfCODE_TVA() {
		return tfCODE_TVA;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldDIVERS_ARTICLE() {
		return fieldDIVERS_ARTICLE;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

	public DecoratedField getFieldCODE_FAMILLE() {
		return fieldCODE_FAMILLE;
	}

	public DecoratedField getFieldNUMCPT_ARTICLE() {
		return fieldNUMCPT_ARTICLE;
	}

	public DecoratedField getFieldLIBELLEC_ARTICLE() {
		return fieldLIBELLEC_ARTICLE;
	}

	public DecoratedField getFieldLIBELLEL_ARTICLE() {
		return fieldLIBELLEL_ARTICLE;
	}

	public DecoratedField getFieldCOMMENTAIRE_ARTICLE() {
		return fieldCOMMENTAIRE_ARTICLE;
	}

	public DecoratedField getFieldSTOCK_MIN_ARTICLE() {
		return fieldSTOCK_MIN_ARTICLE;
	}

	public DecoratedField getFieldCODE_T_TVA() {
		return fieldCODE_T_TVA;
	}

	public DecoratedField getFieldCODE_TVA() {
		return fieldCODE_TVA;
	}

	public Group getGroupPrix() {
		return groupPrix;
	}

	public Group getGroupArticle() {
		return groupArticle;
	}

	public Group getGroupRapportUnite() {
		return groupRapportUnite;
	}

	public Group getGroupCompta() {
		return groupCompta;
	}

	public Label getLaCODE_UNITE() {
		return laCODE_UNITE;
	}

	public Label getLaPRIX_PRIX() {
		return laPRIX_PRIX;
	}

	public Label getLaPRIXTTC_PRIX() {
		return laPRIXTTC_PRIX;
	}

	public Text getTfCODE_UNITE() {
		return tfCODE_UNITE;
	}

	public Text getTfPRIXTTC_PRIX() {
		return tfPRIXTTC_PRIX;
	}

	public Text getTfPRIX_PRIX() {
		return tfPRIX_PRIX;
	}

	public DecoratedField getFieldCODE_UNITE() {
		return fieldCODE_UNITE;
	}

	public DecoratedField getFieldPRIXTTC_PRIX() {
		return fieldPRIXTTC_PRIX;
	}

	public DecoratedField getFieldPRIX_PRIX() {
		return fieldPRIX_PRIX;
	}

	public Group getGroupStock() {
		return groupStock;
	}

	public Label getLaCODE_UNITE2() {
		return laCODE_UNITE2;
	}

	public Label getLaRAPPORT() {
		return laRAPPORT;
	}

	public Label getLaDECIMAL() {
		return laDECIMAL;
	}

	public Text getTfCODE_UNITE2() {
		return tfCODE_UNITE2;
	}

	public Text getTfRAPPORT() {
		return tfRAPPORT;
	}

	public Text getTfDECIMAL() {
		return tfDECIMAL;
	}

	public DecoratedField getFieldCODE_UNITE2() {
		return fieldCODE_UNITE2;
	}

	public DecoratedField getFieldRAPPORT() {
		return fieldRAPPORT;
	}

	public DecoratedField getFieldDECIMAL() {
		return fieldDECIMAL;
	}

	public Button getCbActif() {
		return cbActif;
	}

	public Label getLaSensRapport() {
		return laSensRapport;
	}

	public Combo getCbSensRapport() {
		return cbSensRapport;
	}

	public Label getLaU1PhraseRapport() {
		return laU1PhraseRapport;
	}

	public Label getLaRapportPhraseRapport() {
		return laRapportPhraseRapport;
	}

	public Label getLaEgalePhraseRapport() {
		return laEgalePhraseRapport;
	}

	public Label getLaU2PhraseRapport() {
		return laU2PhraseRapport;
	}

	public Label getLaLongueur() {
		return laLongueur;
	}

	public Label getLaLargeur() {
		return laLargeur;
	}

	public Label getLaHauteur() {
		return laHauteur;
	}

	public Label getLaPoids() {
		return laPoids;
	}

	public Group getGroupDimension() {
		return groupDimension;
	}

	public Text getTfLongueur() {
		return tfLongueur;
	}

	public Text getTfLargeur() {
		return tfLargeur;
	}

	public Text getTfHauteur() {
		return tfHauteur;
	}

	public Text getTfPoids() {
		return tfPoids;
	}

	public DecoratedField getFieldLongueur() {
		return fieldLongueur;
	}

	public DecoratedField getFieldLargeur() {
		return fieldLargeur;
	}

	public DecoratedField getFieldHauteur() {
		return fieldHauteur;
	}

	public DecoratedField getFieldPoids() {
		return fieldPoids;
	}

	public Label getLaActif() {
		return laActif;
	}

	public Label getLaTitreTransportU1() {
		return laTitreTransportU1;
	}

	public Text getTfTitreTransportU1() {
		return tfTitreTransportU1;
	}

	public DecoratedField getFieldTitreTransportU1() {
		return fieldTitreTransportU1;
	}

	public Group getGroupUnite() {
		return groupUnite;
	}

	public Group getGroupCapsule() {
		return groupCapsule;
	}

	public Group getGroupObservation() {
		return groupObservation;
	}

	public Label getLaNombreTitreTransportU1() {
		return laNombreTitreTransportU1;
	}

	public Label getLaUniteTitreTransportU1() {
		return laUniteTitreTransportU1;
	}

	public Combo getCbUniteTitreTransportU1() {
		return cbUniteTitreTransportU1;
	}

	public Text getTfNombreTitreTransportU1() {
		return tfNombreTitreTransportU1;
	}

	public DecoratedField getFieldNombreTitreTransportU1() {
		return fieldNombreTitreTransportU1;
	}

}
