package fr.legrain.gestCom.librairiesEcran.swt;


import java.util.Date;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;

public class DateTimeCellEditor extends CellEditor {
	


	public DateTimeCellEditor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DateTimeCellEditor(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public DateTimeCellEditor(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private ModifyListener modifyListener;
	@Override
	protected Object doGetValue() {
		// TODO Auto-generated method stub
		 return LibDateTime.getDate(((DateTime)getControl()));
	}

	@Override
	protected void doSetValue(Object value) {
		// TODO Auto-generated method stub
        Assert.isTrue(getControl() != null && (value instanceof Date));
        LibDateTime.setDate((DateTime)getControl(), (Date)value) ;
//        text.removeModifyListener(getModifyListener());
//        text.setText(LibConversion.bigDecimalToString((BigDecimal)value));
//        text.addModifyListener(getModifyListener());
    }
	
//    private ModifyListener getModifyListener() {
//        if (modifyListener == null) {
//            modifyListener = new ModifyListener() {
//                public void modifyText(ModifyEvent e) {
//                    editOccured(e);
//                }
//            };
//        }
//        return modifyListener;
//    }

	@Override
	protected Control createControl(Composite parent) {
		// TODO Auto-generated method stub
		return new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
	}

	@Override
	protected void doSetFocus() {
		// TODO Auto-generated method stub
		
	}
}
