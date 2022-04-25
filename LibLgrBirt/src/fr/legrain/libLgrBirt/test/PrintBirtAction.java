package fr.legrain.libLgrBirt.test;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.silentPdfPrint.LgrSpooler;

public class PrintBirtAction implements IWorkbenchWindowActionDelegate {
	
	//static Logger logger = Logger.getLogger(PreviewBirtAction.class.getName());	
	
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public PrintBirtAction() {
	}
	
	public void widgetSelected(SelectionEvent e)
	{
	
	 // String url = ExampleWebappPlugin.getInstance().getExampleServletURL(servlet_path);
	 // _browser.setUrl(url);
	}
	
//	private static boolean _started = false;
//	public static boolean ensureWebappRunning()
//	{
//	  if (!_started)
//	  {
//	    try {
//			WebappManager.start("/example", "SilentPdfPrint", Path.EMPTY);
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    _started = true;
//	  }
//	return _started;
//	}
	
	public void run(IAction action) {
				
		LgrSpooler spooler = LgrSpooler.getInstance();
		spooler.add("c:/aaaaaaaaaa.pdf");
		//spooler.add("c:/run.pdf");
		//spooler.add("c:/a.pdf");
		//spooler.print();
		spooler.print(false);
		
//		ensureWebappRunning();
//		WebappManager.getPort();
//		//public String getExampleServletURL(/PluggedIn);
//		
//		   String s = "http://" + WebappManager.getHost() + ":" +            
//		         WebappManager.getPort() + "/example/" + "pluggedin";
//		   //WebappManager.getPort() + "/example/" + "plugin.xml";
//		   s+="?action=0&sub=1";
//		   
//		   System.out.println(s);
//		
//		try {
//			//org.eclipse.birt.report.viewer.browsers.BrowserManager.getInstance().createBrowser(false).displayURL(s);
//			
////			final String finalURL = url;
////			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
////				public void run() {
////			try {
////				PlatformUI.getWorkbench().getBrowserSupport().createBrowser
////				(IWorkbenchBrowserSupport.AS_EDITOR, "myId", 
////						"Prévisualisation de la facture " ,
////						"Prévisualisation de la facture ").openURL(new URL(finalURL));
////			} catch (PartInitException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (MalformedURLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////				}
////			});
//			Shell sh = new Shell();
//			org.eclipse.swt.browser.Browser b = new org.eclipse.swt.browser.Browser(sh,SWT.NULL);
////			b.setUrl(s);
//			
//		} catch (Exception e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}


//		//PDFBox
//		PDDocument doc = null;
//		System.out.println("aa");
//		try {
//			doc = PDDocument.load("C:/aaaaaaaaaa.pdf");
//			
////			PDFMergerUtility m = new PDFMergerUtility();
////			m.setDestinationFileName("c:/a.pdf");
////			m.addSource("C:/aaaaaaaaaa.pdf");
////			m.addSource("C:/aaaaaaaaaa.pdf");
////			m.mergeDocuments();
//			
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}//works 
//		System.out.println("bb");
//		System.out.println(doc.getNumberOfPages());
//		try {
//			doc.print();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		
//		boolean showPrintDialog=true;
//		PrinterJob printJob = PrinterJob.getPrinterJob ();
//		printJob.setJobName ("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/testSampleDB.html");
//		
//		try {
//			if (showPrintDialog) {
//				if (printJob.printDialog()) {
//					printJob.print();
//				}
//			}
//			else
//				printJob.print ();
//		} catch (Exception PrintException) {
//			PrintException.printStackTrace();
//		}
		
		
//		try {
//		PDDocument pdf = PDDocument.load("C:/Documents and Settings/nicolas/Bureau/tiers.pdf"); 
//		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
//		pdf.save(out); 
//		pdf.close(); 
//		byte pdfBytes[] = null; 
//		pdfBytes = out.toByteArray(); 
//		DocFlavor psInFormat = DocFlavor.BYTE_ARRAY.PCL; 
//		Doc myDoc = new SimpleDoc(pdfBytes, psInFormat, null); 
//		PrintService myService = PrintServiceLookup.lookupDefaultPrintService(); 
//		DocPrintJob job = myService.createPrintJob(); 
//		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); 
//		aset.add(MediaSizeName.ISO_A4); 
//		aset.add(new Copies(2)); 
//		PrinterJob printer = PrinterJob.getPrinterJob(); 
//		job.print(myDoc, aset); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		try {
////			PDDocument document = PDDocument.load("C:/Documents and Settings/nicolas/Bureau/tiers.pdf");
//			//PDDocument document = PDDocument.load("C:/Documents and Settings/nicolas/Bureau/Documentation TransDoc Client.pdf");
//			//document.print(); // works fine with dialog... 
//			PrinterJob job = PrinterJob.getPrinterJob(); 
//			int numPages = document.getNumberOfPages(); 
//			for(int j = 0; j < numPages; j++) { 
//				job.setPrintable(document.getPrintable(j)); 
//				job.print(); 
//			} 
//			document.close(); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		//PDFBox
//		PDDocument doc = null;
//		try {
//			doc = PDDocument.load("C:/aaaaaaaaaa.pdf");
//			doc.print();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}//works 
//		System.out.println(doc.getNumberOfPages());//returns 5 
//		try {
//			doc.print();
//		} catch (PrinterException e) {
//			e.printStackTrace();
//		}//prints only the first page!!! 
		
//		System.out.println("Debug : PrintBirtAction - 1");
//		DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST;
//		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//		aset.add(MediaSizeName.ISO_A4);
//		System.out.println("Debug : PrintBirtAction - 2");
//		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
//		System.out.println("2.5 "+pservices.toString());
//		if (pservices.length > 0) {
//			DocPrintJob pj = pservices[0].createPrintJob();
//			// InputStreamDoc is an implementation of the Doc interface //
//			FileInputStream fis = null;
//			try {
//				System.out.println("Debug : PrintBirtAction - 3");
//				fis = new FileInputStream("C:/Documents and Settings/nicolas/Bureau/tiers.pdf");
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			System.out.println("Debug : PrintBirtAction - 4");
//			Doc doc = new SimpleDoc(fis, flavor, null);
//			try {
//				System.out.println("Debug : PrintBirtAction - 5");
//				pj.print(doc, aset);
//				System.out.println("Debug : PrintBirtAction - 6");
//			} catch (PrintException e) { 
//				e.printStackTrace();
//			}
//		}		
		
		//ADOBE VIEWER
//		Viewer v;
//		try {
//			
//			Frame frame = new Frame("Test Viewer");
//			   frame.setLayout(new BorderLayout());
//			   v = new Viewer(false);
//			   //frame.add(v);
//			   frame.add(v,BorderLayout.CENTER);
//			   v.activate();
//			  // frame.setSize(400, 500);
//			  // frame.show();
//			//v = new Viewer(true);
//			v.openNewWindow();
//			v.setDocumentURL("file:///C:/Documents and Settings/nicolas/Bureau/tiers.pdf");
//			//v.setDocumentInputStream(new FileInputStream("C:/Documents and Settings/nicolas/Bureau/tiers.pdf"));
//			
//			//v.printPages(1, 1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//ESSAI FORUM 
		//PDFPrintLibClass.printPDF("C:/Documents and Settings/nicolas/Bureau/tiers.pdf",
		//		"PDFCreator");
		
//		PrinterData printerData = new PrinterData();
//		org.eclipse.swt.printing.Printer printer = new org.eclipse.swt.printing.Printer();
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}

class PDFPrintLibClass {
    /********************************************************************************************
     * printPDF
     * Prints a PDF using Adobe Reader "/t" (print and terminate)
     * @param File File to be printed, for example, C:\MYFILE.PDF
     * @param Printer Printer, for example, \\MYSERVER\MYPRINTER
     **********************************************************************************************/
    
    public static void printPDF(String pFile, String pPrinter) {
        final String PATH_ADOBE_READER = "C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader\\AcroRd32.exe";
        final String ADOBE_READER_PRINT_COMMAND = "/h /t";
        final String SLASH = "/";
        final String QUOTE = "\"";
        final String SPACE = " ";
        
// Command to be executed
        String lCommand = QUOTE + PATH_ADOBE_READER + QUOTE + SPACE +
                ADOBE_READER_PRINT_COMMAND + SPACE +
                QUOTE + pFile + QUOTE + SPACE +
                QUOTE + pPrinter + QUOTE;
        
        System.out.println("[printPDF] Command to be executed : " + lCommand);
        
        Process lAdobeBackGroundProcess = null;
        Process lAdobeProcess = null;
        
        try {
// Must create a background Adobe Reader process (don't ask why, just do it;-)
          //  lAdobeBackGroundProcess = Runtime.getRuntime().exec(PATH_ADOBE_READER);
            
// Execute the Adobe Reader command "/t" (print and terminate)
            lAdobeProcess = Runtime.getRuntime().exec(lCommand);
            
// Wait for Adobe Reader to complete
            int exitVal = lAdobeProcess.waitFor();
            System.out.println("exitVal : "+exitVal);
            lAdobeProcess.destroy();
            if ( exitVal != 0 ) {
                throw new Exception("[printPDF] Adobe Reader process exitVal : " + exitVal);
            }
        } catch (Exception e) {
            System.err.println("[printPDF] Error printing PDF : " + pFile);
            e.printStackTrace();
        } finally {
            if (lAdobeBackGroundProcess != null) {
                lAdobeBackGroundProcess.destroy();
                lAdobeBackGroundProcess = null;
            }
        }
    }
    /** Creates a new instance of PDFPrintLibClass */
    public PDFPrintLibClass() {
    }
    
}