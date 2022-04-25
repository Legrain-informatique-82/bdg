package fr.legrain.tiers.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for fr.legrain.tiers.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(Test5.class);
		suite.addTestSuite(Test4.class);
		//$JUnit-END$
		return suite;
	}

}
