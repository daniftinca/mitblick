package notifications.dao;

import notifications.entities.Notification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Stateless
public class NotificationPersistenceManager {
    @PersistenceContext(unitName = "mitblick-persistence")
    private EntityManager em;

    public Notification create(Notification notification){
        em.persist(notification);
        em.flush();
        return notification;
    }

    public void delete(Notification notification){
        em.remove(notification);
    }

    public Optional<Notification> getById(Long id) {
        TypedQuery<Notification> q = em.createNamedQuery(Notification.GET_NOTIFICATION_BY_ID, Notification.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
