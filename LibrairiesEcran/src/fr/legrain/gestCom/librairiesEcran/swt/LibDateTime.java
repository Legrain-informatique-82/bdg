package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.widgets.DateTime;

import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;

/**
 * Déplacement du code de fr.legrain.statistiques.Outils pour généraliser.
 * Améliorations à faire
 * @author nicolas
 *
 */
public class LibDateTime {
	
	/* void documentation du composant DateTime */
	private static final int MINIMAL_DAY   = 1;
	private static final int MINIMAL_MONTH = 0;
	private static final int MINIMAL_YEAR  = 1752;
//	private static final int MINIMAL_YEAR  = 2000;
	
	/**
	 * Méthode permettant l'extraction d'une date à partir d'un DateTime
	 * @param laSelection, le Datetime
	 * @return une Date sous forme y/m/d
	 */
	public static Date getDate (DateTime laSelection){
		Calendar recupDate = Calendar.getInstance();

		recupDate.set(laSelection.getYear(),laSelection.getMonth(),laSelection.getDay());
		return recupDate.getTime();

	}
	
	public static String getDateText (DateTime laSelection){
		return LibDate.dateToString(getDate(laSelection));
	}
	
	public static boolean isDateNull (DateTime laSelection){
		boolean isNull = false;
		if(laSelection.getDay()==MINIMAL_DAY
			&& (laSelection.getMonth()==MINIMAL_MONTH ||laSelection.getMonth()==0)
			&& laSelection.getYear()==MINIMAL_YEAR) {
			isNull = true;
		}
		return isNull;
	}
	
	public static boolean isDateNull (Date date){
		boolean isNull = false;
		if(date==null)return true;
		if (LibConversion.stringToInteger(LibDate.getJour(date),0)==MINIMAL_DAY &&
				LibConversion.stringToInteger(LibDate.getMois(date),0)==MINIMAL_MONTH &&
						LibConversion.stringToInteger(LibDate.getAnnee(date),0)==MINIMAL_YEAR){
			isNull = true;
		}
//		if(date.getDay()==MINIMAL_DAY
//			&& date.getMonth()==MINIMAL_MONTH
//			&& date.getYear()==MINIMAL_YEAR) {
//			isNull = true;
//		}
		return isNull;
	}
	public static void setDateNull (DateTime leDateTime){
//		leDateTime.setDate(MINIMAL_DAY,MINIMAL_MONTH,MINIMAL_YEAR);
		leDateTime.setDate(MINIMAL_YEAR,MINIMAL_MONTH,MINIMAL_DAY);
	}
	public static void setDateSystem (DateTime leDateTime){
//		leDateTime.setDate(MINIMAL_DAY,MINIMAL_MONTH,MINIMAL_YEAR);
//		int annee;
//		int mois;
//		int jour;
		Date dateDuJour = new Date();
//		annee=LibConversion.stringToInteger( LibDate.getAnnee(dateDuJour), MINIMAL_YEAR);
//		mois=LibConversion.stringToInteger( LibDate.getMois(dateDuJour), MINIMAL_MONTH);
//		jour=LibConversion.stringToInteger( LibDate.getJour(dateDuJour), MINIMAL_DAY);
		leDateTime.setDate(dateDuJour.getYear()+1900,dateDuJour.getMonth(),dateDuJour.getDate());
	}
	/**
	 * Méthode permettant l'initialisation d'un dateTime à partir d'une date
	 * @param DateTime à modifier
	 * @param Date sous forme y/m/d
	 * 
	 */
	public static void setDate (DateTime leDateTime,Date laDate){
		//TODO: utiliser un calendar pour éviter la méthode deprecated
//		leDateTime.setYear(laDate.getYear()+1900);
//		leDateTime.setMonth(laDate.getMonth());
//		leDateTime.setDay(laDate.getDate());
		leDateTime.setDate(laDate.getYear()+1900,laDate.getMonth(),laDate.getDate());
	}
}
