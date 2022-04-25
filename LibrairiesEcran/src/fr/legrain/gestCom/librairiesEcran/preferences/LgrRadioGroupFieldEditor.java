package fr.legrain.gestCom.librairiesEcran.preferences;

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class LgrRadioGroupFieldEditor extends RadioGroupFieldEditor {
	
	private int nbBouton = 0;

    public LgrRadioGroupFieldEditor(String name, String labelText, int numColumns,
            String[][] labelAndValues, Composite parent) {
    	super(name, labelText, numColumns, labelAndValues, parent, false);
    	nbBouton = labelAndValues.length;
    }
    
    public LgrRadioGroupFieldEditor(String name, String labelText, int numColumns,
            String[][] labelAndValues, Composite parent, boolean useGroup) {
    	super(name, labelText, numColumns, labelAndValues, parent, useGroup);
    	nbBouton = labelAndValues.length;
    }

	public Composite getRadioGroup(Composite parent) {
		return getRadioBoxControl(parent);
	}
	
	public Button[] getRadioButton(Composite parent) {
		Button b[] = new Button[nbBouton];
		Composite c = getRadioBoxControl(parent);
		for(int i = 0, j = 0; i<c.getChildren().length && j<nbBouton; i++ ) {
			if(c.getChildren()[i] instanceof Button) {
				if (SWT.RADIO==(((Button) c.getChildren()[i]).getStyle() & SWT.RADIO)){
					//c'est bien un bouton radio
					b[j] = (Button) c.getChildren()[i];
					j++;
				}
			}
		}
		return b;
	}
	
}
