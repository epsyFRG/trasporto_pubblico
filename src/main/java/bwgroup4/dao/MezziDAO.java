package bwgroup4.dao;

import bwgroup4.entities.Mezzi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MezziDAO {
    private final EntityManager entityManager;

    public MezziDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Mezzi mezzo) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(mezzo);
        transaction.commit();
    }

    public Mezzi findById(Long id) {
        return entityManager.find(Mezzi.class, id);
    }

    public List<Mezzi> findAll() {
        TypedQuery<Mezzi> query = entityManager.createQuery("SELECT m FROM Mezzi m", Mezzi.class);
        return query.getResultList();
    }

    public List<Mezzi> findByTipo(String tipo) {
        TypedQuery<Mezzi> query = entityManager.createQuery(
                "SELECT m FROM Mezzi m WHERE m.tipo = :tipo", Mezzi.class
        );
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }

    public Mezzi findByTarga(String targa) {
        TypedQuery<Mezzi> query = entityManager.createQuery(
                "SELECT m FROM Mezzi m WHERE m.targaPK = :targa", Mezzi.class
        );
        query.setParameter("targa", targa);
        List<Mezzi> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }


    public void update(Mezzi mezzo) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(mezzo);
        transaction.commit();
    }

    public void updateCapienza(Long id, int nuovaCapienza) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery(
                "UPDATE Mezzi m SET m.capienza = :cap WHERE m.id = :id"
        );
        query.setParameter("cap", nuovaCapienza);
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Mezzi found = entityManager.find(Mezzi.class, id);
        if (found != null) {
            entityManager.remove(found);
        }
        transaction.commit();
    }
}
