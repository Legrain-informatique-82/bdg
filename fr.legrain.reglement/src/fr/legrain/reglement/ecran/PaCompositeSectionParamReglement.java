/**
 * PaSectionParam.java
 */
package fr.legrain.reglement.ecran;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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
public class PaCompositeSectionParamReglement {
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
	//private Text tfVariableDate = null;
	
	//private StyledText tfStyleEditeur = null; 
	private Text tfCodeTiers = null;
	private Combo cbPaiement = null;
	private Button btnRefesh = null;
	private Button btnAideTiers = null;
	private ImageHyperlink ihlRefresh = null;

	// Consutrcteur
	public PaCompositeSectionParamReglement(Composite compo,FormToolkit toolkit) {
		// Init du composite
		this.compo = compo;

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

		toolkit.createLabel(compo, "Type de paiement"); 
		cbPaiement = new Combo(compo,  SWT.BORDER);
		cbPaiement.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,true,false,3,1));
		
		
		toolkit.createLabel(compo, "debut période"); 
		cdateDeb = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateDeb.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1));
		
		toolkit.createLabel(compo, "fin période"); 
		cdateFin = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateFin.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1));
		
		toolkit.createLabel(compo, "Code tiers"); 
		tfCodeTiers = toolkit.createText(compo, "", SWT.BORDER | SWT.MULTI | SWT.V_SCROLL); 
		tfCodeTiers.setLayoutData(new GridData(SWT.BEGINNING,SWT.FILL,false,false,1,20));
			//tfStyleEditeur = new StyledText(compo, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
//			tfStyleEditeur.setLayoutData(new GridData(SWT.BEGINNING,SWT.FILL,false,false,1,20));
		
		btnAideTiers = toolkit.createButton(compo, "Aide", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideTiersGridData = new GridData(SWT.BEGINNING,SWT.NONE,true,false,2,1);
		//btnAideTiersGridData.horizontalAlignment = SWT.BEGINNING;
		btnAideTiers.setLayoutData(btnAideTiersGridData);
		
		btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
		GridData btnRefeshGridData = new GridData(SWT.BEGINNING,SWT.NONE,false,false,3,1);
//		btnRefeshGridData.horizontalAlignment = SWT.FILL;
		btnRefesh.setLayoutData(btnRefeshGridData);

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 4;
		layout.makeColumnsEqualWidth = false;
		compo.setLayout(layout);
		compo.setSize(842, 408);

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

//	public Text getTfVariableDate() {
//		return tfVariableDate;
//	}

	public Text getTfCodeTiers() {
		return tfCodeTiers;
	}

	public Combo getCbPaiement() {
		return cbPaiement;
	}

	public DateTime getCdateFin() {
		return cdateFin;
	}

	public Button getBtnAideTiers() {
		return btnAideTiers;
	}

	public static String getIconFindPath() {
		return iconFindPath;
	}



//	public StyledText getTfStyleEditeur() {
//		return tfStyleEditeur;
//	}

}
