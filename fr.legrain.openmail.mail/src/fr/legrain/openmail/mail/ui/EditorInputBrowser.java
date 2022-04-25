package fr.legrain.openmail.mail.ui;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInputBrowser implements IEditorInput {
	
	static Logger logger = Logger.getLogger(EditorInputBrowser.class.getName());
	
	private ParamAfficheBrowser paramAfficheBrowser = null;
	
	public EditorInputBrowser(ParamAfficheBrowser input) {
		this.paramAfficheBrowser = input;
	}

	public ParamAfficheBrowser getParamAfficheBrowser() {
		return paramAfficheBrowser;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return paramAfficheBrowser.titre != null ? paramAfficheBrowser.titre : "";
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EditorInputBrowser)
			return paramAfficheBrowser.titre.equals(((EditorInputBrowser)obj).getName());
		else
			return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return paramAfficheBrowser.titre != null ? paramAfficheBrowser.titre : "";
	}
	
//	public static void openURL(String url, String title, String tooltip) throws PartInitException {
//		openURL(url, null, null, title, tooltip);
//	}
//
//	public static void openURL(String url, String postData, String[] httpHeader, String title, String tooltip) throws PartInitException {
//		if(url!=null) {
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(editorInputBrowser, EditorBrowser.ID);
//
//			ParamAfficheBrowser paramAfficheBrowser = new ParamAfficheBrowser();
//			paramAfficheBrowser.setUrl(url);
//			paramAfficheBrowser.setHttpHeader(httpHeader);
//			paramAfficheBrowser.setPostData(postData);
//			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
//			((JPALgrEditorPart)e).getController().configPanel(paramAfficheBrowser);
//		}
//	}
	
//	public ResultAffiche configPanel(ParamAffiche param) {
//		if (param != null) {
//			if(param instanceof ParamAfficheBrowser) {
//				if(((ParamAfficheBrowser)param).getPostData()!=null){
//					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl(), 
//							((ParamAfficheBrowser)param).getPostData(),
//							((ParamAfficheBrowser)param).getHttpHeader()
//							);
//				} else if(((ParamAfficheBrowser)param).getUrl()!=null){
//					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl());
//				} else {
//					//vue.getBrowser().setUrl("http://www.google.fr");
//				}
//				
//				if(((ParamAfficheBrowser)param).getTitre()!=null){
//					vue.getBrowser().setUrl(((ParamAfficheBrowser)param).getUrl());
//				} else {
//					//vue.getBrowser().setUrl("http://www.google.fr");
//				}
//			}
//		}
//		vue.getBrowser().setFocus();
//		return null;
//	}


}
