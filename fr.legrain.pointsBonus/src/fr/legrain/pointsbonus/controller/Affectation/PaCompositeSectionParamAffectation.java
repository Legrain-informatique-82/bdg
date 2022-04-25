/**
 * PaSectionParam.java
 */
package fr.legrain.pointsbonus.controller.Affectation;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
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
public class PaCompositeSectionParamAffectation {
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
	private Text tfAncienCode = null;
	private Text tfNouveauCode = null;
	private ToolBarManager sectionToolbar = null;
	//private Text tfVariableDate = null;
	private Text tfCodeArticle = null;
	private Combo cbFamille = null;
	private Button btnRefesh = null;
	private Button btnAideArticle = null;
	private ImageHyperlink ihlRefresh = null;

	
	// Consutrcteur
	public PaCompositeSectionParamAffectation(Composite compo,FormToolkit toolkit) {
		// Init du composite
		this.compo = compo;



		toolkit.createLabel(compo, "Type famille"); 
		cbFamille = new Combo(compo,  SWT.BORDER);
		cbFamille.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,true,false,3,1));
		
		toolkit.createLabel(compo, "debut période"); 
		cdateDeb = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateDeb.setLayoutData(new GridData(SWT.BEGINNING,SWT.NONE,false,false,1,1));
		
		toolkit.createLabel(compo, "fin période"); 
		cdateFin = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
		cdateFin.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
				
		
		toolkit.createLabel(compo, "Suffixe Ancien Code"); 
		tfAncienCode = toolkit.createText(compo, "", SWT.BORDER); 
		tfAncienCode.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		
		
		toolkit.createLabel(compo, "Suffixe nouveau Code"); 
		tfNouveauCode = toolkit.createText(compo, "", SWT.BORDER); 
		tfNouveauCode.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));		
		
		toolkit.createLabel(compo, "Code article"); 
		tfCodeArticle = toolkit.createText(compo, "", SWT.BORDER); 
		tfCodeArticle.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,2,1));
		
		
		btnAideArticle = toolkit.createButton(compo, "Aide", SWT.PUSH); //$NON-NLS-1$
		GridData btnAideTiersGridData = new GridData(SWT.NONE,SWT.NONE,true,false,1,1);
//		btnAideTiersGridData.horizontalAlignment = SWT.FILL;
		btnAideArticle.setLayoutData(btnAideTiersGridData);
		
		btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
		GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
		btnRefeshGridData.horizontalAlignment = SWT.FILL;
		btnRefesh.setLayoutData(btnRefeshGridData);
		

		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 4;
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

	public Text getTfCodeArticle() {
		return tfCodeArticle;
	}

	public Combo getCbFamille() {
		return cbFamille;
	}

	public DateTime getCdateFin() {
		return cdateFin;
	}

	public Button getBtnAideArticle() {
		return btnAideArticle;
	}

	public Text getTfAncienCode() {
		return tfAncienCode;
	}

	public Text getTfNouveauCode() {
		return tfNouveauCode;
	}

}
