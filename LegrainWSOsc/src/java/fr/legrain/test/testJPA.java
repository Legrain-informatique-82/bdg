/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.test;

import fr.legrain.classDB.WsTaAcces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lee
 */
public class testJPA {

     //public void persist(WsTaAcces wta,WsTaNombreConnect wtnc){
     public void persist(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LegrainWSOscPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //em.persist(wta);
        em.getTransaction().commit();
        em.close();

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        WsTaAcces wta = new WsTaAcces();
        //WsTaNombreConnect wtnc = new WsTaNombreConnect();
        testJPA m = new testJPA();

        //m.persist(wta,wtnc);
        m.persist();
    }

}
