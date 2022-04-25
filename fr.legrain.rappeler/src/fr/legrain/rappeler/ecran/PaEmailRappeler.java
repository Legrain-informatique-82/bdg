package fr.legrain.rappeler.ecran;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.ecran.PaAdresseSWT;

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
public class PaEmailRappeler extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = true;
	
	
	private Composite paCorpsFormulaire;
	private Label laSUJET_DEM;
	private Text TfSUJET_DEM;
	private Text TfCONTENU_DEM;
	private Label laCONTENU_DEM;
	private Label laSUJET_DEST;
	private Text TfSUJET_DEST;
	private Text TfCONTENU_DEST;
	private Label laCONTENU_DEST;
	/*
	 * 
	 * 	private JLabel laCODE_ARTICLE;
	private JTextField tfCODE_ARTICLE;
	private JTextField TfTTva;
	private JLabel LaTTva;
	private JTextArea tfDIVERS_ARTICLE;
	 */
	private Label laINFORMATION;
	
	// retour en cas d'erreurs
	private DecoratedField fieldSUJET_DEM;
	private DecoratedField fieldCONTENU_DEM;

	private DecoratedField fieldSUJET_DEST;
	private DecoratedField fieldCONTENU_DEST;
	/**
	* Auto-generated main method to display this 
	* fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		


	/**
	* Auto-generated method to display this 
	* fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaEmailRappeler inst = new PaEmailRappeler(shell, SWT.NULL);
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







	public PaEmailRappeler(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
					super.getPaFomulaire(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
			}
			
			{
				laINFORMATION = new Label(paCorpsFormulaire, SWT.NONE);
				laINFORMATION.setText("Personnalisation des emails");
				GridData infoLabelLData = new GridData();
				infoLabelLData.heightHint = 30;
				infoLabelLData.horizontalSpan = 4;
				infoLabelLData.grabExcessHorizontalSpace = true;
				infoLabelLData.horizontalAlignment = GridData.FILL;
				laINFORMATION.setLayoutData(infoLabelLData);
				
			}
			

			
			{
				laSUJET_DEM = new Label(paCorpsFormulaire, SWT.NONE);
				laSUJET_DEM.setText("Sujet demande rappel : ");
				GridData mailLabelLData = new GridData();
				mailLabelLData.widthHint = 47;
				mailLabelLData.heightHint = 17;
				laSUJET_DEM.setLayoutData(mailLabelLData);
				laSUJET_DEM.setBounds(76, 101, 47, 17);
			}

			{
				GridData newMailData = new GridData();
				newMailData.horizontalAlignment = GridData.FILL;
				newMailData.grabExcessHorizontalSpace = true;
				if(!decore) {
					TfSUJET_DEM = new Text(paCorpsFormulaire, SWT.BORDER);
					TfSUJET_DEM.setLayoutData(newMailData);
				} else {					
					fieldSUJET_DEM = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				TfSUJET_DEM = (Text)fieldSUJET_DEM.getControl();
				fieldSUJET_DEM.getLayoutControl().setLayoutData(newMailData);
				}
			}
			{
				laCONTENU_DEM = new Label(paCorpsFormulaire, SWT.NONE);
				laCONTENU_DEM.setText("Contenu demande rappel : ");
				GridData nameLabelLData = new GridData();
				nameLabelLData.widthHint = 47;
				nameLabelLData.heightHint = 17;
				laCONTENU_DEM.setLayoutData(nameLabelLData);
				laCONTENU_DEM.setBounds(76, 134, 47, 17);
			}

			{
				GridData newNameLData = new GridData();
				newNameLData.horizontalAlignment = GridData.FILL;
				newNameLData.grabExcessHorizontalSpace = true;
				if(!decore) {
					TfCONTENU_DEM = new Text(paCorpsFormulaire, SWT.BORDER|SWT.MULTI);
					TfCONTENU_DEM.setLayoutData(newNameLData);
				} else {					
				fieldCONTENU_DEM = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				TfCONTENU_DEM = (Text)fieldCONTENU_DEM.getControl();
				fieldCONTENU_DEM.getLayoutControl().setLayoutData(newNameLData);
				}
			}
			
			{
				laSUJET_DEST = new Label(paCorpsFormulaire, SWT.NONE);
				laSUJET_DEST.setText("Sujet rappel effectué : ");
				GridData mailLabelLData = new GridData();
				mailLabelLData.widthHint = 47;
				mailLabelLData.heightHint = 17;
				laSUJET_DEST.setLayoutData(mailLabelLData);
				laSUJET_DEST.setBounds(76, 101, 47, 17);
			}

			{
				GridData newMailData = new GridData();
				newMailData.horizontalAlignment = GridData.FILL;
				newMailData.grabExcessHorizontalSpace = true;
				if(!decore) {
					TfSUJET_DEST = new Text(paCorpsFormulaire, SWT.BORDER);
					TfSUJET_DEST.setLayoutData(newMailData);
				} else {					
					fieldSUJET_DEM = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					TfSUJET_DEST = (Text)fieldSUJET_DEM.getControl();
				fieldSUJET_DEM.getLayoutControl().setLayoutData(newMailData);
				}
			}
			{
				laCONTENU_DEST = new Label(paCorpsFormulaire, SWT.NONE);
				laCONTENU_DEST.setText("Contenu rappel effectué : ");
				GridData nameLabelLData = new GridData();
				nameLabelLData.widthHint = 47;
				nameLabelLData.heightHint = 17;
				laCONTENU_DEST.setLayoutData(nameLabelLData);
				laCONTENU_DEM.setBounds(76, 134, 47, 17);
			}

			{
				GridData newNameLData = new GridData();
				newNameLData.horizontalAlignment = GridData.FILL;
				newNameLData.grabExcessHorizontalSpace = true;
				if(!decore) {
					TfCONTENU_DEST = new Text(paCorpsFormulaire, SWT.BORDER|SWT.MULTI);
					TfCONTENU_DEST.setLayoutData(newNameLData);
				} else {					
				fieldCONTENU_DEST = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				TfCONTENU_DEST = (Text)fieldCONTENU_DEST.getControl();
				fieldCONTENU_DEST.getLayoutControl().setLayoutData(newNameLData);
				}
			}
	

			this.setLayout(new GridLayout());
			
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessVerticalSpace = true;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			compositeFormLData.grabExcessHorizontalSpace = true;
			super.getPaFomulaire().setLayoutData(paFomulaireLData);
			GridData grilleLData = new GridData();
			grilleLData.verticalAlignment = GridData.FILL;
			grilleLData.horizontalAlignment = GridData.FILL;
			grilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getGrille().setLayoutData(grilleLData);
			
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	public Label getLaSUJET_DEM() {
		return laSUJET_DEM;
	}



	public void setLaSUJET_DEM(Label laSUJET_DEM) {
		this.laSUJET_DEM = laSUJET_DEM;
	}



	public Text getTfSUJET_DEM() {
		return TfSUJET_DEM;
	}



	public void setTfSUJET_DEM(Text tfSUJET_DEM) {
		TfSUJET_DEM = tfSUJET_DEM;
	}



	public Text getTfCONTENU_DEM() {
		return TfCONTENU_DEM;
	}



	public void setTfCONTENU_DEM(Text tfCONTENU_DEM) {
		TfCONTENU_DEM = tfCONTENU_DEM;
	}



	public Label getLaCONTENU_DEM() {
		return laCONTENU_DEM;
	}



	public void setLaCONTENU_DEM(Label laCONTENU_DEM) {
		this.laCONTENU_DEM = laCONTENU_DEM;
	}



	public Label getLaSUJET_DEST() {
		return laSUJET_DEST;
	}



	public void setLaSUJET_DEST(Label laSUJET_DEST) {
		this.laSUJET_DEST = laSUJET_DEST;
	}



	public Text getTfSUJET_DEST() {
		return TfSUJET_DEST;
	}



	public void setTfSUJET_DEST(Text tfSUJET_DEST) {
		TfSUJET_DEST = tfSUJET_DEST;
	}



	public Text getTfCONTENU_DEST() {
		return TfCONTENU_DEST;
	}



	public void setTfCONTENU_DEST(Text tfCONTENU_DEST) {
		TfCONTENU_DEST = tfCONTENU_DEST;
	}



	public Label getLaCONTENU_DEST() {
		return laCONTENU_DEST;
	}



	public void setLaCONTENU_DEST(Label laCONTENU_DEST) {
		this.laCONTENU_DEST = laCONTENU_DEST;
	}



	public Label getLaINFORMATION() {
		return laINFORMATION;
	}



	public void setLaINFORMATION(Label laINFORMATION) {
		this.laINFORMATION = laINFORMATION;
	}



	public DecoratedField getFieldSUJET_DEM() {
		return fieldSUJET_DEM;
	}



	public void setFieldSUJET_DEM(DecoratedField fieldSUJET_DEM) {
		this.fieldSUJET_DEM = fieldSUJET_DEM;
	}



	public DecoratedField getFieldSUJET_DEST() {
		return fieldSUJET_DEST;
	}



	public void setFieldSUJET_DEST(DecoratedField fieldSUJET_DEST) {
		this.fieldSUJET_DEST = fieldSUJET_DEST;
	}



	public DecoratedField getFieldCONTENU_DEST() {
		return fieldCONTENU_DEST;
	}



	public void setFieldCONTENU_DEST(DecoratedField fieldCONTENU_DEST) {
		this.fieldCONTENU_DEST = fieldCONTENU_DEST;
	}



	public boolean isDecore() {
		return decore;
	}



	public void setFieldCONTENU_DEM(DecoratedField fieldCONTENU_DEM) {
		this.fieldCONTENU_DEM = fieldCONTENU_DEM;
	}



	public DecoratedField getFieldCONTENU_DEM() {
		return fieldCONTENU_DEM;
	}



	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
		
	}

}
