package bwgroup4.dao;

import bwgroup4.entities.PuntoVendita;
import bwgroup4.entities.Venditore;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;

public class VenditoreDAO {
    private EntityManager em;

    public VenditoreDAO(EntityManager e){
        this.em=e;
    }

    public Venditore findById(int id){
        Venditore found = em.find(Venditore.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Venditore found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }


}
