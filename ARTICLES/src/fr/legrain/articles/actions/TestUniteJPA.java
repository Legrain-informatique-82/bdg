package fr.legrain.articles.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;



public class TestUniteJPA implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
//		try {
//			System.out.println("ActionCnxBDD.run() : "+new Date());
//			EntityManagerUtil.getEntityManagerFactory();
//			EntityManagerUtil.getEntityManager();
//			System.out.println("ActionCnxBDD.run() : "+new Date());
//			
//			int nb = 10;
//			//EntityManagerFactory emf = Persistence.createEntityManagerFactory("sample");
//			//EntityManager em = emf.createEntityManager(); // Retrieve an application managed entity manager
//			EntityManager em = EntityManagerUtil.getEntityManager();
////			Work with the EM
//
//			em.getTransaction().begin();
//
//
//			System.out.println("init");
////			Session session = HibernateUtil.getSessionFactory().openSession();
////			session.beginTransaction();
//
//			//Session s = HibernateUtil.getSession();
//			//s.beginTransaction();
//			System.out.println("init ok");
//
//			TaArticle taArticle = null;
//
//			int i = 0;
//			while (i<nb) {
//				i++;
////				System.out.println("taArticle N°"+i);
//
////				TaUnite taUnite = (TaUnite) em.find(fr.legrain.articles.dao.TaUnite.class, 1);
//				TaUnite taUnite = new TaUnite();
//				//taUnite.setIdUnite(i);
//				taUnite.setCodeUnite("U_"+i);
//				taUnite.setLiblUnite("Lib_U_"+i);
//
//				ILgrDAO taUniteDAO = new TaUniteDAO();
//				taUniteDAO.persist(taUnite);
//				//em.merge(taArticle);
//				em.persist(taUnite);
//
//			}
//		//	System.out.println("save ok (id = "+taArticle.getIdArticle()+")");
//			//s.getTransaction().commit();
//			em.getTransaction().commit();
//			
//			
//			System.out.println("ActionCloseCnxBdd.run() : "+new Date());
//			EntityManagerUtil.closeEntityManager();
//			System.out.println("ActionCloseCnxBdd.run() : "+new Date());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
