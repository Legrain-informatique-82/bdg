package fr.legrain.publipostage.openoffice.test;

import java.util.LinkedList;

import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.publipostage.openoffice.PublipostageOOoLinux;
import fr.legrain.publipostage.openoffice.PublipostageOOoWin32;

public class TestOOoWin32 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ParamPublipostage params = new ParamPublipostage();
		params.setCheminFichierDonnees("C:/Users/posttest/Desktop/testword/testPub1.txt");
		params.setCheminFichierFinal("C:/Users/posttest/Desktop/testword/test.odt");
//		params.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/defautModelLettre.odt");
		params.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/defautModelLettreBalise.odt");
		params.setCheminFichierMotCle("C:/Users/posttest/Desktop/testword/motcle.properties");
		params.setCheminRepertoireFinal("C:/Users/posttest/Desktop/testword/");
		
		ParamPublipostage params2 = new ParamPublipostage();
		params2.setCheminFichierDonnees("C:/Users/posttest/Desktop/testword/testPub1.txt");
		params2.setCheminFichierFinal("C:/Users/posttest/Desktop/testword/test2.odt");
//		params2.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/defautModelLettre.odt");
		params2.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/defautModelLettreBalise.odt");
		params2.setCheminFichierMotCle("C:/Users/posttest/Desktop/testword/motcle.properties");
		params2.setCheminRepertoireFinal("C:/Users/posttest/Desktop/testword/");

		String pathOpenOffice = "C:/Program Files/OpenOffice.org 3/program/soffice";
		LinkedList<ParamPublipostage> listeParamPublipostages = new LinkedList<ParamPublipostage>();
		listeParamPublipostages.add(params);
		listeParamPublipostages.add(params2);
		
//		AbstractPublipostageOOo publipostageOOoWin32 = PublipostageOOoFactory.createPublipostageOOo(listeParamPublipostages);
		AbstractPublipostageOOo publipostageOOoWin32 = new PublipostageOOoWin32(listeParamPublipostages);
		publipostageOOoWin32.setCheminOpenOffice(pathOpenOffice);
		publipostageOOoWin32.setNomFichierFinalFusionne("test_oo.odt");
		publipostageOOoWin32.setPortOpenOffice("8100");
		publipostageOOoWin32.demarrerServeur();
	
		publipostageOOoWin32.publipostages();
	
		
		
	
	}

}
