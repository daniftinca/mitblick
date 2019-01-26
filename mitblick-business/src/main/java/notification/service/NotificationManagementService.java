package notification.service;

import exception.BusinessException;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NotificationManagementService {

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    public Notification createNotification(Notification notification){
        notificationPersistenceManager.create(notification);
        return notification;
    }

    public void deleteNotification(Notification notification){
        notificationPersistenceManager.delete(notification);
    }
}
