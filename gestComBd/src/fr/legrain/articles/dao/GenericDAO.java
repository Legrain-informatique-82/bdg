package fr.legrain.articles.dao;

//Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Text;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.MessCtrlLgr;
import fr.legrain.lib.data.QueDesChiffresPositif;

/**
 * Home object for domain model class TaPrix.
 * @see fr.legrain.articles.dao.TaPrix
 * @author Hibernate Tools
 */
public class GenericDAO<T> extends AbstractApplicationDAO<T> {

	private static final Log logger = LogFactory.getLog(GenericDAO.class);
	//static Logger logger = Logger.getLogger(TaPrixDAO.class.getName());

	//@PersistenceContext
	//private EntityManager entityManager = getEntityManager();

	private String defaultJPQLQuery = "select p from TaPrix p";
	private Class<T> t;

	public GenericDAO(){
		initChampId(t);
			//ctrlGeneraux.getID_TABLE(t.getSimpleName());
		setJPQLQuery(defaultJPQLQuery);
	}

	public IStatus validate(T entity, String field, String idEntity, String validationContext){
		//public IStatus validate(Object entity, String field, String contexte){
		IStatus s = null;
		try {
//			ClassValidator<TaUnite> uniteValidator = new ClassValidator<TaUnite>( TaUnite.class );
//			InvalidValue[] iv = uniteValidator.getInvalidValues(entity,field);

			System.out.println("GenericDAO.validate() : "+field);
//			if(iv.length>0 && iv[0].getPropertyName().equals(field)) {
//			s = new Status(0,"",iv[0].getMessage());
//			}



			MessCtrlLgr mess = new MessCtrlLgr();
			LgrHibernateValidated a = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(entity, field)).getAnnotation(LgrHibernateValidated.class);
			if(a!=null){
				mess.setContexte(validationContext);
				mess.setNomChampDB(a.champ());
				mess.setNomChamp(field);
				mess.setNomTable(t.getAnnotation(Table.class).name());
				mess.setEntityClass(a.clazz());
				mess.setModeObjet(getModeObjet());
				if(PropertyUtils.getProperty(entity, field)!=null)
					mess.setValeur(PropertyUtils.getProperty(entity, field).toString());
				else
					mess.setValeur(null);
				mess.setID_Valeur(idEntity);
				mess.setNomChampId(initChampId(entity));
				ctrlGeneraux.ctrlSaisie(mess);
			}
			s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (ExceptLgr e) {
			logger.error("",e);
			s = new Status(Status.ERROR,gestComBdPlugin.PLUGIN_ID,e.getMessage(),e);
			return s;
			//valid = false;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
		return null;

	}

	public void persist(T transientInstance) {
		logger.debug("persisting instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void remove(T persistentInstance) {
		logger.debug("removing instance");
		try {
			getEntityManager().remove(persistentInstance);
			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	public T merge(T detachedInstance) {
		logger.debug("merging instance");
		try {
			T result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	//public T findById(Class<T> t, int id) {
	public T findById(int id) {
		logger.debug("getting instance with id: " + id);
		try {
			T instance = getEntityManager().find(t, id);
			logger.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see fr.legrain.articles.dao.ILgrDAO#selectAll()
	 */
	public List<T> selectAll() {
		logger.debug("selectAll");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<T> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	protected T refresh(T persistentInstance) {
		// TODO Auto-generated method stub
		return persistentInstance;
	}

//	/**
//	 * NON IMPLEMENTE, utiliser <code>findById(Class<T> c, int id)</code> a la place
//	 * @deprecated
//	 * @see #findById(Class, int)
//	 */
//	public T findById(int id) {
//		try {
//		throw new NotImplementedException();
//		} catch(Exception e) {
//			logger.error("NotImplementedException : public T findById(int id) dans GenericDAO", e);
//		}
//		return null;
//	}

}
