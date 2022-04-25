package fr.legrain.rappeler;



import org.apache.log4j.Logger;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RappelerPlugin extends AbstractUIPlugin {

	static Logger logger = Logger.getLogger(RappelerPlugin.class.getName());
	// The plug-in ID
	public static final String PLUGIN_ID = "fr.legrain.rappeler";

	// The shared instance
	private static RappelerPlugin plugin;
	
	/**
	 * The constructor
	 */
	public RappelerPlugin() {
	
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.getWorkbench().getActiveWorkbenchWindow().
		addPerspectiveListener(new PerspectiveAdapter() {

			@Override
			//TODO a finir
			public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
				// TODO Auto-generated method stub
				super.perspectiveActivated(page, perspective);
			}	
			
			public void perspectiveClosed(IWorkbenchPage page,
		            IPerspectiveDescriptor perspective) {
				logger.debug("perspectiveClosed :"+perspective.getLabel());
		    }
			
			public void perspectiveDeactivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
				logger.debug("perspectiveDeactivated :"+perspective.getLabel());
		    }
			
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RappelerPlugin getDefault() {
		return plugin;
	}

}
