package fr.legrain.silentPdfPrint;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.http.jetty.JettyConfigurator;
import org.eclipse.equinox.http.jetty.JettyConstants;
//import org.eclipse.help.internal.appserver.WebappManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class LgrSpooler extends ArrayList {
	
	static Logger logger = Logger.getLogger(LgrSpooler.class.getName());
	
	private static LgrSpooler lgrSpooler = null;
	private static boolean started = false;
	private static boolean auto = false;
	private static String context = "print";
	private static String servletPath = "PDFPrint";
	private static String port = "8079";
	private static String host = "localhost";
	
	private Shell shellBrowser;
	private org.eclipse.swt.browser.Browser swtBrowser;
	private ProgressListener progressListener = null;
	
	private LgrSpooler () {
		super();
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				shellBrowser = new Shell();
				swtBrowser = new org.eclipse.swt.browser.Browser(shellBrowser,SWT.NULL);
			}
		});
	}
	
	static public LgrSpooler getInstance() {
		if(lgrSpooler == null)
			lgrSpooler = new LgrSpooler();
		return lgrSpooler;
	}
	
	public void print(boolean preview) {
		if(preview) {
			printEditorPreview();
			//printExternal();
		} else {
			//printSimple();
			printWithProgress();
		}
	}
	
	public static boolean ensureWebappRunning() {
		if (!started) {
			//try {
				//WebappManager.start("/"+context, SilentPdfPrintPlugin.PLUGIN_ID, Path.EMPTY);
				started = true;
			//} catch (CoreException e) {
			//	logger.error("",e);
			//}
		}
		return started;
	}
	
	public static boolean startJetty() {
		if (!started) {
			final Dictionary d = new Hashtable();
			d.put(JettyConstants.HTTP_PORT, new Integer(port)); 

			// set the base URL
			d.put(JettyConstants.CONTEXT_PATH, "/"+context);
			//		d.put("other.info", "org.eclipse.help");

			// suppress Jetty INFO/DEBUG messages to stderr
			//Logger.getLogger("org.mortbay").setLevel(Level.WARNING);
			
			try {
				JettyConfigurator.startServer(SilentPdfPrintPlugin.PLUGIN_ID+".server", d);
				started = true;
			} catch (Exception e) {
				logger.error("",e);
				started = false;
			}
		}
		return started;
	}
	
	public void printEditorPreview() {
		startJetty();
//		ensureWebappRunning();
//		WebappManager.getPort();
		
		String url = null;
//		url = "http://" + WebappManager.getHost() + ":" + WebappManager.getPort() + "/"+context+"/" + servletPath;
		url = "http://"+host+":"+port+"/"+context+"/"+servletPath;
		
		//url+="?action=0&sub=1"; //form
		//url+="?action=1&sub=1"; //pdf
		url+="?action=1&preview=1"; 
		url += "&__document=doc"+new Date().getTime();
		
		final String u = url;
		if(logger.isDebugEnabled())
			logger.debug(url);
		
		try {
			
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getBrowserSupport().createBrowser
						(IWorkbenchBrowserSupport.AS_EDITOR, "myId", 
								"Prévisualisation" ,
						"Prévisualisation").openURL(new URL(u));
					} catch (PartInitException e) {
						logger.error("",e);
					} catch (MalformedURLException e) {
						logger.error("",e);
					}
				}
			});
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
//	public void printSimple() {
//		
//		ensureWebappRunning();
//		WebappManager.getPort();
//		
//		String url = "http://" + WebappManager.getHost() + ":" +            
//			WebappManager.getPort() + "/"+context+"/" + servletPath;
//		
//		//url+="?action=0&sub=1"; //form
//		//url+="?action=1&sub=1"; //pdf
//		url+="?action=1"; 
//		
//		final String u = url;
//		
//		if(logger.isDebugEnabled())
//			logger.debug(url);
//		
//		try {			
//			swtBrowser.setUrl(url);
//		} catch (Exception e) {
//			logger.error("",e);
//		}
//	}
		
	public void printWithProgress() {
		
//		ensureWebappRunning();
//		WebappManager.getPort();
//		
//		String url = "http://" + WebappManager.getHost() + ":" +            
//			WebappManager.getPort() + "/"+context+"/" + servletPath;
		
		startJetty();
//		ensureWebappRunning();
//		WebappManager.getPort();
		
		String url = null;
//		url = "http://" + WebappManager.getHost() + ":" + WebappManager.getPort() + "/"+context+"/" + servletPath;
		url = "http://"+host+":"+port+"/"+context+"/"+servletPath;
		
		//url+="?action=0&sub=1"; //form
		//url+="?action=1&sub=1"; //pdf
		url+="?action=1"; 
				
		final String u = url;
		
		if(logger.isDebugEnabled())
			logger.debug(url);
		
		try {
			
			Job job = new Job("Impression ...") {
				protected IStatus run(IProgressMonitor monitor) {
					final int ticks = 6000;
					final int[] fini = new int[]{1};
					monitor.beginTask("Impression ...", monitor.UNKNOWN);
					try {
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
							public void run() {
								progressListener = new ProgressListener() {
									
									public void changed(ProgressEvent event) {}
									
									public void completed(ProgressEvent event) {
										fini[0]=0;
									}
									
								};
								swtBrowser.removeProgressListener(progressListener);
								swtBrowser.addProgressListener(progressListener);
								swtBrowser.setUrl(u);
							}
						});
						while(fini[0]==1) {
							if (monitor.isCanceled())
								return Status.CANCEL_STATUS;
							
							monitor.worked(1);
						}
						monitor.subTask("Impression ...");
					} finally {
						monitor.done();
					}
					return Status.OK_STATUS;
				}
			};
			
			job.setPriority(Job.SHORT);
			//job.setUser(true);
			job.schedule(); // start as soon as possible
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	@Override
	public boolean add(Object o) {
		return super.add(o);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		swtBrowser.dispose();
		shellBrowser.dispose();
	}

}
