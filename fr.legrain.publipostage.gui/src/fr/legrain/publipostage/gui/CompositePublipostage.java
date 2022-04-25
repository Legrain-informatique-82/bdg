package fr.legrain.publipostage.gui;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.legrain.publipostage.Activator;



public class CompositePublipostage extends Composite{
	
	static Logger logger = Logger.getLogger(CompositePublipostage.class.getName());
	
	private Combo cbListeVersion = null;
	private Label laLETTRE = null; 
	private Text tfLETTRE = null; 
	private Button btnChemin_Model = null;
	private Button btnOuvrir_Model = null;
	private Button btnPubli = null;
	
	public static String iconExport = "/icons/wand.png";
	public static String iconImpression = "/icons/printer.png";
	public static String iconEdit = "/icons/edit.png";
	public static String iconEtiq = "/icons/etiq.png";
	public static String iconPubli = "/icons/Word.png";
	
	public CompositePublipostage(Composite parent, int style) {
		super(parent, style);
		initEtiqTiers(parent);
	}

	private void initEtiqTiers(Composite parent) {
		try {
			{
				//formPubli = new Composite(composite_nouveau, SWT.BORDER);
				setVisible(false);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = false;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				setLayoutData(composite1LData);
				setLayout(composite1Layout);
				{
					Group groupParam = new Group(this, SWT.NONE);
					GridLayout groupParamLayout = new GridLayout();
					groupParamLayout.numColumns = 3;
					groupParam.setLayout(groupParamLayout);
					GridData groupParamLData = new GridData();
					groupParamLData.horizontalSpan = 4;
					groupParam.setLayoutData(groupParamLData);
					groupParam
							.setText("Indiquez la version du logiciel de publipostage que vous souhaitez utiliser");
					{
						cbListeVersion = new Combo(groupParam, SWT.NONE);
					}
				}
				{
					laLETTRE = new Label(this, SWT.NONE);
					laLETTRE.setText("Modèle choisi");
				}
				{
					GridData tfLETTRELData = new GridData();
					tfLETTRELData.horizontalAlignment = GridData.FILL;
					tfLETTRELData.horizontalAlignment = GridData.FILL;
					tfLETTRELData.horizontalSpan = 4;
					tfLETTRELData.heightHint = 13;
					tfLETTRELData.grabExcessHorizontalSpace = true;
					tfLETTRE = new Text(this, SWT.BORDER);
					tfLETTRE.setLayoutData(tfLETTRELData);

				}
				{
					GridData btnChemin_ModelLData = new GridData();
					btnChemin_ModelLData.horizontalAlignment = GridData.END;
					btnChemin_Model = new Button(this, SWT.PUSH
							| SWT.CENTER);
					btnChemin_Model.setLayoutData(btnChemin_ModelLData);
					btnChemin_Model.setText("Changer modèle...");
				}
				{
					GridData btnChemin_OuvrirLData = new GridData();
					btnChemin_OuvrirLData.horizontalAlignment = GridData.END;
					btnOuvrir_Model = new Button(this, SWT.PUSH
							| SWT.CENTER);
					btnOuvrir_Model.setLayoutData(btnChemin_OuvrirLData);
					btnOuvrir_Model.setText("Modifier modèle");
				}
				{
					GridData btnPubliLData = new GridData();
					btnPubliLData.horizontalAlignment = GridData.END;
					btnPubli = new Button(this, SWT.PUSH | SWT.CENTER);
					btnPubli.setLayoutData(btnPubliLData);
					btnPubli.setImage(fr.legrain.publipostage.gui.Activator.getImageDescriptor(iconImpression).createImage());
					btnPubli.setText("Fusionner");
				}
			}

			parent.layout();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	public Combo getCbListeVersion() {
		return cbListeVersion;
	}

	public Label getLaLETTRE() {
		return laLETTRE;
	}

	public Text getTfLETTRE() {
		return tfLETTRE;
	}

	public Button getBtnChemin_Model() {
		return btnChemin_Model;
	}

	public Button getBtnOuvrir_Model() {
		return btnOuvrir_Model;
	}

	public Button getBtnPubli() {
		return btnPubli;
	}
}
