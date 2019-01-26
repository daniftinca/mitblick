package notification.service;

import exception.BusinessException;
import exception.ExceptionCode;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class NotificationManagementService {

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    public Notification createNotification(Notification notification) {
        notificationPersistenceManager.create(notification);
        return notification;
    }

    public void deleteNotification(Long notificationId) throws BusinessException {
        Notification notification = validateNotificationForDelete(notificationId);
        notificationPersistenceManager.delete(notification);
    }

    public Notification validateNotificationForDelete(Long notificationId) throws BusinessException {
        Optional<Notification> notificationOptional = notificationPersistenceManager.getById(notificationId);
        if (notificationOptional.isPresent())
            return notificationOptional.get();
        else
            throw new BusinessException(ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION);

    }
}
