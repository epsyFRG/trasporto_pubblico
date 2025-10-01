package bwgroup4.dao;

import bwgroup4.entities.Corse;
import bwgroup4.entities.DistAuto;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CorseDAO {
    private EntityManager em;

    public CorseDAO(EntityManager e){
        this.em=e;
    }

    public void save (Corse c){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(c);
        tr.commit();
    }
    public Corse findById(long id){
        Corse found = em.find(Corse.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }
    public void remove(long id) {
        Corse found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
    public Double findAvgByMezzo (long idMezzo, long idTratta) {
        Query query=em.createQuery("SELECT AVG(c.durataSec) FROM Corse c WHERE c.mezzi.id = :idMezzo AND c.tratta.id = :idTratta");
        query.setParameter("idMezzo",idMezzo);
        query.setParameter("idTratta",idTratta);
        return (Double) query.getSingleResult();
    }

    public int numCorsePerMezzo(int id){
        TypedQuery<Corse> query = em.createQuery("SELECT c FROM Corse c WHERE c.mezzi.id = :id", Corse.class);
        query.setParameter("id", id);
        return query.getResultList().size();

    }
}
