package fr.legrain.ajoutereditiondemo.controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.ajoutereditiondemo.divers.constPlugin;
import fr.legrain.ajoutereditiondemo.swt.SwtInfoGestionEditionPersonnelle;

public class SwtDemoEditionPerControl {

	static Logger logger = Logger.getLogger(SwtDemoEditionPerControl.class.getName());
	private Shell shell;
	SwtInfoGestionEditionPersonnelle swtInfoGestionEditionPersonnelle = null;
	
	String TITLE_URL = "Bureau de Gestion";
	
	public SwtDemoEditionPerControl(Shell shell) {
		super();
		this.shell = shell;
		swtInfoGestionEditionPersonnelle = new SwtInfoGestionEditionPersonnelle(shell, SWT.NULL);
	}
	
	
	public void init() {
		
		Label lbInfoSupp = swtInfoGestionEditionPersonnelle.getInfoSupp();
		lbInfoSupp.setText(constPlugin.CONTENT_SUPP);
		initInfosGestionPersonnelle(swtInfoGestionEditionPersonnelle);
	}
	public void initAction() {
		actionBtCommander(swtInfoGestionEditionPersonnelle);
	}
	
	public void actionBtCommander(SwtInfoGestionEditionPersonnelle swtGEP){
		Button bt = swtGEP.getBtCommander();
		bt.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String url = constPlugin.HTTP_URL;
				
							final String finalURL = url;
							PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
								public void run() {
									try {
										PlatformUI.getWorkbench().getBrowserSupport()
										.createBrowser(
												IWorkbenchBrowserSupport.AS_EDITOR,
												"myId",
												TITLE_URL,//finalURL,
												finalURL
										).openURL(new URL(finalURL));
				
									} catch (PartInitException e) {
										logger.error("",e);
									} catch (MalformedURLException e) {
										logger.error("",e);
				
									}
								}	
							});
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
	}
	
	public void initInfosGestionPersonnelle(SwtInfoGestionEditionPersonnelle swtGEP){
		final StyledText styledText  = swtGEP.getStyledTextInfos();
		
		
		Font font = new Font(shell.getDisplay(), "Courier New", 12, SWT.NORMAL);
	    styledText.setFont(font);
	    
	    styledText.setText(constPlugin.CONTENT);

//        final StyleRange hyperlinkStyle = new StyleRange(); 
//        String linkWord = "http://www.google.com/"; 
//        hyperlinkStyle.start = constPlugin.CONTENT.indexOf(linkWord); 
//        hyperlinkStyle.length = linkWord.length(); 
//        hyperlinkStyle.fontStyle = SWT.BOLD; 
//        hyperlinkStyle.foreground = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE); 
//        hyperlinkStyle.underlineStyle = SWT.UNDERLINE_LINK; 
//        hyperlinkStyle.data = "http://www.google.com/";
//        styledText.setStyleRange(hyperlinkStyle); 
// 
// 
//        styledText.addMouseListener(new MouseAdapter() { 
//                public void mouseUp(MouseEvent arg0) { 
//                        Point clickPoint = new Point(arg0.x, arg0.y); 
//                        try { 
//                                int offset = styledText.getOffsetAtLocation(clickPoint); 
//                                if (styledText.getStyleRangeAtOffset(offset) != null) { 
//                                        System.out.println("link"); 
//                                } 
//                        } catch (IllegalArgumentException e) { 
//                                //ignore, clicked out of text range. 
//                        } 
//                }}); 


	}
}
