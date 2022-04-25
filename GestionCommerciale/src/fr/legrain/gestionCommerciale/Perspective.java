package fr.legrain.gestionCommerciale;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	public static final String ID = "GestionCommerciale.perspective";
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		//layout.setEditorAreaVisible(false);
		//layout.setFixed(true);
		
//		layout.addPlaceholder("fr.legrain.tiers.views.ListeTiersView",IPageLayout.TOP, 0.33f, editorArea);
//		layout.addPlaceholder("fr.legrain.tiers.views.ListeTiersView",IPageLayout.BOTTOM, 0.33f, editorArea);
//		layout.addPlaceholder("fr.legrain.tiers.views.ListeTiersView",IPageLayout.LEFT, 0.33f, editorArea);

		layout.addPlaceholder("fr.legrain.tiers.views.ListeTiersView",IPageLayout.RIGHT, 0.66f, editorArea);
		
		//layout.addStandaloneView(View.ID,  false, IPageLayout.LEFT, 1.0f, editorArea);
		//layout.addStandaloneView(TestSWTRCPEditor.ID,  false, IPageLayout.LEFT, 1.0f, editorArea);
	}
}
