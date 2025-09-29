package bwgroup4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    static EntityManagerFactory emf= Persistence.createEntityManagerFactory("trasportopubblico");
    public static void main(String[] args) {

        EntityManager em=emf.createEntityManager();

        em.close();
        emf.close();


        System.out.println("Hello World!");
    }
}
