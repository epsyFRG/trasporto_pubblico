package bwgroup4.dao;

import bwgroup4.entities.Abbonamento;
import bwgroup4.entities.DistAuto;
import bwgroup4.entities.Tessera;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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

    public List<Abbonamento> getPerEmitAndPeriodo(int mStart, int mFinish, int anStart, int anFinish, int idVend){
        LocalDate dStart;
        LocalDate dFinish;
        try{
            dStart=LocalDate.of(anStart, mStart, 1);
            YearMonth ym=YearMonth.of(anFinish, mFinish);
            dFinish=ym.atEndOfMonth();
        }catch (DateTimeException ex) {
            System.out.println("data non valida");
            return null;
        }
        String sql="SELECT a FROM Abbonamento a WHERE a.emittente.id = :id AND a.dataEmissione <= :dFinish AND a.dataEmissione >= :dStart";
        TypedQuery<Abbonamento> query= em.createQuery(sql, Abbonamento.class);
        query.setParameter("id", idVend);
        query.setParameter("dFinish", dFinish);
        query.setParameter("dStart", dStart);

        return query.getResultList();

    }

    public Abbonamento getAbByTessera(Tessera tes){
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.codice = :codice ", Abbonamento.class);
        query.setParameter("codice", tes.getCodice());
        return query.getSingleResult();
    }


}
