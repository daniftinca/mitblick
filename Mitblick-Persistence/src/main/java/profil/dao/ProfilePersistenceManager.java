package profil.dao;

import interfaces.DAO;
import profil.entities.Profile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class ProfilePersistenceManager implements DAO<Profile> {

    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;

    @Override
    public Profile create(@NotNull Profile profile) {
        em.persist(profile);
        em.flush();
        return profile;
    }

    @Override
    public void update(Profile profile) {
        em.merge(profile);
    }

    @Override
    public void delete(Profile profile) {
        em.remove(profile);
    }

    @Override
    public List<Profile> getAll() {
        return em.createNamedQuery(Profile.GET_ALL_PROFILES, Profile.class)
                .getResultList();
    }

    @Override
    public Optional<Profile> getById(Long id) {
        TypedQuery<Profile> q = em.createNamedQuery(Profile.GET_PROFILE_BY_ID, Profile.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Profile> getByName(@NotNull String name) {
        TypedQuery<Profile> q = em.createNamedQuery(Profile.GET_PROFILE_BY_NAME, Profile.class)
                .setParameter("name", name);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Profile> getByEmail(@NotNull String email) {
        TypedQuery<Profile> q = em.createNamedQuery(Profile.GET_PROFILE_BY_EMAIL, Profile.class)
                .setParameter("email", email);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
