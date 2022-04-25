package fr.legrain.javascript.editors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Text;


public abstract class AbstractLgrJavascriptEditor {
	
	static Logger logger = Logger.getLogger(AbstractLgrJavascriptEditor.class.getName());
	
	private Browser browser;
	private Text textComponent;
	
	private boolean editable = true;

	abstract public String getBaseURL();
	
	public AbstractLgrJavascriptEditor(Browser browser) {
		this.browser = browser;
		//new CustomFunction (browser, "theJavaFunction");	 
	}
	
	public AbstractLgrJavascriptEditor(Browser browser, Text textComponent) {
		this.browser = browser;
		this.textComponent = textComponent;
	}
	
	private String escapeString(String s) {
		s = s.replaceAll("\"", "\\\\\"");
		return s;
	}
	
	public String getEditorContent() {
//		if(editable) {
			//StringEscapeUtils s = new StringEscapeUtils();
			//browser.evaluate("typeof contenu=='function'");
			String data = browser.evaluate("return contenu();").toString();
			//String data = browser.evaluate("if(typeof contenu=='function')return contenu();").toString();
			logger.debug("sortie de l'editeur: "+data);
			//data = s.escapeHtml(data);
			logger.debug("envoye au tf: "+data);
			return data;
//		} else {
//			return browser.getText();
//		}
	}
	
	public Object setEditorContent(String data) {
//		if(editable) {
			//StringEscapeUtils s = new StringEscapeUtils();
			logger.debug("sortie du tf: "+data);
			//data = s.escapeHtml(data);
			data = escapeString(data);
			logger.debug("entre dans l'editeur: "+data);
			//logger.debug("apres unescape: "+s.unescapeHtml(data));
			return browser.evaluate("return set_contenu(\""+data+"\");");
//		} else {
//			browser.setText(data);
//			return null;
//		}
		
	}

	public Browser getBrowser() {
		return browser;
	}
	
	public void updateTextCompnentFromEditor() {
		String data = getEditorContent();
		if(textComponent!=null && data!=null) {
			textComponent.setText(getEditorContent());
		}
	}
	
	public void updateEditorFromTextCompnent() {
		if(textComponent!=null) {
			setEditorContent(textComponent.getText());
		}
	}
	
//	ProgressListener p = new ProgressListener() {
//		
//		@Override
//		public void completed(ProgressEvent event) {
//			updateEditorFromTextCompnent();
//			System.err.println("===============================================");
//		}
//		
//		@Override
//		public void changed(ProgressEvent event) {
//			// TODO Auto-generated method stub
//			
//		}
//	};
	
	public void setEditable(boolean editable) {
		this.editable = editable;
//		if(editable) {
//			browser.setUrl(getBaseURL());
//			browser.removeProgressListener(p);
//			browser.addProgressListener(p);
//		} else {
//			updateEditorFromTextCompnent();
//		}
		browser.setEnabled(editable);
	}
	
	public boolean isEditable() {
		return this.editable;
	}
	
	// Called by JavaScript
//	class CustomFunction extends BrowserFunction {
//
//		CustomFunction (Browser browser, String name) {
//			super (browser, name);
//		}
//		
//		public Object function (Object[] arguments) {
//			System.out.println ("theJavaFunction() called from javascript with args:");
//			for (int i = 0; i < arguments.length; i++) {
//				Object arg = arguments[i];
//				if (arg == null) {
//					System.out.println ("\t-->null");
//				} else {
//					System.out.println ("\t-->" + arg.getClass ().getName () + ": " + arg.toString ());
//				}
//			}
//			Object returnValue = null;
//
//			return returnValue;
//		}
//	}
}
