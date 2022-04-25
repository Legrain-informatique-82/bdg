package fr.legrain.gestCom.librairiesEcran.swt;

import java.math.BigDecimal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.lib.data.LibConversion;

public class BigDecimalCellEditor extends TextCellEditor {
	


	public BigDecimalCellEditor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimalCellEditor(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public BigDecimalCellEditor(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private ModifyListener modifyListener;
	@Override
	protected Object doGetValue() {
		// TODO Auto-generated method stub
		 return LibConversion.stringToBigDecimal(text.getText());
	}

	@Override
	protected void doSetValue(Object value) {
		// TODO Auto-generated method stub
        Assert.isTrue(text != null && (value instanceof BigDecimal));
        text.removeModifyListener(getModifyListener());
        text.setText(LibConversion.bigDecimalToString((BigDecimal)value));
        text.addModifyListener(getModifyListener());
    }
	
    private ModifyListener getModifyListener() {
        if (modifyListener == null) {
            modifyListener = new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    editOccured(e);
                }
            };
        }
        return modifyListener;
    }
}
