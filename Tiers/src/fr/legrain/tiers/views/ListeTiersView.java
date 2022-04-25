package fr.legrain.tiers.views;


import org.apache.log4j.Logger;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_Tiers.ModelTiers;
import fr.legrain.gestCom.Module_Tiers.SWTCPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTTCivilite;
import fr.legrain.gestCom.Module_Tiers.SWTTEntite;
import fr.legrain.gestCom.Module_Tiers.SWTTTarif;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTypeTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTypeTvaDoc;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDeclencheCommandeControllerListener;
import fr.legrain.gestCom.librairiesEcran.swt.ILgrListView;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrCompositeTableViewer;
import fr.legrain.lib.gui.grille.LgrSimpleTableLabelProvider;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.librairiesLeGrain.LibrairiesLeGrainPlugin;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTCiviliteDAO;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTEntiteDAO;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTTarifDAO;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaConditionPaiementSWT;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.PaTypeCiviliteSWT;
import fr.legrain.tiers.ecran.PaTypeEntiteSWT;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.PaTypeTarifSWT;
import fr.legrain.tiers.ecran.PaTypeTiersSWT;
import fr.legrain.tiers.ecran.PaTypeTtvaDocSWT;
import fr.legrain.tiers.ecran.ParamAfficheConditionPaiement;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.ParamAfficheTypeCivilite;
import fr.legrain.tiers.ecran.ParamAfficheTypeEntite;
import fr.legrain.tiers.ecran.ParamAfficheTypeTarif;
import fr.legrain.tiers.ecran.ParamAfficheTypeTiers;
import fr.legrain.tiers.ecran.ParamAfficheTypeTvaDoc;
import fr.legrain.tiers.ecran.SWTPaConditionPaiementController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.ecran.SWTPaTypeCiviliteController;
import fr.legrain.tiers.ecran.SWTPaTypeEntiteController;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.ecran.SWTPaTypeTarifController;
import fr.legrain.tiers.ecran.SWTPaTypeTiersController;
import fr.legrain.tiers.ecran.SWTPaTypeTvaDocController;
import fr.legrain.tiers.editor.EditorConditionPaiement;
import fr.legrain.tiers.editor.EditorInputConditionPaiement;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorInputTypeCivilite;
import fr.legrain.tiers.editor.EditorInputTypeEntite;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorInputTypeTarif;
import fr.legrain.tiers.editor.EditorInputTypeTiers;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.EditorTypeCivilite;
import fr.legrain.tiers.editor.EditorTypeEntite;
import fr.legrain.tiers.editor.EditorTypePaiement;
import fr.legrain.tiers.editor.EditorTypeTarif;
import fr.legrain.tiers.editor.EditorTypeTiers;
import fr.legrain.tiers.editor.EditorTypeTvaDoc;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class ListeTiersView extends ViewPart implements ILgrListView<TaTiers>, RetourEcranListener {
	
	static Logger logger = Logger.getLogger(ListeTiersView.class.getName());

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "fr.legrain.tiers.views.ListeTiersView";

	private LgrCompositeTableViewer viewer;

	/**
	 * The constructor.
	 */
	public ListeTiersView() {
	}
	
	private PaListeTiersView vue = null;
	private ListeTiersViewController controller = null;

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		vue = new PaListeTiersView(parent, SWT.NONE);
		controller = new ListeTiersViewController(vue, this);
		viewer = vue.getLgrViewer();

//		getSite().setSelectionProvider(viewer.getViewer());
		
//		IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(TiersMultiPageEditor.ID_EDITOR);
//		if(editor!=null) {
//			editor.getSite().setSelectionProvider(getSite().getSelectionProvider());
//		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		if(vue!=null)
			vue.getLgrViewer().getViewer().getControl().setFocus();
	}
	
	public void update(TaTiers taTiers) {

		getController().getModelTiers().addToModel(viewer.getViewer(), taTiers);
	}
	
	public void refresh(TaTiers taTiers) {

		getController().getModelTiers().refreshModel(viewer.getViewer(), taTiers);
	}
	
	public void remove(TaTiers taTiers) {

		getController().getModelTiers().removeFromModel(viewer.getViewer(), taTiers);
	}

	@Override
	public void select(TaTiers t) {
		if(t!=null) {
			SWTTiers tiers = getController().getModelTiers().recherche(Const.C_ID_TIERS, t.getIdTiers());
			viewer.getViewer().setSelection(new StructuredSelection(tiers));
		}
	}

	@Override
	public void select(int index) {
		viewer.selectionGrille(index);
	}

	@Override
	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if(((ResultAffiche) evt.getRetour()).getSelection()!=null){
				SWTTiers tiersRetour =getController().getModelTiers().recherche(Const.C_ID_TIERS, LibConversion.stringToInteger(((ResultAffiche) evt.getRetour()).getIdResult())); 
				if(tiersRetour!=null){
					viewer.getViewer().setSelection(new StructuredSelection(tiersRetour),true);
				}
			}
		}
	}

	public ListeTiersViewController getController() {
		return controller;
	}

	public PaListeTiersView getVue() {
		return vue;
	}
	
}