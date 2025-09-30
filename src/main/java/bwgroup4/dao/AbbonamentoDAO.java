package bwgroup4.dao;

import bwgroup4.entities.Abbonamento;
import bwgroup4.entities.DistAuto;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AbbonamentoDAO {
    private EntityManager em;

    public AbbonamentoDAO(EntityManager e){
        this.em=e;
    }

    public void save (Abbonamento a){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(a);
        tr.commit();
    }
    public Abbonamento findById(int id){
        Abbonamento found = em.find(Abbonamento.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Abbonamento found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }


}
