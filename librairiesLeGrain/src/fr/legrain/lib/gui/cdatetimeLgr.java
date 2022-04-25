package fr.legrain.lib.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Composite;



public class cdatetimeLgr extends CDateTime {

	public cdatetimeLgr(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return true iff the {@link #text} field is in a fit state to be used
	 */
	protected boolean checkText() {
		return (text != null && !text.isDisposed());
	}
	
	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when the receiver's text is modified.
	 *
	 * @param listener the listener which should no longer be notified
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see ModifyListener
	 * @see #addModifyListener
	 */
	public void removeModifyListener (ModifyListener listener) {
		checkWidget();
		if (listener == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
		if(checkText()) text.removeModifyListener(listener);
	}
}
