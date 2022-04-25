package fr.legrain.publipostage.openoffice;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.UnoUrlResolver;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.style.BreakType;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.table.XTableRows;
import com.sun.star.text.ControlCharacter;
import com.sun.star.text.XParagraphCursor;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextTablesSupplier;
import com.sun.star.text.XTextViewCursorSupplier;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XPropertyReplace;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

import fr.legrain.publipostage.Publipostage;
import fr.legrain.publipostage.divers.ParamPublipostage;

public abstract class AbstractPublipostageOOo extends Publipostage {

	static Logger logger = Logger.getLogger(AbstractPublipostageOOo.class.getName());
			
	public static final String WIN32_LINUX_SOFFICE ="soffice";
	/*
	 * http://wiki.services.openoffice.org/wiki/Documentation/OOoAuthors_User_Manual/Getting_Started/Starting_from_the_command_line
	 * http://www.productivitytalk.com/forums/index.php?showtopic=4598
	 * -invisible no startup screen, no default document and no UI
	 * -nodefault don't start with an empty document
	 */
	public static final String PARAMS_START_SERVER_OPENOFFICE_PARAM1 = " -accept=socket,host=localhost,port=?;urp -nodefault";
	public static final String PARAM_CONNECTION_OPEN_OFFICE = 
							"uno:socket,host=localhost,port=?;urp;StarOffice.ServiceManager";
	protected String portOpenOffice = null;
	
	protected String cheminOpenOffice = null;
	private Map<ParamPublipostage, XComponent> map = new HashMap<ParamPublipostage, XComponent>();
	
	protected XText xT;
	protected XComponent xC;
	protected XTextDocument xTD;
	protected XTextTable xTT;
	protected XComponentContext xCC;
	protected XMultiComponentFactory xMCF;
	protected XMultiServiceFactory xMSF;
	protected XSearchable xS; 
	protected XSearchDescriptor xSD;
	protected XTextTablesSupplier xTTS;
	protected XNameAccess xNA;
	protected XUnoUrlResolver xUUR;
	protected XStorable xStorable; 
	protected XComponentLoader xCL;
	protected PropertyValue[] loadProps ;
//	String docType = "swriter"; 
    protected String newDocURL = "private:factory/swriter"; 
	
    private OOoBean oOoBean = new OOoBean();
    
	public AbstractPublipostageOOo(
			List<ParamPublipostage> listeParamPublipostages) {
		super(listeParamPublipostages);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enregistrePublipostage(ParamPublipostage param) {
		// TODO Auto-generated method stub
		try { 
			String uriLettre = convertCheminFileVersUri(param.getCheminFichierFinal());
			oOoBean.enregistrePublipostage(uriLettre);
			
		} catch( Exception e) { 
			logger.error("",e);
		} 
	}

	public void save(String savePath) {
		try { 
		
		String uriLettre = convertCheminFileVersUri(savePath);
		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,xC); 
		PropertyValue storeProperties[] = new PropertyValue[1]; 

		storeProperties[0] = new PropertyValue(); 
		//					storeproperties[0].name = "filtername"; 
		//					storeproperties[0].value = "writer_pdf_Export"; 

		
			xStorable.storeAsURL(uriLettre, storeProperties); 
		} catch( Exception e) { 
			logger.error("",e);
		} 
	}
	@Override
	public void fermeTraitementDeTexte() {
		// TODO Auto-generated method stub
	}

	@Override
	public void publipostage(ParamPublipostage param) {
		// TODO Auto-generated method stub
		/** zhaolin **/
//		oOoBean.createNewDocument(portOpenOffice);
//		XComponent xComponent = oOoBean.getxC();
//		map.put(param, xComponent);
		
		
		initTraitementDeTexte();
		XComponent xComponent = oOoBean.getxC();
		map.put(param, xComponent);
		remplaceChamp(param);
		
	}
	
	@Override
	public void initTraitementDeTexte() {
		// TODO Auto-generated method stub
		oOoBean.createNewDocument(portOpenOffice);
	}
	public void closeDocument(ParamPublipostage param) {
		
		oOoBean.closeDocument(map.get(param));
	}
	@Override
	public void fusionnePublipostage(List<ParamPublipostage> listePublipostage) {
		// TODO Auto-generated method stub
		try {
			initTraitementDeTexte();
			int positionPage = 0;
			for (ParamPublipostage param : listePublipostage) {
				insertPageOpenOffice(param.getCheminFichierFinal(), positionPage);
				closeDocument(param);
				positionPage++;
				
			}
			String uriLettre = convertCheminFileVersUri(listePublipostage.get(0).getCheminRepertoireFinal()+"/"+getNomFichierFinalFusionne());
			oOoBean.enregistrePublipostage(uriLettre);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	@Override
	public void publipostages(String nomFichierFinal) {
		// TODO Auto-generated method stub
	}
	
	public void insertPageOpenOffice(String pathLettre,int positionPage) {
		String uriLettre = convertCheminFileVersUri(pathLettre);
		oOoBean.insertPageOpenOffice(uriLettre, positionPage);
	}
	@Override
	public void ajoutPageModeleTraitementDeTexte(ParamPublipostage param,int positionPage) {
		// TODO Auto-generated method stub
		insertPageOpenOffice(param.getCheminFichierModelLettre(), positionPage);
				
	}


	public void makeLettrePubPosatage(ParamPublipostage param) {
		
		String uriCheminModelLettre = convertCheminFileVersUri(param.getCheminFichierModelLettre());
		int count = 1;
		
		if(param.isAfficheDetail()){
			for (String codeTiers : param.getDetails().keySet()) {
				LinkedList list =  param.getDetails().get(codeTiers);
				
			}
		}
		
		
	}
	abstract public void demarrerServeur();
	abstract public void OuvreDocument(String portOpenOffice,String fichier) ;
	
//	@Override
	public void insereLigneDetail(int indexTable) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void remplaceChampTraitementDeTexte(ParamPublipostage param,
			String valeurRecherche, String nouvelleValeur) {

		oOoBean.replaceTextBalise(valeurRecherche, nouvelleValeur);
	}
	@Override
	public void remplaceChampDetailTraitementDeTexte(ParamPublipostage param,
			String marqueurGroupe, int indexTable) {
		
//		remplaceDetailTable(param, marqueurGroupe, indexTable);
		remplaceDetailBalise(param, marqueurGroupe, indexTable);
		
	}
	
	public void remplaceDetailTable(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";
		
		
		try {
			String texteModele = oOoBean.getTextCellTableExiste(indexTable, 0, 0);
			

			int count = 1;
			for (String[] detail : param.getDetails().get(marqueurGroupe)) {
				ligneFinale = texteModele;
				for (int i = 0; i < detail.length; i++) {
					if(param.getMotCleLettreEtPostion().containsKey(i)){
						motCleLigneDetail = param.getMotCleLettreEtPostion().get(i);
						if(param.getMotCleLettreEtPostion().containsKey(i)){
							valueRmplace = detail[i];
						}
						ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);
					}
				}
				if(count != 1){
					oOoBean.addRow(count-1);
				}
				oOoBean.putTxtToCell(count-1, ligneFinale);	
				count++;
			}
			/*********************** ok **********************/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("", e);
		}
	}
	public void remplaceDetailBalise(ParamPublipostage param, String marqueurGroupe,int indexTable) {
		boolean tableOuTexte = true; //si vrai table sinon texte
		
		String motCleLigneDetail = "";
		String valueRmplace = "";
		String ligneFinale = "";
		try {
			
			String texteModele = oOoBean.findTxtBetweenDetail(param.getBaliseDebutDetail(),param.getBaliseFinDetail());
			
			if(!texteModele.equals("")) {
				//il y a bien une balise de détail avec du texte à l'intérieur
				
				texteModele = convertRetournLigne(texteModele);

				oOoBean.creatTable();

				int count = 1;
				for (String[] detail : param.getDetails().get(marqueurGroupe)) {
					ligneFinale = texteModele;
					for (int i = 0; i < detail.length; i++) {
						if(param.getMotCleLettreEtPostion().containsKey(i)){
							motCleLigneDetail = param.getMotCleLettreEtPostion().get(i);
							if(param.getMotCleLettreEtPostion().containsKey(i)){
								valueRmplace = detail[i];
							}
							ligneFinale = ligneFinale.replaceAll(motCleLigneDetail, valueRmplace);
						}
					}
					if(count != 1){
						oOoBean.addRow(count-1);
					}

					oOoBean.putTxtToCell(count-1, ligneFinale);

					count++;
				}
			}

			

		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		}
		
	}

	abstract public String convertCheminFileVersUri(String cheminFichier);
	abstract public String convertRetournLigne(String texteModele);
	
	public void remplaceDetailBalise() {
//		XSearchDescriptor xSearchDescriptor = x
	}
	public void setCheminOpenOffice(String cheminOpenOffice) {
		this.cheminOpenOffice = cheminOpenOffice;
	}


	public void setPortOpenOffice(String porteOpenOffice) {
		this.portOpenOffice = porteOpenOffice;
	}

	@Override
	public void repositionneCurseurAvantRecherche(int indexTable) {
		// TODO Auto-generated method stub
		
	}
}
