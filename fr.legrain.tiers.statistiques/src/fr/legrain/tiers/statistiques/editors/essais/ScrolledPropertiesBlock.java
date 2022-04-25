/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fr.legrain.tiers.statistiques.editors.essais;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.libLgrBirt.chart.test.data.test.CreateChart;
import fr.legrain.libLgrBirt.chart.test.data.test.SwtLiveChartRotationViewer;
import fr.legrain.tiers.statistiques.Activator;
/**
 *
 */
public class ScrolledPropertiesBlock extends MasterDetailsBlock {
	private FormPage page;
	public ScrolledPropertiesBlock(FormPage page) {
		this.page = page;
	}
	/**
	 * @param id
	 * @param title
	 */
	class MasterContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof SimpleFormEditorInput) {
				SimpleFormEditorInput input = (SimpleFormEditorInput)page.getEditor().getEditorInput();
				return input.getModel().getContents();
			}
			return new Object[0];
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}
	}
	class MasterLabelProvider extends LabelProvider
	implements
	ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return obj.toString();
		}
		public Image getColumnImage(Object obj, int index) {
			if (obj instanceof TypeOne) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_ELEMENT);
			}
			if (obj instanceof TypeTwo) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FILE);
			}
			return null;
		}
	}

	public void createSectionGraph(FormToolkit toolkit, final ScrolledForm form) {
		Section section = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);

		section.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,3,1));

		FillLayout fillLayout = new FillLayout();
//		section.setLayout(fillLayout);
		section.setText("Graphique");
		section.setDescription("Graphique");
		
		Composite client = toolkit.createComposite(section);
		
		Composite compositeComment = toolkit.createComposite(client,SWT.BORDER);
		compositeComment.setLayout(new FillLayout());
		
		compositeComment.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		Text textComment = toolkit.createText(compositeComment,"qsd",SWT.BORDER);
//		Text textComment = new Text(compositeComment, SWT.BORDER); 
		textComment.setText("");
		compositeComment.layout();
		
		//toolkit.createCompositeSeparator(section);
		
		createPieChart(client,compositeComment,textComment);
		
//		toolkit.createLabel(client, "test label"); //$NON-NLS-1$
//		Text text = toolkit.createText(client, "test text", SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
		
		
		
		
		GridLayout layout = new GridLayout();
//		layout.marginWidth = layout.marginHeight = 0;
		
		layout.numColumns = 2;
		client.setLayout(layout);
		section.setClient(client);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		
	}
	public static void createPieChart(Composite composite,Composite compositeComment,
			Text textComment){
		

		SwtLiveChartRotationViewer c3dViewer = new SwtLiveChartRotationViewer(composite, 
				SWT.NO_BACKGROUND,compositeComment,textComment ); 
		
		c3dViewer.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		c3dViewer.addPaintListener( c3dViewer );
//		c3dViewer.addControlListener( c3dViewer );
		
//		Composite composite2 = new Composite(composite, SWT.NULL);
//		Label label = new Label(composite2, SWT.NULL);
//		label.setText("ssss");
//		composite2.layout();
//		GridData gd = new GridData(GridData.FILL_BOTH);
//		Canvas cCenter = new Canvas(composite, SWT.BORDER);
//		cCenter.setLayoutData(gd);
//		cCenter.addPaintListener(c3dViewer);


	}
	public static void createChart(Composite composite){
			CreateChart createChart = new CreateChart();
			createChart.CreateAreaChartMain(composite);
	}
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		/***   zhaolin ***/

		/*****************/
//		final Section section = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
//		section.setText(Messages.getString("ScrolledPropertiesBlock.sname")); //$NON-NLS-1$
//		section.setDescription(Messages.getString("ScrolledPropertiesBlock.sdesc")); //$NON-NLS-1$

		/***** zhaolin ****/
//		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
////		section.setTextClient(toolBarManager.createControl(section));
////				Action action = new Action("zzz") {
////		
////					@Override
////					public void run() {
////						// TODO Auto-generated method stub
////						System.out.println("ddd");
////					}
////					
////				};
////				
////				toolBarManager.add(action);
////		
//		toolBarManager.add(new ControlContribution("Toggle Chart") { 
//			@Override 
//			protected Control createControl(Composite parent) 
//			{ 
//				Button button = new Button(parent, SWT.TOGGLE); 
//				button.setText("test BT");
//				button.addSelectionListener(new SelectionAdapter() { 
//					@Override 
//					public void widgetSelected(SelectionEvent e) { 
//						// Perform action 
//						System.out.println("ddd");
//					} 
//				});
//				return button; 
//			} 
//		}); 
//		toolBarManager.update(true);
//		/*****************/
//		section.marginWidth = 10;
//		section.marginHeight = 5;
//		Composite client = toolkit.createComposite(section, SWT.WRAP);
//		GridLayout layout = new GridLayout();
//		layout.numColumns = 2;
//		layout.marginWidth = 2;
//		layout.marginHeight = 2;
//		client.setLayout(layout);
//		Table t = toolkit.createTable(client, SWT.NULL);
//		GridData gd = new GridData(GridData.FILL_BOTH);
//		gd.heightHint = 20;
//		gd.widthHint = 100;
//		t.setLayoutData(gd);
//		toolkit.paintBordersFor(client);
//		Button b = toolkit.createButton(client, Messages.getString("ScrolledPropertiesBlock.add"), SWT.PUSH); //$NON-NLS-1$
//		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
//		b.setLayoutData(gd);
//		section.setClient(client);
//		final SectionPart spart = new SectionPart(section);
//		managedForm.addPart(spart);
//		TableViewer viewer = new TableViewer(t);
//		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//				managedForm.fireSelectionChanged(spart, event.getSelection());
//			}
//		});
//		viewer.setContentProvider(new MasterContentProvider());
//		viewer.setLabelProvider(new MasterLabelProvider());
//		viewer.setInput(page.getEditor().getEditorInput());
		/*** zhaolin ***/
		createSectionGraph(toolkit,form);
		/**************************/
	}

	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		Action haction = new Action("hor", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText(Messages.getString("ScrolledPropertiesBlock.horizontal")); //$NON-NLS-1$
		haction.setImageDescriptor(Activator.getDefault().getImageRegistry()
				.getDescriptor(Activator.IMG_HORIZONTAL));
		Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText(Messages.getString("ScrolledPropertiesBlock.vertical")); //$NON-NLS-1$
		vaction.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.IMG_VERTICAL));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);

		IToolBarManager toolBarManager = form.getToolBarManager(); 
		toolBarManager.add(new ControlContribution("Toggle Chart") { 
			@Override 
			protected Control createControl(Composite parent) 
			{ 
				Button button = new Button(parent, SWT.PUSH); 
				button.setText("ddd");
				button.addSelectionListener(new SelectionAdapter() { 
					@Override 
					public void widgetSelected(SelectionEvent e) { 
						// Perform action 
						System.out.println("ddd");
					} 
				});
				return button; 
			} 
		}); 

	}
	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(TypeOne.class, new TypeOneDetailsPage());
		detailsPart.registerPage(TypeTwo.class, new TypeTwoDetailsPage());
	}
}