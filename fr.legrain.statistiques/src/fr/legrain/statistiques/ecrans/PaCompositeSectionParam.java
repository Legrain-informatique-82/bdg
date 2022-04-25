/**
 * PaSectionParam.java
 */
package fr.legrain.statistiques.ecrans;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


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
	
	// Composite
	private Composite compo = null;

	// Elements graphiques
	private Label label = null;
	private DateTime cdateDeb = null;
	private DateTime cdatefin = null;
	private ToolBarManager sectionToolbar = null;
	private Button btnJour = null;
	private Button btnMois = null;
	private Button btnAnnee = null;
	private Button btnRefesh = null;
	private ImageHyperlink ihlRefresh = null;

	// Consutrcteur
	public PaCompositeSectionParam(Composite compo,FormToolkit toolkit) {
		// Init du composite
		this.compo = compo;

		// Informations sur la période -- START
		label = toolkit.createLabel(compo, "Période"); 
		label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,4,1));

		toolkit.createLabel(compo, "Du : "); 
		cdateDeb = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);

		toolkit.createLabel(compo, "au : "); 
		cdatefin = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		// Informations sur la période  -- END

		// Boutons radio précision graphique
		toolkit.createLabel(compo, "Précision (graphiques) : ");
		btnJour = toolkit.createButton(compo, "jours", SWT.RADIO);
		btnMois = toolkit.createButton(compo, "mois", SWT.RADIO); 
		btnAnnee = toolkit.createButton(compo, "année", SWT.RADIO); 
		btnJour.setEnabled(false);

		btnMois.setSelection(true);


		btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
		GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
		btnRefeshGridData.horizontalAlignment = SWT.FILL;
		btnRefesh.setLayoutData(btnRefeshGridData);



		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 4;
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

	public DateTime getCdatefin() {
		return cdatefin;
	}
	
	public Button getBtnJour() {
		return btnJour;
	}

	public Button getBtnMois() {
		return btnMois;
	}

	public Button getBtnAnnee() {
		return btnAnnee;
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
}
