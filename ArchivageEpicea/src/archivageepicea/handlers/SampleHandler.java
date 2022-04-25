package archivageepicea.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.legrain.archivageepicea.editor.EditorArchivageEpicea;
import fr.legrain.archivageepicea.editor.EditorInputArchivageEpicea;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"ArchivageEpicea Plug-in",
//				"Hello, Eclipse world");
//		
//		Interface inter = new Interface(window.getShell());
		
		try {
			IEditorPart	e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputArchivageEpicea(), EditorArchivageEpicea.ID);
		
		LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
		
//		SWTPaUniteController swtPaUniteController = new SWTPaUniteController(((EditorUnite)e).getComposite());
//		
//		((LgrEditorPart)e).setController(swtPaUniteController);
//		((LgrEditorPart)e).setPanel(((EditorArchivageEpicea)e).getComposite());
//		
//		ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//		swtPaUniteController.configPanel(paramAfficheUnite);
		
//		ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//		((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getPanel());
//		((LgrEditorPart)e).getController().configPanel(paramAfficheUnite);
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		MoteurInterface m = new MoteurInterface(inter);
		
//		inter.affiche();
		return null;
	}
}
