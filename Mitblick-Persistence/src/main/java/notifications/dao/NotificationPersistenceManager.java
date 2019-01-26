package notifications.dao;

import notifications.entities.Notification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
