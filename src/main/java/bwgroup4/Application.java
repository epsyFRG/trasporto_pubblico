package bwgroup4;

import bwgroup4.dao.*;
import bwgroup4.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class Application {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasportopubblico");

    static EntityManager em = emf.createEntityManager();
    static CorseDAO cDao = new CorseDAO(em);
    static MezziDAO mDao = new MezziDAO(em);
    static TrattaDAO tDao = new TrattaDAO(em);
    static PersonaDAO pd = new PersonaDAO(em);
    static TesseraDAO td = new TesseraDAO(em);
    static AbbonamentoDAO abd = new AbbonamentoDAO(em);
    static DistAutoDAO disDao = new DistAutoDAO(em);
    static BigliettoDAO biglDao = new BigliettoDAO(em);
    static VidimazioniDAO viDao = new VidimazioniDAO(em);
    static ManutenzioniDAO manDao = new ManutenzioniDAO(em);
    static VenditoreDAO venDao = new VenditoreDAO(em);

    public static void deletePerson(int idPersona) {
        TypedQuery<Tessera> tesQuery = em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :id", Tessera.class);
        tesQuery.setParameter("id", idPersona);
        List<Tessera> tessList = tesQuery.getResultList();
        if (!tessList.isEmpty()) {
            Tessera tess = tessList.getFirst();
            TypedQuery<Abbonamento> abQuery = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.codice = :codice", Abbonamento.class);
            abQuery.setParameter("codice", tess.getCodice());
            List<Abbonamento> abList = abQuery.getResultList();
            if (!abList.isEmpty()) {
                Abbonamento ab = abList.getFirst();
                abd.remove(ab.getCodiceUnivoco());
            }
            td.remove(tess.getCodice());
        }
        try {
            pd.remove(idPersona);
        } catch (Exception ex){
            System.out.println("id non trovato");
        }
    }

    public static void deleteMezzo(long idMezzo){
        TypedQuery<Manutenzioni> manQuery = em.createQuery("SELECT m FROM Manutenzioni m WHERE m.mezzi.id = :idMezzo", Manutenzioni.class);
        manQuery.setParameter("idMezzo", idMezzo);
        List<Manutenzioni> manList = manQuery.getResultList();
        if(!manList.isEmpty()){
            for(int i=0; i<manList.size(); i++){
                manDao.remove(manList.get(i).getId());
            }
        }
        TypedQuery<Corse> corsQuery =em.createQuery("SELECT c FROM Corse c WHERE c.mezzi.id = :idMezzo", Corse.class );
        corsQuery.setParameter("idMezzo", idMezzo);
        List<Corse>  corsList = corsQuery.getResultList();
        if(!corsList.isEmpty()){
            for(int i=0; i<corsList.size(); i++ ){
                cDao.remove(corsList.get(i).getId());
            }
        }
        TypedQuery<Vidimazioni> vidQuery = em.createQuery("SELECT v FROM Vidimazioni v WHERE v.mezzo.id = :idMezzo", Vidimazioni.class);
        vidQuery.setParameter("idMezzo",idMezzo );
        List<Vidimazioni> vidList = vidQuery.getResultList();
        if(!vidList.isEmpty()){
            for (int i=0; i<vidList.size(); i++){
                viDao.delete(vidList.get(i).getBiglietto());
            }
        }
        try {
            mDao.delete(idMezzo);
        }catch (Exception ex){
            System.out.println("id non trovato");
        }
    }

    public static void deleteTratta(int idTratta) {
        TypedQuery<Corse> corseQuery = em.createQuery("SELECT c FROM Corse c WHERE c.tratta.id = :id", Corse.class);
        corseQuery.setParameter("id", idTratta);
        List<Corse> corseList = corseQuery.getResultList();
        if (!corseList.isEmpty()) {
            for (Corse corsa : corseList) {
                cDao.remove(corsa.getId().intValue());
            }
        }
        try {
            tDao.delete(idTratta);
        }catch (Exception ex){
            System.out.println("id non trovato");
        }
    }

    public static void deleteBiglietto(int codice){
        TypedQuery <Vidimazioni> vidQuery=em.createQuery("SELECT v FROM Vidimazioni v WHERE v.biglietto.codiceUnivoco = :codice", Vidimazioni.class);
        vidQuery.setParameter("codice", codice);
        List<Vidimazioni> vidList = vidQuery.getResultList();
        if(!vidList.isEmpty()){
            Vidimazioni v=vidList.getFirst();
            viDao.delete(v.getBiglietto());
        }
        try{
           biglDao.remove(codice);
        } catch (Exception ex){
            System.out.println("codice non trovato");
        }

    }

    public static void deleteEmittente(int idEmittente){
        TypedQuery<Abbonamento> abQuery=em.createQuery("SELECT a FROM Abbonamento a WHERE a.emittente.id = :idEmittente", Abbonamento.class);
        abQuery.setParameter("idEmittente", idEmittente);
        List<Abbonamento> abList=abQuery.getResultList();
        if(!abList.isEmpty()){
            for(int i=0; i<abList.size(); i++){
                abd.remove(abList.get(i).getCodiceUnivoco());
            }
        }

        TypedQuery<Biglietto> bigQuery=em.createQuery("SELECT b FROM Biglietto b WHERE b.emittente.id = :idEmittente", Biglietto.class);
        bigQuery.setParameter("idEmittente",idEmittente);
        List<Biglietto> bigList=bigQuery.getResultList();
        if(!bigList.isEmpty()){
            for(int i=0; i<bigList.size(); i++){
                deleteBiglietto(bigList.get(i).getCodiceUnivoco());
            }
        }

        try{
            venDao.remove(idEmittente);
        } catch(Exception ex){
            System.out.println("id non trovato");
        }

    }


    public static void main(String[] args) {


//        Tratta tratta1 = new Tratta("Beverino", "Ceparana", 20);
//        tDao.save(tratta1);
//
//        Mezzi mezzo1 = new Mezzi("CIAO", TipoMezzi.AUTOBUS, 30);
//        mDao.save(mezzo1);
//
//        Corse corsa1 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 1, 8, 30),
//                LocalDateTime.of(2025, 10, 1, 9, 15)
//        );
//        cDao.save(corsa1);
//        Corse corsa2 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 1, 10, 0),
//                LocalDateTime.of(2025, 10, 1, 10, 25)
//        );
//        cDao.save(corsa2);
//        Corse corsa3 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 1, 14, 15),
//                LocalDateTime.of(2025, 10, 1, 14, 35)
//        );
//        cDao.save(corsa3);
//        Corse corsa4 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 2, 7, 0),
//                LocalDateTime.of(2025, 10, 2, 8, 10)
//        );
//        cDao.save(corsa4);
//        Corse corsa5 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 2, 16, 45),
//                LocalDateTime.of(2025, 10, 2, 17, 30)
//        );
//        cDao.save(corsa5);
//        Corse corsa6 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 3, 9, 20),
//                LocalDateTime.of(2025, 10, 3, 10, 0)
//        );
//        cDao.save(corsa6);
//        Corse corsa7 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 3, 11, 30),
//                LocalDateTime.of(2025, 10, 3, 12, 45)
//        );
//        cDao.save(corsa7);
//        Corse corsa8 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 4, 8, 0),
//                LocalDateTime.of(2025, 10, 4, 8, 40)
//        );
//        cDao.save(corsa8);
//        Corse corsa9 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 4, 13, 15),
//                LocalDateTime.of(2025, 10, 4, 14, 5)
//        );
//        cDao.save(corsa9);
//        Corse corsa10 = new Corse(
//                mezzo1,
//                tratta1,
//                LocalDateTime.of(2025, 10, 5, 6, 45),
//                LocalDateTime.of(2025, 10, 5, 7, 15)
//        );
//        cDao.save(corsa10);


        










        em.close();
        emf.close();


        System.out.println("Hello World!");
    }
}
