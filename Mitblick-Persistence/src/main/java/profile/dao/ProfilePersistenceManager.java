package profile.dao;

import profile.entities.Profile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProfilePersistenceManager {

    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;


    public Profile create(@NotNull Profile profile) {
        em.persist(profile);
        em.flush();
        return profile;
    }


    public void update(Profile profile) {
        em.merge(profile);
    }

    public List<Profile> getAll() {
        return em.createNamedQuery(Profile.GET_ALL_PROFILES, Profile.class)
                .getResultList();
    }


    public Optional<Profile> getById(@NotNull Long id) {
        TypedQuery<Profile> q = em.createNamedQuery(Profile.GET_PROFILE_BY_ID, Profile.class)
                .setParameter("id", id);
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
