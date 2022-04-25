package fr.legrain.gestCom.Appli;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.sun.jmx.snmp.Timestamp;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.IModelGeneral;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;

public class ModelGeneralObjet<DTO extends ModelObject,DAO extends AbstractLgrDAO<Entity>,Entity> 

implements IModelGeneral{
	
	static Logger logger = Logger.getLogger(ModelGeneralObjet.class.getName());
	
	private AbstractLgrDAO<Entity> dao;
	private Class<DTO> typeObjet;
	private LinkedList<DTO> listeObjet = new LinkedList<DTO>();
	private Collection<Entity> listeEntity = null;
	private String JPQLQuery = null;
	private LgrDozerMapper<Entity,DTO> mapperModelToUI = null;
	private IlgrMapper<DTO, Entity> lgrMapper = null;
	
	
//	@PersistenceContext
	//private EntityManager entityManager = AbstractApplicationDAO.getEntityManager();
	private EntityManager entityManager = null;//dao.getEntityManager();

	public ModelGeneralObjet(AbstractLgrDAO<Entity> dao,Class<DTO> typeObjet){
		this.dao = dao;
		entityManager = dao.getEntityManager();
		this.typeObjet = typeObjet;
		initMapper();
	}
	
	public ModelGeneralObjet(Class<DTO> typeObjet,EntityManager entityManager){
		this.typeObjet = typeObjet;
		this.entityManager = entityManager;
		initMapper();
	}
	
	public ModelGeneralObjet(Collection<Entity> listeEntity,Class<DTO> typeObjet,EntityManager entityManager){
		this.typeObjet = typeObjet;
		this.listeEntity = listeEntity;
		this.entityManager = entityManager;
		initMapper();
	}
	
	private void initMapper() {
		if(lgrMapper==null)
			mapperModelToUI = new LgrDozerMapper<Entity,DTO>();
	}
	
	public void destroy() {
		dao = null;
		typeObjet = null;
		listeObjet.clear();
		listeObjet = null;
	}

	public AbstractLgrDAO<Entity> getDao() {
		return dao;
	}
	
	public DTO recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<listeObjet.size()){
			try {
				//PropertyUtils.getProperty(listeObjet.get(i), propertyName);
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
	public LinkedList<DTO> remplirListeElement(Object entite,String propertyName, Object value) {
		try {
			logger.debug("ModelGeneralObjet.remplirListeElement()");

			Collection<Entity> l = null;

			//			if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
//			if(listeEntity==null && JPQLQuery!=null) {//mis par nicolas le 17/12/2015 environ
				if(JPQLQuery!=null) {
				CustomFieldMapperIFLGR customFieldMapperIFLGR = new CustomFieldMapperIFLGR(propertyName);
				if(lgrMapper!=null)
					lgrMapper.entityToDto((Entity)entite);
				else {
					customMapper(customFieldMapperIFLGR);
					//mapperModelToUI.getMapper().setCustomFieldMapper(customFieldMapperIFLGR);
					mapperModelToUI.map((Entity)entite, typeObjet);
				}
				//				case Variant.INT:
				//				case Variant.STRING:
				//				case Variant.DOUBLE:
				//				case Variant.BIGDECIMAL:
				//				case Variant.DOUBLE:	
				//				case Variant.FLOAT:
				//				case Variant.LONG:			
				//				case Variant.DATE:
				//				case Variant.TIMESTAMP:
				//				case Variant.BOOLEAN:
				//				case Variant.SHORT:
				Query ejbQuery = null;	

				String predicat=" where ";
					if(JPQLQuery.toUpperCase().contains(" WHERE "))predicat=" and ";
				//traiter suivant type champ
				if (customFieldMapperIFLGR.getSrcFieldType(entite.getClass())
						==java.util.Date.class ||
						customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())==Timestamp.class) {
					ejbQuery = entityManager.createQuery(JPQLQuery
							+predicat+" EXTRACT(DAY FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
							+" and EXTRACT(MONTH FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
							+" and EXTRACT(YEAR FROM "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
					);						
					java.util.Date date =LibDate.stringToDate(value.toString());
					ejbQuery.setParameter(1,LibConversion.stringToInteger(LibDate.getJour(date)));
					ejbQuery.setParameter(2,LibConversion.stringToInteger(LibDate.getMois(date)));
					ejbQuery.setParameter(3,LibConversion.stringToInteger(LibDate.getAnnee(date)));							
				}else if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==String.class) {
					if(!value.toString().equals("")) {
					String aliasEntiteDansRequete ="";
					if(JPQLQuery.toLowerCase().contains("alias"))
						aliasEntiteDansRequete="alias";
					else
						aliasEntiteDansRequete=JPQLQuery.substring("select ".length(),"select ".length()+1);
					ejbQuery = entityManager.createQuery(JPQLQuery
							+predicat+" UPPER("+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") "
							+" like '%"+value.toString().toUpperCase()+"%'"
							//+" where "+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" like ?"
							//+" where "+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" like ?"
					);
					//ejbQuery.setParameter(1, ""+value.toString().toUpperCase()+"");
					}
				}else{
					String aliasEntiteDansRequete ="";
					if(JPQLQuery.toLowerCase().contains("alias"))
						aliasEntiteDansRequete="alias";
					else
						aliasEntiteDansRequete=JPQLQuery.substring("select ".length(),"select ".length()+1);
					ejbQuery = entityManager.createQuery(JPQLQuery
							+predicat+" UPPER("+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+") = ?"
							//+" where "+aliasEntiteDansRequete+"."+customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldName()+" = ?"
					);

					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==Integer.class) {
						if(customFieldMapperIFLGR.getFieldMapRetour().getDestFieldType(typeObjet)==Boolean.class)
							ejbQuery.setParameter(1,LibConversion.booleanToInt(LibConversion.StringToBoolean(value.toString())) );
						else
						ejbQuery.setParameter(1,LibConversion.stringToInteger(value.toString()) );
					}
					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==Double.class) {
						ejbQuery.setParameter(1, LibConversion.stringToDouble(value.toString()));
					}
					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==Float.class) {
						ejbQuery.setParameter(1, LibConversion.stringToFloat(value.toString()));
					}
					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==Short.class) {
						ejbQuery.setParameter(1, LibConversion.stringToShort(value.toString()));
					}
					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==BigDecimal.class) {
						ejbQuery.setParameter(1, LibConversion.stringToBigDecimal(value.toString()));
					}
					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
							==Long.class) {
						ejbQuery.setParameter(1, LibConversion.stringToLong(value.toString()));
					}
//					if (customFieldMapperIFLGR.getFieldMapRetour().getSrcFieldType(entite.getClass())
//							==String.class) {
//						ejbQuery.setParameter(1, value.toString().toUpperCase());
//					}
					
				}
				if(ejbQuery!=null)
					l = ejbQuery.getResultList();


			} else if (dao != null) {
				l = dao.selectAll();
			}
			
				listeEntity = l;
			//			} else {
			//				//l = new LinkedList<Entity>();
			//				l = listeEntity;
			//			}
			listeObjet.clear();

			if(l!=null) {
				for (Entity o : l) {
					DTO t = null;
					if(lgrMapper!=null)
						t = lgrMapper.entityToDto(o);
					else
						t = (DTO) mapperModelToUI.map(o, typeObjet);
					listeObjet.add(t);
				}
			}
			return listeObjet;
		} catch(Exception e) {
			logger.error("",e);
		}

		return listeObjet;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedList<DTO> remplirListe() {
		try {
			logger.debug("ModelGeneralObjet.remplirListe()");

			Collection<Entity> l = null;
			
			if(listeEntity==null) {	//recuperation des entites dans la base de donnees		
				if(JPQLQuery!=null) {
					if(entityManager==null)entityManager=dao.getEntityManager();
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
			logger.debug("Création listeObjet");
			
			logger.debug(new Timestamp(0));
			if(l!=null) {
				for (Entity o : l) {
					DTO t = null;
					if(lgrMapper!=null)
						t = lgrMapper.entityToDto(o);
					else
						t = (DTO) mapperModelToUI.map(o, typeObjet);
					listeObjet.add(t);
				}
			}
			logger.debug(new Timestamp(0));
			return listeObjet;
		} catch(Exception e) {
			logger.error("",e);
		}
		
		return listeObjet;
	}
	

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
	
	public LinkedList<DTO> getListeObjet() {
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

	public LgrDozerMapper<Entity, DTO> getMapperModelToUI() {
		return mapperModelToUI;
	}

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

	public IlgrMapper<DTO, Entity> getLgrMapper() {
		return lgrMapper;
	}

	public void razListEntity(){
		setListeEntity(null);
	}


   public void customMapper(CustomFieldMapperIFLGR custom){
	   mapperModelToUI.getMapper().setCustomFieldMapper(custom);
   }

public void setLgrMapper(IlgrMapper<DTO, Entity> lgrMapper) {
	this.lgrMapper = lgrMapper;
}

@Override
public ModelObject remplirListeLazy(EntityManager em, int id) {
	// TODO Auto-generated method stub
	return null;
}
}




