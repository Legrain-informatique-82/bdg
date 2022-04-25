package fr.legrain.articles.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for fr.legrain.articles.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(Test3.class);
		//$JUnit-END$
		return suite;
	}

}
