package fr.legrain.article.export.catalogue;

import org.apache.log4j.Logger;


public class ImportationTiersCommandeWebFactory {
	
	static Logger logger = Logger.getLogger(ImportationTiersCommandeWebFactory.class.getName());
	
//	public static final int TYPE_JSON = 1;
//	public static final int TYPE_WEBSERVICE_MAGENTO = 2;
//
//	static public IExportationArticle createExportationArticle(int type) {
//		if(type==TYPE_JSON){
//			return new ExportationArticle();
//		} else if(type==TYPE_WEBSERVICE_MAGENTO){
//			return new ExportationArticleWebserviceMagento();
//		}
//		return null;
//	}
	
	/**
	 * Creation d'un objet permettant 
	 */
	static public IImportationTiersCommandeWeb createImportationTiersCommandeWeb() {
		GestionModulePHP.initVersionServeur();
		if(GestionModulePHP.version.versionPrestashop.startsWith(GestionModulePHP.PRESTA_1_4)){
			return new ImportationTiersCommandeWeb();
		}else if(GestionModulePHP.version.versionPrestashop.startsWith(GestionModulePHP.PRESTA_1_5)){
			return new ImportationTiersCommandeWeb();
		} else {
			logger.error("Version de prestashop ("+GestionModulePHP.version.versionPrestashop+") non géré");
		}
		return null;
	}
	
}
