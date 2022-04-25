package fr.legrain.facture.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveFacture implements IPerspectiveFactory {
	
	public static final String ID = "fr.legrain.perspertive.facture"; //$NON-NLS-1$
	
	public static final String ID_BOTTOM_FOLDER = "fr.legrain.perspective.facture.bottomFolder";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		//layout.setFixed(true);
		//layout.addView(ViewTiers.ID_VIEW,   IPageLayout.RIGHT, 0.75f, editorArea);
		//layout.addView(ViewAdresse.ID_VIEW,   IPageLayout.BOTTOM, 0.75f, editorArea);
		//layout.addView(ViewDetailAdresse.ID_VIEW,   IPageLayout.BOTTOM, 0.50f, ViewTiers.ID_VIEW);

		IFolderLayout f = layout.createFolder(ID_BOTTOM_FOLDER, IPageLayout.BOTTOM, 0.80f, editorArea);
//		f.addView(ViewAdresseFacturation.ID_VIEW);
//		f.addView(ViewAdresseLivraison.ID_VIEW);
//		f.addView(ViewConditionPaiement.ID_VIEW);
		
//		f.addView(SWTViewAdresseFacturation.ID_VIEW);
//		f.addView(SWTViewAdresseLivraison.ID_VIEW);
//		f.addView(SWTViewConditionPaiement.ID_VIEW);
	}
}
