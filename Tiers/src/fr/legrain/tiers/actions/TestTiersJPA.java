package fr.legrain.tiers.actions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.gestCom.Appli.EntityManagerUtil;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCommentaire;
import fr.legrain.tiers.dao.TaCompl;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaLiens;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTBanque;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTEmail;
import fr.legrain.tiers.dao.TaTLiens;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTWeb;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaWeb;

public class TestTiersJPA implements IWorkbenchWindowActionDelegate {

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

			TaTiers taTiers = null;
			int actif = 1;
			int ttc = 0;

			int i = 0;
			while (i<nb) {
				i++;
				System.out.println("taTiers N°"+i);
				taTiers = new TaTiers();
				//taArticle.setIdArticle(i);
				taTiers.setCodeTiers("TIERS_"+i);
				taTiers.setActifTiers(actif);
				taTiers.setCodeCompta("CODE"+i);
				taTiers.setCompte("5400"+i);
				taTiers.setNomTiers("nom_"+i);
				taTiers.setPrenomTiers("prenom_"+i);
				taTiers.setSurnomTiers("surnom_"+i);
				taTiers.setTtcTiers(ttc);
				
				TaTTiers taTTiers = (TaTTiers) em.find(fr.legrain.tiers.dao.TaTTiers.class, 15); //client
				taTiers.setTaTTiers(taTTiers);
				
				TaTCivilite taTCivilite = (TaTCivilite) em.find(fr.legrain.tiers.dao.TaTCivilite.class, 14); //Mr
				taTiers.setTaTCivilite(taTCivilite);
				
				TaCommentaire taCommentaire = new TaCommentaire();
				taCommentaire.setCommentaire("commentaire Numero : "+i);
				taTiers.setTaCommentaire(taCommentaire);
				
				TaCompl taCompl = new TaCompl();
				taCompl.setTvaIComCompl("90000000"+i);
				taCompl.setSiretCompl("55555000"+i);
				taTiers.setTaCompl(taCompl);
				
				TaAdresse taAdresse = new TaAdresse();
				taAdresse.setAdresse1Adresse("adr1_"+i);
				taAdresse.setAdresse2Adresse("adr2_"+i);
				taAdresse.setAdresse3Adresse("adr3_"+i);
				taAdresse.setPaysAdresse("pays_"+i);
				taAdresse.setCodepostalAdresse("82000");
				taAdresse.setVilleAdresse("ville_"+i);
				TaTAdr taTAdr = (TaTAdr) em.find(fr.legrain.tiers.dao.TaTAdr.class, 1); //bureau
				taAdresse.setTaTAdr(taTAdr);
				
				taAdresse.setTaTiers(taTiers);
				taTiers.setTaAdresse(taAdresse);
				
				TaEmail taEmail = new TaEmail();
				taEmail.setAdresseEmail("test"+i+"@exemple.com");
				TaTEmail taTEmail = (TaTEmail) em.find(fr.legrain.tiers.dao.TaTEmail.class, 1); //bureau
				if(taTEmail!=null) taEmail.setTaTEmail(taTEmail);
				taEmail.setTaTiers(taTiers);
				taTiers.setTaEmail(taEmail);

				TaTelephone taTelephone = new TaTelephone();
				taTelephone.setNumeroTelephone("05.63."+i);
				taTelephone.setPosteTelephone("0");
				taTelephone.setContact("TITI");
				TaTTel taTTel = (TaTTel) em.find(fr.legrain.tiers.dao.TaTTel.class,1); //bureau
				taTelephone.setTaTTel(taTTel);
				taTelephone.setTaTiers(taTiers);
				taTiers.setTaTelephone(taTelephone);
				
				TaWeb taWeb = new TaWeb();
				taWeb.setAdresseWeb("http://www.test."+i+".com");
				TaTWeb taTWeb = (TaTWeb) em.find(fr.legrain.tiers.dao.TaTWeb.class, 1); //bureau
				if(taWeb!=null) taWeb.setTaTWeb(taTWeb);
				taWeb.setTaTiers(taTiers);
				taTiers.setTaWeb(taWeb);
				
				TaCompteBanque taCompteBanque = new TaCompteBanque();
				taCompteBanque.setNomBanque("BNP_"+i);
				taCompteBanque.setCompte("compte_"+i);
				taCompteBanque.setCodeBanque("code_"+i);
				taCompteBanque.setCodeGuichet("Code_"+i);
				taCompteBanque.setCleRib("AA");
				taCompteBanque.setAdresse1Banque("Adresse1_"+i);
				taCompteBanque.setAdresse2Banque("Adresse2_"+i);
				taCompteBanque.setVilleBanque("Ville_"+i);
				taCompteBanque.setCpBanque("cp_"+i);
				taCompteBanque.setIban("Iban_"+i);
				taCompteBanque.setCodeBIC("BIC_"+i);
				taCompteBanque.setTitulaire("Titulaire_"+i);
				TaTBanque taTBanque = (TaTBanque) em.find(fr.legrain.tiers.dao.TaTBanque.class, 1); //bureau
				if(taCompteBanque!=null) taCompteBanque.setTaTBanque(taTBanque);
				taCompteBanque.setTaTiers(taTiers);
				taTiers.getTaCompteBanques().add(taCompteBanque);
				
				TaLiens taLiens = new TaLiens();
				taLiens.setAdresseLiens("fichier"+i+".txt");
				TaTLiens taTLiens = (TaTLiens) em.find(fr.legrain.tiers.dao.TaTLiens.class, 1); //bureau
				if(taLiens!=null) taLiens.setTaTLiens(taTLiens);
				taLiens.setTaTiers(taTiers);
				taTiers.getTaLienses().add(taLiens);
				
				
				
				//em.merge(taArticle);
				em.persist(taTiers);
				//s.save(taArticle);

			}
			//System.out.println("save ok (id = "+taTiers.getIdTiers()+")");
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
