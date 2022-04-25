package fr.legrain.publipostage.openoffice.test;

import java.util.LinkedList;

import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.publipostage.openoffice.PublipostageOOoLinux;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		ParamPublipostage p1 = new ParamPublipostage();
//		p1.setCheminFichierDonnees("/home/lee/Bureau/test_oo/testPub1.txt");
//		p1.setCheminFichierFinal("/home/lee/Bureau/test_oo/1.odt");
//		p1.setCheminFichierModelLettre("/home/lee/Bureau/test_oo/defautModelLettre.odt");
//		p1.setCheminFichierMotCle("/home/lee/Bureau/test_oo/attributrTestPub.properties");
//		p1.setCheminRepertoireFinal("/home/lee/Bureau/test_oo/");
//		
//		ParamPublipostage p2 = new ParamPublipostage();
//		p2.setCheminFichierDonnees("/home/lee/Bureau/test_oo/testPub2.txt");
//		p2.setCheminFichierFinal("/home/lee/Bureau/test_oo/2.odt");
//		p2.setCheminFichierModelLettre("/home/lee/Bureau/test_oo/defautModelLettre_2.odt");
//		p2.setCheminFichierMotCle("/home/lee/Bureau/test_oo/attributrTestPub.properties");
//		p2.setCheminRepertoireFinal("/home/lee/Bureau/test_oo/");
//
//		LinkedList<ParamPublipostage> listeParamPublipostages = new LinkedList<ParamPublipostage>();
//		listeParamPublipostages.add(p1);
//		listeParamPublipostages.add(p2);
		
		ParamPublipostage params = new ParamPublipostage();
		params.setCheminFichierDonnees("/home/lee/Bureau/test_oo/RelanceFac_R1-05-08-2010.txt");
		params.setCheminFichierFinal("/home/lee/Bureau/test_oo/1.odt");
//		params.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/defautModelLettre.odt");
		params.setCheminFichierModelLettre("/home/lee/Bureau/test_oo/modelLettre.odt");
		params.setCheminFichierMotCle("/home/lee/Bureau/test_oo/motcle.properties");
		params.setCheminRepertoireFinal("/home/lee/Bureau/test_oo/");
		
//		ParamPublipostage params2 = new ParamPublipostage();
//		params2.setCheminFichierDonnees("/home/lee/Bureau/test_oo/testPub2.txt");
//		params2.setCheminFichierFinal("/home/lee/Bureau/test_oo/2.odt");
//		params2.setCheminFichierModelLettre("/home/lee/Bureau/test_oo/defautModelLettre_balise_2.odt");
//		params2.setCheminFichierMotCle("/home/lee/Bureau/test_oo/attributrTestPub.properties");
//		params2.setCheminRepertoireFinal("/home/lee/Bureau/test_oo/");
		
		LinkedList<ParamPublipostage> listeParamPublipostages = new LinkedList<ParamPublipostage>();
		listeParamPublipostages.add(params);
//		listeParamPublipostages.add(params2);
		
		String pathOpenOffice = "/usr/lib/openoffice/program/soffice";
		
//		AbstractPublipostageOOo publipostageOOoLinux = PublipostageOOoFactory.createPublipostageOOo(listeParamPublipostages);
		AbstractPublipostageOOo publipostageOOoLinux = new PublipostageOOoLinux(listeParamPublipostages);
		
		publipostageOOoLinux.setCheminOpenOffice(pathOpenOffice);
		publipostageOOoLinux.setNomFichierFinalFusionne("test.odt");
		publipostageOOoLinux.setPortOpenOffice("8100");
		publipostageOOoLinux.demarrerServeur();
	
		publipostageOOoLinux.publipostages();
	
		
		
	
	}

}
