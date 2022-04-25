/**
 * PaCompositeSectionClients.java
 */
package fr.legrain.reglement.ecran;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleBtnCote;

/**
 * @author nicolas²
 *
 */
public class PaCompositeSectionDocReglement  {
	// Composite
	private Composite compo = null;
	private Group paTotauxLignes;
	private Text tfNbLigne;
	private Label laMT_TTC_CALC;
	private Text tfMT_HT_CALC;
	private Label laMT_HT_CALC;
	private DefaultFrameFormulaireSWTSimpleBtnCote compoEcran;
//	private ScrolledComposite sc = null;
//	private Table table = null;
//	private Table tableDetail = null;
	private ToolBarManager sectionToolbar = null;
	public static String iconpath = "/icons/printer.png";
	public static String iconValidation = "/icons/logo_lgr_16.png";
	//private Button btnCreer = null;
	
	public PaCompositeSectionDocReglement(Composite compo,FormToolkit toolkit) {
		this.compo = compo;
		
//		sc = new ScrolledComposite(compo,SWT.BORDER | SWT.V_SCROLL);
//		
//		GridData ld = new GridData();
//		ld.horizontalAlignment = GridData.FILL;
//		ld.grabExcessHorizontalSpace = true;
//		ld.verticalAlignment = GridData.FILL;
//		ld.grabExcessVerticalSpace = true;
//		ld.horizontalSpan = 3;
//		ld.minimumHeight=500;
//		sc.setLayoutData(ld);
//		sc.setExpandVertical(true);
//		sc.setExpandHorizontal(true);

		
		compoEcran = new DefaultFrameFormulaireSWTSimpleBtnCote(compo,SWT.PUSH,1,SWT.FULL_SELECTION
				| SWT.H_SCROLL
				| SWT.V_SCROLL
				| SWT.BORDER
				| SWT.CHECK ) ;
		compoEcran.getPaFomulaire().setVisible(false);
		compoEcran.setSize(SWT.DEFAULT, 600);

//		table = toolkit.createTable(compoEcran,  SWT.FULL_SELECTION
//				| SWT.H_SCROLL
//				| SWT.V_SCROLL
//				| SWT.BORDER
//				| SWT.CHECK ); //$NON-NLS-1$
//		GridData gridDataTab = new GridData(SWT.FILL,SWT.FILL,true,false,2,1);
//		gridDataTab.minimumHeight = 150;
//		gridDataTab.heightHint = 150;
//		table.setLayoutData(gridDataTab);
		
		{
			paTotauxLignes = new Group(compo, SWT.NONE);
			GridLayout paTotauxLignesLayout = new GridLayout();
			paTotauxLignesLayout.numColumns = 10;
			GridData paTotauxLignesLData = new GridData();
			paTotauxLignesLData.verticalAlignment = GridData.BEGINNING;
			paTotauxLignesLData.horizontalAlignment = GridData.CENTER;
			paTotauxLignesLData.horizontalSpan = 2;
			paTotauxLignesLData.widthHint = 510;
			paTotauxLignesLData.grabExcessVerticalSpace = true;
			paTotauxLignes.setLayoutData(paTotauxLignesLData);
			paTotauxLignes.setLayout(paTotauxLignesLayout);
			paTotauxLignes.setText("Totaux");
		}
		
		{
			laMT_HT_CALC = new Label(paTotauxLignes, SWT.NONE);
			laMT_HT_CALC.setText("Net HT");
		}
		{
			GridData tfMT_HT_CALCLData = new GridData();
			tfMT_HT_CALCLData.widthHint = 127;
			tfMT_HT_CALCLData.verticalAlignment = GridData.BEGINNING;
			tfMT_HT_CALC = new Text(paTotauxLignes, SWT.BORDER);
			tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
			tfMT_HT_CALC.setText("Net HT");
			tfMT_HT_CALC.setEditable(false);

		}
		{
			laMT_TTC_CALC = new Label(paTotauxLignes, SWT.NONE);
			laMT_TTC_CALC.setText("Nombre de lignes");
		}
		{
			GridData tfMT_TVA_AVANT_REMISELData = new GridData();
			tfMT_TVA_AVANT_REMISELData.widthHint = 127;
			tfNbLigne = new Text(paTotauxLignes, SWT.BORDER);
			tfNbLigne.setLayoutData(tfMT_TVA_AVANT_REMISELData);
			tfNbLigne.setText("NB");
			tfNbLigne.setEditable(false);

		}

		
//		btnCreer = toolkit.createButton(compo, "Créer", SWT.PUSH); //$NON-NLS-1$
//		GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,1,1);
//		btnRefeshGridData.horizontalAlignment = SWT.CENTER;
//		btnCreer.setLayoutData(btnRefeshGridData);
		
//		sc.setMinSize(compoEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//
//		sc.setContent(compoEcran);

		GridData ld = new GridData();
		ld.horizontalAlignment = GridData.FILL;
		ld.grabExcessHorizontalSpace = true;
		ld.verticalAlignment = GridData.FILL;
		ld.grabExcessVerticalSpace = true;
		ld.horizontalSpan = 2;
		ld.minimumHeight=500;
		compoEcran.setLayoutData(ld);
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		compo.setLayout(layout);
		sectionToolbar = new ToolBarManager(SWT.FLAT);
	}



	public Composite getCompo() {
		return compo;
	}

	public Table getTable() {
		return compoEcran.getGrille();
	}
	
	public ToolBarManager getSectionToolbar() {
		return sectionToolbar;
	}
	
//
//	public Button getBtnCreer() {
//		return btnCreer;
//	}
	
//	public Table getTableDetail() {
//		return tableDetail;
//	}

	public DefaultFrameFormulaireSWTSimpleBtnCote getCompoEcran() {
		return compoEcran;
	}



	public Group getPaTotauxLignes() {
		return paTotauxLignes;
	}



	public Text getTfNbLigne() {
		return tfNbLigne;
	}



	public Label getLaMT_TTC_CALC() {
		return laMT_TTC_CALC;
	}



	public Text getTfMT_HT_CALC() {
		return tfMT_HT_CALC;
	}



	public Label getLaMT_HT_CALC() {
		return laMT_HT_CALC;
	}
	
}
