package bwgroup4;

import bwgroup4.dao.BigliettoDAO;
import bwgroup4.dao.DistAutoDAO;
import bwgroup4.entities.DistAuto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    static EntityManagerFactory emf= Persistence.createEntityManagerFactory("trasportopubblico");
    public static void main(String[] args) {

        EntityManager em=emf.createEntityManager();

        DistAutoDAO dD =new DistAutoDAO(em);

        DistAuto dist=new DistAuto();
        dD.nuovoDist(dist);


        BigliettoDAO bD=new BigliettoDAO(em);



        em.close();
        emf.close();


        System.out.println("Hello World!");
    }
}
