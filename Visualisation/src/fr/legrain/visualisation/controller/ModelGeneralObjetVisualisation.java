package fr.legrain.visualisation.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.ModelObject;

public class ModelGeneralObjetVisualisation</*DTO extends ModelObject,DAO extends AbstractLgrDAO<Entity>,*/Entity> implements IModelGeneral{
	
	static Logger logger = Logger.getLogger(ModelGeneralObjetVisualisation.class.getName());
	
	private AbstractLgrDAO<Entity> dao;
	private Class<Resultat> typeObjet;
	private LinkedList<Resultat> listeObjet = new LinkedList<Resultat>();
	private Collection<Entity> listeEntity = null;
	private String JPQLQuery = null;
//	private LgrDozerMapper<Entity,DTO> mapperModelToUI = null;
	
	
//	@PersistenceContext
	private EntityManager entityManager = null;//dao.getEntityManager();

	public ModelGeneralObjetVisualisation(AbstractLgrDAO<Entity> dao,Class<Resultat> typeObjet){
		this.dao = dao;
		entityManager = dao.getEntityManager();
		this.typeObjet = typeObjet;
//		initMapper();
	}
	
	public ModelGeneralObjetVisualisation(Class<Resultat> typeObjet){
		this.typeObjet = typeObjet;
//		initMapper();
	}
	
	public ModelGeneralObjetVisualisation(Collection<Entity> listeEntity,Class<Resultat> typeObjet){
		this.typeObjet = typeObjet;
		this.listeEntity = listeEntity;
//		initMapper();
	}
	
//	private void initMapper() {
//		mapperModelToUI = new LgrDozerMapper<Entity,Resultat>();
//	}
	
	public void destroy() {
		dao = null;
		typeObjet = null;
		listeObjet.clear();
		listeObjet = null;
	}

	public AbstractLgrDAO<Entity> getDao() {
		return dao;
	}
	

	
	public Resultat recherche(String propertyName, Object value) {
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
		
		if(trouve)
			return listeObjet.get(i);
		else 
			return null;
		
//		return trouve ? listeObjet.get(i) : null;
		
	}

  
	
	
	@SuppressWarnings("unchecked")
	public LinkedList<Resultat> remplirListe() {
		try {
			logger.debug("ModelGeneralObjet.remplirListe()");

			Collection<Entity> l = null;
			
			if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
				if(JPQLQuery!=null) {
					Query ejbQuery = entityManager.createQuery(JPQLQuery);
					l = ejbQuery.getResultList();
				} else if (dao != null) {
					l = dao.selectAll();
				}
				listeEntity = l;
			} else {
				//l = new LinkedList<Entity>();
				l = listeEntity;
			}
			listeObjet.clear();

			for (Entity o : l) {
				Resultat t = null;
//				t = (Resultat) mapperModelToUI.map(o, typeObjet);
				t = (Resultat) mapModelToUI(o, new Resultat());
				listeObjet.add(t);
			}
			return listeObjet;
		} catch(Exception e) {
			logger.error("",e);
		}
		
		return listeObjet;
	}
	
	@Override
	public ModelObject rechercheDansListe(LinkedList <ModelObject> listeObjet, String propertyName,
			Object value) {
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
		
		if(trouve)
			return listeObjet.get(i);
		else 
			return null;
		
	}
	
	public LinkedList<Resultat> getListeObjet() {
		return listeObjet;
	}

	public void setDao(AbstractLgrDAO<Entity> dao) {
		this.dao = dao;
	}

	public String getJPQLQuery() {
		return JPQLQuery;
	}

	public void setJPQLQuery(String query) {
		JPQLQuery = query;
	}

//	public LgrDozerMapper<Entity, Resultat> getMapperModelToUI() {
//		return mapperModelToUI;
//	}

	public Collection<Entity> getListeEntity() {
		return listeEntity;
	}

	public void setListeEntity(Collection<Entity> listeEntity) {
		this.listeEntity = listeEntity;
	}


	public void addEntity(Entity entite){
		if(listeEntity!=null){
			if(!listeEntity.contains(entite))
				listeEntity.add(entite);
		}
	}
	
	public void removeEntity(Entity entite){
		if(listeEntity!=null){
			if(listeEntity.contains(entite))
				listeEntity.remove(entite);
		}
	}

	public LinkedList<Resultat> rechercheList(String propertyName, Object value) {
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
			Resultat t = listeObjet.get(i);
			LinkedList<Resultat> l = new LinkedList<Resultat>();
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

	
	public Resultat mapModelToUI(Object src, Resultat dest) {
		Object[] o = (Object[])src;
		Object value = null;
		for (int i = 0; i < o.length; i++) {
			value=o[i];
			try {
				if(value!=null 
						&& PropertyUtils.getPropertyDescriptor(dest, Resultat.debutNomChamp+(i+1))!=null
						) {
					PropertyUtils.setProperty(dest, Resultat.debutNomChamp+(i+1), value.toString());
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		PropertyDescriptor[] desc = PropertyUtils.getPropertyDescriptors(src.getClass());
//		Object value = null;
//		for (int i = 0; i < desc.length; i++) {
//			value = desc[i].getValue(desc[i].getName());
//			try {
//				PropertyUtils.setProperty(dest, Resultat.debutNomChamp+(i+1), value.toString());
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		return dest;
	}

	@Override
	public void razListEntity() {
		setListeEntity(null);		
	}

	@Override
	public ModelObject remplirListeLazy(EntityManager em, int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}




