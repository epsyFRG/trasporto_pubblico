package bwgroup4;

import bwgroup4.dao.*;
import bwgroup4.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    //1) metodo nuovo abbonamento passando id venditore FATTO
    //2) metodo nuovo biglietto pasando id venditore FATTO
    //3) metodo setScadenza su tessera se dataScadenza < oggi
    //4) nuova vidimazione fornendo codice biglietto e id mezzo FATTO
    //5) fare la tessera se già non ce l'ha FATTO
    //--------------------------------------------
    //admin
    //1) metodo getPerPeriodoAndEmitt su abbonamenti e biglietti (già fatto, da mettere nello switch) FATTO
    //2) verifica abbonamento in base a tessera (già fatto, da mettere nello switch) FATTO
    //3) metodo getmanutenzioni per mezzo (già fatto, da mettere nello switch) FATTO
    //4) metodo getVidimazioni per mezzo per periodo tempo (già fatto, da mettere nello switch) FATTO
    //5) metodo inserisco id tratta e id mezzo e ottengo media tempo effettivo FATTO
    //6) assegnare mezzo a tratta, cioè creare una nuova corsa FATTO

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LocalDate today=LocalDate.now();



        Persona p=pd.findById(2);
        Tessera t= new Tessera(p);
       // td.save(t);
        DistAuto d = new DistAuto("llklkj", true);
        //disDao.save(d);
        Persona admin=new Persona("amministratore","lkjlk",true);
        //pd.save(admin);

        //--------------------------------------------------------------------------------------------------

        String op = "";
        Persona utente = null;
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
                //Lato admin
                if(utente.isAdmin()){
                    System.out.println("Amministratore");
                    System.out.println("Inserire 1 per visualizzare biglietti e/o abbonamenti ");
                    System.out.println("Inserire 2 per verificare un abbonamento ");
                    System.out.println("Inserire 3 per visualizzare le manutenzioni dei mezzi ");
                    System.out.println("Inserire 4 per visualizzare i biglietti vidimati sui mezzi ");
                    System.out.println("Inserire 5 per visualizzare la durata media delle corse ");
                    System.out.println("Inserire 6 per assegnare un mezzo ad una tratta (nuova corsa) ");
                    System.out.println("Inserire q per uscire");

                    op= scanner.nextLine();
                    switch(op){
                        case "1":
                            //metodo getPerPeriodoAndEmitt su abbonamenti e biglietti (già fatto, da mettere nello switch)
                            String strIdEm="";
                            int idEm=0;
                            int anStart=0, anFinish=0, mStart=0, mFinish=0;
                            System.out.println("Abbonamenti e biglietti emessi");
                            System.out.println("inserire l'id dell'emittente (punto vendita o distributore)");
                            strIdEm=scanner.nextLine();
                            try{
                                idEm=Integer.parseInt(strIdEm);
                                Venditore ven=venDao.findById(idEm);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data inizio ricerca");
                            System.out.println("inserire l'anno (intero)");
                            try{
                                anStart= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data inizio ricerca");
                            System.out.println("inserire il mese (intero da 1 a 12)");
                            try{
                                mStart= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data fine ricerca");
                            System.out.println("inserire l'anno (intero)");
                            try{
                                anFinish= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data fine ricerca");
                            System.out.println("inserire il mese (intero da 1 a 12)");
                            try{
                                mFinish= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("Abbonamenti:");
                            try{
                                List<Abbonamento> abl=abd.getPerEmitAndPeriodo(mStart,mFinish,anStart,anFinish,idEm);
                                if(abl.isEmpty()){
                                    throw new RuntimeException();
                                }
                            System.out.println(abl);}
                            catch (Exception ex){
                                System.out.println("non ci sono risultati per gli abbonamenti");
                            }
                            System.out.println("Biglietti:");
                            try{
                                List<Biglietto> bl=biglDao.getPerPeriodoAndEmitt(mStart,anStart,mFinish,anFinish,idEm);
                                if(bl.isEmpty()){
                                    throw new RuntimeException();
                                }
                            System.out.println(bl);}
                            catch (Exception ex){
                                System.out.println("non ci sono risultati per i biglietti");
                            }
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
                            int aStart=0, aFinish=0, meStart=0, meFinish=0;
                            System.out.println("Ricerca vidimazioni");
                            System.out.println("data inizio ricerca");
                            System.out.println("inserire l'anno (intero)");
                            try{
                                aStart= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data inizio ricerca");
                            System.out.println("inserire il mese (intero da 1 a 12)");
                            try{
                                meStart= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data fine ricerca");
                            System.out.println("inserire l'anno (intero)");
                            try{
                                aFinish= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("data fine ricerca");
                            System.out.println("inserire il mese (intero da 1 a 12)");
                            try{
                                meFinish= Integer.parseInt(scanner.nextLine());
                            } catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            List<Vidimazioni> vidiList=null;
                            try {
                                 vidiList=viDao.getVidsPerPeriodo(meStart, aStart, meFinish, aFinish);
                                System.out.println(vidiList);
                            }catch (Exception ex){
                                System.out.println("non ci sono vidimazioni per i criteri isìnseriti");
                                break;
                            }
                            String yN="";
                            System.out.println("vuoi restringere la ricerca alle vidimazioni di un singolo mezzo? ( y / n )");
                            System.out.println("y = si");
                            System.out.println("n = no");
                            yN=scanner.nextLine();
                            if(!yN.equals("y")){
                                break;
                            }
                            long idMezz=0;
                            Mezzi mezz=null;
                            System.out.println("inserire l'id del mezzo");
                            try{
                                idMezz= Long.parseLong(scanner.nextLine());
                                mezz=mDao.findById(idMezz);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            ArrayList<Vidimazioni> vidMezList=new ArrayList<Vidimazioni>();
                            for (int i=0; i<vidiList.size(); i++){
                                if(Objects.equals(vidiList.get(i).getMezzo().getId(), mezz.getId())){
                                    vidMezList.add(vidiList.get(i));
                                }
                            }
                            if(!vidMezList.isEmpty()){
                                System.out.println(vidMezList);
                            } else{
                                System.out.println("non ci sono vidimazioni per il mezzo inserito");
                            }
                            break;
                        case "5":
                            long idTratta=0;
                            long idMez=0;
                            System.out.println("Durata media effettiva corse");
                            System.out.println("Inserire l'id della tratta");
                            try{
                                idTratta= Long.parseLong(scanner.nextLine());
                                Tratta trattaProva=tDao.findById(idTratta);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("Inserire l'id del mezzo");
                            try {
                                idMez= Long.parseLong(scanner.nextLine());
                                Mezzi me=mDao.findById(idMez);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            try {
                                System.out.println("media in secondi della durata di percorrenza:");
                                if(cDao.findAvgByMezzo(idMez,idTratta)==null){
                                    throw new RuntimeException();
                                }
                                System.out.println(cDao.findAvgByMezzo(idMez,idTratta));
                            }catch (Exception ex){
                                System.out.println("non ci sono risultati per i parametri inseriti");
                            }
                            break;
                        case "6":
                            Mezzi m=null;
                            Tratta tratta=null;
                            long trId=0, idM=0;

                            System.out.println("Nuova corsa");
                            System.out.println("inserire l'id della tratta");
                            try{
                                trId= Long.parseLong(scanner.nextLine());
                                tratta=tDao.findById(trId);
                            }catch(Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("inserire l'id del mezzo da assegnare");
                            try{
                                idM= Long.parseLong(scanner.nextLine());
                                m=mDao.findById(idM);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            //----------check se il mezzo è in manutenzione---------
                            List<Manutenzioni> manList=manDao.getManuPerMezzo(idM);
                            LocalDateTime todayDT= LocalDateTime.now();
                            if(!manList.isEmpty()){
                                for(int i=0; i<manList.size(); i++){
                                    if(manList.get(i).getdatafine()==null || manList.get(i).getdatafine().isAfter(todayDT)){
                                        System.out.println("il mezzo selezionato è in manutenzione");
                                        break;
                                    }
                                }
                            }
                            //-------------------------------------------------------
                            long durata=0;
                            System.out.println("inserire la durata in minuti (intero maggiore di 0)");
                            try {
                                durata= Long.parseLong(scanner.nextLine());
                                if(durata <=0){
                                    throw new RuntimeException();
                                }
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            LocalDateTime now=LocalDateTime.now();
                            LocalDateTime after = now.plusMinutes(durata);
                            Corse cor=new Corse(m,tratta,now,after);
                            cDao.save(cor);
                            break;

                        case "q": break;
                        default:
                            System.out.println("input non valido");
                            break;
                    }
                    if(op.equals("q")){
                        break;
                    }

                }
                //Lato utente
                else {
                    op = "";
                    System.out.println("Utente");
                    System.out.println("Inserire 1 per nuovo abbonamento ");
                    System.out.println("Inserire 2 per biglietto ");
                    System.out.println("Inserire 3 per rinnovare la tessera");
                    System.out.println("Inserire 4 per vidimare il biglietto");
                    System.out.println("Inserire 5 per fare la tessera");
                    System.out.println("Inserire q per uscire");
                    op=scanner.nextLine();

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
                                if(a.getDataScadenza().isAfter(today)){
                                ok=false;
                                System.out.println("hai già un abbonamento");}
                                else {
                                    abd.remove(a.getCodiceUnivoco());
                                    ok=true;
                                }
                            }catch (Exception ex){
                                ok=true;
                            }
                            if(ok){
                            System.out.println("Nuovo abbonamento");
                            System.out.println("inserire l'id dell' emittente ");
                            try {
                                idEm = Integer.parseInt(scanner.nextLine());
                                v = venDao.findById(idEm);
                            }catch(Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("inserire 1 per abbonamento Mansile");
                            System.out.println("inserire 2 per abbonamento Settimanale");
                            mens=scanner.nextLine();
                            if(mens.equals("1")){
                                isMens=true;
                            }else if(mens.equals("2")){
                                isMens=false;
                            } else{
                                System.out.println("input non valido");
                                break;
                            }
                            Abbonamento ab= new Abbonamento(v,tess,isMens);
                            abd.save(ab);
                                System.out.println("abbonamento salvato correttamente");
                            }
                            break;
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
                            System.out.println("-----");
                            break;
                        case "4":
                            System.out.println("Vidimazione biglietto");
                            int codBiglietto=0;
                            long meId=0;
                            Biglietto big=null;
                            Mezzi mezzo =null;
                            System.out.println("inserire il codice del biglietto (numero intero)");
                            try{
                                codBiglietto= Integer.parseInt(scanner.nextLine());
                                big=biglDao.findById(codBiglietto);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            System.out.println("inserire l'id del mezzo (numero intero)");
                            try{
                                meId= Long.parseLong(scanner.nextLine());
                                mezzo=mDao.findById(meId);
                            }catch (Exception ex){
                                System.out.println("input non valido");
                                break;
                            }
                            Vidimazioni newVid = new Vidimazioni(mezzo, big);
                            viDao.save(newVid);
                            break;
                        case "5":
                            try {
                                Tessera ts=pd.getTesseraById(utente.getId());
                                if(ts!=null){
                                    System.out.println("hai già la tessera");
                                }
                            }catch (Exception ex){
                                Tessera newTess= new Tessera(utente);
                                td.save(newTess);
                            }

                            break;
                        case "q": break;
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


        System.out.println("Bye");
    }
}
