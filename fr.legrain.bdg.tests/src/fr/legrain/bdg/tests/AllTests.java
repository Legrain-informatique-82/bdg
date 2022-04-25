package fr.legrain.bdg.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	fr.legrain.articles.tests.AllTestsJUnit4.class,
	fr.legrain.tiers.tests.AllTestsJUnit4.class
        })
public class AllTests {

//	public static Test suite() {
//		TestSuite suite = new TestSuite("Test for fr.legrain.bdg.tests");
//		//$JUnit-BEGIN$
//		suite.addTest(fr.legrain.articles.tests.AllTests.suite());
//		suite.addTest(fr.legrain.tiers.tests.AllTests.suite());
//		//$JUnit-END$
//		return suite;
//	}

}
