package fr.legrain.licence.ecrans;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;

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
public class PaSupportAbon extends org.eclipse.swt.widgets.Composite {



	private Composite paTitre;
	private Label laTitreFenetre;
	private Composite paFomulaire;
	private Table grille;
	private CLabel laMessage;
	private Label laTitreGrille;
	private Composite paGrille;
	private SashForm compositeForm;
	private int tableStyle = SWT.NULL; //style de la table
	

	private CTabItem cTabItem1;
	private CTabFolder cTabFolder1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaSupportAbon inst = new PaSupportAbon(shell, SWT.FILL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public PaSupportAbon(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public PaSupportAbon(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite) {
		super(parent, style);
		initGUI();
	}
	


	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(819, 526);
			{
				GridData compositeTitreLData = new GridData();
				compositeTitreLData.horizontalAlignment = GridData.FILL;
				compositeTitreLData.grabExcessHorizontalSpace = true;
				compositeTitreLData.heightHint = 28;
				paTitre = new Composite(this, SWT.NONE);
				GridLayout compositeTitreLayout = new GridLayout();
				compositeTitreLayout.numColumns = 2;
				paTitre.setLayout(compositeTitreLayout);
				paTitre.setLayoutData(compositeTitreLData);
				{
					laTitreFenetre = new Label(paTitre, SWT.CENTER);
					GridData label4LData = new GridData();
					label4LData.horizontalAlignment = GridData.FILL;
					label4LData.grabExcessHorizontalSpace = true;
					label4LData.verticalAlignment = GridData.FILL;
					label4LData.grabExcessVerticalSpace = true;
					laTitreFenetre.setLayoutData(label4LData);
					laTitreFenetre.setText("titre ecran");
				}
			}
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.verticalAlignment = GridData.FILL;
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.grabExcessVerticalSpace = true;
				compositeForm = new SashForm(this, SWT.BORDER);
				GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.makeColumnsEqualWidth = true;

				{
					GridData compositeGrilleLData = new GridData();
					paGrille = new Composite(compositeForm, SWT.BORDER);
					GridLayout compositeGrilleLayout = new GridLayout();
					paGrille.setLayoutData(compositeGrilleLData);
					paGrille.setLayout(compositeGrilleLayout);

					{
						laTitreGrille = new Label(paGrille, SWT.CENTER);
						GridData label6LData = new GridData();
						label6LData.horizontalAlignment = GridData.FILL;
						label6LData.grabExcessHorizontalSpace = true;
						laTitreGrille.setLayoutData(label6LData);
						laTitreGrille.setText("titre grille");
					}
					{

							grille = new Table(paGrille, SWT.SINGLE
									| SWT.FULL_SELECTION
									| SWT.H_SCROLL
									| SWT.V_SCROLL
//									| SWT.VIRTUAL
									| SWT.BORDER);
//									| SWT.CHECK);
						}
						grille.setHeaderVisible(true);
						GridData grilleLData = new GridData();
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.grabExcessHorizontalSpace = true;
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.grabExcessVerticalSpace = true;
						grille.setLayoutData(grilleLData);
						grille.setLinesVisible(true);
					}
				}

					{
						GridData compositeFormulaireLData = new GridData();
						paFomulaire = new Composite(compositeForm, SWT.BORDER);
						GridLayout compositeFormulaireLayout = new GridLayout();
						paFomulaire.setLayout(compositeFormulaireLayout);
						paFomulaire.setLayoutData(compositeFormulaireLData);
						{
							cTabFolder1 = new CTabFolder(paFomulaire, SWT.NONE);
							GridData cTabFolder1LData = new GridData();
							cTabFolder1LData.verticalAlignment = GridData.FILL;
							cTabFolder1LData.grabExcessVerticalSpace = true;
							cTabFolder1LData.horizontalAlignment = GridData.FILL;
							cTabFolder1LData.grabExcessHorizontalSpace = true;
							cTabFolder1.setLayoutData(cTabFolder1LData);
							cTabFolder1.setSelection(0);
						}
					}
				
			
			

				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.verticalAlignment = GridData.FILL;
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeFormLData1.grabExcessHorizontalSpace = true;
				compositeFormLData1.grabExcessVerticalSpace = true;
				compositeForm.setLayoutData(compositeFormLData1);
				compositeForm.setOrientation(SWT.VERTICAL);

			GridData btnTousLData = new GridData();

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public SashForm getCompositeForm() {
	//public Composite getCompositeForm() {
		return compositeForm;
	}

	public Label getLaTitreFenetre() {
		return laTitreFenetre;
	}


	public Label getLaTitreGrille() {
		return laTitreGrille;
	}

	public Composite getPaFomulaire() {
		return paFomulaire;
	}

	public Composite getPaGrille() {
		return paGrille;
	}

	public Composite getPaTitre() {
		return paTitre;
	}
	
	public Table getGrille() {
		return grille;
	}
	
	public CLabel getLaMessage() {
		return laMessage;
	}

	public void setGrille(Table grille) {
	}
	
	

	public CTabFolder getCTabFolder1() {
		return cTabFolder1;
	}

}
