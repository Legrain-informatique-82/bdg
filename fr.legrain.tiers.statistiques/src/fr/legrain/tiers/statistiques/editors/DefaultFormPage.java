package fr.legrain.tiers.statistiques.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import fr.legrain.tiers.statistiques.Activator;


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
public class DefaultFormPage extends FormPage {

	public static String id = "fr.legrain.tiers.statistiques.editors.DefaultFormPage";
	public static String title = "Statistiques";

	private Section sectionIdentite = null;
	private Section sectionParam = null;
	private Section sectionCA = null;
	private Section sectionSolde = null;
	private Section sectionGraph = null;
	private Section sectionAutre = null;

	private CompositeSectionIdentite compositeSectionIdentite = null;
	private CompositeSectionParam compositeSectionParam = null;
	private CompositeSectionCA compositeSectionCA = null;
	private CompositeSectionSolde compositeSectionSolde = null;
	private CompositeSectionGraph compositeSectionGraph = null;
	private CompositeSectionAutre compositeSectionAutre = null;

	public static String iconPath = "/icons/chart_bar.png";
	public static String iconRefreshPath = "/icons/arrow_refresh_small.png";
	
	private ScrolledForm form = null;


	public DefaultFormPage(FormEditor editor, String id, String title) {
		super((FormEditor) editor, id, title);
	}

	Action myAction = new Action("Action 1",Activator.getImageDescriptor(iconPath)) { 
		@Override 
		public void run() { 
			System.err.println("Action 1");
		}
	};

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		//super.createFormContent(managedForm);
		//final ScrolledForm form = managedForm.getForm();
		form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		//		ColumnLayout layout1 = new ColumnLayout();
		//		layout1.topMargin = 0;
		//		layout1.bottomMargin = 5;
		//		layout1.leftMargin = 10;
		//		layout1.rightMargin = 10;
		//		layout1.horizontalSpacing = 10;
		//		layout1.verticalSpacing = 10;
		//		layout1.maxNumColumns = 4;
		//		layout1.minNumColumns = 1;

		//		TableWrapLayout layout1 = new TableWrapLayout();
		//		layout1.numColumns = 3;

		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 4;

		Layout layout0 = layout1;
		form.getBody().setLayout(layout0);

		form.setText("Statistiques tiers");
		form.setSize(990, 293);
		toolkit.decorateFormHeading(form.getForm());

		/** zhaolin  **/
//		IToolBarManager toolBarManager = form.getToolBarManager(); 
//		toolBarManager.add(new ControlContribution("Toggle Chart") { 
//			@Override 
//			protected Control createControl(Composite parent) 
//			{ 
//				Button button = new Button(parent, SWT.TOGGLE); 
//				button.setToolTipText("Toggle");
//				button.setImage(Activator.getImageDescriptor(iconPath).createImage());
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
		createSectionIdentite(toolkit,form);
		createSectionParam(toolkit,form);
		createSectionCA(toolkit, form);
		createSectionSolde(toolkit, form);
		createSectionGraph(toolkit, form);
		createSectionAutre(toolkit, form);


		ToolBarManager formToolbar=(ToolBarManager)form.getToolBarManager();
//		formToolbar.add(myAction);
		formToolbar.update(true);
		
//		form.getBody().layout();
//		form.reflow(false);

	}
	
	public void reflow() {
//		form.layout();
		form.getDisplay().asyncExec(new Runnable() {
			public void run() {
				//reflowPending = false;
				if (!form.isDisposed())
					form.reflow(true);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public class CompositeSectionIdentite {
		private Composite compo = null;
		private Label labelCode = null;
		private Label labelNom = null;
		private Label labelPrenom = null;

		public CompositeSectionIdentite(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

			toolkit.createLabel(compo, "Code :"); //$NON-NLS-1$
			labelCode = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
			labelCode.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

			toolkit.createLabel(compo, "Nom :"); //$NON-NLS-1$
			labelNom = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
			labelNom.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

			toolkit.createLabel(compo, "Prénom :"); //$NON-NLS-1$
			labelPrenom = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
			labelPrenom.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 2;
			compo.setLayout(layout);
		}

		public Composite getCompo() {
			return compo;
		}

		public Label getLabelCode() {
			return labelCode;
		}

		public Label getLabelNom() {
			return labelNom;
		}

		public Label getLabelPrenom() {
			return labelPrenom;
		}

	}

	public void createSectionIdentite(FormToolkit toolkit, final ScrolledForm form) {

		sectionIdentite = toolkit.createSection(form.getBody(), /*Section.TWISTIE|*/ 
				Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);
		sectionIdentite.setText("Identité");
		//		sectionIdentite.setDescription("description");

		sectionIdentite.getVerticalBar();

		sectionIdentite.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

		compositeSectionIdentite = new CompositeSectionIdentite(toolkit.createComposite(sectionIdentite),toolkit);

		sectionIdentite.setClient(compositeSectionIdentite.getCompo());
		sectionIdentite.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});

	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public class CompositeSectionParam {

		private Composite compo = null;

		private Label label = null;
		private DateTime cdateDeb = null;
		private DateTime cdatefin = null;
		private ToolBarManager sectionToolbar = null;
		private Button btnJour = null;
		private Button btnMois = null;
		private Button btnAnnee = null;
		private Button btnRefesh = null;
		private ImageHyperlink ihlRefresh = null;

		public CompositeSectionParam(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

			label = toolkit.createLabel(compo, "Période"); 
			label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,4,1));

			toolkit.createLabel(compo, "Du : "); 
			cdateDeb = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);

			toolkit.createLabel(compo, "au : "); 
			cdatefin = new DateTime(compo,  SWT.BORDER | SWT.DROP_DOWN);
			//DateTime date = new DateTime(client, SWT.DROP_DOWN);

			toolkit.createLabel(compo, "Précision (graphiques) : ");
			btnJour = toolkit.createButton(compo, "jours", SWT.RADIO);
			btnMois = toolkit.createButton(compo, "mois", SWT.RADIO); 
			btnAnnee = toolkit.createButton(compo, "année", SWT.RADIO); 
			btnJour.setEnabled(false);
//			btnMois.setEnabled(false);
			btnAnnee.setSelection(true);
//			toolkit.createHyperlink(compo, "année", SWT.NONE); 
			//			Composite sep = toolkit.createCompositeSeparator(client);
			//			sep.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));

			btnRefesh = toolkit.createButton(compo, "Recalculer", SWT.PUSH); //$NON-NLS-1$
			GridData btnRefeshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,4,1);
			btnRefeshGridData.horizontalAlignment = SWT.CENTER;
			btnRefesh.setLayoutData(btnRefeshGridData);
			
//			Hyperlink hyperlink = toolkit.createHyperlink(compo, "test link",SWT.NONE).; //$NON-NLS-1$
			
//			ihlRefresh = toolkit.createImageHyperlink(compo,SWT.NONE); //$NON-NLS-1$
//			ihlRefresh.setText("Recalculer");
//			ihlRefresh.setImage(Activator.getImageDescriptor(iconRefreshPath).createImage());
//			GridData ihlRefreshGridData = new GridData(SWT.NONE,SWT.NONE,false,false,4,1);
//			ihlRefreshGridData.horizontalAlignment = SWT.CENTER;
//			ihlRefresh.setLayoutData(ihlRefreshGridData);

			sectionToolbar = new ToolBarManager(SWT.FLAT);
			sectionParam.setTextClient(sectionToolbar.createControl(sectionParam)); 
//			sectionToolbar.add(myAction);
//			sectionToolbar.update(true);


			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 4;
			compo.setLayout(layout);
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
		
		public ToolBarManager getSectionToolbar() {
			return sectionToolbar;
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
	}

	public void createSectionParam(FormToolkit toolkit, final ScrolledForm form) {
		sectionParam = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);
		sectionParam.setText("Paramètres");

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		gridData.heightHint = 50;
		gridData.minimumHeight = 50;
		sectionParam.setLayoutData(gridData);

		sectionParam.setDescription("Paramètres pour les statistiques");

		compositeSectionParam = new CompositeSectionParam(toolkit.createComposite(sectionParam),toolkit);

		sectionParam.setClient(compositeSectionParam.getCompo());
		sectionParam.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public class CompositeSectionCA {
		private Composite compo = null;
		private Label label = null;

		public CompositeSectionCA(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

			toolkit.createLabel(compo, "Chiffre d'affaire HT sur la période : "); //$NON-NLS-1$
			label = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
			label.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
			
			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 2;
			compo.setLayout(layout);
		}

		public Composite getCompo() {
			return compo;
		}

		public Label getText() {
			return label;
		}
	}

	public void createSectionCA(FormToolkit toolkit, final ScrolledForm form) {
		sectionCA = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);

		sectionCA.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

		sectionCA.setText("Chiffre d'affaire HT");
		//		sectionCA.setDescription("Chiffre d'affaire");

		compositeSectionCA = new CompositeSectionCA(toolkit.createComposite(sectionCA),toolkit);

		sectionCA.setClient(compositeSectionCA.getCompo());
		sectionCA.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public class CompositeSectionSolde {
		private Composite compo = null;
		private Label labelSolde = null;
		private Label labelSoldeDesc = null;
		private Label labelSoldeDesc2 = null;
		private Label labelSoldeValeur = null;

		public CompositeSectionSolde(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

			labelSolde = toolkit.createLabel(compo, "Solde du tiers en fin de période : "); //$NON-NLS-1$
			labelSoldeValeur = toolkit.createLabel(compo, "", SWT.SINGLE/*|SWT.BORDER*/); //$NON-NLS-1$
			labelSoldeValeur.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

			labelSoldeDesc = toolkit.createLabel(compo, "(à partir de la date de prise");
			labelSoldeDesc.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,2,1));
			labelSoldeDesc2 = toolkit.createLabel(compo, "en compte des règlements).");
			labelSoldeDesc2.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));
			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 2;
			compo.setLayout(layout);
		}

		public Composite getCompo() {
			return compo;
		}

		public Label getText() {
			return labelSoldeValeur;
		}

		public Label getLabelSolde() {
			return labelSolde;
		}
	}

	public void createSectionSolde(FormToolkit toolkit, final ScrolledForm form) {
		sectionSolde = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);

		sectionSolde.setLayoutData(new GridData(SWT.FILL,SWT.NONE,true,false,1,1));

		sectionSolde.setText("Solde du Tiers");

		compositeSectionSolde = new CompositeSectionSolde(toolkit.createComposite(sectionSolde),toolkit);

		sectionSolde.setClient(compositeSectionSolde.getCompo());
		sectionSolde.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	 

	public class CompositeSectionGraph {
		private Composite compo = null;
		private Text text = null;

		public CompositeSectionGraph(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

//			toolkit.createLabel(compo, "test label"); //$NON-NLS-1$
//			text = toolkit.createText(compo, "test text", SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
//			createPieChart(compo);
			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 2;
			compo.setLayout(layout);
		}

		public Composite getCompo() {
			return compo;
		}

		public Text getText() {
			return text;
		}
	}

	//	public void setActive(boolean active) {
	//		super.setActive(active);
	//		System.out.println("DefaultFormPage.setActive()");
	//	}

	public void createSectionGraph(FormToolkit toolkit, final ScrolledForm form) {
		sectionGraph = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,true,true,4,1);
		gridData.heightHint = 400;
		gridData.minimumHeight = 350;
		sectionGraph.setLayoutData(gridData);

		sectionGraph.setText("Graphique");
//		sectionGraph.setDescription("Graphique");

		compositeSectionGraph = new CompositeSectionGraph(toolkit.createComposite(sectionGraph),toolkit);

		sectionGraph.setClient(compositeSectionGraph.getCompo());
		sectionGraph.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public class CompositeSectionAutre {
		private Composite compo = null;
		private Table table = null;
//		private Tree tree = null;

		public CompositeSectionAutre(Composite compo,FormToolkit toolkit) {
			this.compo = compo;

			table = toolkit.createTable(compo, SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
			table.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
			
//			tree = toolkit.createTree(compo, SWT.SINGLE|SWT.BORDER); //$NON-NLS-1$
//			tree.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));

			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 0;
			layout.numColumns = 2;
			compo.setLayout(layout);
		}

		public Composite getCompo() {
			return compo;
		}

		public Table getTable() {
			return table;
		}
		
//		public Tree getTree() {
//			return tree;
//		}

	}

	public void createSectionAutre(FormToolkit toolkit, final ScrolledForm form) {
		sectionAutre = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED);

		GridData gridData = new GridData(SWT.FILL,SWT.FILL,true,true,4,1);
		gridData.heightHint = 150;
		gridData.minimumHeight = 150;
		sectionAutre.setLayoutData(gridData);

		sectionAutre.setText("Documents liés au tiers");
		//		sectionAutre.setDescription("Autre");

		compositeSectionAutre = new CompositeSectionAutre(toolkit.createComposite(sectionAutre),toolkit);

		sectionAutre.setClient(compositeSectionAutre.getCompo());
		sectionAutre.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
	}

	/* ************************************************************************************************************* */
	/* ************************************************************************************************************* */

	public Section getSectionIdentite() {
		return sectionIdentite;
	}

	public Section getSectionParam() {
		return sectionParam;
	}

	public Section getSectionCA() {
		return sectionCA;
	}

	public Section getSectionGraph() {
		return sectionGraph;
	}

	public Section getSectionAutre() {
		return sectionAutre;
	}

	public CompositeSectionIdentite getCompositeSectionIdentite() {
		return compositeSectionIdentite;
	}

	public CompositeSectionAutre getCompositeSectionAutre() {
		return compositeSectionAutre;
	}

	public CompositeSectionParam getCompositeSectionParam() {
		return compositeSectionParam;
	}

	public CompositeSectionCA getCompositeSectionCA() {
		return compositeSectionCA;
	}

	public CompositeSectionGraph getCompositeSectionGraph() {
		return compositeSectionGraph;
	}

	public CompositeSectionSolde getCompositeSectionSolde() {
		return compositeSectionSolde;
	}

	public Section getSectionSolde() {
		return sectionSolde;
	}

}
