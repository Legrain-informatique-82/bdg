package fr.legrain.bdg.ui.css.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class HandlerSelectionTheme extends AbstractHandler {
	
	static boolean theme = true;
	static private final String t1 = "fr.legrain.bdg.ui.css.theme1";
	static private final String t2 = "fr.legrain.bdg.ui.css.theme2";
	static private final String t3 = "fr.legrain.bdg.ui.css.theme3";
	static private final String t4 = "fr.legrain.bdg.ui.css.theme4";
	
	/**
	 * The constructor.
	 */
	public HandlerSelectionTheme() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"Css",
//				"Hello, Eclipse world");
		theme=!theme;
//		if(theme) {
//			testCSS(t1);
////			testCSS(t2);
////			testCSS(t3);
////			testCSS(t4);
//		} else {
////			testCSS(t1);
////			testCSS(t2);
////			testCSS(t3);
//			testCSS(t4);
//		}
		testCSS(t4);
		return null;
	}
	
	public void testCSS(String themeID) {
    	Bundle b=FrameworkUtil.getBundle(getClass());
    	BundleContext context=b.getBundleContext();
    	ServiceReference serviceRef=context.getServiceReference(IThemeManager.class.getName());
    	IThemeManager themeManager=(IThemeManager)context.getService(serviceRef);
    	final IThemeEngine engine= themeManager.getEngineForDisplay(Display.getCurrent());
    	engine.setTheme(themeID,true);
    }
}
