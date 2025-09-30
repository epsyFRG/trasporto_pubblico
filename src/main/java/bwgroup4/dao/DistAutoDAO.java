package bwgroup4.dao;

import bwgroup4.entities.DistAuto;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DistAutoDAO {
    private EntityManager em;

    public DistAutoDAO(EntityManager e){
        this.em=e;
    }

    public void save(DistAuto d){

        EntityTransaction tr=em.getTransaction();
        tr.begin();
        em.persist(d);
        tr.commit();
    }

    public DistAuto findById(int id) {
        DistAuto found = em.find(DistAuto.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        DistAuto found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }

}
