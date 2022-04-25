package fr.legrain.prestashop.ws.test;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		ClientJersey c = new ClientJersey();
//		TestMagento c = new TestMagento();
//		ClientApacheCXF c = new ClientApacheCXF();
		ClientApacheCXF_Presta15 c = new ClientApacheCXF_Presta15();
		
		c.testClient();

	}

}
