package profile.dao;

import profile.entities.Profile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    public void delete(Profile profile) {
        em.remove(profile);
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

    /**
     * Filters the database for profiles with the given criterias.
     *
     * @param index     startindex, or where to begin the filter
     * @param amount    how many results to display
     * @return
     */
    public List<Profile> filter(int index, int amount, List<String> criteriaNames, List<String> criteriaValues) {

        CriteriaQuery<Profile> criteriaQuery = getProfileCriteriaQuery(criteriaNames, criteriaValues);

        TypedQuery<Profile> query = em.createQuery(criteriaQuery);

        query.setFirstResult(index);
        query.setMaxResults(amount);

        return query.getResultList();
    }


    public Integer filterAmount(List<String> criteriaNames, List<String> criteriaValues) {

        CriteriaQuery<Profile> criteriaQuery = getProfileCriteriaQuery(criteriaNames, criteriaValues);

        TypedQuery<Profile> query = em.createQuery(criteriaQuery);
        return query.getResultList().size();
    }

    private CriteriaQuery<Profile> getProfileCriteriaQuery(List<String> criteriaNames, List<String> criteriaValues) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Profile> criteriaQuery = builder.createQuery(Profile.class);
        Metamodel metamodel = em.getMetamodel();

        EntityType<Profile> entityType = metamodel.entity(Profile.class);
        Root<Profile> root = criteriaQuery.from(entityType);


        criteriaQuery.orderBy(builder.asc(root.get("lastName")), builder.asc(root.get("firstName")));
        buildFilterCriteria(builder, criteriaQuery, root, criteriaNames, criteriaValues);
        return criteriaQuery;
    }


    private void buildFilterCriteria(CriteriaBuilder builder, CriteriaQuery criteriaQuery, Root<Profile> root, List<String> criteriaNames, List<String> criteriaValues) {
        List<Predicate> result = new ArrayList<>();

        for (int i = 0; i < criteriaNames.size(); i++) {
            result.add(builder.like(root.get(criteriaNames.get(i)), "%" + criteriaValues.get(i) + "%"));
        }

        if (!result.isEmpty()) {
            criteriaQuery.where(result.toArray(new Predicate[0]));
        }
    }
}
