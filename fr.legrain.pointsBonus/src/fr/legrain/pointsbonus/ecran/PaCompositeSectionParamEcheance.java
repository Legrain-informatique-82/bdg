/**
 * PaSectionParam.java
 */
package fr.legrain.pointsbonus.ecran;

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
 * Classe "remake" de la classe présente dans
 * fr.legrain.articles.statistiques.editors > DefaultFormPage
 * != car utilise DateTime au lieu du CDateTime
 * @author nicolas²
 *
 */
public class PaCompositeSectionParamEcheance {
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
	private DateTime cdateFinCalcul = null;
	private ToolBarManager sectionToolbar = null;
	//private Text tfVariableDate = null;
	private Text tfCodeTiers = null;
	private Combo cbEtat = null;
	private Button btnRefesh = null;
	private Button btnAideTiers = null;
	private ImageHyperlink ihlRefresh = null;

	// Consutrcteur
	public PaCompositeSectionParamEcheance(Composite compo,FormToolkit toolkit) {
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

		toolkit.createLabel(compo, "Type abonnement"); 
		cbEtat = new Combo(compo,  SWT.BORDER);
		cbEtat.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,true,false,4,1));
		
		toolkit.createLabel(compo, "debut période"); 
		cdateDeb = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateDeb.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1));
		
		toolkit.createLabel(compo, "fin période"); 
		cdateFin = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateFin.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,2,1));
		
		toolkit.createLabel(compo, "Date fin calcul"); 
		cdateFinCalcul = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateFinCalcul.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1));
		
		toolkit.createLabel(compo, "Code tiers"); 
		tfCodeTiers = toolkit.createText(compo, "", SWT.BORDER); 
		tfCodeTiers.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		
		btnAideTiers = toolkit.createButton(compo, "Aide", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideTiersGridData = new GridData(SWT.BEGINNING,SWT.NONE,true,false,1,1);
		//btnAideTiersGridData.horizontalAlignment = SWT.BEGINNING;
		btnAideTiers.setLayoutData(btnAideTiersGridData);
		
		btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
		GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
		btnRefeshGridData.horizontalAlignment = SWT.FILL;
		btnRefesh.setLayoutData(btnRefeshGridData);

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 5;
		layout.makeColumnsEqualWidth = false;
		compo.setLayout(layout);
		
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

	public Combo getCbEtat() {
		return cbEtat;
	}

	public DateTime getCdateFin() {
		return cdateFin;
	}

	public Button getBtnAideTiers() {
		return btnAideTiers;
	}

	public DateTime getCdateFinCalcul() {
		return cdateFinCalcul;
	}
}
