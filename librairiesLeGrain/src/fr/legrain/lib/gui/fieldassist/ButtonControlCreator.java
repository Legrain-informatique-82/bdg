package fr.legrain.lib.gui.fieldassist;

import org.eclipse.jface.fieldassist.IControlCreator;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * An {@link IControlCreator} for SWT Button controls. This is a convenience class
 * for creating button controls to be supplied to a decorated field.
 */
public class ButtonControlCreator implements IControlCreator {

	public Control createControl(Composite parent, int style) {
		return new Button(parent, style);
	}

}
