package bwgroup4.dao;

import bwgroup4.entities.Vidimazioni;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VidimazioniDAO {
    private EntityManager em;

    public VidimazioniDAO(EntityManager e){
        this.em=e;
    }

    public void save (Vidimazioni a){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(a);
        tr.commit();
    }
    public Vidimazioni findById(int id){
        Vidimazioni found = em.find(Vidimazioni.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Vidimazioni found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
}
