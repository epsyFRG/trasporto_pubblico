package bwgroup4.dao;

import bwgroup4.entities.Biglietto;
import bwgroup4.entities.DistAuto;
import bwgroup4.entities.PuntoVendita;
import bwgroup4.entities.Venditore;
import exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BigliettoDAO {
    private EntityManager em;

    public BigliettoDAO(EntityManager e){
        this.em=e;
    }

    public void save (Biglietto b){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(b);
        tr.commit();
    }
    public Biglietto findById(int id){
        Biglietto found = em.find(Biglietto.class, id);
        if (found == null) throw new NotFoundException(id);
        return found;
    }

    public void remove(int id) {
        Biglietto found = this.findById(id);
        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();
    }

    public void emettiBiglietto(Venditore v){
        if(v instanceof DistAuto){
            DistAuto dist=(DistAuto) v;
            if(dist.isInServizio()){
                Biglietto b= new Biglietto(dist);
                this.save(b);

            } else{
                System.out.println("il distributore è fuori servizio");
            }
        } else if( v instanceof PuntoVendita){
            PuntoVendita pV = (PuntoVendita) v;
            Biglietto b=new Biglietto(pV);
            this.save(b);
        }
    }

    public List<Biglietto> getPerPeriodoAndEmitt(int mStart, int anStart, int mFinish, int anFinish, int idEmitt){
        LocalDate dStart = null;
        LocalDate dFinish = null;
        try{
            dStart=LocalDate.of(anStart,mStart, 1);
            YearMonth ym=YearMonth.of(anFinish, mFinish);
            dFinish=ym.atEndOfMonth();
        } catch(DateTimeException ex){
            System.out.println("data non valida");
            return null;
        }
        String sql="SELECT b FROM Biglietto b WHERE b.emittente.id = :id AND b.dataEmissione >= :dStart AND b.dataEmissione <= :dFinish";
        TypedQuery<Biglietto> query=em.createQuery(sql, Biglietto.class);
        query.setParameter("id",idEmitt );
        query.setParameter("dStart",dStart );
        query.setParameter("dFinish",dFinish );
        return query.getResultList();
    }
}
