//package fr.legrain.updater.p2;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import org.eclipse.equinox.internal.p2.core.helpers.ServiceHelper;
//import org.eclipse.equinox.internal.p2.ui.ProvUIActivator;
//import org.eclipse.equinox.internal.provisional.p2.core.ProvisionException;
//import org.eclipse.equinox.internal.provisional.p2.metadata.repository.IMetadataRepositoryManager;
//import org.eclipse.equinox.internal.provisional.p2.repository.IRepositoryManager;
//import org.eclipse.equinox.internal.provisional.p2.ui.QueryableMetadataRepositoryManager;
//import org.eclipse.equinox.internal.provisional.p2.ui.policy.IUViewQueryContext;
//
//import fr.legrain.updater.UpdaterPlugin;
//
//public class LgrQueryableRepositoryManager extends QueryableMetadataRepositoryManager{
//
//	public LgrQueryableRepositoryManager(IUViewQueryContext queryContext,
//			boolean includeDisabledRepos) {
//		super(queryContext, includeDisabledRepos);
//		// TODO Auto-generated constructor stub
//	}
//	
//	protected IRepositoryManager getRepositoryManager() {
//		//return (IMetadataRepositoryManager) ServiceHelper.getService(ProvUIActivator.getContext(), IMetadataRepositoryManager.class.getName());
//		
//		final IMetadataRepositoryManager manager = (IMetadataRepositoryManager) ServiceHelper
//		.getService(UpdaterPlugin.bundleContext,
//				IMetadataRepositoryManager.class.getName());
//		
//		final URI[] reposToSearch = manager.getKnownRepositories(IRepositoryManager.REPOSITORIES_ALL);
//		for (int i = 0; i < reposToSearch.length; i++) {
//			manager.removeRepository(reposToSearch[i]);
//		}
//		
//		URI uri = null;
//		try {
//			//uri = new URI("http://lgrser.lgr/bdg/update/updates/2010/test_xml.php?login=nicolas&password=1234");
//			
//			uri = new URI("file:///home/nicolas/Desktop/aaa/repository");
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		manager.addRepository(uri);
//		manager.setEnabled(uri, true);
//		try {
//			manager.loadRepository(uri, null);
//		} catch (ProvisionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return manager;
//	}
//
//}
