package fr.legrain.gestioncapsules;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.articles.ecran.IExtensionEcran;
import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;

public class ExtensionEcran extends JPABaseControllerSWTStandard implements IExtensionEcran {
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.ecran.IExtensionEcran#ecranSWT()
	 */
	@Override
	public void ecranSWT(PaArticleSWT vue) {
	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.ecran.IExtensionEcran#grilleSWT()
	 */
	@Override
	public void grilleSWT() {
		
	}
	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#initialisation()
//	 */
//	@Override
//	public void initialisation() {
//		
//	}
	
	/* (non-Javadoc)
	 * @see fr.legrain.articles.ecran.IExtensionEcran#bind()
	 */
	@Override
	public void bind() {
		
	}
	
	public void aide(PaArticleSWT vue) {

	}
	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#mappingDTO()
//	 */
//	@Override
//	public void mappingDTO() {
//		//dozer
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#validation()
//	 */
//	@Override
//	public void validation() {
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#aide()
//	 */
//	@Override
//	public void aide() {
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#actions()
//	 */
//	@Override
//	public void actions() {
//		//branchement action/handler/bouton
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#retourEcran()
//	 */
//	@Override
//	public void retourEcran() {
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#enregistrer()
//	 */
//	@Override
//	public void enregistrer() {
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see fr.legrain.articles.ecran.IExtensionEcran#inserer()
//	 */
//	@Override
//	public void inserer() {
//		//init champ
//	}

	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actEnregistrer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actModifier() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actImprimer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actRefresh() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub
		
	}

	
	public void initMapComposantChamps(Map<Control, String> mapComposantChamps) {
//		mapComposantChamps.put(tfTitreTransportU1, Const.C_TITRE_TRANSPORT_ARTICLE_VITI);
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initMapComposantChamps() {
		// TODO Auto-generated method stub
		
	}
	
//	private void createContributors() {
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		IExtensionPoint point = registry.getExtensionPoint("Articles.editorEcranArticles"); //$NON-NLS-1$
//		if (point != null) {
//			//Map<Integer, List<IMultiPaneEditorContributor>> seq2contributors = new HashMap<Integer, List<IMultiPaneEditorContributor>>();
//			ImageDescriptor icon = null;
//			IExtension[] extensions = point.getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
//				for (int jj = 0; jj < confElements.length; jj++) {
//					try {
//						String editorClass = confElements[jj].getAttribute("classe");//$NON-NLS-1$
////						String editorName = confElements[jj].getAttribute("editorLabel");//$NON-NLS-1$
////						String editorIcon = confElements[jj].getAttribute("editorIcon");//$NON-NLS-1$
////						String contributorName = confElements[jj].getContributor().getName();
//						
//						if (editorClass == null /*|| editorName == null*/)
//							continue;
//
//						Object o = confElements[jj].createExecutableExtension("classe");
////						if(editorIcon!=null) {
////							icon = AbstractUIPlugin.imageDescriptorFromPlugin(contributorName, editorIcon);
////						}
//						
//						((IExtensionEcran)o).initialisation();
//						
////						addEditor((ILgrEditor)o,editorName,icon);
//						
////						// test if the contributor applies to this editor
////						boolean isApplicable = false;
////						Class<?> subject = this.getClass();
////						while (subject != EditorPart.class && !isApplicable) {
////							isApplicable = editorClass.equals(subject.getName());
////							subject = subject.getSuperclass();
////						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
}
