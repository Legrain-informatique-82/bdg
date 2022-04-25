package fr.legrain.generationLabelEtiquette.test;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;

public class testFonction {

	/**
	 * @param args
	 */
	public static String C = "TaTiers.nomTiers;TaTiers.prenomTiers;" +
							 "TaAdresse.adresse1Adresse;TaAdresse.adresse2Adresse;" +
							 "TaAdresse.codepostalAdresse;TaAdresse.villeAdresse;" +
							 "TaAdresse.paysAdresse";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String valueFloat = "25555555.2";
//		String stringValue = "ddd";
//		//System.out.println(Integer.valueOf(valueFloat));
////		System.out.println(valueFloat.format("%.2f", 12345.101));
//		//sprintf("%.2f", 12345.101)
//		String pattern = "[0-9]+(.[0-9]+)?";
//		if(valueFloat.matches(pattern)){
//			System.out.println("ok");
//		}else{
//			System.out.println("no ok");
//		}
		int a = 100;
		int b = 3;
		//System.out.println(Math.floor(a/b));
		BigDecimal aa = new BigDecimal(0.39565656).setScale(3, BigDecimal.ROUND_DOWN );
		//System.out.println(aa.floatValue());
//		System.out.println(9%2);
//		Integer s = new Integer(8);
//		System.out.println((float)s);
//		System.out.println(String.format(ConstantModelLabels.LAYOUT_TEXT_ETIQUETTE,
//				"5","5"));
		getInfosMotCleEtiquette();
		

		
	}
	public static void getInfosMotCleEtiquette(){
		String valueMotCle = "";
		String[] arrayMotCleEtiquette = C.split(";");
		for (int i = 0; i < arrayMotCleEtiquette.length; i++) {
			int lastPositionPoint = arrayMotCleEtiquette[i].lastIndexOf(".");
			if(i==0){
				String partiValue = arrayMotCleEtiquette[i].substring(lastPositionPoint+1);
				valueMotCle += partiValue;
			}else{
				String partiValue = arrayMotCleEtiquette[i].substring(lastPositionPoint);
				valueMotCle += partiValue;
			}
		}
		System.out.println(valueMotCle);
	}

	public static void replaceString(){
		String regex = "\\bDate\\b";
		
		String dd = "inrssds jjDate DateJJ Date DateDD DDDate Date";
		
//		 Pattern p = Pattern.compile(regex); // Compiles regular expression into Pattern.
//		 Matcher m = p.matcher(regex); // 
		 
		
		
//		if(p.matches(regex, dd)){
//			System.out.println("ok");
//		}else{
//			System.out.println("no");
//		}
		
		System.out.println(dd.replaceAll(regex, "a"));
	}
}
