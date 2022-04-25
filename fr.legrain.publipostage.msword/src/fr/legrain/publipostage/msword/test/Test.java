package fr.legrain.publipostage.msword.test;

import java.util.LinkedList;

import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.msword.PublipostageMsWord;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParamPublipostage p = new ParamPublipostage();
		p.setCheminFichierDonnees("C:/Users/posttest/Desktop/testword/testPub1.txt");
		p.setCheminFichierFinal("C:/Users/posttest/Desktop/testword/test.doc");
		p.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/modelLettre.doc");
		p.setCheminFichierMotCle("C:/Users/posttest/Desktop/testword/motcle.properties");
		p.setCheminRepertoireFinal("C:/Users/posttest/Desktop/testword/");
		
		ParamPublipostage p2 = new ParamPublipostage();
		p2.setCheminFichierDonnees("C:/Users/posttest/Desktop/testword/testPub1.txt");
		p2.setCheminFichierFinal("C:/Users/posttest/Desktop/testword/test2.doc");
		p2.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/modelLettre2.doc");
		p2.setCheminFichierMotCle("C:/Users/posttest/Desktop/testword/motcle.properties");
		p2.setCheminRepertoireFinal("C:/Users/posttest/Desktop/testword/");
		
//		ParamPublipostage p3 = new ParamPublipostage();
//		p3.setCheminFichierDonnees("C:/Users/posttest/Desktop/testword/testPub3.txt");
//		p3.setCheminFichierFinal("C:/Users/posttest/Desktop/testword/test3.doc");
//		p3.setCheminFichierModelLettre("C:/Users/posttest/Desktop/testword/modelLettre2.doc");
//		p3.setCheminFichierMotCle("C:/Users/posttest/Desktop/testword/motcle.properties");
//		p3.setCheminRepertoireFinal("C:/Users/posttest/Desktop/testword/");
		
		
		LinkedList<ParamPublipostage> listeParamPublipostages = new LinkedList<ParamPublipostage>();
		listeParamPublipostages.add(p);
		listeParamPublipostages.add(p2);
//		listeParamPublipostages.add(p3);
		
		PublipostageMsWord pub = new PublipostageMsWord(listeParamPublipostages);
		pub.setNomFichierFinalFusionne("testFinalFusion.doc");
		pub.publipostages();

	}

}
