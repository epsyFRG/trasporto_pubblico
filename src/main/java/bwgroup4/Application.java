package bwgroup4;

import bwgroup4.dao.*;
import bwgroup4.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

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
        } catch (Exception ex) {
            System.out.println("id non trovato");
        }
    }

    public static void deleteMezzo(long idMezzo) {
        TypedQuery<Manutenzioni> manQuery = em.createQuery("SELECT m FROM Manutenzioni m WHERE m.mezzi.id = :idMezzo", Manutenzioni.class);
        manQuery.setParameter("idMezzo", idMezzo);
        List<Manutenzioni> manList = manQuery.getResultList();
        if (!manList.isEmpty()) {
            for (int i = 0; i < manList.size(); i++) {
                manDao.remove(manList.get(i).getId());
            }
        }
        TypedQuery<Corse> corsQuery = em.createQuery("SELECT c FROM Corse c WHERE c.mezzi.id = :idMezzo", Corse.class);
        corsQuery.setParameter("idMezzo", idMezzo);
        List<Corse> corsList = corsQuery.getResultList();
        if (!corsList.isEmpty()) {
            for (int i = 0; i < corsList.size(); i++) {
                cDao.remove(corsList.get(i).getId());
            }
        }
        TypedQuery<Vidimazioni> vidQuery = em.createQuery("SELECT v FROM Vidimazioni v WHERE v.mezzo.id = :idMezzo", Vidimazioni.class);
        vidQuery.setParameter("idMezzo", idMezzo);
        List<Vidimazioni> vidList = vidQuery.getResultList();
        if (!vidList.isEmpty()) {
            for (int i = 0; i < vidList.size(); i++) {
                viDao.delete(vidList.get(i).getBiglietto());
            }
        }
        try {
            mDao.delete(idMezzo);
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            System.out.println("id non trovato");
        }
    }

    public static void deleteBiglietto(int codice) {
        TypedQuery<Vidimazioni> vidQuery = em.createQuery("SELECT v FROM Vidimazioni v WHERE v.biglietto.codiceUnivoco = :codice", Vidimazioni.class);
        vidQuery.setParameter("codice", codice);
        List<Vidimazioni> vidList = vidQuery.getResultList();
        if (!vidList.isEmpty()) {
            Vidimazioni v = vidList.getFirst();
            viDao.delete(v.getBiglietto());
        }
        try {
            biglDao.remove(codice);
        } catch (Exception ex) {
            System.out.println("codice non trovato");
        }

    }

    public static void deleteEmittente(int idEmittente) {
        TypedQuery<Abbonamento> abQuery = em.createQuery("SELECT a FROM Abbonamento a WHERE a.emittente.id = :idEmittente", Abbonamento.class);
        abQuery.setParameter("idEmittente", idEmittente);
        List<Abbonamento> abList = abQuery.getResultList();
        if (!abList.isEmpty()) {
            for (int i = 0; i < abList.size(); i++) {
                abd.remove(abList.get(i).getCodiceUnivoco());
            }
        }
        TypedQuery<Biglietto> bigQuery = em.createQuery("SELECT b FROM Biglietto b WHERE b.emittente.id = :idEmittente", Biglietto.class);
        bigQuery.setParameter("idEmittente", idEmittente);
        List<Biglietto> bigList = bigQuery.getResultList();
        if (!bigList.isEmpty()) {
            for (int i = 0; i < bigList.size(); i++) {
                deleteBiglietto(bigList.get(i).getCodiceUnivoco());
            }
        }
        try {
            venDao.remove(idEmittente);
        } catch (Exception ex) {
            System.out.println("id non trovato");
        }

    }

    //1) metodo getIsAdmin in base a id persona
    //-------------------------------------------
    // utente
    //1) metodo nuovo abbonamento passando id venditore
    //2) metodo nuovo biglietto pasando id venditore
    //3) metodo setScadenza su tessera se dataScadenza > oggi
    //4) nuova vidimazione fornendo codice biglietto e id mezzo
    //--------------------------------------------
    //admin
    //1) metodo getPerPeriodoAndEmitt su abbonamenti e biglietti (già fatto, da mettere nello switch)
    //2) verifica abbonamento in base a tessera (già fatto, da mettere nello switch)
    //3) metodo getmanutenzioni per mezzo (già fatto, da mettere nello switch)
    //4) metodo getVidimazioni per mezzo per periodo tempo (già fatto, da mettere nello switch)

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
        Scanner scanner = new Scanner(System.in);

        String op = "";
        Persona utente = null;

        Persona p=pd.findById(2);
        Tessera t= new Tessera(p);
       // td.save(t);
        DistAuto d = new DistAuto("llklkj", true);
        //disDao.save(d);


        while(true){
            int ch=0;
            if(utente==null){
            System.out.println("Login utente");
            System.out.println("inserire id utente, oppure q per uscire");
            op=scanner.nextLine();
            if(op.equals("q")){
                break;
            }
            try{
                ch=Integer.parseInt(op);
                 utente=pd.findById(ch);

            }catch(Exception ex){
                System.out.println("input non valido");
            }}
            if(utente!=null){
                op="";
                if(utente.isAdmin()){
                    System.out.println("Amministratore");
                    System.out.println("Inserire 1 per visualizzare biglietti e/o abbonamenti ");
                    System.out.println("Inserire 2 per verificare un abbonamento ");
                    System.out.println("Inserire 3 per visualizzare le manutenzioni dei mezzi ");
                    System.out.println("Inserire 4 per visualizzare i biglietti vidimati sui mezzi ");
                    System.out.println("Inserire q per uscire");

                    op= scanner.nextLine();
                    switch(op){
                        case "1":
                            System.out.println("-----");
                            break;
                        case "2": {
                            try {
                                System.out.print("Abbonamento: ");
                                String codice = scanner.nextLine().trim();
                                LocalDate oggi = java.time.LocalDate.now();
                                Long c = em.createQuery(
                                                "SELECT COUNT(a) FROM Abbonamento a " + "WHERE a.abbonamento.codice = :c AND :oggi BETWEEN a.dataInizio AND a.dataFine",
                                                Long.class)
                                        .setParameter("c", codice)
                                        .setParameter("oggi", oggi)
                                        .getSingleResult();
                                System.out.println(c != null && c > 0 ? "Abbonamento valido" : "Abbonamento non valido");
                            } catch (Exception ex) {
                                System.out.println("Errore verifica: " + ex.getMessage());
                            }
                        }
                        break;
                        case "3":
                            System.out.print("inserisci id del mezzo: ");
                            String inputMezzo = scanner.nextLine();
                            try {
                                long idMezzo = Long.parseLong(inputMezzo);
                                List<Manutenzioni> manutenzioni = manDao.getManuPerMezzo(idMezzo);

                                if (manutenzioni.isEmpty()) {
                                    System.out.println("nessuna manutenzione trovata per questo mezzo");
                                } else {
                                    System.out.println("\nmanutenzioni mezzo id " + idMezzo);
                                    for (Manutenzioni m : manutenzioni) {
                                        System.out.println(m);
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("id non valido");
                            }
                            break;
                        case "4":
                            System.out.println("-----");
                            break;
                        default:
                            System.out.println("input non valido");
                            break;
                    }
                    if(op.equals("q")){
                        break;
                    }

                } else {
                    op = "";
                    System.out.println("Utente");
                    System.out.println("Inserire 1 per nuovo abbonamento ");
                    System.out.println("Inserire 2 per biglietto ");
                    ;
                    System.out.println("Inserire 3 per rinnovare la tessera");
                    System.out.println("Inserire 4 per vidimare il biglietto");
                    switch (op) {
                        case "1":
                            int idEm=0;
                            String mens="";
                            boolean isMens=true;
                            Venditore v=null;
                            Tessera tess=null;
                            boolean ok=true;
                            try{
                                 tess=pd.getTesseraById(utente.getId());
                            }catch (Exception ex){
                                System.out.println("non hai la tessera, non puoi fare l'abbonamento");
                                break;
                            }

                            try{
                                Abbonamento a=abd.getAbByTessera(tess);
                                ok=false;
                            }catch (Exception ex){
                                ok=true;
                            }
                            if(ok){
                            System.out.println("Nuovo abbonamento");
                            System.out.print("Inserisci l'id del venditore: ");
                            int idVenditore;
                            Venditore venditore;
                            try {
                                idVenditore = Integer.parseInt(scanner.nextLine().trim());
                                venditore = venDao.findById(idVenditore);
                                if (venditore == null) {
                                    System.out.println("Venditore non trovato.");
                                    break;
                                }
                            } catch (Exception ex) {
                                System.out.println("Input non valido per l'id venditore");
                                break;
                            }
                            System.out.print("Abbonamento mensile (1) o settimanale (2)? ");
                            String tipo = scanner.nextLine().trim();
                            boolean mensile;
                            if (tipo.equals("1")) {
                                mensile = true;
                            } else if (tipo.equals("2")) {
                                mensile = false;
                            } else {
                                System.out.println("Input non valido per il tipo di abbonamento");
                                break;
                            }
                            try {
                                Abbonamento abbonamento = new Abbonamento(venditore, tess, mensile);
                                abd.save(abbonamento);
                                System.out.println("Abbonamento creato con successo!");
                            } catch (Exception ex) {
                                System.out.println("Errore nella creazione dell'abbonamento: " + ex.getMessage());
                            }
                            break;
                        }
                        case "2": {
                            System.out.print("ID venditore : ");
                            try {
                                int idVend = Integer.parseInt(scanner.nextLine().trim());
                                Venditore vend = em.find(Venditore.class, idVend);
                                if (vend == null) {
                                    System.out.println("Venditore non trovato.");
                                    break;
                                }
                                Biglietto b = new Biglietto(vend);

                                em.getTransaction().begin();
                                em.persist(b);
                                em.getTransaction().commit();

                                System.out.println("Biglietto emesso: " + b.getCodiceUnivoco());
                            } catch (Exception ex) {
                                try {
                                    if (em.getTransaction().isActive()) em.getTransaction().rollback();
                                } catch (Exception ignore) {
                                }
                                System.out.println("Errore emissione biglietto: " + ex.getMessage());
                            }
                            break;
                        }
                        case "3":
                            try {
                                TypedQuery<Tessera> tesQuery = em.createQuery(
                                        "SELECT t FROM Tessera t WHERE t.utente.id = :id",
                                        Tessera.class
                                );
                                tesQuery.setParameter("id", utente.getId());
                                List<Tessera> tessere = tesQuery.getResultList();

                                if (tessere.isEmpty()) {
                                    System.out.println("nessuna tessera trovata per questo utente");
                                } else {
                                    Tessera tessera = tessere.get(0);
                                    LocalDate oggi = LocalDate.now();
                                    LocalDate scadenza = tessera.getDataScadenza();

                                    if (scadenza.isBefore(oggi)) {
                                        tessera.setDataScadenza(oggi.plusYears(1));

                                        em.getTransaction().begin();
                                        em.merge(tessera);
                                        em.getTransaction().commit();

                                        System.out.println("tessera rinnovata, uova scadenza: " + tessera.getDataScadenza());
                                    } else {
                                        System.out.println("la tessera è ancora valida fino al " + scadenza);
                                    }
                                }
                            } catch (Exception ex) {
                                if (em.getTransaction().isActive()) {
                                    em.getTransaction().rollback();
                                }
                                System.out.println("errore nel rinnovo tessera: " + ex.getMessage());
                            }
                            break;
                        case "4": {
                            // Nuova vidimazione fornendo codice biglietto e id mezzo
                            try {
                                System.out.print("Inserisci il codice del biglietto: ");
                                int codiceBiglietto = Integer.parseInt(scanner.nextLine().trim());
                                Biglietto biglietto = biglDao.findById(codiceBiglietto);
                                if (biglietto == null) {
                                    System.out.println("Biglietto non trovato.");
                                    break;
                                }
                                // Controllo se già vidimato
                                try {
                                    viDao.findByBiglietto(biglietto);
                                    System.out.println("Biglietto già vidimato!");
                                    break;
                                } catch (Exception ex) {
                                    // Se non trovato, si può procedere
                                }
                                System.out.print("Inserisci l'id del mezzo: ");
                                long idMezzo = Long.parseLong(scanner.nextLine().trim());
                                Mezzi mezzo = mDao.findById((int) idMezzo);
                                if (mezzo == null) {
                                    System.out.println("Mezzo non trovato.");
                                    break;
                                }
                                Vidimazioni vid = new Vidimazioni(mezzo, biglietto);
                                viDao.save(vid);
                                System.out.println("Vidimazione effettuata con successo!");
                            } catch (Exception ex) {
                                System.out.println("Errore durante la vidimazione: " + ex.getMessage());
                            }
                            break;
                        }
                        default:
                            System.out.println("input non valido");
                            break;

                    }
                    if(op.equals("q")){
                        break;
                    }

                }
            }


        }


        scanner.close();
        em.close();
        emf.close();


        System.out.println("Hello World!");
    }
}
