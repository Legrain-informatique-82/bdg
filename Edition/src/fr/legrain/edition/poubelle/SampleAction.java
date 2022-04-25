package fr.legrain.edition.poubelle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.edition.actions.AttributElementResport;


/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		ArrayList al = new ArrayList();
		al.add("COMPTE");
		al.add("TIERS");
		al.add("LIBELLE");
		al.add("DATE");
		al.add("REFERENCE");
		al.add("DEBIT");
		al.add("CREDIT");
		String DefautWidthColTable = String.valueOf(100/al.size());	
		
		ArrayList nameFooter = new ArrayList();
		nameFooter.add("Totaux :");
		nameFooter.add("solde :");
		
		ArrayList<String> attributeTableFooter = new ArrayList<String>();
		attributeTableFooter.add("right");
		attributeTableFooter.add("bold");
		
		Map<String,ArrayList<String>> mapListAttributeTableFooter = new LinkedHashMap<String, ArrayList<String>>();
		for (int i = 0; i < nameFooter.size(); i++) {
			mapListAttributeTableFooter.put((String)nameFooter.get(i), attributeTableFooter);
		}
		
		Map<String, AttributElementResport> attribuTabFooter = new LinkedHashMap<String, AttributElementResport>();
		attribuTabFooter.put("Totaux :", new AttributElementResport("","right","Medium","bold","","decimal","EURO",""));
		attribuTabFooter.put("Solde :", new AttributElementResport("","right","Medium","bold","","decimal","EURO",""));
		
		Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
		String nameHeaderTitle = "Informations d'ecriture";
		attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("","center","14pt","bold","","string", "VIDE",""));
		/**
		 * setup values defaut for widthCol
		 */
//		attribuElement elemTabHeaderDefault = new attribuElement(DefautWidthColTable,"center","Medium","bold","in");
//		attribuElement elemTabDatailDefault = new attribuElement(DefautWidthColTable,"left","Medium","normal","in");
//		Map<String, attribuElement> attribuTabHeaderDefaut = new TreeMap<String, attribuElement>();		
//		for(int i = 0; i < al.size(); i++){
//			attribuTabHeaderDefaut.put((String)al.get(i), elemTabHeaderDefault);
//	}
//		
//		Map<String, attribuElement> attribuTabDetailDefaut = new TreeMap<String, attribuElement>();
//		for(int i = 0; i < al.size(); i++){
//			attribuTabDetailDefaut.put((String)al.get(i), elemTabDatailDefault);
//		}
	
		
		Map<String, AttributElementResport> attribuTabHeader = new LinkedHashMap<String, AttributElementResport>();
		attribuTabHeader.put("COMPTE", new AttributElementResport("0.729","center","Medium","bold","in","string","VIDE",""));
		attribuTabHeader.put("TIERS", new AttributElementResport("0.719","center","Medium","bold","in","string","VIDE",""));
		attribuTabHeader.put("LIBELLE", new AttributElementResport("3.073","center","Medium","bold","in","string","VIDE",""));
		attribuTabHeader.put("DATE", new AttributElementResport("0.74","center","Medium","bold","in","date","VIDE",""));
		attribuTabHeader.put("REFERENCE", new AttributElementResport("0.719","center","Medium","bold","in","string","VIDE",""));
		attribuTabHeader.put("DEBIT", new AttributElementResport("0.708","center","Medium","bold","in","decimal","EURO",""));
		attribuTabHeader.put("CREDIT", new AttributElementResport("0.708","center","Medium","bold","in","decimal","EURO",""));
		
		Map<String, AttributElementResport> attribuTabDetail = new LinkedHashMap<String, AttributElementResport>();
		attribuTabDetail.put("COMPTE", new AttributElementResport("0.729","right","Medium","normal","in","string","VIDE",""));
		attribuTabDetail.put("TIERS", new AttributElementResport("0.719","right","Medium","normal","in","string","VIDE",""));
		attribuTabDetail.put("LIBELLE", new AttributElementResport("3.073","right","Medium","normal","in","string","VIDE",""));
		attribuTabDetail.put("DATE", new AttributElementResport("0.74","right","Medium","normal","in","date","VIDE",""));
		attribuTabDetail.put("REFERENCE", new AttributElementResport("0.719","right","Medium","normal","in","string","VIDE",""));
		attribuTabDetail.put("DEBIT", new AttributElementResport("0.708","right","Medium","normal","in","decimal","EURO",""));
		attribuTabDetail.put("CREDIT", new AttributElementResport("0.708","right","Medium","normal","in","decimal","EURO",""));
	
		
		String querySql = "select E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",P.REFERENCE,E.DEBIT,E.CREDIT from ECRITURES E join PIECES P on (E.ID_PIECE=P.ID) WHERE cast(E.COMPTE  AS integer)>= '6700000'";
		String queryProcedureSql = "select * from procedure_onglet_ecriture('"+querySql+"')";
		
		String BIRT_HOME = "C:/TestReport/birt-runtime-2_2_2/ReportEngine";
		String PATH_FILE_REPORT = "c:/tmp/sample.rptdesign";
		
		String DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver";
		//String DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver (v2.0)";
		String FILE_FRIEBIRD = "jdbc:firebirdsql://localhost/C:/Archivage_epicea/archepi/JQJQJQJJJJ.FDB";
		String USER = "admin";
		String PASSWORD = "###_PASSWORD_FB_BDG_###";
		String NAME_REPORT = "ECRITURE_RECHERCHE";
//		MakeDynamiqueReport dynamiqueReport = new MakeDynamiqueReport(al,al,querySql/*queryProcedureSql*//*,BIRT_HOME*/,
//											  PATH_FILE_REPORT,DRIVER_CLASS,FILE_FRIEBIRD,USER,PASSWORD,NAME_REPORT,
//											  ConstEdition.PAGE_ORIENTATION_PORTRAIT,"sss");
//		dynamiqueReport.setAttribuTableFooter(mapListAttributeTableFooter);
//		//dynamiqueReport.setNameTableFooter(nameFooter);
//		dynamiqueReport.setAttribuElemTableFooter(attribuTabFooter);
//		
////		dynamiqueReport.buildDesignConfig();
////		dynamiqueReport.setAttribuElemTableHeader(attribuTabHeaderDefaut);
////		dynamiqueReport.setAttribuElemTableDetail(attribuTabDetailDefaut);
////		dynamiqueReport.makeReportTableDBDefeut();
//		
////		dynamiqueReport.setAttribuElemTableHeader(attribuTabHeader);
////		dynamiqueReport.setAttribuElemTableDetail(attribuTabDetail);
//		dynamiqueReport.makeReportHeaderGrid(3,3,100,"%",attribuGridHeader);
//		dynamiqueReport.makeReportTableDB(100,"%","tableEcriture",attribuTabHeader,attribuTabDetail,1,1,2,5,"40");
//		dynamiqueReport.savsAsDesignHandle();
	
//		try {
//			ExecuteReport.executeReport();
//		} catch (EngineException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/**
		 * for fichier ficherSimpleCreate.java  
		 */
//		try {
//			SimpleCreate.buildReport();
//		} catch (SemanticException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/**
		 * for fichier DECreateDynamicTable.java
		 */
//		DECreateDynamicTable de = new DECreateDynamicTable();
//		ArrayList a2 = new ArrayList();
//		a2.add("COMPTE");
//		a2.add("TIERS");
//		a2.add("LIBELLE");
//		a2.add("DATE");
//		a2.add("DEBIT");
//		a2.add("CREDIT");
//		a2.add("REFERENCE");
//		try {
//			de.buildReport(a2, DECreateDynamicTable.queryProcedureSql );
//		} catch (SemanticException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		TextMakeReport.TextReport();
//		MessageDialog.openInformation(
//			window.getShell(),
//			"ArchivageEpiceaTestReport Plug-in",
//			"Hello, Eclipse world");
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