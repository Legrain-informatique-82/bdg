package fr.legrain.lib.gui.fieldassist;

import org.eclipse.jface.fieldassist.IControlCreator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.cdatetimeLgr;


/**
 * An {@link IControlCreator} for CDateTime controls. This is a convenience class
 * for creating controls to be supplied to a decorated field.
 * Les styles applicables sont ceux de org.eclipse.swt.nebula.widgets.cdatetime.CDT
 */
public class CDateTimeControlCreator implements IControlCreator {

	public Control createControl(Composite parent, int style) {
		return new cdatetimeLgr(parent, style);
	}

}
