package bwgroup4.dao;

import bwgroup4.entities.Vidimazioni;
import exceptions.NotFoundException;
import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;


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

    public void delete(int id) {
        Vidimazioni found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }
    
    public List<Vidimazioni> getVidsPerPeriodo(int mStart, int annoStart, int mFinish, int annoFinish) {

        LocalDate dStart;
        LocalDate dFinish;
        try {
            dStart = LocalDate.of(annoStart, mStart, 1);
            YearMonth ym=YearMonth.of(annoFinish, mFinish);
            dFinish = ym.atEndOfMonth();
        } catch (DateTimeException ex) {
            System.out.println("data non valida");
            return null;
        }
        String sql = "SELECT v FROM Vidimazioni v WHERE v.dataVidimazione >= :dStart AND v.dataVidimazione <= :dFinish";
        TypedQuery<Vidimazioni> query = em.createQuery(sql, Vidimazioni.class);
        query.setParameter("dStart", dStart);
        query.setParameter("dFinish", dFinish);
        return query.getResultList();

    }
}
