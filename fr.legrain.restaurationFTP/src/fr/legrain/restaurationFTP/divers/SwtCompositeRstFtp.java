package fr.legrain.restaurationFTP.divers;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

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
public class SwtCompositeRstFtp extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelTitle;
	private Group gpListChoixFile;
	private Button btValider;
	private Button btAnnuler;
	private Composite compositeButton;
	private Composite compositeFileFtp;
	private ScrolledComposite scrolledCompositeFileFtp;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		SwtCompositeRstFtp inst = new SwtCompositeRstFtp(shell, SWT.NULL);
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

	public SwtCompositeRstFtp(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
//			this.setSize(300, 100);
			{
				labelTitle = new Label(this, SWT.BORDER);
				GridData labelTitleLData = new GridData();
				labelTitleLData.horizontalSpan = 2;
				labelTitleLData.grabExcessHorizontalSpace = true;
				labelTitleLData.horizontalAlignment = GridData.FILL;
				labelTitle.setLayoutData(labelTitleLData);
				labelTitle.setText("Liste des sauvegardes disponibles sur le serveur distant");
				labelTitle.setAlignment(SWT.CENTER);
				labelTitle.setFont(SWTResourceManager.getFont("Sans", 11, 1, false, false));
			}
			{
				gpListChoixFile = new Group(this, SWT.NONE);
				GridLayout gpListChoixFileLayout = new GridLayout();
				gpListChoixFileLayout.makeColumnsEqualWidth = true;
				gpListChoixFile.setLayout(gpListChoixFileLayout);
				GridData gpListChoixFileLData = new GridData();
				gpListChoixFileLData.horizontalSpan = 2;
				gpListChoixFileLData.grabExcessHorizontalSpace = true;
				gpListChoixFileLData.horizontalAlignment = GridData.FILL;
				gpListChoixFileLData.grabExcessVerticalSpace = true;
				gpListChoixFileLData.verticalAlignment = GridData.FILL;
				gpListChoixFile.setLayoutData(gpListChoixFileLData);
				gpListChoixFile.setText("Choix fichier de restauration ");
				gpListChoixFile.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					GridData scrolledCompositeFileFtpLData = new GridData();
					scrolledCompositeFileFtpLData.horizontalAlignment = GridData.FILL;
					scrolledCompositeFileFtpLData.grabExcessHorizontalSpace = true;
					scrolledCompositeFileFtpLData.verticalAlignment = GridData.FILL;
					scrolledCompositeFileFtpLData.grabExcessVerticalSpace = true;
					scrolledCompositeFileFtp = new ScrolledComposite(gpListChoixFile, SWT.H_SCROLL | SWT.V_SCROLL | SWT.NULL);
					FillLayout scrolledCompositeFileFtpLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
					scrolledCompositeFileFtp.setLayout(scrolledCompositeFileFtpLayout);
					scrolledCompositeFileFtp.setLayoutData(scrolledCompositeFileFtpLData);
					{
						compositeFileFtp = new Composite(scrolledCompositeFileFtp, SWT.NULL);
						GridLayout compositeFileFtpLayout = new GridLayout();
						compositeFileFtpLayout.makeColumnsEqualWidth = true;
						compositeFileFtp.setLayout(compositeFileFtpLayout);
						scrolledCompositeFileFtp.setContent(compositeFileFtp);
					}
				}
			}
			{
				GridData compositeButtonLData = new GridData();
				compositeButtonLData.horizontalSpan = 2;
				compositeButtonLData.horizontalAlignment = GridData.FILL;
				compositeButtonLData.verticalAlignment = GridData.FILL;
				compositeButton = new Composite(this, SWT.NONE);
				GridLayout compositeButtonLayout = new GridLayout();
				compositeButtonLayout.makeColumnsEqualWidth = true;
				compositeButtonLayout.numColumns = 2;
				compositeButton.setLayout(compositeButtonLayout);
				compositeButton.setLayoutData(compositeButtonLData);
				{
					btAnnuler = new Button(compositeButton, SWT.PUSH | SWT.CENTER);
					GridData btAnnulerLData = new GridData();
					btAnnulerLData.horizontalAlignment = GridData.CENTER;
					btAnnulerLData.widthHint = 75;
					btAnnulerLData.heightHint = 27;
					btAnnuler.setLayoutData(btAnnulerLData);
					btAnnuler.setText("Annuler");
				}
				{
					btValider = new Button(compositeButton, SWT.PUSH | SWT.CENTER);
					GridData btValiderLData = new GridData();
					btValiderLData.horizontalAlignment = GridData.CENTER;
					btValiderLData.grabExcessHorizontalSpace = true;
					btValiderLData.widthHint = 76;
					btValiderLData.heightHint = 27;
					btValider.setLayoutData(btValiderLData);
					btValider.setText("Valider");
				}
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getBtValider() {
		return btValider;
	}

	public void setBtValider(Button btValider) {
		this.btValider = btValider;
	}

	public Button getBtAnnuler() {
		return btAnnuler;
	}

	public void setBtAnnuler(Button btAnnuler) {
		this.btAnnuler = btAnnuler;
	}

	public Composite getCompositeFileFtp() {
		return compositeFileFtp;
	}

	public void setCompositeFileFtp(Composite compositeFileFtp) {
		this.compositeFileFtp = compositeFileFtp;
	}

	public ScrolledComposite getScrolledCompositeFileFtp() {
		return scrolledCompositeFileFtp;
	}

	public void setScrolledCompositeFileFtp(
			ScrolledComposite scrolledCompositeFileFtp) {
		this.scrolledCompositeFileFtp = scrolledCompositeFileFtp;
	}

}
