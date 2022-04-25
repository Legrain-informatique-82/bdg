package fr.legrain.javascript.editors;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.legrain.javascript.editors";

	// The shared instance
	private static Activator plugin;
	private JettyServer srv;
	
	/**
	 * The constructor
	 */
	public Activator() {
//		ContextHandlerCollection contexts = new ContextHandlerCollection();
//		
//		contexts.setHandlers(new Handler[] 
//			{ new AppContextBuilder().buildWebAppContext()});
//		
//		srv = new JettyServer();
//		srv.setHandler(contexts);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
//		srv.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
//		srv.stop();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
