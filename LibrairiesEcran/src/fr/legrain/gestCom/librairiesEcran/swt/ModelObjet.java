package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.QueryDataSet;

import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModelObject;

public class ModelObjet<T extends ModelObject>{ 
//implements IModelGeneral{
	
	static Logger logger = Logger.getLogger(ModelObjet.class.getName());
	
	private Class<T> typeObjet;
	private LinkedList<T> listeObjet = new LinkedList<T>();
	private String separator ;
	private LinkedList liste;

	public ModelObjet(Class<T> typeObjet,String separator){
		this.typeObjet = typeObjet;
		this.separator = separator;		
	}
	public ModelObjet(LinkedList liste,Class<T> typeObjet,String separator){
		this.typeObjet = typeObjet;
		this.separator = separator;
		this.liste = liste;
		remplirListe();
	}
	
	public void destroy() {
		typeObjet = null;
		listeObjet.clear();
		listeObjet = null;
	}



	public LinkedList<T> remplirListe() {
		if(liste!=null){
			for (int i = 0; i < liste.size(); i++) {
				listeObjet.add(remplirObjet(liste.subList(i,i).toString()));	
			}
		}
		return listeObjet;
	}

	public String renvoieValeurObjet() {
		String retour=null;
		for (T elem : listeObjet) {		
			for (int i = 0; i < typeObjet.getDeclaredFields().length; i++) {
				Method getter;
				try {
					getter = typeObjet.getMethod("get"+typeObjet.getDeclaredFields()[i].getName().toUpperCase(), new Class[] {typeObjet.getDeclaredFields()[i].getType()});
					retour+=getter.invoke(elem,null)+";";
				} catch (Exception e) {
					logger.error("",e);
				} 
			}
		}
		return retour;
	}
	
	public T remplirObjet(String ligne){
		T t = null;

		try {
			t = typeObjet.newInstance();

			String[]valeurs = ligne.split(separator);	
			if (valeurs.length!=typeObjet.getDeclaredFields().length)
				throw new Exception();
			for (int i = 0; i < typeObjet.getDeclaredFields().length; i++) {
				String deb =typeObjet.getDeclaredFields()[i].getName().substring(0, 1).toUpperCase();
				String fin =typeObjet.getDeclaredFields()[i].getName().substring(1);
				Method setter = typeObjet.getMethod("set"+deb+fin, new Class[] {typeObjet.getDeclaredFields()[i].getType()});
				setter.invoke(t, new Object[]{valeurs[i]});
			}
			return t;
		} catch (Exception e) {
			logger.error("",e);
		} 

		return t;
	}
	
	
	public LinkedList<T> getListeObjet() {
		return listeObjet;
	}
	public void setListeObjet(LinkedList<T> listeObjet) {
		this.listeObjet = listeObjet;
	}



}
