package bwgroup4.dao;

import bwgroup4.entities.Biglietto;
import bwgroup4.entities.DistAuto;
import bwgroup4.entities.PuntoVendita;
import bwgroup4.entities.Venditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BigliettoDAO {
    private EntityManager em;

    public BigliettoDAO(EntityManager e){
        this.em=e;
    }

    public void emettiBiglietto(Venditore v){
        if(v instanceof DistAuto){
            DistAuto dist=(DistAuto) v;
            if(dist.isInServizio()){
                Biglietto b= new Biglietto(dist);
                EntityTransaction tr=em.getTransaction();
                tr.begin();
                em.persist(b);
                tr.commit();

            } else{
                System.out.println("il distributore è fuori servizio");
            }
        } else if( v instanceof PuntoVendita){
            PuntoVendita pV = (PuntoVendita) v;
            Biglietto b=new Biglietto(pV);
            EntityTransaction tr=em.getTransaction();
            tr.begin();
            em.persist(b);
            tr.commit();
        }
    }
}
