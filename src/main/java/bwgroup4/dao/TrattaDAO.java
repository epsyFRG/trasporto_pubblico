package bwgroup4.dao;

import bwgroup4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TrattaDAO {

    private final EntityManager em;

    //Costruttore che riceve EntityManager dall'esterno
    public TrattaDAO(EntityManager em) {
        this.em = em;
    }
    //Salvo una nuova tratta nel database
    public void save(Tratta tratta) {
        em.persist(tratta);
    }
    //Trovo una tratta tramite Id
    public Tratta findById(Long id) {
        return em.find(Tratta.class, id);
    }
    //Restituisce tutte le tratte
    public List<Tratta> findAll() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class)
                .getResultList();
    }
    //Elimina una tratta dal db,
    public void delete(Tratta tratta) {
        if (!em.contains(tratta)) {
            tratta = em.merge(tratta);
        }
        em.remove(tratta);
    }
    //Conta quante volte un mezzo ha percorso una certa tratta
    public long countPercorrenzeByMezzo(Long trattaId, Long mezzoId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Percorrenza p " +
                        "WHERE p.tratta.id = :t AND p.mezzo.id = :m",
                Long.class);
        query.setParameter("t", trattaId);
        query.setParameter("m", mezzoId);
        return query.getSingleResult();
    }
}
