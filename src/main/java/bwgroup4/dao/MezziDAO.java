package bwgroup4.dao;

import bwgroup4.entities.Mezzi;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MezziDAO {
    private EntityManager em;

    public MezziDAO(EntityManager e){
        this.em=e;
    }

    public void save (Mezzi a){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(a);
        tr.commit();
    }
    public Mezzi findById(int id){
        Mezzi found = em.find(Mezzi.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Mezzi found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
}
