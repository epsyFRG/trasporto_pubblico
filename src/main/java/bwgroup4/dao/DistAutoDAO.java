package bwgroup4.dao;

import bwgroup4.entities.DistAuto;
import bwgroup4.entities.Venditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import javax.swing.text.html.parser.Entity;

public class DistAutoDAO {
    private EntityManager em;

    public DistAutoDAO(EntityManager e){
        this.em=e;
    }

    public void nuovoDist(DistAuto d){

        EntityTransaction tr=em.getTransaction();
        tr.begin();
        em.persist(d);
        tr.commit();
    }

}
