package fr.legrain.tiers.tests;

import junit.framework.TestCase;

public class Test4 extends TestCase {

	public void testActModifier() {
		boolean a = true;
		assertEquals(false, a);
	}

	public void testActSupprimer() {
		boolean a = true;
		assertEquals(true, a);
	}

	public void testInitActions() {
		boolean a = true;
		assertEquals(true, a);
	}

}
