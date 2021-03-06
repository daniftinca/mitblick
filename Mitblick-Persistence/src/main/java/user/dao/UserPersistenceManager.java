package user.dao;


import user.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Provides functions for working with users in the persistence layer.
 */
@Stateless
public class UserPersistenceManager {


    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;


    /**
     * Persists a user in the database.
     *
     * @param user : user entity to be created, should not be null
     */
    public User createUser(@NotNull User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Updates a user from the database.
     *
     * @param user : user entity to be updated, should not be null
     */
    public void updateUser(@NotNull User user) {
        em.merge(user);
    }

    /**
     * Get a list of all users from the database.
     *
     * @return : ResultList, empty if there are no users in the database.
     */
    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_USERS, User.class)
                .getResultList();
    }



    /**
     * Returns a user optional containing a user entity
     * with the corresponding Id from the database.
     *
     * @param id - id corresponding with the primary key from the user table in the db
     * @return Optional of user
     */
    public Optional<User> getUserById(@NotNull Long id) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_ID, User.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }



    /**
     * Returns a user entity with the matching email wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param email : String containing the email.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByEmail(@NotNull String email) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class)
                .setParameter("email", email);
        try {
            User user = q.getSingleResult();
            em.refresh(user);
            return Optional.of(user);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> getSupervisor(String mail){
        Optional<User> userOptional = getUserByEmail(mail);
        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            String supervisorMail =  user.getSupervisorMail();
            Optional<User> supervisorOptional = getUserByEmail(supervisorMail);
            if(supervisorOptional.isPresent())
                return supervisorOptional;
        }
        return Optional.empty();
    }

}