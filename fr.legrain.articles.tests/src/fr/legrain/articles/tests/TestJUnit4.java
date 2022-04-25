package fr.legrain.articles.tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJUnit4 {

	@Test
	public void testActEnregistrer() {
		boolean a = true;
		assertEquals(true, a);
	}

	@Test
	public void testActAide() {
		boolean a = true;
		assertEquals(false, a);
	}

}
