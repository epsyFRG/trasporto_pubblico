package bwgroup4;

import bwgroup4.dao.*;
import bwgroup4.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Application {

    static EntityManagerFactory emf= Persistence.createEntityManagerFactory("trasportopubblico");




    public static void main(String[] args) {


        EntityManager em=emf.createEntityManager();
        CorseDAO cDao = new CorseDAO(em);
        MezziDAO mDao = new MezziDAO(em);
        TrattaDAO tDao = new TrattaDAO(em);

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



        PersonaDAO pd= new PersonaDAO(em);
        TesseraDAO td= new TesseraDAO(em);
        AbbonamentoDAO abd=new AbbonamentoDAO(em);
        DistAutoDAO disDao= new DistAutoDAO(em);
        BigliettoDAO biglDao= new BigliettoDAO(em);
        VidimazioniDAO viDao= new VidimazioniDAO(em);
        ManutenzioniDAO manDao=new ManutenzioniDAO(em);

        Persona per1 = new Persona("pippo", "lkjk",false);
        Persona per2 = new Persona("dfgdfg", "lkjk",false);
        pd.save(per2);
        Persona perFromDb= pd.findById(1);
        Tessera tes2 = new Tessera(perFromDb);
        //td.save(tes1);
        Tessera tesFromDb=td.findById(2);
        DistAuto dist1= new DistAuto("ditributore1",true);
        //disDao.save(dist1);
        DistAuto distFromDb = disDao.findById(152);

        Abbonamento ab1= new Abbonamento(distFromDb, tesFromDb,true);
        //abd.save(ab1);

        Biglietto bigl1= new Biglietto(distFromDb);
        //biglDao.save(bigl1);
        Biglietto biglFromDb=biglDao.findById(1);

        Mezzi mezzo1 = new Mezzi("CIAO", TipoMezzi.AUTOBUS, 30);
       // mDao.save(mezzo1);
        Mezzi mezFromDb=mDao.findById(1);

        Vidimazioni v1= new Vidimazioni(mezFromDb,biglFromDb);
        //viDao.save(v1);

        Manutenzioni man1 = new Manutenzioni("cambio olio", LocalDateTime.of(2025, 2,1, 0,0), LocalDateTime.of(2025, 3, 1, 0,0), mezFromDb);
        //manDao.save(man1);

        Tratta tratta1 = new Tratta("Beverino", "Ceparana", 20);
        //tDao.save(tratta1);
        Tratta trFromDb=tDao.findById(1);

        Corse corsa1 = new Corse( mezFromDb, trFromDb,
                LocalDateTime.of(2025, 10, 1, 8, 30),
                LocalDateTime.of(2025, 10, 1, 9, 15)
        );
       // cDao.save(corsa1);
        Corse corsa2 = new Corse(
                mezFromDb,
                trFromDb,
                LocalDateTime.of(2025, 10, 1, 10, 0),
                LocalDateTime.of(2025, 10, 1, 10, 25)
        );
        //cDao.save(corsa2);

        System.out.println(biglDao.getPerPeriodoAndEmitt(3000, 2025,10,2025,152));













        em.close();
        emf.close();


        System.out.println("Hello World!");
    }
}
