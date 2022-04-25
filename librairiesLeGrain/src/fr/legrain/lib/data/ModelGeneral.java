package fr.legrain.lib.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
 
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.QueryDataSet;

public class ModelGeneral<T extends ModelObject> implements IModelGeneral{
	
	static Logger logger = Logger.getLogger(ModelGeneral.class.getName());
	
	private QueryDataSet queryDataSet;
	private Class<T> typeObjet;
	private LinkedList<T> listeObjet = new LinkedList<T>();

	public ModelGeneral(QueryDataSet queryDataSet,Class<T> typeObjet){
		this.queryDataSet = queryDataSet;
		this.typeObjet = typeObjet;
	}
	
	public void destroy() {
		queryDataSet = null;
		typeObjet = null;
		listeObjet.clear();
		listeObjet = null;
	}

	public QueryDataSet getQueryDataSet() {
		return queryDataSet;
	}

	public LinkedList<T> remplirListe() {
		DataRow dr = new DataRow(queryDataSet);
		if(!queryDataSet.isOpen())
			queryDataSet.open();
		else
			queryDataSet.refresh();
		listeObjet.clear();
		
		if(!queryDataSet.isEmpty()){
			queryDataSet.first();
			do {
				dr = new DataRow(queryDataSet);//isa le 15/09/2008
				//car sinon les dates sont écrasées par la dernière date récupérée !!!
				queryDataSet.getDataRow(dr);	
				listeObjet.add(remplirObjet(dr));
				dr = null;
			} while(queryDataSet.next());
		}
		
		return listeObjet;
	}

	public T remplirObjet(DataRow dr){
		T t = null;

		try {
			t = typeObjet.newInstance();
			Method setter;

			for (int i = 0; i < typeObjet.getDeclaredFields().length; i++) {
				if(dr.getColumn(typeObjet.getDeclaredFields()[i].getName())!=null) {
					setter = typeObjet.getMethod("set"+typeObjet.getDeclaredFields()[i].getName().toUpperCase(), new Class[] {typeObjet.getDeclaredFields()[i].getType()});
				
					switch(dr.getColumn(typeObjet.getDeclaredFields()[i].getName()).getDataType()) {
					case Variant.INT:
						if(typeObjet.getDeclaredFields()[i].getType() == Boolean.class)
							setter.invoke(t, new Object[]{LibConversion.intToBoolean(dr.getInt(typeObjet.getDeclaredFields()[i].getName()))});
						else
							setter.invoke(t, new Object[]{dr.getInt(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.STRING:
						setter.invoke(t, new Object[]{dr.getString(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.DOUBLE:
						setter.invoke(t, new Object[]{dr.getDouble(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.BIGDECIMAL:
						switch(dr.getColumn(typeObjet.getDeclaredFields()[i].getName()).getDataType()){
						case Variant.DOUBLE:setter.invoke(t, new Object[]{dr.getDouble(typeObjet.getDeclaredFields()[i].getName())});
							break;
						case Variant.BIGDECIMAL :setter.invoke(t, new Object[]{dr.getBigDecimal(typeObjet.getDeclaredFields()[i].getName())});
							break;
						}
						break;	
					case Variant.FLOAT:
						setter.invoke(t, new Object[]{dr.getFloat(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.LONG:
						setter.invoke(t, new Object[]{dr.getLong(typeObjet.getDeclaredFields()[i].getName())});
						break;				
					case Variant.DATE:
						setter.invoke(t, new Object[]{dr.getDate(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.TIMESTAMP:
//						SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy",Locale.FRENCH);
//						try {
//							setter.invoke(t, new Object[]{date.parse(date.format(dr.getTimestamp(typeObjet.getDeclaredFields()[i].getName())))});
//							} catch (ParseException e) {
//								setter.invoke(t, new Object[]{new Date()});
//							}
						
						setter.invoke(t, new Object[]{dr.getTimestamp(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.BOOLEAN:
						setter.invoke(t, new Object[]{dr.getBoolean(typeObjet.getDeclaredFields()[i].getName())});
						break;
					case Variant.SHORT:
						if(typeObjet.getDeclaredFields()[i].getType() == Boolean.class)
							setter.invoke(t, new Object[]{LibConversion.intToBoolean(dr.getShort(typeObjet.getDeclaredFields()[i].getName()))});
						else
							setter.invoke(t, new Object[]{dr.getShort(typeObjet.getDeclaredFields()[i].getName())});
						break;
					
					default: 

					}
				}
			}
			return t;
		} catch (InstantiationException e) {
			logger.error("",e);
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (IllegalArgumentException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}

		return t;
	}
	
//	public T remplirObjet(DataRow dr){
//		T t = null;
//
//		try {
//			t = typeObjet.newInstance();
//			Method setter;
//
//			for (int i = 0; i < typeObjet.getDeclaredFields().length; i++) {
//				if(dr.getColumn(typeObjet.getDeclaredFields()[i].getName())!=null) {
//					setter = typeObjet.getMethod("set"+typeObjet.getDeclaredFields()[i].getName(), new Class[] {typeObjet.getDeclaredFields()[i].getType()});
//				
//					switch(dr.getColumn(typeObjet.getDeclaredFields()[i].getName()).getDataType()) {
//					case Variant.STRING:
//						setter.invoke(t, new Object[]{dr.getString(typeObjet.getDeclaredFields()[i].getName())});
//						break;
//					case Variant.INT:
//						if(typeObjet.getDeclaredFields()[i].getType() == Boolean.class)
//							setter.invoke(t, new Object[]{LibConversion.intToBoolean(dr.getInt(typeObjet.getDeclaredFields()[i].getName()))});
//						else
//							setter.invoke(t, new Object[]{String.valueOf(dr.getInt(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					case Variant.DOUBLE:
//						setter.invoke(t, new Object[]{String.valueOf(dr.getDouble(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					case Variant.BIGDECIMAL:	
//						setter.invoke(t, new Object[]{String.valueOf(dr.getBigDecimal(typeObjet.getDeclaredFields()[i].getName()))});
//						break;	
//					case Variant.FLOAT:
//						setter.invoke(t, new Object[]{String.valueOf(dr.getFloat(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					case Variant.LONG:
//						setter.invoke(t, new Object[]{String.valueOf(dr.getLong(typeObjet.getDeclaredFields()[i].getName()))});
//						break;				
//					case Variant.DATE:
//						setter.invoke(t, new Object[]{LibDate.dateToString(dr.getDate(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					case Variant.TIMESTAMP:
//						setter.invoke(t, new Object[]{dr.getTimestamp(typeObjet.getDeclaredFields()[i].getName())});
//						break;
//					case Variant.BOOLEAN:
//						setter.invoke(t, new Object[]{String.valueOf(dr.getBoolean(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					case Variant.SHORT:
//						if(typeObjet.getDeclaredFields()[i].getType() == Boolean.class)
//							setter.invoke(t, new Object[]{LibConversion.intToBoolean(dr.getShort(typeObjet.getDeclaredFields()[i].getName()))});
//						else
//							setter.invoke(t, new Object[]{String.valueOf(dr.getShort(typeObjet.getDeclaredFields()[i].getName()))});
//						break;
//					
//					default:
//
//					}
//				}
//			}
//			return t;
//		} catch (InstantiationException e) {
//			logger.error("",e);
//		} catch (IllegalAccessException e) {
//			logger.error("",e);
//		} catch (SecurityException e) {
//			logger.error("",e);
//		} catch (IllegalArgumentException e) {
//			logger.error("",e);
//		} catch (InvocationTargetException e) {
//			logger.error("",e);
//		} catch (NoSuchMethodException e) {
//			logger.error("",e);
//		}
//
//		return t;
//	}
	
	
	public LinkedList<T> getListeObjet() {
		return listeObjet;
	}

	public void setQueryDataSet(QueryDataSet queryDataSet) {
		this.queryDataSet = queryDataSet;
	}

	public Object recherche(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelObject rechercheDansListe(LinkedList<ModelObject> listeObjet,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<T> rechercheList(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<listeObjet.size()){
			try {
				if(PropertyUtils.getProperty(listeObjet.get(i), propertyName).equals(value)) {
					trouve = true;
				} else {
					i++;
				}
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}	
		if(trouve){
			T t = listeObjet.get(i);
			LinkedList<T> l = new LinkedList<T>();
			l.add(t);
			return l;
		}
		return null;	
	}

	@Override
	public List remplirListeElement(Object e, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void razListEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModelObject remplirListeLazy(EntityManager em, int id) {
		// TODO Auto-generated method stub
		return null;
	}



//	public IDeleteHandler getPersonListDeleteHandler() {
//	return new IDeleteHandler() {
//	public boolean canDelete(int rowInCollection) {
//	return true;
//	}

//	public void deleteRow(int rowInCollection) {
//	ListUnite.remove(rowInCollection);
//	}
//	};
//	}

//	public IInsertHandler getPersonListInsertHandler() {
//	return new IInsertHandler() {
//	public int insert(int positionHint) {
//	Person newPerson = new Person();
//	ListUnite.add(positionHint, newPerson);
//	return positionHint;
//	// int newPosition = (int)(Math.random() *
//	// (personList.size()+1));
//	// personList.add(newPosition, newPerson);
//	// return newPosition;
//	}
//	};
//	}

}
