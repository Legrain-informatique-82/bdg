package fr.legrain.article.export.catalogue;

import org.apache.log4j.Logger;


public class ExportationArticleFactory {
	
	static Logger logger = Logger.getLogger(ExportationArticleFactory.class.getName());
	
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
	static public IExportationArticle createExportationArticle() {
		GestionModulePHP.initVersionServeur();
		if(GestionModulePHP.version.versionPrestashop.startsWith(GestionModulePHP.PRESTA_1_4)){
			return new ExportationArticle();
		}else if(GestionModulePHP.version.versionPrestashop.startsWith(GestionModulePHP.PRESTA_1_5)){
			return new ExportationArticle();
		} else {
			logger.error("Version de prestashop ("+GestionModulePHP.version.versionPrestashop+") non géré");
		}
		return null;
	}
	
}
