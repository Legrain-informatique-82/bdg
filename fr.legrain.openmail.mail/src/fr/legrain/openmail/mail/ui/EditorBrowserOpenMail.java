package fr.legrain.openmail.mail.ui;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class EditorBrowserOpenMail  extends EditorPart /*extends JPALgrEditorPart implements ILgrEditor, ILgrRetourEditeur*/ {

	static Logger logger = Logger.getLogger(EditorBrowserOpenMail.class.getName());
	
	public static final String ID = "fr.legrain.openmail.mail.ui.EditorBrowserOpenMail";
	
	private boolean enabled = true;
	private PaBrowserSWT composite = null;
	

	public EditorBrowserOpenMail() {
		super();
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
	    setInput(input);
	    setPartName(input.getName());
	    //PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(LgrPartListener.getLgrPartListener());

	}
	
	public static void openURL(String url, String title, String tooltip) throws PartInitException {
		openURL(url, null, null, title, tooltip);
	}

	public static void openURL(String url, String postData, String[] httpHeader, String title, String tooltip) throws PartInitException {
		if(url!=null) {
			
			ParamAfficheBrowser paramAfficheBrowser = new ParamAfficheBrowser();
			paramAfficheBrowser.setTitre(title);
			paramAfficheBrowser.setUrl(url);
			paramAfficheBrowser.setHttpHeader(httpHeader);
			paramAfficheBrowser.setPostData(postData);
			
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputBrowser(paramAfficheBrowser), EditorBrowserOpenMail.ID);
		}
	}


	@Override
	public void createPartControl(Composite parent) {
		composite = new PaBrowserSWT(parent,SWT.NONE);
		
		IEditorInput input = getEditorInput();
		
		if(input!=null) {
	    	
	    	if(((EditorInputBrowser)input).getParamAfficheBrowser().getUrl() != null) {
	    		if(((EditorInputBrowser)input).getParamAfficheBrowser().getPostData()!=null){
					composite.getBrowser().setUrl(((EditorInputBrowser)input).getParamAfficheBrowser().getUrl(), 
							((EditorInputBrowser)input).getParamAfficheBrowser().getPostData(),
							((EditorInputBrowser)input).getParamAfficheBrowser().getHttpHeader()
							);
				} else if(((EditorInputBrowser)input).getParamAfficheBrowser().getUrl()!=null){
					composite.getBrowser().setUrl(((EditorInputBrowser)input).getParamAfficheBrowser().getUrl());
				} else {
					//vue.getBrowser().setUrl("http://www.google.fr");
				}
				
//				if(((EditorInputBrowser)input).getParamAfficheBrowser().getTitre()!=null){
//					composite.getBrowser().setUrl(((EditorInputBrowser)input).getParamAfficheBrowser().getUrl());
//				} else {
//					//vue.getBrowser().setUrl("http://www.google.fr");
//				}
	    	}
	    }
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		composite.setEnabled(enabled);
		this.enabled = enabled;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	public PaBrowserSWT getComposite() {
		return composite;
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

}
