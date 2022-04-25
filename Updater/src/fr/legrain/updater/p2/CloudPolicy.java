//package fr.legrain.updater.p2;
//
//import org.eclipse.equinox.internal.provisional.p2.ui.policy.IUViewQueryContext;
//import org.eclipse.equinox.internal.provisional.p2.ui.policy.Policy;
//
///**
// * CloudPolicy defines the RCP Cloud Example policies for the p2 UI. The policy
// * is registered as an OSGi service when the example bundle starts.
// * 
// * @since 3.5
// */
//public class CloudPolicy extends Policy {
//	public CloudPolicy() {
//		// XXX User has no access to manipulate repositories
//		setRepositoryManipulator(null);
//
//		// XXX Default view is by category
//		IUViewQueryContext queryContext = new IUViewQueryContext(
//				IUViewQueryContext.AVAILABLE_VIEW_BY_CATEGORY);
//		setQueryContext(queryContext);
//	}
//}
//
