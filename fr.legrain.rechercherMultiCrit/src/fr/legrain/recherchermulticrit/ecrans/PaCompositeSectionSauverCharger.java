/**
 * PaCompositeSectionSauverCharger.java
 */
package fr.legrain.recherchermulticrit.ecrans;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.Activator;

/**
 * @author posttest
 *
 */
public class PaCompositeSectionSauverCharger {
	
	public static String iconImport = "/icons/table_row_insert.png";
	public static String iconSave = "/icons/table_save.png";
	public static String iconDelete = "/icons/table_delete.png";
	public static String iconExport = "/icons/folder_table.png";
	public static String iconLoad = "/icons/table_add.png";
	private Composite compo = null;
	private Label label = null;
	private Label infolabel = null;
	
	private Label lblCharger =  null;
	private Label lblSauver = null;
	private Label lbldesc = null;
	private Combo combo = null;
	private Text  nomSauvegarde = null;
	private Text  infoSauvegarde = null;
	private Text  infoCharge = null;
	private Text desc = null;
	private Button buttonCharger = null;
	private Button buttonSauver = null;
	private Button buttonSupprimer = null;
	private Button buttonExporter = null;
	private Button buttonImporter = null;

	public PaCompositeSectionSauverCharger(Composite compo,FormToolkit toolkit) {
		this.compo = compo;

		// Elements graphiques 
		// -- Charger --
		lblCharger = toolkit.createLabel(compo, "Je charge une liste de critères ");
		lblCharger.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo = new Combo(compo,SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		
		buttonCharger = toolkit.createButton(compo, "", SWT.NONE);
		buttonCharger.setImage(Activator.getImageDescriptor(PaCompositeSectionSauverCharger.iconLoad).createImage());
		buttonCharger.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		buttonCharger.setToolTipText("Charger une recherche préalablement enregistrée.");
		
		buttonSupprimer = toolkit.createButton(compo, "", SWT.NONE);
		buttonSupprimer.setImage(Activator.getImageDescriptor(PaCompositeSectionSauverCharger.iconDelete).createImage());
		buttonSupprimer.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		buttonSupprimer.setToolTipText("Supprimer la recherche sélectionnée.");
		
		buttonImporter = toolkit.createButton(compo, "", SWT.NONE);
		buttonImporter.setImage(Activator.getImageDescriptor(PaCompositeSectionSauverCharger.iconImport).createImage());
		buttonImporter.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		buttonImporter.setToolTipText("Importer une recherche préalablement enregistrée.");
		
	
		infoCharge = new Text(compo,SWT.READ_ONLY);
		infoCharge.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		
		
		// -- Sauver --
		lblSauver = toolkit.createLabel(compo, "Je sauve la liste de critères courante");
		lblSauver.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nomSauvegarde = new Text(compo,SWT.BORDER);
		nomSauvegarde.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		buttonSauver = toolkit.createButton(compo, "", SWT.NONE);
		buttonSauver.setImage(Activator.getImageDescriptor(PaCompositeSectionSauverCharger.iconSave).createImage());
		buttonSauver.setToolTipText("Enregistrer la recherche afin de pouvoir la réutiliser.");
		
		buttonExporter = toolkit.createButton(compo, "", SWT.NONE);
		buttonExporter.setImage(Activator.getImageDescriptor(PaCompositeSectionSauverCharger.iconExport).createImage());
		buttonExporter.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		buttonExporter.setToolTipText("Exporter la recherche en cours.");
		
		infoSauvegarde = new Text(compo,SWT.READ_ONLY);
		infoSauvegarde.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			
		desc = new Text(compo,SWT.BORDER);
		desc.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,false,false,2,1));
		
		lblCharger = toolkit.createLabel(compo, "(Description)");
		lblCharger.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		// Layout
		GridLayout layout_Etape1 = new GridLayout();
		layout_Etape1.marginWidth = layout_Etape1.marginHeight = 10;
		layout_Etape1.numColumns = 6;
		compo.setLayout(layout_Etape1);
	}

	public Composite getCompo() {
		return compo;
	}

	public Label getText() {
		return label;
	}

	public Label getInfolabel() {
		return infolabel;
	}


	public Combo getCombo() {
		return combo;
	}

	public Label getLabel() {
		return label;
	}

	public Label getLblCharger() {
		return lblCharger;
	}

	public Label getLblSauver() {
		return lblSauver;
	}

	public Text getNomSauvegarde() {
		return nomSauvegarde;
	}

	public Text getInfoSauvegarde() {
		return infoSauvegarde;
	}

	public Text getInfoCharge() {
		return infoCharge;
	}

	public Button getButtonCharger() {
		return buttonCharger;
	}

	public Button getButtonSauver() {
		return buttonSauver;
	}

	public Button getButtonSupprimer() {
		return buttonSupprimer;
	}

	public Button getButtonExporter() {
		return buttonExporter;
	}

	public Button getButtonImporter() {
		return buttonImporter;
	}

	public Text getDesc() {
		return desc;
	}

	
}
