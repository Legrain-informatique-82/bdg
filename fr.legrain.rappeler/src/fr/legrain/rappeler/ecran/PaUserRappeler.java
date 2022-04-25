package fr.legrain.rappeler.ecran;
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
public class PaUserRappeler extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

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
	private Label mailLabel;
	private Label infoLabel;
	private Text newMail;
	private Text newName;
	private Label nameLabel;
	
	
	
	// retour en cas d'erreurs
	private DecoratedField fieldNewMail;
	private DecoratedField fieldNewName;

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
		PaUserRappeler inst = new PaUserRappeler(shell, SWT.NULL);
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

	public Label getMailLabel() {
		return mailLabel;
	}



	public Label getInfoLabel() {
		return infoLabel;
	}



	public Text getNewMail() {
		return newMail;
	}



	public Text getNewName() {
		return newName;
	}



	public Label getNameLabel() {
		return nameLabel;
	}







	public PaUserRappeler(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1Layout.numColumns = 2;
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
				infoLabel = new Label(paCorpsFormulaire, SWT.NONE);
				infoLabel.setText("Inscrire un email au plugin rappeler");
				GridData infoLabelLData = new GridData();
				infoLabelLData.heightHint = 30;
				infoLabelLData.horizontalSpan = 2;
				infoLabelLData.grabExcessHorizontalSpace = true;
				infoLabelLData.horizontalAlignment = GridData.FILL;
				infoLabel.setLayoutData(infoLabelLData);
				
			}
			

			
			{
				mailLabel = new Label(paCorpsFormulaire, SWT.NONE);
				mailLabel.setText("Email : ");
				GridData mailLabelLData = new GridData();
				mailLabelLData.widthHint = 47;
				mailLabelLData.heightHint = 17;
				mailLabel.setLayoutData(mailLabelLData);
				mailLabel.setBounds(76, 101, 47, 17);
			}
//			{
//				//newMail = new Text(paCorpsFormulaire, SWT.BORDER);
//				GridData newMailLData = new GridData();
//				newMailLData.widthHint = 206;
//				newMailLData.heightHint = 13;
//				newMailLData.grabExcessHorizontalSpace = true;
//				newMail.setLayoutData(newMailLData);	
//				if(!decore) {
//					newMail = new Text(paCorpsFormulaire, SWT.BORDER);
//					newMail.setLayoutData(newMailLData);
//				} else {					
//					fieldNewMail = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//					newMail = (Text)fieldNewMail.getControl();
//					fieldNewMail.getLayoutControl().setLayoutData(newMailLData);
//				}
//				
//			}
			{
				GridData newMailData = new GridData();
				newMailData.horizontalAlignment = GridData.FILL;
				newMailData.grabExcessHorizontalSpace = true;
				if(!decore) {
					newMail = new Text(paCorpsFormulaire, SWT.BORDER);
					newMail.setLayoutData(newMailData);
				} else {					
				fieldNewMail = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				newMail = (Text)fieldNewMail.getControl();
				fieldNewMail.getLayoutControl().setLayoutData(newMailData);
				}
			}
			{
				nameLabel = new Label(paCorpsFormulaire, SWT.NONE);
				nameLabel.setText("Nom : ");
				GridData nameLabelLData = new GridData();
				nameLabelLData.widthHint = 47;
				nameLabelLData.heightHint = 17;
				nameLabel.setLayoutData(nameLabelLData);
				nameLabel.setBounds(76, 134, 47, 17);
			}
//			{
//				//newName = new Text(paCorpsFormulaire, SWT.BORDER);
//				GridData newNameLData = new GridData();
//				newNameLData.widthHint = 210;
//				newNameLData.heightHint = 17;
//				newName.setLayoutData(newNameLData);
//				newName.setBounds(145, 130, 214, 21);
//				if(!decore) {
//					newMail = new Text(paCorpsFormulaire, SWT.BORDER);
//					newMail.setLayoutData(newNameLData);
//				} else {					
//					fieldNewName = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//					newMail = (Text)fieldNewName.getControl();
//					fieldNewName.getLayoutControl().setLayoutData(newNameLData);
//				}
//			}
			{
				GridData newNameLData = new GridData();
				newNameLData.horizontalAlignment = GridData.FILL;
				newNameLData.grabExcessHorizontalSpace = true;
				if(!decore) {
					newName = new Text(paCorpsFormulaire, SWT.BORDER);
					newName.setLayoutData(newNameLData);
				} else {					
				fieldNewName = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				newName = (Text)fieldNewName.getControl();
				fieldNewName.getLayoutControl().setLayoutData(newNameLData);
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

	public DecoratedField getFieldNewMail() {
		return fieldNewMail;
	}



	public DecoratedField getFieldNewName() {
		return fieldNewName;
	}



	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
		
	}

}
