package fr.legrain.tiers.views;

import org.eclipse.core.expressions.PropertyTester;

public class ResourcePropertyTester extends PropertyTester {
		  private static final String PROPERTY_MATCHES_PATTERN= "matchesPattern"; //$NON-NLS-1$
		  
		  public ResourcePropertyTester() {
			  
		  }

/*
<extension point="org.eclipse.core.expressions.propertyTesters">
  <propertyTester
      id="org.eclipse.jdt.ui.IResourceTypeExtender"
      type="fr.legrain.tiers.views.ListeTiersViewController"
      namespace="org.demo"
      properties="matchesPattern, ...."
      class="fr.legrain.tiers.views.ListeTiersViewController#ResourcePropertyTester">
   </propertyTester>
</extension>
*/

		  public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
//		    IResource resource= (IResource)receiver;
//		    if (PROPERTY_MATCHES_PATTERN.equals(method)) {
//		      String fileName= resource.getName();
//		      StringMatcher matcher= new StringMatcher((String)expectedValue, false, false);
//		      return expectedValue == null
//		          ? matcher.match(fileName)
//		          : matcher.match(filename) == ((Boolean)expectedValue).booleanValue();
//		    } else if (...) {
//		    }
//		    Assert.isTrue(false);
//		    return false;
		   
			//return false;
		    return true;
		  }
		
}
