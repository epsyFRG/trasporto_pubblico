package bwgroup4.dao;

import bwgroup4.entities.Persona;
import bwgroup4.entities.Tessera;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PersonaDAO {
    private EntityManager em;

    public PersonaDAO(EntityManager e){
        this.em=e;
    }

    public void save (Persona a){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(a);
        tr.commit();
    }
    public Persona findById(int id){
        Persona found = em.find(Persona.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Persona found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }

    public Tessera getTesseraById(int id){
        TypedQuery<Tessera> query=em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :id", Tessera.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
