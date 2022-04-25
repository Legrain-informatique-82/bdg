package fr.legrain.facture.actions;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.gestCom.Appli.EntityManagerUtil;

public class TestPerfJPA implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		try {
			//		System.out.println("ActionCnxBDD.run() : "+new Date());
			//		EntityManagerUtil.getEntityManagerFactory();
			//		EntityManagerUtil.getEntityManager();
			//		System.out.println("ActionCnxBDD.run() : "+new Date());

			int nb = 50000;
			//EntityManagerFactory emf = Persistence.createEntityManagerFactory("sample");
			//EntityManager em = emf.createEntityManager(); // Retrieve an application managed entity manager
			//		EntityManager em = EntityManagerUtil.getEntityManager();
			//		Work with the EM

			//		em.getTransaction().begin();


			System.out.println("init");
			//		Session session = HibernateUtil.getSessionFactory().openSession();
			//		session.beginTransaction();

			//Session s = HibernateUtil.getSession();
			//s.beginTransaction();
			System.out.println("init ok");

			//		TaFacture taFacture = null;

			TaFactureDAO dao = new TaFactureDAO();
			System.out.println("debut select all TestPerfJPA.run()");
			Date deb = new Date();
			List<TaFacture> l = dao.selectAll();
			Date fin = new Date();
			System.out.println("fin select all TestPerfJPA.run() "+deb+" *** "+fin);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
