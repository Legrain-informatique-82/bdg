package fr.legrain.lib.gui.aide;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class PaAideRechercheSWT extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Table grille;
	private Composite paRecherche;
	private Label laRecherche;
	private Label label1;
	private Label laMessageAideNonRemplie;
	private Label laInfo;
	private Text tfChoix;

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
		PaAideRechercheSWT inst = new PaAideRechercheSWT(shell, SWT.NULL);
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

	public PaAideRechercheSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(465, 344);
			{
				GridData label1LData = new GridData();
				label1LData.widthHint = 455;
				label1LData.heightHint = 13;
				label1 = new Label(this, SWT.NONE);
				label1.setLayoutData(label1LData);
				label1.setText("Pour changer de champ de recherche, utilisez la touche F1");
				label1.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 9, 0, false, false));
			}
			{
				GridData laInfoLData = new GridData();
				laInfoLData.heightHint = 13;
				laInfoLData.horizontalAlignment = GridData.FILL;
				laInfoLData.grabExcessHorizontalSpace = true;
				laInfo = new Label(this, SWT.NONE);
				laInfo.setLayoutData(laInfoLData);
			}
			{
				paRecherche = new Composite(this, SWT.NONE);
				GridLayout paRechercheLayout = new GridLayout();
				paRechercheLayout.numColumns = 2;
				GridData paRechercheLData = new GridData();
				paRechercheLData.heightHint = 32;
				paRechercheLData.verticalAlignment = GridData.BEGINNING;
				paRechercheLData.horizontalAlignment = GridData.CENTER;
				paRechercheLData.grabExcessHorizontalSpace = true;
				paRechercheLData.widthHint = 375;
				paRecherche.setLayoutData(paRechercheLData);
				paRecherche.setLayout(paRechercheLayout);
				{
					laRecherche = new Label(paRecherche, SWT.NONE);
					GridData laRechercheLData = new GridData();
					laRechercheLData.horizontalAlignment = GridData.END;
					laRechercheLData.heightHint = 13;
					laRechercheLData.grabExcessHorizontalSpace = true;
					laRechercheLData.widthHint = 210;
					laRecherche.setLayoutData(laRechercheLData);
					laRecherche.setText("Recherche");
				}
				{
					tfChoix = new Text(paRecherche, SWT.BORDER);
					GridData tfChoixLData = new GridData();
					tfChoixLData.widthHint = 122;
					tfChoixLData.heightHint = 13;
					tfChoixLData.horizontalAlignment = GridData.END;
					tfChoixLData.grabExcessHorizontalSpace = true;
					tfChoix.setLayoutData(tfChoixLData);
				}
			}
			{
				laMessageAideNonRemplie = new Label(this, SWT.NONE);
				GridData laMessageAideNonRemplieLData = new GridData();
				laMessageAideNonRemplieLData.grabExcessHorizontalSpace = true;
				laMessageAideNonRemplieLData.horizontalAlignment = GridData.FILL;
				laMessageAideNonRemplie.setLayoutData(laMessageAideNonRemplieLData);
				laMessageAideNonRemplie.setText("Saisir la valeur recherchée, puis Entrée");
				laMessageAideNonRemplie.setAlignment(SWT.CENTER);
				laMessageAideNonRemplie.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				GridData tableLData = new GridData();
				tableLData.horizontalAlignment = GridData.FILL;
				tableLData.grabExcessVerticalSpace = true;
				tableLData.verticalAlignment = GridData.FILL;
				tableLData.grabExcessHorizontalSpace = true;
				grille = new Table(this, SWT.FULL_SELECTION /*| SWT.VIRTUAL*/
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER);
				grille.setLayoutData(tableLData);
				grille.setLinesVisible(true);
				grille.setHeaderVisible(true);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Table getGrille() {
		return grille;
	}
	
	public Composite getPaRecherche() {
		return paRecherche;
	}
	
	public Text getTfChoix() {
		return tfChoix;
	}

	public Label getLaRecherche() {
		return laRecherche;
	}
	
	public Label getLaInfo() {
		return laInfo;
	}
	
	public Label getLaMessageAideNonRemplie() {
		return laMessageAideNonRemplie;
	}

}
