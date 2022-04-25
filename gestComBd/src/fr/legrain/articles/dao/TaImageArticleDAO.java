package fr.legrain.articles.dao;

//Generated Aug 11, 2008 4:05:28 PM by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibChaine;

/**
 * Home object for domain model class TaImageArticle.
 * @see fr.legrain.articles.dao.TaImageArticle
 * @author Hibernate Tools
 */
public class TaImageArticleDAO extends AbstractApplicationDAO<TaImageArticle> {

	private static final Log logger = LogFactory.getLog(TaImageArticleDAO.class);

	private String defaultJPQLQuery = "select p from TaImageArticle p";
	
	public TaImageArticleDAO(){
		this(null);
	}

	public TaImageArticleDAO(EntityManager em){
		if(em!=null) {
			setEm(em);
		}
		initChampId(TaImageArticle.class);
		setJPQLQuery(defaultJPQLQuery);
		setListeChampMaj(Const.C_FICHIER_INI_CHAMPMAJ);
		initNomTable(new TaImageArticle());
	}


	protected void persist(TaImageArticle transientInstance) {
		logger.debug("persisting TaImageArticle instance");
		try {
			getEntityManager().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}
	public TaImageArticle refresh(TaImageArticle detachedInstance) {
		logger.debug("refresh instance");
		try {
			org.hibernate.Session session = (org.hibernate.Session) getEntityManager().getDelegate();
		    session.evict(detachedInstance);
		    detachedInstance = getEntityManager().find(TaImageArticle.class, detachedInstance.getIdImageArticle());
		    return detachedInstance;
			
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}

	protected void remove(TaImageArticle persistentInstance) {
		logger.debug("removing TaImageArticle instance");
		//boolean estRefPrix=false;
		try {
			/*
			 * Firebird ne supporte pas les "self referencing foreign keys" (ou reflexive foreign keys),
			 * càd une clé étrangère qui pointe sur la même table. Sans définition de foreign key pour le champ "imageOrigine"
			 * il est impossible pour JPA/Hibernate de faire le delete en cascade, il faut donc le faire manuellement.
			 */
			for(TaImageArticle img : persistentInstance.getTaImagesGenere()) {
				//img.setImageOrigine(null);
				//img.setTaArticle(null);
				getEntityManager().remove(img);
				
			}
			persistentInstance.getTaImagesGenere().clear();
			
			getEntityManager().remove(persistentInstance);

			logger.debug("remove successful");
		} catch (RuntimeException re) {
			logger.error("remove failed", re);
			throw re;
		}
	}

	protected TaImageArticle merge(TaImageArticle detachedInstance) {
		logger.debug("merging TaImageArticle instance");
		try {
			TaImageArticle result = getEntityManager().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public TaImageArticle findById(int id) {
		logger.debug("getting TaImageArticle instance with id: " + id);
		try {
			TaImageArticle instance = getEntityManager().find(TaImageArticle.class, id);
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
	public List<TaImageArticle> selectAll() {
		logger.debug("selectAll TaArticle");
		try {
			Query ejbQuery = getEntityManager().createQuery(JPQLQuery);
			List<TaImageArticle> l = ejbQuery.getResultList();
			logger.debug("selectAll successful");
			return l;
		} catch (RuntimeException re) {
			logger.error("selectAll failed", re);
			throw re;
		}
	}

	public void ctrlSaisieSpecifique(TaImageArticle entity,String field) throws ExceptLgr {	
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

