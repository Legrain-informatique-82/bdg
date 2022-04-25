package fr.legrain.edition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



public class ImprimeObjet {
	
	//edition avec un seul dataset
	static public final List<Object> l = new ArrayList<Object>();
	static Logger logger = Logger.getLogger(ImprimeObjet.class.getName());
	
	//edition dynamique avec plusieurs dataset
	/*** Map m pour les documents de l'edition ***/
	static public final Map<String, List<Object>> m =new HashMap<String, List<Object>>();
	
	static public final Map<String, String> listWidth =new HashMap<String, String>();

	static public final Map<String, List<?>> mapEdition =new HashMap<String, List<?>>();
	
	static public final Map<String, List<?>> mapEditionTabel =new HashMap<String, List<?>>();
	
	static public final Map<String, Map<String, List<?>>> mapEditionComplexe = new HashMap<String, Map<String,List<?>>>();
	
		
	static public final List<Object> list2 = new ArrayList<Object>();
	
	static public final List<Object> list3 = new ArrayList<Object>();
	
	static public final List<Object> list4 = new ArrayList<Object>();
	
	static public final List<Object> list5 = new ArrayList<Object>();
	
	static public final LinkedHashMap<String,Float> map1 = new LinkedHashMap<String, Float>();
	
	static public final LinkedList<String> LinkedList1 = new LinkedList<String>();
	static public final LinkedList<Float> LinkedList2 = new LinkedList<Float>();
	
	//static public final List<Object> listTaInfoEntreprise = new ArrayList<Object>();
	static public final List<Object> listEntityManager = new ArrayList<Object>();
	
	public static void clearListAndMap(){
		logger.debug("clearListAndMap");
		l.clear();
		m.clear();
		listWidth.clear();
		
		list2.clear();
		list3.clear();
		list4.clear();
		list5.clear();
		map1.clear();
		LinkedList1.clear();
		LinkedList2.clear();
		//listTaInfoEntreprise.clear();
		listEntityManager.clear();
		mapEditionComplexe.clear();
		mapEditionTabel.clear();
		mapEdition.clear();
	
		
	}
	
	 

//	static public list getl() {
//		return l;
//	}
//
//	static public void setL(List l) {
//		ImprimeObjet.l = l;
//	}

}
