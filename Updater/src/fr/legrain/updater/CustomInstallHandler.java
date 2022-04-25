package fr.legrain.updater;

//import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;


public class CustomInstallHandler extends org.eclipse.update.core.BaseInstallHandler {
											//org.eclipse.update.core.DefaultInstallHandler
	
//	static Logger logger = Logger.getLogger(CustomInstallHandler.class.getName());
	
	public CustomInstallHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void completeConfigure() throws CoreException {
		super.completeConfigure();
		System.out.println("CustomInstallHandler.completeConfigure()");
//		logger.debug("completeConfigure");
	}

	@Override
	public void configureCompleted(boolean success) throws CoreException {
		super.configureCompleted(success);
		System.out.println("CustomInstallHandler.configureCompleted()");
//		logger.debug("configureCompleted");
	}

	@Override
	public void configureInitiated() throws CoreException {
		super.configureInitiated();
		System.out.println("CustomInstallHandler.configureInitiated()");
//		logger.debug("configureInitiated");
	}

	@Override
	public void installCompleted(boolean success) throws CoreException {
		super.installCompleted(success);
		System.out.println("CustomInstallHandler.installCompleted()");
//		logger.debug("installCompleted");
	}

	@Override
	public void installInitiated() throws CoreException {
		super.installInitiated();
		System.out.println("CustomInstallHandler.installInitiated()");
//		logger.debug("installInitiated");
	}
	//surcharge les methodes
}
