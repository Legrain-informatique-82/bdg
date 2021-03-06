package googlemap;

import googlemap.navigateur.GoogleMapInterface;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;


//import fr.legrain.archivageepicea.Projet_swt.MoteurInterface_lee;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GoogleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public GoogleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	
		GoogleMapInterface  map  = new GoogleMapInterface();
		map.afficheGoogleMap();
		
		return null;
	}
}
