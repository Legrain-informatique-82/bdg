package fr.legrain.generationLabelEtiquette.ecrans;

import generationlabeletiquette.Activator;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CompositeEtiquette extends Composite{
	
	static Logger logger = Logger.getLogger(CompositeEtiquette.class.getName());
	
	private Label laETTIQUETTE = null;
	private Combo cbListeParamEtiquette = null;
	private Button btnImprimerEtiquette = null;
	private Button btnModifierEtiquette = null;
	
	public static String iconExport = "/icons/wand.png";
	public static String iconImpression = "/icons/printer.png";
	public static String iconEdit = "/icons/edit.png";
	public static String iconEtiq = "/icons/etiq.png";
	public static String iconPubli = "/icons/Word.png";
	
	public CompositeEtiquette(Composite parent, int style) {
		super(parent, style);
		initEtiqTiers(parent);
	}

	private void initEtiqTiers(Composite parent) {
		try {
			{
				//new Composite(parent, SWT.BORDER);
				setVisible(false);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				composite1Layout.makeColumnsEqualWidth = false;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.BEGINNING;
				composite1LData.grabExcessHorizontalSpace = false;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				setLayoutData(composite1LData);
				setLayout(composite1Layout);
				{
					laETTIQUETTE = new Label(this, SWT.NONE);
					GridData laETTIQUETTELData = new GridData();
					laETTIQUETTELData.horizontalSpan = 4;
					laETTIQUETTE.setLayoutData(laETTIQUETTELData);
					laETTIQUETTE.setText("Choix du paramètre d'étiquette : ");
				}

				{
					cbListeParamEtiquette = new Combo(this, SWT.NONE);
					GridData cbListeParamEtiquetteLData = new GridData();
					cbListeParamEtiquetteLData.horizontalSpan = 2;
					cbListeParamEtiquetteLData.horizontalAlignment = GridData.FILL;
					cbListeParamEtiquetteLData.grabExcessHorizontalSpace = true;
					cbListeParamEtiquetteLData.heightHint = 21;
					cbListeParamEtiquette.setLayoutData(cbListeParamEtiquetteLData);
				}

				{
					GridData btnImprimerEtiquetteLData = new GridData();
					//btnImprimerEtiquetteLData.widthHint = 94;
					btnImprimerEtiquetteLData.horizontalAlignment = GridData.END;
					btnImprimerEtiquette = new Button(this, SWT.PUSH
							| SWT.CENTER);
					btnImprimerEtiquette.setLayoutData(btnImprimerEtiquetteLData);
					btnImprimerEtiquette.setImage(Activator.getImageDescriptor(iconImpression).createImage());
					// btnImprimerEtiquette.setText("Imprimer Etiq.");
				}

				{
					GridData btnModifierEtiquetteLData = new GridData();
					//btnModifierEtiquetteLData.widthHint = 94;
					btnModifierEtiquetteLData.horizontalAlignment = GridData.END;
					btnModifierEtiquette = new Button(this, SWT.PUSH| SWT.CENTER);
					btnModifierEtiquette.setLayoutData(btnModifierEtiquetteLData);
					btnModifierEtiquette.setImage(Activator.getImageDescriptor(iconEdit).createImage());
					btnModifierEtiquette.setText("Créer/Modifier");
				}
			}

			parent.layout();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public Label getLaETTIQUETTE() {
		return laETTIQUETTE;
	}

	public Combo getCbListeParamEtiquette() {
		return cbListeParamEtiquette;
	}

	public Button getBtnImprimerEtiquette() {
		return btnImprimerEtiquette;
	}

	public Button getBtnModifierEtiquette() {
		return btnModifierEtiquette;
	}
}
