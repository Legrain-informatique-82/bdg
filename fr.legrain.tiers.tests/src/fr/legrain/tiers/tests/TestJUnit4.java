package fr.legrain.tiers.tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJUnit4 {

	@Test
	public void testActModifier() {
		boolean a = true;
		assertEquals(false, a);
	}

	@Test
	public void testActSupprimer() {
		boolean a = true;
		assertEquals(true, a);
	}

	@Test
	public void testInitActions() {
		boolean a = true;
		assertEquals(true, a);
	}

}
