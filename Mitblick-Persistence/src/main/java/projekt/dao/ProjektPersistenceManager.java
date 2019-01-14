package projekt.dao;

import projekt.entities.Projekt;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProjektPersistenceManager {

    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;

    public Projekt create(@NotNull Projekt projekt) {
        em.persist(projekt);
        em.flush();
        return projekt;
    }

    public void update(Projekt projekt) {
        em.merge(projekt);
    }

    public void delete(Projekt projekt) {
        em.remove(projekt);
    }

    public List<Projekt> getAll() {
        return em.createNamedQuery(Projekt.GET_ALL_PROJEKTS, Projekt.class)
                .getResultList();
    }

    public Optional<Projekt> getById(@NotNull Long id) {
        TypedQuery<Projekt> q = em.createNamedQuery(Projekt.GET_PROJEKT_BY_ID, Projekt.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Projekt> getByName(@NotNull String name) {
        TypedQuery<Projekt> q = em.createNamedQuery(Projekt.GET_PROJEKT_BY_NAME, Projekt.class)
                .setParameter("name", name);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
