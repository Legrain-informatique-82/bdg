package fr.legrain.article.export.catalogue;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IImportationTiersCommandeWeb {
	
	public ResultatImportation importWeb();
	
	public void transfert(final IProgressMonitor monitor);
	
	public void declencheExportSite();
	
	public void recuperationFTPTiersCommandeWeb();
	public void transfertMAJSite();
	public void declencheMAJSite();

}
