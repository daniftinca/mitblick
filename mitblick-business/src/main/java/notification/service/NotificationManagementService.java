package notification.service;

import exception.BusinessException;
import exception.ExceptionCode;
import notifications.dao.NotificationPersistenceManager;
import notifications.entities.Notification;
import user.dao.UserPersistenceManager;
import user.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class NotificationManagementService {

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

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

    public void markNotificationAsRead(Long notificationId) throws BusinessException{
        Optional<Notification> notificationOptional = notificationPersistenceManager.getById(notificationId);
        if(notificationOptional.isPresent()){
            Notification notification = notificationOptional.get();
            notification.setRead(true);
        }
        else
            throw new BusinessException(ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION);
    }

    public void markNotificationAsUnread(Long notificationId) throws BusinessException {
        Optional<Notification> notificationOptional = notificationPersistenceManager.getById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(false);
        } else
            throw new BusinessException(ExceptionCode.NOTIFICATION_VALIDATION_EXCEPTION);
    }

    public List<Notification> getByUserMail(String userMail) throws BusinessException{
        Optional<User> userOptional = userPersistenceManager.getUserByEmail(userMail);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.getNotifications();
        }
        else
            throw  new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
    }

    public int getAmountOfUnreadNotificationsByMail(String userMail) throws BusinessException{
        Optional<User> userOptional = userPersistenceManager.getUserByEmail(userMail);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<Notification> notificationList = new ArrayList<>();
            user.getNotifications().forEach(notification -> {
                if (!notification.getRead())
                    notificationList.add(notification);
            });
            return notificationList.size();
        }
        else
            throw  new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
    }

    public void markAllNotificationsAsRead(String userMail) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByEmail(userMail);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getNotifications().forEach(notification -> notification.setRead(true));
        }
        else
            throw  new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
    }


}
