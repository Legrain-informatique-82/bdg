package fr.legrain.licence.ecrans;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

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
public class PaLicenceSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSansGrille {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	

	private Composite paCorpsFormulaire;
	
	private Label laCodeTiers;
	private Label laCodeSupport;
	private Label laCodeArticle;
	private Label laDateAcquisition;
	private Label laGroupe;
	private Label laCommercial;
	private Label laNomUtilisateur;
	private Label laPrenomUtilisateur;
	private Label laTelUtilisateur;
	private Label laEmailUtilisateur;
	private Label laDateDebAbon;
	private Label laDateFinAbon;
	private Label laNomTiers;
	private Label laPrenomTiers;
	private Label laCodeEntite;
	private Label laEntreprise;

	private Text tfCodeSupport;
	private Text tfCodeArticle;
	private DateTime tfDateAcquisition;
	private Text tfGroupe;
	private Text tfCommercial;
	private Text tfNomUtilisateur;
	private Text tfPrenomUtilisateur;
	private Text tfTelUtilisateur;
	private Text tfEmailUtilisateur;
	private Text tfCommentaire;
	private DateTime tfDateDebAbon;
	private DateTime tfDateFinAbon;
	
	private Text tfNomTiers;
	private Text tfPrenomTiers;
	private Text tfCodeEntite;
	private Text tfEntreprise;
	

	private Group groupSupport;
	private Button btnListeAbonnements;
	private Button btnTelechargement;
	private Button BtnInActif;
	private Button btnTiers;
	private Button btnAbonnement;
	private Button btnComptePoint;
	private Label laHorsAbon;
	private Group groupUtilisateur;
	private Group groupCommercial;
	private Group groupTiers;
	private Group groupComplement;
	private Group groupAbonnement;
	private Group groupCommentaire;
	
	private DecoratedField fieldCodeSupport;
	private DecoratedField fieldCodeArticle;
	private DecoratedField fieldDateAcquisition;
//	private DecoratedField fieldNbPoste;
//	private DecoratedField fieldNbDossier;
	private DecoratedField fieldGroupe;
	private DecoratedField fieldCommercial;
	private DecoratedField fieldNomUtilisateur;
	private DecoratedField fieldPrenomUtilisateur;
	private DecoratedField fieldTelUtilisateur;
	private DecoratedField fieldEmailUtilisateur;
	private DecoratedField fieldCommentaire;
	private DecoratedField fieldDateDebAbon;
	private DecoratedField fieldDateFinAbon;
	
	private DecoratedField fieldNomTiers;
	private DecoratedField fieldPrenomTiers;
	private DecoratedField fieldCodeEntite;
	private DecoratedField fieldEntreprise;
	
	final private boolean decore = LgrMess.isDECORE();

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
		PaLicenceSWT inst = new PaLicenceSWT(shell, SWT.NULL);
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




	public PaLicenceSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);

				{
					groupSupport = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupSupport.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSupport.setLayoutData(groupSuppportLData);
					groupSupport.setText("Licence");

					{
						laCodeSupport = new Label(groupSupport, SWT.NONE);
						laCodeSupport.setText("Code");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.widthHint = 112;
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCodeSupport = new Text(groupSupport, SWT.BORDER);
							tfCodeSupport.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldCodeSupport = new DecoratedField(groupSupport, SWT.BORDER, new TextControlCreator());
							tfCodeSupport = (Text)fieldCodeSupport.getControl();
							fieldCodeSupport.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
					{
						laCodeArticle = new Label(groupSupport, SWT.NONE);
						laCodeArticle.setText("Article");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						tfCodeSupportLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCodeArticle = new Text(groupSupport, SWT.BORDER);
							tfCodeArticle.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldCodeArticle = new DecoratedField(groupSupport, SWT.BORDER, new TextControlCreator());
							tfCodeArticle = (Text)fieldCodeArticle.getControl();
							fieldCodeArticle.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
					{
						laDateAcquisition = new Label(groupSupport, SWT.NONE);
						laDateAcquisition.setText("Date acquisition");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;

					if(!decore) {
						tfDateAcquisition = new DateTime(groupSupport, SWT.BORDER | SWT.DROP_DOWN);
						tfDateAcquisition.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateAcquisition = new DecoratedField(groupSupport, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateAcquisition = (DateTime)fieldDateAcquisition.getControl();
							fieldDateAcquisition.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						btnTelechargement = new Button(groupSupport, SWT.CHECK | SWT.LEFT);
						GridData btnTelechargementLData = new GridData();
						btnTelechargement.setLayoutData(btnTelechargementLData);
						btnTelechargement.setText("Téléchargement");
					}
				}
				{
					groupUtilisateur = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupUtilisateur.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.grabExcessHorizontalSpace = true;
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupUtilisateur.setLayoutData(groupSuppportLData);
					groupUtilisateur.setText("Utilisateur");
					{
						laNomUtilisateur = new Label(groupUtilisateur, SWT.NONE);
						laNomUtilisateur.setText("Nom");
					}
					{
						GridData tfNomLData = new GridData();
						tfNomLData.verticalAlignment = GridData.FILL;
						tfNomLData.grabExcessHorizontalSpace = true;
						tfNomLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfNomUtilisateur = new Text(groupUtilisateur, SWT.BORDER);
							tfNomUtilisateur.setLayoutData(tfNomLData);
						} else {					
							fieldNomUtilisateur = new DecoratedField(groupUtilisateur, SWT.BORDER, new TextControlCreator());
							tfNomUtilisateur = (Text)fieldNomUtilisateur.getControl();
							fieldNomUtilisateur.getLayoutControl().setLayoutData(tfNomLData);
						}
					}
					{
						laPrenomUtilisateur = new Label(groupUtilisateur, SWT.NONE);
						laPrenomUtilisateur.setText("Prénom");
					}
					{
						GridData tfPrenomLData = new GridData();
						tfPrenomLData.horizontalAlignment = GridData.FILL;
						tfPrenomLData.verticalAlignment = GridData.FILL;
						tfPrenomLData.grabExcessHorizontalSpace = true;
						if(!decore) {
							tfPrenomUtilisateur = new Text(groupUtilisateur, SWT.BORDER);
							tfPrenomUtilisateur.setLayoutData(tfPrenomLData);
						} else {					
							fieldPrenomUtilisateur = new DecoratedField(groupUtilisateur, SWT.BORDER, new TextControlCreator());
							tfPrenomUtilisateur = (Text)fieldPrenomUtilisateur.getControl();
							fieldPrenomUtilisateur.getLayoutControl().setLayoutData(tfPrenomLData);
						}
					}
					
					
					{
						laTelUtilisateur = new Label(groupUtilisateur, SWT.NONE);
						laTelUtilisateur.setText("Téléphone");
					}
					{
						GridData tfTelLData = new GridData();
						tfTelLData.verticalAlignment = GridData.FILL;
						tfTelLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfTelUtilisateur = new Text(groupUtilisateur, SWT.BORDER);
							tfTelUtilisateur.setLayoutData(tfTelLData);
						} else {					
							fieldTelUtilisateur = new DecoratedField(groupUtilisateur, SWT.BORDER, new TextControlCreator());
							tfTelUtilisateur = (Text)fieldTelUtilisateur.getControl();
							fieldTelUtilisateur.getLayoutControl().setLayoutData(tfTelLData);
						}
					}
					
					{
						laEmailUtilisateur = new Label(groupUtilisateur, SWT.NONE);
						laEmailUtilisateur.setText("Email");
					}
					{
						GridData tfEmailLData = new GridData();
						tfEmailLData.horizontalAlignment = GridData.FILL;
						tfEmailLData.verticalAlignment = GridData.FILL;
						tfEmailLData.grabExcessHorizontalSpace = true;
						if(!decore) {
							tfEmailUtilisateur = new Text(groupUtilisateur, SWT.BORDER);
							tfEmailUtilisateur.setLayoutData(tfEmailLData);
						} else {					
							fieldEmailUtilisateur = new DecoratedField(groupUtilisateur, SWT.BORDER, new TextControlCreator());
							tfEmailUtilisateur = (Text)fieldEmailUtilisateur.getControl();
							fieldEmailUtilisateur.getLayoutControl().setLayoutData(tfEmailLData);
						}
					}
				}

				
				{
					groupCommercial = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupCommercial.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupCommercial.setLayoutData(groupSuppportLData);
					groupCommercial.setText("Commercial");
					{
						laGroupe = new Label(groupCommercial, SWT.NONE);
						laGroupe.setText("Goupe");
					}
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfGroupe = new Text(groupCommercial, SWT.BORDER);
							tfGroupe.setLayoutData(tfGroupeLData);
						} else {					
							fieldGroupe = new DecoratedField(groupCommercial, SWT.BORDER, new TextControlCreator());
							tfGroupe = (Text)fieldGroupe.getControl();
							fieldGroupe.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						laCommercial = new Label(groupCommercial, SWT.NONE);
						laCommercial.setText("Commercial");
					}
					{
						GridData tfCommercialLData = new GridData();
						tfCommercialLData.horizontalAlignment = GridData.FILL;
						tfCommercialLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCommercial = new Text(groupCommercial, SWT.BORDER);
							tfCommercial.setLayoutData(tfCommercialLData);
						} else {					
							fieldCommercial = new DecoratedField(groupCommercial, SWT.BORDER, new TextControlCreator());
							tfCommercial = (Text)fieldCommercial.getControl();
							fieldCommercial.getLayoutControl().setLayoutData(tfCommercialLData);
						}
					}
				}
				{
					groupTiers = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupTiers.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupTiers.setLayoutData(groupSuppportLData);
					groupTiers.setText("Identité Tiers");
					{
						laCodeTiers = new Label(groupTiers, SWT.NONE);
						GridData laCodeTiersLData = new GridData();
						laCodeTiersLData.horizontalSpan = 2;
						laCodeTiersLData.horizontalAlignment = GridData.FILL;
						laCodeTiers.setLayoutData(laCodeTiersLData);
						laCodeTiers.setText("Code tiers :");
						laCodeTiers.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						laNomTiers = new Label(groupTiers, SWT.NONE);
						laNomTiers.setText("Nom Tiers");
					}
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfNomTiers = new Text(groupTiers, SWT.BORDER);
							tfNomTiers.setLayoutData(tfGroupeLData);
							tfNomTiers.setEditable(false);
						} else {					
							fieldNomTiers = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfNomTiers = (Text)fieldNomTiers.getControl();
							fieldNomTiers.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						laPrenomTiers = new Label(groupTiers, SWT.NONE);
						laPrenomTiers.setText("Prénom Tiers");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfPrenomTiers = new Text(groupTiers, SWT.BORDER);
							tfPrenomTiers.setLayoutData(tfGroupeLData);
							tfPrenomTiers.setEditable(false);
						} else {					
							fieldPrenomTiers = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfPrenomTiers = (Text)fieldPrenomTiers.getControl();
							fieldPrenomTiers.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						laCodeEntite = new Label(groupTiers, SWT.NONE);
						laCodeEntite.setText("Entité");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfCodeEntite = new Text(groupTiers, SWT.BORDER);
							tfCodeEntite.setLayoutData(tfGroupeLData);
							tfCodeEntite.setEditable(false);
						} else {					
							fieldCodeEntite = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfCodeEntite = (Text)fieldCodeEntite.getControl();
							fieldCodeEntite.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}	
					{
						laEntreprise = new Label(groupTiers, SWT.NONE);
						laEntreprise.setText("Entreprise");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfEntreprise = new Text(groupTiers, SWT.BORDER);
							tfEntreprise.setLayoutData(tfGroupeLData);
							tfEntreprise.setEditable(false);
						} else {
							fieldEntreprise = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfEntreprise = (Text)fieldEntreprise.getControl();
							fieldEntreprise.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						btnTiers = new Button(groupTiers, SWT.PUSH | SWT.CENTER);
						GridData btnTiersLData = new GridData();
						btnTiersLData.horizontalSpan = 2;
						btnTiers.setLayoutData(btnTiersLData);
						btnTiers.setText("Fiche Tiers");
					}
				}
				{
					groupCommentaire = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupCommentaireLayout = new GridLayout();
					groupCommentaire.setLayout(groupCommentaireLayout);
					GridData groupCommentaireLData = new GridData();
					groupCommentaireLData.verticalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessVerticalSpace = true;
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessHorizontalSpace = true;
					groupCommentaire.setLayoutData(groupCommentaireLData);
					groupCommentaire.setText("Commentaire");
					{
						GridData tfCOMMENTAIRELData = new GridData();
						tfCOMMENTAIRELData.horizontalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.verticalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.grabExcessVerticalSpace = true;
						tfCOMMENTAIRELData.grabExcessHorizontalSpace = true;
						if(!decore) {
							tfCommentaire = new Text(groupCommentaire, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
							tfCommentaire.setLayoutData(tfCOMMENTAIRELData);
						} else {					
							fieldCommentaire = new DecoratedField(groupCommentaire, SWT.MULTI
									| SWT.WRAP
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER, new TextControlCreator());
							tfCommentaire = (Text)fieldCommentaire.getControl();
							fieldCommentaire.getLayoutControl().setLayoutData(tfCOMMENTAIRELData);
						}
					}
				}
				{
					groupAbonnement = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 3;
					groupAbonnement.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupAbonnement.setLayoutData(groupSuppportLData);
					groupAbonnement.setText("Abonnement");
					{
						laDateDebAbon = new Label(groupAbonnement, SWT.NONE);
						laDateDebAbon.setText("Date début");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;
						dateTimeDATELData.horizontalAlignment = GridData.END;
						dateTimeDATELData.horizontalSpan = 2;

					if(!decore) {
						tfDateDebAbon = new DateTime(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN);
						tfDateDebAbon.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateDebAbon = new DecoratedField(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateDebAbon = (DateTime)fieldDateDebAbon.getControl();
							fieldDateDebAbon.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laDateFinAbon = new Label(groupAbonnement, SWT.NONE);
						laDateFinAbon.setText("Date fin");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;
						dateTimeDATELData.horizontalAlignment = GridData.END;
						dateTimeDATELData.horizontalSpan = 2;

					if(!decore) {
						tfDateFinAbon = new DateTime(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN);
						tfDateFinAbon.setLayoutData(dateTimeDATELData);
						} else {							
							fieldDateDebAbon = new DecoratedField(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateFinAbon = (DateTime)fieldDateDebAbon.getControl();
							fieldDateDebAbon.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					{
						btnAbonnement = new Button(groupAbonnement, SWT.PUSH | SWT.CENTER);
						GridData btnAbonnementLData = new GridData();
						btnAbonnement.setLayoutData(btnAbonnementLData);
						btnAbonnement.setText("Abonnement");
					}
					{
						btnComptePoint = new Button(groupAbonnement, SWT.PUSH | SWT.CENTER);
						GridData btnAbonnementLData = new GridData();
						btnComptePoint.setLayoutData(btnAbonnementLData);
						btnComptePoint.setText("Compte point");
					}					
					{
						btnListeAbonnements = new Button(groupAbonnement, SWT.PUSH | SWT.CENTER);
						GridData btnListeAbonnementsLData = new GridData();
						btnListeAbonnementsLData.widthHint = 112;
						btnListeAbonnementsLData.heightHint = 25;
						btnListeAbonnements.setLayoutData(btnListeAbonnementsLData);
						btnListeAbonnements.setText("Liste abonnements");
					}
					
					{
						BtnInActif = new Button(groupAbonnement, SWT.CHECK | SWT.LEFT);
						GridData BtnInActifLData = new GridData();
						BtnInActifLData.horizontalAlignment = GridData.END;
						BtnInActif.setLayoutData(BtnInActifLData);
						BtnInActif.setText("n'utilise plus");
					}
					}
					{
						laHorsAbon = new Label(groupAbonnement, SWT.NONE);
						GridData laHorsAbonLData = new GridData();
						laHorsAbonLData.horizontalAlignment = GridData.FILL;
						laHorsAbonLData.grabExcessHorizontalSpace = true;
						laHorsAbon.setLayoutData(laHorsAbonLData);
						laHorsAbon.setText("Hors abonnement !!!");
						laHorsAbon.setForeground(SWTResourceManager.getColor(255, 0, 0));
						laHorsAbon.setFont(SWTResourceManager.getFont("Tahoma", 10, 3, false, false));
					}
				}
			}

			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(978, 499);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessHorizontalSpace = true;
			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			super.getPaFomulaire().setLayoutData(paFomulaireLData);
			GridData grilleLData = new GridData();
			grilleLData.verticalAlignment = GridData.FILL;
			grilleLData.horizontalAlignment = GridData.FILL;
			grilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}





	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Label getLaCodeSupport() {
		return laCodeSupport;
	}

	public Text getTfCodeSupport() {
		return tfCodeSupport;
	}

	public DecoratedField getFieldCodeSupport() {
		return fieldCodeSupport;
	}

	public Label getLaCodeArticle() {
		return laCodeArticle;
	}

	public Label getLaDateAcquisition() {
		return laDateAcquisition;
	}
//
//	public Label getLaNbPoste() {
//		return laNbPoste;
//	}
//
//	public Label getLaNbDossier() {
//		return laNbDossier;
//	}

	public Label getLaGroupe() {
		return laGroupe;
	}

	public Label getLaCommercial() {
		return laCommercial;
	}

	public Label getLaNomUtilisateur() {
		return laNomUtilisateur;
	}

	public Label getLaPrenomUtilisateur() {
		return laPrenomUtilisateur;
	}

	public Label getLaTelUtilisateur() {
		return laTelUtilisateur;
	}

	public Label getLaEmailUtilisateur() {
		return laEmailUtilisateur;
	}

	public Label getLaDateDebAbon() {
		return laDateDebAbon;
	}

	public Label getLaDateFinAbon() {
		return laDateFinAbon;
	}

	public Text getTfCodeArticle() {
		return tfCodeArticle;
	}

	public DateTime getTfDateAcquisition() {
		return tfDateAcquisition;
	}

//	public Text getTfNbPoste() {
//		return tfNbPoste;
//	}
//
//	public Text getTfNbDossier() {
//		return tfNbDossier;
//	}

	public Text getTfGroupe() {
		return tfGroupe;
	}

	public Text getTfCommercial() {
		return tfCommercial;
	}

	public Text getTfNomUtilisateur() {
		return tfNomUtilisateur;
	}

	public Text getTfPrenomUtilisateur() {
		return tfPrenomUtilisateur;
	}

	public Text getTfTelUtilisateur() {
		return tfTelUtilisateur;
	}

	public Text getTfEmailUtilisateur() {
		return tfEmailUtilisateur;
	}

	public Text getTfCommentaire() {
		return tfCommentaire;
	}

	public DateTime getTfDateDebAbon() {
		return tfDateDebAbon;
	}

	public DateTime getTfDateFinAbon() {
		return tfDateFinAbon;
	}

	public Group getGroupSupport() {
		return groupSupport;
	}

	public Group getGroupUtilisateur() {
		return groupUtilisateur;
	}

	public Group getGroupCommercial() {
		return groupCommercial;
	}

	public Group getGroupComplement() {
		return groupComplement;
	}

	public Group getGroupAbonnement() {
		return groupAbonnement;
	}

	public Group getGroupCommentaire() {
		return groupCommentaire;
	}

	public DecoratedField getFieldCodeArticle() {
		return fieldCodeArticle;
	}

	public DecoratedField getFieldDateAcquisition() {
		return fieldDateAcquisition;
	}

	public DecoratedField getFieldGroupe() {
		return fieldGroupe;
	}

	public DecoratedField getFieldCommercial() {
		return fieldCommercial;
	}

	public DecoratedField getFieldNomUtilisateur() {
		return fieldNomUtilisateur;
	}

	public DecoratedField getFieldPrenomUtilisateur() {
		return fieldPrenomUtilisateur;
	}

	public DecoratedField getFieldTelUtilisateur() {
		return fieldTelUtilisateur;
	}

	public DecoratedField getFieldEmailUtilisateur() {
		return fieldEmailUtilisateur;
	}

	public DecoratedField getFieldCommentaire() {
		return fieldCommentaire;
	}

	public DecoratedField getFieldDateDebAbon() {
		return fieldDateDebAbon;
	}

	public DecoratedField getFieldDateFinAbon() {
		return fieldDateFinAbon;
	}
	
	public Label getLaHorsAbon() {
		return laHorsAbon;
	}

	public Label getLaCodeTiers() {
		return laCodeTiers;
	}

	public Button getBtnAbonnement() {
		return btnAbonnement;
	}

	public Group getGroupTiers() {
		return groupTiers;
	}

	public Text getTfNomTiers() {
		return tfNomTiers;
	}

	public Text getTfPrenomTiers() {
		return tfPrenomTiers;
	}

	public Text getTfCodeEntite() {
		return tfCodeEntite;
	}

	public Text getTfEntreprise() {
		return tfEntreprise;
	}

	public Label getLaNomTiers() {
		return laNomTiers;
	}

	public DecoratedField getFieldNomTiers() {
		return fieldNomTiers;
	}

	public DecoratedField getFieldPrenomTiers() {
		return fieldPrenomTiers;
	}

	public DecoratedField getFieldCodeEntite() {
		return fieldCodeEntite;
	}

	public DecoratedField getFieldEntreprise() {
		return fieldEntreprise;
	}

	public Label getLaPrenomTiers() {
		return laPrenomTiers;
	}

	public Label getLaCodeEntite() {
		return laCodeEntite;
	}

	public Label getLaEntreprise() {
		return laEntreprise;
	}
	
	public Button getBtnTiers() {
		return btnTiers;
	}
	
	public Button getBtnInActif() {
		return BtnInActif;
	}

	public Button getBtnComptePoint() {
		return btnComptePoint;
	}
	
	public Button getBtnTelechargement() {
		return btnTelechargement;
	}

	public Button getBtnListeAbonnements() {
		return btnListeAbonnements;
	}

}
