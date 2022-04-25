
package fr.legrain.lib.validation;

import java.util.Date;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
//import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Widget;

import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.cdatetimeLgr;

/**
 * Observable value for  CDateTime.
 * 
 */
public class CDateTimeObservableValue extends AbstractObservableValue implements ISWTObservableValue {
//http://eclipsesource.com/blogs/2009/02/03/databinding-a-custom-observable-for-your-widget/
//http://www.javadocexamples.com/java_source/org/eclipse/jface/internal/databinding/provisional/swt/AbstractSWTObservableValue.java.html
//public class CDateTimeObservableValue extends AbstractSWTObservableValue {
	
	private CDateTime dateTime;
	private boolean updating = false;
	private Date currentValue;
	
	private final Widget widget;
	
	private DisposeListener disposeListener = new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
			CDateTimeObservableValue.this.dispose();
		}
	};

	/**
	 * @param CDateTime
	 */
	public CDateTimeObservableValue(CDateTime dateTime) {
		//super();
		super(SWTObservables.getRealm(dateTime.getDisplay()));
		this.dateTime = dateTime;
		this.currentValue = (Date) doGetValue();
		this.widget = dateTime;
		
		 widget.addDisposeListener(disposeListener);

		
//		dateTime.addSelectionListener(new SelectionAdapter() {
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				widgetSelected(e);
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				if (!updating) {
//					Object oldValue = currentValue;
//					currentValue = (Date) doGetValue();
//					fireValueChange(Diffs.createValueDiff(oldValue,currentValue));
//				}
//			}
//		});
		
		dateTime.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void focusLost(FocusEvent e) {
				if (!updating) {
					Object oldValue = currentValue;
					currentValue = (Date) doGetValue();
					fireValueChange(Diffs.createValueDiff(oldValue,currentValue));
				}
			}}
		);
		
//		dateTime.addListener(SWT.Selection, new Listener() {
//
//			public void handleEvent(Event event) {
//				if (!updating) {
//					Object oldValue = currentValue;
//					currentValue = (Date) doGetValue();
//					fireValueChange(Diffs.createValueDiff(oldValue,currentValue));
//				}
//			}
//
//		});
	}
	
	/**
	 * @return Returns the widget.
	 */
	public Widget getWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	protected Object doGetValue() {
		//Le fait de ne pas faire ce test permet d'enregistrer dans la base des dates nulles
		//Si on fait ce test (surtout le else) même si on a rien saisie dans l'interface on enregistrera la date du jour
		//Si on ne peu pas avoir de valeur nulle, cela obligera à avoir une date par defaut qui ne veut rien dire (exemple : 01/01/1900)
//		if(dateTime.getSelection()!=null)
			return dateTime.getSelection();
//		else
//			return new Date();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	public Object getValueType() {
		return Date.class;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	protected void doSetValue(Object value) {
		Date oldValue = null;
		if (dateTime.getSelection() != null)
			oldValue = dateTime.getSelection();
		try {
			updating = true;
			if(value instanceof String){
				dateTime.setSelection(LibDate.stringToDateNew((String)value));
				currentValue = LibDate.stringToDateNew((String)value);				
			}else
			{
			dateTime.setSelection((Date) value);
			currentValue = (Date) value;
			}
		} finally {
			updating = false;
		}
		fireValueChange(Diffs.createValueDiff(oldValue, value));
	}
}
