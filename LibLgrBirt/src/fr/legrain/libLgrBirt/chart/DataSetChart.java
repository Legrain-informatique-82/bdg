package fr.legrain.libLgrBirt.chart;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataSetChart {


	private String[] ArrayValuesXSeries;
	
	private LinkedHashMap<String, Double[]> mapValuesYSeries= new LinkedHashMap<String, Double[]>(); 
	
	public LinkedHashMap<String, Double[]> getMapValuesYSeries() {
		return mapValuesYSeries;
	}

	public void setMapValuesYSeries(LinkedHashMap<String, Double[]> mapValuesYSeries) {
		this.mapValuesYSeries = mapValuesYSeries;
	}

	public String[] getArrayValuesXSeries() {
		return ArrayValuesXSeries;
	}

	public void setArrayValuesXSeries(String[] arrayValuesXSeries) {
		ArrayValuesXSeries = arrayValuesXSeries;
	}	
	
//	private List<String> ValuesXSeries = new LinkedList<String>();
//	public String[] getArrayValuesXSeries(){
//	String[] values = new String[this.ValuesXSeries.size()];
//	for (int i = 0; i < ValuesXSeries.size(); i++) {
//		values[i] = ValuesXSeries.get(i);
//	}
//	return values;
//}
}
