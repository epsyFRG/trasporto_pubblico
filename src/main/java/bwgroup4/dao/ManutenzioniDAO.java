package bwgroup4.dao;


import bwgroup4.entities.Manutenzioni;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
        System.out.println("manutenzione registrata");
    }
    public Manutenzioni findById(long id){
        Manutenzioni found = em.find(Manutenzioni.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(long id) {
        Manutenzioni found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }

    public List<Manutenzioni> getManuPerMezzo(long id){
        TypedQuery<Manutenzioni> query = em.createQuery("SELECT m FROM Manutenzioni m WHERE m.mezzi.id = :id", Manutenzioni.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
