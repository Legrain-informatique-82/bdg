package fr.legrain.ajouteredition.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;



import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.ajouteredition.Activator;
import fr.legrain.ajouteredition.divers.ConstPlugin;
import fr.legrain.ajouteredition.handlers.HandlerBtAnnuler;
import fr.legrain.ajouteredition.swt.SwtReportWithExpandbar;
import fr.legrain.edition.actions.AnalyseFileReport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;


public class SwtChoixChargeEditionControl /*extends SWTBaseControllerSWTStandard*/{

	
	static Logger logger = Logger.getLogger(SwtChoixChargeEditionControl.class.getName());

	private SwtReportWithExpandbar swtReportWithExpandbar;
	private Shell shell;
	private String pathEditionTofind = null;
	boolean flagBtValider = false;
	
	private String subjectEdition = null;
	private String titleEdition = null;
	private String displayNameEdition = null;
	private String commentEdition = null;
	private boolean flagEditionExiste= false;
	
	private Map<String, List<String>> mapEditionExpanbar = new LinkedHashMap<String, List<String>>();
	private Map<String, List<String>> mapExpanItemComment = new LinkedHashMap<String, List<String>>();
	private Map<String, String> mapEditionCommentVsPath = new LinkedHashMap<String,String>();
	
	private List<String> listPathFile = new LinkedList<String>();
	private Map<String, String> mapIconExpandItem = new LinkedHashMap<String, String>(); 
	
	private ExpandItem expandItemCourant = null;
	private String pathEditionToStock = null;
	
//	private String pathEditionSupp = Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/"+
//	 								 Const.FOLDER_EDITION_SUPP;
	
	private String pathEditionSupp = Const.PATH_FOLDER_EDITION_SUPP;
	
	public SwtChoixChargeEditionControl(Shell shell) {
		super();
		this.shell = shell;
	}


	public void init() {
		
		swtReportWithExpandbar = new SwtReportWithExpandbar(shell, SWT.NULL);
		initImageBouton(swtReportWithExpandbar);
		
		cleanMap();
		findAllEdtionPersonnelle(new File(pathEditionSupp));
		AddMapEditionExpanbarAndMapExpanItemComment();
		addMapIconExpandItem();
		swtReportWithExpandbar.getButtonValider().setEnabled(flagBtValider);
		
	}
	
	public void addMapIconExpandItem() {
		mapIconExpandItem.clear();
		
		mapIconExpandItem.put("Articles", "/icons/package.png");
		mapIconExpandItem.put("Tiers", "/icons/group.png");
		mapIconExpandItem.put("Facture", "/icons/table_add.png");
		mapIconExpandItem.put("BonLivraison", "/icons/lorry.png");
		mapIconExpandItem.put("Devis", "/icons/calculator_edit.png");
		mapIconExpandItem.put("Apporteur", "/icons/creditcards.png");
		mapIconExpandItem.put("Avoir", "/icons/table.png");
		mapIconExpandItem.put("Boncde", "/icons/calculator_edit.png");
		mapIconExpandItem.put("Proforma", "/icons/calculator_edit.png");
	} 

	public void addExpandItemForExpandBar(ExpandBar expandBar){
		
//		for (String nameExpandItem : mapEditionExpanbar.keySet()) {
//			ExpandItem item = new ExpandItem(expandBar, SWT.PUSH);
//			item.setText(Const.FOLDER_EDITION+" "+nameExpandItem);
//			String pathRelativeImage = mapIconExpandItem.get(nameExpandItem);
//			Image image = Activator.getImageDescriptor(pathRelativeImage).createImage();
//			item.setImage(image);
//		}
		
		for (String nameExpandItem : mapIconExpandItem.keySet()) {
			ExpandItem item = new ExpandItem(expandBar, SWT.PUSH);
			item.setText(Const.FOLDER_EDITION+" "+nameExpandItem);
			String pathRelativeImage = mapIconExpandItem.get(nameExpandItem);
			Image image = Activator.getImageDescriptor(pathRelativeImage).createImage();
			item.setImage(image);
		}
		
	}

    
    public void addItemForExpanbarItem(ExpandBar expandBar,ExpandItem item) {
		cleanMap();
    	findAllEdtionPersonnelle(new File(pathEditionSupp));
		AddMapEditionExpanbarAndMapExpanItemComment();
		
    	Composite composite = new Composite(expandBar,SWT.NONE);
		GridLayout thisLayout = new GridLayout();
		thisLayout.numColumns = 2;
		composite.setLayout(thisLayout);
		for (String nameExpandItem : mapEditionExpanbar.keySet()) {
			if(item.getText().equals(Const.FOLDER_EDITION+" "+nameExpandItem)){
				List<String> listFileEditionSupp = mapEditionExpanbar.get(nameExpandItem);
				AnalyseFileReport analyseFileReport = new AnalyseFileReport();
				for (String pathEditionSupp : listFileEditionSupp) {
					analyseFileReport.initializeBuildDesignReportConfig(pathEditionSupp);
					String comment = analyseFileReport.findCommentsReport();
					
					Label label = new Label(composite,SWT.NULL);
					label.setText(comment);
					Button btDelete = new Button(composite, SWT.NULL);
					Image image = Activator.getImageDescriptor("/icons/application_delete.png").createImage();
					btDelete.setImage(image);
					addActionBtDeleteEdition(btDelete,pathEditionSupp,nameExpandItem,composite,label,
							expandBar,item,comment);
					
				}
//				expandItemCourant.setExpanded(true);
			}
		}
		item.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(composite);
    	
	}

	public void addActionExpanbarItem(SwtReportWithExpandbar swtReportWithExpandbar) {

		final ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		final Label labelMessage = swtReportWithExpandbar.getLabelMessage();
		expandBar.addListener(SWT.Expand, new Listener() {
            @Override
            public void handleEvent(Event e) {
            	labelMessage.setText("");
            	for (int i = 0; i < expandBar.getItems().length; i++) {
					ExpandItem item = expandBar.getItems()[i];
					if (e.item == item) {
						expandItemCourant = item;
						addItemForExpanbarItem(expandBar,item); 
					}else{
						item.setExpanded(false);
					}
				} 
            	
            }
        });
	}
	public void actionExpandEditionSupp(SwtReportWithExpandbar swtReportWithExpandbar){
		
		ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		addExpandItemForExpandBar(expandBar);
		addActionExpanbarItem(swtReportWithExpandbar);
	

		actionBtAnnulerAndValider(swtReportWithExpandbar);
		actionBtParcourir(swtReportWithExpandbar);
		actionBtCommander(swtReportWithExpandbar);
		
		ScrolledComposite scrolledComposite = swtReportWithExpandbar.getScrolledCompositeListReport();
	
		scrolledComposite.setContent(expandBar);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setMinSize(expandBar.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
	}
	
	public void addActionBtDeleteEdition(final Button btDelete,final String pathEditionSupp,final String key,
			final Composite composite,final Label label,final ExpandBar expandBar,final ExpandItem item,
			final String comment) {
//		try {
			btDelete.addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
					
				}

				public void widgetSelected(SelectionEvent e) {
					boolean flag = false;
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					flag = MessageDialog.openConfirm(shell, ConstPlugin.MESSAGE_TITLE_DELETE_EDITION, 
							ConstPlugin.MESSAGE_CONTENT_DELETE_EDITION);
					if(flag){
						new File(pathEditionSupp).delete();
						mapEditionExpanbar.get(key).remove(pathEditionSupp);					
						mapExpanItemComment.get(key).remove(comment);
						mapEditionCommentVsPath.remove(comment);

						item.setExpanded(false);
						item.getControl().isDisposed();
						addItemForExpanbarItem(expandBar, item);
						item.setExpanded(true);
											
					}
				}
				
			});
			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

	}
	public void initActionElement() {
		actionExpandEditionSupp(swtReportWithExpandbar);
	}
		
	public String getPathToStockEditionSupp(String pathEditionTofind) {
		String path = "";
		String partiPathEdition = null;
		AnalyseFileReport analyseFileReport = new AnalyseFileReport();
		analyseFileReport.initializeBuildDesignReportConfig(pathEditionTofind);
		
		titleEdition = analyseFileReport.findTitleReport();
		subjectEdition = analyseFileReport.findSubjectReport();
		displayNameEdition = analyseFileReport.findDisplayNameReport();
		commentEdition = analyseFileReport.findCommentsReport();
		
		if(displayNameEdition.equals(titleEdition)){
			partiPathEdition = displayNameEdition;
		}else{
			partiPathEdition = titleEdition+ConstEdition.SEPARATOR+displayNameEdition;
		}
		
		path = Const.PATH_FOLDER_EDITION_SUPP+ConstEdition.SEPARATOR+subjectEdition+
		ConstEdition.SEPARATOR+partiPathEdition+ConstEdition.SEPARATOR;
		
		analyseFileReport.closeDesignReportConfig();
		return path;
	} 
	
	public void actionBtCommander(final SwtReportWithExpandbar swtReportWithExpandbar){
		Button button = swtReportWithExpandbar.getBtCommander();
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.close();
				final String finalURL = ConstPlugin.HTTP_URL;
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						try {
							PlatformUI.getWorkbench().getBrowserSupport()
							.createBrowser(
									IWorkbenchBrowserSupport.AS_EDITOR,
									"myId",
									finalURL,
									finalURL
							).openURL(new URL(finalURL));

						} catch (PartInitException e) {
							logger.error("",e);
						} catch (MalformedURLException e) {
							logger.error("",e);

						}
					}	
				});
			}
			
		});
		
	}
	public void actionBtParcourir(final SwtReportWithExpandbar swtReportWithExpandbar){
		
		Button btParcourir = swtReportWithExpandbar.getBtParcouirir();
		final Button btValider =swtReportWithExpandbar.getButtonValider();
		final Label labelMessage = swtReportWithExpandbar.getLabelMessage();
		labelMessage.setText("");
		final ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		btParcourir.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub				
				cleanMap();
				findAllEdtionPersonnelle(new File(pathEditionSupp));
				AddMapEditionExpanbarAndMapExpanItemComment();
				
				expandItemCourant = null;
				FileDialog fileDialog= new FileDialog(shell);
				fileDialog.setText("Choix édition à ajouter");
				fileDialog.setFilterExtensions(ConstPlugin.fileTypeEdition);

				pathEditionTofind = fileDialog.open();
				swtReportWithExpandbar.getTextPathEdition().setText(pathEditionTofind);
				if(!swtReportWithExpandbar.getTextPathEdition().getText().equals("")){
					pathEditionToStock = getPathToStockEditionSupp(pathEditionTofind);
					
					for (int i = 0; i < expandBar.getItems().length; i++) {
						ExpandItem item = expandBar.getItems()[i];
						item.setExpanded(false);
						if(item.getText().equals(Const.FOLDER_EDITION+" "+subjectEdition)){
							expandItemCourant = item;
						}
					}
					flagBtValider = true;
					
					if(mapExpanItemComment.containsKey(subjectEdition)){
						
						flagEditionExiste = mapExpanItemComment.get(subjectEdition).contains(commentEdition);
						if(flagEditionExiste){
							labelMessage.setText(ConstPlugin.MESSAGE_EDITION_EXISTE);
							labelMessage.setForeground(SWTResourceManager.getColor(255, 0, 0));
							labelMessage.setFont(SWTResourceManager.getFont("Sans", 9, 0, false, false));
						}
					}			
				}
				
				btValider.setEnabled(flagBtValider);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
	}
	public String getNameFileEdition(String nameFile){		
		String name = "";
		name = nameFile.substring(0, nameFile.indexOf(ConstEdition.TYPE_FILE_REPORT));
		return name;
	}
	public void actionBtAnnulerAndValider(SwtReportWithExpandbar swtReportWithExpandbar ) {
		
		Button btAnnuler = swtReportWithExpandbar.getButtonAnnuler();
		final Button btValider = swtReportWithExpandbar.getButtonValider();
		final ExpandBar expandBar = swtReportWithExpandbar.getExpandBarEdition();
		final Text textPathEdition = swtReportWithExpandbar.getTextPathEdition();
		final Label labelMessage = swtReportWithExpandbar.getLabelMessage();
		btAnnuler.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
		btValider.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				boolean flag = false;
				String nameFileEdition = getNameFileEdition(new File(pathEditionTofind).getName());
				if(!new File(pathEditionToStock).exists()){
					new File(pathEditionToStock).mkdirs();
				}
				DateFormat df = new SimpleDateFormat ("_ddMMyyyy_HHmmss");
				Date d = new Date();
				File filePathEditionToStock = new File(pathEditionToStock+nameFileEdition+df.format(d)+
							ConstEdition.TYPE_FILE_REPORT);
				
				boolean flagAddEdition = false;
				
				if(flagEditionExiste){
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					flag = MessageDialog.openConfirm(shell, ConstPlugin.MESSAGE_TITLE_OVERWRITE_EDITION, 
							ConstPlugin.MESSAGE_CONTENT_OVERWRITE_EDITION);
					if(flag){
						String pathFileExist = mapEditionCommentVsPath.get(commentEdition);
						new File(pathFileExist).delete();
						flagAddEdition = true;
					}
				}else{
					flagAddEdition = true;
				}
				if(flagAddEdition){
					FileUtils fileUtils = new FileUtils();
					try {
						fileUtils.copyFile(new File(pathEditionTofind), filePathEditionToStock);
					} catch (IOException e1) {
						logger.error("", e1);
					}
				}
				textPathEdition.setText("");
				labelMessage.setForeground(SWTResourceManager.getColor(255, 0, 0));
				labelMessage.setFont(SWTResourceManager.getFont("Sans", 9, 0, false, false));
				labelMessage.setText(ConstPlugin.MESSAGE_RESSUE_ADD_EDITION+subjectEdition);
				btValider.setEnabled(false);
//				for (int i = 0; i < expandBar.getChildren().length; i++) {
//					expandBar.getChildren()[i].dispose();
//					addItemForExpanbarItem(expandBar, item);
//					item.setExpanded(true);
//				}
				
				/************* garder l'anncienne edition ****************/
//				String nameFileEdition = getNameFileEdition(new File(pathEditionTofind).getName());
//				
//				if(!new File(pathEditionToStock).exists()){
//					new File(pathEditionToStock).mkdirs();
//				}
//				DateFormat df = new SimpleDateFormat ("_ddMMyyyy_HHmmss");
//				Date d = new Date();
//				File filePathEditionToStock = new File(pathEditionToStock+nameFileEdition+df.format(d)+ConstEdition.TYPE_FILE_REPORT);
//				
//				FileUtils fileUtils = new FileUtils();
//				try {
//					fileUtils.copyFile(new File(pathEditionTofind), filePathEditionToStock);
//				} catch (IOException e1) {
//					logger.error("", e1);
//				}
//				textPathEdition.setText("");
				/******************************************************/	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		
	}
	public String[] getElementCombo(){
		ConstPlugin.fillMapTypeEditionAndEntity();
		String[] arrayElementCombo = new String[ConstPlugin.mapTypeEditionAndEntity.size()];
		Map<String,	String> a = ConstPlugin.mapTypeEditionAndEntity;
		int count = 0;
		for (String keyMap : ConstPlugin.mapTypeEditionAndEntity.keySet()) {
			//arrayElementCombo[count] = ConstPlugin.mapTypeEditionAndEntity.get(keyMap);
			arrayElementCombo[count] = keyMap;
			count++;
		}
		return arrayElementCombo;
	}
	
	public void initImageBouton(SwtReportWithExpandbar swtReportWithExpandbar){
		final String C_IMAGE_VALIDER = "/icons/accept.png";
		final String C_IMAGE_ANNULER = "/icons/cancel.png";
		if(swtReportWithExpandbar instanceof SwtReportWithExpandbar){
			swtReportWithExpandbar.getButtonAnnuler().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_ANNULER).createImage());
			swtReportWithExpandbar.getButtonValider().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_VALIDER).createImage());
		}
	}
	
	public boolean checkOutEditionIsExsit() {
		boolean flag = false;
		
		
		return flag;
	}
	public Map<String, String> arrayPathFileEdition(String pathFolder){
		
		Map<String, String> mapEditionComment = new HashMap<String, String>();
		AnalyseFileReport analyseFileReport = new AnalyseFileReport();
		File folder = new File(pathFolder);
		FilenameFilter filter = new TypeFileReportFilter();
		File[] arrayFileReport = folder.listFiles(filter);
		if(arrayFileReport != null){
			for (int i = 0; i < arrayFileReport.length; i++) {
				String pathFileReport = arrayFileReport[i].toString();
				analyseFileReport.initializeBuildDesignReportConfig(pathFileReport);
				String CommentaireEdition = analyseFileReport.findCommentsReport();	
				mapEditionComment.put(CommentaireEdition, pathFileReport);
			}
		}
		return mapEditionComment;
	}
	private class TypeFileReportFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String nameFile) {
			// TODO Auto-generated method stub
			//return false;
			return nameFile.endsWith(ConstEdition.TYPE_FILE_REPORT);
			//return nameFile.endsWith(".dat");
		}
		
	}
	public void cleanMap() {
		mapEditionExpanbar.clear();
		mapExpanItemComment.clear();
		listPathFile.clear();
		mapEditionCommentVsPath.clear();
	}
	public void findAllEdtionPersonnelle(File filePathEditionSupp) {
		
		if(filePathEditionSupp.isFile() && 
				filePathEditionSupp.getPath().endsWith(ConstEdition.TYPE_FILE_REPORT)) {
			listPathFile.add(filePathEditionSupp.getPath());
		}else if (filePathEditionSupp.isDirectory()) {
			File[] listeFichier = filePathEditionSupp.listFiles();
			for(int i = 0; i < listeFichier.length; i++) {
				if(listeFichier[i].isDirectory()) {
					findAllEdtionPersonnelle(listeFichier[i]);
				}else{
					if(listeFichier[i].getPath().endsWith(ConstEdition.TYPE_FILE_REPORT)){
						listPathFile.add(listeFichier[i].getPath());
					}
				}
			}
		}
	}
	public void AddMapEditionExpanbarAndMapExpanItemComment() {
		AnalyseFileReport analyseFileReport = new AnalyseFileReport();
		
		for (String pathEditionSupp : listPathFile) {
			analyseFileReport.initializeBuildDesignReportConfig(pathEditionSupp);
			String sujet = analyseFileReport.findSubjectReport();
			String comment = analyseFileReport.findCommentsReport();
			mapEditionCommentVsPath.put(comment, pathEditionSupp);
			if(mapEditionExpanbar.containsKey(sujet)){
				mapEditionExpanbar.get(sujet).add(pathEditionSupp);
				mapExpanItemComment.get(sujet).add(comment);
			}else{
				List<String> listGroupPathFile = new LinkedList<String>();
				List<String> listGroupComment = new LinkedList<String>();
				
				listGroupPathFile.add(pathEditionSupp);
				listGroupComment.add(comment);
				mapEditionExpanbar.put(sujet, listGroupPathFile);
				mapExpanItemComment.put(sujet, listGroupComment);
			}
			analyseFileReport.closeDesignReportConfig();
		}
	}


	public SwtReportWithExpandbar getSwtReportWithExpandbar() {
		return swtReportWithExpandbar;
	}


	public List<String> getListPathFile() {
		return listPathFile;
	}


	
	/*************************************/
	/**
	 *  il y a probleme, entre handler et commande 
	 */
	public void initFocusCommand(String focusId,Shell dialogShell){
//		List<Button> listeComposantFocusable = null;
		IHandlerService handlerService = null;
		List<Button> listeComposantFocusable =  new ArrayList<Button>();
		
//		listeComposantFocusable.add(swtReportWithExpandbar.getButtonAnnuler());
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		
//		IFocusService focusService = (IFocusService) PlatformUI.getWorkbench().
//				getService(IFocusService.class);
//		for (Button button : listeComposantFocusable) {
//			focusService.addFocusTracker(button, focusId);
//		}
		
//		Expression exp = new FocusControlExpression(focusId);

		ActiveShellExpression activeShellExpression = null;
		IContextService contextService = null;
		if(handlerService == null)
			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		if(contextService == null)
			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
		//contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
		//activeShellExpression = new ActiveShellExpression(vue.getShell());
		contextService.registerShell(dialogShell,IContextService.TYPE_WINDOW);
		activeShellExpression = new ActiveShellExpression(dialogShell);
		HandlerBtAnnuler handlerBtAnnuler = new HandlerBtAnnuler();
		
		handlerService.activateHandler(ConstPlugin.C_COMMAND_BT_ANNULER_ID,handlerBtAnnuler, 
				activeShellExpression);
	}
	/************************************/
	
	
}
