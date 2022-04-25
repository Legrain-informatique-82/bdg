package fr.legrain.document.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
public class PaParamCreationDocMultiple extends Composite  {
	private Button btn1semaine;
	private Button btnAppliquer;
	private Text tfJours;
	private Button btnXjours;
	private Button btn1mois;
	private Button btn2semaines;
	private Button btnDocument;
	private Button btnTiers;
	private Button btnDecad;



	public PaParamCreationDocMultiple(Composite parent, int style) {
		super(parent, style);
		initGUI();
		// TODO Auto-generated constructor stub
	}
	
	private void initGUI() {
		try {
			{
				GridLayout thisLayout = new GridLayout();
				thisLayout.numColumns = 2;
				this.setLayout(thisLayout);
				this.setSize(347, 138);
				{
					btnTiers = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btnTiersLData = new GridData();
					btnTiersLData.horizontalIndent = 20;
					btnTiers.setLayoutData(btnTiersLData);
					btnTiers.setText("tiers");
				}
				{
					btnDocument = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btnDocumentLData = new GridData();
					btnDocumentLData.horizontalSpan = 1;
					btnDocument.setLayoutData(btnDocumentLData);
					btnDocument.setText("document");
				}
				{
					btn1semaine = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btn1semaineLData = new GridData();
					btn1semaineLData.horizontalIndent = 20;
					btn1semaine.setLayoutData(btn1semaineLData);
					btn1semaine.setText("1");
				}
				{
					btn2semaines = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btn2semainesLData = new GridData();
					btn2semaines.setLayoutData(btn2semainesLData);
					btn2semaines.setText("2 semaines de document");
				}
				{
					btnDecad = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btn1moisLData = new GridData();
					btn1moisLData.horizontalIndent = 20;
//					btn1moisLData.horizontalSpan = 1;
					btnDecad.setLayoutData(btn1moisLData);
					btnDecad.setText("par décade");
				}
				{
					btn1mois = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btn1moisLData = new GridData();
//					btn1moisLData.horizontalSpan = 1;
					btn1mois.setLayoutData(btn1moisLData);
					btn1mois.setText("1 mois de document");
				}
				{
					btnXjours = new Button(this, SWT.RADIO | SWT.LEFT);
					GridData btnXjoursLData = new GridData();
					btnXjoursLData.horizontalIndent = 20;
					btnXjoursLData.horizontalSpan = 1;
					btnXjours.setLayoutData(btnXjoursLData);
					btnXjours.setText("X jour(s) de document");
				}
				{
					tfJours = new Text(this, SWT.BORDER);
					GridData tfJoursLData = new GridData();
					tfJoursLData.widthHint = 40;
					tfJoursLData.heightHint = 14;
					tfJours.setLayoutData(tfJoursLData);
					tfJours.setText("1");
				}
				{
					btnAppliquer = new Button(this, SWT.CHECK | SWT.LEFT);
					GridData btnAppliquerLData = new GridData();
					btnAppliquerLData.horizontalSpan = 2;
					btnAppliquerLData.grabExcessHorizontalSpace = true;
					btnAppliquer.setLayoutData(btnAppliquerLData);
					btnAppliquer.setText("Appliquer à tous les tiers");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public Button getBtnTiers() {
		return btnTiers;
	}
	
	public Button getBtnDocument() {
		return btnDocument;
	}
	
	public Button getBtn1semaine() {
		return btn1semaine;
	}
	
	public Button getBtn2semaines() {
		return btn2semaines;
	}
	
	public Button getBtn1mois() {
		return btn1mois;
	}
	
	public Button getBtnXjours() {
		return btnXjours;
	}
	
	public Text getTfJours() {
		return tfJours;
	}
	
	public Button getBtnAppliquer() {
		return btnAppliquer;
	}
	
	public Button getBtnDecad() {
		return btnDecad;
	}


}
