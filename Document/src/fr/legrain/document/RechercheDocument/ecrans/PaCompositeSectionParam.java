/**
 * PaSectionParam.java
 */
package fr.legrain.document.RechercheDocument.ecrans;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;



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
/**
 * Classe "remake" de la classe présente dans
 * fr.legrain.articles.statistiques.editors > DefaultFormPage
 * != car utilise DateTime au lieu du CDateTime
 * @author nicolas²
 *
 */
public class PaCompositeSectionParam {



	// icons
	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	public static String iconFindPath = "/icons/find.png";
	
	// Composite
	private Composite compo = null;

	// Elements graphiques
	private Label label = null;
	private DateTime cdateDeb = null;
	private DateTime cdateFin = null;
	private ToolBarManager sectionToolbar = null;
	private Text tfCodeTiers = null;
	private Text tfCodeDocument = null;
	private Text tfNomTiers = null;
	private Combo cbTypeDoc = null;
//	private Button btnNonRegle = null;
	private Button btnRefesh = null;
	private Button btnAideTiers = null;
	private Button btnAideDoc = null;
	private Button btnAideNomTiers = null;
	private ImageHyperlink ihlRefresh = null;


	// Consutrcteur
	public PaCompositeSectionParam(Composite compo,FormToolkit toolkit) {
		// Init du composite
		this.compo = compo;
		Composite compoGauche = new Composite(compo, 0);
		Composite compoDroite = new Composite(compo, 0);
		// Informations sur la période -- START
		//label = toolkit.createLabel(compo, "Echéance"); 
		//label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,4,1));
		
		/*
		 * Si etat == en cours
		 * 		tous les devis "en cours" avec une échéance antérieure à la date du jour
		 * 		+ tous les devis devis arrivant à échéance avant date du jour + nb jour (1 seule date + variable)
		 * Si etat != en cours
		 * 		tous les devis ayant une échéance entre date deb et date fin + nb jour (2 dates + variables)
		 */

		toolkit.createLabel(compoGauche, "Type document"); 
		cbTypeDoc = new Combo(compoGauche,  SWT.BORDER);
		cbTypeDoc.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
		
		toolkit.createLabel(compoDroite, "Code document"); 
		tfCodeDocument = toolkit.createText(compoDroite, "", SWT.BORDER); 
		tfCodeDocument.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		btnAideDoc = toolkit.createButton(compoDroite, "Aide doc", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideTiersGridData = new GridData(SWT.FILL,SWT.NONE,false,false,1,1);
		//btnAideTiersGridData.horizontalAlignment = SWT.BEGINNING;
		btnAideDoc.setLayoutData(btnAideTiersGridData);
		
		toolkit.createLabel(compoGauche, "Date debut"); 
		cdateDeb = new DateTime(compoGauche,  SWT.BORDER | SWT.DROP_DOWN);
		cdateDeb.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,2,1));
		
		toolkit.createLabel(compoDroite, "Date fin"); 
		cdateFin = new DateTime(compoDroite,  SWT.BORDER | SWT.DROP_DOWN);
		cdateFin.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,2,1));
		
		toolkit.createLabel(compoGauche, "Code tiers"); 
		tfCodeTiers = toolkit.createText(compoGauche, "", SWT.BORDER); 
		tfCodeTiers.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		btnAideTiers = toolkit.createButton(compoGauche, "Aide tiers", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideDocGridData = new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1);
		//btnAideTiersGridData.horizontalAlignment = SWT.BEGINNING;
		btnAideTiers.setLayoutData(btnAideDocGridData);
		
		toolkit.createLabel(compoDroite, "Nom tiers"); 
		tfNomTiers = toolkit.createText(compoDroite, "", SWT.BORDER); 
		tfNomTiers.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
		
		btnAideNomTiers = toolkit.createButton(compoDroite, "Aide nom tiers", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideNomTiersGridData = new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1);
		//btnAideTiersGridData.horizontalAlignment = SWT.BEGINNING;
		btnAideNomTiers.setLayoutData(btnAideNomTiersGridData);
//		
//		btnNonRegle = toolkit.createButton(compo, "Non Réglé", SWT.CHECK); //$NON-NLS-1$
//		GridData btnRegleGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
//		btnRegleGridData.horizontalAlignment = SWT.BEGINNING;
//		btnNonRegle.setLayoutData(btnRegleGridData);
		
		
		btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
		GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
		btnRefeshGridData.horizontalAlignment = SWT.FILL;
		btnRefesh.setLayoutData(btnRefeshGridData);

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = false;
		compoGauche.setLayout(layout);
//		compoGauche.setSize(300, 297);
		
		GridLayout layout2 = new GridLayout();
		layout2.marginWidth = layout2.marginHeight = 0;
		layout2.numColumns = 3;
		layout2.makeColumnsEqualWidth = false;
		compoDroite.setLayout(layout2);
//		compoDroite.setSize(300, 297);
		
		GridLayout layout3 = new GridLayout();
		layout3.marginWidth = layout3.marginHeight = 0;
		layout3.numColumns = 2;
		layout3.makeColumnsEqualWidth = true;
		compo.setLayout(layout3);
		compo.setSize(600, 297);

		
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}

	public Composite getCompo() {
		return compo;
	}

	public Label getLabel() {
		return label;
	}

	public DateTime getCdateDeb() {
		return cdateDeb;
	}
	
	public Button getBtnRefesh() {
		return btnRefesh;
	}
	
	public ImageHyperlink getIhlRefresh() {
		return ihlRefresh;
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}


	public Text getTfCodeTiers() {
		return tfCodeTiers;
	}


	public DateTime getCdateFin() {
		return cdateFin;
	}

	public Button getBtnAideTiers() {
		return btnAideTiers;
	}

//	public Button getBtnNonRegle() {
//		return btnNonRegle;
//	}

	public Text getTfCodeDocument() {
		return tfCodeDocument;
	}

	public Text getTfNomTiers() {
		return tfNomTiers;
	}

	public Combo getCbTypeDoc() {
		return cbTypeDoc;
	}

	public Button getBtnAideNomTiers() {
		return btnAideNomTiers;
	}

	public Button getBtnAideDoc() {
		return btnAideDoc;
	}

}
