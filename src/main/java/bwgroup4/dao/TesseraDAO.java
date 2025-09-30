package bwgroup4.dao;

import bwgroup4.entities.Abbonamento;
import bwgroup4.entities.Tessera;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;

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

    public boolean verificaAbb(int codiceTessera){
        Tessera tess=this.findById(codiceTessera);
        String sql="SELECT a FROM Abbonamento a WHERE a.tessera = :tess";
        TypedQuery<Abbonamento> query =em.createQuery(sql, Abbonamento.class);
        query.setParameter("tess", tess);
        Abbonamento ab=query.getSingleResult();
        LocalDate today=LocalDate.now();
        if(tess.getDataScadenza().isBefore(today)){
            System.out.println("tessera scaduta");
            return false;
        }
        if(ab.getDataScadenza().isBefore(today)){
            System.out.println("l'abbpnamento è scaduto");
            return false;
        } else{
            System.out.println("abbonamento e tessera in regola");
            return true;
        }

    }

}
