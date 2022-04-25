package fr.legrain.articles.actions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.articles.dao.ILgrValidable;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaRefPrix;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.EntityManagerUtil;



public class TestArticleJPA implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		try {
			System.out.println("ActionCnxBDD.run() : "+new Date());
			EntityManagerUtil.getEntityManagerFactory();
			EntityManagerUtil.getEntityManager();
			System.out.println("ActionCnxBDD.run() : "+new Date());
			
			int nb = 10;
			//EntityManagerFactory emf = Persistence.createEntityManagerFactory("sample");
			//EntityManager em = emf.createEntityManager(); // Retrieve an application managed entity manager
			EntityManager em = EntityManagerUtil.getEntityManager();
//			Work with the EM

			em.getTransaction().begin();


			System.out.println("init");
//			Session session = HibernateUtil.getSessionFactory().openSession();
//			session.beginTransaction();

			//Session s = HibernateUtil.getSession();
			//s.beginTransaction();
			System.out.println("init ok");

			TaArticle taArticle = null;

			int i = 0;
			while (i<nb) {
				i++;
				System.out.println("taArticle N°"+i);
				taArticle = new TaArticle();
				//taArticle.setIdArticle(i);
				taArticle.setCodeArticle("ART_"+i);
				taArticle.setCommentaireArticle("commentaire_"+i);
				taArticle.setDiversArticle("divers_"+i);
				taArticle.setLibellecArticle("artc_"+i);
				taArticle.setLibellelArticle("artl_"+i);
				taArticle.setNumcptArticle("123456");
				taArticle.setStockMinArticle(new BigDecimal(10));

				TaFamille taFamille = (TaFamille) em.find(fr.legrain.articles.dao.TaFamille.class, 1);
				taArticle.setTaFamille(taFamille);

//				taArticle.setTaLFactures(taLFactures);
//				taArticle.setTaLFactureTemps(taLFactureTemps);

				TaPrix taPrix = new TaPrix();
				//taPrix.setIdPrix(idPrix);
				taPrix.setPrixPrix(new BigDecimal(100+i));
				taPrix.setPrixttcPrix(new BigDecimal(200+i));
				TaUnite taUnite = (TaUnite) em.find(fr.legrain.articles.dao.TaUnite.class, 1);
				taPrix.setTaUnite(taUnite);
				taPrix.setTaArticle(taArticle);
				
				taArticle.setTaPrix(taPrix);
				
//				if(i%2==0){
//					short p = 0;
//					taPrix.setRefPrixPrix(p);
//				} else {
//					short p = 1;
//					taPrix.setRefPrixPrix(p);
//				}

//				TaRefPrix taRefPrix = new TaRefPrix();
//				taRefPrix.setTaArticle(taArticle);
//				taRefPrix.setTaPrix(taPrix);
//				taPrix.getTaRefPrixes().add(taRefPrix);
//
//				taArticle.getTaPrixes().add(taPrix);
//				taArticle.setTaRefPrixes(taPrix.getTaRefPrixes());

				TaTTva taTTva =(TaTTva) em.find(fr.legrain.articles.dao.TaTTva.class, 1);
				taArticle.setTaTTva(taTTva);
				TaTva taTva =(TaTva) em.find(fr.legrain.articles.dao.TaTva.class, 1);
				taArticle.setTaTva(taTva);

				//TaArticleHome taArticleHome = new TaArticleHome();
				//taArticleHome.attachDirty(taArticle);
				
				//em.merge(taArticle);
				em.persist(taArticle);
				//s.save(taArticle);

			}
		//	System.out.println("save ok (id = "+taArticle.getIdArticle()+")");
			//s.getTransaction().commit();
			em.getTransaction().commit();
			
			
			
			System.out.println("ActionCloseCnxBdd.run() : "+new Date());
			EntityManagerUtil.closeEntityManager();
			System.out.println("ActionCloseCnxBdd.run() : "+new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
