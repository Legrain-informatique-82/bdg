package fr.legrain.edition.divers;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.legrain.edition.actions.ConstEdition;

public class ScrolledCompositeFieldEditor extends FieldEditor{

	private Composite top;
	private ConstEdition constEdition;
	private String namePlugin;
	
	public ScrolledCompositeFieldEditor(Composite parent,String properties, String labelText,
										ConstEdition constEdition,String namePlugin){
//		super();
		this.namePlugin = namePlugin; 
		this.constEdition = constEdition; 
		this.top = parent;
		init(properties, labelText);
		createControl(parent);
		
	}
	public ScrolledCompositeFieldEditor(Composite parent) {
		super();
		this.top = parent;
//		init(properties, labelText);
		createControl(parent);
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		// TODO Auto-generated method stub
		GridData layoutData = (GridData) top.getLayoutData();
		layoutData.horizontalSpan = numColumns;
	}
	
	@Override
	public int getNumberOfControls() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	protected void doFillIntoGrid(Composite parent, int numCocomplumns) {
		// TODO Auto-generated method stub		
		
		top = parent;
		//GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		GridData gd = new GridData();
		gd.horizontalSpan = numCocomplumns;
		parent.setLayoutData(gd);
		Label lableTitle = this.getLabelControl(parent);
//		lableTitle.setLayoutData(gd);
		
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.grabExcessHorizontalSpace = true;
		
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		FillLayout scrolledCompositeFillLayout = new FillLayout(SWT.HORIZONTAL);
//		scrolledComposite.setLayout(scrolledCompositeFillLayout);
		scrolledComposite.setLayoutData(gd);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		constEdition.addExpandItemAndButtonEditionPreference(scrolledComposite,constEdition.getMapExpandItemButton(),
															 namePlugin);
	}

	@Override
	protected void doLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doLoadDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStore() {
		// TODO Auto-generated method stub
		
	}
	/********* fonction  personnelle (28/12/2009) **********/
	public ConstEdition getConstEdition() {
		return constEdition;
	}
	public void setConstEdition(ConstEdition constEdition) {
		this.constEdition = constEdition;
	}
	

}
