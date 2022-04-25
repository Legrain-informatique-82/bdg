package fr.legrain.article.export.catalogue;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IExportationArticle {
	
	public ResultatExportation exportationCatalogueWeb();
	
	public void transfert(final IProgressMonitor monitor);
	
	public void declencheMAJSite();
	
	public void exportWS();

}
