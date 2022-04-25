package fr.legrain.liasseFiscale.actions;

import java.math.BigDecimal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.hibernate.Session;

import fr.legrain.liasseFiscale.db.dao.HibernateUtil;
import fr.legrain.liasseFiscale.db.dao.Initial;
import fr.legrain.liasseFiscale.db.ibTables.IbTaInitial;

public class TestXMLLiasseAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
	}
	
	public void in() {
		String xmlFile = "C:/Projet/Java/Eclipse/GestionCommerciale/LiasseFiscale/test.xml";
		
		InfosCompta info = new InfosCompta();
		Compte c = new Compte("bal","a","b",10.0,20.0);
		Compte c2 = new Compte("bal","c","d",30.0,40.0);
		Compte c3 = new Compte("immo","e","f",50.0,60.0);
		info.getListeCompte().add(c);
		info.getListeCompte().add(c2);
		info.getListeCompte().add(c3);
		
		InfoComplement compl = new InfoComplement("cle","v1","v2");
		InfoComplement compl2 = new InfoComplement("cle","v3","v4");
//		info.getListeSaisieComplement().add(compl);
//		info.getListeSaisieComplement().add(compl2);
		info.getListeSaisieComplement().put(new Cle("cle"),compl);
		info.getListeSaisieComplement().put(new Cle("cle"),compl2);
		
		info.sortieXML(xmlFile);
		
		info.lectureXML(xmlFile);
	}
	
	public void out() {
		String xmlFile = "C:/Projet/Java/Eclipse/GestionCommerciale/LiasseFiscale/test-repart.xml";
		
		Repart rep = new Repart();
		Repart rep2 = new Repart();
		Repart rep3 = new Repart();
		
		Compte c = new Compte("bal","a","b",10.0,20.0);
		Compte c2 = new Compte("bal","c","d",30.0,40.0);
		Compte c3 = new Compte("immo","e","f",50.0,60.0);
		
		Repartition repart1 = new Repartition();
		Repartition repart2 = new Repartition();
		Repartition repart3 = new Repartition();
		repart1.setMontant(10.0);
		repart1.getDetail().add(c);
		repart1.getDetail().add(c2);
		repart2.setMontant(20.0);
		repart2.getDetail().add(c);
		repart2.getDetail().add(c3);
		repart3.setMontant(30.0);
		repart3.getDetail().add(c);
		
		String cle1 = new String("A");
		String cle2 = new String("B");
		String cle3 = new String("C");
		
//		rep.getListeRepartition().put(cle1,repart1);
//		rep.getListeRepartition().put(cle2,repart2);
//		rep.getListeRepartition().put(cle3,repart3);
		
		rep.sortieXML(xmlFile);
		
		rep.lectureXML(xmlFile);
	}
	
	public void lectureTxtEpicea() {
		String txtFile = "C:/Projet/Java/Eclipse/GestionCommerciale/LiasseFiscale/exportEpicea.txt";
		String xmlFile = "C:/Projet/Java/Eclipse/GestionCommerciale/LiasseFiscale/txt2xml.xml";
		InfosComptaTxtEpicea compta = new InfosComptaTxtEpicea(txtFile);
		compta.readTxt();
		
		enregistrementIBTable(compta);
		//enregistrementHibernate(compta);
		
		((InfosCompta)compta).sortieXML(xmlFile);
	}
	
	/*
	 * Test IbTables
	 */
	public void enregistrementIBTable(InfosComptaTxtEpicea compta) {
		Initial initial = new Initial();
		IbTaInitial ibTaInitial = new IbTaInitial();
		//int x=0;
		for (Compte c : compta.getListeCompte()) {
			//initial.setIdInitial(x++);
			initial.setNumCpt(c.getNumero());
			initial.setLibelleCpt(c.getLibelle());
			initial.setMtDebit(new BigDecimal(c.getMtDebit()));
			
			if(c.getMtCredit()!=null) initial.setMtCredit(new BigDecimal(c.getMtCredit()));
			else initial.setMtCredit(new BigDecimal(0));
			
			if(c.getSolde()!=null) initial.setSolde(new BigDecimal(c.getSolde()));
			else initial.setSolde(new BigDecimal(0));
			
			if(c.getMtDebit2()!=null) initial.setMtDebit2(new BigDecimal(c.getMtDebit2()));
			else initial.setMtDebit2(new BigDecimal(0));
			
			if(c.getMtCredit2()!=null) initial.setMtCredit2(new BigDecimal(c.getMtCredit2()));
			else initial.setMtCredit2(new BigDecimal(0));
			
			if(c.getSolde2()!=null) initial.setSolde2(new BigDecimal(c.getSolde2()));
			else initial.setSolde2(new BigDecimal(0));
			
			if(c.getMtDebit3()!=null) initial.setMtDebit3(new BigDecimal(c.getMtDebit3()));
			else initial.setMtDebit3(new BigDecimal(0));
			
			if(c.getMtCredit3()!=null) initial.setMtCredit3(new BigDecimal(c.getMtCredit3()));
			else initial.setMtCredit3(new BigDecimal(0));
			
			if(c.getSolde3()!=null) initial.setSolde3(new BigDecimal(c.getSolde3()));
			else initial.setSolde3(new BigDecimal(0));
			
//			initial.setSolde(new BigDecimal(0));
//			initial.setMtDebit2(new BigDecimal(0));
//			initial.setMtCredit2(new BigDecimal(0));
//			initial.setSolde2(new BigDecimal(0));
//			initial.setMtDebit3(new BigDecimal(0));
//			initial.setMtCredit3(new BigDecimal(0));
//			initial.setSolde3(new BigDecimal(0));
			try {
				ibTaInitial.insertion(initial);
				ibTaInitial.enregistrement(initial);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Test Hibernate
	 * Mettre en commentaire : TaAcces.hbm.xml et TaModif.hbm.xml dans le fichier hibernate.cfg.xml
	 */
	public void enregistrementHibernate(InfosComptaTxtEpicea compta) {
		try {
			//HibernateUtil.getSessionFactory().close();
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Initial initial = null;

			int x=0;
			for (Compte c : compta.getListeCompte()) {
				initial = new Initial();
				initial.setIdInitial(x++);
				initial.setNumCpt(c.getNumero());
				initial.setLibelleCpt(c.getLibelle());
				initial.setMtDebit(new BigDecimal(c.getMtDebit()));
				
				if(c.getMtCredit()!=null) initial.setMtCredit(new BigDecimal(c.getMtCredit()));
				else initial.setMtCredit(new BigDecimal(0));
				
				if(c.getSolde()!=null) initial.setSolde(new BigDecimal(c.getSolde()));
				else initial.setSolde(new BigDecimal(0));
				
				if(c.getMtDebit2()!=null) initial.setMtDebit2(new BigDecimal(c.getMtDebit2()));
				else initial.setMtDebit2(new BigDecimal(0));
				
				if(c.getMtCredit2()!=null) initial.setMtCredit2(new BigDecimal(c.getMtCredit2()));
				else initial.setMtCredit2(new BigDecimal(0));
				
				if(c.getSolde2()!=null) initial.setSolde2(new BigDecimal(c.getSolde2()));
				else initial.setSolde2(new BigDecimal(0));
				
				if(c.getMtDebit3()!=null) initial.setMtDebit3(new BigDecimal(c.getMtDebit3()));
				else initial.setMtDebit3(new BigDecimal(0));
				
				if(c.getMtCredit3()!=null) initial.setMtCredit3(new BigDecimal(c.getMtCredit3()));
				else initial.setMtCredit3(new BigDecimal(0));
				
				if(c.getSolde3()!=null) initial.setSolde3(new BigDecimal(c.getSolde3()));
				else initial.setSolde3(new BigDecimal(0));
				
//				initial.setSolde(new BigDecimal(0));
//				initial.setMtDebit2(new BigDecimal(0));
//				initial.setMtCredit2(new BigDecimal(0));
//				initial.setSolde2(new BigDecimal(0));
//				initial.setMtDebit3(new BigDecimal(0));
//				initial.setMtCredit3(new BigDecimal(0));
//				initial.setSolde3(new BigDecimal(0));
				
				session.save(initial);
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(IAction action) {
		in();
		out();
		lectureTxtEpicea();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
