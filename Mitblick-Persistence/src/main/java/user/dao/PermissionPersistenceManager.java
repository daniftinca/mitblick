package user.dao;

import user.entities.Permission;
import user.entities.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class PermissionPersistenceManager {


    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;


    /**
     * Removes a role from the database.
     *
     * @param role : role entity to be removed, should not be null
     */
    public void removeRole(Role role) {
        em.remove(role);
    }



    /**
     * Persists the permission from the parameter in the database
     *
     * @param permission - permission entity
     */
    public void createPermission(Permission permission) {
        em.persist(permission);
    }


    /**
     * Returns a list of all permissions saved in the database
     *
     * @return
     */
    public List<Permission> getAllPermissions() {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_ALL_PERMISSIONS, Permission.class);
        return query.getResultList();
    }


    /**
     * Persists the role from the parameter in the database
     *
     * @param role
     * @return
     */
    public Role createRole(Role role) {
        em.persist(role);
        return role;
    }

    /**
     * Returns the role of a given type
     *
     * @param type
     * @return
     */
    public Optional<Role> getRoleByType(String type) {
        TypedQuery<Role> query = em.createNamedQuery(Role.GET_ROLE_BY_TYPE, Role.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns a list of all roles
     *
     * @return
     */
    public List<Role> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        return q.getResultList();
    }

    /**
     * Returns the permissions of a given type.
     * @param type
     * @return
     */
    public Optional<Permission> getPermissionByType(String type) {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_PERMISSION_BY_TYPE, Permission.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


}

