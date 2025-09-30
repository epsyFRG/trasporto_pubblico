package bwgroup4.dao;


import bwgroup4.entities.Manutenzioni;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ManutenzioniDAO {
    private EntityManager em;

    public ManutenzioniDAO(EntityManager e){
        this.em=e;
    }

    public void save (Manutenzioni m){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(m);
        tr.commit();
    }
    public Manutenzioni findById(int id){
        Manutenzioni found = em.find(Manutenzioni.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Manutenzioni found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
}
