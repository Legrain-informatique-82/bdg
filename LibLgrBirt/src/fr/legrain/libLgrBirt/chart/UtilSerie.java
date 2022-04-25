package fr.legrain.libLgrBirt.chart;

import java.util.Date;
import java.util.LinkedList;

import com.ibm.icu.util.Calendar;

public class UtilSerie {
	
	public static final int PRECISION_GRAPH_JOUR = 2;
	public static final int PRECISION_GRAPH_MOIS = 1;
	public static final int PRECISION_GRAPH_ANNEE = 0;
	
	static public String[] listeAnneeEntre2Date(Date debut, Date fin) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(debut);
		
		Calendar cf = Calendar.getInstance();
		cf.setTime(fin);
	
		int anneeDebut = cd.get(Calendar.YEAR);
		int anneeFin = cf.get(Calendar.YEAR);
		
//		int moisDebut = cd.get(Calendar.MONTH)+1;
//		int moisFin = cf.get(Calendar.MONTH)+1;
//		//int a = java.util.Calendar.MONTH;
		
		String[] tabAnnee = new String[anneeFin-anneeDebut+1];
//		LinkedList<String> listeMois = new LinkedList<String>();
		for(int x=anneeDebut,y=0; x<=anneeFin;x++,y++) {
			tabAnnee[y]=String.valueOf(x);
		}
		
		return tabAnnee;
	}
	
	static public String[] listeMoisEntre2Date(Date debut, Date fin) {
		LinkedList<String> listeMois = new LinkedList<String>();
		
		String[] tabAnnee = listeAnneeEntre2Date(debut,fin);
		
		Calendar cd = Calendar.getInstance();
		cd.setTime(debut);
		
		Calendar cf = Calendar.getInstance();
		cf.setTime(fin);
		
		int moisDebut = cd.get(Calendar.MONTH)+1;
		int moisFin = cf.get(Calendar.MONTH)+1;
		
		if(tabAnnee.length>=2) {
			for(int x=moisDebut; x<=12;x++) {
				listeMois.add(String .valueOf(x)+"/"+tabAnnee[0]);
			}

			for(int x=1; x<tabAnnee.length-1;x++) {
				listeMois.add("1/"+tabAnnee[x]);
				listeMois.add("2/"+tabAnnee[x]);
				listeMois.add("3/"+tabAnnee[x]);
				listeMois.add("4/"+tabAnnee[x]);
				listeMois.add("5/"+tabAnnee[x]);
				listeMois.add("6/"+tabAnnee[x]);
				listeMois.add("7/"+tabAnnee[x]);
				listeMois.add("8/"+tabAnnee[x]);
				listeMois.add("9/"+tabAnnee[x]);
				listeMois.add("10/"+tabAnnee[x]);
				listeMois.add("11/"+tabAnnee[x]);
				listeMois.add("12/"+tabAnnee[x]);
			}

			for(int x=1; x<=moisFin;x++) {
				listeMois.add(String .valueOf(x)+"/"+tabAnnee[tabAnnee.length-1]);
			}
		} else {
//			int nbMois = moisFin-moisDebut;
			for(int x=moisDebut; x<=moisFin;x++) {
				listeMois.add(String .valueOf(x)+"/"+tabAnnee[0]);
			}
		}
		String[] tabMois = new String[listeMois.size()];
		int k = 0;
		for (String mois : listeMois) {
			tabMois[k]=listeMois.get(k);
			k++;
		}
		
		return tabMois;
	}
	
	static public String[] listeJoursEntre2Date(Date debut, Date fin) {
		LinkedList<String> listeJour = new LinkedList<String>();
				
		Calendar cd = Calendar.getInstance();
		cd.setTime(debut);
		
		Calendar cf = Calendar.getInstance();
		cf.setTime(fin);
		
		int nbJour = cd.fieldDifference(fin, Calendar.DATE);
		
		for(int i=0; i<nbJour; i++) {
			listeJour.add(cd.get(Calendar.DATE)+"/"+cd.get(Calendar.MONTH)+"/"+cd.get(Calendar.YEAR));
			cd.add(Calendar.DATE, 1);
			i++;
		}
		
		String[] tabJour = new String[listeJour.size()];
		int k = 0;
		for (String jour : listeJour) {
			tabJour[k]=listeJour.get(k);
			k++;
		}
		
		return tabJour;
	}
	
	static public String genereCleDate(String jour,String mois, String annee, int precision) {
		String cle = null;
		if(precision==PRECISION_GRAPH_ANNEE) {
			cle = annee;
		} else if (precision==PRECISION_GRAPH_MOIS){
			cle = mois+"/"+annee;
		} else if (precision==PRECISION_GRAPH_JOUR){
			cle = jour+"/"+mois+"/"+annee;
		}
		return cle;
	}

}
