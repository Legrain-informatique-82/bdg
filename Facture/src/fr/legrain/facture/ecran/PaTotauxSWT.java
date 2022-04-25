package fr.legrain.facture.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.gui.PaBtnAvecAssistant;
import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
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
public class PaTotauxSWT extends org.eclipse.swt.widgets.Composite {
//public class PaTotauxSWT extends ScrolledComposite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	private Composite paEcran;
	private Table tableTVA;
	private Label laTX_REM_HT_FACTURE;
	private Text tfCODE_T_PAIEMENT;
	private Label laCODE_T_PAIEMENT;
	private Text tfLIBELLE_PAIEMENT;
	private ExpandBar expandBarCondition;
	private Label laDATE_ECH_FACTURE;
	private DateTime dateTimeDATE_ECH_FACTURE;
	private Label laReglementsIndirects;
	private Group group1;
	private Label label1;
	private Label laPourcentage;
	private Group groupNetAPayer;
	private Group groupResteARegler;
	private Group groupRemise;
	private Text tfRESTE_A_REGLER;
	private Text tfACOMPTES;
	private Text tfAVOIRS;
	private Text tfMT_TTC_AVANT_REMISE;
	private Text tfMT_TVA_AVANT_REMISE;
	private Text tfMT_HT_AVANT_REMISE;
	private Text tfREGLE_FACTURE;
	private Text tfMT_TVA_APRES_REMISE;
	private Label laMT_TVA_CALC;
	private Button cbImprimer;
	private Button cbAffectationStricte;
	private Text tfMT_TTC_APRES_REMISE;
	private Label laMT_TTC_CALC;
	private Text tfMT_ESCOMPTE;
	private Text tfMT_REMISE;
	private Text tfNET_A_PAYER;
	private Label laResteARegle;
	private Label laLibellePaiement;
	private Label laMT_PAIEMENT;
	private Label laAcomptes;
	private Label laAvoirs;
	private Label laAvertissement;
	private Label laAvertissementAvoirs;
	private Label laNET_A_PAYER;
	private Label laMT_ESCOMPTE;
	private Label laMT_REMISE_HT;
	private Label laMT_TTC_AVANT_REMISE;
	private Label laMT_TVA_AVANT_REMISE;
	private Label laMT_HT_AVANT_REMISE;
	private Group groupTVA;
	private Group groupReglement;
	private Group groupEscompte;
	private Group groupTotaux;
	private Group groupTotauxAvantRemise;
	private ExpandBar expandBar;
	private PaBtnAvecAssistant paBtnAvecAssistant;
	private Label laMessage;
	private Text tfMT_HT_APRES_REMISE;
	private Label laMT_HT_CALC;
	private Text tfTX_REM_TTC_FACTURE;
	private Label laTX_REM_TTC_FACTURE;
	private Text tfTX_REM_HT_FACTURE;
	private Label laEtiquette;
	private Combo cbListeParamEtiquette;
	private Table tableCourrier;
	private Button btnOuvreRepertoireCourrier;
	private Button btnFusionPublipostage;
	private Button btnPublipostage;
	private Group groupCourrier;
	private Button cbImprimerCourrier;
	
//	private Button btnEmail;
	
	private DecoratedField fieldMT_TTC_CALC;
	private DecoratedField fieldTTC;
	private DecoratedField fieldTX_REM_TTC_FACTURE;
	private DecoratedField fieldMT_HT_CALC;
	private DecoratedField fieldTX_REM_HT_FACTURE;
	private DecoratedField fieldREGLE_FACTURE;
	private DecoratedField fieldCODE_T_PAIEMENT;
	private DecoratedField fieldMT_TVA_CALC;
	private DecoratedField fieldImprimer;

	private DecoratedField fieldLIBELLE_PAIEMENT;
	private DecoratedField fieldRESTE_A_REGLER;
	private DecoratedField fieldACOMPTES;
	private DecoratedField fieldAVOIRS;
	private DecoratedField fieldMT_TTC_AVANT_REMISE;
	private DecoratedField fieldMT_TVA_AVANT_REMISE;
	private DecoratedField fieldMT_HT_AVANT_REMISE;
	private DecoratedField fieldMT_ESCOMPTE;
	private DecoratedField fieldMT_REMISE;
	private DecoratedField fieldNET_A_PAYER;
	private DecoratedField fieldDATE_ECH_FACTURE;
	
	private ScrolledComposite scrolledComposite = null;

	public Composite getPaEcran() {
		return paEcran;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}



	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Group inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Group inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaTotauxSWT inst = new PaTotauxSWT(shell, SWT.NULL);
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

	public PaTotauxSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 1;
			this.setLayout(thisLayout);
			this.setLayoutData(null);
			{
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
				GridData ld = new GridData();
				ld.horizontalAlignment = GridData.FILL;
				ld.grabExcessHorizontalSpace = true;
				ld.verticalAlignment = GridData.FILL;
				ld.grabExcessVerticalSpace = true;
				ld.horizontalSpan = 3;
				scrolledComposite.setLayoutData(ld);
				
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				
				/****/
				paEcran = new Composite(scrolledComposite, SWT.NONE);
				GridLayout paEcranLayout = new GridLayout();
				paEcranLayout.numColumns = 5;
				paEcran.setLayout(paEcranLayout);
				
				scrolledComposite.setContent(paEcran);
				scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				
				GridData paEcranLData = new GridData();
				paEcranLData.verticalAlignment = GridData.FILL;
				paEcranLData.grabExcessVerticalSpace = true;
				paEcranLData.horizontalAlignment = GridData.FILL;
				paEcranLData.grabExcessHorizontalSpace = true;
				
				
				groupTotauxAvantRemise = new Group(paEcran, SWT.NONE);
				GridLayout groupRemiseHTLayout = new GridLayout();
				groupRemiseHTLayout.numColumns = 2;
				groupTotauxAvantRemise.setLayout(groupRemiseHTLayout);
				GridData groupRemiseHTLData = new GridData();
				groupRemiseHTLData.verticalAlignment = GridData.FILL;
				groupRemiseHTLData.widthHint = 240;
				groupTotauxAvantRemise.setLayoutData(groupRemiseHTLData);
				groupTotauxAvantRemise.setText("Totaux");
				{
					laMT_HT_AVANT_REMISE = new Label(groupTotauxAvantRemise, SWT.NONE);
					GridData laMT_HT_AVANT_REMISELData = new GridData();
					laMT_HT_AVANT_REMISE.setLayoutData(laMT_HT_AVANT_REMISELData);
					laMT_HT_AVANT_REMISE.setText("HT");
				}
				{
					GridData tfMT_HT_AVANT_REMISELData = new GridData();
					if(!decore){
						tfMT_HT_AVANT_REMISE = new Text(groupTotauxAvantRemise, SWT.BORDER);
						tfMT_HT_AVANT_REMISE.setLayoutData(tfMT_HT_AVANT_REMISELData);
						tfMT_HT_AVANT_REMISELData.heightHint = 13;					
					}else{
						fieldMT_HT_AVANT_REMISE = new DecoratedField(groupTotauxAvantRemise, SWT.BORDER, new TextControlCreator());
						tfMT_HT_AVANT_REMISE = (Text)fieldMT_HT_AVANT_REMISE.getControl();
						fieldMT_HT_AVANT_REMISE.getLayoutControl().setLayoutData(tfMT_HT_AVANT_REMISELData);
					}
					tfMT_HT_AVANT_REMISE.setText("MT_HT_AVANT_REMISE");					
					tfMT_HT_AVANT_REMISELData.widthHint = 153;
				}
				{
					laMT_TVA_AVANT_REMISE = new Label(groupTotauxAvantRemise, SWT.NONE);
					GridData laMT_TVA_AVANT_REMISELData = new GridData();
					laMT_TVA_AVANT_REMISE.setLayoutData(laMT_TVA_AVANT_REMISELData);
					laMT_TVA_AVANT_REMISE.setText("TVA");
				}
				{
					GridData tfMT_TVA_AVANT_REMISELData = new GridData();
					if(!decore){
						tfMT_TVA_AVANT_REMISE = new Text(groupTotauxAvantRemise, SWT.BORDER);
						tfMT_TVA_AVANT_REMISE.setLayoutData(tfMT_TVA_AVANT_REMISELData);
						tfMT_TVA_AVANT_REMISELData.heightHint = 13;
						
					}else{
						fieldMT_HT_AVANT_REMISE = new DecoratedField(groupTotauxAvantRemise, SWT.BORDER, new TextControlCreator());
						tfMT_TVA_AVANT_REMISE = (Text)fieldMT_HT_AVANT_REMISE.getControl();
						fieldMT_HT_AVANT_REMISE.getLayoutControl().setLayoutData(tfMT_TVA_AVANT_REMISELData);
					}
					tfMT_TVA_AVANT_REMISE.setText("MT_TVA_AVANT_REMISE");					
					tfMT_TVA_AVANT_REMISELData.widthHint = 153;
				}
				
				{
					laMT_TTC_AVANT_REMISE = new Label(groupTotauxAvantRemise, SWT.NONE);
					GridData laMT_TTC_AVANT_REMISELData = new GridData();
					laMT_TTC_AVANT_REMISE.setLayoutData(laMT_TTC_AVANT_REMISELData);
					laMT_TTC_AVANT_REMISE.setText("TTC");
				}
				{
					GridData tfMT_TTC_AVANT_REMISELData = new GridData();
					if(!decore){
						tfMT_TTC_AVANT_REMISE = new Text(groupTotauxAvantRemise, SWT.BORDER);
						tfMT_TTC_AVANT_REMISE.setLayoutData(tfMT_TTC_AVANT_REMISELData);
						tfMT_TTC_AVANT_REMISELData.heightHint = 13;
					}else{
						fieldMT_TTC_AVANT_REMISE = new DecoratedField(groupTotauxAvantRemise, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_AVANT_REMISE = (Text)fieldMT_TTC_AVANT_REMISE.getControl();
						fieldMT_TTC_AVANT_REMISE.getLayoutControl().setLayoutData(tfMT_TTC_AVANT_REMISELData);						
					}
					tfMT_TTC_AVANT_REMISE.setText("MT_TTC_AVANT_REMISE");
					tfMT_TTC_AVANT_REMISELData.widthHint = 153;
				}
				
				
				{
					groupTotaux = new Group(paEcran, SWT.NONE);
					GridLayout groupTotauxLayout = new GridLayout();
					groupTotauxLayout.numColumns = 2;
					groupTotaux.setLayout(groupTotauxLayout);
					GridData groupTotauxLData = new GridData();
					groupTotauxLData.verticalAlignment = GridData.FILL;
					groupTotauxLData.widthHint = 240;
					groupTotaux.setLayoutData(groupTotauxLData);
					groupTotaux.setText("Totaux après remise globale");
					{
						laMT_HT_CALC = new Label(groupTotaux, SWT.NONE);
						laMT_HT_CALC.setText("Montant HT");
					}
					{
						GridData tfMT_HT_CALCLData = new GridData();
						if(!decore){
							tfMT_HT_APRES_REMISE = new Text(groupTotaux, SWT.BORDER);
							tfMT_HT_APRES_REMISE.setLayoutData(tfMT_HT_CALCLData);
							tfMT_HT_CALCLData.heightHint = 13;						
						}else{
							fieldMT_HT_CALC = new DecoratedField(groupTotaux, SWT.BORDER, new TextControlCreator());
							tfMT_HT_APRES_REMISE = (Text)fieldMT_HT_CALC.getControl();
							fieldMT_HT_CALC.getLayoutControl().setLayoutData(tfMT_HT_CALCLData);						
						}
						tfMT_HT_APRES_REMISE.setText("MT_HT_CALC");
						tfMT_HT_CALCLData.widthHint = 140;
					} 								
					{
						{
							laMT_TVA_CALC = new Label(groupTotaux, SWT.NONE);
							laMT_TVA_CALC.setText("Montant TVA");
						}
						GridData tfMT_TVA_CALCLData1 = new GridData();
						if(!decore){
							tfMT_TVA_APRES_REMISE = new Text(groupTotaux, SWT.BORDER);
							tfMT_TVA_APRES_REMISE.setLayoutData(tfMT_TVA_CALCLData1);
							tfMT_TVA_CALCLData1.heightHint = 13;
						}else{
							fieldMT_TVA_CALC = new DecoratedField(groupTotaux, SWT.BORDER, new TextControlCreator());
							tfMT_TVA_APRES_REMISE = (Text)fieldMT_TVA_CALC.getControl();
							fieldMT_TVA_CALC.getLayoutControl().setLayoutData(tfMT_TVA_CALCLData1);						
						}
						tfMT_TVA_APRES_REMISE.setText("MT_TVA_CALC");
						tfMT_TVA_CALCLData1.widthHint = 140;
					} 			
				}
				{
					groupReglement = new Group(paEcran, SWT.NONE);
					GridLayout groupReglementLayout = new GridLayout();
					groupReglementLayout.numColumns = 6;
					groupReglement.setLayout(groupReglementLayout);
					GridData groupReglementLData = new GridData();
					groupReglementLData.verticalAlignment = GridData.FILL;
					groupReglementLData.widthHint = 485;
					//groupReglementLData.grabExcessHorizontalSpace = true;
					groupReglementLData.horizontalSpan = 2;
					groupReglementLData.verticalSpan = 2;
					groupReglement.setLayoutData(groupReglementLData);
					groupReglement.setText("Règlements");
					groupReglement.setToolTipText("Acomptes en attente d'affectation");
					{
						laAcomptes = new Label(groupReglement, SWT.NONE);
						GridData laAcomptesLData = new GridData();
						laAcomptes.setLayoutData(laAcomptesLData);
						laAcomptes.setText("Acomptes");
					}
					{
						GridData tfACOMPTESLData = new GridData();
						if(!decore){
							tfACOMPTES = new Text(groupReglement, SWT.BORDER);
							tfACOMPTES.setLayoutData(tfACOMPTESLData);
							tfACOMPTESLData.heightHint = 13;
						}else{
							fieldACOMPTES = new DecoratedField(groupReglement, SWT.BORDER, new TextControlCreator());
							tfACOMPTES = (Text)fieldACOMPTES.getControl();
							fieldACOMPTES.getLayoutControl().setLayoutData(tfACOMPTESLData);						
						}
						tfACOMPTESLData.widthHint = 97;
						tfACOMPTES.setText("ACOMPTES");
					}
					{
						laAvertissement = new Label(groupReglement, SWT.NONE);
						GridData laAcomptesLData = new GridData();
						laAcomptesLData.grabExcessHorizontalSpace = true;
						laAvertissement.setLayoutData(laAcomptesLData);
						laAvertissement.setText("*");
						laAvertissement.setForeground(SWTResourceManager.getColor(255, 0, 0));
						laAvertissement.setFont(SWTResourceManager.getFont("Tahoma", 16, 0, false, false));
						laAvertissement.setToolTipText("Acomptes en attente d'affectation");
					}
					{
						laAvoirs = new Label(groupReglement, SWT.NONE);
						GridData laAcomptesLData = new GridData();
						laAcomptesLData.horizontalAlignment = GridData.END;
						laAvoirs.setLayoutData(laAcomptesLData);
						laAvoirs.setText("Avoirs");
					}
					{
						GridData tfAVOIRSLData = new GridData();
						//tfAVOIRSLData.heightHint = 19;
						tfAVOIRSLData.widthHint = 78;
						
						if(!decore){
							tfAVOIRS = new Text(groupReglement, SWT.BORDER);
							tfAVOIRS.setLayoutData(tfAVOIRSLData);
						}else{
							fieldAVOIRS = new DecoratedField(groupReglement, SWT.BORDER, new TextControlCreator());
							tfAVOIRS = (Text)fieldAVOIRS.getControl();
							fieldAVOIRS.getLayoutControl().setLayoutData(tfAVOIRSLData);						
						}
						tfAVOIRS.setText("AVOIRS");
					}
					{
						laAvertissementAvoirs = new Label(groupReglement, SWT.NONE);
						GridData laAcomptesLData = new GridData();
						laAcomptesLData.heightHint = 26;
						laAvertissementAvoirs.setLayoutData(laAcomptesLData);
						laAvertissementAvoirs.setText("*");
						laAvertissementAvoirs.setForeground(SWTResourceManager.getColor(255, 0, 0));
						laAvertissementAvoirs.setFont(SWTResourceManager.getFont("Tahoma", 16, 0, false, false));
						laAvertissementAvoirs.setToolTipText("Avoirs en attente d'affectation");
					}
					{
						laMT_PAIEMENT = new Label(groupReglement, SWT.NONE);
						GridData laMT_PAIEMENTLData = new GridData();
						laMT_PAIEMENT.setLayoutData(laMT_PAIEMENTLData);
						laMT_PAIEMENT.setText("Montant paiement");
					}
					{
						GridData tfREGLE_FACTURELData1 = new GridData();
						tfREGLE_FACTURELData1.horizontalSpan = 2;
						tfREGLE_FACTURELData1.widthHint = 97;
						//						tfREGLE_FACTURELData1.heightHint = 17;
						
						if(!decore){
							tfREGLE_FACTURE = new Text(groupReglement, SWT.BORDER);
							tfREGLE_FACTURE.setLayoutData(tfREGLE_FACTURELData1);
						}else{
							fieldREGLE_FACTURE = new DecoratedField(groupReglement, SWT.BORDER, new TextControlCreator());
							tfREGLE_FACTURE = (Text)fieldREGLE_FACTURE.getControl();
							fieldREGLE_FACTURE.getLayoutControl().setLayoutData(tfREGLE_FACTURELData1);						
						}
						tfREGLE_FACTURE.setText("REGLE_FACTURE");
						{
							laCODE_T_PAIEMENT = new Label(groupReglement, SWT.NONE);
							laCODE_T_PAIEMENT.setText("Type paiement");
						}
						{
							GridData tfCODE_T_PAIEMENTLData1 = new GridData();
							if(!decore){
								tfCODE_T_PAIEMENT = new Text(groupReglement, SWT.BORDER);
								tfCODE_T_PAIEMENT.setLayoutData(tfCODE_T_PAIEMENTLData1);
							}else{
								fieldCODE_T_PAIEMENT = new DecoratedField(groupReglement, SWT.BORDER, new TextControlCreator());
								tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
								fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData1);						
							}
							tfCODE_T_PAIEMENTLData1.horizontalSpan = 2;
							tfCODE_T_PAIEMENT.setText("CODE_T_PAIEMENT");
							{
								laDATE_ECH_FACTURE = new Label(groupReglement, SWT.NONE);
								GridData laDATE_ECH_FACTURELData = new GridData();
								//laDATE_ECH_FACTURELData.widthHint = 72;
								//laDATE_ECH_FACTURELData.heightHint = 13;
								laDATE_ECH_FACTURE.setLayoutData(laDATE_ECH_FACTURELData);
								laDATE_ECH_FACTURE.setText("Date échéance");
							}
							{
								GridData dateTimeDATE_ECH_FACTURELData = new GridData();
								//dateTimeDATE_ECH_FACTURELData.widthHint = 107;
								//dateTimeDATE_ECH_FACTURELData.heightHint = 17;
								dateTimeDATE_ECH_FACTURELData.horizontalSpan = 2;
								dateTimeDATE_ECH_FACTURELData.grabExcessHorizontalSpace = true;
								dateTimeDATE_ECH_FACTURELData.widthHint = 108;
								//							dateTimeDATE_ECH_FACTURELData.heightHint = 17;
								
								if(!decore) {
									dateTimeDATE_ECH_FACTURE = new DateTime(groupReglement, SWT.BORDER | SWT.DROP_DOWN);
									dateTimeDATE_ECH_FACTURE.setLayoutData(dateTimeDATE_ECH_FACTURELData);
								} else {
									fieldDATE_ECH_FACTURE = new DecoratedField(groupReglement, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
									dateTimeDATE_ECH_FACTURE = (DateTime)fieldDATE_ECH_FACTURE.getControl();
									fieldDATE_ECH_FACTURE.getLayoutControl().setLayoutData(dateTimeDATE_ECH_FACTURELData);
								}
								//							dateTimeDATE_ECH_FACTURE.setPattern("dd/MM/yyyy");	
							}
							
						} 			
					}
					{
						group1 = new Group(paEcran, SWT.NONE);
						GridLayout group1Layout = new GridLayout();
						group1.setLayout(group1Layout);
						GridData group1LData = new GridData();
						group1LData.verticalSpan = 3;
						group1LData.verticalAlignment = GridData.FILL;
						group1LData.grabExcessHorizontalSpace = true;
						group1LData.horizontalAlignment = GridData.FILL;
						group1.setLayoutData(group1LData);
						group1.setVisible(false);
					}
					
					{
						groupRemise = new Group(paEcran, SWT.NONE);
						GridLayout group1Layout = new GridLayout();
						group1Layout.numColumns = 3;
						groupRemise.setLayout(group1Layout);
						GridData group1LData = new GridData();
						group1LData.verticalAlignment = GridData.FILL;
						group1LData.widthHint = 240;
						groupRemise.setLayoutData(group1LData);
						groupRemise.setText("Remise");
						{
							laTX_REM_HT_FACTURE = new Label(groupRemise, SWT.NONE);
							laTX_REM_HT_FACTURE.setText("Remise globale");
						}
						{
							GridData tfTX_REM_HT_FACTURELData = new GridData();
							if(!decore){
								tfTX_REM_HT_FACTURE = new Text(groupRemise, SWT.BORDER);
								tfTX_REM_HT_FACTURE.setLayoutData(tfTX_REM_HT_FACTURELData);
								tfTX_REM_HT_FACTURELData.heightHint = 13;
							}else{
								fieldTX_REM_HT_FACTURE = new DecoratedField(groupRemise, SWT.BORDER, new TextControlCreator());
								tfTX_REM_HT_FACTURE = (Text)fieldTX_REM_HT_FACTURE.getControl();
								fieldTX_REM_HT_FACTURE.getLayoutControl().setLayoutData(tfTX_REM_HT_FACTURELData);						
							}
							tfTX_REM_HT_FACTURELData.widthHint = 47;
							tfTX_REM_HT_FACTURE.setText("TX_REM_HT_FACTURE");
						}
						{
							laPourcentage = new Label(groupRemise, SWT.NONE);
							GridData laPourcentageLData = new GridData();
							laPourcentage.setLayoutData(laPourcentageLData);
							laPourcentage.setText("%");
						}
						{
							laMT_REMISE_HT = new Label(groupRemise, SWT.NONE);
							laMT_REMISE_HT.setText("Remise sur HT");
						}
						{
							GridData tfMT_REMISELData = new GridData();
							if(!decore){
								tfMT_REMISE = new Text(groupRemise, SWT.BORDER);
								tfMT_REMISE.setLayoutData(tfMT_REMISELData);
								tfMT_REMISELData.heightHint = 13;
							}else{
								fieldMT_REMISE = new DecoratedField(groupRemise, SWT.BORDER, new TextControlCreator());
								tfMT_REMISE = (Text)fieldMT_REMISE.getControl();
								fieldMT_REMISE.getLayoutControl().setLayoutData(tfMT_REMISELData);						
							}
							tfMT_REMISELData.widthHint = 121;
							tfMT_REMISELData.horizontalSpan = 2;
							tfMT_REMISE.setText("MT_REMISE");
						}
					}
					
					
					groupEscompte = new Group(paEcran, SWT.NONE);
					GridLayout groupEscompteLayout = new GridLayout();
					groupEscompteLayout.numColumns = 3;
					groupEscompte.setLayout(groupEscompteLayout);
					GridData groupEscompteLData = new GridData();
					groupEscompteLData.verticalAlignment = GridData.FILL;
					groupEscompteLData.widthHint = 240;
					groupEscompte.setLayoutData(groupEscompteLData);
					groupEscompte.setText("Escompte");
					
					{
						laTX_REM_TTC_FACTURE = new Label(groupEscompte, SWT.NONE);
						laTX_REM_TTC_FACTURE.setText("Escompte");
					}
					{
						GridData tfTX_REM_TTC_FACTURELData1 = new GridData();
						if(!decore){
							tfTX_REM_TTC_FACTURE = new Text(groupEscompte, SWT.BORDER);
							tfTX_REM_TTC_FACTURE.setLayoutData(tfTX_REM_TTC_FACTURELData1);
							tfTX_REM_TTC_FACTURELData1.heightHint = 13;
						}else{
							fieldTX_REM_TTC_FACTURE = new DecoratedField(groupEscompte, SWT.BORDER, new TextControlCreator());
							tfTX_REM_TTC_FACTURE = (Text)fieldTX_REM_TTC_FACTURE.getControl();
							fieldTX_REM_TTC_FACTURE.getLayoutControl().setLayoutData(tfTX_REM_TTC_FACTURELData1);						
						}
						tfTX_REM_TTC_FACTURE.setText("TX_REM_TTC_FACTURE");
						tfTX_REM_TTC_FACTURELData1.widthHint = 47;
					}
					{
						{
							label1 = new Label(groupEscompte, SWT.NONE);
							GridData label1LData = new GridData();
							label1LData.widthHint = 60;
							label1LData.heightHint = 17;
							label1.setLayoutData(label1LData);
							label1.setText("%");
						}
						laMT_ESCOMPTE = new Label(groupEscompte, SWT.NONE);
						GridData laMT_ESCOMPTELData = new GridData();
						laMT_ESCOMPTE.setLayoutData(laMT_ESCOMPTELData);
						laMT_ESCOMPTE.setText("Montant escompte");
					}
					
					{
						GridData tfMT_ESCOMPTELData = new GridData();
						tfMT_ESCOMPTELData.horizontalSpan = 2;
						tfMT_ESCOMPTELData.widthHint = 105;
						//tfMT_ESCOMPTELData.heightHint = 17;
						if(!decore){
							tfMT_ESCOMPTE = new Text(groupEscompte, SWT.BORDER);
							tfMT_ESCOMPTE.setLayoutData(tfMT_ESCOMPTELData);
						}else{
							fieldMT_ESCOMPTE = new DecoratedField(groupEscompte, SWT.BORDER, new TextControlCreator());
							tfMT_ESCOMPTE = (Text)fieldMT_ESCOMPTE.getControl();
							fieldMT_ESCOMPTE.getLayoutControl().setLayoutData(tfMT_ESCOMPTELData);						
						}
						tfMT_ESCOMPTE.setText("MT_ESCOMPTE");
					}
				}
				{
					GridData expandBar1LData = new GridData();
					expandBar1LData.heightHint = 94;
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBar1LData.horizontalAlignment = GridData.FILL;
					expandBarCondition = new ExpandBar(paEcran, SWT.V_SCROLL);
					GridData expandBarConditionLData = new GridData();
					expandBarConditionLData.widthHint = 477;
					expandBarConditionLData.heightHint = 94;
					expandBarConditionLData.horizontalSpan = 2;
					expandBarCondition.setLayoutData(expandBarConditionLData);
				}
				
				
				
				{
					groupNetAPayer = new Group(paEcran, SWT.NONE);
					GridLayout group3Layout = new GridLayout();
					group3Layout.numColumns = 2;
					groupNetAPayer.setLayout(group3Layout);
					GridData group3LData = new GridData();
					group3LData.verticalAlignment = GridData.FILL;
					group3LData.horizontalAlignment = GridData.FILL;
					groupNetAPayer.setLayoutData(group3LData);
					{
						laNET_A_PAYER = new Label(groupNetAPayer, SWT.NONE);
						GridData laNET_A_PAYERLData = new GridData();
						laNET_A_PAYER.setLayoutData(laNET_A_PAYERLData);
						laNET_A_PAYER.setText("Net à payer");
					}														{
						GridData tfNET_A_PAYERLData = new GridData();
						if(!decore){
							tfNET_A_PAYER = new Text(groupNetAPayer, SWT.BORDER);
							tfNET_A_PAYER.setLayoutData(tfNET_A_PAYERLData);
							tfNET_A_PAYERLData.heightHint = 13;
						}else{
							fieldNET_A_PAYER = new DecoratedField(groupNetAPayer, SWT.BORDER, new TextControlCreator());
							tfNET_A_PAYER = (Text)fieldNET_A_PAYER.getControl();
							fieldNET_A_PAYER.getLayoutControl().setLayoutData(tfNET_A_PAYERLData);						
						}
						tfNET_A_PAYER.setText("NET_A_PAYER");
						tfNET_A_PAYERLData.widthHint = 116;
					}
				}
				{
					groupResteARegler = new Group(paEcran, SWT.NONE);
					GridLayout group2Layout = new GridLayout();
					group2Layout.numColumns = 4;
					groupResteARegler.setLayout(group2Layout);
					GridData group2LData = new GridData();
					group2LData.verticalAlignment = GridData.FILL;
					group2LData.widthHint = 269;
					groupResteARegler.setLayoutData(group2LData);
					{
						laResteARegle = new Label(groupResteARegler, SWT.NONE);
						GridData laResteARegleLData = new GridData();
						laResteARegleLData.verticalAlignment = GridData.BEGINNING;
						laResteARegle.setLayoutData(laResteARegleLData);
						laResteARegle.setText("Reste à régler");
					}
					{
						GridData tfRESTE_A_REGLERLData = new GridData();
						//tfRESTE_A_REGLERLData.heightHint = 13;
						tfRESTE_A_REGLERLData.verticalAlignment = GridData.BEGINNING;
						tfRESTE_A_REGLERLData.grabExcessHorizontalSpace = true;
						tfRESTE_A_REGLERLData.widthHint = 116;
						if(!decore){
							tfRESTE_A_REGLER = new Text(groupResteARegler, SWT.BORDER);
							tfRESTE_A_REGLER.setLayoutData(tfRESTE_A_REGLERLData);
							
						}else{
							fieldRESTE_A_REGLER = new DecoratedField(groupResteARegler, SWT.BORDER, new TextControlCreator());
							tfRESTE_A_REGLER = (Text)fieldRESTE_A_REGLER.getControl();
							fieldRESTE_A_REGLER.getLayoutControl().setLayoutData(tfRESTE_A_REGLERLData);						
						}
						tfRESTE_A_REGLER.setText("RESTE_A_REGLER");
						//tfRESTE_A_REGLER.setSize(116, 13);
					}
					{
						laReglementsIndirects = new Label(groupResteARegler, SWT.NONE);
						laReglementsIndirects.setText("*");
						laReglementsIndirects.setFont(SWTResourceManager.getFont("Tahoma",16,0,false,false));
						laReglementsIndirects.setForeground(SWTResourceManager.getColor(255,0,0));
						GridData label2LData = new GridData();
						label2LData.horizontalSpan = 2;
						label2LData.verticalAlignment = GridData.FILL;
						label2LData.grabExcessHorizontalSpace = true;
						laReglementsIndirects.setLayoutData(label2LData);
						laReglementsIndirects.setToolTipText("Un ou plusieurs Règlement(s) ont été effectué(s) hors facture."+
								Const.finDeLigne+
								"Ils ne sont pas pris en compte dans la facture et n'apparaîtront pas dans cette écran.");
					}
					
				}
				
				{
					laMT_TTC_CALC = new Label(groupTotaux, SWT.NONE);
					laMT_TTC_CALC.setText("Montant TTC");
				}
				{
					GridData tfMT_TTC_CALCLData = new GridData();
					if(!decore){
						tfMT_TTC_APRES_REMISE = new Text(groupTotaux, SWT.BORDER);
						tfMT_TTC_APRES_REMISE.setLayoutData(tfMT_TTC_CALCLData);
						tfMT_TTC_CALCLData.heightHint = 13;
					}else{
						fieldMT_TTC_CALC = new DecoratedField(groupTotaux, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_APRES_REMISE = (Text)fieldMT_TTC_CALC.getControl();
						fieldMT_TTC_CALC.getLayoutControl().setLayoutData(tfMT_TTC_CALCLData);						
					}
					tfMT_TTC_CALCLData.widthHint = 140;
					tfMT_TTC_APRES_REMISE.setText("MT_TTC_CALC");
				} 							
				
				//			{
					//				laMT_PAIEMENT = new Label(groupReglement, SWT.NONE);
					//				GridData laMT_PAIEMENTLData = new GridData();
					//				laMT_PAIEMENT.setLayoutData(laMT_PAIEMENTLData);
					//				laMT_PAIEMENT.setText("Montant paiement");
					//			}
				//			{
					//				GridData tfREGLE_FACTURELData1 = new GridData();
					//				tfREGLE_FACTURELData1.horizontalSpan = 2;
					//				tfREGLE_FACTURELData1.widthHint = 97;
					////				tfREGLE_FACTURELData1.heightHint = 17;
					//
					//				if(!decore){
						//					tfREGLE_FACTURE = new Text(groupReglement, SWT.BORDER);
						//					tfREGLE_FACTURE.setLayoutData(tfREGLE_FACTURELData1);
						//				}else{
							//					fieldREGLE_FACTURE = new DecoratedField(groupReglement, SWT.BORDER, new TextControlCreator());
							//					tfREGLE_FACTURE = (Text)fieldREGLE_FACTURE.getControl();
							//					fieldREGLE_FACTURE.getLayoutControl().setLayoutData(tfREGLE_FACTURELData1);						
							//				}
					//				tfREGLE_FACTURE.setText("REGLE_FACTURE");
					{
						GridData cbAffectationLData = new GridData();
						cbAffectationLData.horizontalSpan = 3;
						
						cbAffectationStricte = new Button(groupReglement, SWT.CHECK | SWT.LEFT);
						cbAffectationStricte.setLayoutData(cbAffectationLData);
						cbAffectationStricte.setText("Affectation stricte des réglements");
					}
					{
						laLibellePaiement = new Label(groupReglement, SWT.NONE);
						GridData laLibellePaiementLData = new GridData();
						laLibellePaiement.setLayoutData(laLibellePaiementLData);
						laLibellePaiement.setText("Libelle paiement");
					}
					{
						GridData tfLIBELLE_PAIEMENTLData = new GridData();
						if(!decore){
							tfLIBELLE_PAIEMENT = new Text(groupReglement, SWT.MULTI | SWT.WRAP | SWT.BORDER);
							tfLIBELLE_PAIEMENT.setLayoutData(tfLIBELLE_PAIEMENTLData);
						}else{
							fieldLIBELLE_PAIEMENT = new DecoratedField(groupReglement, SWT.MULTI | SWT.WRAP | SWT.BORDER, new TextControlCreator());
							tfLIBELLE_PAIEMENT = (Text)fieldLIBELLE_PAIEMENT.getControl();
							fieldLIBELLE_PAIEMENT.getLayoutControl().setLayoutData(tfLIBELLE_PAIEMENTLData);						
						}
						tfLIBELLE_PAIEMENTLData.horizontalSpan = 5;
						tfLIBELLE_PAIEMENTLData.horizontalAlignment = GridData.FILL;
						tfLIBELLE_PAIEMENTLData.grabExcessHorizontalSpace = true;
						tfLIBELLE_PAIEMENTLData.heightHint = 54;
						tfLIBELLE_PAIEMENT.setText("LIBELLE_PAIEMENT");
					}
					{
						groupTVA = new Group(paEcran, SWT.NONE);
						GridLayout groupTVALayout = new GridLayout();
						groupTVALayout.makeColumnsEqualWidth = true;
						groupTVA.setLayout(groupTVALayout);
						GridData groupTVALData = new GridData();
						groupTVALData.horizontalSpan = 2;
						groupTVALData.verticalAlignment = GridData.BEGINNING;
						groupTVALData.heightHint = 163;
						groupTVALData.horizontalAlignment = GridData.FILL;
						groupTVA.setLayoutData(groupTVALData);
						groupTVA.setText("TVA");
						{
							tableTVA = new Table(groupTVA, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
							tableTVA.setHeaderVisible(true);
							GridData tableTVALData = new GridData();
							tableTVALData.horizontalAlignment = GridData.FILL;
							tableTVALData.grabExcessHorizontalSpace = true;
							tableTVALData.verticalAlignment = GridData.FILL;
							tableTVALData.grabExcessVerticalSpace = true;
							tableTVA.setLayoutData(tableTVALData);
							tableTVA.setLinesVisible(true);
						}
					}
					
					
					{
						Group groupEdition = new Group(paEcran, SWT.NONE);
						GridLayout groupEditionLayout = new GridLayout();
						groupEditionLayout.makeColumnsEqualWidth = false;
						groupEditionLayout.numColumns = 2;
						groupEdition.setLayout(groupEditionLayout);
						GridData groupEditionLData = new GridData();
						groupEditionLData.horizontalSpan = 2;
						groupEditionLData.verticalAlignment = GridData.BEGINNING;
						groupEditionLData.widthHint = 485;
						groupEditionLData.heightHint = 164;
						//groupEditionLData.heightHint = 100;
						groupEdition.setLayoutData(groupEditionLData);
						groupEdition.setText("Zone d'édition");
						
						{
							Group groupImpression = new Group(groupEdition, SWT.NONE);
							GridLayout groupImpressionLayout = new GridLayout();
							groupImpressionLayout.makeColumnsEqualWidth = true;
							groupImpression.setLayout(groupImpressionLayout);
							GridData groupImpressionLData = new GridData();
							groupImpressionLData.horizontalAlignment = GridData.FILL;
							//groupImpressionLData.horizontalSpan = 2;
							groupImpressionLData.verticalAlignment = GridData.BEGINNING;
							//						groupImpressionLData.heightHint = 85;
							groupImpression.setLayoutData(groupImpressionLData);
							groupImpression.setText("");
							{
								GridData cbImprimerLData = new GridData();
								//							cbImprimerLData.heightHint = 16;
								//							cbImprimerLData.horizontalAlignment = GridData.FILL;
								//							cbImprimerLData.horizontalSpan = 6;
								
								if(!decore) {
									cbImprimer = new Button(groupImpression, SWT.CHECK	| SWT.LEFT);
									cbImprimer.setLayoutData(cbImprimerLData);
								} else {
									fieldImprimer = new DecoratedField(groupImpression, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
									cbImprimer = (Button)fieldImprimer.getControl();
									fieldImprimer.getLayoutControl().setLayoutData(cbImprimerLData);
								}
								cbImprimer.setText("Imprimer document");
							}
							
							{
								GridData cbImprimerLData = new GridData();
								cbImprimerCourrier = new Button(groupImpression, SWT.CHECK);
								cbImprimerCourrier.setLayoutData(cbImprimerLData);
								cbImprimerCourrier.setText("Imprimer courriers");
							}
							
//							{
//								btnEmail = new Button(groupImpression, SWT.PUSH);
//								btnEmail.setText("");
//							}
						}
						
						{
							groupCourrier = new Group(groupEdition, SWT.NONE);
							GridLayout groupCourrierLayout = new GridLayout();
							groupCourrierLayout.makeColumnsEqualWidth = true;
							groupCourrierLayout.numColumns = 2;
							groupCourrier.setLayout(groupCourrierLayout);
							GridData groupCourrierLData = new GridData();
							groupCourrierLData.horizontalAlignment = GridData.FILL;
							groupCourrierLData.verticalAlignment = GridData.FILL;
							groupCourrierLData.grabExcessHorizontalSpace = true;
							groupCourrierLData.grabExcessVerticalSpace = true;
							groupCourrierLData.verticalSpan = 3;
							//groupCourrierLData.verticalAlignment = GridData.BEGINNING;
							//						groupCourrierLData.heightHint = 85;
							groupCourrier.setLayoutData(groupCourrierLData);
							groupCourrier.setText("Courrier");
							{
								tableCourrier = new Table(groupCourrier, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER | SWT.CHECK);
								tableCourrier.setHeaderVisible(true);
								GridData tableCourrierLData = new GridData();
								tableCourrierLData.horizontalAlignment = GridData.FILL;
								tableCourrierLData.grabExcessHorizontalSpace = true;
								tableCourrierLData.horizontalSpan = 2;
								tableCourrierLData.verticalAlignment = GridData.FILL;
								tableCourrierLData.grabExcessVerticalSpace = true;
								tableCourrier.setLayoutData(tableCourrierLData);
								tableCourrier.setLinesVisible(true);
							}
							{
								btnFusionPublipostage = new Button(groupCourrier, SWT.CHECK);
								btnFusionPublipostage.setText("Fusionner les courriers");
								GridData btnPublipostageLData = new GridData();
								btnFusionPublipostage.setLayoutData(btnPublipostageLData);
							}
							//						{
								//							btnPublipostage = new Button(groupCourrier, SWT.PUSH);
								//							//btnPublipostage.setText("Publipostage");
								//							GridData btnPublipostageLData = new GridData();
								//							btnPublipostageLData.horizontalAlignment = GridData.END;
								//							btnPublipostage.setLayoutData(btnPublipostageLData);
								//						}
						}
						
						{
							Group groupEtiquette = new Group(groupEdition, SWT.NONE);
							GridLayout groupImpressionLayout = new GridLayout();
							groupImpressionLayout.makeColumnsEqualWidth = true;
							groupEtiquette.setLayout(groupImpressionLayout);
							GridData groupImpressionLData = new GridData();
							groupImpressionLData.horizontalAlignment = GridData.FILL;
							//groupImpressionLData.horizontalSpan = 2;
							groupImpressionLData.verticalAlignment = GridData.BEGINNING;
							//						groupImpressionLData.heightHint = 85;
							groupEtiquette.setLayoutData(groupImpressionLData);
							groupEtiquette.setText("Etiquette");
							//						{
								//							laEtiquette = new Label(groupEtiquette, SWT.NONE);
								//							GridData laEtiquetteLData = new GridData();
								//							laEtiquetteLData.horizontalAlignment = GridData.FILL;
								//							laEtiquette.setLayoutData(laEtiquetteLData);
								//							laEtiquette.setText("Etiquette");
								//						}
							{
								cbListeParamEtiquette = new Combo(groupEtiquette, SWT.READ_ONLY);
								GridData cbListeParamEtiquetteLData = new GridData();
								cbListeParamEtiquetteLData.horizontalSpan = 5;
								cbListeParamEtiquette.setLayoutData(cbListeParamEtiquetteLData);
							}
						}
						
						{
							btnOuvreRepertoireCourrier = new Button(groupEdition, SWT.PUSH);
							//btnPublipostage.setText("Publipostage");
							GridData btnPublipostageLData = new GridData();
							btnPublipostageLData.verticalAlignment = GridData.BEGINNING;
							btnOuvreRepertoireCourrier.setLayoutData(btnPublipostageLData);
						}
						
						
					}
					
					
					{
						GridData expandBarLData = new GridData();
						expandBarLData.horizontalSpan = 7;
						expandBarLData.horizontalAlignment = GridData.FILL;
						expandBarLData.grabExcessHorizontalSpace = true;
						expandBarLData.grabExcessVerticalSpace = true;
						expandBarLData.verticalAlignment = GridData.FILL;
						expandBarLData.minimumHeight = 200;
						expandBar = new ExpandBar(paEcran, SWT.V_SCROLL);
						expandBar.setLayoutData(expandBarLData);
					}
					{
						GridData paBtnAvecAssistantLData = new GridData();
						paBtnAvecAssistantLData.horizontalSpan = 7;
						paBtnAvecAssistantLData.horizontalAlignment = GridData.CENTER;
						paBtnAvecAssistant = new PaBtnAvecAssistant(this, SWT.NONE);
						paBtnAvecAssistant.setLayoutData(paBtnAvecAssistantLData);
					}
					{
						GridData laMessageLData = new GridData();
						laMessageLData.grabExcessHorizontalSpace = true;
						laMessageLData.horizontalAlignment = GridData.FILL;
						laMessageLData.verticalAlignment = GridData.END;
						laMessageLData.heightHint = 10;
						laMessage = new Label(this, SWT.NONE);
						laMessage.setLayoutData(laMessageLData);
					}
				}
				{

	}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Table getTableTVA() {
		return tableTVA;
	}



	public Label getLaTX_REM_HT_FACTURE() {
		return laTX_REM_HT_FACTURE;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}

	public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}

	public Text getTfREGLE_FACTURE() {
		return tfREGLE_FACTURE;
	}


	public Text getTfMT_TVA_APRES_REMISE() {
		return tfMT_TVA_APRES_REMISE;
	}

	public Label getLaMT_TVA_CALC() {
		return laMT_TVA_CALC;
	}

	public Button getCbImprimer() {
		return cbImprimer;
	}

	public Text getTfMT_TTC_APRES_REMISE() {
		return tfMT_TTC_APRES_REMISE;
	}

	public Label getLaMT_TTC_CALC() {
		return laMT_TTC_CALC;
	}

	public Text getTfMT_HT_APRES_REMISE() {
		return tfMT_HT_APRES_REMISE;
	}

	public Label getLaMT_HT_CALC() {
		return laMT_HT_CALC;
	}

	public Text getTfTX_REM_TTC_FACTURE() {
		return tfTX_REM_TTC_FACTURE;
	}

	public Label getLaTX_REM_TTC_FACTURE() {
		return laTX_REM_TTC_FACTURE;
	}

	public Text getTfTX_REM_HT_FACTURE() {
		return tfTX_REM_HT_FACTURE;
	}

	public DecoratedField getFieldMT_TTC_CALC() {
		return fieldMT_TTC_CALC;
	}

	public DecoratedField getFieldTTC() {
		return fieldTTC;
	}

	public DecoratedField getFieldTX_REM_TTC_FACTURE() {
		return fieldTX_REM_TTC_FACTURE;
	}

	public DecoratedField getFieldMT_HT_CALC() {
		return fieldMT_HT_CALC;
	}

	public DecoratedField getFieldTX_REM_HT_FACTURE() {
		return fieldTX_REM_HT_FACTURE;
	}

	public DecoratedField getFieldREGLE_FACTURE() {
		return fieldREGLE_FACTURE;
	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public DecoratedField getFieldMT_TVA_CALC() {
		return fieldMT_TVA_CALC;
	}

	public DecoratedField getFieldImprimer() {
		return fieldImprimer;
	}

	public Label getLaMessage() {
		return laMessage;
	}

	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}

	public ExpandBar getExpandBar() {
		return expandBar;
	}

	public Group getGroupTotauxAvantRemise() {
		return groupTotauxAvantRemise;
	}

	public Group getGroupTotaux() {
		return groupTotaux;
	}

	public Group getGroupEscompte() {
		return groupEscompte;
	}

	public Group getGroupReglement() {
		return groupReglement;
	}

	public Group getGroupTVA() {
		return groupTVA;
	}

	public Label getLaMT_HT_AVANT_REMISE() {
		return laMT_HT_AVANT_REMISE;
	}

	public Label getLaMT_TVA_AVANT_REMISE() {
		return laMT_TVA_AVANT_REMISE;
	}

	public Label getLaMT_TTC_AVANT_REMISE() {
		return laMT_TTC_AVANT_REMISE;
	}

	public Label getLaMT_REMISE_HT() {
		return laMT_REMISE_HT;
	}

	public Label getLaMT_ESCOMPTE() {
		return laMT_ESCOMPTE;
	}

	public Label getLaNET_A_PAYER() {
		return laNET_A_PAYER;
	}

	public Label getLaAcomptes() {
		return laAcomptes;
	}

	public Label getLaMT_PAIEMENT() {
		return laMT_PAIEMENT;
	}

	public Label getLaLibellePaiement() {
		return laLibellePaiement;
	}

	public Label getLaResteARegle() {
		return laResteARegle;
	}

	public Text getTfMT_ESCOMPTE() {
		return tfMT_ESCOMPTE;
	}

	public Text getTfNET_A_PAYER() {
		return tfNET_A_PAYER;
	}

	public Text getTfMT_REMISE() {
		return tfMT_REMISE;
	}

	public Text getTfLIBELLE_PAIEMENT() {
		return tfLIBELLE_PAIEMENT;
	}

	public Text getTfMT_HT_AVANT_REMISE() {
		return tfMT_HT_AVANT_REMISE;
	}

	public Text getTfMT_TVA_AVANT_REMISE() {
		return tfMT_TVA_AVANT_REMISE;
	}

	public Text getTfMT_TTC_AVANT_REMISE() {
		return tfMT_TTC_AVANT_REMISE;
	}

	public Text getTfACOMPTES() {
		return tfACOMPTES;
	}

	public Text getTfRESTE_A_REGLER() {
		return tfRESTE_A_REGLER;
	}

	public Label getLaPourcentage() {
		return laPourcentage;
	}

	public Group getGroupNetAPayer() {
		return groupNetAPayer;
	}

	public Group getGroupResteARegler() {
		return groupResteARegler;
	}

	public Group getGroupRemise() {
		return groupRemise;
	}

	public DecoratedField getFieldLIBELLE_PAIEMENT() {
		return fieldLIBELLE_PAIEMENT;
	}

	public DecoratedField getFieldRESTE_A_REGLER() {
		return fieldRESTE_A_REGLER;
	}

	public DecoratedField getFieldACOMPTES() {
		return fieldACOMPTES;
	}

	public DecoratedField getFieldMT_TTC_AVANT_REMISE() {
		return fieldMT_TTC_AVANT_REMISE;
	}

	public DecoratedField getFieldMT_TVA_AVANT_REMISE() {
		return fieldMT_TVA_AVANT_REMISE;
	}

	public DecoratedField getFieldMT_HT_AVANT_REMISE() {
		return fieldMT_HT_AVANT_REMISE;
	}

	public DecoratedField getFieldMT_ESCOMPTE() {
		return fieldMT_ESCOMPTE;
	}

	public DecoratedField getFieldMT_REMISE() {
		return fieldMT_REMISE;
	}

	public DecoratedField getFieldNET_A_PAYER() {
		return fieldNET_A_PAYER;
	}

	public Button getCbAffectationStricte() {
		return cbAffectationStricte;
	}

	public Label getLaAvertissement() {
		return laAvertissement;
	}

	public Label getLaReglementsIndirects() {
		return laReglementsIndirects;
	}

	public Combo getCbListeParamEtiquette() {
		return cbListeParamEtiquette;
	}

	public Label getLaEtiquette() {
		return laEtiquette;
	}

	public Text getTfAVOIRS() {
		return tfAVOIRS;
	}

	public Label getLaAvoirs() {
		return laAvoirs;
	}

	public DecoratedField getFieldAVOIRS() {
		return fieldAVOIRS;
	}

	public Label getLaAvertissementAvoirs() {
		return laAvertissementAvoirs;
	}

	public Table getTableCourrier() {
		return tableCourrier;
	}

	public Button getBtnOuvreRepertoireCourrier() {
		return btnOuvreRepertoireCourrier;
	}

	public Button getBtnFusionPublipostage() {
		return btnFusionPublipostage;
	}

	public Button getBtnPublipostage() {
		return btnPublipostage;
	}

	public Group getGroupCourrier() {
		return groupCourrier;
	}

	public Button getCbImprimerCourrier() {
		return cbImprimerCourrier;
	}
	
	public Label getLaDATE_ECH_FACTURE() {
		return laDATE_ECH_FACTURE;
	}

	public DateTime getDateTimeDATE_ECH_FACTURE() {
		return dateTimeDATE_ECH_FACTURE;
	}

	public DecoratedField getFieldDATE_ECH_FACTURE() {
		return fieldDATE_ECH_FACTURE;
	}
	


	public ExpandBar getExpandBarCondition() {
		return expandBarCondition;
	}

//	public Button getBtnEmail() {
//		return btnEmail;
//	}

}
