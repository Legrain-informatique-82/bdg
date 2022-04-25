package fr.legrain.gestionCommerciale;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.UIManager;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4jFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.IB_APPLICATION;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.libMessageLGR.LgrMess;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IPlatformRunnable {
	
	static Logger logger = Logger.getLogger(Application.class.getName());
	File fileLock = null;
	FileChannel fileChannelLock = null;
	FileLock lock = null;

	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
	 */
	public Object run(Object args) throws Exception {
		initLog4j();
		
		initInstanceLocation();
		for (int i = 0; i < ((String[]) args).length; i++) {
			if(((String[]) args)[i].equals("-typeVersion")){
				if(((String[]) args)[i+1].equals("Distribution"))
					LgrMess.setDEVELOPPEMENT(false,false);
				else LgrMess.setDEVELOPPEMENT(true,false);
			}
			if(((String[]) args)[i].equals("-MAJ")){
				if(((String[]) args)[i+1].equals("0"))
					LgrMess.setTOUJOURS_MAJ(false);
				else LgrMess.setTOUJOURS_MAJ(true);
			}	
		}


		if (logger.isDebugEnabled()) {
			logger.debug("Lancement : Log4J application Eclipse RCP");
			
		}
				
		//Chemin d'installation du workspace / racine de l'appli
		IPath workspace = Platform.getLocation();
		File file = new File(workspace.toString());
		String fullPath = file.getAbsolutePath();
		//System.out.println("path : "+fullPath);
		logger.info("InstanceLocation : "+Platform.getInstanceLocation().getURL().getFile());
		logger.info("InstallLocation : "+Platform.getInstallLocation().getURL().getFile());
		logger.info("UserLocation : "+Platform.getUserLocation().getURL().getFile());

		logger.info("OS : "+Platform.getOS());
		Display display = null;
		try {
			//if(lockWorkspace()) {
			if(true) { //uniquement pour tester les acces concurrent sur la meme machine
				//	UtilWorkspace uw = new UtilWorkspace();
				//	uw.verifyWorkplace();
				
				//Pour Swing : LookAndFeel natif
				//Problème avec la version Cocoa d'OSX
				//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				display = PlatformUI.createDisplay();
				

				int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
				
				if (logger.isDebugEnabled()) {
					logger.debug("Fin : Log4J application Eclipse RCP");
				}

				if (returnCode == PlatformUI.RETURN_RESTART) {
					return IPlatformRunnable.EXIT_RESTART;
				}
				return IPlatformRunnable.EXIT_OK;
			} else {
				return IPlatformRunnable.EXIT_OK;
			}
		} finally {
			if(display!=null)
				display.dispose();
			IB_APPLICATION.fermetureConnectionBdd();
			new JPABdLgr().fermetureConnection();
			unlockFile();
		}
	}
	
	/**
	 * Initialiation des loggers à partir du fichier de configuration
	 * @throws Exception
	 */
	private void initLog4j() throws Exception {
		URL urlConfigFileLog4j = Platform.asLocalURL(GestionCommercialePlugin.getDefault().getBundle().getEntry("/Bd/log4j.properties"));
		URI uriConfigFileLog4j = new URI("file",urlConfigFileLog4j.getAuthority(),urlConfigFileLog4j.getPath(),urlConfigFileLog4j.getQuery(),null);
		PropertyConfigurator.configure(new File(uriConfigFileLog4j).getAbsolutePath());
		//PropertyConfigurator.configure( Const.C_FICHIER_CONF_LOG4J);
		
		//
		// Set up Commons Logging to use the Log4J Logging
		//
//		System.getProperties().setProperty(LogFactory.class.getName(),Log4jFactory.class.getName());

	}
	
	private void initInstanceLocation() {
		String C_DEFAULT_INSTANCE_LOCATION_WIN32 = "c:/lgrdoss/BureauDeGestion";
		
		try {
			Location loc = Platform.getInstanceLocation();
			if(!loc.isSet()) { // -data @noDefault or -data not specified
				if(Platform.getOS().equals(Platform.OS_WIN32)) {
					URL choice = new File(C_DEFAULT_INSTANCE_LOCATION_WIN32).toURL();
					logger.info("location set");
					
					if (loc.setURL(choice, true)) {
						logger.debug("initInstanceLocation OK");
					} else {
						//logger.debug("initInstanceLocation");
					}	
				}
			} else {
				logger.debug("InstanceLocation déjà spécifiée.");
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private boolean lockWorkspace() {
		boolean res = true;
		try {
//			fileLock = new File(Platform.getInstanceLocation().getURL().getFile()+".lock");
//					
////			if (lock.exists()) {  
////				// Supprimer le fichier verrou si il existe
////				lock.delete();  
////			}  
//			// Verrouille le fichier
//			fileChannelLock = new RandomAccessFile(fileLock, "rw").getChannel();  
//			lock = fileChannelLock.tryLock();  
//			
//			//System.err.println(fileLock.toString());
//			if(lock == null)  {  
//				// Le fichier est deja verrouille par une autre application
//				fileChannelLock.close();  
//				//throw new RuntimeException("");  
//				res = false;
//			}  
			return res;
		} catch (Exception e) {
			logger.error("",e);
			return res;
		}
	}

	private void unlockFile() {  
		// release and delete file lock  
		try {  
			if(lock != null) {  
				lock.release();  
				fileChannelLock.close();  
				fileLock.delete();  
			}  
		} catch(Exception e) {  
			logger.error("",e);
		}  
	}

 
		

}
