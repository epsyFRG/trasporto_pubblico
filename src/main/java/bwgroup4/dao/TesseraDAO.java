package bwgroup4.dao;

import bwgroup4.entities.Tessera;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TesseraDAO {
    private EntityManager em;

    public TesseraDAO(EntityManager e){
        this.em=e;
    }

    public void save (Tessera a){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(a);
        tr.commit();
    }
    public Tessera findById(int id){
        Tessera found = em.find(Tessera.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Tessera found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
}
