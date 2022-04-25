package fr.legrain.tiers.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.lib.gui.grille.LgrCompositeTableViewer;

public class PaListeTiersView extends Composite {
	
	private Composite barContainer;
	private ProgressBar bar;
	private LgrCompositeTableViewer lgrViewer;
	private Label laCodeTiers = null;
	private Text tfCodeTiers = null;

	public PaListeTiersView(Composite parent, int style) {
		super(parent, style);
		init(parent);
	}
	
	public PaListeTiersView(Composite parent, int style, int styleViewer) {
		super(parent, style);
		init(parent,styleViewer);
	}
	
	private void init(Composite parent) {
		init(parent,0);
	}
	
	private void init(Composite parent, int styleViewer) {
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.setLayout(new GridLayout(2, false));
		
		barContainer = new Composite(this, SWT.NONE);
		GridData gdBarContainer = new GridData(GridData.FILL_HORIZONTAL);
		gdBarContainer.horizontalSpan = 2;
		barContainer.setLayoutData(gdBarContainer);
		barContainer.setLayout(new GridLayout(1, false));

		Label l = new Label(barContainer, SWT.NONE);
		l.setText("Chargement des données ...");
		int loadingType = 2;

		bar = new ProgressBar(barContainer, (loadingType == 1) ? SWT.INDETERMINATE : SWT.NONE);
		bar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		barContainer.layout();
		
//		if (loadingType == 2) {
//			bar.setMaximum(10);
//		}
		/*******************************************************************************************************************/
		if(styleViewer==0) {
			styleViewer = SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL;
		}
		
		laCodeTiers = new Label(this, SWT.NONE);
		laCodeTiers.setText("Code tiers");
		
		tfCodeTiers = new Text(this, SWT.BORDER);

//		Table t = new Table(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL);
//		viewer = new LgrTableViewer(t);
		//LgrTableViewer.newCheckList(parent, 0);
		//viewer = new LgrCompositeTableViewer<SWTTiers>(CheckboxTableViewer.newCheckList(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL));
		lgrViewer = new LgrCompositeTableViewer<SWTTiers>(new TableViewer(this, styleViewer));
		
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		lgrViewer.getViewer().getTable().setLayoutData(gd);
		
		this.layout();
	}

	public Composite getBarContainer() {
		return barContainer;
	}

	public ProgressBar getBar() {
		return bar;
	}

	public LgrCompositeTableViewer getLgrViewer() {
		return lgrViewer;
	}

	public Text getTfCodeTiers() {
		return tfCodeTiers;
	}

}
